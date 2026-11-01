package com.miui.clock.task;

import android.os.HandlerThread;
import android.os.Looper;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
class TaskManager {
    public static final int CORE_POOL_SIZE;
    public static final int CPU_COUNT;
    public static HandlerThread handlerThread;
    public static AtomicInteger idAtomicInteger;
    public static volatile TaskManager sManager;
    public final ThreadPoolExecutor asyncPool;
    public final ScheduledExecutorService pool = Executors.newScheduledThreadPool(CORE_POOL_SIZE, new ThreadFactory() { // from class: com.miui.clock.task.TaskManager.1
        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "MiuiClock #" + TaskManager.idAtomicInteger.getAndIncrement());
        }
    });

    public static /* synthetic */ Thread $r8$lambda$CoYKr0aQ8L1xHDdOOyu1ghu_nkc(Runnable runnable) {
        return new Thread(runnable, "ClockAsync#" + idAtomicInteger.getAndIncrement());
    }

    static {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        CPU_COUNT = iAvailableProcessors;
        CORE_POOL_SIZE = (iAvailableProcessors * 2) + 1;
        idAtomicInteger = new AtomicInteger(1);
        handlerThread = null;
    }

    public TaskManager() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new ThreadFactory() { // from class: com.miui.clock.task.TaskManager$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return TaskManager.$r8$lambda$CoYKr0aQ8L1xHDdOOyu1ghu_nkc(runnable);
            }
        });
        this.asyncPool = threadPoolExecutor;
        threadPoolExecutor.allowCoreThreadTimeOut(true);
    }

    public static TaskManager getInstance() {
        if (sManager == null) {
            synchronized (TaskManager.class) {
                try {
                    if (sManager == null) {
                        sManager = new TaskManager();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return sManager;
    }

    public Looper getBgHandlerLooper() {
        if (handlerThread == null) {
            HandlerThread handlerThread2 = new HandlerThread("MiuiClockBg");
            handlerThread = handlerThread2;
            handlerThread2.start();
        }
        return handlerThread.getLooper();
    }

    public ScheduledExecutorService getPool() {
        return this.pool;
    }
}
