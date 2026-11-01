package com.miui.aod.util;

import android.content.Context;
import android.widget.Toast;
import java.lang.ref.WeakReference;
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
/* JADX INFO: compiled from: ToastUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class ToastUtils {
    public Toast.Callback callBack;
    public WeakReference currentToast;
    public boolean isShowing;

    public final void cancelToast() {
        Toast toast;
        WeakReference weakReference;
        Toast toast2;
        Toast.Callback callback = this.callBack;
        if (callback != null && (weakReference = this.currentToast) != null && (toast2 = (Toast) weakReference.get()) != null) {
            toast2.removeCallback(callback);
        }
        WeakReference weakReference2 = this.currentToast;
        if (weakReference2 != null && (toast = (Toast) weakReference2.get()) != null) {
            toast.cancel();
        }
        this.currentToast = null;
    }

    public final void showToast(Context context, String toast, int i) {
        Toast toast2;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(toast, "toast");
        if (this.isShowing || toast.length() == 0) {
            return;
        }
        if (this.callBack == null) {
            this.callBack = new Toast.Callback() { // from class: com.miui.aod.util.ToastUtils.showToast.1
                @Override // android.widget.Toast.Callback
                public void onToastHidden() {
                    super.onToastHidden();
                    ToastUtils.this.isShowing = false;
                    ToastUtils.this.currentToast = null;
                }

                @Override // android.widget.Toast.Callback
                public void onToastShown() {
                    super.onToastShown();
                    ToastUtils.this.isShowing = true;
                }
            };
        }
        Toast.Callback callback = this.callBack;
        if (callback != null) {
            WeakReference weakReference = this.currentToast;
            if (weakReference != null && (toast2 = (Toast) weakReference.get()) != null) {
                toast2.removeCallback(callback);
            }
            Toast toastMakeText = Toast.makeText(context, toast, i);
            toastMakeText.addCallback(callback);
            toastMakeText.show();
            this.currentToast = new WeakReference(toastMakeText);
        }
    }
}
