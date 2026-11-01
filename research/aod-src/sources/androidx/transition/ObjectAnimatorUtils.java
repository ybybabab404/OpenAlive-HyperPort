package androidx.transition;

import android.animation.ObjectAnimator;
import android.animation.TypeConverter;
import android.graphics.Path;
import android.util.Property;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes.dex */
class ObjectAnimatorUtils {

    @RequiresApi
    public static class Api21Impl {
        @DoNotInline
        public static <T, V> ObjectAnimator ofObject(T t, Property<T, V> property, Path path) {
            return ObjectAnimator.ofObject(t, property, (TypeConverter) null, path);
        }
    }

    public static ObjectAnimator ofPointF(Object obj, Property property, Path path) {
        return Api21Impl.ofObject(obj, property, path);
    }
}
