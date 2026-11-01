package com.miui.keyguard.editor.utils;

import android.os.Handler;
import android.os.Looper;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
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
    */
/* JADX INFO: compiled from: Debouncer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class Debouncer {
    public final long delayMillis;
    public final Handler handler;
    public Runnable runnable;

    public Debouncer(long j) {
        this.delayMillis = j;
        this.handler = new Handler(Looper.getMainLooper());
    }

    public /* synthetic */ Debouncer(long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 200L : j);
    }

    public final void debounce(final Function0 action) {
        Intrinsics.checkNotNullParameter(action, "action");
        Runnable runnable = this.runnable;
        if (runnable != null) {
            Handler handler = this.handler;
            if (runnable == null) {
                Intrinsics.throwUninitializedPropertyAccessException("runnable");
                runnable = null;
            }
            handler.removeCallbacks(runnable);
        }
        Runnable runnable2 = new Runnable() { // from class: com.miui.keyguard.editor.utils.Debouncer$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                action.invoke();
            }
        };
        this.runnable = runnable2;
        this.handler.postDelayed(runnable2, this.delayMillis);
    }
}
