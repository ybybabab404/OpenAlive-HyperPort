package org.aliveclean;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

/**
 * 「息屏样式」在模块壁纸期间的接管状态。
 *
 * 写入方和读取方是两个 uid：org.aliveclean 的 system-uid 适配进程负责写，
 * com.miui.aod 负责读和兜底恢复。SharedPreferences 是各自私有的，跨进程读不到，
 * 所以原值和接管标记放在 Settings.Secure（全系统可读，写入需要 WRITE_SECURE_SETTINGS）。
 */
final class AodWallpaperMode {
    static final String KEY_MODE = "full_screen_aod_on";
    static final String KEY_RESTORE = "alive_aod_restore_value";
    static final String KEY_HAS_RESTORE = "alive_aod_restore_set";
    private static final String TAG = "HyperAliveHooks";
    private static final int UNSET = -1;

    private AodWallpaperMode() {}

    /** 当前桌面/锁屏壁纸是不是本模块的 Live Wallpaper。 */
    static boolean alive(Context context) {
        try {
            WallpaperManager manager = WallpaperManager.getInstance(context);
            WallpaperInfo info = manager.getWallpaperInfo();
            if (info == null && android.os.Build.VERSION.SDK_INT >= 34) {
                info = manager.getWallpaperInfo(WallpaperManager.FLAG_LOCK);
            }
            return info != null && info.getComponent() != null
                    && "org.aliveclean".equals(info.getComponent().getPackageName());
        } catch (Throwable error) {
            Log.w(TAG, "aod wallpaper lookup failed", error);
            return false;
        }
    }

    /** 壁纸应用成功后调用（UID 1000）。读不到原值就不写，只记日志。 */
    static void enter(Context context) {
        ContentResolver resolver = context.getContentResolver();
        int original;
        try {
            original = Settings.Secure.getInt(resolver, KEY_MODE, UNSET);
        } catch (Throwable error) {
            Log.w(TAG, "aod mode read failed; left untouched", error);
            return;
        }
        if (original == UNSET) {
            Log.w(TAG, "aod mode absent; left untouched");
            return;
        }
        try {
            if (Settings.Secure.getInt(resolver, KEY_HAS_RESTORE, 0) != 1) {
                Settings.Secure.putInt(resolver, KEY_RESTORE, original);
                Settings.Secure.putInt(resolver, KEY_HAS_RESTORE, 1);
            }
            Settings.Secure.putInt(resolver, KEY_MODE, 1);
            Log.i(TAG, "aod mode ->1 (original=" + original + ")");
        } catch (Throwable error) {
            Log.w(TAG, "aod mode write failed", error);
        }
    }

    /** 切回系统壁纸后恢复原值；没有接管记录时是空操作。 */
    static void restore(Context context) {
        ContentResolver resolver = context.getContentResolver();
        try {
            if (Settings.Secure.getInt(resolver, KEY_HAS_RESTORE, 0) != 1) return;
            int original = Settings.Secure.getInt(resolver, KEY_RESTORE, UNSET);
            if (original == UNSET) {
                Settings.Secure.putString(resolver, KEY_MODE, null);
            } else {
                Settings.Secure.putInt(resolver, KEY_MODE, original);
            }
            Settings.Secure.putString(resolver, KEY_RESTORE, null);
            Settings.Secure.putString(resolver, KEY_HAS_RESTORE, null);
            Log.i(TAG, "aod mode restored=" + original);
        } catch (Throwable error) {
            Log.w(TAG, "aod mode restore failed", error);
        }
    }
}
