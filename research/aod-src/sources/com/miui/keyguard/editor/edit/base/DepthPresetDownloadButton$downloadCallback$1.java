package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import com.miui.keyguard.editor.R;
import com.miui.maml.folme.AnimatedPropertyType;
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
/* JADX INFO: compiled from: DepthPresetDownloadButton.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class DepthPresetDownloadButton$downloadCallback$1 implements DownloadButtonCallback {
    public final /* synthetic */ Context $context;
    public final /* synthetic */ DepthPresetDownloadButton this$0;

    public DepthPresetDownloadButton$downloadCallback$1(DepthPresetDownloadButton depthPresetDownloadButton, Context context) {
        this.this$0 = depthPresetDownloadButton;
        this.$context = context;
    }

    public static final void onChanged$lambda$0(int i, DepthPresetDownloadButton depthPresetDownloadButton, int i2, Context context) {
        switch (i) {
            case AnimatedPropertyType.TEXT_SIZE /* 1002 */:
                if (depthPresetDownloadButton.getStatus() == 0) {
                    DepthPresetDownloadButton.changeStatus$default(depthPresetDownloadButton, 1, false, 2, null);
                }
                depthPresetDownloadButton.changeProgressStatus(AnimatedPropertyType.TEXT_SIZE, i2);
                break;
            case AnimatedPropertyType.FILL_COLOR /* 1004 */:
                if (depthPresetDownloadButton.getStatus() == 1) {
                    DepthPresetDownloadButton.changeStatus$default(depthPresetDownloadButton, 0, false, 2, null);
                }
                break;
            case AnimatedPropertyType.STROKE_COLOR /* 1005 */:
                depthPresetDownloadButton.checkVideoFinish();
                break;
            case AnimatedPropertyType.CORNER_RADIUS_X /* 1006 */:
            case AnimatedPropertyType.TINT_COLOR /* 1008 */:
                if (depthPresetDownloadButton.getStatus() == 1) {
                    DepthPresetDownloadButton.changeStatus$default(depthPresetDownloadButton, 0, false, 2, null);
                    depthPresetDownloadButton.getIcon().setImageResource(R.drawable.kg_function_icon_no_network);
                    depthPresetDownloadButton.getTextView().setText(context.getResources().getString(R.string.kg_depth_preset_network_fail));
                }
                break;
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.DownloadButtonCallback
    public void onChanged(final int i, final int i2) {
        final DepthPresetDownloadButton depthPresetDownloadButton = this.this$0;
        final Context context = this.$context;
        depthPresetDownloadButton.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.DepthPresetDownloadButton$downloadCallback$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DepthPresetDownloadButton$downloadCallback$1.onChanged$lambda$0(i, depthPresetDownloadButton, i2, context);
            }
        });
    }
}
