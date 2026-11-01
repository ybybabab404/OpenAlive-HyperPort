package androidx.core.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import androidx.annotation.VisibleForTesting;

/* JADX INFO: loaded from: classes.dex */
public class DifferentialMotionFlingController {
    public final Context mContext;
    public final int[] mFlingVelocityThresholds;
    public float mLastFlingVelocity;
    public int mLastProcessedAxis;
    public int mLastProcessedDeviceId;
    public int mLastProcessedSource;
    public final DifferentialMotionFlingTarget mTarget;
    public final DifferentialVelocityProvider mVelocityProvider;
    public final FlingVelocityThresholdCalculator mVelocityThresholdCalculator;
    public VelocityTracker mVelocityTracker;

    @VisibleForTesting
    public interface DifferentialVelocityProvider {
        float getCurrentVelocity(VelocityTracker velocityTracker, MotionEvent motionEvent, int i);
    }

    @VisibleForTesting
    public interface FlingVelocityThresholdCalculator {
        void calculateFlingVelocityThresholds(Context context, int[] iArr, MotionEvent motionEvent, int i);
    }

    public DifferentialMotionFlingController(Context context, DifferentialMotionFlingTarget differentialMotionFlingTarget) {
        this(context, differentialMotionFlingTarget, new FlingVelocityThresholdCalculator() { // from class: androidx.core.view.DifferentialMotionFlingController$$ExternalSyntheticLambda0
            @Override // androidx.core.view.DifferentialMotionFlingController.FlingVelocityThresholdCalculator
            public final void calculateFlingVelocityThresholds(Context context2, int[] iArr, MotionEvent motionEvent, int i) {
                DifferentialMotionFlingController.calculateFlingVelocityThresholds(context2, iArr, motionEvent, i);
            }
        }, new DifferentialVelocityProvider() { // from class: androidx.core.view.DifferentialMotionFlingController$$ExternalSyntheticLambda1
            @Override // androidx.core.view.DifferentialMotionFlingController.DifferentialVelocityProvider
            public final float getCurrentVelocity(VelocityTracker velocityTracker, MotionEvent motionEvent, int i) {
                return DifferentialMotionFlingController.getCurrentVelocity(velocityTracker, motionEvent, i);
            }
        });
    }

    public DifferentialMotionFlingController(Context context, DifferentialMotionFlingTarget differentialMotionFlingTarget, FlingVelocityThresholdCalculator flingVelocityThresholdCalculator, DifferentialVelocityProvider differentialVelocityProvider) {
        this.mLastProcessedAxis = -1;
        this.mLastProcessedSource = -1;
        this.mLastProcessedDeviceId = -1;
        this.mFlingVelocityThresholds = new int[]{Integer.MAX_VALUE, 0};
        this.mContext = context;
        this.mTarget = differentialMotionFlingTarget;
        this.mVelocityThresholdCalculator = flingVelocityThresholdCalculator;
        this.mVelocityProvider = differentialVelocityProvider;
    }

    public static void calculateFlingVelocityThresholds(Context context, int[] iArr, MotionEvent motionEvent, int i) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        iArr[0] = ViewConfigurationCompat.getScaledMinimumFlingVelocity(context, viewConfiguration, motionEvent.getDeviceId(), i, motionEvent.getSource());
        iArr[1] = ViewConfigurationCompat.getScaledMaximumFlingVelocity(context, viewConfiguration, motionEvent.getDeviceId(), i, motionEvent.getSource());
    }

    public static float getCurrentVelocity(VelocityTracker velocityTracker, MotionEvent motionEvent, int i) {
        VelocityTrackerCompat.addMovement(velocityTracker, motionEvent);
        VelocityTrackerCompat.computeCurrentVelocity(velocityTracker, 1000);
        return VelocityTrackerCompat.getAxisVelocity(velocityTracker, i);
    }

    public final boolean calculateFlingVelocityThresholds(MotionEvent motionEvent, int i) {
        int source = motionEvent.getSource();
        int deviceId = motionEvent.getDeviceId();
        if (this.mLastProcessedSource == source && this.mLastProcessedDeviceId == deviceId && this.mLastProcessedAxis == i) {
            return false;
        }
        this.mVelocityThresholdCalculator.calculateFlingVelocityThresholds(this.mContext, this.mFlingVelocityThresholds, motionEvent, i);
        this.mLastProcessedSource = source;
        this.mLastProcessedDeviceId = deviceId;
        this.mLastProcessedAxis = i;
        return true;
    }

    public final float getCurrentVelocity(MotionEvent motionEvent, int i) {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        return this.mVelocityProvider.getCurrentVelocity(this.mVelocityTracker, motionEvent, i);
    }

    public void onMotionEvent(MotionEvent motionEvent, int i) {
        boolean zCalculateFlingVelocityThresholds = calculateFlingVelocityThresholds(motionEvent, i);
        if (this.mFlingVelocityThresholds[0] == Integer.MAX_VALUE) {
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.mVelocityTracker = null;
                return;
            }
            return;
        }
        float currentVelocity = getCurrentVelocity(motionEvent, i) * this.mTarget.getScaledScrollFactor();
        float fSignum = Math.signum(currentVelocity);
        if (zCalculateFlingVelocityThresholds || (fSignum != Math.signum(this.mLastFlingVelocity) && fSignum != 0.0f)) {
            this.mTarget.stopDifferentialMotionFling();
        }
        float fAbs = Math.abs(currentVelocity);
        int[] iArr = this.mFlingVelocityThresholds;
        if (fAbs < iArr[0]) {
            return;
        }
        int i2 = iArr[1];
        float fMax = Math.max(-i2, Math.min(currentVelocity, i2));
        this.mLastFlingVelocity = this.mTarget.startDifferentialMotionFling(fMax) ? fMax : 0.0f;
    }
}
