package miuix.androidbasewidget.widget;

import android.view.animation.AnimationUtils;

/* JADX INFO: loaded from: classes2.dex */
public class SpringScroller {
    public double mCurrX;
    public double mCurrY;
    public long mCurrentTime;
    public double mEndX;
    public double mEndY;
    public boolean mFinished = true;
    public boolean mLastStep;
    public int mOrientation;
    public double mOriginStartY;
    public SpringOperator mSpringOperator;
    public long mStartTime;
    public double mStartX;
    public double mStartY;
    public double mVelocity;

    public final void abortAnimation() {
        this.mFinished = true;
    }

    public boolean computeScrollOffset() {
        if (this.mSpringOperator == null || this.mFinished) {
            return false;
        }
        if (this.mLastStep) {
            this.mFinished = true;
            this.mCurrY = this.mEndY;
            this.mCurrX = this.mEndX;
            return true;
        }
        long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        this.mCurrentTime = jCurrentAnimationTimeMillis;
        float fMin = Math.min((jCurrentAnimationTimeMillis - this.mStartTime) / 1000.0f, 0.016f);
        float f = fMin == 0.0f ? 0.016f : fMin;
        this.mStartTime = this.mCurrentTime;
        if (this.mOrientation == 2) {
            double dUpdateVelocity = this.mSpringOperator.updateVelocity(this.mVelocity, f, this.mEndY, this.mStartY);
            double d = this.mStartY + (((double) f) * dUpdateVelocity);
            this.mCurrY = d;
            this.mVelocity = dUpdateVelocity;
            if (isAtEquilibrium(d, this.mEndY)) {
                this.mLastStep = true;
            } else {
                this.mStartY = this.mCurrY;
            }
        } else {
            double dUpdateVelocity2 = this.mSpringOperator.updateVelocity(this.mVelocity, f, this.mEndX, this.mStartX);
            double d2 = this.mStartX + (((double) f) * dUpdateVelocity2);
            this.mCurrX = d2;
            this.mVelocity = dUpdateVelocity2;
            if (isAtEquilibrium(d2, this.mEndX)) {
                this.mLastStep = true;
            } else {
                this.mStartX = this.mCurrX;
            }
        }
        return true;
    }

    public final int getCurrX() {
        return (int) this.mCurrX;
    }

    public final int getCurrY() {
        return (int) this.mCurrY;
    }

    public final int getFinalX() {
        return (int) this.mEndX;
    }

    public final int getStartX() {
        return (int) this.mStartX;
    }

    public boolean isAtEquilibrium(double d, double d2) {
        return Math.abs(d - d2) < 1.0d;
    }

    public final boolean isFinished() {
        return this.mFinished;
    }

    public void setFinalX(int i) {
        this.mEndX = i;
        this.mFinished = false;
    }

    public void startScroll(float f, float f2, float f3, float f4, float f5) {
        this.mFinished = false;
        this.mLastStep = false;
        this.mStartX = f;
        this.mEndX = f2;
        double d = f3;
        this.mStartY = d;
        this.mOriginStartY = d;
        this.mCurrY = (int) d;
        this.mEndY = f4;
        double d2 = f5;
        this.mVelocity = d2;
        if (Math.abs(d2) <= 5000.0d) {
            this.mSpringOperator = new SpringOperator(0.9f, 0.35f);
        } else {
            this.mSpringOperator = new SpringOperator(0.9f, 0.35f);
        }
        this.mOrientation = Math.abs(f4 - f3) > Math.abs(f2 - f) ? 2 : 1;
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
    }
}
