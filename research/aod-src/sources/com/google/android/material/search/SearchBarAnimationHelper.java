package com.google.android.material.search;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import com.google.android.material.animation.AnimatableView;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
class SearchBarAnimationHelper {
    public boolean collapsing;
    public Animator defaultCenterViewAnimator;
    public boolean expanding;
    public Animator secondaryViewAnimator;
    public final Set onLoadAnimationCallbacks = new LinkedHashSet();
    public final Set expandAnimationListeners = new LinkedHashSet();
    public final Set collapseAnimationListeners = new LinkedHashSet();
    public boolean onLoadAnimationFadeInEnabled = true;
    public Animator runningExpandOrCollapseAnimator = null;

    /* JADX INFO: renamed from: com.google.android.material.search.SearchBarAnimationHelper$1, reason: invalid class name */
    class AnonymousClass1 extends AnimatorListenerAdapter {
        public final /* synthetic */ SearchBarAnimationHelper this$0;

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            this.this$0.dispatchOnLoadAnimation(new OnLoadAnimationInvocation() { // from class: com.google.android.material.search.SearchBarAnimationHelper$1$$ExternalSyntheticLambda0
                @Override // com.google.android.material.search.SearchBarAnimationHelper.OnLoadAnimationInvocation
                public final void invoke(SearchBar.OnLoadAnimationCallback onLoadAnimationCallback) {
                    onLoadAnimationCallback.onAnimationEnd();
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.google.android.material.search.SearchBarAnimationHelper$2, reason: invalid class name */
    class AnonymousClass2 extends AnimatorListenerAdapter {
        public final /* synthetic */ View val$centerView;
        public final /* synthetic */ Animator val$secondaryViewAnimator;

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            this.val$centerView.setVisibility(8);
            this.val$secondaryViewAnimator.start();
        }
    }

    /* JADX INFO: renamed from: com.google.android.material.search.SearchBarAnimationHelper$3, reason: invalid class name */
    class AnonymousClass3 extends AnimatorListenerAdapter {
        public final /* synthetic */ SearchBarAnimationHelper this$0;

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            this.this$0.runningExpandOrCollapseAnimator = null;
        }
    }

    /* JADX INFO: renamed from: com.google.android.material.search.SearchBarAnimationHelper$4, reason: invalid class name */
    class AnonymousClass4 extends AnimatorListenerAdapter {
        public final /* synthetic */ SearchBarAnimationHelper this$0;
        public final /* synthetic */ SearchBar val$searchBar;

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            this.this$0.expanding = false;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            this.val$searchBar.setVisibility(4);
        }
    }

    /* JADX INFO: renamed from: com.google.android.material.search.SearchBarAnimationHelper$5, reason: invalid class name */
    class AnonymousClass5 extends AnimatorListenerAdapter {
        public final /* synthetic */ SearchBarAnimationHelper this$0;

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            this.this$0.runningExpandOrCollapseAnimator = null;
        }
    }

    /* JADX INFO: renamed from: com.google.android.material.search.SearchBarAnimationHelper$6, reason: invalid class name */
    class AnonymousClass6 extends AnimatorListenerAdapter {
        public final /* synthetic */ SearchBarAnimationHelper this$0;
        public final /* synthetic */ SearchBar val$searchBar;

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            this.val$searchBar.setVisibility(0);
            this.this$0.collapsing = false;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            this.val$searchBar.stopOnLoadAnimation();
        }
    }

    public interface OnLoadAnimationInvocation {
        void invoke(SearchBar.OnLoadAnimationCallback onLoadAnimationCallback);
    }

    public final void dispatchOnLoadAnimation(OnLoadAnimationInvocation onLoadAnimationInvocation) {
        Iterator it = this.onLoadAnimationCallbacks.iterator();
        while (it.hasNext()) {
            onLoadAnimationInvocation.invoke((SearchBar.OnLoadAnimationCallback) it.next());
        }
    }

    public void setOnLoadAnimationFadeInEnabled(boolean z) {
        this.onLoadAnimationFadeInEnabled = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void stopOnLoadAnimation(SearchBar searchBar) {
        Animator animator = this.secondaryViewAnimator;
        if (animator != null) {
            animator.end();
        }
        Animator animator2 = this.defaultCenterViewAnimator;
        if (animator2 != null) {
            animator2.end();
        }
        View centerView = searchBar.getCenterView();
        if (centerView instanceof AnimatableView) {
            ((AnimatableView) centerView).stopAnimation();
        }
        if (centerView != 0) {
            centerView.setAlpha(0.0f);
        }
    }
}
