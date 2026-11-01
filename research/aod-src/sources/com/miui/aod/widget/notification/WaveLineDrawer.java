package com.miui.aod.widget.notification;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.core.content.res.ResourcesCompat;
import com.miui.aod.R;
import com.miui.aod.widget.NotificationAnimationView;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class WaveLineDrawer implements NotificationAnimationView.AnimationDrawer {
    public static final long DURATION = 8200;
    private static final int LINE_COUNT = 8;
    private static final int LINE_WIDTH = 4;
    private static final int MAX_AMP = 32;
    private static final int MAX_LINE_MARGIN = 9;
    private static final double PI_ANGLE = 6.283185307179586d;
    private NotificationAnimationView.AnimationDrawer.AnimationStateListener mAnimationStateListener;
    private int mCanvasHeight;
    private int mCanvasWidth;
    private final List<LineData> mLineList;
    private LinearGradient mLinearGradient;
    private final Paint mPaint;
    private final Drawable mShaderDrawable;
    private boolean mRepeat = false;
    private final SparseArray<Float> mCache = new SparseArray<>();
    private final Interpolator mLinearInterpolator = new LinearInterpolator();
    private final QuartEaseOutInterpolator mQuartEaseOutInterpolator = new QuartEaseOutInterpolator();
    private long mLastFrameTime = -1;
    private final CustomInterpolator mInterpolator = new CustomInterpolator();

    public class CustomInterpolator implements Interpolator {
        public float rate = 0.1f;

        public CustomInterpolator() {
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            float interpolation;
            float f2 = this.rate;
            if (f < f2) {
                interpolation = WaveLineDrawer.this.mQuartEaseOutInterpolator.getInterpolation(f / this.rate);
            } else {
                interpolation = 1.0f - WaveLineDrawer.this.mQuartEaseOutInterpolator.getInterpolation((f - this.rate) / (1.0f - f2));
            }
            return Math.max(0.0f, Math.min(1.0f, interpolation));
        }
    }

    public static class LineData {
        public float mAmp;
        public final Path mLeftPath;
        public final Path mRightPath;

        public LineData(Path path, Path path2, int i) {
            this.mLeftPath = path;
            this.mRightPath = path2;
            this.mAmp = i;
        }
    }

    public WaveLineDrawer(Context context) {
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4.0f);
        this.mLineList = new ArrayList(8);
        for (int i = 0; i < 8; i++) {
            this.mLineList.add(new LineData(new Path(), new Path(), 0));
        }
        this.mShaderDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.slide_blue_shader, null);
    }

    private float computeSine(int i) {
        if (this.mCanvasHeight <= 0) {
            return 0.0f;
        }
        if (this.mCache.indexOfKey(i) >= 0) {
            return this.mCache.get(i).floatValue();
        }
        float fCos = (float) Math.cos((float) ((((double) i) * PI_ANGLE) / ((double) this.mCanvasHeight)));
        this.mCache.put(i, Float.valueOf(fCos));
        this.mCache.put(this.mCanvasHeight - i, Float.valueOf(fCos));
        return fCos;
    }

    private void drawBackground(Canvas canvas, float f) {
        canvas.save();
        Drawable drawable = this.mShaderDrawable;
        double d = 1.0d - (((double) f) * 0.8d);
        int i = this.mCanvasWidth;
        drawable.setBounds((int) (d * ((double) i)), 0, i, this.mCanvasHeight);
        this.mShaderDrawable.draw(canvas);
        canvas.rotate(180.0f, this.mCanvasWidth / 2.0f, this.mCanvasHeight / 2.0f);
        this.mShaderDrawable.draw(canvas);
        canvas.restore();
    }

    private void drawPath(List<LineData> list, Canvas canvas, float f, float f2) {
        float fMax = f > 0.1f ? Math.max(0.0f, 1.0f - this.mLinearInterpolator.getInterpolation((Math.min(0.5f, f) - 0.1f) / 0.4f)) : 1.0f;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            LineData lineData = list.get(i);
            if (f2 <= 0.0f) {
                this.mPaint.setAlpha(0);
            } else {
                this.mPaint.setAlpha((int) (getAlphaByAmp(lineData.mAmp) * fMax));
            }
            lineData.mLeftPath.lineTo(0.0f, this.mCanvasHeight);
            canvas.drawPath(lineData.mLeftPath, this.mPaint);
            lineData.mRightPath.lineTo(this.mCanvasWidth, this.mCanvasHeight);
            canvas.drawPath(lineData.mRightPath, this.mPaint);
        }
    }

    public void drawWaveLine(Canvas canvas, float f) {
        float interpolation = this.mInterpolator.getInterpolation(f);
        int height = canvas.getHeight();
        this.mCanvasWidth = canvas.getWidth();
        if (this.mCanvasHeight != height) {
            this.mCanvasHeight = height;
            this.mCache.clear();
        }
        if (this.mLinearGradient == null) {
            LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, 0.0f, height, new int[]{Color.parseColor("#153d9e"), Color.parseColor("#00fff6"), Color.parseColor("#153d9e")}, (float[]) null, Shader.TileMode.REPEAT);
            this.mLinearGradient = linearGradient;
            this.mPaint.setShader(linearGradient);
        }
        drawBackground(canvas, interpolation);
        updatePathList(this.mLineList, interpolation);
        drawPath(this.mLineList, canvas, f, interpolation);
    }

    public int getAlphaByAmp(float f) {
        if (f > 22.5f) {
            return (int) (Math.max(0.0f, (27.0f - f) / 4.5f) * 50);
        }
        return Math.min(255, Math.max((int) (255.0f - ((255.0f - 50) * (f / 22.5f))), 0));
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
            f = ((j - j2) / 1000000.0f) / 8200.0f;
        }
        float fMax = Math.max(0.0f, Math.min(1.0f, f));
        drawWaveLine(canvas, fMax);
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
        this.mLineList.clear();
    }

    @Override // com.miui.aod.widget.NotificationAnimationView.AnimationDrawer
    public void setAnimationListener(NotificationAnimationView.AnimationDrawer.AnimationStateListener animationStateListener) {
        this.mAnimationStateListener = animationStateListener;
    }

    @Override // com.miui.aod.widget.NotificationAnimationView.AnimationDrawer
    public void setRepeatMode(boolean z) {
        this.mRepeat = z;
    }

    public void updatePathList(List<LineData> list, float f) {
        for (LineData lineData : list) {
            lineData.mLeftPath.reset();
            lineData.mLeftPath.moveTo(-2, 0.0f);
            lineData.mRightPath.reset();
            lineData.mRightPath.moveTo(this.mCanvasWidth + 2, 0.0f);
        }
        float f2 = f * 32.0f;
        for (int i = 0; i <= this.mCanvasHeight; i++) {
            float fComputeSine = 1.0f - computeSine(i);
            int size = this.mLineList.size();
            for (int i2 = 0; i2 < size; i2++) {
                float fMax = Math.max(0.0f, f2 - (i2 * 4.5f));
                float f3 = (fComputeSine * fMax) - 2;
                list.get(i2).mAmp = fMax;
                float f4 = i;
                list.get(i2).mLeftPath.lineTo(f3, f4);
                list.get(i2).mRightPath.lineTo(this.mCanvasWidth - f3, f4);
            }
        }
    }
}
