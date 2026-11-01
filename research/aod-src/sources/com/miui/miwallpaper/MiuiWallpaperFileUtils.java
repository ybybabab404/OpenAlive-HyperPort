package com.miui.miwallpaper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class MiuiWallpaperFileUtils {
    public static boolean chmod(File file, int i) {
        if (file.exists()) {
            return chmod(file.getPath(), i);
        }
        Log.e("MiuiWallpaperFileUtils", "chmod param error file does not exist: file = " + file);
        return false;
    }

    public static boolean chmod(String str, int i) {
        if (str.length() < 1) {
            Log.e("MiuiWallpaperFileUtils", "chmod param error: path = " + str);
            return false;
        }
        try {
            Os.chmod(str, i);
            return true;
        } catch (ErrnoException e) {
            Log.e("MiuiWallpaperFileUtils", "chmod fail: mode = " + i, e);
            return false;
        }
    }

    public static boolean copyFile(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            Log.e("MiuiWallpaperFileUtils", "copyFile fail, param error: oriPath = " + str + ", copyPath = " + str2);
            return false;
        }
        if (new File(str2).exists()) {
            removeFile(str2);
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            try {
                copyStreamToWallpaperFile(fileInputStream, str2);
                fileInputStream.close();
                return true;
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            Log.e("MiuiWallpaperFileUtils", "Failed to copy( " + str + " to + " + str2 + " ): " + e);
            return false;
        }
    }

    public static boolean copyStreamToWallpaperFile(InputStream inputStream, String str) {
        if (inputStream == null || str == null) {
            Log.e("MiuiWallpaperFileUtils", "copyStreamToWallpaperFile fail, param error: inputStream = " + inputStream + ", outPath = " + str);
            return false;
        }
        File file = new File(str);
        if (file.exists()) {
            removeFile(str);
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                boolean zChmod = chmod(str, 511);
                Class<?> cls = Class.forName("android.os.FileUtils");
                Method method = cls.getMethod("copy", InputStream.class, OutputStream.class);
                long jCurrentTimeMillis = System.currentTimeMillis();
                method.invoke(cls, inputStream, fileOutputStream);
                if (!zChmod) {
                    chmod(str, 511);
                }
                long jCurrentTimeMillis2 = System.currentTimeMillis();
                fileOutputStream.getFD().sync();
                Log.i("MiuiWallpaperFileUtils", "copyStreamToWallpaperFile: outPath=" + str + " cpCost=" + (System.currentTimeMillis() - jCurrentTimeMillis) + " sCost=" + (jCurrentTimeMillis - jCurrentTimeMillis2) + ", chmod=" + zChmod);
                fileOutputStream.close();
                return true;
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Exception e) {
            Log.e("MiuiWallpaperFileUtils", "WriteToFile fail : ", e);
            return false;
        }
    }

    public static boolean mkdirs(File file, int i) {
        if (file.exists()) {
            return file.isDirectory();
        }
        boolean zMkdir = false;
        if (!mkdirs(file.getParentFile(), i)) {
            return false;
        }
        try {
            zMkdir = file.mkdir();
            Log.d("MiuiWallpaperFileUtils", "mkdirs::result = " + zMkdir + " file = " + file);
            chmod(file, i);
            return zMkdir;
        } catch (Exception e) {
            Log.e("MiuiWallpaperFileUtils", "mkdirs failed: ", e);
            return zMkdir;
        }
    }

    public static Bitmap readFromFile(ParcelFileDescriptor parcelFileDescriptor) {
        return readFromFile(parcelFileDescriptor, null);
    }

    public static Bitmap readFromFile(ParcelFileDescriptor parcelFileDescriptor, Bitmap bitmap) {
        if (parcelFileDescriptor == null) {
            return null;
        }
        try {
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fileDescriptor, new Rect(-1, -1, -1, -1), options);
            if (bitmap != null && !bitmap.isRecycled() && bitmap.isMutable() && options.outWidth <= bitmap.getWidth() && options.outHeight <= bitmap.getHeight()) {
                options.inBitmap = bitmap;
            }
            Log.i("MiuiWallpaperFileUtils", "readFromFile: inBitmap=" + options.inBitmap);
            options.inJustDecodeBounds = false;
            options.inMutable = true;
            return BitmapFactory.decodeFileDescriptor(fileDescriptor, new Rect(-1, -1, -1, -1), options);
        } catch (Exception e) {
            Log.w("MiuiWallpaperFileUtils", "Can't decode file", e);
            return null;
        }
    }

    public static void removeFile(String str) {
        try {
            File file = new File(str);
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        } catch (Exception e) {
            Log.e("MiuiWallpaperFileUtils", "removeFile failed: ", e);
        }
    }

    public static void removeFile(List list) {
        for (int i = 0; i < list.size(); i++) {
            removeFile((String) list.get(i));
        }
    }

    public static boolean writeToFile(Bitmap bitmap, String str) {
        if (bitmap == null) {
            Log.e("MiuiWallpaperFileUtils", "writeToFile fail, bitmap is null");
            return false;
        }
        File file = new File(str);
        file.delete();
        if (file.getParent() != null) {
            File file2 = new File(file.getParent());
            if (!file2.exists()) {
                mkdirs(file2, 511);
                Log.d("MiuiWallpaperFileUtils", "writeToFile, path = " + file.getAbsolutePath());
                chmod(file2, 511);
            }
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                boolean zChmod = chmod(str, 511);
                Bitmap.Config config = bitmap.getConfig();
                Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
                int i = 100;
                if (config == Bitmap.Config.ALPHA_8) {
                    ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bitmap.getWidth() * bitmap.getHeight());
                    bitmap.copyPixelsToBuffer(byteBufferAllocate);
                    fileOutputStream.write(byteBufferAllocate.array());
                } else {
                    if (config == Bitmap.Config.ARGB_8888) {
                        compressFormat = Bitmap.CompressFormat.WEBP_LOSSLESS;
                        i = 0;
                    }
                    bitmap.compress(compressFormat, i, fileOutputStream);
                }
                Log.d("MiuiWallpaperFileUtils", "writeToFile: config=" + config + ", format=" + compressFormat + ", quality=" + i + ", chmod=" + zChmod);
                if (!zChmod) {
                    chmod(str, 511);
                }
                fileOutputStream.getFD().sync();
                fileOutputStream.close();
                return true;
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Exception e) {
            Log.e("MiuiWallpaperFileUtils", "WriteToFile fail : ", e);
            return false;
        }
    }
}
