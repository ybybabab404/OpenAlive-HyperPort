package androidx.vectordrawable.graphics.drawable;

import android.content.Context;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public class AnimationUtilsCompat {
    public static Interpolator loadInterpolator(Context context, int i) {
        return AnimationUtils.loadInterpolator(context, i);
    }
}
