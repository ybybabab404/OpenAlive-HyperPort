package com.miui.maml.util;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import java.io.File;

/* JADX INFO: loaded from: classes2.dex */
public class CustomUtils {
    private static final int WALLPAPER_SUPPORT_SCALE_CONFIG_DISABLED = 0;
    private static final int WALLPAPER_SUPPORT_SCALE_CONFIG_ENABLED = 1;
    private static final String WALLPAPER_SUPPORT_SCALE_CONFIG_PATH = "system/media/wallpaper/maml-wallpaper-scale-config";
    private static final int WALLPAPER_SUPPORT_SCALE_CONFIG_UNDEFINE = -1;
    private static int mWallpaperImageSupportScale = -1;

    private CustomUtils() {
    }

    public static boolean isWallpaperImageSupportScale() {
        if (mWallpaperImageSupportScale == -1) {
            mWallpaperImageSupportScale = new File(WALLPAPER_SUPPORT_SCALE_CONFIG_PATH).exists() ? 1 : 0;
        }
        return mWallpaperImageSupportScale == 1;
    }

    public static void replaceCameraIntentInfoOnF3M(String str, String str2, Intent intent) {
        if ("vela".equals(Build.DEVICE) && intent != null && "com.android.camera".equals(str) && "com.android.camera.Camera".equals(str2)) {
            intent.setComponent(new ComponentName("com.mlab.cam", "com.mtlab.camera.CameraActivity"));
            if ("android.intent.action.MAIN".equals(intent.getAction())) {
                intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
            }
        }
    }
}
