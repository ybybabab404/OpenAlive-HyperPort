package miuix.animation.physics;

import android.view.View;
import androidx.annotation.NonNull;

/* JADX INFO: loaded from: classes2.dex */
public class AnimationHelper {
    public static void postInvalidateOnAnimation(View view) {
        AnimationHandler.getInstance().postVsyncCallback();
        view.postInvalidateOnAnimation();
    }

    public static void postOnAnimation(@NonNull View view, @NonNull Runnable runnable) {
        AnimationHandler.getInstance().postVsyncCallback();
        view.postOnAnimation(runnable);
    }
}
