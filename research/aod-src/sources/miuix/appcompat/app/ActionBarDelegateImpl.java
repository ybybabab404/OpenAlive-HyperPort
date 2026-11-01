package miuix.appcompat.app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import androidx.activity.OnBackPressedCallback;
import androidx.core.view.ScrollingView;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.LifecycleOwner;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import miuix.appcompat.R;
import miuix.appcompat.internal.app.widget.ActionBarContainer;
import miuix.appcompat.internal.app.widget.ActionBarContextView;
import miuix.appcompat.internal.app.widget.ActionBarImpl;
import miuix.appcompat.internal.app.widget.ActionBarOverlayLayout;
import miuix.appcompat.internal.app.widget.ActionBarView;
import miuix.appcompat.internal.view.menu.ImmersionMenuPopupWindow;
import miuix.appcompat.internal.view.menu.ImmersionMenuPopupWindowImpl;
import miuix.appcompat.internal.view.menu.MenuBuilder;
import miuix.appcompat.internal.view.menu.MenuPresenter;
import miuix.container.ExtraPaddingObserver;
import miuix.container.ExtraPaddingPolicy;
import miuix.container.ExtraPaddingProcessor;
import miuix.core.util.MiuixUIUtils;
import miuix.core.util.variable.WindowWrapper;
import miuix.internal.util.AttributeResolver;
import miuix.internal.util.ViewUtils;
import miuix.os.DeviceHelper;
import miuix.theme.token.ContainerToken;

/* JADX INFO: loaded from: classes2.dex */
public abstract class ActionBarDelegateImpl implements ActionBarDelegate, ExtraPaddingProcessor, ExtraPaddingObserver, MenuPresenter.Callback, MenuBuilder.Callback {
    public ActionBar mActionBar;
    public ActionBarView mActionBarView;
    public ActionMode mActionMode;
    public final AppCompatActivity mActivity;
    public MenuBuilder mChildMenu;
    public Rect mContentInset;
    public int mDeviceType;
    public boolean mEnableChildMenu;
    public boolean mEndActionMenuEnabled;
    public boolean mExtraPaddingApplyToContentEnable;
    public boolean mExtraPaddingEnable;
    public boolean mExtraPaddingInitEnable;
    public ExtraPaddingPolicy mExtraPaddingPolicy;
    public boolean mFeatureIndeterminateProgress;
    public boolean mFeatureProgress;
    public GroupButtonsConfig mGroupButtonsConfig;
    public boolean mHasActionBar;
    public boolean mHyperActionMenuEnabled;
    public boolean mHyperSplitMenuEnabled;
    public MenuBuilder mImmersionMenu;
    public boolean mImmersionMenuEnabled;
    public MenuBuilder mMenu;
    public MenuInflater mMenuInflater;
    public ImmersionMenuPopupWindow mMenuPopupWindow;
    public OnBackPressedCallback mOnBackPressedCallback;
    public MenuBuilder mOriginMenu;
    public boolean mOverlayActionBar;
    public boolean mSubDecorInstalled;
    public boolean mUserExtraPaddingPolicy;
    public View mViewWithContentInset;
    public ViewUtils.RelativePadding mViewWithContentInsetInitPadding;
    public int mTranslucentStatus = 0;
    public boolean mHasAddSplitActionBar = false;
    public int mExtraHorizontalPadding = 0;
    public List mExtraPaddingObserver = null;
    public boolean mIsDelegateAnimRunning = false;
    public boolean mIsFloatingPanelMode = false;
    public int mFloatingPanelMarginStart = -1;
    public int mFloatingPanelMarginTop = -1;
    public int mFloatingPanelMarginEnd = -1;
    public int mFloatingPanelMarginBottom = -1;
    public int mFloatingPanelCornerSide = 0;
    public boolean mFloatingPanelDynamicCornerEnabled = true;

    public ActionBarDelegateImpl(AppCompatActivity appCompatActivity) {
        this.mActivity = appCompatActivity;
        this.mDeviceType = DeviceHelper.detectType(appCompatActivity);
    }

    public void addContentMask(ActionBarOverlayLayout actionBarOverlayLayout) {
        if (actionBarOverlayLayout != null) {
            ViewStub viewStub = (ViewStub) actionBarOverlayLayout.findViewById(R.id.content_mask_vs);
            actionBarOverlayLayout.setContentMask(viewStub != null ? viewStub.inflate() : actionBarOverlayLayout.findViewById(R.id.content_mask));
        }
    }

    public void addExtraPaddingObserver(ExtraPaddingObserver extraPaddingObserver) {
        if (this.mExtraPaddingObserver == null) {
            this.mExtraPaddingObserver = new CopyOnWriteArrayList();
        }
        if (this.mExtraPaddingObserver.contains(extraPaddingObserver)) {
            this.mExtraPaddingObserver.add(extraPaddingObserver);
            extraPaddingObserver.setExtraHorizontalPadding(this.mExtraHorizontalPadding);
        }
    }

    public void addGroupButtons(GroupButtonsConfig groupButtonsConfig) {
        this.mGroupButtonsConfig = groupButtonsConfig;
    }

    public void addSplitActionBar(boolean z, boolean z2, ActionBarOverlayLayout actionBarOverlayLayout) {
        if (this.mHasAddSplitActionBar) {
            return;
        }
        this.mHasAddSplitActionBar = true;
        ViewStub viewStub = (ViewStub) actionBarOverlayLayout.findViewById(R.id.split_action_bar_vs);
        ActionBarContainer actionBarContainer = viewStub != null ? (ActionBarContainer) viewStub.inflate() : (ActionBarContainer) actionBarOverlayLayout.findViewById(R.id.split_action_bar);
        if (actionBarContainer != null) {
            this.mActionBarView.setSplitView(actionBarContainer);
            this.mActionBarView.setSplitActionBar(z);
            this.mActionBarView.setSplitWhenNarrow(z2);
            actionBarOverlayLayout.setSplitActionBarView(actionBarContainer);
            addContentMask(actionBarOverlayLayout);
        }
        ActionBarContainer actionBarContainer2 = (ActionBarContainer) actionBarOverlayLayout.findViewById(R.id.action_bar_container);
        ViewStub viewStub2 = (ViewStub) actionBarOverlayLayout.findViewById(R.id.action_context_bar_vs);
        ActionBarContextView actionBarContextView = viewStub2 != null ? (ActionBarContextView) viewStub2.inflate() : (ActionBarContextView) actionBarOverlayLayout.findViewById(R.id.action_context_bar);
        if (actionBarContextView != null) {
            actionBarContainer2.setActionBarContextView(actionBarContextView);
            actionBarOverlayLayout.setActionBarContextView(actionBarContextView);
            if (actionBarContainer != null) {
                actionBarContextView.setSplitView(actionBarContainer);
                actionBarContextView.setSplitActionBar(z);
                actionBarContextView.setSplitWhenNarrow(z2);
            }
        }
    }

    public void bindViewWithContentInset(View view) {
        this.mViewWithContentInset = view;
        ViewUtils.RelativePadding relativePadding = new ViewUtils.RelativePadding(ViewCompat.getPaddingStart(view), this.mViewWithContentInset.getPaddingTop(), ViewCompat.getPaddingEnd(this.mViewWithContentInset), this.mViewWithContentInset.getPaddingBottom());
        this.mViewWithContentInsetInitPadding = relativePadding;
        if (view instanceof ViewGroup) {
            relativePadding.clipToPadding = ((ViewGroup) view).getClipToPadding();
        }
    }

    public MenuBuilder createMenu() {
        MenuBuilder menuBuilder = new MenuBuilder(getActionBarThemedContext());
        menuBuilder.setCallback(this);
        this.mOriginMenu = menuBuilder;
        return menuBuilder;
    }

    public void dismissImmersionMenu(boolean z) {
        ImmersionMenuPopupWindow immersionMenuPopupWindow = this.mMenuPopupWindow;
        if (immersionMenuPopupWindow != null) {
            immersionMenuPopupWindow.dismiss(z);
        }
    }

    public ActionBar getActionBar() {
        if (!hasActionBar()) {
            this.mActionBar = null;
        } else if (this.mActionBar == null) {
            this.mActionBar = createActionBar();
        }
        return this.mActionBar;
    }

    public final Context getActionBarThemedContext() {
        AppCompatActivity appCompatActivity = this.mActivity;
        ActionBar actionBar = getActionBar();
        return actionBar != null ? actionBar.getThemedContext() : appCompatActivity;
    }

    public AppCompatActivity getActivity() {
        return this.mActivity;
    }

    public int getBottomMenuMode() {
        return 2;
    }

    public final int getCornerInsetMargin(int i, int i2, int i3, int i4) {
        int i5 = this.mFloatingPanelCornerSide;
        if (i5 == 1) {
            return i2;
        }
        if (i5 != 2) {
            return i5 != 3 ? i : i4;
        }
        return i3;
    }

    public int getExtraHorizontalPadding() {
        return this.mExtraHorizontalPadding;
    }

    public int getExtraHorizontalPaddingLevel() {
        return 0;
    }

    public ExtraPaddingPolicy getExtraPaddingPolicy() {
        return this.mExtraPaddingPolicy;
    }

    public final int getFloatingPanelMarginFromAttr(Context context, int i, int i2) {
        try {
            TypedValue typedValueResolveTypedValue = AttributeResolver.resolveTypedValue(context, i);
            if (typedValueResolveTypedValue != null && typedValueResolveTypedValue.type == 5) {
                return typedValueResolveTypedValue.resourceId > 0 ? context.getResources().getDimensionPixelSize(typedValueResolveTypedValue.resourceId) : TypedValue.complexToDimensionPixelSize(typedValueResolveTypedValue.data, context.getResources().getDisplayMetrics());
            }
        } catch (Exception e) {
            Log.w("ActionBarDelegate", "Failed to resolve floating panel margin attr", e);
        }
        return i2;
    }

    public abstract LifecycleOwner getLifecycleOwner();

    public MenuInflater getMenuInflater() {
        if (this.mMenuInflater == null) {
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                this.mMenuInflater = new MenuInflater(actionBar.getThemedContext());
            } else {
                this.mMenuInflater = new MenuInflater(this.mActivity);
            }
        }
        return this.mMenuInflater;
    }

    public abstract Context getThemedContext();

    public int getTranslucentStatus() {
        return this.mTranslucentStatus;
    }

    public final String getUiOptionsFromMetadata() {
        try {
            Bundle bundle = this.mActivity.getPackageManager().getActivityInfo(this.mActivity.getComponentName(), 128).metaData;
            if (bundle != null) {
                return bundle.getString("android.support.UI_OPTIONS");
            }
            return null;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("ActionBarDelegate", "getUiOptionsFromMetadata: Activity '" + this.mActivity.getClass().getSimpleName() + "' not in manifest");
            return null;
        }
    }

    public abstract View getView();

    public boolean hasActionBar() {
        return this.mHasActionBar || this.mOverlayActionBar;
    }

    public void hideEndOverflowMenu() {
        ActionBarView actionBarView = this.mActionBarView;
        if (actionBarView != null) {
            actionBarView.hideEndOverflowMenu();
        }
    }

    public void hideOverflowMenu() {
        ActionBarView actionBarView = this.mActionBarView;
        if (actionBarView != null) {
            actionBarView.hideOverflowMenu();
        }
    }

    public void initExtraPaddingPolicy() {
        ExtraPaddingPolicy extraPaddingPolicyCreateDefault = ExtraPaddingPolicy.Builder.createDefault(this.mDeviceType, ContainerToken.PADDING_BASE_DP, ContainerToken.PADDING_HORIZONTAL_COMMON);
        this.mExtraPaddingPolicy = extraPaddingPolicyCreateDefault;
        if (extraPaddingPolicyCreateDefault != null) {
            extraPaddingPolicyCreateDefault.setEnable(this.mExtraPaddingEnable);
        }
    }

    public boolean isEndActionMenuEnabled() {
        return this.mEndActionMenuEnabled;
    }

    @Override // miuix.container.ExtraPaddingProcessor
    public boolean isExtraHorizontalPaddingEnable() {
        return this.mExtraPaddingEnable;
    }

    public boolean isExtraPaddingApplyToContentEnable() {
        return this.mExtraPaddingApplyToContentEnable;
    }

    public boolean isImmersionMenuShowing() {
        ImmersionMenuPopupWindow immersionMenuPopupWindow = this.mMenuPopupWindow;
        if (immersionMenuPopupWindow instanceof ImmersionMenuPopupWindowImpl) {
            return immersionMenuPopupWindow.isShowing();
        }
        return false;
    }

    public void onActionModeFinished(ActionMode actionMode) {
        this.mActionMode = null;
        updateOnBackPressedCallbackState(false);
    }

    public void onActionModeStarted(ActionMode actionMode) {
        this.mActionMode = actionMode;
        updateOnBackPressedCallbackState(true);
    }

    @Override // miuix.appcompat.internal.view.menu.MenuPresenter.Callback
    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        this.mActivity.closeOptionsMenu();
    }

    public void onConfigurationChanged(Configuration configuration) {
        ActionBarImpl actionBarImpl;
        if (this.mHasActionBar && this.mSubDecorInstalled && (actionBarImpl = (ActionBarImpl) getActionBar()) != null) {
            actionBarImpl.onConfigurationChanged(configuration);
        }
    }

    @Override // miuix.appcompat.app.IContentInsetState
    public void onContentInsetChanged(Rect rect) {
        this.mContentInset = rect;
    }

    public void onCreate(Bundle bundle) {
    }

    public abstract boolean onCreateImmersionMenu(MenuBuilder menuBuilder);

    public void onDestroy() {
        ActionBarImpl actionBarImpl;
        ActionMode actionMode = this.mActionMode;
        if (actionMode != null) {
            actionMode.finish();
        }
        if (this.mHasActionBar && this.mSubDecorInstalled && (actionBarImpl = (ActionBarImpl) getActionBar()) != null) {
            actionBarImpl.onDestroy();
        }
    }

    public abstract /* synthetic */ boolean onMenuItemSelected(int i, MenuItem menuItem);

    @Override // miuix.appcompat.internal.view.menu.MenuBuilder.Callback
    public void onMenuModeChange(MenuBuilder menuBuilder) {
        reopenMenu(menuBuilder, true);
    }

    @Override // miuix.appcompat.internal.view.menu.MenuPresenter.Callback
    public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
        return false;
    }

    public void onPostResume() {
        ActionBarImpl actionBarImpl;
        if (this.mHasActionBar && this.mSubDecorInstalled && (actionBarImpl = (ActionBarImpl) getActionBar()) != null) {
            actionBarImpl.setShowHideAnimationEnabled(true);
        }
    }

    public abstract boolean onPrepareImmersionMenu(MenuBuilder menuBuilder);

    public void onProcessBindViewWithContentInset(Rect rect) {
        if (this.mViewWithContentInset == null) {
            return;
        }
        ViewUtils.RelativePadding relativePadding = new ViewUtils.RelativePadding(this.mViewWithContentInsetInitPadding);
        boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this.mViewWithContentInset);
        relativePadding.start += zIsLayoutRtl ? rect.right : rect.left;
        relativePadding.top += rect.top;
        relativePadding.end += zIsLayoutRtl ? rect.left : rect.right;
        View view = this.mViewWithContentInset;
        if ((view instanceof ViewGroup) && (view instanceof ScrollingView)) {
            relativePadding.applyToScrollingView((ViewGroup) view);
        } else {
            relativePadding.applyToView(view);
        }
    }

    public void onStop() {
        ActionBarImpl actionBarImpl;
        dismissImmersionMenu(false);
        if (this.mHasActionBar && this.mSubDecorInstalled && (actionBarImpl = (ActionBarImpl) getActionBar()) != null) {
            actionBarImpl.setShowHideAnimationEnabled(false);
        }
    }

    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return null;
    }

    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
        if (i == 0) {
            return onWindowStartingActionMode(callback);
        }
        return null;
    }

    public void registerCoordinateScrollView(View view) {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.registerCoordinateScrollView(view);
        }
    }

    public void removeExtraPaddingObserver(ExtraPaddingObserver extraPaddingObserver) {
        List list = this.mExtraPaddingObserver;
        if (list != null) {
            list.remove(extraPaddingObserver);
        }
    }

    public void reopenMenu(MenuBuilder menuBuilder, boolean z) {
        ActionBarView actionBarView = this.mActionBarView;
        if (actionBarView == null || !actionBarView.isOverflowReserved()) {
            menuBuilder.close();
            return;
        }
        if (this.mActionBarView.isOverflowMenuShowing() && z) {
            this.mActionBarView.hideOverflowMenu();
        } else if (this.mActionBarView.getVisibility() == 0) {
            this.mActionBarView.showOverflowMenu();
        }
    }

    public boolean requestWindowFeature(int i) {
        if (i == 2) {
            this.mFeatureProgress = true;
            return true;
        }
        if (i == 5) {
            this.mFeatureIndeterminateProgress = true;
            return true;
        }
        if (i == 8) {
            this.mHasActionBar = true;
            return true;
        }
        if (i != 9) {
            return this.mActivity.requestWindowFeature(i);
        }
        this.mOverlayActionBar = true;
        return true;
    }

    public void setEndActionMenuEnabled(boolean z) {
        setEndActionMenuEnabled(z, true);
    }

    public void setEndActionMenuEnabled(boolean z, boolean z2) {
        setEndActionMenuEnabled(z, false, z2);
    }

    public void setEndActionMenuEnabled(boolean z, boolean z2, boolean z3) {
        this.mEndActionMenuEnabled = z;
        this.mHyperActionMenuEnabled = z2;
        if (this.mSubDecorInstalled && this.mHasActionBar) {
            this.mActionBarView.setEndActionMenuEnable(z);
            this.mActionBarView.setHyperActionMenuEnable(z2);
            this.mActionBarView.setHyperSplitMenuEnabled(this.mHyperSplitMenuEnabled);
            if (z3) {
                invalidateOptionsMenu();
            } else {
                this.mActivity.getWindow().getDecorView().post(new Runnable() { // from class: miuix.appcompat.app.ActionBarDelegateImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.invalidateOptionsMenu();
                    }
                });
            }
        }
    }

    @Override // miuix.container.ExtraPaddingObserver
    public boolean setExtraHorizontalPadding(int i) {
        if (this.mExtraHorizontalPadding == i) {
            return false;
        }
        this.mExtraHorizontalPadding = i;
        return true;
    }

    public void setExtraHorizontalPaddingEnable(boolean z) {
        this.mExtraPaddingEnable = z;
        ExtraPaddingPolicy extraPaddingPolicy = this.mExtraPaddingPolicy;
        if (extraPaddingPolicy != null) {
            extraPaddingPolicy.setEnable(z);
        }
    }

    public void setExtraHorizontalPaddingInitEnable(boolean z) {
        this.mExtraPaddingInitEnable = z;
    }

    public void setExtraHorizontalPaddingLevel(int i) {
    }

    public void setExtraPaddingApplyToContentEnable(boolean z) {
        this.mExtraPaddingApplyToContentEnable = z;
    }

    public void setExtraPaddingPolicy(ExtraPaddingPolicy extraPaddingPolicy) {
        if (extraPaddingPolicy != null) {
            this.mUserExtraPaddingPolicy = true;
            this.mExtraPaddingPolicy = extraPaddingPolicy;
        } else if (this.mUserExtraPaddingPolicy && this.mExtraPaddingPolicy != null) {
            this.mUserExtraPaddingPolicy = false;
            initExtraPaddingPolicy();
        }
        ExtraPaddingPolicy extraPaddingPolicy2 = this.mExtraPaddingPolicy;
        if (extraPaddingPolicy2 != null) {
            extraPaddingPolicy2.setEnable(this.mExtraPaddingEnable);
        }
    }

    public void setHyperActionMenuEnabled(boolean z) {
        setEndActionMenuEnabled(true, z, true);
    }

    public void setHyperSplitMenuEnabled(boolean z) {
        this.mHyperSplitMenuEnabled = z;
        ActionBarView actionBarView = this.mActionBarView;
        if (actionBarView != null) {
            actionBarView.setHyperSplitMenuEnabled(z);
        }
    }

    public void setImmersionMenuEnabled(boolean z) {
        this.mImmersionMenuEnabled = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setMenu(MenuBuilder menuBuilder) {
        if (menuBuilder == this.mMenu) {
            return;
        }
        this.mMenu = menuBuilder;
        ActionBarView actionBarView = this.mActionBarView;
        if (actionBarView != null) {
            actionBarView.setMenu(menuBuilder, this);
            if (this.mActionBarView.isEndActionMenuEnable()) {
                onPanelViewAdded(0, null, this.mMenu, this.mActionBarView.getEndMenu());
            }
        }
    }

    public void setTranslucentStatus(int i) {
        int integer = this.mActivity.getResources().getInteger(R.integer.window_translucent_status);
        if (integer >= 0 && integer <= 2) {
            i = integer;
        }
        if (this.mTranslucentStatus == i || !WindowWrapper.setTranslucentStatus(this.mActivity.getWindow(), i)) {
            return;
        }
        this.mTranslucentStatus = i;
    }

    public void showEndOverflowMenu() {
        ActionBarView actionBarView = this.mActionBarView;
        if (actionBarView != null) {
            actionBarView.showEndOverflowMenu();
        }
    }

    public void showImmersionMenu() {
        View viewFindViewById;
        ImmersionMenuPopupWindow immersionMenuPopupWindow = this.mMenuPopupWindow;
        if (immersionMenuPopupWindow instanceof ImmersionMenuPopupWindowImpl) {
            View lastAnchor = ((ImmersionMenuPopupWindowImpl) immersionMenuPopupWindow).getLastAnchor();
            ViewGroup lastParent = ((ImmersionMenuPopupWindowImpl) this.mMenuPopupWindow).getLastParent();
            if (lastAnchor != null) {
                showImmersionMenu(lastAnchor, lastParent);
                return;
            }
        }
        ActionBarView actionBarView = this.mActionBarView;
        if (actionBarView == null || (viewFindViewById = actionBarView.findViewById(R.id.more)) == null) {
            throw new IllegalStateException("Can't find anchor view in actionbar or any other anchor view used before. Do you use default actionbar and immersion menu is enabled?");
        }
        showImmersionMenu(viewFindViewById, this.mActionBarView);
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public void showImmersionMenu(View view, ViewGroup viewGroup) {
        if (!this.mImmersionMenuEnabled) {
            Log.w("ActionBarDelegate", "Try to show immersion menu when immersion menu disabled");
            return;
        }
        if (view == null) {
            throw new IllegalArgumentException("You must specify a valid anchor view");
        }
        if (this.mImmersionMenu == null) {
            MenuBuilder menuBuilderCreateMenu = createMenu();
            this.mImmersionMenu = menuBuilderCreateMenu;
            onCreateImmersionMenu(menuBuilderCreateMenu);
        }
        if (onPrepareImmersionMenu(this.mImmersionMenu) && this.mImmersionMenu.hasVisibleItems()) {
            ImmersionMenuPopupWindow immersionMenuPopupWindow = this.mMenuPopupWindow;
            if (immersionMenuPopupWindow == null) {
                ImmersionMenuPopupWindowImpl immersionMenuPopupWindowImpl = new ImmersionMenuPopupWindowImpl(this, this.mImmersionMenu, getView());
                immersionMenuPopupWindowImpl.setDropDownGravity(49);
                immersionMenuPopupWindowImpl.setHorizontalOffset(0);
                immersionMenuPopupWindowImpl.setVerticalOffset(0);
                this.mMenuPopupWindow = immersionMenuPopupWindowImpl;
            } else {
                immersionMenuPopupWindow.update(this.mImmersionMenu);
            }
            if (this.mMenuPopupWindow.isShowing()) {
                return;
            }
            this.mMenuPopupWindow.show(view, null);
        }
    }

    public void showOverflowMenu() {
        ActionBarView actionBarView = this.mActionBarView;
        if (actionBarView != null) {
            actionBarView.showOverflowMenu();
        }
    }

    public void unregisterCoordinateScrollView(View view) {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.unregisterCoordinateScrollView(view);
        }
    }

    public void updateActionBarOverlayLayoutParams(ActionBarOverlayLayout actionBarOverlayLayout, boolean z, boolean z2) {
        actionBarOverlayLayout.setFloatingPanelMode(z);
        if (!z || actionBarOverlayLayout.isFloatingWindowMode()) {
            actionBarOverlayLayout.setFloatingPanelUserMarginTop(-1);
            actionBarOverlayLayout.setFloatingPanelUserMarginBottom(-1);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) actionBarOverlayLayout.getLayoutParams();
            marginLayoutParams.setMarginStart(0);
            marginLayoutParams.topMargin = 0;
            marginLayoutParams.setMarginEnd(0);
            marginLayoutParams.bottomMargin = 0;
            actionBarOverlayLayout.setLayoutParams(marginLayoutParams);
            actionBarOverlayLayout.setRadius(0.0f);
            return;
        }
        Context context = actionBarOverlayLayout.getContext();
        int dimensionPixelOffset = context.getResources().getDimensionPixelOffset(R.dimen.miuix_appcompat_floating_panel_default_margin);
        int floatingPanelMarginFromAttr = this.mFloatingPanelMarginStart;
        if (floatingPanelMarginFromAttr < 0) {
            floatingPanelMarginFromAttr = getFloatingPanelMarginFromAttr(context, R.attr.floatingPanelMarginStart, dimensionPixelOffset);
        }
        int floatingPanelMarginFromAttr2 = this.mFloatingPanelMarginTop;
        if (floatingPanelMarginFromAttr2 < 0) {
            floatingPanelMarginFromAttr2 = getFloatingPanelMarginFromAttr(context, R.attr.floatingPanelMarginTop, dimensionPixelOffset);
        }
        int floatingPanelMarginFromAttr3 = this.mFloatingPanelMarginEnd;
        if (floatingPanelMarginFromAttr3 < 0) {
            floatingPanelMarginFromAttr3 = getFloatingPanelMarginFromAttr(context, R.attr.floatingPanelMarginEnd, dimensionPixelOffset);
        }
        int floatingPanelMarginFromAttr4 = this.mFloatingPanelMarginBottom;
        if (floatingPanelMarginFromAttr4 < 0) {
            floatingPanelMarginFromAttr4 = getFloatingPanelMarginFromAttr(context, R.attr.floatingPanelMarginBottom, dimensionPixelOffset);
        }
        float dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_floating_panel_default_radius);
        float fDp2px = MiuixUIUtils.dp2px(context, 48.0f);
        int cornerInsetMargin = getCornerInsetMargin(floatingPanelMarginFromAttr, floatingPanelMarginFromAttr2, floatingPanelMarginFromAttr3, floatingPanelMarginFromAttr4);
        if (this.mFloatingPanelDynamicCornerEnabled) {
            dimensionPixelSize = MiuixUIUtils.getWidgetCornerRadius(context, cornerInsetMargin, 3, dimensionPixelSize, fDp2px);
        }
        actionBarOverlayLayout.setFloatingPanelUserMarginTop(floatingPanelMarginFromAttr2);
        actionBarOverlayLayout.setFloatingPanelUserMarginBottom(floatingPanelMarginFromAttr4);
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) actionBarOverlayLayout.getLayoutParams();
        marginLayoutParams2.setMarginStart(floatingPanelMarginFromAttr);
        marginLayoutParams2.setMarginEnd(floatingPanelMarginFromAttr3);
        marginLayoutParams2.bottomMargin = floatingPanelMarginFromAttr4;
        actionBarOverlayLayout.setLayoutParams(marginLayoutParams2);
        actionBarOverlayLayout.setRadius(dimensionPixelSize);
        actionBarOverlayLayout.requestApplyInsets();
    }

    public final void updateOnBackPressedCallbackState(boolean z) {
        OnBackPressedCallback onBackPressedCallback = this.mOnBackPressedCallback;
        if (onBackPressedCallback != null) {
            onBackPressedCallback.setEnabled(z);
        } else {
            this.mOnBackPressedCallback = new OnBackPressedCallback(z) { // from class: miuix.appcompat.app.ActionBarDelegateImpl.1
                @Override // androidx.activity.OnBackPressedCallback
                public void handleOnBackPressed() {
                    ActionMode actionMode;
                    ActionBarDelegateImpl actionBarDelegateImpl = ActionBarDelegateImpl.this;
                    if (actionBarDelegateImpl.mIsDelegateAnimRunning || (actionMode = actionBarDelegateImpl.mActionMode) == null) {
                        return;
                    }
                    actionMode.finish();
                }
            };
            this.mActivity.getOnBackPressedDispatcher().addCallback(getLifecycleOwner(), this.mOnBackPressedCallback);
        }
    }
}
