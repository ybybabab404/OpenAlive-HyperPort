package com.miui.keyguard.editor.view;

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
/* JADX INFO: compiled from: AutoBottomSheet.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class AutoBottomSheetKt {
    public static final int ALIGN_BOTTOM_TO_BOTTOM = 4;
    public static final int ALIGN_BOTTOM_TO_TOP = 8;
    public static final int ALIGN_END_TO_END = 128;
    public static final int ALIGN_END_TO_START = 64;
    public static final int ALIGN_SCREEN_BOTTOM = 1024;
    public static final int ALIGN_SCREEN_CENTER_HORIZONTAL = 256;
    public static final int ALIGN_SCREEN_CENTER_VERTICAL = 512;
    public static final int ALIGN_SCREEN_END = 2048;
    public static final int ALIGN_SCREEN_START = 8192;
    public static final int ALIGN_SCREEN_TOP = 4096;
    public static final int ALIGN_START_TO_END = 16;
    public static final int ALIGN_START_TO_START = 32;
    public static final int ALIGN_TOP_TO_BOTTOM = 1;
    public static final int ALIGN_TOP_TO_TOP = 2;

    @NotNull
    private static final String TAG = "AutoBottomSheet";
}
