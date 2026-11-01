package com.miui.clock.utils;

import android.graphics.drawable.Drawable;
import androidx.core.graphics.drawable.DrawableCompat;

/* JADX INFO: loaded from: classes.dex */
public class SvgUtils {
    public static Drawable initSvgColor(Drawable drawable, int i) {
        return tintDrawable(drawable, i);
    }

    public static Drawable tintDrawable(Drawable drawable, int i) {
        Drawable drawableMutate = DrawableCompat.wrap(drawable).mutate();
        DrawableCompat.setTint(drawableMutate, i);
        return drawableMutate;
    }
}
