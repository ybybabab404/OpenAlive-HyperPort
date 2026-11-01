package androidx.core.app;

import android.app.Dialog;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes.dex */
public class DialogCompat {

    @RequiresApi
    public static class Api28Impl {
        @DoNotInline
        public static <T> T requireViewById(Dialog dialog, int i) {
            return (T) dialog.requireViewById(i);
        }
    }
}
