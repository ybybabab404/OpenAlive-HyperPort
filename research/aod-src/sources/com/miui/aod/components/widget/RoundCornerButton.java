package com.miui.aod.components.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

/* JADX INFO: loaded from: classes.dex */
public class RoundCornerButton extends AppCompatTextView {
    private final Paint mPaint;

    public RoundCornerButton(Context context) {
        this(context, null);
    }

    public RoundCornerButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RoundCornerButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        float f = height / 2;
        canvas.drawRoundRect(0.0f, 0.0f, width, height, f, f, this.mPaint);
        super.onDraw(canvas);
    }

    public void setBtnColor(int i) {
        this.mPaint.setColor(i);
        invalidate();
    }
}
