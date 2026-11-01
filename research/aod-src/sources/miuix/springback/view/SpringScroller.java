package miuix.springback.view;

import com.miui.aod.components.view.BaseStyleSelectView;
import miuix.view.animation.AnimationUtils;

/* JADX INFO: loaded from: classes.dex */
public class SpringScroller {
    public double mCurrX;
    public double mCurrY;
    public long mCurrentTimeNanos;
    public double mEndX;
    public double mEndY;
    public boolean mFinished = true;
    public int mFirstStep;
    public boolean mLastStep;
    public int mOrientation;
    public double mOriginStartX;
    public double mOriginStartY;
    public double mOriginVelocity;
    public SpringOperator mSpringOperator;
    public long mStartTimeNanos;
    public double mStartX;
    public double mStartY;
    public double mVelocity;

    public boolean computeScrollOffset() {
        if (this.mSpringOperator == null || this.mFinished) {
            return false;
        }
        int i = this.mFirstStep;
        if (i != 0) {
            if (this.mOrientation == 1) {
                this.mCurrX = i;
                this.mStartX = i;
            } else {
                this.mCurrY = i;
                this.mStartY = i;
            }
            this.mFirstStep = 0;
            return true;
        }
        if (this.mLastStep) {
            this.mFinished = true;
            return true;
        }
        long jCurrentAnimationTimeNanos = AnimationUtils.currentAnimationTimeNanos();
        this.mCurrentTimeNanos = jCurrentAnimationTimeNanos;
        double dMin = Math.min((jCurrentAnimationTimeNanos - this.mStartTimeNanos) / 1.0E9d, 0.01600000075995922d);
        double d = dMin == BaseStyleSelectView.COLLAPSE_SLIDE_VALUE ? 0.01600000075995922d : dMin;
        this.mStartTimeNanos = this.mCurrentTimeNanos;
        if (this.mOrientation == 2) {
            double dUpdateVelocity = this.mSpringOperator.updateVelocity(this.mVelocity, d, this.mEndY, this.mStartY);
            double d2 = this.mStartY + (d * dUpdateVelocity);
            this.mCurrY = d2;
            this.mVelocity = dUpdateVelocity;
            if (isAtEquilibrium(d2, this.mOriginStartY, this.mEndY)) {
                this.mLastStep = true;
                this.mCurrY = this.mEndY;
            } else {
                this.mStartY = this.mCurrY;
            }
        } else {
            double dUpdateVelocity2 = this.mSpringOperator.updateVelocity(this.mVelocity, d, this.mEndX, this.mStartX);
            double d3 = this.mStartX + (d * dUpdateVelocity2);
            this.mCurrX = d3;
            this.mVelocity = dUpdateVelocity2;
            if (isAtEquilibrium(d3, this.mOriginStartX, this.mEndX)) {
                this.mLastStep = true;
                this.mCurrX = this.mEndX;
            } else {
                this.mStartX = this.mCurrX;
            }
        }
        return true;
    }

    public final void forceStop() {
        this.mFinished = true;
        this.mFirstStep = 0;
    }

    public final int getCurrX() {
        return (int) this.mCurrX;
    }

    public final int getCurrY() {
        return (int) this.mCurrY;
    }

    public boolean isAtEquilibrium(double d, double d2, double d3) {
        if (d2 < d3 && d > d3) {
            return true;
        }
        if (d2 <= d3 || d >= d3) {
            return (d2 == d3 && Math.signum(this.mOriginVelocity) != Math.signum(d)) || Math.abs(d - d3) < 1.0d;
        }
        return true;
    }

    public final boolean isFinished() {
        return this.mFinished;
    }

    public void scrollByFling(float f, float f2, float f3, float f4, float f5, int i, boolean z) {
        this.mFinished = false;
        this.mLastStep = false;
        double d = f;
        this.mStartX = d;
        this.mOriginStartX = d;
        this.mEndX = f2;
        double d2 = f3;
        this.mStartY = d2;
        this.mOriginStartY = d2;
        this.mCurrY = (int) d2;
        this.mEndY = f4;
        double d3 = f5;
        this.mOriginVelocity = d3;
        this.mVelocity = d3;
        if (Math.abs(d3) <= 5000.0d || z) {
            this.mSpringOperator = new SpringOperator(1.0f, 0.4f);
        } else {
            this.mSpringOperator = new SpringOperator(1.0f, 0.55f);
        }
        this.mOrientation = i;
        this.mStartTimeNanos = AnimationUtils.currentAnimationTimeNanos();
    }

    public void setFirstStep(int i) {
        this.mFirstStep = i;
    }
}
