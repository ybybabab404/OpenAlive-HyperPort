package com.miui.aod.util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.miui.aod.Utils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public final class FileUtils {
    public static boolean __createNewFile(File file) {
        makeSureParentExist(file);
        if (file.exists()) {
            delete(file);
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            Log.e("FileUtils", "error...", e);
            return false;
        }
    }

    public static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.wtf("FileUtils", "close error", e);
            }
        }
    }

    public static void copy(File file, File file2) {
        makeSureParentExist(file2);
        copy(new FileInputStream(file), new FileOutputStream(file2));
    }

    public static void copy(InputStream inputStream, OutputStream outputStream) {
        try {
            byte[] bArr = new byte[524288];
            inputStream = makeInputBuffered(inputStream);
            outputStream = makeOutputBuffered(outputStream);
            while (true) {
                int i = inputStream.read(bArr);
                if (i == -1) {
                    closeStream(inputStream);
                    closeStream(outputStream);
                    return;
                } else {
                    outputStream.write(bArr, 0, i);
                    outputStream.flush();
                }
            }
        } catch (Throwable th) {
            closeStream(inputStream);
            closeStream(outputStream);
            throw th;
        }
    }

    public static void createNewFile(File file) {
        __createNewFile(file);
    }

    public static void delete(File file) {
        if (file == null || !file.exists() || file.delete()) {
            return;
        }
        throw new RuntimeException(file.getAbsolutePath() + " doesn't be deleted!");
    }

    public static void deleteFileAll(File file) {
        if (file.exists()) {
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null) {
                file.delete();
                return;
            }
            for (File file2 : fileArrListFiles) {
                if (file2.isDirectory()) {
                    deleteFileAll(file2);
                } else {
                    file2.delete();
                }
            }
            file.delete();
        }
    }

    public static void fileCopy(String str, String str2) {
        try {
            FileChannel channel = new FileInputStream(str).getChannel();
            try {
                FileChannel channel2 = new FileOutputStream(str2).getChannel();
                try {
                    channel2.transferFrom(channel, 0L, channel.size());
                    channel2.close();
                    channel.close();
                } catch (Throwable th) {
                    if (channel2 == null) {
                        throw th;
                    }
                    try {
                        channel2.close();
                        throw th;
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                        throw th;
                    }
                    Log.wtf("FileUtils", "error occur while copy", e);
                }
            } catch (Throwable th3) {
                if (channel == null) {
                    throw th3;
                }
                try {
                    channel.close();
                    throw th3;
                } catch (Throwable th4) {
                    th3.addSuppressed(th4);
                    throw th3;
                }
            }
        } catch (Exception e) {
            Log.wtf("FileUtils", "error occur while copy", e);
        }
    }

    public static void fileCopyFromUri(Context context, Uri uri, String str) {
        try {
            InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(str);
                try {
                    copy(inputStreamOpenInputStream, fileOutputStream);
                    fileOutputStream.close();
                    if (inputStreamOpenInputStream != null) {
                        inputStreamOpenInputStream.close();
                    }
                } catch (Throwable th) {
                    try {
                        fileOutputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                if (inputStreamOpenInputStream != null) {
                    try {
                        inputStreamOpenInputStream.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        } catch (Exception e) {
            Log.wtf("FileUtils", "error occur while copy", e);
        }
    }

    public static String generateRandomFilename() {
        int iNextInt = new Random().nextInt();
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(13);
        int i2 = calendar.get(12);
        int i3 = calendar.get(10);
        int i4 = calendar.get(5);
        int i5 = calendar.get(2) + 1;
        String str = calendar.get(1) + "_" + i5 + "_" + i4 + "_" + i3 + "" + i2 + "" + i + "_";
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        if (iNextInt <= 0) {
            iNextInt *= -1;
        }
        sb.append(iNextInt);
        return sb.toString();
    }

    public static InputStream makeInputBuffered(InputStream inputStream) {
        return inputStream instanceof BufferedInputStream ? inputStream : new BufferedInputStream(inputStream, 524288);
    }

    public static OutputStream makeOutputBuffered(OutputStream outputStream) {
        return outputStream instanceof BufferedOutputStream ? outputStream : new BufferedOutputStream(outputStream, 524288);
    }

    public static void makeSureFileExist(File file) {
        if (file.exists()) {
            return;
        }
        makeSureParentExist(file);
        createNewFile(file);
    }

    public static void makeSureParentExist(File file) {
        File parentFile = file.getParentFile();
        if (parentFile == null || parentFile.exists()) {
            return;
        }
        parentFile.mkdirs();
    }

    public static String parseStringFromFilePath(Context context, String str) {
        String stringFromInputStream = null;
        try {
            InputStream inputStreamOpenInputStream = Utils.isUri(str) ? context.getContentResolver().openInputStream(Uri.parse(str)) : new FileInputStream(str);
            try {
                stringFromInputStream = parseStringFromInputStream(inputStreamOpenInputStream);
                if (inputStreamOpenInputStream != null) {
                    inputStreamOpenInputStream.close();
                }
                return stringFromInputStream;
            } catch (Throwable th) {
                if (inputStreamOpenInputStream != null) {
                    try {
                        inputStreamOpenInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (IOException e) {
            Log.wtf("FileUtils", "parseStringFromFilePath error", e);
            return stringFromInputStream;
        }
    }

    public static String parseStringFromFilePath(String str) {
        return parseStringFromFilePath(null, str);
    }

    public static String parseStringFromInputStream(InputStream inputStream) {
        String string = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            try {
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        string = sb.toString();
                        bufferedReader.close();
                        return string;
                    }
                    sb.append(line);
                    Log.wtf("FileUtils", "parseStringFromInputStream error", e);
                    return string;
                }
            } catch (Throwable th) {
                try {
                    bufferedReader.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            Log.wtf("FileUtils", "parseStringFromInputStream error", e);
            return string;
        }
    }
}
