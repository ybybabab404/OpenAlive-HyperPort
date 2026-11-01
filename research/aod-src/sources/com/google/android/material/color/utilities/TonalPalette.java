package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public final class TonalPalette {
    public Map cache = new HashMap();
    public double chroma;
    public double hue;
    public Hct keyColor;

    public TonalPalette(double d, double d2, Hct hct) {
        this.hue = d;
        this.chroma = d2;
        this.keyColor = hct;
    }

    public static Hct createKeyColor(double d, double d2) {
        Hct hctFrom = Hct.from(d, d2, 50.0d);
        double dAbs = Math.abs(hctFrom.getChroma() - d2);
        for (double d3 = 1.0d; d3 < 50.0d && Math.round(d2) != Math.round(hctFrom.getChroma()); d3 += 1.0d) {
            Hct hctFrom2 = Hct.from(d, d2, 50.0d + d3);
            double dAbs2 = Math.abs(hctFrom2.getChroma() - d2);
            if (dAbs2 < dAbs) {
                dAbs = dAbs2;
                hctFrom = hctFrom2;
            }
            Hct hctFrom3 = Hct.from(d, d2, 50.0d - d3);
            double dAbs3 = Math.abs(hctFrom3.getChroma() - d2);
            if (dAbs3 < dAbs) {
                dAbs = dAbs3;
                hctFrom = hctFrom3;
            }
        }
        return hctFrom;
    }

    public static TonalPalette fromHct(Hct hct) {
        return new TonalPalette(hct.getHue(), hct.getChroma(), hct);
    }

    public static TonalPalette fromHueAndChroma(double d, double d2) {
        return new TonalPalette(d, d2, createKeyColor(d, d2));
    }

    public double getChroma() {
        return this.chroma;
    }

    public Hct getHct(double d) {
        return Hct.from(this.hue, this.chroma, d);
    }

    public double getHue() {
        return this.hue;
    }
}
