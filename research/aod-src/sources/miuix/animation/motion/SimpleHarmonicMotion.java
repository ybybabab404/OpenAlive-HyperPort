package miuix.animation.motion;

import com.miui.aod.components.view.BaseStyleSelectView;
import miuix.animation.function.Differentiable;
import miuix.animation.function.Trigonometric;

/* JADX INFO: loaded from: classes2.dex */
public class SimpleHarmonicMotion extends BaseMotion implements Motion {
    private Differentiable function;
    private double omega;
    private double xStar;

    public SimpleHarmonicMotion(double d) {
        this.omega = d;
        this.xStar = BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
        this.function = null;
    }

    public SimpleHarmonicMotion(double d, double d2, double d3) {
        this.omega = Math.sqrt(d / d3);
        this.xStar = (d3 * d2) / d;
        this.function = null;
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
            double initialX = getInitialX() - this.xStar;
            double initialV = getInitialV() * getInitialV();
            double d = this.omega;
            this.function = new Trigonometric(Math.sqrt((initialX * initialX) + ((initialV / d) / d)), this.omega, Math.atan2(-getInitialV(), this.omega * initialX), this.xStar);
        }
        return this.function;
    }
}
