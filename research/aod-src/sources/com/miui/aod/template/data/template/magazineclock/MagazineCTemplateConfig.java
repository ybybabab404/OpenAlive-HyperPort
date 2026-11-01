package com.miui.aod.template.data.template.magazineclock;

import android.content.Context;
import com.miui.aod.R;
import com.miui.aod.template.FullAodTemplateView;
import com.miui.aod.template.data.template.base.EffectsTemplateConfig;
import com.miui.clock.MiuiClockView;
import com.miui.clock.module.ClockBean;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.color.picker.AutoColorPicker;
import com.miui.keyguard.editor.edit.color.picker.MagazineCAutoColorPicker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: MagazineCTemplateConfig.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class MagazineCTemplateConfig extends EffectsTemplateConfig {
    public final ClockBean currentDateClockBean;
    public MiuiClockView dateView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MagazineCTemplateConfig(TemplateConfig config, Context context, FullAodTemplateView templateView) {
        super(config, context, templateView);
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        this.currentDateClockBean = new ClockBean("magazine_c_date_only");
    }

    private final void updateDateViewVisible() {
        MiuiClockView miuiClockView = this.dateView;
        if (miuiClockView != null) {
            miuiClockView.setVisibility(getCurrentClockBean().getStyle() != 5 ? 0 : 8);
        }
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public int backContentLayerId() {
        return R.layout.full_aod_pre_template_view;
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public AutoColorPicker createAutoColorPicker() {
        return new MagazineCAutoColorPicker(getContext(), getClockStyleType(), getCurrentClockBean());
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public int foreContentLayerId() {
        return R.layout.full_aod_pre_force_clock;
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public String getClockStyleType() {
        return "magazine_c";
    }

    @Override // com.miui.aod.template.data.template.base.EffectsTemplateConfig, com.miui.aod.template.data.template.base.BaseTemplateConfig
    public void initTemplateBean(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        super.initTemplateBean(templateConfig);
        ClockInfo clockInfo = templateConfig.getClockInfo();
        getCurrentClockBean().setStyle(clockInfo.getStyle());
        getCurrentClockBean().setPrimaryColor(clockInfo.getPrimaryColor());
        getCurrentClockBean().setAutoPrimaryColor(clockInfo.isAutoPrimaryColor());
        getCurrentClockBean().setSecondaryColor(clockInfo.getSecondaryColor());
        getCurrentClockBean().setAutoSecondaryColor(clockInfo.isAutoSecondaryColor());
        getCurrentClockBean().setBlendColor(clockInfo.getBlendColor());
        getCurrentClockBean().setSecondaryBlendColor(clockInfo.getSecondaryBlendColor());
        getCurrentClockBean().setClockEffect(clockInfo.getClockEffect());
        this.currentDateClockBean.setPrimaryColor(clockInfo.getPrimaryColor());
        this.currentDateClockBean.setAutoPrimaryColor(clockInfo.isAutoPrimaryColor());
        this.currentDateClockBean.setBlendColor(clockInfo.getBlendColor());
        this.currentDateClockBean.setClockEffect(clockInfo.getClockEffect());
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public void onMiuiClockViewCreated() {
        super.onMiuiClockViewCreated();
        MiuiClockView miuiClockView = (MiuiClockView) getTemplateView().findViewById(R.id.fore_clock);
        Intrinsics.checkNotNull(miuiClockView);
        miuiClockView.setVisibility(!miuiClockView.isDualClock() ? 0 : 8);
        this.dateView = miuiClockView;
        if (!isDualClock()) {
            if (getTemplateConfig() != null) {
                MiuiClockView miuiClockView2 = this.dateView;
                if (miuiClockView2 != null) {
                    miuiClockView2.init(this.currentDateClockBean, false);
                }
                updateDateViewVisible();
            }
            MiuiClockView miuiClockView3 = this.dateView;
            if (miuiClockView3 != null) {
                miuiClockView3.setBackgroundBlurContainer(getBackGroundView());
            }
        }
        if (getSuccessUpdateClockView()) {
            return;
        }
        completeWithCacheColor(false);
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public void updateClockColor(ColorData colorData) {
        Intrinsics.checkNotNullParameter(colorData, "colorData");
        MiuiClockView miuiClockView = getMiuiClockView();
        boolean z = false;
        if (miuiClockView != null) {
            miuiClockView.setClockPalette(0, colorData.getDarkClockArea(), colorData.getClockPalette());
        }
        MiuiClockView miuiClockView2 = this.dateView;
        if (miuiClockView2 != null) {
            miuiClockView2.setClockPalette(0, colorData.getDarkClockArea(), colorData.getClockPalette());
        }
        if (getMiuiClockView() != null && this.dateView != null) {
            z = true;
        }
        setSuccessUpdateClockView(z);
    }
}
