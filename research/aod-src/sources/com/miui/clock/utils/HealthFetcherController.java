package com.miui.clock.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.miui.clock.module.HealthBean;
import com.miui.clock.task.Task;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

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
/* JADX INFO: compiled from: HealthFetcherController.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class HealthFetcherController {
    public CompletableFuture mCurrentTask;
    public double mLatestRequestId;
    public final ScheduledExecutorService mExecutorService = Task.getPool();
    public final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
    public long timeout = 10000;
    public final String TAG = "HealthFetcherController";

    /* JADX INFO: compiled from: HealthFetcherController.kt */
    @Metadata
    public interface Callback {
        void updateHealth(HealthBean healthBean);
    }

    /* JADX INFO: renamed from: com.miui.clock.utils.HealthFetcherController$fetchHealthData$2, reason: invalid class name */
    /* JADX INFO: compiled from: HealthFetcherController.kt */
    @Metadata
    public static final class AnonymousClass2 extends Lambda implements Function2<HealthBean, Throwable, Unit> {
        public final /* synthetic */ Callback $callback;
        public final /* synthetic */ double $requestId;
        public final /* synthetic */ ScheduledFuture $timeoutTask;
        public final /* synthetic */ HealthFetcherController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(ScheduledFuture scheduledFuture, double d, HealthFetcherController healthFetcherController, Callback callback) {
            super(2);
            this.$timeoutTask = scheduledFuture;
            this.$requestId = d;
            this.this$0 = healthFetcherController;
            this.$callback = callback;
        }

        public static final void invoke$lambda$0(Callback callback) {
            Intrinsics.checkNotNullParameter(callback, "$callback");
            callback.updateHealth(new HealthBean());
        }

        public static final void invoke$lambda$1(Callback callback, HealthBean healthBean) {
            Intrinsics.checkNotNullParameter(callback, "$callback");
            callback.updateHealth(healthBean);
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            invoke((HealthBean) obj, (Throwable) obj2);
            return Unit.INSTANCE;
        }

        public final void invoke(final HealthBean healthBean, Throwable th) {
            this.$timeoutTask.cancel(false);
            if (this.$requestId == this.this$0.mLatestRequestId) {
                if (th == null && healthBean != null) {
                    Handler handler = this.this$0.mMainThreadHandler;
                    final Callback callback = this.$callback;
                    handler.post(new Runnable() { // from class: com.miui.clock.utils.HealthFetcherController$fetchHealthData$2$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            HealthFetcherController.AnonymousClass2.invoke$lambda$1(callback, healthBean);
                        }
                    });
                } else {
                    if (th != null) {
                        th.printStackTrace();
                    }
                    Log.w(this.this$0.TAG, "health fetcher is timeout or throwable");
                    Handler handler2 = this.this$0.mMainThreadHandler;
                    final Callback callback2 = this.$callback;
                    handler2.post(new Runnable() { // from class: com.miui.clock.utils.HealthFetcherController$fetchHealthData$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            HealthFetcherController.AnonymousClass2.invoke$lambda$0(callback2);
                        }
                    });
                }
            }
        }
    }

    public static final HealthBean fetchHealthData$lambda$0(HashSet typeList, Context context) {
        Intrinsics.checkNotNullParameter(typeList, "$typeList");
        Intrinsics.checkNotNullParameter(context, "$context");
        try {
            int size = typeList.size();
            int[] iArr = new int[size];
            Iterator it = typeList.iterator();
            int i = 0;
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                Intrinsics.checkNotNull(num);
                iArr[i] = num.intValue();
                i++;
            }
            HealthBean healthBean = null;
            for (int i2 = 0; i2 < size; i2++) {
                healthBean = DataUtils.getHealthBean(new WeakReference(context), iArr[i2], healthBean);
            }
            return healthBean == null ? new HealthBean() : healthBean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final void fetchHealthData$lambda$2(HealthFetcherController this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        CompletableFuture completableFuture = this$0.mCurrentTask;
        if (completableFuture == null || completableFuture.isDone()) {
            return;
        }
        Log.w(this$0.TAG, "health fetcher is timeout");
        completableFuture.complete(null);
    }

    public static final void fetchHealthData$lambda$3(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj, obj2);
    }

    public static final void fetchHealthData$lambda$5$lambda$4(Callback callback) {
        Intrinsics.checkNotNullParameter(callback, "$callback");
        callback.updateHealth(new HealthBean());
    }

    public final void fetchHealthData(final Context context, final HashSet typeList, Callback callback) {
        HealthFetcherController healthFetcherController;
        final Callback callback2;
        CompletableFuture completableFutureWhenComplete;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(typeList, "typeList");
        Intrinsics.checkNotNullParameter(callback, "callback");
        double d = this.mLatestRequestId + 1.0d;
        this.mLatestRequestId = d;
        CompletableFuture completableFuture = this.mCurrentTask;
        if (completableFuture != null) {
            completableFuture.cancel(true);
        }
        this.mCurrentTask = CompletableFuture.supplyAsync(new Supplier() { // from class: com.miui.clock.utils.HealthFetcherController$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return HealthFetcherController.fetchHealthData$lambda$0(typeList, context);
            }
        });
        ScheduledFuture<?> scheduledFutureSchedule = this.mExecutorService.schedule(new Runnable() { // from class: com.miui.clock.utils.HealthFetcherController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                HealthFetcherController.fetchHealthData$lambda$2(this.f$0);
            }
        }, this.timeout, TimeUnit.MILLISECONDS);
        CompletableFuture completableFuture2 = this.mCurrentTask;
        if (completableFuture2 != null) {
            healthFetcherController = this;
            callback2 = callback;
            final AnonymousClass2 anonymousClass2 = new AnonymousClass2(scheduledFutureSchedule, d, healthFetcherController, callback2);
            completableFutureWhenComplete = completableFuture2.whenComplete(new BiConsumer() { // from class: com.miui.clock.utils.HealthFetcherController$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    HealthFetcherController.fetchHealthData$lambda$3(anonymousClass2, obj, obj2);
                }
            });
        } else {
            healthFetcherController = this;
            callback2 = callback;
            completableFutureWhenComplete = null;
        }
        if (completableFutureWhenComplete == null) {
            Log.w(healthFetcherController.TAG, "health fetcher mCurrentTask is null");
            healthFetcherController.mMainThreadHandler.post(new Runnable() { // from class: com.miui.clock.utils.HealthFetcherController$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    HealthFetcherController.fetchHealthData$lambda$5$lambda$4(callback2);
                }
            });
        }
    }

    public final void reset() {
        CompletableFuture completableFuture = this.mCurrentTask;
        if (completableFuture != null) {
            completableFuture.cancel(true);
        }
        this.mCurrentTask = null;
    }
}
