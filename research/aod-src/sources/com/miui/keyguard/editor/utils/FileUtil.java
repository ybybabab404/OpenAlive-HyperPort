package com.miui.keyguard.editor.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.text.TextUtils;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import com.xiaomi.onetrack.util.ac;
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
import java.io.Reader;
import java.math.BigInteger;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import miuix.core.util.FileUtils;
import miuix.core.util.IOUtils;

/* JADX INFO: loaded from: classes2.dex */
public class FileUtil {
    public static final String[] HEX_DIGITS = {"0", "1", ExifInterface.GPS_MEASUREMENT_2D, ExifInterface.GPS_MEASUREMENT_3D, "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    public static final Boolean SUPPORT_DEPTH_VIDEO_FLAG;

    static {
        SUPPORT_DEPTH_VIDEO_FLAG = Boolean.valueOf(new File("product/media/wallpaper/sysui_editor_wallpaper_group/enable_video_depth").exists() && !CommonUtils.INSTANCE.isRuRegion());
    }

    public static void addFolderToZip(String str, File file, ZipOutputStream zipOutputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(file.getName());
        sb.append(file.isDirectory() ? "/" : "");
        String string = sb.toString();
        if (file.isDirectory()) {
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null) {
                Log.i("FileUtil", "addFolderToZip: files is null");
                return;
            }
            for (File file2 : fileArrListFiles) {
                addFolderToZip(string, file2, zipOutputStream);
            }
            return;
        }
        byte[] bArr = new byte[1024];
        FileInputStream fileInputStream = new FileInputStream(file);
        zipOutputStream.putNextEntry(new ZipEntry(string));
        while (true) {
            int i = fileInputStream.read(bArr);
            if (i == -1) {
                fileInputStream.close();
                return;
            }
            zipOutputStream.write(bArr, 0, i);
        }
    }

    public static boolean atomicWriteToFile(String str, String str2) {
        String str3 = str + ".tmp";
        try {
            if (!writeStringToFile(str3, str2)) {
                Log.e("FileUtil", "atomicWriteToFile: failed to write temp file: " + str3);
                delete(str3);
                return false;
            }
            File file = new File(str3);
            File file2 = new File(str);
            if (file2.exists() && !file2.delete()) {
                Log.w("FileUtil", "atomicWriteToFile: failed to delete existing target file: " + str);
            }
            if (file.renameTo(file2)) {
                return true;
            }
            Log.w("FileUtil", "atomicWriteToFile: renameTo failed, fallback to copy: " + str);
            boolean zCopyFile = copyFile(file, file2);
            delete(str3);
            if (zCopyFile) {
                return true;
            }
            Log.e("FileUtil", "atomicWriteToFile: fallback copy also failed: " + str);
            return false;
        } catch (Exception e) {
            Log.e("FileUtil", "atomicWriteToFile: exception", e);
            delete(str3);
            return false;
        }
    }

    public static String byteArrayToString(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            stringBuffer.append(byteToHexString(b));
        }
        return stringBuffer.toString();
    }

    public static String byteToHexString(byte b) {
        int i = b;
        if (b < 0) {
            i = b + 256;
        }
        StringBuilder sb = new StringBuilder();
        String[] strArr = HEX_DIGITS;
        sb.append(strArr[i / 16]);
        sb.append(strArr[i % 16]);
        return sb.toString();
    }

    /* JADX WARN: Not initialized variable reg: 2, insn: 0x001d: MOVE (r0 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:10:0x001d */
    public static String calculateMD5(File file) throws Throwable {
        FileInputStream fileInputStream;
        InputStream inputStream;
        InputStream inputStream2 = null;
        try {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                fileInputStream = new FileInputStream(file);
                try {
                    byte[] bArr = new byte[AutoBottomSheetKt.ALIGN_SCREEN_START];
                    while (true) {
                        int i = fileInputStream.read(bArr);
                        if (i == -1) {
                            break;
                        }
                        messageDigest.update(bArr, 0, i);
                    }
                    StringBuilder sb = new StringBuilder(new BigInteger(1, messageDigest.digest()).toString(16));
                    while (sb.length() < 32) {
                        sb.insert(0, "0");
                    }
                    String string = sb.toString();
                    IOUtils.closeQuietly((InputStream) fileInputStream);
                    return string;
                } catch (IOException | NoSuchAlgorithmException e) {
                    e = e;
                    Log.e("FileUtil", "calculateMD5 e:" + e.getMessage());
                    IOUtils.closeQuietly((InputStream) fileInputStream);
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                inputStream2 = inputStream;
                IOUtils.closeQuietly(inputStream2);
                throw th;
            }
        } catch (IOException | NoSuchAlgorithmException e2) {
            e = e2;
            fileInputStream = null;
        } catch (Throwable th2) {
            th = th2;
            IOUtils.closeQuietly(inputStream2);
            throw th;
        }
    }

    public static int chmod(File file, int i) {
        if (!file.exists()) {
            return -1;
        }
        try {
            Os.chmod(file.getPath(), i);
            return 0;
        } catch (ErrnoException e) {
            Log.e("FileUtil", "chmod fail: mode = " + i, e);
            return 0;
        }
    }

    public static int chmod(String str, int i) {
        Log.i("FileUtil", "chmod path = " + str + ", mode = " + i);
        if (TextUtils.isEmpty(str) || str.length() < 1) {
            return -1;
        }
        return chmod(new File(str), i);
    }

    public static boolean close(Closeable... closeableArr) {
        if (closeableArr == null || closeableArr.length == 0) {
            return false;
        }
        boolean z = true;
        for (Closeable closeable : closeableArr) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
                z = false;
            }
        }
        return z;
    }

    public static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.wtf("FileUtil", "close error", e);
            }
        }
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

    public static boolean copyExternalFile(Context context, Uri uri, File file) throws Throwable {
        BufferedOutputStream bufferedOutputStream;
        if (uri == null) {
            return false;
        }
        BufferedInputStream bufferedInputStream = null;
        try {
            ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            try {
                BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(parcelFileDescriptorOpenFileDescriptor.getFileDescriptor()));
                try {
                    bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                    try {
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int i = bufferedInputStream2.read(bArr);
                            if (i != -1) {
                                bufferedOutputStream.write(bArr, 0, i);
                            } else {
                                try {
                                    parcelFileDescriptorOpenFileDescriptor.close();
                                    IOUtils.closeQuietly((InputStream) bufferedInputStream2);
                                    IOUtils.closeQuietly((OutputStream) bufferedOutputStream);
                                    return true;
                                } catch (Exception e) {
                                    e = e;
                                    bufferedInputStream = bufferedInputStream2;
                                } catch (Throwable th) {
                                    th = th;
                                    bufferedInputStream = bufferedInputStream2;
                                    IOUtils.closeQuietly((InputStream) bufferedInputStream);
                                    IOUtils.closeQuietly((OutputStream) bufferedOutputStream);
                                    throw th;
                                }
                            }
                            e.printStackTrace();
                            IOUtils.closeQuietly((InputStream) bufferedInputStream);
                            IOUtils.closeQuietly((OutputStream) bufferedOutputStream);
                            return false;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedInputStream = bufferedInputStream2;
                        if (parcelFileDescriptorOpenFileDescriptor != null) {
                            try {
                                parcelFileDescriptorOpenFileDescriptor.close();
                            } catch (Throwable th3) {
                                try {
                                    try {
                                        th.addSuppressed(th3);
                                    } catch (Exception e2) {
                                        e = e2;
                                        e.printStackTrace();
                                        IOUtils.closeQuietly((InputStream) bufferedInputStream);
                                        IOUtils.closeQuietly((OutputStream) bufferedOutputStream);
                                        return false;
                                    }
                                } catch (Throwable th4) {
                                    th = th4;
                                    IOUtils.closeQuietly((InputStream) bufferedInputStream);
                                    IOUtils.closeQuietly((OutputStream) bufferedOutputStream);
                                    throw th;
                                }
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    bufferedOutputStream = null;
                }
            } catch (Throwable th6) {
                th = th6;
                bufferedOutputStream = null;
            }
        } catch (Exception e3) {
            e = e3;
            bufferedOutputStream = null;
        } catch (Throwable th7) {
            th = th7;
            bufferedOutputStream = null;
        }
    }

    public static boolean copyFile(File file, File file2) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                boolean zCopyToFile = copyToFile(fileInputStream, file2);
                fileInputStream.close();
                return zCopyToFile;
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            Log.d("FileUtil", "copyFile: " + e);
            return false;
        }
    }

    public static boolean copyFile(String str, String str2) {
        File file = new File(str2);
        makeFileExists(file);
        return FileUtils.copyFile(new File(str), file);
    }

    public static boolean copyFolder(File file, File file2) {
        try {
            if (file.isFile()) {
                file2.getParentFile().mkdirs();
                if (copyFile(file.getPath(), file2.getPath())) {
                    if (file2.exists()) {
                        return true;
                    }
                    Log.e("FileUtil", "target does not exists after copyFile: " + file2.getAbsolutePath());
                    return false;
                }
                Log.e("FileUtil", "copyFile fail. src: " + file.getAbsolutePath() + " target: " + file2.getAbsolutePath());
                return false;
            }
            if (!file.isDirectory()) {
                Log.e("FileUtil", "source is not a file or directory: " + file.getAbsolutePath());
                return false;
            }
            if (!file2.exists()) {
                if (!file2.mkdirs()) {
                    Log.e("FileUtil", "targetFolder mkdirs fail: " + file2.getAbsolutePath());
                    return false;
                }
                if (!file2.exists()) {
                    Log.e("FileUtil", "target does not exists after mkdirs: " + file2.getAbsolutePath());
                    return false;
                }
            }
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null) {
                Log.e("FileUtil", "copying folder is empty: " + file.getAbsolutePath());
                return true;
            }
            for (File file3 : fileArrListFiles) {
                if (!copyFolder(file3, new File(file2, file3.getName()))) {
                    Log.e("FileUtil", "copyFolder fail. src: " + file3.getAbsolutePath());
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            Log.e("FileUtil", "copyFolder throw exception : " + e);
            return false;
        }
    }

    public static String copyPresetVideo(Context context, File file, String str) throws Exception {
        Log.d("FileUtil", "copyPresetVideo previewPath 0000:" + str + " dir = " + file.getAbsolutePath());
        if (TextUtils.isEmpty(str)) {
            throw new Exception("previewPath == null");
        }
        Log.d("FileUtil", "copyPresetVideo previewPath 1111:" + str);
        if (!isUri(str)) {
            File file2 = new File(str);
            String name = file2.getName();
            if (!file2.exists()) {
                throw new Exception(str + " not exist");
            }
            fileCopy(str, file.getAbsolutePath() + File.separator + file2.getName());
            return name;
        }
        String strSubstring = str.substring(str.lastIndexOf(47) + 1);
        String str2 = file.getAbsolutePath() + File.separator + strSubstring;
        fileCopyFromUri(context, Uri.parse(str), str2);
        Log.d("FileUtil", "copyPresetVideo 4444 result :" + strSubstring + " videoPath = " + str2);
        return strSubstring;
    }

    public static boolean copyToFile(InputStream inputStream, File file) {
        if (inputStream == null) {
            return false;
        }
        try {
            if (file.exists() && !file.delete()) {
                return false;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                byte[] bArr = new byte[AutoBottomSheetKt.ALIGN_SCREEN_TOP];
                while (true) {
                    int i = inputStream.read(bArr);
                    if (i < 0) {
                        fileOutputStream.close();
                        return true;
                    }
                    fileOutputStream.write(bArr, 0, i);
                    Log.e("FileUtil", "copyToFile: " + e);
                    return false;
                }
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            Log.e("FileUtil", "copyToFile: " + e);
            return false;
        }
    }

    public static File createDrawableDir(Context context, String str) {
        File file = new File(str + File.separator + ".videodownload");
        if (!file.exists()) {
            context.getExternalCacheDir();
            file.mkdirs();
        }
        return file;
    }

    public static void delete(String str) {
        File file = new File(str);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    public static boolean deleteFolder(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.w("FileUtil", "deleteFolder: filePath is empty !");
            return false;
        }
        String str2 = File.separator;
        if (!str.endsWith(str2)) {
            str = str + str2;
        }
        try {
            File file = new File(str);
            if (file.exists() && file.isDirectory()) {
                File[] fileArrListFiles = file.listFiles();
                if (fileArrListFiles != null && fileArrListFiles.length != 0) {
                    for (File file2 : fileArrListFiles) {
                        if (file2 != null) {
                            String absolutePath = file2.getAbsolutePath();
                            if (file2.isFile()) {
                                delete(absolutePath);
                            } else {
                                deleteFolder(absolutePath);
                            }
                        }
                    }
                    return file.delete();
                }
                StringBuilder sb = new StringBuilder();
                sb.append("deleteFolder: files=");
                sb.append(fileArrListFiles == null ? "null" : Arrays.toString(fileArrListFiles));
                Log.w("FileUtil", sb.toString());
                return false;
            }
            Log.w("FileUtil", "deleteFolder: file " + file.getAbsolutePath() + " not exist !");
            return false;
        } catch (Exception e) {
            Log.e("FileUtil", "deleteFolder: " + e);
            return false;
        }
    }

    public static final String encodeMD5(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            return byteArrayToString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean fastCopy(String str, String str2, boolean z) {
        String str3;
        String str4;
        int i = 0;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || !isFileExist(str) || TextUtils.equals(str, str2)) {
            Log.i("FileUtil", "fastCopy: error " + str + ac.b + str2);
            return false;
        }
        delete(str2);
        makeFileExists(str2);
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(str2);
                try {
                    FileChannel channel = fileInputStream.getChannel();
                    FileChannel channel2 = fileOutputStream.getChannel();
                    long size = channel.size();
                    long j = 0;
                    while (true) {
                        if (j >= size || i >= 3) {
                            str3 = "/";
                            break;
                        }
                        str3 = "/";
                        long jTransferTo = channel.transferTo(j, size - j, channel2);
                        if (jTransferTo <= 0) {
                            break;
                        }
                        j += jTransferTo;
                        if (j < size) {
                            i++;
                            Log.w("FileUtil", "fastCopy: partial transfer, retry " + i + " transferred=" + j + str3 + size);
                        }
                    }
                    if (z) {
                        fileOutputStream.getFD().sync();
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("fastCopy: ");
                    sb.append(j);
                    sb.append(str3);
                    sb.append(size);
                    if (i > 0) {
                        str4 = " retries=" + i;
                    } else {
                        str4 = "";
                    }
                    sb.append(str4);
                    Log.i("FileUtil", sb.toString());
                    if (j == size) {
                        fileOutputStream.close();
                        fileInputStream.close();
                        return true;
                    }
                    Log.e("FileUtil", "fastCopy: incomplete transfer " + j + str3 + size + ", fallback to copyFile");
                    delete(str2);
                    boolean zCopyFile = copyFile(str, str2);
                    fileOutputStream.close();
                    fileInputStream.close();
                    return zCopyFile;
                } catch (Throwable th) {
                    try {
                        fileOutputStream.close();
                        throw th;
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                fileInputStream.close();
                throw th3;
            }
            try {
                fileInputStream.close();
                throw th3;
            } catch (Throwable th4) {
                th3.addSuppressed(th4);
                throw th3;
            }
        } catch (Exception e) {
            Log.e("FileUtil", "fastCopy: exception " + e.getMessage());
            delete(str2);
            return copyFile(str, str2);
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
                    Log.wtf("FileUtil", "error occur while copy", e);
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
            Log.wtf("FileUtil", "error occur while copy", e);
        }
    }

    public static void fileCopyFromUri(Context context, Uri uri, String str) {
        Log.d("FileUtil", "copyPresetVideo fileCopyFromUri 2222 srcPath  " + uri + " destPath = " + str);
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(str);
                try {
                    copy(inputStreamOpenInputStream, fileOutputStream);
                    Log.d("FileUtil", "copyPresetVideo fileCopyFromUri 3333 end timediff =   " + (System.currentTimeMillis() - jCurrentTimeMillis));
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
            Log.wtf("FileUtil", "error occur while copy", e);
        }
    }

    public static Set getChildDirs(String str) {
        File file = new File(str);
        if (!file.exists()) {
            Log.e("FileUtil", "not exit:" + str);
            return null;
        }
        if (!file.canRead()) {
            Log.e("FileUtil", "inaccessible path:" + str);
            return null;
        }
        if (!file.isDirectory()) {
            Log.e("FileUtil", "not folder:" + str);
            return null;
        }
        File[] fileArrListFiles = file.listFiles();
        HashSet hashSet = new HashSet();
        if (fileArrListFiles == null) {
            Log.d("FileUtil", "empty folder:" + str);
            return hashSet;
        }
        for (File file2 : fileArrListFiles) {
            if (file2.isDirectory()) {
                hashSet.add(file2.getName());
            }
        }
        return hashSet;
    }

    public static String getFileExtension(String str) {
        int iLastIndexOf;
        if (TextUtils.isEmpty(str) || (iLastIndexOf = str.lastIndexOf(46)) == -1 || iLastIndexOf >= str.length() - 1) {
            return null;
        }
        return str.substring(iLastIndexOf + 1);
    }

    public static boolean isFileExist(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            return new File(str).exists();
        } catch (SecurityException unused) {
            return false;
        }
    }

    public static boolean isGifImage(String str) {
        return str.toLowerCase().endsWith(".gif");
    }

    public static boolean isSupportVideoDepth() {
        return SUPPORT_DEPTH_VIDEO_FLAG.booleanValue();
    }

    public static boolean isUri(String str) {
        return str != null && str.startsWith("content://");
    }

    public static boolean makeDirExists(String str) {
        if (str == null) {
            return false;
        }
        return new File(str).mkdirs();
    }

    public static void makeFileExists(File file) {
        try {
            if (file.exists()) {
                return;
            }
            new File(file.getParent()).mkdirs();
            file.createNewFile();
        } catch (Exception unused) {
            Log.e("FileUtil", "makeParentDirExists: $e");
        }
    }

    public static boolean makeFileExists(String str) {
        if (str == null) {
            return false;
        }
        try {
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            return file.createNewFile();
        } catch (IOException e) {
            Log.e("FileUtil", "makeFileExists: ", e);
            return false;
        }
    }

    public static InputStream makeInputBuffered(InputStream inputStream) {
        return inputStream instanceof BufferedInputStream ? inputStream : new BufferedInputStream(inputStream, 524288);
    }

    public static OutputStream makeOutputBuffered(OutputStream outputStream) {
        return outputStream instanceof BufferedOutputStream ? outputStream : new BufferedOutputStream(outputStream, 524288);
    }

    public static boolean mkdirs(File file, int i, int i2, int i3) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isDirectory();
        }
        if (!mkdirs(file.getParentFile(), i, i2, i3)) {
            return false;
        }
        try {
            boolean zMkdir = file.mkdir();
            Os.chmod(file.getPath(), i);
            Os.chown(file.getPath(), i2, i3);
            return zMkdir;
        } catch (Exception e) {
            Log.e("FileUtils", "mkdirs failed. ", e);
            return false;
        }
    }

    public static String readFileFromAssets(Context context, String str) {
        try {
            return readFileFromReader(new InputStreamReader(context.getResources().getAssets().open(str)));
        } catch (IOException e) {
            Log.e("FileUtil", "readFileFromAssets: ", e);
            return "";
        }
    }

    public static String readFileFromReader(Reader reader) throws Throwable {
        BufferedReader bufferedReader;
        IOException e;
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader2 = null;
        try {
            bufferedReader = new BufferedReader(reader);
            while (true) {
                try {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        sb.append(line);
                    } catch (IOException e2) {
                        e = e2;
                        Log.e("FileUtil", "readFileFromReader: ", e);
                    }
                } catch (Throwable th) {
                    th = th;
                    bufferedReader2 = bufferedReader;
                    IOUtils.closeQuietly(reader);
                    IOUtils.closeQuietly((Reader) bufferedReader2);
                    throw th;
                }
            }
        } catch (IOException e3) {
            bufferedReader = null;
            e = e3;
        } catch (Throwable th2) {
            th = th2;
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly((Reader) bufferedReader2);
            throw th;
        }
        IOUtils.closeQuietly(reader);
        IOUtils.closeQuietly((Reader) bufferedReader);
        return sb.toString();
    }

    public static String resolveFileSuffix(Context context, Uri uri) {
        try {
            String path = uri.getPath();
            if (TextUtils.isEmpty(path)) {
                path = context.getContentResolver().getType(uri);
            }
            return path != null ? resolveFileSuffix(path) : path;
        } catch (Exception e) {
            Log.e("FileUtil", "resolveFileSuffix e:" + e.getMessage());
            return null;
        }
    }

    public static String resolveFileSuffix(String str) {
        if (str != null) {
            try {
                String[] strArrSplit = str.split("/");
                if (strArrSplit.length > 1) {
                    return strArrSplit[strArrSplit.length - 1];
                }
            } catch (Exception e) {
                Log.e("FileUtil", "resolveFileSuffix e:" + e.getMessage());
                return null;
            }
        }
        return "";
    }

    public static boolean saveFile(String str, String str2) throws Throwable {
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return false;
        }
        FileOutputStream fileOutputStream = null;
        try {
            try {
                try {
                    File file = new File(str);
                    if (!file.exists()) {
                        new File(file.getParent()).mkdirs();
                        file.createNewFile();
                    }
                    FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                    try {
                        fileOutputStream2.write(str2.getBytes());
                        fileOutputStream2.close();
                    } catch (Exception e) {
                        fileOutputStream = fileOutputStream2;
                        e = e;
                        Log.e("FileUtil", "Exception", e);
                        if (fileOutputStream == null) {
                            return true;
                        }
                        fileOutputStream.close();
                    } catch (Throwable th) {
                        fileOutputStream = fileOutputStream2;
                        th = th;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e2) {
                                Log.e("FileUtil", "IOException", e2);
                            }
                        }
                        throw th;
                    }
                } catch (Exception e3) {
                    e = e3;
                }
                return true;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e4) {
            Log.e("FileUtil", "IOException", e4);
            return true;
        }
    }

    public static void unzip(File file, File file2, List list) throws IOException {
        if (file == null) {
            return;
        }
        Log.i("FileUtil", "unzip: zip file is " + file.getName() + "targetEntries is " + list);
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
        byte[] bArr = new byte[1024];
        while (true) {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            if (nextEntry == null) {
                zipInputStream.close();
                return;
            }
            String name = nextEntry.getName();
            if (list == null || list.contains(name)) {
                Log.i("FileUtil", "unzip: not target file is " + name);
                File file3 = new File(file2, name);
                makeFileExists(file3);
                if (nextEntry.isDirectory()) {
                    file3.mkdirs();
                } else {
                    FileOutputStream fileOutputStream = new FileOutputStream(file3);
                    while (true) {
                        int i = zipInputStream.read(bArr);
                        if (i == -1) {
                            break;
                        } else {
                            fileOutputStream.write(bArr, 0, i);
                        }
                    }
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            } else {
                Log.i("FileUtil", "unzip: target file is " + name);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [android.os.ParcelFileDescriptor] */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v5, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v5, types: [android.os.ParcelFileDescriptor$AutoCloseInputStream, java.io.InputStream] */
    public static void writeFdToFile(ParcelFileDescriptor parcelFileDescriptor, String str) throws Throwable {
        Throwable th;
        Exception e;
        FileOutputStream fileOutputStream;
        try {
            try {
                File file = new File((String) str);
                makeFileExists(file);
                str = new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
                try {
                    fileOutputStream = new FileOutputStream(file);
                    try {
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int i = str.read(bArr);
                            if (i == -1) {
                                IOUtils.closeQuietly((InputStream) str);
                                IOUtils.closeQuietly((OutputStream) fileOutputStream);
                                return;
                            }
                            fileOutputStream.write(bArr, 0, i);
                        }
                    } catch (Exception e2) {
                        e = e2;
                        Log.e("FileUtil", "writeFdToFile: " + e);
                        IOUtils.closeQuietly((InputStream) str);
                        IOUtils.closeQuietly((OutputStream) fileOutputStream);
                    }
                } catch (Exception e3) {
                    e = e3;
                    fileOutputStream = null;
                } catch (Throwable th2) {
                    th = th2;
                    parcelFileDescriptor = 0;
                    IOUtils.closeQuietly((InputStream) str);
                    IOUtils.closeQuietly((OutputStream) parcelFileDescriptor);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Exception e4) {
            str = 0;
            e = e4;
            fileOutputStream = null;
        } catch (Throwable th4) {
            str = 0;
            th = th4;
            parcelFileDescriptor = 0;
        }
    }

    public static boolean writeStringToFd(ParcelFileDescriptor parcelFileDescriptor, String str) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
            fileOutputStream.write(str.getBytes());
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            Log.e("FileUtil", "writeStringToFd: " + e);
            return false;
        }
    }

    public static boolean writeStringToFile(String str, String str2) {
        try {
            File file = new File(str);
            File parentFile = file.getParentFile();
            if (parentFile != null && !parentFile.exists()) {
                parentFile.mkdirs();
            }
            ParcelFileDescriptor parcelFileDescriptorOpen = ParcelFileDescriptor.open(file, 671088640);
            try {
                boolean zWriteStringToFd = writeStringToFd(parcelFileDescriptorOpen, str2);
                if (parcelFileDescriptorOpen != null) {
                    parcelFileDescriptorOpen.close();
                }
                return zWriteStringToFd;
            } catch (Throwable th) {
                if (parcelFileDescriptorOpen != null) {
                    try {
                        parcelFileDescriptorOpen.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (Exception e) {
            Log.e("FileUtil", "writeStringToFile: " + e);
            return false;
        }
    }

    public static void zipFolder(File file, File file2) {
        FileOutputStream fileOutputStream;
        Log.w("FileUtil", "zipFolder begin");
        ZipOutputStream zipOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file2);
            try {
                try {
                    ZipOutputStream zipOutputStream2 = new ZipOutputStream(fileOutputStream);
                    try {
                        addFolderToZip("", file, zipOutputStream2);
                        zipOutputStream2.flush();
                        close(zipOutputStream2, fileOutputStream);
                    } catch (Exception e) {
                        e = e;
                        zipOutputStream = zipOutputStream2;
                        e.printStackTrace();
                        close(zipOutputStream, fileOutputStream);
                    } catch (Throwable th) {
                        th = th;
                        zipOutputStream = zipOutputStream2;
                        close(zipOutputStream, fileOutputStream);
                        throw th;
                    }
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e3) {
            e = e3;
            fileOutputStream = null;
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
        }
        Log.w("FileUtil", "zipFolder: end");
    }
}
