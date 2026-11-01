package com.miui.aod.template.data.template.classicclock;

import android.content.Context;
import com.miui.aod.template.FullAodTemplateView;
import com.miui.aod.template.data.template.base.EffectsTemplateConfig;
import com.miui.clock.MiuiClockView;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.edit.color.ColorData;
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
/* JADX INFO: compiled from: AbstractClassicTemplateConfig.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public abstract class AbstractClassicTemplateConfig extends EffectsTemplateConfig {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractClassicTemplateConfig(TemplateConfig config, Context context, FullAodTemplateView templateView) {
        super(config, context, templateView);
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
    }

    @Override // com.miui.aod.template.data.template.base.EffectsTemplateConfig, com.miui.aod.template.data.template.base.BaseTemplateConfig
    public void initTemplateBean(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        super.initTemplateBean(templateConfig);
        getCurrentClockBean().setClockWeight(templateConfig.getClockInfo().getClockWeight());
        getCurrentClockBean().setStyle(templateConfig.getClockInfo().getStyle());
        getCurrentClockBean().setPrimaryColor(templateConfig.getClockInfo().getPrimaryColor());
        getCurrentClockBean().setClassicLine1(templateConfig.getClockInfo().getClassicLine1());
        getCurrentClockBean().setClassicLine2(templateConfig.getClockInfo().getClassicLine2());
        getCurrentClockBean().setClassicLine3(templateConfig.getClockInfo().getClassicLine3());
        getCurrentClockBean().setClassicLine5(templateConfig.getClockInfo().getClassicLine5());
        getCurrentClockBean().setClassicSignature(templateConfig.getClockInfo().getClassicSignature());
        getCurrentClockBean().setAutoPrimaryColor(templateConfig.getClockInfo().isAutoPrimaryColor());
        getCurrentClockBean().setSecondaryColor(templateConfig.getClockInfo().getSecondaryColor());
        getCurrentClockBean().setAutoSecondaryColor(templateConfig.getClockInfo().isAutoSecondaryColor());
        getCurrentClockBean().setBlendColor(templateConfig.getClockInfo().getBlendColor());
        getCurrentClockBean().setSecondaryBlendColor(templateConfig.getClockInfo().getSecondaryBlendColor());
        getCurrentClockBean().setClockEffect(templateConfig.getClockInfo().getClockEffect());
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public void onMiuiClockViewCreated() {
        super.onMiuiClockViewCreated();
        if (getSuccessUpdateClockView()) {
            return;
        }
        completeWithCacheColor(false);
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public void updateClockColor(ColorData colorData) {
        Intrinsics.checkNotNullParameter(colorData, "colorData");
        MiuiClockView miuiClockView = getMiuiClockView();
        if (miuiClockView != null) {
            miuiClockView.setClockPalette(0, colorData.getDarkClockArea(), colorData.getClockPalette());
        }
        setSuccessUpdateClockView(getMiuiClockView() != null);
    }
}
