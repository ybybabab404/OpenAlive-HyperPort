package com.miui.aod.common;

import android.os.Handler;
import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
public class MainThread {
    private static Handler sHandler;

    public static void execute(Runnable runnable) {
        if (getHandler().getLooper() == Looper.myLooper()) {
            runnable.run();
        } else {
            getHandler().post(runnable);
        }
    }

    public static Handler getHandler() {
        Handler handler;
        synchronized (BackgroundThread.class) {
            try {
                if (sHandler == null) {
                    sHandler = new Handler(Looper.getMainLooper());
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

    public static void postAtFrontOfQueue(Runnable runnable) {
        getHandler().postAtFrontOfQueue(runnable);
    }

    public static void postDelayed(Runnable runnable, long j) {
        getHandler().postDelayed(runnable, j);
    }

    public static void removeCallbacks(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }
}
