package org.aliveclean;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

final class AodWallpaperMode {
    static final String KEY_MODE = "full_screen_aod_on";
    static final String KEY_RESTORE = "alive_aod_restore_value";
    static final String KEY_HAS_RESTORE = "alive_aod_restore_set";
    private static final String TAG = "AliveHSHooks";
    private static final int UNSET = -1;

    private AodWallpaperMode() {}

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
            AliveLog.w(TAG, "aod wallpaper lookup failed", error);
            return false;
        }
    }

    static void enter(Context context) {
        ContentResolver resolver = context.getContentResolver();
        int original;
        try {
            original = Settings.Secure.getInt(resolver, KEY_MODE, UNSET);
        } catch (Throwable error) {
            AliveLog.w(TAG, "aod mode read failed; left untouched", error);
            return;
        }
        if (original == UNSET) {
            AliveLog.w(TAG, "aod mode absent; left untouched");
            return;
        }
        try {
            if (Settings.Secure.getInt(resolver, KEY_HAS_RESTORE, 0) != 1) {
                Settings.Secure.putInt(resolver, KEY_RESTORE, original);
                Settings.Secure.putInt(resolver, KEY_HAS_RESTORE, 1);
            }
            Settings.Secure.putInt(resolver, KEY_MODE, 1);
            AliveLog.i(TAG, "aod mode ->1 (original=" + original + ")");
        } catch (Throwable error) {
            AliveLog.w(TAG, "aod mode write failed", error);
        }
    }

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
            AliveLog.i(TAG, "aod mode restored=" + original);
        } catch (Throwable error) {
            AliveLog.w(TAG, "aod mode restore failed", error);
        }
    }
}
