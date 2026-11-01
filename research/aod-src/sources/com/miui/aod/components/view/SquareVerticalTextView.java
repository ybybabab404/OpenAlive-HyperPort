package com.miui.aod.components.view;

import android.content.Context;
import android.util.AttributeSet;

/* JADX INFO: loaded from: classes.dex */
public class SquareVerticalTextView extends VerticalTextView {
    private boolean mIsSquare;
    private boolean mShouldReMeasureText;

    public SquareVerticalTextView(Context context) {
        super(context);
        this.mIsSquare = false;
        this.mShouldReMeasureText = false;
    }

    public SquareVerticalTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsSquare = false;
        this.mShouldReMeasureText = false;
    }

    public SquareVerticalTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsSquare = false;
        this.mShouldReMeasureText = false;
    }

    @Override // com.miui.aod.components.view.VerticalTextView, androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mShouldReMeasureText && isVerticalMode()) {
            reMeasureText(getMeasuredWidth(), getMeasuredHeight());
        }
        super.onLayout(z, i, i2, i3, i4);
    }

    @Override // com.miui.aod.components.view.VerticalTextView, androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public void onMeasure(int i, int i2) {
        int measuredHeight;
        int measuredWidth;
        super.onMeasure(i, i2);
        if (!this.mIsSquare || (measuredWidth = getMeasuredWidth()) == (measuredHeight = getMeasuredHeight())) {
            return;
        }
        this.mShouldReMeasureText = true;
        if (measuredWidth <= measuredHeight) {
            measuredHeight = measuredWidth;
        }
        setMeasuredDimension(measuredHeight, measuredHeight);
    }

    public void setIsSquare(boolean z) {
        this.mIsSquare = z;
    }
}
