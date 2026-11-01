package com.miui.aod.util;

import android.content.Context;
import com.tencent.mmkv.MMKV;

/* JADX INFO: loaded from: classes.dex */
public final class MMKVUtils {
    public static volatile boolean initialized = false;

    public static synchronized void initialize(Context context) {
        if (initialized) {
            return;
        }
        MMKV.initialize(context);
        initialized = true;
    }
}
