package androidx.core.os;

import android.os.Trace;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class TraceCompat {

    @RequiresApi
    public static class Api29Impl {
        @DoNotInline
        public static void beginAsyncSection(String str, int i) {
            Trace.beginAsyncSection(str, i);
        }

        @DoNotInline
        public static void endAsyncSection(String str, int i) {
            Trace.endAsyncSection(str, i);
        }

        @DoNotInline
        public static boolean isEnabled() {
            return Trace.isEnabled();
        }

        @DoNotInline
        public static void setCounter(String str, long j) {
            Trace.setCounter(str, j);
        }
    }

    public static void beginSection(String str) {
        Trace.beginSection(str);
    }

    public static void endSection() {
        Trace.endSection();
    }
}
