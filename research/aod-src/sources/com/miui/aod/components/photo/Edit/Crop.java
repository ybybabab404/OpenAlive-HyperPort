package com.miui.aod.components.photo.Edit;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewConfiguration;
import androidx.annotation.Keep;
import com.miui.aod.R;
import com.miui.aod.components.photo.OneShotAnimateListener;
import com.miui.aod.components.view.EditorView;

/* JADX INFO: loaded from: classes.dex */
public class Crop extends EditorView.Plugin {
    private static final String ANIMATOR_ROTATE = "rotate";
    private static final String ANIMATOR_SCALE = "scale";
    private static final String ANIMATOR_WINDOW = "window";
    private static final float CROP_AREA_MIN_PIXELS = 32.0f;
    private static final int CROP_AREA_MIN_SIZE = 200;
    public static final int CROP_SIZE_FREE = 0;
    public static final int CROP_SIZE_ORIGINAL = -1;
    public static final int CROP_SIZE_SCREEN = -2;
    private static final long DEFAULT_ROTATE_ANIMATOR_DURATION = 100;
    private static final int EDIT_FINISH_TIMEOUT = 1000;
    private static final int GUIDE_LINE_ALPHA_MAX = 255;
    private static final int GUIDE_LINE_ALPHA_MIN = 0;
    private static final int GUIDE_LINE_ANIM_DURATION = 300;
    private static final int GUIDE_LINE_FINISH_TIMEOUT = 1300;
    private static final int MASK_COLOR_ALPHA_DOWN = 127;
    private static final int MASK_COLOR_ALPHA_UP = 255;
    private static final int MASK_COLOR_ANIM_DURATION = 400;
    private static final int RESIZE_EDGE_BOTTOM = 4096;
    private static final int RESIZE_EDGE_HORIZONTAL = 257;
    private static final int RESIZE_EDGE_LEFT = 1;
    private static final int RESIZE_EDGE_RIGHT = 256;
    private static final int RESIZE_EDGE_TOP = 16;
    private static final int RESIZE_EDGE_VERTICAL = 4112;
    private static final String TAG = "Crop";
    private float mAdjustDegree;
    private boolean mAlwaysInTapRegion;
    private AnimatorSet mAnimatorSet;
    private ValueAnimator mCanvasAnimator;
    private int mGuideLineAlpha;
    private ObjectAnimator mGuideLineAnimator;
    private final float mGuideLineSize;
    private IOnTabListener mIOnTabListener;
    private float mInitialDegree;
    private Matrix mInitialMatrix;
    private final int mMaskColor;
    private ObjectAnimator mMaskColorAnimator;
    private ObjectAnimator mMaskUpColorAnimator;
    private int mMsgCropFinish;
    private int mMsgGuideLineFinish;
    private OnCropChangedListener mOnCropChangedListener;
    private int mRotateDegree;
    private PropertyValuesHolder mRotateValues;
    private final ScaleGestureDetector mScaleGestureDetector;
    private PropertyValuesHolder mScaleValues;
    private int mTouchSlop;
    private final TranslateDetector mTranslateDetector;
    private ValueAnimator mWindowAnimator;
    private PropertyValuesHolder mWindowValues;
    private final RectF mCropArea = new RectF();
    private final Matrix mUnmodifiedMatrix = new Matrix();
    private final Rect mBgPadding = new Rect();
    private final Rect mBgBounds = new Rect();
    private final Rect mClipBounds = new Rect();
    private final Rect mCropPadding = new Rect();
    private AspectRatio mAspectRatio = AspectRatio.RATIO_NONE;
    private EventState mEventState = EventState.IDLE;
    private boolean mCropAreaChanged = false;
    private final RectF mTouchBounds = new RectF();
    private final PointF mOffset = new PointF();
    private final RectF mInvertArea = new RectF();
    private final Matrix mInvertMatrix = new Matrix();
    private final Matrix mMatrix = new Matrix();
    private final RectF mRect = new RectF();
    private final Paint mPaint = new Paint();
    private final PointF mTabPoint = new PointF();
    private boolean mInnerTab = false;
    private boolean mCropable = true;
    private boolean mStartGuideLine = true;
    private GuideLine mGuideLineMode = GuideLine.CROP;
    private boolean mFirstIn = true;
    private final Runnable mEditFinished = new Runnable() { // from class: com.miui.aod.components.photo.Edit.Crop$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.animCropAreaChanged(null);
        }
    };
    private final ValueAnimator.AnimatorUpdateListener mWindowUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.aod.components.photo.Edit.Crop.3
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            Crop.this.mCropArea.set((RectF) Crop.this.mWindowAnimator.getAnimatedValue());
            Crop.this.invalidate();
        }
    };
    private final Animator.AnimatorListener mPerformCropFinish = new Animator.AnimatorListener() { // from class: com.miui.aod.components.photo.Edit.Crop.4
        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            Crop.this.performCropFinished();
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            Crop.this.getEventHandler().removeMessages(Crop.this.mMsgCropFinish);
        }
    };
    private final EditorView.Plugin.BoundsFixCallback mBoundsFixCallback = new EditorView.Plugin.BoundsFixCallback() { // from class: com.miui.aod.components.photo.Edit.Crop$$ExternalSyntheticLambda1
        @Override // com.miui.aod.components.view.EditorView.Plugin.BoundsFixCallback
        public final void onDone() {
            this.f$0.postCropFinish();
        }
    };
    private final Runnable mGuideLineFinished = new Runnable() { // from class: com.miui.aod.components.photo.Edit.Crop.5
        @Override // java.lang.Runnable
        public void run() {
            Crop.this.animGuideLine(false);
            if (Crop.this.mOnCropChangedListener != null) {
                Crop.this.mOnCropChangedListener.changeRotationState(true);
            }
        }
    };
    private int mMaskColorAlpha = 255;

    /* JADX INFO: renamed from: com.miui.aod.components.photo.Edit.Crop$7, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass7 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$aod$components$photo$Edit$Crop$EventState;

        static {
            int[] iArr = new int[EventState.values().length];
            $SwitchMap$com$miui$aod$components$photo$Edit$Crop$EventState = iArr;
            try {
                iArr[EventState.ANIMATOR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$aod$components$photo$Edit$Crop$EventState[EventState.SKIP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$aod$components$photo$Edit$Crop$EventState[EventState.TRANSLATE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$aod$components$photo$Edit$Crop$EventState[EventState.SCALE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$aod$components$photo$Edit$Crop$EventState[EventState.IDLE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public static class AspectRatio {
        private static final AspectRatio RATIO_NONE = new AspectRatio(0.0f, 0.0f);
        private final float mHeight;
        private final float mWidth;

        public AspectRatio(float f, float f2) {
            this.mWidth = f;
            this.mHeight = f2;
        }

        public float getHeight(float f) {
            return (f / this.mWidth) * this.mHeight;
        }

        public float getWidth(float f) {
            return (f / this.mHeight) * this.mWidth;
        }
    }

    public enum EventState {
        IDLE,
        RESIZE,
        SCALE,
        TRANSLATE,
        ANIMATOR,
        ROTATION,
        SKIP
    }

    public enum GuideLine {
        PREVIEW(0),
        CROP(3),
        ROTATE(9);

        final int mCount;

        GuideLine(int i) {
            this.mCount = i;
        }
    }

    public interface IOnTabListener {
        void onTab(boolean z);
    }

    public interface OnCropChangedListener {
        void changeRotationState(boolean z);

        void onChanged();
    }

    public class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {
        private ScaleListener() {
        }

        @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            Crop.this.mEventState = EventState.SCALE;
            float scaleFactor = scaleGestureDetector.getScaleFactor();
            float fMapRadius = Crop.this.getImageMatrix().mapRadius(Crop.this.calculateMinPixels());
            float fMin = Math.min(Crop.this.mCropArea.width(), Crop.this.mCropArea.height());
            if (scaleFactor * fMapRadius > fMin) {
                scaleFactor = fMin / fMapRadius;
            }
            Crop.this.getImageMatrix().postScale(scaleFactor, scaleFactor, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
            if (Crop.this.mOnCropChangedListener != null) {
                Crop.this.mOnCropChangedListener.onChanged();
            }
            Crop.this.invalidate();
            return true;
        }

        @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            Crop.this.preTransform();
            return true;
        }

        @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            Crop crop = Crop.this;
            crop.fixImageBounds(crop.mCropArea, Crop.this.mBoundsFixCallback);
            Crop.this.endManualRotate();
        }
    }

    public class TranslateListener implements TranslateDetector.OnTranslateListener {
        private TranslateListener() {
        }

        @Override // com.miui.aod.components.photo.Edit.TranslateDetector.OnTranslateListener
        public void onTranslate(float f, float f2) {
            Crop.this.getImageMatrix().postTranslate(f, f2);
            Crop.this.invalidate();
        }

        @Override // com.miui.aod.components.photo.Edit.TranslateDetector.OnTranslateListener
        public boolean onTranslateBegin() {
            if (Crop.this.mEventState == EventState.IDLE) {
                Crop.this.mEventState = EventState.TRANSLATE;
                Crop.this.preTransform();
                return true;
            }
            if (Crop.this.mEventState != EventState.SCALE) {
                return false;
            }
            Crop.this.preTransform();
            return true;
        }

        @Override // com.miui.aod.components.photo.Edit.TranslateDetector.OnTranslateListener
        public void onTranslateEnd() {
            Crop crop = Crop.this;
            crop.fixImageBounds(crop.mCropArea, Crop.this.mBoundsFixCallback);
            Crop.this.mEventState = EventState.IDLE;
        }
    }

    public Crop(Context context) {
        this.mTouchSlop = 0;
        this.mMaskColor = context.getResources().getColor(R.color.crop_view_mask_color);
        this.mGuideLineSize = context.getResources().getDimension(R.dimen.guide_line_size);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mTranslateDetector = new TranslateDetector(new TranslateListener());
        this.mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animCropAreaChanged(OneShotAnimateListener oneShotAnimateListener) {
        Matrix matrix = new Matrix(getImageMatrix());
        Matrix matrix2 = new Matrix(getImageMatrix());
        RectF rectF = new RectF(this.mCropArea);
        RectF rectF2 = new RectF();
        this.mMatrix.setRectToRect(this.mCropArea, getBounds(), Matrix.ScaleToFit.CENTER);
        this.mMatrix.mapRect(rectF2, this.mCropArea);
        matrix2.postConcat(this.mMatrix);
        setupImageAnimator(matrix, matrix2, null);
        setupWindowAnimator(rectF, rectF2, null);
        setupAnimatorSet(this.mImageAnimator, this.mWindowAnimator, oneShotAnimateListener);
        this.mAnimatorSet.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animGuideLine(boolean z) {
        ObjectAnimator objectAnimator = this.mGuideLineAnimator;
        if (objectAnimator != null && objectAnimator.isRunning()) {
            this.mGuideLineAnimator.cancel();
        }
        if (z) {
            int i = this.mGuideLineAlpha;
            if (i < 255) {
                this.mGuideLineAnimator = ObjectAnimator.ofInt(this, "guideLineAlpha", i, 255);
            }
        } else {
            int i2 = this.mGuideLineAlpha;
            if (i2 > 0) {
                ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(this, "guideLineAlpha", i2, 0);
                this.mGuideLineAnimator = objectAnimatorOfInt;
                objectAnimatorOfInt.addListener(new Animator.AnimatorListener() { // from class: com.miui.aod.components.photo.Edit.Crop.6
                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                        Crop.this.mStartGuideLine = false;
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        Crop.this.mStartGuideLine = false;
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationRepeat(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                    }
                });
            }
        }
        ObjectAnimator objectAnimator2 = this.mGuideLineAnimator;
        if (objectAnimator2 != null) {
            objectAnimator2.setDuration(300L);
            this.mGuideLineAnimator.start();
        }
    }

    private Animator animRotate(float f, long j, OneShotAnimateListener oneShotAnimateListener) {
        if (this.mCanvasAnimator == null) {
            this.mCanvasAnimator = new ValueAnimator();
            this.mRotateValues = PropertyValuesHolder.ofFloat(ANIMATOR_ROTATE, 0.0f, 0.0f);
            this.mScaleValues = PropertyValuesHolder.ofFloat(ANIMATOR_SCALE, 0.0f, 0.0f);
        }
        this.mCanvasAnimator.setDuration(j);
        this.mRotateValues.setFloatValues(0.0f, -90.0f);
        this.mScaleValues.setFloatValues(1.0f, f);
        this.mCanvasAnimator.setValues(this.mRotateValues, this.mScaleValues);
        if (oneShotAnimateListener != null) {
            this.mCanvasAnimator.addListener(oneShotAnimateListener);
        }
        return this.mCanvasAnimator;
    }

    private void animTouchMaskColor(boolean z, boolean z2) {
        if (z) {
            ObjectAnimator objectAnimator = this.mMaskUpColorAnimator;
            if (objectAnimator != null && objectAnimator.isRunning()) {
                this.mMaskUpColorAnimator.cancel();
            }
        } else {
            ObjectAnimator objectAnimator2 = this.mMaskColorAnimator;
            if (objectAnimator2 != null && objectAnimator2.isRunning()) {
                this.mMaskColorAnimator.cancel();
            }
        }
        if (z) {
            ObjectAnimator objectAnimator3 = this.mMaskColorAnimator;
            if (objectAnimator3 != null && objectAnimator3.isRunning()) {
                if (!z2) {
                    return;
                } else {
                    this.mMaskColorAnimator.cancel();
                }
            }
            if (this.mMaskColorAlpha != MASK_COLOR_ALPHA_DOWN) {
                ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(this, "maskColorAlpha", 255, MASK_COLOR_ALPHA_DOWN);
                this.mMaskColorAnimator = objectAnimatorOfInt;
                objectAnimatorOfInt.setDuration(400L);
                this.mMaskColorAnimator.start();
                return;
            }
            return;
        }
        ObjectAnimator objectAnimator4 = this.mMaskUpColorAnimator;
        if (objectAnimator4 != null && objectAnimator4.isRunning()) {
            if (!z2) {
                return;
            } else {
                this.mMaskUpColorAnimator.cancel();
            }
        }
        if (this.mMaskColorAlpha != 255) {
            ObjectAnimator objectAnimatorOfInt2 = ObjectAnimator.ofInt(this, "maskColorAlpha", MASK_COLOR_ALPHA_DOWN, 255);
            this.mMaskUpColorAnimator = objectAnimatorOfInt2;
            objectAnimatorOfInt2.setDuration(400L);
            this.mMaskUpColorAnimator.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float calculateMinPixels() {
        return Math.max(CROP_AREA_MIN_PIXELS, Math.max(getImageBounds().height(), getImageBounds().width()) / 10.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doMirror() {
        Matrix matrix = new Matrix(getImageMatrix());
        matrix.postScale(-1.0f, 1.0f, this.mCropArea.centerX(), this.mCropArea.centerY());
        getImageMatrix().set(matrix);
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRotate() {
        this.mMatrix.setRotate(-90.0f, this.mCropArea.centerX(), this.mCropArea.centerY());
        this.mMatrix.mapRect(this.mRect, this.mCropArea);
        animRotate(Math.min(getBounds().width() / this.mRect.width(), getBounds().height() / this.mRect.height()), 100L, null).start();
    }

    private void drawGuideLines(Canvas canvas) {
        if (this.mGuideLineMode.mCount == 0) {
            return;
        }
        this.mPaint.reset();
        this.mPaint.setStrokeWidth(this.mGuideLineSize);
        this.mPaint.setColor(-1);
        this.mPaint.setAlpha(this.mGuideLineAlpha);
        this.mPaint.setStyle(Paint.Style.STROKE);
        RectF rectF = this.mCropArea;
        float f = rectF.right - rectF.left;
        int i = this.mGuideLineMode.mCount;
        float f2 = f / i;
        float f3 = (rectF.bottom - rectF.top) / i;
        for (int i2 = 1; i2 < this.mGuideLineMode.mCount; i2++) {
            RectF rectF2 = this.mCropArea;
            float f4 = rectF2.left;
            float f5 = rectF2.top;
            float f6 = i2;
            float f7 = f3 * f6;
            canvas.drawLine(f4, f5 + f7, rectF2.right, f5 + f7, this.mPaint);
            RectF rectF3 = this.mCropArea;
            float f8 = rectF3.left;
            float f9 = f6 * f2;
            canvas.drawLine(f8 + f9, rectF3.top, f8 + f9, rectF3.bottom, this.mPaint);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void endManualRotate() {
        this.mInitialDegree = 0.0f;
        this.mInitialMatrix = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performCropFinished() {
        this.mCropAreaChanged = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postCropFinish() {
        if (this.mCropAreaChanged) {
            getEventHandler().sendEmptyMessageDelayed(this.mMsgCropFinish, 1000L);
            OnCropChangedListener onCropChangedListener = this.mOnCropChangedListener;
            if (onCropChangedListener != null) {
                onCropChangedListener.onChanged();
            }
        }
    }

    private void postCropProceed() {
        if (this.mCropAreaChanged) {
            getEventHandler().removeMessages(this.mMsgCropFinish);
        }
    }

    private void postGuideLineFinish() {
        getEventHandler().removeMessages(this.mMsgGuideLineFinish);
        getEventHandler().sendEmptyMessageDelayed(this.mMsgGuideLineFinish, 1300L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void preTransform() {
        postCropProceed();
        ValueAnimator valueAnimator = this.mImageAnimator;
        if (valueAnimator == null || !valueAnimator.isStarted()) {
            return;
        }
        this.mImageAnimator.cancel();
    }

    private void setupAnimatorSet(Animator animator, Animator animator2, OneShotAnimateListener oneShotAnimateListener) {
        if (this.mAnimatorSet == null) {
            AnimatorSet animatorSet = new AnimatorSet();
            this.mAnimatorSet = animatorSet;
            animatorSet.playTogether(animator, animator2);
            this.mAnimatorSet.addListener(this.mPerformCropFinish);
        }
        this.mAnimatorSet.setDuration(300L);
        if (oneShotAnimateListener != null) {
            this.mAnimatorSet.addListener(oneShotAnimateListener);
        }
    }

    private void setupWindowAnimator(RectF rectF, RectF rectF2, OneShotAnimateListener oneShotAnimateListener) {
        if (this.mWindowAnimator == null) {
            this.mWindowAnimator = new ValueAnimator();
            this.mWindowValues = PropertyValuesHolder.ofObject(ANIMATOR_WINDOW, new RectFEvaluator(), rectF, rectF2);
            this.mWindowAnimator.addUpdateListener(this.mWindowUpdateListener);
        }
        this.mWindowAnimator.setDuration(300L);
        this.mWindowValues.setObjectValues(rectF, rectF2);
        this.mWindowAnimator.setValues(this.mWindowValues);
        if (oneShotAnimateListener != null) {
            this.mWindowAnimator.addListener(oneShotAnimateListener);
        }
    }

    private void startGuideLine() {
        getEventHandler().removeMessages(this.mMsgGuideLineFinish);
        this.mStartGuideLine = true;
        animGuideLine(true);
    }

    public void beginAutoRotate() {
        this.mEventState = EventState.ROTATION;
        this.mInitialDegree = this.mAdjustDegree;
        this.mInitialMatrix = new Matrix(this.mUnmodifiedMatrix);
        this.mGuideLineMode = GuideLine.CROP;
        this.mMaskColorAlpha = 255;
        startGuideLine();
        invalidate();
    }

    public void beginRotate() {
        this.mEventState = EventState.ROTATION;
        this.mInitialDegree = this.mAdjustDegree;
        this.mInitialMatrix = new Matrix(getImageMatrix());
        this.mGuideLineMode = GuideLine.ROTATE;
        this.mMaskColorAlpha = MASK_COLOR_ALPHA_DOWN;
        startGuideLine();
        invalidate();
    }

    @Override // com.miui.aod.components.view.EditorView.Plugin
    public void config(Canvas canvas) {
        super.config(canvas);
    }

    @Override // com.miui.aod.components.view.EditorView.Plugin
    public void drawOverlay(Canvas canvas) {
        if (this.mMaskColorAlpha != 255 || this.mStartGuideLine) {
            drawGuideLines(canvas);
        }
        this.mBgBounds.set(Math.round(this.mCropArea.left - this.mBgPadding.left), Math.round(this.mCropArea.top - this.mBgPadding.top), Math.round(this.mCropArea.right + this.mBgPadding.right), Math.round(this.mCropArea.bottom + this.mBgPadding.bottom));
        getImageMatrix().mapRect(getImageDisplayBounds(), getImageBounds());
        float f = getImageDisplayBounds().top;
        int i = this.mCropPadding.left;
        canvas.getClipBounds(this.mClipBounds);
        this.mPaint.setColor(this.mMaskColor);
        this.mPaint.setAlpha(this.mMaskColorAlpha);
        this.mPaint.setStyle(Paint.Style.FILL);
        Path path = new Path();
        float dimension = getContext().getResources().getDimension(R.dimen.aod_preview_bg_corners_radius);
        path.addRoundRect(new RectF(this.mBgBounds), dimension, dimension, Path.Direction.CW);
        canvas.clipOutPath(path);
        canvas.drawPaint(this.mPaint);
    }

    public void finishRotate() {
        this.mMaskColorAlpha = 255;
        invalidate();
        postGuideLineFinish();
        this.mEventState = EventState.IDLE;
    }

    public AspectRatio getAspectRatio() {
        return this.mAspectRatio;
    }

    public RectF getCroppedSize() {
        return this.mCropArea;
    }

    public float getRotateDegree() {
        return this.mAdjustDegree + this.mRotateDegree;
    }

    public RectF getSampleSize() {
        RectF rectF = new RectF(getImageBounds());
        getImageMatrix().mapRect(rectF);
        return rectF;
    }

    public boolean isModified() {
        return !getImageMatrix().equals(this.mUnmodifiedMatrix);
    }

    public void mirror() {
        if (this.mCropAreaChanged) {
            AnimatorSet animatorSet = this.mAnimatorSet;
            if (animatorSet == null || !animatorSet.isStarted()) {
                animCropAreaChanged(new OneShotAnimateListener() { // from class: com.miui.aod.components.photo.Edit.Crop.1
                    @Override // com.miui.aod.components.photo.OneShotAnimateListener, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        Crop.this.doMirror();
                    }
                });
                return;
            }
            return;
        }
        ValueAnimator valueAnimator = this.mImageAnimator;
        if (valueAnimator == null || !valueAnimator.isStarted()) {
            doMirror();
        }
    }

    @Override // com.miui.aod.components.view.EditorView.Plugin
    public void onMatrixChanged() {
        super.onMatrixChanged();
        this.mCropArea.set(getBounds());
        this.mTouchBounds.set(getTouchBounds());
        this.mUnmodifiedMatrix.set(getImageMatrix());
        ValueAnimator valueAnimator = this.mImageAnimator;
        if (valueAnimator == null || !valueAnimator.isStarted()) {
            return;
        }
        this.mImageAnimator.cancel();
    }

    @Override // com.miui.aod.components.view.EditorView.Plugin
    public void onStart() {
        this.mMsgCropFinish = getEventHandler().register(this.mEditFinished);
        this.mMsgGuideLineFinish = getEventHandler().register(this.mGuideLineFinished);
        if (this.mFirstIn) {
            reset();
            this.mFirstIn = false;
        }
    }

    @Override // com.miui.aod.components.view.EditorView.Plugin
    public void onStop() {
        getEventHandler().removeMessages(this.mMsgCropFinish);
        getEventHandler().unregister(this.mMsgCropFinish);
        getEventHandler().removeMessages(this.mMsgGuideLineFinish);
        getEventHandler().unregister(this.mMsgGuideLineFinish);
        this.mMsgCropFinish = 0;
        AnimatorSet animatorSet = this.mAnimatorSet;
        if (animatorSet != null && animatorSet.isStarted()) {
            this.mAnimatorSet.cancel();
        }
        ValueAnimator valueAnimator = this.mImageAnimator;
        if (valueAnimator != null && valueAnimator.isStarted()) {
            this.mImageAnimator.cancel();
        }
        ValueAnimator valueAnimator2 = this.mWindowAnimator;
        if (valueAnimator2 != null && valueAnimator2.isStarted()) {
            this.mWindowAnimator.cancel();
        }
        ValueAnimator valueAnimator3 = this.mCanvasAnimator;
        if (valueAnimator3 != null && valueAnimator3.isStarted()) {
            this.mCanvasAnimator.cancel();
        }
        ObjectAnimator objectAnimator = this.mMaskColorAnimator;
        if (objectAnimator != null && objectAnimator.isStarted()) {
            this.mMaskColorAnimator.cancel();
        }
        ObjectAnimator objectAnimator2 = this.mMaskUpColorAnimator;
        if (objectAnimator2 != null && objectAnimator2.isStarted()) {
            this.mMaskUpColorAnimator.cancel();
        }
        ObjectAnimator objectAnimator3 = this.mGuideLineAnimator;
        if (objectAnimator3 == null || !objectAnimator3.isStarted()) {
            return;
        }
        this.mGuideLineAnimator.cancel();
    }

    @Override // com.miui.aod.components.view.EditorView.Plugin
    public boolean onTouchEvent(MotionEvent motionEvent) {
        IOnTabListener iOnTabListener;
        int actionMasked = motionEvent.getActionMasked();
        EventState eventState = this.mEventState;
        if (eventState == EventState.ROTATION) {
            return true;
        }
        if (actionMasked == 0) {
            if (this.mTouchBounds.contains(motionEvent.getX(), motionEvent.getY())) {
                this.mAlwaysInTapRegion = true;
                if (this.mCropArea.contains(motionEvent.getX(), motionEvent.getY())) {
                    this.mInnerTab = true;
                    this.mTabPoint.set(motionEvent.getX(), motionEvent.getY());
                } else {
                    this.mInnerTab = false;
                    this.mTabPoint.set(motionEvent.getX(), motionEvent.getY());
                }
                this.mEventState = EventState.IDLE;
            } else {
                EventState eventState2 = this.mEventState;
                EventState eventState3 = EventState.SKIP;
                if (eventState2 != eventState3) {
                    if (eventState2 != EventState.IDLE) {
                        return false;
                    }
                    this.mEventState = eventState3;
                }
            }
        } else if (eventState == EventState.SKIP && this.mTouchBounds.contains(motionEvent.getX(), motionEvent.getY())) {
            this.mEventState = EventState.IDLE;
            motionEvent.setAction(0);
        }
        if (this.mEventState != EventState.SKIP) {
            if (actionMasked == 0) {
                RectF rectF = this.mCropArea;
                PointF pointF = this.mTabPoint;
                if (rectF.contains(pointF.x, pointF.y) && this.mCropable) {
                    animTouchMaskColor(true, true);
                }
                OnCropChangedListener onCropChangedListener = this.mOnCropChangedListener;
                if (onCropChangedListener != null) {
                    onCropChangedListener.changeRotationState(false);
                }
            } else if (actionMasked == 2) {
                float x = motionEvent.getX() - this.mTabPoint.x;
                float y = motionEvent.getY();
                PointF pointF2 = this.mTabPoint;
                float f = pointF2.y;
                float f2 = y - f;
                float f3 = (x * x) + (f2 * f2);
                int i = this.mTouchSlop;
                if (f3 > i * i) {
                    this.mAlwaysInTapRegion = false;
                    if (!this.mCropArea.contains(pointF2.x, f) && this.mCropable) {
                        animTouchMaskColor(true, false);
                    }
                }
            } else if (actionMasked == 1) {
                if (this.mCropable) {
                    animTouchMaskColor(false, true);
                }
                if (this.mAlwaysInTapRegion && (iOnTabListener = this.mIOnTabListener) != null) {
                    iOnTabListener.onTab(this.mInnerTab);
                }
            }
        }
        if (!this.mCropable) {
            this.mEventState = EventState.IDLE;
            return true;
        }
        int i2 = AnonymousClass7.$SwitchMap$com$miui$aod$components$photo$Edit$Crop$EventState[this.mEventState.ordinal()];
        if (i2 != 1 && i2 != 2) {
            if (i2 != 3 && i2 != 4 && i2 != 5) {
                return false;
            }
            this.mScaleGestureDetector.onTouchEvent(motionEvent);
            this.mTranslateDetector.onTouchEvent(motionEvent);
        }
        return true;
    }

    public void reset() {
        this.mCropArea.set(getBounds());
        this.mUnmodifiedMatrix.set(getImageMatrix());
        this.mTouchBounds.set(getTouchBounds());
        this.mAdjustDegree = 0.0f;
        this.mRotateDegree = 0;
        this.mAspectRatio = AspectRatio.RATIO_NONE;
    }

    public void rotate() {
        if (this.mCropAreaChanged) {
            AnimatorSet animatorSet = this.mAnimatorSet;
            if (animatorSet == null || !animatorSet.isStarted()) {
                animCropAreaChanged(new OneShotAnimateListener() { // from class: com.miui.aod.components.photo.Edit.Crop.2
                    @Override // com.miui.aod.components.photo.OneShotAnimateListener, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        Crop.this.doRotate();
                    }
                });
                return;
            }
            return;
        }
        ValueAnimator valueAnimator = this.mCanvasAnimator;
        if (valueAnimator == null || !valueAnimator.isStarted()) {
            doRotate();
        }
    }

    @Override // com.miui.aod.components.view.EditorView.Plugin
    public void setCropable(boolean z) {
        this.mCropable = z;
    }

    @Keep
    public void setGuideLineAlpha(int i) {
        this.mGuideLineAlpha = i;
        invalidate();
    }

    @Keep
    public void setMaskColorAlpha(int i) {
        this.mMaskColorAlpha = i;
        invalidate();
    }

    public void setOnCropChangedListener(OnCropChangedListener onCropChangedListener) {
        this.mOnCropChangedListener = onCropChangedListener;
    }

    @Keep
    public void setRotateDegree(float f) {
        if (this.mCropAreaChanged) {
            return;
        }
        if (this.mInitialMatrix == null) {
            beginRotate();
        }
        Matrix imageMatrix = getImageMatrix();
        imageMatrix.set(this.mInitialMatrix);
        RectF imageBounds = getImageBounds();
        imageMatrix.postRotate(f - this.mInitialDegree, this.mCropArea.centerX(), this.mCropArea.centerY());
        this.mAdjustDegree = f;
        imageMatrix.invert(this.mInvertMatrix);
        this.mInvertMatrix.mapRect(this.mInvertArea, this.mCropArea);
        RectF rectF = this.mInvertArea;
        float fMax = rectF.left < 0.0f ? Math.max(1.0f, (rectF.width() / 2.0f) / this.mInvertArea.centerX()) : 1.0f;
        RectF rectF2 = this.mInvertArea;
        if (rectF2.right > imageBounds.right) {
            fMax = Math.max(fMax, (rectF2.width() / 2.0f) / (imageBounds.right - this.mInvertArea.centerX()));
        }
        RectF rectF3 = this.mInvertArea;
        if (rectF3.top < 0.0f) {
            fMax = Math.max(fMax, (rectF3.height() / 2.0f) / this.mInvertArea.centerY());
        }
        RectF rectF4 = this.mInvertArea;
        if (rectF4.bottom > imageBounds.bottom) {
            fMax = Math.max(fMax, (rectF4.height() / 2.0f) / (imageBounds.bottom - this.mInvertArea.centerY()));
        }
        imageMatrix.preScale(fMax, fMax, this.mInvertArea.centerX(), this.mInvertArea.centerY());
        invalidate();
    }

    public void setTabListener(IOnTabListener iOnTabListener) {
        this.mIOnTabListener = iOnTabListener;
    }
}
