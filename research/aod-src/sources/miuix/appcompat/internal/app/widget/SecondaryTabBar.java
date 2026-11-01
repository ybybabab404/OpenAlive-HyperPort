package miuix.appcompat.internal.app.widget;

import android.view.ViewGroup;
import miuix.appcompat.app.ActionBar;

/* JADX INFO: loaded from: classes2.dex */
public interface SecondaryTabBar extends ActionBar.FragmentViewPagerChangeListener {
    void animateToTab(int i);

    ViewGroup asViewGroup();

    void setParentBlurEnabled(boolean z);

    void setTabSelected(int i);
}
