package com.miui.clock.eastern.b;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/* JADX INFO: loaded from: classes.dex */
public class RotatedTextView extends AppCompatTextView {
    public RotatedTextView(Context context) {
        super(context);
        init();
    }

    public RotatedTextView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public RotatedTextView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        setMaxLines(1);
        setGravity(17);
        setIncludeFontPadding(false);
        setPadding(0, 0, 0, 0);
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        float measuredWidth = getMeasuredWidth() / 2.0f;
        canvas.save();
        canvas.rotate(90.0f, measuredWidth, measuredWidth);
        super.onDraw(canvas);
        canvas.restore();
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(0, 0), i2);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }

    @Override // android.widget.TextView
    public void setGravity(int i) {
        super.setGravity(17);
    }

    @Override // android.widget.TextView
    public void setMaxLines(int i) {
        super.setMaxLines(1);
    }

    @Override // android.view.View
    public void setRotation(float f) {
    }

    @Override // android.view.View
    public void setRotationX(float f) {
    }

    @Override // android.view.View
    public void setRotationY(float f) {
    }
}
