package androidx.core.graphics;

import android.graphics.Typeface;
import android.util.Log;
import androidx.annotation.RestrictTo;
import androidx.collection.LongSparseArray;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
final class WeightTypefaceApi14 {
    public static final Field sNativeInstance;
    public static final Object sWeightCacheLock;
    public static final LongSparseArray sWeightTypefaceCache;

    static {
        Field declaredField;
        try {
            declaredField = Typeface.class.getDeclaredField("native_instance");
            declaredField.setAccessible(true);
        } catch (Exception e) {
            Log.e("WeightTypeface", e.getClass().getName(), e);
            declaredField = null;
        }
        sNativeInstance = declaredField;
        sWeightTypefaceCache = new LongSparseArray(3);
        sWeightCacheLock = new Object();
    }
}
