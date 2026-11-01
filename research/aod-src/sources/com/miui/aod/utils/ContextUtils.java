package com.miui.aod.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.UserHandle;

/* JADX INFO: loaded from: classes.dex */
public final class ContextUtils {
    public static final int RECEIVER_EXPORTED = 2;

    private ContextUtils() {
    }

    public static Context createContextAsUser(Context context, int i, int i2) {
        return context.createContextAsUser(UserHandle.of(i), i2);
    }

    public static Intent registerReceiverAsUser(Context context, BroadcastReceiver broadcastReceiver, UserHandle userHandle, IntentFilter intentFilter, String str, Handler handler, int i) {
        return context.registerReceiverAsUser(broadcastReceiver, userHandle, intentFilter, str, handler, i);
    }

    public static Intent registerReceiverForAllUsers(Context context, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String str, Handler handler) {
        return context.registerReceiverForAllUsers(broadcastReceiver, intentFilter, str, handler, 2);
    }
}
