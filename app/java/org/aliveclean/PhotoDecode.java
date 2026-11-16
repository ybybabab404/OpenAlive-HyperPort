package org.aliveclean;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 照片解码：BitmapFactory + inSampleSize（先取边界，再按 2 的幂抽样，最后精确缩放）。
 *
 * 不用 ImageDecoder 是因为实测它在同一张大图上慢 3-4 倍，且目标尺寸压到 64px 也要 1 秒
 * 多——它基本是整幅解码后再缩。实测（9248×6944 JPEG，26MB）：
 *   ImageDecoder target=2048 → 1107~1511ms
 *   ImageDecoder target=64   →  971~1158ms
 *   BitmapFactory 全尺寸    →  573~964ms
 *   BitmapFactory inSampleSize=4 → 261~592ms
 */
final class PhotoDecode {
    private PhotoDecode() { }

    /** 只读尺寸，几毫秒；用于校验"这是一张能读的照片"和算采样率。 */
    static int[] bounds(File file) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try (InputStream in = new FileInputStream(file)) {
            BitmapFactory.decodeStream(in, null, options);
        }
        if (options.outWidth <= 0 || options.outHeight <= 0) throw new IOException("无法读取照片尺寸");
        return new int[]{options.outWidth, options.outHeight};
    }

    static Bitmap decode(File file, int maxEdge) throws IOException {
        return decode(file, maxEdge, true);
    }

    /** @param allowBelow 见 {@link #options(int[], int, boolean)}；导出传 false。 */
    static Bitmap decode(File file, int maxEdge, boolean allowBelow) throws IOException {
        int[] size = bounds(file);
        BitmapFactory.Options options = options(size, maxEdge, allowBelow);
        long start = System.currentTimeMillis();
        Bitmap bitmap;
        try (InputStream in = new FileInputStream(file)) {
            bitmap = BitmapFactory.decodeStream(in, null, options);
        }
        bitmap = finish(bitmap);
        AliveLog.i("AliveClean", "photo decode " + size[0] + "x" + size[1] + " sample=" + options.inSampleSize
                + " -> " + bitmap.getWidth() + "x" + bitmap.getHeight() + " " + (System.currentTimeMillis() - start) + "ms");
        return bitmap;
    }

    static Bitmap decode(ContentResolver resolver, Uri uri, int maxEdge) throws IOException {
        BitmapFactory.Options probe = new BitmapFactory.Options();
        probe.inJustDecodeBounds = true;
        // 先用 FileDescriptor 取边界：provider 给的是普通文件，offset 为 0。
        try (AssetFileDescriptor descriptor = resolver.openAssetFileDescriptor(uri, "r")) {
            if (descriptor == null) throw new IOException("无法读取照片");
            BitmapFactory.decodeFileDescriptor(descriptor.getFileDescriptor(), null, probe);
        }
        if (probe.outWidth <= 0 || probe.outHeight <= 0) throw new IOException("无法读取照片尺寸");
        int[] size = {probe.outWidth, probe.outHeight};
        BitmapFactory.Options options = options(size, maxEdge);
        long start = System.currentTimeMillis();
        Bitmap bitmap;
        try (AssetFileDescriptor descriptor = resolver.openAssetFileDescriptor(uri, "r")) {
            if (descriptor == null) throw new IOException("无法读取照片");
            bitmap = BitmapFactory.decodeFileDescriptor(descriptor.getFileDescriptor(), null, options);
        }
        bitmap = finish(bitmap);
        AliveLog.i("AliveClean", "photo decode uri " + size[0] + "x" + size[1]
                + " sample=" + options.inSampleSize + " -> " + bitmap.getWidth() + "x" + bitmap.getHeight()
                + " " + (System.currentTimeMillis() - start) + "ms");
        return bitmap;
    }

    private static BitmapFactory.Options options(int[] size, int maxEdge) {
        return options(size, maxEdge, true);
    }

    /**
     * @param allowBelow true 时允许"再降一档"，让结果落在 maxEdge 的 0.7~1.4 倍之间（只用于显示，
     *                   画面近似的分辨率下解码像素少一半，明显更快）；导出用 false，保证不低于目标尺寸。
     */
    private static BitmapFactory.Options options(int[] size, int maxEdge, boolean allowBelow) {
        int edge = Math.max(size[0], size[1]);
        int sample = 1;
        while (edge / sample > maxEdge * 2) sample *= 2;
        if (allowBelow) while (edge / (sample * 2) >= maxEdge * 0.7f) sample *= 2;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sample;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return options;
    }

    private static Bitmap finish(Bitmap bitmap) throws IOException {
        if (bitmap == null) throw new IOException("无法解码照片");
        // 采样已经保证长边落在 [maxEdge, 2*maxEdge)；不再做精确缩放：
        // 多出的一点分辨率对 GPU 纹理无害，而 createScaledBitmap 要多一遍全图插值。
        return bitmap;
    }
}
