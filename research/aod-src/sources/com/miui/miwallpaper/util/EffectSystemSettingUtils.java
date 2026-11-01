package com.miui.miwallpaper.util;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import miui.os.Build;

/* JADX INFO: loaded from: classes2.dex */
public class EffectSystemSettingUtils {
    public static final boolean IS_FOLD_DEVICE = isFoldDevices();
    public static final boolean IS_TABLE_OR_FOLD;
    public static final boolean NEED_ROTATE_WALLPAPER;

    static {
        boolean z = isFoldDevices() || Build.IS_TABLET;
        IS_TABLE_OR_FOLD = z;
        NEED_ROTATE_WALLPAPER = z;
    }

    public static boolean isFoldDevices() {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return ((Integer) cls.getMethod("getInt", String.class, Integer.TYPE).invoke(cls, "persist.sys.muiltdisplay_type", 0)).intValue() == 2;
        } catch (Throwable th) {
            Log.e("SystemSettingUtils", "isFoldDevices fail : ", th);
            return false;
        }
    }

    public static boolean isLargeScreen(Context context) {
        return isLargeScreen(context.getResources().getConfiguration());
    }

    public static boolean isLargeScreen(Configuration configuration) {
        return (configuration.screenLayout & 15) == 3;
    }

    public static boolean isScreenOrientationLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }
}
