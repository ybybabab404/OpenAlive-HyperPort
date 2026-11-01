package miuix.appcompat.app;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentFactory;
import java.util.List;
import miuix.appcompat.R;
import miuix.appcompat.internal.util.LayoutUIUtils;
import miuix.autodensity.AutoDensityConfig;
import miuix.core.util.MiuixUIUtils;
import miuix.responsive.interfaces.IResponsive;
import miuix.responsive.map.ScreenSpec;

/* JADX INFO: loaded from: classes2.dex */
public class Fragment extends androidx.fragment.app.Fragment implements IFragment, IContentInsetState, IResponsive<Fragment>, ShortcutsCallback {
    public FragmentDelegate mDelegate;
    public int mInputViewLimitTextSizeDp;
    public boolean mHasMenu = true;
    public boolean mMenuVisible = true;

    public static /* synthetic */ void $r8$lambda$aYK4pWauq3DjeUn3ruFCVL3pKNE(Fragment fragment, View view) {
        if (fragment.isAdded()) {
            LayoutUIUtils.resetSearchModeStubInputTextSize(fragment.getResources(), view, fragment.mInputViewLimitTextSizeDp);
        }
    }

    @Override // miuix.appcompat.app.IFragment
    public boolean acceptExtraPaddingFromParent() {
        return this.mDelegate.acceptExtraPaddingFromParent();
    }

    @Override // miuix.appcompat.app.IFragment
    public void checkThemeLegality() {
    }

    @Override // miuix.responsive.interfaces.IResponsive
    public void dispatchResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean z) {
        this.mDelegate.dispatchResponsiveLayout(configuration, screenSpec, z);
    }

    @Override // miuix.appcompat.app.IFragment
    public ActionBar getActionBar() {
        return this.mDelegate.getActionBar();
    }

    @Override // miuix.appcompat.app.IContentInsetState
    public Rect getContentInset() {
        return this.mDelegate.getContentInset();
    }

    @Override // miuix.responsive.interfaces.IResponsive
    public Fragment getResponsiveSubject() {
        return this;
    }

    @Override // miuix.appcompat.app.IFragment
    public Context getThemedContext() {
        return this.mDelegate.getThemedContext();
    }

    @Override // androidx.fragment.app.Fragment
    public View getView() {
        FragmentDelegate fragmentDelegate = this.mDelegate;
        if (fragmentDelegate == null) {
            return null;
        }
        return fragmentDelegate.getView();
    }

    @Override // miuix.appcompat.app.IFragment
    public boolean hasActionBar() {
        FragmentDelegate fragmentDelegate = this.mDelegate;
        if (fragmentDelegate == null) {
            return false;
        }
        return fragmentDelegate.hasActionBar();
    }

    @Override // miuix.container.ExtraPaddingProcessor
    public boolean isExtraHorizontalPaddingEnable() {
        return this.mDelegate.isExtraHorizontalPaddingEnable();
    }

    public boolean isResponsiveEnabled() {
        return false;
    }

    @Override // miuix.appcompat.app.IFragment
    public void onActionModeFinished(ActionMode actionMode) {
        this.mDelegate.onActionModeFinished(actionMode);
    }

    @Override // miuix.appcompat.app.IFragment
    public void onActionModeStarted(ActionMode actionMode) {
        this.mDelegate.onActionModeStarted(actionMode);
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        AutoDensityConfig.updateDensity(context);
        FragmentFactory fragmentFactory = getParentFragmentManager().getFragmentFactory();
        if (fragmentFactory instanceof DelegateFragmentFactory) {
            this.mDelegate = ((DelegateFragmentFactory) fragmentFactory).createFragmentDelegate(this);
        } else {
            this.mDelegate = new FragmentDelegate(this);
        }
        this.mDelegate.setResponsiveEnabled(isResponsiveEnabled());
        this.mInputViewLimitTextSizeDp = MiuixUIUtils.isTallFontLang(getContext()) ? 16 : 27;
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        AutoDensityConfig.updateDensityByConfig(getContext(), configuration);
        this.mDelegate.onConfigurationChanged(configuration);
    }

    @Override // miuix.appcompat.app.IContentInsetState
    public void onContentInsetChanged(Rect rect) {
        this.mDelegate.onContentInsetChanged(rect);
        onProcessBindViewWithContentInset(rect);
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mDelegate.onCreate(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public Animator onCreateAnimator(int i, boolean z, int i2) {
        return this.mDelegate.onCreateAnimator(i, z, i2);
    }

    @Override // miuix.appcompat.app.IFragment
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override // miuix.appcompat.app.IFragment
    public boolean onCreatePanelMenu(int i, Menu menu) {
        if (i == 0 && this.mHasMenu && this.mMenuVisible && !isHidden() && isAdded()) {
            return onCreateOptionsMenu(menu);
        }
        return false;
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.mDelegate.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.mDelegate.dismissImmersionMenu(false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.mDelegate.onDestroyView();
    }

    @Override // miuix.container.ExtraPaddingObserver
    public void onExtraPaddingChanged(int i) {
        this.mDelegate.onExtraPaddingChanged(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // miuix.appcompat.app.ShortcutsCallback
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        for (androidx.fragment.app.Fragment fragment : getChildFragmentManager().getFragments()) {
            if (fragment.isAdded() && !fragment.isHidden() && fragment.isResumed() && (fragment instanceof ShortcutsCallback) && ((ShortcutsCallback) fragment).onGenericMotionEvent(motionEvent)) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onHiddenChanged(boolean z) {
        FragmentDelegate fragmentDelegate;
        super.onHiddenChanged(z);
        if (!z && (fragmentDelegate = this.mDelegate) != null) {
            fragmentDelegate.invalidateOptionsMenu();
        }
        onVisibilityChanged(!z);
    }

    @Override // miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // miuix.appcompat.app.ShortcutsCallback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        for (androidx.fragment.app.Fragment fragment : getChildFragmentManager().getFragments()) {
            if (fragment.isAdded() && !fragment.isHidden() && fragment.isResumed() && (fragment instanceof ShortcutsCallback) && ((ShortcutsCallback) fragment).onKeyDown(i, keyEvent)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // miuix.appcompat.app.ShortcutsCallback
    public boolean onKeyEvent(KeyEvent keyEvent) {
        for (androidx.fragment.app.Fragment fragment : getChildFragmentManager().getFragments()) {
            if (fragment.isAdded() && !fragment.isHidden() && fragment.isResumed() && (fragment instanceof ShortcutsCallback) && ((ShortcutsCallback) fragment).onKeyEvent(keyEvent)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // miuix.appcompat.app.ShortcutsCallback
    public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
        for (androidx.fragment.app.Fragment fragment : getChildFragmentManager().getFragments()) {
            if (fragment.isAdded() && !fragment.isHidden() && fragment.isResumed() && (fragment instanceof ShortcutsCallback) && ((ShortcutsCallback) fragment).onKeyLongPress(i, keyEvent)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // miuix.appcompat.app.ShortcutsCallback
    public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
        for (androidx.fragment.app.Fragment fragment : getChildFragmentManager().getFragments()) {
            if (fragment.isAdded() && !fragment.isHidden() && fragment.isResumed() && (fragment instanceof ShortcutsCallback) && ((ShortcutsCallback) fragment).onKeyMultiple(i, i2, keyEvent)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // miuix.appcompat.app.ShortcutsCallback
    public boolean onKeyShortcutEvent(KeyEvent keyEvent) {
        for (androidx.fragment.app.Fragment fragment : getChildFragmentManager().getFragments()) {
            if (fragment.isAdded() && !fragment.isHidden() && fragment.isResumed() && (fragment instanceof ShortcutsCallback) && ((ShortcutsCallback) fragment).onKeyShortcutEvent(keyEvent)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // miuix.appcompat.app.ShortcutsCallback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        for (androidx.fragment.app.Fragment fragment : getChildFragmentManager().getFragments()) {
            if (fragment.isAdded() && !fragment.isHidden() && fragment.isResumed() && (fragment instanceof ShortcutsCallback) && ((ShortcutsCallback) fragment).onKeyUp(i, keyEvent)) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332 || getActionBar() == null || (getActionBar().getDisplayOptions() & 4) == 0) {
            return false;
        }
        FragmentActivity activity = getActivity();
        if (activity.getParent() == null ? activity.onNavigateUp() : activity.getParent().onNavigateUpFromChild(activity)) {
            return true;
        }
        getActivity().getOnBackPressedDispatcher().onBackPressed();
        return true;
    }

    @Override // miuix.appcompat.app.IFragment
    public void onOptionsMenuViewAdded(Menu menu, Menu menu2) {
    }

    @Override // miuix.appcompat.app.IFragment
    public void onPanelClosed(int i, Menu menu) {
    }

    @Override // miuix.appcompat.app.IFragment
    public void onPreparePanel(int i, View view, Menu menu) {
        if (i == 0 && this.mHasMenu && this.mMenuVisible && !isHidden() && isAdded()) {
            onPrepareOptionsMenu(menu);
        }
    }

    public void onProcessBindViewWithContentInset(Rect rect) {
        this.mDelegate.onProcessBindViewWithContentInset(rect);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // miuix.appcompat.app.ShortcutsCallback
    public void onProvideKeyboardShortcuts(List list, Menu menu, int i) {
        for (androidx.fragment.app.Fragment fragment : getChildFragmentManager().getFragments()) {
            if (fragment.isAdded() && !fragment.isHidden() && fragment.isResumed() && (fragment instanceof ShortcutsCallback)) {
                ((ShortcutsCallback) fragment).onProvideKeyboardShortcuts(list, menu, i);
            }
        }
    }

    @Override // miuix.responsive.interfaces.IResponsive
    public void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean z) {
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        FragmentDelegate fragmentDelegate = this.mDelegate;
        if (fragmentDelegate != null) {
            fragmentDelegate.onPostResume();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.mDelegate.onStop();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // miuix.appcompat.app.ShortcutsCallback
    public boolean onTouchEvent(MotionEvent motionEvent) {
        for (androidx.fragment.app.Fragment fragment : getChildFragmentManager().getFragments()) {
            if (fragment.isAdded() && !fragment.isHidden() && fragment.isResumed() && (fragment instanceof ShortcutsCallback) && ((ShortcutsCallback) fragment).onTouchEvent(motionEvent)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // miuix.appcompat.app.ShortcutsCallback
    public boolean onTrackballEvent(MotionEvent motionEvent) {
        for (androidx.fragment.app.Fragment fragment : getChildFragmentManager().getFragments()) {
            if (fragment.isAdded() && !fragment.isHidden() && fragment.isResumed() && (fragment instanceof ShortcutsCallback) && ((ShortcutsCallback) fragment).onTrackballEvent(motionEvent)) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        final View viewFindViewById;
        this.mDelegate.onViewCreated(view, bundle);
        Rect contentInset = this.mDelegate.getContentInset();
        if (contentInset != null && (contentInset.top != 0 || contentInset.bottom != 0 || contentInset.left != 0 || contentInset.right != 0)) {
            onContentInsetChanged(contentInset);
        }
        if (view == null || !isAdded() || (viewFindViewById = view.findViewById(R.id.search_mode_stub)) == null) {
            return;
        }
        viewFindViewById.post(new Runnable() { // from class: miuix.appcompat.app.Fragment$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Fragment.$r8$lambda$aYK4pWauq3DjeUn3ruFCVL3pKNE(this.f$0, viewFindViewById);
            }
        });
    }

    @Override // miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle bundle) {
    }

    public void onVisibilityChanged(boolean z) {
    }

    @Override // miuix.container.ExtraPaddingObserver
    public boolean setExtraHorizontalPadding(int i) {
        return this.mDelegate.setExtraHorizontalPadding(i);
    }

    @Override // androidx.fragment.app.Fragment
    public void setHasOptionsMenu(boolean z) {
        super.setHasOptionsMenu(z);
        if (this.mHasMenu != z) {
            this.mHasMenu = z;
            if (!z || this.mDelegate == null || isHidden() || !isAdded()) {
                return;
            }
            this.mDelegate.invalidateOptionsMenu();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void setMenuVisibility(boolean z) {
        FragmentDelegate fragmentDelegate;
        super.setMenuVisibility(z);
        if (this.mMenuVisible != z) {
            this.mMenuVisible = z;
            if (isHidden() || !isAdded() || (fragmentDelegate = this.mDelegate) == null || !fragmentDelegate.hasActionBar()) {
                return;
            }
            this.mDelegate.invalidateOptionsMenu();
        }
    }
}
