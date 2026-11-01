package com.miui.aod.utils;

import android.app.WallpaperInfo;

/* JADX INFO: loaded from: classes.dex */
public final class WallpaperInfoUtils {
    private WallpaperInfoUtils() {
    }

    public static boolean supportsAmbientMode(WallpaperInfo wallpaperInfo) {
        return wallpaperInfo.supportsAmbientMode();
    }
}
