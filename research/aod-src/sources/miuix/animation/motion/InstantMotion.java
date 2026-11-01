package miuix.animation.motion;

import com.miui.aod.components.view.BaseStyleSelectView;

/* JADX INFO: loaded from: classes2.dex */
public abstract class InstantMotion extends BaseMotion implements Motion {
    @Override // miuix.animation.motion.Motion
    public final double finishTime() {
        return BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
    }
}
