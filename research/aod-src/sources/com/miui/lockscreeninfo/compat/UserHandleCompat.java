package com.miui.lockscreeninfo.compat;

import android.os.UserHandle;
import com.miui.lockscreeninfo.ReflectUtils;

/* JADX INFO: loaded from: classes2.dex */
public class UserHandleCompat {
    public static UserHandle newInstance(int i) {
        return (UserHandle) ReflectUtils.newInstance("android.os.UserHandle", new Class[]{Integer.TYPE}, Integer.valueOf(i));
    }
}
