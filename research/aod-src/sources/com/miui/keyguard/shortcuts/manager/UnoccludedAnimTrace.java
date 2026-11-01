package com.miui.keyguard.shortcuts.manager;

import android.os.Trace;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: TraceTagManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class UnoccludedAnimTrace {
    public static final UnoccludedAnimTrace INSTANCE = new UnoccludedAnimTrace();
    public static final int TRACE_COOKIE_UNOCCLUDED_LEASH_HIDE = -1538566676;
    public static final AtomicBoolean traceStartedForLeashHide = new AtomicBoolean(false);

    public final void endForLeashHide() {
        if (traceStartedForLeashHide.getAndSet(false)) {
            Trace.endAsyncSection("KeyguardAnim-ShortcutUnocc-leashHide", TRACE_COOKIE_UNOCCLUDED_LEASH_HIDE);
        }
    }

    public final void startForLeashHide() {
        if (traceStartedForLeashHide.getAndSet(true)) {
            return;
        }
        Trace.beginAsyncSection("KeyguardAnim-ShortcutUnocc-leashHide", TRACE_COOKIE_UNOCCLUDED_LEASH_HIDE);
    }
}
