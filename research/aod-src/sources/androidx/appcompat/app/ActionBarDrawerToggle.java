package androidx.appcompat.app;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

/* JADX INFO: loaded from: classes.dex */
public class ActionBarDrawerToggle implements DrawerLayout.DrawerListener {
    public final Delegate mActivityImpl;
    public final int mCloseDrawerContentDescRes;
    public boolean mDrawerIndicatorEnabled;
    public final DrawerLayout mDrawerLayout;
    public boolean mDrawerSlideAnimationEnabled;
    public final int mOpenDrawerContentDescRes;
    public DrawerArrowDrawable mSlider;
    public View.OnClickListener mToolbarNavigationClickListener;

    /* JADX INFO: renamed from: androidx.appcompat.app.ActionBarDrawerToggle$1, reason: invalid class name */
    class AnonymousClass1 implements View.OnClickListener {
        public final /* synthetic */ ActionBarDrawerToggle this$0;

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ActionBarDrawerToggle actionBarDrawerToggle = this.this$0;
            if (actionBarDrawerToggle.mDrawerIndicatorEnabled) {
                actionBarDrawerToggle.toggle();
                return;
            }
            View.OnClickListener onClickListener = actionBarDrawerToggle.mToolbarNavigationClickListener;
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
        }
    }

    public interface Delegate {
        void setActionBarDescription(int i);
    }

    public interface DelegateProvider {
    }

    public static class FrameworkActionBarDelegate implements Delegate {
        public final Activity mActivity;

        @RequiresApi
        public static class Api18Impl {
            @DoNotInline
            public static void setHomeActionContentDescription(android.app.ActionBar actionBar, int i) {
                actionBar.setHomeActionContentDescription(i);
            }

            @DoNotInline
            public static void setHomeAsUpIndicator(android.app.ActionBar actionBar, Drawable drawable) {
                actionBar.setHomeAsUpIndicator(drawable);
            }
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public void setActionBarDescription(int i) {
            android.app.ActionBar actionBar = this.mActivity.getActionBar();
            if (actionBar != null) {
                Api18Impl.setHomeActionContentDescription(actionBar, i);
            }
        }
    }

    public static class ToolbarCompatDelegate implements Delegate {
        public final CharSequence mDefaultContentDescription;
        public final Toolbar mToolbar;

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public void setActionBarDescription(int i) {
            if (i == 0) {
                this.mToolbar.setNavigationContentDescription(this.mDefaultContentDescription);
            } else {
                this.mToolbar.setNavigationContentDescription(i);
            }
        }
    }

    @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
    public void onDrawerClosed(View view) {
        setPosition(0.0f);
        if (this.mDrawerIndicatorEnabled) {
            setActionBarDescription(this.mOpenDrawerContentDescRes);
        }
    }

    @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
    public void onDrawerOpened(View view) {
        setPosition(1.0f);
        if (this.mDrawerIndicatorEnabled) {
            setActionBarDescription(this.mCloseDrawerContentDescRes);
        }
    }

    @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
    public void onDrawerSlide(View view, float f) {
        if (this.mDrawerSlideAnimationEnabled) {
            setPosition(Math.min(1.0f, Math.max(0.0f, f)));
        } else {
            setPosition(0.0f);
        }
    }

    @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
    public void onDrawerStateChanged(int i) {
    }

    public void setActionBarDescription(int i) {
        this.mActivityImpl.setActionBarDescription(i);
    }

    public final void setPosition(float f) {
        if (f == 1.0f) {
            this.mSlider.setVerticalMirror(true);
        } else if (f == 0.0f) {
            this.mSlider.setVerticalMirror(false);
        }
        this.mSlider.setProgress(f);
    }

    public void toggle() {
        int drawerLockMode = this.mDrawerLayout.getDrawerLockMode(8388611);
        if (this.mDrawerLayout.isDrawerVisible(8388611) && drawerLockMode != 2) {
            this.mDrawerLayout.closeDrawer(8388611);
        } else if (drawerLockMode != 1) {
            this.mDrawerLayout.openDrawer(8388611);
        }
    }
}
