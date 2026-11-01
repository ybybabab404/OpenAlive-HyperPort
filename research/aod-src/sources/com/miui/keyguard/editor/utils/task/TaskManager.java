package com.miui.keyguard.editor.utils.task;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes2.dex */
class TaskManager {
    public static final int CORE_POOL_SIZE;
    public static final int CPU_COUNT;
    public static AtomicInteger idAtomicInteger;
    public static volatile TaskManager sManager;
    public final ScheduledExecutorService pool = Executors.newScheduledThreadPool(CORE_POOL_SIZE, new ThreadFactory() { // from class: com.miui.keyguard.editor.utils.task.TaskManager.1
        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "KeyguardEditor #" + TaskManager.idAtomicInteger.getAndIncrement());
        }
    });

    static {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        CPU_COUNT = iAvailableProcessors;
        CORE_POOL_SIZE = (iAvailableProcessors * 2) + 1;
        idAtomicInteger = new AtomicInteger(1);
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

    public void execute(Runnable runnable) {
        this.pool.execute(runnable);
    }

    public ScheduledExecutorService getPool() {
        return this.pool;
    }

    public Future submit(Callable callable) {
        return this.pool.submit(callable);
    }
}
