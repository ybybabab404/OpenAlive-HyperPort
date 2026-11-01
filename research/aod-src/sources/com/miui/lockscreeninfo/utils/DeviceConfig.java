package com.miui.lockscreeninfo.utils;

import android.os.Build;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes2.dex */
public class DeviceConfig {
    public static String DEVICE_N8 = "ruyi";
    public static String DEVICE_O8 = "bixi";

    public static boolean isLongScreenDevice() {
        return isN8() || isO8();
    }

    public static boolean isN8() {
        return TextUtils.equals(Build.DEVICE, DEVICE_N8);
    }

    public static boolean isO8() {
        return TextUtils.equals(Build.DEVICE, DEVICE_O8);
    }
}
