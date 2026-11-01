package androidx.core.os;

import android.os.Environment;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public final class EnvironmentCompat {

    @RequiresApi
    public static class Api21Impl {
        @DoNotInline
        public static String getExternalStorageState(File file) {
            return Environment.getExternalStorageState(file);
        }
    }
}
