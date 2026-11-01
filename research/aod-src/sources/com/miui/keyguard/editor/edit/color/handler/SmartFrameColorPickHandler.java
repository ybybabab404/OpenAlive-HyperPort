package com.miui.keyguard.editor.edit.color.handler;

import com.miui.clock.module.ClockBean;
import com.miui.keyguard.editor.data.bean.SmartFrameInfo;
import com.miui.keyguard.editor.edit.color.ColorSelectBean;
import com.miui.keyguard.editor.edit.frameclock.FrameTemplateView;
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
    */
/* JADX INFO: compiled from: SmartFrameColorPickHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SmartFrameColorPickHandler extends ColorPickHandler {
    public final FrameTemplateView frameTemplateView;
    public final SmartFrameInfo smartFrameInfo;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmartFrameColorPickHandler(FrameTemplateView frameTemplateView, ClockBean clockBean, SmartFrameInfo smartFrameInfo) {
        super(frameTemplateView, clockBean, null, 4, null);
        Intrinsics.checkNotNullParameter(frameTemplateView, "frameTemplateView");
        Intrinsics.checkNotNullParameter(clockBean, "clockBean");
        Intrinsics.checkNotNullParameter(smartFrameInfo, "smartFrameInfo");
        this.frameTemplateView = frameTemplateView;
        this.smartFrameInfo = smartFrameInfo;
    }

    @Override // com.miui.keyguard.editor.edit.color.handler.ColorPickHandler
    public void onColorSelected(ColorSelectBean data) {
        Intrinsics.checkNotNullParameter(data, "data");
        getClockBean().setClockEffect(data.getFontFilterId());
        updateColorId(data);
        if (data.isPrimaryColor()) {
            if (data.getFontFilterId() == 0) {
                getClockBean().setPrimaryColor(data.getSelectedColor());
                getClockBean().setAutoPrimaryColor(data.isAutoPickColor());
            } else {
                getClockBean().setBlendColor(data.getSelectedColor());
                getClockBean().setAutoPrimaryColor(data.isAutoPickColor());
            }
            this.frameTemplateView.onClockInfoUpdate();
            return;
        }
        if (data.getFontFilterId() == 0) {
            getClockBean().setSecondaryColor(data.getSelectedColor());
            getClockBean().setAutoSecondaryColor(data.isAutoPickColor());
        } else {
            getClockBean().setSecondaryBlendColor(data.getSelectedColor());
            getClockBean().setAutoSecondaryColor(data.isAutoPickColor());
        }
        this.smartFrameInfo.setSolidColor(data.getSelectedColor());
        this.smartFrameInfo.setAutoSolidColor(data.isAutoPickColor());
        this.frameTemplateView.onFrameInfoUpdate();
    }
}
