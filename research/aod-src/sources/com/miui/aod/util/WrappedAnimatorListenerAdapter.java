package com.miui.aod.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public abstract class WrappedAnimatorListenerAdapter extends AnimatorListenerAdapter {
    public static final String TAG = "WrappedAnimatorListenerAdapter";

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        super.onAnimationCancel(animator);
        Log.d(TAG, "onAnimationCancel: =======");
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        super.onAnimationEnd(animator);
        Log.d(TAG, "onAnimationEnd: ======");
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
    public void onAnimationPause(Animator animator) {
        super.onAnimationPause(animator);
        Log.d(TAG, "onAnimationPause: =====");
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(Animator animator) {
        super.onAnimationRepeat(animator);
        Log.d(TAG, "onAnimationRepeat: =====");
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
    public void onAnimationResume(Animator animator) {
        super.onAnimationResume(animator);
        Log.d(TAG, "onAnimationResume: =====");
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        super.onAnimationStart(animator);
        Log.d(TAG, "onAnimationStart: =====");
    }
}
