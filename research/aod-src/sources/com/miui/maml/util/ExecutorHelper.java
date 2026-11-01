package com.miui.maml.util;

import android.util.Log;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes2.dex */
public class ExecutorHelper {
    private static final Object MUTEX_LOCK = new Object();
    private static volatile ThreadPoolExecutor sBigOnlineTaskExecutor;
    private static volatile ThreadPoolExecutor sLocalExecutor;

    public static /* synthetic */ Thread $r8$lambda$hLBrXcNoiTTxSYd_awg2SO5zbOw(Runnable runnable) {
        return new Thread(runnable, "MAML-LocalTask");
    }

    public static /* synthetic */ Thread $r8$lambda$v3F4w18yQp8SU4tM0e5n7n4EYlg(Runnable runnable) {
        return new Thread(runnable, "MAML-OnlineTask");
    }

    public static /* synthetic */ void $r8$lambda$y1XjLLS5zrbfZP4p62Xblf7aSVE(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
        Log.w("ExecutorHelper", "Exceeded Local ThreadPoolExecutor pool size. useOnline");
        getLongOnlineTaskExecutor().execute(runnable);
    }

    private ExecutorHelper() {
    }

    public static ThreadPoolExecutor getLocalTaskExecutor() {
        if (sLocalExecutor == null) {
            synchronized (MUTEX_LOCK) {
                try {
                    if (sLocalExecutor == null) {
                        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 10, 60L, TimeUnit.SECONDS, new SynchronousQueue(), new ThreadFactory() { // from class: com.miui.maml.util.ExecutorHelper$$ExternalSyntheticLambda1
                            @Override // java.util.concurrent.ThreadFactory
                            public final Thread newThread(Runnable runnable) {
                                return ExecutorHelper.$r8$lambda$hLBrXcNoiTTxSYd_awg2SO5zbOw(runnable);
                            }
                        });
                        threadPoolExecutor.allowCoreThreadTimeOut(true);
                        threadPoolExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() { // from class: com.miui.maml.util.ExecutorHelper$$ExternalSyntheticLambda2
                            @Override // java.util.concurrent.RejectedExecutionHandler
                            public final void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor2) {
                                ExecutorHelper.$r8$lambda$y1XjLLS5zrbfZP4p62Xblf7aSVE(runnable, threadPoolExecutor2);
                            }
                        });
                        sLocalExecutor = threadPoolExecutor;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return sLocalExecutor;
    }

    public static ThreadPoolExecutor getLongOnlineTaskExecutor() {
        if (sBigOnlineTaskExecutor == null) {
            synchronized (MUTEX_LOCK) {
                try {
                    if (sBigOnlineTaskExecutor == null) {
                        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new ThreadFactory() { // from class: com.miui.maml.util.ExecutorHelper$$ExternalSyntheticLambda0
                            @Override // java.util.concurrent.ThreadFactory
                            public final Thread newThread(Runnable runnable) {
                                return ExecutorHelper.$r8$lambda$v3F4w18yQp8SU4tM0e5n7n4EYlg(runnable);
                            }
                        });
                        threadPoolExecutor.allowCoreThreadTimeOut(true);
                        sBigOnlineTaskExecutor = threadPoolExecutor;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return sBigOnlineTaskExecutor;
    }
}
