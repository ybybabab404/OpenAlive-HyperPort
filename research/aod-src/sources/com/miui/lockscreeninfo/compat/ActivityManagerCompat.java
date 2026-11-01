package com.miui.lockscreeninfo.compat;

import android.app.ActivityManager;
import com.miui.lockscreeninfo.ReflectUtils;

/* JADX INFO: loaded from: classes2.dex */
public class ActivityManagerCompat {
    public static int getCurrentUser() {
        return ((Integer) ReflectUtils.invokeObject(ActivityManager.class, null, "getCurrentUser", Integer.TYPE, 0, null, new Object[0])).intValue();
    }
}
