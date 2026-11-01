package com.miui.aod.util;

import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
public class Assert {
    public static void isMainThread() {
        if (!Looper.getMainLooper().isCurrentThread()) {
            throw new IllegalStateException("should be called from the main thread.");
        }
    }
}
