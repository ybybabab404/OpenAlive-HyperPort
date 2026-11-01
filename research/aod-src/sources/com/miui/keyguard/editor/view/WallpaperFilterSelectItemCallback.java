package com.miui.keyguard.editor.view;

import android.view.View;
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
/* JADX INFO: compiled from: WallpaperFilterView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public interface WallpaperFilterSelectItemCallback {

    /* JADX INFO: compiled from: WallpaperFilterView.kt */
    @Metadata
    public static final class DefaultImpls {
        public static /* synthetic */ void onSelectItem$default(WallpaperFilterSelectItemCallback wallpaperFilterSelectItemCallback, int i, View view, boolean z, int i2, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onSelectItem");
            }
            if ((i2 & 4) != 0) {
                z = true;
            }
            wallpaperFilterSelectItemCallback.onSelectItem(i, view, z);
        }
    }

    void onCreatedItem(int i, View view);

    void onSelectItem(int i, View view, boolean z);
}
