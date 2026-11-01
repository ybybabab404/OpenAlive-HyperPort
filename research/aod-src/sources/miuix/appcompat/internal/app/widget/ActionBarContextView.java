package miuix.appcompat.internal.app.widget;

import android.R;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import androidx.annotation.RestrictTo;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.IHoverStyle;
import miuix.animation.IStateStyle;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.physics.DynamicAnimation;
import miuix.animation.physics.SpringAnimation;
import miuix.animation.property.ViewProperty;
import miuix.appcompat.internal.app.widget.actionbar.CollapseTitle;
import miuix.appcompat.internal.app.widget.actionbar.ExpandTitle;
import miuix.appcompat.internal.graphics.drawable.RoundStateDrawable;
import miuix.appcompat.internal.util.FloatingEffectHelper;
import miuix.appcompat.internal.view.ActionBarPolicy;
import miuix.appcompat.internal.view.EditActionModeImpl;
import miuix.appcompat.internal.view.menu.MenuBuilder;
import miuix.appcompat.internal.view.menu.action.ActionMenuItem;
import miuix.appcompat.internal.view.menu.action.ActionMenuPresenter;
import miuix.appcompat.internal.view.menu.action.ActionMenuView;
import miuix.appcompat.internal.view.menu.action.ResponsiveActionMenuView;
import miuix.core.util.MiuixTraceUtils;
import miuix.core.util.MiuixUIUtils;
import miuix.core.util.RomUtils;
import miuix.internal.util.AttributeResolver;
import miuix.internal.util.ViewUtils;
import miuix.theme.Typography;
import miuix.view.ActionModeAnimationListener;

/* JADX INFO: loaded from: classes2.dex */
@RestrictTo
public class ActionBarContextView extends AbsActionBarView implements ActionModeView {
    public boolean mActionBarButtonMaterial;
    public ActionBarView mActionBarView;
    public WeakReference mActionMode;
    public Drawable mActionModeBackground;
    public boolean mAnimateStart;
    public boolean mAnimateToVisible;
    public List mAnimationListeners;
    public int mAnimationMode;
    public float mAnimationProgress;
    public boolean mBackgroundViewApplyBlur;
    public ImageView mButton1;
    public ActionMenuItem mButton1MenuItem;
    public ImageView mButton2;
    public ActionMenuItem mButton2MenuItem;
    public AbsActionBarView.CollapseView mCollapseController;
    public int mCollapseTotalHeight;
    public int mContentInset;
    public int mExpandTitleStyleRes;
    public TextView mExpandTitleView;
    public int mExpandTotalHeight;
    public Boolean mFloatingMode;
    public final Handler mHandler;
    public boolean mIsAnimating;
    public View mMainContainer;
    public FrameLayout mMediumContainer;
    public AbsActionBarView.CollapseView mMediumController;
    public int mMediumTitleStyleRes;
    public TextView mMediumTitleView;
    public AnimConfig mMenuAnimConfig;
    public AbsActionBarView.CollapseView mMovableController;
    public FrameLayout mMovableMainContainer;
    public boolean mNonTouchScrolling;
    public View.OnClickListener mOnMenuItemClickListener;
    public int mPendingHeight;
    public final Runnable mPostAnimationRunnable;
    public Runnable mPostScroll;
    public Scroller mPostScroller;
    public boolean mRequestAnimation;
    public Drawable mSplitBackground;
    public boolean mStartWithAnim;
    public CharSequence mTitle;
    public LinearLayout mTitleLayout;
    public boolean mTitleOptional;
    public int mTitleStyleRes;
    public TextView mTitleView;
    public boolean mTouchScrolling;
    public TransitionListener mTransitionListener;
    public SpringAnimation mVisibilityAnim;

    public static class CountDown {
        public int mCount;
        public CountDownCompleteListener mCountDownCompleteListener;

        public interface CountDownCompleteListener {
            void onCountDownComplete();
        }

        public CountDown(int i, CountDownCompleteListener countDownCompleteListener) {
            this.mCount = i;
            this.mCountDownCompleteListener = countDownCompleteListener;
        }

        public void countDown() {
            int i = this.mCount - 1;
            this.mCount = i;
            if (i == 0) {
                this.mCountDownCompleteListener.onCountDownComplete();
            }
        }
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.ClassLoaderCreator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: miuix.appcompat.internal.app.widget.ActionBarContextView.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
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
        public CharSequence defaultButtonText;
        public int expandState;
        public boolean isOverflowOpen;
        public CharSequence title;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.isOverflowOpen = parcel.readInt() != 0;
            Parcelable.Creator creator = TextUtils.CHAR_SEQUENCE_CREATOR;
            this.title = (CharSequence) creator.createFromParcel(parcel);
            this.defaultButtonText = (CharSequence) creator.createFromParcel(parcel);
            this.expandState = parcel.readInt();
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.isOverflowOpen = parcel.readInt() != 0;
            Parcelable.Creator creator = TextUtils.CHAR_SEQUENCE_CREATOR;
            this.title = (CharSequence) creator.createFromParcel(parcel);
            this.defaultButtonText = (CharSequence) creator.createFromParcel(parcel);
            this.expandState = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.isOverflowOpen ? 1 : 0);
            TextUtils.writeToParcel(this.title, parcel, 0);
            TextUtils.writeToParcel(this.defaultButtonText, parcel, 0);
            parcel.writeInt(this.expandState);
        }
    }

    public ActionBarContextView(Context context) {
        this(context, null);
    }

    public ActionBarContextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.actionModeStyle);
    }

    public ActionBarContextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mStartWithAnim = true;
        this.mBackgroundViewApplyBlur = false;
        this.mFloatingMode = null;
        this.mActionBarButtonMaterial = false;
        this.mHandler = new Handler(Looper.myLooper());
        this.mPostAnimationRunnable = new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarContextView.1
            @Override // java.lang.Runnable
            public void run() {
                if (ActionBarContextView.this.mPostScroll != null) {
                    ActionBarContextView actionBarContextView = ActionBarContextView.this;
                    actionBarContextView.postOnAnimation(actionBarContextView.mPostScroll);
                }
            }
        };
        this.mOnMenuItemClickListener = new View.OnClickListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarContextView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditActionModeImpl editActionModeImpl;
                ActionMenuItem actionMenuItem = view.getId() == 16908313 ? ActionBarContextView.this.mButton1MenuItem : ActionBarContextView.this.mButton2MenuItem;
                if (ActionBarContextView.this.mActionMode == null || (editActionModeImpl = (EditActionModeImpl) ActionBarContextView.this.mActionMode.get()) == null) {
                    return;
                }
                editActionModeImpl.onMenuItemSelected((MenuBuilder) editActionModeImpl.getMenu(), actionMenuItem);
            }
        };
        this.mCollapseController = new AbsActionBarView.CollapseView();
        this.mMediumController = new AbsActionBarView.CollapseView();
        this.mMovableController = new AbsActionBarView.CollapseView();
        this.mTouchScrolling = false;
        this.mNonTouchScrolling = false;
        this.mPostScroll = new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarContextView.4
            @Override // java.lang.Runnable
            public void run() {
                if (ActionBarContextView.this.mPostScroller.computeScrollOffset()) {
                    ActionBarContextView actionBarContextView = ActionBarContextView.this;
                    actionBarContextView.mPendingHeight = actionBarContextView.mPostScroller.getCurrY() - ActionBarContextView.this.mCollapseTotalHeight;
                    ActionBarContextView.this.requestLayout();
                    if (!ActionBarContextView.this.mPostScroller.isFinished()) {
                        ActionBarContextView.this.postOnAnimation(this);
                    } else if (ActionBarContextView.this.mPostScroller.getCurrY() == ActionBarContextView.this.mCollapseTotalHeight) {
                        ActionBarContextView.this.setExpandState(0);
                    } else if (ActionBarContextView.this.mPostScroller.getCurrY() == ActionBarContextView.this.mCollapseTotalHeight + ActionBarContextView.this.mMovableMainContainer.getMeasuredHeight()) {
                        ActionBarContextView.this.setExpandState(2);
                    }
                }
            }
        };
        this.mPostScroller = new Scroller(context);
        FrameLayout frameLayout = new FrameLayout(context);
        this.mMovableMainContainer = frameLayout;
        frameLayout.setId(miuix.appcompat.R.id.action_bar_movable_container);
        FrameLayout frameLayout2 = this.mMovableMainContainer;
        Resources resources = context.getResources();
        int i2 = miuix.appcompat.R.dimen.miuix_appcompat_action_bar_title_horizontal_padding;
        frameLayout2.setPaddingRelative(resources.getDimensionPixelOffset(i2), context.getResources().getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_title_top_padding), context.getResources().getDimensionPixelOffset(i2), context.getResources().getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_title_bottom_padding));
        this.mMovableMainContainer.setVisibility(0);
        this.mMovableController.attachViews(this.mMovableMainContainer);
        FrameLayout frameLayout3 = new FrameLayout(context);
        this.mMediumContainer = frameLayout3;
        frameLayout3.setId(miuix.appcompat.R.id.action_bar_medium_container);
        this.mMediumContainer.setPaddingRelative(getMediumTitleStartInset(context), 0, getMediumTitleEndInset(context), 0);
        this.mMediumContainer.setVisibility(0);
        this.mMediumController.attachViews(this.mMediumContainer);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, miuix.appcompat.R.styleable.ActionMode, i, 0);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(miuix.appcompat.R.styleable.ActionMode_android_background);
        this.mActionModeBackground = drawable;
        setBackground(drawable);
        this.mTitleStyleRes = typedArrayObtainStyledAttributes.getResourceId(miuix.appcompat.R.styleable.ActionMode_android_titleTextStyle, 0);
        this.mExpandTitleStyleRes = typedArrayObtainStyledAttributes.getResourceId(miuix.appcompat.R.styleable.ActionMode_expandTitleTextStyle, 0);
        this.mMediumTitleStyleRes = typedArrayObtainStyledAttributes.getResourceId(miuix.appcompat.R.styleable.ActionMode_mediumTitleTextStyle, miuix.appcompat.R.style.Miuix_AppCompat_TextAppearance_WindowTitle_Medium);
        this.mTitleMinHeight = typedArrayObtainStyledAttributes.getLayoutDimension(miuix.appcompat.R.styleable.ActionMode_android_minHeight, 0);
        this.mSplitBackground = typedArrayObtainStyledAttributes.getDrawable(miuix.appcompat.R.styleable.ActionMode_android_backgroundSplit);
        this.mButton1MenuItem = new ActionMenuItem(context, 0, R.id.button1, 0, 0, context.getString(R.string.cancel));
        this.mButton2MenuItem = new ActionMenuItem(context, 0, R.id.button2, 0, 0, context.getString(miuix.appcompat.R.string.miuix_appcompat_action_mode_select_all));
        this.mStartWithAnim = typedArrayObtainStyledAttributes.getBoolean(miuix.appcompat.R.styleable.ActionMode_actionModeAnim, true);
        typedArrayObtainStyledAttributes.recycle();
        this.mActionBarButtonMaterial = AttributeResolver.resolveBoolean(context, miuix.appcompat.R.attr.actionBarButtonMaterial, false);
        setClipChildren(false);
        setClipToPadding(false);
    }

    private void addSplitMenuView() {
        this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
        ActionMenuView actionMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
        this.mMenuView = actionMenuView;
        ViewGroup viewGroup = (ViewGroup) actionMenuView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(this.mMenuView);
            this.mSplitView.onActionModeMenuViewRemoved(this.mMenuView);
        }
        ActionMenuView actionMenuView2 = this.mMenuView;
        if (actionMenuView2 != null) {
            actionMenuView2.setSupportBlur(true);
            this.mMenuView.setEnableBlur(this.mSplitView.isEnableBlur());
            this.mMenuView.applyBlur(this.mSplitView.isEnableBlur() && this.mMenuView.getMeasuredWidth() > 0 && this.mMenuView.getMeasuredHeight() > 0);
            this.mMenuView.updateBackground(this.mBackgroundViewApplyBlur);
        }
        boolean z = this.mBottomMenuMode == 3;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        layoutParams.gravity = 81;
        if (z) {
            layoutParams.topMargin = MiuixUIUtils.dp2px(getContext(), 20.0f);
            layoutParams.bottomMargin = MiuixUIUtils.dp2px(getContext(), 24.0f);
        }
        Rect rect = this.mPendingInset;
        if (rect != null) {
            if (z) {
                layoutParams.bottomMargin = calculateSuspendBottomMargin();
                ViewUtils.resetPaddingBottom(this.mMenuView, 0);
            } else {
                ViewUtils.resetPaddingBottom(this.mMenuView, rect.bottom);
            }
        }
        ActionMenuView actionMenuView3 = this.mMenuView;
        if (actionMenuView3 instanceof ResponsiveActionMenuView) {
            ((ResponsiveActionMenuView) actionMenuView3).setSuspendEnabled(z);
        }
        this.mSplitView.addView(this.mMenuView, layoutParams);
        this.mSplitView.onActionModeMenuViewAdded(this.mMenuView);
        requestLayout();
    }

    private void animateLayoutWithProcess(float f) {
        float fMin = 1.0f - Math.min(1.0f, f * 3.0f);
        int i = this.mInnerExpandState;
        if (i == 3) {
            if (fMin > 0.0f) {
                this.mCollapseController.animTo(0.0f, 0, 20, this.mCollapseAnimHideConfig);
            } else {
                this.mCollapseController.animTo(1.0f, 0, 0, this.mCollapseAnimShowConfig);
            }
            this.mMediumController.animTo(0.0f, 0, 0, this.mMovableAnimNormalConfig);
            this.mMovableController.animTo(fMin, 0, 0, this.mMovableAnimNormalConfig);
            return;
        }
        if (i == 2) {
            this.mCollapseController.animTo(0.0f, 0, 20, this.mCollapseAnimHideConfig);
            this.mMediumController.animTo(0.0f, 0, 0, this.mMovableAnimNormalConfig);
            this.mMovableController.animTo(1.0f, 0, 0, this.mMovableAnimNormalConfig);
        } else if (i == 1) {
            this.mCollapseController.animTo(0.0f, 0, 20, this.mCollapseAnimHideConfig);
            this.mMediumController.animTo(1.0f, 0, 0, this.mMovableAnimNormalConfig);
            this.mMovableController.animTo(0.0f, 0, 0, this.mMovableAnimNormalConfig);
        } else if (i == 0) {
            this.mCollapseController.animTo(1.0f, 0, 0, this.mCollapseAnimShowConfig);
            this.mMediumController.animTo(0.0f, 0, 0, this.mMovableAnimNormalConfig);
            this.mMovableController.animTo(0.0f, 0, 0, this.mMovableAnimNormalConfig);
        }
    }

    private void applyFloatingStateToButtons() {
        boolean zIsLightTheme = MiuixUIUtils.isLightTheme(getContext());
        boolean zBooleanValue = this.mFloatingMode.booleanValue();
        ImageView imageView = this.mButton1;
        if (imageView != null) {
            FloatingEffectHelper.updateViewFloatingState(imageView, zBooleanValue, zIsLightTheme, this.mActionBarButtonMaterial);
        }
        ImageView imageView2 = this.mButton2;
        if (imageView2 != null) {
            FloatingEffectHelper.updateViewFloatingState(imageView2, zBooleanValue, zIsLightTheme, this.mActionBarButtonMaterial);
        }
    }

    private void clearBackground() {
        ActionBarContainer actionBarContainer;
        setBackground(null);
        if (!this.mSplitActionBarEnable || (actionBarContainer = this.mSplitView) == null) {
            return;
        }
        actionBarContainer.updateBackgroundInternal(true);
    }

    public static int getMediumTitleEndInset(Context context) {
        return context.getResources().getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_mode_title_button_width) + context.getResources().getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_title_padding_gap);
    }

    public static int getMediumTitleStartInset(Context context) {
        return context.getResources().getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_mode_title_button_width) + context.getResources().getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_title_padding_gap);
    }

    private void resetBackground() {
        ActionBarContainer actionBarContainer;
        setBackground(this.mActionModeBackground);
        if (!this.mSplitActionBarEnable || (actionBarContainer = this.mSplitView) == null) {
            return;
        }
        actionBarContainer.updateBackgroundInternal(false);
    }

    private void setSplitAnimating(boolean z) {
        ActionBarContainer actionBarContainer = this.mSplitView;
        if (actionBarContainer != null) {
            ((ActionBarOverlayLayout) actionBarContainer.getParent()).setAnimating(z);
        }
    }

    @Override // miuix.appcompat.internal.app.widget.ActionModeView
    public void addAnimationListener(ActionModeAnimationListener actionModeAnimationListener) {
        if (actionModeAnimationListener == null) {
            return;
        }
        if (this.mAnimationListeners == null) {
            this.mAnimationListeners = new ArrayList();
        }
        this.mAnimationListeners.add(actionModeAnimationListener);
    }

    @Override // miuix.appcompat.internal.app.widget.ActionModeView
    public void animateToVisibility(boolean z) {
        cancelAnimation();
        setSplitAnimating(this.mStartWithAnim);
        if (!z) {
            if (this.mStartWithAnim) {
                makeContextViewsShowHideWithAnimation(false);
                return;
            } else {
                makeContextViewsShowHide(false);
                return;
            }
        }
        if (!this.mStartWithAnim) {
            makeContextViewsShowHide(true);
        } else {
            setVisibility(0);
            this.mRequestAnimation = true;
        }
    }

    public final void bindActionMenuItemInfo(ActionMenuItem actionMenuItem, CharSequence charSequence) {
        if (actionMenuItem == null) {
            return;
        }
        actionMenuItem.setTitle(charSequence);
    }

    public final void bindButtonInfo(ImageView imageView, int i, CharSequence charSequence) {
        if (imageView == null) {
            return;
        }
        imageView.setVisibility(i == 0 ? 8 : 0);
        imageView.setImageResource(i);
        if (TextUtils.isEmpty(charSequence)) {
            return;
        }
        imageView.setContentDescription(charSequence);
    }

    public final int calculateSuspendBottomMargin() {
        int iDp2px = MiuixUIUtils.dp2px(getContext(), 24.0f);
        int i = this.mPendingInset.bottom;
        return i < iDp2px ? iDp2px : i + MiuixUIUtils.dp2px(getContext(), 8.0f);
    }

    public final boolean canTitleBeShown() {
        boolean z = (!isResizable() && getExpandState() == 0) || this.mTitleView.getPaint().measureText(this.mTitle.toString()) <= ((float) this.mTitleView.getMeasuredWidth());
        if (!ActionBarPolicy.get(getContext()).isTitleEnableEllipsis() || z) {
            return z;
        }
        return true;
    }

    public void cancelAnimation() {
        SpringAnimation springAnimation = this.mVisibilityAnim;
        if (springAnimation != null) {
            springAnimation.cancel();
            this.mVisibilityAnim = null;
        }
        stopSplitMenuAnimation();
        setSplitAnimating(false);
    }

    @Override // miuix.appcompat.internal.app.widget.ActionModeView
    public void closeMode() {
        endAnimation();
        this.mAnimationMode = 2;
    }

    public void endAnimation() {
        SpringAnimation springAnimation = this.mVisibilityAnim;
        if (springAnimation != null) {
            springAnimation.skipToEnd();
            this.mVisibilityAnim = null;
        }
        stopSplitMenuAnimation();
        setSplitAnimating(false);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.MarginLayoutParams(-1, -2);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ViewGroup.MarginLayoutParams(getContext(), attributeSet);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public int getActionBarStyle() {
        return R.attr.actionModeStyle;
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ ActionMenuView getActionMenuView() {
        return super.getActionMenuView();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ int getAnimatedVisibility() {
        return super.getAnimatedVisibility();
    }

    public float getAnimationProgress() {
        return this.mAnimationProgress;
    }

    public final ImageView getButton(int i) {
        if (i == 16908313) {
            return this.mButton1;
        }
        if (i == 16908314) {
            return this.mButton2;
        }
        return null;
    }

    public final ActionMenuItem getButtonMenuItem(int i) {
        if (i == 16908313) {
            return this.mButton1MenuItem;
        }
        if (i == 16908314) {
            return this.mButton2MenuItem;
        }
        return null;
    }

    public CollapseTitle getCollapseTitle() {
        return null;
    }

    public int getCollapsedHeight() {
        return this.mCollapseTotalHeight;
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ int getExpandState() {
        return super.getExpandState();
    }

    public ExpandTitle getExpandTitle() {
        return null;
    }

    public int getExpandedHeight() {
        return this.mExpandTotalHeight;
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ ActionMenuView getMenuView() {
        return super.getMenuView();
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    @Override // miuix.appcompat.internal.app.widget.ActionModeView
    public int getViewHeight() {
        return getCollapsedHeight();
    }

    public final SpringAnimation getViewSpringAnima(View view, float f, float f2, float f3) {
        SpringAnimation springAnimation = new SpringAnimation(view, ViewProperty.ALPHA, f3);
        springAnimation.setStartValue(f2);
        springAnimation.getSpring().setStiffness(f);
        springAnimation.getSpring().setDampingRatio(0.9f);
        springAnimation.setMinimumVisibleChange(0.00390625f);
        return springAnimation;
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public boolean hideOverflowMenu() {
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        return actionMenuPresenter != null && actionMenuPresenter.hideOverflowMenu(false);
    }

    @Override // miuix.appcompat.internal.app.widget.ActionModeView
    public void initForMode(ActionMode actionMode) {
        if (this.mActionMode != null) {
            cancelAnimation();
            killMode();
        }
        initTitle();
        if (this.mTitleView.getEllipsize() == TextUtils.TruncateAt.MARQUEE) {
            this.mTitleView.requestFocus();
        }
        this.mActionMode = new WeakReference(actionMode);
        MenuBuilder menuBuilder = (MenuBuilder) actionMode.getMenu();
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.dismissPopupMenus(false);
        }
        Object parent = getParent();
        while (true) {
            View view = (View) parent;
            if (view instanceof ActionBarOverlayLayout) {
                ActionMenuPresenter actionMenuPresenter2 = new ActionMenuPresenter(getContext(), (ActionBarOverlayLayout) view, miuix.appcompat.R.layout.miuix_appcompat_responsive_action_menu_layout, miuix.appcompat.R.layout.miuix_appcompat_action_mode_menu_item_layout);
                this.mActionMenuPresenter = actionMenuPresenter2;
                actionMenuPresenter2.setReserveOverflow(true);
                this.mActionMenuPresenter.setActionEditMode(true);
                int i = this.mMaxActionMenuItemCount;
                if (i != Integer.MIN_VALUE) {
                    this.mActionMenuPresenter.setItemLimit(i);
                }
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -1);
                menuBuilder.addMenuPresenter(this.mActionMenuPresenter);
                if (this.mSplitActionBarEnable) {
                    addSplitMenuView();
                    return;
                }
                ActionMenuView actionMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
                this.mMenuView = actionMenuView;
                actionMenuView.setBackground(null);
                addView(this.mMenuView, layoutParams);
                return;
            }
            if (!(view.getParent() instanceof View)) {
                throw new IllegalStateException("ActionBarOverlayLayout not found");
            }
            parent = view.getParent();
        }
    }

    public void initTitle() {
        if (this.mTitleLayout == null) {
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(miuix.appcompat.R.layout.miuix_appcompat_action_mode_title_item, (ViewGroup) this, false);
            this.mTitleLayout = linearLayout;
            this.mButton1 = (ImageView) linearLayout.findViewById(R.id.button1);
            this.mButton2 = (ImageView) this.mTitleLayout.findViewById(R.id.button2);
            boolean zIsLightTheme = MiuixUIUtils.isLightTheme(getContext());
            boolean z = !zIsLightTheme;
            int i = zIsLightTheme ? 16777215 : 0;
            ImageView imageView = this.mButton1;
            if (imageView != null) {
                imageView.setBackground(new RoundStateDrawable(i, z));
                this.mButton1.setOnClickListener(this.mOnMenuItemClickListener);
                Folme.useAt(this.mButton1).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setAlpha(0.6f, new ITouchStyle.TouchType[0]).handleTouchOf(this.mButton1, new AnimConfig[0]);
                Folme.useAt(this.mButton1).hover().setFeedbackRadius(60.0f);
                Folme.useAt(this.mButton1).hover().setEffect(IHoverStyle.HoverEffect.FLOATED_WRAPPED).handleHoverOf(this.mButton1, new AnimConfig[0]);
            }
            ImageView imageView2 = this.mButton2;
            if (imageView2 != null) {
                imageView2.setBackground(new RoundStateDrawable(i, z));
                this.mButton2.setOnClickListener(this.mOnMenuItemClickListener);
                Folme.useAt(this.mButton2).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setAlpha(0.6f, new ITouchStyle.TouchType[0]).handleTouchOf(this.mButton2, new AnimConfig[0]);
                Folme.useAt(this.mButton2).hover().setFeedbackRadius(60.0f);
                Folme.useAt(this.mButton2).hover().setEffect(IHoverStyle.HoverEffect.FLOATED_WRAPPED).handleHoverOf(this.mButton2, new AnimConfig[0]);
            }
            Boolean bool = this.mFloatingMode;
            if (bool != null && bool.booleanValue()) {
                ImageView imageView3 = this.mButton1;
                if (imageView3 != null) {
                    FloatingEffectHelper.updateViewFloatingState(imageView3, true, zIsLightTheme, this.mActionBarButtonMaterial);
                }
                ImageView imageView4 = this.mButton2;
                if (imageView4 != null) {
                    FloatingEffectHelper.updateViewFloatingState(imageView4, true, zIsLightTheme, this.mActionBarButtonMaterial);
                }
            }
            TextView textView = (TextView) this.mTitleLayout.findViewById(R.id.title);
            this.mTitleView = textView;
            if (this.mTitleStyleRes != 0) {
                textView.setTextAppearance(getContext(), this.mTitleStyleRes);
            }
            TextView textView2 = new TextView(getContext());
            this.mExpandTitleView = textView2;
            if (this.mExpandTitleStyleRes != 0) {
                textView2.setTextAppearance(getContext(), this.mExpandTitleStyleRes);
                if (RomUtils.getHyperOsVersion() <= 1) {
                    Typography.applyMiSansLight(this.mExpandTitleView);
                }
            }
            TextView textView3 = new TextView(getContext());
            this.mMediumTitleView = textView3;
            if (this.mMediumTitleStyleRes != 0) {
                textView3.setTextAppearance(getContext(), this.mMediumTitleStyleRes);
            }
            this.mMediumTitleView.setSingleLine(true);
            this.mMediumTitleView.setEllipsize(TextUtils.TruncateAt.END);
            this.mMediumTitleView.setGravity(8388627);
            this.mMediumTitleView.setTextAlignment(1);
            this.mMediumTitleView.setId(miuix.appcompat.R.id.action_bar_title_medium);
        }
        this.mTitleView.setText(this.mTitle);
        this.mExpandTitleView.setText(this.mTitle);
        this.mMediumTitleView.setText(this.mTitle);
        this.mMainContainer = this.mTitleLayout;
        this.mCollapseController.attachViews(this.mTitleView);
        boolean zIsEmpty = TextUtils.isEmpty(this.mTitle);
        this.mTitleLayout.setVisibility(!zIsEmpty ? 0 : 8);
        this.mExpandTitleView.setVisibility(!zIsEmpty ? 0 : 8);
        this.mMediumTitleView.setVisibility(zIsEmpty ? 8 : 0);
        if (this.mTitleLayout.getParent() == null) {
            addView(this.mTitleLayout);
        }
        if (this.mExpandTitleView.getParent() == null) {
            this.mExpandTitleView.setId(miuix.appcompat.R.id.action_context_bar_expand_title);
            this.mMovableMainContainer.addView(this.mExpandTitleView);
        }
        if (this.mMovableMainContainer.getParent() == null) {
            addView(this.mMovableMainContainer);
        }
        if (this.mMediumTitleView.getParent() == null) {
            this.mMediumContainer.addView(this.mMediumTitleView, new FrameLayout.LayoutParams(-2, -2, 8388627));
        }
        if (this.mMediumContainer.getParent() == null) {
            addView(this.mMediumContainer);
        }
        int i2 = this.mInnerExpandState;
        if (i2 == 0) {
            this.mCollapseController.setAnimFrom(1.0f, 0, 0);
            this.mMediumController.setAnimFrom(0.0f, 0, 0);
            this.mMovableController.setAnimFrom(0.0f, 0, 0);
        } else if (i2 == 1) {
            this.mCollapseController.setAnimFrom(0.0f, 0, 20);
            this.mMediumController.setAnimFrom(1.0f, 0, 0);
            this.mMovableController.setAnimFrom(0.0f, 0, 0);
        } else if (i2 == 2) {
            this.mCollapseController.setAnimFrom(0.0f, 0, 20);
            this.mMediumController.setAnimFrom(0.0f, 0, 0);
            this.mMovableController.setAnimFrom(1.0f, 0, 0);
        }
    }

    public boolean isAnimating() {
        return this.mIsAnimating;
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public boolean isOverflowMenuShowing() {
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        return actionMenuPresenter != null && actionMenuPresenter.isOverflowMenuShowing();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ boolean isResizable() {
        return super.isResizable();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ boolean isUserSetExpandState() {
        return super.isUserSetExpandState();
    }

    @Override // miuix.appcompat.internal.app.widget.ActionModeView
    public void killMode() {
        removeAllViews();
        List list = this.mAnimationListeners;
        if (list != null) {
            list.clear();
            this.mAnimationListeners = null;
        }
        if (this.mSplitView != null) {
            ActionMenuView actionMenuView = this.mMenuView;
            if (actionMenuView != null) {
                actionMenuView.onWillRemoved();
            }
            this.mSplitView.removeView(this.mMenuView);
            this.mSplitView.onActionModeMenuViewRemoved(this.mMenuView);
        }
        this.mMenuView = null;
        this.mActionMode = null;
    }

    public void makeContextViewsShowHide(boolean z) {
        setAlpha(z ? 1.0f : 0.0f);
        if (!this.mSplitActionBarEnable) {
            onFinishStartActionMode(z);
            return;
        }
        ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) this.mSplitView.getParent();
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView != null) {
            int collapsedHeight = actionMenuView.getCollapsedHeight();
            this.mMenuView.setTranslationY(z ? 0.0f : collapsedHeight);
            if (!z) {
                collapsedHeight = 0;
            }
            actionBarOverlayLayout.animateContentMarginBottomByBottomMenu(collapsedHeight);
            this.mMenuView.setAlpha(z ? 1.0f : 0.0f);
        }
        onFinishStartActionMode(z);
    }

    public void makeContextViewsShowHideWithAnimation(final boolean z) {
        float f;
        float f2;
        final float f3;
        if (z != this.mAnimateToVisible || this.mVisibilityAnim == null) {
            this.mAnimateToVisible = z;
            this.mAnimateStart = false;
            if (z) {
                f = 0.0f;
                f2 = 1.0f;
            } else {
                f = 1.0f;
                f2 = 0.0f;
            }
            SpringAnimation viewSpringAnima = getViewSpringAnima(this, z ? 322.27f : 986.96f, f, f2);
            viewSpringAnima.setStartDelay(z ? 50L : 0L);
            setAlpha(f);
            this.mVisibilityAnim = viewSpringAnima;
            if (!this.mSplitActionBarEnable) {
                final CountDown countDown = new CountDown(1, new CountDown.CountDownCompleteListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarContextView$$ExternalSyntheticLambda0
                    @Override // miuix.appcompat.internal.app.widget.ActionBarContextView.CountDown.CountDownCompleteListener
                    public final void onCountDownComplete() {
                        this.f$0.onAllAnimationsEnd();
                    }
                });
                viewSpringAnima.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarContextView$$ExternalSyntheticLambda1
                    @Override // miuix.animation.physics.DynamicAnimation.OnAnimationEndListener
                    public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z2, float f4, float f5) {
                        countDown.countDown();
                    }
                });
                viewSpringAnima.start();
                return;
            }
            final CountDown countDown2 = new CountDown(2, new CountDown.CountDownCompleteListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarContextView$$ExternalSyntheticLambda0
                @Override // miuix.appcompat.internal.app.widget.ActionBarContextView.CountDown.CountDownCompleteListener
                public final void onCountDownComplete() {
                    this.f$0.onAllAnimationsEnd();
                }
            });
            viewSpringAnima.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarContextView$$ExternalSyntheticLambda2
                @Override // miuix.animation.physics.DynamicAnimation.OnAnimationEndListener
                public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z2, float f4, float f5) {
                    countDown2.countDown();
                }
            });
            viewSpringAnima.start();
            ActionMenuView actionMenuView = this.mMenuView;
            final ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) getParent().getParent();
            final int collapsedHeight = actionMenuView == null ? 0 : actionMenuView.getCollapsedHeight();
            final float fApplyDimension = TypedValue.applyDimension(1, 15.0f, getContext().getResources().getDisplayMetrics());
            if (z) {
                f3 = fApplyDimension;
                fApplyDimension = 0.0f;
            } else {
                f3 = 0.0f;
            }
            if (actionMenuView != null) {
                if (this.mMenuAnimConfig == null) {
                    this.mMenuAnimConfig = new AnimConfig().setEase(-2, 0.82f, 0.3f).setSpecial(ViewProperty.ALPHA, FolmeEase.linear(150L), new float[0]).setSpecial(AbsActionBarView.SELF_BLUR, FolmeEase.linear(150L), new float[0]);
                }
                TransitionListener transitionListener = this.mTransitionListener;
                if (transitionListener != null) {
                    this.mMenuAnimConfig.removeListeners(transitionListener);
                }
                AnimConfig animConfig = this.mMenuAnimConfig;
                TransitionListener transitionListener2 = new TransitionListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarContextView.3
                    public final int traceCookie = MiuixTraceUtils.generateUniqueCookie();

                    @Override // miuix.animation.listener.TransitionListener
                    public void onBegin(Object obj) {
                        MiuixTraceUtils.beginAsyncTrace("MIUIX_Widget_Animation", this.traceCookie);
                        if (ActionBarContextView.this.mAnimateStart) {
                            return;
                        }
                        ActionBarContextView.this.notifyAnimationStart(z);
                        ActionBarContextView.this.mAnimateStart = true;
                        ActionBarContextView.this.mIsAnimating = true;
                    }

                    @Override // miuix.animation.listener.TransitionListener
                    public void onCancel(Object obj) {
                        MiuixTraceUtils.endAsyncTrace("MIUIX_Widget_Animation", this.traceCookie);
                    }

                    @Override // miuix.animation.listener.TransitionListener
                    public void onComplete(Object obj) {
                        ActionBarContextView.this.mIsAnimating = false;
                        countDown2.countDown();
                        MiuixTraceUtils.endAsyncTrace("MIUIX_Widget_Animation", this.traceCookie);
                    }

                    @Override // miuix.animation.listener.TransitionListener
                    public void onUpdate(Object obj, Collection collection) {
                        UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, View.TRANSLATION_Y.getName());
                        if (updateInfoFindByName == null) {
                            return;
                        }
                        float floatValue = updateInfoFindByName.getFloatValue();
                        actionBarOverlayLayout.onMenuStateChanged((int) (collapsedHeight - floatValue), 1);
                        float f4 = fApplyDimension;
                        float f5 = f3;
                        ActionBarContextView.this.notifyAnimationUpdate(z, f4 == f5 ? 1.0f : (floatValue - f5) / (f4 - f5));
                    }
                };
                this.mTransitionListener = transitionListener2;
                animConfig.addListeners(transitionListener2);
                float f4 = z ? 0.0f : 1.0f;
                float f5 = z ? 1.0f : 0.0f;
                float f6 = z ? 50.0f : 0.0f;
                float f7 = z ? 0.0f : 50.0f;
                this.mMenuAnimConfig.setDelay(z ? 100 : 0);
                IStateStyle iStateStyleState = Folme.useAt(actionMenuView).state();
                ViewProperty viewProperty = ViewProperty.TRANSLATION_Y;
                Float fValueOf = Float.valueOf(f3);
                ViewProperty viewProperty2 = ViewProperty.ALPHA;
                Float fValueOf2 = Float.valueOf(f4);
                ViewProperty viewProperty3 = AbsActionBarView.SELF_BLUR;
                iStateStyleState.setTo(viewProperty, fValueOf, viewProperty2, fValueOf2, viewProperty3, Float.valueOf(f6)).to(viewProperty, Float.valueOf(fApplyDimension), viewProperty2, Float.valueOf(f5), viewProperty3, Float.valueOf(f7), this.mMenuAnimConfig);
                actionBarOverlayLayout.onMenuStateChanged(0, 1);
            }
        }
    }

    public void notifyAnimationEnd(boolean z) {
        List list = this.mAnimationListeners;
        if (list == null) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((ActionModeAnimationListener) it.next()).onStop(z);
        }
    }

    public void notifyAnimationStart(boolean z) {
        List list = this.mAnimationListeners;
        if (list == null) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((ActionModeAnimationListener) it.next()).onStart(z);
        }
    }

    public void notifyAnimationUpdate(boolean z, float f) {
        List list = this.mAnimationListeners;
        if (list == null) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((ActionModeAnimationListener) it.next()).onUpdate(z, f);
        }
    }

    public final void onAllAnimationsEnd() {
        ActionMenuView actionMenuView;
        setSplitAnimating(false);
        this.mAnimateStart = false;
        notifyAnimationEnd(this.mAnimateToVisible);
        if (this.mAnimationMode == 2) {
            killMode();
        }
        this.mAnimationMode = 0;
        this.mVisibilityAnim = null;
        setVisibility(this.mAnimateToVisible ? 0 : 8);
        if (this.mSplitView != null && (actionMenuView = this.mMenuView) != null) {
            actionMenuView.setVisibility(this.mAnimateToVisible ? 0 : 8);
        }
        ActionMenuView actionMenuView2 = this.mMenuView;
        if (actionMenuView2 != null) {
            Folme.clean(actionMenuView2);
        }
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        TextView textView;
        super.onConfigurationChanged(configuration);
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(null, miuix.appcompat.R.styleable.ActionMode, getActionBarStyle(), 0);
        this.mTitleMinHeight = typedArrayObtainStyledAttributes.getLayoutDimension(miuix.appcompat.R.styleable.ActionMode_android_minHeight, 0);
        typedArrayObtainStyledAttributes.recycle();
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_title_horizontal_padding);
        this.mMovableMainContainer.setPaddingRelative(dimensionPixelOffset, getResources().getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_title_top_padding), dimensionPixelOffset, getResources().getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_title_bottom_padding));
        FrameLayout frameLayout = this.mMediumContainer;
        if (frameLayout != null) {
            frameLayout.setPaddingRelative(getMediumTitleStartInset(getContext()), 0, getMediumTitleEndInset(getContext()), 0);
        }
        setPaddingRelative(AttributeResolver.resolveDimensionPixelSize(getContext(), miuix.appcompat.R.attr.actionBarPaddingStart), getPaddingTop(), AttributeResolver.resolveDimensionPixelSize(getContext(), miuix.appcompat.R.attr.actionBarPaddingEnd), getPaddingBottom());
        if (this.mTitleStyleRes == 0 || (textView = this.mTitleView) == null) {
            return;
        }
        textView.setTextAppearance(getContext(), this.mTitleStyleRes);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.hideOverflowMenu(false);
            this.mActionMenuPresenter.hideSubMenus();
        }
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public void onExpandStateChanged(int i, int i2) {
        AbsActionBarView.CollapseView collapseView;
        if (i == 3) {
            this.mPendingHeight = 0;
            if (!this.mPostScroller.isFinished()) {
                this.mPostScroller.forceFinished(true);
            }
        }
        if (i2 == 3 && (collapseView = this.mMovableController) != null) {
            collapseView.setVisibility(0);
        }
        if (i2 == 2) {
            if (this.mMovableMainContainer.getAlpha() > 0.0f) {
                AbsActionBarView.CollapseView collapseView2 = this.mCollapseController;
                if (collapseView2 != null) {
                    collapseView2.setAnimFrom(0.0f, 0, 20, true);
                }
                AbsActionBarView.CollapseView collapseView3 = this.mMovableController;
                if (collapseView3 != null) {
                    collapseView3.setAnimFrom(1.0f, 0, 0, true);
                }
            }
            AbsActionBarView.CollapseView collapseView4 = this.mMediumController;
            if (collapseView4 != null) {
                collapseView4.setAnimFrom(0.0f, 0, 0, true);
                this.mMediumController.setVisibility(8);
            }
            AbsActionBarView.CollapseView collapseView5 = this.mMovableController;
            if (collapseView5 != null) {
                collapseView5.setVisibility(0);
            }
        }
        if (i2 == 1) {
            AbsActionBarView.CollapseView collapseView6 = this.mCollapseController;
            if (collapseView6 != null) {
                collapseView6.setAnimFrom(0.0f, 0, 20, true);
            }
            AbsActionBarView.CollapseView collapseView7 = this.mMediumController;
            if (collapseView7 != null) {
                collapseView7.setAnimFrom(1.0f, 0, 0, true);
                this.mMediumController.setVisibility(0);
                this.mMediumController.onShow();
            }
            AbsActionBarView.CollapseView collapseView8 = this.mMovableController;
            if (collapseView8 != null) {
                collapseView8.setAnimFrom(0.0f, 0, 0, true);
                this.mMovableController.setVisibility(8);
            }
        }
        if (i2 != 0) {
            this.mPendingHeight = getHeight() - this.mCollapseTotalHeight;
            return;
        }
        AbsActionBarView.CollapseView collapseView9 = this.mCollapseController;
        if (collapseView9 != null) {
            collapseView9.setAnimFrom(1.0f, 0, 0, true);
            this.mCollapseController.setVisibility(0);
            this.mCollapseController.onShow();
        }
        AbsActionBarView.CollapseView collapseView10 = this.mMediumController;
        if (collapseView10 != null) {
            collapseView10.setAnimFrom(0.0f, 0, 0, true);
            this.mMediumController.setVisibility(8);
        }
        AbsActionBarView.CollapseView collapseView11 = this.mMovableController;
        if (collapseView11 != null) {
            collapseView11.setAnimFrom(0.0f, 0, 0, true);
            this.mMovableController.setVisibility(8);
        }
    }

    public final void onFinishStartActionMode(boolean z) {
        ActionMenuView actionMenuView;
        notifyAnimationEnd(z);
        setVisibility(z ? 0 : 8);
        if (this.mSplitView == null || (actionMenuView = this.mMenuView) == null) {
            return;
        }
        actionMenuView.setVisibility(z ? 0 : 8);
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0054  */
    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredHeight;
        int i5 = (int) ((i3 - i) / getContext().getResources().getDisplayMetrics().density);
        int i6 = this.mInnerExpandState;
        if (i6 != 3) {
            if (i6 == 2) {
                measuredHeight = this.mMovableMainContainer.getMeasuredHeight();
            } else {
                measuredHeight = 0;
            }
            int i7 = i4 - i2;
            onLayoutCollapseViews(i, i2, i3, i4 - measuredHeight);
            onLayoutExpandViews(z, i, i7 - measuredHeight, i3, i7);
            float fMin = Math.min(1.0f, (this.mMovableMainContainer.getMeasuredHeight() - measuredHeight) / this.mMovableMainContainer.getMeasuredHeight());
            animateLayoutWithProcess(fMin);
            this.mLastProcess = fMin;
            this.mIsInWideMode = i5 > 600;
        }
        measuredHeight = this.mPendingHeight;
        int i8 = i4 - i2;
        onLayoutCollapseViews(i, i2, i3, i4 - measuredHeight);
        onLayoutExpandViews(z, i, i8 - measuredHeight, i3, i8);
        float fMin2 = Math.min(1.0f, (this.mMovableMainContainer.getMeasuredHeight() - measuredHeight) / this.mMovableMainContainer.getMeasuredHeight());
        animateLayoutWithProcess(fMin2);
        this.mLastProcess = fMin2;
        this.mIsInWideMode = i5 > 600;
    }

    public final void onLayoutCollapseViews(int i, int i2, int i3, int i4) {
        ActionBarContextView actionBarContextView;
        int paddingStart = getPaddingStart();
        int i5 = i4 - i2;
        int measuredHeight = (i5 - this.mMainContainer.getMeasuredHeight()) / 2;
        if (this.mMainContainer.getVisibility() != 8) {
            View view = this.mMainContainer;
            actionBarContextView = this;
            ViewUtils.layoutChildView(actionBarContextView, view, paddingStart, measuredHeight, paddingStart + view.getMeasuredWidth(), measuredHeight + this.mMainContainer.getMeasuredHeight());
        } else {
            actionBarContextView = this;
        }
        FrameLayout frameLayout = actionBarContextView.mMediumContainer;
        if (frameLayout != null && frameLayout.getVisibility() != 8 && actionBarContextView.mMediumContainer.getChildCount() > 0) {
            int measuredHeight2 = actionBarContextView.mMediumContainer.getMeasuredHeight();
            int i6 = (i5 - measuredHeight2) / 2;
            FrameLayout frameLayout2 = actionBarContextView.mMediumContainer;
            ViewUtils.layoutChildView(actionBarContextView, frameLayout2, paddingStart, i6, paddingStart + frameLayout2.getMeasuredWidth(), i6 + measuredHeight2);
        }
        int paddingEnd = (i3 - i) - actionBarContextView.getPaddingEnd();
        ActionMenuView actionMenuView = actionBarContextView.mMenuView;
        if (actionMenuView != null && actionMenuView.getParent() == actionBarContextView) {
            actionBarContextView.positionChildInverse(actionBarContextView.mMenuView, paddingEnd, 0, i5, actionBarContextView.mButtonCenterVerticalEnabled && i5 > actionBarContextView.mTitleMinHeight);
        }
        if (actionBarContextView.mRequestAnimation) {
            actionBarContextView.mAnimationMode = 1;
            actionBarContextView.makeContextViewsShowHideWithAnimation(true);
            actionBarContextView.mRequestAnimation = false;
        } else if (actionBarContextView.mMenuView != null) {
            ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) actionBarContextView.getParent().getParent();
            if (actionBarOverlayLayout.isBottomAnimating()) {
                return;
            }
            actionBarOverlayLayout.onMenuStateChanged(actionBarContextView.mMenuView.getCollapsedHeight(), 1);
        }
    }

    public void onLayoutExpandViews(boolean z, int i, int i2, int i3, int i4) {
        FrameLayout frameLayout = this.mMovableMainContainer;
        if (frameLayout == null || frameLayout.getVisibility() != 0 || this.mInnerExpandState == 0) {
            return;
        }
        FrameLayout frameLayout2 = this.mMovableMainContainer;
        frameLayout2.layout(i, i4 - frameLayout2.getMeasuredHeight(), i3, i4);
        if (ViewUtils.isLayoutRtl(this)) {
            i = i3 - this.mMovableMainContainer.getMeasuredWidth();
        }
        Rect rect = new Rect();
        rect.set(i, this.mMovableMainContainer.getMeasuredHeight() - (i4 - i2), this.mMovableMainContainer.getMeasuredWidth() + i, this.mMovableMainContainer.getMeasuredHeight());
        this.mMovableMainContainer.setClipBounds(rect);
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int iMax;
        int size = View.MeasureSpec.getSize(i);
        int i3 = this.mTitleMaxHeight;
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec((i3 > 0 ? i3 : View.MeasureSpec.getSize(i2)) - paddingTop, Integer.MIN_VALUE);
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView == null || actionMenuView.getParent() != this) {
            iMax = 0;
        } else {
            paddingLeft = measureChildView(this.mMenuView, paddingLeft, iMakeMeasureSpec, 0);
            iMax = this.mMenuView.getMeasuredHeight();
        }
        if (this.mMainContainer.getVisibility() != 8) {
            this.mMainContainer.measure(View.MeasureSpec.makeMeasureSpec(paddingLeft, 1073741824), iMakeMeasureSpec);
            iMax = Math.max(iMax, this.mMainContainer.getMeasuredHeight());
            TextView textView = this.mTitleView;
            if (textView != null) {
                textView.setVisibility(canTitleBeShown() ? 0 : 4);
            }
        }
        FrameLayout frameLayout = this.mMediumContainer;
        if (frameLayout != null && frameLayout.getVisibility() != 8 && this.mMediumContainer.getChildCount() > 0) {
            this.mMediumContainer.measure(View.MeasureSpec.makeMeasureSpec(paddingLeft, 1073741824), iMakeMeasureSpec);
            if (this.mInnerExpandState == 1) {
                iMax = Math.max(iMax, this.mMediumContainer.getMeasuredHeight());
            }
        }
        if (this.mMovableMainContainer.getVisibility() != 8) {
            this.mMovableMainContainer.measure(View.MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, 0));
        }
        if (i3 <= 0) {
            this.mCollapseTotalHeight = iMax > 0 ? Math.max(iMax, this.mTitleMinHeight) + this.mContentInset : 0;
        } else if (iMax >= i3) {
            this.mCollapseTotalHeight = i3 + this.mContentInset;
        }
        int measuredHeight = this.mCollapseTotalHeight + this.mMovableMainContainer.getMeasuredHeight();
        this.mExpandTotalHeight = measuredHeight;
        int i4 = this.mInnerExpandState;
        if (i4 == 3) {
            setMeasuredDimension(size, this.mCollapseTotalHeight + this.mPendingHeight);
        } else if (i4 == 2) {
            setMeasuredDimension(size, measuredHeight);
        } else {
            setMeasuredDimension(size, this.mCollapseTotalHeight);
        }
    }

    public void onNestedPreScroll(View view, int i, int i2, int[] iArr, int i3, int[] iArr2) {
        int i4;
        if (isResizable()) {
            int height = getHeight();
            if (i2 <= 0 || height <= (i4 = this.mCollapseTotalHeight)) {
                return;
            }
            int i5 = height - i2;
            int i6 = this.mPendingHeight;
            if (i5 >= i4) {
                this.mPendingHeight = i6 - i2;
            } else {
                this.mPendingHeight = 0;
            }
            iArr[1] = iArr[1] + i2;
            if (this.mPendingHeight != i6) {
                if (this.mInnerExpandState != 3) {
                    if (!this.mPostScroller.isFinished()) {
                        this.mPostScroller.forceFinished(true);
                    }
                    setExpandState(3);
                }
                iArr2[1] = i2;
                requestLayout();
            }
        }
    }

    public void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5, int[] iArr, int[] iArr2) {
        if (isResizable()) {
            int measuredHeight = this.mMovableMainContainer.getMeasuredHeight();
            int i6 = this.mCollapseTotalHeight + measuredHeight;
            int height = getHeight();
            if (i4 >= 0 || height >= i6) {
                return;
            }
            int i7 = this.mPendingHeight;
            if (height - i4 <= i6) {
                this.mPendingHeight = i7 - i4;
                iArr[1] = iArr[1] + i4;
            } else {
                this.mPendingHeight = measuredHeight;
                iArr[1] = iArr[1] + (-(i6 - height));
            }
            if (this.mPendingHeight != i7) {
                if (this.mInnerExpandState != 3) {
                    if (!this.mPostScroller.isFinished()) {
                        this.mPostScroller.forceFinished(true);
                    }
                    setExpandState(3);
                }
                iArr2[1] = i4;
                requestLayout();
            }
        }
    }

    public void onNestedScrollAccepted(View view, View view2, int i, int i2) {
        if (isResizable()) {
            if (i2 == 0) {
                this.mTouchScrolling = true;
            } else {
                this.mNonTouchScrolling = true;
            }
            if (!this.mPostScroller.isFinished()) {
                this.mPostScroller.forceFinished(true);
                Runnable runnable = this.mPostScroll;
                if (runnable != null) {
                    removeCallbacks(runnable);
                }
            }
            setExpandState(3);
        }
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setTitle(savedState.title);
        setButton(R.id.button2, savedState.defaultButtonText);
        if (savedState.isOverflowOpen) {
            postShowOverflowMenu();
        }
        setExpandState(savedState.expandState);
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.isOverflowOpen = isOverflowMenuShowing();
        savedState.title = getTitle();
        ActionMenuItem actionMenuItem = this.mButton2MenuItem;
        if (actionMenuItem != null) {
            savedState.defaultButtonText = actionMenuItem.getTitle();
        }
        int i = this.mInnerExpandState;
        if (i == 3) {
            savedState.expandState = 0;
            return savedState;
        }
        savedState.expandState = i;
        return savedState;
    }

    public boolean onStartNestedScroll(View view, View view2, int i, int i2) {
        return true;
    }

    public void onStopNestedScroll(View view, int i) {
        if (isResizable()) {
            int measuredHeight = this.mMovableMainContainer.getMeasuredHeight();
            int height = getHeight();
            if (this.mTouchScrolling) {
                this.mTouchScrolling = false;
                if (this.mNonTouchScrolling) {
                    return;
                }
            } else if (!this.mNonTouchScrolling) {
                return;
            } else {
                this.mNonTouchScrolling = false;
            }
            int i2 = this.mPendingHeight;
            if (i2 == 0) {
                setExpandState(0);
                return;
            }
            if (i2 == measuredHeight) {
                setExpandState(2);
                return;
            }
            int i3 = this.mCollapseTotalHeight;
            if (height > (measuredHeight / 2) + i3) {
                this.mPostScroller.startScroll(0, height, 0, (i3 + measuredHeight) - height);
            } else {
                this.mPostScroller.startScroll(0, height, 0, i3 - height);
            }
            this.mHandler.postDelayed(this.mPostAnimationRunnable, 17L);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void postShowOverflowMenu() {
        super.postShowOverflowMenu();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public void refreshBottomMenu() {
        if (!this.mSplitActionBarEnable || this.mActionMenuPresenter == null || this.mActionMode == null) {
            return;
        }
        addSplitMenuView();
    }

    public void refreshFloatingState() {
        if (this.mFloatingMode != null) {
            applyFloatingStateToButtons();
        }
    }

    public void setActionBarView(ActionBarView actionBarView) {
        this.mActionBarView = actionBarView;
    }

    public void setActionModeAnim(boolean z) {
        this.mStartWithAnim = z;
    }

    public void setAnimationProgress(float f) {
        this.mAnimationProgress = f;
        notifyAnimationUpdate(this.mAnimateToVisible, f);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setBottomMenuMode(int i) {
        super.setBottomMenuMode(i);
    }

    public void setButton(int i, CharSequence charSequence) {
        setButton(i, null, charSequence, 0);
    }

    public void setButton(int i, CharSequence charSequence, int i2) {
        initTitle();
        ImageView button = getButton(i);
        bindButtonInfo(button, i2, null);
        bindActionMenuItemInfo(getButtonMenuItem(i), charSequence);
        if (i2 != 0) {
            setButtonContentDescription(button, i2);
        }
    }

    public void setButton(int i, CharSequence charSequence, int i2, CharSequence charSequence2, int i3) {
        initTitle();
        ImageView button = getButton(i);
        bindButtonInfo(button, i3, charSequence);
        bindActionMenuItemInfo(getButtonMenuItem(i), charSequence2);
        if (button != null) {
            button.setImportantForAccessibility(i2);
        }
    }

    public void setButton(int i, CharSequence charSequence, CharSequence charSequence2, int i2) {
        setButton(i, charSequence, 0, charSequence2, i2);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setButtonCenterVerticalEnabled(boolean z) {
        super.setButtonCenterVerticalEnabled(z);
    }

    public final void setButtonContentDescription(ImageView imageView, int i) {
        if (imageView == null) {
            return;
        }
        if (miuix.appcompat.R.drawable.miuix_appcompat_action_mode_title_button_cancel == i || miuix.appcompat.R.drawable.miuix_action_icon_cancel_light == i || miuix.appcompat.R.drawable.miuix_action_icon_cancel_dark == i) {
            imageView.setContentDescription(getResources().getString(miuix.appcompat.R.string.miuix_appcompat_cancel_description));
            return;
        }
        if (miuix.appcompat.R.drawable.miuix_appcompat_action_mode_title_button_confirm == i || miuix.appcompat.R.drawable.miuix_action_icon_immersion_confirm_light == i || miuix.appcompat.R.drawable.miuix_action_icon_immersion_confirm_dark == i) {
            imageView.setContentDescription(getResources().getString(miuix.appcompat.R.string.miuix_appcompat_confirm_description));
            return;
        }
        if (miuix.appcompat.R.drawable.miuix_appcompat_action_mode_title_button_select_all == i || miuix.appcompat.R.drawable.miuix_action_icon_select_all_light == i || miuix.appcompat.R.drawable.miuix_action_icon_select_all_dark == i) {
            imageView.setContentDescription(getResources().getString(miuix.appcompat.R.string.miuix_appcompat_select_all_description));
            return;
        }
        if (miuix.appcompat.R.drawable.miuix_appcompat_action_mode_title_button_deselect_all == i || miuix.appcompat.R.drawable.miuix_action_icon_deselect_all_light == i || miuix.appcompat.R.drawable.miuix_action_icon_deselect_all_dark == i) {
            imageView.setContentDescription(getResources().getString(miuix.appcompat.R.string.miuix_appcompat_deselect_all_description));
        } else if (miuix.appcompat.R.drawable.miuix_appcompat_action_mode_title_button_delete == i || miuix.appcompat.R.drawable.miuix_action_icon_immersion_delete_light == i || miuix.appcompat.R.drawable.miuix_action_icon_immersion_delete_dark == i) {
            imageView.setContentDescription(getResources().getString(miuix.appcompat.R.string.miuix_appcompat_delete_description));
        }
    }

    public void setContentInset(int i) {
        this.mContentInset = i;
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setExpandState(int i) {
        super.setExpandState(i);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setExpandState(int i, boolean z, boolean z2) {
        super.setExpandState(i, z, z2);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setPendingInset(Rect rect) {
        super.setPendingInset(rect);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setResizable(boolean z) {
        super.setResizable(z);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public void setSplitActionBar(boolean z) {
        if (this.mSplitActionBarEnable != z) {
            if (this.mActionMenuPresenter != null) {
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -1);
                if (z) {
                    this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
                    layoutParams.width = -1;
                    layoutParams.height = -2;
                    layoutParams.gravity = this.mIsInWideMode ? 17 : 80;
                    ActionMenuView actionMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
                    this.mMenuView = actionMenuView;
                    actionMenuView.setBackground(this.mSplitBackground);
                    ViewGroup viewGroup = (ViewGroup) this.mMenuView.getParent();
                    if (viewGroup != null) {
                        viewGroup.removeView(this.mMenuView);
                        this.mSplitView.onActionModeMenuViewRemoved(this.mMenuView);
                    }
                    this.mSplitView.addView(this.mMenuView, layoutParams);
                    this.mSplitView.onActionModeMenuViewAdded(this.mMenuView);
                } else {
                    ActionMenuView actionMenuView2 = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
                    this.mMenuView = actionMenuView2;
                    actionMenuView2.setBackground(null);
                    ViewGroup viewGroup2 = (ViewGroup) this.mMenuView.getParent();
                    if (viewGroup2 != null) {
                        viewGroup2.removeView(this.mMenuView);
                    }
                    addView(this.mMenuView, layoutParams);
                }
            }
            super.setSplitActionBar(z);
        }
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setSplitView(ActionBarContainer actionBarContainer) {
        super.setSplitView(actionBarContainer);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setSplitWhenNarrow(boolean z) {
        super.setSplitWhenNarrow(z);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setSubTitleClickListener(View.OnClickListener onClickListener) {
        super.setSubTitleClickListener(onClickListener);
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        initTitle();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setTitleClickable(boolean z) {
        super.setTitleClickable(z);
    }

    public void setTitleOptional(boolean z) {
        if (z != this.mTitleOptional) {
            requestLayout();
        }
        this.mTitleOptional = z;
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView, android.view.View
    public /* bridge */ /* synthetic */ void setVisibility(int i) {
        super.setVisibility(i);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public boolean showOverflowMenu() {
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        return actionMenuPresenter != null && actionMenuPresenter.showOverflowMenu();
    }

    public final void stopSplitMenuAnimation() {
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView != null) {
            Folme.useAt(this.mMenuView).state().setTo(new AnimState().add(ViewProperty.TRANSLATION_Y, this.mAnimateToVisible ? 0 : actionMenuView.getCollapsedHeight()));
        }
    }

    public void updateActionButtonFloatingState(boolean z) {
        Boolean bool = this.mFloatingMode;
        if (bool == null || bool.booleanValue() != z) {
            this.mFloatingMode = Boolean.valueOf(z);
            applyFloatingStateToButtons();
        }
    }

    public void updateBackground(boolean z) {
        this.mBackgroundViewApplyBlur = z;
        if (z) {
            clearBackground();
        } else {
            resetBackground();
        }
    }
}
