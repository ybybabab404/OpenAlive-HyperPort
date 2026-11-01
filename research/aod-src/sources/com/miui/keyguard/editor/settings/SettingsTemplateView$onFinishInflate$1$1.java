package com.miui.keyguard.editor.settings;

import android.os.Handler;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView;
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
/* JADX INFO: compiled from: SettingsTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SettingsTemplateView$onFinishInflate$1$1 extends RecyclerView.AdapterDataObserver {
    public final /* synthetic */ SettingsTemplateView this$0;

    public SettingsTemplateView$onFinishInflate$1$1(SettingsTemplateView settingsTemplateView) {
        this.this$0 = settingsTemplateView;
    }

    public static final void onChanged$lambda$0(SettingsTemplateView settingsTemplateView) {
        Log.i("SettingsTemplateView", "viewPager2.requestTransform");
        settingsTemplateView.getViewPager2().requestTransform();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
    public void onChanged() {
        Handler handler = this.this$0.handler;
        final SettingsTemplateView settingsTemplateView = this.this$0;
        handler.post(new Runnable() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateView$onFinishInflate$1$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SettingsTemplateView$onFinishInflate$1$1.onChanged$lambda$0(settingsTemplateView);
            }
        });
    }
}
