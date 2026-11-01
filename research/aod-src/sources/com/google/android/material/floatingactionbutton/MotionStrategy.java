package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import com.google.android.material.animation.MotionSpec;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
interface MotionStrategy {
    AnimatorSet createAnimator();

    int getDefaultMotionSpecResource();

    List getListeners();

    MotionSpec getMotionSpec();

    void onAnimationCancel();

    void onAnimationEnd();

    void onAnimationStart(Animator animator);

    void onChange(ExtendedFloatingActionButton.OnChangedCallback onChangedCallback);

    void performNow();

    void setMotionSpec(MotionSpec motionSpec);

    boolean shouldCancel();
}
