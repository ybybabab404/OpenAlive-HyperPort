package miuix.internal.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: classes2.dex */
public class CheckWidgetCircleDrawable extends Drawable {
    public boolean mHasStroke;
    public boolean mIsChecked;
    public boolean mIsEnabled;
    public boolean mIsGrayDrawable;
    public Paint mPaint;
    public float mScale;
    public int mStrokeDisableAlpha;
    public int mStrokeNormalAlpha;
    public Paint mStrokePaint;
    public int mStrokeStyle;
    public float mStrokeWidth;
    public int mUncheckedDisableAlpha;
    public int mUncheckedNormalAlpha;

    public CheckWidgetCircleDrawable(int i, int i2, int i3, int i4, int i5, int i6, boolean z) {
        this.mStrokeStyle = 0;
        this.mStrokeWidth = 2.0f;
        this.mIsEnabled = true;
        this.mIsChecked = false;
        this.mPaint = new Paint();
        this.mStrokePaint = new Paint();
        this.mScale = 1.0f;
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(i);
        this.mUncheckedNormalAlpha = i2;
        this.mUncheckedDisableAlpha = i3;
        boolean z2 = i4 != 0;
        this.mHasStroke = z2;
        if (z2) {
            this.mStrokePaint.setAntiAlias(true);
            this.mStrokePaint.setColor(i4);
            this.mStrokePaint.setStyle(Paint.Style.STROKE);
            this.mStrokePaint.setStrokeWidth(this.mStrokeWidth);
        }
        this.mStrokeNormalAlpha = i5;
        this.mStrokeDisableAlpha = i6;
        this.mIsGrayDrawable = z;
    }

    public CheckWidgetCircleDrawable(int i, int i2, int i3, boolean z) {
        this(i, i2, i3, 0, 0, 0, z);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        int i = bounds.right;
        int i2 = bounds.left;
        int i3 = (i + i2) / 2;
        int i4 = bounds.top;
        int i5 = bounds.bottom;
        int i6 = (i4 + i5) / 2;
        int iMin = Math.min(i - i2, i5 - i4) / 2;
        int i7 = this.mStrokeStyle;
        if (i7 == 0) {
            drawWithTemporaryStroke(canvas, i3, i6, iMin);
        } else if (i7 != 1) {
            drawDefault(canvas, i3, i6, iMin);
        } else {
            drawWithPermanentStroke(canvas, i3, i6, iMin);
        }
    }

    public final void drawDefault(Canvas canvas, int i, int i2, int i3) {
        float f = i3;
        float f2 = i;
        float f3 = i2;
        canvas.drawCircle(f2, f3, (this.mScale * f) - 1.0f, this.mPaint);
        if (this.mHasStroke) {
            canvas.drawCircle(f2, f3, ((f * this.mScale) - 1.0f) - 2.0f, this.mStrokePaint);
        }
    }

    public final void drawWithPermanentStroke(Canvas canvas, int i, int i2, int i3) {
        float f = i3;
        float f2 = ((this.mScale * f) - this.mStrokeWidth) - 1.0f;
        if (shouldDrawMainCircle()) {
            canvas.drawCircle(i, i2, f2, this.mPaint);
        }
        if (this.mHasStroke) {
            canvas.drawCircle(i, i2, ((f * this.mScale) - (this.mStrokeWidth / 2.0f)) - 1.0f, this.mStrokePaint);
        }
    }

    public final void drawWithTemporaryStroke(Canvas canvas, int i, int i2, int i3) {
        float f = (this.mIsGrayDrawable ? (i3 * this.mScale) - this.mStrokeWidth : i3 * this.mScale) - 1.0f;
        if (shouldDrawMainCircle()) {
            canvas.drawCircle(i, i2, f, this.mPaint);
        }
        if (!this.mHasStroke || this.mIsChecked) {
            return;
        }
        canvas.drawCircle(i, i2, ((i3 * this.mScale) - (this.mStrokeWidth / 2.0f)) - 1.0f, this.mStrokePaint);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mPaint.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
        if (this.mHasStroke) {
            if (this.mIsEnabled) {
                this.mStrokePaint.setAlpha(this.mStrokeNormalAlpha);
            } else {
                this.mStrokePaint.setAlpha(this.mStrokeDisableAlpha);
            }
        }
    }

    public void setCheckWidgetStrokeStyle(int i) {
        this.mStrokeStyle = i;
    }

    public void setCheckWidgetStrokeWidth(float f) {
        this.mStrokeWidth = f;
        this.mStrokePaint.setStrokeWidth(f);
    }

    public void setChecked(boolean z) {
        this.mIsChecked = z;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    public void setEnabled(boolean z) {
        this.mIsEnabled = z;
    }

    public void setScale(float f) {
        this.mScale = f;
    }

    public final boolean shouldDrawMainCircle() {
        return this.mIsGrayDrawable || this.mIsEnabled;
    }
}
