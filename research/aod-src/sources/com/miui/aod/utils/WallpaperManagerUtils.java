package com.miui.aod.utils;

import android.annotation.SuppressLint;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.os.Build;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public final class WallpaperManagerUtils {
    private static final String TAG = "WallpaperManagerUtils";

    private WallpaperManagerUtils() {
    }

    @SuppressLint({"NewApi"})
    public static WallpaperInfo getWallpaperInfo(WallpaperManager wallpaperManager, int i) {
        try {
            return Build.VERSION.SDK_INT >= 34 ? (WallpaperInfo) wallpaperManager.getClass().getMethod("getWallpaperInfoForUser", Integer.TYPE).invoke(wallpaperManager, Integer.valueOf(i)) : wallpaperManager.getWallpaperInfo(i);
        } catch (Exception e) {
            Log.wtf(TAG, "wallpaperInfo acquire error ", e);
            return null;
        }
    }
}
