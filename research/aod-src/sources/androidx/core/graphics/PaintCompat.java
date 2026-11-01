package androidx.core.graphics;

import android.graphics.BlendMode;
import android.graphics.Paint;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes.dex */
public final class PaintCompat {
    public static final ThreadLocal sRectThreadLocal = new ThreadLocal();

    @RequiresApi
    public static class Api23Impl {
        @DoNotInline
        public static boolean hasGlyph(Paint paint, String str) {
            return paint.hasGlyph(str);
        }
    }

    @RequiresApi
    public static class Api29Impl {
        @DoNotInline
        public static void setBlendMode(Paint paint, Object obj) {
            paint.setBlendMode((BlendMode) obj);
        }
    }

    public static boolean hasGlyph(Paint paint, String str) {
        return Api23Impl.hasGlyph(paint, str);
    }
}
