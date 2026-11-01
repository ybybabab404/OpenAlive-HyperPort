package com.miui.keyguard.shortcuts.utils;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import java.lang.ref.WeakReference;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import miuix.view.HapticCompat;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: HapticFeedbackUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class HapticFeedbackUtils {
    public static Context appContext;
    public static volatile Handler bgHandler;
    public static volatile HandlerThread bgThread;
    public static volatile boolean isInitialized;
    public static final HapticFeedbackUtils INSTANCE = new HapticFeedbackUtils();
    public static final Lazy obtainVibrator$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.utils.HapticFeedbackUtils$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return HapticFeedbackUtils.obtainVibrator_delegate$lambda$2();
        }
    });

    public static final Vibrator obtainVibrator_delegate$lambda$2() {
        Vibrator vibrator;
        Context context = appContext;
        if (context == null || (vibrator = (Vibrator) context.getSystemService(Vibrator.class)) == null) {
            Log.w("HapticFeedbackUtils", "Vibrator service not available");
            return null;
        }
        Log.d("HapticFeedbackUtils", "Vibrator initialized");
        return vibrator;
    }

    public static final void performHapticFeedback$lambda$4(WeakReference weakReference, int i, int i2, long j) {
        Vibrator obtainVibrator;
        try {
            View view = (View) weakReference.get();
            if (view == null) {
                Log.d("HapticFeedbackUtils", "View was garbage collected, skipping haptic feedback");
            } else {
                if (HapticCompat.performHapticFeedback(view, i, i2) || j <= 0 || (obtainVibrator = INSTANCE.getObtainVibrator()) == null) {
                    return;
                }
                obtainVibrator.vibrate(VibrationEffect.createOneShot(j, -1));
            }
        } catch (Exception e) {
            Log.w("HapticFeedbackUtils", "performHapticFeedback failed", e);
        }
    }

    public final void cleanup() {
        Looper looper;
        isInitialized = false;
        Handler handler = bgHandler;
        if (handler != null && (looper = handler.getLooper()) != null) {
            looper.quit();
        }
        HandlerThread handlerThread = bgThread;
        if (handlerThread != null) {
            handlerThread.quit();
        }
        bgHandler = null;
        bgThread = null;
        appContext = null;
    }

    public final Vibrator getObtainVibrator() {
        return (Vibrator) obtainVibrator$delegate.getValue();
    }

    public final boolean hasVibrator() {
        Log.d("HapticFeedbackUtils", "hasVibrator:" + getObtainVibrator());
        Vibrator obtainVibrator = getObtainVibrator();
        return obtainVibrator != null && obtainVibrator.hasVibrator();
    }

    public final void init(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (isInitialized) {
            Log.w("HapticFeedbackUtils", "Already initialized, skipping re-initialization");
            return;
        }
        isInitialized = true;
        Context applicationContext = context.getApplicationContext();
        if (applicationContext != null) {
            context = applicationContext;
        }
        appContext = context;
        HandlerThread handlerThread = new HandlerThread("HapticFeedbackBgThread");
        handlerThread.start();
        bgHandler = new Handler(handlerThread.getLooper());
        bgThread = handlerThread;
    }

    public final void performHapticFeedback(View view, int i, int i2, long j) {
        Intrinsics.checkNotNullParameter(view, "view");
        performHapticFeedback(view, i, i2, j, null);
    }

    public final void performHapticFeedback(View view, final int i, final int i2, final long j, Handler handler) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (!hasVibrator() || !isInitialized) {
            Log.w("HapticFeedbackUtils", "performHapticFeedback called after cleanup or before init");
        } else {
            final WeakReference weakReference = new WeakReference(view);
            postToBgThreadIfNeed(new Runnable() { // from class: com.miui.keyguard.shortcuts.utils.HapticFeedbackUtils$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    HapticFeedbackUtils.performHapticFeedback$lambda$4(weakReference, i, i2, j);
                }
            }, handler);
        }
    }

    public final void postToBgThreadIfNeed(Runnable runnable, Handler handler) {
        if (handler != null) {
            handler.post(runnable);
            return;
        }
        if (!Looper.getMainLooper().isCurrentThread() || !isInitialized) {
            runnable.run();
            return;
        }
        Handler handler2 = bgHandler;
        if (handler2 != null) {
            handler2.post(runnable);
        }
    }
}
