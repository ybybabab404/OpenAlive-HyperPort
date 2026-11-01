package com.miui.keyguard.editor.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import androidx.core.content.res.ResourcesCompat;
import com.miui.keyguard.editor.R;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
/* JADX INFO: compiled from: AlertDialogsKt.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class AlertDialogBuilder extends AlertDialog.Builder {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlertDialogBuilder(Context context, int i) {
        super(context, i);
        Intrinsics.checkNotNullParameter(context, "context");
        setOnShowListener(null);
        preloadResourceForDialog();
    }

    public /* synthetic */ AlertDialogBuilder(Context context, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? AlertDialogsKtKt.isDarkMode(context) ? R.style.KgAlertDialogDark : R.style.KgAlertDialog : i);
    }

    public static final void setOnShowListener$lambda$0(AlertDialogBuilder alertDialogBuilder, DialogInterface.OnShowListener onShowListener, DialogInterface dialogInterface) {
        Context context = alertDialogBuilder.getContext();
        ContextWrapper contextWrapper = context instanceof ContextWrapper ? (ContextWrapper) context : null;
        Context baseContext = contextWrapper != null ? contextWrapper.getBaseContext() : null;
        DialogInterface.OnShowListener onShowListener2 = baseContext instanceof DialogInterface.OnShowListener ? (DialogInterface.OnShowListener) baseContext : null;
        if (onShowListener2 != null) {
            onShowListener2.onShow(dialogInterface);
        }
        if (onShowListener != null) {
            onShowListener.onShow(dialogInterface);
        }
    }

    public final void preloadResourceForDialog() {
        Integer[] numArr = {Integer.valueOf(R.drawable.miuix_appcompat_dialog_round_bg_light), Integer.valueOf(R.drawable.miuix_appcompat_scrollbar_thumb_vertical_light), Integer.valueOf(R.drawable.miuix_appcompat_dialog_default_btn_bg_light_smooth), Integer.valueOf(R.drawable.miuix_appcompat_dialog_default_btn_bg_primary_light_smooth)};
        Integer numValueOf = Integer.valueOf(R.color.miuix_appcompat_action_bar_title_text_light);
        int i = R.color.miuix_appcompat_secondary_text_light;
        Integer[] numArr2 = {numValueOf, Integer.valueOf(i), Integer.valueOf(R.color.miuix_appcompat_normal_text_light), Integer.valueOf(i), Integer.valueOf(R.color.miuix_appcompat_btn_bg_color_light), Integer.valueOf(R.color.miuix_appcompat_button_text_light), Integer.valueOf(R.color.miuix_appcompat_btn_bg_primary_color_light)};
        Integer[] numArr3 = {Integer.valueOf(R.layout.miuix_appcompat_alert_dialog), Integer.valueOf(R.layout.miuix_appcompat_alert_dialog_dim_bg)};
        Integer[] numArr4 = {Integer.valueOf(android.R.transition.fade), Integer.valueOf(android.R.transition.move)};
        Integer[] numArr5 = {Integer.valueOf(android.R.interpolator.linear_out_slow_in)};
        Resources resources = getContext().getResources();
        Resources.Theme theme = getContext().getTheme();
        for (int i2 = 0; i2 < 4; i2++) {
            ResourcesCompat.getDrawable(resources, numArr[i2].intValue(), theme);
        }
        for (int i3 = 0; i3 < 7; i3++) {
            ResourcesCompat.getColor(resources, numArr2[i3].intValue(), theme);
        }
        for (int i4 = 0; i4 < 2; i4++) {
            LayoutInflater.from(getContext()).inflate(numArr3[i4].intValue(), (ViewGroup) null);
        }
        for (int i5 = 0; i5 < 2; i5++) {
            TransitionInflater.from(getContext()).inflateTransition(numArr4[i5].intValue());
        }
        AnimationUtils.loadInterpolator(getContext(), numArr5[0].intValue());
    }

    @Override // miuix.appcompat.app.AlertDialog.Builder
    public AlertDialog.Builder setOnShowListener(final DialogInterface.OnShowListener onShowListener) {
        AlertDialog.Builder onShowListener2 = super.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.miui.keyguard.editor.view.AlertDialogBuilder$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                AlertDialogBuilder.setOnShowListener$lambda$0(this.f$0, onShowListener, dialogInterface);
            }
        });
        Intrinsics.checkNotNullExpressionValue(onShowListener2, "setOnShowListener(...)");
        return onShowListener2;
    }
}
