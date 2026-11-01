package com.miui.miwallpaper.material.utils;

import android.app.WallpaperColors;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Log;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.maml.folme.AnimatedProperty;

/* JADX INFO: loaded from: classes2.dex */
public class MiuiWallpaperColors {
    public static void RGBToHSL(int i, int i2, int i3, float[] fArr) {
        float f;
        float fAbs;
        float f2 = i / 255.0f;
        float f3 = i2 / 255.0f;
        float f4 = i3 / 255.0f;
        float fMax = Math.max(f2, Math.max(f3, f4));
        float fMin = Math.min(f2, Math.min(f3, f4));
        float f5 = fMax - fMin;
        float f6 = (fMax + fMin) / 2.0f;
        if (fMax == fMin) {
            f = 0.0f;
            fAbs = 0.0f;
        } else {
            if (fMax == f2) {
                f = ((f3 - f4) / f5) % 6.0f;
            } else {
                f = fMax == f3 ? ((f4 - f2) / f5) + 2.0f : 4.0f + ((f2 - f3) / f5);
            }
            fAbs = f5 / (1.0f - Math.abs((2.0f * f6) - 1.0f));
        }
        float f7 = (f * 60.0f) % 360.0f;
        if (f7 < 0.0f) {
            f7 += 360.0f;
        }
        fArr[0] = constrain(f7, 0.0f, 360.0f);
        fArr[1] = constrain(fAbs, 0.0f, 1.0f);
        fArr[2] = constrain(f6, 0.0f, 1.0f);
    }

    public static void colorToHSL(int i, float[] fArr) {
        RGBToHSL(Color.red(i), Color.green(i), Color.blue(i), fArr);
    }

    public static float constrain(float f, float f2, float f3) {
        if (f < f2) {
            return f2;
        }
        return f > f3 ? f3 : f;
    }

    public static int fromBitmap(Bitmap bitmap) {
        return fromBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
    }

    public static int fromBitmap(Bitmap bitmap, Rect rect) {
        return MiPaletteUtils.getInstance().getMainColor(bitmap, rect);
    }

    public static WallpaperColors generateWallpaperColors(Bitmap bitmap) {
        return new WallpaperColors(Color.valueOf(fromBitmap(bitmap)), null, null, miuiCalculateDarkHints(bitmap, false));
    }

    public static float getGray(Color color) {
        return (color.red() * 0.299f) + (color.green() * 0.587f) + (color.blue() * 0.114f);
    }

    public static int miuiCalculateDarkHints(Bitmap bitmap, boolean z) {
        return miuiCalculateDarkHints(bitmap, z, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
    }

    public static int miuiCalculateDarkHints(Bitmap bitmap, boolean z, Rect rect) {
        Bitmap bitmapCreateBitmap;
        int[] iArr;
        float f;
        float f2;
        if (bitmap == null || bitmap.getHeight() == 0 || bitmap.getWidth() == 0) {
            return 0;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int iMax = Math.max(0, Math.min(rect.left, width));
        int iMax2 = Math.max(0, Math.min(rect.top, height));
        int iMax3 = Math.max(iMax, Math.min(rect.right, width));
        int iMax4 = Math.max(iMax2, Math.min(rect.bottom, height));
        if (iMax3 - iMax <= 0 || iMax4 - iMax2 <= 0) {
            Log.w("MiuiWallpaperColors", "miuiCalculateDarkHints: rect out of bitmap bounds, fallback to full bitmap. rect=" + rect + " bitmap=" + width + AnimatedProperty.PROPERTY_NAME_X + height);
            iMax = 0;
            iMax2 = 0;
        } else {
            width = iMax3;
            height = iMax4;
        }
        int i = width - iMax;
        int i2 = height - iMax2;
        int i3 = i * i2;
        if (i3 > 12544) {
            double dSqrt = Math.sqrt(12544.0d / ((double) i3));
            Matrix matrix = new Matrix();
            float f3 = (float) dSqrt;
            matrix.setScale(f3, f3);
            bitmapCreateBitmap = Bitmap.createBitmap(bitmap, iMax, iMax2, i, i2, matrix, false);
        } else {
            bitmapCreateBitmap = Bitmap.createBitmap(bitmap, iMax, iMax2, i, i2);
        }
        Bitmap bitmap2 = bitmapCreateBitmap;
        int width2 = bitmap2.getWidth() * bitmap2.getHeight();
        int[] iArr2 = new int[width2];
        if (z) {
            int width3 = (bitmap2.getWidth() * bitmap2.getHeight()) / 2;
            int[] iArr3 = new int[width3];
            int[] iArr4 = new int[width3];
            bitmap2.getPixels(iArr3, 0, bitmap2.getWidth(), 0, 0, bitmap2.getWidth(), bitmap2.getHeight() / 2);
            bitmap2.getPixels(iArr4, 0, bitmap2.getWidth(), 0, bitmap2.getHeight() / 2, bitmap2.getWidth(), bitmap2.getHeight() / 2);
            f = 0.0f;
            f2 = 0.0f;
            for (int i4 = 0; i4 < width3; i4++) {
                f += ((double) getGray(Color.valueOf(iArr3[i4]))) >= 0.7d ? 3.0f : 0.0f;
                f2 += 3.0f;
            }
            for (int i5 = 0; i5 < width3; i5++) {
                f += ((double) getGray(Color.valueOf(iArr4[i5]))) >= 0.7d ? 1.0f : 0.0f;
                f2 += 1.0f;
            }
            iArr = iArr2;
        } else {
            iArr = iArr2;
            bitmap2.getPixels(iArr, 0, bitmap2.getWidth(), 0, 0, bitmap2.getWidth(), bitmap2.getHeight());
            f = 0.0f;
            f2 = 0.0f;
            for (int i6 = 0; i6 < width2; i6++) {
                f += ((double) getGray(Color.valueOf(iArr[i6]))) >= 0.8d ? 1.0f : 0.0f;
                f2 += 1.0f;
            }
        }
        int i7 = (f2 == 0.0f || f / f2 <= 0.6f) ? 0 : 1;
        bitmap2.getPixels(iArr, 0, bitmap2.getWidth(), 0, 0, bitmap2.getWidth(), bitmap2.getHeight());
        float[] fArr = new float[3];
        double d = BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
        for (int i8 = 0; i8 < width2; i8++) {
            colorToHSL(iArr[i8], fArr);
            d += (double) fArr[2];
        }
        if (d / ((double) width2) < 0.25d) {
            i7 |= 2;
        }
        return i7 | 4;
    }
}
