package miuix.animation.motion;

import com.miui.aod.components.view.BaseStyleSelectView;

/* JADX INFO: loaded from: classes2.dex */
public class AndroidFreeDampedMotion extends FreeDampedMotion implements AndroidMotion {
    private double finishTime;
    private final double g;
    private final double p;
    private double threshold;

    public AndroidFreeDampedMotion(double d, double d2) {
        super(d, d2);
        this.p = d;
        this.g = d2;
    }

    private double solveFinishTime() {
        double dFinishTime = super.finishTime();
        if (dFinishTime == BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
            return dFinishTime;
        }
        double d = this.threshold;
        return d == BaseStyleSelectView.COLLAPSE_SLIDE_VALUE ? dFinishTime : (-Math.log(d)) / this.p;
    }

    @Override // miuix.animation.motion.FreeDampedMotion, miuix.animation.motion.Motion
    public double finishTime() {
        if (Double.isNaN(this.finishTime)) {
            this.finishTime = solveFinishTime();
        }
        return this.finishTime;
    }

    @Override // miuix.animation.motion.FreeDampedMotion, miuix.animation.motion.BaseMotion
    public void onInitialVChanged() {
        super.onInitialVChanged();
        this.finishTime = Double.NaN;
    }

    @Override // miuix.animation.motion.FreeDampedMotion, miuix.animation.motion.BaseMotion
    public void onInitialXChanged() {
        super.onInitialXChanged();
        this.finishTime = Double.NaN;
    }

    @Override // miuix.animation.motion.AndroidMotion
    public void setThreshold(double d) {
        this.threshold = d;
        this.finishTime = Double.NaN;
    }

    @Override // miuix.animation.motion.FreeDampedMotion, miuix.animation.motion.Motion
    public double stopPosition() {
        return this.g == BaseStyleSelectView.COLLAPSE_SLIDE_VALUE ? getInitialX() + (getInitialV() / this.p) : solve().apply(finishTime());
    }
}
