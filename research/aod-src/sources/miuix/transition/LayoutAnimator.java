package miuix.transition;

import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes3.dex */
public abstract class LayoutAnimator {
    public MiuixTransition mTransition;

    public static EaseManager.EaseStyle spring(float f, float f2) {
        return EaseManager.getStyle(-2, f, f2);
    }
}
