package com.miui.keyguard.editor.view;

import android.content.Context;
import android.content.DialogInterface;
import com.miui.keyguard.editor.track.TrackHelper;
import com.miui.keyguard.editor.utils.ViewUtil;
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
public class AlertDialogClickCallback implements DialogInterface.OnClickListener {
    public final Context context;
    public final Function2 onNegativeClick;

    public AlertDialogClickCallback(Context context, Function2 onNegativeClick) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(onNegativeClick, "onNegativeClick");
        this.context = context;
        this.onNegativeClick = onNegativeClick;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        if (dialogInterface != null) {
            ViewUtil.INSTANCE.writeApplyFlag(dialogInterface, i == -1);
        }
        if (i == -2) {
            this.onNegativeClick.invoke(dialogInterface, Integer.valueOf(i));
            TrackHelper.INSTANCE.trackVideoDialogClick(this.context, true);
            if (dialogInterface != null) {
                dialogInterface.dismiss();
                return;
            }
            return;
        }
        if (i != -1) {
            return;
        }
        TrackHelper.INSTANCE.trackVideoDialogClick(this.context, false);
        if (dialogInterface != null) {
            dialogInterface.dismiss();
        }
    }
}
