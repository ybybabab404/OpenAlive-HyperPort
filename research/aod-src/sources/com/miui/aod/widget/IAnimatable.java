package com.miui.aod.widget;

import android.animation.AnimatorListenerAdapter;

/* JADX INFO: loaded from: classes.dex */
public interface IAnimatable {
    void clearAnimationView();

    default void startAnimation() {
        startAnimation(null);
    }

    void startAnimation(AnimatorListenerAdapter animatorListenerAdapter);
}
