package com.miui.clock.tiny.utils;

import android.os.HandlerThread;
import android.os.Looper;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: TaskManager.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class TaskManager {
    public static final int CORE_POOL_SIZE;
    public static final int CPU_COUNT;
    public static final Companion Companion = new Companion(null);
    public static HandlerThread handlerThread;
    public static final AtomicInteger idAtomicInteger;
    public static volatile TaskManager sManager;
    public final ScheduledExecutorService pool;

    /* JADX INFO: compiled from: TaskManager.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final TaskManager getInstance() {
            if (TaskManager.sManager == null) {
                synchronized (TaskManager.class) {
                    try {
                        if (TaskManager.sManager == null) {
                            TaskManager.sManager = new TaskManager(null);
                        }
                        Unit unit = Unit.INSTANCE;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            TaskManager taskManager = TaskManager.sManager;
            Intrinsics.checkNotNull(taskManager);
            return taskManager;
        }
    }

    static {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        CPU_COUNT = iAvailableProcessors;
        CORE_POOL_SIZE = (iAvailableProcessors * 2) + 1;
        idAtomicInteger = new AtomicInteger(1);
    }

    public TaskManager() {
        ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool = java.util.concurrent.Executors.newScheduledThreadPool(CORE_POOL_SIZE, new ThreadFactory() { // from class: com.miui.clock.tiny.utils.TaskManager$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return TaskManager.pool$lambda$0(runnable);
            }
        });
        Intrinsics.checkNotNullExpressionValue(scheduledExecutorServiceNewScheduledThreadPool, "newScheduledThreadPool(...)");
        this.pool = scheduledExecutorServiceNewScheduledThreadPool;
    }

    public /* synthetic */ TaskManager(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public static final Thread pool$lambda$0(Runnable runnable) {
        return new Thread(runnable, "TMiuiClock #" + idAtomicInteger.getAndIncrement());
    }

    public final void execute(Runnable runnable) {
        this.pool.execute(runnable);
    }

    public final Looper getBgHandlerLooper() {
        if (handlerThread == null) {
            HandlerThread handlerThread2 = new HandlerThread("TMiuiClockBg");
            handlerThread = handlerThread2;
            Intrinsics.checkNotNull(handlerThread2);
            handlerThread2.start();
        }
        HandlerThread handlerThread3 = handlerThread;
        Intrinsics.checkNotNull(handlerThread3);
        Looper looper = handlerThread3.getLooper();
        Intrinsics.checkNotNullExpressionValue(looper, "getLooper(...)");
        return looper;
    }
}
