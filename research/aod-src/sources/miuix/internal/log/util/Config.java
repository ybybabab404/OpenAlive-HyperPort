package miuix.internal.log.util;

import android.content.Context;
import android.os.Process;
import android.util.Log;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.Locale;

/* JADX INFO: loaded from: classes2.dex */
public class Config {
    public static String LOG_NAME = getProcessNameByPid(Process.myPid());
    public static volatile boolean useAospLikeLogFormat = false;

    public static String getApplicationCacheLogDir(Context context) {
        File cacheDir = context.getCacheDir();
        if (cacheDir == null) {
            Log.e("Config", "Fail to getCacheDir");
            return null;
        }
        return cacheDir.getPath() + "/debug_log/";
    }

    public static String getDefaultCacheLogDir(Context context) {
        return getApplicationCacheLogDir(context);
    }

    public static String getProcessNameByPid(int i) {
        String str = String.format(Locale.US, "/proc/%d/cmdline", Integer.valueOf(i));
        try {
            String fileAsString = readFileAsString(str);
            if (fileAsString == null) {
                return null;
            }
            int iIndexOf = fileAsString.indexOf(0);
            return iIndexOf >= 0 ? fileAsString.substring(0, iIndexOf) : fileAsString;
        } catch (IOException e) {
            Log.e("Config", "Fail to read cmdline: " + str, e);
            return null;
        }
    }

    public static boolean isUseAospLikeLogFormat() {
        return useAospLikeLogFormat;
    }

    public static byte[] readFileAsBytes(String str) throws Throwable {
        RandomAccessFile randomAccessFile = null;
        try {
            RandomAccessFile randomAccessFile2 = new RandomAccessFile(str, "r");
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((int) randomAccessFile2.length());
                byte[] bArr = new byte[AutoBottomSheetKt.ALIGN_SCREEN_START];
                while (true) {
                    int i = randomAccessFile2.read(bArr);
                    if (i == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, i);
                }
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                try {
                    randomAccessFile2.close();
                } catch (IOException unused) {
                }
                return byteArray;
            } catch (Throwable th) {
                th = th;
                randomAccessFile = randomAccessFile2;
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static String readFileAsString(String str) {
        return new String(readFileAsBytes(str), Charset.forName("UTF-8"));
    }
}
