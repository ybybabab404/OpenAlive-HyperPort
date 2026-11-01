package com.xiaomi.micloudsdk.sync;

import android.util.ArrayMap;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class SyncCommandConstant {
    public static final Map SUPPORT_COMMAND_PKGS;

    static {
        ArrayMap arrayMap = new ArrayMap();
        SUPPORT_COMMAND_PKGS = arrayMap;
        arrayMap.put("com.miui.gallery.cloud.provider", "com.miui.gallery");
    }
}
