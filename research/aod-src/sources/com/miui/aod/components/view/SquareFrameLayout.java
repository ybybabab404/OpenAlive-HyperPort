package com.miui.aod.components.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.miui.aod.R;

/* JADX INFO: loaded from: classes.dex */
public class SquareFrameLayout extends FrameLayout {
    private boolean mIsSquare;

    public SquareFrameLayout(@NonNull Context context) {
        super(context);
        this.mIsSquare = false;
    }

    public SquareFrameLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsSquare = false;
    }

    public SquareFrameLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsSquare = false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        Path path = new Path();
        float dimension = getResources().getDimension(R.dimen.aod_preview_bg_corners_radius);
        path.addRoundRect(new RectF(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight()), dimension, dimension, Path.Direction.CW);
        canvas.clipPath(path);
        super.dispatchDraw(canvas);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int measuredHeight;
        int measuredWidth;
        super.onMeasure(i, i2);
        if (!this.mIsSquare || (measuredWidth = getMeasuredWidth()) == (measuredHeight = getMeasuredHeight())) {
            return;
        }
        if (measuredWidth <= measuredHeight) {
            measuredHeight = measuredWidth;
        }
        setMeasuredDimension(measuredHeight, measuredHeight);
    }

    public void setIsSquare(boolean z) {
        this.mIsSquare = z;
    }
}
