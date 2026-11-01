package com.miui.aod.attention;

import android.os.Handler;
import android.os.HandlerThread;

/* JADX INFO: loaded from: classes.dex */
public final class AttentionThread {
    public static Handler sHandler;

    public static Handler getHandler() {
        Handler handler;
        synchronized (AttentionThread.class) {
            try {
                if (sHandler == null) {
                    HandlerThread handlerThread = new HandlerThread("AttentionBinder", 0);
                    handlerThread.start();
                    sHandler = new Handler(handlerThread.getLooper());
                }
                handler = sHandler;
            } catch (Throwable th) {
                throw th;
            }
        }
        return handler;
    }

    public static void post(Runnable runnable) {
        getHandler().post(runnable);
    }
}
