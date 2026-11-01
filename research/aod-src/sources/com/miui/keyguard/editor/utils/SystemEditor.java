package com.miui.keyguard.editor.utils;

import android.os.Build;
import android.os.SystemProperties;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.functions.Function0;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: Constants.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SystemEditor {
    public static final SystemEditor INSTANCE = new SystemEditor();
    public static final int osVersion;
    public static final Lazy sdkVersion$delegate;
    public static final Lazy systemVersion$delegate;

    static {
        Object objM1061constructorimpl;
        try {
            Result.Companion companion = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(Integer.valueOf(SystemProperties.getInt("ro.mi.os.version.code", 0)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m1065isFailureimpl(objM1061constructorimpl)) {
            objM1061constructorimpl = 0;
        }
        osVersion = ((Number) objM1061constructorimpl).intValue();
        systemVersion$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.utils.SystemEditor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SystemEditor.systemVersion_delegate$lambda$1();
            }
        });
        sdkVersion$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.utils.SystemEditor$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Integer.valueOf(Build.VERSION.SDK_INT);
            }
        });
    }

    public static final String systemVersion_delegate$lambda$1() {
        return Build.VERSION.RELEASE + "_" + Build.VERSION.INCREMENTAL;
    }

    public final int getOsVersion() {
        return osVersion;
    }

    public final String getSystemVersion() {
        return (String) systemVersion$delegate.getValue();
    }
}
