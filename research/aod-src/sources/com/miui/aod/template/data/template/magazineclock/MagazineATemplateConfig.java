package com.miui.aod.template.data.template.magazineclock;

import android.content.Context;
import com.miui.aod.R;
import com.miui.aod.template.FullAodTemplateView;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.edit.color.picker.AutoColorPicker;
import com.miui.keyguard.editor.edit.color.picker.MagazineABAutoColorPicker;
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
/* JADX INFO: compiled from: MagazineATemplateConfig.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class MagazineATemplateConfig extends MagazineSignatureTemplateConfig {
    public TemplateConfig currentConfig;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MagazineATemplateConfig(TemplateConfig config, Context context, FullAodTemplateView templateView) {
        super(config, context, templateView);
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public int backContentLayerId() {
        return R.layout.full_aod_magazine_sign_clock_back_content_layer;
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public AutoColorPicker createAutoColorPicker() {
        return new MagazineABAutoColorPicker(getContext(), getClockStyleType(), getCurrentClockBean(), getCurrentSignatureInfo());
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public int foreContentLayerId() {
        return R.layout.full_aod_pre_template_view;
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public String getClockStyleType() {
        return "magazine_a";
    }

    @Override // com.miui.aod.template.data.template.magazineclock.MagazineSignatureTemplateConfig, com.miui.aod.template.data.template.base.EffectsTemplateConfig, com.miui.aod.template.data.template.base.BaseTemplateConfig
    public void initTemplateBean(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        super.initTemplateBean(templateConfig);
        this.currentConfig = templateConfig;
        getCurrentClockBean().setStyle(templateConfig.getClockInfo().getStyle());
    }
}
