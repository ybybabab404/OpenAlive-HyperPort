package com.airbnb.lottie.value;

import android.view.animation.Interpolator;

/* JADX INFO: loaded from: classes.dex */
abstract class LottieInterpolatedValue<T> extends LottieValueCallback<T> {
    public final Object endValue;
    public final Interpolator interpolator;
    public final Object startValue;

    @Override // com.airbnb.lottie.value.LottieValueCallback
    public Object getValue(LottieFrameInfo lottieFrameInfo) {
        return interpolateValue(this.startValue, this.endValue, this.interpolator.getInterpolation(lottieFrameInfo.getOverallProgress()));
    }

    public abstract Object interpolateValue(Object obj, Object obj2, float f);
}
