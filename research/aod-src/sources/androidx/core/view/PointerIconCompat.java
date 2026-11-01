package androidx.core.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.PointerIcon;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes.dex */
public final class PointerIconCompat {
    public final PointerIcon mPointerIcon;

    @RequiresApi
    public static class Api24Impl {
        @DoNotInline
        public static PointerIcon create(Bitmap bitmap, float f, float f2) {
            return PointerIcon.create(bitmap, f, f2);
        }

        @DoNotInline
        public static PointerIcon getSystemIcon(Context context, int i) {
            return PointerIcon.getSystemIcon(context, i);
        }

        @DoNotInline
        public static PointerIcon load(Resources resources, int i) {
            return PointerIcon.load(resources, i);
        }
    }

    public PointerIconCompat(PointerIcon pointerIcon) {
        this.mPointerIcon = pointerIcon;
    }

    public static PointerIconCompat getSystemIcon(Context context, int i) {
        return new PointerIconCompat(Api24Impl.getSystemIcon(context, i));
    }

    public Object getPointerIcon() {
        return this.mPointerIcon;
    }
}
