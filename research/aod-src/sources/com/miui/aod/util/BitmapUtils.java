package com.miui.aod.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public final class BitmapUtils {
    public static Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        if (!(drawable instanceof AdaptiveIconDrawable)) {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmapCreateBitmap;
        }
        AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) drawable;
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{adaptiveIconDrawable.getBackground(), adaptiveIconDrawable.getForeground()});
        Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(layerDrawable.getIntrinsicWidth(), layerDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmapCreateBitmap2);
        layerDrawable.setBounds(0, 0, canvas2.getWidth(), canvas2.getHeight());
        layerDrawable.draw(canvas2);
        return bitmapCreateBitmap2;
    }

    public static Bitmap getBitmapFromView(View view, float f) {
        if (view != null) {
            try {
                if (view.getWidth() > 0 && view.getHeight() > 0) {
                    Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmapCreateBitmap);
                    Path path = new Path();
                    path.addRoundRect(new RectF(0.0f, 0.0f, view.getWidth(), view.getHeight()), f, f, Path.Direction.CW);
                    canvas.clipPath(path);
                    view.draw(canvas);
                    return bitmapCreateBitmap;
                }
            } catch (Exception e) {
                Log.e("BitmapUtils", "drawToBitmap error", e);
            }
        }
        return null;
    }

    public static Bitmap.Config getConfig(Bitmap bitmap) {
        return bitmap.getConfig();
    }

    public static Bitmap getInvertColorBitmap(Bitmap bitmap, boolean z) {
        if (bitmap == null || bitmap.isRecycled()) {
            return bitmap;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), getConfig(bitmap));
        bitmapCreateBitmap.setDensity(bitmap.getDensity());
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(new float[]{-1.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, -1.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, -1.0f, 0.0f, 255.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint(6);
        paint.setColorFilter(colorMatrixColorFilter);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        if (z) {
            bitmap.recycle();
        }
        return bitmapCreateBitmap;
    }
}
