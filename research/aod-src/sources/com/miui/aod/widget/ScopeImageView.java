package com.miui.aod.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miui.aod.components.ScopePoint;
import java.util.List;
import java.util.Random;
import miuix.view.animation.ExponentialEaseOutInterpolator;

/* JADX INFO: loaded from: classes.dex */
public class ScopeImageView extends View {
    public final ValueAnimator mAnimator;
    public Bitmap mArcBitmap;
    public Canvas mArcCanvas;
    public Bitmap mBitmap;
    public int mClipR;
    public RectF mClipRect;
    public ScopePoint mEnd;
    public Runnable mEndAction;
    public AnimatorListenerAdapter mExternalListener;
    public boolean mFirst;
    public float mH;
    public int mIndex;
    public boolean mLoop;
    public Matrix mMatrix;
    public boolean mNeedDrawBitmap;
    public final ValueAnimator mOutAnimator;
    public Paint mPaint;
    public List mPoints;
    public boolean mRandom;
    public float mRotate;
    public Bitmap mScopeBitmap;
    public Canvas mScopeCanvas;
    public ScopeInfo mScopeInfo;
    public Paint mSrcInPaint;
    public ScopePoint mStart;
    public float mW;
    public float mX;
    public float mY;

    public static /* synthetic */ void $r8$lambda$VFa6Qak1FZUj0HVADtZEA70VgO8(ScopeImageView scopeImageView, ValueAnimator valueAnimator) {
        scopeImageView.getClass();
        scopeImageView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    /* JADX INFO: renamed from: $r8$lambda$oSaDMWi_-wz58-5XPZUzOSSZI3U, reason: not valid java name */
    public static /* synthetic */ void m600$r8$lambda$oSaDMWi_wz585XPZUzOSSZI3U(ScopeImageView scopeImageView, ValueAnimator valueAnimator) {
        scopeImageView.getClass();
        float animatedFraction = valueAnimator.getAnimatedFraction();
        ScopePoint scopePoint = scopeImageView.mEnd;
        float f = scopePoint.x * animatedFraction;
        float f2 = 1.0f - animatedFraction;
        ScopePoint scopePoint2 = scopeImageView.mStart;
        scopeImageView.mX = f + (scopePoint2.x * f2);
        scopeImageView.mY = (scopePoint.y * animatedFraction) + (scopePoint2.y * f2);
        scopeImageView.mW = (int) ((scopePoint.w * animatedFraction) + (scopePoint2.w * f2));
        scopeImageView.mH = (int) ((scopePoint.h * animatedFraction) + (scopePoint2.h * f2));
        scopeImageView.mRotate = (animatedFraction * scopePoint.r) + (f2 * scopePoint2.r);
        scopeImageView.mNeedDrawBitmap = true;
        scopeImageView.setAlpha(1.0f);
        scopeImageView.invalidate();
    }

    public ScopeImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMatrix = new Matrix();
        this.mFirst = true;
        Paint paint = new Paint();
        this.mSrcInPaint = paint;
        paint.setAntiAlias(true);
        this.mSrcInPaint.setFilterBitmap(true);
        this.mSrcInPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Paint paint2 = new Paint();
        this.mPaint = paint2;
        paint2.setAntiAlias(true);
        this.mPaint.setFilterBitmap(true);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.aod.widget.ScopeImageView$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ScopeImageView.m600$r8$lambda$oSaDMWi_wz585XPZUzOSSZI3U(this.f$0, valueAnimator);
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.miui.aod.widget.ScopeImageView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                ScopeImageView scopeImageView = ScopeImageView.this;
                scopeImageView.mNeedDrawBitmap = true;
                if (scopeImageView.mLoop) {
                    scopeImageView.mOutAnimator.start();
                }
                Runnable runnable = ScopeImageView.this.mEndAction;
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
        valueAnimatorOfFloat.setInterpolator(new DecelerateInterpolator(3.5f));
        valueAnimatorOfFloat.setDuration(2000L);
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.mOutAnimator = valueAnimatorOfFloat2;
        valueAnimatorOfFloat2.setDuration(400L);
        valueAnimatorOfFloat2.setInterpolator(new ExponentialEaseOutInterpolator());
        valueAnimatorOfFloat2.setStartDelay(500L);
        valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.aod.widget.ScopeImageView$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ScopeImageView.$r8$lambda$VFa6Qak1FZUj0HVADtZEA70VgO8(this.f$0, valueAnimator);
            }
        });
        valueAnimatorOfFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.miui.aod.widget.ScopeImageView.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ScopeImageView scopeImageView = ScopeImageView.this;
                if (scopeImageView.mLoop) {
                    List list = scopeImageView.mPoints;
                    int i = scopeImageView.mIndex + 1;
                    scopeImageView.mIndex = i;
                    if (i >= list.size() / 2) {
                        ScopeImageView.this.mIndex = 0;
                    }
                    ScopeImageView scopeImageView2 = ScopeImageView.this;
                    scopeImageView2.mStart = (ScopePoint) list.get(scopeImageView2.mIndex * 2);
                    ScopeImageView scopeImageView3 = ScopeImageView.this;
                    scopeImageView3.mEnd = (ScopePoint) list.get((scopeImageView3.mIndex * 2) + 1);
                    ScopeImageView.this.mAnimator.start();
                }
            }
        });
    }

    private int getNextIndex() {
        int i = this.mIndex;
        List list = this.mPoints;
        if (this.mRandom) {
            return new Random().nextInt(list.size() / 2);
        }
        if (this.mFirst) {
            int iNextInt = new Random().nextInt(list.size() / 2);
            this.mFirst = false;
            return iNextInt;
        }
        int i2 = i + 1;
        if (i2 >= list.size() / 2) {
            return 0;
        }
        return i2;
    }

    public void clearAnimationView(boolean z) {
        this.mAnimator.cancel();
        this.mNeedDrawBitmap = false;
        if (z) {
            return;
        }
        this.mScopeCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        setAlpha(0.0f);
        postInvalidateOnAnimation();
    }

    public final void drawArc(Canvas canvas) {
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        canvas.drawArc(this.mClipRect, 0.0f, 30.0f, true, new Paint());
        this.mMatrix.reset();
        this.mMatrix.setRotate(-this.mRotate);
        this.mMatrix.postScale(this.mW / this.mBitmap.getWidth(), this.mH / this.mBitmap.getHeight(), 0.0f, 0.0f);
        this.mMatrix.postTranslate(this.mX, this.mY);
        canvas.drawBitmap(this.mBitmap, this.mMatrix, this.mSrcInPaint);
    }

    public final void drawScope(Canvas canvas) {
        drawArc(this.mArcCanvas);
        canvas.save();
        int i = 0;
        while (i < 3) {
            canvas.save();
            int i2 = i + 1;
            canvas.rotate((i2 / 2) * 60.0f);
            canvas.scale(1.0f, i % 2 == 0 ? 1.0f : -1.0f, 0.0f, 0.0f);
            canvas.drawBitmap(this.mArcBitmap, -2.0f, -0.4f, this.mPaint);
            canvas.restore();
            i = i2;
        }
        canvas.restore();
        this.mScopeBitmap.prepareToDraw();
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mEndAction = null;
        this.mAnimator.cancel();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mScopeInfo == null) {
            return;
        }
        int width = getWidth();
        if (this.mNeedDrawBitmap) {
            drawScope(this.mScopeCanvas);
            this.mNeedDrawBitmap = false;
        }
        canvas.save();
        float f = width / 2;
        canvas.translate(f, f);
        float f2 = width / 2.0f;
        int i = this.mClipR;
        canvas.scale(f2 / i, f2 / i);
        for (int i2 = 0; i2 < 4; i2++) {
            canvas.scale(1.0f, i2 % 2 == 0 ? 1.0f : -1.0f, 0.0f, 0.0f);
            canvas.rotate(180.0f);
            canvas.drawBitmap(this.mScopeBitmap, -1.0f, -1.0f, this.mPaint);
        }
        canvas.restore();
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (this.mLoop) {
            if (z) {
                this.mAnimator.resume();
                this.mOutAnimator.resume();
            } else {
                this.mAnimator.pause();
                this.mOutAnimator.pause();
            }
        }
    }

    public void setEndAction(Runnable runnable) {
        this.mEndAction = runnable;
    }

    public void setScopeInfo(ScopeInfo scopeInfo) {
        this.mScopeInfo = scopeInfo;
        this.mPoints = (List) new Gson().fromJson(getResources().getString(scopeInfo.mPoints), new TypeToken<List<ScopePoint>>() { // from class: com.miui.aod.widget.ScopeImageView.3
        }.getType());
        this.mClipR = scopeInfo.mClipR;
        int i = this.mClipR;
        this.mClipRect = new RectF(-i, -i, i, i);
        ScopePoint scopePoint = (ScopePoint) this.mPoints.get(0);
        this.mX = scopePoint.x;
        this.mY = scopePoint.y;
        this.mW = scopePoint.w;
        this.mH = scopePoint.h;
        this.mRotate = scopePoint.r;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        this.mBitmap = BitmapFactory.decodeResource(getResources(), scopeInfo.mResIdImage, options);
        int i2 = this.mClipR;
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        this.mScopeBitmap = Bitmap.createBitmap(i2, i2, config);
        this.mScopeCanvas = new Canvas(this.mScopeBitmap);
        int i3 = this.mClipR;
        this.mArcBitmap = Bitmap.createBitmap(i3, i3 / 2, config);
        this.mArcCanvas = new Canvas(this.mArcBitmap);
    }

    public void startAnimation(boolean z, AnimatorListenerAdapter animatorListenerAdapter) {
        this.mRandom = z;
        this.mLoop = false;
        if (this.mScopeInfo == null) {
            return;
        }
        List list = this.mPoints;
        int nextIndex = getNextIndex();
        int i = nextIndex * 2;
        this.mStart = (ScopePoint) list.get(i);
        this.mEnd = (ScopePoint) list.get(i + 1);
        this.mIndex = nextIndex;
        AnimatorListenerAdapter animatorListenerAdapter2 = this.mExternalListener;
        if (animatorListenerAdapter2 != null) {
            this.mAnimator.removeListener(animatorListenerAdapter2);
        }
        this.mExternalListener = animatorListenerAdapter;
        if (animatorListenerAdapter != null) {
            this.mAnimator.addListener(animatorListenerAdapter);
        }
        this.mAnimator.start();
    }
}
