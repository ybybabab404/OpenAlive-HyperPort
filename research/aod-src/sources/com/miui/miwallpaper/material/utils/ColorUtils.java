package com.miui.miwallpaper.material.utils;

/* JADX INFO: loaded from: classes2.dex */
public class ColorUtils {
    public static final double[][] SRGB_TO_XYZ = {new double[]{0.41233895d, 0.35762064d, 0.18051042d}, new double[]{0.2126d, 0.7152d, 0.0722d}, new double[]{0.01932141d, 0.11916382d, 0.95034478d}};
    public static final double[][] XYZ_TO_SRGB = {new double[]{3.2413774792388685d, -1.5376652402851851d, -0.49885366846268053d}, new double[]{-0.9691452513005321d, 1.8758853451067872d, 0.04156585616912061d}, new double[]{0.05562093689691305d, -0.20395524564742123d, 1.0571799111220335d}};
    public static final double[] WHITE_POINT_D65 = {95.047d, 100.0d, 108.883d};

    public static double labInvf(double d) {
        double d2 = d * d * d;
        return d2 > 0.008856451679035631d ? d2 : ((d * 116.0d) - 16.0d) / 903.2962962962963d;
    }

    public static double[] whitePointD65() {
        return WHITE_POINT_D65;
    }

    public static double yFromLstar(double d) {
        return labInvf((d + 16.0d) / 116.0d) * 100.0d;
    }
}
