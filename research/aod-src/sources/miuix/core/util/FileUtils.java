package miuix.core.util;

import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes2.dex */
public class FileUtils {
    public static final Pattern SAFE_FILENAME_PATTERN = Pattern.compile("[\\w%+,./=_-]+");

    public static boolean copyFile(File file, File file2) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                return copyToFile(fileInputStream, file2);
            } finally {
                fileInputStream.close();
            }
        } catch (IOException unused) {
            return false;
        }
    }

    public static boolean copyToFile(InputStream inputStream, File file) {
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
                        break;
                    }
                    fileOutputStream.write(bArr, 0, i);
                }
                fileOutputStream.flush();
                try {
                    fileOutputStream.getFD().sync();
                } catch (IOException unused) {
                }
                try {
                    fileOutputStream.close();
                    return true;
                } catch (IOException unused2) {
                    return true;
                }
            } catch (Throwable th) {
                fileOutputStream.flush();
                try {
                    fileOutputStream.getFD().sync();
                } catch (IOException unused3) {
                }
                try {
                    fileOutputStream.close();
                    throw th;
                } catch (IOException unused4) {
                    throw th;
                }
            }
        } catch (IOException unused5) {
            return false;
        }
    }

    public static void stringToFile(String str, String str2) throws IOException {
        FileWriter fileWriter = new FileWriter(str);
        try {
            fileWriter.write(str2);
        } finally {
            fileWriter.close();
        }
    }
}
