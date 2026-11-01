package miuix.animation.easing;

import com.miui.aod.components.view.BaseStyleSelectView;
import miuix.animation.motion.FreeDampedMotion;
import miuix.animation.motion.Motion;

/* JADX INFO: loaded from: classes2.dex */
public class DampingEasing implements PhysicalEasing {
    private final double acceleration;
    private final double damping;

    public DampingEasing(double d, double d2) {
        if (d < BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
            throw new IllegalArgumentException("damping must not be negative");
        }
        this.damping = d;
        this.acceleration = d2;
    }

    public final double getAcceleration() {
        return this.acceleration;
    }

    public final double getDamping() {
        return this.damping;
    }

    @Override // miuix.animation.FolmeEase
    public Motion newMotion() {
        return new FreeDampedMotion(this.damping, this.acceleration);
    }

    public String toString() {
        return "Damping(" + this.damping + ", " + this.acceleration + ")";
    }
}
