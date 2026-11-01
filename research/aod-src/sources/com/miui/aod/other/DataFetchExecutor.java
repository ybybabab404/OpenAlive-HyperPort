package com.miui.aod.other;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public class DataFetchExecutor {
    public static volatile DataFetchExecutor sInstance;
    public Executor mExecutor;

    public static DataFetchExecutor getInstance() {
        if (sInstance == null) {
            synchronized (DataFetchExecutor.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new DataFetchExecutor();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return sInstance;
    }

    public void executeTask(Runnable runnable) {
        if (this.mExecutor == null) {
            this.mExecutor = Executors.newCachedThreadPool();
        }
        this.mExecutor.execute(runnable);
    }
}
