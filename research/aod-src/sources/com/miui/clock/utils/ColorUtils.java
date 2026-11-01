package com.miui.clock.utils;

import android.graphics.Color;
import androidx.exifinterface.media.ExifInterface;

/* JADX INFO: loaded from: classes.dex */
public class ColorUtils {
    public static int blendColor(int i, float f) {
        return Color.argb((int) (Color.alpha(i) * f), Color.red(i), Color.green(i), Color.blue(i));
    }

    public static String getColorHex(int i) {
        return String.format("#%08X", Integer.valueOf(i));
    }

    public static int setAlphaComponent(int i, int i2) {
        return (i & 16777215) | ((i2 & 255) << 24);
    }

    public static int toOpaqueRed255(int i) {
        return (i & ExifInterface.COLOR_SPACE_UNCALIBRATED) | (-65536);
    }
}
