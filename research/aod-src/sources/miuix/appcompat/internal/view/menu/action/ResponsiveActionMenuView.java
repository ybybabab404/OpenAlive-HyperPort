package miuix.appcompat.internal.view.menu.action;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import java.util.Collection;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.appcompat.R;
import miuix.appcompat.app.floatingactivity.helper.BaseFloatingActivityHelper;
import miuix.appcompat.internal.view.menu.action.layout.LayoutStrategy;
import miuix.appcompat.internal.view.menu.action.layout.LayoutStrategySelector;
import miuix.appcompat.internal.view.menu.action.layout.LayoutTier;
import miuix.appcompat.internal.view.menu.action.layout.MultiItemLayoutStrategy;
import miuix.appcompat.internal.view.menu.action.layout.TextWidthMeasurer;
import miuix.core.util.ContextUtils;
import miuix.core.util.HyperBloomStrokeUtils;
import miuix.core.util.HyperMaterialUtils;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MaterialDayNightConfig;
import miuix.core.util.MiuixUIUtils;
import miuix.graphics.shadow.DropShadowConfig;
import miuix.graphics.shadow.DropShadowHelper;
import miuix.internal.util.AttributeResolver;
import miuix.internal.util.ViewUtils;
import miuix.smooth.SmoothCornerHelper;
import miuix.theme.token.ShadowToken;
import miuix.theme.token.hypermaterial.InternalMaterial;
import miuix.view.MiuiBlurUiHelper;

/* JADX INFO: loaded from: classes2.dex */
public class ResponsiveActionMenuView extends ActionMenuView {
    public AnimConfig mAnimConfig;
    public boolean mApplyBlur;
    public AttributeSet mAttrs;
    public Drawable mBackgroundInBlur;
    public final MiuiBlurUiHelper mBlurUiHelper;
    public Drawable mBottomMenuBackground;
    public int mBottomMenuItemHeight;
    public final Context mContext;
    public View mCurrentHoverItem;
    public LayoutTier mCurrentLayoutTier;
    public MaterialConfig mCurrentMaterial;
    public View mCurrentPressedItem;
    public int mCurrentTotalVisibleItems;
    public View mCustomView;
    public Rect mCustomViewClipBounds;
    public int mDensityDpi;
    public boolean mHasOnlyCustomView;
    public boolean mIsCustomViewHidden;
    public boolean mIsEmpty;
    public boolean mIsFloatingWindow;
    public boolean mIsHidden;
    public int mItemNormalPaddingTop;
    public int mItemSmallPaddingTop;
    public boolean mLargeFontAdaptionEnabled;
    public MaterialDayNightConfig mMaterial;
    public int mMenuItemGap;
    public int mMenuItemHeight;
    public int mMenuItemWidth;
    public int mOffSet;
    public int mPressBackgroundMargin;
    public MenuItemPressBackgroundView mPressBackgroundView;
    public DropShadowHelper mShadowHelper;
    public boolean mSuspendEnabled;
    public int mSuspendItemCenterExtraUp;
    public Drawable mSuspendMenuBackground;
    public int mSuspendMenuBackgroundRadius;
    public int mSuspendMenuMinMargin;
    public int mSuspendMenuMinWidth;
    public ViewOutlineProvider mViewOutlineInSuspend;

    public ResponsiveActionMenuView(Context context) {
        this(context, null);
    }

    public ResponsiveActionMenuView(Context context, AttributeSet attributeSet) {
        ResponsiveActionMenuView responsiveActionMenuView;
        Context context2;
        super(context, attributeSet);
        this.mSuspendEnabled = true;
        this.mHasOnlyCustomView = false;
        this.mIsEmpty = false;
        this.mOffSet = 0;
        this.mIsHidden = false;
        this.mIsCustomViewHidden = false;
        this.mApplyBlur = false;
        this.mLargeFontAdaptionEnabled = false;
        this.mCurrentLayoutTier = LayoutTier.NORMAL;
        this.mCurrentTotalVisibleItems = 0;
        this.mViewOutlineInSuspend = new ViewOutlineProvider() { // from class: miuix.appcompat.internal.view.menu.action.ResponsiveActionMenuView.1
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), ResponsiveActionMenuView.this.mSuspendMenuBackgroundRadius);
            }
        };
        this.mAnimConfig = new AnimConfig().setEase(-2, 0.9f, 0.25f).addListeners(new TransitionListener() { // from class: miuix.appcompat.internal.view.menu.action.ResponsiveActionMenuView.2
            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                ResponsiveActionMenuView.this.mOffSet = UpdateInfo.findByName(collection, "target").getIntValue();
                ResponsiveActionMenuView.this.requestLayout();
            }
        });
        boolean z = AttributeResolver.resolveBoolean(context, R.attr.largeFontAdaptationEnabled, true) && MiuixUIUtils.getFontLevel(context) == 2;
        this.mLargeFontAdaptionEnabled = z;
        this.mMenuItemGap = z ? MiuixUIUtils.dp2px(context, 16.0f) : MiuixUIUtils.dp2px(context, 0.0f);
        Resources resources = context.getResources();
        this.mBottomMenuItemHeight = this.mLargeFontAdaptionEnabled ? resources.getDimensionPixelSize(R.dimen.miuix_appcompat_bottom_menu_height_in_large_font) : resources.getDimensionPixelSize(R.dimen.miuix_appcompat_bottom_menu_height);
        this.mSuspendMenuMinMargin = MiuixUIUtils.dp2px(context, 16.0f);
        this.mSuspendMenuMinWidth = MiuixUIUtils.dp2px(context, 196.0f);
        this.mItemNormalPaddingTop = MiuixUIUtils.dp2px(context, 8.0f);
        this.mItemSmallPaddingTop = MiuixUIUtils.dp2px(context, 5.0f);
        this.mSuspendItemCenterExtraUp = MiuixUIUtils.dp2px(context, 2.0f);
        this.mPressBackgroundMargin = MiuixUIUtils.dp2px(context, 3.0f);
        this.mSuspendMenuBackgroundRadius = context.getResources().getDimensionPixelSize(this.mLargeFontAdaptionEnabled ? R.dimen.miuix_appcompat_suspend_menu_bg_radius_large_font : R.dimen.miuix_appcompat_suspend_menu_bg_radius);
        this.mDensityDpi = context.getResources().getDisplayMetrics().densityDpi;
        this.mContext = context;
        this.mAttrs = attributeSet;
        setClickable(true);
        refreshMenuBackgroundDrawables(attributeSet);
        setClipChildren(false);
        setClipToPadding(false);
        setWillNotDraw(false);
        SmoothCornerHelper.setViewSmoothCornerEnable(this, true);
        if (HyperMaterialUtils.isEnable()) {
            this.mMaterial = MaterialDayNightConfig.create(InternalMaterial.Pured_Thin_Glass);
            responsiveActionMenuView = this;
            context2 = context;
            responsiveActionMenuView.mBlurUiHelper = new MiuiBlurUiHelper(context2, responsiveActionMenuView, false, false, new MiuiBlurUiHelper.BlurStateCallback() { // from class: miuix.appcompat.internal.view.menu.action.ResponsiveActionMenuView.3
                public final boolean isDarkThemeOverlay;

                {
                    this.isDarkThemeOverlay = MiuixUIUtils.isDarkThemeOverlay(ResponsiveActionMenuView.this.getContext(), R.color.miuix_default_color_on_surface_light);
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public Drawable getBackground() {
                    boolean z2 = ResponsiveActionMenuView.this.mSuspendEnabled;
                    ResponsiveActionMenuView responsiveActionMenuView2 = ResponsiveActionMenuView.this;
                    return z2 ? responsiveActionMenuView2.mSuspendMenuBackground : responsiveActionMenuView2.mBottomMenuBackground;
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public int getBackgroundColor() {
                    if (this.isDarkThemeOverlay) {
                        return AttributeResolver.resolveColor(ResponsiveActionMenuView.this.getContext(), miuix.theme.R.attr.colorSurface, 0);
                    }
                    return 0;
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public MaterialConfig.BlurConfig getBlurConfig(boolean z2) {
                    MaterialDayNightConfig materialDayNightConfig = ResponsiveActionMenuView.this.mMaterial;
                    if (materialDayNightConfig != null) {
                        return materialDayNightConfig.getBlurConfig(z2);
                    }
                    return null;
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public boolean isLightTheme() {
                    Integer colorFromDrawable;
                    Drawable drawable = ResponsiveActionMenuView.this.mSuspendEnabled ? ResponsiveActionMenuView.this.mSuspendMenuBackground : ResponsiveActionMenuView.this.mBottomMenuBackground;
                    if (drawable == null || (colorFromDrawable = MiuixUIUtils.getColorFromDrawable(drawable)) == null) {
                        return !this.isDarkThemeOverlay && AttributeResolver.resolveBoolean(ResponsiveActionMenuView.this.getContext(), R.attr.isLightTheme, true);
                    }
                    return MiuixUIUtils.isLightColor(colorFromDrawable.intValue()) && !this.isDarkThemeOverlay;
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public void onBlurApplyStateChanged(boolean z2) {
                    ResponsiveActionMenuView.this.mApplyBlur = z2;
                    ResponsiveActionMenuView.this.updateBackground();
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public void onBlurEnableStateChanged(boolean z2) {
                }
            });
        } else {
            responsiveActionMenuView = this;
            context2 = context;
            responsiveActionMenuView.mBlurUiHelper = null;
        }
        responsiveActionMenuView.updateBackground();
        MenuItemPressBackgroundView menuItemPressBackgroundView = new MenuItemPressBackgroundView(context2);
        responsiveActionMenuView.mPressBackgroundView = menuItemPressBackgroundView;
        menuItemPressBackgroundView.setParentView(responsiveActionMenuView);
    }

    private int getActionMenuItemCount() {
        int childCount = getChildCount();
        if (indexOfChild(this.mCustomView) != -1) {
            childCount--;
        }
        for (int i = 0; i < getChildCount(); i++) {
            if (isOverflowButtonChild(getChildAt(i))) {
                childCount--;
            }
        }
        return childCount;
    }

    private Rect getCustomViewClipBounds() {
        if (this.mCustomViewClipBounds == null) {
            this.mCustomViewClipBounds = new Rect();
        }
        this.mCustomViewClipBounds.set(0, 0, this.mCustomView.getMeasuredWidth(), this.mCustomView.getMeasuredHeight() - this.mOffSet);
        return this.mCustomViewClipBounds;
    }

    private int getMaxChildrenTotalHeight() {
        int childrenHeight;
        int childCount = getChildCount();
        int i = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (!isNotActionMenuItemChild(childAt) && (childAt instanceof LinearLayout) && i < (childrenHeight = getChildrenHeight((LinearLayout) childAt))) {
                i = childrenHeight;
            }
        }
        return i;
    }

    private boolean isNotActionMenuItemChild(View view) {
        return view == this.mCustomView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBackground() {
        boolean zIsApplyBlur = isApplyBlur();
        if (this.mSuspendEnabled) {
            setOutlineProvider(this.mViewOutlineInSuspend);
            setBackground(zIsApplyBlur ? this.mBackgroundInBlur : this.mSuspendMenuBackground);
            return;
        }
        setOutlineProvider(null);
        if (this.mBackgroundViewApplyBlur) {
            setBackground(null);
        } else {
            setBackground(zIsApplyBlur ? this.mBackgroundInBlur : this.mBottomMenuBackground);
        }
    }

    public void addCustomView(View view) {
        if (view == null) {
            return;
        }
        this.mCustomView = view;
        addView(view);
    }

    @Override // miuix.view.BlurableWidget
    public void applyBlur(boolean z) {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper == null) {
            return;
        }
        miuiBlurUiHelper.applyBlur(z);
    }

    public final void applyTextTruncatedDeal(boolean z, boolean z2) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof ActionMenuItemView) {
                Rect rect = new Rect(0, 0, 0, 0);
                ActionMenuItemView actionMenuItemView = (ActionMenuItemView) childAt;
                actionMenuItemView.setResponsiveViewSuspendEnabled(z2);
                actionMenuItemView.setTextPaddings(rect);
                actionMenuItemView.setTextVerticalTruncatedDealEnabled(z);
                actionMenuItemView.setUseBackgroundPressEffect(z2);
            }
        }
    }

    public final boolean canShowPressEffect(View view) {
        return view != null && isActionMenuItem(view) && view.isEnabled();
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView
    public void clearBackground() {
        setBackground(null);
    }

    public final boolean detectedTextTruncatedHappened(boolean z) {
        int childCount = getChildCount();
        boolean z2 = false;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof ActionMenuItemView) {
                z2 = z2 || ((ActionMenuItemView) childAt).isVerticalTextTruncated();
            }
        }
        return z2 && z;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        MenuItemPressBackgroundView menuItemPressBackgroundView;
        if (this.mSuspendEnabled && (menuItemPressBackgroundView = this.mPressBackgroundView) != null) {
            menuItemPressBackgroundView.drawBackground(canvas);
        }
        super.dispatchDraw(canvas);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        if (this.mSuspendEnabled) {
            int action = motionEvent.getAction();
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if (action == 7 || action == 9) {
                View viewFindItemAt = findItemAt(x, y);
                if (canShowPressEffect(viewFindItemAt)) {
                    if (viewFindItemAt != this.mCurrentHoverItem) {
                        this.mCurrentHoverItem = viewFindItemAt;
                        showHoverBackground(viewFindItemAt);
                    }
                } else if (this.mCurrentHoverItem != null) {
                    hideHoverBackground();
                    this.mCurrentHoverItem = null;
                }
            } else if (action == 10) {
                hideHoverBackground();
                this.mCurrentHoverItem = null;
            }
        }
        return super.dispatchHoverEvent(motionEvent);
    }

    /* JADX WARN: Code duplicated, block: B:18:0x002f  */
    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.mSuspendEnabled) {
            int action = motionEvent.getAction();
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if (action == 0) {
                View viewFindItemAt = findItemAt(x, y);
                if (canShowPressEffect(viewFindItemAt)) {
                    this.mCurrentPressedItem = viewFindItemAt;
                    showPressBackground(viewFindItemAt);
                }
            } else if (action == 1) {
                hidePressBackground();
                this.mCurrentPressedItem = null;
            } else if (action != 2) {
                if (action == 3) {
                    hidePressBackground();
                    this.mCurrentPressedItem = null;
                }
            } else if (this.mCurrentPressedItem != null && findItemAt(x, y) != this.mCurrentPressedItem) {
                hidePressBackground();
                this.mCurrentPressedItem = null;
            }
        }
        return super.dispatchTouchEvent(motionEvent);
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

    public final View findItemAt(float f, float f2) {
        int childCount = getChildCount();
        View view = null;
        float f3 = Float.MAX_VALUE;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (!isNotActionMenuItemChild(childAt) && (((childAt instanceof ActionMenuItemView) || isOverflowButton(childAt)) && f >= childAt.getLeft() && f <= childAt.getRight() && f2 >= childAt.getTop() && f2 <= childAt.getBottom())) {
                float fAbs = Math.abs(f - ((childAt.getLeft() + childAt.getRight()) / 2.0f));
                if (fAbs < f3) {
                    view = childAt;
                    f3 = fAbs;
                }
            }
        }
        return view;
    }

    public final TextView findTextViewInOverflowButton(View view) {
        TextView textViewFindTextViewInOverflowButton;
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof TextView) {
                return (TextView) childAt;
            }
            if ((childAt instanceof ViewGroup) && (textViewFindTextViewInOverflowButton = findTextViewInOverflowButton(childAt)) != null) {
                return textViewFindTextViewInOverflowButton;
            }
        }
        return null;
    }

    public int getBottomMenuCustomViewOffset() {
        return this.mOffSet;
    }

    public final int getChildrenHeight(LinearLayout linearLayout) {
        int childCount = linearLayout.getChildCount();
        int measuredHeight = 0;
        for (int i = 0; i < childCount; i++) {
            measuredHeight += linearLayout.getChildAt(i).getMeasuredHeight();
        }
        return measuredHeight;
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView
    public int getCollapsedHeight() {
        if (this.mIsEmpty) {
            return 0;
        }
        int measuredHeightWithMargin = ViewUtils.getMeasuredHeightWithMargin(this);
        View view = (View) getParent();
        int measuredHeight = view != null ? view.getMeasuredHeight() : 0;
        if (measuredHeight <= 0) {
            return 0;
        }
        return Math.max(measuredHeight, measuredHeightWithMargin);
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView, miuix.view.HyperMaterialWidget
    @Nullable
    public MaterialConfig getCurrentMaterial() {
        return this.mCurrentMaterial;
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView, miuix.view.HyperMaterialWidget
    @Nullable
    public MaterialDayNightConfig getMaterial() {
        return this.mMaterial;
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView
    public boolean hasOnlyCustomView() {
        return this.mHasOnlyCustomView;
    }

    public void hideBottomMenuCustomView() {
        if (this.mCustomView == null || this.mIsCustomViewHidden) {
            return;
        }
        Folme.useValue(new Object[0]).setTo("target", Float.valueOf(0.0f)).to("target", Float.valueOf(this.mCustomView.getMeasuredHeight()), this.mAnimConfig);
        this.mIsCustomViewHidden = true;
    }

    public final void hideHoverBackground() {
        MenuItemPressBackgroundView menuItemPressBackgroundView = this.mPressBackgroundView;
        if (menuItemPressBackgroundView != null) {
            menuItemPressBackgroundView.hideHover();
            invalidate();
        }
    }

    public void hideOverflowSelectedBackground() {
        MenuItemPressBackgroundView menuItemPressBackgroundView = this.mPressBackgroundView;
        if (menuItemPressBackgroundView == null) {
            return;
        }
        menuItemPressBackgroundView.hideActivated();
        invalidate();
    }

    public final void hidePressBackground() {
        MenuItemPressBackgroundView menuItemPressBackgroundView = this.mPressBackgroundView;
        if (menuItemPressBackgroundView != null) {
            menuItemPressBackgroundView.hide();
            invalidate();
        }
    }

    public final void initShadow() {
        if (!isSuspend()) {
            setElevation(0.0f);
            DropShadowHelper dropShadowHelper = this.mShadowHelper;
            if (dropShadowHelper != null) {
                dropShadowHelper.enableViewShadow(this, false, 2);
                return;
            }
            return;
        }
        ShadowToken shadowToken = ShadowToken.Regular;
        DropShadowConfig.Builder offsetYDp = new DropShadowConfig.Builder(shadowToken.radius).setOffsetXDp(shadowToken.offsetX).setOffsetYDp(shadowToken.offsetY);
        int i = shadowToken.color;
        DropShadowConfig dropShadowConfigCreate = offsetYDp.setColor(i, i).setDispersion(shadowToken.dispersion).setClipShadowEnable(true).create();
        DropShadowHelper dropShadowHelper2 = this.mShadowHelper;
        if (dropShadowHelper2 == null) {
            DropShadowHelper dropShadowHelper3 = new DropShadowHelper(getContext(), dropShadowConfigCreate, isLightTheme());
            this.mShadowHelper = dropShadowHelper3;
            dropShadowHelper3.setClipShadow(true);
        } else {
            dropShadowHelper2.updateDropShadowConfig(dropShadowConfigCreate);
        }
        if (!this.mShadowHelper.isEnableMiShadow()) {
            setElevation(MiuixUIUtils.dp2px(this.mContext, 8.0f));
        } else {
            setElevation(0.0f);
            this.mShadowHelper.updateViewShadow(this, 2);
        }
    }

    public final boolean isActionMenuItem(View view) {
        return (view instanceof ActionMenuItemView) || isOverflowButton(view);
    }

    public boolean isApplyBlur() {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper == null) {
            return false;
        }
        return miuiBlurUiHelper.isApplyBlur();
    }

    public boolean isEnableBlur() {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper == null) {
            return false;
        }
        return miuiBlurUiHelper.isEnableBlur();
    }

    public final boolean isLightTheme() {
        Integer colorFromDrawable;
        boolean zIsDarkThemeOverlay = MiuixUIUtils.isDarkThemeOverlay(getContext(), R.color.miuix_default_color_on_surface_light);
        Drawable drawable = this.mSuspendEnabled ? this.mSuspendMenuBackground : this.mBottomMenuBackground;
        if (drawable == null || (colorFromDrawable = MiuixUIUtils.getColorFromDrawable(drawable)) == null) {
            return !zIsDarkThemeOverlay && AttributeResolver.resolveBoolean(getContext(), R.attr.isLightTheme, true);
        }
        return MiuixUIUtils.isLightColor(colorFromDrawable.intValue()) && !zIsDarkThemeOverlay;
    }

    public final boolean isOverflowButton(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ActionMenuView.LayoutParams) {
            return ((ActionMenuView.LayoutParams) layoutParams).isOverflowButton;
        }
        return false;
    }

    public final boolean isOverflowButtonChild(View view) {
        if (view == null || view == this.mCustomView) {
            return false;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        return layoutParams instanceof ActionMenuView.LayoutParams ? ((ActionMenuView.LayoutParams) layoutParams).isOverflowButton : view instanceof OverflowMenuButton;
    }

    public boolean isSuspend() {
        return this.mSuspendEnabled;
    }

    public final void keepHidden() {
        if (this.mIsHidden) {
            setTranslationY(ViewUtils.getMeasuredHeightWithMargin(this));
        }
    }

    public final void measureActionMenuItem(int i, int i2, boolean z) {
        applyTextTruncatedDeal(detectedTextTruncatedHappened(z), z);
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (!isNotActionMenuItemChild(childAt)) {
                if (childAt instanceof LinearLayout) {
                    ((LinearLayout) childAt).setGravity(1);
                }
                if (z) {
                    childAt.setPadding(0, 0, 0, 0);
                } else {
                    childAt.setPadding(0, (!MiuixUIUtils.isLayoutHideNavigation(this) || (MiuixUIUtils.isFullScreenGestureMode(this.mContext) && !MiuixUIUtils.isShowNavigationHandle(this.mContext))) ? this.mItemSmallPaddingTop : this.mItemNormalPaddingTop, 0, 0);
                }
                childAt.measure(i, i2);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        initShadow();
        updateMaterialEffect();
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper != null) {
            miuiBlurUiHelper.onConfigChanged();
        }
        MenuItemPressBackgroundView menuItemPressBackgroundView = this.mPressBackgroundView;
        if (menuItemPressBackgroundView != null) {
            menuItemPressBackgroundView.updateDarkMode((configuration.uiMode & 48) == 32);
        }
        int i = configuration.densityDpi;
        if (i != this.mDensityDpi) {
            this.mDensityDpi = i;
            this.mMenuItemGap = this.mLargeFontAdaptionEnabled ? MiuixUIUtils.dp2px(this.mContext, 16.0f) : MiuixUIUtils.dp2px(this.mContext, 0.0f);
            this.mSuspendMenuMinMargin = MiuixUIUtils.dp2px(this.mContext, 16.0f);
            this.mSuspendMenuMinWidth = MiuixUIUtils.dp2px(this.mContext, 196.0f);
            this.mItemNormalPaddingTop = MiuixUIUtils.dp2px(this.mContext, 8.0f);
            this.mItemSmallPaddingTop = MiuixUIUtils.dp2px(this.mContext, 5.0f);
            this.mSuspendItemCenterExtraUp = MiuixUIUtils.dp2px(this.mContext, 2.0f);
            Resources resources = getContext().getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.miuix_appcompat_bottom_menu_height);
            int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.miuix_appcompat_bottom_menu_height_in_large_font);
            boolean z = this.mLargeFontAdaptionEnabled;
            if (z) {
                dimensionPixelSize = dimensionPixelSize2;
            }
            this.mBottomMenuItemHeight = dimensionPixelSize;
            this.mSuspendMenuBackgroundRadius = resources.getDimensionPixelSize(z ? R.dimen.miuix_appcompat_suspend_menu_bg_radius_large_font : R.dimen.miuix_appcompat_suspend_menu_bg_radius);
            refreshBackground();
        } else if (this.mIsFloatingWindow != BaseFloatingActivityHelper.isFloatingWindow(ContextUtils.getActivity(this.mContext))) {
            this.mIsFloatingWindow = BaseFloatingActivityHelper.isFloatingWindow(this.mContext);
            refreshBackground();
        }
        updateMaterialEffect();
        DropShadowHelper dropShadowHelper = this.mShadowHelper;
        if (dropShadowHelper != null) {
            dropShadowHelper.onConfigChanged(this, configuration, isLightTheme());
        }
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        applyBlur(false);
        DropShadowHelper dropShadowHelper = this.mShadowHelper;
        if (dropShadowHelper != null) {
            dropShadowHelper.enableViewShadow(this, false, 2);
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        return super.onHoverEvent(motionEvent);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int measuredHeight2 = 0;
        if (this.mHasOnlyCustomView) {
            View view = this.mCustomView;
            if (view == null || view.getVisibility() == 8) {
                return;
            }
            View view2 = this.mCustomView;
            ViewUtils.layoutChildView(this, view2, 0, 0, view2.getMeasuredWidth(), this.mCustomView.getMeasuredHeight());
            return;
        }
        View view3 = this.mCustomView;
        boolean z2 = false;
        if (view3 != null && view3.getVisibility() != 8) {
            View view4 = this.mCustomView;
            ViewUtils.layoutChildView(this, view4, 0, 0, view4.getMeasuredWidth(), this.mCustomView.getMeasuredHeight());
            measuredHeight2 = this.mCustomView.getMeasuredHeight() - this.mOffSet;
            if (measuredHeight2 < 0) {
                measuredHeight2 = 0;
            }
        }
        int childCount = getChildCount();
        int actionMenuItemCount = getActionMenuItemCount();
        if (this.mSuspendEnabled) {
            int iDp2px = MiuixUIUtils.dp2px(this.mContext, 3.0f);
            int i10 = 0;
            while (true) {
                if (i10 >= childCount) {
                    i7 = 0;
                    break;
                } else {
                    if (isOverflowButtonChild(getChildAt(i10))) {
                        i7 = 1;
                        break;
                    }
                    i10++;
                }
            }
            int i11 = actionMenuItemCount + i7;
            int i12 = 0;
            int i13 = 0;
            while (i12 < childCount) {
                View childAt = getChildAt(i12);
                if (!isNotActionMenuItemChild(childAt) && ((childAt instanceof ActionMenuItemView) || isOverflowButtonChild(childAt))) {
                    int measuredWidth2 = childAt.getMeasuredWidth();
                    if (i11 == 1) {
                        i9 = (measuredWidth - measuredWidth2) / 2;
                    } else if (i11 < 4 && this.mCurrentLayoutTier == LayoutTier.NORMAL) {
                        i9 = (((((measuredWidth - iDp2px) - measuredWidth2) - iDp2px) * i13) / (i11 - 1)) + iDp2px;
                    } else {
                        int iDp2px2 = MiuixUIUtils.dp2px(this.mContext, 10.0f);
                        i9 = ((iDp2px2 + measuredWidth2) * i13) + iDp2px + iDp2px2;
                    }
                    i8 = measuredHeight;
                    ViewUtils.layoutChildView(this, childAt, i9, measuredHeight2, measuredWidth2 + i9, i8);
                    i13++;
                } else {
                    i8 = measuredHeight;
                }
                i12++;
                measuredHeight = i8;
            }
            i5 = measuredHeight;
        } else {
            i5 = measuredHeight;
            int i14 = 0;
            while (true) {
                if (i14 >= childCount) {
                    i6 = 0;
                    break;
                } else {
                    if (isOverflowButtonChild(getChildAt(i14))) {
                        i6 = 1;
                        break;
                    }
                    i14++;
                }
            }
            int i15 = actionMenuItemCount + i6;
            int paddingStart = getPaddingStart() + ((((measuredWidth - getPaddingStart()) - getPaddingEnd()) - ((this.mMenuItemWidth * i15) + ((i15 - 1) * this.mMenuItemGap))) / 2);
            for (int i16 = 0; i16 < childCount; i16++) {
                View childAt2 = getChildAt(i16);
                if (!isNotActionMenuItemChild(childAt2) && ((childAt2 instanceof ActionMenuItemView) || isOverflowButtonChild(childAt2))) {
                    ViewUtils.layoutChildView(this, childAt2, paddingStart, measuredHeight2, childAt2.getMeasuredWidth() + paddingStart, i5);
                    paddingStart += childAt2.getMeasuredWidth() + this.mMenuItemGap;
                }
            }
        }
        if (isEnableBlur() && measuredWidth > 0 && i5 > 0) {
            z2 = true;
        }
        applyBlur(z2);
    }

    /* JADX WARN: Code duplicated, block: B:42:0x00d8  */
    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView, android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        View childAt;
        int i3;
        int i4;
        boolean z;
        TextView textViewFindTextViewInOverflowButton;
        int i5;
        int i6;
        int childrenHeight;
        int i7;
        int childrenHeight2;
        int i8;
        boolean z2;
        int i9;
        this.mHasOnlyCustomView = false;
        this.mIsEmpty = false;
        int paddingBottom = getPaddingBottom();
        int paddingTop = getPaddingTop() + paddingBottom;
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int childCount = getChildCount();
        int actionMenuItemCount = getActionMenuItemCount();
        int size = View.MeasureSpec.getSize(i);
        int i10 = 0;
        while (true) {
            if (i10 >= childCount) {
                childAt = null;
                break;
            } else {
                if (isOverflowButtonChild(getChildAt(i10))) {
                    childAt = getChildAt(i10);
                    break;
                }
                i10++;
            }
        }
        int i11 = 1073741824;
        if (childCount == 0 || (actionMenuItemCount == 0 && childAt == null)) {
            this.mMenuItemHeight = 0;
            View view = this.mCustomView;
            if (view == null || view.getVisibility() == 8) {
                this.mIsEmpty = true;
                setMeasuredDimension(0, 0);
            } else {
                this.mHasOnlyCustomView = true;
                ActionMenuView.LayoutParams layoutParams = (ActionMenuView.LayoutParams) this.mCustomView.getLayoutParams();
                if (this.mSuspendEnabled) {
                    this.mCustomView.measure(View.MeasureSpec.makeMeasureSpec(size - (this.mSuspendMenuMinMargin * 2), 1073741824), LinearLayout.getChildMeasureSpec(i2, paddingTop, ((LinearLayout.LayoutParams) layoutParams).height));
                } else {
                    this.mCustomView.measure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), LinearLayout.getChildMeasureSpec(i2, paddingTop, ((LinearLayout.LayoutParams) layoutParams).height));
                }
                this.mCustomView.setClipBounds(getCustomViewClipBounds());
                int measuredWidth = this.mCustomView.getMeasuredWidth();
                int measuredHeight = (this.mCustomView.getMeasuredHeight() + paddingTop) - this.mOffSet;
                if (measuredHeight < 0) {
                    measuredHeight = 0;
                }
                setMeasuredDimension(measuredWidth, measuredHeight);
            }
            keepHidden();
            return;
        }
        if (this.mSuspendEnabled) {
            int i12 = actionMenuItemCount + (childAt != null ? 1 : 0);
            this.mCurrentTotalVisibleItems = i12;
            float[] fArr = new float[i12];
            TextWidthMeasurer textWidthMeasurer = new TextWidthMeasurer(this.mContext);
            int i13 = 0;
            for (int i14 = 0; i14 < childCount; i14++) {
                View childAt2 = getChildAt(i14);
                if (childAt2 instanceof ActionMenuItemView) {
                    i9 = i13 + 1;
                    fArr[i13] = textWidthMeasurer.measureMenuItemTextWidth((ActionMenuItemView) childAt2);
                } else {
                    if (isOverflowButtonChild(childAt2)) {
                        i9 = i13 + 1;
                        fArr[i13] = textWidthMeasurer.measureViewTextWidth(childAt2);
                    }
                }
                i13 = i9;
            }
            LayoutStrategy layoutStrategySelectStrategy = new LayoutStrategySelector(this.mContext).selectStrategy(i12, size);
            LayoutTier layoutTierDetermineLayoutTier = layoutStrategySelectStrategy.determineLayoutTier(fArr);
            this.mCurrentLayoutTier = layoutTierDetermineLayoutTier;
            int iCalculateMenuWidth = layoutStrategySelectStrategy.calculateMenuWidth(layoutTierDetermineLayoutTier, i12, size);
            int[] iArrCalculateItemWidths = layoutStrategySelectStrategy.calculateItemWidths(layoutTierDetermineLayoutTier, iCalculateMenuWidth, i12);
            this.mMenuItemWidth = iArrCalculateItemWidths.length > 0 ? iArrCalculateItemWidths[0] : 0;
            int iCalculateMenuHeight = layoutStrategySelectStrategy.calculateMenuHeight(layoutTierDetermineLayoutTier, MiuixUIUtils.dp2px(this.mContext, 56.0f));
            if (layoutStrategySelectStrategy instanceof MultiItemLayoutStrategy) {
                if (((MultiItemLayoutStrategy) layoutStrategySelectStrategy).needHeightIncrease(fArr, iArrCalculateItemWidths)) {
                    iCalculateMenuHeight += MiuixUIUtils.dp2px(this.mContext, 20.0f);
                    z = true;
                } else {
                    z = false;
                }
            } else if (layoutTierDetermineLayoutTier != LayoutTier.SEVERE_OVERFLOW || i12 <= 1) {
                z = false;
            } else {
                z = true;
            }
            if (this.mLargeFontAdaptionEnabled && !z) {
                iCalculateMenuHeight += MiuixUIUtils.dp2px(this.mContext, 6.0f);
            }
            this.mMenuItemHeight = iCalculateMenuHeight;
            int i15 = 0;
            int i16 = 0;
            while (i15 < childCount) {
                View childAt3 = getChildAt(i15);
                if (childAt3 instanceof ActionMenuItemView) {
                    int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i16 < iArrCalculateItemWidths.length ? iArrCalculateItemWidths[i16] : this.mMenuItemWidth, i11);
                    i8 = paddingBottom;
                    int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(iCalculateMenuHeight, i11);
                    if (childAt3 != null) {
                        z2 = true;
                        ((LinearLayout) childAt3).setGravity(1);
                    } else {
                        z2 = true;
                    }
                    ((ActionMenuItemView) childAt3).setUseBackgroundPressEffect(z2);
                    childAt3.setPadding(0, 0, 0, 0);
                    childAt3.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
                } else {
                    i8 = paddingBottom;
                    i15 = i15;
                    if (isOverflowButtonChild(childAt3)) {
                        int iMakeMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(i16 < iArrCalculateItemWidths.length ? iArrCalculateItemWidths[i16] : this.mMenuItemWidth, 1073741824);
                        int iMakeMeasureSpec4 = View.MeasureSpec.makeMeasureSpec(iCalculateMenuHeight, 1073741824);
                        childAt3.setPadding(0, 0, 0, 0);
                        childAt3.measure(iMakeMeasureSpec3, iMakeMeasureSpec4);
                    }
                    i15++;
                    paddingBottom = i8;
                    i11 = 1073741824;
                }
                i16++;
                i15++;
                paddingBottom = i8;
                i11 = 1073741824;
            }
            i3 = paddingBottom;
            int i17 = 0;
            for (int i18 = 0; i18 < childCount; i18++) {
                View childAt4 = getChildAt(i18);
                if (((childAt4 instanceof ActionMenuItemView) || isOverflowButtonChild(childAt4)) && (childAt4 instanceof LinearLayout) && (childrenHeight2 = getChildrenHeight((LinearLayout) childAt4)) > i17) {
                    i17 = childrenHeight2;
                }
            }
            int i19 = (iCalculateMenuHeight - i17) / 2;
            if (i19 < 0) {
                i19 = 0;
            }
            int i20 = 0;
            int i21 = 0;
            while (i20 < childCount) {
                View childAt5 = getChildAt(i20);
                if (childAt5 instanceof ActionMenuItemView) {
                    int iMakeMeasureSpec5 = View.MeasureSpec.makeMeasureSpec(i21 < iArrCalculateItemWidths.length ? iArrCalculateItemWidths[i21] : this.mMenuItemWidth, 1073741824);
                    i7 = i20;
                    int iMakeMeasureSpec6 = View.MeasureSpec.makeMeasureSpec(iCalculateMenuHeight, 1073741824);
                    childAt5.setPadding(0, i19, 0, 0);
                    childAt5.measure(iMakeMeasureSpec5, iMakeMeasureSpec6);
                } else {
                    i7 = i20;
                    if (isOverflowButtonChild(childAt5)) {
                        int iMakeMeasureSpec7 = View.MeasureSpec.makeMeasureSpec(i21 < iArrCalculateItemWidths.length ? iArrCalculateItemWidths[i21] : this.mMenuItemWidth, 1073741824);
                        int iMakeMeasureSpec8 = View.MeasureSpec.makeMeasureSpec(iCalculateMenuHeight, 1073741824);
                        childAt5.setPadding(0, i19, 0, 0);
                        childAt5.measure(iMakeMeasureSpec7, iMakeMeasureSpec8);
                    }
                    i20 = i7 + 1;
                }
                i21++;
                i20 = i7 + 1;
            }
            int iDp2px = size - (MiuixUIUtils.dp2px(this.mContext, 24.0f) * 2);
            if (iCalculateMenuWidth > iDp2px && iDp2px > 0) {
                this.mCurrentLayoutTier = LayoutTier.SLIGHT_OVERFLOW;
                int iDp2px2 = (iDp2px - ((MiuixUIUtils.dp2px(this.mContext, 3.0f) * 2) + ((i12 + 1) * MiuixUIUtils.dp2px(this.mContext, 10.0f)))) / i12;
                if (iDp2px2 > 0) {
                    this.mMenuItemWidth = iDp2px2;
                    for (int i22 = 0; i22 < childCount; i22++) {
                        View childAt6 = getChildAt(i22);
                        if ((childAt6 instanceof ActionMenuItemView) || isOverflowButtonChild(childAt6)) {
                            int iMakeMeasureSpec9 = View.MeasureSpec.makeMeasureSpec(iDp2px2, 1073741824);
                            int iMakeMeasureSpec10 = View.MeasureSpec.makeMeasureSpec(iCalculateMenuHeight, 1073741824);
                            childAt6.setPadding(0, i19, 0, 0);
                            childAt6.measure(iMakeMeasureSpec9, iMakeMeasureSpec10);
                        }
                    }
                }
                iCalculateMenuWidth = iDp2px;
            }
            int iDp2px3 = MiuixUIUtils.dp2px(this.mContext, this.mLargeFontAdaptionEnabled ? 94.0f : 76.0f);
            if (iCalculateMenuHeight < iDp2px3) {
                for (int i23 = 0; i23 < childCount; i23++) {
                    View childAt7 = getChildAt(i23);
                    if (((childAt7 instanceof ActionMenuItemView) || isOverflowButtonChild(childAt7)) && (textViewFindTextViewInOverflowButton = findTextViewInOverflowButton(childAt7)) != null && textViewFindTextViewInOverflowButton.getLineCount() > 1) {
                        this.mMenuItemHeight = iDp2px3;
                        int i24 = 0;
                        int i25 = 0;
                        for (int i26 = 0; i26 < childCount; i26++) {
                            View childAt8 = getChildAt(i26);
                            if ((childAt8 instanceof ActionMenuItemView) || isOverflowButtonChild(childAt8)) {
                                if (this.mCurrentLayoutTier != LayoutTier.SLIGHT_OVERFLOW || (i6 = this.mMenuItemWidth) <= 0) {
                                    i6 = i25 < iArrCalculateItemWidths.length ? iArrCalculateItemWidths[i25] : this.mMenuItemWidth;
                                }
                                int iMakeMeasureSpec11 = View.MeasureSpec.makeMeasureSpec(i6, 1073741824);
                                int iMakeMeasureSpec12 = View.MeasureSpec.makeMeasureSpec(iDp2px3, 1073741824);
                                childAt8.setPadding(0, 0, 0, 0);
                                childAt8.measure(iMakeMeasureSpec11, iMakeMeasureSpec12);
                                if ((childAt8 instanceof LinearLayout) && (childrenHeight = getChildrenHeight((LinearLayout) childAt8)) > i24) {
                                    i24 = childrenHeight;
                                }
                                i25++;
                            }
                        }
                        int iMax = Math.max(0, (iDp2px3 - i24) / 2);
                        int i27 = 0;
                        for (int i28 = 0; i28 < childCount; i28++) {
                            View childAt9 = getChildAt(i28);
                            if ((childAt9 instanceof ActionMenuItemView) || isOverflowButtonChild(childAt9)) {
                                if (this.mCurrentLayoutTier != LayoutTier.SLIGHT_OVERFLOW || (i5 = this.mMenuItemWidth) <= 0) {
                                    i5 = i27 < iArrCalculateItemWidths.length ? iArrCalculateItemWidths[i27] : this.mMenuItemWidth;
                                }
                                int iMakeMeasureSpec13 = View.MeasureSpec.makeMeasureSpec(i5, 1073741824);
                                int iMakeMeasureSpec14 = View.MeasureSpec.makeMeasureSpec(iDp2px3, 1073741824);
                                childAt9.setPadding(0, iMax, 0, 0);
                                childAt9.measure(iMakeMeasureSpec13, iMakeMeasureSpec14);
                                i27++;
                            }
                        }
                        break;
                    }
                }
            }
            size = (layoutTierDetermineLayoutTier == LayoutTier.NORMAL || i12 < 2) ? iCalculateMenuWidth : Math.max(iCalculateMenuWidth, this.mSuspendMenuMinWidth);
        } else {
            i3 = paddingBottom;
            int i29 = 0;
            while (true) {
                if (i29 >= childCount) {
                    i4 = 0;
                    break;
                } else {
                    if (isOverflowButtonChild(getChildAt(i29))) {
                        i4 = 1;
                        break;
                    }
                    i29++;
                }
            }
            int i30 = actionMenuItemCount + i4;
            int iMax2 = ((size - paddingLeft) - ((i30 - 1) * this.mMenuItemGap)) / Math.max(i30, 1);
            this.mMenuItemWidth = iMax2;
            int i31 = this.mBottomMenuItemHeight + i3;
            int iMakeMeasureSpec15 = View.MeasureSpec.makeMeasureSpec(iMax2, 1073741824);
            int iMakeMeasureSpec16 = View.MeasureSpec.makeMeasureSpec(i31, 1073741824);
            measureActionMenuItem(iMakeMeasureSpec15, iMakeMeasureSpec16, this.mSuspendEnabled);
            for (int i32 = 0; i32 < childCount; i32++) {
                View childAt10 = getChildAt(i32);
                if (isOverflowButtonChild(childAt10)) {
                    childAt10.measure(iMakeMeasureSpec15, iMakeMeasureSpec16);
                }
            }
            this.mMenuItemHeight = i31;
        }
        int measuredHeight2 = this.mMenuItemHeight + paddingTop;
        if (!this.mSuspendEnabled) {
            measuredHeight2 -= i3;
        }
        View view2 = this.mCustomView;
        if (view2 != null && view2.getVisibility() != 8) {
            this.mCustomView.measure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), LinearLayout.getChildMeasureSpec(i2, paddingTop, ((LinearLayout.LayoutParams) ((ActionMenuView.LayoutParams) this.mCustomView.getLayoutParams())).height));
            this.mCustomView.setClipBounds(getCustomViewClipBounds());
            measuredHeight2 = (measuredHeight2 + this.mCustomView.getMeasuredHeight()) - this.mOffSet;
        }
        setMeasuredDimension(size, measuredHeight2);
        keepHidden();
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView
    public void onPageScrolled(int i, float f, boolean z, boolean z2) {
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(parcelable);
        applyBlur(false);
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView
    public void onWillRemoved() {
        super.onWillRemoved();
        applyBlur(false);
        DropShadowHelper dropShadowHelper = this.mShadowHelper;
        if (dropShadowHelper != null) {
            dropShadowHelper.enableViewShadow(this, false, 2);
        }
    }

    public final void refreshBackground() {
        refreshMenuBackgroundDrawables(this.mAttrs);
        updateBackground();
    }

    public final void refreshMenuBackgroundDrawables(AttributeSet attributeSet) {
        Context context = this.mContext;
        if (context == null) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = null;
        try {
            this.mIsFloatingWindow = BaseFloatingActivityHelper.isFloatingWindow(ContextUtils.getActivity(context));
            typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, R.styleable.ResponsiveActionMenuView, R.attr.responsiveActionMenuViewStyle, 0);
            this.mBottomMenuBackground = typedArrayObtainStyledAttributes.getDrawable(this.mIsFloatingWindow ? R.styleable.ResponsiveActionMenuView_floatingWindowBottomMenuBackground : R.styleable.ResponsiveActionMenuView_bottomMenuBackground);
            this.mSuspendMenuBackground = typedArrayObtainStyledAttributes.getDrawable(this.mIsFloatingWindow ? R.styleable.ResponsiveActionMenuView_floatingWindowSuspendMenuBackground : R.styleable.ResponsiveActionMenuView_suspendMenuBackground);
            typedArrayObtainStyledAttributes.recycle();
            if (HyperMaterialUtils.isEnable()) {
                this.mBackgroundInBlur = new ColorDrawable(0);
            }
        } catch (Throwable th) {
            if (typedArrayObtainStyledAttributes != null) {
                typedArrayObtainStyledAttributes.recycle();
            }
            throw th;
        }
    }

    public void removeCustomView() {
        View view = this.mCustomView;
        if (view == null || view.getParent() == null) {
            return;
        }
        removeView(this.mCustomView);
        this.mOffSet = 0;
        this.mCustomView = null;
        this.mIsCustomViewHidden = false;
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView
    public void resetBackground() {
        updateBackground();
    }

    public void setBottomMenuCustomViewTranslationYWithPx(int i) {
        if (this.mCustomView == null || i < 0) {
            return;
        }
        this.mOffSet = i;
        requestLayout();
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView
    public void setEnableBlur(boolean z) {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper == null) {
            return;
        }
        miuiBlurUiHelper.setEnableBlur(z);
        applyBlur(z && getMeasuredWidth() > 0 && getMeasuredHeight() > 0);
        updateBackground();
    }

    public void setHidden(boolean z) {
        this.mIsHidden = z;
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView, miuix.view.HyperMaterialWidget
    public /* bridge */ /* synthetic */ void setMaterial(@Nullable MaterialConfig materialConfig) {
        super.setMaterial(materialConfig);
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView, miuix.view.HyperMaterialWidget
    public void setMaterial(@Nullable MaterialDayNightConfig materialDayNightConfig) {
        if (materialDayNightConfig != null) {
            this.mMaterial = materialDayNightConfig;
            updateMaterialEffect();
        } else {
            this.mMaterial = null;
            this.mCurrentMaterial = null;
            applyBlur(false);
            HyperBloomStrokeUtils.clearBloomStroke(this);
        }
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuView
    public void setSupportBlur(boolean z) {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper == null) {
            return;
        }
        miuiBlurUiHelper.setSupportBlur(z);
    }

    public void setSuspendEnabled(boolean z) {
        if (this.mSuspendEnabled != z) {
            this.mSuspendEnabled = z;
            MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
            if (miuiBlurUiHelper != null) {
                miuiBlurUiHelper.resetBlurParams();
                this.mBlurUiHelper.refreshBlur();
            }
        }
        updateBackground();
        initShadow();
    }

    @Override // android.view.View
    public void setTranslationY(float f) {
        super.setTranslationY(f);
    }

    public void showBottomMenuCustomView() {
        if (this.mCustomView == null || !this.mIsCustomViewHidden) {
            return;
        }
        Folme.useValue(new Object[0]).setTo("target", Float.valueOf(this.mCustomView.getMeasuredHeight())).to("target", Float.valueOf(0.0f), this.mAnimConfig);
        this.mIsCustomViewHidden = false;
    }

    public final void showHoverBackground(View view) {
        float right;
        float f;
        if (this.mPressBackgroundView != null) {
            float f2 = this.mPressBackgroundMargin;
            int height = getHeight();
            int i = this.mPressBackgroundMargin;
            float f3 = height - i;
            float f4 = this.mSuspendMenuBackgroundRadius - i;
            int i2 = this.mCurrentTotalVisibleItems;
            if (i2 == 1) {
                f = i;
                right = getWidth() - this.mPressBackgroundMargin;
            } else if (this.mCurrentLayoutTier != LayoutTier.NORMAL || i2 > 3) {
                int iDp2px = MiuixUIUtils.dp2px(this.mContext, 10.0f);
                float left = view.getLeft() - iDp2px;
                right = view.getRight() + iDp2px;
                f = left;
            } else {
                float left2 = view.getLeft();
                right = view.getRight();
                f = left2;
            }
            this.mPressBackgroundView.showHoverAt(f, f2, right, f3, f4);
            invalidate();
        }
    }

    public void showOverflowSelectedBackground(View view) {
        float right;
        float f;
        if (this.mPressBackgroundView == null || view == null) {
            return;
        }
        float f2 = this.mPressBackgroundMargin;
        int height = getHeight();
        int i = this.mPressBackgroundMargin;
        float f3 = height - i;
        float f4 = this.mSuspendMenuBackgroundRadius - i;
        int i2 = this.mCurrentTotalVisibleItems;
        if (i2 == 1) {
            f = i;
            right = getWidth() - this.mPressBackgroundMargin;
        } else if (this.mCurrentLayoutTier != LayoutTier.NORMAL || i2 > 3) {
            int iDp2px = MiuixUIUtils.dp2px(this.mContext, 10.0f);
            float left = view.getLeft() - iDp2px;
            right = view.getRight() + iDp2px;
            f = left;
        } else {
            float left2 = view.getLeft();
            right = view.getRight();
            f = left2;
        }
        this.mPressBackgroundView.showActivatedAt(f, f2, right, f3, f4);
        invalidate();
    }

    public final void showPressBackground(View view) {
        float right;
        float f;
        if (this.mPressBackgroundView != null) {
            float f2 = this.mPressBackgroundMargin;
            int height = getHeight();
            int i = this.mPressBackgroundMargin;
            float f3 = height - i;
            float f4 = this.mSuspendMenuBackgroundRadius - i;
            int i2 = this.mCurrentTotalVisibleItems;
            if (i2 == 1) {
                f = i;
                right = getWidth() - this.mPressBackgroundMargin;
            } else if (this.mCurrentLayoutTier != LayoutTier.NORMAL || i2 > 3) {
                int iDp2px = MiuixUIUtils.dp2px(this.mContext, 10.0f);
                float left = view.getLeft() - iDp2px;
                right = view.getRight() + iDp2px;
                f = left;
            } else {
                float left2 = view.getLeft();
                right = view.getRight();
                f = left2;
            }
            this.mPressBackgroundView.showAt(f, f2, right, f3, f4);
            invalidate();
        }
    }

    public void updateMaterialEffect() {
        if (this.mMaterial == null) {
            return;
        }
        MaterialConfig materialConfig = this.mMaterial.get(isLightTheme());
        this.mCurrentMaterial = materialConfig;
        if (materialConfig == null || !HyperMaterialUtils.isFeatureEnable(getContext())) {
            applyBlur(false);
            setEnableBlur(false);
            HyperBloomStrokeUtils.clearBloomStroke(this);
            return;
        }
        setEnableBlur(true);
        if (this.mBlurUiHelper != null && this.mCurrentMaterial.getBlurConfig() != null) {
            if (isApplyBlur()) {
                this.mBlurUiHelper.onConfigChanged();
                this.mBlurUiHelper.refreshBlur();
            } else {
                this.mBlurUiHelper.onConfigChanged();
                applyBlur(true);
            }
        }
        MaterialConfig.BloomStrokeConfig bloomStrokeConfig = this.mCurrentMaterial.getBloomStrokeConfig();
        if (bloomStrokeConfig != null) {
            HyperBloomStrokeUtils.setBloomStrokeConfig(this, bloomStrokeConfig);
        } else {
            HyperBloomStrokeUtils.clearBloomStroke(this);
        }
    }
}
