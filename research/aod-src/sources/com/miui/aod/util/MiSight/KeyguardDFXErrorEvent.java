package com.miui.aod.util.MiSight;

import kotlin.Metadata;

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
/* JADX INFO: compiled from: KeyguardDFXErrorEvent.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class KeyguardDFXErrorEvent {

    /* JADX INFO: compiled from: KeyguardDFXErrorEvent.kt */
    @Metadata
    public static final class aodTimeError {
        public static final aodTimeError INSTANCE = new aodTimeError();
    }

    /* JADX INFO: compiled from: KeyguardDFXErrorEvent.kt */
    @Metadata
    public static final class aodViewAnomaly {
        public static final aodViewAnomaly INSTANCE = new aodViewAnomaly();
    }
}
