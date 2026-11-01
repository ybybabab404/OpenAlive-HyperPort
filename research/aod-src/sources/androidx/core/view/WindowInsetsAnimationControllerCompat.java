package androidx.core.view;

import android.view.WindowInsetsAnimationController;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes.dex */
public final class WindowInsetsAnimationControllerCompat {
    public final Impl mImpl;

    public static class Impl {
    }

    @RequiresApi
    public static class Impl30 extends Impl {
        public final WindowInsetsAnimationController mController;

        public Impl30(WindowInsetsAnimationController windowInsetsAnimationController) {
            this.mController = windowInsetsAnimationController;
        }
    }

    public WindowInsetsAnimationControllerCompat(WindowInsetsAnimationController windowInsetsAnimationController) {
        this.mImpl = new Impl30(windowInsetsAnimationController);
    }
}
