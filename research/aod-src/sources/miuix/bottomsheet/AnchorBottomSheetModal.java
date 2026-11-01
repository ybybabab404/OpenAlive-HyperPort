package miuix.bottomsheet;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import androidx.activity.OnBackPressedCallback;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.miui.maml.folme.AnimatedProperty;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MiuiBlurUtils;
import miuix.core.util.MiuixUIUtils;
import miuix.internal.util.ViewUtils;
import miuix.theme.token.DimToken;
import miuix.view.DynamicThemeWidget;

/* JADX INFO: loaded from: classes2.dex */
public class AnchorBottomSheetModal implements DynamicThemeWidget {
    public final SparseIntArray mAccessibility;
    public AnchorBottomSheetBehavior mBehavior;
    public AnchorBottomSheetView mBottomSheet;
    public final AnchorBottomSheetBehavior.BottomSheetCallback mBottomSheetCallback;
    public boolean mCanceledOnTouchOutside;
    public FrameLayout mContainer;
    public final Context mContext;
    public CoordinatorLayout mCoordinator;
    public boolean mExecuteDismissed;
    public boolean mIsDismissing;
    public boolean mIsShowing;
    public View mModalBackground;
    public boolean mModalBackgroundEnabled;
    public OnBackListener mOnBackListener;
    public OnBackPressedCallback mOnBackPressedCallback;
    public OnDismissListener mOnDismissListener;
    public OnDismissStartListener mOnDismissStartListener;
    public OnModeChangeListener mOnModeChangeListener;
    public OnShowListener mOnShowListener;
    public OnShowStartListener mOnShowStartListener;
    public final ViewGroup mRootView;
    public boolean mShouldRequestLayout;
    public boolean mShowAnimPending;
    public Runnable mShowImmediatelyRunnable;
    public Runnable mShowRunnable;
    public int mUserThemeType;

    /* JADX INFO: renamed from: miuix.bottomsheet.AnchorBottomSheetModal$1, reason: invalid class name */
    class AnonymousClass1 extends OnBackPressedCallback {
        public final /* synthetic */ AnchorBottomSheetModal this$0;

        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            if ((this.this$0.mOnBackListener == null || !this.this$0.mOnBackListener.onBack()) && this.this$0.isShowing()) {
                this.this$0.dismiss();
            }
        }
    }

    /* JADX INFO: renamed from: miuix.bottomsheet.AnchorBottomSheetModal$2, reason: invalid class name */
    class AnonymousClass2 extends AccessibilityDelegateCompat {
        public final /* synthetic */ AnchorBottomSheetModal this$0;

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            if (!this.this$0.mCanceledOnTouchOutside) {
                accessibilityNodeInfoCompat.setDismissable(false);
            } else {
                accessibilityNodeInfoCompat.addAction(1048576);
                accessibilityNodeInfoCompat.setDismissable(true);
            }
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (i != 1048576 || !this.this$0.mCanceledOnTouchOutside) {
                return super.performAccessibilityAction(view, i, bundle);
            }
            this.this$0.dismiss();
            return true;
        }
    }

    /* JADX INFO: renamed from: miuix.bottomsheet.AnchorBottomSheetModal$4, reason: invalid class name */
    public class AnonymousClass4 extends AnchorBottomSheetBehavior.BottomSheetCallback {
        public final /* synthetic */ AnchorBottomSheetModal this$0;

        @Override // miuix.bottomsheet.AnchorBottomSheetBehavior.BottomSheetCallback
        public void onSlide(View view, float f) {
        }

        @Override // miuix.bottomsheet.AnchorBottomSheetBehavior.BottomSheetCallback
        public void onStateChanged(View view, int i) {
            if (i != 5 || this.this$0.mExecuteDismissed) {
                return;
            }
            this.this$0.dismissImmediately();
        }
    }

    /* JADX INFO: renamed from: miuix.bottomsheet.AnchorBottomSheetModal$5, reason: invalid class name */
    class AnonymousClass5 implements Runnable {
        public final /* synthetic */ AnchorBottomSheetModal this$0;

        /* JADX INFO: renamed from: miuix.bottomsheet.AnchorBottomSheetModal$5$1, reason: invalid class name */
        public class AnonymousClass1 implements AnchorBottomSheetBehavior.AnimationListener {
            public static /* synthetic */ void $r8$lambda$QFcVWv5yr3cWGhlgzDax8R4IABM(AnonymousClass1 anonymousClass1) {
                if (AnonymousClass5.this.this$0.mOnShowListener == null || !AnonymousClass5.this.this$0.mIsShowing) {
                    return;
                }
                AnonymousClass5.this.this$0.mOnShowListener.onShow();
            }

            public AnonymousClass1() {
            }

            @Override // miuix.bottomsheet.AnchorBottomSheetBehavior.AnimationListener
            public void onAnimationEnd() {
                AnonymousClass5.this.this$0.mCoordinator.setVisibility(0);
                if (AnonymousClass5.this.this$0.mOnShowListener != null && AnonymousClass5.this.this$0.mIsShowing) {
                    AnonymousClass5.this.this$0.mBottomSheet.post(new Runnable() { // from class: miuix.bottomsheet.AnchorBottomSheetModal$5$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AnchorBottomSheetModal.AnonymousClass5.AnonymousClass1.$r8$lambda$QFcVWv5yr3cWGhlgzDax8R4IABM(this.f$0);
                        }
                    });
                }
                AnonymousClass5.this.this$0.mBottomSheet.removeCallbacks(AnonymousClass5.this.this$0.mShowImmediatelyRunnable);
            }

            @Override // miuix.bottomsheet.AnchorBottomSheetBehavior.AnimationListener
            public void onAnimationStart() {
                if (AnonymousClass5.this.this$0.mModalBackgroundEnabled) {
                    AnonymousClass5.this.this$0.mModalBackground.setAlpha(ViewUtils.isNightMode(AnonymousClass5.this.this$0.mCoordinator.getContext()) ? DimToken.DIM_DARK : DimToken.DIM_LIGHT);
                }
                if (AnonymousClass5.this.this$0.mOnShowStartListener != null) {
                    AnonymousClass5.this.this$0.mOnShowStartListener.onShowStart();
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.this$0.mBehavior.getExpandedOffset() == 0) {
                this.this$0.mBottomSheet.post(this);
            } else {
                this.this$0.mShowAnimPending = false;
                this.this$0.mBehavior.startEnterAnimation(new AnonymousClass1(), true);
            }
        }
    }

    /* JADX INFO: renamed from: miuix.bottomsheet.AnchorBottomSheetModal$6, reason: invalid class name */
    class AnonymousClass6 implements AnchorBottomSheetBehavior.AnimationListener {
        public final /* synthetic */ AnchorBottomSheetModal this$0;

        public static /* synthetic */ void $r8$lambda$juymhLKCtU7NIrAeKmVdNazjzA8(AnonymousClass6 anonymousClass6) {
            if (anonymousClass6.this$0.mOnShowListener == null || !anonymousClass6.this$0.mIsShowing) {
                return;
            }
            anonymousClass6.this$0.mOnShowListener.onShow();
        }

        @Override // miuix.bottomsheet.AnchorBottomSheetBehavior.AnimationListener
        public void onAnimationEnd() {
            if (this.this$0.mOnShowListener == null || !this.this$0.mIsShowing) {
                return;
            }
            this.this$0.mBottomSheet.post(new Runnable() { // from class: miuix.bottomsheet.AnchorBottomSheetModal$6$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AnchorBottomSheetModal.AnonymousClass6.$r8$lambda$juymhLKCtU7NIrAeKmVdNazjzA8(this.f$0);
                }
            });
        }

        @Override // miuix.bottomsheet.AnchorBottomSheetBehavior.AnimationListener
        public void onAnimationStart() {
            this.this$0.mExecuteDismissed = false;
            this.this$0.mIsDismissing = false;
            if (this.this$0.mOnBackPressedCallback != null) {
                this.this$0.mOnBackPressedCallback.setEnabled(true);
            }
            if (this.this$0.mOnShowStartListener != null) {
                this.this$0.mOnShowStartListener.onShowStart();
            }
        }
    }

    /* JADX INFO: renamed from: miuix.bottomsheet.AnchorBottomSheetModal$7, reason: invalid class name */
    public class AnonymousClass7 implements AnchorBottomSheetBehavior.AnimationListener {
        public AnonymousClass7() {
        }

        @Override // miuix.bottomsheet.AnchorBottomSheetBehavior.AnimationListener
        public void onAnimationEnd() {
            if (AnchorBottomSheetModal.this.mShowImmediatelyRunnable != null) {
                AnchorBottomSheetModal.this.mBottomSheet.removeCallbacks(AnchorBottomSheetModal.this.mShowImmediatelyRunnable);
            }
            AnchorBottomSheetModal.this.mRootView.post(new Runnable() { // from class: miuix.bottomsheet.AnchorBottomSheetModal$7$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AnchorBottomSheetModal.this.doRemove();
                }
            });
        }

        @Override // miuix.bottomsheet.AnchorBottomSheetBehavior.AnimationListener
        public void onAnimationStart() {
            AnchorBottomSheetModal.this.mExecuteDismissed = true;
            if (AnchorBottomSheetModal.this.mOnDismissStartListener != null) {
                AnchorBottomSheetModal.this.mOnDismissStartListener.onDismissStart();
            }
        }
    }

    /* JADX INFO: renamed from: miuix.bottomsheet.AnchorBottomSheetModal$8, reason: invalid class name */
    public class AnonymousClass8 implements AnchorBottomSheetBehavior.AnimationListener {
        public AnonymousClass8() {
        }

        @Override // miuix.bottomsheet.AnchorBottomSheetBehavior.AnimationListener
        public void onAnimationEnd() {
            if (AnchorBottomSheetModal.this.mShowImmediatelyRunnable != null) {
                AnchorBottomSheetModal.this.mBottomSheet.removeCallbacks(AnchorBottomSheetModal.this.mShowImmediatelyRunnable);
            }
            AnchorBottomSheetModal.this.mRootView.post(new Runnable() { // from class: miuix.bottomsheet.AnchorBottomSheetModal$8$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AnchorBottomSheetModal.this.doRemove();
                }
            });
        }

        @Override // miuix.bottomsheet.AnchorBottomSheetBehavior.AnimationListener
        public void onAnimationStart() {
            AnchorBottomSheetModal.this.mExecuteDismissed = true;
        }
    }

    public static class DimAnimator {
        public static void dismiss(View view) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, AnimatedProperty.PROPERTY_NAME_ALPHA, ViewUtils.isNightMode(view.getContext()) ? DimToken.DIM_DARK : DimToken.DIM_LIGHT, 0.0f);
            objectAnimatorOfFloat.setInterpolator(new DecelerateInterpolator(1.5f));
            objectAnimatorOfFloat.setDuration(250L);
            objectAnimatorOfFloat.start();
        }
    }

    public interface OnBackListener {
        boolean onBack();
    }

    public interface OnDismissListener {
        void onDismiss();
    }

    public interface OnDismissStartListener {
        void onDismissStart();
    }

    public interface OnModeChangeListener {
        void onModeChanged(int i, View view);
    }

    public interface OnShowListener {
        void onShow();
    }

    public interface OnShowStartListener {
        void onShowStart();
    }

    public interface OnTouchOutsideListener {
    }

    public static /* synthetic */ void $r8$lambda$AjYr4o_ptlhO5FBLdYAVJJ8ZIL4(AnchorBottomSheetModal anchorBottomSheetModal) {
        OnDismissListener onDismissListener = anchorBottomSheetModal.mOnDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$dTUGTybvjUAwrivrLmGJ-zXH5Xw, reason: not valid java name */
    public static /* synthetic */ void m1330$r8$lambda$dTUGTybvjUAwrivrLmGJzXH5Xw(AnchorBottomSheetModal anchorBottomSheetModal, int i, View view) {
        anchorBottomSheetModal.updateModalBackgroundForMode(i);
        OnModeChangeListener onModeChangeListener = anchorBottomSheetModal.mOnModeChangeListener;
        if (onModeChangeListener != null) {
            onModeChangeListener.onModeChanged(i, view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isShowing() {
        return isAddedToRootView();
    }

    public void dismiss() {
        dismiss(true);
    }

    public void dismiss(boolean z) {
        if (!this.mIsShowing || this.mIsDismissing) {
            return;
        }
        AnchorBottomSheetView anchorBottomSheetView = this.mBottomSheet;
        if (anchorBottomSheetView != null) {
            anchorBottomSheetView.removeCallbacks(this.mShowRunnable);
            this.mBottomSheet.removeCallbacks(this.mShowImmediatelyRunnable);
        }
        if (isAddedToRootView()) {
            this.mIsDismissing = true;
            FrameLayout frameLayout = this.mContainer;
            if (frameLayout != null) {
                hideSoftIME(frameLayout);
                if (!z) {
                    OnDismissStartListener onDismissStartListener = this.mOnDismissStartListener;
                    if (onDismissStartListener != null) {
                        onDismissStartListener.onDismissStart();
                    }
                    doRemove();
                    return;
                }
                if (!this.mBehavior.startExitAnimation(new AnonymousClass7())) {
                    this.mIsDismissing = false;
                } else if (this.mModalBackgroundEnabled && this.mModalBackground.getVisibility() == 0) {
                    DimAnimator.dismiss(this.mModalBackground);
                }
            }
        }
    }

    public final void dismissImmediately() {
        if (!this.mIsShowing || this.mIsDismissing) {
            return;
        }
        this.mIsDismissing = true;
        if (this.mContainer == null || this.mBehavior.startExitAnimation(new AnonymousClass8(), true)) {
            return;
        }
        this.mIsDismissing = false;
    }

    public final void doRemove() {
        this.mRootView.removeView(this.mContainer);
        this.mShouldRequestLayout = true;
        this.mIsShowing = false;
        this.mIsDismissing = false;
        this.mShowAnimPending = false;
        if (this.mOnDismissListener != null) {
            this.mRootView.post(new Runnable() { // from class: miuix.bottomsheet.AnchorBottomSheetModal$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    AnchorBottomSheetModal.$r8$lambda$AjYr4o_ptlhO5FBLdYAVJJ8ZIL4(this.f$0);
                }
            });
        }
        OnBackPressedCallback onBackPressedCallback = this.mOnBackPressedCallback;
        if (onBackPressedCallback != null) {
            onBackPressedCallback.setEnabled(false);
        }
        recoverAccessibility();
    }

    public final FrameLayout ensureContainerAndBehavior() {
        MaterialConfig.BlurConfig blurConfig;
        if (this.mContainer == null) {
            FrameLayout frameLayout = (FrameLayout) View.inflate(this.mContext, R.layout.miuix_anchor_bottom_sheet_modal_view, null);
            this.mContainer = frameLayout;
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) frameLayout.findViewById(R.id.coordinator);
            this.mCoordinator = coordinatorLayout;
            coordinatorLayout.setVisibility(4);
            this.mBottomSheet = (AnchorBottomSheetView) this.mContainer.findViewById(R.id.bottom_sheet_view);
            if (hasThemeType()) {
                this.mBottomSheet.setThemeType(this.mUserThemeType);
            }
            if (this.mBottomSheet.isApplyBlur() && this.mBottomSheet.getCurrentMaterial() != null && (blurConfig = this.mBottomSheet.getCurrentMaterial().getBlurConfig()) != null) {
                MiuiBlurUtils.setBackgroundBlurMode(this.mContainer, 2);
                MiuiBlurUtils.setBackgroundBlurRadius(this.mContainer, MiuixUIUtils.dp2px(this.mRootView.getContext(), blurConfig.blurRadius));
            }
            View viewFindViewById = this.mContainer.findViewById(R.id.modal_background);
            this.mModalBackground = viewFindViewById;
            if (this.mModalBackgroundEnabled) {
                viewFindViewById.setAlpha(0.0f);
                this.mModalBackground.setVisibility(0);
            } else {
                viewFindViewById.setVisibility(8);
            }
            AnchorBottomSheetBehavior anchorBottomSheetBehaviorFrom = AnchorBottomSheetBehavior.from(this.mBottomSheet);
            this.mBehavior = anchorBottomSheetBehaviorFrom;
            anchorBottomSheetBehaviorFrom.setOriginalWindowInsetsEnabled(true);
            this.mBehavior.setAnimationInterruptible(true);
            this.mBehavior.addBottomSheetCallback(this.mBottomSheetCallback);
            this.mBehavior.setHideable(this.mCanceledOnTouchOutside);
            this.mBehavior.setReleaseAnimListener(new AnchorBottomSheetBehavior.ReleaseAnimListener() { // from class: miuix.bottomsheet.AnchorBottomSheetModal.3
                @Override // miuix.bottomsheet.AnchorBottomSheetBehavior.ReleaseAnimListener
                public void onEnd(int i) {
                    if (i == 5) {
                        AnchorBottomSheetModal.this.dismissImmediately();
                    }
                }

                @Override // miuix.bottomsheet.AnchorBottomSheetBehavior.ReleaseAnimListener
                public void onStart(int i) {
                    if (i == 5) {
                        AnchorBottomSheetModal.this.mExecuteDismissed = true;
                        if (AnchorBottomSheetModal.this.mOnDismissStartListener != null) {
                            AnchorBottomSheetModal.this.mOnDismissStartListener.onDismissStart();
                        }
                        if (AnchorBottomSheetModal.this.mModalBackgroundEnabled && AnchorBottomSheetModal.this.mModalBackground.getVisibility() == 0) {
                            DimAnimator.dismiss(AnchorBottomSheetModal.this.mModalBackground);
                        }
                    }
                }
            });
            this.mBehavior.setOnModeChangeListener(new AnchorBottomSheetBehavior.OnModeChangeListener() { // from class: miuix.bottomsheet.AnchorBottomSheetModal$$ExternalSyntheticLambda0
                @Override // miuix.bottomsheet.AnchorBottomSheetBehavior.OnModeChangeListener
                public final void onModeChange(int i, View view) {
                    AnchorBottomSheetModal.m1330$r8$lambda$dTUGTybvjUAwrivrLmGJzXH5Xw(this.f$0, i, view);
                }
            });
        }
        return this.mContainer;
    }

    public boolean hasThemeType() {
        return this.mUserThemeType > 0;
    }

    public final void hideSoftIME(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) ContextCompat.getSystemService(this.mContext, InputMethodManager.class);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public final boolean isAddedToRootView() {
        ensureContainerAndBehavior();
        return this.mContainer.getParent() == this.mRootView;
    }

    public final void recoverAccessibility() {
        ViewGroup viewGroup = this.mRootView;
        if (viewGroup == null) {
            return;
        }
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mRootView.getChildAt(i);
            int i2 = this.mAccessibility.get(childAt.hashCode(), -1);
            if (i2 >= 0) {
                childAt.setImportantForAccessibility(i2);
            }
        }
        this.mAccessibility.clear();
    }

    public final void updateModalBackgroundForMode(int i) {
        View view = this.mModalBackground;
        if (view == null) {
            return;
        }
        if (i == 1) {
            view.setVisibility(8);
            return;
        }
        if (!this.mModalBackgroundEnabled) {
            view.setVisibility(8);
            return;
        }
        if (this.mShowAnimPending) {
            view.setAlpha(0.0f);
        } else {
            this.mModalBackground.setAlpha(ViewUtils.isNightMode(view.getContext()) ? DimToken.DIM_DARK : DimToken.DIM_LIGHT);
        }
        this.mModalBackground.setVisibility(0);
    }
}
