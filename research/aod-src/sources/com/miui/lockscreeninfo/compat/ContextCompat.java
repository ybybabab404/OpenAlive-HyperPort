package com.miui.lockscreeninfo.compat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.UserHandle;
import com.miui.lockscreeninfo.ReflectUtils;

/* JADX INFO: loaded from: classes2.dex */
public class ContextCompat {
    public static Intent registerReceiverAsUser(Context context, BroadcastReceiver broadcastReceiver, UserHandle userHandle, IntentFilter intentFilter, String str, Handler handler) {
        return (Intent) ReflectUtils.invokeObject(Context.class, context, "registerReceiverAsUser", Intent.class, new Class[]{BroadcastReceiver.class, UserHandle.class, IntentFilter.class, String.class, Handler.class}, broadcastReceiver, userHandle, intentFilter, str, handler);
    }
}
