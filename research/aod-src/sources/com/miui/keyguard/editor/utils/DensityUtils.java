package com.miui.keyguard.editor.utils;

import android.util.DisplayMetrics;
import com.miui.keyguard.editor.EditorApplicationProxy;
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
/* JADX INFO: compiled from: DensityUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class DensityUtils {
    public static final DensityUtils INSTANCE = new DensityUtils();
    public static DisplayMetrics mDisplayMetrics;

    public static final int dp2Px(int i) {
        DisplayMetrics defaultDisplayMetrics = INSTANCE.getDefaultDisplayMetrics();
        return defaultDisplayMetrics != null ? (int) (((double) (defaultDisplayMetrics.density * i)) + 0.5d) : i;
    }

    public final DisplayMetrics getDefaultDisplayMetrics() {
        if (mDisplayMetrics == null) {
            mDisplayMetrics = EditorApplicationProxy.Companion.getApplication().getResources().getDisplayMetrics();
        }
        return mDisplayMetrics;
    }
}
