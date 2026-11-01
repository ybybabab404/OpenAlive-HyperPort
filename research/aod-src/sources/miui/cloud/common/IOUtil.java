package miui.cloud.common;

import java.io.Closeable;
import java.io.IOException;

/* JADX INFO: loaded from: classes2.dex */
public class IOUtil {
    public static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException unused) {
            XLogger.loge("Closing failed. ");
        }
    }
}
