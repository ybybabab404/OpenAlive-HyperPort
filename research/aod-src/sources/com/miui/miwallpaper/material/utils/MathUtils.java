package com.miui.miwallpaper.material.utils;

/* JADX INFO: loaded from: classes2.dex */
public class MathUtils {
    public static double clampDouble(double d, double d2, double d3) {
        if (d3 < d) {
            return d;
        }
        return d3 > d2 ? d2 : d3;
    }

    public static double lerp(double d, double d2, double d3) {
        return ((1.0d - d3) * d) + (d3 * d2);
    }
}
