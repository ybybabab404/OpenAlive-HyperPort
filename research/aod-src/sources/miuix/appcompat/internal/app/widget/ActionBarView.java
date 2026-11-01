package miuix.appcompat.internal.app.widget;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.CollapsibleActionView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import com.miui.maml.folme.AnimatedProperty;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.IHoverStyle;
import miuix.animation.IStateStyle;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.ViewProperty;
import miuix.appcompat.app.ActionBarTransitionListener;
import miuix.appcompat.app.TextViewDrawableConfig;
import miuix.appcompat.internal.app.NavigatorSwitchPresenter;
import miuix.appcompat.internal.app.widget.actionbar.CollapseTitle;
import miuix.appcompat.internal.app.widget.actionbar.ExpandTitle;
import miuix.appcompat.internal.app.widget.actionbar.MediumTitle;
import miuix.appcompat.internal.util.ActionBarViewFactory;
import miuix.appcompat.internal.util.FloatingEffectHelper;
import miuix.appcompat.internal.view.ActionBarPolicy;
import miuix.appcompat.internal.view.menu.MenuBuilder;
import miuix.appcompat.internal.view.menu.MenuItemImpl;
import miuix.appcompat.internal.view.menu.MenuPresenter;
import miuix.appcompat.internal.view.menu.SubMenuBuilder;
import miuix.appcompat.internal.view.menu.action.ActionMenuItem;
import miuix.appcompat.internal.view.menu.action.ActionMenuPresenter;
import miuix.appcompat.internal.view.menu.action.ActionMenuView;
import miuix.appcompat.internal.view.menu.action.EndActionMenuPresenter;
import miuix.appcompat.internal.view.menu.action.EndActionMenuView;
import miuix.appcompat.internal.view.menu.action.HyperActionMenuPresenter;
import miuix.appcompat.internal.view.menu.action.HyperSplitActionMenuPresenter;
import miuix.appcompat.internal.view.menu.action.ResponsiveActionMenuView;
import miuix.container.ExtraPaddingPolicy;
import miuix.core.util.EnvStateManager;
import miuix.core.util.HyperMaterialUtils;
import miuix.core.util.MiuiBlurUtils;
import miuix.core.util.MiuixUIUtils;
import miuix.device.DeviceUtils;
import miuix.internal.util.AttributeResolver;
import miuix.internal.util.ViewUtils;
import miuix.transition.R;
import miuix.view.ActionModeAnimationListener;

/* JADX INFO: loaded from: classes2.dex */
public class ActionBarView extends AbsActionBarView implements ActionModeAnimationListener {
    public static final ViewProperty SELF_BLUR = new ViewProperty("self_blur", 1.0f) { // from class: miuix.appcompat.internal.app.widget.ActionBarView.17
        @Override // miuix.animation.property.FloatProperty
        public float getValue(View view) {
            Object tag = view.getTag(R.id.miuix_animation_tag_view_self_blur);
            if (tag == null) {
                return 0.0f;
            }
            return ((Float) tag).floatValue();
        }

        @Override // miuix.animation.property.FloatProperty
        public void setValue(View view, float f) {
            view.setTag(R.id.miuix_animation_tag_view_self_blur, Float.valueOf(f));
            int i = (int) f;
            if (MiuiBlurUtils.setSelfBlur(view, i, null, 128)) {
                return;
            }
            MiuiBlurUtils.setSelfBlur(view, i, null);
        }
    };
    public AnimConfig enterAnimConfig;
    public AnimConfig exitAnimConfig;
    public boolean mActionBarButtonMaterial;
    public AnimState mActionBarHideState;
    public boolean mActionBarSelfBlurEnabled;
    public AnimState mActionBarShowState;
    public boolean mAnimateStart;
    public boolean mApplyBgBlur;
    public boolean mAutoTranslate;
    public OnBackInvokedCallback mBackInvokedCallback;
    public OnBackInvokedDispatcher mBackInvokedDispatcher;
    public TransitionListener mBottomMenuTransitionListener;
    public ActionBar.OnNavigationListener mCallback;
    public View mCenterView;
    public TransitionListener mCollapseAnimHideConfigListener;
    public TransitionListener mCollapseAnimShowConfigListener;
    public final AbsActionBarView.CollapseView mCollapseController;
    public FrameLayout mCollapseCustomContainer;
    public int mCollapseSecondaryTabHeight;
    public final int mCollapseSubtitleStyleRes;
    public FrameLayout mCollapseTabContainer;
    public ScrollingTabContainerView mCollapseTabs;
    public CollapseTitle mCollapseTitle;
    public boolean mCollapseTitleShowable;
    public int mCollapseTitleStyleRes;
    public int mCollapseTotalHeight;
    public boolean mCollapsedTitleVisible;
    public Context mContext;
    public View mCustomNavView;
    public final TextWatcher mCustomTitleWatcher;
    public float mDensity;
    public int mDisplayOptions;
    public boolean mDoContainerShowAnimInFinishActionMode;
    public int mEndActionMenuItemLimit;
    public ActionMenuPresenter mEndActionMenuPresenter;
    public MenuBuilder mEndMenu;
    public ActionMenuView mEndMenuView;
    public View mEndView;
    public int mExpandSubtitlePaddingBottom;
    public int mExpandTabTopPadding;
    public ScrollingTabContainerView mExpandTabs;
    public ExpandTitle mExpandTitle;
    public int mExpandTitlePaddingBottom;
    public boolean mExpandTitleVisible;
    public int mExpandTotalHeight;
    public View mExpandedActionView;
    public final View.OnClickListener mExpandedActionViewUpListener;
    public HomeView mExpandedHomeLayout;
    public ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    public int mExtraPadding;
    public ExtraPaddingPolicy mExtraPaddingPolicy;
    public Boolean mFloatingMode;
    public boolean mForceShowSearchIcon;
    public final Handler mHandler;
    public boolean mHasNavigatorSwitchView;
    public boolean mHasStartView;
    public Drawable mHomeAsUpIndicatorDrawable;
    public int mHomeAsUpIndicatorResId;
    public HomeView mHomeLayout;
    public final int mHomeResId;
    public Drawable mIcon;
    public int mIconLogoInitIndicator;
    public boolean mInActionMode;
    public boolean mInActionModeAnimating;
    public boolean mInSearchMode;
    public ProgressBar mIndeterminateProgressView;
    public CharSequence mInitCustomTitle;
    public boolean mInitialA11yFocusRequested;
    public boolean mIsBottomMenuVisible;
    public boolean mIsCollapseTitleShowingOnResizing;
    public boolean mIsCollapsed;
    public int mItemPadding;
    public float mLastResizingProcess;
    public LifecycleOwner mLifecycleOwner;
    public LinearLayout mListNavLayout;
    public Drawable mLogo;
    public ActionMenuItem mLogoNavItem;
    public FrameLayout mMainContainer;
    public FrameLayout mMediumContainer;
    public final AbsActionBarView.CollapseView mMediumController;
    public int mMediumSubtitleStyleRes;
    public MediumTitle mMediumTitle;
    public int mMediumTitlePaddingStartNoUp;
    public int mMediumTitleStyleRes;
    public AnimConfig mMenuAnimConfig;
    public TransitionListener mMovableAlphaShowListener;
    public TransitionListener mMovableAnimAlphaListener;
    public final AbsActionBarView.CollapseView mMovableController;
    public FrameLayout mMovableMainContainer;
    public int mMovableSecondaryTabHeight;
    public FrameLayout mMovableTabContainer;
    public final AdapterView.OnItemSelectedListener mNavItemSelectedListener;
    public int mNavigationMode;
    public View mNavigatorSwitch;
    public final int mNavigatorSwitchResId;
    public boolean mNeedRequestLayoutOnExpandTitleShowing;
    public boolean mNonTouchScrolling;
    public boolean mOptionalIconsVisible;
    public MenuBuilder mOptionsMenu;
    public boolean mPendingCreated;
    public int mPendingHeight;
    public Runnable mPendingTitleUpIndicatorUpdate;
    public final Runnable mPostAnimationRunnable;
    public Runnable mPostScroll;
    public final Scroller mPostScroller;
    public int mProgressBarPadding;
    public ProgressBar mProgressView;
    public boolean mRequestInitialA11yFocusEnabled;
    public Runnable mScheduleBottomMenuRunnable;
    public MenuItem mSearchIconMenuItem;
    public View mSearchView;
    public int mSearchViewMargin;
    public int mSearchViewMaxWidth;
    public OnSearchViewStyleChangeListener mSearchViewStyleChangeListener;
    public SecondaryTabBar mSecondaryCollapseTabs;
    public SecondaryTabBar mSecondaryExpandTabs;
    public int mSecondaryTabBottomPadding;
    public int mSecondaryTabTopPadding;
    public Spinner mSpinner;
    public SpinnerAdapter mSpinnerAdapter;
    public View mStartView;
    public IStateStyle mStateChangeAnimStateStyle;
    public final View.OnClickListener mSubTitleClickListener;
    public CharSequence mSubtitle;
    public boolean mTabsExit;
    public CharSequence mTitle;
    public boolean mTitleCenter;
    public final View.OnClickListener mTitleClickListener;
    public int mTitleGapPaddingStart;
    public int mTitleMinWidth;
    public ActionMenuItem mTitleNavItem;
    public View mTitleUpView;
    public int mTitleUpViewMarginEnd;
    public int mTitleUpViewMarginStart;
    public boolean mTouchScrolling;
    public int mTransitionTarget;
    public int mUncollapsePaddingH;
    public int mUncollapseTabPaddingH;
    public final View.OnClickListener mUpClickListener;
    public boolean mUserSetEndActionMenuItemLimit;
    public boolean mUserTitle;
    public Window.Callback mWindowCallback;
    public int mWindowWidthThreshold;
    public View.OnLayoutChangeListener mWindowWidthWatcher;

    public class ExpandedActionViewMenuPresenter implements MenuPresenter {
        public MenuItemImpl mCurrentExpandedItem;
        public MenuBuilder mMenu;

        public ExpandedActionViewMenuPresenter() {
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter
        public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
            KeyEvent.Callback callback = ActionBarView.this.mExpandedActionView;
            if (callback instanceof CollapsibleActionView) {
                ((CollapsibleActionView) callback).onActionViewCollapsed();
            }
            ActionBarView actionBarView = ActionBarView.this;
            actionBarView.removeView(actionBarView.mExpandedActionView);
            ActionBarView actionBarView2 = ActionBarView.this;
            actionBarView2.removeView(actionBarView2.mExpandedHomeLayout);
            ActionBarView actionBarView3 = ActionBarView.this;
            actionBarView3.mExpandedActionView = null;
            if ((actionBarView3.mDisplayOptions & 2) != 0) {
                ActionBarView.this.mHomeLayout.setVisibility(0);
            }
            if ((ActionBarView.this.mDisplayOptions & 8) != 0) {
                if (ActionBarView.this.mCollapseTitle == null) {
                    ActionBarView.this.initTitle();
                } else {
                    ActionBarView.this.setTitleVisibility(true);
                }
            }
            if (ActionBarView.this.mCollapseTabs != null && ActionBarView.this.mNavigationMode == 2) {
                ActionBarView.this.mCollapseTabs.setVisibility(0);
            }
            if (ActionBarView.this.mExpandTabs != null && ActionBarView.this.mNavigationMode == 2) {
                ActionBarView.this.mExpandTabs.setVisibility(0);
            }
            if (ActionBarView.this.mSecondaryCollapseTabs != null && ActionBarView.this.mNavigationMode == 2) {
                ActionBarView.this.mSecondaryCollapseTabs.asViewGroup().setVisibility(0);
            }
            if (ActionBarView.this.mSecondaryExpandTabs != null && ActionBarView.this.mNavigationMode == 2) {
                ActionBarView.this.mSecondaryExpandTabs.asViewGroup().setVisibility(0);
            }
            if (ActionBarView.this.mSpinner != null && ActionBarView.this.mNavigationMode == 1) {
                ActionBarView.this.mSpinner.setVisibility(0);
            }
            if (ActionBarView.this.mCustomNavView != null && (ActionBarView.this.mDisplayOptions & 16) != 0) {
                ActionBarView.this.mCustomNavView.setVisibility(0);
            }
            ActionBarView.this.mExpandedHomeLayout.setIcon(null);
            this.mCurrentExpandedItem = null;
            ActionBarView.this.requestLayout();
            menuItemImpl.setActionViewExpanded(false);
            ActionBarView.this.updateBackInvokedCallbackState();
            return true;
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter
        public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
            ActionBarView.this.mExpandedActionView = menuItemImpl.getActionView();
            ActionBarView.this.initExpandedHomeLayout();
            ActionBarView.this.mExpandedHomeLayout.setIcon(ActionBarView.this.getIcon().getConstantState().newDrawable(ActionBarView.this.getResources()));
            this.mCurrentExpandedItem = menuItemImpl;
            ViewParent parent = ActionBarView.this.mExpandedActionView.getParent();
            ActionBarView actionBarView = ActionBarView.this;
            if (parent != actionBarView) {
                actionBarView.addView(actionBarView.mExpandedActionView);
            }
            ViewParent parent2 = ActionBarView.this.mExpandedHomeLayout.getParent();
            ActionBarView actionBarView2 = ActionBarView.this;
            if (parent2 != actionBarView2) {
                actionBarView2.addView(actionBarView2.mExpandedHomeLayout);
            }
            if (ActionBarView.this.mHomeLayout != null) {
                ActionBarView.this.mHomeLayout.setVisibility(8);
            }
            if (ActionBarView.this.mCollapseTitle != null) {
                ActionBarView.this.setTitleVisibility(false);
            }
            if (ActionBarView.this.mCollapseTabs != null) {
                ActionBarView.this.mCollapseTabs.setVisibility(8);
            }
            if (ActionBarView.this.mExpandTabs != null) {
                ActionBarView.this.mExpandTabs.setVisibility(8);
            }
            if (ActionBarView.this.mSecondaryCollapseTabs != null) {
                ActionBarView.this.mSecondaryCollapseTabs.asViewGroup().setVisibility(8);
            }
            if (ActionBarView.this.mSecondaryExpandTabs != null) {
                ActionBarView.this.mSecondaryExpandTabs.asViewGroup().setVisibility(8);
            }
            if (ActionBarView.this.mSpinner != null) {
                ActionBarView.this.mSpinner.setVisibility(8);
            }
            if (ActionBarView.this.mCustomNavView != null) {
                ActionBarView.this.mCustomNavView.setVisibility(8);
            }
            ActionBarView.this.requestLayout();
            menuItemImpl.setActionViewExpanded(true);
            KeyEvent.Callback callback = ActionBarView.this.mExpandedActionView;
            if (callback instanceof CollapsibleActionView) {
                ((CollapsibleActionView) callback).onActionViewExpanded();
            }
            ActionBarView.this.updateBackInvokedCallbackState();
            return true;
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter
        public boolean flagActionItems() {
            return false;
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter
        public int getId() {
            return 0;
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter
        public void initForMenu(Context context, MenuBuilder menuBuilder) {
            MenuItemImpl menuItemImpl;
            MenuBuilder menuBuilder2 = this.mMenu;
            if (menuBuilder2 != null && (menuItemImpl = this.mCurrentExpandedItem) != null) {
                menuBuilder2.collapseItemActionView(menuItemImpl);
            }
            this.mMenu = menuBuilder;
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter
        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter
        public Parcelable onSaveInstanceState() {
            return null;
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter
        public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
            return false;
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter
        public void updateMenuView(boolean z) {
            if (this.mCurrentExpandedItem != null) {
                MenuBuilder menuBuilder = this.mMenu;
                if (menuBuilder != null) {
                    int size = menuBuilder.size();
                    for (int i = 0; i < size; i++) {
                        if (this.mMenu.getItem(i) == this.mCurrentExpandedItem) {
                            return;
                        }
                    }
                }
                collapseItemActionView(this.mMenu, this.mCurrentExpandedItem);
            }
        }
    }

    public static class HomeView extends FrameLayout {
        public Drawable mDefaultUpIndicator;
        public int mHorizontalPadding;
        public ImageView mIconView;
        public int mUpIndicatorRes;
        public ImageView mUpView;

        public HomeView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mHorizontalPadding = context.getResources().getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_title_view_padding_horizontal);
        }

        @Override // android.view.View
        public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            CharSequence contentDescription = getContentDescription();
            if (TextUtils.isEmpty(contentDescription)) {
                return true;
            }
            accessibilityEvent.getText().add(contentDescription);
            return true;
        }

        public int getStartOffset() {
            return 0;
        }

        public View getUp() {
            return this.mUpView;
        }

        @Override // android.view.View
        public void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            int i = this.mUpIndicatorRes;
            if (i != 0) {
                setUpIndicator(i);
            }
        }

        @Override // android.view.View
        public void onFinishInflate() {
            super.onFinishInflate();
            this.mUpView = (ImageView) findViewById(miuix.appcompat.R.id.up);
            this.mIconView = (ImageView) findViewById(miuix.appcompat.R.id.home);
            ImageView imageView = this.mUpView;
            if (imageView != null) {
                this.mDefaultUpIndicator = imageView.getDrawable();
                Folme.useAt(this.mUpView).hover().setFeedbackRadius(60.0f);
                Folme.useAt(this.mUpView).hover().setEffect(IHoverStyle.HoverEffect.FLOATED_WRAPPED).handleHoverOf(this.mUpView, new AnimConfig[0]);
            }
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            int i5;
            int i6;
            int i7;
            int i8;
            int i9 = (i4 - i2) / 2;
            boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this);
            if (this.mUpView.getVisibility() != 8) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mUpView.getLayoutParams();
                int measuredHeight = this.mUpView.getMeasuredHeight();
                int measuredWidth = this.mUpView.getMeasuredWidth();
                int i10 = i9 - (measuredHeight / 2);
                ViewUtils.layoutChildView(this, this.mUpView, 0, i10, measuredWidth, measuredHeight + i10);
                i5 = layoutParams.leftMargin + measuredWidth + layoutParams.rightMargin;
                if (zIsLayoutRtl) {
                    i7 = i5;
                    i8 = i3 - i5;
                    i6 = i;
                } else {
                    i6 = i + i5;
                }
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mIconView.getLayoutParams();
                int measuredHeight2 = this.mIconView.getMeasuredHeight();
                int measuredWidth2 = this.mIconView.getMeasuredWidth();
                int iMax = i7 + Math.max(layoutParams2.getMarginStart(), (((i8 - i6) - this.mHorizontalPadding) / 2) - (measuredWidth2 / 2));
                int iMax2 = Math.max(layoutParams2.topMargin, i9 - (measuredHeight2 / 2));
                ViewUtils.layoutChildView(this, this.mIconView, iMax, iMax2, iMax + measuredWidth2, measuredHeight2 + iMax2);
            }
            i5 = 0;
            i6 = i;
            i7 = i5;
            i8 = i3;
            FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) this.mIconView.getLayoutParams();
            int measuredHeight3 = this.mIconView.getMeasuredHeight();
            int measuredWidth3 = this.mIconView.getMeasuredWidth();
            int iMax3 = i7 + Math.max(layoutParams3.getMarginStart(), (((i8 - i6) - this.mHorizontalPadding) / 2) - (measuredWidth3 / 2));
            int iMax4 = Math.max(layoutParams3.topMargin, i9 - (measuredHeight3 / 2));
            ViewUtils.layoutChildView(this, this.mIconView, iMax3, iMax4, iMax3 + measuredWidth3, measuredHeight3 + iMax4);
        }

        @Override // android.widget.FrameLayout, android.view.View
        public void onMeasure(int i, int i2) {
            measureChildWithMargins(this.mUpView, i, 0, i2, 0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mUpView.getLayoutParams();
            int measuredWidth = this.mUpView.getVisibility() == 8 ? 0 : layoutParams.leftMargin + this.mUpView.getMeasuredWidth() + layoutParams.rightMargin;
            int measuredHeight = layoutParams.topMargin + this.mUpView.getMeasuredHeight() + layoutParams.bottomMargin;
            measureChildWithMargins(this.mIconView, i, measuredWidth, i2, 0);
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mIconView.getLayoutParams();
            int measuredWidth2 = measuredWidth + (this.mIconView.getVisibility() != 8 ? layoutParams2.leftMargin + this.mIconView.getMeasuredWidth() + layoutParams2.rightMargin : 0);
            if (measuredWidth2 > 0) {
                measuredWidth2 += this.mHorizontalPadding;
            }
            int iMax = Math.max(measuredHeight, layoutParams2.topMargin + this.mIconView.getMeasuredHeight() + layoutParams2.bottomMargin);
            int mode = View.MeasureSpec.getMode(i);
            int mode2 = View.MeasureSpec.getMode(i2);
            int size = View.MeasureSpec.getSize(i);
            int size2 = View.MeasureSpec.getSize(i2);
            if (mode == Integer.MIN_VALUE) {
                measuredWidth2 = Math.min(measuredWidth2, size);
            } else if (mode == 1073741824) {
                measuredWidth2 = size;
            }
            if (mode2 == Integer.MIN_VALUE) {
                iMax = Math.min(iMax, size2);
            } else if (mode2 == 1073741824) {
                iMax = size2;
            }
            setMeasuredDimension(measuredWidth2, iMax);
        }

        public void setIcon(Drawable drawable) {
            this.mIconView.setImageDrawable(drawable);
        }

        public void setUp(boolean z) {
            this.mUpView.setVisibility(z ? 0 : 8);
        }

        public void setUpIndicator(int i) {
            this.mUpIndicatorRes = i;
            this.mUpView.setImageDrawable(i != 0 ? getResources().getDrawable(i) : null);
        }

        public void setUpIndicator(Drawable drawable) {
            ImageView imageView = this.mUpView;
            if (drawable == null) {
                drawable = this.mDefaultUpIndicator;
            }
            imageView.setImageDrawable(drawable);
            this.mUpIndicatorRes = 0;
        }
    }

    public static class InnerTransitionListener extends TransitionListener {
        public WeakReference mRef;

        public InnerTransitionListener(ActionBarView actionBarView) {
            this.mRef = new WeakReference(actionBarView);
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onBegin(Object obj) {
            super.onBegin(obj);
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onCancel(Object obj) {
            super.onCancel(obj);
            this.mRef.clear();
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            this.mRef.clear();
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onUpdate(Object obj, Collection collection) {
            ActionBarView actionBarView;
            super.onUpdate(obj, collection);
            UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, "actionbar_state_change");
            if (updateInfoFindByName == null || (actionBarView = (ActionBarView) this.mRef.get()) == null) {
                return;
            }
            actionBarView.mPendingHeight = updateInfoFindByName.getIntValue();
            actionBarView.requestLayout();
        }
    }

    public interface OnSearchViewStyleChangeListener {
        void onStyleChange(boolean z);
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.ClassLoaderCreator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.SavedState.1
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
        public boolean applyBlur;
        public int expandState;
        public int expandedMenuItemId;
        public boolean isEndOverflowOpen;
        public boolean isOverflowOpen;
        public int userExpandState;
        public boolean userSetExpandState;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.expandedMenuItemId = parcel.readInt();
            this.isOverflowOpen = parcel.readInt() != 0;
            this.isEndOverflowOpen = parcel.readInt() != 0;
            this.expandState = parcel.readInt();
            this.userSetExpandState = parcel.readInt() != 0;
            this.userExpandState = parcel.readInt();
            this.applyBlur = parcel.readInt() != 0;
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.expandedMenuItemId = parcel.readInt();
            this.isOverflowOpen = parcel.readInt() != 0;
            this.isEndOverflowOpen = parcel.readInt() != 0;
            this.expandState = parcel.readInt();
            this.userSetExpandState = parcel.readInt() != 0;
            this.userExpandState = parcel.readInt();
            this.applyBlur = parcel.readInt() != 0;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.expandedMenuItemId);
            parcel.writeInt(this.isOverflowOpen ? 1 : 0);
            parcel.writeInt(this.isEndOverflowOpen ? 1 : 0);
            parcel.writeInt(this.expandState);
            parcel.writeInt(this.userSetExpandState ? 1 : 0);
            parcel.writeInt(this.userExpandState);
            parcel.writeInt(this.applyBlur ? 1 : 0);
        }
    }

    public static /* synthetic */ void $r8$lambda$04kpj5IDuE0qXTIj9b55WOW81uI(ActionBarView actionBarView) {
        int i = actionBarView.mInnerExpandState;
        if (i == 0) {
            actionBarView.mCollapseController.setAnimFrom(1.0f, 0, 0, true);
            actionBarView.mMediumController.setAnimFrom(0.0f, 0, 0, true);
            actionBarView.mMovableController.setAnimFrom(0.0f, 0, 0, true);
        } else {
            if (i != 1) {
                if (i == 2) {
                    actionBarView.mCollapseController.setAnimFrom(0.0f, 0, 20, true);
                    actionBarView.mMediumController.setAnimFrom(0.0f, 0, 0, true);
                    actionBarView.mMovableController.setAnimFrom(1.0f, 0, 0, true);
                    return;
                }
                return;
            }
            if (actionBarView.hasTabsInContainer(actionBarView.mMainContainer)) {
                actionBarView.mCollapseController.setAnimFrom(1.0f, 0, 0, true);
                actionBarView.mMediumController.setAnimFrom(0.0f, 0, 0, true);
            } else {
                actionBarView.mCollapseController.setAnimFrom(0.0f, 0, 0, true);
                actionBarView.mMediumController.setAnimFrom(1.0f, 0, 0, true);
                actionBarView.ensureCollapseTabContainerVisibleForMedium();
            }
            actionBarView.mMovableController.setAnimFrom(0.0f, 0, 0, true);
        }
    }

    public static /* synthetic */ void $r8$lambda$ADuzlzSkaxUsMj_gi3Z3NBj_ofk(ActionBarView actionBarView) {
        ActionMenuPresenter actionMenuPresenter = actionBarView.mEndActionMenuPresenter;
        if (actionMenuPresenter == null || !actionMenuPresenter.isOverflowMenuShowing()) {
            return;
        }
        LifecycleOwner lifecycleOwner = actionBarView.mLifecycleOwner;
        if (lifecycleOwner != null ? lifecycleOwner.getLifecycle().getCurrentState().equals(Lifecycle.State.RESUMED) : true) {
            return;
        }
        actionBarView.mEndActionMenuPresenter.hideOverflowMenu(false);
    }

    /* JADX INFO: renamed from: $r8$lambda$IYt-O9vhxJ9B-mLhQbMo_WNkz5c, reason: not valid java name */
    public static /* synthetic */ void m1318$r8$lambda$IYtO9vhxJ9BmLhQbMo_WNkz5c(ActionBarView actionBarView) {
        CollapseTitle collapseTitle = actionBarView.mCollapseTitle;
        if (collapseTitle != null) {
            collapseTitle.setSubTitleTextSize(collapseTitle.getSubtitleAdjustSize());
        }
    }

    public static /* synthetic */ void $r8$lambda$NDUojLe2gQffAvCVeWwY0y8iXq0(ActionBarView actionBarView, ImageView imageView, Drawable drawable) {
        actionBarView.getClass();
        imageView.setImageDrawable(drawable);
        actionBarView.mPendingTitleUpIndicatorUpdate = null;
    }

    /* JADX INFO: renamed from: $r8$lambda$k-H3G-jmqt-8XDKmq2nK5G4txog, reason: not valid java name */
    public static /* synthetic */ void m1319$r8$lambda$kH3Gjmqt8XDKmq2nK5G4txog(ActionBarView actionBarView) {
        actionBarView.pendingCreateTitle();
        actionBarView.setTitleVisibility(actionBarView.shouldTitleVisible());
        actionBarView.updateTightTitle();
    }

    public static /* synthetic */ void $r8$lambda$mtfXocOWfKTftX6UTRcy4K7yeLU(ActionBarView actionBarView) {
        CollapseTitle collapseTitle = actionBarView.mCollapseTitle;
        if (collapseTitle != null) {
            collapseTitle.setSubTitleTextSize(collapseTitle.getSubtitleAdjustSize());
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$uy8sVM-VoKbv133jMlWiGKAoSJo, reason: not valid java name */
    public static /* synthetic */ void m1320$r8$lambda$uy8sVMVoKbv133jMlWiGKAoSJo(ActionBarView actionBarView) {
        View view = actionBarView.mTitleUpView;
        if (view != null && view.isAttachedToWindow() && actionBarView.mTitleUpView.getVisibility() == 0) {
            actionBarView.mTitleUpView.performAccessibilityAction(64, null);
        }
    }

    public ActionBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDisplayOptions = -1;
        this.mLifecycleOwner = null;
        this.mDoContainerShowAnimInFinishActionMode = false;
        this.mRequestInitialA11yFocusEnabled = true;
        this.mIsBottomMenuVisible = true;
        this.mPendingCreated = false;
        this.mCollapsedTitleVisible = true;
        this.mExpandTitleVisible = true;
        this.mHasStartView = false;
        this.mHasNavigatorSwitchView = false;
        this.mApplyBgBlur = false;
        this.mActionBarButtonMaterial = false;
        this.mActionBarSelfBlurEnabled = true;
        this.mCollapseTitleShowable = true;
        this.mLastResizingProcess = 0.0f;
        this.mNeedRequestLayoutOnExpandTitleShowing = false;
        this.mHandler = new Handler(Looper.myLooper());
        this.mPostAnimationRunnable = new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.1
            @Override // java.lang.Runnable
            public void run() {
                if (ActionBarView.this.mPostScroll != null) {
                    ActionBarView actionBarView = ActionBarView.this;
                    actionBarView.postOnAnimation(actionBarView.mPostScroll);
                }
            }
        };
        this.mCollapseAnimShowConfigListener = new TransitionListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.2
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
                if (ActionBarView.this.mCollapseController != null) {
                    ActionBarView.this.mCollapseController.onShow();
                }
            }
        };
        this.mCollapseAnimHideConfigListener = new TransitionListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.3
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                if (ActionBarView.this.mCollapseController != null) {
                    ActionBarView.this.mCollapseController.onHide();
                }
            }
        };
        this.mMovableAlphaShowListener = new TransitionListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.4
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj, Collection collection) {
                super.onBegin(obj, collection);
                if (ActionBarView.this.mMovableMainContainer == null || ActionBarView.this.mMovableMainContainer.getVisibility() == 0) {
                    return;
                }
                ActionBarView.this.mMovableController.setVisibility(0);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                if (ActionBarView.this.mNeedRequestLayoutOnExpandTitleShowing) {
                    ActionBarView.this.requestLayout();
                }
                ActionBarView.this.mNeedRequestLayoutOnExpandTitleShowing = false;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                super.onUpdate(obj, collection);
                if (ActionBarView.this.mNeedRequestLayoutOnExpandTitleShowing) {
                    ActionBarView.this.requestLayout();
                }
            }
        };
        this.mMovableAnimAlphaListener = new TransitionListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.5
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj, Collection collection) {
                super.onBegin(obj, collection);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                if (ActionBarView.this.mMovableMainContainer.getAlpha() != 0.0f) {
                    if (ActionBarView.this.mMovableMainContainer.getVisibility() != 0) {
                        ActionBarView.this.mMovableController.setVisibility(0);
                        return;
                    }
                    return;
                }
                ActionBarView actionBarView = ActionBarView.this;
                int i = actionBarView.mInnerExpandState;
                if (i == 0) {
                    if (actionBarView.mMovableMainContainer.getVisibility() != 8) {
                        ActionBarView.this.mMovableController.setVisibility(8);
                    }
                } else if (i == 1) {
                    if (actionBarView.mMovableMainContainer.getVisibility() != 8) {
                        ActionBarView.this.mMovableController.setVisibility(8);
                    }
                } else if (i == 3 && actionBarView.mMovableMainContainer.getVisibility() != 4) {
                    ActionBarView.this.mMovableController.setVisibility(4);
                }
            }
        };
        this.mNavItemSelectedListener = new AdapterView.OnItemSelectedListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.6
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView adapterView, View view, int i, long j) {
                if (ActionBarView.this.mCallback != null) {
                    ActionBarView.this.mCallback.onNavigationItemSelected(i, j);
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView adapterView) {
            }
        };
        this.mExpandedActionViewUpListener = new View.OnClickListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MenuItemImpl menuItemImpl = ActionBarView.this.mExpandedMenuPresenter.mCurrentExpandedItem;
                if (menuItemImpl != null) {
                    menuItemImpl.collapseActionView();
                }
            }
        };
        this.mUpClickListener = new View.OnClickListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ActionBarView actionBarView = ActionBarView.this;
                actionBarView.mWindowCallback.onMenuItemSelected(0, actionBarView.mLogoNavItem);
            }
        };
        this.mTitleClickListener = new View.OnClickListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ActionBarView actionBarView = ActionBarView.this;
                actionBarView.mWindowCallback.onMenuItemSelected(0, actionBarView.mTitleNavItem);
            }
        };
        this.mSubTitleClickListener = new View.OnClickListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                View.OnClickListener onClickListener = ActionBarView.this.mUserSubTitleClickListener;
                if (onClickListener != null) {
                    onClickListener.onClick(view);
                }
            }
        };
        this.mCustomTitleWatcher = new TextWatcher() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.11
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (!charSequence.equals(ActionBarView.this.mTitle)) {
                    ActionBarView.this.mInitCustomTitle = charSequence;
                }
                if (ActionBarView.this.mExpandTitle != null) {
                    ActionBarView.this.mExpandTitle.setTitle(charSequence);
                }
            }
        };
        this.mIsCollapseTitleShowingOnResizing = false;
        this.mTransitionTarget = 0;
        AbsActionBarView.CollapseView collapseView = new AbsActionBarView.CollapseView();
        this.mCollapseController = collapseView;
        AbsActionBarView.CollapseView collapseView2 = new AbsActionBarView.CollapseView();
        this.mMovableController = collapseView2;
        AbsActionBarView.CollapseView collapseView3 = new AbsActionBarView.CollapseView();
        this.mMediumController = collapseView3;
        this.mTouchScrolling = false;
        this.mNonTouchScrolling = false;
        this.mInActionMode = false;
        this.mInSearchMode = false;
        this.mInActionModeAnimating = false;
        this.mStateChangeAnimStateStyle = null;
        this.mPostScroll = new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.16
            @Override // java.lang.Runnable
            public void run() {
                if (ActionBarView.this.mPostScroller.computeScrollOffset()) {
                    ActionBarView actionBarView = ActionBarView.this;
                    int currY = actionBarView.mPostScroller.getCurrY();
                    ActionBarView actionBarView2 = ActionBarView.this;
                    actionBarView.mPendingHeight = (currY - actionBarView2.mCollapseTotalHeight) + actionBarView2.mCollapseSecondaryTabHeight;
                    ActionBarView.this.requestLayout();
                    if (!ActionBarView.this.mPostScroller.isFinished()) {
                        ActionBarView.this.postOnAnimation(this);
                        return;
                    }
                    int currY2 = ActionBarView.this.mPostScroller.getCurrY();
                    ActionBarView actionBarView3 = ActionBarView.this;
                    if (currY2 == actionBarView3.mCollapseTotalHeight) {
                        actionBarView3.setExpandState(0);
                        return;
                    }
                    int currY3 = actionBarView3.mPostScroller.getCurrY();
                    ActionBarView actionBarView4 = ActionBarView.this;
                    if (currY3 == actionBarView4.mCollapseTotalHeight + actionBarView4.mMovableMainContainer.getMeasuredHeight()) {
                        ActionBarView.this.setExpandState(2);
                    }
                }
            }
        };
        AnimConfig ease = new AnimConfig().setEase(FolmeEase.springLegacy(400.0f, 0.98f, 0.75f));
        ViewProperty viewProperty = SELF_BLUR;
        AnimConfig special = ease.setSpecial(viewProperty, FolmeEase.linear(150L), new float[0]);
        ViewProperty viewProperty2 = ViewProperty.ALPHA;
        this.enterAnimConfig = special.setSpecial(viewProperty2, FolmeEase.bezier(isSelfBlurEnabled() ? 150L : 80L, 0.0f, 0.0f, 0.67f, 1.0f), new float[0]);
        this.exitAnimConfig = new AnimConfig().setEase(FolmeEase.springLegacy(400.0f, 0.98f, 0.75f)).setSpecial(viewProperty, FolmeEase.linear(120L), new float[0]).setSpecial(viewProperty2, FolmeEase.bezier(isSelfBlurEnabled() ? 150L : 80L, 0.26f, 0.0f, 0.6f, 0.2f), new float[0]);
        this.mFloatingMode = null;
        this.mContext = context;
        this.mPostScroller = new Scroller(context);
        this.mInActionMode = false;
        this.mInSearchMode = false;
        Resources resources = this.mContext.getResources();
        this.mDensity = resources.getDisplayMetrics().density;
        this.mUncollapsePaddingH = resources.getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_title_horizontal_padding);
        this.mUncollapseTabPaddingH = resources.getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_title_tab_horizontal_padding);
        this.mExpandTabTopPadding = resources.getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_title_top_padding);
        this.mExpandTitlePaddingBottom = resources.getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_title_bottom_padding);
        this.mExpandSubtitlePaddingBottom = context.getResources().getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_subtitle_bottom_padding);
        this.mSecondaryTabTopPadding = context.getResources().getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_secondary_tab_top_padding);
        this.mSecondaryTabBottomPadding = context.getResources().getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_secondary_tab_bottom_padding);
        this.mTitleUpViewMarginStart = context.getResources().getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_up_view_margin_start);
        this.mTitleUpViewMarginEnd = 0;
        this.mTitleGapPaddingStart = resources.getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_title_padding_gap);
        this.mMediumTitlePaddingStartNoUp = resources.getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_medium_title_padding_start_no_up);
        this.mSearchViewMaxWidth = resources.getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_search_view_max_width);
        this.mSearchViewMargin = resources.getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_search_view_margin);
        this.mTitleMinWidth = resources.getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_title_min_width);
        this.mMovableAnimShowConfig.addListeners(this.mMovableAlphaShowListener);
        this.mMovableAnimNormalConfig.addListeners(this.mMovableAnimAlphaListener);
        this.mCollapseAnimShowConfig.addListeners(this.mCollapseAnimShowConfigListener);
        this.mCollapseAnimHideConfig.addListeners(this.mCollapseAnimHideConfigListener);
        FrameLayout frameLayout = new FrameLayout(context);
        this.mMainContainer = frameLayout;
        frameLayout.setId(miuix.appcompat.R.id.action_bar_collapse_container);
        this.mMainContainer.setForegroundGravity(17);
        this.mMainContainer.setVisibility(0);
        this.mMainContainer.setAlpha(this.mInnerExpandState == 0 ? 1.0f : 0.0f);
        FrameLayout frameLayout2 = new FrameLayout(context);
        this.mMediumContainer = frameLayout2;
        frameLayout2.setId(miuix.appcompat.R.id.action_bar_medium_container);
        this.mMediumContainer.setForegroundGravity(8388611);
        this.mMediumContainer.setVisibility(0);
        this.mMediumContainer.setAlpha(this.mInnerExpandState == 1 ? 1.0f : 0.0f);
        FrameLayout frameLayout3 = new FrameLayout(context);
        this.mMovableMainContainer = frameLayout3;
        frameLayout3.setId(miuix.appcompat.R.id.action_bar_movable_container);
        FrameLayout frameLayout4 = this.mMovableMainContainer;
        int i = this.mUncollapsePaddingH;
        frameLayout4.setPaddingRelative(i, this.mExpandTabTopPadding, i, this.mExpandTitlePaddingBottom);
        this.mMovableMainContainer.setVisibility(0);
        FrameLayout frameLayout5 = this.mMovableMainContainer;
        int i2 = this.mInnerExpandState;
        frameLayout5.setAlpha((i2 == 0 || i2 == 1) ? 0.0f : 1.0f);
        collapseView.attachViews(this.mMainContainer);
        collapseView3.attachViews(this.mMediumContainer);
        collapseView2.attachViews(this.mMovableMainContainer);
        setBackgroundResource(0);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, miuix.appcompat.R.styleable.ActionBar, android.R.attr.actionBarStyle, 0);
        this.mNavigationMode = typedArrayObtainStyledAttributes.getInt(miuix.appcompat.R.styleable.ActionBar_android_navigationMode, 0);
        this.mTitle = typedArrayObtainStyledAttributes.getText(miuix.appcompat.R.styleable.ActionBar_android_title);
        this.mSubtitle = typedArrayObtainStyledAttributes.getText(miuix.appcompat.R.styleable.ActionBar_android_subtitle);
        this.mTitleCenter = typedArrayObtainStyledAttributes.getBoolean(miuix.appcompat.R.styleable.ActionBar_titleCenter, false);
        this.mLogo = typedArrayObtainStyledAttributes.getDrawable(miuix.appcompat.R.styleable.ActionBar_android_logo);
        this.mIcon = typedArrayObtainStyledAttributes.getDrawable(miuix.appcompat.R.styleable.ActionBar_android_icon);
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(context);
        this.mNavigatorSwitchResId = typedArrayObtainStyledAttributes.getResourceId(miuix.appcompat.R.styleable.ActionBar_navigatorSwitchLayout, miuix.appcompat.R.layout.miuix_appcompat_action_bar_navigator_switch);
        this.mHomeResId = typedArrayObtainStyledAttributes.getResourceId(miuix.appcompat.R.styleable.ActionBar_android_homeLayout, miuix.appcompat.R.layout.miuix_appcompat_action_bar_home);
        this.mCollapseTitleStyleRes = typedArrayObtainStyledAttributes.getResourceId(miuix.appcompat.R.styleable.ActionBar_android_titleTextStyle, 0);
        this.mCollapseSubtitleStyleRes = typedArrayObtainStyledAttributes.getResourceId(miuix.appcompat.R.styleable.ActionBar_android_subtitleTextStyle, 0);
        this.mMediumTitleStyleRes = typedArrayObtainStyledAttributes.getResourceId(miuix.appcompat.R.styleable.ActionBar_mediumTitleTextStyle, miuix.appcompat.R.style.MediumTitleStyle);
        this.mMediumSubtitleStyleRes = typedArrayObtainStyledAttributes.getResourceId(miuix.appcompat.R.styleable.ActionBar_mediumSubtitleTextStyle, miuix.appcompat.R.style.MediumSubtitleStyle);
        this.mProgressBarPadding = typedArrayObtainStyledAttributes.getDimensionPixelOffset(miuix.appcompat.R.styleable.ActionBar_android_progressBarPadding, 0);
        this.mItemPadding = typedArrayObtainStyledAttributes.getDimensionPixelOffset(miuix.appcompat.R.styleable.ActionBar_android_itemPadding, 0);
        setDisplayOptions(typedArrayObtainStyledAttributes.getInt(miuix.appcompat.R.styleable.ActionBar_android_displayOptions, 0));
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(miuix.appcompat.R.styleable.ActionBar_android_customNavigationLayout, 0);
        if (resourceId != 0) {
            View viewInflate = layoutInflaterFrom.inflate(resourceId, (ViewGroup) this, false);
            this.mCustomNavView = viewInflate;
            viewInflate.setLayoutParams(new ActionBar.LayoutParams(-1, -2, 8388627));
            this.mNavigationMode = 0;
        }
        this.mTitleMinHeight = typedArrayObtainStyledAttributes.getLayoutDimension(miuix.appcompat.R.styleable.ActionBar_android_minHeight, 0);
        this.mTitleMaxHeight = typedArrayObtainStyledAttributes.getLayoutDimension(miuix.appcompat.R.styleable.ActionBar_android_maxHeight, 0);
        this.mTitleMaxHeight = (AttributeResolver.resolveBoolean(this.mContext, miuix.appcompat.R.attr.actionBarTitleAdaptLargeFont, true) && (MiuixUIUtils.getFontLevel(this.mContext) == 2)) ? typedArrayObtainStyledAttributes.getDimensionPixelSize(miuix.appcompat.R.styleable.ActionBar_actionBarMaxSizeInLargeFont, this.mContext.getResources().getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_large_font_max_height)) : this.mTitleMaxHeight;
        this.mOptionalIconsVisible = typedArrayObtainStyledAttributes.getBoolean(miuix.appcompat.R.styleable.ActionBar_showOptionIcons, false);
        typedArrayObtainStyledAttributes.recycle();
        this.mActionBarButtonMaterial = AttributeResolver.resolveBoolean(context, miuix.appcompat.R.attr.actionBarButtonMaterial, false);
        boolean zResolveBoolean = AttributeResolver.resolveBoolean(context, miuix.appcompat.R.attr.actionBarSelfBlurEnabled, true);
        this.mActionBarSelfBlurEnabled = zResolveBoolean;
        collapseView.attachViews(this.mMainContainer, zResolveBoolean);
        collapseView3.attachViews(this.mMediumContainer, this.mActionBarSelfBlurEnabled);
        collapseView2.attachViews(this.mMovableMainContainer, this.mActionBarSelfBlurEnabled);
        this.mLogoNavItem = new ActionMenuItem(context, 0, android.R.id.home, 0, 0, this.mTitle);
        this.mTitleNavItem = new ActionMenuItem(context, 0, android.R.id.title, 0, 0, this.mTitle);
        postRefreshTitleControllerStatus();
    }

    private ProgressBar getCircularProgressBar() {
        ProgressBar progressBar = this.mIndeterminateProgressView;
        if (progressBar != null) {
            progressBar.setVisibility(4);
        }
        return progressBar;
    }

    private ProgressBar getHorizontalProgressBar() {
        ProgressBar progressBar = this.mProgressView;
        if (progressBar != null) {
            progressBar.setVisibility(4);
        }
        return progressBar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Drawable getIcon() {
        if ((this.mIconLogoInitIndicator & 1) != 1) {
            Context context = this.mContext;
            if (context instanceof Activity) {
                try {
                    this.mIcon = context.getPackageManager().getActivityIcon(((Activity) this.mContext).getComponentName());
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("ActionBarView", "Activity component name not found!", e);
                }
            }
            if (this.mIcon == null) {
                this.mIcon = this.mContext.getApplicationInfo().loadIcon(this.mContext.getPackageManager());
            }
            this.mIconLogoInitIndicator |= 1;
        }
        return this.mIcon;
    }

    private Drawable getLogo() {
        if ((this.mIconLogoInitIndicator & 2) != 2) {
            Context context = this.mContext;
            if (context instanceof Activity) {
                try {
                    this.mLogo = context.getPackageManager().getActivityLogo(((Activity) this.mContext).getComponentName());
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("ActionBarView", "Activity component name not found!", e);
                }
            }
            if (this.mLogo == null) {
                this.mLogo = this.mContext.getApplicationInfo().loadLogo(this.mContext.getPackageManager());
            }
            this.mIconLogoInitIndicator |= 2;
        }
        return this.mLogo;
    }

    @Nullable
    private ImageView getTitleUpInnerImageView() {
        View view = this.mTitleUpView;
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        if (viewGroup.getChildCount() <= 0 || !(viewGroup.getChildAt(0) instanceof ImageView)) {
            return null;
        }
        return (ImageView) viewGroup.getChildAt(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTitle() {
        this.mPendingCreated = false;
        initTitleUpView();
        if (this.mNavigationMode == 2) {
            freeMainContainerChildren();
        }
        createTitleForCurrentState();
        updateTightTitle();
        post(new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarView$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                ActionBarView.m1319$r8$lambda$kH3Gjmqt8XDKmq2nK5G4txog(this.f$0);
            }
        });
        if (this.mExpandedActionView != null || isAllTitlesEmpty()) {
            setTitleVisibility(false);
        }
        safeAddView(this, this.mMainContainer);
        safeAddView(this, this.mMediumContainer);
        safeAddView(this, this.mMovableMainContainer, 0);
    }

    private void safeAddView(ViewGroup viewGroup, View view) {
        safeAddView(viewGroup, view, -1);
    }

    private void safeAddView(ViewGroup viewGroup, View view, int i) {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        if (viewGroup != null) {
            viewGroup.addView(view, i);
        }
    }

    private void setTitleImpl(CharSequence charSequence) {
        boolean zShouldTitleVisible = shouldTitleVisible();
        this.mTitle = charSequence;
        boolean z = false;
        if (((this.mDisplayOptions & 16) == 0 || this.mCustomNavView == null) ? false : updateExpandTitleOnShowCustom()) {
            return;
        }
        updateCollapseTitle();
        updateExpandTitle();
        updateMediumTitle();
        boolean zShouldTitleVisible2 = shouldTitleVisible();
        setTitleVisibility(zShouldTitleVisible2);
        ActionMenuItem actionMenuItem = this.mLogoNavItem;
        if (actionMenuItem != null) {
            actionMenuItem.setTitle(charSequence);
        }
        ActionMenuItem actionMenuItem2 = this.mTitleNavItem;
        if (actionMenuItem2 != null) {
            actionMenuItem2.setTitle(charSequence);
        }
        if (zShouldTitleVisible && !zShouldTitleVisible2) {
            if ((getNavigationMode() == 2) || isTightTitleWithEmbeddedTabs()) {
                addTabsToMainContainers();
                return;
            }
            return;
        }
        if (zShouldTitleVisible || !zShouldTitleVisible2) {
            return;
        }
        if ((getNavigationMode() == 2) && isTightTitleWithEmbeddedTabs()) {
            return;
        }
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null && collapseTitle.getLayout().getParent() == null) {
            z = true;
        }
        ExpandTitle expandTitle = this.mExpandTitle;
        if ((expandTitle == null || z || expandTitle.getLayout().getParent() != null) ? z : true) {
            freeMainContainerChildren();
            CollapseTitle collapseTitle2 = this.mCollapseTitle;
            if (collapseTitle2 != null) {
                safeAddView(this.mMainContainer, collapseTitle2.getLayout());
            }
            ExpandTitle expandTitle2 = this.mExpandTitle;
            if (expandTitle2 != null) {
                safeAddView(this.mMovableMainContainer, expandTitle2.getLayout());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTitleVisibility(boolean z) {
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null) {
            collapseTitle.setVisibility(z ? 0 : 8);
        }
        MediumTitle mediumTitle = this.mMediumTitle;
        if (mediumTitle != null) {
            mediumTitle.setVisibility(z ? 0 : 8);
        }
        ExpandTitle expandTitle = this.mExpandTitle;
        if (expandTitle != null) {
            expandTitle.setVisibility(z ? 0 : 4);
        }
        updateMovableContainerVisibility(z);
        updateTitleUpViewVisibility();
        int i = TextUtils.isEmpty(this.mSubtitle) ? this.mExpandTitlePaddingBottom : this.mExpandSubtitlePaddingBottom;
        FrameLayout frameLayout = this.mMovableMainContainer;
        frameLayout.setPaddingRelative(frameLayout.getPaddingStart(), this.mMovableMainContainer.getPaddingTop(), this.mMovableMainContainer.getPaddingEnd(), i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBackInvokedCallbackState() {
        OnBackInvokedDispatcher onBackInvokedDispatcher;
        OnBackInvokedDispatcher onBackInvokedDispatcherFindOnBackInvokedDispatcher = findOnBackInvokedDispatcher();
        boolean z = hasExpandedActionView() && onBackInvokedDispatcherFindOnBackInvokedDispatcher != null && ViewCompat.isAttachedToWindow(this);
        if (z && this.mBackInvokedDispatcher == null) {
            if (this.mBackInvokedCallback == null) {
                this.mBackInvokedCallback = new OnBackInvokedCallback() { // from class: miuix.appcompat.internal.app.widget.ActionBarView$$ExternalSyntheticLambda3
                    @Override // android.window.OnBackInvokedCallback
                    public final void onBackInvoked() {
                        this.f$0.collapseActionView();
                    }
                };
            }
            onBackInvokedDispatcherFindOnBackInvokedDispatcher.registerOnBackInvokedCallback(AnimState.VIEW_SIZE, this.mBackInvokedCallback);
            this.mBackInvokedDispatcher = onBackInvokedDispatcherFindOnBackInvokedDispatcher;
            return;
        }
        if (z || (onBackInvokedDispatcher = this.mBackInvokedDispatcher) == null) {
            return;
        }
        onBackInvokedDispatcher.unregisterOnBackInvokedCallback(this.mBackInvokedCallback);
        this.mBackInvokedDispatcher = null;
    }

    public final void addCustomView() {
        FrameLayout frameLayout = (FrameLayout) this.mCustomNavView.findViewById(miuix.appcompat.R.id.action_bar_expand_container);
        TextView customTitleView = getCustomTitleView(frameLayout);
        if (customTitleView != null) {
            this.mInitCustomTitle = customTitleView.getText();
            freeMainContainerChildren();
            this.mCollapseCustomContainer = frameLayout;
            this.mCollapseController.attachViews(frameLayout);
            ExpandTitle expandTitle = this.mExpandTitle;
            if (expandTitle != null) {
                expandTitle.setTitle(this.mInitCustomTitle);
                this.mExpandTitle.setTitleVisibility(0);
                this.mExpandTitle.setVisibility(0);
                this.mExpandTitle.setSubTitleVisibility(8);
                if (this.mMovableMainContainer != this.mExpandTitle.getLayout().getParent()) {
                    safeAddView(this.mMovableMainContainer, this.mExpandTitle.getLayout());
                }
            }
            customTitleView.addTextChangedListener(this.mCustomTitleWatcher);
        }
    }

    public final void addEndMenuView() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -1);
        this.mEndActionMenuPresenter.setExpandedActionViewsExclusive(getResources().getBoolean(miuix.appcompat.R.bool.abc_action_bar_expanded_action_views_exclusive));
        this.mEndActionMenuPresenter.setItemLimit(this.mEndActionMenuItemLimit);
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.gravity = 8388613;
        ActionMenuView actionMenuView = (ActionMenuView) this.mEndActionMenuPresenter.getMenuView(this);
        ViewGroup viewGroup = (ViewGroup) actionMenuView.getParent();
        if (viewGroup != null && viewGroup != this) {
            viewGroup.removeView(actionMenuView);
        }
        addView(actionMenuView, layoutParams);
        this.mEndMenuView = actionMenuView;
        if (this.mFloatingMode == null || !(actionMenuView instanceof EndActionMenuView)) {
            return;
        }
        ((EndActionMenuView) actionMenuView).setFloatingMode(this.mFloatingMode.booleanValue(), MiuixUIUtils.isLightTheme(getContext()), this.mActionBarButtonMaterial);
    }

    public final void addSecondaryTabsToCollapseTabContainers() {
        if (this.mSecondaryCollapseTabs != null) {
            FrameLayout frameLayout = this.mCollapseTabContainer;
            if (frameLayout == null) {
                FrameLayout frameLayoutCreateSecondaryTabContainer = createSecondaryTabContainer(miuix.appcompat.R.id.action_bar_collapse_tab_container);
                this.mCollapseTabContainer = frameLayoutCreateSecondaryTabContainer;
                if (this.mInnerExpandState == 2) {
                    frameLayoutCreateSecondaryTabContainer.setAlpha(0.0f);
                }
            } else {
                frameLayout.removeAllViews();
            }
            this.mCollapseTabContainer.addView(this.mSecondaryCollapseTabs.asViewGroup(), new FrameLayout.LayoutParams(-1, -2, 1));
            if (this.mCollapseTabContainer.getParent() == null) {
                addView(this.mCollapseTabContainer, new FrameLayout.LayoutParams(-1, -2));
                if (this.mInnerExpandState == 2) {
                    this.mCollapseTabContainer.setVisibility(8);
                }
                this.mCollapseController.attachViews(this.mCollapseTabContainer, this.mActionBarSelfBlurEnabled);
            }
        }
    }

    public final void addSecondaryTabsToExpandTabContainers() {
        if (this.mSecondaryExpandTabs != null) {
            FrameLayout frameLayout = this.mMovableTabContainer;
            if (frameLayout == null) {
                FrameLayout frameLayoutCreateSecondaryTabContainer = createSecondaryTabContainer(miuix.appcompat.R.id.action_bar_movable_tab_container);
                this.mMovableTabContainer = frameLayoutCreateSecondaryTabContainer;
                if (this.mInnerExpandState == 0) {
                    frameLayoutCreateSecondaryTabContainer.setAlpha(0.0f);
                }
            } else {
                frameLayout.removeAllViews();
            }
            this.mMovableTabContainer.addView(this.mSecondaryExpandTabs.asViewGroup(), new FrameLayout.LayoutParams(-1, -2, 1));
            if (this.mMovableTabContainer.getParent() == null) {
                addView(this.mMovableTabContainer, new FrameLayout.LayoutParams(-1, -2));
                if (this.mInnerExpandState == 0) {
                    this.mMovableTabContainer.setVisibility(8);
                }
                this.mMovableController.attachViews(this.mMovableTabContainer, this.mActionBarSelfBlurEnabled);
            }
        }
    }

    public final void addSplitMenuView() {
        ActionMenuView actionMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
        this.mMenuView = actionMenuView;
        if (actionMenuView != null && this.mScheduleBottomMenuRunnable != null) {
            actionMenuView.setVisibility(4);
            this.mMenuView.post(this.mScheduleBottomMenuRunnable);
            this.mScheduleBottomMenuRunnable = null;
        }
        boolean z = this.mBottomMenuMode == 3;
        this.mActionMenuPresenter.setExpandedActionViewsExclusive(false);
        this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
        FrameLayout.LayoutParams layoutParamsBuildSplitMenuLayoutParams = buildSplitMenuLayoutParams(z);
        if (this.mSplitView == null) {
            this.mMenuView.setLayoutParams(layoutParamsBuildSplitMenuLayoutParams);
            return;
        }
        removeMenuViewFromOldParent(this.mMenuView);
        this.mSplitView.onResidentActionMenuViewRemoved(this.mMenuView);
        ActionMenuView actionMenuView2 = this.mMenuView;
        if (actionMenuView2 instanceof ResponsiveActionMenuView) {
            ResponsiveActionMenuView responsiveActionMenuView = (ResponsiveActionMenuView) actionMenuView2;
            responsiveActionMenuView.setSuspendEnabled(z);
            responsiveActionMenuView.setHidden(!this.mIsBottomMenuVisible);
        }
        this.mSplitView.addView(this.mMenuView, 0, layoutParamsBuildSplitMenuLayoutParams);
        this.mSplitView.onResidentActionMenuViewAdded(this.mMenuView);
        View viewFindViewById = this.mMenuView.findViewById(miuix.appcompat.R.id.expanded_menu);
        if (viewFindViewById != null) {
            viewFindViewById.requestLayout();
        }
        requestLayout();
    }

    public final void addTabsContainer() {
        FrameLayout frameLayout;
        View view;
        View layout = null;
        if (this.mInnerExpandState == 2) {
            frameLayout = this.mMovableMainContainer;
            ExpandTitle expandTitle = this.mExpandTitle;
            if (expandTitle != null) {
                layout = expandTitle.getLayout();
            }
        } else {
            frameLayout = this.mMainContainer;
            CollapseTitle collapseTitle = this.mCollapseTitle;
            if (collapseTitle != null) {
                layout = collapseTitle.getLayout();
            }
        }
        boolean z = (!((this.mDisplayOptions & 16) != 0) || (view = this.mCustomNavView) == null || getCustomTitleView((FrameLayout) view.findViewById(miuix.appcompat.R.id.action_bar_expand_container)) == null) ? false : true;
        boolean z2 = ((this.mDisplayOptions & 8) == 0 || isAllTitlesEmpty()) ? false : true;
        if ((frameLayout.getChildCount() == 0 && !z) || !z2) {
            addTabsToMainContainers();
        } else if (z) {
            addSecondaryTabsToCollapseTabContainers();
            addSecondaryTabsToExpandTabContainers();
        } else if (layout != null && layout.getParent() == frameLayout) {
            ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(this.mContext);
            if (this.mInnerExpandState == 1) {
                addSecondaryTabsToCollapseTabContainers();
                addSecondaryTabsToExpandTabContainers();
            } else if (actionBarPolicy.isTightTitle() || hasTabsInContainer(frameLayout)) {
                addTabsToMainContainers();
            } else {
                addSecondaryTabsToCollapseTabContainers();
                addSecondaryTabsToExpandTabContainers();
            }
        }
        if (this.mMainContainer.getParent() != this) {
            safeAddView(this, this.mMainContainer);
        }
        if (this.mMediumContainer.getParent() != this) {
            safeAddView(this, this.mMediumContainer);
        }
        if (this.mMovableMainContainer.getParent() != this) {
            safeAddView(this, this.mMovableMainContainer, 0);
        }
        updateTabsLayoutParams();
        updateTightTitle();
    }

    public final void addTabsToMainContainers() {
        FrameLayout frameLayout = this.mCollapseTabContainer;
        if (frameLayout != null) {
            if (frameLayout.getParent() == this) {
                removeView(this.mCollapseTabContainer);
                this.mCollapseController.detachView(this.mCollapseTabContainer);
            }
            this.mCollapseTabContainer.removeAllViews();
            this.mCollapseTabContainer = null;
        }
        FrameLayout frameLayout2 = this.mMovableTabContainer;
        if (frameLayout2 != null) {
            if (frameLayout2.getParent() == this) {
                removeView(this.mMovableTabContainer);
                this.mMovableController.detachView(this.mMovableTabContainer);
            }
            this.mMovableTabContainer.removeAllViews();
            this.mMovableTabContainer = null;
        }
        this.mMainContainer.removeAllViews();
        ScrollingTabContainerView scrollingTabContainerView = this.mCollapseTabs;
        if (scrollingTabContainerView != null) {
            scrollingTabContainerView.setVisibility(0);
            safeAddView(this.mMainContainer, this.mCollapseTabs);
        }
        this.mMovableMainContainer.removeAllViews();
        ScrollingTabContainerView scrollingTabContainerView2 = this.mExpandTabs;
        if (scrollingTabContainerView2 != null) {
            scrollingTabContainerView2.setVisibility(0);
            safeAddView(this.mMovableMainContainer, this.mExpandTabs);
        }
        if (this.mInnerExpandState == 3) {
            setExpandState(this.mExpandState, false, false);
        }
        ensureMainContainerVisibleForMedium();
    }

    public final void animateLayoutWithProcess(float f) {
        float fMin = 1.0f - Math.min(1.0f, 3.0f * f);
        int i = this.mInnerExpandState;
        if (i == 3) {
            if (this.mLastProcess == f) {
                this.mLastResizingProcess = fMin;
                return;
            }
            if (fMin > 0.0f) {
                if (this.mIsCollapseTitleShowingOnResizing) {
                    this.mIsCollapseTitleShowingOnResizing = false;
                    this.mCollapseController.animTo(0.0f, 0, 20, this.mCollapseAnimHideConfig);
                    if (this.mActionBarTransitionListeners.size() > 0) {
                        Folme.useValue("target", 0).setFlags(1L).setup(2).setTo("expand", Integer.valueOf(this.mTransitionTarget)).to("expand", 20, this.mHideProcessConfig);
                    }
                    this.mMovableController.setVisibility(0);
                }
            } else if (!this.mIsCollapseTitleShowingOnResizing) {
                this.mIsCollapseTitleShowingOnResizing = true;
                this.mCollapseController.animTo(1.0f, 0, 0, this.mCollapseAnimShowConfig);
                if (this.mActionBarTransitionListeners.size() > 0) {
                    Folme.useValue("target", 0).setFlags(1L).setup(0).setTo("collapse", Integer.valueOf(this.mTransitionTarget)).to("collapse", 0, this.mShowProcessConfig);
                }
                this.mCollapseController.setVisibility(0);
            }
            if (this.mLastResizingProcess != fMin) {
                this.mMovableController.setAnimFrom(fMin, 0, 0, true);
                this.mLastResizingProcess = fMin;
                return;
            }
            return;
        }
        if (i == 2) {
            this.mNeedRequestLayoutOnExpandTitleShowing = this.mLastResizingProcess == 0.0f;
            this.mTransitionTarget = 20;
            this.mLastResizingProcess = 1.0f;
            this.mIsCollapseTitleShowingOnResizing = false;
            if (this.mLastProcess == f) {
                return;
            }
            this.mCollapseController.animTo(0.0f, 0, 20, this.mCollapseAnimHideConfig);
            this.mMovableController.animTo(1.0f, 0, 0, this.mMovableAnimShowConfig);
            return;
        }
        if (i == 0) {
            this.mNeedRequestLayoutOnExpandTitleShowing = false;
            this.mTransitionTarget = 0;
            this.mLastResizingProcess = 0.0f;
            this.mIsCollapseTitleShowingOnResizing = true;
            if (this.mLastProcess == f) {
                return;
            }
            this.mCollapseController.animTo(1.0f, 0, 0, this.mCollapseAnimShowConfig);
            this.mMovableController.animTo(0.0f, 0, 0, this.mMovableAnimNormalConfig);
            return;
        }
        if (i == 1) {
            this.mNeedRequestLayoutOnExpandTitleShowing = false;
            this.mTransitionTarget = 0;
            this.mLastResizingProcess = 0.0f;
            this.mIsCollapseTitleShowingOnResizing = false;
            if (hasTabsInContainer(this.mMainContainer)) {
                this.mMainContainer.setVisibility(0);
                this.mMainContainer.setAlpha(1.0f);
                this.mMainContainer.setTranslationX(0.0f);
                this.mMainContainer.setTranslationY(0.0f);
                AbsActionBarView.CollapseView collapseView = this.mMediumController;
                if (collapseView != null) {
                    collapseView.setVisibility(8);
                }
            } else {
                AbsActionBarView.CollapseView collapseView2 = this.mMediumController;
                if (collapseView2 != null) {
                    collapseView2.setVisibility(0);
                    this.mMediumController.setAnimFrom(1.0f, 0, 0, true);
                }
                this.mMainContainer.setVisibility(8);
                this.mMainContainer.clearFocus();
                this.mMainContainer.setAlpha(0.0f);
                this.mMainContainer.setTranslationX(0.0f);
                this.mMainContainer.setTranslationY(0.0f);
            }
            AbsActionBarView.CollapseView collapseView3 = this.mMovableController;
            if (collapseView3 != null) {
                collapseView3.setVisibility(8);
                this.mMovableController.setAnimFrom(0.0f, 0, 0, true);
            }
            ensureCollapseTabContainerVisibleForMedium();
        }
    }

    public final void animateMenuViewShowHide(boolean z) {
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView == null) {
            return;
        }
        ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) getParent().getParent();
        int collapsedHeight = actionMenuView.getCollapsedHeight();
        float fApplyDimension = TypedValue.applyDimension(1, 15.0f, getContext().getResources().getDisplayMetrics());
        float f = z ? fApplyDimension : 0.0f;
        if (z) {
            fApplyDimension = 0.0f;
        }
        ensureMenuAnimConfig(actionBarOverlayLayout, collapsedHeight);
        actionMenuView.setTranslationY(f);
        float f2 = z ? 1.0f : 0.0f;
        float f3 = z ? 0.0f : 50.0f;
        int i = z ? 100 : 0;
        actionMenuView.setAlpha(z ? 0.0f : 1.0f);
        this.mMenuAnimConfig.setDelay(i);
        Folme.useAt(actionMenuView).state().to(new AnimState("menu_end_state").add(ViewProperty.TRANSLATION_Y, fApplyDimension).add(ViewProperty.ALPHA, f2).add(SELF_BLUR, f3), this.mMenuAnimConfig);
        if (actionMenuView instanceof ResponsiveActionMenuView) {
            ((ResponsiveActionMenuView) actionMenuView).setHidden(!this.mIsBottomMenuVisible);
        }
    }

    public final void applyFloatingStateToButtons() {
        boolean zIsLightTheme = MiuixUIUtils.isLightTheme(getContext());
        boolean zBooleanValue = this.mFloatingMode.booleanValue();
        View view = this.mTitleUpView;
        if ((view instanceof ViewGroup) && ((ViewGroup) view).getChildCount() > 0) {
            FloatingEffectHelper.updateViewFloatingState(((ViewGroup) this.mTitleUpView).getChildAt(0), zBooleanValue, zIsLightTheme, this.mActionBarButtonMaterial);
        }
        HomeView homeView = this.mHomeLayout;
        if (homeView != null) {
            FloatingEffectHelper.updateViewFloatingState(homeView.getUp(), zBooleanValue, zIsLightTheme, this.mActionBarButtonMaterial);
        }
        View view2 = this.mNavigatorSwitch;
        if (view2 != null) {
            FloatingEffectHelper.updateViewFloatingState(view2, zBooleanValue, zIsLightTheme, this.mActionBarButtonMaterial);
        }
        ActionMenuView actionMenuView = this.mEndMenuView;
        if (actionMenuView instanceof EndActionMenuView) {
            ((EndActionMenuView) actionMenuView).setFloatingMode(zBooleanValue, zIsLightTheme, this.mActionBarButtonMaterial);
        }
    }

    public final void applyNewStateVisibility(int i) {
        if (i == 2) {
            this.mCollapseController.setVisibility(4);
            return;
        }
        if (i != 1) {
            if (i == 0) {
                this.mCollapseController.setVisibility(0);
            }
        } else {
            if (hasTabsInContainer(this.mMainContainer)) {
                this.mMainContainer.setVisibility(0);
                this.mMainContainer.setAlpha(1.0f);
            } else {
                this.mMainContainer.setVisibility(8);
                this.mMainContainer.clearFocus();
            }
            ensureCollapseTabContainerVisibleForMedium();
        }
    }

    public final void applySplitMenuInsets(FrameLayout.LayoutParams layoutParams, boolean z) {
        if (!z) {
            ViewUtils.resetPaddingBottom(this.mMenuView, this.mPendingInset.bottom);
            return;
        }
        int iDp2px = MiuixUIUtils.dp2px(getContext(), 24.0f);
        int i = this.mPendingInset.bottom;
        if (i < iDp2px) {
            layoutParams.bottomMargin = iDp2px;
        } else {
            layoutParams.bottomMargin = i + MiuixUIUtils.dp2px(getContext(), 8.0f);
        }
        ViewUtils.resetPaddingBottom(this.mMenuView, 0);
    }

    public final FrameLayout.LayoutParams buildSplitMenuLayoutParams(boolean z) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        layoutParams.gravity = 1;
        if (z) {
            layoutParams.topMargin = MiuixUIUtils.dp2px(getContext(), 20.0f);
            layoutParams.bottomMargin = MiuixUIUtils.dp2px(getContext(), 24.0f);
        }
        if (this.mPendingInset != null) {
            applySplitMenuInsets(layoutParams, z);
        }
        return layoutParams;
    }

    public final boolean canCollapseTitleBeShown() {
        if (this.mCollapseTitle == null || TextUtils.isEmpty(this.mTitle)) {
            return false;
        }
        boolean zCanTitleBeShown = this.mCollapseTitle.canTitleBeShown(this.mTitle.toString());
        if (!ActionBarPolicy.get(this.mContext).isTitleEnableEllipsis() || zCanTitleBeShown) {
            return zCanTitleBeShown;
        }
        return true;
    }

    public final boolean canConsumeScroll() {
        return (isShowTitle() || this.mCustomNavView != null) && isResizable() && this.mInnerExpandState != 1;
    }

    public final void checkItemsVisibleIsExpected(MenuItem menuItem, ArrayList arrayList) {
        if (menuItem == null || !menuItem.hasSubMenu()) {
            return;
        }
        if (menuItem.isVisible() != hasAnySubMenuVisibleItem(menuItem.getSubMenu())) {
            arrayList.add(menuItem);
        }
    }

    public void collapseActionView() {
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        MenuItemImpl menuItemImpl = expandedActionViewMenuPresenter == null ? null : expandedActionViewMenuPresenter.mCurrentExpandedItem;
        if (menuItemImpl != null) {
            menuItemImpl.collapseActionView();
        }
    }

    public final int computeCollapseContentHeight() {
        int iMax = Math.max(this.mTitleMinHeight, this.mMainContainer.getMeasuredHeight());
        View view = this.mCustomNavView;
        if (view != null && view.getParent() == this) {
            iMax = Math.max(iMax, this.mCustomNavView.getMeasuredHeight());
        }
        View view2 = this.mCenterView;
        return (view2 == null || view2.getVisibility() == 8) ? iMax : Math.max(iMax, this.mCenterView.getMeasuredHeight());
    }

    public final int computeExtraHeight(int i, int i2) {
        int i3 = this.mInnerExpandState;
        if (i3 == 3) {
            return this.mPendingHeight;
        }
        if (i3 == 2) {
            return i + i2;
        }
        return 0;
    }

    public final float computeLayoutProcess(int i, int i2, int i3) {
        if (hasTitle() || i != 0) {
            return Math.min(1.0f, ((i2 + i) - i3) / i);
        }
        return 1.0f;
    }

    public final int computePendingHeightForOldState(int i) {
        if (i == 2) {
            return this.mMovableMainContainer.getMeasuredHeight() + this.mMovableSecondaryTabHeight;
        }
        if (i == 1) {
            return this.mMainContainer.getMeasuredHeight();
        }
        return 0;
    }

    public final int computeTargetHeightForNewState(int i) {
        if (i == 2) {
            return this.mMovableMainContainer.getMeasuredHeight() + this.mMovableSecondaryTabHeight;
        }
        if (i == 1) {
            return this.mMainContainer.getMeasuredHeight();
        }
        return 0;
    }

    public final int computeTitleCenterLayoutStart(View view) {
        int width = (getWidth() - view.getMeasuredWidth()) / 2;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        return width - (layoutParams instanceof LinearLayout.LayoutParams ? ((LinearLayout.LayoutParams) layoutParams).getMarginStart() : 0);
    }

    public final void correctItemsVisible(ArrayList arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            MenuItem menuItem = (MenuItem) arrayList.get(i);
            if (menuItem != null) {
                menuItem.setVisible(!menuItem.isVisible());
            }
        }
    }

    public ActionMenuPresenter createActionMenuPresenter(MenuPresenter.Callback callback, boolean z) {
        ActionBarOverlayLayout actionBarOverlayLayoutFindActionBarOverlayLayout = findActionBarOverlayLayout();
        ActionMenuPresenter hyperSplitActionMenuPresenter = z ? new HyperSplitActionMenuPresenter(this.mContext, actionBarOverlayLayoutFindActionBarOverlayLayout, miuix.appcompat.R.layout.miuix_appcompat_responsive_action_menu_layout, miuix.appcompat.R.layout.miuix_appcompat_action_menu_item_layout) : new ActionMenuPresenter(this.mContext, actionBarOverlayLayoutFindActionBarOverlayLayout, miuix.appcompat.R.layout.miuix_appcompat_responsive_action_menu_layout, miuix.appcompat.R.layout.miuix_appcompat_action_menu_item_layout);
        hyperSplitActionMenuPresenter.setCallback(callback);
        hyperSplitActionMenuPresenter.setId(miuix.appcompat.R.id.action_menu_presenter);
        return hyperSplitActionMenuPresenter;
    }

    public final void createCollapseTitle(boolean z) {
        if (this.mCollapseTitle == null) {
            CollapseTitle collapseTitleGenerateCollapseTitle = ActionBarViewFactory.generateCollapseTitle(getContext(), this.mCollapseTitleStyleRes, this.mCollapseSubtitleStyleRes);
            this.mCollapseTitle = collapseTitleGenerateCollapseTitle;
            collapseTitleGenerateCollapseTitle.setVisible(this.mCollapsedTitleVisible);
            this.mCollapseTitle.setTextColorTransitEnable(this.mCollapseTitleColorTransitEnable, this.mInnerExpandState);
            this.mCollapseTitle.setAllTitlesClickable(this.mTitleClickable);
            this.mCollapseTitle.setTitle(this.mTitle);
            this.mCollapseTitle.setOnClickListener(this.mTitleClickListener, this.mTitleClickable);
            this.mCollapseTitle.setSubTitleOnClickListener(this.mSubTitleClickListener, this.mUserSubTitleClickListener != null);
            this.mCollapseTitle.setSubTitle(this.mSubtitle);
            if (!z) {
                safeAddView(this.mMainContainer, this.mCollapseTitle.getLayout());
                return;
            }
            if ((this.mDisplayOptions & 8) != 0) {
                if (getNavigationMode() == 2 && isTightTitleWithEmbeddedTabs()) {
                    return;
                }
                if (hasTabsInContainer(this.mMainContainer)) {
                    addSecondaryTabsToCollapseTabContainers();
                }
                this.mMainContainer.removeAllViews();
                safeAddView(this.mMainContainer, this.mCollapseTitle.getLayout());
            }
        }
    }

    public EndActionMenuPresenter createEndActionMenuPresenter(MenuPresenter.Callback callback, boolean z) {
        ActionBarOverlayLayout actionBarOverlayLayoutFindActionBarOverlayLayout = findActionBarOverlayLayout();
        EndActionMenuPresenter hyperActionMenuPresenter = z ? new HyperActionMenuPresenter(this.mContext, actionBarOverlayLayoutFindActionBarOverlayLayout, miuix.appcompat.R.layout.miuix_appcompat_action_end_menu_layout, miuix.appcompat.R.layout.miuix_appcompat_action_end_menu_item_layout, miuix.appcompat.R.layout.miuix_appcompat_action_bar_expanded_menu_layout, miuix.appcompat.R.layout.miuix_appcompat_action_bar_list_menu_item_layout) : new EndActionMenuPresenter(this.mContext, actionBarOverlayLayoutFindActionBarOverlayLayout, miuix.appcompat.R.layout.miuix_appcompat_action_end_menu_layout, miuix.appcompat.R.layout.miuix_appcompat_action_end_menu_item_layout, miuix.appcompat.R.layout.miuix_appcompat_action_bar_expanded_menu_layout, miuix.appcompat.R.layout.miuix_appcompat_action_bar_list_menu_item_layout);
        hyperActionMenuPresenter.setCallback(callback);
        hyperActionMenuPresenter.setId(miuix.appcompat.R.id.miuix_action_end_menu_presenter);
        return hyperActionMenuPresenter;
    }

    public final void createExpandTitle(boolean z) {
        boolean z2;
        View view;
        if (this.mExpandTitle == null) {
            ExpandTitle expandTitleGenerateExpandTitle = ActionBarViewFactory.generateExpandTitle(getContext());
            this.mExpandTitle = expandTitleGenerateExpandTitle;
            expandTitleGenerateExpandTitle.setVisible(this.mExpandTitleVisible);
            this.mExpandTitle.setTextColorTransitEnable(this.mExpandTitleColorTransitEnable, this.mInnerExpandState);
            this.mExpandTitle.setAllTitlesClickable(this.mTitleClickable);
            CharSequence charSequence = this.mTitle;
            if (!z || (this.mDisplayOptions & 16) == 0 || (view = this.mCustomNavView) == null || getCustomTitleView((FrameLayout) view.findViewById(miuix.appcompat.R.id.action_bar_expand_container)) == null || TextUtils.isEmpty(this.mInitCustomTitle)) {
                z2 = false;
            } else {
                charSequence = this.mInitCustomTitle;
                z2 = true;
            }
            this.mExpandTitle.setTitle(charSequence);
            this.mExpandTitle.setOnClickListener(this.mTitleClickListener, this.mTitleClickable);
            this.mExpandTitle.setSubTitleOnClickListener(this.mSubTitleClickListener, this.mUserSubTitleClickListener != null);
            if (z2) {
                this.mExpandTitle.setSubTitle(null);
            } else {
                this.mExpandTitle.setSubTitle(this.mSubtitle);
            }
            if (!z) {
                safeAddView(this.mMovableMainContainer, this.mExpandTitle.getLayout());
                return;
            }
            if ((this.mDisplayOptions & 8) != 0) {
                if (getNavigationMode() == 2 && isTightTitleWithEmbeddedTabs()) {
                    return;
                }
                if (hasTabsInContainer(this.mMovableMainContainer)) {
                    addSecondaryTabsToExpandTabContainers();
                }
                this.mMovableMainContainer.removeAllViews();
                safeAddView(this.mMovableMainContainer, this.mExpandTitle.getLayout());
            }
        }
    }

    public ExpandedActionViewMenuPresenter createExpandedActionViewMenuPresenter() {
        return new ExpandedActionViewMenuPresenter();
    }

    public final void createMediumTitle(boolean z) {
        if (this.mMediumTitle == null) {
            MediumTitle mediumTitleGenerateMediumTitle = ActionBarViewFactory.generateMediumTitle(getContext(), this.mMediumTitleStyleRes, this.mMediumSubtitleStyleRes);
            this.mMediumTitle = mediumTitleGenerateMediumTitle;
            mediumTitleGenerateMediumTitle.setVisible(this.mCollapsedTitleVisible);
            this.mMediumTitle.setTextColorTransitEnable(this.mCollapseTitleColorTransitEnable, this.mInnerExpandState);
            this.mMediumTitle.setTitleClickable(this.mTitleClickable);
            this.mMediumTitle.setTitle(this.mTitle);
            this.mMediumTitle.setOnClickListener(this.mTitleClickListener, this.mTitleClickable);
            this.mMediumTitle.setSubTitleOnClickListener(this.mSubTitleClickListener, this.mUserSubTitleClickListener != null);
            this.mMediumTitle.setSubTitle(this.mSubtitle);
            safeAddView(this.mMediumContainer, this.mMediumTitle.getLayout());
        }
    }

    public final FrameLayout createSecondaryTabContainer(int i) {
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setId(i);
        frameLayout.setPaddingRelative(frameLayout.getPaddingStart(), this.mSecondaryTabTopPadding, frameLayout.getPaddingEnd(), this.mSecondaryTabBottomPadding);
        frameLayout.setVisibility(0);
        return frameLayout;
    }

    public final void createTitleForCurrentState() {
        int i = this.mInnerExpandState;
        if (i == 2) {
            if (this.mExpandTitle == null) {
                createExpandTitle(false);
            }
            AbsActionBarView.CollapseView collapseView = this.mCollapseController;
            if (collapseView != null) {
                collapseView.onHide();
                return;
            }
            return;
        }
        if (i == 1) {
            if (this.mMediumTitle == null) {
                createMediumTitle(false);
            }
        } else if (i == 0 && this.mCollapseTitle == null) {
            createCollapseTitle(false);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Pair divideHyperMenuAndSplitMenu(Menu menu) {
        SubMenu subMenu;
        MenuBuilder menuBuilder = (MenuBuilder) menu;
        MenuBuilder menuBuilder2 = new MenuBuilder(this.mContext);
        menuBuilder2.setCallback(menuBuilder.getCallback());
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int size = menu.size() - 1; size >= 0; size--) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) menu.getItem(size);
            int groupId = menuItemImpl.getGroupId();
            int i = miuix.appcompat.R.id.miuix_split_action_menu_group;
            if (groupId == i) {
                menuBuilder.removeItemAt(size);
                SubMenu subMenu2 = menuItemImpl.getSubMenu();
                if (subMenu2 instanceof SubMenuBuilder) {
                    ((SubMenuBuilder) subMenu2).setParentMenu(menuBuilder2);
                }
                menuItemImpl.setMenu(menuBuilder2);
                arrayList.add(menuItemImpl);
            }
            if ((menuItemImpl.getGroupId() != i || menuItemImpl.getItemId() != miuix.appcompat.R.id.miuix_hyper_split_parent_item) && this.mHyperActionMenuEnable) {
                checkItemsVisibleIsExpected(menuItemImpl, arrayList2);
            }
        }
        if (this.mHyperActionMenuEnable) {
            correctItemsVisible(arrayList2);
        }
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            menuBuilder2.add((MenuItemImpl) arrayList.get(size2));
        }
        int size3 = menuBuilder.size() - 1;
        while (true) {
            if (size3 < 0) {
                subMenu = null;
                break;
            }
            MenuItem item = menuBuilder.getItem(size3);
            if (item.getItemId() == miuix.appcompat.R.id.miuix_hyper_split_parent_item) {
                menuBuilder.removeItemAt(size3);
                subMenu = item.getSubMenu();
                break;
            }
            size3--;
        }
        if (this.mHyperSplitMenuEnabled && subMenu != null) {
            ArrayList arrayList3 = new ArrayList();
            for (int i2 = 0; i2 < subMenu.size(); i2++) {
                MenuItem item2 = subMenu.getItem(i2);
                if (item2 instanceof MenuItemImpl) {
                    menuBuilder2.add((MenuItemImpl) item2);
                }
                checkItemsVisibleIsExpected(item2, arrayList3);
            }
            subMenu.clear();
            correctItemsVisible(arrayList3);
        }
        return new Pair(menuBuilder2, menuBuilder);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Pair divideMenuByGroup(Menu menu) {
        SubMenu subMenu;
        MenuBuilder menuBuilder = (MenuBuilder) menu;
        MenuBuilder menuBuilder2 = new MenuBuilder(this.mContext);
        menuBuilder2.setCallback(menuBuilder.getCallback());
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int size = menu.size() - 1; size >= 0; size--) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) menu.getItem(size);
            if (menuItemImpl.getGroupId() == miuix.appcompat.R.id.miuix_action_end_menu_group) {
                menuBuilder.removeItemAt(size);
                SubMenu subMenu2 = menuItemImpl.getSubMenu();
                if (subMenu2 instanceof SubMenuBuilder) {
                    ((SubMenuBuilder) subMenu2).setParentMenu(menuBuilder2);
                }
                menuItemImpl.setMenu(menuBuilder2);
                arrayList.add(menuItemImpl);
            }
            if ((menuItemImpl.getGroupId() != miuix.appcompat.R.id.miuix_split_action_menu_group || menuItemImpl.getItemId() != miuix.appcompat.R.id.miuix_hyper_split_parent_item) && this.mHyperActionMenuEnable) {
                checkItemsVisibleIsExpected(menuItemImpl, arrayList2);
            }
        }
        if (this.mHyperActionMenuEnable) {
            correctItemsVisible(arrayList2);
        }
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            menuBuilder2.add((MenuItemImpl) arrayList.get(size2));
        }
        int i = 0;
        while (true) {
            if (i >= menuBuilder.size()) {
                subMenu = null;
                break;
            }
            MenuItem item = menuBuilder.getItem(i);
            if (item.getItemId() == miuix.appcompat.R.id.miuix_hyper_split_parent_item) {
                menuBuilder.removeItemAt(i);
                subMenu = item.getSubMenu();
                break;
            }
            i++;
        }
        if (this.mHyperSplitMenuEnabled && subMenu != null) {
            ArrayList arrayList3 = new ArrayList();
            for (int i2 = 0; i2 < subMenu.size(); i2++) {
                MenuItem item2 = subMenu.getItem(i2);
                if (item2 instanceof MenuItemImpl) {
                    menuBuilder.add((MenuItemImpl) item2);
                }
                checkItemsVisibleIsExpected(item2, arrayList3);
            }
            subMenu.clear();
            correctItemsVisible(arrayList3);
        }
        return new Pair(menuBuilder, menuBuilder2);
    }

    public final void ensureCollapseTabContainerVisibleForMedium() {
        FrameLayout frameLayout = this.mCollapseTabContainer;
        if (frameLayout == null || frameLayout.getParent() != this) {
            return;
        }
        this.mCollapseTabContainer.setVisibility(0);
        this.mCollapseTabContainer.setAlpha(1.0f);
        MiuiBlurUtils.setSelfBlur(this.mCollapseTabContainer, 0, null);
        if (this.mCollapseTabContainer.isAttachedToWindow()) {
            Folme.useAt(this.mCollapseTabContainer).state().setTo(new AnimState("collapseTabVisible").add(ViewProperty.ALPHA, 1.0d).add(SELF_BLUR, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE));
        }
    }

    public final void ensureMainContainerVisibleForMedium() {
        if (this.mInnerExpandState != 1 || this.mCollapseTabs == null) {
            return;
        }
        this.mCollapseController.setAnimFrom(1.0f, 0, 0, true);
        this.mCollapseController.setVisibility(0);
        AbsActionBarView.CollapseView collapseView = this.mMediumController;
        if (collapseView != null) {
            collapseView.setVisibility(8);
        }
    }

    public final void ensureMenuAnimConfig(final ActionBarOverlayLayout actionBarOverlayLayout, final int i) {
        if (this.mMenuAnimConfig == null) {
            this.mMenuAnimConfig = new AnimConfig().setEase(-2, 0.82f, 0.3f).setSpecial(ViewProperty.ALPHA, FolmeEase.linear(150L), new float[0]).setSpecial(SELF_BLUR, FolmeEase.linear(150L), new float[0]);
        }
        TransitionListener transitionListener = this.mBottomMenuTransitionListener;
        if (transitionListener != null) {
            this.mMenuAnimConfig.removeListeners(transitionListener);
        }
        AnimConfig animConfig = this.mMenuAnimConfig;
        TransitionListener transitionListener2 = new TransitionListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.13
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                if (ActionBarView.this.mAnimateStart) {
                    return;
                }
                ActionBarView.this.mAnimateStart = true;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                ActionBarView.this.mAnimateStart = false;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, View.TRANSLATION_Y.getName());
                if (updateInfoFindByName == null) {
                    return;
                }
                actionBarOverlayLayout.onMenuStateChanged((int) (i - updateInfoFindByName.getFloatValue()), 0);
            }
        };
        this.mBottomMenuTransitionListener = transitionListener2;
        animConfig.addListeners(transitionListener2);
    }

    public final ActionBarOverlayLayout findActionBarOverlayLayout() {
        Object parent = getParent();
        while (true) {
            View view = (View) parent;
            if (view instanceof ActionBarOverlayLayout) {
                return (ActionBarOverlayLayout) view;
            }
            if (!(view.getParent() instanceof View)) {
                throw new IllegalStateException("ActionBarOverlayLayout not found");
            }
            parent = view.getParent();
        }
    }

    public final boolean freeMainContainerChildren() {
        if (hasTabsInContainer(this.mMainContainer)) {
            addSecondaryTabsToCollapseTabContainers();
        }
        if (hasTabsInContainer(this.mMovableMainContainer)) {
            addSecondaryTabsToExpandTabContainers();
        }
        this.mMainContainer.removeAllViews();
        this.mMovableMainContainer.removeAllViews();
        return true;
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ActionBar.LayoutParams(8388627);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ActionBar.LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams == null ? generateDefaultLayoutParams() : layoutParams;
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ ActionMenuView getActionMenuView() {
        return super.getActionMenuView();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ int getAnimatedVisibility() {
        return super.getAnimatedVisibility();
    }

    public int getBottomMenuCustomViewOffset() {
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView instanceof ResponsiveActionMenuView) {
            return ((ResponsiveActionMenuView) actionMenuView).getBottomMenuCustomViewOffset();
        }
        return 0;
    }

    public View getCenterView() {
        return this.mCenterView;
    }

    public CollapseTitle getCollapseTitle() {
        if (this.mCollapseTitle == null) {
            createCollapseTitle(true);
        }
        return this.mCollapseTitle;
    }

    public int getCollapsedHeight() {
        return this.mCollapseTotalHeight;
    }

    public View getCustomNavigationView() {
        return this.mCustomNavView;
    }

    public final TextView getCustomTitleView(View view) {
        if (view != null) {
            return (TextView) view.findViewById(android.R.id.title);
        }
        return null;
    }

    public int getDisplayOptions() {
        return this.mDisplayOptions;
    }

    public SpinnerAdapter getDropdownAdapter() {
        return this.mSpinnerAdapter;
    }

    public int getDropdownSelectedPosition() {
        return this.mSpinner.getSelectedItemPosition();
    }

    public int getEndActionMenuItemLimit() {
        return this.mEndActionMenuItemLimit;
    }

    public View getEndActionMenuView() {
        return this.mEndMenuView;
    }

    @Nullable
    public MenuBuilder getEndMenu() {
        return this.mEndMenu;
    }

    public View getEndView() {
        return this.mEndView;
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ int getExpandState() {
        return super.getExpandState();
    }

    public ExpandTitle getExpandTitle() {
        if (this.mExpandTitle == null) {
            createExpandTitle(true);
        }
        return this.mExpandTitle;
    }

    public int getExpandedHeight() {
        return this.mExpandTotalHeight;
    }

    public Map<Integer, Boolean> getHyperMenuPrimaryCheckedData() {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        if (actionMenuPresenter instanceof HyperActionMenuPresenter) {
            return ((HyperActionMenuPresenter) actionMenuPresenter).getHyperPrimaryCheckedData();
        }
        return null;
    }

    public Map<Integer, Boolean[]> getHyperMenuSecondaryCheckedData() {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        if (actionMenuPresenter instanceof HyperActionMenuPresenter) {
            return ((HyperActionMenuPresenter) actionMenuPresenter).getHyperSecondaryCheckedData();
        }
        return null;
    }

    @Nullable
    public Map<Integer, Boolean> getHyperSplitMenuPrimaryCheckedData() {
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        if (actionMenuPresenter instanceof HyperSplitActionMenuPresenter) {
            return ((HyperSplitActionMenuPresenter) actionMenuPresenter).getHyperSplitMenuPrimaryCheckedMap();
        }
        return null;
    }

    @Nullable
    public Map<Integer, Boolean[]> getHyperSplitMenuSecondaryCheckedData() {
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        if (actionMenuPresenter instanceof HyperSplitActionMenuPresenter) {
            return ((HyperSplitActionMenuPresenter) actionMenuPresenter).getHyperSplitMenuSecondaryCheckedMap();
        }
        return null;
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ ActionMenuView getMenuView() {
        return super.getMenuView();
    }

    public int getNavigationMode() {
        return this.mNavigationMode;
    }

    public View getSearchView() {
        return this.mSearchView;
    }

    public View getStartView() {
        return this.mStartView;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public final boolean hasAnySubMenuVisibleItem(SubMenu subMenu) {
        if (subMenu != null && subMenu.size() != 0) {
            for (int i = 0; i < subMenu.size(); i++) {
                if (subMenu.getItem(i).isVisible()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasExpandedActionView() {
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        return (expandedActionViewMenuPresenter == null || expandedActionViewMenuPresenter.mCurrentExpandedItem == null) ? false : true;
    }

    public final boolean hasTabsInContainer(ViewGroup viewGroup) {
        return viewGroup != null && viewGroup.getChildCount() == 1 && (viewGroup.getChildAt(0) instanceof ScrollingTabContainerView);
    }

    public final boolean hasTitle() {
        return !((this.mDisplayOptions & 8) == 0 || isAllTitlesEmpty()) || getNavigationMode() == 2;
    }

    public void hideBottomMenuCustomView() {
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView != null) {
            ((ResponsiveActionMenuView) actionMenuView).hideBottomMenuCustomView();
        }
    }

    public boolean hideEndOverflowMenu() {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        return actionMenuPresenter != null && actionMenuPresenter.hideOverflowMenu(true);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ boolean hideOverflowMenu() {
        return super.hideOverflowMenu();
    }

    public final void hideProgressBars(ProgressBar progressBar, ProgressBar progressBar2) {
        if (progressBar2 != null && progressBar2.getVisibility() == 0) {
            progressBar2.setVisibility(4);
        }
        if (progressBar == null || progressBar.getVisibility() != 0) {
            return;
        }
        progressBar.setVisibility(4);
    }

    public final void initExpandedHomeLayout() {
        if (this.mExpandedHomeLayout == null) {
            HomeView homeView = (HomeView) LayoutInflater.from(this.mContext).inflate(this.mHomeResId, (ViewGroup) this, false);
            this.mExpandedHomeLayout = homeView;
            homeView.setUp(true);
            this.mExpandedHomeLayout.setOnClickListener(this.mExpandedActionViewUpListener);
        }
    }

    public final void initHomeLayout() {
        if (this.mHomeLayout == null) {
            HomeView homeView = (HomeView) LayoutInflater.from(this.mContext).inflate(this.mHomeResId, (ViewGroup) this, false);
            this.mHomeLayout = homeView;
            homeView.setOnClickListener(this.mUpClickListener);
            this.mHomeLayout.setClickable(true);
            this.mHomeLayout.setFocusable(true);
            int i = this.mHomeAsUpIndicatorResId;
            if (i != 0) {
                this.mHomeLayout.setUpIndicator(i);
                this.mHomeAsUpIndicatorResId = 0;
            }
            Drawable drawable = this.mHomeAsUpIndicatorDrawable;
            if (drawable != null) {
                this.mHomeLayout.setUpIndicator(drawable);
                this.mHomeAsUpIndicatorDrawable = null;
            }
            Boolean bool = this.mFloatingMode;
            if (bool != null && bool.booleanValue()) {
                FloatingEffectHelper.updateViewFloatingState(this.mHomeLayout.getUp(), true, MiuixUIUtils.isLightTheme(getContext()), this.mActionBarButtonMaterial);
            }
            addView(this.mHomeLayout);
        }
    }

    public void initIndeterminateProgress() {
        ProgressBar progressBar = new ProgressBar(this.mContext, null, miuix.appcompat.R.attr.actionBarIndeterminateProgressStyle);
        this.mIndeterminateProgressView = progressBar;
        progressBar.setId(miuix.appcompat.R.id.progress_circular);
        this.mIndeterminateProgressView.setVisibility(8);
        this.mIndeterminateProgressView.setIndeterminate(true);
        addView(this.mIndeterminateProgressView);
    }

    public final void initTitleUpView() {
        int i;
        if (this.mTitleUpView == null) {
            View viewGenerateTitleUpView = ActionBarViewFactory.generateTitleUpView(getContext(), null);
            this.mTitleUpView = viewGenerateTitleUpView;
            viewGenerateTitleUpView.setOnClickListener(this.mUpClickListener);
        }
        int i2 = this.mDisplayOptions;
        boolean z = (i2 & 4) != 0;
        boolean z2 = (i2 & 2) != 0;
        View view = this.mTitleUpView;
        if (z2) {
            i = 8;
        } else {
            i = z ? 0 : 4;
        }
        view.setVisibility(i);
        this.mTitleUpView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        this.mTitleUpView.setAccessibilityTraversalBefore(miuix.appcompat.R.id.action_bar_movable_container);
        safeAddView(this, this.mTitleUpView);
        Boolean bool = this.mFloatingMode;
        if (bool == null || !bool.booleanValue()) {
            return;
        }
        View view2 = this.mTitleUpView;
        if (!(view2 instanceof ViewGroup) || ((ViewGroup) view2).getChildCount() <= 0) {
            return;
        }
        FloatingEffectHelper.updateViewFloatingState(((ViewGroup) this.mTitleUpView).getChildAt(0), true, MiuixUIUtils.isLightTheme(getContext()), this.mActionBarButtonMaterial);
    }

    public final boolean isAccessibilityServiceActive() {
        AccessibilityManager accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        return accessibilityManager != null && accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled();
    }

    public final boolean isAllTitlesEmpty() {
        return TextUtils.isEmpty(this.mTitle) && TextUtils.isEmpty(this.mSubtitle);
    }

    public boolean isCollapsed() {
        return this.mIsCollapsed;
    }

    public boolean isEndActionMenuEnable() {
        return this.mEndActionMenuEnable;
    }

    public boolean isEndOverflowMenuShowing() {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        return actionMenuPresenter != null && actionMenuPresenter.isOverflowMenuShowing();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ boolean isOverflowMenuShowing() {
        return super.isOverflowMenuShowing();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ boolean isOverflowReserved() {
        return super.isOverflowReserved();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ boolean isResizable() {
        return super.isResizable();
    }

    public final boolean isSelfBlurEnabled() {
        return HyperMaterialUtils.isEnable();
    }

    public final boolean isShowTitle() {
        if (this.mMainContainer.getChildCount() <= 0) {
            return (this.mCustomNavView == null || this.mCollapseCustomContainer == null) ? false : true;
        }
        return true;
    }

    public final boolean isSimpleCustomNavView() {
        View view = this.mCustomNavView;
        if (view == null || view.getVisibility() != 0) {
            return true;
        }
        ViewGroup.LayoutParams layoutParams = this.mCustomNavView.getLayoutParams();
        ActionBar.LayoutParams layoutParams2 = layoutParams instanceof ActionBar.LayoutParams ? (ActionBar.LayoutParams) layoutParams : null;
        return layoutParams2 != null && normalizeHorizontalGravity(layoutParams2.gravity, ViewUtils.isLayoutRtl(this)) == 8388613;
    }

    public boolean isSplitActionBar() {
        return this.mSplitActionBarEnable;
    }

    public boolean isTightTitleWithEmbeddedTabs() {
        return this.mTabsExit && ActionBarPolicy.get(this.mContext).isTightTitle();
    }

    public final boolean isTitleCenter() {
        if (!this.mTitleCenter || !isSimpleCustomNavView()) {
            return false;
        }
        HomeView homeView = this.mHomeLayout;
        return homeView == null || homeView.getVisibility() == 8;
    }

    public boolean isUserSetEndActionMenuItemLimit() {
        return this.mUserSetEndActionMenuItemLimit;
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ boolean isUserSetExpandState() {
        return super.isUserSetExpandState();
    }

    public void makeMenuViewShowHide(final boolean z) {
        if (this.mSplitActionBarEnable && z != this.mIsBottomMenuVisible) {
            if (this.mMenuView == null) {
                scheduleBottomMenuAnimation(new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.14
                    @Override // java.lang.Runnable
                    public void run() {
                        ActionBarView.this.makeMenuViewShowHide(z);
                        ActionMenuView actionMenuView = ActionBarView.this.mMenuView;
                        if (actionMenuView != null) {
                            actionMenuView.setVisibility(0);
                        }
                    }
                });
                return;
            }
            ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) this.mSplitView.getParent();
            int collapsedHeight = this.mMenuView.getCollapsedHeight();
            this.mMenuView.setTranslationY(z ? 0.0f : collapsedHeight);
            if (!z) {
                collapsedHeight = 0;
            }
            actionBarOverlayLayout.animateContentMarginBottomByBottomMenu(collapsedHeight);
            this.mIsBottomMenuVisible = z;
            ActionMenuView actionMenuView = this.mMenuView;
            if (actionMenuView instanceof ResponsiveActionMenuView) {
                ((ResponsiveActionMenuView) actionMenuView).setHidden(!z);
            }
        }
    }

    public void makeMenuViewShowHideWithAnimation(final boolean z) {
        if (z == this.mIsBottomMenuVisible) {
            return;
        }
        if (this.mMenuView == null) {
            scheduleBottomMenuAnimation(new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.12
                @Override // java.lang.Runnable
                public void run() {
                    ActionBarView.this.makeMenuViewShowHide(z);
                    ActionMenuView actionMenuView = ActionBarView.this.mMenuView;
                    if (actionMenuView != null) {
                        actionMenuView.setVisibility(0);
                    }
                }
            });
            return;
        }
        this.mIsBottomMenuVisible = z;
        this.mAnimateStart = false;
        if (this.mSplitActionBarEnable) {
            animateMenuViewShowHide(z);
        }
    }

    public final int normalizeHorizontalGravity(int i, boolean z) {
        int i2 = 8388615 & i;
        if ((i & 8388608) == 0) {
            if (i2 == 3) {
                return z ? 8388613 : 8388611;
            }
            if (i2 == 5) {
                return z ? 8388611 : 8388613;
            }
        }
        return i2;
    }

    public final void notifyMenuStateChange() {
        if (!this.mSplitActionBarEnable || this.mMenuView == null) {
            return;
        }
        ((ActionBarOverlayLayout) this.mSplitView.getParent()).onMenuStateChanged((int) (this.mMenuView.getCollapsedHeight() - this.mMenuView.getTranslationY()), 0);
    }

    public void onActionModeEnd(boolean z, boolean z2) {
        this.mInActionMode = false;
        if (z2) {
            this.mInSearchMode = false;
            restoreContainerVisibilityForState(getExpandState());
            restoreActionBarChildrenAlpha();
            if (z) {
                this.mMovableController.setAcceptAlphaChange(true);
                this.mCollapseController.setAcceptAlphaChange(true);
                this.mMediumController.setAcceptAlphaChange(true);
                postRefreshTitleControllerStatus();
                return;
            }
            return;
        }
        if (!this.mInSearchMode) {
            setAlpha(0.0f);
            setVisibility(0);
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, AnimatedProperty.PROPERTY_NAME_ALPHA, 0.0f, 1.0f);
            objectAnimatorOfFloat.setInterpolator(new AccelerateInterpolator());
            objectAnimatorOfFloat.setDuration(300L);
            objectAnimatorOfFloat.start();
        }
        this.mInSearchMode = false;
        restoreContainerVisibilityForState(getExpandState());
        restoreActionBarChildrenAlpha();
        if (z) {
            this.mMovableController.setAcceptAlphaChange(true);
            this.mCollapseController.setAcceptAlphaChange(true);
            this.mMediumController.setAcceptAlphaChange(true);
            postRefreshTitleControllerStatus();
        }
    }

    public void onActionModeStart(boolean z, boolean z2) {
        this.mInActionMode = true;
        this.mInSearchMode = z;
        if (!z) {
            this.mCollapseController.setVisibility(8);
            this.mMediumController.setVisibility(8);
            this.mMovableController.setVisibility(8);
            setVisibility(8);
        }
        View view = this.mStartView;
        if (view != null) {
            view.setAlpha(0.0f);
        }
        View view2 = this.mEndView;
        if (view2 != null) {
            view2.setAlpha(0.0f);
        }
        View view3 = this.mCenterView;
        if (view3 != null) {
            view3.setAlpha(0.0f);
        }
        View view4 = this.mTitleUpView;
        if (view4 != null) {
            view4.setAlpha(0.0f);
        }
        View view5 = this.mNavigatorSwitch;
        if (view5 != null) {
            NavigatorSwitchPresenter navigatorSwitchPresenter = (NavigatorSwitchPresenter) view5.getTag(miuix.appcompat.R.id.miuix_appcompat_navigator_switch_presenter);
            if (navigatorSwitchPresenter != null) {
                navigatorSwitchPresenter.suppressAlpha(true, 0.0f);
            } else {
                this.mNavigatorSwitch.setAlpha(0.0f);
            }
        }
        if (z2) {
            this.mMovableController.setAcceptAlphaChange(false);
            this.mMediumController.setAcceptAlphaChange(false);
            this.mCollapseController.setAcceptAlphaChange(false);
        }
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public void onAnimatedExpandStateChanged(int i, int i2) {
        IStateStyle iStateStyle = this.mStateChangeAnimStateStyle;
        if (iStateStyle != null) {
            iStateStyle.cancel();
        }
        this.mPendingHeight = computePendingHeightForOldState(i);
        AnimConfig animConfigAddListeners = new AnimConfig().addListeners(new InnerTransitionListener(this));
        int iComputeTargetHeightForNewState = computeTargetHeightForNewState(i2);
        applyNewStateVisibility(i2);
        updateTitleVisibilityByState(i2);
        this.mStateChangeAnimStateStyle = Folme.useValue(new Object[0]).setFlags(1L).setTo("actionbar_state_change", Integer.valueOf(this.mPendingHeight)).to("actionbar_state_change", Integer.valueOf(iComputeTargetHeightForNewState), animConfigAddListeners);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mCollapseController.onAttachedToWindow();
        this.mMediumController.onAttachedToWindow();
        this.mMovableController.onAttachedToWindow();
        updateBackInvokedCallbackState();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(null, miuix.appcompat.R.styleable.ActionBar, getActionBarStyle(), 0);
        this.mTitleMinHeight = typedArrayObtainStyledAttributes.getLayoutDimension(miuix.appcompat.R.styleable.ActionBar_android_minHeight, 0);
        this.mTitleMaxHeight = typedArrayObtainStyledAttributes.getLayoutDimension(miuix.appcompat.R.styleable.ActionBar_android_maxHeight, 0);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(miuix.appcompat.R.styleable.ActionBar_actionBarMaxSizeInLargeFont, this.mContext.getResources().getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_large_font_max_height));
        boolean zResolveBoolean = AttributeResolver.resolveBoolean(this.mContext, miuix.appcompat.R.attr.actionBarTitleAdaptLargeFont, true);
        boolean z = MiuixUIUtils.getFontLevel(this.mContext) == 2;
        if (!zResolveBoolean || !z) {
            dimensionPixelSize = this.mTitleMaxHeight;
        }
        this.mTitleMaxHeight = dimensionPixelSize;
        typedArrayObtainStyledAttributes.recycle();
        Configuration configuration2 = getResources().getConfiguration();
        this.mCollapseTitleShowable = true;
        updateTightTitle();
        if ((getDisplayOptions() & 8) != 0) {
            CollapseTitle collapseTitle = this.mCollapseTitle;
            if (collapseTitle != null) {
                collapseTitle.onConfigurationChanged(configuration2);
            }
            ExpandTitle expandTitle = this.mExpandTitle;
            if (expandTitle != null) {
                expandTitle.onConfigurationChanged(configuration2);
            }
            MediumTitle mediumTitle = this.mMediumTitle;
            if (mediumTitle != null) {
                mediumTitle.onConfigurationChanged(configuration2);
            }
        }
        Resources resources = this.mContext.getResources();
        float f = resources.getDisplayMetrics().density;
        if (f != this.mDensity) {
            this.mDensity = f;
            this.mUncollapseTabPaddingH = resources.getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_title_tab_horizontal_padding);
            this.mExpandTabTopPadding = resources.getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_title_top_padding);
            this.mExpandTitlePaddingBottom = resources.getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_title_bottom_padding);
            this.mExpandSubtitlePaddingBottom = resources.getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_subtitle_bottom_padding);
            this.mTitleUpViewMarginStart = resources.getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_up_view_margin_start);
            this.mTitleUpViewMarginEnd = 0;
            this.mTitleGapPaddingStart = resources.getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_title_padding_gap);
            this.mMediumTitlePaddingStartNoUp = resources.getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_medium_title_padding_start_no_up);
        }
        this.mUncollapsePaddingH = resources.getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_title_horizontal_padding);
        this.mMovableMainContainer.setPaddingRelative(this.mUncollapsePaddingH, getResources().getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_title_top_padding), this.mUncollapsePaddingH, TextUtils.isEmpty(this.mSubtitle) ? this.mExpandTitlePaddingBottom : this.mExpandSubtitlePaddingBottom);
        this.mSecondaryTabTopPadding = resources.getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_secondary_tab_top_padding);
        this.mSecondaryTabBottomPadding = resources.getDimensionPixelOffset(miuix.appcompat.R.dimen.miuix_appcompat_action_bar_secondary_tab_bottom_padding);
        FrameLayout frameLayout = this.mCollapseTabContainer;
        if (frameLayout != null) {
            frameLayout.setPaddingRelative(frameLayout.getPaddingStart(), this.mSecondaryTabTopPadding, this.mCollapseTabContainer.getPaddingEnd(), this.mSecondaryTabBottomPadding);
        }
        FrameLayout frameLayout2 = this.mMovableTabContainer;
        if (frameLayout2 != null) {
            frameLayout2.setPaddingRelative(frameLayout2.getPaddingStart(), this.mSecondaryTabTopPadding, this.mMovableTabContainer.getPaddingEnd(), this.mSecondaryTabBottomPadding);
        }
        setPaddingRelative(AttributeResolver.resolveDimensionPixelSize(getContext(), miuix.appcompat.R.attr.actionBarPaddingStart), getPaddingTop(), AttributeResolver.resolveDimensionPixelSize(getContext(), miuix.appcompat.R.attr.actionBarPaddingEnd), getPaddingBottom());
        if (this.mTabsExit) {
            updateTabsLayoutParams();
        }
        post(new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ActionBarView.$r8$lambda$ADuzlzSkaxUsMj_gi3Z3NBj_ofk(this.f$0);
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.hideOverflowMenu(false);
            this.mActionMenuPresenter.hideSubMenus();
        }
        ActionMenuPresenter actionMenuPresenter2 = this.mEndActionMenuPresenter;
        if (actionMenuPresenter2 != null) {
            actionMenuPresenter2.hideOverflowMenu(false);
            this.mEndActionMenuPresenter.hideSubMenus();
        }
        View.OnLayoutChangeListener onLayoutChangeListener = this.mWindowWidthWatcher;
        if (onLayoutChangeListener != null) {
            removeOnLayoutChangeListener(onLayoutChangeListener);
            this.mWindowWidthWatcher = null;
        }
        this.mCollapseController.onDetachedFromWindow();
        this.mMediumController.onDetachedFromWindow();
        this.mMovableController.onDetachedFromWindow();
        updateBackInvokedCallbackState();
        this.mInitialA11yFocusRequested = false;
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
            AbsActionBarView.CollapseView collapseView2 = this.mMediumController;
            if (collapseView2 != null) {
                collapseView2.setVisibility(8);
            }
        }
        if (i2 == 2) {
            if (this.mMovableMainContainer.getAlpha() > 0.0f) {
                AbsActionBarView.CollapseView collapseView3 = this.mCollapseController;
                if (collapseView3 != null) {
                    collapseView3.setAnimFrom(0.0f, 0, 20, true);
                }
                AbsActionBarView.CollapseView collapseView4 = this.mMediumController;
                if (collapseView4 != null) {
                    collapseView4.setAnimFrom(0.0f, 0, 0, true);
                }
                AbsActionBarView.CollapseView collapseView5 = this.mMovableController;
                if (collapseView5 != null) {
                    collapseView5.setAnimFrom(1.0f, 0, 0, true);
                }
            }
            AbsActionBarView.CollapseView collapseView6 = this.mCollapseController;
            if (collapseView6 != null) {
                collapseView6.setVisibility(4);
            }
            AbsActionBarView.CollapseView collapseView7 = this.mMediumController;
            if (collapseView7 != null) {
                collapseView7.setVisibility(8);
            }
            AbsActionBarView.CollapseView collapseView8 = this.mMovableController;
            if (collapseView8 != null) {
                collapseView8.setVisibility(0);
            }
            if (getParent() instanceof ActionBarContainer) {
                ((ActionBarContainer) getParent()).onActionBarExpandStateToExpand();
            }
        }
        if (i2 == 1) {
            if (hasTabsInContainer(this.mMainContainer)) {
                this.mMainContainer.setVisibility(0);
                this.mMainContainer.setAlpha(1.0f);
                AbsActionBarView.CollapseView collapseView9 = this.mMediumController;
                if (collapseView9 != null) {
                    collapseView9.setVisibility(8);
                }
            } else {
                this.mMainContainer.setVisibility(8);
                this.mMainContainer.clearFocus();
                this.mMainContainer.setAlpha(0.0f);
                AbsActionBarView.CollapseView collapseView10 = this.mMediumController;
                if (collapseView10 != null) {
                    collapseView10.setAnimFrom(1.0f, 0, 0, true);
                    this.mMediumController.setVisibility(0);
                    this.mMediumController.onShow();
                }
                this.mMediumContainer.setVisibility(0);
                this.mMediumContainer.setAlpha(1.0f);
            }
            AbsActionBarView.CollapseView collapseView11 = this.mMovableController;
            if (collapseView11 != null) {
                collapseView11.setVisibility(8);
            }
            this.mMovableMainContainer.setAlpha(0.0f);
            ensureCollapseTabContainerVisibleForMedium();
        }
        if (i2 == 0) {
            AbsActionBarView.CollapseView collapseView12 = this.mCollapseController;
            if (collapseView12 != null && !this.mInActionMode) {
                collapseView12.setAnimFrom(1.0f, 0, 0, true);
                this.mCollapseController.setVisibility(0);
                this.mCollapseController.onShow();
            }
            AbsActionBarView.CollapseView collapseView13 = this.mMediumController;
            if (collapseView13 != null) {
                collapseView13.setVisibility(8);
            }
            AbsActionBarView.CollapseView collapseView14 = this.mMovableController;
            if (collapseView14 != null) {
                collapseView14.setVisibility(8);
            }
        } else {
            this.mPendingHeight = (getHeight() - this.mCollapseTotalHeight) + this.mCollapseSecondaryTabHeight;
        }
        updateTitleVisibilityByState(i2);
        if (this.mActionBarTransitionListeners.size() > 0) {
            if (this.mExpandStateBeforeResizing == i2 && this.mExpandStateOnLayout == i2) {
                return;
            }
            for (ActionBarTransitionListener actionBarTransitionListener : this.mActionBarTransitionListeners) {
                if (i2 == 2) {
                    actionBarTransitionListener.onExpandStateChanged(2);
                } else if (i2 == 1) {
                    actionBarTransitionListener.onExpandStateChanged(1);
                } else if (i2 == 0) {
                    actionBarTransitionListener.onExpandStateChanged(0);
                }
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int iComputeCollapseContentHeight = computeCollapseContentHeight();
        int i5 = this.mCollapseSecondaryTabHeight;
        int measuredHeight = this.mMovableMainContainer.getMeasuredHeight();
        int i6 = this.mMovableSecondaryTabHeight;
        int iComputeExtraHeight = computeExtraHeight(measuredHeight, i6);
        int i7 = (i4 - i2) - i6;
        float fComputeLayoutProcess = computeLayoutProcess(measuredHeight, i6, iComputeExtraHeight);
        onLayoutCollapseViews(z, i, 0, i3, iComputeCollapseContentHeight, i5);
        onLayoutExpandViews(z, i, i7 - iComputeExtraHeight, i3, i7, i6, fComputeLayoutProcess);
        notifyMenuStateChange();
        if (!this.mInActionMode && !this.mInActionModeAnimating) {
            animateLayoutWithProcess(fComputeLayoutProcess);
        }
        this.mLastProcess = fComputeLayoutProcess;
        updateBadgeOnMenuItemViews();
    }

    /* JADX WARN: Code duplicated, block: B:113:0x0193  */
    /* JADX WARN: Code duplicated, block: B:115:0x0199  */
    /* JADX WARN: Code duplicated, block: B:117:0x01a1  */
    /* JADX WARN: Code duplicated, block: B:118:0x01a6  */
    /* JADX WARN: Code duplicated, block: B:122:0x01bd A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:123:0x01bf  */
    /* JADX WARN: Code duplicated, block: B:125:0x01c7  */
    /* JADX WARN: Code duplicated, block: B:127:0x01cb  */
    /* JADX WARN: Code duplicated, block: B:128:0x01cd A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:129:0x01cf  */
    /* JADX WARN: Code duplicated, block: B:130:0x01d9  */
    /* JADX WARN: Code duplicated, block: B:132:0x01f7  */
    /* JADX WARN: Code duplicated, block: B:133:0x0206  */
    /* JADX WARN: Code duplicated, block: B:140:0x0223  */
    /* JADX WARN: Code duplicated, block: B:141:0x0225  */
    /* JADX WARN: Code duplicated, block: B:149:0x0239  */
    /* JADX WARN: Code duplicated, block: B:151:0x0246  */
    /* JADX WARN: Code duplicated, block: B:185:0x02ae  */
    public final void onLayoutCollapseViews(boolean z, int i, int i2, int i3, int i4, int i5) {
        HomeView homeView;
        int i6;
        int i7;
        int i8;
        int paddingBottom;
        View view;
        boolean z2;
        int i9;
        int iPositionChild;
        View view2;
        int measuredWidth;
        int i10;
        int iComputeTitleCenterLayoutStart;
        int measuredWidth2;
        int i11;
        int measuredWidth3;
        int measuredWidth4;
        int paddingStart = getPaddingStart();
        int paddingTop = getPaddingTop();
        FrameLayout frameLayout = this.mMainContainer;
        FrameLayout frameLayout2 = this.mMediumContainer;
        FrameLayout frameLayout3 = this.mCollapseTabContainer;
        boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this);
        int paddingTop2 = (i4 - getPaddingTop()) - getPaddingBottom();
        if (paddingTop2 <= 0) {
            return;
        }
        int i12 = i3 - i;
        int paddingEnd = i12 - getPaddingEnd();
        boolean z3 = this.mButtonCenterVerticalEnabled && paddingTop2 > this.mTitleMinHeight;
        ActionMenuView actionMenuView = this.mEndMenuView;
        if (actionMenuView != null && actionMenuView.getParent() == this && this.mEndMenuView.getVisibility() != 8) {
            positionChildInverse(this.mEndMenuView, paddingEnd, paddingTop, paddingTop2, z3);
            paddingEnd -= this.mEndMenuView.getMeasuredWidth();
        }
        View view3 = this.mEndView;
        if (view3 != null && view3.getVisibility() != 8) {
            positionChildInverse(this.mEndView, paddingEnd, paddingTop, paddingTop2, z3);
            paddingEnd -= this.mEndView.getMeasuredWidth();
        }
        View view4 = this.mSearchView;
        if (view4 != null && view4.getVisibility() != 8) {
            int i13 = paddingEnd - this.mSearchViewMargin;
            positionChildInverse(this.mSearchView, i13, paddingTop, paddingTop2, z3);
            paddingEnd = i13 - (this.mSearchView.getMeasuredWidth() + this.mSearchViewMargin);
        }
        int marginEnd = paddingEnd;
        ProgressBar progressBar = this.mIndeterminateProgressView;
        if (progressBar != null && progressBar.getVisibility() != 8) {
            positionChildInverse(this.mIndeterminateProgressView, marginEnd - this.mProgressBarPadding, paddingTop, paddingTop2, z3);
            marginEnd -= this.mIndeterminateProgressView.getMeasuredWidth() - (this.mProgressBarPadding * 2);
        }
        View view5 = this.mNavigatorSwitch;
        int iPositionChild2 = (view5 == null || view5.getVisibility() == 8) ? 0 : positionChild(this.mNavigatorSwitch, paddingStart, paddingTop, paddingTop2, z3);
        HomeView homeView2 = this.mExpandedActionView != null ? this.mExpandedHomeLayout : this.mHomeLayout;
        if (homeView2 == null || homeView2.getVisibility() != 0) {
            homeView = homeView2;
            View view6 = this.mStartView;
            if (view6 != null && view6.getVisibility() != 8) {
                paddingStart += positionChild(this.mStartView, paddingStart, paddingTop, paddingTop2, z3);
            }
        } else {
            int startOffset = homeView2.getStartOffset();
            homeView = homeView2;
            paddingStart += positionChild(homeView2, paddingStart + startOffset, paddingTop, paddingTop2, z3) + startOffset;
        }
        int marginStart = paddingStart;
        if (this.mExpandedActionView == null) {
            boolean zIsShowTitle = isShowTitle();
            if ((zIsShowTitle || (getDisplayOptions() & 32) != 0) && (view = this.mTitleUpView) != null && view.getVisibility() == 0) {
                marginStart += (this.mHasStartView || this.mHasNavigatorSwitchView) ? positionChildWithOffset(this.mTitleUpView, marginStart, paddingTop, paddingTop2, z3, this.mTitleUpViewMarginStart) : positionChild(this.mTitleUpView, marginStart, paddingTop, paddingTop2, z3);
            }
            boolean z4 = (zIsShowTitle || this.mInnerExpandState == 3) && frameLayout != null && frameLayout.getChildCount() > 0;
            int i14 = marginStart + iPositionChild2;
            int iMax = this.mTitleGapPaddingStart + i14;
            View view7 = this.mTitleUpView;
            boolean z5 = view7 != null && view7.getVisibility() == 0;
            boolean z6 = homeView != null && homeView.getVisibility() == 0;
            View view8 = this.mStartView;
            boolean z7 = (view8 == null || view8.getVisibility() == 8) ? false : true;
            View view9 = this.mNavigatorSwitch;
            if (view9 != null) {
                z2 = z4;
                boolean z8 = view9.getVisibility() != 8;
                if (!z5 && !z6 && !z7 && !z8) {
                    iMax = Math.max(this.mMediumTitlePaddingStartNoUp, iMax);
                }
                i9 = iMax;
                if (z2) {
                    i6 = paddingTop2;
                    iPositionChild = marginStart;
                } else if (isTitleCenter()) {
                    if (frameLayout.getVisibility() != 8) {
                        iComputeTitleCenterLayoutStart = computeTitleCenterLayoutStart(frameLayout);
                    } else {
                        iComputeTitleCenterLayoutStart = i14;
                    }
                    this.mCollapseTitleShowable = canCollapseTitleBeShown();
                    updateTightTitle();
                    measuredWidth2 = frameLayout.getMeasuredWidth() + iComputeTitleCenterLayoutStart;
                    if (frameLayout.getMeasuredWidth() + i14 <= marginEnd) {
                        i11 = i14;
                        measuredWidth3 = marginEnd;
                    } else if (measuredWidth2 > marginEnd) {
                        measuredWidth4 = marginEnd - frameLayout.getMeasuredWidth();
                        if (measuredWidth4 < i14) {
                            i11 = i14;
                        } else {
                            i11 = measuredWidth4;
                        }
                        measuredWidth3 = marginEnd;
                    } else if (iComputeTitleCenterLayoutStart < i14) {
                        measuredWidth3 = i14 + frameLayout.getMeasuredWidth();
                        i11 = i14;
                    } else {
                        i11 = iComputeTitleCenterLayoutStart;
                        measuredWidth3 = measuredWidth2;
                    }
                    int measuredHeight = frameLayout.getMeasuredHeight();
                    int i15 = ((paddingTop2 - measuredHeight) / 2) + paddingTop;
                    int i16 = measuredHeight + i15;
                    i6 = paddingTop2;
                    iPositionChild = i14;
                    ViewUtils.layoutChildView(this, frameLayout, i11, i15, measuredWidth3, i16);
                } else {
                    i6 = paddingTop2;
                    iPositionChild = i14 + positionChild(frameLayout, i14 + this.mTitleGapPaddingStart, paddingTop, i6);
                }
                if (zIsShowTitle && frameLayout2 != null && frameLayout2.getChildCount() > 0) {
                    int measuredHeight2 = frameLayout2.getMeasuredHeight();
                    int i17 = paddingTop + ((i6 - measuredHeight2) / 2);
                    measuredWidth = frameLayout2.getMeasuredWidth() + i9;
                    if (measuredWidth > marginEnd) {
                        i10 = marginEnd;
                    } else {
                        i10 = measuredWidth;
                    }
                    ViewUtils.layoutChildView(this, frameLayout2, i9, i17, i10, i17 + measuredHeight2);
                }
                if (this.mNavigationMode == 1 || (view2 = this.mListNavLayout) == null) {
                    marginStart = iPositionChild;
                } else {
                    if (zIsShowTitle) {
                        iPositionChild += this.mItemPadding;
                    }
                    marginStart = iPositionChild + positionChild(view2, iPositionChild, paddingTop, i6) + this.mItemPadding;
                }
            } else {
                z2 = z4;
            }
            if (!z5) {
                iMax = Math.max(this.mMediumTitlePaddingStartNoUp, iMax);
            }
            i9 = iMax;
            if (z2) {
                i6 = paddingTop2;
                iPositionChild = marginStart;
            } else if (isTitleCenter()) {
                if (frameLayout.getVisibility() != 8) {
                    iComputeTitleCenterLayoutStart = computeTitleCenterLayoutStart(frameLayout);
                } else {
                    iComputeTitleCenterLayoutStart = i14;
                }
                this.mCollapseTitleShowable = canCollapseTitleBeShown();
                updateTightTitle();
                measuredWidth2 = frameLayout.getMeasuredWidth() + iComputeTitleCenterLayoutStart;
                if (frameLayout.getMeasuredWidth() + i14 <= marginEnd) {
                    i11 = i14;
                    measuredWidth3 = marginEnd;
                } else if (measuredWidth2 > marginEnd) {
                    measuredWidth4 = marginEnd - frameLayout.getMeasuredWidth();
                    if (measuredWidth4 < i14) {
                        i11 = i14;
                    } else {
                        i11 = measuredWidth4;
                    }
                    measuredWidth3 = marginEnd;
                } else if (iComputeTitleCenterLayoutStart < i14) {
                    measuredWidth3 = i14 + frameLayout.getMeasuredWidth();
                    i11 = i14;
                } else {
                    i11 = iComputeTitleCenterLayoutStart;
                    measuredWidth3 = measuredWidth2;
                }
                int measuredHeight3 = frameLayout.getMeasuredHeight();
                int i18 = ((paddingTop2 - measuredHeight3) / 2) + paddingTop;
                int i19 = measuredHeight3 + i18;
                i6 = paddingTop2;
                iPositionChild = i14;
                ViewUtils.layoutChildView(this, frameLayout, i11, i18, measuredWidth3, i19);
            } else {
                i6 = paddingTop2;
                iPositionChild = i14 + positionChild(frameLayout, i14 + this.mTitleGapPaddingStart, paddingTop, i6);
            }
            if (zIsShowTitle) {
                int measuredHeight4 = frameLayout2.getMeasuredHeight();
                int i110 = paddingTop + ((i6 - measuredHeight4) / 2);
                measuredWidth = frameLayout2.getMeasuredWidth() + i9;
                if (measuredWidth > marginEnd) {
                    i10 = marginEnd;
                } else {
                    i10 = measuredWidth;
                }
                ViewUtils.layoutChildView(this, frameLayout2, i9, i110, i10, i110 + measuredHeight4);
            }
            if (this.mNavigationMode == 1) {
                marginStart = iPositionChild;
            } else {
                if (zIsShowTitle) {
                    iPositionChild += this.mItemPadding;
                }
                marginStart = iPositionChild + positionChild(view2, iPositionChild, paddingTop, i6) + this.mItemPadding;
            }
        } else {
            paddingTop = paddingTop;
            i6 = paddingTop2;
        }
        View view10 = this.mExpandedActionView;
        if (view10 == null && ((this.mDisplayOptions & 16) == 0 || (view10 = this.mCustomNavView) == null)) {
            view10 = null;
        }
        if (view10 != null && view10.getVisibility() != 8) {
            ViewGroup.LayoutParams layoutParams = view10.getLayoutParams();
            ActionBar.LayoutParams layoutParams2 = layoutParams instanceof ActionBar.LayoutParams ? (ActionBar.LayoutParams) layoutParams : null;
            int i20 = layoutParams2 != null ? layoutParams2.gravity : 8388627;
            int measuredWidth5 = view10.getMeasuredWidth();
            if (layoutParams2 != null) {
                marginStart += layoutParams2.getMarginStart();
                marginEnd -= layoutParams2.getMarginEnd();
                i8 = ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin;
                i7 = ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin;
            } else {
                i7 = 0;
                i8 = 0;
            }
            int i21 = 8388615 & i20;
            if (i21 == 1) {
                int width = (getWidth() - measuredWidth5) / 2;
                if (width < marginStart) {
                    i21 = 8388611;
                } else if (width + measuredWidth5 > marginEnd) {
                    i21 = 8388613;
                }
            } else if (i20 == -1 || i21 == 0) {
                i21 = 8388611;
            }
            int paddingStart2 = getPaddingStart();
            int iNormalizeHorizontalGravity = normalizeHorizontalGravity(i21, zIsLayoutRtl);
            if (iNormalizeHorizontalGravity == 1) {
                paddingStart2 = (getWidth() - measuredWidth5) / 2;
            } else if (iNormalizeHorizontalGravity == 8388611) {
                paddingStart2 = marginStart;
            } else if (iNormalizeHorizontalGravity == 8388613) {
                paddingStart2 = marginEnd - measuredWidth5;
            }
            int i22 = i20 & com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle;
            if (i20 == -1 || i22 == 0) {
                i22 = 16;
            }
            if (i22 == 16) {
                paddingBottom = ((((this.mCollapseTotalHeight - i5) - getPaddingBottom()) - getPaddingTop()) - view10.getMeasuredHeight()) / 2;
            } else if (i22 != 48) {
                paddingBottom = i22 != 80 ? 0 : (((this.mCollapseTotalHeight - i5) - getPaddingBottom()) - view10.getMeasuredHeight()) - i7;
            } else {
                paddingBottom = getPaddingTop() + i8;
            }
            int measuredWidth6 = view10.getMeasuredWidth();
            view10.layout(zIsLayoutRtl ? (getWidth() - paddingStart2) - measuredWidth6 : paddingStart2, paddingBottom, zIsLayoutRtl ? getWidth() - paddingStart2 : measuredWidth6 + paddingStart2, view10.getMeasuredHeight() + paddingBottom);
        }
        int i23 = marginStart;
        ProgressBar progressBar2 = this.mProgressView;
        if (progressBar2 != null) {
            progressBar2.bringToFront();
            int measuredHeight5 = this.mProgressView.getMeasuredHeight() / 2;
            ProgressBar progressBar3 = this.mProgressView;
            int i24 = this.mProgressBarPadding;
            progressBar3.layout(i24, -measuredHeight5, progressBar3.getMeasuredWidth() + i24, measuredHeight5);
        }
        if (i5 > 0) {
            int i25 = this.mExtraPadding + this.mUncollapseTabPaddingH;
            ViewUtils.layoutChildView(this, frameLayout3, i25, i4, i25 + frameLayout3.getMeasuredWidth(), i4 + i5);
        }
        View view11 = this.mCenterView;
        if (view11 == null || view11.getVisibility() == 8) {
            return;
        }
        int measuredWidth7 = this.mCenterView.getMeasuredWidth();
        int measuredHeight6 = this.mCenterView.getMeasuredHeight();
        int i26 = (i12 - measuredWidth7) / 2;
        int i27 = i26 + measuredWidth7;
        if (i27 > marginEnd) {
            i26 = marginEnd - measuredWidth7;
        } else {
            marginEnd = i27;
        }
        if (i26 < i23) {
            marginEnd = i23 + measuredWidth7;
        } else {
            i23 = i26;
        }
        int i28 = paddingTop + ((i6 - measuredHeight6) / 2);
        ViewUtils.layoutChildView(this, this.mCenterView, i23, i28, marginEnd, measuredHeight6 + i28);
    }

    public void onLayoutExpandViews(boolean z, int i, int i2, int i3, int i4, int i5, float f) {
        int i6;
        int measuredWidth;
        if (hasTitle()) {
            FrameLayout frameLayout = this.mMovableMainContainer;
            FrameLayout frameLayout2 = this.mMovableTabContainer;
            int measuredHeight = (frameLayout == null || frameLayout.getVisibility() != 0) ? 0 : frameLayout.getMeasuredHeight();
            int i7 = this.mMovableSecondaryTabHeight;
            if (frameLayout != null && frameLayout.getVisibility() == 0 && this.mInnerExpandState != 0) {
                frameLayout.layout(i, i4 - measuredHeight, i3, i4);
                ScrollingTabContainerView scrollingTabContainerView = hasTabsInContainer(this.mMovableMainContainer) ? (ScrollingTabContainerView) this.mMovableMainContainer.getChildAt(0) : null;
                if (scrollingTabContainerView != null) {
                    int measuredWidth2 = this.mUncollapsePaddingH;
                    if (ViewUtils.isLayoutRtl(this)) {
                        measuredWidth2 = (i3 - this.mUncollapsePaddingH) - scrollingTabContainerView.getMeasuredWidth();
                    }
                    scrollingTabContainerView.layout(measuredWidth2, this.mExpandTabTopPadding, scrollingTabContainerView.getMeasuredWidth() + measuredWidth2, scrollingTabContainerView.getMeasuredHeight() + this.mExpandTabTopPadding);
                }
            }
            if (i7 <= 0 || this.mInnerExpandState == 0) {
                return;
            }
            int i8 = i + this.mUncollapseTabPaddingH + this.mExtraPadding;
            int i9 = i4 + i5;
            ViewUtils.layoutChildView(this, frameLayout2, i8, i9 - i7, i8 + frameLayout2.getMeasuredWidth(), i9);
            ScrollingTabContainerView scrollingTabContainerView2 = hasTabsInContainer(frameLayout2) ? (ScrollingTabContainerView) frameLayout2.getChildAt(0) : null;
            if (scrollingTabContainerView2 != null) {
                int measuredWidth3 = scrollingTabContainerView2.getMeasuredWidth();
                if (ViewUtils.isLayoutRtl(this)) {
                    measuredWidth = (i3 - (this.mUncollapseTabPaddingH * 2)) - scrollingTabContainerView2.getMeasuredWidth();
                    i6 = i3 - (this.mUncollapseTabPaddingH * 2);
                } else {
                    i6 = measuredWidth3;
                    measuredWidth = 0;
                }
                scrollingTabContainerView2.layout(measuredWidth, 0, i6, scrollingTabContainerView2.getMeasuredHeight());
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:165:0x031f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:166:0x0321  */
    /* JADX WARN: Code duplicated, block: B:167:0x0326 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:168:0x0328  */
    /* JADX WARN: Code duplicated, block: B:169:0x032d  */
    /* JADX WARN: Code duplicated, block: B:173:0x0336  */
    /* JADX WARN: Code duplicated, block: B:174:0x0339  */
    /* JADX WARN: Code duplicated, block: B:176:0x033d  */
    /* JADX WARN: Code duplicated, block: B:177:0x0346  */
    /* JADX WARN: Code duplicated, block: B:180:0x0353  */
    /* JADX WARN: Code duplicated, block: B:181:0x0356  */
    /* JADX WARN: Code duplicated, block: B:205:0x03e2  */
    /* JADX WARN: Code duplicated, block: B:212:0x03f6 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:213:0x03f8  */
    /* JADX WARN: Code duplicated, block: B:215:0x03fe  */
    /* JADX WARN: Code duplicated, block: B:218:0x0419 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:221:0x042d  */
    /* JADX WARN: Code duplicated, block: B:222:0x0434  */
    /* JADX WARN: Code duplicated, block: B:227:0x044f  */
    /* JADX WARN: Code duplicated, block: B:233:0x0470  */
    /* JADX WARN: Code duplicated, block: B:237:0x0483 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:238:0x0485  */
    /* JADX WARN: Code duplicated, block: B:239:0x0487  */
    /* JADX WARN: Code duplicated, block: B:241:0x049c  */
    /* JADX WARN: Code duplicated, block: B:247:0x04ce  */
    /* JADX WARN: Code duplicated, block: B:250:0x04e9  */
    /* JADX WARN: Code duplicated, block: B:251:0x0501  */
    /* JADX WARN: Code duplicated, block: B:254:0x0508  */
    /* JADX WARN: Code duplicated, block: B:255:0x051f  */
    /* JADX WARN: Code duplicated, block: B:265:0x0532  */
    /* JADX WARN: Code duplicated, block: B:268:0x0547  */
    /* JADX WARN: Code duplicated, block: B:269:0x054e  */
    /* JADX WARN: Code duplicated, block: B:272:0x0569  */
    /* JADX WARN: Code duplicated, block: B:274:0x0571  */
    /* JADX WARN: Code duplicated, block: B:276:0x0575  */
    /* JADX WARN: Code duplicated, block: B:277:0x0584  */
    /* JADX WARN: Code duplicated, block: B:279:0x0588  */
    /* JADX WARN: Code duplicated, block: B:280:0x058c  */
    /* JADX WARN: Code duplicated, block: B:282:0x058f  */
    /* JADX WARN: Code duplicated, block: B:283:0x0595  */
    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int iMax;
        int i3;
        int iMax2;
        int i4;
        int iMax3;
        int i5;
        int i6;
        boolean z;
        int iMax4;
        boolean z2;
        int i7;
        int i8;
        boolean zShouldMeasureCollapseTabContainer;
        boolean zShouldMeasureMovableTabContainer;
        ExtraPaddingPolicy extraPaddingPolicy;
        int i9;
        int iMax5;
        int i10;
        int i11;
        int measuredHeight;
        int i12;
        int measuredHeight2;
        int iMax6;
        int i13;
        ProgressBar progressBar;
        int i14;
        int i15;
        int iMeasureChildView;
        int i16;
        int measuredWidth;
        int i17;
        int measuredWidth2;
        int i18;
        int i19;
        int i20;
        int iMin;
        int iMax7;
        int i21;
        int i22;
        int iMin2;
        int iMax8;
        int i23;
        View view;
        View view2;
        int i24;
        ActionMenuView actionMenuView;
        int childCount = getChildCount();
        int i25 = 0;
        for (int i26 = 0; i26 < childCount; i26++) {
            View childAt = getChildAt(i26);
            if (childAt.getVisibility() != 8 && (childAt != (actionMenuView = this.mMenuView) || actionMenuView.getChildCount() != 0)) {
                i25++;
            }
        }
        if (i25 == 0) {
            setMeasuredDimension(0, 0);
            this.mIsCollapsed = true;
            return;
        }
        this.mIsCollapsed = false;
        int size = View.MeasureSpec.getSize(i);
        int i27 = this.mTitleMaxHeight;
        int i28 = this.mTitleMinHeight;
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int paddingStart = getPaddingStart();
        int paddingEnd = getPaddingEnd();
        int size2 = (i27 > 0 ? i27 : View.MeasureSpec.getSize(i2)) - paddingTop;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size2, Integer.MIN_VALUE);
        int measuredWidth3 = (size - paddingStart) - paddingEnd;
        int iMax9 = measuredWidth3 / 2;
        boolean zHasTitle = hasTitle();
        View view3 = this.mStartView;
        if (view3 == null || view3.getVisibility() == 8) {
            iMax = 0;
        } else {
            measuredWidth3 = measureChildView(this.mStartView, measuredWidth3, iMakeMeasureSpec, 0);
            paddingStart += this.mStartView.getMeasuredWidth();
            iMax = this.mStartView.getMeasuredHeight() + paddingTop;
        }
        View view4 = this.mNavigatorSwitch;
        if (view4 != null) {
            ((NavigatorSwitchPresenter) view4.getTag(miuix.appcompat.R.id.miuix_appcompat_navigator_switch_presenter)).suppressVisibility(false, 0);
            if (this.mNavigatorSwitch.getVisibility() != 8) {
                measuredWidth3 = measureChildView(this.mNavigatorSwitch, measuredWidth3, iMakeMeasureSpec, 0);
                paddingStart += this.mNavigatorSwitch.getMeasuredWidth();
                iMax = Math.max(iMax, this.mNavigatorSwitch.getMeasuredHeight() + paddingTop);
            }
        }
        View view5 = this.mStartView;
        if (view5 != null && view5.getVisibility() != 0) {
            this.mHasStartView = false;
        }
        View view6 = this.mNavigatorSwitch;
        if (view6 != null && view6.getVisibility() != 0) {
            this.mHasNavigatorSwitchView = false;
        }
        HomeView homeView = this.mExpandedActionView != null ? this.mExpandedHomeLayout : this.mHomeLayout;
        if (this.mStartView == null || homeView == null) {
            i3 = 8;
        } else {
            i3 = 8;
            homeView.setVisibility(8);
        }
        if (homeView == null || homeView.getVisibility() == i3) {
            iMax2 = iMax9;
        } else {
            int i29 = homeView.getLayoutParams().width;
            homeView.measure(i29 < 0 ? View.MeasureSpec.makeMeasureSpec(measuredWidth3, Integer.MIN_VALUE) : View.MeasureSpec.makeMeasureSpec(i29, 1073741824), iMakeMeasureSpec);
            int measuredWidth4 = homeView.getMeasuredWidth() + homeView.getStartOffset();
            measuredWidth3 = Math.max(0, measuredWidth3 - measuredWidth4);
            iMax2 = Math.max(0, measuredWidth3 - measuredWidth4);
            paddingStart += measuredWidth4;
            iMax = Math.max(iMax, homeView.getMeasuredHeight() + paddingTop);
        }
        ActionMenuView actionMenuView2 = this.mEndMenuView;
        if (actionMenuView2 != null && actionMenuView2.getParent() == this && this.mEndMenuView.getVisibility() != 8) {
            measuredWidth3 = measureChildView(this.mEndMenuView, measuredWidth3, iMakeMeasureSpec, 0);
            iMax9 = Math.max(0, iMax9 - this.mEndMenuView.getMeasuredWidth());
            paddingEnd += this.mEndMenuView.getMeasuredWidth();
            iMax = Math.max(iMax, this.mEndMenuView.getMeasuredHeight() + paddingTop);
        }
        View view7 = this.mEndView;
        if (view7 != null && view7.getVisibility() != 8) {
            measuredWidth3 = measureChildView(this.mEndView, measuredWidth3, iMakeMeasureSpec, 0);
            paddingEnd += this.mEndView.getMeasuredWidth();
            iMax = Math.max(iMax, this.mEndView.getMeasuredHeight() + paddingTop);
        }
        View view8 = this.mSearchView;
        if (view8 != null) {
            i4 = 2;
            if (view8.getVisibility() != 8) {
                int iMin3 = measuredWidth3 - (this.mSearchViewMargin * 2);
                ViewGroup.LayoutParams layoutParams = this.mSearchView.getLayoutParams();
                if (layoutParams != null && (i24 = layoutParams.width) > 0) {
                    iMin3 = Math.min(i24, iMin3);
                }
                this.mSearchView.measure(View.MeasureSpec.makeMeasureSpec(iMin3, 1073741824), iMakeMeasureSpec);
                int measuredWidth5 = this.mSearchView.getMeasuredWidth() + (this.mSearchViewMargin * 2);
                measuredWidth3 = Math.max(0, measuredWidth3 - measuredWidth5);
                iMax9 = Math.max(0, iMax9 - measuredWidth5);
                paddingEnd += measuredWidth5;
                iMax = Math.max(iMax, this.mSearchView.getMeasuredHeight() + paddingTop);
            }
        } else {
            i4 = 2;
        }
        ProgressBar progressBar2 = this.mIndeterminateProgressView;
        if (progressBar2 != null && progressBar2.getVisibility() != 8) {
            measuredWidth3 = measureChildView(this.mIndeterminateProgressView, measuredWidth3, iMakeMeasureSpec, this.mProgressBarPadding * 2);
            iMax9 = Math.max(0, (iMax9 - this.mIndeterminateProgressView.getMeasuredWidth()) - (this.mProgressBarPadding * 2));
            paddingEnd += this.mIndeterminateProgressView.getMeasuredWidth();
            iMax = Math.max(iMax, this.mIndeterminateProgressView.getMeasuredHeight() + paddingTop);
        }
        boolean zIsShowTitle = isShowTitle();
        if (this.mExpandedActionView == null && ((zIsShowTitle || (getDisplayOptions() & 32) != 0) && (view2 = this.mTitleUpView) != null && view2.getVisibility() == 0)) {
            measuredWidth3 = measureChildView(this.mTitleUpView, measuredWidth3, iMakeMeasureSpec, (this.mHasStartView || this.mHasNavigatorSwitchView) ? this.mTitleUpViewMarginStart + this.mTitleUpViewMarginEnd : 0);
            paddingStart += this.mTitleUpView.getMeasuredWidth() + ((this.mHasStartView || this.mHasNavigatorSwitchView) ? this.mTitleUpViewMarginEnd + this.mTitleUpViewMarginStart : 0);
            iMax = Math.max(iMax, this.mTitleUpView.getMeasuredHeight() + paddingTop);
        }
        View view9 = this.mCenterView;
        if (view9 == null || view9.getVisibility() == 8) {
            iMax3 = i28;
        } else {
            measureChildView(this.mCenterView, measuredWidth3, iMakeMeasureSpec, 0);
            int measuredHeight3 = this.mCenterView.getMeasuredHeight() + paddingTop;
            Math.max(iMax, measuredHeight3);
            iMax3 = Math.max(i28, measuredHeight3);
        }
        if (zIsShowTitle != 0) {
            updateTitleCenter();
        }
        View view10 = this.mExpandedActionView;
        if (view10 == null && ((this.mDisplayOptions & 16) == 0 || (view10 = this.mCustomNavView) == null)) {
            view10 = null;
        }
        if ((this.mStartView == null && this.mEndView == null) || view10 == null) {
            i5 = 8;
        } else {
            i5 = 8;
            view10.setVisibility(8);
        }
        if (view10 == null || view10.getVisibility() == i5) {
            i6 = size;
            i28 = i28;
            z = zHasTitle;
            ScrollingTabContainerView scrollingTabContainerView = this.mCollapseTabs;
            if (scrollingTabContainerView == null) {
                if (view10 != null && view10.getVisibility() == 8 && zIsShowTitle == 0) {
                    iMax4 = iMax3;
                    z2 = true;
                } else {
                    iMax4 = iMax3;
                }
                if (this.mExpandedActionView == null) {
                    if ((!zIsShowTitle || this.mInnerExpandState == 3) && this.mMainContainer.getChildCount() > 0) {
                        if (isTitleCenter()) {
                            if (paddingStart > paddingEnd) {
                                i16 = i6 - (paddingStart * 2);
                            } else {
                                i16 = measuredWidth3;
                            }
                            int iMax10 = Math.max(0, i16);
                            this.mMainContainer.measure(View.MeasureSpec.makeMeasureSpec(iMax10, Integer.MIN_VALUE), iMakeMeasureSpec);
                            measuredWidth = this.mMainContainer.getMeasuredWidth();
                            i17 = this.mTitleMinWidth;
                            if (measuredWidth < i17 || iMax10 >= i17) {
                                measuredWidth2 = this.mMainContainer.getMeasuredWidth();
                            } else {
                                this.mMainContainer.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth3, Integer.MIN_VALUE), iMakeMeasureSpec);
                                measuredWidth2 = this.mMainContainer.getMeasuredWidth();
                            }
                            iMeasureChildView = measuredWidth3 - measuredWidth2;
                        } else {
                            iMeasureChildView = measureChildView(this.mMainContainer, measuredWidth3, iMakeMeasureSpec, 0);
                        }
                        iMax3 = Math.max(iMax3, this.mMainContainer.getMeasuredHeight());
                    } else {
                        iMeasureChildView = measuredWidth3;
                    }
                    if (this.mMediumContainer.getChildCount() != 0) {
                        if (isTitleCenter() && paddingStart > paddingEnd) {
                            measuredWidth3 = i6 - (paddingStart * 2);
                        }
                        this.mMediumContainer.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, measuredWidth3), Integer.MIN_VALUE), iMakeMeasureSpec);
                        if (this.mInnerExpandState == 1) {
                            iMax3 = Math.max(iMax3, this.mMediumContainer.getMeasuredHeight());
                        }
                    }
                    measuredWidth3 = iMeasureChildView;
                }
                if (this.mMovableMainContainer.getChildCount() != 0) {
                    if (z) {
                        i15 = 0;
                    } else {
                        i15 = 1073741824;
                    }
                    i7 = i6;
                    i8 = 0;
                    this.mMovableMainContainer.measure(View.MeasureSpec.makeMeasureSpec(i7, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, i15));
                } else {
                    i7 = i6;
                    i8 = 0;
                }
                zShouldMeasureCollapseTabContainer = shouldMeasureCollapseTabContainer();
                zShouldMeasureMovableTabContainer = shouldMeasureMovableTabContainer();
                this.mMovableSecondaryTabHeight = i8;
                float f = getResources().getConfiguration().densityDpi / 160.0f;
                extraPaddingPolicy = this.mExtraPaddingPolicy;
                if (extraPaddingPolicy == null && extraPaddingPolicy.isEnable()) {
                    this.mExtraPadding = (int) (this.mExtraPaddingPolicy.getExtraPaddingDp() * f);
                    i9 = 0;
                } else {
                    i9 = 0;
                    this.mExtraPadding = 0;
                }
                iMax5 = Math.max(i9, i7 - ((this.mUncollapseTabPaddingH + this.mExtraPadding) * 2));
                i10 = i7 - ((this.mUncollapseTabPaddingH + this.mExtraPadding) * 2);
                if (zShouldMeasureMovableTabContainer) {
                    i11 = 1073741824;
                    this.mMovableTabContainer.measure(View.MeasureSpec.makeMeasureSpec(iMax5, 1073741824), View.MeasureSpec.makeMeasureSpec(i9, i9));
                    measuredHeight = this.mMovableTabContainer.getMeasuredHeight();
                    this.mMovableSecondaryTabHeight = measuredHeight;
                } else {
                    i11 = 1073741824;
                    measuredHeight = i9;
                }
                this.mCollapseSecondaryTabHeight = i9;
                if (zShouldMeasureCollapseTabContainer) {
                    this.mCollapseTabContainer.measure(View.MeasureSpec.makeMeasureSpec(i10, i11), View.MeasureSpec.makeMeasureSpec(i9, i9));
                    int measuredHeight4 = this.mCollapseTabContainer.getMeasuredHeight();
                    this.mCollapseSecondaryTabHeight = measuredHeight4;
                    i12 = measuredHeight4;
                } else {
                    i12 = 0;
                }
                if (this.mExpandedActionView == null && this.mNavigationMode == 1 && this.mListNavLayout != null) {
                    i14 = this.mItemPadding;
                    if (zIsShowTitle != 0) {
                        i14 *= 2;
                    }
                    this.mListNavLayout.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, measuredWidth3 - i14), Integer.MIN_VALUE), iMakeMeasureSpec);
                }
                if (z) {
                    measuredHeight2 = this.mMovableMainContainer.getMeasuredHeight();
                } else {
                    measuredHeight2 = 0;
                }
                int iMax11 = Math.max(iMax4, iMax3 + i12);
                this.mCollapseTotalHeight = iMax11;
                this.mCollapseTotalHeight = Math.max(i28, iMax11);
                iMax6 = Math.max(iMax4, iMax3) + measuredHeight2 + measuredHeight;
                this.mExpandTotalHeight = iMax6;
                if (z2) {
                    setMeasuredDimension(i7, 0);
                    this.mIsCollapsed = true;
                    return;
                }
                i13 = this.mInnerExpandState;
                if (i13 == 3) {
                    int i30 = this.mCollapseTotalHeight;
                    setMeasuredDimension(i7, Math.max((i30 - i12) + this.mPendingHeight, i30));
                } else if (i13 == i4) {
                    setMeasuredDimension(i7, iMax6);
                } else if (i13 == 1) {
                    setMeasuredDimension(i7, this.mCollapseTotalHeight);
                } else {
                    setMeasuredDimension(i7, this.mCollapseTotalHeight);
                }
                progressBar = this.mProgressView;
                if (progressBar != null || progressBar.getVisibility() == 8) {
                }
                this.mProgressView.measure(View.MeasureSpec.makeMeasureSpec(i7 - (this.mProgressBarPadding * 2), 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), Integer.MIN_VALUE));
                return;
            }
            measureChildView(scrollingTabContainerView, measuredWidth3, iMakeMeasureSpec, 0);
            iMax4 = Math.max(iMax3, this.mCollapseTabs.getMeasuredHeight());
        } else {
            View view11 = this.mTitleUpView;
            int measuredWidth6 = (view11 == null || view11.getVisibility() != 4) ? 0 : this.mTitleUpView.getMeasuredWidth();
            ViewGroup.LayoutParams layoutParamsGenerateLayoutParams = generateLayoutParams(view10.getLayoutParams());
            int i31 = measuredWidth6;
            ActionBar.LayoutParams layoutParams2 = layoutParamsGenerateLayoutParams instanceof ActionBar.LayoutParams ? (ActionBar.LayoutParams) layoutParamsGenerateLayoutParams : null;
            if (layoutParams2 != null) {
                i19 = ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin;
                i18 = ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin;
            } else {
                i18 = 0;
                i19 = 0;
            }
            if (i27 <= 0) {
                i20 = i18;
            } else {
                i20 = i18;
                int i32 = layoutParamsGenerateLayoutParams.height >= 0 ? 1073741824 : Integer.MIN_VALUE;
                z = zHasTitle;
                iMin = layoutParamsGenerateLayoutParams.height;
                i6 = size;
                if (iMin >= 0) {
                    if (iMin == -1) {
                        iMax7 = Math.max(i28, i27);
                    } else {
                        iMax7 = size2;
                    }
                    iMin = iMax7 - i20;
                } else if (i27 > 0) {
                    iMin = Math.min(iMin, i27);
                }
                i21 = layoutParamsGenerateLayoutParams.width;
                if (i21 != -2) {
                    i22 = 1073741824;
                } else {
                    i22 = Integer.MIN_VALUE;
                }
                if (i21 >= 0) {
                    iMin2 = Math.min(i21, measuredWidth3 + i31);
                } else {
                    iMin2 = measuredWidth3 + i31;
                }
                iMax8 = Math.max(0, iMin2 - i19);
                if (layoutParams2 != null) {
                    i23 = layoutParams2.gravity;
                } else {
                    i23 = 8388627;
                }
                if ((i23 & 8388615) == 1 && layoutParamsGenerateLayoutParams.width == -1) {
                    iMax8 = Math.min(iMax2, iMax9) * 2;
                }
                view = this.mNavigatorSwitch;
                if (view != null && view.getVisibility() == 0) {
                    iMax8 = (int) (iMax8 + ((this.mNavigatorSwitch.getMeasuredWidth() * (1.0f - this.mNavigatorSwitch.getAlpha())) - (this.mTitleUpViewMarginStart * this.mNavigatorSwitch.getAlpha())));
                }
                view10.measure(View.MeasureSpec.makeMeasureSpec(iMax8, i22), View.MeasureSpec.makeMeasureSpec(iMin, i32));
                measuredWidth3 -= (i19 + view10.getMeasuredWidth()) - i31;
                iMax4 = Math.max(iMax3, view10.getMeasuredHeight());
            }
            z = zHasTitle;
            iMin = layoutParamsGenerateLayoutParams.height;
            i6 = size;
            if (iMin >= 0) {
                if (iMin == -1) {
                    iMax7 = Math.max(i28, i27);
                } else {
                    iMax7 = size2;
                }
                iMin = iMax7 - i20;
            } else if (i27 > 0) {
                iMin = Math.min(iMin, i27);
            }
            i21 = layoutParamsGenerateLayoutParams.width;
            if (i21 != -2) {
                i22 = 1073741824;
            } else {
                i22 = Integer.MIN_VALUE;
            }
            if (i21 >= 0) {
                iMin2 = Math.min(i21, measuredWidth3 + i31);
            } else {
                iMin2 = measuredWidth3 + i31;
            }
            iMax8 = Math.max(0, iMin2 - i19);
            if (layoutParams2 != null) {
                i23 = layoutParams2.gravity;
            } else {
                i23 = 8388627;
            }
            if ((i23 & 8388615) == 1) {
                iMax8 = Math.min(iMax2, iMax9) * 2;
            }
            view = this.mNavigatorSwitch;
            if (view != null) {
                iMax8 = (int) (iMax8 + ((this.mNavigatorSwitch.getMeasuredWidth() * (1.0f - this.mNavigatorSwitch.getAlpha())) - (this.mTitleUpViewMarginStart * this.mNavigatorSwitch.getAlpha())));
            }
            view10.measure(View.MeasureSpec.makeMeasureSpec(iMax8, i22), View.MeasureSpec.makeMeasureSpec(iMin, i32));
            measuredWidth3 -= (i19 + view10.getMeasuredWidth()) - i31;
            iMax4 = Math.max(iMax3, view10.getMeasuredHeight());
        }
        z2 = false;
        if (this.mExpandedActionView == null) {
            if (zIsShowTitle) {
                if (isTitleCenter()) {
                    if (paddingStart > paddingEnd) {
                        i16 = i6 - (paddingStart * 2);
                    } else {
                        i16 = measuredWidth3;
                    }
                    int iMax12 = Math.max(0, i16);
                    this.mMainContainer.measure(View.MeasureSpec.makeMeasureSpec(iMax12, Integer.MIN_VALUE), iMakeMeasureSpec);
                    measuredWidth = this.mMainContainer.getMeasuredWidth();
                    i17 = this.mTitleMinWidth;
                    if (measuredWidth < i17) {
                        measuredWidth2 = this.mMainContainer.getMeasuredWidth();
                    } else {
                        measuredWidth2 = this.mMainContainer.getMeasuredWidth();
                    }
                    iMeasureChildView = measuredWidth3 - measuredWidth2;
                } else {
                    iMeasureChildView = measureChildView(this.mMainContainer, measuredWidth3, iMakeMeasureSpec, 0);
                }
                iMax3 = Math.max(iMax3, this.mMainContainer.getMeasuredHeight());
            } else {
                if (isTitleCenter()) {
                    if (paddingStart > paddingEnd) {
                        i16 = i6 - (paddingStart * 2);
                    } else {
                        i16 = measuredWidth3;
                    }
                    int iMax13 = Math.max(0, i16);
                    this.mMainContainer.measure(View.MeasureSpec.makeMeasureSpec(iMax13, Integer.MIN_VALUE), iMakeMeasureSpec);
                    measuredWidth = this.mMainContainer.getMeasuredWidth();
                    i17 = this.mTitleMinWidth;
                    if (measuredWidth < i17) {
                        measuredWidth2 = this.mMainContainer.getMeasuredWidth();
                    } else {
                        measuredWidth2 = this.mMainContainer.getMeasuredWidth();
                    }
                    iMeasureChildView = measuredWidth3 - measuredWidth2;
                } else {
                    iMeasureChildView = measureChildView(this.mMainContainer, measuredWidth3, iMakeMeasureSpec, 0);
                }
                iMax3 = Math.max(iMax3, this.mMainContainer.getMeasuredHeight());
            }
            if (this.mMediumContainer.getChildCount() != 0) {
                if (isTitleCenter()) {
                    measuredWidth3 = i6 - (paddingStart * 2);
                }
                this.mMediumContainer.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, measuredWidth3), Integer.MIN_VALUE), iMakeMeasureSpec);
                if (this.mInnerExpandState == 1) {
                    iMax3 = Math.max(iMax3, this.mMediumContainer.getMeasuredHeight());
                }
            }
            measuredWidth3 = iMeasureChildView;
        }
        if (this.mMovableMainContainer.getChildCount() != 0) {
            if (z) {
                i15 = 0;
            } else {
                i15 = 1073741824;
            }
            i7 = i6;
            i8 = 0;
            this.mMovableMainContainer.measure(View.MeasureSpec.makeMeasureSpec(i7, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, i15));
        } else {
            i7 = i6;
            i8 = 0;
        }
        zShouldMeasureCollapseTabContainer = shouldMeasureCollapseTabContainer();
        zShouldMeasureMovableTabContainer = shouldMeasureMovableTabContainer();
        this.mMovableSecondaryTabHeight = i8;
        float f2 = getResources().getConfiguration().densityDpi / 160.0f;
        extraPaddingPolicy = this.mExtraPaddingPolicy;
        if (extraPaddingPolicy == null) {
            i9 = 0;
            this.mExtraPadding = 0;
        } else {
            i9 = 0;
            this.mExtraPadding = 0;
        }
        iMax5 = Math.max(i9, i7 - ((this.mUncollapseTabPaddingH + this.mExtraPadding) * 2));
        i10 = i7 - ((this.mUncollapseTabPaddingH + this.mExtraPadding) * 2);
        if (zShouldMeasureMovableTabContainer) {
            i11 = 1073741824;
            this.mMovableTabContainer.measure(View.MeasureSpec.makeMeasureSpec(iMax5, 1073741824), View.MeasureSpec.makeMeasureSpec(i9, i9));
            measuredHeight = this.mMovableTabContainer.getMeasuredHeight();
            this.mMovableSecondaryTabHeight = measuredHeight;
        } else {
            i11 = 1073741824;
            measuredHeight = i9;
        }
        this.mCollapseSecondaryTabHeight = i9;
        if (zShouldMeasureCollapseTabContainer) {
            this.mCollapseTabContainer.measure(View.MeasureSpec.makeMeasureSpec(i10, i11), View.MeasureSpec.makeMeasureSpec(i9, i9));
            int measuredHeight5 = this.mCollapseTabContainer.getMeasuredHeight();
            this.mCollapseSecondaryTabHeight = measuredHeight5;
            i12 = measuredHeight5;
        } else {
            i12 = 0;
        }
        if (this.mExpandedActionView == null) {
            i14 = this.mItemPadding;
            if (zIsShowTitle != 0) {
                i14 *= 2;
            }
            this.mListNavLayout.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, measuredWidth3 - i14), Integer.MIN_VALUE), iMakeMeasureSpec);
        }
        if (z) {
            measuredHeight2 = this.mMovableMainContainer.getMeasuredHeight();
        } else {
            measuredHeight2 = 0;
        }
        int iMax14 = Math.max(iMax4, iMax3 + i12);
        this.mCollapseTotalHeight = iMax14;
        this.mCollapseTotalHeight = Math.max(i28, iMax14);
        iMax6 = Math.max(iMax4, iMax3) + measuredHeight2 + measuredHeight;
        this.mExpandTotalHeight = iMax6;
        if (z2) {
            setMeasuredDimension(i7, 0);
            this.mIsCollapsed = true;
            return;
        }
        i13 = this.mInnerExpandState;
        if (i13 == 3) {
            int i33 = this.mCollapseTotalHeight;
            setMeasuredDimension(i7, Math.max((i33 - i12) + this.mPendingHeight, i33));
        } else if (i13 == i4) {
            setMeasuredDimension(i7, iMax6);
        } else if (i13 == 1) {
            setMeasuredDimension(i7, this.mCollapseTotalHeight);
        } else {
            setMeasuredDimension(i7, this.mCollapseTotalHeight);
        }
        progressBar = this.mProgressView;
        if (progressBar != null) {
        }
    }

    public void onNestedPreScroll(View view, int i, int i2, int[] iArr, int i3, int[] iArr2) {
        int i4;
        if (canConsumeScroll()) {
            if (isUserSetExpandState() && this.mUserExpandState == 1) {
                return;
            }
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
        if (canConsumeScroll()) {
            int measuredHeight = this.mMovableMainContainer.getMeasuredHeight() + this.mMovableSecondaryTabHeight;
            if (!hasTitle() && (this.mDisplayOptions & 16) != 0 && this.mCustomNavView != null) {
                measuredHeight = 0;
            }
            int i6 = (this.mCollapseTotalHeight - this.mCollapseSecondaryTabHeight) + measuredHeight;
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
        if (canConsumeScroll()) {
            if (isUserSetExpandState() && this.mUserExpandState == 1) {
                return;
            }
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
        MenuBuilder menuBuilder;
        MenuItem menuItemFindItem;
        if (!(parcelable instanceof SavedState)) {
            Log.w("ActionBarView", "Wrong state class, expecting SavedState! This usually happens when two views of different type have the same id in the same hierarchy.");
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        int i = savedState.expandedMenuItemId;
        if (i != 0 && this.mExpandedMenuPresenter != null && (menuBuilder = this.mOptionsMenu) != null && (menuItemFindItem = menuBuilder.findItem(i)) != null) {
            menuItemFindItem.expandActionView();
        }
        if (savedState.isOverflowOpen) {
            postShowOverflowMenu();
        }
        if (savedState.isEndOverflowOpen) {
            postShowEndOverflowMenu();
        }
        if (this.mUserExpandState == -1) {
            this.mUserSetExpandState = savedState.userSetExpandState;
            this.mUserExpandState = savedState.userExpandState;
            setExpandState(isUserSetExpandState() ? this.mUserExpandState : savedState.expandState, false, false);
        }
        if (savedState.applyBlur) {
            setApplyBgBlur(this.mApplyBgBlur);
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        MenuItemImpl menuItemImpl;
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        if (expandedActionViewMenuPresenter == null || (menuItemImpl = expandedActionViewMenuPresenter.mCurrentExpandedItem) == null) {
            savedState.expandedMenuItemId = 0;
        } else {
            savedState.expandedMenuItemId = menuItemImpl.getItemId();
        }
        savedState.isOverflowOpen = isOverflowMenuShowing();
        savedState.isEndOverflowOpen = isEndOverflowMenuShowing();
        int i = this.mInnerExpandState;
        if (i == 3) {
            savedState.expandState = 0;
        } else {
            savedState.expandState = i;
        }
        savedState.userSetExpandState = this.mUserSetExpandState;
        savedState.userExpandState = this.mUserExpandState;
        savedState.applyBlur = this.mApplyBgBlur;
        return savedState;
    }

    @Override // miuix.view.ActionModeAnimationListener
    public void onStart(boolean z) {
        this.mInActionModeAnimating = true;
        if (this.mActionBarHideState == null) {
            AnimState animState = new AnimState();
            this.mActionBarHideState = animState;
            animState.add(ViewProperty.SCALE_X, 0.8999999761581421d);
            this.mActionBarHideState.add(ViewProperty.SCALE_Y, 0.8999999761581421d);
            this.mActionBarHideState.add(ViewProperty.ALPHA, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
            if (isSelfBlurEnabled()) {
                this.mActionBarHideState.add(SELF_BLUR, 50.0d);
            }
        }
        if (this.mActionBarShowState == null) {
            AnimState animState2 = new AnimState();
            this.mActionBarShowState = animState2;
            animState2.add(ViewProperty.SCALE_X, 1.0d);
            this.mActionBarShowState.add(ViewProperty.SCALE_Y, 1.0d);
            this.mActionBarShowState.add(ViewProperty.ALPHA, 1.0d);
            if (isSelfBlurEnabled()) {
                this.mActionBarShowState.add(SELF_BLUR, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
            }
        }
        if (z) {
            this.mDoContainerShowAnimInFinishActionMode = false;
            Folme.use((View) this).setTo(this.mActionBarShowState).to(this.mActionBarHideState, this.enterAnimConfig);
            return;
        }
        if (getExpandState() == 0) {
            this.mCollapseController.setVisibility(0);
            this.mCollapseController.setAlpha(0.0f);
            this.mMovableController.setVisibility(8);
        } else if (getExpandState() == 1) {
            if (hasTabsInContainer(this.mMainContainer)) {
                this.mMainContainer.setVisibility(0);
                this.mMainContainer.setAlpha(0.0f);
                this.mMediumController.setVisibility(8);
            } else {
                this.mMainContainer.setVisibility(8);
                this.mMainContainer.clearFocus();
                this.mMediumController.setVisibility(0);
                this.mMediumController.setAlpha(0.0f);
            }
            this.mMovableController.setVisibility(8);
            ensureCollapseTabContainerVisibleForMedium();
        } else if (getExpandState() == 2) {
            this.mCollapseController.setVisibility(4);
            this.mMovableController.setVisibility(0);
            this.mMovableController.setAlpha(0.0f);
        }
        Folme.use((View) this).setTo(this.mActionBarHideState).to(this.mActionBarShowState, this.exitAnimConfig);
    }

    public boolean onStartNestedScroll(View view, View view2, int i, int i2) {
        return this.mExpandedActionView == null || this.mCustomNavView != null;
    }

    @Override // miuix.view.ActionModeAnimationListener
    public void onStop(boolean z) {
        this.mInActionModeAnimating = false;
        if (z) {
            this.mCollapseController.setVisibility(4);
            this.mMovableController.setVisibility(4);
        } else {
            if (!this.mDoContainerShowAnimInFinishActionMode) {
                showContainerInFinishActionMode();
            }
            this.mDoContainerShowAnimInFinishActionMode = false;
        }
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0014  */
    public void onStopNestedScroll(View view, int i) {
        boolean z = true;
        if (this.mTouchScrolling) {
            this.mTouchScrolling = false;
            if (this.mNonTouchScrolling) {
                z = false;
            }
        } else if (this.mNonTouchScrolling) {
            this.mNonTouchScrolling = false;
        } else {
            z = false;
        }
        if (canConsumeScroll() && z) {
            snapToFinalExpandState();
        }
    }

    @Override // miuix.view.ActionModeAnimationListener
    public void onUpdate(boolean z, float f) {
        if (this.mDoContainerShowAnimInFinishActionMode || z || f <= 0.8f) {
            return;
        }
        this.mDoContainerShowAnimInFinishActionMode = true;
        showContainerInFinishActionMode();
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z && !this.mInitialA11yFocusRequested && this.mRequestInitialA11yFocusEnabled) {
            this.mInitialA11yFocusRequested = true;
            requestInitialAccessibilityFocusOnUp();
        }
    }

    public final void pendingCreateAllTitles(boolean z) {
        if (z) {
            if (this.mExpandTitle == null) {
                createExpandTitle(true);
                updateExpandTitle();
            }
            if (this.mCollapseTitle == null) {
                createCollapseTitle(true);
            }
            updateCollapseTitle();
        }
    }

    public final void pendingCreateMediumTitle(boolean z) {
        if (z && this.mMediumTitle == null) {
            createMediumTitle(true);
            updateMediumTitle();
        }
    }

    public final void pendingCreateTitle() {
        if (this.mPendingCreated) {
            return;
        }
        boolean z = true;
        this.mPendingCreated = true;
        boolean z2 = (this.mDisplayOptions & 8) != 0;
        boolean z3 = z2 || (isUserSetExpandState() && this.mUserExpandState == 1);
        if (!z2 && (!isResizable() || !z3)) {
            z = false;
        }
        pendingCreateAllTitles(z);
        pendingCreateMediumTitle(z3);
        updateCollapseTitleTouchDelegate();
    }

    public final void postRefreshTitleControllerStatus() {
        post(new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarView$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                ActionBarView.$r8$lambda$04kpj5IDuE0qXTIj9b55WOW81uI(this.f$0);
            }
        });
    }

    public void postShowEndOverflowMenu() {
        post(new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarView$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.showEndOverflowMenu();
            }
        });
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void postShowOverflowMenu() {
        super.postShowOverflowMenu();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public void refreshBottomMenu() {
        if (!this.mSplitActionBarEnable || this.mActionMenuPresenter == null) {
            return;
        }
        addSplitMenuView();
    }

    public void refreshFloatingState() {
        if (this.mFloatingMode != null) {
            applyFloatingStateToButtons();
        }
    }

    public void removeBottomMenuCustomView() {
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.removeBottomMenuCustomView();
        }
    }

    public final void removeMenuViewFromOldParent(View view) {
        ViewGroup viewGroup;
        if (view == null || (viewGroup = (ViewGroup) view.getParent()) == null) {
            return;
        }
        viewGroup.removeView(view);
    }

    public final void removeTabsFromContainer() {
        FrameLayout frameLayout = this.mCollapseTabContainer;
        if (frameLayout != null) {
            if (frameLayout.getParent() != null) {
                removeView(this.mCollapseTabContainer);
                this.mCollapseController.detachView(this.mCollapseTabContainer);
            }
            this.mCollapseTabContainer.removeAllViews();
            this.mCollapseTabContainer = null;
        }
        FrameLayout frameLayout2 = this.mMovableTabContainer;
        if (frameLayout2 != null) {
            if (frameLayout2.getParent() != null) {
                removeView(this.mMovableTabContainer);
                this.mMovableController.detachView(this.mMovableTabContainer);
            }
            this.mMovableTabContainer.removeAllViews();
            this.mMovableTabContainer = null;
        }
        SecondaryTabBar secondaryTabBar = this.mSecondaryCollapseTabs;
        if (secondaryTabBar != null && secondaryTabBar.asViewGroup().getParent() != null) {
            removeView(this.mSecondaryCollapseTabs.asViewGroup());
        }
        SecondaryTabBar secondaryTabBar2 = this.mSecondaryExpandTabs;
        if (secondaryTabBar2 != null && secondaryTabBar2.asViewGroup().getParent() != null) {
            removeView(this.mSecondaryExpandTabs.asViewGroup());
        }
        if (!this.mPostScroller.isFinished()) {
            this.mPostScroller.forceFinished(true);
        }
        removeCallbacks(this.mPostScroll);
        setExpandState(this.mExpandState);
    }

    public final void requestInitialAccessibilityFocusOnUp() {
        View view = this.mTitleUpView;
        if (view != null && view.getVisibility() == 0 && isAccessibilityServiceActive()) {
            this.mTitleUpView.post(new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarView$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    ActionBarView.m1320$r8$lambda$uy8sVMVoKbv133jMlWiGKAoSJo(this.f$0);
                }
            });
        }
    }

    public final void restoreActionBarChildrenAlpha() {
        View view = this.mStartView;
        if (view != null) {
            view.setAlpha(1.0f);
        }
        View view2 = this.mEndView;
        if (view2 != null) {
            view2.setAlpha(1.0f);
        }
        View view3 = this.mCenterView;
        if (view3 != null) {
            view3.setAlpha(1.0f);
        }
        View view4 = this.mTitleUpView;
        if (view4 != null) {
            view4.setAlpha(1.0f);
        }
        View view5 = this.mNavigatorSwitch;
        if (view5 != null) {
            NavigatorSwitchPresenter navigatorSwitchPresenter = (NavigatorSwitchPresenter) view5.getTag(miuix.appcompat.R.id.miuix_appcompat_navigator_switch_presenter);
            if (navigatorSwitchPresenter != null) {
                navigatorSwitchPresenter.suppressAlpha(false, 0.0f);
            } else {
                this.mNavigatorSwitch.setAlpha(1.0f);
            }
        }
    }

    public void restoreCollapseStateIfNeed(boolean z) {
        if (z && getExpandState() == 2 && isResizable() && !isUserSetExpandState()) {
            setExpandState(0);
        }
    }

    public final void restoreContainerVisibilityForState(int i) {
        if (i == 0) {
            this.mCollapseController.setVisibility(0);
            this.mMediumController.setVisibility(8);
            this.mMovableController.setVisibility(8);
            return;
        }
        if (i != 1) {
            if (i == 2) {
                this.mCollapseController.setVisibility(4);
                this.mMediumController.setVisibility(8);
                this.mMovableController.setVisibility(0);
                return;
            }
            return;
        }
        if (hasTabsInContainer(this.mMainContainer)) {
            this.mMainContainer.setVisibility(0);
            this.mMainContainer.setAlpha(1.0f);
            this.mMediumController.setVisibility(8);
        } else {
            this.mMainContainer.setVisibility(8);
            this.mMainContainer.clearFocus();
            this.mMediumController.setVisibility(0);
        }
        this.mMovableController.setVisibility(8);
        ensureCollapseTabContainerVisibleForMedium();
    }

    public final void scheduleBottomMenuAnimation(Runnable runnable) {
        this.mScheduleBottomMenuRunnable = runnable;
    }

    public void setApplyBgBlur(boolean z) {
        if (this.mApplyBgBlur != z) {
            this.mApplyBgBlur = z;
            SecondaryTabBar secondaryTabBar = this.mSecondaryCollapseTabs;
            if (secondaryTabBar != null) {
                secondaryTabBar.setParentBlurEnabled(z);
            }
            SecondaryTabBar secondaryTabBar2 = this.mSecondaryExpandTabs;
            if (secondaryTabBar2 != null) {
                secondaryTabBar2.setParentBlurEnabled(z);
            }
        }
    }

    public void setBottomMenuCustomView(View view) {
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.setBottomMenuCustomView(view);
        }
    }

    public void setBottomMenuCustomViewTranslationYWithPx(int i) {
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView instanceof ResponsiveActionMenuView) {
            ((ResponsiveActionMenuView) actionMenuView).setBottomMenuCustomViewTranslationYWithPx(i);
        }
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setBottomMenuMode(int i) {
        super.setBottomMenuMode(i);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setButtonCenterVerticalEnabled(boolean z) {
        super.setButtonCenterVerticalEnabled(z);
    }

    public void setCallback(ActionBar.OnNavigationListener onNavigationListener) {
        this.mCallback = onNavigationListener;
    }

    public void setCenterView(View view) {
        View view2 = this.mCenterView;
        if (view2 != null) {
            removeView(view2);
        }
        this.mCenterView = view;
        if (view != null) {
            addView(view);
        }
    }

    public void setCollapsable(boolean z) {
    }

    public void setCollapseTitleVisibility(int i) {
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null) {
            collapseTitle.setVisibility(i);
        }
        int i2 = i == 0 ? 0 : 4;
        FrameLayout frameLayout = this.mMainContainer;
        if (frameLayout != null) {
            frameLayout.setImportantForAccessibility(i2);
        }
    }

    public void setCustomNavigationView(View view) {
        boolean z = (this.mDisplayOptions & 16) != 0;
        View view2 = this.mCustomNavView;
        if (view2 != null && z) {
            removeView(view2);
        }
        this.mCustomNavView = view;
        if (view == null || !z) {
            this.mCollapseController.attachViews(this.mMainContainer);
        } else {
            addView(view);
            addCustomView();
        }
    }

    public void setDisplayOptions(int i) {
        View view;
        int i2 = this.mDisplayOptions;
        int i3 = i2 != -1 ? i ^ i2 : -1;
        this.mDisplayOptions = i;
        if ((i3 & 8223) != 0) {
            boolean z = (i & 2) != 0;
            int i4 = 8;
            if (z) {
                initHomeLayout();
                this.mHomeLayout.setVisibility(this.mExpandedActionView == null ? 0 : 8);
                if ((i3 & 4) != 0) {
                    boolean z2 = (i & 4) != 0;
                    this.mHomeLayout.setUp(z2);
                    if (z2) {
                        setHomeButtonEnabled(true);
                    }
                }
                if ((i3 & 1) != 0) {
                    Drawable logo = getLogo();
                    boolean z3 = (logo == null || (i & 1) == 0) ? false : true;
                    HomeView homeView = this.mHomeLayout;
                    if (!z3) {
                        logo = getIcon();
                    }
                    homeView.setIcon(logo);
                }
            } else {
                HomeView homeView2 = this.mHomeLayout;
                if (homeView2 != null) {
                    removeView(homeView2);
                }
            }
            if ((i3 & 8) != 0) {
                if ((i & 8) != 0) {
                    if (getNavigationMode() == 2) {
                        freeMainContainerChildren();
                    }
                    initTitle();
                } else {
                    CollapseTitle collapseTitle = this.mCollapseTitle;
                    if (collapseTitle != null) {
                        this.mMainContainer.removeView(collapseTitle.getLayout());
                    }
                    ExpandTitle expandTitle = this.mExpandTitle;
                    if (expandTitle != null) {
                        this.mMovableMainContainer.removeView(expandTitle.getLayout());
                    }
                    this.mCollapseTitle = null;
                    this.mExpandTitle = null;
                    if ((getDisplayOptions() & 32) == 0) {
                        removeView(this.mTitleUpView);
                        this.mTitleUpView = null;
                    }
                    if (getNavigationMode() == 2) {
                        addTabsToMainContainers();
                    }
                }
            }
            if ((i3 & 6) != 0) {
                boolean z4 = (this.mDisplayOptions & 4) != 0;
                CollapseTitle collapseTitle2 = this.mCollapseTitle;
                boolean z5 = collapseTitle2 != null && collapseTitle2.getVisibility() == 0;
                ExpandTitle expandTitle2 = this.mExpandTitle;
                if (expandTitle2 != null && expandTitle2.getVisibility() == 0) {
                    z5 = true;
                }
                MediumTitle mediumTitle = this.mMediumTitle;
                if (mediumTitle != null && mediumTitle.getVisibility() == 0) {
                    z5 = true;
                }
                if (this.mTitleUpView != null && (z5 || (getDisplayOptions() & 32) != 0)) {
                    View view2 = this.mTitleUpView;
                    if (!z) {
                        i4 = z4 ? 0 : 4;
                    }
                    view2.setVisibility(i4);
                }
            }
            if ((i3 & 16) != 0 && (view = this.mCustomNavView) != null) {
                if ((i & 16) != 0) {
                    safeAddView(this, view);
                    addCustomView();
                } else {
                    removeView(view);
                }
            }
            if ((i3 & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0) {
                if ((i & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0) {
                    View viewInflate = LayoutInflater.from(this.mContext).inflate(this.mNavigatorSwitchResId, (ViewGroup) this, false);
                    this.mNavigatorSwitch = viewInflate;
                    viewInflate.setTag(miuix.appcompat.R.id.miuix_appcompat_navigator_switch_presenter, new NavigatorSwitchPresenter(viewInflate));
                    Folme.useAt(this.mNavigatorSwitch).hover().setFeedbackRadius(60.0f);
                    Folme.useAt(this.mNavigatorSwitch).hover().setEffect(IHoverStyle.HoverEffect.FLOATED_WRAPPED).handleHoverOf(this.mNavigatorSwitch, new AnimConfig[0]);
                    addView(this.mNavigatorSwitch);
                    Boolean bool = this.mFloatingMode;
                    if (bool != null && bool.booleanValue()) {
                        FloatingEffectHelper.updateViewFloatingState(this.mNavigatorSwitch, true, MiuixUIUtils.isLightTheme(getContext()), this.mActionBarButtonMaterial);
                    }
                } else {
                    removeView(this.mNavigatorSwitch);
                    this.mNavigatorSwitch = null;
                }
            }
            requestLayout();
        } else {
            invalidate();
        }
        HomeView homeView3 = this.mHomeLayout;
        if (homeView3 != null) {
            if (!homeView3.isEnabled()) {
                this.mHomeLayout.setContentDescription(null);
            } else if ((i & 4) != 0) {
                this.mHomeLayout.setContentDescription(this.mContext.getResources().getText(miuix.appcompat.R.string.abc_action_bar_up_description));
            } else {
                this.mHomeLayout.setContentDescription(this.mContext.getResources().getText(miuix.appcompat.R.string.abc_action_bar_home_description));
            }
        }
    }

    public void setDropdownAdapter(SpinnerAdapter spinnerAdapter) {
        this.mSpinnerAdapter = spinnerAdapter;
        Spinner spinner = this.mSpinner;
        if (spinner != null) {
            spinner.setAdapter(spinnerAdapter);
        }
    }

    public void setDropdownSelectedPosition(int i) {
        this.mSpinner.setSelection(i);
    }

    public void setEmbeddedTabView(ScrollingTabContainerView scrollingTabContainerView, ScrollingTabContainerView scrollingTabContainerView2, SecondaryTabBar secondaryTabBar, SecondaryTabBar secondaryTabBar2) {
        boolean z = scrollingTabContainerView != null;
        this.mTabsExit = z;
        if (z) {
            setupTabView(scrollingTabContainerView, scrollingTabContainerView2, secondaryTabBar, secondaryTabBar2);
            if (this.mNavigationMode == 2) {
                addTabsContainer();
            }
        }
    }

    public void setEndActionMenuEnable(boolean z) {
        this.mEndActionMenuEnable = z;
    }

    public void setEndActionMenuItemLimit(int i) {
        this.mEndActionMenuItemLimit = i;
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.setItemLimit(i);
        }
    }

    public void setEndView(View view) {
        View view2 = this.mEndView;
        if (view2 != null) {
            removeView(view2);
        }
        this.mEndView = view;
        if (view != null) {
            addView(view);
            Folme.useAt(this.mEndView).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setAlpha(0.6f, new ITouchStyle.TouchType[0]).handleTouchOf(view, new AnimConfig[0]);
            Folme.useAt(this.mEndView).hover().setFeedbackRadius(60.0f);
            Folme.useAt(this.mEndView).hover().setEffect(IHoverStyle.HoverEffect.FLOATED_WRAPPED).handleHoverOf(this.mEndView, new AnimConfig[0]);
        }
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setExpandState(int i) {
        super.setExpandState(i);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public void setExpandState(int i, boolean z, boolean z2) {
        if (!z) {
            pendingCreateTitle();
        }
        if (i == 1 && this.mMediumTitle == null) {
            createMediumTitle(false);
            updateMediumTitle();
        }
        super.setExpandState(i, z, z2);
    }

    public void setExtraPaddingPolicy(ExtraPaddingPolicy extraPaddingPolicy) {
        this.mExtraPaddingPolicy = extraPaddingPolicy;
    }

    public void setForceShowSearchIcon(boolean z) {
        this.mForceShowSearchIcon = z;
    }

    public void setHomeAsUpIndicator(int i) {
        HomeView homeView = this.mHomeLayout;
        if (homeView != null) {
            homeView.setUpIndicator(i);
        } else {
            this.mHomeAsUpIndicatorDrawable = null;
            this.mHomeAsUpIndicatorResId = i;
        }
    }

    public void setHomeAsUpIndicator(Drawable drawable) {
        HomeView homeView = this.mHomeLayout;
        if (homeView != null) {
            homeView.setUpIndicator(drawable);
        } else {
            this.mHomeAsUpIndicatorDrawable = drawable;
            this.mHomeAsUpIndicatorResId = 0;
        }
    }

    public void setHomeButtonEnabled(boolean z) {
        HomeView homeView = this.mHomeLayout;
        if (homeView != null) {
            homeView.setEnabled(z);
            this.mHomeLayout.setFocusable(z);
            if (!z) {
                this.mHomeLayout.setContentDescription(null);
            } else if ((this.mDisplayOptions & 4) != 0) {
                this.mHomeLayout.setContentDescription(this.mContext.getResources().getText(miuix.appcompat.R.string.abc_action_bar_up_description));
            } else {
                this.mHomeLayout.setContentDescription(this.mContext.getResources().getText(miuix.appcompat.R.string.abc_action_bar_home_description));
            }
        }
    }

    public void setHyperActionMenuEnable(boolean z) {
        this.mHyperActionMenuEnable = z;
    }

    public void setHyperMenuSaveStatusByIdEnabled(boolean z) {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        if (actionMenuPresenter instanceof HyperActionMenuPresenter) {
            ((HyperActionMenuPresenter) actionMenuPresenter).setHyperMenuSaveStatusByIdEnabled(z);
        }
    }

    public void setHyperSplitMenuEnabled(boolean z) {
        this.mHyperSplitMenuEnabled = z;
    }

    public void setHyperSplitMenuSaveStatusByIdEnabled(boolean z) {
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        if (actionMenuPresenter instanceof HyperSplitActionMenuPresenter) {
            ((HyperSplitActionMenuPresenter) actionMenuPresenter).setHyperSplitMenuSaveStatusByIdEnabled(z);
        }
    }

    public void setIcon(int i) {
        setIcon(this.mContext.getResources().getDrawable(i));
    }

    public void setIcon(Drawable drawable) {
        HomeView homeView;
        this.mIcon = drawable;
        this.mIconLogoInitIndicator |= 1;
        if (drawable != null && (((this.mDisplayOptions & 1) == 0 || getLogo() == null) && (homeView = this.mHomeLayout) != null)) {
            homeView.setIcon(drawable);
        }
        if (this.mExpandedActionView != null) {
            this.mExpandedHomeLayout.setIcon(this.mIcon.getConstantState().newDrawable(getResources()));
        }
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.mLifecycleOwner = lifecycleOwner;
    }

    public void setLogo(int i) {
        setLogo(this.mContext.getResources().getDrawable(i));
    }

    public void setLogo(Drawable drawable) {
        HomeView homeView;
        this.mLogo = drawable;
        this.mIconLogoInitIndicator |= 2;
        if (drawable == null || (this.mDisplayOptions & 1) == 0 || (homeView = this.mHomeLayout) == null) {
            return;
        }
        homeView.setIcon(drawable);
    }

    public void setMenu(Menu menu, MenuPresenter.Callback callback) {
        MenuBuilder menuBuilder;
        MenuBuilder menuBuilder2 = this.mOptionsMenu;
        if (menuBuilder2 != null) {
            menuBuilder2.removeMenuPresenter(this.mActionMenuPresenter);
            this.mOptionsMenu.removeMenuPresenter(this.mExpandedMenuPresenter);
        }
        MenuBuilder menuBuilder3 = this.mEndMenu;
        if (menuBuilder3 != null) {
            menuBuilder3.removeMenuPresenter(this.mEndActionMenuPresenter);
        }
        removeMenuViewFromOldParent(this.mMenuView);
        removeMenuViewFromOldParent(this.mEndMenuView);
        if (menu == null || !(this.mSplitActionBarEnable || this.mEndActionMenuEnable)) {
            this.mActionMenuPresenter = null;
            this.mEndActionMenuPresenter = null;
            this.mExpandedMenuPresenter = null;
            return;
        }
        if (this.mEndActionMenuEnable && this.mHyperActionMenuEnable) {
            Pair pairDivideHyperMenuAndSplitMenu = divideHyperMenuAndSplitMenu(menu);
            this.mOptionsMenu = (MenuBuilder) pairDivideHyperMenuAndSplitMenu.first;
            this.mEndMenu = (MenuBuilder) pairDivideHyperMenuAndSplitMenu.second;
        } else {
            Pair pairDivideMenuByGroup = divideMenuByGroup(menu);
            this.mOptionsMenu = (MenuBuilder) pairDivideMenuByGroup.first;
            this.mEndMenu = (MenuBuilder) pairDivideMenuByGroup.second;
        }
        if (this.mSplitActionBarEnable) {
            if (this.mActionMenuPresenter == null) {
                this.mActionMenuPresenter = createActionMenuPresenter(callback, this.mHyperSplitMenuEnabled);
                this.mExpandedMenuPresenter = createExpandedActionViewMenuPresenter();
            }
            MenuBuilder menuBuilder4 = this.mOptionsMenu;
            if (menuBuilder4 != null) {
                menuBuilder4.addMenuPresenter(this.mActionMenuPresenter);
                this.mOptionsMenu.addMenuPresenter(this.mExpandedMenuPresenter);
                this.mOptionsMenu.setForceShowOptionalIcon(this.mOptionalIconsVisible);
            } else {
                this.mActionMenuPresenter.initForMenu(this.mContext, null);
                this.mExpandedMenuPresenter.initForMenu(this.mContext, null);
            }
            this.mActionMenuPresenter.updateMenuView(true);
            this.mExpandedMenuPresenter.updateMenuView(true);
            addSplitMenuView();
        }
        if (this.mEndActionMenuEnable && (menuBuilder = this.mEndMenu) != null && menuBuilder.size() > 0) {
            if (this.mEndActionMenuPresenter == null) {
                this.mEndActionMenuPresenter = createEndActionMenuPresenter(callback, this.mHyperActionMenuEnable);
            }
            this.mEndMenu.addMenuPresenter(this.mEndActionMenuPresenter);
            this.mEndMenu.setForceShowOptionalIcon(this.mOptionalIconsVisible);
            this.mEndActionMenuPresenter.updateMenuView(true);
            addEndMenuView();
        }
        updateBadgeOnMenuItemViews();
        updateBackInvokedCallbackState();
    }

    public void setNavigationMode(int i) {
        LinearLayout linearLayout;
        int i2 = this.mNavigationMode;
        if (i != i2) {
            if (i2 == 1 && (linearLayout = this.mListNavLayout) != null) {
                removeView(linearLayout);
            }
            if (i != 0) {
                if (i == 1) {
                    throw new UnsupportedOperationException("MIUIX Deleted");
                }
                if (i == 2 && this.mTabsExit) {
                    addTabsContainer();
                }
            } else if (this.mTabsExit) {
                removeTabsFromContainer();
            }
            this.mNavigationMode = i;
            requestLayout();
        }
    }

    public void setOnSearchViewStyleChangeListener(OnSearchViewStyleChangeListener onSearchViewStyleChangeListener) {
        this.mSearchViewStyleChangeListener = onSearchViewStyleChangeListener;
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setPendingInset(Rect rect) {
        super.setPendingInset(rect);
    }

    public void setProgress(int i) {
        updateProgressBars(i);
    }

    public void setProgressBarIndeterminate(boolean z) {
        updateProgressBars(z ? -3 : -4);
    }

    public void setProgressBarIndeterminateVisibility(boolean z) {
        updateProgressBars(z ? -1 : -2);
    }

    public void setProgressBarVisibility(boolean z) {
        updateProgressBars(z ? -1 : -2);
    }

    public void setRequestInitialAccessibilityFocus(boolean z) {
        this.mRequestInitialA11yFocusEnabled = z;
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setResizable(boolean z) {
        super.setResizable(z);
    }

    public void setSearchBarIconAutoTranslate(boolean z) {
        this.mAutoTranslate = z;
    }

    public void setSearchView(View view) {
        setupWindowWidthWatcher();
        View view2 = this.mSearchView;
        if (view2 != null) {
            removeView(view2);
        }
        this.mSearchView = view;
        if (view != null) {
            addView(view);
        }
        requestLayout();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public void setSplitActionBar(boolean z) {
        if (this.mSplitActionBarEnable != z) {
            ActionMenuView actionMenuView = this.mMenuView;
            if (actionMenuView != null) {
                removeMenuViewFromOldParent(actionMenuView);
                if (z) {
                    ActionBarContainer actionBarContainer = this.mSplitView;
                    if (actionBarContainer != null) {
                        actionBarContainer.addView(this.mMenuView);
                    }
                    this.mMenuView.getLayoutParams().width = -1;
                } else {
                    addView(this.mMenuView);
                    this.mMenuView.getLayoutParams().width = -2;
                }
                this.mMenuView.requestLayout();
            }
            ActionBarContainer actionBarContainer2 = this.mSplitView;
            if (actionBarContainer2 != null) {
                actionBarContainer2.setVisibility(z ? 0 : 8);
            }
            ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
            if (actionMenuPresenter != null) {
                if (z) {
                    actionMenuPresenter.setExpandedActionViewsExclusive(false);
                    this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
                } else {
                    actionMenuPresenter.setExpandedActionViewsExclusive(getResources().getBoolean(miuix.appcompat.R.bool.abc_action_bar_expanded_action_views_exclusive));
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

    public void setStartView(View view) {
        View view2 = this.mStartView;
        if (view2 != null) {
            removeView(view2);
        }
        this.mStartView = view;
        if (view != null) {
            addView(view);
            Folme.useAt(view).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setAlpha(0.6f, new ITouchStyle.TouchType[0]).handleTouchOf(view, new AnimConfig[0]);
            Folme.useAt(this.mStartView).hover().setFeedbackRadius(60.0f);
            Folme.useAt(this.mStartView).hover().setEffect(IHoverStyle.HoverEffect.FLOATED_WRAPPED).handleHoverOf(this.mStartView, new AnimConfig[0]);
        }
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public void setSubTitleClickListener(View.OnClickListener onClickListener) {
        super.setSubTitleClickListener(onClickListener);
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null) {
            collapseTitle.setSubTitleClickable(onClickListener != null);
        }
        MediumTitle mediumTitle = this.mMediumTitle;
        if (mediumTitle != null) {
            mediumTitle.setSubTitleClickable(onClickListener != null);
        }
        ExpandTitle expandTitle = this.mExpandTitle;
        if (expandTitle != null) {
            expandTitle.setSubTitleClickable(onClickListener != null);
        }
    }

    public void setSubTitleDrawable(TextViewDrawableConfig textViewDrawableConfig) {
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null) {
            collapseTitle.setSubTitleDrawable(textViewDrawableConfig);
        }
    }

    public void setSubtitle(CharSequence charSequence) {
        this.mSubtitle = charSequence;
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null) {
            collapseTitle.setSubTitle(charSequence);
        }
        ExpandTitle expandTitle = this.mExpandTitle;
        if (expandTitle != null) {
            expandTitle.setSubTitle(charSequence);
        }
        MediumTitle mediumTitle = this.mMediumTitle;
        if (mediumTitle != null) {
            mediumTitle.setSubTitle(charSequence);
        }
        setTitleVisibility(shouldTitleVisible());
        updateTightTitle();
        post(new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ActionBarView.m1318$r8$lambda$IYtO9vhxJ9BmLhQbMo_WNkz5c(this.f$0);
            }
        });
    }

    public void setTitle(CharSequence charSequence) {
        this.mUserTitle = true;
        setTitleImpl(charSequence);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public void setTitleClickable(boolean z) {
        super.setTitleClickable(z);
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null) {
            collapseTitle.setTitleClickable(z);
        }
        MediumTitle mediumTitle = this.mMediumTitle;
        if (mediumTitle != null) {
            mediumTitle.setTitleClickable(z);
        }
        ExpandTitle expandTitle = this.mExpandTitle;
        if (expandTitle != null) {
            expandTitle.setTitleClickable(z);
        }
    }

    public void setTitleUpIndicator(@DrawableRes int i) {
        setTitleUpIndicator(i == 0 ? null : ContextCompat.getDrawable(getContext(), i));
    }

    public void setTitleUpIndicator(@Nullable final Drawable drawable) {
        if (this.mTitleUpView == null) {
            initTitleUpView();
        }
        final ImageView titleUpInnerImageView = getTitleUpInnerImageView();
        if (titleUpInnerImageView == null) {
            return;
        }
        if (drawable == null) {
            drawable = AttributeResolver.resolveDrawable(getContext(), android.R.attr.homeAsUpIndicator);
        }
        Runnable runnable = this.mPendingTitleUpIndicatorUpdate;
        if (runnable != null) {
            titleUpInnerImageView.removeCallbacks(runnable);
        }
        Runnable runnable2 = new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarView$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                ActionBarView.$r8$lambda$NDUojLe2gQffAvCVeWwY0y8iXq0(this.f$0, titleUpInnerImageView, drawable);
            }
        };
        this.mPendingTitleUpIndicatorUpdate = runnable2;
        titleUpInnerImageView.post(runnable2);
    }

    public void setTitleVisible(boolean z, boolean z2) {
        this.mCollapsedTitleVisible = z;
        this.mExpandTitleVisible = z2;
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null) {
            collapseTitle.setVisible(z);
        }
        MediumTitle mediumTitle = this.mMediumTitle;
        if (mediumTitle != null) {
            mediumTitle.setVisible(z);
        }
        ExpandTitle expandTitle = this.mExpandTitle;
        if (expandTitle != null) {
            expandTitle.setVisible(z2);
        }
    }

    public void setUserSetEndActionMenuItemLimit(boolean z) {
        this.mUserSetEndActionMenuItemLimit = z;
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView, android.view.View
    public /* bridge */ /* synthetic */ void setVisibility(int i) {
        super.setVisibility(i);
    }

    public void setWindowCallback(Window.Callback callback) {
        this.mWindowCallback = callback;
    }

    public void setWindowTitle(CharSequence charSequence) {
        if (this.mUserTitle) {
            return;
        }
        setTitleImpl(charSequence);
    }

    public final void setupTabView(ScrollingTabContainerView scrollingTabContainerView, ScrollingTabContainerView scrollingTabContainerView2, SecondaryTabBar secondaryTabBar, SecondaryTabBar secondaryTabBar2) {
        this.mCollapseTabs = scrollingTabContainerView;
        this.mExpandTabs = scrollingTabContainerView2;
        this.mSecondaryCollapseTabs = secondaryTabBar;
        this.mSecondaryExpandTabs = secondaryTabBar2;
        if (secondaryTabBar != null) {
            secondaryTabBar.setParentBlurEnabled(this.mApplyBgBlur);
        }
        SecondaryTabBar secondaryTabBar3 = this.mSecondaryExpandTabs;
        if (secondaryTabBar3 != null) {
            secondaryTabBar3.setParentBlurEnabled(this.mApplyBgBlur);
        }
    }

    public final void setupWindowWidthWatcher() {
        if (this.mWindowWidthThreshold == 0) {
            this.mWindowWidthThreshold = getResources().getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_search_view_window_width_threshold);
        }
        if (this.mWindowWidthWatcher == null) {
            View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.15
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    if (DeviceUtils.isTablet() && ActionBarView.this.mAutoTranslate) {
                        int i9 = EnvStateManager.getWindowSize(ActionBarView.this.mContext).x;
                        MenuBuilder endMenu = ActionBarView.this.getEndMenu();
                        if (endMenu == null) {
                            return;
                        }
                        ActionBarView.this.updateSearchViewStyle(endMenu, i9 < ActionBarView.this.mWindowWidthThreshold || ActionBarView.this.mForceShowSearchIcon);
                    }
                }
            };
            this.mWindowWidthWatcher = onLayoutChangeListener;
            addOnLayoutChangeListener(onLayoutChangeListener);
        }
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public final boolean shouldMeasureCollapseTabContainer() {
        FrameLayout frameLayout = this.mCollapseTabContainer;
        return (frameLayout == null || frameLayout.getParent() != this || this.mCollapseTabContainer.getChildCount() == 0) ? false : true;
    }

    public final boolean shouldMeasureMovableTabContainer() {
        FrameLayout frameLayout = this.mMovableTabContainer;
        return (frameLayout == null || frameLayout.getParent() != this || this.mMovableTabContainer.getChildCount() == 0) ? false : true;
    }

    public final boolean shouldTitleVisible() {
        return (this.mExpandedActionView != null || (this.mDisplayOptions & 8) == 0 || isAllTitlesEmpty()) ? false : true;
    }

    public void showBottomMenuCustomView() {
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView instanceof ResponsiveActionMenuView) {
            ((ResponsiveActionMenuView) actionMenuView).showBottomMenuCustomView();
        }
    }

    public final void showContainerInFinishActionMode() {
        if (getExpandState() == 0) {
            this.mCollapseController.animTo(1.0f, 0, 0, this.mMovableAnimNormalConfig);
            return;
        }
        if (getExpandState() == 1) {
            if (hasTabsInContainer(this.mMainContainer)) {
                this.mCollapseController.animTo(1.0f, 0, 0, this.mMovableAnimNormalConfig);
            } else {
                this.mMediumController.animTo(1.0f, 0, 0, this.mMovableAnimNormalConfig);
            }
            ensureCollapseTabContainerVisibleForMedium();
            return;
        }
        if (getExpandState() == 2) {
            this.mCollapseController.setAlpha(0.0f);
            this.mCollapseController.setVisibility(0);
            this.mMovableController.animTo(1.0f, 0, 0, this.mMovableAnimShowConfig);
        }
    }

    public boolean showEndOverflowMenu() {
        ActionMenuPresenter actionMenuPresenter;
        LifecycleOwner lifecycleOwner = this.mLifecycleOwner;
        return (lifecycleOwner != null ? lifecycleOwner.getLifecycle().getCurrentState().equals(Lifecycle.State.RESUMED) : true) && (actionMenuPresenter = this.mEndActionMenuPresenter) != null && this.mEndActionMenuEnable && actionMenuPresenter.showOverflowMenu();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ boolean showOverflowMenu() {
        return super.showOverflowMenu();
    }

    public final void showProgressBars(ProgressBar progressBar, ProgressBar progressBar2) {
        if (progressBar2 != null && progressBar2.getVisibility() == 4) {
            progressBar2.setVisibility(0);
        }
        if (progressBar == null || progressBar.getProgress() >= 10000) {
            return;
        }
        progressBar.setVisibility(0);
    }

    public final void snapToFinalExpandState() {
        int measuredHeight = this.mMovableMainContainer.getMeasuredHeight();
        int height = getHeight();
        if (isUserSetExpandState() && this.mUserExpandState == 1) {
            return;
        }
        int i = this.mPendingHeight;
        if (i == 0) {
            setExpandState(0);
            return;
        }
        int i2 = this.mMovableSecondaryTabHeight;
        if (i == measuredHeight + i2) {
            setExpandState(2);
            return;
        }
        int i3 = this.mCollapseTotalHeight;
        if (height > ((i2 + measuredHeight) / 2) + i3) {
            this.mPostScroller.startScroll(0, height, 0, (i3 + measuredHeight) - height);
        } else {
            this.mPostScroller.startScroll(0, height, 0, i3 - height);
        }
        this.mHandler.postDelayed(this.mPostAnimationRunnable, 20L);
    }

    public void updateActionButtonFloatingState(boolean z) {
        Boolean bool = this.mFloatingMode;
        if (bool == null || bool.booleanValue() != z) {
            this.mFloatingMode = Boolean.valueOf(z);
            applyFloatingStateToButtons();
        }
    }

    public final void updateBadgeOnMenuItemViews() {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        if (actionMenuPresenter == null) {
            return;
        }
        actionMenuPresenter.updateBadgeOnItemViews();
        ActionMenuPresenter actionMenuPresenter2 = this.mEndActionMenuPresenter;
        if (actionMenuPresenter2 instanceof EndActionMenuPresenter) {
            ((EndActionMenuPresenter) actionMenuPresenter2).updateBadgeOnMoreButton();
        }
    }

    public final void updateCollapseTitle() {
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null) {
            if (collapseTitle.getTitleVisibility() != 0) {
                this.mCollapseTitle.setTitleVisibility(0);
            }
            this.mCollapseTitle.setTitle(this.mTitle);
            this.mCollapseTitle.setSubTitle(this.mSubtitle);
            post(new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarView$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    ActionBarView.$r8$lambda$mtfXocOWfKTftX6UTRcy4K7yeLU(this.f$0);
                }
            });
        }
    }

    public final void updateCollapseTitleTouchDelegate() {
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null) {
            Rect hitRect = collapseTitle.getHitRect();
            hitRect.left -= AttributeResolver.resolveDimensionPixelSize(getContext(), miuix.appcompat.R.attr.actionBarPaddingStart);
            setTouchDelegate(new TouchDelegate(hitRect, this.mCollapseTitle.getLayout()));
        }
    }

    public final void updateCollapseTitleVisibility(boolean z) {
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null) {
            collapseTitle.setVisibility(z ? 0 : 4);
        }
    }

    public boolean updateExpandStateOnLayout() {
        if (this.mInnerExpandState != 3) {
            return false;
        }
        int i = this.mExpandStateOnLayout;
        int i2 = this.mPendingHeight;
        if (i2 == 0) {
            i = 0;
        } else if (i2 == this.mMovableMainContainer.getMeasuredHeight() + this.mMovableSecondaryTabHeight) {
            i = 2;
        }
        if (this.mExpandStateOnLayout == i) {
            return false;
        }
        this.mExpandStateOnLayout = i;
        this.mExpandStateBeforeResizing = i;
        return true;
    }

    public final void updateExpandTitle() {
        if (this.mExpandTitle != null) {
            boolean zUpdateExpandTitleOnShowCustom = (!((this.mDisplayOptions & 16) != 0) || this.mCustomNavView == null) ? false : updateExpandTitleOnShowCustom();
            this.mExpandTitle.setTitleVisibility(0);
            if (!zUpdateExpandTitleOnShowCustom) {
                this.mExpandTitle.setTitle(this.mTitle);
            }
            this.mExpandTitle.setSubTitle(this.mSubtitle);
        }
    }

    public final boolean updateExpandTitleOnShowCustom() {
        TextView customTitleView = getCustomTitleView((FrameLayout) this.mCustomNavView.findViewById(miuix.appcompat.R.id.action_bar_expand_container));
        if (customTitleView == null) {
            return false;
        }
        if (this.mExpandTitle == null) {
            return true;
        }
        if (TextUtils.isEmpty(this.mInitCustomTitle)) {
            this.mExpandTitle.setTitle(this.mTitle);
            customTitleView.removeTextChangedListener(this.mCustomTitleWatcher);
            customTitleView.setText(this.mTitle);
            customTitleView.addTextChangedListener(this.mCustomTitleWatcher);
        } else {
            if (!this.mInitCustomTitle.equals(customTitleView.getText())) {
                customTitleView.removeTextChangedListener(this.mCustomTitleWatcher);
                customTitleView.setText(this.mInitCustomTitle);
                customTitleView.addTextChangedListener(this.mCustomTitleWatcher);
            }
            this.mExpandTitle.setTitle(this.mInitCustomTitle);
        }
        if (this.mExpandTitle.getVisibility() != 0) {
            this.mExpandTitle.setVisibility(0);
        }
        this.mExpandTitle.setSubTitleVisibility(8);
        return true;
    }

    public final void updateExpandTitleVisibility(boolean z) {
        ExpandTitle expandTitle = this.mExpandTitle;
        if (expandTitle != null) {
            expandTitle.setVisibility(z ? 0 : 4);
        }
    }

    public final void updateMediumTitle() {
        MediumTitle mediumTitle = this.mMediumTitle;
        if (mediumTitle != null) {
            mediumTitle.setTitleVisibility(0);
            this.mMediumTitle.setTitle(this.mTitle);
            this.mMediumTitle.setSubTitle(this.mSubtitle);
            this.mMediumTitle.setVisibility(0);
        }
    }

    public final void updateMediumTitleVisibility(boolean z) {
        MediumTitle mediumTitle = this.mMediumTitle;
        if (mediumTitle != null) {
            mediumTitle.setVisibility(z ? 0 : 8);
        }
    }

    public final void updateMovableContainerVisibility(boolean z) {
        if (this.mMovableController == null) {
            return;
        }
        if (isAllTitlesEmpty()) {
            this.mMovableController.setVisibility(8);
            return;
        }
        if (z) {
            int i = this.mInnerExpandState;
            if (i != 0 && i != 1) {
                this.mMovableController.setVisibility(0);
            } else {
                this.mMovableController.setVisibility(8);
                this.mMovableController.setAnimFrom(0.0f, 0, 0, true);
            }
        }
    }

    public final void updateProgressBars(int i) {
        ProgressBar circularProgressBar = getCircularProgressBar();
        ProgressBar horizontalProgressBar = getHorizontalProgressBar();
        if (i == -1) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setVisibility((horizontalProgressBar.isIndeterminate() || horizontalProgressBar.getProgress() < 10000) ? 0 : 4);
            }
            if (circularProgressBar != null) {
                circularProgressBar.setVisibility(0);
                return;
            }
            return;
        }
        if (i == -2) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setVisibility(8);
            }
            if (circularProgressBar != null) {
                circularProgressBar.setVisibility(8);
                return;
            }
            return;
        }
        if (i == -3) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setIndeterminate(true);
            }
        } else if (i == -4) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setIndeterminate(false);
            }
        } else {
            if (i < 0 || i > 10000 || horizontalProgressBar == null) {
                return;
            }
            horizontalProgressBar.setProgress(i);
            if (i < 10000) {
                showProgressBars(horizontalProgressBar, circularProgressBar);
            } else {
                hideProgressBars(horizontalProgressBar, circularProgressBar);
            }
        }
    }

    public final void updateSearchViewStyle(MenuBuilder menuBuilder, boolean z) {
        if (z) {
            View view = this.mSearchView;
            if (view != null) {
                view.setVisibility(8);
            }
            MenuItem menuItem = this.mSearchIconMenuItem;
            if (menuItem == null) {
                MenuItem menuItemAdd = menuBuilder.add(miuix.appcompat.R.id.miuix_action_end_menu_group, miuix.appcompat.R.id.miuix_action_end_menu_search, 0, (CharSequence) null);
                this.mSearchIconMenuItem = menuItemAdd;
                menuItemAdd.setIcon(ViewUtils.isNightMode(this.mContext) ? miuix.appcompat.R.drawable.miuix_action_icon_search_dark : miuix.appcompat.R.drawable.miuix_action_icon_search_light);
                this.mSearchIconMenuItem.setShowAsAction(2);
            } else {
                menuItem.setVisible(true);
            }
        } else {
            View view2 = this.mSearchView;
            if (view2 != null) {
                view2.setVisibility(0);
            }
            MenuItem menuItem2 = this.mSearchIconMenuItem;
            if (menuItem2 != null) {
                menuItem2.setVisible(false);
            }
        }
        OnSearchViewStyleChangeListener onSearchViewStyleChangeListener = this.mSearchViewStyleChangeListener;
        if (onSearchViewStyleChangeListener != null) {
            onSearchViewStyleChangeListener.onStyleChange(z);
        }
    }

    public final void updateTabsLayoutParams() {
        ViewGroup.LayoutParams layoutParams;
        ViewGroup.LayoutParams layoutParams2;
        ViewGroup.LayoutParams layoutParams3;
        ViewGroup.LayoutParams layoutParams4;
        ScrollingTabContainerView scrollingTabContainerView = this.mCollapseTabs;
        if (scrollingTabContainerView != null && (layoutParams4 = scrollingTabContainerView.getLayoutParams()) != null) {
            layoutParams4.width = -2;
            layoutParams4.height = -1;
        }
        ScrollingTabContainerView scrollingTabContainerView2 = this.mExpandTabs;
        if (scrollingTabContainerView2 != null && (layoutParams3 = scrollingTabContainerView2.getLayoutParams()) != null) {
            layoutParams3.width = -2;
            layoutParams3.height = -2;
        }
        SecondaryTabBar secondaryTabBar = this.mSecondaryCollapseTabs;
        if (secondaryTabBar != null && (layoutParams2 = secondaryTabBar.asViewGroup().getLayoutParams()) != null) {
            layoutParams2.width = -1;
            layoutParams2.height = -1;
        }
        SecondaryTabBar secondaryTabBar2 = this.mSecondaryExpandTabs;
        if (secondaryTabBar2 == null || (layoutParams = secondaryTabBar2.asViewGroup().getLayoutParams()) == null) {
            return;
        }
        layoutParams.width = -1;
        layoutParams.height = -1;
    }

    public final void updateTightTitle() {
        if (isTightTitleWithEmbeddedTabs()) {
            TextUtils.isEmpty(this.mTitle);
        }
        TextUtils.isEmpty(this.mSubtitle);
    }

    public final void updateTitleCenter() {
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null) {
            collapseTitle.updateTitleCenter(isTitleCenter());
        }
    }

    public final void updateTitleUpViewVisibility() {
        if (this.mTitleUpView == null || (getDisplayOptions() & 32) != 0) {
            return;
        }
        int i = this.mDisplayOptions;
        boolean z = (i & 4) != 0;
        if ((i & 2) != 0) {
            this.mTitleUpView.setVisibility(8);
        } else {
            this.mTitleUpView.setVisibility(z ? 0 : 4);
        }
    }

    public final void updateTitleVisibilityByState(int i) {
        boolean zShouldTitleVisible = shouldTitleVisible();
        boolean z = false;
        boolean z2 = i == 0 || i == 3;
        boolean z3 = i == 2 || i == 3;
        boolean z4 = i == 1;
        updateCollapseTitleVisibility(zShouldTitleVisible && z2);
        updateMediumTitleVisibility(zShouldTitleVisible && z4);
        if (zShouldTitleVisible && z3) {
            z = true;
        }
        updateExpandTitleVisibility(z);
    }
}
