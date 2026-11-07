package org.aliveclean;

import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

final class AodWallpaperModeHook {
    private static final String TAG = "AliveHSHooks";

    private AodWallpaperModeHook() {}

    static void install(ClassLoader loader) {
        Class<?> service = Xp.findClassIfExists(
                "com.android.server.wallpaper.WallpaperManagerService", loader);
        if (service == null) {
            AliveLog.w(TAG, "WallpaperManagerService missing");
            return;
        }
        try {
            Class<?> data = Xp.findClass("com.android.server.wallpaper.WallpaperData", loader);
            Class<?> callback = Xp.findClass("android.os.IRemoteCallback", loader);
            Xp.findAndHookMethod(service, "bindWallpaperComponentLocked",
                    ComponentName.class, boolean.class, boolean.class, data, callback,
                    new Xp.MethodHook() {
                        @Override
                        protected void afterHookedMethod(Xp.MethodHookParam param) {
                            Object raw = Xp.getObjectField(param.thisObject, "mContext");
                            if (!(raw instanceof Context)) return;
                            final Context context = (Context) raw;

                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        if (!AodWallpaperMode.alive(context)) {
                                            AodWallpaperMode.restore(context);
                                        }
                                    } catch (Throwable error) {
                                        AliveLog.w(TAG, "aod restore check failed", error);
                                    }
                                }
                            }, 800);
                        }
                    });
            AliveLog.i(TAG, "aod wallpaper restore hook: ok");
        } catch (Throwable error) {
            AliveLog.e(TAG, "aod wallpaper restore hook failed", error);
        }
    }
}
