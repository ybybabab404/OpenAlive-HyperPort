package miuix.responsive.map;

import android.view.View;

/* JADX INFO: loaded from: classes2.dex */
public class ResponsiveViewSpec {
    public int mEffectiveScreenOrientation;
    public int mHideInScreenMode;
    public View mView;
    public int mViewId;

    public ResponsiveViewSpec(int i) {
        this.mViewId = i;
    }

    public ResponsiveViewSpec(int i, int i2) {
        this.mViewId = i;
        this.mHideInScreenMode = i2;
    }

    public int getEffectiveScreenOrientation() {
        return this.mEffectiveScreenOrientation;
    }

    public View getView() {
        return this.mView;
    }

    public int getViewId() {
        return this.mViewId;
    }

    public void onResponsiveState(ScreenSpec screenSpec) {
        int i = screenSpec.screenMode & 7;
        View view = this.mView;
        if (view != null) {
            view.setVisibility(this.mHideInScreenMode < i ? 0 : 8);
        }
    }

    public void setEffectiveScreenOrientation(int i) {
        this.mEffectiveScreenOrientation = i;
    }

    public void setView(View view) {
        this.mView = view;
    }
}
