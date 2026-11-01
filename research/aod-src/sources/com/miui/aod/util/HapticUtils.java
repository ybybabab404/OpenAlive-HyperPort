package com.miui.aod.util;

import android.view.View;
import miuix.view.HapticCompat;
import miuix.view.HapticFeedbackConstants;

/* JADX INFO: loaded from: classes.dex */
public final class HapticUtils {
    public static void setClickHapticFeedback(View view) {
        HapticCompat.performHapticFeedback(view, HapticFeedbackConstants.MIUI_MESH_NORMAL);
    }
}
