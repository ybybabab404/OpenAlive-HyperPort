package com.miui.keyguard.editor.homepage.view.adapter;

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
/* JADX INFO: compiled from: BaseAdapterAndHolder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public interface PreviewHolder {

    /* JADX INFO: compiled from: BaseAdapterAndHolder.kt */
    @Metadata
    public static final class DefaultImpls {
        public static /* synthetic */ void switchHome$default(PreviewHolder previewHolder, boolean z, boolean z2, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: switchHome");
            }
            if ((i & 2) != 0) {
                z2 = true;
            }
            previewHolder.switchHome(z, z2);
        }
    }

    View getCustomButton();

    View getHomePreview();

    View getItemView();

    View getLockPreview();

    Object getPreviewData();

    void switchHome(boolean z, boolean z2);
}
