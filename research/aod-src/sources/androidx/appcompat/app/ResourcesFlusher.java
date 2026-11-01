package androidx.appcompat.app;

import android.util.LongSparseArray;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes.dex */
class ResourcesFlusher {

    @RequiresApi
    public static class Api16Impl {
        @DoNotInline
        public static void clear(LongSparseArray longSparseArray) {
            longSparseArray.clear();
        }
    }
}
