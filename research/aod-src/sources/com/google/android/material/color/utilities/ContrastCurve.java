package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;
import com.miui.aod.components.view.BaseStyleSelectView;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public final class ContrastCurve {
    public final double high;
    public final double low;
    public final double medium;
    public final double normal;

    public ContrastCurve(double d, double d2, double d3, double d4) {
        this.low = d;
        this.normal = d2;
        this.medium = d3;
        this.high = d4;
    }

    public double getContrast(double d) {
        if (d <= -1.0d) {
            return this.low;
        }
        if (d < BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
            return MathUtils.lerp(this.low, this.normal, (d - (-1.0d)) / 1.0d);
        }
        if (d < 0.5d) {
            return MathUtils.lerp(this.normal, this.medium, (d - BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) / 0.5d);
        }
        return d < 1.0d ? MathUtils.lerp(this.medium, this.high, (d - 0.5d) / 0.5d) : this.high;
    }
}
