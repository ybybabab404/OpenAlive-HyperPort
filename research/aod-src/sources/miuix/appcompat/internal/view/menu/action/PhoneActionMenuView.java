package miuix.appcompat.internal.view.menu.action;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import java.lang.reflect.Method;
import miuix.appcompat.internal.app.widget.ActionBarOverlayLayout;
import miuix.appcompat.internal.view.menu.ExpandedMenuBlurView;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MaterialDayNightConfig;
import miuix.core.util.MiuixUIUtils;
import miuix.internal.util.DeviceHelper;
import miuix.internal.util.ViewUtils;
import miuix.view.animation.CubicEaseOutInterpolator;

/* JADX INFO: loaded from: classes2.dex */
public class PhoneActionMenuView extends ActionMenuView {
    public static final int[] ATTRS = {R.attr.background, miuix.appcompat.R.attr.expandBackground, miuix.appcompat.R.attr.splitActionBarOverlayHeight};
    public int mActionCount;
    public Rect mBackgroundPadding;
    public View mBackgroundView;
    public Drawable mCollapseBackground;
    public Context mContext;
    public final int mDeviceType;
    public Drawable mExpandBackground;
    public ExpandedMenuBlurView mExpandedMenuBlurView;
    public int mMaxActionButtonWidth;
    public int mMenuItemGap;
    public int mMenuItemGapForBigWide;
    public int mMenuItemGapForCommon;
    public int mMenuItemGapForNormalWide;
    public int mMenuItemGapForSmallWide;
    public int mMenuItemGapForTinyWide;
    public int mMenuItemHeight;
    public int mMenuItemWidth;
    public OverflowMenuState mOverflowMenuState;
    public View mOverflowMenuView;
    public OverflowMenuViewAnimator mOverflowMenuViewAnimator;
    public int mSplitActionBarOverlayHeight;

    public enum OverflowMenuState {
        Collapsed,
        Expanding,
        Expanded,
        Collapsing
    }

    public class OverflowMenuViewAnimator implements Animator.AnimatorListener, View.OnClickListener {
        public AnimatorSet mCollapseAnimator;
        public AnimatorSet mExpandAnimator;
        public ActionBarOverlayLayout overlayLayout;

        public OverflowMenuViewAnimator() {
        }

        public void ensureAnimators(ActionBarOverlayLayout actionBarOverlayLayout) {
            this.overlayLayout = actionBarOverlayLayout;
            if (this.mExpandAnimator == null) {
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(ObjectAnimator.ofFloat(PhoneActionMenuView.this, "Value", 1.0f, 0.0f), actionBarOverlayLayout.getContentMaskAnimator(this).show());
                animatorSet.setDuration(PhoneActionMenuView.this.getResources().getInteger(R.integer.config_shortAnimTime));
                animatorSet.setInterpolator(new CubicEaseOutInterpolator());
                animatorSet.addListener(this);
                this.mExpandAnimator = animatorSet;
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(ObjectAnimator.ofFloat(PhoneActionMenuView.this, "Value", 0.0f, 1.0f), actionBarOverlayLayout.getContentMaskAnimator(null).hide());
                animatorSet2.setDuration(PhoneActionMenuView.this.getResources().getInteger(R.integer.config_shortAnimTime));
                animatorSet2.setInterpolator(new CubicEaseOutInterpolator());
                animatorSet2.addListener(this);
                this.mCollapseAnimator = animatorSet2;
                if (DeviceHelper.isFeatureWholeAnim()) {
                    return;
                }
                this.mExpandAnimator.setDuration(0L);
                this.mCollapseAnimator.setDuration(0L);
            }
        }

        public void hide(ActionBarOverlayLayout actionBarOverlayLayout) {
            ensureAnimators(actionBarOverlayLayout);
            this.mCollapseAnimator.cancel();
            this.mExpandAnimator.cancel();
            this.mCollapseAnimator.start();
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            if (PhoneActionMenuView.this.mOverflowMenuState == OverflowMenuState.Expanding || PhoneActionMenuView.this.mOverflowMenuState == OverflowMenuState.Expanded) {
                PhoneActionMenuView.this.setValue(0.0f);
                setContentViewImportantForAccessibility(false);
            } else if (PhoneActionMenuView.this.mOverflowMenuState == OverflowMenuState.Collapsing || PhoneActionMenuView.this.mOverflowMenuState == OverflowMenuState.Collapsed) {
                PhoneActionMenuView.this.setValue(1.0f);
                setContentViewImportantForAccessibility(true);
            }
            PhoneActionMenuView.this.postInvalidateOnAnimation();
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (PhoneActionMenuView.this.mOverflowMenuView != null) {
                if (PhoneActionMenuView.this.mOverflowMenuView.getTranslationY() == 0.0f) {
                    PhoneActionMenuView.this.mOverflowMenuState = OverflowMenuState.Expanded;
                    setContentViewImportantForAccessibility(false);
                } else if (PhoneActionMenuView.this.mOverflowMenuView.getTranslationY() == PhoneActionMenuView.this.getMeasuredHeight()) {
                    PhoneActionMenuView.this.mOverflowMenuState = OverflowMenuState.Collapsed;
                    setContentViewImportantForAccessibility(true);
                    PhoneActionMenuView.this.mBackgroundView.setBackground(PhoneActionMenuView.this.mCollapseBackground);
                }
            }
            PhoneActionMenuView.this.postInvalidateOnAnimation();
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (PhoneActionMenuView.this.mOverflowMenuState == OverflowMenuState.Expanded) {
                PhoneActionMenuView.this.getPresenter().hideOverflowMenu(true);
            }
        }

        public void reverse() {
            try {
                Method declaredMethod = Class.forName("android.animation.AnimatorSet").getDeclaredMethod("reverse", new Class[0]);
                if (this.mExpandAnimator.isRunning()) {
                    declaredMethod.invoke(this.mExpandAnimator, new Object[0]);
                }
                if (this.mCollapseAnimator.isRunning()) {
                    declaredMethod.invoke(this.mCollapseAnimator, new Object[0]);
                }
            } catch (Exception e) {
                Log.e("PhoneActionMenuView", "reverse: ", e);
            }
        }

        public final void setContentViewImportantForAccessibility(boolean z) {
            if (z) {
                this.overlayLayout.getContentView().setImportantForAccessibility(0);
            } else {
                this.overlayLayout.getContentView().setImportantForAccessibility(4);
            }
        }

        public void show(ActionBarOverlayLayout actionBarOverlayLayout) {
            ensureAnimators(actionBarOverlayLayout);
            this.mCollapseAnimator.cancel();
            this.mExpandAnimator.cancel();
            this.mExpandAnimator.start();
        }
    }

    public PhoneActionMenuView(Context context) {
        this(context, null);
    }

    public PhoneActionMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOverflowMenuState = OverflowMenuState.Collapsed;
        this.mMaxActionButtonWidth = 0;
        this.mMenuItemGapForCommon = 0;
        this.mMenuItemGapForTinyWide = 0;
        this.mMenuItemGapForSmallWide = 0;
        this.mMenuItemGapForNormalWide = 0;
        this.mMenuItemGapForBigWide = 0;
        this.mMenuItemGap = 0;
        this.mActionCount = 0;
        super.setBackground(null);
        this.mContext = context;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ATTRS);
        this.mCollapseBackground = typedArrayObtainStyledAttributes.getDrawable(0);
        this.mExpandBackground = typedArrayObtainStyledAttributes.getDrawable(1);
        this.mSplitActionBarOverlayHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(2, 0);
        typedArrayObtainStyledAttributes.recycle();
        extractBackground();
        View view = new View(context);
        this.mBackgroundView = view;
        addView(view);
        setChildrenDrawingOrderEnabled(true);
        this.mDeviceType = miuix.os.DeviceHelper.detectType(context);
        updateActionButtonValues(context);
    }

    private int getActionMenuItemCount() {
        int childCount = getChildCount();
        if (indexOfChild(this.mOverflowMenuView) != -1) {
            childCount--;
        }
        return indexOfChild(this.mBackgroundView) != -1 ? childCount - 1 : childCount;
    }

    private OverflowMenuViewAnimator getOverflowMenuViewAnimator() {
        if (this.mOverflowMenuViewAnimator == null) {
            this.mOverflowMenuViewAnimator = new OverflowMenuViewAnimator();
        }
        return this.mOverflowMenuViewAnimator;
    }

    private boolean isNotActionMenuItemChild(View view) {
        return view == this.mOverflowMenuView || view == this.mBackgroundView;
    }

    @Override // miuix.view.BlurableWidget
    public void applyBlur(boolean z) {
    }

    public final void extractBackground() {
        if (this.mBackgroundPadding == null) {
            this.mBackgroundPadding = new Rect();
        }
        Drawable drawable = this.mOverflowMenuView == null ? this.mCollapseBackground : this.mExpandBackground;
        if (drawable == null) {
            this.mBackgroundPadding.setEmpty();
        } else {
            drawable.getPadding(this.mBackgroundPadding);
        }
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView, miuix.appcompat.internal.view.menu.MenuView
    public boolean filterLeftoverView(int i) {
        View childAt = getChildAt(i);
        if (isNotActionMenuItemChild(childAt)) {
            return false;
        }
        ActionMenuView.LayoutParams layoutParams = (ActionMenuView.LayoutParams) childAt.getLayoutParams();
        return (layoutParams == null || !layoutParams.isOverflowButton) && super.filterLeftoverView(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x001a, code lost:
    
        if (r1 != (-1)) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x001c, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0012, code lost:
    
        if (r1 != (-1)) goto L12;
     */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int getChildDrawingOrder(int r5, int r6) {
        /*
            r4 = this;
            android.view.View r0 = r4.mOverflowMenuView
            int r0 = r4.indexOfChild(r0)
            android.view.View r1 = r4.mBackgroundView
            int r1 = r4.indexOfChild(r1)
            r2 = -1
            if (r6 != 0) goto L15
            if (r0 == r2) goto L12
            return r0
        L12:
            if (r1 == r2) goto L1d
            goto L1c
        L15:
            r3 = 1
            if (r6 != r3) goto L1d
            if (r0 == r2) goto L1d
            if (r1 == r2) goto L1d
        L1c:
            return r1
        L1d:
            r2 = 0
        L1e:
            if (r2 >= r5) goto L35
            if (r2 == r0) goto L32
            if (r2 != r1) goto L25
            goto L32
        L25:
            if (r2 >= r0) goto L2a
            int r3 = r2 + 1
            goto L2b
        L2a:
            r3 = r2
        L2b:
            if (r2 >= r1) goto L2f
            int r3 = r3 + 1
        L2f:
            if (r3 != r6) goto L32
            return r2
        L32:
            int r2 = r2 + 1
            goto L1e
        L35:
            int r4 = super.getChildDrawingOrder(r5, r6)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: miuix.appcompat.internal.view.menu.action.PhoneActionMenuView.getChildDrawingOrder(int, int):int");
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView
    public int getCollapsedHeight() {
        int i = this.mMenuItemHeight;
        if (i == 0) {
            return 0;
        }
        return (i + this.mBackgroundPadding.top) - this.mSplitActionBarOverlayHeight;
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView, miuix.view.HyperMaterialWidget
    @Nullable
    public /* bridge */ /* synthetic */ MaterialConfig getCurrentMaterial() {
        return super.getCurrentMaterial();
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView, miuix.view.HyperMaterialWidget
    @Nullable
    public /* bridge */ /* synthetic */ MaterialDayNightConfig getMaterial() {
        return super.getMaterial();
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView, miuix.appcompat.internal.view.menu.MenuView
    public boolean hasBackgroundView() {
        return getChildAt(0) == this.mBackgroundView || getChildAt(1) == this.mBackgroundView;
    }

    public boolean hideOverflowMenu(ActionBarOverlayLayout actionBarOverlayLayout) {
        OverflowMenuState overflowMenuState = this.mOverflowMenuState;
        OverflowMenuState overflowMenuState2 = OverflowMenuState.Collapsing;
        if (overflowMenuState == overflowMenuState2 || overflowMenuState == OverflowMenuState.Collapsed) {
            return false;
        }
        OverflowMenuViewAnimator overflowMenuViewAnimator = getOverflowMenuViewAnimator();
        if (overflowMenuState == OverflowMenuState.Expanded) {
            this.mOverflowMenuState = overflowMenuState2;
            overflowMenuViewAnimator.hide(actionBarOverlayLayout);
            return true;
        }
        if (overflowMenuState != OverflowMenuState.Expanding) {
            return true;
        }
        overflowMenuViewAnimator.reverse();
        return true;
    }

    public boolean isOverflowMenuShowing() {
        OverflowMenuState overflowMenuState = this.mOverflowMenuState;
        return overflowMenuState == OverflowMenuState.Expanding || overflowMenuState == OverflowMenuState.Expanded;
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateActionButtonValues(this.mContext);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6 = i3 - i;
        int i7 = i4 - i2;
        View view = this.mOverflowMenuView;
        if (view != null) {
            int measuredHeight = view.getMeasuredHeight();
            ViewUtils.layoutChildView(this, this.mOverflowMenuView, 0, 0, i6, measuredHeight);
            i5 = measuredHeight - this.mBackgroundPadding.top;
        } else {
            i5 = 0;
        }
        ViewUtils.layoutChildView(this, this.mBackgroundView, 0, i5, i6, i7);
        int childCount = getChildCount();
        int measuredWidth = (i6 - this.mMenuItemWidth) >> 1;
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (!isNotActionMenuItemChild(childAt)) {
                ViewUtils.layoutChildView(this, childAt, measuredWidth, i5, measuredWidth + childAt.getMeasuredWidth(), i7);
                measuredWidth += childAt.getMeasuredWidth() + this.mMenuItemGap;
            }
        }
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView, android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        int actionMenuItemCount = getActionMenuItemCount();
        this.mActionCount = actionMenuItemCount;
        if (childCount == 0 || actionMenuItemCount == 0) {
            this.mMenuItemHeight = 0;
            setMeasuredDimension(0, 0);
            return;
        }
        int size = View.MeasureSpec.getSize(i);
        this.mMaxActionButtonWidth = Math.min(size / this.mActionCount, this.mMaxActionButtonWidth);
        updateItemGapAdaptByCurrentWidth(getContext(), size);
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mMaxActionButtonWidth, Integer.MIN_VALUE);
        int iMin = 0;
        int measuredHeight = 0;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (!isNotActionMenuItemChild(childAt)) {
                measureChildWithMargins(childAt, iMakeMeasureSpec, 0, i2, 0);
                iMin += Math.min(childAt.getMeasuredWidth(), this.mMaxActionButtonWidth);
                measuredHeight = Math.max(measuredHeight, childAt.getMeasuredHeight());
            }
        }
        int i4 = this.mMenuItemGap;
        int i5 = this.mActionCount;
        if ((i4 * (i5 - 1)) + iMin > size) {
            this.mMenuItemGap = 0;
        }
        int i6 = iMin + (this.mMenuItemGap * (i5 - 1));
        this.mMenuItemWidth = i6;
        this.mMenuItemHeight = measuredHeight;
        View view = this.mOverflowMenuView;
        if (view != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            marginLayoutParams.topMargin = MiuixUIUtils.getStatusBarHeight(this.mContext);
            marginLayoutParams.bottomMargin = this.mMenuItemHeight;
            measureChildWithMargins(this.mOverflowMenuView, i, 0, i2, 0);
            Math.max(i6, this.mOverflowMenuView.getMeasuredWidth());
            measuredHeight += this.mOverflowMenuView.getMeasuredHeight();
            OverflowMenuState overflowMenuState = this.mOverflowMenuState;
            if (overflowMenuState == OverflowMenuState.Expanded) {
                this.mOverflowMenuView.setTranslationY(0.0f);
            } else if (overflowMenuState == OverflowMenuState.Collapsed) {
                this.mOverflowMenuView.setTranslationY(measuredHeight);
            }
        }
        if (this.mOverflowMenuView == null) {
            measuredHeight += this.mBackgroundPadding.top;
        }
        this.mBackgroundView.setBackground(this.mOverflowMenuState == OverflowMenuState.Collapsed ? this.mCollapseBackground : this.mExpandBackground);
        setMeasuredDimension(size, measuredHeight);
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView
    public void onPageScrolled(int i, float f, boolean z, boolean z2) {
        if (DeviceHelper.isFeatureWholeAnim()) {
            setAlpha(computeAlpha(f, z, z2));
        }
        float fComputeTranslationY = computeTranslationY(f, z, z2);
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (!isNotActionMenuItemChild(childAt)) {
                childAt.setTranslationY(fComputeTranslationY);
            }
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float y = motionEvent.getY();
        View view = this.mOverflowMenuView;
        return y > (view == null ? 0.0f : view.getTranslationY()) || super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public void setBackground(Drawable drawable) {
        if (this.mCollapseBackground != drawable) {
            this.mCollapseBackground = drawable;
            extractBackground();
        }
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView
    public void setEnableBlur(boolean z) {
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView, miuix.view.HyperMaterialWidget
    public /* bridge */ /* synthetic */ void setMaterial(@Nullable MaterialConfig materialConfig) {
        super.setMaterial(materialConfig);
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView, miuix.view.HyperMaterialWidget
    public /* bridge */ /* synthetic */ void setMaterial(@Nullable MaterialDayNightConfig materialDayNightConfig) {
        super.setMaterial(materialDayNightConfig);
    }

    public void setOverflowMenuView(View view) {
        ExpandedMenuBlurView expandedMenuBlurView = this.mExpandedMenuBlurView;
        if (((expandedMenuBlurView == null || expandedMenuBlurView.getChildCount() <= 1) ? null : this.mExpandedMenuBlurView.getChildAt(1)) != view) {
            View view2 = this.mOverflowMenuView;
            if (view2 != null) {
                if (view2.getAnimation() != null) {
                    this.mOverflowMenuView.clearAnimation();
                }
                ExpandedMenuBlurView expandedMenuBlurView2 = this.mExpandedMenuBlurView;
                if (expandedMenuBlurView2 != null) {
                    expandedMenuBlurView2.removeAllViews();
                    removeView(this.mExpandedMenuBlurView);
                    this.mExpandedMenuBlurView = null;
                }
            }
            if (view != null) {
                if (this.mExpandedMenuBlurView == null) {
                    this.mExpandedMenuBlurView = new ExpandedMenuBlurView(this.mContext);
                }
                this.mExpandedMenuBlurView.addView(view);
                addView(this.mExpandedMenuBlurView);
            }
            this.mOverflowMenuView = this.mExpandedMenuBlurView;
            extractBackground();
        }
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView
    public void setSupportBlur(boolean z) {
    }

    public void setValue(float f) {
        View view = this.mOverflowMenuView;
        if (view == null) {
            return;
        }
        view.setTranslationY(f * getMeasuredHeight());
    }

    public boolean showOverflowMenu(ActionBarOverlayLayout actionBarOverlayLayout) {
        OverflowMenuState overflowMenuState = this.mOverflowMenuState;
        OverflowMenuState overflowMenuState2 = OverflowMenuState.Expanding;
        if (overflowMenuState == overflowMenuState2 || overflowMenuState == OverflowMenuState.Expanded || this.mOverflowMenuView == null) {
            return false;
        }
        this.mBackgroundView.setBackground(this.mExpandBackground);
        OverflowMenuViewAnimator overflowMenuViewAnimator = getOverflowMenuViewAnimator();
        if (overflowMenuState == OverflowMenuState.Collapsed) {
            this.mOverflowMenuState = overflowMenuState2;
            overflowMenuViewAnimator.show(actionBarOverlayLayout);
        } else if (overflowMenuState == OverflowMenuState.Collapsing) {
            overflowMenuViewAnimator.reverse();
        }
        postInvalidateOnAnimation();
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        return true;
    }

    public final void updateActionButtonValues(Context context) {
        this.mMaxActionButtonWidth = context.getResources().getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_action_button_max_width);
        this.mMenuItemGapForCommon = context.getResources().getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_action_button_gap);
        if (this.mDeviceType != 1) {
            this.mMenuItemGapForTinyWide = context.getResources().getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_action_button_gap_tiny_wide);
            this.mMenuItemGapForSmallWide = context.getResources().getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_action_button_gap_small_wide);
            this.mMenuItemGapForNormalWide = context.getResources().getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_action_button_gap_normal_wide);
            this.mMenuItemGapForBigWide = context.getResources().getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_action_button_gap_big_wide);
        }
    }

    public final void updateItemGapAdaptByCurrentWidth(Context context, int i) {
        int i2 = this.mDeviceType;
        if (i2 == 3) {
            this.mMenuItemGap = this.mMenuItemGapForTinyWide;
            return;
        }
        if (i2 != 2) {
            this.mMenuItemGap = this.mMenuItemGapForCommon;
            return;
        }
        int i3 = (int) ((i * 1.0f) / context.getResources().getDisplayMetrics().density);
        if (i3 >= 700 && i3 < 740) {
            this.mMenuItemGap = this.mMenuItemGapForSmallWide;
            return;
        }
        if (i3 >= 740 && i3 < 1000) {
            this.mMenuItemGap = this.mMenuItemGapForNormalWide;
        } else if (i3 >= 1000) {
            this.mMenuItemGap = this.mMenuItemGapForBigWide;
        } else {
            this.mMenuItemGap = this.mMenuItemGapForTinyWide;
        }
    }
}
