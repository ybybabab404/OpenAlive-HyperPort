package androidx.transition;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi
class GhostViewPlatform implements GhostView {
    public final View mGhostView;

    @Override // androidx.transition.GhostView
    public void reserveEndViewTransition(ViewGroup viewGroup, View view) {
    }

    @Override // androidx.transition.GhostView
    public void setVisibility(int i) {
        this.mGhostView.setVisibility(i);
    }
}
