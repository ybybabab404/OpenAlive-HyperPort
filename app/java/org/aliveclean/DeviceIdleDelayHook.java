package org.aliveclean;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

/**
 * system_server：想在亮屏后把 DeviceIdle 的「无活动」状态转换推迟 1.2 秒，给息屏/解锁动画留时间。
 * 拦截条件是 quick doze + state=ACTIVE(0)，其他情况一律放行。
 *
 * <p><b>当前实现并不生效</b>：{@code beforeHookedMethod} 在 {@code mScreenOn==true} 时 {@code arm()}，
 * 而同一方法（{@code updateInteractivityLocked}）的 {@code afterHookedMethod} 在<b>同一条件</b>下
 * {@code cancel()}，刚装上的延时任务立刻被撤掉。要真正推迟，after 的取消条件应改成息屏
 * （{@code mScreenOn==false}），或只在 false→true 的边沿 arm。已记在
 * {@code docs/kb/troubleshooting/02-已定位Bug档案.md} §13.5，尚未修改。</p>
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
                Object screen = Xp.getObjectField(param.thisObject, "mScreenOn");
                if (screen instanceof Boolean && (Boolean) screen) {
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
