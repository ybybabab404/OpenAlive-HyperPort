package com.miui.keyguard.editor.data.bean;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: DepthVideo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class DepthVideo {
    public static final int AI_CONVERT_TO_RGB_FAILED = 2;
    public static final int AI_INIT_FAILED = 1;
    public static final int AI_INVALID_INPUT = 3;
    public static final int AI_INVALID_OUTPUT = 4;
    public static final int AI_NOT_SUPPORT = 6;
    public static final int AI_SEG_FAILED = 5;

    @NotNull
    public static final DepthVideo INSTANCE = new DepthVideo();

    private DepthVideo() {
    }
}
