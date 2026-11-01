package com.miui.aod.util;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.miui.aod.AODApplication;
import com.miui.aod.AODApplicationDelegate;
import com.miui.aod.Utils;
import com.miui.aod.category.CategoryConstants;
import com.miui.miwallpaper.MiuiWallpaperManager;

/* JADX INFO: loaded from: classes.dex */
public final class WallPaperUtils {
    public static volatile WallpaperManager mWallpaperManager;
    public static volatile boolean mWallpaperSupportsAmbientMode;

    public static Bitmap getBlankSuperWallpaperThumbnail() {
        if (mWallpaperManager == null) {
            mWallpaperManager = (WallpaperManager) AODApplication.sInstance.getSystemService("wallpaper");
        }
        if (mWallpaperManager == null) {
            Log.i("WallPaperUtils", "mWallpaperManager == null");
            return null;
        }
        WallpaperInfo wallpaperInfo = mWallpaperManager.getWallpaperInfo();
        if (wallpaperInfo != null && wallpaperInfo.getServiceInfo() != null && wallpaperInfo.getServiceInfo().metaData != null) {
            String string = wallpaperInfo.getServiceInfo().metaData.getString("condition_provider");
            if (!TextUtils.isEmpty(string)) {
                try {
                    ContentResolver contentResolver = AODApplication.sInstance.getContentResolver();
                    Bundle bundle = new Bundle();
                    bundle.putString("previewType", "0");
                    Bundle bundleCall = contentResolver.call(Uri.parse(string), "getPreview", (String) null, bundle);
                    if (bundleCall != null) {
                        return (Bitmap) bundleCall.getParcelable("preview");
                    }
                } catch (Exception e) {
                    Log.e("WallPaperUtils", "getBlankSuperWallpaperThumbnail fail : " + e);
                }
            }
        }
        return null;
    }

    public static Icon getWallpaperPreview() {
        if (mWallpaperManager == null) {
            mWallpaperManager = (WallpaperManager) AODApplication.sInstance.getSystemService("wallpaper");
        }
        if (mWallpaperManager == null) {
            Log.i("WallPaperUtils", "mWallpaperManager == null");
            return null;
        }
        WallpaperInfo wallpaperInfo = mWallpaperManager.getWallpaperInfo();
        if (wallpaperInfo == null || wallpaperInfo.getServiceInfo() == null || wallpaperInfo.getServiceInfo().metaData == null) {
            return null;
        }
        return Icon.createWithResource(wallpaperInfo.getServiceInfo().packageName, wallpaperInfo.getServiceInfo().metaData.getInt(Utils.isNightMode(AODApplication.sInstance) ? "aod_small_preview_dark" : "aod_small_preview"));
    }

    public static boolean isWallpaperSupportsAmbientMode() {
        return mWallpaperSupportsAmbientMode;
    }

    public static void updateWallpaperSupportsAmbientModeState() {
        MiuiWallpaperManager miuiWallpaperManager = AODApplicationDelegate.mMiuiWallpaperManager;
        if (miuiWallpaperManager != null) {
            updateWallpaperSupportsAmbientModeState(CategoryConstants.CATEGORY_SUPER_WALLPAPER.equals(miuiWallpaperManager.getMiuiWallpaperType(1)), CategoryConstants.CATEGORY_SUPER_WALLPAPER.equals(AODApplicationDelegate.mMiuiWallpaperManager.getMiuiWallpaperType(2)));
        }
    }

    public static void updateWallpaperSupportsAmbientModeState(boolean z, boolean z2) {
        mWallpaperSupportsAmbientMode = z && z2;
    }
}
