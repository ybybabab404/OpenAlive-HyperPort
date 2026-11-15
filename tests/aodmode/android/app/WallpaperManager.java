package android.app;

import android.content.Context;

/** WallpaperManager double; tests set the static fields directly. */
public class WallpaperManager {
    public static final int FLAG_SYSTEM = 1;

    public static WallpaperInfo info;
    public static int systemId = -1;
    public static boolean throwOnInfo;
    public static boolean throwOnId;

    private static final WallpaperManager INSTANCE = new WallpaperManager();

    public static WallpaperManager getInstance(Context context) { return INSTANCE; }

    public WallpaperInfo getWallpaperInfo() {
        if (throwOnInfo) throw new IllegalStateException("wallpaper info unavailable");
        return info;
    }

    public int getWallpaperId(int flag) {
        if (throwOnId) throw new IllegalStateException("wallpaper id unavailable");
        return systemId;
    }
}
