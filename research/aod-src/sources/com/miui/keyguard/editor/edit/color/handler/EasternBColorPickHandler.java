package com.miui.keyguard.editor.edit.color.handler;

import com.miui.clock.module.ClockBean;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.color.ColorSelectBean;
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
/* JADX INFO: compiled from: EasternBColorPickHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class EasternBColorPickHandler extends ColorPickHandler {
    public final ClockBean currentClockBean;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EasternBColorPickHandler(BaseClockView templateView, ClockBean clockBean, ClockBean foreClockBean) {
        super(templateView, clockBean, foreClockBean);
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        Intrinsics.checkNotNullParameter(clockBean, "clockBean");
        Intrinsics.checkNotNullParameter(foreClockBean, "foreClockBean");
        this.currentClockBean = clockBean;
    }

    @Override // com.miui.keyguard.editor.edit.color.handler.ColorPickHandler
    public void handleSecondaryColor(ColorSelectBean data) {
        Intrinsics.checkNotNullParameter(data, "data");
        getClockBean().setSecondaryColor(data.getSelectedColor());
        getClockBean().setSecondaryBlendColor(data.getSelectedColor());
        getClockBean().setAutoSecondaryColor(data.isAutoPickColor());
        ClockBean foreClockBean = getForeClockBean();
        if (foreClockBean != null) {
            foreClockBean.setSecondaryColor(data.getSelectedColor());
        }
        ClockBean foreClockBean2 = getForeClockBean();
        if (foreClockBean2 != null) {
            foreClockBean2.setSecondaryBlendColor(data.getSelectedColor());
        }
        ClockBean foreClockBean3 = getForeClockBean();
        if (foreClockBean3 != null) {
            foreClockBean3.setAutoSecondaryColor(data.isAutoPickColor());
        }
    }
}
