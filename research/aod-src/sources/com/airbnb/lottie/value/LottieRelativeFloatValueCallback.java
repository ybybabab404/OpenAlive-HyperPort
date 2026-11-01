package com.airbnb.lottie.value;

import com.airbnb.lottie.utils.MiscUtils;

/* JADX INFO: loaded from: classes.dex */
public class LottieRelativeFloatValueCallback extends LottieValueCallback<Float> {
    public Float getOffset(LottieFrameInfo lottieFrameInfo) {
        Object obj = this.value;
        if (obj != null) {
            return (Float) obj;
        }
        throw new IllegalArgumentException("You must provide a static value in the constructor , call setValue, or override getValue.");
    }

    @Override // com.airbnb.lottie.value.LottieValueCallback
    public Float getValue(LottieFrameInfo lottieFrameInfo) {
        return Float.valueOf(MiscUtils.lerp(((Float) lottieFrameInfo.getStartValue()).floatValue(), ((Float) lottieFrameInfo.getEndValue()).floatValue(), lottieFrameInfo.getInterpolatedKeyframeProgress()) + getOffset(lottieFrameInfo).floatValue());
    }
}
