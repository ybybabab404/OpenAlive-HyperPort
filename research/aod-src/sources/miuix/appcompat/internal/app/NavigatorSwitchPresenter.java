package miuix.appcompat.internal.app;

import android.view.View;

/* JADX INFO: loaded from: classes2.dex */
public class NavigatorSwitchPresenter {
    public float mAlpha;
    public final View mNavigatorSwitch;
    public boolean mSuppressAlpha;
    public boolean mSuppressVisibility;
    public int mVisibility;

    public NavigatorSwitchPresenter(View view) {
        this.mNavigatorSwitch = view;
        this.mVisibility = view.getVisibility();
        this.mAlpha = view.getAlpha();
    }

    public void suppressAlpha(boolean z, float f) {
        this.mSuppressAlpha = z;
        if (z) {
            this.mNavigatorSwitch.setAlpha(f);
        } else {
            this.mNavigatorSwitch.setAlpha(this.mAlpha);
        }
    }

    public void suppressVisibility(boolean z, int i) {
        this.mSuppressVisibility = z;
        if (z) {
            this.mNavigatorSwitch.setVisibility(i);
        } else {
            this.mNavigatorSwitch.setVisibility(this.mVisibility);
        }
    }
}
