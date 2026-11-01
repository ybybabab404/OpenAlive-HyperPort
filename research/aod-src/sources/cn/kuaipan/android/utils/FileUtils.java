package cn.kuaipan.android.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class FileUtils {
    public static final File EXTERNAL_STORAGE_ANDROID_DATA_DIRECTORY = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
    public static final Object mSync = new Object();
    public static File sExternalCacheDir;

    public static boolean deleteChildren(File file) {
        File[] fileArrListFiles;
        if (file == null || !file.exists() || !file.isDirectory() || (fileArrListFiles = file.listFiles()) == null) {
            return true;
        }
        boolean z = true;
        for (File file2 : fileArrListFiles) {
            z = deletes(file2) && z;
        }
        return z;
    }

    public static boolean deletes(File file) {
        if (file == null || !file.exists()) {
            return true;
        }
        if (file.isDirectory()) {
            deleteChildren(file);
        }
        return file.delete();
    }

    public static File getCacheDir(Context context, String str, boolean z) {
        File cacheDir = getCacheDir(context, z);
        if (cacheDir == null) {
            return null;
        }
        File file = new File(cacheDir, str);
        if (!file.exists()) {
            file.mkdirs();
            if (!file.exists() || !file.isDirectory()) {
                Log.w("FileUtils", "Unable to create cache directory:" + file);
                return null;
            }
        }
        return file;
    }

    public static File getCacheDir(Context context, boolean z) {
        return getCacheDir(context, z, true);
    }

    public static File getCacheDir(Context context, boolean z, boolean z2) {
        return z ? getExternalCacheDir(context, z2) : context.getCacheDir();
    }

    public static File getExternalCacheDir(Context context, boolean z) {
        synchronized (mSync) {
            try {
                if (sExternalCacheDir == null) {
                    sExternalCacheDir = getExternalStorageAppCacheDirectory(context.getPackageName());
                }
                if (!sExternalCacheDir.exists()) {
                    try {
                        new File(EXTERNAL_STORAGE_ANDROID_DATA_DIRECTORY, ".nomedia").createNewFile();
                    } catch (IOException unused) {
                    }
                    sExternalCacheDir.mkdirs();
                    if (z && (!sExternalCacheDir.exists() || !sExternalCacheDir.isDirectory())) {
                        Log.w("FileUtils", "Unable to create external cache directory");
                        return null;
                    }
                }
                return sExternalCacheDir;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static File getExternalStorageAppCacheDirectory(String str) {
        return new File(new File(EXTERNAL_STORAGE_ANDROID_DATA_DIRECTORY, str), "cache");
    }
}
