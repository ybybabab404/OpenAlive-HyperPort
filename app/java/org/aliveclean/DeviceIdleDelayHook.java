package org.aliveclean;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

/**
 * system_server：亮屏后把 DeviceIdle 的「无活动」状态转换推迟 1.2 秒，给息屏/解锁动画留时间。
 * 拦截条件还要 quick doze + state=ACTIVE(0)，其他情况一律放行。
 *
 * <p>时序：{@code beforeHookedMethod} 在 {@code mScreenOn==true} 时 {@code arm()}（登记 1.2 秒后的重放）；
 * {@code afterHookedMethod} 只在<b>息屏</b>（{@code mScreenOn==false}）时 {@code cancel()}。
 * 2026-11-17 之前这里两处条件是同一个，arm 完立刻 cancel，功能等于没做（见
 * {@code docs/kb/troubleshooting/02-已定位Bug档案.md} §13.5）。</p>
 */
public final class DeviceIdleDelayHook {
    private static final String TAG = "AliveHSHooks";
    private static final long DEFER_MS = 1200L;

    private static long deferUntil;
    private static Handler handler;
    private static Runnable pending;
    private static boolean suppressedLogged;

    private DeviceIdleDelayHook() {
    }

    public static void install(ClassLoader loader) throws Throwable {
        Class<?> type = Xp.findClassIfExists("com.android.server.DeviceIdleController", loader);
        if (type == null) throw new IllegalStateException("DeviceIdleController missing");

        Xp.findAndHookMethod(type, "updateInteractivityLocked", new Xp.MethodHook() {
            @Override protected void beforeHookedMethod(Xp.MethodHookParam param) {
                Object screen = Xp.getObjectField(param.thisObject, "mScreenOn");
                if (screen instanceof Boolean && (Boolean) screen) {
                    arm(param.thisObject);
                }
            }

            @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                // 只在息屏时撤销：亮屏这次调用正是要推迟的那一次，撤掉就等于功能没做
                // （2026-11-17 修：原先这里判 mScreenOn==true，与 before 的 arm 条件相同，刚装就撤）。
                Object screen = Xp.getObjectField(param.thisObject, "mScreenOn");
                if (screen instanceof Boolean && !((Boolean) screen)) {
                    cancel();
                }
            }
        });

        Xp.findAndHookMethod(type, "becomeInactiveIfAppropriateLocked", new Xp.MethodHook() {
            @Override protected void beforeHookedMethod(Xp.MethodHookParam param) {
                if (deferUntil == 0 || SystemClock.uptimeMillis() >= deferUntil) return;
                Object quick = Xp.getObjectField(param.thisObject, "mQuickDozeActivated");
                Object state = Xp.getObjectField(param.thisObject, "mState");
                if (!(quick instanceof Boolean) || !((Boolean) quick)) return;
                if (!(state instanceof Integer) || ((Integer) state) != 0) return;
                param.setResult(null);
                if (!suppressedLogged) {
                    suppressedLogged = true;
                    AliveLog.i(TAG, "device idle: first no-activity deferred");
                }
            }
        });

        AliveLog.i(TAG, "device idle delay hook: ok");
    }

    private static void arm(Object controller) {
        if (deferUntil != 0) return;
        Handler target = null;
        Object field = Xp.getObjectField(controller, "mHandler");
        if (field instanceof Handler) target = (Handler) field;
        if (target == null) target = new Handler(Looper.getMainLooper());
        handler = target;
        deferUntil = SystemClock.uptimeMillis() + DEFER_MS;
        pending = new Runnable() {
            @Override public void run() {
                deferUntil = 0;
                pending = null;
                try {
                    Xp.callMethod(controller, "becomeInactiveIfAppropriateLocked");
                } catch (Throwable error) {
                    AliveLog.e(TAG, "device idle: deferred transition failed", error);
                }
            }
        };
        handler.postDelayed(pending, DEFER_MS);
        AliveLog.i(TAG, "device idle: no-activity armed for " + DEFER_MS + "ms");
    }

    private static void cancel() {
        if (pending != null && handler != null) handler.removeCallbacks(pending);
        pending = null;
        deferUntil = 0;
    }
}
