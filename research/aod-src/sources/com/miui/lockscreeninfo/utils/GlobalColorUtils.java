package com.miui.lockscreeninfo.utils;

import com.miui.lockscreeninfo.hct.Hct;
import com.miui.lockscreeninfo.hct.HctSolver;

/* JADX INFO: loaded from: classes2.dex */
public class GlobalColorUtils {
    public static int transformFullAodColor(int i) {
        Hct hctFromInt = Hct.fromInt(i);
        double tone = hctFromInt.getTone();
        double chroma = hctFromInt.getChroma();
        double dCos = (Math.cos((tone * 0.04800000041723251d) - 5.400000095367432d) * 20.0d) + 74.0d;
        return HctSolver.solveToInt(hctFromInt.getHue(), chroma * 1.2000000476837158d, dCos);
    }
}
