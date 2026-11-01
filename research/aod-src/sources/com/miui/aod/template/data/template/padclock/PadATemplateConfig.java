package com.miui.aod.template.data.template.padclock;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.miui.aod.R;
import com.miui.aod.template.FullAodTemplateView;
import com.miui.aod.template.data.template.base.EffectsTemplateConfig;
import com.miui.clock.MiuiClockView;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockViewType;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.color.picker.AutoColorPicker;
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
/* JADX INFO: compiled from: PadATemplateConfig.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class PadATemplateConfig extends EffectsTemplateConfig {
    public final ClockBean foreClockBean;
    public MiuiClockView foreMiuiClockView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PadATemplateConfig(TemplateConfig config, Context context, FullAodTemplateView templateView) {
        super(config, context, templateView);
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        this.foreClockBean = new ClockBean("pad_exclusive_a_signature");
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public int backContentLayerId() {
        return R.layout.full_aod_pre_template_view;
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public AutoColorPicker createAutoColorPicker() {
        return new AutoColorPicker(getContext(), getClockStyleType(), getCurrentClockBean());
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public int foreContentLayerId() {
        return R.layout.full_aod_pre_force_clock;
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public String getClockStyleType() {
        return "pad_exclusive_a";
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public void initForeClock(FrameLayout templateView) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        this.foreMiuiClockView = (MiuiClockView) templateView.findViewById(R.id.fore_clock);
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
        this.foreClockBean.setBlendColor(clockInfo.getBlendColor());
        this.foreClockBean.setSecondaryBlendColor(clockInfo.getSecondaryBlendColor());
        this.foreClockBean.setClockEffect(clockInfo.getClockEffect());
        this.foreClockBean.setStyle(clockInfo.getStyle());
        this.foreClockBean.setPrimaryColor(clockInfo.getPrimaryColor());
        this.foreClockBean.setAutoPrimaryColor(clockInfo.isAutoPrimaryColor());
        this.foreClockBean.setSecondaryColor(clockInfo.getSecondaryColor());
        this.foreClockBean.setAutoSecondaryColor(clockInfo.isAutoSecondaryColor());
        this.foreClockBean.setSignatureLine1(clockInfo.getSignatureLine1());
        this.foreClockBean.setSignatureLine2(clockInfo.getSignatureLine2());
        this.foreClockBean.setSignatureLine3(clockInfo.getSignatureLine3());
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public void onMiuiClockViewCreated() {
        View iClockView;
        View iClockView2;
        View iClockView3;
        MiuiClockView miuiClockView;
        super.onMiuiClockViewCreated();
        MiuiClockView miuiClockView2 = this.foreMiuiClockView;
        if (miuiClockView2 != null) {
            miuiClockView2.setVisibility(!miuiClockView2.isDualClock() ? 0 : 8);
        }
        if (!isDualClock()) {
            if (getTemplateConfig() != null && (miuiClockView = this.foreMiuiClockView) != null) {
                miuiClockView.init(this.foreClockBean, 32, false);
            }
            MiuiClockView miuiClockView3 = this.foreMiuiClockView;
            if (miuiClockView3 != null && (iClockView3 = miuiClockView3.getIClockView(ClockViewType.TEXT_AREA)) != null) {
                ((TextView) iClockView3).setText(this.foreClockBean.getSignatureLine1());
            }
            MiuiClockView miuiClockView4 = this.foreMiuiClockView;
            if (miuiClockView4 != null && (iClockView2 = miuiClockView4.getIClockView(ClockViewType.TEXT_AREA2)) != null) {
                ((TextView) iClockView2).setText(this.foreClockBean.getSignatureLine2());
            }
            MiuiClockView miuiClockView5 = this.foreMiuiClockView;
            if (miuiClockView5 != null && (iClockView = miuiClockView5.getIClockView(ClockViewType.TEXT_AREA3)) != null) {
                ((TextView) iClockView).setText(this.foreClockBean.getSignatureLine3());
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
        MiuiClockView miuiClockView2 = this.foreMiuiClockView;
        if (miuiClockView2 != null) {
            miuiClockView2.setClockPalette(0, colorData.getDarkClockArea(), colorData.getClockPalette());
        }
        if (getMiuiClockView() != null && this.foreMiuiClockView != null) {
            z = true;
        }
        setSuccessUpdateClockView(z);
    }
}
