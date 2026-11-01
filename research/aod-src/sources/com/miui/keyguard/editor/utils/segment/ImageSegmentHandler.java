package com.miui.keyguard.editor.utils.segment;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil;
import com.miui.keyguard.editor.utils.Process;
import com.miui.keyguard.editor.utils.task.Task;
import com.xiaomi.aicr.common.AiServiceConnection;
import com.xiaomi.aicr.vision.imagesegment.ImageSegmentManager;
import com.xiaomi.taiyi.api.segment.SegmentAbility;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ImageSegmentHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ImageSegmentHandler {
    public static Application context;
    public static Pair resultConsumer;
    public static volatile SegmentAbility segmentAbility;
    public static volatile ImageSegmentManager segmentManager;
    public static final ImageSegmentHandler INSTANCE = new ImageSegmentHandler();
    public static AtomicBoolean initialized = new AtomicBoolean(false);
    public static final int CONNECT_MAX_COUNT = 60;
    public static final CopyOnWriteArrayList serviceListeners = new CopyOnWriteArrayList();
    public static final Lazy mAiServiceConnection$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.utils.segment.ImageSegmentHandler$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return ImageSegmentHandler.mAiServiceConnection_delegate$lambda$6();
        }
    });

    public static final void init$lambda$5() {
        initialized.compareAndSet(false, INSTANCE.initializeInternal());
    }

    public static final ImageSegmentHandler$mAiServiceConnection$2$1 mAiServiceConnection_delegate$lambda$6() {
        return new ImageSegmentHandler$mAiServiceConnection$2$1();
    }

    public static final SegmentInfo segment$lambda$0(Bitmap bitmap, long j) {
        if (context == null) {
            Log.e("ImageSegmentHandler", "segment: context is null");
            try {
                KeyguardEditorMiSightUtil.reportHierarchyInvisible$default(KeyguardEditorMiSightUtil.INSTANCE, "ImageSegmentHandler.segment_context_null", null, 2, null);
            } catch (Exception unused) {
            }
            return new SegmentInfo(j, null, null);
        }
        if (segmentAbility == null) {
            Application application = context;
            Intrinsics.checkNotNull(application);
            Context applicationContext = application.getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
            segmentAbility = new SegmentAbility(applicationContext);
        }
        SegmentAbility segmentAbility2 = segmentAbility;
        if (segmentAbility2 != null && segmentAbility2.isSupport()) {
            Log.e("ImageSegmentHandler", "segment: segmentAbility support");
            return new SegmentNewTask(segmentAbility, bitmap, j).get();
        }
        if (INSTANCE.checkInitialize()) {
            Log.e("ImageSegmentHandler", "segment: segmentManager init");
            return new SegmentTask(segmentManager, bitmap, j).get();
        }
        Log.e("ImageSegmentHandler", "segment: not init");
        return new SegmentInfo(j, null, null);
    }

    public static final SegmentInfo segment$lambda$1(Future future, long j) {
        try {
            return (SegmentInfo) future.get(6000L, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            Log.e("ImageSegmentHandler", "segmentFuture get", e);
            try {
                KeyguardEditorMiSightUtil.reportHierarchyInvisible$default(KeyguardEditorMiSightUtil.INSTANCE, "ImageSegmentHandler.segment_timeout_or_exception", null, 2, null);
            } catch (Exception unused) {
            }
            return new SegmentInfo(j, null, null);
        }
    }

    public static final void segment$lambda$4(SegmentInfo segmentInfo) {
        Pair pair = resultConsumer;
        if (pair == null) {
            Log.e("ImageSegmentHandler", "resultConsumer is null");
        } else if (segmentInfo.getId() == ((Number) pair.getFirst()).longValue()) {
            ((Consumer) pair.getSecond()).accept(segmentInfo);
        } else {
            Log.e("ImageSegmentHandler", "task id not match");
        }
    }

    public final void addVideoSegmentServiceListener(VideoSegmentServiceListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        serviceListeners.add(listener);
    }

    public final boolean checkInitialize() {
        if (initialized.get()) {
            return true;
        }
        if (initializeInternal()) {
            return initialized.compareAndSet(false, true);
        }
        return false;
    }

    public final Bitmap depthSegment(Bitmap source) {
        Intrinsics.checkNotNullParameter(source, "source");
        if (segmentManager == null) {
            return null;
        }
        if (!checkInitialize()) {
            Log.i("ImageSegmentHandler", "depthSegment: init failed");
            return null;
        }
        ImageSegmentManager imageSegmentManager = segmentManager;
        Intrinsics.checkNotNull(imageSegmentManager);
        if (imageSegmentManager.depth_getSegmentMainFuncVersion() == null) {
            Log.w("ImageSegmentHandler", "depthSegment: error not support");
            return null;
        }
        ImageSegmentManager imageSegmentManager2 = segmentManager;
        Intrinsics.checkNotNull(imageSegmentManager2);
        return imageSegmentManager2.depth_segmentMainFunc(source);
    }

    public final AiServiceConnection getMAiServiceConnection() {
        return (AiServiceConnection) mAiServiceConnection$delegate.getValue();
    }

    public final void init(Application context2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        if (!DeviceUtil.INSTANCE.isSupportHierarchyDevices() || !Process.INSTANCE.isKeyguardeditorProcess()) {
            Log.i("ImageSegmentHandler", "init not support");
        } else {
            context = context2;
            Task.run(new Runnable() { // from class: com.miui.keyguard.editor.utils.segment.ImageSegmentHandler$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ImageSegmentHandler.init$lambda$5();
                }
            });
        }
    }

    public final boolean initializeInternal() {
        if (!DeviceUtil.INSTANCE.isSupportHierarchyDevices()) {
            Log.i("ImageSegmentHandler", "initializeInternal not supported");
            return false;
        }
        if (segmentManager != null) {
            ImageSegmentManager imageSegmentManager = segmentManager;
            Intrinsics.checkNotNull(imageSegmentManager);
            if (imageSegmentManager.isConnected()) {
                Log.d("ImageSegmentHandler", "segmentManager != null and isConnected");
                return true;
            }
        }
        if (Intrinsics.areEqual(Looper.getMainLooper(), Looper.myLooper())) {
            Log.e("ImageSegmentHandler", " aicr cannot init in mainthread");
            return false;
        }
        synchronized (ImageSegmentHandler.class) {
            try {
                if (segmentManager == null) {
                    segmentManager = new ImageSegmentManager(context, INSTANCE.getMAiServiceConnection());
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        ImageSegmentManager imageSegmentManager2 = segmentManager;
        Intrinsics.checkNotNull(imageSegmentManager2);
        if (!imageSegmentManager2.isConnected()) {
            ImageSegmentManager imageSegmentManager3 = segmentManager;
            Intrinsics.checkNotNull(imageSegmentManager3);
            imageSegmentManager3.waitForConnection(5000L);
        }
        ImageSegmentManager imageSegmentManager4 = segmentManager;
        Intrinsics.checkNotNull(imageSegmentManager4);
        Log.i("ImageSegmentHandler", "isConnected " + imageSegmentManager4.isConnected());
        ImageSegmentManager imageSegmentManager5 = segmentManager;
        Intrinsics.checkNotNull(imageSegmentManager5);
        if (!imageSegmentManager5.isConnected()) {
            return false;
        }
        Log.i("ImageSegmentHandler", "start initAlgorithmCachePath");
        ImageSegmentManager imageSegmentManager6 = segmentManager;
        Intrinsics.checkNotNull(imageSegmentManager6);
        imageSegmentManager6.initAlgorithmCachePath();
        Log.i("ImageSegmentHandler", "initAlgorithmCachePath end");
        Log.i("ImageSegmentHandler", "start initPersonModel");
        ImageSegmentManager imageSegmentManager7 = segmentManager;
        Intrinsics.checkNotNull(imageSegmentManager7);
        imageSegmentManager7.initPersonModel();
        Log.i("ImageSegmentHandler", "initPersonModel end");
        Log.i("ImageSegmentHandler", "start initSkyModel");
        ImageSegmentManager imageSegmentManager8 = segmentManager;
        Intrinsics.checkNotNull(imageSegmentManager8);
        imageSegmentManager8.initSkyModel();
        Log.i("ImageSegmentHandler", "initSkyModel end");
        Log.i("ImageSegmentHandler", "depth_initAlgorithmCachePath end");
        Log.i("ImageSegmentHandler", "depth_initDepthModel end");
        return true;
    }

    public final void removeVideoSegmentServiceListener(VideoSegmentServiceListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        serviceListeners.remove(listener);
    }

    public final void segment(final Bitmap bitmap, Consumer consumer) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(consumer, "consumer");
        Log.i("ImageSegmentHandler", "begin segment");
        final long jElapsedRealtime = SystemClock.elapsedRealtime();
        resultConsumer = new Pair(Long.valueOf(jElapsedRealtime), consumer);
        final Future futureSubmit = Task.submit(new Callable() { // from class: com.miui.keyguard.editor.utils.segment.ImageSegmentHandler$$ExternalSyntheticLambda2
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return ImageSegmentHandler.segment$lambda$0(bitmap, jElapsedRealtime);
            }
        });
        Task.create(new Supplier() { // from class: com.miui.keyguard.editor.utils.segment.ImageSegmentHandler$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return ImageSegmentHandler.segment$lambda$1(futureSubmit, jElapsedRealtime);
            }
        }).post(new Consumer() { // from class: com.miui.keyguard.editor.utils.segment.ImageSegmentHandler$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ImageSegmentHandler.segment$lambda$4((SegmentInfo) obj);
            }
        });
    }
}
