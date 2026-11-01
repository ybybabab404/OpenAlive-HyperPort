package com.miui.keyguardtemplate.doodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import com.miui.keyguardtemplate.R;
import com.miui.keyguardtemplate.Util;
import com.miui.keyguardtemplate.utils.DeviceConfig;

/* JADX INFO: loaded from: classes2.dex */
public class DoodleView extends View {
    public Rect destRect;
    public SparseArray doodleBitmaps;
    public int doodleType;
    public Paint paint;
    public Rect srcRect;

    public DoodleView(Context context) {
        this(context, null);
    }

    public DoodleView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.doodleType = 1;
        this.srcRect = new Rect();
        this.destRect = new Rect();
        this.doodleBitmaps = new SparseArray(2);
        this.paint = new Paint(1);
    }

    private Bitmap getCurDoodleBitmap() {
        return (Bitmap) this.doodleBitmaps.get(this.doodleType);
    }

    public final void calculateRect(int i, int i2, Bitmap bitmap) {
        if (DeviceConfig.isPhone()) {
            this.srcRect = null;
            this.destRect.set(0, 0, i, i2);
            return;
        }
        float dimen = getDimen(R.dimen.doodle_width);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Log.d("DoodleView", "doodleBitmapWidth = " + width + " w =" + i + " doodleBitmapHeight = " + height + " h = " + i2);
        float f = (float) width;
        float f2 = f / dimen;
        float f3 = ((float) i) * f2;
        float f4 = ((float) i2) * f2;
        Log.d("DoodleView", "doodleWidth = " + dimen + " scaledViewWith = " + f3 + " scaledViewHeight = " + f4);
        Rect rect = this.srcRect;
        int i3 = (int) ((f - f3) / 2.0f);
        rect.left = i3;
        rect.right = (int) (((float) i3) + f3);
        rect.top = 0;
        float f5 = (float) height;
        if (f4 <= f5) {
            rect.bottom = (int) f4;
            this.destRect.set(0, 0, i, i2);
        } else {
            rect.bottom = height;
            this.destRect.set(0, (int) (f4 - f5), i, i2);
        }
    }

    public final Bitmap cropBgBitmap(Context context, Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Point flipScreenPhysicalSize = DeviceConfig.isFlip() ? DeviceConfig.getFlipScreenPhysicalSize(context.getApplicationContext(), true) : DeviceConfig.currentDisplaySize(context.getApplicationContext());
        int i = (int) (width * 0.4279476f);
        int i2 = (width - i) / 2;
        int i3 = (int) (i * ((flipScreenPhysicalSize.y * 1.0f) / flipScreenPhysicalSize.x));
        if (i3 <= height) {
            height = i3;
        }
        return Bitmap.createBitmap(bitmap, i2, 0, i, height);
    }

    public int getDimen(int i) {
        Log.d("DoodleView", "scale =" + DeviceConfig.calculateScale(getContext()));
        return (int) (getResources().getDimensionPixelSize(i) * DeviceConfig.calculateScale(getContext()));
    }

    public int getDoodleType() {
        return this.doodleType;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        Bitmap curDoodleBitmap = getCurDoodleBitmap();
        if (curDoodleBitmap != null) {
            canvas.drawBitmap(curDoodleBitmap, !DeviceConfig.isPhone() ? this.srcRect : null, this.destRect, this.paint);
        }
    }

    @Override // android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        Bitmap curDoodleBitmap = getCurDoodleBitmap();
        if (curDoodleBitmap == null) {
            return;
        }
        calculateRect(i, i2, curDoodleBitmap);
        invalidate();
    }

    public Bitmap preLoadBitmap(Context context, int i) {
        this.doodleType = i;
        Bitmap bitmap = (Bitmap) this.doodleBitmaps.get(i);
        if (bitmap != null && !bitmap.isRecycled()) {
            return bitmap;
        }
        int i2 = R.drawable.doodle_background_cn;
        if (i == 2) {
            i2 = R.drawable.doodle_background_en;
        }
        Bitmap bitmapDecodeResource = Util.decodeResource(context, i2);
        return DeviceConfig.isPhone() ? cropBgBitmap(context, bitmapDecodeResource) : bitmapDecodeResource;
    }

    public void setDoodleBitmap(Bitmap bitmap, @IntRange int i) {
        this.doodleType = i;
        if (bitmap == null) {
            Log.e("DoodleView", "setDoodleType:setDoodleType please after preLoadBitmap");
            return;
        }
        calculateRect(getWidth(), getHeight(), bitmap);
        this.doodleBitmaps.put(i, bitmap);
        invalidate();
    }

    public void setDoodleColor(@ColorInt int i) {
        if (i == 0) {
            return;
        }
        Log.d("DoodleView", "setDoodleColor");
        this.paint.setColorFilter(new PorterDuffColorFilter(i, PorterDuff.Mode.SRC_IN));
        invalidate();
    }
}
