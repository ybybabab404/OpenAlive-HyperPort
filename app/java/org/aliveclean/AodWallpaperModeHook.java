package org.aliveclean;

import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * system_server 侧：每次壁纸组件重新绑定后，确认模块壁纸是否还是当前壁纸；
 * 不是就恢复 full_screen_aod_on 的原值。静态壁纸和另一张 Live Wallpaper 都会走
 * WallpaperManagerService.bindWallpaperComponentLocked。
 */
final class AodWallpaperModeHook {
    private static final String TAG = "HyperAliveHooks";

    private AodWallpaperModeHook() {}

    static void install(ClassLoader loader) {
        Class<?> service = XposedHelpers.findClassIfExists(
                "com.android.server.wallpaper.WallpaperManagerService", loader);
        if (service == null) {
            Log.w(TAG, "WallpaperManagerService missing");
            return;
        }
        try {
            Class<?> data = XposedHelpers.findClass("com.android.server.wallpaper.WallpaperData", loader);
            Class<?> callback = XposedHelpers.findClass("android.os.IRemoteCallback", loader);
            XposedHelpers.findAndHookMethod(service, "bindWallpaperComponentLocked",
                    ComponentName.class, boolean.class, boolean.class, data, callback,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            Object raw = XposedHelpers.getObjectField(param.thisObject, "mContext");
                            if (!(raw instanceof Context)) return;
                            final Context context = (Context) raw;
                            // 绑定动作可能成对出现（桌面 + 锁屏），等它落定后再判断；
                            // 也不能在服务持锁时回调 WallpaperManager，否则会和 binder 线程互锁。
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        if (!AodWallpaperMode.alive(context)) {
                                            AodWallpaperMode.restore(context);
                                        }
                                    } catch (Throwable error) {
                                        Log.w(TAG, "aod restore check failed", error);
                                    }
                                }
                            }, 800);
                        }
                    });
            Log.i(TAG, "aod wallpaper restore hook: ok");
        } catch (Throwable error) {
            Log.e(TAG, "aod wallpaper restore hook failed", error);
        }
    }
}
