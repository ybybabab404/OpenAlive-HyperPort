package com.miui.aod.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.miui.aod.R;
import com.miui.aod.Utils;

/* JADX INFO: loaded from: classes.dex */
public class EdgeFrameLayout extends FrameLayout {
    public float mBorderWidth;
    public int mColor;
    public float[] mCornerRadii;
    public Paint mPaint;
    public final Path mPath;
    public final RectF mRectF;
    public float sCornerRadius;

    public EdgeFrameLayout(Context context) {
        this(context, null);
    }

    public EdgeFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EdgeFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.sCornerRadius = getResources().getDimensionPixelSize(R.dimen.aod_style_bg_radius);
        this.mPath = new Path();
        this.mRectF = new RectF();
        this.mColor = Color.parseColor("#2effffff");
        this.mBorderWidth = Utils.getImageShowStrokeWidth();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.mPaint == null) {
            Paint paint = new Paint(1);
            this.mPaint = paint;
            paint.setColor(this.mColor);
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setStrokeWidth(this.mBorderWidth);
        }
        float strokeWidth = this.mPaint.getStrokeWidth();
        Rect clipBounds = canvas.getClipBounds();
        float f = strokeWidth / 2.0f;
        this.mRectF.set(clipBounds.left + f, clipBounds.top + f, clipBounds.right - f, clipBounds.bottom - f);
        if (this.mCornerRadii != null) {
            this.mPath.reset();
            this.mPath.addRoundRect(this.mRectF, this.mCornerRadii, Path.Direction.CW);
            canvas.drawPath(this.mPath, this.mPaint);
        } else {
            RectF rectF = this.mRectF;
            float f2 = this.sCornerRadius;
            canvas.drawRoundRect(rectF, f2, f2, this.mPaint);
        }
    }

    public void updateRadiiAndColor(int i, float[] fArr) {
        this.mCornerRadii = (fArr == null || fArr.length != 8) ? null : fArr;
        if (fArr != null && fArr.length != 8) {
            this.sCornerRadius = fArr.length > 0 ? fArr[0] : 0.0f;
        }
        this.mColor = i;
        invalidate();
    }

    public void updateRadiusAndColor(int i, int i2) {
        this.sCornerRadius = i2;
        this.mCornerRadii = null;
        this.mColor = i;
        invalidate();
    }
}
