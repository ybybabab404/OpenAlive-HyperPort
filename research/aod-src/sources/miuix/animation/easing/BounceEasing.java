package miuix.animation.easing;

import com.miui.aod.components.view.BaseStyleSelectView;
import miuix.animation.function.Bounce;
import miuix.animation.motion.FunctionMotion;
import miuix.animation.motion.Motion;
import miuix.animation.motion.ScaleMotion;

/* JADX INFO: loaded from: classes2.dex */
public class BounceEasing implements SimpleEasing {
    private final double duration;

    public BounceEasing() {
        this(1.0d);
    }

    public BounceEasing(double d) {
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
        return new ScaleMotion(new FunctionMotion(new Bounce()), 1.0d, this.duration);
    }

    @Override // miuix.animation.easing.SimpleEasing
    public double startSpeed() {
        return BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
    }

    public String toString() {
        return "Bounce(" + this.duration + ")";
    }
}
