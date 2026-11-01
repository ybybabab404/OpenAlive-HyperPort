package androidx.core.graphics;

import android.graphics.Path;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes.dex */
public final class PathUtils {

    @RequiresApi
    public static class Api26Impl {
        @DoNotInline
        public static float[] approximate(Path path, float f) {
            return path.approximate(f);
        }
    }
}
