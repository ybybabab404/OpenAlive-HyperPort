package miuix.animation.function;

import com.miui.aod.components.view.BaseStyleSelectView;

/* JADX INFO: loaded from: classes2.dex */
public class Trigonometric implements Differentiable {
    private final double a;
    private Function derivative;
    private final double omega;
    private final double phi;
    private final double xStar;

    public Trigonometric(double d, double d2, double d3, double d4) {
        this.a = d;
        this.omega = d2;
        this.phi = d3;
        this.xStar = d4;
    }

    @Override // miuix.animation.function.Differentiable, miuix.animation.function.Function
    public double apply(double d) {
        return (this.a * Math.cos((this.omega * d) + this.phi)) + this.xStar;
    }

    @Override // miuix.animation.function.Differentiable
    public Function derivative() {
        if (this.derivative == null) {
            double d = this.omega;
            this.derivative = new Trigonometric(this.a * d, d, this.phi + 1.5707963267948966d, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
        }
        return this.derivative;
    }
}
