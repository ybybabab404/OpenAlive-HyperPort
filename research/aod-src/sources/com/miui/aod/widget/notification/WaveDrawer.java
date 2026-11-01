package com.miui.aod.widget.notification;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.animation.Interpolator;
import androidx.core.content.res.ResourcesCompat;
import com.miui.aod.widget.NotificationAnimationView;

/* JADX INFO: loaded from: classes.dex */
public class WaveDrawer implements NotificationAnimationView.AnimationDrawer {
    public static final long DURATION = 4800;
    private NotificationAnimationView.AnimationDrawer.AnimationStateListener mAnimationStateListener;
    private int mDrawableWidth;
    private final Drawable mShaderDrawable;
    private boolean mRepeat = false;
    private final QuartEaseOutInterpolator mQuartEaseOutInterpolator = new QuartEaseOutInterpolator();
    private long mLastFrameTime = -1;
    private final Interpolator mInterpolator = new CustomInterpolator();
    private final Interpolator mAlphaInterpolator = new AlphaInterpolator();

    public class AlphaInterpolator implements Interpolator {
        public AlphaInterpolator() {
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            float interpolation;
            if (f < 0.07f) {
                interpolation = WaveDrawer.this.mQuartEaseOutInterpolator.getInterpolation(f / 0.07f);
            } else {
                interpolation = f < 0.32f ? 1.0f : 1.0f - WaveDrawer.this.mQuartEaseOutInterpolator.getInterpolation((f - 0.32f) / 0.68f);
            }
            return Math.max(0.0f, Math.min(1.0f, interpolation));
        }
    }

    public class CustomInterpolator implements Interpolator {
        public CustomInterpolator() {
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            float interpolation;
            if (f < 0.07f) {
                interpolation = WaveDrawer.this.mQuartEaseOutInterpolator.getInterpolation(f / 0.07f);
            } else {
                interpolation = f < 0.12f ? 1.0f : 1.0f - WaveDrawer.this.mQuartEaseOutInterpolator.getInterpolation((f - 0.12f) / 0.88f);
            }
            return Math.max(0.0f, Math.min(1.0f, interpolation));
        }
    }

    public WaveDrawer(Context context, int i) {
        Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), i, null);
        this.mShaderDrawable = drawable;
        if (drawable != null) {
            this.mDrawableWidth = drawable.getIntrinsicWidth();
        }
    }

    public void drawWaveLine(Canvas canvas, float f, float f2) {
        int height = canvas.getHeight();
        int width = canvas.getWidth();
        canvas.save();
        float f3 = height;
        float f4 = ((0.2f - f) * f3) / 2.0f;
        this.mShaderDrawable.setBounds(0, (int) f4, (int) ((f + 0.2f) * this.mDrawableWidth), (int) (f3 - f4));
        this.mShaderDrawable.setAlpha((int) (f2 * 255.0f));
        this.mShaderDrawable.draw(canvas);
        canvas.translate(width, 0.0f);
        canvas.scale(-1.0f, 1.0f);
        this.mShaderDrawable.draw(canvas);
        canvas.restore();
    }

    @Override // com.miui.aod.widget.NotificationAnimationView.AnimationDrawer
    public long getDuration() {
        return DURATION;
    }

    @Override // com.miui.aod.widget.NotificationAnimationView.AnimationDrawer
    public boolean onAnimationDraw(Canvas canvas, long j) {
        float f;
        long j2 = this.mLastFrameTime;
        if (j2 == -1) {
            this.mLastFrameTime = j;
            f = 0.0f;
        } else {
            f = ((j - j2) / 1000000.0f) / 4800.0f;
        }
        float fMax = Math.max(0.0f, Math.min(1.0f, f));
        drawWaveLine(canvas, this.mInterpolator.getInterpolation(fMax), this.mAlphaInterpolator.getInterpolation(fMax));
        if (fMax >= 1.0f) {
            if (this.mRepeat) {
                this.mLastFrameTime = -1L;
            } else {
                NotificationAnimationView.AnimationDrawer.AnimationStateListener animationStateListener = this.mAnimationStateListener;
                if (animationStateListener != null) {
                    animationStateListener.onAnimationEnd();
                }
            }
        }
        return this.mRepeat || fMax < 1.0f;
    }

    @Override // com.miui.aod.widget.NotificationAnimationView.AnimationDrawer
    public void release() {
    }

    @Override // com.miui.aod.widget.NotificationAnimationView.AnimationDrawer
    public void setAnimationListener(NotificationAnimationView.AnimationDrawer.AnimationStateListener animationStateListener) {
        this.mAnimationStateListener = animationStateListener;
    }

    @Override // com.miui.aod.widget.NotificationAnimationView.AnimationDrawer
    public void setRepeatMode(boolean z) {
        this.mRepeat = z;
    }
}
