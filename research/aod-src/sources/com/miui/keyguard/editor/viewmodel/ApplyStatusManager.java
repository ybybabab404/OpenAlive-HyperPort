package com.miui.keyguard.editor.viewmodel;

import android.util.Log;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: EditorActivityViewModel.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ApplyStatusManager {
    public static final ApplyStatusManager INSTANCE = new ApplyStatusManager();
    public static volatile int status;

    public final synchronized int getStatus() {
        return status;
    }

    public final boolean isApplying() {
        return getStatus() == 1;
    }

    public final synchronized void updateStatus(int i) {
        if (!ArraysKt.contains(new Integer[]{0, 1, 2}, Integer.valueOf(i))) {
            Log.w("EditorActivityViewModel", "Invalid status value: " + i);
            return;
        }
        Log.d("EditorActivityViewModel", "updateStatus: status=" + i);
        status = i;
    }
}
