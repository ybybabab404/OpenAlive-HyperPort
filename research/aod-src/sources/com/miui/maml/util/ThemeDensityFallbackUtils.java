package com.miui.maml.util;

import android.content.res.Configuration;
import android.content.res.Resources;
import com.miui.maml.R;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/* JADX INFO: loaded from: classes2.dex */
public class ThemeDensityFallbackUtils {
    private static final int DENSITY_NONE = 1;
    private static final int[] DENSITIES = {640, 480, 320, 240, 0};
    private static final int[] ALL_SUPPORT_DENSITY = {640, 480, 320, 440, 240, 160, R.styleable.AppCompatTheme_windowFixedHeightMajor};

    private ThemeDensityFallbackUtils() {
    }

    public static String findNearestDir(String str, final int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (true) {
            int[] iArr = ALL_SUPPORT_DENSITY;
            if (i2 >= iArr.length) {
                break;
            }
            arrayList.add(Integer.valueOf(iArr[i2]));
            i2++;
        }
        Collections.sort(arrayList, new Comparator<Integer>() { // from class: com.miui.maml.util.ThemeDensityFallbackUtils.1
            @Override // java.util.Comparator
            public int compare(Integer num, Integer num2) {
                return Math.abs(num.intValue() - i) - Math.abs(num2.intValue() - i);
            }
        });
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            String str2 = "drawable" + getScreenWidthSuffix(Resources.getSystem().getConfiguration()) + getDensitySuffix(((Integer) arrayList.get(i3)).intValue());
            if (new File(str + str2).exists()) {
                return str2;
            }
        }
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            String str3 = "drawable" + getDensitySuffix(((Integer) arrayList.get(i4)).intValue());
            if (new File(str + str3).exists()) {
                return str3;
            }
        }
        if (new File(str + "drawable").exists()) {
            return "drawable";
        }
        return null;
    }

    public static int findNearestSupportDensity(int i) {
        int i2 = 440;
        int i3 = Integer.MAX_VALUE;
        int i4 = 0;
        while (true) {
            int[] iArr = ALL_SUPPORT_DENSITY;
            if (i4 >= iArr.length) {
                return i2;
            }
            int iAbs = Math.abs(i - iArr[i4]);
            if (iAbs < i3) {
                i2 = iArr[i4];
                i3 = iAbs;
            }
            i4++;
        }
    }

    public static String getDensitySuffix(int i) {
        if (i == 0) {
            return "";
        }
        if (i == 1) {
            return "-nodpi";
        }
        if (i == 120) {
            return "-ldpi";
        }
        if (i == 160) {
            return "-mdpi";
        }
        if (i == 240) {
            return "-hdpi";
        }
        if (i == 320) {
            return "-xhdpi";
        }
        if (i == 440) {
            return "-nxhdpi";
        }
        if (i == 480) {
            return "-xxhdpi";
        }
        if (i == 640) {
            return "-xxxhdpi";
        }
        int length = DENSITIES.length;
        int i2 = length - 1;
        for (int i3 = length - 2; i3 >= 0; i3--) {
            int[] iArr = DENSITIES;
            if (Math.abs(iArr[i3] - i) <= Math.abs(iArr[i2] - i)) {
                i2 = i3;
            }
        }
        return getDensitySuffix(DENSITIES[i2]);
    }

    public static String getScreenWidthSuffix(Configuration configuration) {
        return configuration.smallestScreenWidthDp >= 720 ? "-sw720dp" : "";
    }
}
