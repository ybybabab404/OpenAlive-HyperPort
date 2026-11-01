package com.google.android.material.internal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public class ExpandCollapseAnimationHelper {
    public final View expandedView;

    /* JADX INFO: renamed from: com.google.android.material.internal.ExpandCollapseAnimationHelper$1, reason: invalid class name */
    class AnonymousClass1 extends AnimatorListenerAdapter {
        public final /* synthetic */ ExpandCollapseAnimationHelper this$0;

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            this.this$0.expandedView.setVisibility(0);
        }
    }

    /* JADX INFO: renamed from: com.google.android.material.internal.ExpandCollapseAnimationHelper$2, reason: invalid class name */
    class AnonymousClass2 extends AnimatorListenerAdapter {
        public final /* synthetic */ ExpandCollapseAnimationHelper this$0;

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            this.this$0.expandedView.setVisibility(8);
        }
    }
}
