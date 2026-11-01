package miuix.springback.view;

import com.miui.aod.components.view.BaseStyleSelectView;
import miuix.view.animation.AnimationUtils;

/* JADX INFO: loaded from: classes.dex */
public class SpringOverScroller {
    public double mCurrX;
    public double mCurrY;
    public long mCurrentTimeNanos;
    public boolean mFinished = true;
    public int mFirstStep;
    public boolean mLastStep;
    public int mOrientation;
    public SpringOperator mSpringOperator;
    public long mStartTimeNanos;
    public double mStartX;
    public double mStartY;
    public double mVelocity;

    public final double calculateDelta() {
        double dMin = Math.min((this.mCurrentTimeNanos - this.mStartTimeNanos) / 1.0E9d, 0.01600000075995922d);
        double d = dMin != BaseStyleSelectView.COLLAPSE_SLIDE_VALUE ? dMin : 0.01600000075995922d;
        this.mStartTimeNanos = this.mCurrentTimeNanos;
        return d;
    }

    public final double clampOverscrollDistance(double d) {
        if (Math.abs(d) <= 600.0d) {
            return d;
        }
        this.mLastStep = true;
        return Math.signum(d) * 600.0d;
    }

    public final float computeCurrentVelocity(float f, float f2) {
        return (f > 0.0f ? 1 : -1) * Math.abs(f) * Math.max(0.0f, Math.min(1.0f, 1.0f - (f2 * 18.0f)));
    }

    public boolean computeScrollOffset() {
        if (this.mSpringOperator == null || this.mFinished) {
            return false;
        }
        if (this.mFirstStep != 0) {
            handleFirstStep();
            return true;
        }
        if (this.mLastStep) {
            handleLastStep();
            return true;
        }
        updateDeltaTime();
        return this.mOrientation == 2 ? handleVerticalScroll() : handleHorizontalScroll();
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

    public final void handleFirstStep() {
        if (this.mOrientation == 1) {
            setHorizontalPosition(this.mFirstStep);
        } else {
            setVerticalPosition(this.mFirstStep);
        }
        resetFirstStep();
    }

    public final boolean handleHorizontalScroll() {
        double dCalculateDelta = calculateDelta();
        double dComputeCurrentVelocity = computeCurrentVelocity((float) this.mVelocity, (float) dCalculateDelta);
        double d = this.mStartX + (dCalculateDelta * dComputeCurrentVelocity);
        this.mCurrX = d;
        this.mCurrX = clampOverscrollDistance(d);
        this.mVelocity = dComputeCurrentVelocity;
        updateHorizontalState();
        return true;
    }

    public final void handleLastStep() {
        this.mFinished = true;
    }

    public final boolean handleVerticalScroll() {
        double dCalculateDelta = calculateDelta();
        double dComputeCurrentVelocity = computeCurrentVelocity((float) this.mVelocity, (float) dCalculateDelta);
        double d = this.mStartY + (dCalculateDelta * dComputeCurrentVelocity);
        this.mCurrY = d;
        this.mCurrY = clampOverscrollDistance(d);
        this.mVelocity = dComputeCurrentVelocity;
        updateVerticalState();
        return true;
    }

    public final boolean isFinished() {
        return this.mFinished;
    }

    public final void resetFirstStep() {
        this.mFirstStep = 0;
    }

    public final void setHorizontalPosition(int i) {
        double d = i;
        this.mCurrX = d;
        this.mStartX = d;
    }

    public final void setVerticalPosition(int i) {
        double d = i;
        this.mCurrY = d;
        this.mStartY = d;
    }

    public void startOverScroll(float f, float f2, float f3, int i) {
        this.mFinished = false;
        this.mLastStep = false;
        this.mStartX = f;
        double d = f2;
        this.mStartY = d;
        this.mCurrY = (int) d;
        double d2 = f3;
        this.mVelocity = d2;
        if (Math.abs(d2) <= 5000.0d) {
            this.mSpringOperator = new SpringOperator(1.0f, 0.4f);
        } else {
            this.mSpringOperator = new SpringOperator(1.0f, 0.55f);
        }
        this.mOrientation = i;
        this.mStartTimeNanos = AnimationUtils.currentAnimationTimeNanos();
    }

    public final void updateDeltaTime() {
        this.mCurrentTimeNanos = AnimationUtils.currentAnimationTimeNanos();
    }

    public final void updateHorizontalState() {
        if (Math.abs(this.mVelocity) <= 2000.0d) {
            this.mLastStep = true;
        } else {
            this.mStartX = this.mCurrX;
        }
    }

    public final void updateVerticalState() {
        if (Math.abs(this.mVelocity) <= 2000.0d) {
            this.mLastStep = true;
        } else {
            this.mStartY = this.mCurrY;
        }
    }
}
