package com.miui.keyguard.editor.edit.aiwapper;

import com.miui.maml.folme.AnimatedPropertyType;
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
/* JADX INFO: compiled from: AiWallPaperStatus.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class AiWallPaperStatus {
    public static final AiWallPaperStatus INSTANCE = new AiWallPaperStatus();
    public static final int[] DISABLE_ERROR_CODE_ARRAY = {1016, 1019, AnimatedPropertyType.PIVOT_Z, AnimatedPropertyType.TINT_COLOR, 20010, 20030, 20040, 40012, 40013, 1024};
    public static final int[] DISABLE_ERROR_CODE_ARRAY_REMOTE = {1016, 1019, AnimatedPropertyType.PIVOT_Z, AnimatedPropertyType.TINT_COLOR, 1024};
    public static final int[] NEED_ALTER_ERROR_CODE_ARRAY = {4600, AnimatedPropertyType.PIVOT_Z};

    public final int[] getDISABLE_ERROR_CODE_ARRAY() {
        return DISABLE_ERROR_CODE_ARRAY;
    }

    public final int[] getDISABLE_ERROR_CODE_ARRAY_REMOTE() {
        return DISABLE_ERROR_CODE_ARRAY_REMOTE;
    }

    public final int[] getNEED_ALTER_ERROR_CODE_ARRAY() {
        return NEED_ALTER_ERROR_CODE_ARRAY;
    }
}
