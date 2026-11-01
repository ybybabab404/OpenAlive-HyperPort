package miuix.animation.motion;

/* JADX INFO: loaded from: classes2.dex */
public abstract class BaseMotion implements Motion {
    private double initialV;
    private double initialX;

    @Override // miuix.animation.motion.Motion
    public double getInitialV() {
        return this.initialV;
    }

    @Override // miuix.animation.motion.Motion
    public double getInitialX() {
        return this.initialX;
    }

    public void onInitialVChanged() {
    }

    public void onInitialXChanged() {
    }

    @Override // miuix.animation.motion.Motion
    public final void setInitialV(double d) {
        if (this.initialV != d) {
            this.initialV = d;
            onInitialVChanged();
        }
    }

    @Override // miuix.animation.motion.Motion
    public final void setInitialX(double d) {
        if (this.initialX != d) {
            this.initialX = d;
            onInitialXChanged();
        }
    }
}
