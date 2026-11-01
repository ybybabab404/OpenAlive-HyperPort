package com.miui.keyguardtemplate.smartframe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.miui.keyguardtemplate.R;
import com.miui.keyguardtemplate.Util;

/* JADX INFO: loaded from: classes2.dex */
public class SmartFrameView extends View {
    public int currentShape;
    public int drawableTranslateX;
    public int drawableTranslateY;
    public int frameColor;
    public int mDrawableHeight;
    public int mDrawableLeft;
    public int mDrawableTop;
    public int mDrawableWidth;
    public int mHeight;
    public SmartFrameChangedListener mSmartFrameChangeListener;
    public int mWidth;
    public Paint paint;
    public Drawable shapeDrawable;
    public SparseIntArray shapes;
    public Drawable strokeDrawable;
    public Xfermode xfermode;

    public interface SmartFrameChangedListener {
        void onSizeChanged(int i, int i2, int i3, int i4);
    }

    public SmartFrameView(Context context) {
        this(context, null);
    }

    public SmartFrameView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SmartFrameView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.currentShape = 1;
        this.drawableTranslateX = 0;
        this.drawableTranslateY = 0;
        this.frameColor = -65536;
        this.mSmartFrameChangeListener = null;
        SparseIntArray sparseIntArray = new SparseIntArray(6);
        this.shapes = sparseIntArray;
        int i2 = R.drawable.sf_shape1;
        sparseIntArray.append(1, i2);
        this.shapes.append(2, R.drawable.sf_shape2);
        this.shapes.append(3, R.drawable.sf_shape3);
        this.shapes.append(4, R.drawable.sf_shape4);
        this.shapes.append(5, R.drawable.sf_shape5);
        this.shapes.append(6, R.drawable.sf_shape6);
        this.mWidth = getWidth();
        this.mHeight = getHeight();
        initDrawablePositionParams();
        this.xfermode = new PorterDuffXfermode(PorterDuff.Mode.XOR);
        this.paint = new Paint();
        this.shapeDrawable = ContextCompat.getDrawable(context, i2);
    }

    public int getDrawableHeight() {
        return this.mDrawableHeight;
    }

    public int getDrawableLeft() {
        return this.mDrawableLeft;
    }

    public int getDrawableTop() {
        return this.mDrawableTop;
    }

    public int getDrawableTranslateX() {
        return this.drawableTranslateX;
    }

    public int getDrawableTranslateY() {
        return this.drawableTranslateY;
    }

    public int getDrawableWidth() {
        return this.mDrawableWidth;
    }

    public int getFillColor() {
        return this.frameColor;
    }

    public int getRealDrawableLeft() {
        return this.mDrawableLeft + this.drawableTranslateX;
    }

    public int getRealDrawableTop() {
        return this.mDrawableTop + this.drawableTranslateY;
    }

    public int getShape() {
        return this.currentShape;
    }

    public Drawable getShapeDrawable() {
        return this.shapeDrawable;
    }

    public Drawable getStrokeDrawable() {
        return this.strokeDrawable;
    }

    public final void initDrawablePositionParams() {
        int i;
        float fCalculateScale = Util.calculateScale(getContext());
        this.mDrawableLeft = (int) (getContext().getResources().getDimensionPixelSize(R.dimen.sf_shape_margin_left) * fCalculateScale);
        int dimensionPixelSize = (int) (getContext().getResources().getDimensionPixelSize(R.dimen.sf_shape_margin_top) * fCalculateScale);
        int dimensionPixelSize2 = (int) (getContext().getResources().getDimensionPixelSize(R.dimen.sf_shape_margin_bottom) * fCalculateScale);
        this.mDrawableWidth = (int) (getContext().getResources().getDimensionPixelSize(R.dimen.sf_shape_width) * fCalculateScale);
        int dimensionPixelSize3 = (int) (getContext().getResources().getDimensionPixelSize(R.dimen.sf_shape_height) * fCalculateScale);
        this.mDrawableHeight = dimensionPixelSize3;
        this.mDrawableTop = (Math.max(((this.mHeight - dimensionPixelSize) - dimensionPixelSize2) - dimensionPixelSize3, 0) / 2) + dimensionPixelSize;
        if (this.mDrawableLeft != 0 || (i = this.mWidth) == 0) {
            return;
        }
        this.mDrawableLeft = (i - this.mDrawableWidth) / 2;
    }

    public void onConfigChange() {
        initDrawablePositionParams();
        invalidate();
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mSmartFrameChangeListener = null;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        int iSaveLayer = canvas.saveLayer(0.0f, 0.0f, getWidth(), getHeight(), this.paint, 31);
        this.shapeDrawable.setBounds(getRealDrawableLeft(), getRealDrawableTop(), getRealDrawableLeft() + this.mDrawableWidth, getRealDrawableTop() + this.mDrawableHeight);
        this.shapeDrawable.draw(canvas);
        this.paint.setColor(this.frameColor);
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setXfermode(this.xfermode);
        canvas.drawRect(0.0f, 0.0f, getWidth(), getHeight(), this.paint);
        this.paint.setXfermode(null);
        Drawable drawable = this.strokeDrawable;
        if (drawable != null) {
            drawable.setBounds(getRealDrawableLeft(), getRealDrawableTop(), getRealDrawableLeft() + this.mDrawableWidth, getRealDrawableTop() + this.mDrawableHeight);
            this.strokeDrawable.draw(canvas);
        }
        canvas.restoreToCount(iSaveLayer);
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mWidth = i;
        this.mHeight = i2;
        onConfigChange();
        SmartFrameChangedListener smartFrameChangedListener = this.mSmartFrameChangeListener;
        if (smartFrameChangedListener != null) {
            smartFrameChangedListener.onSizeChanged(i, i2, i3, i4);
        }
    }

    public void setDrawableHeight(int i) {
        this.mDrawableHeight = i;
    }

    public void setDrawableLeft(int i) {
        this.mDrawableLeft = i;
    }

    public void setDrawableTop(int i) {
        this.mDrawableTop = i;
    }

    public void setDrawableTranslateX(int i) {
        this.drawableTranslateX = i;
    }

    public void setDrawableTranslateY(int i) {
        this.drawableTranslateY = i;
    }

    public void setDrawableWidth(int i) {
        this.mDrawableWidth = i;
    }

    public void setFillColor(int i) {
        if (this.frameColor == i) {
            return;
        }
        this.frameColor = i;
        invalidate();
    }

    public void setShape(@IntRange int i) {
        if (this.currentShape == i) {
            return;
        }
        this.shapeDrawable = ContextCompat.getDrawable(getContext(), this.shapes.get(i));
        this.currentShape = i;
        invalidate();
    }

    public void setShapeDrawable(Drawable drawable) {
        this.shapeDrawable = drawable;
        invalidate();
    }

    public void setSmartFrameChangeListener(SmartFrameChangedListener smartFrameChangedListener) {
        this.mSmartFrameChangeListener = smartFrameChangedListener;
    }

    public void setStrokeDrawable(Drawable drawable) {
        this.strokeDrawable = drawable;
    }
}
