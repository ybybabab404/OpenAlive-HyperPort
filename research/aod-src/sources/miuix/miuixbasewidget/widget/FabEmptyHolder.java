package miuix.miuixbasewidget.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;

/* JADX INFO: loaded from: classes2.dex */
class FabEmptyHolder extends Drawable {
    public final Drawable mDrawable;
    public final Paint mPaint;
    public final View mView;

    public FabEmptyHolder(View view, Drawable drawable) {
        if (view == null) {
            throw new IllegalArgumentException("View must not be null");
        }
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable must not be null");
        }
        this.mView = view;
        this.mDrawable = drawable;
        this.mPaint = new Paint();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        int width = this.mView.getWidth();
        int height = this.mView.getHeight();
        int paddingLeft = this.mView.getPaddingLeft();
        int paddingTop = this.mView.getPaddingTop();
        int paddingRight = (width - paddingLeft) - this.mView.getPaddingRight();
        int paddingBottom = (height - paddingTop) - this.mView.getPaddingBottom();
        int iMin = Math.min(paddingRight, paddingBottom);
        int i = paddingLeft + ((paddingRight - iMin) / 2);
        int i2 = paddingTop + ((paddingBottom - iMin) / 2);
        this.mDrawable.setBounds(i, i2, i + iMin, iMin + i2);
        this.mDrawable.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return this.mDrawable.getConstantState();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return this.mDrawable.getOpacity();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mDrawable.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mDrawable.setColorFilter(colorFilter);
    }
}
