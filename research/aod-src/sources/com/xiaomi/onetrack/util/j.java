package com.xiaomi.onetrack.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes2.dex */
public class j {
    private static final String a = "ExecutorUtil";
    private static ThreadPoolExecutor b;
    private static int c = Runtime.getRuntime().availableProcessors() + 1;

    public static class a implements ThreadFactory {
        private final AtomicInteger a;

        private a() {
            this.a = new AtomicInteger(1);
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "onetrack_executor_thread_" + this.a.getAndIncrement());
        }
    }

    static {
        int i = c;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(i, i, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new a());
        b = threadPoolExecutor;
        threadPoolExecutor.allowCoreThreadTimeOut(true);
    }

    public static void a(Runnable runnable) {
        try {
            b.execute(runnable);
        } catch (Throwable th) {
            s.b(a, "execute error", th);
        }
    }
}
