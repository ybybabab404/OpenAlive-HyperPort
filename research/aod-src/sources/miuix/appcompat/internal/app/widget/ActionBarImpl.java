package miuix.appcompat.internal.app.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.core.view.ScrollingView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.miui.aod.components.view.BaseStyleSelectView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import miuix.appcompat.R;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.app.ActionBarTransitionListener;
import miuix.appcompat.app.AppCompatActivity;
import miuix.appcompat.app.IFragment;
import miuix.appcompat.app.strategy.ActionBarConfig;
import miuix.appcompat.app.strategy.ActionBarSpec;
import miuix.appcompat.app.strategy.CommonActionBarStrategy;
import miuix.appcompat.app.strategy.IActionBarStrategy;
import miuix.appcompat.internal.app.widget.actionbar.CollapseTitle;
import miuix.appcompat.internal.app.widget.actionbar.ExpandTitle;
import miuix.appcompat.internal.view.ActionBarPolicy;
import miuix.appcompat.internal.view.ActionModeImpl;
import miuix.appcompat.internal.view.EditActionModeImpl;
import miuix.appcompat.internal.view.SearchActionModeImpl;
import miuix.appcompat.internal.view.menu.action.PhoneActionMenuView;
import miuix.container.ExtraPaddingPolicy;
import miuix.core.util.EnvStateManager;
import miuix.core.util.MiuiBlurUtils;
import miuix.core.util.MiuixUIUtils;
import miuix.core.view.NestedContentInsetObserver;
import miuix.core.view.NestedCoordinatorObserver;
import miuix.internal.util.AttributeResolver;
import miuix.internal.util.LiteUtils;
import miuix.view.SearchActionMode;

/* JADX INFO: loaded from: classes2.dex */
public class ActionBarImpl extends ActionBar {
    public boolean isSelectingTab;
    public int mActionBarHeightTotalGap;
    public int mActionBarHeightTotalGapOnShow;
    public IActionBarStrategy mActionBarStrategy;
    public ActionMode mActionMode;
    public ActionModeImpl.ActionModeCallback mActionModeCallback;
    public ActionModeView mActionModeView;
    public ActionBarView mActionView;
    public boolean mAdsorptionToNoOverlay;
    public IStateStyle mContainerAnim;
    public ActionBarContainer mContainerView;
    public final TransitionListener mContainerViewAnimationListener;
    public Rect mContentInset;
    public int mContentInsetTop;
    public View mContentMask;
    public View.OnClickListener mContentMaskOnClickListener;
    public Context mContext;
    public int mContextDisplayMode;
    public ActionBarContextView mContextView;
    public final HashMap mCoordinateOffsetViewSet;
    public int mCurWindowVisibility;
    public int mCurrentAccessibilityImportant;
    public int mCurrentActionBarHeightGap;
    public int mCurrentActionBarHeightGapOnShow;
    public int mCurrentExpandState;
    public boolean mCurrentResizable;
    public boolean mDisplayHomeAsUpSet;
    public ScrollingTabContainerView mExpandTabScrollView;
    public ExtraPaddingPolicy mExtraPaddingPolicy;
    public FragmentManager mFragmentManager;
    public boolean mHiddenByApp;
    public boolean mHiddenByAppBeforeSearchActionMode;
    public boolean mHiddenBySystem;
    public boolean mIsContainerAnimationRunning;
    public boolean mIsWindowInfoChanged;
    public int mMaxActionMenuItemCount;
    public ArrayList mMenuVisibilityListeners;
    public final HashSet mNestedContentInsetObserverSet;
    public boolean mNowShowing;
    public ActionBarOverlayLayout mOverlayLayout;
    public boolean mOverlayMaskRecheckPending;
    public ActionBarCoordinateListener mOwnCoordinateListener;
    public ViewTreeObserver.OnPreDrawListener mRecheckPreDrawListener;
    public boolean mRestoreCollapseOnScrollRecheckEnabled;
    public int mSavedTabPosition;
    public SearchActionModeView mSearchActionModeView;
    public SecondaryTabBar mSecondaryExpandTabScrollView;
    public SecondaryTabBar mSecondaryTabScrollView;
    public TabImpl mSelectedTab;
    public boolean mShowHideAnimationEnabled;
    public boolean mShowingForMode;
    public PhoneActionMenuView mSplitMenuView;
    public ActionBarContainer mSplitView;
    public View.OnLayoutChangeListener mStrategyLayoutChangeListener;
    public ViewTreeObserver.OnPreDrawListener mStrategyPreDrawListener;
    public ScrollingTabContainerView mTabScrollView;
    public ArrayList mTabs;
    public float mTargetTranslationY;
    public Context mThemedContext;
    public final int[] mTmpLocationA;
    public final int[] mTmpLocationB;
    public ActionBarViewPagerController mViewPagerController;
    public int mWindowMode;
    public static androidx.appcompat.app.ActionBar.TabListener sTabListenerWrapper = new androidx.appcompat.app.ActionBar.TabListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarImpl.1
        @Override // androidx.appcompat.app.ActionBar.TabListener
        public void onTabReselected(androidx.appcompat.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            TabImpl tabImpl = (TabImpl) tab;
            if (tabImpl.mInternalCallback != null) {
                tabImpl.mInternalCallback.onTabReselected(tab, fragmentTransaction);
            }
            if (tabImpl.mCallback != null) {
                tabImpl.mCallback.onTabReselected(tab, fragmentTransaction);
            }
        }

        @Override // androidx.appcompat.app.ActionBar.TabListener
        public void onTabSelected(androidx.appcompat.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            TabImpl tabImpl = (TabImpl) tab;
            if (tabImpl.mInternalCallback != null) {
                tabImpl.mInternalCallback.onTabSelected(tab, fragmentTransaction);
            }
            if (tabImpl.mCallback != null) {
                tabImpl.mCallback.onTabSelected(tab, fragmentTransaction);
            }
        }

        @Override // androidx.appcompat.app.ActionBar.TabListener
        public void onTabUnselected(androidx.appcompat.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            TabImpl tabImpl = (TabImpl) tab;
            if (tabImpl.mInternalCallback != null) {
                tabImpl.mInternalCallback.onTabUnselected(tab, fragmentTransaction);
            }
            if (tabImpl.mCallback != null) {
                tabImpl.mCallback.onTabUnselected(tab, fragmentTransaction);
            }
        }
    };
    public static final Integer UNINITIALIZED_OFFSET = -1;

    /* JADX INFO: renamed from: miuix.appcompat.internal.app.widget.ActionBarImpl$5, reason: invalid class name */
    public class AnonymousClass5 implements View.OnLayoutChangeListener {
        public int lastWidth = 0;
        public int lastHeight = 0;

        /* JADX INFO: renamed from: $r8$lambda$VBjL__c9DLk1QV2vfeVIDz7FE-4, reason: not valid java name */
        public static /* synthetic */ void m1317$r8$lambda$VBjL__c9DLk1QV2vfeVIDz7FE4(AnonymousClass5 anonymousClass5) {
            ActionBarImpl actionBarImpl = ActionBarImpl.this;
            actionBarImpl.applyActionBarStrategy(actionBarImpl.mActionView, ActionBarImpl.this.mContextView);
        }

        public AnonymousClass5() {
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            int i9 = i3 - i;
            int i10 = i4 - i2;
            if (this.lastWidth != i9 || ActionBarImpl.this.mIsWindowInfoChanged) {
                ActionBarImpl.this.mIsWindowInfoChanged = false;
                this.lastWidth = i9;
                this.lastHeight = i10;
                ActionBarImpl.this.mActionView.post(new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarImpl$5$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActionBarImpl.AnonymousClass5.m1317$r8$lambda$VBjL__c9DLk1QV2vfeVIDz7FE4(this.f$0);
                    }
                });
                return;
            }
            if (this.lastHeight != i10) {
                this.lastHeight = i10;
                ActionBarImpl.this.postRecheckOverlayMaskState();
            }
        }
    }

    public class TabImpl extends androidx.appcompat.app.ActionBar.Tab {
        public androidx.appcompat.app.ActionBar.TabListener mCallback;
        public CharSequence mContentDesc;
        public View mCustomView;
        public Drawable mIcon;
        public androidx.appcompat.app.ActionBar.TabListener mInternalCallback;
        public int mPosition;
        public CharSequence mText;
        public boolean mWithAnim;
        public final /* synthetic */ ActionBarImpl this$0;

        public androidx.appcompat.app.ActionBar.TabListener getCallback() {
            return ActionBarImpl.sTabListenerWrapper;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public CharSequence getContentDescription() {
            return this.mContentDesc;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public View getCustomView() {
            return this.mCustomView;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public Drawable getIcon() {
            return this.mIcon;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public int getPosition() {
            return this.mPosition;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public CharSequence getText() {
            return this.mText;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public void select() {
            this.this$0.selectTab(this, true);
        }
    }

    public static class ViewHideTransitionListener extends TransitionListener {
        public WeakReference mActionBarRef;
        public WeakReference mRef;

        public ViewHideTransitionListener(View view, ActionBarImpl actionBarImpl) {
            this.mRef = new WeakReference(view);
            this.mActionBarRef = new WeakReference(actionBarImpl);
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            ActionBarImpl actionBarImpl = (ActionBarImpl) this.mActionBarRef.get();
            View view = (View) this.mRef.get();
            if (view == null || actionBarImpl == null || actionBarImpl.mNowShowing) {
                return;
            }
            view.setVisibility(8);
            if (view.getId() == R.id.action_bar_container) {
                view.setTranslationY(-view.getHeight());
            }
        }
    }

    public static class ViewShowTransitionListener extends TransitionListener {
        public WeakReference mActionBarRef;
        public WeakReference mRef;

        public ViewShowTransitionListener(View view, ActionBarImpl actionBarImpl) {
            this.mRef = new WeakReference(view);
            this.mActionBarRef = new WeakReference(actionBarImpl);
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            ActionBarImpl actionBarImpl = (ActionBarImpl) this.mActionBarRef.get();
            View view = (View) this.mRef.get();
            if (view == null || actionBarImpl == null || !actionBarImpl.mNowShowing || view.getId() != R.id.action_bar_container) {
                return;
            }
            view.setTranslationY(0.0f);
        }
    }

    public static /* synthetic */ boolean $r8$lambda$I0xKt96dlj64UHqSakKhd66xoJ8(WeakReference weakReference) {
        ActionBarImpl actionBarImpl = (ActionBarImpl) weakReference.get();
        if (actionBarImpl != null) {
            Context context = actionBarImpl.mContext;
            if (!(context instanceof Activity) || !((Activity) context).isDestroyed()) {
                actionBarImpl.preCreateSearchActionModeView();
                return false;
            }
        }
        return false;
    }

    public static /* synthetic */ void $r8$lambda$cXU2ox_wcuV6zykg8U20_3QSuww(ActionBarImpl actionBarImpl, int i, float f, int i2, int i3) {
        actionBarImpl.mCurrentActionBarHeightGapOnShow = i2;
        actionBarImpl.mActionBarHeightTotalGapOnShow = i3;
        float height = (actionBarImpl.mContainerView.getHeight() + actionBarImpl.mContainerView.getTranslationY()) / actionBarImpl.mContainerView.getHeight();
        float f2 = actionBarImpl.mTargetTranslationY;
        if (f2 != 0.0f) {
            height = (f2 - actionBarImpl.mContainerView.getTranslationY()) / actionBarImpl.mTargetTranslationY;
        }
        if (actionBarImpl.mContainerView.getHeight() == 0) {
            height = 1.0f;
        }
        actionBarImpl.mCurrentActionBarHeightGap = (int) (actionBarImpl.mCurrentActionBarHeightGapOnShow * height);
        actionBarImpl.mActionBarHeightTotalGap = (int) (actionBarImpl.mActionBarHeightTotalGapOnShow * height);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ActionBarImpl(Fragment fragment) {
        this.mRestoreCollapseOnScrollRecheckEnabled = false;
        this.mCoordinateOffsetViewSet = new HashMap();
        this.mNestedContentInsetObserverSet = new HashSet();
        this.mTabs = new ArrayList();
        this.isSelectingTab = false;
        this.mSavedTabPosition = -1;
        this.mMenuVisibilityListeners = new ArrayList();
        this.mCurWindowVisibility = 0;
        this.mNowShowing = true;
        this.mActionModeCallback = new ActionModeImpl.ActionModeCallback() { // from class: miuix.appcompat.internal.app.widget.ActionBarImpl.2
            @Override // miuix.appcompat.internal.view.ActionModeImpl.ActionModeCallback
            public void onActionModeFinish(ActionMode actionMode) {
                ActionBarImpl.this.animateToMode(false);
                ActionBarImpl.this.mActionMode = null;
            }
        };
        this.mIsContainerAnimationRunning = false;
        this.mTmpLocationA = new int[2];
        this.mTmpLocationB = new int[2];
        this.mMaxActionMenuItemCount = -1;
        this.mContentInsetTop = 0;
        this.mCurrentActionBarHeightGap = 0;
        this.mActionBarHeightTotalGap = 0;
        this.mCurrentActionBarHeightGapOnShow = 0;
        this.mActionBarHeightTotalGapOnShow = 0;
        this.mTargetTranslationY = 0.0f;
        this.mContainerViewAnimationListener = new TransitionListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarImpl.8
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                super.onCancel(obj);
                ActionBarImpl.this.mIsContainerAnimationRunning = false;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                ActionBarImpl.this.mIsContainerAnimationRunning = false;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                super.onUpdate(obj, collection);
                float translationY = (ActionBarImpl.this.mTargetTranslationY - ActionBarImpl.this.mContainerView.getTranslationY()) / ActionBarImpl.this.mTargetTranslationY;
                ActionBarImpl actionBarImpl = ActionBarImpl.this;
                actionBarImpl.mActionBarHeightTotalGap = (int) Math.max(0.0f, actionBarImpl.mActionBarHeightTotalGapOnShow * translationY);
                ActionBarImpl actionBarImpl2 = ActionBarImpl.this;
                actionBarImpl2.mCurrentActionBarHeightGap = (int) Math.max(0.0f, actionBarImpl2.mCurrentActionBarHeightGapOnShow * translationY);
                ActionBarImpl.this.mOverlayLayout.updateCurrentContentInsetInOverlayMode();
                ActionBarImpl.this.updateCoordinateOffsetView();
            }
        };
        this.mContext = ((IFragment) fragment).getThemedContext();
        this.mFragmentManager = fragment.getChildFragmentManager();
        init((ViewGroup) fragment.getView());
        FragmentActivity activity = fragment.getActivity();
        this.mActionView.setWindowTitle(activity != null ? activity.getTitle() : null);
    }

    public ActionBarImpl(AppCompatActivity appCompatActivity, ViewGroup viewGroup) {
        this.mRestoreCollapseOnScrollRecheckEnabled = false;
        this.mCoordinateOffsetViewSet = new HashMap();
        this.mNestedContentInsetObserverSet = new HashSet();
        this.mTabs = new ArrayList();
        this.isSelectingTab = false;
        this.mSavedTabPosition = -1;
        this.mMenuVisibilityListeners = new ArrayList();
        this.mCurWindowVisibility = 0;
        this.mNowShowing = true;
        this.mActionModeCallback = new ActionModeImpl.ActionModeCallback() { // from class: miuix.appcompat.internal.app.widget.ActionBarImpl.2
            @Override // miuix.appcompat.internal.view.ActionModeImpl.ActionModeCallback
            public void onActionModeFinish(ActionMode actionMode) {
                ActionBarImpl.this.animateToMode(false);
                ActionBarImpl.this.mActionMode = null;
            }
        };
        this.mIsContainerAnimationRunning = false;
        this.mTmpLocationA = new int[2];
        this.mTmpLocationB = new int[2];
        this.mMaxActionMenuItemCount = -1;
        this.mContentInsetTop = 0;
        this.mCurrentActionBarHeightGap = 0;
        this.mActionBarHeightTotalGap = 0;
        this.mCurrentActionBarHeightGapOnShow = 0;
        this.mActionBarHeightTotalGapOnShow = 0;
        this.mTargetTranslationY = 0.0f;
        this.mContainerViewAnimationListener = new TransitionListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarImpl.8
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                super.onCancel(obj);
                ActionBarImpl.this.mIsContainerAnimationRunning = false;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                ActionBarImpl.this.mIsContainerAnimationRunning = false;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                super.onUpdate(obj, collection);
                float translationY = (ActionBarImpl.this.mTargetTranslationY - ActionBarImpl.this.mContainerView.getTranslationY()) / ActionBarImpl.this.mTargetTranslationY;
                ActionBarImpl actionBarImpl = ActionBarImpl.this;
                actionBarImpl.mActionBarHeightTotalGap = (int) Math.max(0.0f, actionBarImpl.mActionBarHeightTotalGapOnShow * translationY);
                ActionBarImpl actionBarImpl2 = ActionBarImpl.this;
                actionBarImpl2.mCurrentActionBarHeightGap = (int) Math.max(0.0f, actionBarImpl2.mCurrentActionBarHeightGapOnShow * translationY);
                ActionBarImpl.this.mOverlayLayout.updateCurrentContentInsetInOverlayMode();
                ActionBarImpl.this.updateCoordinateOffsetView();
            }
        };
        this.mContext = appCompatActivity;
        this.mFragmentManager = appCompatActivity.getSupportFragmentManager();
        init(viewGroup);
        this.mActionView.setWindowTitle(appCompatActivity.getTitle());
    }

    private static boolean checkShowingFlags(boolean z, boolean z2, boolean z3) {
        if (z3) {
            return true;
        }
        return (z || z2) ? false : true;
    }

    private void doHide(boolean z) {
        doHide(z, true, null);
    }

    private void doShow(boolean z) {
        doShow(z, true, null);
    }

    public static boolean isViewOrDescendantScrolled(View view) {
        return isViewOrDescendantScrolled(view, 0);
    }

    public static boolean isViewOrDescendantScrolled(View view, int i) {
        if (view != null && view.getVisibility() != 8) {
            if (view.canScrollVertically(-1)) {
                return true;
            }
            if (i < 8 && (view instanceof ViewGroup)) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    if (isViewOrDescendantScrolled(viewGroup.getChildAt(i2), i + 1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void setHasEmbeddedTabs(boolean z) {
        ActionBarContainer actionBarContainer = this.mContainerView;
        if (actionBarContainer != null) {
            actionBarContainer.setTabContainer(null);
        }
        this.mActionView.setEmbeddedTabView(this.mTabScrollView, this.mExpandTabScrollView, this.mSecondaryTabScrollView, this.mSecondaryExpandTabScrollView);
        boolean z2 = getNavigationMode() == 2;
        ScrollingTabContainerView scrollingTabContainerView = this.mTabScrollView;
        if (scrollingTabContainerView != null) {
            if (z2) {
                scrollingTabContainerView.setVisibility(0);
            } else {
                scrollingTabContainerView.setVisibility(8);
            }
            this.mTabScrollView.setEmbeded(true);
        }
        ScrollingTabContainerView scrollingTabContainerView2 = this.mExpandTabScrollView;
        if (scrollingTabContainerView2 != null) {
            if (z2) {
                scrollingTabContainerView2.setVisibility(0);
            } else {
                scrollingTabContainerView2.setVisibility(8);
            }
            this.mExpandTabScrollView.setEmbeded(true);
        }
        SecondaryTabBar secondaryTabBar = this.mSecondaryTabScrollView;
        if (secondaryTabBar != null) {
            if (z2) {
                secondaryTabBar.asViewGroup().setVisibility(0);
            } else {
                secondaryTabBar.asViewGroup().setVisibility(8);
            }
        }
        SecondaryTabBar secondaryTabBar2 = this.mSecondaryExpandTabScrollView;
        if (secondaryTabBar2 != null) {
            if (z2) {
                secondaryTabBar2.asViewGroup().setVisibility(0);
            } else {
                secondaryTabBar2.asViewGroup().setVisibility(8);
            }
        }
        this.mActionView.setCollapsable(false);
    }

    private void updateVisibility(boolean z) {
        updateVisibility(z, true, null);
    }

    @Override // miuix.appcompat.app.ActionBar
    public void addActionBarTransitionListener(ActionBarTransitionListener actionBarTransitionListener) {
        this.mContainerView.addActionBarTransitionListener(actionBarTransitionListener);
    }

    public void animateToMode(boolean z) {
        if (z) {
            showForActionMode();
            ActionModeView actionModeView = this.mActionModeView;
            if (actionModeView instanceof SearchActionModeView) {
                ((SearchActionModeView) actionModeView).setActionBarHiddenBeforeSearchMode(this.mHiddenByAppBeforeSearchActionMode);
            }
        } else {
            ActionModeView actionModeView2 = this.mActionModeView;
            if (actionModeView2 instanceof SearchActionModeView) {
                ((SearchActionModeView) actionModeView2).setActionBarHiddenBeforeSearchMode(this.mHiddenByAppBeforeSearchActionMode);
            }
            hideForActionMode();
        }
        this.mActionModeView.animateToVisibility(z);
        if (this.mTabScrollView == null || this.mActionView.isTightTitleWithEmbeddedTabs() || !this.mActionView.isCollapsed()) {
            return;
        }
        this.mTabScrollView.setEnabled(!z);
        this.mExpandTabScrollView.setEnabled(!z);
        this.mSecondaryTabScrollView.asViewGroup().setEnabled(!z);
        this.mSecondaryExpandTabScrollView.asViewGroup().setEnabled(!z);
    }

    public final void applyActionBarStrategy(ActionBarView actionBarView, ActionBarContextView actionBarContextView) {
        if (this.mActionBarStrategy == null) {
            return;
        }
        int expandState = getExpandState();
        ActionBarConfig actionBarConfigConfig = this.mActionBarStrategy.config(this, getActionBarSpec(this.mContainerView, this.mActionView));
        if (actionBarView != null && actionBarConfigConfig != null) {
            if (!actionBarView.isUserSetExpandState() || actionBarConfigConfig.overrideUserExpandStateConfig) {
                if (!actionBarView.isResizable() || !actionBarConfigConfig.resizable) {
                    actionBarView.setExpandState(actionBarConfigConfig.expandState, false, true);
                }
                actionBarView.setResizable(actionBarConfigConfig.resizable);
            }
            if (!actionBarView.isUserSetEndActionMenuItemLimit() || actionBarConfigConfig.overrideUserEndMenuConfig) {
                actionBarView.setEndActionMenuItemLimit(actionBarConfigConfig.endMenuMaxItemCount);
            }
        }
        if (actionBarContextView != null && actionBarConfigConfig != null && (!actionBarContextView.isUserSetExpandState() || actionBarConfigConfig.overrideUserExpandStateConfig)) {
            if (!actionBarContextView.isResizable() || !actionBarConfigConfig.resizable) {
                actionBarContextView.setExpandState(actionBarConfigConfig.expandState, false, true);
            }
            actionBarContextView.setResizable(actionBarConfigConfig.resizable);
        }
        this.mCurrentExpandState = getExpandState();
        this.mCurrentResizable = isResizable();
        int i = this.mCurrentExpandState;
        if (i != 2 || expandState == i || this.mContentInset == null) {
            return;
        }
        Iterator it = this.mCoordinateOffsetViewSet.keySet().iterator();
        while (it.hasNext()) {
            this.mCoordinateOffsetViewSet.put((View) it.next(), Integer.valueOf(this.mContentInset.top));
        }
        Iterator it2 = this.mNestedContentInsetObserverSet.iterator();
        while (it2.hasNext()) {
            ((NestedContentInsetObserver) it2.next()).onContentInsetChanged(this.mContentInset);
        }
        ActionBarContainer actionBarContainer = this.mContainerView;
        if (actionBarContainer != null) {
            actionBarContainer.setActionBarBlurByNestedScrolled(false);
        }
    }

    public ActionBarCoordinateListener createActionBarCoordinateListener() {
        return new ActionBarCoordinateListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarImpl$$ExternalSyntheticLambda0
            @Override // miuix.appcompat.internal.app.widget.ActionBarCoordinateListener
            public final void onActionBarResizing(int i, float f, int i2, int i3) {
                ActionBarImpl.$r8$lambda$cXU2ox_wcuV6zykg8U20_3QSuww(this.f$0, i, f, i2, i3);
            }
        };
    }

    public final ActionMode createActionMode(ActionMode.Callback callback) {
        return callback instanceof SearchActionMode.Callback ? new SearchActionModeImpl(this.mContext, callback) : new EditActionModeImpl(this.mContext, callback);
    }

    public ActionModeView createActionModeView(ActionMode.Callback callback) {
        ActionModeView actionModeView;
        int i;
        if (callback instanceof SearchActionMode.Callback) {
            if (this.mSearchActionModeView == null) {
                SearchActionModeView searchActionModeViewCreateSearchActionModeView = createSearchActionModeView();
                this.mSearchActionModeView = searchActionModeViewCreateSearchActionModeView;
                searchActionModeViewCreateSearchActionModeView.setExtraPaddingPolicy(this.mExtraPaddingPolicy);
            }
            if (this.mOverlayLayout != this.mSearchActionModeView.getParent()) {
                this.mOverlayLayout.addView(this.mSearchActionModeView);
            }
            measureSearchActionModeView();
            if (!isActionBarHiddenBeforeSearchActionMode()) {
                this.mSearchActionModeView.addAnimationListener(this.mActionView);
            }
            actionModeView = this.mSearchActionModeView;
        } else {
            actionModeView = this.mContextView;
            if (actionModeView == null) {
                throw new IllegalStateException("not set windowSplitActionBar true in activity style!");
            }
        }
        if ((actionModeView instanceof ActionBarContextView) && (i = this.mMaxActionMenuItemCount) != -1) {
            ((ActionBarContextView) actionModeView).setActionMenuItemLimit(i);
        }
        return actionModeView;
    }

    public SearchActionModeView createSearchActionModeView() {
        SearchActionModeView searchActionModeView = (SearchActionModeView) LayoutInflater.from(getThemedContext()).inflate(R.layout.miuix_appcompat_search_action_mode_view, (ViewGroup) this.mOverlayLayout, false);
        searchActionModeView.setOverlayModeView(this.mOverlayLayout);
        searchActionModeView.setOnBackClickListener(new View.OnClickListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarImpl.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ActionBarImpl.this.mActionMode != null) {
                    ActionBarImpl.this.mActionMode.finish();
                }
            }
        });
        return searchActionModeView;
    }

    public final void doHide(boolean z, boolean z2, AnimState animState) {
        AnimState currentState;
        IStateStyle iStateStyle = this.mContainerAnim;
        if (iStateStyle == null || !this.mIsContainerAnimationRunning) {
            currentState = null;
        } else {
            currentState = iStateStyle.getCurrentState();
            this.mContainerAnim.cancel();
        }
        if ((isShowHideAnimationEnabled() || z) && z2) {
            this.mContainerAnim = startContainerViewAnimation(false, "HideActionBar", currentState, animState);
            return;
        }
        ActionBarContainer actionBarContainer = this.mContainerView;
        actionBarContainer.setTranslationY(-actionBarContainer.getHeight());
        this.mContainerView.setAlpha(0.0f);
        this.mActionBarHeightTotalGap = 0;
        this.mCurrentActionBarHeightGap = 0;
        this.mContainerView.setVisibility(8);
    }

    public final void doShow(boolean z, boolean z2, AnimState animState) {
        AnimState currentState;
        IStateStyle iStateStyle = this.mContainerAnim;
        if (iStateStyle == null || !this.mIsContainerAnimationRunning) {
            currentState = null;
        } else {
            currentState = iStateStyle.getCurrentState();
            this.mContainerAnim.cancel();
        }
        boolean z3 = (isShowHideAnimationEnabled() || z) && z2;
        if (this.mActionMode instanceof SearchActionMode) {
            this.mContainerView.setVisibility(this.mOverlayLayout.isInOverlayMode() ? 4 : 8);
        } else {
            this.mContainerView.setVisibility(0);
        }
        this.mContainerView.resetActionBarBlurConfigOnReshow();
        if (z3) {
            this.mContainerAnim = startContainerViewAnimation(true, "ShowActionBar", currentState, animState);
        } else {
            this.mContainerView.setTranslationY(0.0f);
            this.mContainerView.setAlpha(1.0f);
        }
    }

    public final void doUpdateTopOffsetForCoordinateView(View view, int i) {
        int top = view.getTop();
        int i2 = this.mCurrentActionBarHeightGap;
        if (top != i2 + i) {
            view.offsetTopAndBottom((Math.max(0, i2) + i) - top);
        }
    }

    public final View findOverflowingScrollable(View view, int i) {
        if (view != null && i <= 8 && view.getVisibility() == 0) {
            boolean z = view instanceof ViewGroup;
            if (z && (view instanceof ScrollingView)) {
                ViewGroup viewGroup = (ViewGroup) view;
                if (viewGroup.getChildCount() > 0) {
                    int i2 = 0;
                    for (int i3 = 0; i3 < viewGroup.getChildCount(); i3++) {
                        int bottom = viewGroup.getChildAt(i3).getBottom();
                        if (bottom > i2) {
                            i2 = bottom;
                        }
                    }
                    if (i2 > viewGroup.getHeight()) {
                        return viewGroup;
                    }
                }
            }
            if (z) {
                ViewGroup viewGroup2 = (ViewGroup) view;
                for (int i4 = 0; i4 < viewGroup2.getChildCount(); i4++) {
                    View viewFindOverflowingScrollable = findOverflowingScrollable(viewGroup2.getChildAt(i4), i + 1);
                    if (viewFindOverflowingScrollable != null) {
                        return viewFindOverflowingScrollable;
                    }
                }
            }
        }
        return null;
    }

    public final View findOverflowingScrollableChild() {
        ViewGroup viewGroup;
        ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
        if (actionBarOverlayLayout == null || (viewGroup = (ViewGroup) actionBarOverlayLayout.findViewById(android.R.id.content)) == null) {
            return null;
        }
        return findOverflowingScrollable(viewGroup, 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0010, code lost:
    
        r3 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final android.view.View findScrollableAncestor(android.view.View r3) {
        /*
            r2 = this;
            r0 = 0
            if (r3 != 0) goto L4
            return r0
        L4:
            android.view.ViewParent r3 = r3.getParent()
            boolean r1 = r3 instanceof android.view.View
            if (r1 == 0) goto Lf
            android.view.View r3 = (android.view.View) r3
            goto L10
        Lf:
            r3 = r0
        L10:
            if (r3 == 0) goto L31
            miuix.appcompat.internal.app.widget.ActionBarOverlayLayout r1 = r2.mOverlayLayout
            if (r3 == r1) goto L31
            r1 = -1
            boolean r1 = r3.canScrollVertically(r1)
            if (r1 != 0) goto L30
            r1 = 1
            boolean r1 = r3.canScrollVertically(r1)
            if (r1 == 0) goto L25
            goto L30
        L25:
            android.view.ViewParent r3 = r3.getParent()
            boolean r1 = r3 instanceof android.view.View
            if (r1 == 0) goto Lf
            android.view.View r3 = (android.view.View) r3
            goto L10
        L30:
            return r3
        L31:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: miuix.appcompat.internal.app.widget.ActionBarImpl.findScrollableAncestor(android.view.View):android.view.View");
    }

    public final ActionBarSpec getActionBarSpec(ActionBarContainer actionBarContainer, ActionBarView actionBarView) {
        ActionBarSpec actionBarSpec = new ActionBarSpec();
        actionBarSpec.deviceType = this.mOverlayLayout.getDeviceType();
        actionBarSpec.windowMode = this.mWindowMode;
        if (actionBarContainer != null && actionBarView != null) {
            float f = actionBarView.getContext().getResources().getDisplayMetrics().density;
            Point windowSize = EnvStateManager.getWindowSize(actionBarView.getContext());
            int i = windowSize.x;
            actionBarSpec.windowWidth = i;
            actionBarSpec.windowHeight = windowSize.y;
            actionBarSpec.windowWidthDp = MiuixUIUtils.px2dp(f, i);
            actionBarSpec.windowHeightDp = MiuixUIUtils.px2dp(f, actionBarSpec.windowHeight);
            int measuredWidth = actionBarContainer.getMeasuredWidth();
            actionBarSpec.actionBarWidth = measuredWidth;
            if (measuredWidth == 0) {
                actionBarSpec.actionBarWidth = this.mOverlayLayout.getMeasuredWidth();
            }
            actionBarSpec.actionBarWidthDp = MiuixUIUtils.px2dp(f, actionBarSpec.actionBarWidth);
            int measuredHeight = actionBarView.getMeasuredHeight();
            actionBarSpec.actionBarHeight = measuredHeight;
            actionBarSpec.actionBarHeightDp = MiuixUIUtils.px2dp(f, measuredHeight);
            actionBarSpec.isUserSetExpandState = actionBarView.isUserSetExpandState();
            actionBarSpec.expandState = actionBarView.getExpandState();
            actionBarSpec.resizable = actionBarView.isResizable();
            actionBarSpec.isUserSetEndActionMenuItemLimit = actionBarView.isUserSetEndActionMenuItemLimit();
            actionBarSpec.endActionMenuItemLimit = actionBarView.getEndActionMenuItemLimit();
        }
        Context context = this.mContext;
        if (context instanceof AppCompatActivity) {
            actionBarSpec.isInFloatingWindowMode = ((AppCompatActivity) context).isInFloatingWindowMode();
        }
        return actionBarSpec;
    }

    @Override // miuix.appcompat.app.ActionBar
    public View getActionBarView() {
        return this.mActionView;
    }

    public final int getBlurOptions() {
        return ((getDisplayOptions() & 32768) != 0 ? 32768 : 0) | ((getDisplayOptions() & 16384) != 0 ? 16384 : 0);
    }

    @Override // miuix.appcompat.app.ActionBar
    public CollapseTitle getCollapseTitle() {
        ActionBarView actionBarView = this.mActionView;
        if (actionBarView != null) {
            return actionBarView.getCollapseTitle();
        }
        return null;
    }

    public View getContentView() {
        ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
        if (actionBarOverlayLayout != null) {
            return actionBarOverlayLayout.findViewById(android.R.id.content);
        }
        return null;
    }

    public final Integer getCoordinateOffsetViewTopOffsetOrDefault(View view) {
        Integer num = (Integer) this.mCoordinateOffsetViewSet.get(view);
        return Integer.valueOf(Objects.equals(num, UNINITIALIZED_OFFSET) ? 0 : num.intValue());
    }

    @Override // androidx.appcompat.app.ActionBar
    public int getDisplayOptions() {
        return this.mActionView.getDisplayOptions();
    }

    public int getExpandState() {
        return this.mActionView.getExpandState();
    }

    @Override // miuix.appcompat.app.ActionBar
    public ExpandTitle getExpandTitle() {
        ActionBarView actionBarView = this.mActionView;
        if (actionBarView != null) {
            return actionBarView.getExpandTitle();
        }
        return null;
    }

    @Override // androidx.appcompat.app.ActionBar
    public int getHeight() {
        return this.mContainerView.getHeight();
    }

    public int getNavigationMode() {
        return this.mActionView.getNavigationMode();
    }

    @Override // androidx.appcompat.app.ActionBar
    public Context getThemedContext() {
        if (this.mThemedContext == null) {
            TypedValue typedValue = new TypedValue();
            this.mContext.getTheme().resolveAttribute(android.R.attr.actionBarWidgetTheme, typedValue, true);
            int i = typedValue.resourceId;
            if (i != 0) {
                this.mThemedContext = new ContextThemeWrapper(this.mContext, i);
            } else {
                this.mThemedContext = this.mContext;
            }
        }
        return this.mThemedContext;
    }

    public int getTopOffsetForCoordinateView(View view) {
        if (this.mCoordinateOffsetViewSet.containsKey(view)) {
            return getCoordinateOffsetViewTopOffsetOrDefault(view).intValue();
        }
        return -1;
    }

    public int getTopViewHeight() {
        ActionModeView actionModeView;
        if (this.mActionMode != null && (actionModeView = this.mActionModeView) != null) {
            return actionModeView.getViewHeight();
        }
        if (this.mActionView.isCollapsed()) {
            return 0;
        }
        return this.mActionView.getCollapsedHeight();
    }

    @Override // androidx.appcompat.app.ActionBar
    public void hide() {
        hide((AnimState) null);
    }

    public void hide(AnimState animState) {
        hide(true, animState);
    }

    public void hide(boolean z) {
        hide(z, null);
    }

    public void hide(boolean z, AnimState animState) {
        if (this.mHiddenByApp) {
            return;
        }
        this.mHiddenByApp = true;
        updateVisibility(false, z, animState);
    }

    public void hideForActionMode() {
        if (this.mShowingForMode) {
            this.mShowingForMode = false;
            this.mActionView.onActionModeEnd((getDisplayOptions() & 32768) != 0, this.mHiddenByAppBeforeSearchActionMode);
            updateVisibility(false);
            if (this.mHiddenByAppBeforeSearchActionMode) {
                hide(false);
            }
            this.mHiddenByAppBeforeSearchActionMode = false;
            if (this.mActionModeView instanceof SearchActionModeView) {
                setResizable(this.mCurrentResizable);
            } else {
                this.mContainerView.finishActionMode();
                this.mCurrentResizable = ((ActionBarContextView) this.mActionModeView).isResizable();
                this.mCurrentExpandState = ((ActionBarContextView) this.mActionModeView).getExpandState();
                setResizable(this.mCurrentResizable);
                this.mActionView.setExpandState(this.mCurrentExpandState);
            }
            this.mActionView.setImportantForAccessibility(this.mCurrentAccessibilityImportant);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void init(ViewGroup viewGroup) {
        int iResolveInt;
        ExtraPaddingPolicy extraPaddingPolicy;
        if (viewGroup == null) {
            return;
        }
        TypedValue typedValueResolveTypedValue = AttributeResolver.resolveTypedValue(this.mContext, R.attr.actionBarStrategy);
        if (typedValueResolveTypedValue != null) {
            try {
                this.mActionBarStrategy = (IActionBarStrategy) Class.forName(typedValueResolveTypedValue.string.toString()).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (Exception unused) {
            }
        }
        this.mWindowMode = EnvStateManager.getWindowInfo(this.mContext).windowMode;
        ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) viewGroup;
        this.mOverlayLayout = actionBarOverlayLayout;
        actionBarOverlayLayout.setActionBar(this);
        ActionBarView actionBarView = (ActionBarView) viewGroup.findViewById(R.id.action_bar);
        this.mActionView = actionBarView;
        if (actionBarView != null && (extraPaddingPolicy = this.mExtraPaddingPolicy) != null) {
            actionBarView.setExtraPaddingPolicy(extraPaddingPolicy);
        }
        this.mContextView = (ActionBarContextView) viewGroup.findViewById(R.id.action_context_bar);
        ActionBarContainer actionBarContainer = (ActionBarContainer) viewGroup.findViewById(R.id.action_bar_container);
        this.mContainerView = actionBarContainer;
        if (actionBarContainer != null) {
            actionBarContainer.setPostExpandRecheckScheduler(new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.postRecheckOverlayMaskState();
                }
            });
        }
        this.mSplitView = (ActionBarContainer) viewGroup.findViewById(R.id.split_action_bar);
        View viewFindViewById = viewGroup.findViewById(R.id.content_mask);
        this.mContentMask = viewFindViewById;
        if (viewFindViewById != null) {
            this.mContentMaskOnClickListener = new View.OnClickListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarImpl.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (ActionBarImpl.this.mSplitMenuView == null || !ActionBarImpl.this.mSplitMenuView.isOverflowMenuShowing()) {
                        return;
                    }
                    ActionBarImpl.this.mSplitMenuView.getPresenter().hideOverflowMenu(true);
                }
            };
        }
        ActionBarView actionBarView2 = this.mActionView;
        if (actionBarView2 == null && this.mContextView == null && this.mContainerView == null) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used with a compatible window decor layout");
        }
        this.mContextDisplayMode = actionBarView2.isSplitActionBar() ? 1 : 0;
        Object[] objArr = (this.mActionView.getDisplayOptions() & 4) != 0;
        if (objArr != false) {
            this.mDisplayHomeAsUpSet = true;
        }
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(this.mContext);
        setHomeButtonEnabled(actionBarPolicy.enableHomeButtonByDefault() || objArr == true);
        setHasEmbeddedTabs(actionBarPolicy.hasEmbeddedTabs());
        boolean z = MiuiBlurUtils.isEnable() && !LiteUtils.isCommonLiteStrategy();
        ActionBarContainer actionBarContainer2 = this.mContainerView;
        if (actionBarContainer2 != null) {
            actionBarContainer2.setSupportBlur(z);
        }
        ActionBarContainer actionBarContainer3 = this.mSplitView;
        if (actionBarContainer3 != null) {
            actionBarContainer3.setSupportBlur(z);
        }
        if (z && (iResolveInt = AttributeResolver.resolveInt(this.mContext, R.attr.bgBlurOptions, 0)) != 0) {
            int displayOptions = getDisplayOptions();
            if ((iResolveInt & 1) != 0) {
                displayOptions |= 32768;
            }
            if ((iResolveInt & 2) != 0) {
                displayOptions |= 16384;
            }
            setDisplayOptions(displayOptions);
        }
        if (this.mActionBarStrategy == null) {
            this.mActionBarStrategy = new CommonActionBarStrategy();
        }
        this.mStrategyPreDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarImpl.4
            public int lastWidth = 0;

            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                int measuredWidth = ActionBarImpl.this.mOverlayLayout.getMeasuredWidth();
                if (this.lastWidth == measuredWidth && !ActionBarImpl.this.mIsWindowInfoChanged) {
                    return true;
                }
                ActionBarImpl.this.mIsWindowInfoChanged = false;
                this.lastWidth = measuredWidth;
                int expandState = ActionBarImpl.this.getExpandState();
                ActionBarImpl actionBarImpl = ActionBarImpl.this;
                actionBarImpl.applyActionBarStrategy(actionBarImpl.mActionView, ActionBarImpl.this.mContextView);
                int expandState2 = ActionBarImpl.this.getExpandState();
                ActionBarImpl.this.mOverlayLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                ActionBarImpl.this.mStrategyPreDrawListener = null;
                ActionBarImpl.this.mOverlayLayout.requestLayout();
                ActionBarImpl.this.postRecheckOverlayMaskState();
                return expandState == expandState2;
            }
        };
        this.mOverlayLayout.getViewTreeObserver().addOnPreDrawListener(this.mStrategyPreDrawListener);
        AnonymousClass5 anonymousClass5 = new AnonymousClass5();
        this.mStrategyLayoutChangeListener = anonymousClass5;
        this.mOverlayLayout.addOnLayoutChangeListener(anonymousClass5);
        if (this.mOverlayLayout.findViewById(R.id.search_mode_stub) != null) {
            final WeakReference weakReference = new WeakReference(this);
            this.mOverlayLayout.post(new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarImpl$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() { // from class: miuix.appcompat.internal.app.widget.ActionBarImpl$$ExternalSyntheticLambda4
                        @Override // android.os.MessageQueue.IdleHandler
                        public final boolean queueIdle() {
                            return ActionBarImpl.$r8$lambda$I0xKt96dlj64UHqSakKhd66xoJ8(weakReference);
                        }
                    });
                }
            });
        }
    }

    public final boolean isActionBarHiddenBeforeSearchActionMode() {
        ActionBarContainer actionBarContainer;
        return this.mHiddenByApp || (actionBarContainer = this.mContainerView) == null || this.mActionView == null || actionBarContainer.getVisibility() != 0 || this.mActionView.getVisibility() != 0 || this.mActionView.getAlpha() < 0.01f;
    }

    @Override // miuix.appcompat.app.ActionBar
    public boolean isAdsorptionToNoOverlay() {
        return this.mAdsorptionToNoOverlay;
    }

    public final boolean isFocusedViewUnderActionBar(View view) {
        ActionBarContainer actionBarContainer;
        ActionBarView actionBarView;
        if (view != null && (actionBarContainer = this.mContainerView) != null) {
            int height = actionBarContainer.getHeight();
            if (height <= 0 && (actionBarView = this.mActionView) != null) {
                height = actionBarView.getHeight();
            }
            if (height <= 0) {
                return false;
            }
            this.mOverlayLayout.getLocationInWindow(this.mTmpLocationA);
            view.getLocationInWindow(this.mTmpLocationB);
            int i = this.mTmpLocationB[1] - this.mTmpLocationA[1];
            int height2 = view.getHeight() + i;
            int height3 = this.mOverlayLayout.getHeight();
            if (i < height || height2 > height3) {
                return true;
            }
        }
        return false;
    }

    public boolean isFragmentViewPagerMode() {
        return this.mViewPagerController != null;
    }

    public final boolean isRegisteredScrollViewScrolled() {
        for (Object obj : this.mNestedContentInsetObserverSet) {
            if ((obj instanceof View) && isViewOrDescendantScrolled((View) obj)) {
                return true;
            }
        }
        Iterator it = this.mCoordinateOffsetViewSet.keySet().iterator();
        while (it.hasNext()) {
            if (isViewOrDescendantScrolled((View) it.next())) {
                return true;
            }
        }
        return false;
    }

    public boolean isResizable() {
        return this.mActionView.isResizable();
    }

    public boolean isShowHideAnimationEnabled() {
        return this.mShowHideAnimationEnabled;
    }

    public boolean isShowing() {
        return this.mNowShowing;
    }

    public final void measureSearchActionModeView() {
        this.mSearchActionModeView.measure(ViewGroup.getChildMeasureSpec(this.mOverlayLayout.getMeasuredWidth(), 0, this.mSearchActionModeView.getLayoutParams().width), ViewGroup.getChildMeasureSpec(this.mOverlayLayout.getMeasuredHeight(), 0, this.mSearchActionModeView.getLayoutParams().height));
    }

    @Override // androidx.appcompat.app.ActionBar
    public void onConfigurationChanged(Configuration configuration) {
        this.mIsWindowInfoChanged = true;
        this.mWindowMode = EnvStateManager.getWindowInfo(this.mContext, configuration).windowMode;
        setHasEmbeddedTabs(ActionBarPolicy.get(this.mContext).hasEmbeddedTabs());
        SearchActionModeView searchActionModeView = this.mSearchActionModeView;
        if (searchActionModeView == null || searchActionModeView.isAttachedToWindow()) {
            return;
        }
        this.mSearchActionModeView.onConfigurationChanged(configuration);
    }

    public void onDestroy() {
        ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
        if (actionBarOverlayLayout == null) {
            return;
        }
        if (this.mStrategyPreDrawListener != null) {
            actionBarOverlayLayout.getViewTreeObserver().removeOnPreDrawListener(this.mStrategyPreDrawListener);
            this.mStrategyPreDrawListener = null;
        }
        if (this.mRecheckPreDrawListener != null) {
            this.mOverlayLayout.getViewTreeObserver().removeOnPreDrawListener(this.mRecheckPreDrawListener);
            this.mRecheckPreDrawListener = null;
        }
        View.OnLayoutChangeListener onLayoutChangeListener = this.mStrategyLayoutChangeListener;
        if (onLayoutChangeListener != null) {
            this.mOverlayLayout.removeOnLayoutChangeListener(onLayoutChangeListener);
            this.mStrategyLayoutChangeListener = null;
        }
    }

    public void onFloatingModeChanged(boolean z) {
        this.mContainerView.setIsMiuixFloating(z);
        SearchActionModeView searchActionModeView = this.mSearchActionModeView;
        if (searchActionModeView != null) {
            searchActionModeView.onFloatingModeChanged();
        }
    }

    public final void postRecheckOverlayMaskState() {
        ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
        if (actionBarOverlayLayout == null || this.mOverlayMaskRecheckPending) {
            return;
        }
        if (this.mRecheckPreDrawListener != null) {
            actionBarOverlayLayout.getViewTreeObserver().removeOnPreDrawListener(this.mRecheckPreDrawListener);
        }
        this.mOverlayMaskRecheckPending = true;
        this.mRecheckPreDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarImpl.6
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                ActionBarImpl.this.mOverlayLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                ActionBarImpl.this.mRecheckPreDrawListener = null;
                ActionBarImpl.this.mOverlayMaskRecheckPending = false;
                if (ActionBarImpl.this.mContainerView != null && ActionBarImpl.this.mOverlayLayout.isAttachedToWindow()) {
                    boolean zIsRegisteredScrollViewScrolled = ActionBarImpl.this.isRegisteredScrollViewScrolled();
                    if (!zIsRegisteredScrollViewScrolled) {
                        View viewFindFocus = ActionBarImpl.this.mOverlayLayout.findFocus();
                        View viewFindScrollableAncestor = ActionBarImpl.this.findScrollableAncestor(viewFindFocus);
                        boolean zIsFocusedViewUnderActionBar = viewFindScrollableAncestor != null && ActionBarImpl.isViewOrDescendantScrolled(viewFindScrollableAncestor);
                        if (!zIsFocusedViewUnderActionBar && viewFindScrollableAncestor != null && ActionBarImpl.this.mOverlayLayout.getImeInsetBottom() > 0) {
                            zIsFocusedViewUnderActionBar = ActionBarImpl.this.isFocusedViewUnderActionBar(viewFindFocus);
                        }
                        zIsRegisteredScrollViewScrolled = (zIsFocusedViewUnderActionBar || viewFindScrollableAncestor != null || ActionBarImpl.this.mOverlayLayout.getImeInsetBottom() <= 0 || ActionBarImpl.this.findOverflowingScrollableChild() == null) ? zIsFocusedViewUnderActionBar : ActionBarImpl.this.isFocusedViewUnderActionBar(viewFindFocus);
                    }
                    ActionBarImpl.this.mContainerView.recheckOverlayMaskState(zIsRegisteredScrollViewScrolled);
                    if (zIsRegisteredScrollViewScrolled) {
                        for (View view : ActionBarImpl.this.mCoordinateOffsetViewSet.keySet()) {
                            if (ActionBarImpl.this.getCoordinateOffsetViewTopOffsetOrDefault(view).intValue() > 0) {
                                ActionBarImpl.this.mCoordinateOffsetViewSet.put(view, 0);
                                ActionBarImpl.this.doUpdateTopOffsetForCoordinateView(view, 0);
                            }
                        }
                    }
                    if (ActionBarImpl.this.mRestoreCollapseOnScrollRecheckEnabled && ActionBarImpl.this.mActionView != null) {
                        ActionBarImpl.this.mActionView.restoreCollapseStateIfNeed(zIsRegisteredScrollViewScrolled);
                    }
                }
                return true;
            }
        };
        this.mOverlayLayout.getViewTreeObserver().addOnPreDrawListener(this.mRecheckPreDrawListener);
    }

    public void preCreateSearchActionModeView() {
        if (this.mSearchActionModeView == null) {
            SearchActionModeView searchActionModeViewCreateSearchActionModeView = createSearchActionModeView();
            this.mSearchActionModeView = searchActionModeViewCreateSearchActionModeView;
            searchActionModeViewCreateSearchActionModeView.setExtraPaddingPolicy(this.mExtraPaddingPolicy);
        }
        if (this.mOverlayLayout != this.mSearchActionModeView.getParent()) {
            this.mSearchActionModeView.setVisibility(8);
            this.mOverlayLayout.addView(this.mSearchActionModeView);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // miuix.appcompat.app.ActionBar
    public void registerCoordinateScrollView(View view) {
        if (view == 0) {
            Log.w("miuix-appcompat", "warning!! the view is null on registerCoordinateScrollView!!");
            return;
        }
        if (view instanceof NestedContentInsetObserver) {
            NestedContentInsetObserver nestedContentInsetObserver = (NestedContentInsetObserver) view;
            this.mNestedContentInsetObserverSet.add(nestedContentInsetObserver);
            Rect rect = this.mContentInset;
            if (rect != null) {
                nestedContentInsetObserver.onContentInsetChanged(rect);
            }
        } else {
            HashMap map = this.mCoordinateOffsetViewSet;
            Rect rect2 = this.mContentInset;
            map.put(view, Integer.valueOf(rect2 != null ? rect2.top : UNINITIALIZED_OFFSET.intValue()));
            Rect rect3 = this.mContentInset;
            if (rect3 != null) {
                this.mCoordinateOffsetViewSet.put(view, Integer.valueOf(rect3.top));
                doUpdateTopOffsetForCoordinateView(view, this.mContentInset.top);
            }
        }
        if (this.mContainerView.getActionBarCoordinateListener() == null) {
            ActionBarCoordinateListener actionBarCoordinateListenerCreateActionBarCoordinateListener = createActionBarCoordinateListener();
            this.mOwnCoordinateListener = actionBarCoordinateListenerCreateActionBarCoordinateListener;
            this.mContainerView.setActionBarCoordinateListener(actionBarCoordinateListenerCreateActionBarCoordinateListener);
        }
    }

    public void selectTab(androidx.appcompat.app.ActionBar.Tab tab) {
        selectTab(tab, true);
    }

    public void selectTab(androidx.appcompat.app.ActionBar.Tab tab, boolean z) {
        if (this.isSelectingTab) {
            this.isSelectingTab = false;
            return;
        }
        this.isSelectingTab = true;
        Context context = this.mContext;
        if ((context instanceof Activity) && (((Activity) context).isDestroyed() || ((Activity) this.mContext).isFinishing())) {
            return;
        }
        if (getNavigationMode() != 2) {
            this.mSavedTabPosition = tab != null ? tab.getPosition() : -1;
            return;
        }
        FragmentTransaction fragmentTransactionDisallowAddToBackStack = this.mFragmentManager.beginTransaction().disallowAddToBackStack();
        TabImpl tabImpl = this.mSelectedTab;
        if (tabImpl != tab) {
            this.mTabScrollView.setTabSelected(tab != null ? tab.getPosition() : -1, z);
            this.mExpandTabScrollView.setTabSelected(tab != null ? tab.getPosition() : -1, z);
            this.mSecondaryTabScrollView.setTabSelected(tab != null ? tab.getPosition() : -1);
            this.mSecondaryExpandTabScrollView.setTabSelected(tab != null ? tab.getPosition() : -1);
            TabImpl tabImpl2 = this.mSelectedTab;
            if (tabImpl2 != null) {
                tabImpl2.getCallback().onTabUnselected(this.mSelectedTab, fragmentTransactionDisallowAddToBackStack);
            }
            TabImpl tabImpl3 = (TabImpl) tab;
            this.mSelectedTab = tabImpl3;
            if (tabImpl3 != null) {
                tabImpl3.mWithAnim = z;
                tabImpl3.getCallback().onTabSelected(this.mSelectedTab, fragmentTransactionDisallowAddToBackStack);
            }
        } else if (tabImpl != null) {
            tabImpl.getCallback().onTabReselected(this.mSelectedTab, fragmentTransactionDisallowAddToBackStack);
            this.mTabScrollView.animateToTab(tab.getPosition());
            this.mExpandTabScrollView.animateToTab(tab.getPosition());
            this.mSecondaryTabScrollView.animateToTab(tab.getPosition());
            this.mSecondaryExpandTabScrollView.animateToTab(tab.getPosition());
        }
        if (!fragmentTransactionDisallowAddToBackStack.isEmpty()) {
            fragmentTransactionDisallowAddToBackStack.commit();
        }
        this.isSelectingTab = false;
    }

    @Override // miuix.appcompat.app.ActionBar
    public void setActionBarStrategy(IActionBarStrategy iActionBarStrategy) {
        this.mActionBarStrategy = iActionBarStrategy;
        this.mOverlayLayout.post(new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarImpl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ActionBarImpl actionBarImpl = this.f$0;
                actionBarImpl.applyActionBarStrategy(actionBarImpl.mActionView, actionBarImpl.mContextView);
            }
        });
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setBackgroundDrawable(Drawable drawable) {
        this.mContainerView.setPrimaryBackground(drawable);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setDisplayHomeAsUpEnabled(boolean z) {
        int blurOptions = getBlurOptions();
        setDisplayOptions((z ? 4 : 0) | blurOptions, blurOptions | 4);
    }

    public void setDisplayOptions(int i) {
        if ((i & 4) != 0) {
            this.mDisplayHomeAsUpSet = true;
        }
        this.mActionView.setDisplayOptions(i);
        int displayOptions = this.mActionView.getDisplayOptions();
        ActionBarContainer actionBarContainer = this.mContainerView;
        if (actionBarContainer != null) {
            actionBarContainer.setEnableBlur((displayOptions & 32768) != 0);
        }
        ActionBarContainer actionBarContainer2 = this.mSplitView;
        if (actionBarContainer2 != null) {
            actionBarContainer2.setEnableBlur((i & 16384) != 0);
        }
    }

    public void setDisplayOptions(int i, int i2) {
        int displayOptions = this.mActionView.getDisplayOptions();
        if ((i2 & 4) != 0) {
            this.mDisplayHomeAsUpSet = true;
        }
        this.mActionView.setDisplayOptions((i & i2) | ((~i2) & displayOptions));
        int displayOptions2 = this.mActionView.getDisplayOptions();
        ActionBarContainer actionBarContainer = this.mContainerView;
        if (actionBarContainer != null) {
            actionBarContainer.setEnableBlur((32768 & displayOptions2) != 0);
        }
        ActionBarContainer actionBarContainer2 = this.mSplitView;
        if (actionBarContainer2 != null) {
            actionBarContainer2.setEnableBlur((displayOptions2 & 16384) != 0);
        }
    }

    @Override // miuix.appcompat.app.ActionBar
    public void setEndView(View view) {
        this.mActionView.setEndView(view);
    }

    @Override // miuix.appcompat.app.ActionBar
    public void setExpandState(int i) {
        this.mActionView.setExpandStateByUser(i);
        this.mActionView.setExpandState(i);
        ActionBarContextView actionBarContextView = this.mContextView;
        if (actionBarContextView != null) {
            actionBarContextView.setExpandStateByUser(i);
            this.mContextView.setExpandState(i);
        }
    }

    public void setExtraPaddingPolicy(ExtraPaddingPolicy extraPaddingPolicy) {
        if (this.mExtraPaddingPolicy != extraPaddingPolicy) {
            this.mExtraPaddingPolicy = extraPaddingPolicy;
            ActionBarView actionBarView = this.mActionView;
            if (actionBarView != null) {
                actionBarView.setExtraPaddingPolicy(extraPaddingPolicy);
            }
            SearchActionModeView searchActionModeView = this.mSearchActionModeView;
            if (searchActionModeView != null) {
                searchActionModeView.setExtraPaddingPolicy(this.mExtraPaddingPolicy);
            }
        }
    }

    public void setHomeButtonEnabled(boolean z) {
        this.mActionView.setHomeButtonEnabled(z);
    }

    @Override // miuix.appcompat.app.ActionBar
    public void setMaskColor(int i) {
        ActionBarContainer actionBarContainer = this.mContainerView;
        if (actionBarContainer != null) {
            actionBarContainer.setMaskColor(i);
        }
    }

    @Override // miuix.appcompat.app.ActionBar
    public void setResizable(boolean z) {
        this.mActionView.setResizable(z);
    }

    public void setSelectedNavigationItem(int i) {
        int navigationMode = this.mActionView.getNavigationMode();
        if (navigationMode == 1) {
            this.mActionView.setDropdownSelectedPosition(i);
        } else {
            if (navigationMode != 2) {
                throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
            }
            selectTab((androidx.appcompat.app.ActionBar.Tab) this.mTabs.get(i));
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setShowHideAnimationEnabled(boolean z) {
        this.mShowHideAnimationEnabled = z;
        if (z) {
            return;
        }
        if (isShowing()) {
            doShow(false);
        } else {
            doHide(false);
        }
    }

    @Override // miuix.appcompat.app.ActionBar
    public void setStartView(View view) {
        this.mActionView.setStartView(view);
    }

    public void showForActionMode() {
        if (this.mShowingForMode) {
            return;
        }
        this.mHiddenByAppBeforeSearchActionMode = (this.mActionModeView instanceof SearchActionModeView) && isActionBarHiddenBeforeSearchActionMode();
        this.mShowingForMode = true;
        updateVisibility(false);
        if (this.mActionModeView instanceof SearchActionModeView) {
            ActionBarView actionBarView = this.mActionView;
            if (actionBarView.mInnerExpandState == 3) {
                int height = actionBarView.getHeight();
                int collapsedHeight = this.mActionView.getCollapsedHeight();
                if (height <= collapsedHeight + ((this.mActionView.getExpandedHeight() - collapsedHeight) / 2)) {
                    this.mActionView.setExpandState(0);
                } else {
                    this.mActionView.setExpandState(2);
                }
            }
            this.mCurrentExpandState = getExpandState();
            this.mCurrentResizable = isResizable();
            setResizable(false);
        } else {
            this.mCurrentExpandState = getExpandState();
            this.mCurrentResizable = isResizable();
            this.mContainerView.startActionMode();
            ((ActionBarContextView) this.mActionModeView).setExpandState(this.mCurrentExpandState);
            ((ActionBarContextView) this.mActionModeView).setResizable(this.mCurrentResizable);
        }
        this.mCurrentAccessibilityImportant = this.mActionView.getImportantForAccessibility();
        this.mActionView.setImportantForAccessibility(4);
        this.mActionView.onActionModeStart(this.mActionModeView instanceof SearchActionModeView, (getDisplayOptions() & 32768) != 0);
    }

    public ActionMode startActionMode(ActionMode.Callback callback) {
        Rect baseInnerInsets;
        SearchActionModeView searchActionModeView;
        EditText searchInput;
        ActionMode actionMode = this.mActionMode;
        if (actionMode != null) {
            actionMode.finish();
        }
        if ((callback instanceof SearchActionMode.Callback) && (searchActionModeView = this.mSearchActionModeView) != null && (searchInput = searchActionModeView.getSearchInput()) != null && searchInput.isAttachedToWindow()) {
            this.mSearchActionModeView.setAlpha(0.0f);
            this.mSearchActionModeView.setVisibility(0);
            searchInput.setFocusable(true);
            searchInput.setFocusableInTouchMode(true);
            searchInput.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService("input_method");
            inputMethodManager.viewClicked(searchInput);
            inputMethodManager.showSoftInput(searchInput, 0);
        }
        ActionMode actionModeCreateActionMode = createActionMode(callback);
        ActionModeView actionModeView = this.mActionModeView;
        if (((actionModeView instanceof SearchActionModeView) && (actionModeCreateActionMode instanceof SearchActionModeImpl)) || ((actionModeView instanceof ActionBarContextView) && (actionModeCreateActionMode instanceof EditActionModeImpl))) {
            actionModeView.closeMode();
            this.mActionModeView.killMode();
        }
        ActionModeView actionModeViewCreateActionModeView = createActionModeView(callback);
        this.mActionModeView = actionModeViewCreateActionModeView;
        if (actionModeViewCreateActionModeView == null) {
            throw new IllegalStateException("not set windowSplitActionBar true in activity style!");
        }
        if (!(actionModeCreateActionMode instanceof ActionModeImpl)) {
            return null;
        }
        ActionModeImpl actionModeImpl = (ActionModeImpl) actionModeCreateActionMode;
        actionModeImpl.setActionModeView(actionModeViewCreateActionModeView);
        if ((actionModeImpl instanceof SearchActionModeImpl) && (baseInnerInsets = this.mOverlayLayout.getBaseInnerInsets()) != null) {
            ((SearchActionModeImpl) actionModeImpl).setPendingInsets(baseInnerInsets);
        }
        actionModeImpl.setActionModeCallback(this.mActionModeCallback);
        if (!actionModeImpl.dispatchOnCreate()) {
            return null;
        }
        actionModeCreateActionMode.invalidate();
        this.mActionModeView.initForMode(actionModeCreateActionMode);
        this.mActionMode = actionModeCreateActionMode;
        animateToMode(true);
        ActionBarContainer actionBarContainer = this.mSplitView;
        if (actionBarContainer != null && this.mContextDisplayMode == 1 && actionBarContainer.getVisibility() != 0) {
            this.mSplitView.setVisibility(0);
        }
        ActionModeView actionModeView2 = this.mActionModeView;
        if (actionModeView2 instanceof ActionBarContextView) {
            ((ActionBarContextView) actionModeView2).sendAccessibilityEvent(32);
        }
        return actionModeCreateActionMode;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x00a6 A[PHI: r13
  0x00a6: PHI (r13v9 miuix.animation.controller.AnimState) = 
  (r13v5 miuix.animation.controller.AnimState)
  (r13v5 miuix.animation.controller.AnimState)
  (r13v15 miuix.animation.controller.AnimState)
  (r13v15 miuix.animation.controller.AnimState)
 binds: [B:19:0x00d9, B:20:0x00db, B:11:0x008f, B:12:0x0091] A[DONT_GENERATE, DONT_INLINE]] */
    public final IStateStyle startContainerViewAnimation(boolean z, String str, AnimState animState, AnimState animState2) {
        AnimState animStateAdd;
        AnimState animStateAdd2;
        int height = this.mContainerView.getHeight();
        if (height == 0) {
            int childMeasureSpec = ViewGroup.getChildMeasureSpec(this.mOverlayLayout.getMeasuredWidth(), 0, this.mOverlayLayout.getLayoutParams().width);
            int childMeasureSpec2 = ViewGroup.getChildMeasureSpec(this.mOverlayLayout.getMeasuredHeight(), 0, this.mOverlayLayout.getLayoutParams().height);
            this.mContainerView.measure(childMeasureSpec, childMeasureSpec2);
            applyActionBarStrategy(this.mActionView, this.mContextView);
            this.mContainerView.measure(childMeasureSpec, childMeasureSpec2);
            height = this.mContainerView.getMeasuredHeight();
        }
        int i = -height;
        this.mTargetTranslationY = i;
        AnimConfig animConfig = new AnimConfig();
        animConfig.addListeners(this.mContainerViewAnimationListener);
        if (z) {
            animConfig.setEase(EaseManager.getStyle(-2, 0.9f, 0.25f));
            animConfig.addListeners(new ViewShowTransitionListener(this.mContainerView, this));
            animStateAdd = animState2 == null ? new AnimState(str).add(ViewProperty.TRANSLATION_Y, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE).add(ViewProperty.ALPHA, 1.0d) : animState2;
            if (animState == null && animState2 == null) {
                animStateAdd2 = new AnimState(str).add(ViewProperty.TRANSLATION_Y, i).add(ViewProperty.ALPHA, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
            } else {
                animStateAdd2 = animState;
            }
        } else {
            animConfig.setEase(EaseManager.getStyle(-2, 1.0f, 0.35f));
            animConfig.addListeners(new ViewHideTransitionListener(this.mContainerView, this));
            animStateAdd = animState2 == null ? new AnimState(str).add(ViewProperty.TRANSLATION_Y, i).add(ViewProperty.ALPHA, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) : animState2;
            if (animState == null && animState2 == null) {
                animStateAdd2 = new AnimState(str).add(ViewProperty.TRANSLATION_Y, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE).add(ViewProperty.ALPHA, 1.0d);
            } else {
                animStateAdd2 = animState;
            }
        }
        IStateStyle iStateStyleState = Folme.useAt(this.mContainerView).state();
        if (animStateAdd2 != null) {
            animStateAdd2.setTag(str);
            iStateStyleState = iStateStyleState.setTo(animStateAdd2);
        }
        iStateStyleState.to(animStateAdd, animConfig);
        this.mIsContainerAnimationRunning = true;
        return iStateStyleState;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // miuix.appcompat.app.ActionBar
    public void unregisterCoordinateScrollView(View view) {
        if (view instanceof NestedContentInsetObserver) {
            this.mNestedContentInsetObserverSet.remove((NestedContentInsetObserver) view);
        } else {
            this.mCoordinateOffsetViewSet.remove(view);
        }
        if (this.mCoordinateOffsetViewSet.size() == 0 && this.mNestedContentInsetObserverSet.size() == 0 && this.mOwnCoordinateListener != null && this.mContainerView.getActionBarCoordinateListener() == this.mOwnCoordinateListener) {
            this.mContainerView.setActionBarCoordinateListener(null);
            this.mOwnCoordinateListener = null;
        }
    }

    public void updateContentInsetForNestedObserver(Rect rect) {
        this.mContentInset = rect;
        int i = rect.top;
        int i2 = i - this.mContentInsetTop;
        this.mContentInsetTop = i;
        Iterator it = this.mNestedContentInsetObserverSet.iterator();
        while (it.hasNext()) {
            ((NestedContentInsetObserver) it.next()).onContentInsetChanged(rect);
        }
        for (View view : this.mCoordinateOffsetViewSet.keySet()) {
            Integer num = (Integer) this.mCoordinateOffsetViewSet.get(view);
            if (num != null && i2 != 0) {
                if (num.equals(UNINITIALIZED_OFFSET)) {
                    num = 0;
                } else if (num.intValue() == 0) {
                }
                int iMax = Math.max(0, num.intValue() + i2);
                this.mCoordinateOffsetViewSet.put(view, Integer.valueOf(iMax));
                doUpdateTopOffsetForCoordinateView(view, iMax);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void updateCoordinateOffsetView() {
        if (this.mCoordinateOffsetViewSet.size() == 0 && this.mNestedContentInsetObserverSet.size() == 0) {
            if (this.mOwnCoordinateListener == null || this.mContainerView.getActionBarCoordinateListener() != this.mOwnCoordinateListener) {
                return;
            }
            this.mContainerView.setActionBarCoordinateListener(null);
            this.mOwnCoordinateListener = null;
            return;
        }
        for (View view : this.mCoordinateOffsetViewSet.keySet()) {
            doUpdateTopOffsetForCoordinateView(view, getCoordinateOffsetViewTopOffsetOrDefault(view).intValue());
        }
        Iterator it = this.mNestedContentInsetObserverSet.iterator();
        while (it.hasNext()) {
            View view2 = (View) ((NestedContentInsetObserver) it.next());
            if (view2 instanceof NestedCoordinatorObserver) {
                ((NestedCoordinatorObserver) view2).updateCoordinatorHeightGapInfo(this.mCurrentActionBarHeightGap, this.mActionBarHeightTotalGap);
            }
            doUpdateTopOffsetForCoordinateView(view2, 0);
        }
    }

    public int updateTopOffsetOnNestedPreScroll(View view, int i) {
        if (!this.mCoordinateOffsetViewSet.containsKey(view)) {
            return 0;
        }
        Integer coordinateOffsetViewTopOffsetOrDefault = getCoordinateOffsetViewTopOffsetOrDefault(view);
        if (coordinateOffsetViewTopOffsetOrDefault.intValue() <= i) {
            return 0;
        }
        this.mCoordinateOffsetViewSet.put(view, Integer.valueOf(i));
        doUpdateTopOffsetForCoordinateView(view, i);
        return coordinateOffsetViewTopOffsetOrDefault.intValue() - i;
    }

    public int updateTopOffsetOnNestedScroll(View view, int i) {
        int i2 = 0;
        for (View view2 : this.mCoordinateOffsetViewSet.keySet()) {
            int iIntValue = getCoordinateOffsetViewTopOffsetOrDefault(view2).intValue();
            int i3 = iIntValue - i;
            Rect rect = this.mContentInset;
            int iMin = Math.min(i3, rect == null ? 0 : rect.top);
            if (iIntValue < iMin) {
                this.mCoordinateOffsetViewSet.put(view2, Integer.valueOf(iMin));
                doUpdateTopOffsetForCoordinateView(view2, iMin);
                if (view == view2) {
                    i2 = iIntValue - iMin;
                }
            }
        }
        return i2;
    }

    public void updateTopOffsetOnPostScroll(View view, int i) {
        for (View view2 : this.mCoordinateOffsetViewSet.keySet()) {
            if (view == view2) {
                int iIntValue = getCoordinateOffsetViewTopOffsetOrDefault(view2).intValue();
                Rect rect = this.mContentInset;
                int iMin = Math.min(i, rect == null ? 0 : rect.top);
                if (iIntValue != iMin) {
                    this.mCoordinateOffsetViewSet.put(view2, Integer.valueOf(iMin));
                    doUpdateTopOffsetForCoordinateView(view2, iMin);
                }
            }
        }
    }

    public final void updateVisibility(boolean z, boolean z2, AnimState animState) {
        if (checkShowingFlags(this.mHiddenByApp, this.mHiddenBySystem, this.mShowingForMode)) {
            if (this.mNowShowing) {
                return;
            }
            this.mNowShowing = true;
            doShow(z, z2, animState);
            return;
        }
        if (this.mNowShowing) {
            this.mNowShowing = false;
            doHide(z, z2, animState);
        }
    }
}
