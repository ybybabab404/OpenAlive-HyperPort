package com.xiaomi.onetrack.c;

import android.os.Handler;
import android.os.HandlerThread;

/* JADX INFO: loaded from: classes2.dex */
public class a {
    private static final String a = "AppConfigExecutor";
    private static final String b = "onetrack_app_config";
    private static volatile Handler c;

    private static void a() {
        if (c == null) {
            synchronized (a.class) {
                try {
                    if (c == null) {
                        HandlerThread handlerThread = new HandlerThread(b);
                        handlerThread.start();
                        c = new Handler(handlerThread.getLooper());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public static void a(Runnable runnable) {
        try {
            a();
            c.post(runnable);
        } catch (Throwable th) {
            com.xiaomi.onetrack.util.s.b(a, "execute failed, " + th.getMessage());
        }
    }
}
