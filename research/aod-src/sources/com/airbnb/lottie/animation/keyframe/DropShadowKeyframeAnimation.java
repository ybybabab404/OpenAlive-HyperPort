package com.airbnb.lottie.animation.keyframe;

import android.graphics.Color;
import android.graphics.Paint;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.parser.DropShadowEffect;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;

/* JADX INFO: loaded from: classes.dex */
public class DropShadowKeyframeAnimation implements BaseKeyframeAnimation.AnimationListener {
    public final BaseKeyframeAnimation color;
    public final BaseKeyframeAnimation direction;
    public final BaseKeyframeAnimation distance;
    public boolean isDirty = true;
    public final BaseKeyframeAnimation.AnimationListener listener;
    public final BaseKeyframeAnimation opacity;
    public final BaseKeyframeAnimation radius;

    public DropShadowKeyframeAnimation(BaseKeyframeAnimation.AnimationListener animationListener, BaseLayer baseLayer, DropShadowEffect dropShadowEffect) {
        this.listener = animationListener;
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation = dropShadowEffect.getColor().createAnimation();
        this.color = baseKeyframeAnimationCreateAnimation;
        baseKeyframeAnimationCreateAnimation.addUpdateListener(this);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation);
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation2 = dropShadowEffect.getOpacity().createAnimation();
        this.opacity = baseKeyframeAnimationCreateAnimation2;
        baseKeyframeAnimationCreateAnimation2.addUpdateListener(this);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation2);
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation3 = dropShadowEffect.getDirection().createAnimation();
        this.direction = baseKeyframeAnimationCreateAnimation3;
        baseKeyframeAnimationCreateAnimation3.addUpdateListener(this);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation3);
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation4 = dropShadowEffect.getDistance().createAnimation();
        this.distance = baseKeyframeAnimationCreateAnimation4;
        baseKeyframeAnimationCreateAnimation4.addUpdateListener(this);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation4);
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation5 = dropShadowEffect.getRadius().createAnimation();
        this.radius = baseKeyframeAnimationCreateAnimation5;
        baseKeyframeAnimationCreateAnimation5.addUpdateListener(this);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation5);
    }

    public void applyTo(Paint paint) {
        if (this.isDirty) {
            this.isDirty = false;
            double dFloatValue = ((double) ((Float) this.direction.getValue()).floatValue()) * 0.017453292519943295d;
            float fFloatValue = ((Float) this.distance.getValue()).floatValue();
            float fSin = ((float) Math.sin(dFloatValue)) * fFloatValue;
            float fCos = ((float) Math.cos(dFloatValue + 3.141592653589793d)) * fFloatValue;
            int iIntValue = ((Integer) this.color.getValue()).intValue();
            paint.setShadowLayer(((Float) this.radius.getValue()).floatValue(), fSin, fCos, Color.argb(Math.round(((Float) this.opacity.getValue()).floatValue()), Color.red(iIntValue), Color.green(iIntValue), Color.blue(iIntValue)));
        }
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public void onValueChanged() {
        this.isDirty = true;
        this.listener.onValueChanged();
    }

    public void setColorCallback(LottieValueCallback lottieValueCallback) {
        this.color.setValueCallback(lottieValueCallback);
    }

    public void setDirectionCallback(LottieValueCallback lottieValueCallback) {
        this.direction.setValueCallback(lottieValueCallback);
    }

    public void setDistanceCallback(LottieValueCallback lottieValueCallback) {
        this.distance.setValueCallback(lottieValueCallback);
    }

    public void setOpacityCallback(final LottieValueCallback lottieValueCallback) {
        if (lottieValueCallback == null) {
            this.opacity.setValueCallback(null);
        } else {
            this.opacity.setValueCallback(new LottieValueCallback<Float>() { // from class: com.airbnb.lottie.animation.keyframe.DropShadowKeyframeAnimation.1
                @Override // com.airbnb.lottie.value.LottieValueCallback
                public Float getValue(LottieFrameInfo lottieFrameInfo) {
                    Float f = (Float) lottieValueCallback.getValue(lottieFrameInfo);
                    if (f == null) {
                        return null;
                    }
                    return Float.valueOf(f.floatValue() * 2.55f);
                }
            });
        }
    }

    public void setRadiusCallback(LottieValueCallback lottieValueCallback) {
        this.radius.setValueCallback(lottieValueCallback);
    }
}
