package com.miui.aod.components.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;

/* JADX INFO: loaded from: classes.dex */
public class RoundCornerImageView extends AppCompatImageView {
    private int mBorderWidth;
    private final Matrix mMatrix;
    private final Paint mPaintBitmap;
    private Bitmap mRawBitmap;
    private final RectF mRectBitmap;
    private int mRectRoundRadius;
    private BitmapShader mShader;

    public RoundCornerImageView(Context context) {
        this(context, null);
    }

    public RoundCornerImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RoundCornerImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRectRoundRadius = 100;
        this.mPaintBitmap = new Paint(1);
        this.mRectBitmap = new RectF();
        this.mMatrix = new Matrix();
        this.mBorderWidth = 10;
    }

    private Bitmap getBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        if (!(drawable instanceof ColorDrawable)) {
            return null;
        }
        Rect bounds = drawable.getBounds();
        int i = bounds.right - bounds.left;
        int i2 = bounds.bottom - bounds.top;
        int color = ((ColorDrawable) drawable).getColor();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        new Canvas(bitmapCreateBitmap).drawARGB(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));
        return bitmapCreateBitmap;
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        Bitmap bitmap = getBitmap(getDrawable());
        if (bitmap == null) {
            super.onDraw(canvas);
            return;
        }
        int width = getWidth();
        int height = getHeight();
        if (this.mShader == null || !bitmap.equals(this.mRawBitmap)) {
            this.mRawBitmap = bitmap;
            Bitmap bitmap2 = this.mRawBitmap;
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            this.mShader = new BitmapShader(bitmap2, tileMode, tileMode);
        }
        float fMin = Math.min((width * 1.0f) / bitmap.getWidth(), (height * 1.0f) / bitmap.getHeight());
        this.mMatrix.setScale(fMin, fMin);
        this.mShader.setLocalMatrix(this.mMatrix);
        this.mPaintBitmap.setShader(this.mShader);
        RectF rectF = this.mRectBitmap;
        int i = this.mBorderWidth;
        rectF.set(i, i, width - i, height - i);
        RectF rectF2 = this.mRectBitmap;
        int i2 = this.mRectRoundRadius;
        canvas.drawRoundRect(rectF2, i2, i2, this.mPaintBitmap);
    }

    public void setRectRound(int i, int i2) {
        this.mRectRoundRadius = i;
        this.mBorderWidth = i2;
        invalidate();
    }

    public void setRectRoundRadius(int i) {
        this.mRectRoundRadius = i;
        invalidate();
    }
}
