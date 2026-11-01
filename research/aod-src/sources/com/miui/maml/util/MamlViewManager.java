package com.miui.maml.util;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import androidx.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public interface MamlViewManager extends ViewManager {
    void addView(View view, int i, ViewGroup.LayoutParams layoutParams);

    @Nullable
    default Context getViewContext() {
        return null;
    }

    @Nullable
    default Rect getViewLocationOnScreen() {
        return null;
    }
}
