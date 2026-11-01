package com.google.android.material.internal;

import android.animation.ValueAnimator;
import android.view.View;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public class MultiViewUpdateListener implements ValueAnimator.AnimatorUpdateListener {
    public final Listener listener;
    public final View[] views;

    public interface Listener {
        void onAnimationUpdate(ValueAnimator valueAnimator, View view);
    }

    public MultiViewUpdateListener(Listener listener, View... viewArr) {
        this.listener = listener;
        this.views = viewArr;
    }

    public static MultiViewUpdateListener alphaListener(View... viewArr) {
        return new MultiViewUpdateListener(new Listener() { // from class: com.google.android.material.internal.MultiViewUpdateListener$$ExternalSyntheticLambda3
            @Override // com.google.android.material.internal.MultiViewUpdateListener.Listener
            public final void onAnimationUpdate(ValueAnimator valueAnimator, View view) {
                MultiViewUpdateListener.setAlpha(valueAnimator, view);
            }
        }, viewArr);
    }

    public static MultiViewUpdateListener scaleListener(View... viewArr) {
        return new MultiViewUpdateListener(new Listener() { // from class: com.google.android.material.internal.MultiViewUpdateListener$$ExternalSyntheticLambda2
            @Override // com.google.android.material.internal.MultiViewUpdateListener.Listener
            public final void onAnimationUpdate(ValueAnimator valueAnimator, View view) {
                MultiViewUpdateListener.setScale(valueAnimator, view);
            }
        }, viewArr);
    }

    public static void setAlpha(ValueAnimator valueAnimator, View view) {
        view.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    public static void setScale(ValueAnimator valueAnimator, View view) {
        Float f = (Float) valueAnimator.getAnimatedValue();
        view.setScaleX(f.floatValue());
        view.setScaleY(f.floatValue());
    }

    public static void setTranslationX(ValueAnimator valueAnimator, View view) {
        view.setTranslationX(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    public static void setTranslationY(ValueAnimator valueAnimator, View view) {
        view.setTranslationY(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    public static MultiViewUpdateListener translationXListener(View... viewArr) {
        return new MultiViewUpdateListener(new Listener() { // from class: com.google.android.material.internal.MultiViewUpdateListener$$ExternalSyntheticLambda0
            @Override // com.google.android.material.internal.MultiViewUpdateListener.Listener
            public final void onAnimationUpdate(ValueAnimator valueAnimator, View view) {
                MultiViewUpdateListener.setTranslationX(valueAnimator, view);
            }
        }, viewArr);
    }

    public static MultiViewUpdateListener translationYListener(View... viewArr) {
        return new MultiViewUpdateListener(new Listener() { // from class: com.google.android.material.internal.MultiViewUpdateListener$$ExternalSyntheticLambda1
            @Override // com.google.android.material.internal.MultiViewUpdateListener.Listener
            public final void onAnimationUpdate(ValueAnimator valueAnimator, View view) {
                MultiViewUpdateListener.setTranslationY(valueAnimator, view);
            }
        }, viewArr);
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        for (View view : this.views) {
            this.listener.onAnimationUpdate(valueAnimator, view);
        }
    }
}
