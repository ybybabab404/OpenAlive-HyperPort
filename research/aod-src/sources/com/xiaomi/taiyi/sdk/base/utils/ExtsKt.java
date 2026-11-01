package com.xiaomi.taiyi.sdk.base.utils;

import android.os.Looper;
import android.os.Process;
import android.os.SystemClock;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
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
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ExtsKt {
    public static final Lazy MY_PID$delegate = LazyKt.lazy(new Function0() { // from class: com.xiaomi.taiyi.sdk.base.utils.ExtsKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Integer.valueOf(Process.myPid());
        }
    });
    public static final Lazy MY_UID$delegate = LazyKt.lazy(new Function0() { // from class: com.xiaomi.taiyi.sdk.base.utils.ExtsKt$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Integer.valueOf(Process.myUid());
        }
    });
    public static final Lazy MY_PROCESS_NAME$delegate = LazyKt.lazy(new Function0() { // from class: com.xiaomi.taiyi.sdk.base.utils.ExtsKt$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Process.myProcessName();
        }
    });

    public static final long bootMills() {
        return SystemClock.elapsedRealtime();
    }

    public static final String formatTime() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault()).format(Calendar.getInstance().getTime());
    }

    public static final int getMY_PID() {
        return ((Number) MY_PID$delegate.getValue()).intValue();
    }

    public static final String getMY_PROCESS_NAME() {
        return (String) MY_PROCESS_NAME$delegate.getValue();
    }

    public static final long getMY_TID() {
        return Thread.currentThread().getId();
    }

    public static final int getMY_UID() {
        return ((Number) MY_UID$delegate.getValue()).intValue();
    }

    public static final boolean isMainThread() {
        return Intrinsics.areEqual(Looper.getMainLooper().getThread(), Thread.currentThread());
    }

    public static final long timestamp() {
        return System.currentTimeMillis();
    }
}
