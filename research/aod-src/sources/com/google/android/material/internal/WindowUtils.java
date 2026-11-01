package com.google.android.material.internal;

import android.content.Context;
import android.graphics.Rect;
import android.view.WindowManager;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public class WindowUtils {

    public static class Api14Impl {
    }

    @RequiresApi
    public static class Api17Impl {
    }

    @RequiresApi
    public static class Api30Impl {
        public static Rect getCurrentWindowBounds(WindowManager windowManager) {
            return windowManager.getCurrentWindowMetrics().getBounds();
        }
    }

    public static Rect getCurrentWindowBounds(Context context) {
        return Api30Impl.getCurrentWindowBounds((WindowManager) context.getSystemService("window"));
    }
}
