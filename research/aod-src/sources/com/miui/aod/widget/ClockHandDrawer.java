package com.miui.aod.widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.animation.Interpolator;
import miuix.animation.internal.FolmeCore;
import miuix.view.animation.SineEaseInOutInterpolator;

/* JADX INFO: loaded from: classes.dex */
public class ClockHandDrawer implements AnimationView.AnimationDrawer {
    public final Drawable mDotHandDrawable;
    public int mDotHeight;
    public Rect mDotRect;
    public int mDotWidth;
    public final Drawable mHourHandDrawable;
    public int mHourHeight;
    public Rect mHourRect;
    public int mHourWidth;
    public AnimationView.AnimationDrawer.AnimationStateListener mListener;
    public final Drawable mMinHandDrawable;
    public int mMinHeight;
    public Rect mMinRect;
    public int mMinWidth;
    public float mTargetHourDegree;
    public float mTargetMinDegree;
    public final Interpolator mInterpolator = new SineEaseInOutInterpolator();
    public long mLastFrameTime = -1;
    public long mStartTime = -1;
    public long mStartDelay = 0;

    public ClockHandDrawer(Drawable drawable, Drawable drawable2, Drawable drawable3) {
        this.mHourHandDrawable = drawable;
        this.mMinHandDrawable = drawable2;
        this.mDotHandDrawable = drawable3;
        this.mHourWidth = drawable.getIntrinsicWidth();
        this.mHourHeight = drawable.getIntrinsicHeight();
        this.mMinWidth = drawable2.getIntrinsicWidth();
        this.mMinHeight = drawable2.getIntrinsicHeight();
        this.mDotWidth = drawable3.getIntrinsicWidth();
        this.mDotHeight = drawable3.getIntrinsicHeight();
        this.mHourRect = new Rect(0, 0, this.mHourWidth, this.mHourHeight);
        this.mMinRect = new Rect(0, 0, this.mMinWidth, this.mMinHeight);
        this.mDotRect = new Rect(0, 0, this.mDotWidth, this.mDotHeight);
    }

    public final void drawDot(Canvas canvas) {
        this.mDotRect.offsetTo((canvas.getWidth() / 2) - (this.mDotWidth / 2), (canvas.getHeight() / 2) - (this.mDotHeight / 2));
        this.mDotHandDrawable.setBounds(this.mDotRect);
        this.mDotHandDrawable.draw(canvas);
    }

    public final void drawHourHand(Canvas canvas, float f) {
        int width = canvas.getWidth() / 2;
        int height = canvas.getHeight() / 2;
        canvas.save();
        this.mHourRect.offsetTo(width - (this.mHourWidth / 2), height - (this.mHourHeight / 2));
        canvas.rotate(f, width, height);
        this.mHourHandDrawable.setBounds(this.mHourRect);
        this.mHourHandDrawable.draw(canvas);
        canvas.restore();
    }

    public final void drawMinHand(Canvas canvas, float f) {
        int width = canvas.getWidth() / 2;
        int height = canvas.getHeight() / 2;
        canvas.save();
        this.mMinRect.offsetTo(width - (this.mMinWidth / 2), height - (this.mMinHeight / 2));
        canvas.rotate(f, width, height);
        this.mMinHandDrawable.setBounds(this.mMinRect);
        this.mMinHandDrawable.draw(canvas);
        canvas.restore();
    }

    @Override // com.miui.aod.widget.AnimationView.AnimationDrawer
    public boolean onAnimationDraw(Canvas canvas, long j) {
        float f;
        boolean z;
        float f2;
        boolean z2;
        long j2 = this.mStartDelay;
        if (j2 > 0 && this.mStartTime < 0) {
            this.mStartTime = j;
            return true;
        }
        if (j2 > 0 && j - this.mStartTime < j2) {
            return true;
        }
        long j3 = this.mLastFrameTime;
        if (j3 == -1) {
            AnimationView.AnimationDrawer.AnimationStateListener animationStateListener = this.mListener;
            if (animationStateListener != null) {
                animationStateListener.onAnimationStart();
            }
            f = this.mTargetHourDegree - 60.0f;
            f2 = this.mTargetMinDegree - 120.0f;
            this.mLastFrameTime = j;
            z = false;
            z2 = false;
        } else {
            float f3 = ((j - j3) / FolmeCore.NANOS_TO_MS) * 1.0f;
            float interpolation = this.mInterpolator.getInterpolation(Math.min(1.0f, f3 / 600.0f));
            boolean z3 = interpolation == 1.0f;
            f = this.mTargetHourDegree - ((1.0f - interpolation) * 60.0f);
            float interpolation2 = this.mInterpolator.getInterpolation(Math.min(1.0f, f3 / 900.0f));
            float f4 = this.mTargetMinDegree - ((1.0f - interpolation2) * 120.0f);
            if (interpolation2 == 1.0f) {
                z = z3;
                f2 = f4;
                z2 = true;
            } else {
                z = z3;
                f2 = f4;
                z2 = false;
            }
        }
        drawHourHand(canvas, f);
        drawMinHand(canvas, f2);
        drawDot(canvas);
        if (!z || !z2) {
            return true;
        }
        AnimationView.AnimationDrawer.AnimationStateListener animationStateListener2 = this.mListener;
        if (animationStateListener2 != null) {
            animationStateListener2.onAnimationEnd();
        }
        return false;
    }

    public void setAnimationListener(AnimationView.AnimationDrawer.AnimationStateListener animationStateListener) {
        this.mListener = animationStateListener;
    }

    public void setAnimationValue(float f, float f2) {
        this.mTargetHourDegree = f;
        this.mTargetMinDegree = f2;
    }

    public void setDotDrawableSize(int i) {
        this.mDotHeight = i;
        this.mDotWidth = (i * this.mDotHandDrawable.getIntrinsicWidth()) / this.mDotHandDrawable.getIntrinsicHeight();
        this.mDotRect = new Rect(0, 0, this.mDotWidth, this.mDotHeight);
    }

    public void setHourDrawableSize(int i) {
        this.mHourHeight = i;
        this.mHourWidth = (i * this.mHourHandDrawable.getIntrinsicWidth()) / this.mHourHandDrawable.getIntrinsicHeight();
        this.mHourRect = new Rect(0, 0, this.mHourWidth, this.mHourHeight);
    }

    public void setMinDrawableSize(int i) {
        this.mMinHeight = i;
        this.mMinWidth = (i * this.mMinHandDrawable.getIntrinsicWidth()) / this.mMinHandDrawable.getIntrinsicHeight();
        this.mMinRect = new Rect(0, 0, this.mMinWidth, this.mMinHeight);
    }

    public void setStartDelay(long j) {
        this.mStartDelay = j;
    }
}
