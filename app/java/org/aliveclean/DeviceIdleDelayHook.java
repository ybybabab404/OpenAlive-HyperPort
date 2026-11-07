package org.aliveclean;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

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
        if (type == null) {
            Log.w(TAG, "device idle: DeviceIdleController missing");
            return;
        }

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
                    Log.i(TAG, "device idle: first no-activity deferred");
                }
            }
        });

        Log.i(TAG, "device idle delay hook: ok");
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
                    Log.e(TAG, "device idle: deferred transition failed", error);
                }
            }
        };
        handler.postDelayed(pending, DEFER_MS);
        Log.i(TAG, "device idle: no-activity armed for " + DEFER_MS + "ms");
    }

    private static void cancel() {
        if (pending != null && handler != null) handler.removeCallbacks(pending);
        pending = null;
        deferUntil = 0;
    }
}
