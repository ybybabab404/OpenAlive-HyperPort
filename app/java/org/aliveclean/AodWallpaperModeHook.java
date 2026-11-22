package org.aliveclean;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * The only reliable place to notice a wallpaper change is the system_server's
 * WallpaperManagerService.  The old implementation only hooked
 * bindWallpaperComponentLocked, so a switch back to a static system wallpaper could
 * leave full_screen_aod_on=1 until the next process restart.  Hook the public mutation
 * entry points as well; each one posts the same delayed check, and the restore probe
 * inside it is deduplicated, so a burst of mutations costs one real check.
 */
final class AodWallpaperModeHook {
    private static final String TAG = "AliveHSHooks";
    /** 壁纸变更后的首检延迟；配合 {@code AodWallpaperMode} 里的 150/350ms 两次探测，整体约 1 秒内收敛。 */
    private static final long CHECK_DELAY_MS = 250L;

    private AodWallpaperModeHook() {}

    static void install(ClassLoader loader) {
        Class<?> service = Xp.findClassIfExists(
                "com.android.server.wallpaper.WallpaperManagerService", loader);
        if (service == null) throw new IllegalStateException("WallpaperManagerService missing");
        int hooks = 0;
        hooks += hookBind(service);
        hooks += hookMutations(service);
        AliveLog.i(TAG, "aod wallpaper restore hooks=" + hooks);
    }

    private static int hookBind(Class<?> service) {
        // Static wallpapers are bound by WallpaperObserver through
        // bindWallpaperDescriptionLocked(...), not bindWallpaperComponentLocked(...).
        int count = hookMutation(service, "bindWallpaperDescriptionLocked");
        if (count == 0) {
            count += hookMutation(service, "bindWallpaperComponentLocked");
        }
        return count;
    }

    private static int hookMutations(Class<?> service) {
        int count = 0;
        count += hookMutation(service, "setWallpaper");
        count += hookMutation(service, "setWallpaperComponent");
        count += hookMutation(service, "setWallpaperComponentWithFlags");
        count += hookMutation(service, "clearWallpaper");
        return count;
    }

    private static Context serviceContext(Object service) {
        try {
            Object raw = Xp.getObjectField(service, "mContext");
            return raw instanceof Context ? (Context) raw : null;
        } catch (Throwable error) {
            return null;
        }
    }

    private static int hookMutation(Class<?> service, String name) {
        try {
            return Xp.hookAllMethods(service, name, new Xp.MethodHook() {
                @Override
                protected void afterHookedMethod(Xp.MethodHookParam param) {
                    postCheck(param.thisObject, name);
                }
            }).size();
        } catch (Throwable error) {
            AliveLog.w(TAG, "aod wallpaper " + name + " hook failed", error);
            return 0;
        }
    }

    private static void postCheck(Object service, final String source) {
        final Context context = serviceContext(service);
        if (context == null) return;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    // 桌面槽独立判定：锁屏是我们的并不代表桌面也已恢复。
                    // 只在开机后的一小段时间内把桌面壁纸恢复成我们的（解决"重启解锁后
                    // 桌面壁纸回落"）。用户后来在个性化/编辑页主动应用系统壁纸时，
                    // 系统自己会把组件换成 MIUI 静态壁纸 —— 那是用户要的结果，不能抢回来。
                    long sinceBoot = android.os.SystemClock.elapsedRealtime();
                    if (sinceBoot < 5 * 60 * 1000L) {
                        AodWallpaperMode.restoreHomeWhenForeign(context, source);
                    } else {
                        AliveLog.i(TAG, "home wallpaper restore skipped (past boot window) source=" + source);
                    }
                    if (AodWallpaperMode.alive(context)) {
                        AodWallpaperMode.ensureEnabled(context);
                    } else {
                        AodWallpaperMode.restoreWhenForeign(context);
                    }
                } catch (Throwable error) {
                    AliveLog.w(TAG, "aod restore check failed", error);
                }
            }
        }, CHECK_DELAY_MS);
    }
}
