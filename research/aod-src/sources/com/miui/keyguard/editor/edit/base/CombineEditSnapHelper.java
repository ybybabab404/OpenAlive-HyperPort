package com.miui.keyguard.editor.edit.base;

import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.miui.aod.components.view.BaseStyleSelectView;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import miuix.animation.utils.SpringInterpolator;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: CombineEditSnapHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class CombineEditSnapHelper {
    public static final Companion Companion = new Companion(null);
    public static final float DECELERATION_RATE = (float) (Math.log(0.78d) / Math.log(0.9d));
    public static final float[] SPLINE_POSITION = new float[101];
    public static final float[] SPLINE_TIME = new float[101];
    public final WeakReference combineEditViewWeak;
    public final CombineSpringTouchManager touchManager;
    public final CombineViewFlinger viewFling;

    /* JADX INFO: compiled from: CombineEditSnapHelper.kt */
    @Metadata
    public final class CombineViewFlinger implements Runnable {
        public final int IDLE;
        public final int SPLINE;
        public int currentDx;
        public int currentDy;
        public Interpolator currentInterpolator;
        public final Interpolator defaultInterpolator;
        public int duration;
        public int dx;
        public int dy;
        public float mCurrVelocity;
        public int mCurrentPosition;
        public float mDeceleration;
        public int mFinal;
        public boolean mFinished;
        public int mLastFlingX;
        public int mMode;
        public int mOver;
        public final float mPhysicalCoeff;
        public int mSplineDistance;
        public int mSplineDuration;
        public int mStart;
        public int mState;
        public int mVelocity;
        public final float ppi;
        public long startTime;
        public final int SCROLL = 1;
        public final int FLING = 2;
        public final int CUBIC = 1;
        public final int BALLISTIC = 2;
        public final float mFlingFriction = ViewConfiguration.getScrollFriction();

        public CombineViewFlinger() {
            float density = CombineEditSnapHelper.this.touchManager.getDensity() * 160.0f;
            this.ppi = density;
            this.mPhysicalCoeff = density * 386.0878f * 0.84f;
            this.mMode = this.IDLE;
            this.mState = this.SPLINE;
            this.defaultInterpolator = new SpringInterpolator(1.0f, 0.4f);
        }

        public static final Unit run$lambda$0(CombineViewFlinger combineViewFlinger, CombineEditSnapHelper combineEditSnapHelper) {
            Log.d("CombineEditSnapHelper", "cancel run");
            combineViewFlinger.mMode = combineViewFlinger.IDLE;
            combineEditSnapHelper.touchManager.updateScrollState(0);
            combineViewFlinger.resetFling();
            return Unit.INSTANCE;
        }

        public static final Unit run$lambda$1(CombineViewFlinger combineViewFlinger, CombineEditSnapHelper combineEditSnapHelper, CombineEditView combineEditView, Function0 function0) {
            int i = combineViewFlinger.mCurrentPosition;
            int i2 = -(i - combineViewFlinger.mLastFlingX);
            int horizontalRealOffset = combineEditSnapHelper.touchManager.getHorizontalRealOffset(i2);
            combineEditView.offsetLeftAndRight(horizontalRealOffset);
            Log.d("CombineEditSnapHelper", "run: unconsumedX=" + i2 + " realOffset=" + horizontalRealOffset + " mLastFlingX=" + combineViewFlinger.mLastFlingX + " x=" + i + " mFinished=" + combineViewFlinger.mFinished + " mState=" + combineViewFlinger.mState);
            combineViewFlinger.mLastFlingX = i;
            if (horizontalRealOffset != i2) {
                combineViewFlinger.mFinished = true;
            }
            if (combineViewFlinger.mFinished) {
                function0.invoke();
            } else {
                combineViewFlinger.postOnAnimation(combineEditView);
            }
            return Unit.INSTANCE;
        }

        public static /* synthetic */ void smoothScrollBy$default(CombineViewFlinger combineViewFlinger, int i, int i2, int i3, Interpolator interpolator, int i4, Object obj) {
            if ((i4 & 4) != 0) {
                i3 = Integer.MIN_VALUE;
            }
            if ((i4 & 8) != 0) {
                interpolator = combineViewFlinger.defaultInterpolator;
            }
            combineViewFlinger.smoothScrollBy(i, i2, i3, interpolator);
        }

        public final boolean computeFlingOffset() {
            double d;
            float f;
            float f2;
            if (this.mFinished) {
                return false;
            }
            long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis() - this.startTime;
            if (jCurrentAnimationTimeMillis == 0) {
                return this.duration > 0;
            }
            int i = this.duration;
            if (jCurrentAnimationTimeMillis > i) {
                return false;
            }
            int i2 = this.mState;
            if (i2 == this.SPLINE) {
                float f3 = jCurrentAnimationTimeMillis / this.mSplineDuration;
                float f4 = 100;
                int i3 = (int) (f4 * f3);
                if (i3 < 100) {
                    float f5 = i3 / f4;
                    int i4 = i3 + 1;
                    float f6 = i4 / f4;
                    float f7 = CombineEditSnapHelper.SPLINE_POSITION[i3];
                    f2 = (CombineEditSnapHelper.SPLINE_POSITION[i4] - f7) / (f6 - f5);
                    f = f7 + ((f3 - f5) * f2);
                } else {
                    f = 1.0f;
                    f2 = 0.0f;
                }
                int i5 = this.mSplineDistance;
                d = f * i5;
                this.mCurrVelocity = ((f2 * i5) / this.mSplineDuration) * 1000.0f;
            } else if (i2 == this.BALLISTIC) {
                float f8 = jCurrentAnimationTimeMillis / 1000.0f;
                int i6 = this.mVelocity;
                float f9 = this.mDeceleration;
                this.mCurrVelocity = i6 + (f9 * f8);
                d = (i6 * f8) + (((f9 * f8) * f8) / 2.0f);
            } else if (i2 == this.CUBIC) {
                float f10 = jCurrentAnimationTimeMillis / i;
                float f11 = f10 * f10;
                float fSignum = (float) Math.signum(this.mVelocity);
                int i7 = this.mOver;
                this.mCurrVelocity = fSignum * i7 * 6.0f * ((-f10) + f11);
                d = i7 * fSignum * ((3.0f * f11) - ((2.0f * f10) * f11));
            } else {
                d = BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
            }
            this.mCurrentPosition = this.mStart + ((int) Math.round(d));
            return true;
        }

        public final int computeScrollDuration(int i, int i2) {
            CombineEditView combineEditView = (CombineEditView) CombineEditSnapHelper.this.combineEditViewWeak.get();
            if (combineEditView == null) {
                return 0;
            }
            int iAbs = (int) Math.abs(i);
            int iAbs2 = (int) Math.abs(i2);
            boolean z = iAbs > iAbs2;
            int width = z ? combineEditView.getWidth() : combineEditView.getHeight();
            if (!z) {
                iAbs = iAbs2;
            }
            return Math.min((int) (((iAbs / width) + 1) * 300), 2000);
        }

        public final int computeScrollXOffset() {
            int iRound;
            int i;
            long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis() - this.startTime;
            int i2 = 0;
            boolean z = Math.abs(this.currentDx) < Math.abs(this.dx);
            Interpolator interpolator = this.currentInterpolator;
            Intrinsics.checkNotNull(interpolator);
            float fCoerceAtMost = RangesKt.coerceAtMost(interpolator.getInterpolation(jCurrentAnimationTimeMillis / this.duration), 1.0f);
            if (jCurrentAnimationTimeMillis >= this.duration || !z) {
                if (z) {
                    iRound = this.dx;
                    i = this.currentDx;
                }
                this.currentDx += i2;
                return i2;
            }
            iRound = Math.round(fCoerceAtMost * this.dx);
            i = this.currentDx;
            i2 = iRound - i;
            this.currentDx += i2;
            return i2;
        }

        public final boolean continueWhenFinished() {
            int i = this.mState;
            if (i == this.SPLINE) {
                Log.d("CombineEditSnapHelper", "continueWhenFinished: duration=" + this.duration + " mSplineDuration=" + this.mSplineDuration);
                if (this.duration >= this.mSplineDuration) {
                    Log.d("CombineEditSnapHelper", "SPLINE finish");
                    return false;
                }
                int i2 = this.mFinal;
                this.mStart = i2;
                this.mCurrentPosition = i2;
                int i3 = (int) this.mCurrVelocity;
                this.mVelocity = i3;
                this.mDeceleration = CombineEditSnapHelper.Companion.getDeceleration(i3);
                this.startTime += (long) this.duration;
                onEdgeReached();
            } else if (i == this.BALLISTIC) {
                this.startTime += (long) this.duration;
                startSpringBack(this.mFinal, this.mStart, 0);
            } else if (i == this.CUBIC) {
                Log.d("CombineEditSnapHelper", "CUBIC finish");
                return false;
            }
            computeFlingOffset();
            return true;
        }

        public final double getSplineDeceleration(int i) {
            return Math.log((((double) 0.35f) * Math.abs(i)) / ((double) (this.mFlingFriction * this.mPhysicalCoeff)));
        }

        public final double getSplineFlingDistance(int i) {
            return ((double) (this.mFlingFriction * this.mPhysicalCoeff)) * Math.exp((((double) CombineEditSnapHelper.DECELERATION_RATE) / (((double) CombineEditSnapHelper.DECELERATION_RATE) - 1.0d)) * getSplineDeceleration(i));
        }

        public final boolean isScrollFinish() {
            return Math.abs(this.currentDx) >= Math.abs(this.dx);
        }

        public final void onEdgeReached() {
            int i = this.mVelocity;
            float f = i * i;
            float fAbs = (float) (((double) f) / (((double) 2.0f) * Math.abs(this.mDeceleration)));
            float fSignum = (float) Math.signum(this.mVelocity);
            int i2 = this.mOver;
            if (fAbs > i2) {
                this.mDeceleration = ((-fSignum) * f) / (i2 * 2.0f);
                fAbs = i2;
            }
            this.mOver = (int) fAbs;
            this.mState = this.BALLISTIC;
            int i3 = this.mStart;
            int i4 = this.mVelocity;
            if (i4 <= 0) {
                fAbs = -fAbs;
            }
            this.mFinal = i3 + ((int) fAbs);
            int i5 = -((int) ((i4 * 1000.0f) / this.mDeceleration));
            this.duration = i5;
            Log.d("CombineEditSnapHelper", "onEdgeReached: duration=" + i5 + " mSplineDuration=" + this.mSplineDuration);
        }

        public final void postOnAnimation(View view) {
            view.removeCallbacks(this);
            view.postOnAnimation(this);
        }

        public final void resetFling() {
            this.mSplineDuration = 0;
            this.mVelocity = 0;
            this.mCurrVelocity = 0.0f;
            this.mStart = 0;
            this.mCurrentPosition = 0;
            this.mSplineDistance = 0;
            this.mFinal = 0;
            this.mLastFlingX = 0;
            this.mFinished = true;
            this.mDeceleration = 0.0f;
            this.mOver = 0;
        }

        public final void resetScroll() {
            this.duration = 0;
            this.dx = 0;
            this.dy = 0;
            this.currentDx = 0;
            this.currentDy = 0;
        }

        @Override // java.lang.Runnable
        public void run() {
            final CombineEditView combineEditView = (CombineEditView) CombineEditSnapHelper.this.combineEditViewWeak.get();
            if (combineEditView == null) {
                return;
            }
            int i = this.mMode;
            if (i == this.SCROLL) {
                combineEditView.offsetLeftAndRight(computeScrollXOffset());
                if (!isScrollFinish()) {
                    postOnAnimation(combineEditView);
                    return;
                }
                this.mMode = this.IDLE;
                CombineEditSnapHelper.this.touchManager.updateScrollState(0);
                resetScroll();
                return;
            }
            if (i == this.FLING) {
                final CombineEditSnapHelper combineEditSnapHelper = CombineEditSnapHelper.this;
                final Function0 function0 = new Function0() { // from class: com.miui.keyguard.editor.edit.base.CombineEditSnapHelper$CombineViewFlinger$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return CombineEditSnapHelper.CombineViewFlinger.run$lambda$0(this.f$0, combineEditSnapHelper);
                    }
                };
                final CombineEditSnapHelper combineEditSnapHelper2 = CombineEditSnapHelper.this;
                Function0 function1 = new Function0() { // from class: com.miui.keyguard.editor.edit.base.CombineEditSnapHelper$CombineViewFlinger$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return CombineEditSnapHelper.CombineViewFlinger.run$lambda$1(this.f$0, combineEditSnapHelper2, combineEditView, function0);
                    }
                };
                if (computeFlingOffset()) {
                    function1.invoke();
                } else if (continueWhenFinished()) {
                    function1.invoke();
                } else {
                    function0.invoke();
                }
            }
        }

        public final void smoothScrollBy(int i, int i2, int i3, Interpolator interpolator) {
            Intrinsics.checkNotNullParameter(interpolator, "interpolator");
            CombineEditView combineEditView = (CombineEditView) CombineEditSnapHelper.this.combineEditViewWeak.get();
            if (combineEditView == null) {
                return;
            }
            stop();
            this.mMode = this.SCROLL;
            this.mFinished = false;
            this.startTime = AnimationUtils.currentAnimationTimeMillis();
            this.dx = i;
            this.dy = i2;
            this.currentDx = 0;
            this.currentDy = 0;
            this.currentInterpolator = interpolator;
            if (i3 == Integer.MIN_VALUE) {
                i3 = computeScrollDuration(i, i2);
            }
            this.duration = i3;
            CombineEditSnapHelper.this.touchManager.updateScrollState(2);
            Log.d("CombineEditSnapHelper", "smoothScrollBy: dx=" + this.dx + " dy=" + this.dy + " mDuration=" + this.duration + " startTime=" + this.startTime);
            postOnAnimation(combineEditView);
        }

        public final void startSpringBack(int i, int i2, int i3) {
            this.mFinished = false;
            this.mState = this.CUBIC;
            this.mStart = i;
            this.mCurrentPosition = i;
            this.mFinal = i2;
            int i4 = i - i2;
            this.mDeceleration = CombineEditSnapHelper.Companion.getDeceleration(i4);
            this.mVelocity = -i4;
            this.mOver = Math.abs(i4);
            this.duration = (int) (Math.sqrt((((double) i4) * (-2.0d)) / ((double) this.mDeceleration)) * 1000.0d);
        }

        public final void stop() {
            int i = this.mMode;
            int i2 = this.IDLE;
            if (i == i2) {
                return;
            }
            this.mMode = i2;
            CombineEditView combineEditView = (CombineEditView) CombineEditSnapHelper.this.combineEditViewWeak.get();
            if (combineEditView != null) {
                combineEditView.removeCallbacks(this);
            }
        }
    }

    /* JADX INFO: compiled from: CombineEditSnapHelper.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final float getDeceleration(int i) {
            return i > 0 ? -2000.0f : 2000.0f;
        }

        public final void initSnapHelper() {
            float f;
            float f2;
            float f3;
            float f4;
            float f5;
            float f6;
            float f7;
            float f8;
            float f9;
            float f10 = 0.0f;
            int i = 0;
            float f11 = 0.0f;
            while (true) {
                float f12 = 1.0f;
                if (i >= 100) {
                    CombineEditSnapHelper.SPLINE_POSITION[100] = 1.0f;
                    CombineEditSnapHelper.SPLINE_TIME[100] = 1.0f;
                    return;
                }
                float f13 = i / 100;
                float f14 = 1.0f;
                while (true) {
                    f = ((f14 - f10) / 2.0f) + f10;
                    f2 = f12 - f;
                    f3 = f * 3.0f * f2;
                    f4 = f * f * f;
                    float f15 = (((f2 * 0.175f) + (f * 0.35000002f)) * f3) + f4;
                    f12 = f12;
                    f5 = f13;
                    if (Math.abs(f15 - f13) < 1.0E-5d) {
                        break;
                    }
                    if (f15 > f5) {
                        f14 = f;
                    } else {
                        f10 = f;
                    }
                    f13 = f5;
                }
                CombineEditSnapHelper.SPLINE_POSITION[i] = (f3 * ((f2 * 0.5f) + f)) + f4;
                float f16 = f12;
                while (true) {
                    f6 = ((f16 - f11) / 2.0f) + f11;
                    f7 = f12 - f6;
                    f8 = f6 * 3.0f * f7;
                    f9 = f6 * f6 * f6;
                    float f17 = (((f7 * 0.5f) + f6) * f8) + f9;
                    if (Math.abs(f17 - f5) >= 1.0E-5d) {
                        if (f17 > f5) {
                            f16 = f6;
                        } else {
                            f11 = f6;
                        }
                    }
                }
                CombineEditSnapHelper.SPLINE_TIME[i] = (f8 * ((f7 * 0.175f) + (f6 * 0.35000002f))) + f9;
                i++;
            }
        }
    }

    public CombineEditSnapHelper(WeakReference combineEditViewWeak, CombineSpringTouchManager touchManager) {
        Intrinsics.checkNotNullParameter(combineEditViewWeak, "combineEditViewWeak");
        Intrinsics.checkNotNullParameter(touchManager, "touchManager");
        this.combineEditViewWeak = combineEditViewWeak;
        this.touchManager = touchManager;
        this.viewFling = new CombineViewFlinger();
    }

    public final Point calculateDistanceToFinalSnap(EditViewHolder editViewHolder) {
        Point center;
        Point point = new Point();
        CombineEditView combineEditView = (CombineEditView) this.combineEditViewWeak.get();
        if (combineEditView != null && editViewHolder != null && (center = editViewHolder.getCenter()) != null) {
            point.set(combineEditView.getCenter().x - center.x, 0);
        }
        return point;
    }

    public final EditViewHolder findSnapViewHolder() {
        Point center;
        int iAbs;
        CombineEditView combineEditView = (CombineEditView) this.combineEditViewWeak.get();
        if (combineEditView == null) {
            return null;
        }
        EditViewHolder[] allEditViewHolder = combineEditView.getAllEditViewHolder();
        Point center2 = combineEditView.getCenter();
        int i = Integer.MAX_VALUE;
        EditViewHolder editViewHolder = null;
        for (EditViewHolder editViewHolder2 : allEditViewHolder) {
            if (editViewHolder2 != null && (center = editViewHolder2.getCenter()) != null && (iAbs = Math.abs(center.x - center2.x)) < i) {
                editViewHolder = editViewHolder2;
                i = iAbs;
            }
        }
        Log.d("CombineEditSnapHelper", "findSnapViewHolder: closestHolder viewType=" + (editViewHolder != null ? Integer.valueOf(editViewHolder.getViewType()) : null));
        return editViewHolder;
    }

    public final double getFlingDistance(int i) {
        return this.viewFling.getSplineFlingDistance(i);
    }

    public final void snapToSelectView(int i) {
        EditViewHolder[] allEditViewHolder;
        EditViewHolder editViewHolder;
        Point pointCalculateDistanceToFinalSnap;
        int i2;
        CombineEditView combineEditView = (CombineEditView) this.combineEditViewWeak.get();
        if (combineEditView == null || (allEditViewHolder = combineEditView.getAllEditViewHolder()) == null || (editViewHolder = allEditViewHolder[i]) == null || (i2 = (pointCalculateDistanceToFinalSnap = calculateDistanceToFinalSnap(editViewHolder)).x) == 0) {
            return;
        }
        CombineViewFlinger.smoothScrollBy$default(this.viewFling, i2, pointCalculateDistanceToFinalSnap.y, 0, null, 12, null);
    }

    public final boolean snapToTargetExistingView() {
        Point pointCalculateDistanceToFinalSnap;
        int i;
        EditViewHolder editViewHolderFindSnapViewHolder = findSnapViewHolder();
        if (editViewHolderFindSnapViewHolder == null || (i = (pointCalculateDistanceToFinalSnap = calculateDistanceToFinalSnap(editViewHolderFindSnapViewHolder)).x) == 0) {
            return false;
        }
        CombineViewFlinger.smoothScrollBy$default(this.viewFling, i, pointCalculateDistanceToFinalSnap.y, 0, null, 12, null);
        return true;
    }

    public final void stopFling() {
        this.viewFling.stop();
    }
}
