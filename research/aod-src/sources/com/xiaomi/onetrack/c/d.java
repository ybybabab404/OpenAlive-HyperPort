package com.xiaomi.onetrack.c;

import android.os.Handler;
import android.os.HandlerThread;

/* JADX INFO: loaded from: classes2.dex */
public class d {
    private static final String a = "DbExecutor";
    private static String b = "onetrack_db";
    private static Handler c;

    private static void a() {
        if (c == null) {
            synchronized (d.class) {
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
            com.xiaomi.onetrack.util.s.b(a, th.getMessage());
        }
    }

    public static void a(Runnable runnable, long j) {
        try {
            a();
            c.postDelayed(runnable, j);
        } catch (Throwable th) {
            com.xiaomi.onetrack.util.s.b(a, th.getMessage());
        }
    }
}
