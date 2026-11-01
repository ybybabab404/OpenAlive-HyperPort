package com.miui.clock.classic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.miui.clock.R;
import com.miui.clock.utils.DeviceConfig;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"AppCompatCustomView"})
public class PlaceHolderTextView extends TextView {
    public Paint mRectPaint;
    public boolean needDrawRect;
    public int rectHeight;
    public int rectPadding;
    public int rectRadius;
    public int rectWidth;

    public PlaceHolderTextView(Context context) {
        super(context);
        init();
    }

    public PlaceHolderTextView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public PlaceHolderTextView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.needDrawRect) {
            float height = ((getHeight() - this.rectHeight) + (this.rectPadding * 2)) / 2.0f;
            Log.e("zggg", "paddingStart = " + getPaddingStart());
            int i = this.rectPadding;
            float f = (float) i;
            float f2 = (float) (this.rectWidth - i);
            float f3 = (this.rectHeight + height) - (i * 2);
            int i2 = this.rectRadius;
            canvas.drawRoundRect(f, height, f2, f3, i2, i2, this.mRectPaint);
        }
    }

    public final int getDimen(int i) {
        return (int) (getResources().getDimensionPixelSize(i) * DeviceConfig.calculateScale(getContext()));
    }

    public final void init() {
        Paint paint = new Paint();
        this.mRectPaint = paint;
        paint.setColor(getPaint().getColor());
        this.mRectPaint.setStyle(Paint.Style.FILL);
        this.needDrawRect = TextUtils.isEmpty(getText());
        this.rectWidth = getDimen(R.dimen.text_placeholder_width);
        this.rectHeight = getDimen(R.dimen.text_placeholder_height);
        this.rectPadding = getDimen(R.dimen.text_placeholder_padding);
        this.rectRadius = getDimen(R.dimen.text_placeholder_radius);
        setPadding(this.needDrawRect ? this.rectWidth : 0, 0, 0, 0);
    }

    @Override // android.widget.TextView
    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        super.setText(charSequence, bufferType);
        boolean zIsEmpty = TextUtils.isEmpty(getText());
        this.needDrawRect = zIsEmpty;
        setPadding(zIsEmpty ? this.rectWidth : 0, 0, 0, 0);
    }

    @Override // android.widget.TextView
    public void setTextColor(int i) {
        super.setTextColor(i);
        this.mRectPaint.setColor(i);
    }
}
