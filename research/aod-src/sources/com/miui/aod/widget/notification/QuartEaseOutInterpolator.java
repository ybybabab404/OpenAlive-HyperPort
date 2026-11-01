package com.miui.aod.widget.notification;

import android.view.animation.Interpolator;

/* JADX INFO: loaded from: classes.dex */
public class QuartEaseOutInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        float f2 = f - 1.0f;
        return -((((f2 * f2) * f2) * f2) - 1.0f);
    }
}
