package com.miui.clock.tiny.utils;

import android.os.HandlerThread;
import android.os.Looper;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class Executors {
    public static final LooperExecutor KEYGUARD_LOOPER_EXECUTOR;
    public static final LooperExecutor MAIN_EXECUTOR;
    public static final Map PACKAGE_EXECUTORS;
    public static final int POOL_SIZE;
    public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;

    static {
        int iMax = Math.max(Runtime.getRuntime().availableProcessors(), 2);
        POOL_SIZE = iMax;
        PACKAGE_EXECUTORS = new ConcurrentHashMap();
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(iMax, iMax, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        MAIN_EXECUTOR = new LooperExecutor(Looper.getMainLooper());
        KEYGUARD_LOOPER_EXECUTOR = new LooperExecutor(createAndStartNewLooper("flipKeyguard"));
    }

    public static Looper createAndStartNewLooper(String str) {
        return createAndStartNewLooper(str, 0);
    }

    public static Looper createAndStartNewLooper(String str, int i) {
        HandlerThread handlerThread = new HandlerThread(str, i);
        handlerThread.start();
        return handlerThread.getLooper();
    }
}
