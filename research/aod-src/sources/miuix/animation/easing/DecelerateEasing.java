package miuix.animation.easing;

import com.miui.aod.components.view.BaseStyleSelectView;
import miuix.animation.function.Decelerate;
import miuix.animation.motion.FunctionMotion;
import miuix.animation.motion.Motion;
import miuix.animation.motion.ScaleMotion;

/* JADX INFO: loaded from: classes2.dex */
public class DecelerateEasing implements SimpleEasing {
    private final double duration;
    private final double factor;

    public DecelerateEasing() {
        this(1.0d);
    }

    public DecelerateEasing(double d) {
        if (d <= BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
            throw new IllegalArgumentException("duration must be positive");
        }
        this.factor = 1.0d;
        this.duration = d;
    }

    public DecelerateEasing(double d, double d2) {
        if (d2 <= BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
            throw new IllegalArgumentException("duration must be positive");
        }
        this.factor = Math.max(BaseStyleSelectView.COLLAPSE_SLIDE_VALUE, d);
        this.duration = d2;
    }

    @Override // miuix.animation.easing.SimpleEasing
    public final double duration() {
        return this.duration;
    }

    @Override // miuix.animation.FolmeEase
    public Motion newMotion() {
        return new ScaleMotion(new FunctionMotion(new Decelerate(this.factor)), 1.0d, this.duration);
    }

    @Override // miuix.animation.easing.SimpleEasing
    public double startSpeed() {
        return BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
    }

    public String toString() {
        return "Decelerate(" + this.duration + ")";
    }
}
