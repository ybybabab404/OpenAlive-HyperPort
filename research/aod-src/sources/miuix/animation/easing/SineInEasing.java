package miuix.animation.easing;

import com.miui.aod.components.view.BaseStyleSelectView;
import miuix.animation.motion.DurationMotion;
import miuix.animation.motion.Motion;
import miuix.animation.motion.SimpleHarmonicMotion;

/* JADX INFO: loaded from: classes2.dex */
public class SineInEasing implements SimpleEasing {
    private final double duration;

    public SineInEasing() {
        this(1.0d);
    }

    public SineInEasing(double d) {
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
        double d = 1.5707963267948966d / this.duration;
        return new DurationMotion(new SimpleHarmonicMotion(1.0d, d * d, (1.0d / d) / d), this.duration, true);
    }

    @Override // miuix.animation.easing.SimpleEasing
    public double startSpeed() {
        return BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
    }

    public String toString() {
        return "SineIn(" + this.duration + ")";
    }
}
