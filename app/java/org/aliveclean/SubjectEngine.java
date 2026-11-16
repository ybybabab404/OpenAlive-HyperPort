package org.aliveclean;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * 主体蒙版统一入口：走小米自带的景深/主体分割引擎（com.xiaomi.aicr）。
 *
 * 算法与模型都在小米进程里跑，本 APK 只做「缩放 → 调用 → 还原尺寸」；
 * 引擎不可用时返回 null，由编辑器退回手动涂抹（不再内置 u2netp）。
 */
final class SubjectEngine {
    private static final String TAG = "AliveClean";
    /** 送入算法的长边上限：兼顾质量与 Binder/模型耗时。 */
    private static final int MAX_EDGE = 1440;
    private static final long TIMEOUT_MS = 8000;

    private SubjectEngine() { }

    /**
     * 生成与 photo 同尺寸的主体蒙版（白色 = 主体，alpha 有效）。
     *
     * @param selection 归一化选区；全图时传 new RectF(0,0,1,1)
     */
    static Bitmap subjectMask(Context context, Bitmap photo, RectF selection) {
        Rect crop = cropOf(photo, selection);
        Bitmap mask = xiaomiMask(context, photo, crop);
        if (mask == null) AliveLog.i(TAG, "subject mask unavailable (engine returned nothing)");
        return mask;
    }

    private static Rect cropOf(Bitmap photo, RectF selection) {
        if (selection == null) return new Rect(0, 0, photo.getWidth(), photo.getHeight());
        int left = clamp(Math.round(selection.left * photo.getWidth()), 0, photo.getWidth() - 1);
        int top = clamp(Math.round(selection.top * photo.getHeight()), 0, photo.getHeight() - 1);
        int right = clamp(Math.round(selection.right * photo.getWidth()), left + 1, photo.getWidth());
        int bottom = clamp(Math.round(selection.bottom * photo.getHeight()), top + 1, photo.getHeight());
        return new Rect(left, top, right, bottom);
    }

    private static Bitmap xiaomiMask(Context context, Bitmap photo, Rect crop) {
        boolean fullCrop = crop.left == 0 && crop.top == 0
                && crop.right == photo.getWidth() && crop.bottom == photo.getHeight();
        Bitmap input = null;
        Bitmap scaled = null;
        try {
            input = fullCrop ? photo : Bitmap.createBitmap(photo, crop.left, crop.top, crop.width(), crop.height());
            long edge = Math.max(input.getWidth(), input.getHeight());
            scaled = input;
            if (edge > MAX_EDGE) {
                float factor = MAX_EDGE / (float) edge;
                scaled = Bitmap.createScaledBitmap(input, Math.max(1, Math.round(input.getWidth() * factor)),
                        Math.max(1, Math.round(input.getHeight() * factor)), true);
            }
            Bitmap raw = AicrSegmentClient.subjectMask(context, scaled, false, true, TIMEOUT_MS);
            if (raw == null) return null;
            return composeMask(raw, photo, crop);
        } catch (Throwable error) {
            AliveLog.w(TAG, "xiaomi subject mask failed", error);
            return null;
        } finally {
            if (scaled != null && scaled != input) recycle(scaled);
            if (!fullCrop) recycle(input);
        }
    }

    private static void recycle(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) bitmap.recycle();
    }

    /** 把算法返回的位图（可能是 alpha 通道，也可能是灰度）铺回原图尺寸的蒙版。 */
    private static Bitmap composeMask(Bitmap raw, Bitmap photo, Rect crop) {
        Bitmap mask = Bitmap.createBitmap(photo.getWidth(), photo.getHeight(), Bitmap.Config.ARGB_8888);
        mask.eraseColor(Color.TRANSPARENT);
        boolean useAlpha = raw.hasAlpha();
        int width = raw.getWidth();
        int height = raw.getHeight();
        int[] pixels = new int[width * height];
        raw.getPixels(pixels, 0, width, 0, 0, width, height);
        int[] output = new int[width * height];
        for (int i = 0; i < pixels.length; i++) {
            int pixel = pixels[i];
            int value = useAlpha ? (pixel >>> 24) : luminance(pixel);
            int alpha = clamp(value, 0, 255);
            output[i] = (alpha << 24) | 0x00FFFFFF;
        }
        Bitmap normalized = Bitmap.createBitmap(output, width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mask);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG);
        canvas.drawBitmap(normalized, null, new Rect(crop.left, crop.top, crop.right, crop.bottom), paint);
        normalized.recycle();
        recycle(raw);
        AliveLog.i(TAG, "subject mask composed " + mask.getWidth() + "x" + mask.getHeight()
                + " from " + width + "x" + height + " alpha=" + useAlpha);
        return mask;
    }

    private static int luminance(int pixel) {
        int red = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue = pixel & 0xFF;
        return (red * 299 + green * 587 + blue * 114) / 1000;
    }

    private static int clamp(int value, int min, int max) {
        return value < min ? min : Math.min(value, max);
    }
}
