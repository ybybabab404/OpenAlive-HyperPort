package com.miui.keyguard.editor.utils.task;

import android.os.Looper;
import com.miui.keyguard.editor.utils.LooperExecutor;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes2.dex */
public class Task {
    public Executor executor = getPool();
    public Executor mainExecutor = new LooperExecutor(Looper.getMainLooper());

    public static SingleTask create(Supplier supplier) {
        return new SingleTask(supplier);
    }

    public static ScheduledExecutorService getPool() {
        return TaskManager.getInstance().getPool();
    }

    public static void run(Runnable runnable) {
        TaskManager.getInstance().execute(runnable);
    }

    public static Future submit(Callable callable) {
        return TaskManager.getInstance().submit(callable);
    }
}
