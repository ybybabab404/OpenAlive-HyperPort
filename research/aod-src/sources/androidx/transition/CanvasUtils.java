package androidx.transition;

import android.graphics.Canvas;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes.dex */
class CanvasUtils {

    @RequiresApi
    public static class Api29Impl {
        @DoNotInline
        public static void disableZ(Canvas canvas) {
            canvas.disableZ();
        }

        @DoNotInline
        public static void enableZ(Canvas canvas) {
            canvas.enableZ();
        }
    }

    public static void enableZ(Canvas canvas, boolean z) {
        if (z) {
            Api29Impl.enableZ(canvas);
        } else {
            Api29Impl.disableZ(canvas);
        }
    }
}
