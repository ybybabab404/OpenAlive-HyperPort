package com.miui.keyguard.editor.view;

import android.view.View;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;

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
public final class AodDialogViewManager {
    public final List aodClockViews = new ArrayList();
    public View aodPreview;
    public BaseTemplateView lockscreenPreview;
    public View rootView;

    public final List getAodClockViews() {
        return this.aodClockViews;
    }

    public final View getRootView() {
        return this.rootView;
    }

    public final void onDismiss() {
        this.rootView = null;
        BaseTemplateView baseTemplateView = this.lockscreenPreview;
        if (baseTemplateView != null) {
            baseTemplateView.onDestroy();
        }
        this.lockscreenPreview = null;
        this.aodPreview = null;
        this.aodClockViews.clear();
    }

    public final void setAodPreview(View view) {
        this.aodPreview = view;
    }

    public final void setRootView(View view) {
        this.rootView = view;
    }
}
