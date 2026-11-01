package miuix.bottomsheet;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import com.miui.aod.components.view.BaseStyleSelectView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import miuix.container.ExtraPaddingPolicy;
import miuix.core.util.EnvStateManager;
import miuix.core.util.MiShadowUtils;
import miuix.core.util.MiuixUIUtils;
import miuix.core.util.WindowUtils;
import miuix.internal.util.ViewUtils;
import miuix.os.DeviceHelper;
import miuix.popupwidget.widget.PopupAnimationListener;
import miuix.popupwidget.widget.TransformAnimation;
import miuix.theme.token.ContainerToken;
import miuix.theme.token.ShadowToken;
import miuix.view.ITransformableView;
import miuix.view.WindowInsetsController;

/* JADX INFO: loaded from: classes2.dex */
public class AnchorBottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    public WeakReference accessibilityDelegateViewRef;
    public int activePointerId;
    public boolean animInterruptible;
    public boolean animRunning;
    public AttributeSet attrs;
    public ColorStateList backgroundTint;
    public AnimConfig bottomEnterAnimConfig;
    public IStateStyle bottomEnterAnimStateStyle;
    public TransitionListener bottomEnterAnimTransitionListener;
    public AnimConfig bottomExitAnimConfig;
    public IStateStyle bottomExitAnimStateStyle;
    public TransitionListener bottomExitAnimTransitionListener;
    public int bottomModeLandscapeWidth;
    public boolean bottomModeLandscapeWidthRuleEnabled;
    public int bottomModeMaxWidth;
    public final ArrayList callbacks;
    public int childHeight;
    public int childYInWindow;
    public int collapsedOffset;
    public int defaultExpandedOffset;
    public int defaultHighExpandedOffset;
    public float density;
    public boolean dismissed;
    public final ViewDragHelper.Callback dragCallback;
    public boolean draggable;
    public float elevation;
    public final SparseIntArray expandHalfwayActionIds;
    public int expandedOffset;
    public int extraHeight;
    public boolean extraPaddingEnabled;
    public ExtraPaddingPolicy extraPaddingPolicy;
    public int fitToContentsOffset;
    public float fixedHeightRatio;
    public boolean fixedHeightRatioEnabled;
    public boolean forceFullHeight;
    public float fullHeightHighRatio;
    public float fullHeightLowRatio;
    public int fullHeightLowRatioThreshold;
    public float fullHeightMiddleRatio;
    public int fullHeightMiddleRatioThreshold;
    public boolean fullHeightMode;
    public int gestureInsetBottom;
    public boolean gestureInsetBottomIgnored;
    public int halfExpandedOffset;
    public float halfExpandedRatio;
    public float halfExpandedRatioWhenFixHeightRatio;
    public float hideFriction;
    public boolean hideable;
    public int highExpandedOffsetThreshold;
    public int horizontalExtraPadding;
    public boolean ignoreEvents;
    public Map importantForAccessibilityMap;
    public boolean improveAnimPerformance;
    public int initialY;
    public int insetBottom;
    public int insetTop;
    public int insetTopInMeasureStep;
    public boolean internalDraggable;
    public boolean internalFixedHeightRatioEnabled;
    public int lastMode;
    public int lastNestedScrollDy;
    public int lastStableState;
    public ViewTreeObserver.OnGlobalLayoutListener mAnchorLayoutListener;
    public boolean mAnchorLayoutListenerAttached;
    public WeakReference mAnchorLayoutListenerView;
    public WeakReference mAnchorView;
    public int mDeviceType;
    public int mImeAdjustedMaxHeight;
    public WindowInsetsAnimation.Callback mImeAnimationCallback;
    public int mOriginalPopupHeight;
    public int mOriginalPopupY;
    public boolean mPopupAdjustedForIme;
    public final PopupPositionCalculator mPopupPositionCalculator;
    public float mRadius;
    public RequestLayoutRunnable mRequestLayoutRunnable;
    public TransformAnimation mTransformAnim;
    public final ITransformableView mTransformableDelegate;
    public boolean marginLeftSystemWindowInsets;
    public boolean marginRightSystemWindowInsets;
    public int maxHeight;
    public int maxWidth;
    public float maximumVelocity;
    public int minHeight;
    public int mode;
    public int modeConfig;
    public boolean nestedScrolled;
    public WeakReference nestedScrollingChildRef;
    public OnExtraPaddingListener onExtraPaddingListener;
    public OnModeChangeListener onModeChangeListener;
    public boolean originalWindowInsetsEnabled;
    public boolean paddingBottomSystemWindowInsets;
    public boolean paddingLeftSystemWindowInsets;
    public boolean paddingRightSystemWindowInsets;
    public int parentHeight;
    public WeakReference parentViewRef;
    public int parentWidth;
    public int peekHeight;
    public boolean peekHeightAuto;
    public int peekHeightGestureInsetBuffer;
    public int peekHeightMin;
    public AnimConfig popupEnterAnimConfig;
    public IStateStyle popupEnterAnimStateStyle;
    public TransitionListener popupEnterAnimTransitionListener;
    public AnimConfig popupExitAnimConfig;
    public IStateStyle popupExitAnimStateStyle;
    public TransitionListener popupExitAnimTransitionListener;
    public AnimConfig releaseAnimConfig;
    public ReleaseAnimListener releaseAnimListener;
    public IStateStyle releaseAnimStateStyle;
    public TransitionListener releaseAnimTransitionListener;
    public final AnimState releaseEndAnimState;
    public final AnimState releaseStartAnimState;
    public int saveFlags;
    public boolean shouldResetChildBeforeFirstShow;
    public int significantDistanceThreshold;
    public int significantVelocityThreshold;
    public boolean skipCollapsed;
    public boolean skipHalfExpanded;
    public int stableStateChildTop;
    public int state;
    public boolean touchingScrollingChild;
    public boolean updateImportantForAccessibilityOnSiblings;
    public VelocityTracker velocityTracker;
    public ViewDragHelper viewDragHelper;
    public WeakReference viewRef;

    public interface AnimationListener {
        void onAnimationEnd();

        void onAnimationStart();
    }

    public static abstract class BottomSheetCallback {
        public void onLayout(View view) {
        }

        public abstract void onSlide(View view, float f);

        public abstract void onStateChanged(View view, int i);
    }

    public static class FoldFloatingHelper {
    }

    public interface OnExtraPaddingListener {
        void onExtraPaddingChanged(int i);
    }

    public interface OnModeChangeListener {
        void onModeChange(int i, View view);
    }

    public static class PadFloatingHelper {
    }

    public interface ReleaseAnimListener {
        void onEnd(int i);

        void onStart(int i);
    }

    public static class RequestLayoutRunnable implements Runnable {
        public int mInsetTop = Integer.MIN_VALUE;
        public int mInsetTopInMeasuredStep = Integer.MIN_VALUE;
        public final WeakReference mViewRef;

        public RequestLayoutRunnable(View view) {
            this.mViewRef = new WeakReference(view);
        }

        @Override // java.lang.Runnable
        public void run() {
            int i = this.mInsetTop;
            if (i == Integer.MIN_VALUE || i == this.mInsetTopInMeasuredStep) {
                return;
            }
            ((View) this.mViewRef.get()).requestLayout();
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface SaveFlags {
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: miuix.bottomsheet.AnchorBottomSheetBehavior.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }

            @Override // android.os.Parcelable.ClassLoaderCreator
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public boolean hideable;
        public int peekHeight;
        public boolean skipCollapsed;
        public final int state;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.state = parcel.readInt();
            this.peekHeight = parcel.readInt();
            this.hideable = parcel.readInt() == 1;
            this.skipCollapsed = parcel.readInt() == 1;
        }

        public SavedState(Parcelable parcelable, AnchorBottomSheetBehavior anchorBottomSheetBehavior) {
            super(parcelable);
            this.state = anchorBottomSheetBehavior.state;
            this.peekHeight = anchorBottomSheetBehavior.peekHeight;
            this.hideable = anchorBottomSheetBehavior.hideable;
            this.skipCollapsed = anchorBottomSheetBehavior.skipCollapsed;
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.state);
            parcel.writeInt(this.peekHeight);
            parcel.writeInt(this.hideable ? 1 : 0);
            parcel.writeInt(this.skipCollapsed ? 1 : 0);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface StableState {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface State {
    }

    public class StateSettlingTracker {
        public final Runnable continueSettlingRunnable;
        public boolean isContinueSettlingRunnablePosted;
        public int targetState;
        public final /* synthetic */ AnchorBottomSheetBehavior this$0;

        /* JADX INFO: renamed from: miuix.bottomsheet.AnchorBottomSheetBehavior$StateSettlingTracker$1, reason: invalid class name */
        public class AnonymousClass1 implements Runnable {
            public final /* synthetic */ StateSettlingTracker this$1;

            @Override // java.lang.Runnable
            public void run() {
                this.this$1.isContinueSettlingRunnablePosted = false;
                ViewDragHelper viewDragHelper = this.this$1.this$0.viewDragHelper;
                if (viewDragHelper != null && viewDragHelper.continueSettling(true)) {
                    StateSettlingTracker stateSettlingTracker = this.this$1;
                    stateSettlingTracker.continueSettlingToState(stateSettlingTracker.targetState);
                    return;
                }
                StateSettlingTracker stateSettlingTracker2 = this.this$1;
                AnchorBottomSheetBehavior anchorBottomSheetBehavior = stateSettlingTracker2.this$0;
                if (anchorBottomSheetBehavior.state == 2) {
                    anchorBottomSheetBehavior.setStateInternal(stateSettlingTracker2.targetState);
                }
            }
        }

        public void continueSettlingToState(int i) {
            WeakReference weakReference = this.this$0.viewRef;
            if (weakReference == null || weakReference.get() == null) {
                return;
            }
            this.targetState = i;
            if (this.isContinueSettlingRunnablePosted) {
                return;
            }
            ViewCompat.postOnAnimation((View) this.this$0.viewRef.get(), this.continueSettlingRunnable);
            this.isContinueSettlingRunnablePosted = true;
        }
    }

    /* JADX WARN: Code duplicated, block: B:36:0x0097  */
    /* JADX WARN: Code duplicated, block: B:42:0x00a5  */
    /* JADX INFO: renamed from: $r8$lambda$Sga_d_y-BrLJ4RkRFoh0r0i2p4A, reason: not valid java name */
    public static /* synthetic */ WindowInsetsCompat m1328$r8$lambda$Sga_d_yBrLJ4RkRFoh0r0i2p4A(AnchorBottomSheetBehavior anchorBottomSheetBehavior, View view, boolean z, View view2, WindowInsetsCompat windowInsetsCompat, ViewUtils.RelativePadding relativePadding) {
        boolean z2;
        WindowInsetsCompat rootWindowInsets;
        if (anchorBottomSheetBehavior.originalWindowInsetsEnabled && (rootWindowInsets = ViewCompat.getRootWindowInsets(view2)) != null) {
            windowInsetsCompat = rootWindowInsets;
        }
        boolean z3 = true;
        if (anchorBottomSheetBehavior.mode != 1) {
            Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            Insets insets2 = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.mandatorySystemGestures());
            int i = insets.top;
            anchorBottomSheetBehavior.insetTop = i;
            if (i != anchorBottomSheetBehavior.insetTopInMeasureStep) {
                if (anchorBottomSheetBehavior.mRequestLayoutRunnable == null) {
                    anchorBottomSheetBehavior.mRequestLayoutRunnable = new RequestLayoutRunnable(view);
                }
                view2.removeCallbacks(anchorBottomSheetBehavior.mRequestLayoutRunnable);
                RequestLayoutRunnable requestLayoutRunnable = anchorBottomSheetBehavior.mRequestLayoutRunnable;
                requestLayoutRunnable.mInsetTop = anchorBottomSheetBehavior.insetTop;
                view2.post(requestLayoutRunnable);
            }
            boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(view2);
            int paddingBottom = view2.getPaddingBottom();
            int paddingLeft = view2.getPaddingLeft();
            int paddingRight = view2.getPaddingRight();
            if (anchorBottomSheetBehavior.paddingBottomSystemWindowInsets) {
                int systemWindowInsetBottom = windowInsetsCompat.getSystemWindowInsetBottom();
                anchorBottomSheetBehavior.insetBottom = systemWindowInsetBottom;
                paddingBottom = systemWindowInsetBottom + relativePadding.bottom;
            }
            if (anchorBottomSheetBehavior.paddingLeftSystemWindowInsets) {
                paddingLeft = (zIsLayoutRtl ? relativePadding.end : relativePadding.start) + insets.left;
            }
            if (anchorBottomSheetBehavior.paddingRightSystemWindowInsets) {
                paddingRight = (zIsLayoutRtl ? relativePadding.start : relativePadding.end) + insets.right;
            }
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view2.getLayoutParams();
            if (anchorBottomSheetBehavior.marginLeftSystemWindowInsets) {
                int i2 = marginLayoutParams.leftMargin;
                int i3 = insets.left;
                if (i2 != i3) {
                    marginLayoutParams.leftMargin = i3;
                    z2 = true;
                } else {
                    z2 = false;
                }
            } else {
                z2 = false;
            }
            if (anchorBottomSheetBehavior.marginRightSystemWindowInsets) {
                int i4 = marginLayoutParams.rightMargin;
                int i5 = insets.right;
                if (i4 != i5) {
                    marginLayoutParams.rightMargin = i5;
                } else {
                    z3 = z2;
                }
            } else {
                z3 = z2;
            }
            if (z3) {
                view2.setLayoutParams(marginLayoutParams);
            }
            view2.setPadding(paddingLeft, view2.getPaddingTop(), paddingRight, paddingBottom);
            if (z) {
                anchorBottomSheetBehavior.gestureInsetBottom = insets2.bottom;
            }
            if (anchorBottomSheetBehavior.paddingBottomSystemWindowInsets || z) {
                anchorBottomSheetBehavior.updatePeekHeight(false);
                return windowInsetsCompat;
            }
        }
        return windowInsetsCompat;
    }

    public static /* synthetic */ void $r8$lambda$ptW78Iay132wnOR97lGE2VIeVPk(AnchorBottomSheetBehavior anchorBottomSheetBehavior) {
        WeakReference weakReference;
        if (anchorBottomSheetBehavior.animRunning) {
            return;
        }
        if (anchorBottomSheetBehavior.dismissed || anchorBottomSheetBehavior.mode != 1) {
            anchorBottomSheetBehavior.detachAnchorLayoutListener();
            return;
        }
        WeakReference weakReference2 = anchorBottomSheetBehavior.viewRef;
        if (weakReference2 == null || weakReference2.get() == null || (weakReference = anchorBottomSheetBehavior.parentViewRef) == null || weakReference.get() == null) {
            return;
        }
        anchorBottomSheetBehavior.layoutPopupMode((CoordinatorLayout) anchorBottomSheetBehavior.parentViewRef.get(), (View) anchorBottomSheetBehavior.viewRef.get());
        if (anchorBottomSheetBehavior.mPopupAdjustedForIme) {
            return;
        }
        anchorBottomSheetBehavior.mOriginalPopupY = (int) ((View) anchorBottomSheetBehavior.viewRef.get()).getY();
        anchorBottomSheetBehavior.mOriginalPopupHeight = ((View) anchorBottomSheetBehavior.viewRef.get()).getHeight();
    }

    public AnchorBottomSheetBehavior() {
        this.saveFlags = 0;
        this.updateImportantForAccessibilityOnSiblings = false;
        this.maxWidth = -1;
        this.maxHeight = -1;
        this.insetTop = -1;
        this.halfExpandedRatio = 0.5f;
        this.halfExpandedRatioWhenFixHeightRatio = 0.7f;
        this.elevation = -1.0f;
        this.draggable = true;
        this.state = 4;
        this.lastStableState = 4;
        this.hideFriction = 0.1f;
        this.callbacks = new ArrayList();
        this.initialY = -1;
        this.expandHalfwayActionIds = new SparseIntArray();
        this.modeConfig = 0;
        this.mode = 0;
        this.lastMode = -1;
        this.fullHeightMode = false;
        this.fullHeightHighRatio = 0.8f;
        this.fullHeightMiddleRatio = 0.7f;
        this.fullHeightLowRatio = 0.0f;
        this.skipHalfExpanded = false;
        this.forceFullHeight = false;
        this.fixedHeightRatioEnabled = false;
        this.internalFixedHeightRatioEnabled = false;
        this.fixedHeightRatio = 0.7f;
        this.bottomModeLandscapeWidthRuleEnabled = false;
        this.originalWindowInsetsEnabled = false;
        this.internalDraggable = true;
        this.animRunning = false;
        this.animInterruptible = false;
        this.horizontalExtraPadding = 0;
        this.extraPaddingEnabled = true;
        this.improveAnimPerformance = false;
        this.dismissed = true;
        this.shouldResetChildBeforeFirstShow = true;
        this.mOriginalPopupY = 0;
        this.mOriginalPopupHeight = 0;
        this.mPopupAdjustedForIme = false;
        this.mImeAdjustedMaxHeight = -1;
        this.mRadius = 0.0f;
        this.mAnchorLayoutListenerAttached = false;
        this.mTransformableDelegate = new ITransformableView() { // from class: miuix.bottomsheet.AnchorBottomSheetBehavior.2
            @Override // miuix.view.ITransformableView
            public void applyTransformElevation(View view) {
                if (!MiShadowUtils.SUPPORT_MI_SHADOW) {
                    view.setElevation(AnchorBottomSheetBehavior.this.elevation);
                } else {
                    ShadowToken shadowToken = ShadowToken.High;
                    MiShadowUtils.setMiShadow(view, shadowToken.color, shadowToken.offsetX, shadowToken.offsetY, shadowToken.radius);
                }
            }

            @Override // miuix.view.ITransformableView
            public View getContainerView() {
                return (View) AnchorBottomSheetBehavior.this.viewRef.get();
            }

            @Override // miuix.view.ITransformableView
            public View getContentView() {
                View view = (View) AnchorBottomSheetBehavior.this.viewRef.get();
                if (view instanceof AnchorBottomSheetView) {
                    return ((AnchorBottomSheetView) view).getContentView();
                }
                return null;
            }

            @Override // miuix.view.ITransformableView
            public float getCornerRadius() {
                return AnchorBottomSheetBehavior.this.mRadius;
            }

            @Override // miuix.view.ITransformableView
            public void setCornerRadius(float f) {
                AnchorBottomSheetBehavior.this.mRadius = f;
                View view = (View) AnchorBottomSheetBehavior.this.viewRef.get();
                if (view instanceof AnchorBottomSheetView) {
                    ((AnchorBottomSheetView) view).setPopupModeRadius((int) f);
                    view.invalidateOutline();
                }
            }
        };
        this.releaseStartAnimState = new AnimState();
        this.releaseEndAnimState = new AnimState();
        this.dragCallback = new ViewDragHelper.Callback() { // from class: miuix.bottomsheet.AnchorBottomSheetBehavior.9
            public long viewCapturedMillis;

            private boolean releasedLow(View view) {
                int top = view.getTop();
                AnchorBottomSheetBehavior anchorBottomSheetBehavior = AnchorBottomSheetBehavior.this;
                return top > (anchorBottomSheetBehavior.parentHeight + anchorBottomSheetBehavior.getExpandedOffset()) / 2;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(View view, int i, int i2) {
                return view.getLeft();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(View view, int i, int i2) {
                return MathUtils.clamp(i, AnchorBottomSheetBehavior.this.getExpandedOffset(), getViewVerticalDragRange(view));
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(View view) {
                return AnchorBottomSheetBehavior.this.canBeHiddenByDragging() ? AnchorBottomSheetBehavior.this.parentHeight : AnchorBottomSheetBehavior.this.collapsedOffset;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewDragStateChanged(int i) {
                if (i == 1 && AnchorBottomSheetBehavior.this.draggable) {
                    AnchorBottomSheetBehavior.this.setStateInternal(1);
                }
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
                AnchorBottomSheetBehavior.this.dispatchOnSlide(i2);
            }

            /* JADX WARN: Code duplicated, block: B:39:0x00ad  */
            /* JADX WARN: Code duplicated, block: B:6:0x0010  */
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(View view, float f, float f2) {
                int i = 6;
                if (f2 >= 0.0f) {
                    AnchorBottomSheetBehavior anchorBottomSheetBehavior = AnchorBottomSheetBehavior.this;
                    if (anchorBottomSheetBehavior.hideable && anchorBottomSheetBehavior.shouldHide(view, f2)) {
                        if ((Math.abs(f) < Math.abs(f2) && f2 > AnchorBottomSheetBehavior.this.significantVelocityThreshold) || releasedLow(view)) {
                            i = 5;
                        } else if (AnchorBottomSheetBehavior.this.shouldSkipHalfExpanded() || Math.abs(view.getTop() - AnchorBottomSheetBehavior.this.getExpandedOffset()) < Math.abs(view.getTop() - AnchorBottomSheetBehavior.this.halfExpandedOffset)) {
                            i = 3;
                        }
                    } else if (f2 == 0.0f || Math.abs(f) > Math.abs(f2)) {
                        int top = view.getTop();
                        if (!AnchorBottomSheetBehavior.this.shouldSkipHalfExpanded()) {
                            AnchorBottomSheetBehavior anchorBottomSheetBehavior2 = AnchorBottomSheetBehavior.this;
                            int i2 = anchorBottomSheetBehavior2.halfExpandedOffset;
                            if (top < i2) {
                                if (top < Math.abs(top - anchorBottomSheetBehavior2.collapsedOffset)) {
                                    i = 3;
                                } else if (AnchorBottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                                    i = 4;
                                }
                            } else if (Math.abs(top - i2) >= Math.abs(top - AnchorBottomSheetBehavior.this.collapsedOffset) || AnchorBottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                                i = 4;
                            }
                        } else if (Math.abs(top - AnchorBottomSheetBehavior.this.fitToContentsOffset) < Math.abs(top - AnchorBottomSheetBehavior.this.collapsedOffset)) {
                            i = 3;
                        } else {
                            i = 4;
                        }
                    } else if (AnchorBottomSheetBehavior.this.shouldSkipHalfExpanded()) {
                        i = 4;
                    } else {
                        int top2 = view.getTop();
                        if (Math.abs(top2 - AnchorBottomSheetBehavior.this.halfExpandedOffset) >= Math.abs(top2 - AnchorBottomSheetBehavior.this.collapsedOffset) || AnchorBottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                            i = 4;
                        }
                    }
                } else if (AnchorBottomSheetBehavior.this.shouldSkipHalfExpanded()) {
                    i = 3;
                } else {
                    int top3 = view.getTop();
                    long jCurrentTimeMillis = System.currentTimeMillis() - this.viewCapturedMillis;
                    if (AnchorBottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                        AnchorBottomSheetBehavior anchorBottomSheetBehavior3 = AnchorBottomSheetBehavior.this;
                        if (!anchorBottomSheetBehavior3.shouldExpandOnUpwardDrag(jCurrentTimeMillis, (top3 * 100.0f) / anchorBottomSheetBehavior3.parentHeight)) {
                            i = 4;
                        }
                    } else if (top3 <= AnchorBottomSheetBehavior.this.halfExpandedOffset) {
                    }
                    i = 3;
                }
                AnchorBottomSheetBehavior anchorBottomSheetBehavior4 = AnchorBottomSheetBehavior.this;
                anchorBottomSheetBehavior4.startSettling(view, i, anchorBottomSheetBehavior4.shouldSkipSmoothAnimation());
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(View view, int i) {
                AnchorBottomSheetBehavior anchorBottomSheetBehavior;
                int i2;
                if (AnchorBottomSheetBehavior.this.animRunning || (i2 = (anchorBottomSheetBehavior = AnchorBottomSheetBehavior.this).state) == 1 || anchorBottomSheetBehavior.touchingScrollingChild) {
                    return false;
                }
                if (i2 == 3 && anchorBottomSheetBehavior.activePointerId == i) {
                    WeakReference weakReference = anchorBottomSheetBehavior.nestedScrollingChildRef;
                    View view2 = weakReference != null ? (View) weakReference.get() : null;
                    if (view2 != null && view2.canScrollVertically(-1)) {
                        return false;
                    }
                }
                this.viewCapturedMillis = System.currentTimeMillis();
                WeakReference weakReference2 = AnchorBottomSheetBehavior.this.viewRef;
                return weakReference2 != null && weakReference2.get() == view;
            }
        };
        this.mPopupPositionCalculator = new PopupPositionCalculator();
    }

    public AnchorBottomSheetBehavior(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.saveFlags = 0;
        this.updateImportantForAccessibilityOnSiblings = false;
        this.maxWidth = -1;
        this.maxHeight = -1;
        this.insetTop = -1;
        this.halfExpandedRatio = 0.5f;
        this.halfExpandedRatioWhenFixHeightRatio = 0.7f;
        this.elevation = -1.0f;
        this.draggable = true;
        this.state = 4;
        this.lastStableState = 4;
        this.hideFriction = 0.1f;
        this.callbacks = new ArrayList();
        this.initialY = -1;
        this.expandHalfwayActionIds = new SparseIntArray();
        this.modeConfig = 0;
        this.mode = 0;
        this.lastMode = -1;
        this.fullHeightMode = false;
        this.fullHeightHighRatio = 0.8f;
        this.fullHeightMiddleRatio = 0.7f;
        this.fullHeightLowRatio = 0.0f;
        this.skipHalfExpanded = false;
        this.forceFullHeight = false;
        this.fixedHeightRatioEnabled = false;
        this.internalFixedHeightRatioEnabled = false;
        this.fixedHeightRatio = 0.7f;
        this.bottomModeLandscapeWidthRuleEnabled = false;
        this.originalWindowInsetsEnabled = false;
        this.internalDraggable = true;
        this.animRunning = false;
        this.animInterruptible = false;
        this.horizontalExtraPadding = 0;
        this.extraPaddingEnabled = true;
        this.improveAnimPerformance = false;
        this.dismissed = true;
        this.shouldResetChildBeforeFirstShow = true;
        this.mOriginalPopupY = 0;
        this.mOriginalPopupHeight = 0;
        this.mPopupAdjustedForIme = false;
        this.mImeAdjustedMaxHeight = -1;
        this.mRadius = 0.0f;
        this.mAnchorLayoutListenerAttached = false;
        this.mTransformableDelegate = new ITransformableView() { // from class: miuix.bottomsheet.AnchorBottomSheetBehavior.2
            @Override // miuix.view.ITransformableView
            public void applyTransformElevation(View view) {
                if (!MiShadowUtils.SUPPORT_MI_SHADOW) {
                    view.setElevation(AnchorBottomSheetBehavior.this.elevation);
                } else {
                    ShadowToken shadowToken = ShadowToken.High;
                    MiShadowUtils.setMiShadow(view, shadowToken.color, shadowToken.offsetX, shadowToken.offsetY, shadowToken.radius);
                }
            }

            @Override // miuix.view.ITransformableView
            public View getContainerView() {
                return (View) AnchorBottomSheetBehavior.this.viewRef.get();
            }

            @Override // miuix.view.ITransformableView
            public View getContentView() {
                View view = (View) AnchorBottomSheetBehavior.this.viewRef.get();
                if (view instanceof AnchorBottomSheetView) {
                    return ((AnchorBottomSheetView) view).getContentView();
                }
                return null;
            }

            @Override // miuix.view.ITransformableView
            public float getCornerRadius() {
                return AnchorBottomSheetBehavior.this.mRadius;
            }

            @Override // miuix.view.ITransformableView
            public void setCornerRadius(float f) {
                AnchorBottomSheetBehavior.this.mRadius = f;
                View view = (View) AnchorBottomSheetBehavior.this.viewRef.get();
                if (view instanceof AnchorBottomSheetView) {
                    ((AnchorBottomSheetView) view).setPopupModeRadius((int) f);
                    view.invalidateOutline();
                }
            }
        };
        this.releaseStartAnimState = new AnimState();
        this.releaseEndAnimState = new AnimState();
        this.dragCallback = new ViewDragHelper.Callback() { // from class: miuix.bottomsheet.AnchorBottomSheetBehavior.9
            public long viewCapturedMillis;

            private boolean releasedLow(View view) {
                int top = view.getTop();
                AnchorBottomSheetBehavior anchorBottomSheetBehavior = AnchorBottomSheetBehavior.this;
                return top > (anchorBottomSheetBehavior.parentHeight + anchorBottomSheetBehavior.getExpandedOffset()) / 2;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(View view, int i, int i2) {
                return view.getLeft();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(View view, int i, int i2) {
                return MathUtils.clamp(i, AnchorBottomSheetBehavior.this.getExpandedOffset(), getViewVerticalDragRange(view));
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(View view) {
                return AnchorBottomSheetBehavior.this.canBeHiddenByDragging() ? AnchorBottomSheetBehavior.this.parentHeight : AnchorBottomSheetBehavior.this.collapsedOffset;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewDragStateChanged(int i) {
                if (i == 1 && AnchorBottomSheetBehavior.this.draggable) {
                    AnchorBottomSheetBehavior.this.setStateInternal(1);
                }
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
                AnchorBottomSheetBehavior.this.dispatchOnSlide(i2);
            }

            /* JADX WARN: Code duplicated, block: B:39:0x00ad  */
            /* JADX WARN: Code duplicated, block: B:6:0x0010  */
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(View view, float f, float f2) {
                int i = 6;
                if (f2 >= 0.0f) {
                    AnchorBottomSheetBehavior anchorBottomSheetBehavior = AnchorBottomSheetBehavior.this;
                    if (anchorBottomSheetBehavior.hideable && anchorBottomSheetBehavior.shouldHide(view, f2)) {
                        if ((Math.abs(f) < Math.abs(f2) && f2 > AnchorBottomSheetBehavior.this.significantVelocityThreshold) || releasedLow(view)) {
                            i = 5;
                        } else if (AnchorBottomSheetBehavior.this.shouldSkipHalfExpanded() || Math.abs(view.getTop() - AnchorBottomSheetBehavior.this.getExpandedOffset()) < Math.abs(view.getTop() - AnchorBottomSheetBehavior.this.halfExpandedOffset)) {
                            i = 3;
                        }
                    } else if (f2 == 0.0f || Math.abs(f) > Math.abs(f2)) {
                        int top = view.getTop();
                        if (!AnchorBottomSheetBehavior.this.shouldSkipHalfExpanded()) {
                            AnchorBottomSheetBehavior anchorBottomSheetBehavior2 = AnchorBottomSheetBehavior.this;
                            int i2 = anchorBottomSheetBehavior2.halfExpandedOffset;
                            if (top < i2) {
                                if (top < Math.abs(top - anchorBottomSheetBehavior2.collapsedOffset)) {
                                    i = 3;
                                } else if (AnchorBottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                                    i = 4;
                                }
                            } else if (Math.abs(top - i2) >= Math.abs(top - AnchorBottomSheetBehavior.this.collapsedOffset) || AnchorBottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                                i = 4;
                            }
                        } else if (Math.abs(top - AnchorBottomSheetBehavior.this.fitToContentsOffset) < Math.abs(top - AnchorBottomSheetBehavior.this.collapsedOffset)) {
                            i = 3;
                        } else {
                            i = 4;
                        }
                    } else if (AnchorBottomSheetBehavior.this.shouldSkipHalfExpanded()) {
                        i = 4;
                    } else {
                        int top2 = view.getTop();
                        if (Math.abs(top2 - AnchorBottomSheetBehavior.this.halfExpandedOffset) >= Math.abs(top2 - AnchorBottomSheetBehavior.this.collapsedOffset) || AnchorBottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                            i = 4;
                        }
                    }
                } else if (AnchorBottomSheetBehavior.this.shouldSkipHalfExpanded()) {
                    i = 3;
                } else {
                    int top3 = view.getTop();
                    long jCurrentTimeMillis = System.currentTimeMillis() - this.viewCapturedMillis;
                    if (AnchorBottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                        AnchorBottomSheetBehavior anchorBottomSheetBehavior3 = AnchorBottomSheetBehavior.this;
                        if (!anchorBottomSheetBehavior3.shouldExpandOnUpwardDrag(jCurrentTimeMillis, (top3 * 100.0f) / anchorBottomSheetBehavior3.parentHeight)) {
                            i = 4;
                        }
                    } else if (top3 <= AnchorBottomSheetBehavior.this.halfExpandedOffset) {
                    }
                    i = 3;
                }
                AnchorBottomSheetBehavior anchorBottomSheetBehavior4 = AnchorBottomSheetBehavior.this;
                anchorBottomSheetBehavior4.startSettling(view, i, anchorBottomSheetBehavior4.shouldSkipSmoothAnimation());
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(View view, int i) {
                AnchorBottomSheetBehavior anchorBottomSheetBehavior;
                int i2;
                if (AnchorBottomSheetBehavior.this.animRunning || (i2 = (anchorBottomSheetBehavior = AnchorBottomSheetBehavior.this).state) == 1 || anchorBottomSheetBehavior.touchingScrollingChild) {
                    return false;
                }
                if (i2 == 3 && anchorBottomSheetBehavior.activePointerId == i) {
                    WeakReference weakReference = anchorBottomSheetBehavior.nestedScrollingChildRef;
                    View view2 = weakReference != null ? (View) weakReference.get() : null;
                    if (view2 != null && view2.canScrollVertically(-1)) {
                        return false;
                    }
                }
                this.viewCapturedMillis = System.currentTimeMillis();
                WeakReference weakReference2 = AnchorBottomSheetBehavior.this.viewRef;
                return weakReference2 != null && weakReference2.get() == view;
            }
        };
        this.density = context.getResources().getDisplayMetrics().density;
        this.attrs = attributeSet;
        this.mPopupPositionCalculator = new PopupPositionCalculator();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MiuixAnchorBottomSheetBehavior_Layout);
        int i = R.styleable.MiuixAnchorBottomSheetBehavior_Layout_miuix_backgroundTint;
        if (typedArrayObtainStyledAttributes.hasValue(i)) {
            this.backgroundTint = getColorStateList(context, typedArrayObtainStyledAttributes, i);
        }
        setHideable(typedArrayObtainStyledAttributes.getBoolean(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_miuix_behavior_hideable, false));
        setGestureInsetBottomIgnored(typedArrayObtainStyledAttributes.getBoolean(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_miuixGestureInsetBottomIgnored, false));
        setForceFullHeight(typedArrayObtainStyledAttributes.getBoolean(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_miuix_behavior_force_full_height, false));
        setSkipCollapsed(typedArrayObtainStyledAttributes.getBoolean(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_miuix_behavior_skipCollapsed, false));
        setDraggable(typedArrayObtainStyledAttributes.getBoolean(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_miuix_behavior_draggable, true));
        setSaveFlags(typedArrayObtainStyledAttributes.getInt(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_miuix_behavior_saveFlags, 0));
        setHalfExpandedRatio(typedArrayObtainStyledAttributes.getFloat(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_miuix_behavior_halfExpandedRatio, 0.5f));
        setSignificantVelocityThreshold(typedArrayObtainStyledAttributes.getInt(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_miuix_behavior_significantVelocityThreshold, 1000));
        setSignificantDistanceThreshold(typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_miuix_behavior_significantDistanceThreshold, MiuixUIUtils.dp2px(context, 60.0f)));
        this.paddingBottomSystemWindowInsets = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_miuixPaddingBottomSystemWindowInsets, true);
        this.paddingLeftSystemWindowInsets = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_miuixPaddingLeftSystemWindowInsets, false);
        this.paddingRightSystemWindowInsets = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_miuixPaddingRightSystemWindowInsets, false);
        this.marginLeftSystemWindowInsets = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_miuixMarginLeftSystemWindowInsets, true);
        this.marginRightSystemWindowInsets = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_miuixMarginRightSystemWindowInsets, true);
        this.modeConfig = typedArrayObtainStyledAttributes.getInt(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_anchorModeConfig, 0);
        this.fullHeightHighRatio = typedArrayObtainStyledAttributes.getFloat(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_miuix_behavior_full_height_high_ratio, 0.8f);
        this.fullHeightMiddleRatio = typedArrayObtainStyledAttributes.getFloat(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_miuix_behavior_full_height_middle_ratio, 0.7f);
        this.fullHeightLowRatio = typedArrayObtainStyledAttributes.getFloat(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_miuix_behavior_full_height_low_ratio, 0.0f);
        float f = typedArrayObtainStyledAttributes.getFloat(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_miuix_behavior_fixed_height_ratio, 0.7f);
        this.fixedHeightRatio = f;
        this.halfExpandedRatioWhenFixHeightRatio = f;
        typedArrayObtainStyledAttributes.recycle();
        updateSizeConfig(context, attributeSet);
        this.mDeviceType = DeviceHelper.detectType(context);
        this.maximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }

    private int addAccessibilityActionForState(View view, int i, int i2) {
        return ViewCompat.addAccessibilityAction(view, view.getResources().getString(i), createAccessibilityViewCommandForState(i2));
    }

    private void calculateCollapsedOffset() {
        this.collapsedOffset = Math.max(this.parentHeight - calculatePeekHeight(), this.fitToContentsOffset);
    }

    private void calculateHalfExpandedOffset() {
        this.halfExpandedOffset = (int) (this.parentHeight * (1.0f - (this.internalFixedHeightRatioEnabled ? this.halfExpandedRatioWhenFixHeightRatio : this.halfExpandedRatio)));
    }

    private int calculatePeekHeight() {
        int iMin;
        int i;
        int i2;
        if (this.peekHeightAuto) {
            iMin = Math.min(Math.max(this.peekHeightMin, this.parentHeight - ((this.parentWidth * 9) / 16)), this.childHeight);
            i = this.insetBottom;
        } else {
            if (!this.gestureInsetBottomIgnored && !this.paddingBottomSystemWindowInsets && (i2 = this.gestureInsetBottom) > 0) {
                return Math.max(this.peekHeight, i2 + this.peekHeightGestureInsetBuffer);
            }
            iMin = this.peekHeight;
            i = this.insetBottom;
        }
        return iMin + i;
    }

    private float calculateSlideOffsetWithTop(int i) {
        float f;
        float expandedOffset;
        int i2 = this.collapsedOffset;
        if (i > i2 || i2 == getExpandedOffset()) {
            int i3 = this.collapsedOffset;
            f = i3 - i;
            expandedOffset = this.parentHeight - i3;
        } else {
            int i4 = this.collapsedOffset;
            f = i4 - i;
            expandedOffset = i4 - getExpandedOffset();
        }
        return f / expandedOffset;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean canBeHiddenByDragging() {
        return isHideable() && isHideableWhenDragging();
    }

    private void clearAccessibilityAction(View view, int i) {
        if (view == null) {
            return;
        }
        ViewCompat.removeAccessibilityAction(view, 524288);
        ViewCompat.removeAccessibilityAction(view, 262144);
        ViewCompat.removeAccessibilityAction(view, 1048576);
        int i2 = this.expandHalfwayActionIds.get(i, -1);
        if (i2 != -1) {
            ViewCompat.removeAccessibilityAction(view, i2);
            this.expandHalfwayActionIds.delete(i);
        }
    }

    private AccessibilityViewCommand createAccessibilityViewCommandForState(final int i) {
        return new AccessibilityViewCommand() { // from class: miuix.bottomsheet.AnchorBottomSheetBehavior.10
            @Override // androidx.core.view.accessibility.AccessibilityViewCommand
            public boolean perform(View view, AccessibilityViewCommand.CommandArguments commandArguments) {
                AnchorBottomSheetBehavior.this.setState(i);
                return true;
            }
        };
    }

    public static AnchorBottomSheetBehavior from(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
        if (behavior instanceof AnchorBottomSheetBehavior) {
            return (AnchorBottomSheetBehavior) behavior;
        }
        throw new IllegalArgumentException("The view is not associated with AnchorBottomSheetBehavior");
    }

    private int getChildMeasureSpec(int i, int i2, int i3, int i4) {
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i, i2, i4);
        if (i3 == -1) {
            return childMeasureSpec;
        }
        int mode = View.MeasureSpec.getMode(childMeasureSpec);
        int size = View.MeasureSpec.getSize(childMeasureSpec);
        if (mode == 1073741824) {
            return View.MeasureSpec.makeMeasureSpec(Math.min(size, i3), 1073741824);
        }
        if (size != 0) {
            i3 = Math.min(size, i3);
        }
        return View.MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE);
    }

    public static ColorStateList getColorStateList(Context context, TypedArray typedArray, int i) {
        int resourceId;
        ColorStateList colorStateList;
        return (!typedArray.hasValue(i) || (resourceId = typedArray.getResourceId(i, 0)) == 0 || (colorStateList = AppCompatResources.getColorStateList(context, resourceId)) == null) ? typedArray.getColorStateList(i) : colorStateList;
    }

    private int getTopOffsetForState(int i) {
        if (i == 3) {
            return getExpandedOffset();
        }
        if (i == 4) {
            return this.collapsedOffset;
        }
        if (i == 5) {
            return this.parentHeight;
        }
        if (i == 6) {
            return this.halfExpandedOffset;
        }
        throw new IllegalArgumentException("Invalid state to get top offset: " + i);
    }

    private float getYVelocity() {
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker == null) {
            return 0.0f;
        }
        velocityTracker.computeCurrentVelocity(1000, this.maximumVelocity);
        return this.velocityTracker.getYVelocity(this.activePointerId);
    }

    private boolean isLayouting(View view) {
        ViewParent parent = view.getParent();
        return parent != null && parent.isLayoutRequested() && ViewCompat.isAttachedToWindow(view);
    }

    private void replaceAccessibilityActionForState(View view, AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat, int i) {
        ViewCompat.replaceAccessibilityAction(view, accessibilityActionCompat, null, createAccessibilityViewCommandForState(i));
    }

    private void reset() {
        this.activePointerId = -1;
        this.initialY = -1;
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.velocityTracker = null;
        }
    }

    private void runAfterLayout(View view, Runnable runnable) {
        if (isLayouting(view)) {
            view.post(runnable);
        } else {
            runnable.run();
        }
    }

    private void setWindowInsetsListener(final View view) {
        final boolean z = (isGestureInsetBottomIgnored() || this.peekHeightAuto) ? false : true;
        ViewUtils.doOnApplyWindowInsets(view, new ViewUtils.OnApplyWindowInsetsListener() { // from class: miuix.bottomsheet.AnchorBottomSheetBehavior$$ExternalSyntheticLambda2
            @Override // miuix.internal.util.ViewUtils.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat, ViewUtils.RelativePadding relativePadding) {
                return AnchorBottomSheetBehavior.m1328$r8$lambda$Sga_d_yBrLJ4RkRFoh0r0i2p4A(this.f$0, view, z, view2, windowInsetsCompat, relativePadding);
            }
        });
        setupImeAnimationCallback(view);
    }

    private boolean shouldHandleDraggingWithHelper() {
        if (this.viewDragHelper != null) {
            return isInternalDraggable() || this.state == 1;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSettling(View view, final int i, boolean z) {
        int topOffsetForState = getTopOffsetForState(i);
        this.releaseStartAnimState.add("folme_key", view.getTop());
        this.releaseEndAnimState.add("folme_key", topOffsetForState);
        if (this.viewDragHelper == null) {
            setStateInternal(i);
            return;
        }
        setStateInternal(2);
        if (this.releaseAnimConfig == null) {
            AnimConfig animConfig = new AnimConfig();
            this.releaseAnimConfig = animConfig;
            animConfig.setEase(FolmeEase.spring(0.85f, 0.4f));
        }
        TransitionListener transitionListener = this.releaseAnimTransitionListener;
        if (transitionListener != null) {
            this.releaseAnimConfig.removeListeners(transitionListener);
        }
        TransitionListener transitionListener2 = new TransitionListener() { // from class: miuix.bottomsheet.AnchorBottomSheetBehavior.8
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                if (AnchorBottomSheetBehavior.this.releaseAnimListener != null) {
                    AnchorBottomSheetBehavior.this.releaseAnimListener.onStart(i);
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                AnchorBottomSheetBehavior anchorBottomSheetBehavior = AnchorBottomSheetBehavior.this;
                if (anchorBottomSheetBehavior.state == 2) {
                    anchorBottomSheetBehavior.setStateInternal(i);
                    if (AnchorBottomSheetBehavior.this.releaseAnimListener != null) {
                        AnchorBottomSheetBehavior.this.releaseAnimListener.onEnd(i);
                    }
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, "folme_key");
                View childView = AnchorBottomSheetBehavior.this.getChildView();
                if (updateInfoFindByName == null || childView == null) {
                    return;
                }
                childView.offsetTopAndBottom(updateInfoFindByName.getIntValue() - childView.getTop());
                if (AnchorBottomSheetBehavior.this.releaseAnimStateStyle != null && i == 5 && AnchorBottomSheetBehavior.this.shouldBottomExitAnimEnd()) {
                    AnchorBottomSheetBehavior.this.releaseAnimStateStyle.end();
                }
            }
        };
        this.releaseAnimTransitionListener = transitionListener2;
        this.releaseAnimConfig.addListeners(transitionListener2);
        this.releaseAnimConfig.setFromSpeed(Math.min(10000.0f, Math.max(-10000.0f, getYVelocity())));
        IStateStyle iStateStyleUseValue = Folme.useValue(this);
        this.releaseAnimStateStyle = iStateStyleUseValue;
        if (iStateStyleUseValue != null) {
            iStateStyleUseValue.setFlags(1L).setTo(this.releaseStartAnimState).to(this.releaseEndAnimState, this.releaseAnimConfig);
        }
    }

    private void updateAccessibilityActions() {
        WeakReference weakReference = this.viewRef;
        if (weakReference != null) {
            updateAccessibilityActions((View) weakReference.get(), 0);
        }
        WeakReference weakReference2 = this.accessibilityDelegateViewRef;
        if (weakReference2 != null) {
            updateAccessibilityActions((View) weakReference2.get(), 1);
        }
    }

    private void updateAccessibilityActions(View view, int i) {
        if (view == null) {
            return;
        }
        clearAccessibilityAction(view, i);
        if (!shouldSkipHalfExpanded() && this.state != 6) {
            this.expandHalfwayActionIds.put(i, addAccessibilityActionForState(view, R.string.bottomsheet_action_expand_halfway, 6));
        }
        if (this.hideable && isHideableWhenDragging() && this.state != 5) {
            replaceAccessibilityActionForState(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS, 5);
        }
        int i2 = this.state;
        if (i2 == 3) {
            replaceAccessibilityActionForState(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, shouldSkipHalfExpanded() ? 4 : 6);
            return;
        }
        if (i2 == 4) {
            replaceAccessibilityActionForState(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, shouldSkipHalfExpanded() ? 3 : 6);
        } else {
            if (i2 != 6) {
                return;
            }
            replaceAccessibilityActionForState(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, 4);
            replaceAccessibilityActionForState(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, 3);
        }
    }

    private void updateImportantForAccessibility(boolean z) {
        Map map;
        WeakReference weakReference = this.viewRef;
        if (weakReference == null) {
            return;
        }
        ViewParent parent = ((View) weakReference.get()).getParent();
        if (parent instanceof CoordinatorLayout) {
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) parent;
            int childCount = coordinatorLayout.getChildCount();
            if (z) {
                if (this.importantForAccessibilityMap != null) {
                    return;
                } else {
                    this.importantForAccessibilityMap = new HashMap(childCount);
                }
            }
            for (int i = 0; i < childCount; i++) {
                View childAt = coordinatorLayout.getChildAt(i);
                if (childAt != this.viewRef.get()) {
                    if (z) {
                        this.importantForAccessibilityMap.put(childAt, Integer.valueOf(childAt.getImportantForAccessibility()));
                        if (this.updateImportantForAccessibilityOnSiblings) {
                            ViewCompat.setImportantForAccessibility(childAt, 4);
                        }
                    } else if (this.updateImportantForAccessibilityOnSiblings && (map = this.importantForAccessibilityMap) != null && map.containsKey(childAt)) {
                        ViewCompat.setImportantForAccessibility(childAt, ((Integer) this.importantForAccessibilityMap.get(childAt)).intValue());
                    }
                }
            }
            if (!z) {
                this.importantForAccessibilityMap = null;
            } else if (this.updateImportantForAccessibilityOnSiblings) {
                ((View) this.viewRef.get()).sendAccessibilityEvent(8);
            }
        }
    }

    private void updatePeekHeight(boolean z) {
        View view;
        if (this.viewRef != null) {
            calculateCollapsedOffset();
            if (this.state != 4 || (view = (View) this.viewRef.get()) == null) {
                return;
            }
            if (z) {
                setState(4);
            } else {
                view.requestLayout();
            }
        }
    }

    public void addBottomSheetCallback(BottomSheetCallback bottomSheetCallback) {
        if (this.callbacks.contains(bottomSheetCallback)) {
            return;
        }
        this.callbacks.add(bottomSheetCallback);
    }

    public final void adjustPopupForIme(View view, int i) {
        if (!this.animRunning && i > 0) {
            ViewParent parent = view.getParent();
            if (parent instanceof CoordinatorLayout) {
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) parent;
                Context context = view.getContext();
                int height = coordinatorLayout.getHeight();
                int iDp2px = MiuixUIUtils.dp2px(context, 8.0f);
                int iDp2px2 = MiuixUIUtils.dp2px(context, 24.0f);
                int iDp2px3 = MiuixUIUtils.dp2px(context, 112.0f);
                int i2 = height - i;
                int i3 = (int) (this.mPopupPositionCalculator.getSafeRect(coordinatorLayout).minY + iDp2px2);
                int i4 = i2 - iDp2px;
                int i5 = i4 - i3;
                int iMax = this.mOriginalPopupHeight;
                int i6 = i4 - iMax;
                int i7 = this.mOriginalPopupY;
                if (i6 >= i7) {
                    i3 = i7;
                } else if (i6 >= i3) {
                    i3 = i6;
                } else {
                    iMax = Math.max(i5, iDp2px3);
                }
                if (((int) view.getY()) != i3) {
                    view.setY(i3);
                }
                if (this.mImeAdjustedMaxHeight != iMax) {
                    this.mImeAdjustedMaxHeight = iMax;
                    view.requestLayout();
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void applyWindowInsets(View view, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        if (view != 0) {
            if (view instanceof WindowInsetsController) {
                ((WindowInsetsController) view).applyWindowInsets(z, z2, z3, z4, z5, z6);
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    applyWindowInsets(viewGroup.getChildAt(i), z, z2, z3, z4, z5, z6);
                }
            }
        }
    }

    public final void attachAnchorLayoutListener() {
        View anchorView;
        if (this.mAnchorLayoutListenerAttached || (anchorView = getAnchorView()) == null) {
            return;
        }
        if (this.mAnchorLayoutListener == null) {
            this.mAnchorLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: miuix.bottomsheet.AnchorBottomSheetBehavior$$ExternalSyntheticLambda1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    AnchorBottomSheetBehavior.$r8$lambda$ptW78Iay132wnOR97lGE2VIeVPk(this.f$0);
                }
            };
        }
        this.mAnchorLayoutListenerAttached = true;
        this.mAnchorLayoutListenerView = new WeakReference(anchorView);
        anchorView.getViewTreeObserver().addOnGlobalLayoutListener(this.mAnchorLayoutListener);
    }

    public final int calculateExpandedOffset(int i, int i2) {
        if (i2 == -1) {
            return 0;
        }
        int i3 = i >= this.highExpandedOffsetThreshold ? this.defaultHighExpandedOffset : this.defaultExpandedOffset;
        int i4 = this.insetTop;
        return i4 > i3 ? i4 : i3;
    }

    public final float calculatePopupPivotX(View view) {
        WeakReference weakReference = this.mAnchorView;
        if (weakReference == null || weakReference.get() == null) {
            return view.getWidth() / 2.0f;
        }
        View view2 = (View) this.mAnchorView.get();
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        view2.getLocationInWindow(iArr);
        view.getLocationInWindow(iArr2);
        return Math.max(0.0f, Math.min(view.getWidth(), (iArr[0] + (view2.getWidth() / 2.0f)) - iArr2[0]));
    }

    public final float calculatePopupPivotY(View view) {
        WeakReference weakReference = this.mAnchorView;
        if (weakReference == null || weakReference.get() == null) {
            return view.getHeight() / 2.0f;
        }
        View view2 = (View) this.mAnchorView.get();
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        view2.getLocationInWindow(iArr);
        view.getLocationInWindow(iArr2);
        return Math.max(0.0f, Math.min(view.getHeight(), (iArr[1] + (view2.getHeight() / 2.0f)) - iArr2[1]));
    }

    public final void detachAnchorLayoutListener() {
        if (this.mAnchorLayoutListenerAttached) {
            this.mAnchorLayoutListenerAttached = false;
            WeakReference weakReference = this.mAnchorLayoutListenerView;
            View view = weakReference != null ? (View) weakReference.get() : null;
            if (view != null && this.mAnchorLayoutListener != null) {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this.mAnchorLayoutListener);
            }
            this.mAnchorLayoutListenerView = null;
        }
    }

    public void dispatchOnSlide(int i) {
        View view = (View) this.viewRef.get();
        if (view == null || this.callbacks.isEmpty()) {
            return;
        }
        float fCalculateSlideOffsetWithTop = calculateSlideOffsetWithTop(i);
        for (int i2 = 0; i2 < this.callbacks.size(); i2++) {
            ((BottomSheetCallback) this.callbacks.get(i2)).onSlide(view, fCalculateSlideOffsetWithTop);
        }
    }

    public final void executeAnimatedPopupEnter(View view) {
        AnimState animStateAdd = new AnimState().add(ViewProperty.SCALE_X, 1.0d).add(ViewProperty.SCALE_Y, 1.0d).add(ViewProperty.ALPHA, 1.0d);
        if (this.animInterruptible) {
            resetChildForFirstShowInPopupMode(view);
            this.popupEnterAnimStateStyle = Folme.useAt(view).state().to(animStateAdd, this.popupEnterAnimConfig);
        } else {
            view.setScaleX(0.15f);
            view.setScaleY(0.15f);
            view.setAlpha(0.0f);
            this.popupEnterAnimStateStyle = Folme.useAt(view).state().to(animStateAdd, this.popupEnterAnimConfig);
        }
    }

    public final void executeImmediatePopupEnter(AnimationListener animationListener, View view) {
        this.animRunning = true;
        if (animationListener != null) {
            animationListener.onAnimationStart();
        }
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
        view.setAlpha(1.0f);
        if (animationListener != null) {
            animationListener.onAnimationEnd();
        }
        this.animRunning = false;
    }

    public View findScrollingChild(View view) {
        if (view.getVisibility() != 0) {
            return null;
        }
        if (ViewCompat.isNestedScrollingEnabled(view)) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View viewFindScrollingChild = findScrollingChild(viewGroup.getChildAt(i));
                if (viewFindScrollingChild != null) {
                    return viewFindScrollingChild;
                }
            }
        }
        return null;
    }

    public final int fixStateInPopupMode(int i) {
        if (this.mode == 1 && (i == 4 || i == 6)) {
            return 3;
        }
        return i;
    }

    public View getAnchorView() {
        WeakReference weakReference = this.mAnchorView;
        if (weakReference != null) {
            return (View) weakReference.get();
        }
        return null;
    }

    public final View getChildView() {
        WeakReference weakReference = this.viewRef;
        if (weakReference == null) {
            return null;
        }
        return (View) weakReference.get();
    }

    public int getExpandedOffset() {
        return this.fitToContentsOffset;
    }

    public final float getFullHeightRatio(int i) {
        if (i <= this.fullHeightLowRatioThreshold) {
            return this.fullHeightLowRatio;
        }
        return i <= this.fullHeightMiddleRatioThreshold ? this.fullHeightMiddleRatio : this.fullHeightHighRatio;
    }

    public final int getMaxHeight(int i) {
        int i2 = this.expandedOffset;
        return i2 == -1 ? i - calculateExpandedOffset(i, this.insetTop) : i - i2;
    }

    public final View getParentView() {
        WeakReference weakReference = this.parentViewRef;
        if (weakReference == null) {
            return null;
        }
        return (View) weakReference.get();
    }

    public final void handleTransformExitAnim(final AnimationListener animationListener, View view) {
        this.animRunning = true;
        view.setVisibility(0);
        if (animationListener != null) {
            animationListener.onAnimationStart();
        }
        if (this.mTransformAnim == null) {
            if (animationListener != null) {
                animationListener.onAnimationEnd();
            }
            this.mTransformAnim = null;
            this.animRunning = false;
            this.dismissed = true;
            return;
        }
        WeakReference weakReference = this.viewRef;
        if (weakReference != null) {
            View view2 = (View) weakReference.get();
            if (view2 instanceof AnchorBottomSheetView) {
                ((AnchorBottomSheetView) view2).setInterceptTouchEvent(true);
            }
        }
        this.mTransformAnim.addPopupAnimationListener(new PopupAnimationListener() { // from class: miuix.bottomsheet.AnchorBottomSheetBehavior.7
            @Override // miuix.popupwidget.widget.PopupAnimationListener, miuix.popupwidget.widget.IPopupAnimationListener
            public void onReverseAnimationEnd() {
                AnchorBottomSheetBehavior.this.mTransformAnim.removePopupAnimationListener(this);
                AnimationListener animationListener2 = animationListener;
                if (animationListener2 != null) {
                    animationListener2.onAnimationEnd();
                }
                AnchorBottomSheetBehavior.this.mTransformAnim = null;
                AnchorBottomSheetBehavior.this.animRunning = false;
                AnchorBottomSheetBehavior.this.dismissed = true;
            }
        });
        this.mTransformAnim.updateAnimationOptions();
        this.mTransformAnim.dismissWithAnim();
    }

    public final boolean hasValidAnchor() {
        WeakReference weakReference = this.mAnchorView;
        return (weakReference == null || weakReference.get() == null) ? false : true;
    }

    public boolean isGestureInsetBottomIgnored() {
        return this.gestureInsetBottomIgnored;
    }

    public boolean isHideable() {
        return this.hideable;
    }

    public boolean isHideableWhenDragging() {
        return true;
    }

    public final boolean isInternalDraggable() {
        return this.draggable && this.internalDraggable;
    }

    public boolean isNestedScrollingCheckEnabled() {
        return true;
    }

    public final boolean layoutPopupMode(CoordinatorLayout coordinatorLayout, View view) {
        View view2;
        PopupPositionCalculator.PopupPosition popupPositionCalculate;
        WeakReference weakReference = this.mAnchorView;
        if (weakReference == null || weakReference.get() == null) {
            Log.w("AnchorBottomSheetBehavior", "No valid anchor for Popup mode, cannot layout");
            return false;
        }
        View view3 = (View) this.mAnchorView.get();
        if (view3 instanceof ITransformableView) {
            view2 = view;
            popupPositionCalculate = this.mPopupPositionCalculator.calculateTransform(view3, view2, coordinatorLayout, 392, 24, 8);
        } else {
            view2 = view;
            popupPositionCalculate = this.mPopupPositionCalculator.calculate(view3, view2, coordinatorLayout, 392, 24, 8);
        }
        view2.setX(popupPositionCalculate.x);
        if (this.mPopupAdjustedForIme) {
            return true;
        }
        view2.setY(popupPositionCalculate.y);
        return true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
        super.onAttachedToLayoutParams(layoutParams);
        this.viewRef = null;
        this.viewDragHelper = null;
        this.parentViewRef = null;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onDetachedFromLayoutParams() {
        super.onDetachedFromLayoutParams();
        this.viewRef = null;
        this.viewDragHelper = null;
        this.parentViewRef = null;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        int i;
        ViewDragHelper viewDragHelper;
        if (!view.isShown() || !isInternalDraggable()) {
            this.ignoreEvents = true;
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            reset();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(motionEvent);
        if (actionMasked == 0) {
            int x = (int) motionEvent.getX();
            this.initialY = (int) motionEvent.getY();
            if (this.state != 2) {
                WeakReference weakReference = this.nestedScrollingChildRef;
                View view2 = weakReference != null ? (View) weakReference.get() : null;
                if (view2 != null && coordinatorLayout.isPointInChildBounds(view2, x, this.initialY)) {
                    this.activePointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
                    this.touchingScrollingChild = true;
                }
            }
            this.ignoreEvents = this.activePointerId == -1 && !coordinatorLayout.isPointInChildBounds(view, x, this.initialY);
        } else if (actionMasked == 1 || actionMasked == 3) {
            this.touchingScrollingChild = false;
            this.activePointerId = -1;
            if (this.ignoreEvents) {
                this.ignoreEvents = false;
                return false;
            }
        }
        if (!this.ignoreEvents && (viewDragHelper = this.viewDragHelper) != null && viewDragHelper.shouldInterceptTouchEvent(motionEvent)) {
            return true;
        }
        WeakReference weakReference2 = this.nestedScrollingChildRef;
        View view3 = weakReference2 != null ? (View) weakReference2.get() : null;
        return (actionMasked != 2 || view3 == null || this.ignoreEvents || this.state == 1 || coordinatorLayout.isPointInChildBounds(view3, (int) motionEvent.getX(), (int) motionEvent.getY()) || this.viewDragHelper == null || (i = this.initialY) == -1 || Math.abs(((float) i) - motionEvent.getY()) <= ((float) this.viewDragHelper.getTouchSlop())) ? false : true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
        int i2;
        ExtraPaddingPolicy extraPaddingPolicy;
        int i3;
        if (ViewCompat.getFitsSystemWindows(coordinatorLayout) && !ViewCompat.getFitsSystemWindows(view)) {
            view.setFitsSystemWindows(true);
        }
        if (this.viewRef == null) {
            this.peekHeightMin = coordinatorLayout.getResources().getDimensionPixelSize(R.dimen.miuix_bottom_sheet_peek_height_min);
            setWindowInsetsListener(view);
            this.viewRef = new WeakReference(view);
            ColorStateList colorStateList = this.backgroundTint;
            if (colorStateList != null) {
                ViewCompat.setBackgroundTintList(view, colorStateList);
            }
            updateAccessibilityActions();
            if (ViewCompat.getImportantForAccessibility(view) == 0) {
                ViewCompat.setImportantForAccessibility(view, 1);
            }
        }
        if (this.parentViewRef == null) {
            this.parentViewRef = new WeakReference(coordinatorLayout);
        }
        if (this.viewDragHelper == null) {
            this.viewDragHelper = ViewDragHelper.create(coordinatorLayout, this.dragCallback);
        }
        int top = view.getTop();
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
        int extraPaddingDp = 0;
        if (coordinatorLayout.getMeasuredWidth() - (Math.max(((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin) * 2) > view.getMeasuredWidth()) {
            int measuredWidth = (coordinatorLayout.getMeasuredWidth() - view.getMeasuredWidth()) / 2;
            view.layout(measuredWidth, 0, view.getMeasuredWidth() + measuredWidth, view.getMeasuredHeight());
        } else {
            coordinatorLayout.onLayoutChild(view, i);
        }
        if ((this.parentWidth != coordinatorLayout.getWidth() || this.parentHeight != coordinatorLayout.getHeight()) && this.dismissed) {
            view.setTranslationY(this.mode == 1 ? (int) ((coordinatorLayout.getHeight() / 2.0f) + (view.getHeight() / 2.0f)) : coordinatorLayout.getHeight());
        }
        this.parentWidth = coordinatorLayout.getWidth();
        this.parentHeight = coordinatorLayout.getHeight();
        if (this.mode == 1) {
            this.childHeight = view.getHeight();
            if (!layoutPopupMode(coordinatorLayout, view)) {
                Log.w("AnchorBottomSheetBehavior", "Popup mode layout failed due to invalid anchor");
            }
            if (!this.dismissed) {
                attachAnchorLayoutListener();
            }
            if (!this.mPopupAdjustedForIme) {
                this.mOriginalPopupY = (int) view.getY();
                this.mOriginalPopupHeight = view.getHeight();
            }
            setInternalDraggable(false);
            setStateInternal(fixStateInPopupMode(this.state));
        } else {
            detachAnchorLayoutListener();
            this.childHeight = view.getHeight() - this.extraHeight;
            setInternalDraggable(true);
            this.fitToContentsOffset = Math.max(0, this.parentHeight - this.childHeight);
            if (view.getX() != view.getLeft()) {
                view.setX(view.getLeft());
            }
            if ((!this.dismissed || ((i2 = this.lastMode) != this.mode && i2 == 1)) && !this.animRunning && view.getY() != view.getTop()) {
                view.setY(view.getTop());
            }
        }
        int i4 = this.parentHeight;
        int i5 = i4 - this.childHeight;
        int i6 = this.insetTop;
        if (i5 < i6) {
            this.childHeight = i4 - i6;
        }
        calculateHalfExpandedOffset();
        calculateCollapsedOffset();
        if (this.mode == 0) {
            int i7 = this.state;
            if (i7 == 3) {
                ViewCompat.offsetTopAndBottom(view, getExpandedOffset());
                this.stableStateChildTop = view.getTop();
            } else if (i7 == 6) {
                ViewCompat.offsetTopAndBottom(view, this.halfExpandedOffset);
                this.stableStateChildTop = view.getTop();
            } else if (this.hideable && i7 == 5) {
                ViewCompat.offsetTopAndBottom(view, this.parentHeight);
            } else if (i7 == 4) {
                ViewCompat.offsetTopAndBottom(view, this.collapsedOffset);
                this.stableStateChildTop = view.getTop();
            } else if (i7 == 1 || i7 == 2) {
                ViewCompat.offsetTopAndBottom(view, top - view.getTop());
            }
        }
        updateChildY(view);
        this.nestedScrollingChildRef = new WeakReference(findScrollingChild(view));
        for (int i8 = 0; i8 < this.callbacks.size(); i8++) {
            ((BottomSheetCallback) this.callbacks.get(i8)).onLayout(view);
        }
        OnModeChangeListener onModeChangeListener = this.onModeChangeListener;
        if (onModeChangeListener != null && (i3 = this.mode) != this.lastMode) {
            onModeChangeListener.onModeChange(i3, view);
        }
        this.lastMode = this.mode;
        if (this.onExtraPaddingListener != null && this.extraPaddingPolicy == null) {
            ExtraPaddingPolicy extraPaddingPolicyCreateDefault = ExtraPaddingPolicy.Builder.createDefault(this.mDeviceType, ContainerToken.PADDING_BASE_DP, ContainerToken.PADDING_HORIZONTAL_COMMON);
            this.extraPaddingPolicy = extraPaddingPolicyCreateDefault;
            if (extraPaddingPolicyCreateDefault != null) {
                extraPaddingPolicyCreateDefault.setEnable(this.extraPaddingEnabled);
            }
        }
        if (this.onExtraPaddingListener != null && (extraPaddingPolicy = this.extraPaddingPolicy) != null) {
            extraPaddingPolicy.setEnable(this.extraPaddingEnabled);
            if (this.extraPaddingPolicy.isEnable()) {
                Context context = view.getContext();
                Point point = EnvStateManager.getWindowInfo(context).windowSizeDp;
                float f = context.getResources().getDisplayMetrics().density;
                this.extraPaddingPolicy.onContainerSizeChanged(point.x, point.y, view.getWidth(), view.getHeight(), f, this.mode == 1);
                extraPaddingDp = (int) (this.extraPaddingPolicy.getExtraPaddingDp() * f);
            }
            if (extraPaddingDp != this.horizontalExtraPadding) {
                this.horizontalExtraPadding = extraPaddingDp;
                this.onExtraPaddingListener.onExtraPaddingChanged(extraPaddingDp);
            }
        }
        return true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3, int i4) {
        CoordinatorLayout coordinatorLayout2;
        boolean z = false;
        this.fullHeightMode = false;
        this.extraHeight = 0;
        int size = View.MeasureSpec.getSize(i3);
        View.MeasureSpec.getSize(i);
        if (View.MeasureSpec.getMode(i3) != 1073741824) {
            StringBuilder sb = new StringBuilder();
            coordinatorLayout2 = coordinatorLayout;
            sb.append(coordinatorLayout2);
            sb.append(" measure spec mode is not MeasureSpec.EXACTLY! Usually layout_height should be match_parent.");
            Log.w("AnchorBottomSheetBehavior", sb.toString());
        } else {
            coordinatorLayout2 = coordinatorLayout;
        }
        Context context = coordinatorLayout2.getContext();
        float f = context.getResources().getDisplayMetrics().density;
        if (f != this.density) {
            this.density = f;
            updateSizeConfig(context, this.attrs);
        }
        int i5 = this.modeConfig;
        if (i5 == 0) {
            if (supportPopupMode(context) && hasValidAnchor()) {
                this.mode = 1;
            } else {
                this.mode = 0;
            }
        } else if (i5 == 1) {
            this.mode = 0;
        } else {
            if (i5 != 2) {
                throw new IllegalStateException("Unexpected mode config: " + this.modeConfig);
            }
            if (hasValidAnchor()) {
                this.mode = 1;
            } else {
                Log.w("AnchorBottomSheetBehavior", "Popup mode requested but no anchor set, falling back to Bottom mode");
                this.mode = 0;
            }
        }
        boolean zIsPortrait = WindowUtils.isPortrait(view.getContext());
        if (this.mode == 1) {
            if (view instanceof AnchorBottomSheetView) {
                AnchorBottomSheetView anchorBottomSheetView = (AnchorBottomSheetView) view;
                applyWindowInsets(anchorBottomSheetView, true, false, true, true, true, true);
                anchorBottomSheetView.setBottomSheetMode(this.mode);
                anchorBottomSheetView.hideDragHandleView();
                anchorBottomSheetView.setExtraHeightEnabled(false);
            }
            int iDp2px = MiuixUIUtils.dp2px(context, 392.0f);
            int iDp2px2 = MiuixUIUtils.dp2px(context, 112.0f);
            int iDp2px3 = zIsPortrait ? MiuixUIUtils.dp2px(context, 636.0f) : (int) (EnvStateManager.getWindowInfo(context).windowSize.y * 0.8f);
            int i6 = this.mImeAdjustedMaxHeight;
            if (i6 > 0) {
                iDp2px3 = Math.min(iDp2px3, i6);
            }
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(iDp2px, 1073741824);
            view.measure(iMakeMeasureSpec, View.MeasureSpec.makeMeasureSpec(iDp2px3, Integer.MIN_VALUE));
            if (view.getMeasuredHeight() < iDp2px2) {
                view.measure(iMakeMeasureSpec, View.MeasureSpec.makeMeasureSpec(iDp2px2, 1073741824));
            }
        } else {
            TransformAnimation transformAnimation = this.mTransformAnim;
            if (transformAnimation != null) {
                transformAnimation.resetFromContentView();
            }
            boolean z2 = view instanceof AnchorBottomSheetView;
            if (z2) {
                AnchorBottomSheetView anchorBottomSheetView2 = (AnchorBottomSheetView) view;
                applyWindowInsets(anchorBottomSheetView2, false, true, true, true, true, this.paddingBottomSystemWindowInsets);
                anchorBottomSheetView2.setBottomSheetMode(this.mode);
                if (anchorBottomSheetView2.isDragHandleViewEnabled()) {
                    anchorBottomSheetView2.showDragHandleView();
                } else {
                    anchorBottomSheetView2.hideDragHandleView();
                }
                anchorBottomSheetView2.setExtraHeightEnabled(true);
            }
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            int childMeasureSpec = getChildMeasureSpec(i, coordinatorLayout2.getPaddingLeft() + coordinatorLayout2.getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, this.maxWidth, marginLayoutParams.width);
            int childMeasureSpec2 = getChildMeasureSpec(i3, coordinatorLayout2.getPaddingTop() + coordinatorLayout2.getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, this.maxHeight, marginLayoutParams.height);
            view.measure(childMeasureSpec, childMeasureSpec2);
            if (z2) {
                this.extraHeight = ((AnchorBottomSheetView) view).getExtraHeight();
            }
            boolean z3 = this.fixedHeightRatioEnabled && size > this.fullHeightLowRatioThreshold;
            this.internalFixedHeightRatioEnabled = z3;
            if (z3) {
                childMeasureSpec2 = View.MeasureSpec.makeMeasureSpec((int) ((size * this.fixedHeightRatio) + this.extraHeight), 1073741824);
                view.measure(childMeasureSpec, childMeasureSpec2);
            }
            int measuredHeight = view.getMeasuredHeight() - this.extraHeight;
            int measuredWidth = view.getMeasuredWidth();
            if (this.forceFullHeight || measuredHeight > size * getFullHeightRatio(size)) {
                this.fullHeightMode = true;
            }
            int i7 = this.bottomModeMaxWidth;
            if (this.bottomModeLandscapeWidthRuleEnabled && !zIsPortrait) {
                i7 = this.bottomModeLandscapeWidth;
            }
            if (measuredWidth > i7) {
                childMeasureSpec = View.MeasureSpec.makeMeasureSpec(i7, 1073741824);
                z = true;
            }
            this.insetTopInMeasureStep = this.insetTop;
            if (this.mRequestLayoutRunnable == null) {
                this.mRequestLayoutRunnable = new RequestLayoutRunnable(view);
            }
            this.mRequestLayoutRunnable.mInsetTopInMeasuredStep = this.insetTopInMeasureStep;
            if (this.fullHeightMode) {
                view.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(getMaxHeight(size) + this.extraHeight, 1073741824));
            } else {
                int i8 = this.minHeight;
                if (measuredHeight <= i8) {
                    view.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(i8 + this.extraHeight, 1073741824));
                } else if (z) {
                    view.measure(childMeasureSpec, childMeasureSpec2);
                }
            }
        }
        return true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View view, View view2, float f, float f2) {
        WeakReference weakReference;
        return isNestedScrollingCheckEnabled() && (weakReference = this.nestedScrollingChildRef) != null && view2 == weakReference.get() && (this.state != 3 || super.onNestedPreFling(coordinatorLayout, view, view2, f, f2));
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int[] iArr, int i3) {
        if (i3 == 1) {
            return;
        }
        WeakReference weakReference = this.nestedScrollingChildRef;
        View view3 = weakReference != null ? (View) weakReference.get() : null;
        if (!isNestedScrollingCheckEnabled() || view2 == view3) {
            int top = view.getTop();
            int i4 = top - i2;
            if (i2 > 0) {
                if (i4 < getExpandedOffset()) {
                    int expandedOffset = top - getExpandedOffset();
                    iArr[1] = expandedOffset;
                    ViewCompat.offsetTopAndBottom(view, -expandedOffset);
                    setStateInternal(3);
                } else {
                    if (!isInternalDraggable()) {
                        return;
                    }
                    iArr[1] = i2;
                    ViewCompat.offsetTopAndBottom(view, -i2);
                    setStateInternal(1);
                }
            } else if (i2 < 0 && !view2.canScrollVertically(-1)) {
                if (i4 > this.collapsedOffset && !canBeHiddenByDragging()) {
                    int i5 = top - this.collapsedOffset;
                    iArr[1] = i5;
                    ViewCompat.offsetTopAndBottom(view, -i5);
                    setStateInternal(4);
                } else {
                    if (!isInternalDraggable()) {
                        return;
                    }
                    iArr[1] = i2;
                    ViewCompat.offsetTopAndBottom(view, -i2);
                    setStateInternal(1);
                }
            }
            dispatchOnSlide(view.getTop());
            this.lastNestedScrollDy = i2;
            this.nestedScrolled = true;
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int i3, int i4, int i5, int[] iArr) {
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, View view, Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(coordinatorLayout, view, savedState.getSuperState());
        restoreOptionalState(savedState);
        int i = savedState.state;
        if (i == 1 || i == 2) {
            this.state = 4;
            this.lastStableState = 4;
        } else {
            this.state = i;
            this.lastStableState = i;
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, View view) {
        return new SavedState(super.onSaveInstanceState(coordinatorLayout, view), this);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i, int i2) {
        this.lastNestedScrollDy = 0;
        this.nestedScrolled = false;
        return (this.animRunning || this.mode == 1 || (i & 2) == 0) ? false : true;
    }

    /* JADX WARN: Code duplicated, block: B:54:0x00b3  */
    /* JADX WARN: Code duplicated, block: B:57:0x00ca  */
    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i) {
        WeakReference weakReference;
        int i2 = 3;
        if (view.getTop() == getExpandedOffset()) {
            setStateInternal(3);
            return;
        }
        if (!isNestedScrollingCheckEnabled() || ((weakReference = this.nestedScrollingChildRef) != null && view2 == weakReference.get() && this.nestedScrolled)) {
            if (this.lastNestedScrollDy > 0) {
                if (!shouldSkipHalfExpanded() && view.getTop() > this.halfExpandedOffset) {
                    i2 = 6;
                }
            } else if (this.hideable && shouldHide(view, getYVelocity())) {
                i2 = (view.getTop() - this.stableStateChildTop > this.significantDistanceThreshold || getYVelocity() > ((float) this.significantVelocityThreshold)) ? 5 : this.lastStableState;
            } else if (this.lastNestedScrollDy == 0) {
                int top = view.getTop();
                if (!shouldSkipHalfExpanded()) {
                    int i3 = this.halfExpandedOffset;
                    if (top < i3) {
                        if (top >= Math.abs(top - this.collapsedOffset)) {
                            if (shouldSkipHalfExpandedStateWhenDragging()) {
                                i2 = 4;
                            } else {
                                i2 = 6;
                            }
                        }
                    } else if (Math.abs(top - i3) < Math.abs(top - this.collapsedOffset)) {
                        i2 = 6;
                    } else {
                        i2 = 4;
                    }
                } else if (Math.abs(top - this.fitToContentsOffset) >= Math.abs(top - this.collapsedOffset)) {
                    i2 = 4;
                }
            } else {
                if (!shouldSkipHalfExpanded()) {
                    int top2 = view.getTop();
                    if (Math.abs(top2 - this.halfExpandedOffset) < Math.abs(top2 - this.collapsedOffset)) {
                        i2 = 6;
                    }
                }
                i2 = 4;
            }
            startSettling(view, i2, false);
            this.nestedScrolled = false;
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        if (!view.isShown()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (this.state == 1 && actionMasked == 0) {
            return true;
        }
        if (shouldHandleDraggingWithHelper()) {
            this.viewDragHelper.processTouchEvent(motionEvent);
        }
        if (actionMasked == 0) {
            reset();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(motionEvent);
        if (shouldHandleDraggingWithHelper() && actionMasked == 2 && !this.ignoreEvents && Math.abs(this.initialY - motionEvent.getY()) > this.viewDragHelper.getTouchSlop()) {
            this.viewDragHelper.captureChildView(view, motionEvent.getPointerId(motionEvent.getActionIndex()));
        }
        return !this.ignoreEvents;
    }

    public final void resetChildForFirstShow(View view, int i) {
        if (this.shouldResetChildBeforeFirstShow && view.getTranslationY() == 0.0f) {
            view.setTranslationY(i);
            this.shouldResetChildBeforeFirstShow = false;
        }
    }

    public final void resetChildForFirstShowInPopupMode(View view) {
        if (this.shouldResetChildBeforeFirstShow) {
            view.setScaleX(0.15f);
            view.setScaleY(0.15f);
            view.setAlpha(0.0f);
            this.shouldResetChildBeforeFirstShow = false;
        }
    }

    public final void restoreOptionalState(SavedState savedState) {
        int i = this.saveFlags;
        if (i == 0) {
            return;
        }
        if (i == -1 || (i & 1) == 1) {
            this.peekHeight = savedState.peekHeight;
        }
        if (i == -1 || (i & 4) == 4) {
            this.hideable = savedState.hideable;
        }
        if (i == -1 || (i & 8) == 8) {
            this.skipCollapsed = savedState.skipCollapsed;
        }
    }

    public final void restorePopupOriginalState(View view) {
        if (!this.animRunning && this.mPopupAdjustedForIme) {
            view.setY(this.mOriginalPopupY);
            this.mImeAdjustedMaxHeight = -1;
            this.mPopupAdjustedForIme = false;
            this.mOriginalPopupY = 0;
            this.mOriginalPopupHeight = 0;
            view.requestLayout();
        }
    }

    public void setAnimationInterruptible(boolean z) {
        this.animInterruptible = z;
    }

    public void setBottomModeLandscapeMaxWidth(int i) {
        this.bottomModeLandscapeWidth = i;
    }

    public void setBottomModeLandscapeWidthRuleEnabled(boolean z) {
        this.bottomModeLandscapeWidthRuleEnabled = z;
    }

    public void setBottomModeMaxWidth(int i) {
        this.bottomModeMaxWidth = i;
    }

    public void setDraggable(boolean z) {
        this.draggable = z;
    }

    public void setExpandedOffset(int i) {
        if (i < 0 && i != -1) {
            throw new IllegalArgumentException("offset must be greater than or equal to 0");
        }
        this.expandedOffset = i;
    }

    public void setForceFullHeight(boolean z) {
        this.forceFullHeight = z;
    }

    public void setGestureInsetBottomIgnored(boolean z) {
        this.gestureInsetBottomIgnored = z;
    }

    public void setHalfExpandedRatio(float f) {
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("ratio must be a float value between 0 and 1");
        }
        this.halfExpandedRatio = f;
        if (this.viewRef != null) {
            calculateHalfExpandedOffset();
        }
    }

    public void setHideable(boolean z) {
        if (this.hideable != z) {
            this.hideable = z;
            if (!z && this.state == 5) {
                setState(4);
            }
            updateAccessibilityActions();
        }
    }

    public final void setInternalDraggable(boolean z) {
        this.internalDraggable = z;
    }

    public void setMaxHeight(int i) {
        this.maxHeight = i;
    }

    public void setMaxWidth(int i) {
        this.maxWidth = i;
    }

    public void setOnModeChangeListener(OnModeChangeListener onModeChangeListener) {
        this.onModeChangeListener = onModeChangeListener;
    }

    public void setOriginalWindowInsetsEnabled(boolean z) {
        this.originalWindowInsetsEnabled = z;
    }

    public void setPeekHeight(int i) {
        setPeekHeight(i, false);
    }

    public final void setPeekHeight(int i, boolean z) {
        if (i == -1) {
            if (this.peekHeightAuto) {
                return;
            } else {
                this.peekHeightAuto = true;
            }
        } else {
            if (!this.peekHeightAuto && this.peekHeight == i) {
                return;
            }
            this.peekHeightAuto = false;
            this.peekHeight = Math.max(0, i);
        }
        updatePeekHeight(z);
    }

    public void setReleaseAnimListener(ReleaseAnimListener releaseAnimListener) {
        this.releaseAnimListener = releaseAnimListener;
    }

    public void setSaveFlags(int i) {
        this.saveFlags = i;
    }

    public void setSignificantDistanceThreshold(int i) {
        this.significantDistanceThreshold = i;
    }

    public void setSignificantVelocityThreshold(int i) {
        this.significantVelocityThreshold = i;
    }

    public void setSkipCollapsed(boolean z) {
        this.skipCollapsed = z;
    }

    public void setState(int i) {
        if (i == 1 || i == 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("STATE_");
            sb.append(i == 1 ? "DRAGGING" : "SETTLING");
            sb.append(" should not be set externally.");
            throw new IllegalArgumentException(sb.toString());
        }
        if (!this.hideable && i == 5) {
            Log.w("AnchorBottomSheetBehavior", "Cannot set state: " + i);
            return;
        }
        int iFixStateInPopupMode = fixStateInPopupMode(i);
        final int i2 = (iFixStateInPopupMode == 6 && shouldSkipHalfExpanded() && getTopOffsetForState(iFixStateInPopupMode) <= this.fitToContentsOffset) ? 3 : iFixStateInPopupMode;
        WeakReference weakReference = this.viewRef;
        if (weakReference == null || weakReference.get() == null) {
            setStateInternal(iFixStateInPopupMode);
            return;
        }
        final View view = (View) this.viewRef.get();
        if (view.isLaidOut()) {
            runAfterLayout(view, new Runnable() { // from class: miuix.bottomsheet.AnchorBottomSheetBehavior$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.startSettling(view, i2, false);
                }
            });
        } else {
            setStateInternal(iFixStateInPopupMode);
        }
    }

    public void setStateInternal(int i) {
        View view;
        if (this.state == i) {
            return;
        }
        this.state = i;
        if (i == 4 || i == 3 || i == 6 || (this.hideable && i == 5)) {
            this.lastStableState = i;
        }
        WeakReference weakReference = this.viewRef;
        if (weakReference == null || (view = (View) weakReference.get()) == null) {
            return;
        }
        if (i == 3) {
            updateImportantForAccessibility(true);
        } else if (i == 6 || i == 5 || i == 4) {
            updateImportantForAccessibility(false);
        }
        for (int i2 = 0; i2 < this.callbacks.size(); i2++) {
            ((BottomSheetCallback) this.callbacks.get(i2)).onStateChanged(view, i);
        }
        updateAccessibilityActions();
    }

    public final void setupImeAnimationCallback(final View view) {
        if (this.mImeAnimationCallback != null) {
            return;
        }
        WindowInsetsAnimation.Callback callback = new WindowInsetsAnimation.Callback(0) { // from class: miuix.bottomsheet.AnchorBottomSheetBehavior.11
            @Override // android.view.WindowInsetsAnimation.Callback
            public void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
                WindowInsets rootWindowInsets;
                super.onEnd(windowInsetsAnimation);
                if (AnchorBottomSheetBehavior.this.mode == 1 && (windowInsetsAnimation.getTypeMask() & WindowInsets.Type.ime()) != 0 && (rootWindowInsets = view.getRootWindowInsets()) != null && rootWindowInsets.getInsets(WindowInsets.Type.ime()).bottom == 0 && AnchorBottomSheetBehavior.this.mPopupAdjustedForIme) {
                    AnchorBottomSheetBehavior.this.restorePopupOriginalState(view);
                }
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public void onPrepare(WindowInsetsAnimation windowInsetsAnimation) {
                super.onPrepare(windowInsetsAnimation);
                if (AnchorBottomSheetBehavior.this.mode != 1 || (windowInsetsAnimation.getTypeMask() & WindowInsets.Type.ime()) == 0 || AnchorBottomSheetBehavior.this.mPopupAdjustedForIme) {
                    return;
                }
                AnchorBottomSheetBehavior.this.mPopupAdjustedForIme = true;
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public WindowInsets onProgress(WindowInsets windowInsets, List list) {
                if (AnchorBottomSheetBehavior.this.mode != 1) {
                    return windowInsets;
                }
                AnchorBottomSheetBehavior.this.adjustPopupForIme(view, windowInsets.getInsets(WindowInsets.Type.ime()).bottom);
                return windowInsets;
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public WindowInsetsAnimation.Bounds onStart(WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds) {
                return bounds;
            }
        };
        this.mImeAnimationCallback = callback;
        view.setWindowInsetsAnimationCallback(callback);
    }

    public final void setupPopupEnterAnimConfig(final AnimationListener animationListener) {
        if (this.popupEnterAnimConfig == null) {
            AnimConfig ease = new AnimConfig().setEase(FolmeEase.spring(0.8f, 0.4f));
            this.popupEnterAnimConfig = ease;
            ease.setSpecial(ViewProperty.ALPHA, EaseManager.getStyle(1, 200.0f), new float[0]);
        }
        TransitionListener transitionListener = this.popupEnterAnimTransitionListener;
        if (transitionListener != null) {
            this.popupEnterAnimConfig.removeListeners(transitionListener);
        }
        TransitionListener transitionListener2 = new TransitionListener() { // from class: miuix.bottomsheet.AnchorBottomSheetBehavior.4
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                AnchorBottomSheetBehavior.this.dismissed = false;
                AnchorBottomSheetBehavior.this.animRunning = true;
                View parentView = AnchorBottomSheetBehavior.this.getParentView();
                if (parentView != null) {
                    parentView.setVisibility(0);
                }
                View childView = AnchorBottomSheetBehavior.this.getChildView();
                if (AnchorBottomSheetBehavior.this.improveAnimPerformance && childView != null && childView.isHardwareAccelerated()) {
                    childView.setLayerType(2, null);
                }
                AnimationListener animationListener2 = animationListener;
                if (animationListener2 != null) {
                    animationListener2.onAnimationStart();
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                View childView = AnchorBottomSheetBehavior.this.getChildView();
                if (AnchorBottomSheetBehavior.this.improveAnimPerformance && childView != null && childView.isHardwareAccelerated()) {
                    childView.setLayerType(0, null);
                }
                AnchorBottomSheetBehavior.this.animRunning = false;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                View childView = AnchorBottomSheetBehavior.this.getChildView();
                if (childView != null) {
                    Folme.clean(childView);
                }
                if (AnchorBottomSheetBehavior.this.improveAnimPerformance && childView != null && childView.isHardwareAccelerated()) {
                    childView.setLayerType(0, null);
                }
                AnimationListener animationListener2 = animationListener;
                if (animationListener2 != null) {
                    animationListener2.onAnimationEnd();
                }
                AnchorBottomSheetBehavior.this.animRunning = false;
            }
        };
        this.popupEnterAnimTransitionListener = transitionListener2;
        this.popupEnterAnimConfig.addListeners(transitionListener2);
    }

    public final void setupPopupPivotPoint(View view) {
        float fCalculatePopupPivotX = calculatePopupPivotX(view);
        float fCalculatePopupPivotY = calculatePopupPivotY(view);
        view.setPivotX(fCalculatePopupPivotX);
        view.setPivotY(fCalculatePopupPivotY);
    }

    public final boolean shouldBottomExitAnimEnd() {
        WeakReference weakReference = this.parentViewRef;
        if (weakReference != null && this.viewRef != null) {
            View view = (View) weakReference.get();
            View view2 = (View) this.viewRef.get();
            return view == null || view2 == null || view2.getTranslationY() + ((float) view2.getTop()) > ((float) (view.getHeight() + (-10)));
        }
        return true;
    }

    public boolean shouldExpandOnUpwardDrag(long j, float f) {
        return false;
    }

    public boolean shouldHide(View view, float f) {
        if (this.skipCollapsed) {
            return true;
        }
        if (isHideableWhenDragging() && view.getTop() >= this.collapsedOffset) {
            return Math.abs((((float) view.getTop()) + (f * this.hideFriction)) - ((float) this.collapsedOffset)) / ((float) calculatePeekHeight()) > 0.5f;
        }
        return false;
    }

    public boolean shouldSkipHalfExpanded() {
        if (this.skipHalfExpanded) {
            return true;
        }
        return !this.fullHeightMode;
    }

    public boolean shouldSkipHalfExpandedStateWhenDragging() {
        return false;
    }

    public boolean shouldSkipSmoothAnimation() {
        return true;
    }

    public final boolean shouldUseTransformAnimation(View view) {
        return view instanceof ITransformableView;
    }

    public final void startBottomEnterAnim(final AnimationListener animationListener, View view, final View view2, boolean z) {
        if (this.bottomEnterAnimConfig == null) {
            this.bottomEnterAnimConfig = new AnimConfig().setEase(FolmeEase.spring(0.9f, 0.38f));
        }
        TransitionListener transitionListener = this.bottomEnterAnimTransitionListener;
        if (transitionListener != null) {
            this.bottomEnterAnimConfig.removeListeners(transitionListener);
        }
        TransitionListener transitionListener2 = new TransitionListener() { // from class: miuix.bottomsheet.AnchorBottomSheetBehavior.1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                AnchorBottomSheetBehavior.this.dismissed = false;
                AnchorBottomSheetBehavior.this.animRunning = true;
                View parentView = AnchorBottomSheetBehavior.this.getParentView();
                if (parentView != null) {
                    parentView.setVisibility(0);
                }
                AnimationListener animationListener2 = animationListener;
                if (animationListener2 != null) {
                    animationListener2.onAnimationStart();
                }
                View childView = AnchorBottomSheetBehavior.this.getChildView();
                if (AnchorBottomSheetBehavior.this.improveAnimPerformance && childView != null && childView.isHardwareAccelerated()) {
                    childView.setLayerType(2, null);
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                View view3;
                if (AnchorBottomSheetBehavior.this.improveAnimPerformance && (view3 = view2) != null && view3.isHardwareAccelerated()) {
                    view2.setLayerType(0, null);
                }
                AnchorBottomSheetBehavior.this.animRunning = false;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                View childView = AnchorBottomSheetBehavior.this.getChildView();
                if (childView != null) {
                    Folme.clean(childView);
                }
                if (AnchorBottomSheetBehavior.this.improveAnimPerformance && childView != null && childView.isHardwareAccelerated()) {
                    childView.setLayerType(0, null);
                }
                AnimationListener animationListener2 = animationListener;
                if (animationListener2 != null) {
                    animationListener2.onAnimationEnd();
                }
                AnchorBottomSheetBehavior.this.animRunning = false;
            }
        };
        this.bottomEnterAnimTransitionListener = transitionListener2;
        this.bottomEnterAnimConfig.addListeners(transitionListener2);
        AnimState animState = new AnimState();
        ViewProperty viewProperty = ViewProperty.TRANSLATION_Y;
        AnimState animStateAdd = animState.add(viewProperty, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
        if (!z) {
            if (!this.animInterruptible) {
                this.bottomEnterAnimStateStyle = Folme.useAt(view2).state().setTo(viewProperty, Integer.valueOf(view.getHeight())).to(animStateAdd, this.bottomEnterAnimConfig);
                return;
            } else {
                resetChildForFirstShow(view2, view.getHeight());
                this.bottomEnterAnimStateStyle = Folme.useAt(view2).state().to(animStateAdd, this.bottomEnterAnimConfig);
                return;
            }
        }
        this.animRunning = true;
        if (animationListener != null) {
            animationListener.onAnimationStart();
        }
        view2.setTranslationY(0.0f);
        view.setTranslationY(0.0f);
        if (animationListener != null) {
            animationListener.onAnimationEnd();
        }
        this.animRunning = false;
    }

    public final void startBottomExitAnimation(final AnimationListener animationListener, View view, final View view2, boolean z) {
        if (this.bottomExitAnimConfig == null) {
            this.bottomExitAnimConfig = new AnimConfig().setEase(FolmeEase.spring(0.9f, 0.38f));
        }
        TransitionListener transitionListener = this.bottomExitAnimTransitionListener;
        if (transitionListener != null) {
            this.bottomExitAnimConfig.removeListeners(transitionListener);
        }
        TransitionListener transitionListener2 = new TransitionListener() { // from class: miuix.bottomsheet.AnchorBottomSheetBehavior.5
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                AnchorBottomSheetBehavior.this.animRunning = true;
                View parentView = AnchorBottomSheetBehavior.this.getParentView();
                if (parentView != null) {
                    parentView.setVisibility(0);
                }
                AnimationListener animationListener2 = animationListener;
                if (animationListener2 != null) {
                    animationListener2.onAnimationStart();
                }
                View childView = AnchorBottomSheetBehavior.this.getChildView();
                if (AnchorBottomSheetBehavior.this.improveAnimPerformance && childView != null && childView.isHardwareAccelerated()) {
                    childView.setLayerType(2, null);
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                View view3;
                if (AnchorBottomSheetBehavior.this.improveAnimPerformance && (view3 = view2) != null && view3.isHardwareAccelerated()) {
                    view2.setLayerType(0, null);
                }
                AnchorBottomSheetBehavior.this.animRunning = false;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                View childView = AnchorBottomSheetBehavior.this.getChildView();
                if (childView != null) {
                    Folme.clean(childView);
                }
                if (AnchorBottomSheetBehavior.this.improveAnimPerformance && childView != null && childView.isHardwareAccelerated()) {
                    childView.setLayerType(0, null);
                }
                AnimationListener animationListener2 = animationListener;
                if (animationListener2 != null) {
                    animationListener2.onAnimationEnd();
                }
                AnchorBottomSheetBehavior.this.animRunning = false;
                AnchorBottomSheetBehavior.this.dismissed = true;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                if (AnchorBottomSheetBehavior.this.bottomExitAnimStateStyle == null || !AnchorBottomSheetBehavior.this.shouldBottomExitAnimEnd()) {
                    return;
                }
                AnchorBottomSheetBehavior.this.bottomExitAnimStateStyle.end();
            }
        };
        this.bottomExitAnimTransitionListener = transitionListener2;
        this.bottomExitAnimConfig.addListeners(transitionListener2);
        AnimState animState = new AnimState();
        ViewProperty viewProperty = ViewProperty.TRANSLATION_Y;
        AnimState animStateAdd = animState.add(viewProperty, view.getHeight());
        if (z) {
            this.animRunning = true;
            if (animationListener != null) {
                animationListener.onAnimationStart();
            }
            if (view2 != null) {
                view2.setTranslationY(view.getHeight());
            }
            if (animationListener != null) {
                animationListener.onAnimationEnd();
            }
            this.animRunning = false;
            return;
        }
        if (this.animInterruptible) {
            IStateStyle iStateStyleState = Folme.useAt(view2).state();
            this.bottomExitAnimStateStyle = iStateStyleState;
            iStateStyleState.to(animStateAdd, this.bottomExitAnimConfig);
        } else {
            IStateStyle iStateStyleState2 = Folme.useAt(view2).state();
            this.bottomExitAnimStateStyle = iStateStyleState2;
            iStateStyleState2.setTo(viewProperty, 0).to(animStateAdd, this.bottomExitAnimConfig);
        }
    }

    public boolean startEnterAnimation(AnimationListener animationListener, boolean z) {
        WeakReference weakReference;
        CoordinatorLayout coordinatorLayout;
        WeakReference weakReference2;
        View view;
        if ((!this.animInterruptible && this.animRunning) || (weakReference = this.parentViewRef) == null || (coordinatorLayout = (CoordinatorLayout) weakReference.get()) == null || (weakReference2 = this.viewRef) == null || (view = (View) weakReference2.get()) == null) {
            return false;
        }
        if (this.mode == 0) {
            startBottomEnterAnim(animationListener, coordinatorLayout, view, z);
            return true;
        }
        startPopupEnterAnim(animationListener, coordinatorLayout, view, z);
        return true;
    }

    public boolean startExitAnimation(AnimationListener animationListener) {
        return startExitAnimation(animationListener, false);
    }

    public boolean startExitAnimation(AnimationListener animationListener, boolean z) {
        WeakReference weakReference;
        View view;
        WeakReference weakReference2;
        View view2;
        if ((!this.animInterruptible && this.animRunning) || (weakReference = this.parentViewRef) == null || (view = (View) weakReference.get()) == null || (weakReference2 = this.viewRef) == null || (view2 = (View) weakReference2.get()) == null) {
            return false;
        }
        if (this.mode == 0) {
            startBottomExitAnimation(animationListener, view, view2, z);
            return true;
        }
        startPopupExitAnim(animationListener, view, view2, z);
        return true;
    }

    public final void startPopupEnterAnim(AnimationListener animationListener, View view, View view2, boolean z) {
        updateChildY(view2);
        View anchorView = getAnchorView();
        if (shouldUseTransformAnimation(anchorView)) {
            startTransformAnimation(animationListener, view, anchorView);
            return;
        }
        setupPopupEnterAnimConfig(animationListener);
        setupPopupPivotPoint(view2);
        if (z) {
            executeImmediatePopupEnter(animationListener, view2);
        } else {
            executeAnimatedPopupEnter(view2);
        }
    }

    public final void startPopupExitAnim(final AnimationListener animationListener, View view, View view2, boolean z) {
        if (shouldUseTransformAnimation(getAnchorView()) && this.mTransformAnim != null) {
            handleTransformExitAnim(animationListener, view);
            return;
        }
        if (this.popupExitAnimConfig == null) {
            AnimConfig ease = new AnimConfig().setEase(FolmeEase.spring(0.8f, 0.4f));
            this.popupExitAnimConfig = ease;
            ease.setSpecial(ViewProperty.ALPHA, EaseManager.getStyle(1, 150.0f), new float[0]);
        }
        TransitionListener transitionListener = this.popupExitAnimTransitionListener;
        if (transitionListener != null) {
            this.popupExitAnimConfig.removeListeners(transitionListener);
        }
        TransitionListener transitionListener2 = new TransitionListener() { // from class: miuix.bottomsheet.AnchorBottomSheetBehavior.6
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                AnchorBottomSheetBehavior.this.animRunning = true;
                View parentView = AnchorBottomSheetBehavior.this.getParentView();
                if (parentView != null) {
                    parentView.setVisibility(0);
                }
                View childView = AnchorBottomSheetBehavior.this.getChildView();
                if (AnchorBottomSheetBehavior.this.improveAnimPerformance && childView != null && childView.isHardwareAccelerated()) {
                    childView.setLayerType(2, null);
                }
                AnimationListener animationListener2 = animationListener;
                if (animationListener2 != null) {
                    animationListener2.onAnimationStart();
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                View childView = AnchorBottomSheetBehavior.this.getChildView();
                if (AnchorBottomSheetBehavior.this.improveAnimPerformance && childView != null && childView.isHardwareAccelerated()) {
                    childView.setLayerType(0, null);
                }
                AnchorBottomSheetBehavior.this.animRunning = false;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                View childView = AnchorBottomSheetBehavior.this.getChildView();
                if (childView != null) {
                    Folme.clean(childView);
                }
                if (AnchorBottomSheetBehavior.this.improveAnimPerformance && childView != null && childView.isHardwareAccelerated()) {
                    childView.setLayerType(0, null);
                }
                AnimationListener animationListener2 = animationListener;
                if (animationListener2 != null) {
                    animationListener2.onAnimationEnd();
                }
                AnchorBottomSheetBehavior.this.animRunning = false;
                AnchorBottomSheetBehavior.this.dismissed = true;
            }
        };
        this.popupExitAnimTransitionListener = transitionListener2;
        this.popupExitAnimConfig.addListeners(transitionListener2);
        float fCalculatePopupPivotX = calculatePopupPivotX(view2);
        float fCalculatePopupPivotY = calculatePopupPivotY(view2);
        view2.setPivotX(fCalculatePopupPivotX);
        view2.setPivotY(fCalculatePopupPivotY);
        AnimState animState = new AnimState();
        ViewProperty viewProperty = ViewProperty.SCALE_X;
        AnimState animStateAdd = animState.add(viewProperty, 0.15000000596046448d);
        ViewProperty viewProperty2 = ViewProperty.SCALE_Y;
        AnimState animStateAdd2 = animStateAdd.add(viewProperty2, 0.15000000596046448d);
        ViewProperty viewProperty3 = ViewProperty.ALPHA;
        AnimState animStateAdd3 = animStateAdd2.add(viewProperty3, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
        if (!z) {
            if (this.animInterruptible) {
                IStateStyle iStateStyleState = Folme.useAt(view2).state();
                this.popupExitAnimStateStyle = iStateStyleState;
                iStateStyleState.to(animStateAdd3, this.popupExitAnimConfig);
                return;
            } else {
                IStateStyle iStateStyleState2 = Folme.useAt(view2).state();
                this.popupExitAnimStateStyle = iStateStyleState2;
                iStateStyleState2.setTo(viewProperty, Float.valueOf(1.0f)).setTo(viewProperty2, Float.valueOf(1.0f)).setTo(viewProperty3, Float.valueOf(1.0f)).to(animStateAdd3, this.popupExitAnimConfig);
                return;
            }
        }
        this.animRunning = true;
        if (animationListener != null) {
            animationListener.onAnimationStart();
        }
        view2.setScaleX(0.15f);
        view2.setScaleY(0.15f);
        view2.setAlpha(0.0f);
        if (animationListener != null) {
            animationListener.onAnimationEnd();
        }
        this.animRunning = false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void startTransformAnimation(final AnimationListener animationListener, View view, View view2) {
        WeakReference weakReference = this.viewRef;
        if (weakReference != null) {
            View view3 = (View) weakReference.get();
            if (view3 instanceof AnchorBottomSheetView) {
                ((AnchorBottomSheetView) view3).setInterceptTouchEvent(false);
            }
        }
        if (view != null) {
            view.setVisibility(0);
        }
        this.dismissed = false;
        this.animRunning = true;
        if (animationListener != null) {
            animationListener.onAnimationStart();
        }
        TransformAnimation transformAnimation = new TransformAnimation((ITransformableView) view2, this.mTransformableDelegate);
        this.mTransformAnim = transformAnimation;
        transformAnimation.setCrossWindow(false);
        this.mTransformAnim.addPopupAnimationListener(new PopupAnimationListener() { // from class: miuix.bottomsheet.AnchorBottomSheetBehavior.3
            @Override // miuix.popupwidget.widget.PopupAnimationListener, miuix.popupwidget.widget.IPopupAnimationListener
            public void onForwardAnimationCancel() {
                onForwardAnimationEnd();
            }

            @Override // miuix.popupwidget.widget.PopupAnimationListener, miuix.popupwidget.widget.IPopupAnimationListener
            public void onForwardAnimationEnd() {
                if (AnchorBottomSheetBehavior.this.mTransformAnim != null) {
                    AnchorBottomSheetBehavior.this.mTransformAnim.removePopupAnimationListener(this);
                }
                AnimationListener animationListener2 = animationListener;
                if (animationListener2 != null) {
                    animationListener2.onAnimationEnd();
                }
                AnchorBottomSheetBehavior.this.animRunning = false;
            }
        });
        this.mTransformAnim.showWithAnim();
    }

    public final boolean supportPopupMode(Context context) {
        return MiuixUIUtils.px2dp(context.getResources().getDisplayMetrics().density, (float) EnvStateManager.getWindowInfo(context).windowSize.x) > 600;
    }

    public final void updateChildY(View view) {
        int top = view.getTop();
        this.childYInWindow = top;
        if (top <= 0) {
            Log.w("AnchorBottomSheetBehavior", view + "==》childYInWindow <= 0 ! It's probably a bad time to get the location.");
        }
    }

    public final void updateSizeConfig(Context context, AttributeSet attributeSet) {
        int i;
        if (context == null) {
            return;
        }
        Resources resources = context.getResources();
        this.peekHeightGestureInsetBuffer = resources.getDimensionPixelSize(R.dimen.miuix_min_touch_target_size);
        this.minHeight = resources.getDimensionPixelSize(R.dimen.miuix_bottom_sheet_min_height);
        this.defaultExpandedOffset = resources.getDimensionPixelSize(R.dimen.miuix_bottom_sheet_default_expanded_offset);
        this.defaultHighExpandedOffset = resources.getDimensionPixelSize(R.dimen.miuix_bottom_sheet_default_high_expanded_offset);
        this.highExpandedOffsetThreshold = resources.getDimensionPixelSize(R.dimen.miuix_bottom_sheet_high_expanded_offset_threshold);
        this.fullHeightLowRatioThreshold = resources.getDimensionPixelSize(R.dimen.miuix_bottom_sheet_full_height_low_ratio_threshold);
        this.fullHeightMiddleRatioThreshold = resources.getDimensionPixelSize(R.dimen.miuix_bottom_sheet_full_height_middle_ratio_threshold);
        this.extraHeight = resources.getDimensionPixelSize(R.dimen.miuix_bottom_sheet_extra_height);
        if (attributeSet == null) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MiuixAnchorBottomSheetBehavior_Layout);
        this.elevation = typedArrayObtainStyledAttributes.getDimension(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_android_elevation, -1.0f);
        int i2 = R.styleable.MiuixAnchorBottomSheetBehavior_Layout_android_maxWidth;
        if (typedArrayObtainStyledAttributes.hasValue(i2)) {
            setMaxWidth(typedArrayObtainStyledAttributes.getDimensionPixelSize(i2, -1));
        }
        int i3 = R.styleable.MiuixAnchorBottomSheetBehavior_Layout_android_maxHeight;
        if (typedArrayObtainStyledAttributes.hasValue(i3)) {
            setMaxHeight(typedArrayObtainStyledAttributes.getDimensionPixelSize(i3, -1));
        }
        int i4 = R.styleable.MiuixAnchorBottomSheetBehavior_Layout_miuix_behavior_peekHeight;
        TypedValue typedValuePeekValue = typedArrayObtainStyledAttributes.peekValue(i4);
        if (typedValuePeekValue == null || (i = typedValuePeekValue.data) != -1) {
            setPeekHeight(typedArrayObtainStyledAttributes.getDimensionPixelSize(i4, -1));
        } else {
            setPeekHeight(i);
        }
        int i5 = R.styleable.MiuixAnchorBottomSheetBehavior_Layout_miuix_behavior_expandedOffset;
        TypedValue typedValuePeekValue2 = typedArrayObtainStyledAttributes.peekValue(i5);
        if (typedValuePeekValue2 == null || typedValuePeekValue2.type != 16) {
            setExpandedOffset(typedArrayObtainStyledAttributes.getDimensionPixelOffset(i5, -1));
        } else {
            setExpandedOffset(typedValuePeekValue2.data);
        }
        setBottomModeMaxWidth(typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_bottomModeMaxWidth, resources.getDimensionPixelSize(R.dimen.miuix_bottom_sheet_default_max_width)));
        setBottomModeLandscapeMaxWidth(typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_bottomModeLandscapeMaxWidth, resources.getDimensionPixelSize(R.dimen.miuix_bottom_sheet_default_max_landscape_width)));
        setBottomModeLandscapeWidthRuleEnabled(typedArrayObtainStyledAttributes.getBoolean(R.styleable.MiuixAnchorBottomSheetBehavior_Layout_bottomModeLandscapeMaxWidthRuleEnabled, false));
        typedArrayObtainStyledAttributes.recycle();
    }
}
