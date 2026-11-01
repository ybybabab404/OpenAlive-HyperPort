package miuix.bottomsheet;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import androidx.activity.ComponentActivity;
import androidx.activity.OnBackPressedCallback;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.miui.maml.folme.AnimatedProperty;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MiuiBlurUtils;
import miuix.core.util.MiuixUIUtils;
import miuix.internal.util.ViewUtils;
import miuix.theme.token.DimToken;
import miuix.view.DynamicThemeWidget;

/* JADX INFO: loaded from: classes2.dex */
public class BottomSheetModal implements DynamicThemeWidget {
    public BottomSheetBehavior mBehavior;
    public BottomSheetView mBottomSheet;
    public FrameLayout mContainer;
    public final Context mContext;
    public CoordinatorLayout mCoordinator;
    public View mModalBackground;
    public boolean mModalBackgroundEnabled;
    public OnBackListener mOnBackListener;
    public OnBackPressedCallback mOnBackPressedCallback;
    public OnDismissListener mOnDismissListener;
    public OnDismissStartListener mOnDismissStartListener;
    public OnShowListener mOnShowListener;
    public OnShowStartListener mOnShowStartListener;
    public OnTouchOutsideListener mOnTouchOutsideListener;
    public final ViewGroup mRootView;
    public Runnable mShowImediatelyRunnable;
    public Runnable mShowRunnable;
    public int mUserThemeType;
    public boolean mDragHandleViewEnabled = true;
    public boolean mCanceledOnTouchOutside = true;
    public boolean mShouldRequestLayout = false;
    public boolean mShowAndDismissWithAnimation = true;
    public boolean mExecuteDismissed = true;
    public final SparseIntArray mAccessibility = new SparseIntArray();
    public final BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() { // from class: miuix.bottomsheet.BottomSheetModal.4
        @Override // miuix.bottomsheet.BottomSheetBehavior.BottomSheetCallback
        public void onSlide(View view, float f) {
        }

        @Override // miuix.bottomsheet.BottomSheetBehavior.BottomSheetCallback
        public void onStateChanged(View view, int i) {
            if (i != 5 || BottomSheetModal.this.mExecuteDismissed) {
                return;
            }
            BottomSheetModal.this.dismissImmediately();
        }
    };

    /* JADX INFO: renamed from: miuix.bottomsheet.BottomSheetModal$7, reason: invalid class name */
    public class AnonymousClass7 implements BottomSheetBehavior.AnimationListener {
        public AnonymousClass7() {
        }

        @Override // miuix.bottomsheet.BottomSheetBehavior.AnimationListener
        public void onAnimationEnd() {
            if (BottomSheetModal.this.mShowImediatelyRunnable != null) {
                BottomSheetModal.this.mBottomSheet.removeCallbacks(BottomSheetModal.this.mShowImediatelyRunnable);
            }
            BottomSheetModal.this.mRootView.post(new Runnable() { // from class: miuix.bottomsheet.BottomSheetModal$7$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BottomSheetModal.this.doRemove();
                }
            });
        }

        @Override // miuix.bottomsheet.BottomSheetBehavior.AnimationListener
        public void onAnimationStart() {
            BottomSheetModal.this.mExecuteDismissed = true;
            if (BottomSheetModal.this.mOnDismissStartListener != null) {
                BottomSheetModal.this.mOnDismissStartListener.onDismissStart();
            }
        }
    }

    /* JADX INFO: renamed from: miuix.bottomsheet.BottomSheetModal$8, reason: invalid class name */
    public class AnonymousClass8 implements BottomSheetBehavior.AnimationListener {
        public AnonymousClass8() {
        }

        @Override // miuix.bottomsheet.BottomSheetBehavior.AnimationListener
        public void onAnimationEnd() {
            if (BottomSheetModal.this.mShowImediatelyRunnable != null) {
                BottomSheetModal.this.mBottomSheet.removeCallbacks(BottomSheetModal.this.mShowImediatelyRunnable);
            }
            BottomSheetModal.this.mRootView.post(new Runnable() { // from class: miuix.bottomsheet.BottomSheetModal$8$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BottomSheetModal.this.doRemove();
                }
            });
        }

        @Override // miuix.bottomsheet.BottomSheetBehavior.AnimationListener
        public void onAnimationStart() {
            BottomSheetModal.this.mExecuteDismissed = true;
        }
    }

    public static class DimAnimator {
        public static void dismiss(View view) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, AnimatedProperty.PROPERTY_NAME_ALPHA, ViewUtils.isNightMode(view.getContext()) ? DimToken.DIM_DARK : DimToken.DIM_LIGHT, 0.0f);
            objectAnimatorOfFloat.setInterpolator(new DecelerateInterpolator(1.5f));
            objectAnimatorOfFloat.setDuration(250L);
            objectAnimatorOfFloat.start();
        }

        public static void show(View view) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, AnimatedProperty.PROPERTY_NAME_ALPHA, 0.0f, ViewUtils.isNightMode(view.getContext()) ? DimToken.DIM_DARK : DimToken.DIM_LIGHT);
            objectAnimatorOfFloat.setInterpolator(new DecelerateInterpolator(1.5f));
            objectAnimatorOfFloat.setDuration(300L);
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

    public interface OnShowListener {
        void onShow();
    }

    public interface OnShowStartListener {
        void onShowStart();
    }

    public interface OnTouchOutsideListener {
        boolean onTouchOutside();
    }

    public static /* synthetic */ boolean $r8$lambda$czKYH7zcnFjhSI51lzSQcaoKgcI(View view, MotionEvent motionEvent) {
        return true;
    }

    public static /* synthetic */ void $r8$lambda$gbtrEGNwgEFRxZtn1wtzLPGLxME(BottomSheetModal bottomSheetModal, View view) {
        OnTouchOutsideListener onTouchOutsideListener = bottomSheetModal.mOnTouchOutsideListener;
        if ((onTouchOutsideListener == null || !onTouchOutsideListener.onTouchOutside()) && bottomSheetModal.mCanceledOnTouchOutside && bottomSheetModal.isShowing() && !bottomSheetModal.mExecuteDismissed) {
            bottomSheetModal.dismiss();
        }
    }

    public BottomSheetModal(Activity activity, boolean z) {
        boolean z2 = false;
        this.mModalBackgroundEnabled = false;
        View decorView = activity.getWindow().getDecorView();
        if (!(decorView instanceof ViewGroup)) {
            throw new IllegalStateException("DecorView from activity is not ViewGroup!");
        }
        this.mModalBackgroundEnabled = z;
        ViewGroup viewGroup = (ViewGroup) decorView;
        this.mRootView = viewGroup;
        this.mContext = viewGroup.getContext();
        if (activity instanceof ComponentActivity) {
            this.mOnBackPressedCallback = new OnBackPressedCallback(z2) { // from class: miuix.bottomsheet.BottomSheetModal.1
                @Override // androidx.activity.OnBackPressedCallback
                public void handleOnBackPressed() {
                    if ((BottomSheetModal.this.mOnBackListener == null || !BottomSheetModal.this.mOnBackListener.onBack()) && BottomSheetModal.this.isShowing()) {
                        BottomSheetModal.this.dismiss();
                    }
                }
            };
            ((ComponentActivity) activity).getOnBackPressedDispatcher().addCallback(this.mOnBackPressedCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissImmediately() {
        if (this.mContainer == null || this.mBehavior.startExitAnimation(new AnonymousClass8(), true)) {
            return;
        }
        Log.w("BottomSheetModal", "startExitAnimation failed in dismissImmediately(), BottomSheetBehavior may not be ready.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRemove() {
        this.mRootView.removeView(this.mContainer);
        this.mShouldRequestLayout = true;
        OnDismissListener onDismissListener = this.mOnDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
        OnBackPressedCallback onBackPressedCallback = this.mOnBackPressedCallback;
        if (onBackPressedCallback != null) {
            onBackPressedCallback.setEnabled(false);
        }
        recoverAccessibility();
    }

    private FrameLayout ensureContainerAndBehavior() {
        MaterialConfig.BlurConfig blurConfig;
        if (this.mContainer == null) {
            FrameLayout frameLayout = (FrameLayout) View.inflate(this.mContext, R.layout.miuix_bottom_sheet_modal_view, null);
            this.mContainer = frameLayout;
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) frameLayout.findViewById(R.id.coordinator);
            this.mCoordinator = coordinatorLayout;
            coordinatorLayout.setVisibility(4);
            this.mBottomSheet = (BottomSheetView) this.mContainer.findViewById(R.id.bottom_sheet_view);
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
                viewFindViewById.setVisibility(0);
            } else {
                viewFindViewById.setVisibility(8);
            }
            BottomSheetBehavior bottomSheetBehaviorFrom = BottomSheetBehavior.from(this.mBottomSheet);
            this.mBehavior = bottomSheetBehaviorFrom;
            bottomSheetBehaviorFrom.setOriginalWindowInsetsEnabled(true);
            this.mBehavior.addBottomSheetCallback(this.mBottomSheetCallback);
            this.mBehavior.setHideable(this.mCanceledOnTouchOutside);
            this.mBehavior.setReleaseAnimListener(new BottomSheetBehavior.ReleaseAnimListener() { // from class: miuix.bottomsheet.BottomSheetModal.3
                @Override // miuix.bottomsheet.BottomSheetBehavior.ReleaseAnimListener
                public void onEnd(int i) {
                    if (i == 5) {
                        BottomSheetModal.this.dismissImmediately();
                    }
                }

                @Override // miuix.bottomsheet.BottomSheetBehavior.ReleaseAnimListener
                public void onStart(int i) {
                    if (i == 5) {
                        BottomSheetModal.this.mExecuteDismissed = true;
                        if (BottomSheetModal.this.mOnDismissStartListener != null) {
                            BottomSheetModal.this.mOnDismissStartListener.onDismissStart();
                        }
                        if (BottomSheetModal.this.mModalBackgroundEnabled) {
                            DimAnimator.dismiss(BottomSheetModal.this.mModalBackground);
                        }
                    }
                }
            });
        }
        return this.mContainer;
    }

    private void hideSoftIME(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) ContextCompat.getSystemService(this.mContext, InputMethodManager.class);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private boolean isAddedToRootView() {
        ensureContainerAndBehavior();
        return this.mContainer.getParent() == this.mRootView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isShowing() {
        return isAddedToRootView();
    }

    private void recoverAccessibility() {
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

    public void applyOverlayBlur(View view, boolean z) {
        MaterialConfig.BlurConfig blurConfig;
        if (!z) {
            MiuiBlurUtils.clearBackgroundBlur(view);
        } else {
            if (this.mBottomSheet.getCurrentMaterial() == null || (blurConfig = this.mBottomSheet.getCurrentMaterial().getBlurConfig()) == null) {
                return;
            }
            MiuiBlurUtils.setBackgroundBlurMode(view, this.mModalBackgroundEnabled ? 1 : 2);
            MiuiBlurUtils.setBackgroundBlurRadius(view, MiuixUIUtils.dp2px(view.getContext(), blurConfig.blurRadius));
        }
    }

    public void dismiss() {
        FrameLayout frameLayout;
        BottomSheetView bottomSheetView = this.mBottomSheet;
        if (bottomSheetView != null) {
            bottomSheetView.removeCallbacks(this.mShowRunnable);
            this.mBottomSheet.removeCallbacks(this.mShowImediatelyRunnable);
        }
        if (isAddedToRootView() && (frameLayout = this.mContainer) != null) {
            hideSoftIME(frameLayout);
            if (!this.mShowAndDismissWithAnimation) {
                OnDismissStartListener onDismissStartListener = this.mOnDismissStartListener;
                if (onDismissStartListener != null) {
                    onDismissStartListener.onDismissStart();
                }
                doRemove();
                return;
            }
            if (!this.mBehavior.startExitAnimation(new AnonymousClass7())) {
                Log.w("BottomSheetModal", "startExitAnimation failed in dismiss(), BottomSheetBehavior may not be ready.");
            } else if (this.mModalBackgroundEnabled) {
                DimAnimator.dismiss(this.mModalBackground);
            }
        }
    }

    public final void doShow() {
        if (this.mBehavior.startEnterAnimation(new BottomSheetBehavior.AnimationListener() { // from class: miuix.bottomsheet.BottomSheetModal.6
            @Override // miuix.bottomsheet.BottomSheetBehavior.AnimationListener
            public void onAnimationEnd() {
                if (BottomSheetModal.this.mOnShowListener == null || !BottomSheetModal.this.isShowing()) {
                    return;
                }
                BottomSheetModal.this.mOnShowListener.onShow();
            }

            @Override // miuix.bottomsheet.BottomSheetBehavior.AnimationListener
            public void onAnimationStart() {
                BottomSheetModal.this.mExecuteDismissed = false;
                if (BottomSheetModal.this.mOnBackPressedCallback != null) {
                    BottomSheetModal.this.mOnBackPressedCallback.setEnabled(true);
                }
                if (BottomSheetModal.this.mOnShowStartListener != null) {
                    BottomSheetModal.this.mOnShowStartListener.onShowStart();
                }
            }
        }) && this.mModalBackgroundEnabled) {
            DimAnimator.show(this.mModalBackground);
        }
    }

    public BottomSheetBehavior getBehavior() {
        if (this.mBehavior == null) {
            ensureContainerAndBehavior();
        }
        return this.mBehavior;
    }

    public boolean hasThemeType() {
        return this.mUserThemeType > 0;
    }

    public void release() {
        BottomSheetView bottomSheetView;
        setOnBackListener(null);
        setOnTouchOutsideListener(null);
        setOnDismissListener(null);
        setOnDismissStartListener(null);
        setOnOnShowListener(null);
        setOnShowStartListener(null);
        OnBackPressedCallback onBackPressedCallback = this.mOnBackPressedCallback;
        if (onBackPressedCallback != null) {
            onBackPressedCallback.remove();
            this.mOnBackPressedCallback = null;
        }
        Runnable runnable = this.mShowImediatelyRunnable;
        if (runnable != null && (bottomSheetView = this.mBottomSheet) != null) {
            bottomSheetView.removeCallbacks(runnable);
            this.mBottomSheet.removeCallbacks(this.mShowRunnable);
        }
        BottomSheetBehavior bottomSheetBehavior = this.mBehavior;
        if (bottomSheetBehavior != null) {
            bottomSheetBehavior.release();
        }
    }

    public final void removeAccessibility() {
        ViewGroup viewGroup = this.mRootView;
        if (viewGroup == null) {
            return;
        }
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mRootView.getChildAt(i);
            this.mAccessibility.append(childAt.hashCode(), childAt.getImportantForAccessibility());
            childAt.setImportantForAccessibility(4);
        }
    }

    public void setAnimationInterruptible(boolean z) {
        getBehavior().setAnimationInterruptible(z);
    }

    public void setContentView(View view) {
        wrapInBottomSheet(0, view, null);
    }

    public void setOnBackListener(OnBackListener onBackListener) {
        this.mOnBackListener = onBackListener;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    public void setOnDismissStartListener(OnDismissStartListener onDismissStartListener) {
        this.mOnDismissStartListener = onDismissStartListener;
    }

    public void setOnOnShowListener(OnShowListener onShowListener) {
        this.mOnShowListener = onShowListener;
    }

    public void setOnShowStartListener(OnShowStartListener onShowStartListener) {
        this.mOnShowStartListener = onShowStartListener;
    }

    public void setOnTouchOutsideListener(OnTouchOutsideListener onTouchOutsideListener) {
        this.mOnTouchOutsideListener = onTouchOutsideListener;
    }

    public void show() {
        if (isAddedToRootView()) {
            if (getBehavior().isAnimationInterruptible()) {
                doShow();
                return;
            }
            return;
        }
        this.mBottomSheet.removeCallbacks(this.mShowRunnable);
        this.mBottomSheet.removeCallbacks(this.mShowImediatelyRunnable);
        removeAccessibility();
        this.mRootView.addView(this.mContainer, -1, -1);
        if (this.mShouldRequestLayout) {
            this.mBottomSheet.requestLayout();
            this.mBottomSheet.requestApplyInsets();
        }
        if (this.mShowAndDismissWithAnimation) {
            if (this.mShowRunnable == null) {
                this.mShowRunnable = new Runnable() { // from class: miuix.bottomsheet.BottomSheetModal$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.doShow();
                    }
                };
            }
            this.mBottomSheet.post(this.mShowRunnable);
            return;
        }
        this.mExecuteDismissed = false;
        OnBackPressedCallback onBackPressedCallback = this.mOnBackPressedCallback;
        if (onBackPressedCallback != null) {
            onBackPressedCallback.setEnabled(true);
        }
        if (this.mShowImediatelyRunnable == null) {
            this.mShowImediatelyRunnable = new Runnable() { // from class: miuix.bottomsheet.BottomSheetModal.5
                @Override // java.lang.Runnable
                public void run() {
                    if (BottomSheetModal.this.mBehavior.getExpandedOffset() == 0) {
                        BottomSheetModal.this.mBottomSheet.post(this);
                    } else {
                        if (BottomSheetModal.this.mBehavior.startEnterAnimation(new BottomSheetBehavior.AnimationListener() { // from class: miuix.bottomsheet.BottomSheetModal.5.1
                            @Override // miuix.bottomsheet.BottomSheetBehavior.AnimationListener
                            public void onAnimationEnd() {
                                BottomSheetModal.this.mCoordinator.setVisibility(0);
                                if (BottomSheetModal.this.mOnShowListener != null && BottomSheetModal.this.isShowing()) {
                                    BottomSheetModal.this.mOnShowListener.onShow();
                                }
                                BottomSheetModal.this.mBottomSheet.removeCallbacks(BottomSheetModal.this.mShowImediatelyRunnable);
                            }

                            @Override // miuix.bottomsheet.BottomSheetBehavior.AnimationListener
                            public void onAnimationStart() {
                                if (BottomSheetModal.this.mModalBackgroundEnabled) {
                                    BottomSheetModal.this.mModalBackground.setAlpha(ViewUtils.isNightMode(BottomSheetModal.this.mCoordinator.getContext()) ? DimToken.DIM_DARK : DimToken.DIM_LIGHT);
                                }
                                if (BottomSheetModal.this.mOnShowStartListener != null) {
                                    BottomSheetModal.this.mOnShowStartListener.onShowStart();
                                }
                            }
                        }, true)) {
                            return;
                        }
                        Log.w("BottomSheetModal", "startEnterAnimation failed in immediate mode, BottomSheetBehavior may not be ready. This can happen when show() is called before layout is complete.");
                    }
                }
            };
        }
        this.mBottomSheet.post(this.mShowImediatelyRunnable);
    }

    public final View wrapInBottomSheet(int i, View view, ViewGroup.LayoutParams layoutParams) {
        ensureContainerAndBehavior();
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) this.mContainer.findViewById(R.id.coordinator);
        ViewCompat.setAccessibilityPaneTitle(this.mContainer, this.mContext.getString(R.string.miuix_popup_window_default_title));
        if (i != 0 && view == null) {
            view = LayoutInflater.from(this.mContext).inflate(i, (ViewGroup) coordinatorLayout, false);
        }
        this.mBottomSheet.setDragHandleViewEnabled(this.mDragHandleViewEnabled);
        this.mBottomSheet.removeAllContentViews();
        if (layoutParams == null) {
            this.mBottomSheet.addContentChildView(view);
        } else {
            this.mBottomSheet.addContentChildView(view, layoutParams);
        }
        coordinatorLayout.findViewById(R.id.touch_outside).setOnClickListener(new View.OnClickListener() { // from class: miuix.bottomsheet.BottomSheetModal$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                BottomSheetModal.$r8$lambda$gbtrEGNwgEFRxZtn1wtzLPGLxME(this.f$0, view2);
            }
        });
        ViewCompat.setAccessibilityDelegate(this.mBottomSheet, new AccessibilityDelegateCompat() { // from class: miuix.bottomsheet.BottomSheetModal.2
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat);
                if (!BottomSheetModal.this.mCanceledOnTouchOutside) {
                    accessibilityNodeInfoCompat.setDismissable(false);
                } else {
                    accessibilityNodeInfoCompat.addAction(1048576);
                    accessibilityNodeInfoCompat.setDismissable(true);
                }
            }

            @Override // androidx.core.view.AccessibilityDelegateCompat
            public boolean performAccessibilityAction(View view2, int i2, Bundle bundle) {
                if (i2 != 1048576 || !BottomSheetModal.this.mCanceledOnTouchOutside) {
                    return super.performAccessibilityAction(view2, i2, bundle);
                }
                BottomSheetModal.this.dismiss();
                return true;
            }
        });
        this.mBottomSheet.setOnTouchListener(new View.OnTouchListener() { // from class: miuix.bottomsheet.BottomSheetModal$$ExternalSyntheticLambda2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return BottomSheetModal.$r8$lambda$czKYH7zcnFjhSI51lzSQcaoKgcI(view2, motionEvent);
            }
        });
        if (this.mBottomSheet.isEnableBlur() && this.mBottomSheet.isApplyBlur()) {
            applyOverlayBlur(this.mContainer, true);
        }
        return this.mContainer;
    }
}
