package com.miui.keyguard.editor.edit.base;

import android.content.res.Configuration;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: BaseTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public interface CombineEditListener {

    /* JADX INFO: compiled from: BaseTemplateView.kt */
    @Metadata
    public static final class DefaultImpls {
        public static int getCurrentSelect(CombineEditListener combineEditListener) {
            return 0;
        }

        public static int getDialogOffset(CombineEditListener combineEditListener) {
            return 0;
        }

        public static void onDismissBottomSheet(CombineEditListener combineEditListener) {
        }

        public static void onDismissBottomSheetForScreenChange(CombineEditListener combineEditListener, Configuration newConfig) {
            Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        }

        public static void onDismissKeyboard(CombineEditListener combineEditListener) {
        }

        public static void onPreviewChanged(CombineEditListener combineEditListener, boolean z, boolean z2) {
        }

        public static /* synthetic */ void onPreviewChanged$default(CombineEditListener combineEditListener, boolean z, boolean z2, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onPreviewChanged");
            }
            if ((i & 2) != 0) {
                z2 = false;
            }
            combineEditListener.onPreviewChanged(z, z2);
        }

        public static void onScrolledChanged(CombineEditListener combineEditListener, boolean z, boolean z2) {
        }

        public static void onShowBottomSheet(CombineEditListener combineEditListener) {
        }

        public static void onTemplateReady(CombineEditListener combineEditListener, int i) {
        }
    }

    int getCurrentSelect();

    int getDialogOffset();

    void onDismissBottomSheet();

    void onDismissBottomSheetForScreenChange(Configuration configuration);

    void onDismissKeyboard();

    void onPreviewChanged(boolean z, boolean z2);

    void onScrolledChanged(boolean z, boolean z2);

    void onShowBottomSheet();

    void onTemplateReady(int i);
}
