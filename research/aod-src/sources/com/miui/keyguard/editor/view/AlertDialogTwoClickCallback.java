package com.miui.keyguard.editor.view;

import android.content.Context;
import android.content.DialogInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: FashionGalleryDialogs.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class AlertDialogTwoClickCallback extends AlertDialogClickCallback {
    public final Context context;
    public final Function2 onNegativeClick;
    public final Function2 onPositiveClick;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlertDialogTwoClickCallback(Context context, Function2 onPositiveClick, Function2 onNegativeClick) {
        super(context, onNegativeClick);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(onPositiveClick, "onPositiveClick");
        Intrinsics.checkNotNullParameter(onNegativeClick, "onNegativeClick");
        this.context = context;
        this.onPositiveClick = onPositiveClick;
        this.onNegativeClick = onNegativeClick;
    }

    @Override // com.miui.keyguard.editor.view.AlertDialogClickCallback, android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        super.onClick(dialogInterface, i);
        if (i != -1) {
            return;
        }
        this.onPositiveClick.invoke(dialogInterface, Integer.valueOf(i));
    }
}
