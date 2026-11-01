package miuix.animation.easing;

import com.miui.aod.components.view.BaseStyleSelectView;
import miuix.animation.motion.DampedHarmonicMotion;
import miuix.animation.motion.Motion;

/* JADX INFO: loaded from: classes2.dex */
public class SpringEasing implements PhysicalEasing {
    private final double omega;
    private final double zeta;

    public SpringEasing(double d, double d2) {
        if (d < BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
            throw new IllegalArgumentException("damping must not be negative");
        }
        if (d2 < BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
            throw new IllegalArgumentException("response must not be negative");
        }
        this.zeta = d;
        this.omega = 6.283185307179586d / d2;
    }

    public final double getOmega() {
        return this.omega;
    }

    public final double getZeta() {
        return this.zeta;
    }

    @Override // miuix.animation.FolmeEase
    public Motion newMotion() {
        return newMotion(BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
    }

    public Motion newMotion(double d) {
        return new DampedHarmonicMotion(this.zeta, this.omega, d, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
    }

    public String toString() {
        return "Spring(" + this.zeta + ", " + this.omega + ")";
    }
}
