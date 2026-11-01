package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;
import com.miui.aod.components.view.BaseStyleSelectView;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public final class Contrast {
    public static double darker(double d, double d2) {
        if (d >= BaseStyleSelectView.COLLAPSE_SLIDE_VALUE && d <= 100.0d) {
            double dYFromLstar = ColorUtils.yFromLstar(d);
            double d3 = ((dYFromLstar + 5.0d) / d2) - 5.0d;
            if (d3 >= BaseStyleSelectView.COLLAPSE_SLIDE_VALUE && d3 <= 100.0d) {
                double dRatioOfYs = ratioOfYs(dYFromLstar, d3);
                double dAbs = Math.abs(dRatioOfYs - d2);
                if (dRatioOfYs < d2 && dAbs > 0.04d) {
                    return -1.0d;
                }
                double dLstarFromY = ColorUtils.lstarFromY(d3) - 0.4d;
                if (dLstarFromY >= BaseStyleSelectView.COLLAPSE_SLIDE_VALUE && dLstarFromY <= 100.0d) {
                    return dLstarFromY;
                }
            }
        }
        return -1.0d;
    }

    public static double darkerUnsafe(double d, double d2) {
        return Math.max(BaseStyleSelectView.COLLAPSE_SLIDE_VALUE, darker(d, d2));
    }

    public static double lighter(double d, double d2) {
        if (d >= BaseStyleSelectView.COLLAPSE_SLIDE_VALUE && d <= 100.0d) {
            double dYFromLstar = ColorUtils.yFromLstar(d);
            double d3 = ((dYFromLstar + 5.0d) * d2) - 5.0d;
            if (d3 >= BaseStyleSelectView.COLLAPSE_SLIDE_VALUE && d3 <= 100.0d) {
                double dRatioOfYs = ratioOfYs(d3, dYFromLstar);
                double dAbs = Math.abs(dRatioOfYs - d2);
                if (dRatioOfYs < d2 && dAbs > 0.04d) {
                    return -1.0d;
                }
                double dLstarFromY = ColorUtils.lstarFromY(d3) + 0.4d;
                if (dLstarFromY >= BaseStyleSelectView.COLLAPSE_SLIDE_VALUE && dLstarFromY <= 100.0d) {
                    return dLstarFromY;
                }
            }
        }
        return -1.0d;
    }

    public static double lighterUnsafe(double d, double d2) {
        double dLighter = lighter(d, d2);
        if (dLighter < BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
            return 100.0d;
        }
        return dLighter;
    }

    public static double ratioOfTones(double d, double d2) {
        return ratioOfYs(ColorUtils.yFromLstar(d), ColorUtils.yFromLstar(d2));
    }

    public static double ratioOfYs(double d, double d2) {
        double dMax = Math.max(d, d2);
        if (dMax != d2) {
            d = d2;
        }
        return (dMax + 5.0d) / (d + 5.0d);
    }
}
