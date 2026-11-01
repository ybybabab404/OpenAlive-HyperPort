package com.miui.keyguard.editor.utils;

import android.content.Context;
import android.content.DialogInterface;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.view.AlertDialogBuilder;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.app.AlertDialog;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: SpecialWallpaperHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SpecialWallpaperHelper {
    public static final SpecialWallpaperHelper INSTANCE = new SpecialWallpaperHelper();
    public static final List NOT_SUPPORT_SUPER_WALLPAPER_TEMPLATES = CollectionsKt.listOf((Object[]) new String[]{"doodle", "smart_frame"});

    public static /* synthetic */ AlertDialog createConfirmDialogReplaceLinkageVideoWallpaper$default(SpecialWallpaperHelper specialWallpaperHelper, Context context, DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onClickListener2, int i, Object obj) {
        if ((i & 4) != 0) {
            onClickListener2 = null;
        }
        return specialWallpaperHelper.createConfirmDialogReplaceLinkageVideoWallpaper(context, onClickListener, onClickListener2);
    }

    public static final void createConfirmDialogReplaceLinkageVideoWallpaper$lambda$5(DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onClickListener2, DialogInterface dialogInterface, int i) {
        if (dialogInterface != null) {
            ViewUtil.INSTANCE.writeApplyFlag(dialogInterface, i != -1);
            dialogInterface.dismiss();
            if (i != -2) {
                if (i != -1) {
                    return;
                }
                onClickListener.onClick(dialogInterface, i);
            } else if (onClickListener2 != null) {
                onClickListener2.onClick(dialogInterface, i);
            }
        }
    }

    public static /* synthetic */ AlertDialog createConfirmDialogReplaceSuperWallpaper$default(SpecialWallpaperHelper specialWallpaperHelper, Context context, DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onClickListener2, int i, Object obj) {
        if ((i & 4) != 0) {
            onClickListener2 = null;
        }
        return specialWallpaperHelper.createConfirmDialogReplaceSuperWallpaper(context, onClickListener, onClickListener2);
    }

    public static final void createConfirmDialogReplaceSuperWallpaper$lambda$0(DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onClickListener2, DialogInterface dialogInterface, int i) {
        if (dialogInterface == null) {
            return;
        }
        ViewUtil.INSTANCE.writeApplyFlag(dialogInterface, i != -1);
        dialogInterface.dismiss();
        if (i != -2) {
            if (i != -1) {
                return;
            }
            onClickListener.onClick(dialogInterface, i);
        } else if (onClickListener2 != null) {
            onClickListener2.onClick(dialogInterface, i);
        }
    }

    public static /* synthetic */ AlertDialog createConfirmDialogWithUnSupportTemplate$default(SpecialWallpaperHelper specialWallpaperHelper, Context context, DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onClickListener2, int i, Object obj) {
        if ((i & 4) != 0) {
            onClickListener2 = null;
        }
        return specialWallpaperHelper.createConfirmDialogWithUnSupportTemplate(context, onClickListener, onClickListener2);
    }

    public static final void createConfirmDialogWithUnSupportTemplate$lambda$2(DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onClickListener2, DialogInterface dialogInterface, int i) {
        if (dialogInterface != null) {
            ViewUtil.INSTANCE.writeApplyFlag(dialogInterface, i != -1);
        }
        if (dialogInterface != null) {
            dialogInterface.dismiss();
        }
        if (i != -2) {
            if (i != -1) {
                return;
            }
            onClickListener.onClick(dialogInterface, i);
        } else if (onClickListener2 != null) {
            onClickListener2.onClick(dialogInterface, i);
        }
    }

    public final AlertDialog createConfirmDialogReplaceLinkageVideoWallpaper(Context context, final DialogInterface.OnClickListener positiveClick, final DialogInterface.OnClickListener onClickListener) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(positiveClick, "positiveClick");
        DialogInterface.OnClickListener onClickListener2 = new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.utils.SpecialWallpaperHelper$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SpecialWallpaperHelper.createConfirmDialogReplaceLinkageVideoWallpaper$lambda$5(positiveClick, onClickListener, dialogInterface, i);
            }
        };
        AlertDialogBuilder alertDialogBuilder = new AlertDialogBuilder(context, 0, 2, null);
        alertDialogBuilder.setTitle(R.string.kg_dialog_apply_linkage_video_wallpaper_confirm_title);
        alertDialogBuilder.setMessage(R.string.kg_dialog_apply_linkage_video_wallpaper_wallpaper_confirm);
        alertDialogBuilder.setNegativeButton(R.string.kg_dialog_apply_linkage_video_wallpaper_negative_button, onClickListener2);
        alertDialogBuilder.setPositiveButton(R.string.kg_dialog_apply_linkage_video_wallpaper_positive_button, onClickListener2);
        AlertDialog alertDialogCreate = alertDialogBuilder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        return alertDialogCreate;
    }

    public final AlertDialog createConfirmDialogReplaceSuperWallpaper(Context context, final DialogInterface.OnClickListener positiveClick, final DialogInterface.OnClickListener onClickListener) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(positiveClick, "positiveClick");
        DialogInterface.OnClickListener onClickListener2 = new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.utils.SpecialWallpaperHelper$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SpecialWallpaperHelper.createConfirmDialogReplaceSuperWallpaper$lambda$0(positiveClick, onClickListener, dialogInterface, i);
            }
        };
        AlertDialogBuilder alertDialogBuilder = new AlertDialogBuilder(context, 0, 2, null);
        alertDialogBuilder.setMessage(R.string.kg_dialog_apply_super_wallpaper_confirm);
        alertDialogBuilder.setNegativeButton(R.string.kg_dialog_apply_super_wallpaper_negative_button, onClickListener2);
        alertDialogBuilder.setPositiveButton(R.string.kg_dialog_apply_super_wallpaper_positive_button, onClickListener2);
        AlertDialog alertDialogCreate = alertDialogBuilder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        return alertDialogCreate;
    }

    public final AlertDialog createConfirmDialogWithUnSupportTemplate(Context context, final DialogInterface.OnClickListener positiveClick, final DialogInterface.OnClickListener onClickListener) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(positiveClick, "positiveClick");
        DialogInterface.OnClickListener onClickListener2 = new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.utils.SpecialWallpaperHelper$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SpecialWallpaperHelper.createConfirmDialogWithUnSupportTemplate$lambda$2(positiveClick, onClickListener, dialogInterface, i);
            }
        };
        AlertDialogBuilder alertDialogBuilder = new AlertDialogBuilder(context, 0, 2, null);
        alertDialogBuilder.setMessage(R.string.kg_dialog_apply_not_support_super_wallpaper_confirm);
        alertDialogBuilder.setNegativeButton(R.string.kg_dialog_apply_super_wallpaper_negative_button, onClickListener2);
        alertDialogBuilder.setPositiveButton(R.string.kg_dialog_apply_super_wallpaper_positive_button, onClickListener2);
        AlertDialog alertDialogCreate = alertDialogBuilder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        return alertDialogCreate;
    }

    public final boolean isUnSupportSuperWallpaper(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return NOT_SUPPORT_SUPER_WALLPAPER_TEMPLATES.contains(str);
    }
}
