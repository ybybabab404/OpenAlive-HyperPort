package androidx.core.view;

import android.view.Display;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes.dex */
public final class DisplayCompat {

    @RequiresApi
    public static class Api23Impl {
    }

    public static final class ModeCompat {

        @RequiresApi
        public static class Api23Impl {
            @DoNotInline
            public static int getPhysicalHeight(Display.Mode mode) {
                return mode.getPhysicalHeight();
            }

            @DoNotInline
            public static int getPhysicalWidth(Display.Mode mode) {
                return mode.getPhysicalWidth();
            }
        }
    }
}
