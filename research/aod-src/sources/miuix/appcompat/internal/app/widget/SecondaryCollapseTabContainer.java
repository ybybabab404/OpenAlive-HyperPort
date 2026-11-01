package miuix.appcompat.internal.app.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import miuix.springback.view.SpringBackLayout;

/* JADX INFO: loaded from: classes2.dex */
public class SecondaryCollapseTabContainer extends SpringBackLayout implements SecondaryTabBar {
    public final SecondaryTabContainerView mTabContainer;

    public SecondaryCollapseTabContainer(Context context) {
        super(context);
        setScrollOrientation(1);
        SecondaryTabContainerView secondaryTabContainerView = new SecondaryTabContainerView(context);
        this.mTabContainer = secondaryTabContainerView;
        addView(secondaryTabContainerView);
        secondaryTabContainerView.setContentHeight(secondaryTabContainerView.getTabContainerHeight());
        setTarget(secondaryTabContainerView);
    }

    @Override // miuix.appcompat.internal.app.widget.SecondaryTabBar
    public void animateToTab(int i) {
        this.mTabContainer.animateToTab(i);
    }

    @Override // miuix.appcompat.internal.app.widget.SecondaryTabBar
    public ViewGroup asViewGroup() {
        return this;
    }

    @NonNull
    public SecondaryTabContainerView getSecondaryTabContainerView() {
        return this.mTabContainer;
    }

    @Override // miuix.springback.view.SpringBackLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        setSpringBackEnable(this.mTabContainer.canScrollHorizontally());
    }

    @Override // miuix.appcompat.app.ActionBar.FragmentViewPagerChangeListener
    public void onPageScrollStateChanged(int i) {
        this.mTabContainer.onPageScrollStateChanged(i);
    }

    @Override // miuix.appcompat.app.ActionBar.FragmentViewPagerChangeListener
    public void onPageScrolled(int i, float f, boolean z, boolean z2) {
        this.mTabContainer.onPageScrolled(i, f, z, z2);
    }

    @Override // miuix.appcompat.app.ActionBar.FragmentViewPagerChangeListener
    public void onPageSelected(int i) {
        this.mTabContainer.onPageSelected(i);
    }

    public void setBadgeVisibility(int i, boolean z) {
        this.mTabContainer.setBadgeVisibility(i, z);
    }

    public void setFilteredTabShadowEnabled(boolean z) {
        this.mTabContainer.setFilteredTabShadowEnabled(z);
    }

    @Override // miuix.appcompat.internal.app.widget.SecondaryTabBar
    public void setParentBlurEnabled(boolean z) {
        this.mTabContainer.setParentBlurEnabled(z);
    }

    public void setTabBadgeDisappearOnClick(int i, boolean z) {
        this.mTabContainer.setTabBadgeDisappearOnClick(i, z);
    }

    public void setTabIconWithPosition(int i, int i2, Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        this.mTabContainer.setTabIconWithPosition(i, i2, drawable, drawable2, drawable3, drawable4);
    }

    @Override // miuix.appcompat.internal.app.widget.SecondaryTabBar
    public void setTabSelected(int i) {
        this.mTabContainer.setTabSelected(i);
    }

    public void setTextAppearance(int i, int i2) {
        this.mTabContainer.setTextAppearance(i, i2);
    }

    public void setTextAppearance(int i, int i2, int i3) {
        this.mTabContainer.setTextAppearance(i, i2, i3);
    }

    public void setUnfilteredTabShadowEnabled(boolean z) {
        this.mTabContainer.setUnfilteredTabShadowEnabled(z);
    }
}
