package com.miui.keyguard.editor.view;

import android.content.Context;
import android.content.DialogInterface;
import com.miui.keyguard.editor.R;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.app.AlertDialog;
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
/* JADX INFO: compiled from: FashionGalleryDialogs.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class FashionGalleryDialogsKt {
    @NotNull
    public static final AlertDialog createDepthVideoDownloadDialog(@NotNull Context context, @NotNull Function2<? super DialogInterface, ? super Integer, Unit> onDialogPositiveClick, @NotNull Function2<? super DialogInterface, ? super Integer, Unit> onDialogNegativeClick) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(onDialogPositiveClick, "onDialogPositiveClick");
        Intrinsics.checkNotNullParameter(onDialogNegativeClick, "onDialogNegativeClick");
        Context applicationContext = context.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        AlertDialogTwoClickCallback alertDialogTwoClickCallback = new AlertDialogTwoClickCallback(applicationContext, onDialogPositiveClick, onDialogNegativeClick);
        AlertDialogBuilder alertDialogBuilder = new AlertDialogBuilder(context, 0, 2, null);
        alertDialogBuilder.setTitle(R.string.kg_depth_preset_dialog_title);
        alertDialogBuilder.setMessage(R.string.kg_depth_preset_dialog_content);
        alertDialogBuilder.setNegativeButton(R.string.kg_depth_preset_dialog_image_button, alertDialogTwoClickCallback);
        alertDialogBuilder.setPositiveButton(R.string.kg_depth_preset_dialog_download_button, alertDialogTwoClickCallback);
        AlertDialog alertDialogCreate = alertDialogBuilder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        return alertDialogCreate;
    }

    @NotNull
    public static final AlertDialog createFashionGalleryDialog(@NotNull Context context, @NotNull Function2<? super DialogInterface, ? super Integer, Unit> onDialogPositiveClick) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(onDialogPositiveClick, "onDialogPositiveClick");
        Context applicationContext = context.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        AlertDialogClickCallback alertDialogClickCallback = new AlertDialogClickCallback(applicationContext, onDialogPositiveClick);
        AlertDialogBuilder alertDialogBuilder = new AlertDialogBuilder(context, 0, 2, null);
        alertDialogBuilder.setTitle(R.string.kg_dialog_fashion_gallery_title);
        alertDialogBuilder.setMessage(R.string.kg_dialog_fashion_gallery_message);
        alertDialogBuilder.setNegativeButton(R.string.kg_dialog_fashion_gallery_button_negative, alertDialogClickCallback);
        alertDialogBuilder.setPositiveButton(R.string.kg_dialog_fashion_gallery_button_positive, alertDialogClickCallback);
        AlertDialog alertDialogCreate = alertDialogBuilder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        return alertDialogCreate;
    }
}
