package miuix.animation.easing;

import androidx.annotation.NonNull;
import com.miui.aod.components.view.BaseStyleSelectView;

/* JADX INFO: loaded from: classes2.dex */
public class AndroidFrictionEasing extends AndroidDampingEasing implements PhysicalEasing {
    public AndroidFrictionEasing(double d) {
        super(d, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
    }

    @Override // miuix.animation.easing.DampingEasing
    @NonNull
    public String toString() {
        return "Friction(" + getDamping() + ")";
    }
}
