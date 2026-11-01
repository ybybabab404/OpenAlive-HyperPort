package com.miui.aod.util;

import android.view.View;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;

/* JADX INFO: loaded from: classes.dex */
public final class MiAnimUtils {
    public static void applyPressedTintAndScaleStyle(View view) {
        Folme.useAt(view).touch().handleTouchOf(view, new AnimConfig[0]);
    }

    public static void applyScaleStyle(View view) {
        Folme.useAt(view).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f).handleTouchOf(view, new AnimConfig[0]);
    }
}
