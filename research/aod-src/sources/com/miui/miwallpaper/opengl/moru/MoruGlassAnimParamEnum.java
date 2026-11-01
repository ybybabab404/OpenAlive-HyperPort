package com.miui.miwallpaper.opengl.moru;

import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

/* JADX INFO: loaded from: classes2.dex */
public enum MoruGlassAnimParamEnum {
    REFRACTION(new DecelerateInterpolator(1.2f), 2700),
    PRISM(new DecelerateInterpolator(1.5f), 2000),
    THIN_LINE(new DecelerateInterpolator(1.0f), 2700);

    public final long animDuration;
    public final Interpolator interpolator;

    MoruGlassAnimParamEnum(Interpolator interpolator, long j) {
        this.interpolator = interpolator;
        this.animDuration = j;
    }
}
