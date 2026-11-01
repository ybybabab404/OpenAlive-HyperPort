package com.miui.maml.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.VectorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import com.xiaomi.onetrack.util.ac;
import miuix.animation.internal.TransitionInfo;

/* JADX INFO: loaded from: classes2.dex */
public class BitmapUtils {
    private static final String LOG_TAG = "MAML-BitmapUtils";
    private static final int sAlphaThreshold = 50;
    private static final Canvas sCanvas;
    private static final int sColorByteSize = 4;
    private static volatile Paint sCutPaint;

    static {
        Canvas canvas = new Canvas();
        sCanvas = canvas;
        canvas.setDrawFilter(new PaintFlagsDrawFilter(4, 2));
    }

    public static Bitmap composeIcon(Bitmap bitmap, Bitmap bitmap2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int byteCount = bitmap.getByteCount() / 4;
        int rowBytes = bitmap.getRowBytes() / 4;
        int[] iArr = new int[byteCount];
        bitmap.getPixels(iArr, 0, rowBytes, 0, 0, width, height);
        bitmap.recycle();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.drawBitmap(iArr, 0, rowBytes, 0, 0, width, height, true, (Paint) null);
        if (bitmap2 != null) {
            if (sCutPaint == null) {
                synchronized (BitmapUtils.class) {
                    try {
                        if (sCutPaint == null) {
                            Paint paint = new Paint();
                            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
                            sCutPaint = paint;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            canvas.drawBitmap(bitmap2, 0.0f, 0.0f, sCutPaint);
            bitmapCreateBitmap.getPixels(iArr, 0, rowBytes, 0, 0, width, height);
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        }
        canvas.drawBitmap(iArr, 0, rowBytes, 0, 0, width, height, true, (Paint) null);
        return bitmapCreateBitmap;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    public static Bitmap drawableToBitmapWithoutBlank(Drawable drawable, float f, boolean z) {
        Bitmap bitmapCreateBitmap;
        Canvas canvas = sCanvas;
        synchronized (canvas) {
            try {
                int intrinsicWidth = drawable.getIntrinsicWidth();
                int intrinsicHeight = drawable.getIntrinsicHeight();
                DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
                if (drawable instanceof PaintDrawable) {
                    PaintDrawable paintDrawable = (PaintDrawable) drawable;
                    paintDrawable.setIntrinsicWidth(intrinsicWidth);
                    paintDrawable.setIntrinsicHeight(intrinsicHeight);
                } else if (drawable instanceof BitmapDrawable) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    int density = bitmapDrawable.getBitmap().getDensity();
                    if (density != displayMetrics.densityDpi) {
                        bitmapDrawable.setTargetDensity(density);
                        f = getScaleRatio(drawable);
                        Log.d(LOG_TAG, "BitmapDensity = " + density + "  setTargetDensity = " + density);
                    }
                }
                if (z) {
                    f = 1.0f;
                }
                drawable.setBounds(0, 0, intrinsicWidth, intrinsicWidth);
                bitmapCreateBitmap = Bitmap.createBitmap(displayMetrics, intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
                canvas.setBitmap(bitmapCreateBitmap);
                canvas.save();
                float f2 = intrinsicWidth;
                float f3 = f2 * f;
                canvas.translate((f2 - f3) / 2.0f, (intrinsicHeight - f3) / 2.0f);
                canvas.scale(f, f);
                drawable.draw(canvas);
                canvas.restore();
                canvas.setBitmap(null);
            } catch (Throwable th) {
                throw th;
            }
        }
        return bitmapCreateBitmap;
    }

    public static Bitmap getBitmapFromDrawable(Context context, @DrawableRes int i, int i2, int i3) {
        Drawable drawable = ContextCompat.getDrawable(context, i);
        if (!(drawable instanceof BitmapDrawable) && !(drawable instanceof VectorDrawable) && !(drawable instanceof VectorDrawableCompat)) {
            throw new IllegalArgumentException("unsupported drawable type");
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    private static byte[] getBuffer(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int byteCount = bitmap.getByteCount() / 4;
        byte[] bArr = null;
        try {
            int[] iArr = new int[byteCount];
            bitmap.getPixels(iArr, 0, bitmap.getRowBytes() / 4, 0, 0, width, height);
            bArr = new byte[bitmap.getByteCount()];
            for (int i = 0; i < byteCount; i++) {
                int i2 = i * 4;
                int i3 = iArr[i];
                bArr[i2 + 3] = (byte) ((i3 >> 24) & 255);
                bArr[i2] = (byte) ((i3 >> 16) & 255);
                bArr[i2 + 1] = (byte) ((i3 >> 8) & 255);
                bArr[i2 + 2] = (byte) (i3 & 255);
            }
            return bArr;
        } catch (OutOfMemoryError e) {
            Log.e(LOG_TAG, "failed to get buffer, baseWidth = " + width + ", baseHeight = " + height, e);
            return bArr;
        }
    }

    private static float getContentRatio(Drawable drawable) {
        if (!(drawable instanceof BitmapDrawable)) {
            return -1.0f;
        }
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        if (bitmap.getConfig() != Bitmap.Config.ARGB_8888) {
            return -1.0f;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int rowBytes = bitmap.getRowBytes();
        byte[] buffer = getBuffer(bitmap);
        if (buffer == null || buffer.length < 1) {
            Log.w(LOG_TAG, "getContentRatio fail pixels invalid.");
            return -1.0f;
        }
        int edgePosition = getEdgePosition(width, height, rowBytes, buffer, true, false);
        int edgePosition2 = getEdgePosition(width, height, rowBytes, buffer, true, true);
        int edgePosition3 = getEdgePosition(width, height, rowBytes, buffer, false, false);
        int edgePosition4 = getEdgePosition(width, height, rowBytes, buffer, false, true);
        if (edgePosition >= 0 && edgePosition2 >= 0 && edgePosition3 >= 0 && edgePosition4 >= 0) {
            return Math.min(drawable.getIntrinsicWidth() / Math.min(drawable.getIntrinsicWidth(), (edgePosition4 - edgePosition3) + 1), drawable.getIntrinsicHeight() / Math.min(drawable.getIntrinsicHeight(), (edgePosition2 - edgePosition) + 1));
        }
        Log.w(LOG_TAG, "getContentRatio fail, edge invalid:" + edgePosition + ac.b + edgePosition2 + ac.b + edgePosition3 + ac.b + edgePosition4);
        return -1.0f;
    }

    private static int getEdgePosition(int i, int i2, int i3, byte[] bArr, boolean z, boolean z2) {
        int i4 = !z2 ? -1 : i;
        int i5 = !z2 ? -1 : i2;
        int i6 = z2 ? -1 : 1;
        int i7 = z2 ? -1 : 1;
        if (bArr != null) {
            int i8 = 0;
            while (i8 == 0) {
                if (z) {
                    i5 += i7;
                    if (i5 < 0 || i5 >= i2) {
                        break;
                    }
                    i4 = 0;
                    while (i4 < i) {
                        if ((bArr[(i5 * i3) + (i4 << 2) + 3] & TransitionInfo.INIT) > 50) {
                            i8++;
                        }
                        i4++;
                    }
                } else {
                    i4 += i6;
                    if (i4 < 0 || i4 >= i) {
                        break;
                    }
                    i5 = 0;
                    while (i5 < i2) {
                        if ((bArr[(i5 * i3) + (i4 << 2) + 3] & TransitionInfo.INIT) > 50) {
                            i8++;
                        }
                        i5++;
                    }
                }
            }
        }
        return z ? i5 : i4;
    }

    public static float getScaleRatio(Drawable drawable) {
        if (drawable instanceof PaintDrawable) {
            return 1.0f;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicWidth > 0 && intrinsicHeight > 0) {
            float contentRatio = getContentRatio(drawable);
            Log.d(LOG_TAG, "Content Ratio = " + contentRatio);
            if (contentRatio > 0.0f) {
                return contentRatio;
            }
        }
        return 1.0f;
    }
}
