package com.miui.keyguard.editor.edit.base;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: FastPlayerManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class FastPlayerManagerKt {
    public static final Lazy playerThread$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.edit.base.FastPlayerManagerKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return FastPlayerManagerKt.playerThread_delegate$lambda$1();
        }
    });
    public static final Lazy playerHandler$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.edit.base.FastPlayerManagerKt$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return FastPlayerManagerKt.playerHandler_delegate$lambda$2();
        }
    });

    public static final Handler getPlayerHandler() {
        return (Handler) playerHandler$delegate.getValue();
    }

    public static final HandlerThread getPlayerThread() {
        return (HandlerThread) playerThread$delegate.getValue();
    }

    public static final Handler playerHandler_delegate$lambda$2() {
        return new Handler(getPlayerThread().getLooper());
    }

    public static final HandlerThread playerThread_delegate$lambda$1() {
        HandlerThread handlerThread = new HandlerThread("FastPlayerThread");
        handlerThread.start();
        return handlerThread;
    }

    public static final void runOnPlayerThread(final Function0 function0) {
        if (Looper.myLooper() == getPlayerThread().getLooper()) {
            function0.invoke();
        } else {
            getPlayerHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.FastPlayerManagerKt$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    function0.invoke();
                }
            });
        }
    }
}
