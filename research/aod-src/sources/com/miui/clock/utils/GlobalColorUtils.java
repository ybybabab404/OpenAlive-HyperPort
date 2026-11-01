package com.miui.clock.utils;

import com.miui.clock.hct.Hct;
import com.miui.clock.hct.HctSolver;

/* JADX INFO: loaded from: classes.dex */
public class GlobalColorUtils {
    public static int addColorTone(int i, double d) {
        Hct hctFromInt = Hct.fromInt(i);
        hctFromInt.setTone(hctFromInt.getTone() + d);
        return hctFromInt.toInt();
    }

    public static int transformAodColor(int i) {
        Hct hctFromInt = Hct.fromInt(i);
        double tone = hctFromInt.getTone();
        double chroma = hctFromInt.getChroma();
        double dCos = (Math.cos((tone * 0.052000001072883606d) - 5.199999809265137d) * 16.0d) + 72.0d;
        return HctSolver.solveToInt(hctFromInt.getHue(), chroma * 1.2000000476837158d, dCos);
    }

    public static int transformFullAodColor(int i) {
        Hct hctFromInt = Hct.fromInt(i);
        double tone = hctFromInt.getTone();
        double chroma = hctFromInt.getChroma();
        double dCos = (Math.cos((tone * 0.04800000041723251d) - 5.400000095367432d) * 20.0d) + 74.0d;
        return HctSolver.solveToInt(hctFromInt.getHue(), chroma * 1.2000000476837158d, dCos);
    }

    public static int transformSmartFrameAodColor(int i) {
        Hct hctFromInt = Hct.fromInt(transformAodColor(i));
        return HctSolver.solveToInt(hctFromInt.getHue(), Math.log((hctFromInt.getChroma() * 3.0d) + 1.0d) * 8.0d, hctFromInt.getTone());
    }

    public static int transformWithTone(int i, int i2) {
        Hct hctFromInt = Hct.fromInt(i);
        return HctSolver.solveToInt(hctFromInt.getHue(), hctFromInt.getChroma(), i2);
    }
}
