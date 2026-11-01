package androidx.core.view;

import android.view.View;
import android.view.Window;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes.dex */
public final class WindowCompat {

    public static class Api16Impl {
    }

    @RequiresApi
    public static class Api28Impl {
        @DoNotInline
        public static <T> T requireViewById(Window window, int i) {
            return (T) window.requireViewById(i);
        }
    }

    @RequiresApi
    public static class Api30Impl {
        @DoNotInline
        public static void setDecorFitsSystemWindows(@NonNull Window window, boolean z) {
            window.setDecorFitsSystemWindows(z);
        }
    }

    public static WindowInsetsControllerCompat getInsetsController(Window window, View view) {
        return new WindowInsetsControllerCompat(window, view);
    }

    public static void setDecorFitsSystemWindows(Window window, boolean z) {
        Api30Impl.setDecorFitsSystemWindows(window, z);
    }
}
