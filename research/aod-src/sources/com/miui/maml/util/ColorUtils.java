package com.miui.maml.util;

import android.graphics.Color;

/* JADX INFO: loaded from: classes2.dex */
public class ColorUtils {
    public static String ColorToHex(int i) {
        int iAlpha = Color.alpha(i);
        int iRed = Color.red(i);
        int iGreen = Color.green(i);
        int iBlue = Color.blue(i);
        return "#" + toHex(iAlpha) + toHex(iRed) + toHex(iGreen) + toHex(iBlue);
    }

    private static String toHex(int i) {
        String strConcat = "00".concat(Integer.toHexString(i));
        return strConcat.substring(strConcat.length() - 2);
    }
}
