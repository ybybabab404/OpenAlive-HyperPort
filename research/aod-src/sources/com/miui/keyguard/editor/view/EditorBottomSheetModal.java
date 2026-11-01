package com.miui.keyguard.editor.view;

import android.app.Activity;
import android.util.Log;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.bottomsheet.BottomSheetModal;

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
public final class EditorBottomSheetModal extends BottomSheetModal {
    public BottomSheetModal.OnDismissListener onBeforeDismissListener;
    public BottomSheetModal.OnShowListener onBeforeShowListener;
    public boolean showing;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditorBottomSheetModal(Activity activity, boolean z) {
        super(activity, z);
        Intrinsics.checkNotNullParameter(activity, "activity");
    }

    @Override // miuix.bottomsheet.BottomSheetModal
    public void dismiss() {
        Log.d("AutoBottomSheet", "dismiss: EditorBottomSheetModal = " + this);
        this.showing = false;
        BottomSheetModal.OnDismissListener onDismissListener = this.onBeforeDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
        super.dismiss();
    }

    public final boolean isShowing() {
        return this.showing;
    }

    public final void setBeforeDismissListener(BottomSheetModal.OnDismissListener onDismissListener) {
        this.onBeforeDismissListener = onDismissListener;
    }

    public final void setBeforeShowListener(BottomSheetModal.OnShowListener onShowListener) {
        this.onBeforeShowListener = onShowListener;
    }

    @Override // miuix.bottomsheet.BottomSheetModal
    public void show() {
        this.showing = true;
        BottomSheetModal.OnShowListener onShowListener = this.onBeforeShowListener;
        if (onShowListener != null) {
            onShowListener.onShow();
        }
        super.show();
    }
}
