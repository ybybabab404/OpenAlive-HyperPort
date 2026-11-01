package miuix.appcompat.app;

import android.view.View;
import miuix.appcompat.app.strategy.IActionBarStrategy;
import miuix.appcompat.internal.app.widget.actionbar.CollapseTitle;
import miuix.appcompat.internal.app.widget.actionbar.ExpandTitle;

/* JADX INFO: loaded from: classes2.dex */
public abstract class ActionBar extends androidx.appcompat.app.ActionBar {

    public interface FragmentViewPagerChangeListener {
        void onPageScrollStateChanged(int i);

        void onPageScrolled(int i, float f, boolean z, boolean z2);

        void onPageSelected(int i);
    }

    public interface OnScrollListener {
        boolean onContentScrolled();

        void onFling(float f, int i);

        void onScroll(int i, float f);

        void onStartScroll();

        void onStopScroll();
    }

    public abstract void addActionBarTransitionListener(ActionBarTransitionListener actionBarTransitionListener);

    public abstract View getActionBarView();

    public abstract CollapseTitle getCollapseTitle();

    public abstract ExpandTitle getExpandTitle();

    public abstract boolean isAdsorptionToNoOverlay();

    public abstract void registerCoordinateScrollView(View view);

    public abstract void setActionBarStrategy(IActionBarStrategy iActionBarStrategy);

    public abstract void setEndView(View view);

    public abstract void setExpandState(int i);

    public abstract void setMaskColor(int i);

    public abstract void setResizable(boolean z);

    public abstract void setStartView(View view);

    public abstract void unregisterCoordinateScrollView(View view);
}
