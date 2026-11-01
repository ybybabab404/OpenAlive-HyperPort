package miuix.animation.easing;

import com.miui.aod.components.view.BaseStyleSelectView;
import miuix.animation.motion.Motion;
import miuix.animation.motion.PolynomialMotion;
import miuix.animation.motion.ScaleMotion;

/* JADX INFO: loaded from: classes2.dex */
public class QuartInEasing implements SimpleEasing {
    private final double duration;

    public QuartInEasing() {
        this(1.0d);
    }

    public QuartInEasing(double d) {
        if (d <= BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
            throw new IllegalArgumentException("duration must be positive");
        }
        this.duration = d;
    }

    @Override // miuix.animation.easing.SimpleEasing
    public final double duration() {
        return this.duration;
    }

    @Override // miuix.animation.FolmeEase
    public Motion newMotion() {
        return new ScaleMotion(new PolynomialMotion(4, 1.0d, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE), 1.0d, this.duration);
    }

    @Override // miuix.animation.easing.SimpleEasing
    public double startSpeed() {
        return BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
    }

    public String toString() {
        return "QuartIn(" + this.duration + ")";
    }
}
