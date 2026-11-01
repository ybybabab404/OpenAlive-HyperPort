package miuix.overscroller.widget;

import android.content.Context;
import android.util.Log;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import com.miui.aod.components.view.BaseStyleSelectView;
import miuix.animation.internal.AnimData;
import miuix.animation.internal.FolmeCore;
import miuix.animation.physics.SpringOperator;
import miuix.view.animation.AnimationUtils;

/* JADX INFO: loaded from: classes2.dex */
public class OverScroller {
    public final boolean mFlywheel;
    public Interpolator mInterpolator;
    public int mMode;
    public final SplineOverScroller mScrollerX;
    public final SplineOverScroller mScrollerY;

    public static class SplineOverScroller {
        public static float DECELERATION_RATE = (float) (Math.log(0.78d) / Math.log(0.9d));
        public static final float[] SPLINE_POSITION = new float[101];
        public static final float[] SPLINE_TIME = new float[101];
        public Context mContext;
        public double mCurrVelocity;
        public double mCurrentPosition;
        public float mDeceleration;
        public int mDuration;
        public double mFinal;
        public boolean mLastStep;
        public double mOriginStart;
        public int mOver;
        public float mPhysicalCoeff;
        public int mSplineDistance;
        public int mSplineDuration;
        public AnimData mSpringAnimData;
        public double[] mSpringFactor;
        public SpringOperator mSpringOperator;
        public double[] mSpringParams;
        public double mStart;
        public long mStartTime;
        public double mVelocity;
        public float mFlingFriction = ViewConfiguration.getScrollFriction();
        public int mState = 0;
        public boolean mFinished = true;

        static {
            float f;
            float f2;
            float f3;
            float f4;
            float f5;
            float f6;
            float f7;
            float f8;
            float f9;
            float f10;
            float f11 = 0.0f;
            int i = 0;
            float f12 = 0.0f;
            while (true) {
                float f13 = 1.0f;
                if (i >= 100) {
                    float[] fArr = SPLINE_POSITION;
                    SPLINE_TIME[100] = 1.0f;
                    fArr[100] = 1.0f;
                    return;
                }
                float f14 = i / 100.0f;
                float f15 = 1.0f;
                while (true) {
                    f = ((f15 - f11) / 2.0f) + f11;
                    f2 = f13 - f;
                    f3 = f * 3.0f * f2;
                    f4 = f * f * f;
                    float f16 = (((f2 * 0.175f) + (f * 0.35000002f)) * f3) + f4;
                    f5 = f13;
                    f6 = f14;
                    if (Math.abs(f16 - f14) < 1.0E-5d) {
                        break;
                    }
                    if (f16 > f6) {
                        f15 = f;
                    } else {
                        f11 = f;
                    }
                    f13 = f5;
                    f14 = f6;
                }
                SPLINE_POSITION[i] = (f3 * ((f2 * 0.5f) + f)) + f4;
                float f17 = f5;
                while (true) {
                    f7 = ((f17 - f12) / 2.0f) + f12;
                    f8 = f5 - f7;
                    f9 = f7 * 3.0f * f8;
                    f10 = f7 * f7 * f7;
                    float f18 = (((f8 * 0.5f) + f7) * f9) + f10;
                    if (Math.abs(f18 - f6) < 1.0E-5d) {
                        break;
                    } else if (f18 > f6) {
                        f17 = f7;
                    } else {
                        f12 = f7;
                    }
                }
                SPLINE_TIME[i] = (f9 * ((f8 * 0.175f) + (f7 * 0.35000002f))) + f10;
                i++;
            }
        }

        public SplineOverScroller(Context context) {
            this.mContext = context;
            this.mPhysicalCoeff = context.getResources().getDisplayMetrics().density * 160.0f * 386.0878f * 0.84f;
        }

        public static float getDeceleration(int i) {
            return i > 0 ? -2000.0f : 2000.0f;
        }

        public final void adjustDuration(int i, int i2, int i3) {
            float fAbs = Math.abs((i3 - i) / (i2 - i));
            int i4 = (int) (fAbs * 100.0f);
            if (i4 < 100) {
                float f = i4 / 100.0f;
                int i5 = i4 + 1;
                float[] fArr = SPLINE_TIME;
                float f2 = fArr[i4];
                this.mDuration = (int) (this.mDuration * (f2 + (((fAbs - f) / ((i5 / 100.0f) - f)) * (fArr[i5] - f2))));
            }
        }

        public boolean computeScrollOffset() {
            if (this.mSpringOperator == null || this.mFinished) {
                return false;
            }
            if (this.mLastStep) {
                this.mFinished = true;
                this.mCurrentPosition = this.mFinal;
                return true;
            }
            long jCurrentAnimationTimeNanos = AnimationUtils.currentAnimationTimeNanos();
            double dMin = Math.min((jCurrentAnimationTimeNanos - this.mStartTime) / 1.0E9d, 0.01600000075995922d);
            double d = dMin == BaseStyleSelectView.COLLAPSE_SLIDE_VALUE ? 0.01600000075995922d : dMin;
            this.mStartTime = jCurrentAnimationTimeNanos;
            AnimData animData = this.mSpringAnimData;
            animData.startValue = this.mStart;
            animData.targetValue = this.mFinal;
            animData.value = this.mCurrentPosition;
            double d2 = this.mSpringFactor[0];
            double[] dArr = this.mSpringParams;
            SpringOperator.updateValues(animData, d2, dArr[1], dArr[2], d, false);
            AnimData animData2 = this.mSpringAnimData;
            double d3 = animData2.value;
            this.mCurrentPosition = d3;
            this.mCurrVelocity = animData2.velocity;
            if (isAtEquilibrium(d3, this.mFinal)) {
                this.mLastStep = true;
            } else {
                this.mStart = this.mCurrentPosition;
            }
            return true;
        }

        public boolean continueWhenFinished() {
            int i = this.mState;
            if (i != 0) {
                if (i == 1) {
                    return false;
                }
                if (i == 2) {
                    this.mStartTime += ((long) this.mDuration) * FolmeCore.NANOS_TO_MS;
                    startSpringback((int) this.mFinal, (int) this.mStart, 0);
                }
            } else {
                if (this.mDuration >= this.mSplineDuration) {
                    return false;
                }
                double d = this.mFinal;
                this.mStart = d;
                this.mCurrentPosition = d;
                double d2 = (int) this.mCurrVelocity;
                this.mVelocity = d2;
                this.mDeceleration = getDeceleration((int) d2);
                this.mStartTime += ((long) this.mDuration) * FolmeCore.NANOS_TO_MS;
                onEdgeReached();
            }
            update();
            return true;
        }

        public void finish() {
            this.mCurrentPosition = this.mFinal;
            this.mFinished = true;
        }

        public final void fitOnBounceCurve(int i, int i2, int i3) {
            float f = this.mDeceleration;
            float f2 = (-i3) / f;
            float f3 = i3;
            float fSqrt = (float) Math.sqrt((((double) ((((f3 * f3) / 2.0f) / Math.abs(f)) + Math.abs(i2 - i))) * 2.0d) / ((double) Math.abs(this.mDeceleration)));
            this.mStartTime -= (long) ((int) ((fSqrt - f2) * 1.0E9f));
            double d = i2;
            this.mStart = d;
            this.mCurrentPosition = d;
            this.mVelocity = (int) ((-this.mDeceleration) * fSqrt);
        }

        public void fling(int i, int i2, int i3, int i4, int i5) {
            double splineFlingDistance;
            this.mOver = i5;
            this.mFinished = false;
            double d = i2;
            this.mVelocity = d;
            this.mCurrVelocity = d;
            this.mSplineDuration = 0;
            this.mDuration = 0;
            this.mStartTime = AnimationUtils.currentAnimationTimeNanos();
            double d2 = i;
            this.mStart = d2;
            this.mCurrentPosition = d2;
            if (i > i4 || i < i3) {
                startAfterEdge(i, i3, i4, i2);
                return;
            }
            this.mState = 0;
            if (i2 != 0) {
                int splineFlingDuration = getSplineFlingDuration(i2);
                this.mSplineDuration = splineFlingDuration;
                this.mDuration = splineFlingDuration;
                splineFlingDistance = getSplineFlingDistance(i2);
            } else {
                splineFlingDistance = BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
            }
            int iSignum = (int) (splineFlingDistance * ((double) Math.signum(i2)));
            this.mSplineDistance = iSignum;
            double d3 = i + iSignum;
            this.mFinal = d3;
            double d4 = i3;
            if (d3 < d4) {
                adjustDuration((int) this.mStart, (int) d3, i3);
                this.mFinal = d4;
            }
            double d5 = this.mFinal;
            double d6 = i4;
            if (d5 > d6) {
                adjustDuration((int) this.mStart, (int) d5, i4);
                this.mFinal = d6;
            }
        }

        public final float getCurrVelocity() {
            return (float) this.mCurrVelocity;
        }

        public final int getCurrentPosition() {
            return (int) this.mCurrentPosition;
        }

        public final int getFinal() {
            return (int) this.mFinal;
        }

        public final double getSplineDeceleration(int i) {
            return Math.log((Math.abs(i) * 0.35f) / (this.mFlingFriction * this.mPhysicalCoeff));
        }

        public final double getSplineFlingDistance(int i) {
            double splineDeceleration = getSplineDeceleration(i);
            float f = DECELERATION_RATE;
            return ((double) (this.mFlingFriction * this.mPhysicalCoeff)) * Math.exp((((double) f) / (((double) f) - 1.0d)) * splineDeceleration);
        }

        public final int getSplineFlingDuration(int i) {
            return (int) (Math.exp(getSplineDeceleration(i) / (((double) DECELERATION_RATE) - 1.0d)) * 1000.0d);
        }

        public final int getStart() {
            return (int) this.mStart;
        }

        public final int getState() {
            return this.mState;
        }

        public boolean isAtEquilibrium(double d, double d2) {
            return Math.abs(d - d2) < 1.0d;
        }

        public final boolean isFinished() {
            return this.mFinished;
        }

        public void notifyEdgeReached(int i, int i2, int i3) {
            if (this.mState == 0) {
                this.mOver = i3;
                this.mStartTime = AnimationUtils.currentAnimationTimeNanos();
                startAfterEdge(i, i2, i2, (int) this.mCurrVelocity);
            }
        }

        public final void onEdgeReached() {
            double d = this.mVelocity;
            float f = ((float) d) * ((float) d);
            float fAbs = f / (Math.abs(this.mDeceleration) * 2.0f);
            float fSignum = Math.signum((float) this.mVelocity);
            int i = this.mOver;
            if (fAbs > i) {
                this.mDeceleration = ((-fSignum) * f) / (i * 2.0f);
                fAbs = i;
            }
            this.mOver = (int) fAbs;
            this.mState = 2;
            double d2 = this.mStart;
            double d3 = this.mVelocity;
            if (d3 <= BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
                fAbs = -fAbs;
            }
            this.mFinal = d2 + ((double) ((int) fAbs));
            this.mDuration = -((int) ((d3 * 1000.0d) / ((double) this.mDeceleration)));
        }

        public final void setCurrVelocity(float f) {
            this.mCurrVelocity = f;
        }

        public final void setCurrentPosition(int i) {
            this.mCurrentPosition = i;
        }

        public final void setDuration(int i) {
            this.mDuration = i;
        }

        public final void setFinal(int i) {
            this.mFinal = i;
        }

        public void setFinalPosition(int i) {
            this.mFinal = i;
            this.mFinished = false;
        }

        public final void setFinished(boolean z) {
            this.mFinished = z;
        }

        public final void setStart(int i) {
            this.mStart = i;
        }

        public final void setStartTime(long j) {
            this.mStartTime = j;
        }

        public final void setState(int i) {
            this.mState = i;
        }

        public boolean springback(int i, int i2, int i3) {
            this.mFinished = true;
            double d = i;
            this.mFinal = d;
            this.mStart = d;
            this.mCurrentPosition = d;
            this.mVelocity = BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
            this.mStartTime = AnimationUtils.currentAnimationTimeNanos();
            this.mDuration = 0;
            if (i < i2) {
                startSpringback(i, i2, 0);
            } else if (i > i3) {
                startSpringback(i, i3, 0);
            }
            return !this.mFinished;
        }

        public final void startAfterEdge(int i, int i2, int i3, int i4) {
            if (i > i2 && i < i3) {
                Log.e("OverScroller", "startAfterEdge called from a valid position");
                this.mFinished = true;
                return;
            }
            boolean z = i > i3;
            int i5 = z ? i3 : i2;
            int i6 = i - i5;
            if (i6 * i4 >= 0) {
                startBounceAfterEdge(i, i5, i4);
            } else if (getSplineFlingDistance(i4) > Math.abs(i6)) {
                fling(i, i4, z ? i2 : i, z ? i : i3, this.mOver);
            } else {
                startSpringback(i, i5, i4);
            }
        }

        public final void startBounceAfterEdge(int i, int i2, int i3) {
            this.mDeceleration = getDeceleration(i3 == 0 ? i - i2 : i3);
            fitOnBounceCurve(i, i2, i3);
            onEdgeReached();
        }

        public void startScroll(int i, int i2, int i3) {
            this.mFinished = false;
            double d = i;
            this.mStart = d;
            this.mCurrentPosition = d;
            this.mFinal = i + i2;
            this.mStartTime = AnimationUtils.currentAnimationTimeNanos();
            this.mDuration = i3;
            this.mDeceleration = 0.0f;
            this.mVelocity = BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
        }

        public void startScrollByFling(float f, int i, int i2) {
            this.mFinished = false;
            this.mLastStep = false;
            setState(0);
            double d = f;
            this.mOriginStart = d;
            this.mStart = d;
            this.mCurrentPosition = d;
            this.mFinal = f + i;
            this.mStartTime = AnimationUtils.currentAnimationTimeNanos();
            double d2 = i2;
            this.mVelocity = d2;
            this.mCurrVelocity = d2;
            this.mSpringOperator = new SpringOperator();
            this.mSpringFactor = new double[]{0.99d, 0.4d};
            AnimData animData = new AnimData();
            this.mSpringAnimData = animData;
            animData.startValue = this.mStart;
            animData.targetValue = this.mFinal;
            animData.value = this.mCurrentPosition;
            animData.velocity = this.mCurrVelocity;
            double[] dArr = new double[3];
            this.mSpringParams = dArr;
            this.mSpringOperator.getParameters(this.mSpringFactor, dArr);
        }

        public final void startSpringback(int i, int i2, int i3) {
            this.mFinished = false;
            this.mState = 1;
            double d = i;
            this.mStart = d;
            this.mCurrentPosition = d;
            this.mFinal = i2;
            int i4 = i - i2;
            this.mDeceleration = getDeceleration(i4);
            this.mVelocity = -i4;
            this.mOver = Math.abs(i4);
            this.mDuration = (int) (Math.sqrt((((double) i4) * (-2.0d)) / ((double) this.mDeceleration)) * 1000.0d);
        }

        public boolean update() {
            float f;
            float f2;
            double d;
            double d2;
            long jCurrentAnimationTimeNanos = AnimationUtils.currentAnimationTimeNanos() - this.mStartTime;
            if (jCurrentAnimationTimeNanos == 0) {
                return this.mDuration > 0;
            }
            int i = this.mDuration;
            if (jCurrentAnimationTimeNanos > ((long) i) * FolmeCore.NANOS_TO_MS) {
                return false;
            }
            int i2 = this.mState;
            if (i2 != 0) {
                if (i2 == 1) {
                    float f3 = jCurrentAnimationTimeNanos / i;
                    float f4 = f3 * f3;
                    float fSignum = Math.signum((float) this.mVelocity);
                    int i3 = this.mOver;
                    d = i3 * fSignum * ((3.0f * f4) - ((2.0f * f3) * f4));
                    this.mCurrVelocity = fSignum * i3 * 6.0f * ((-f3) + f4);
                } else if (i2 != 2) {
                    d2 = BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
                } else {
                    float f5 = (float) (jCurrentAnimationTimeNanos / 1.0E9d);
                    double d3 = this.mVelocity;
                    float f6 = this.mDeceleration;
                    this.mCurrVelocity = ((double) (f6 * f5)) + d3;
                    d2 = (d3 * ((double) f5)) + ((double) (((f6 * f5) * f5) / 2.0f));
                }
                this.mCurrentPosition = this.mStart + ((double) ((int) Math.round(d2)));
                return true;
            }
            int i4 = this.mSplineDuration;
            float f7 = jCurrentAnimationTimeNanos / (((long) i4) * FolmeCore.NANOS_TO_MS);
            int i5 = (int) (f7 * 100.0f);
            if (i5 < 100) {
                float f8 = i5 / 100.0f;
                int i6 = i5 + 1;
                float[] fArr = SPLINE_POSITION;
                float f9 = fArr[i5];
                f2 = (fArr[i6] - f9) / ((i6 / 100.0f) - f8);
                f = f9 + ((f7 - f8) * f2);
            } else {
                f = 1.0f;
                f2 = 0.0f;
            }
            int i7 = this.mSplineDistance;
            d = f * i7;
            this.mCurrVelocity = ((f2 * i7) / i4) * 1000.0f;
            d2 = d;
            this.mCurrentPosition = this.mStart + ((double) ((int) Math.round(d2)));
            return true;
        }

        public void updateScroll(float f) {
            double d = this.mStart;
            this.mCurrentPosition = d + Math.round(((double) f) * (this.mFinal - d));
        }
    }

    public static class ViscousFluidInterpolator implements Interpolator {
        public static final float VISCOUS_FLUID_NORMALIZE;
        public static final float VISCOUS_FLUID_OFFSET;

        static {
            float fViscousFluid = 1.0f / viscousFluid(1.0f);
            VISCOUS_FLUID_NORMALIZE = fViscousFluid;
            VISCOUS_FLUID_OFFSET = 1.0f - (fViscousFluid * viscousFluid(1.0f));
        }

        public static float viscousFluid(float f) {
            float f2 = f * 8.0f;
            return f2 < 1.0f ? f2 - (1.0f - ((float) Math.exp(-f2))) : ((1.0f - ((float) Math.exp(1.0f - f2))) * 0.63212055f) + 0.36787945f;
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            float fViscousFluid = VISCOUS_FLUID_NORMALIZE * viscousFluid(f);
            return fViscousFluid > 0.0f ? fViscousFluid + VISCOUS_FLUID_OFFSET : fViscousFluid;
        }
    }

    public OverScroller(Context context) {
        this(context, null);
    }

    public OverScroller(Context context, Interpolator interpolator) {
        this(context, interpolator, true);
    }

    public OverScroller(Context context, Interpolator interpolator, boolean z) {
        if (interpolator == null) {
            this.mInterpolator = new ViscousFluidInterpolator();
        } else {
            this.mInterpolator = interpolator;
        }
        this.mFlywheel = z;
        this.mScrollerX = new DynamicScroller(context);
        this.mScrollerY = new DynamicScroller(context);
    }

    public void abortAnimation() {
        this.mScrollerX.finish();
        this.mScrollerY.finish();
    }

    public boolean computeScrollOffset() {
        if (isFinished()) {
            return false;
        }
        int i = this.mMode;
        if (i == 0) {
            long jCurrentAnimationTimeNanos = AnimationUtils.currentAnimationTimeNanos() - this.mScrollerX.mStartTime;
            int i2 = this.mScrollerX.mDuration;
            if (jCurrentAnimationTimeNanos < i2) {
                float interpolation = this.mInterpolator.getInterpolation(jCurrentAnimationTimeNanos / i2);
                this.mScrollerX.updateScroll(interpolation);
                this.mScrollerY.updateScroll(interpolation);
            } else {
                abortAnimation();
            }
        } else if (i == 1) {
            if (!this.mScrollerX.mFinished && !this.mScrollerX.update() && !this.mScrollerX.continueWhenFinished()) {
                this.mScrollerX.finish();
            }
            if (!this.mScrollerY.mFinished && !this.mScrollerY.update() && !this.mScrollerY.continueWhenFinished()) {
                this.mScrollerY.finish();
            }
        } else if (i == 2) {
            return this.mScrollerY.computeScrollOffset() || this.mScrollerX.computeScrollOffset();
        }
        return true;
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        fling(i, i2, i3, i4, i5, i6, i7, i8, 0, 0);
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        if (this.mFlywheel && !isFinished()) {
            float f = (float) this.mScrollerX.mCurrVelocity;
            float f2 = (float) this.mScrollerY.mCurrVelocity;
            float f3 = i3;
            if (Math.signum(f3) == Math.signum(f)) {
                float f4 = i4;
                if (Math.signum(f4) == Math.signum(f2)) {
                    i3 = (int) (f3 + f);
                    i4 = (int) (f4 + f2);
                }
            }
        }
        this.mMode = 1;
        this.mScrollerX.fling(i, i3, i5, i6, i9);
        this.mScrollerY.fling(i2, i4, i7, i8, i10);
    }

    public final void forceFinished(boolean z) {
        this.mScrollerX.mFinished = this.mScrollerY.mFinished = z;
    }

    public float getCurrVelocity() {
        return (float) Math.hypot(this.mScrollerX.mCurrVelocity, this.mScrollerY.mCurrVelocity);
    }

    public float getCurrVelocityX() {
        return (float) this.mScrollerX.mCurrVelocity;
    }

    public float getCurrVelocityY() {
        return (float) this.mScrollerY.mCurrVelocity;
    }

    public final int getCurrX() {
        return (int) this.mScrollerX.mCurrentPosition;
    }

    public final int getCurrY() {
        return (int) this.mScrollerY.mCurrentPosition;
    }

    public final int getFinalX() {
        return (int) this.mScrollerX.mFinal;
    }

    public final int getFinalY() {
        return (int) this.mScrollerY.mFinal;
    }

    public int getMode() {
        return this.mMode;
    }

    public final int getStartX() {
        return (int) this.mScrollerX.mStart;
    }

    public final int getStartY() {
        return (int) this.mScrollerY.mStart;
    }

    public final boolean isFinished() {
        return this.mScrollerX.mFinished && this.mScrollerY.mFinished;
    }

    public void notifyHorizontalEdgeReached(int i, int i2, int i3) {
        this.mScrollerX.notifyEdgeReached(i, i2, i3);
    }

    public void notifyVerticalEdgeReached(int i, int i2, int i3) {
        this.mScrollerY.notifyEdgeReached(i, i2, i3);
    }

    public final void resetPosition() {
        resetScrollerPosition(this.mScrollerX);
        resetScrollerPosition(this.mScrollerY);
    }

    public final void resetScrollerPosition(SplineOverScroller splineOverScroller) {
        splineOverScroller.setStart(0);
        splineOverScroller.setFinal(0);
        splineOverScroller.setCurrentPosition(0);
    }

    public boolean springBack(int i, int i2, int i3, int i4, int i5, int i6) {
        this.mMode = 1;
        return this.mScrollerX.springback(i, i3, i4) || this.mScrollerY.springback(i2, i5, i6);
    }

    public void startScroll(int i, int i2, int i3, int i4) {
        startScroll(i, i2, i3, i4, 250);
    }

    public void startScroll(int i, int i2, int i3, int i4, int i5) {
        this.mMode = 0;
        this.mScrollerX.startScroll(i, i3, i5);
        this.mScrollerY.startScroll(i2, i4, i5);
    }

    public void startScrollByFling(int i, int i2, int i3, int i4, int i5, int i6) {
        this.mMode = 2;
        this.mScrollerX.startScrollByFling(i, i3, i5);
        this.mScrollerY.startScrollByFling(i2, i4, i6);
    }
}
