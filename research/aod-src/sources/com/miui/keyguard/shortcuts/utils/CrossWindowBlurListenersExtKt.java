package com.miui.keyguard.shortcuts.utils;

import android.view.CrossWindowBlurListeners;
import kotlin.Metadata;
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
/* JADX INFO: compiled from: CrossWindowBlurListenersExt.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class CrossWindowBlurListenersExtKt {
    public static final CrossWindowBlurListeners getCrossWindowBlurListenersInstanceByReflection() {
        try {
            Object objInvoke = CrossWindowBlurListeners.class.getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type android.view.CrossWindowBlurListeners");
            return (CrossWindowBlurListeners) objInvoke;
        } catch (Exception e) {
            LogUtils.logW$default(LogUtils.INSTANCE, "CrossWindowBlurListenersExt", "getCrossWindowBlurListenersInstanceByReflection failed: " + e.getMessage(), null, 4, null);
            return null;
        }
    }

    public static final boolean isCrossWindowBlurEnabledByRefection(CrossWindowBlurListeners crossWindowBlurListeners) {
        Intrinsics.checkNotNullParameter(crossWindowBlurListeners, "<this>");
        try {
            Object objInvoke = CrossWindowBlurListeners.class.getDeclaredMethod("isCrossWindowBlurEnabled", new Class[0]).invoke(crossWindowBlurListeners, new Object[0]);
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.Boolean");
            return ((Boolean) objInvoke).booleanValue();
        } catch (Exception e) {
            LogUtils.logW$default(LogUtils.INSTANCE, "CrossWindowBlurListenersExt", "isCrossWindowBlurEnabledByRefection failed: " + e.getMessage(), null, 4, null);
            return false;
        }
    }
}
