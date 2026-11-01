package com.miui.aod.theme;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import com.miui.aod.R;
import com.miui.aod.Utils;

/* JADX INFO: loaded from: classes.dex */
public class OnlineImageView extends AppCompatImageView {
    public Paint mPaint;
    public final RectF mRectF;
    public final float sCornerRadius;

    public OnlineImageView(Context context) {
        this(context, null);
    }

    public OnlineImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public OnlineImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.sCornerRadius = getResources().getDimensionPixelSize(R.dimen.aod_style_bg_radius);
        this.mRectF = new RectF();
    }

    @Override // android.view.View
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.mPaint == null) {
            Paint paint = new Paint(1);
            this.mPaint = paint;
            paint.setColor(Color.parseColor("#2effffff"));
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setStrokeWidth(Utils.getImageShowStrokeWidth());
        }
        float strokeWidth = this.mPaint.getStrokeWidth();
        Rect clipBounds = canvas.getClipBounds();
        float f = strokeWidth / 2.0f;
        this.mRectF.set(clipBounds.left + f, clipBounds.top + f, clipBounds.right - f, clipBounds.bottom - f);
        RectF rectF = this.mRectF;
        float f2 = this.sCornerRadius;
        canvas.drawRoundRect(rectF, f2, f2, this.mPaint);
    }
}
