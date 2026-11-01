package com.miui.clock.task;

import android.os.Handler;
import java.util.concurrent.ScheduledExecutorService;

/* JADX INFO: loaded from: classes.dex */
public class Task {
    public static Handler mBgHandler;

    public static ScheduledExecutorService getPool() {
        return TaskManager.getInstance().getPool();
    }

    public static void postBgTask(Runnable runnable) {
        if (mBgHandler == null) {
            mBgHandler = new Handler(TaskManager.getInstance().getBgHandlerLooper());
        }
        mBgHandler.post(runnable);
    }

    public static void removeBgTask(Runnable runnable) {
        if (mBgHandler == null) {
            mBgHandler = new Handler(TaskManager.getInstance().getBgHandlerLooper());
        }
        mBgHandler.removeCallbacks(runnable);
    }
}
