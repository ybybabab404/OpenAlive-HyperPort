package com.miui.clock.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.miui.clock.module.WeatherBean;
import com.miui.clock.task.Task;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
/* JADX INFO: compiled from: WeatherFetcherController.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class WeatherFetcherController {
    public static final Companion Companion = new Companion(null);
    public CompletableFuture mCurrentTask;
    public double mLatestRequestId;
    public final ScheduledExecutorService mExecutorService = Task.getPool();
    public final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
    public long timeout = 10000;
    public final String TAG = "WeatherFetcherControlle";

    /* JADX INFO: compiled from: WeatherFetcherController.kt */
    @Metadata
    public interface Callback {
        void updateWeatherInfo(WeatherBean weatherBean);
    }

    /* JADX INFO: compiled from: WeatherFetcherController.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Context getSpecialContext(Context context, int i) {
            Intrinsics.checkNotNullParameter(context, "context");
            if (i == 0) {
                return context;
            }
            String packageName = context.getPackageName();
            if (Intrinsics.areEqual("com.android.systemui", packageName) || Intrinsics.areEqual("com.miui.aod", packageName)) {
                try {
                    Class cls = Integer.TYPE;
                    Method method = Context.class.getMethod("createContextAsUser", UserHandle.class, cls);
                    Constructor constructor = UserHandle.class.getConstructor(cls);
                    Intrinsics.checkNotNullExpressionValue(constructor, "getConstructor(...)");
                    Object objNewInstance = constructor.newInstance(Integer.valueOf(i));
                    Intrinsics.checkNotNullExpressionValue(objNewInstance, "newInstance(...)");
                    Object objInvoke = method.invoke(context, (UserHandle) objNewInstance, 1);
                    Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type android.content.Context");
                    return (Context) objInvoke;
                } catch (Exception e) {
                    Log.e("EasternArtB", "get special context fail", e);
                }
            }
            return context;
        }
    }

    /* JADX INFO: renamed from: com.miui.clock.utils.WeatherFetcherController$fetchWeatherData$2, reason: invalid class name */
    /* JADX INFO: compiled from: WeatherFetcherController.kt */
    @Metadata
    public static final class AnonymousClass2 extends Lambda implements Function2<WeatherBean, Throwable, Unit> {
        public final /* synthetic */ Callback $callback;
        public final /* synthetic */ double $requestId;
        public final /* synthetic */ ScheduledFuture $timeoutTask;
        public final /* synthetic */ WeatherFetcherController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(ScheduledFuture scheduledFuture, double d, WeatherFetcherController weatherFetcherController, Callback callback) {
            super(2);
            this.$timeoutTask = scheduledFuture;
            this.$requestId = d;
            this.this$0 = weatherFetcherController;
            this.$callback = callback;
        }

        public static final void invoke$lambda$0(Callback callback) {
            Intrinsics.checkNotNullParameter(callback, "$callback");
            callback.updateWeatherInfo(null);
        }

        public static final void invoke$lambda$1(Callback callback, WeatherBean weatherBean) {
            Intrinsics.checkNotNullParameter(callback, "$callback");
            callback.updateWeatherInfo(weatherBean);
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            invoke((WeatherBean) obj, (Throwable) obj2);
            return Unit.INSTANCE;
        }

        public final void invoke(final WeatherBean weatherBean, Throwable th) {
            this.$timeoutTask.cancel(false);
            if (this.$requestId == this.this$0.mLatestRequestId) {
                if (th == null && weatherBean != null) {
                    Handler handler = this.this$0.mMainThreadHandler;
                    final Callback callback = this.$callback;
                    handler.post(new Runnable() { // from class: com.miui.clock.utils.WeatherFetcherController$fetchWeatherData$2$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            WeatherFetcherController.AnonymousClass2.invoke$lambda$1(callback, weatherBean);
                        }
                    });
                } else {
                    if (th != null) {
                        th.printStackTrace();
                    }
                    Log.w(this.this$0.TAG, "weather fetcher is timeout or throwable");
                    Handler handler2 = this.this$0.mMainThreadHandler;
                    final Callback callback2 = this.$callback;
                    handler2.post(new Runnable() { // from class: com.miui.clock.utils.WeatherFetcherController$fetchWeatherData$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            WeatherFetcherController.AnonymousClass2.invoke$lambda$0(callback2);
                        }
                    });
                }
            }
        }
    }

    public static final WeatherBean fetchWeatherData$lambda$0(Context context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "$context");
        try {
            return DataUtils.getWeatherBean(new WeakReference(context), z ? ExifInterface.GPS_MEASUREMENT_2D : "1");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final void fetchWeatherData$lambda$2(WeatherFetcherController this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        CompletableFuture completableFuture = this$0.mCurrentTask;
        if (completableFuture == null || completableFuture.isDone()) {
            return;
        }
        Log.w(this$0.TAG, "weather fetcher is timeout");
        completableFuture.complete(null);
    }

    public static final void fetchWeatherData$lambda$3(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj, obj2);
    }

    public static final void fetchWeatherData$lambda$5$lambda$4(Callback callback) {
        Intrinsics.checkNotNullParameter(callback, "$callback");
        callback.updateWeatherInfo(null);
    }

    public final void fetchWeatherData(final Context context, final boolean z, Callback callback) {
        WeatherFetcherController weatherFetcherController;
        final Callback callback2;
        CompletableFuture completableFutureWhenComplete;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(callback, "callback");
        double d = this.mLatestRequestId + 1.0d;
        this.mLatestRequestId = d;
        CompletableFuture completableFuture = this.mCurrentTask;
        if (completableFuture != null) {
            completableFuture.cancel(true);
        }
        this.mCurrentTask = CompletableFuture.supplyAsync(new Supplier() { // from class: com.miui.clock.utils.WeatherFetcherController$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return WeatherFetcherController.fetchWeatherData$lambda$0(context, z);
            }
        });
        ScheduledFuture<?> scheduledFutureSchedule = this.mExecutorService.schedule(new Runnable() { // from class: com.miui.clock.utils.WeatherFetcherController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                WeatherFetcherController.fetchWeatherData$lambda$2(this.f$0);
            }
        }, this.timeout, TimeUnit.MILLISECONDS);
        CompletableFuture completableFuture2 = this.mCurrentTask;
        if (completableFuture2 != null) {
            weatherFetcherController = this;
            callback2 = callback;
            final AnonymousClass2 anonymousClass2 = new AnonymousClass2(scheduledFutureSchedule, d, weatherFetcherController, callback2);
            completableFutureWhenComplete = completableFuture2.whenComplete(new BiConsumer() { // from class: com.miui.clock.utils.WeatherFetcherController$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    WeatherFetcherController.fetchWeatherData$lambda$3(anonymousClass2, obj, obj2);
                }
            });
        } else {
            weatherFetcherController = this;
            callback2 = callback;
            completableFutureWhenComplete = null;
        }
        if (completableFutureWhenComplete == null) {
            Log.w(weatherFetcherController.TAG, "weather fetcher mCurrentTask is null");
            weatherFetcherController.mMainThreadHandler.post(new Runnable() { // from class: com.miui.clock.utils.WeatherFetcherController$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    WeatherFetcherController.fetchWeatherData$lambda$5$lambda$4(callback2);
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
