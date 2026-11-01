package com.miui.clock.tiny.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.WindowManager;
import miuix.core.util.SystemProperties;

/* JADX INFO: loaded from: classes.dex */
public class DeviceConfig {
    public static int mBackgroundBlurEnabled = -1;
    public static final boolean BACKGROUND_BLUR_SUPPORTED_2 = SystemProperties.getBoolean("persist.sys.background_blur_supported", false);
    public static final int BACKGROUND_BLUR_VERSION = SystemProperties.getInt("persist.sys.background_blur_version", 0);
    public static String DEVICE_O8 = "bixi";
    public static String DEVICE_N8 = "ruyi";

    public static int getRotationInCutout(Context context) {
        DisplayCutout cutout = ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getCutout();
        if (cutout == null) {
            return 0;
        }
        cutout.getBoundingRectLeft();
        return !cutout.getBoundingRectRight().isEmpty() ? 2 : 0;
    }

    public static int isBackgroundBlurEnabled(Context context) {
        if (!DateFormatUtils.isSystemUI(context)) {
            return Settings.Secure.getInt(context.getContentResolver(), "background_blur_enable", 0);
        }
        try {
            int iUpdateCurrentUserId = DateFormatUtils.updateCurrentUserId();
            Class cls = Integer.TYPE;
            return ((Integer) Settings.Secure.class.getMethod("getIntForUser", ContentResolver.class, String.class, cls, cls).invoke(null, context.getContentResolver(), "background_blur_enable", 0, Integer.valueOf(iUpdateCurrentUserId))).intValue();
        } catch (Exception e) {
            Log.e("ClockDeviceConfig", "getIntForUser fail", e);
            return 0;
        }
    }

    public static boolean isN8() {
        return TextUtils.equals(Build.DEVICE, DEVICE_N8);
    }

    public static boolean supportBackgroundBlur(Context context) {
        int iIsBackgroundBlurEnabled = isBackgroundBlurEnabled(context);
        mBackgroundBlurEnabled = iIsBackgroundBlurEnabled;
        return BACKGROUND_BLUR_SUPPORTED_2 && iIsBackgroundBlurEnabled == 1;
    }

    public static boolean supportBlurMode1000() {
        return BACKGROUND_BLUR_VERSION >= 2;
    }
}
