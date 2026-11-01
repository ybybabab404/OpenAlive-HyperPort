package miuix.appcompat.app;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentFactory;
import miuix.appcompat.R;
import miuix.appcompat.internal.app.widget.ActionBarOverlayLayout;
import miuix.responsive.interfaces.IResponsive;
import miuix.responsive.map.ScreenSpec;

/* JADX INFO: loaded from: classes2.dex */
public class ListFragment extends androidx.fragment.app.ListFragment implements IFragment, IResponsive<androidx.fragment.app.Fragment> {
    public FragmentDelegate mDelegate;
    public boolean mHasMenu = true;
    public boolean mMenuVisible = true;

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
    public androidx.fragment.app.Fragment getResponsiveSubject() {
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
    public final void onActionModeFinished(ActionMode actionMode) {
        this.mDelegate.onActionModeFinished(actionMode);
    }

    @Override // miuix.appcompat.app.IFragment
    public final void onActionModeStarted(ActionMode actionMode) {
        this.mDelegate.onActionModeStarted(actionMode);
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        FragmentFactory fragmentFactory = getParentFragmentManager().getFragmentFactory();
        if (fragmentFactory instanceof DelegateFragmentFactory) {
            this.mDelegate = ((DelegateFragmentFactory) fragmentFactory).createFragmentDelegate(this);
        } else {
            this.mDelegate = new FragmentDelegate(this);
        }
        this.mDelegate.setResponsiveEnabled(isResponsiveEnabled());
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
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

    @Override // androidx.fragment.app.ListFragment, androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        boolean z;
        View viewOnCreateView = this.mDelegate.onCreateView(layoutInflater, viewGroup, bundle);
        if (viewOnCreateView instanceof ActionBarOverlayLayout) {
            boolean zEquals = "splitActionBarWhenNarrow".equals(this.mDelegate.getUiOptionsFromMetadata());
            if (zEquals) {
                z = getActivity().getResources().getBoolean(R.bool.abc_split_action_bar_is_narrow);
            } else {
                TypedArray typedArrayObtainStyledAttributes = getActivity().obtainStyledAttributes(R.styleable.Window);
                boolean z2 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.Window_windowSplitActionBar, false);
                typedArrayObtainStyledAttributes.recycle();
                z = z2;
            }
            this.mDelegate.addSplitActionBar(z, zEquals, (ActionBarOverlayLayout) viewOnCreateView);
        }
        return viewOnCreateView;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.mDelegate.dismissImmersionMenu(false);
    }

    @Override // miuix.container.ExtraPaddingObserver
    public void onExtraPaddingChanged(int i) {
        this.mDelegate.onExtraPaddingChanged(i);
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
        return super.onCreateView(layoutInflater, viewGroup, bundle);
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

    @Override // miuix.responsive.interfaces.IResponsive
    public void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean z) {
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.mDelegate.onPostResume();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.mDelegate.onStop();
    }

    @Override // androidx.fragment.app.ListFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        this.mDelegate.onViewCreated(view, bundle);
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
        FragmentDelegate fragmentDelegate;
        super.setHasOptionsMenu(z);
        if (this.mHasMenu != z) {
            this.mHasMenu = z;
            if (isHidden() || !isAdded() || (fragmentDelegate = this.mDelegate) == null) {
                return;
            }
            fragmentDelegate.invalidateOptionsMenu();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void setMenuVisibility(boolean z) {
        FragmentDelegate fragmentDelegate;
        super.setMenuVisibility(z);
        if (this.mMenuVisible != z) {
            this.mMenuVisible = z;
            if (isHidden() || !isAdded() || (fragmentDelegate = this.mDelegate) == null) {
                return;
            }
            fragmentDelegate.invalidateOptionsMenu();
        }
    }
}
