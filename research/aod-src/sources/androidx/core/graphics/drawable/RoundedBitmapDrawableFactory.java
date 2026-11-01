package androidx.core.graphics.drawable;

import android.graphics.Rect;
import androidx.core.view.GravityCompat;

/* JADX INFO: loaded from: classes.dex */
public final class RoundedBitmapDrawableFactory {

    public static class DefaultRoundedBitmapDrawable extends RoundedBitmapDrawable {
        @Override // androidx.core.graphics.drawable.RoundedBitmapDrawable
        public void gravityCompatApply(int i, int i2, int i3, Rect rect, Rect rect2) {
            GravityCompat.apply(i, i2, i3, rect, rect2, 0);
        }
    }
}
