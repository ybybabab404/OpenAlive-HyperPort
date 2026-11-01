package androidx.transition;

import android.graphics.Matrix;
import android.widget.ImageView;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes.dex */
class ImageViewUtils {

    @RequiresApi
    public static class Api29Impl {
        @DoNotInline
        public static void animateTransform(ImageView imageView, Matrix matrix) {
            imageView.animateTransform(matrix);
        }
    }

    public static void animateTransform(ImageView imageView, Matrix matrix) {
        Api29Impl.animateTransform(imageView, matrix);
    }
}
