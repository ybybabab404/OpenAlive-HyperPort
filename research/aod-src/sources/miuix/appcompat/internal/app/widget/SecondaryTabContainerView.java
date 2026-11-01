package miuix.appcompat.internal.app.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import miuix.appcompat.R;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.widget.BadgeDrawable;
import miuix.internal.util.AttributeResolver;
import miuix.miuixbasewidget.widget.FilterSortView2;

/* JADX INFO: loaded from: classes2.dex */
public class SecondaryTabContainerView extends FilterSortView2 implements SecondaryTabBar, ActionBar.FragmentViewPagerChangeListener {
    public int mActivatedTextAppearanceId;
    public boolean mAllowCollapse;
    public int mContentHeight;
    public int mTextAppearanceId;

    public static class SecondaryTabView extends FilterSortView2.TabView {
        public final BadgeDrawable mBadge;
        public boolean mBadgeDisappearOnClick;
        public boolean mBadgeNeeded;
        public androidx.appcompat.app.ActionBar.Tab mTab;

        public SecondaryTabView(Context context) {
            this(context, null);
        }

        public SecondaryTabView(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, 0);
        }

        public SecondaryTabView(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            this.mBadgeNeeded = false;
            this.mBadgeDisappearOnClick = true;
            this.mBadge = new BadgeDrawable(context, 2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setBadgeDisappearOnClick(boolean z) {
            this.mBadgeDisappearOnClick = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setBadgeNeeded(boolean z) {
            this.mBadgeNeeded = z;
            updateBadge();
        }

        public final void attachBadge() {
            BadgeDrawable badgeDrawable = this.mBadge;
            if (badgeDrawable != null) {
                badgeDrawable.attachBadgeDrawable(this);
            }
        }

        public final void detachBadge() {
            BadgeDrawable badgeDrawable = this.mBadge;
            if (badgeDrawable != null) {
                badgeDrawable.detachBadgeDrawable();
            }
        }

        public androidx.appcompat.app.ActionBar.Tab getTab() {
            return this.mTab;
        }

        @Override // android.view.View
        public void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            updateIconView();
        }

        @Override // android.view.View
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            updateBadge();
        }

        @Override // android.view.View
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            if (this.mBadgeNeeded) {
                StringBuilder sb = new StringBuilder();
                TextView textView = getTextView();
                if (textView != null && textView.getText() != null) {
                    sb.append(textView.getText());
                }
                sb.append(getResources().getString(R.string.miuix_appcompat_accessibility_new_message));
                accessibilityNodeInfo.setContentDescription(sb);
            }
        }

        public final void updateBadge() {
            if (this.mBadgeNeeded) {
                attachBadge();
            } else {
                detachBadge();
            }
        }

        public final void updateIconView() {
            ImageView iconView = getIconView();
            if (iconView != null) {
                iconView.setImageDrawable(this.mTab.getIcon());
            }
        }
    }

    public static class TabClickListener implements View.OnClickListener {
        public WeakReference mRefs;

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            WeakReference weakReference = this.mRefs;
            SecondaryTabContainerView secondaryTabContainerView = weakReference != null ? (SecondaryTabContainerView) weakReference.get() : null;
            if (secondaryTabContainerView == null) {
                return;
            }
            SecondaryTabView secondaryTabView = (SecondaryTabView) view;
            secondaryTabView.getTab().select();
            int tabCount = secondaryTabContainerView.getTabCount();
            for (int i = 0; i < tabCount; i++) {
                FilterSortView2.TabView tabViewAt = secondaryTabContainerView.getTabViewAt(i);
                tabViewAt.setActivated(tabViewAt == view);
            }
            if (secondaryTabView.mBadgeDisappearOnClick) {
                secondaryTabView.setBadgeNeeded(false);
            }
        }
    }

    public SecondaryTabContainerView(Context context) {
        super(context);
        init(context);
    }

    public SecondaryTabContainerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public SecondaryTabContainerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        this.mTextAppearanceId = AttributeResolver.resolve(context, getDefaultTabTextStyle());
        this.mActivatedTextAppearanceId = AttributeResolver.resolve(context, getTabActivatedTextStyle());
    }

    @Override // miuix.appcompat.internal.app.widget.SecondaryTabBar
    public void animateToTab(int i) {
        setFilteredTab(i);
    }

    @Override // miuix.appcompat.internal.app.widget.SecondaryTabBar
    public ViewGroup asViewGroup() {
        return this;
    }

    public int getDefaultTabTextStyle() {
        return R.attr.actionBarTabTextSecondaryStyle;
    }

    public int getTabActivatedTextStyle() {
        return R.attr.actionBarTabActivatedTextSecondaryStyle;
    }

    public int getTabContainerHeight() {
        return -2;
    }

    @Override // miuix.miuixbasewidget.widget.FilterSortView2, android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int i3 = this.mContentHeight;
        if (i3 != -2) {
            i2 = View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
        }
        super.onMeasure(i, i2);
    }

    @Override // miuix.appcompat.app.ActionBar.FragmentViewPagerChangeListener
    public void onPageScrollStateChanged(int i) {
    }

    @Override // miuix.appcompat.app.ActionBar.FragmentViewPagerChangeListener
    public void onPageScrolled(int i, float f, boolean z, boolean z2) {
    }

    @Override // miuix.appcompat.app.ActionBar.FragmentViewPagerChangeListener
    public void onPageSelected(int i) {
        FilterSortView2.TabView tabViewAt = getTabViewAt(i);
        if (tabViewAt instanceof SecondaryTabView) {
            setFilteredTab(tabViewAt);
        }
    }

    public void setAllowCollapse(boolean z) {
        this.mAllowCollapse = z;
    }

    public void setBadgeVisibility(int i, boolean z) {
        if (i >= getTabCount()) {
            return;
        }
        FilterSortView2.TabView tabViewAt = getTabViewAt(i);
        if (tabViewAt instanceof SecondaryTabView) {
            ((SecondaryTabView) tabViewAt).setBadgeNeeded(z);
        }
    }

    public void setContentHeight(int i) {
        if (this.mContentHeight != i) {
            this.mContentHeight = i;
            requestLayout();
        }
    }

    @Override // miuix.appcompat.internal.app.widget.SecondaryTabBar
    public void setParentBlurEnabled(boolean z) {
    }

    public void setTabBadgeDisappearOnClick(int i, boolean z) {
        if (i >= getTabCount()) {
            return;
        }
        FilterSortView2.TabView tabViewAt = getTabViewAt(i);
        if (tabViewAt instanceof SecondaryTabView) {
            ((SecondaryTabView) tabViewAt).setBadgeDisappearOnClick(z);
        }
    }

    public void setTabIconWithPosition(int i, int i2, Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        FilterSortView2.TabView tabViewAt;
        TextView textView;
        if (i > getChildCount() - 1 || (tabViewAt = getTabViewAt(i)) == null || (textView = tabViewAt.getTextView()) == null) {
            return;
        }
        textView.setCompoundDrawablePadding(i2);
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
    }

    @Override // miuix.appcompat.internal.app.widget.SecondaryTabBar
    public void setTabSelected(int i) {
        setFilteredTab(i);
    }

    @Deprecated
    public void setTextAppearance(int i, int i2) {
        setTextAppearance(i, i2, i2);
    }

    public void setTextAppearance(int i, int i2, int i3) {
        FilterSortView2.TabView tabViewAt;
        if (i < 0 || i >= getTabCount() || (tabViewAt = getTabViewAt(i)) == null) {
            return;
        }
        tabViewAt.setTextAppearance(i2);
        tabViewAt.setActivatedTextAppearance(i3);
    }
}
