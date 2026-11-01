package miuix.animation.motion;

import com.miui.aod.components.view.BaseStyleSelectView;
import miuix.animation.function.CriticalDamping;
import miuix.animation.function.Differentiable;
import miuix.animation.function.OverDamping;
import miuix.animation.function.UnderDamping;

/* JADX INFO: loaded from: classes2.dex */
public class DampedHarmonicMotion extends BaseMotion implements Motion {
    private Differentiable function;
    private final double g;
    private final double p;
    private final double q;
    private final double xStar;

    public DampedHarmonicMotion(double d, double d2, double d3, double d4) {
        if (d == BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
            throw new IllegalArgumentException("zeta must not be 0");
        }
        if (d2 == BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
            throw new IllegalArgumentException("omega must not be 0");
        }
        this.p = d * 2.0d * d2;
        double d5 = d2 * d2;
        this.q = d5;
        this.g = d4;
        this.xStar = ((-d4) / d5) + d3;
        this.function = null;
    }

    private void solveInternal() {
        double d = this.p;
        double d2 = (d * d) - (this.q * 4.0d);
        double initialX = getInitialX() - this.xStar;
        if (d2 > BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
            double dSqrt = Math.sqrt(d2);
            double d3 = this.p;
            double d4 = (dSqrt - d3) / 2.0d;
            double d5 = ((-dSqrt) - d3) / 2.0d;
            this.function = new OverDamping((getInitialV() - (initialX * d5)) / dSqrt, (-(getInitialV() - (initialX * d4))) / dSqrt, d4, d5, this.xStar);
            return;
        }
        if (d2 == BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
            double d6 = (-this.p) / 2.0d;
            this.function = new CriticalDamping(initialX, getInitialV() - (initialX * d6), d6, this.xStar);
        } else {
            double d7 = (-this.p) / 2.0d;
            double dSqrt2 = Math.sqrt(-d2) / 2.0d;
            this.function = new UnderDamping(initialX, (getInitialV() - (initialX * d7)) / dSqrt2, d7, dSqrt2, this.xStar);
        }
    }

    @Override // miuix.animation.motion.Motion
    public double finishTime() {
        return (getInitialX() == stopPosition() && getInitialV() == stopSpeed()) ? BaseStyleSelectView.COLLAPSE_SLIDE_VALUE : super.finishTime();
    }

    @Override // miuix.animation.motion.BaseMotion
    public void onInitialVChanged() {
        super.onInitialVChanged();
        this.function = null;
    }

    @Override // miuix.animation.motion.BaseMotion
    public void onInitialXChanged() {
        super.onInitialXChanged();
        this.function = null;
    }

    @Override // miuix.animation.motion.Motion
    public Differentiable solve() {
        if (this.function == null) {
            solveInternal();
        }
        return this.function;
    }

    @Override // miuix.animation.motion.Motion
    public double stopPosition() {
        return this.xStar + (this.g / this.p);
    }

    @Override // miuix.animation.motion.Motion
    public double stopSpeed() {
        return BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
    }
}
