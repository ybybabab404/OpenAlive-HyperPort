package com.miui.aod.template.data.template.classicclock;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import com.miui.aod.R;
import com.miui.aod.template.FullAodTemplateView;
import com.miui.clock.MiuiClockView;
import com.miui.clock.MiuiTextGlassView;
import com.miui.clock.classic.ClassicContentAreaView;
import com.miui.clock.classic.ClassicTextAreaView;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockViewType;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.color.picker.AutoColorPicker;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
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
/* JADX INFO: compiled from: ClassicTemplateConfig.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class ClassicTemplateConfig extends AbstractClassicTemplateConfig {
    public ClassicContentAreaView contentAreaView1;
    public ClassicContentAreaView contentAreaView2;
    public final ClockBean foreClockBean;
    public MiuiClockView foreMiuiClockView;
    public View line3;
    public View line4;
    public EditText signatureAreaView;
    public ClassicTextAreaView textAreaView;
    public MiuiTextGlassView timeAreaView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassicTemplateConfig(TemplateConfig config, Context context, FullAodTemplateView templateView) {
        super(config, context, templateView);
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        this.foreClockBean = new ClockBean("classic_signature");
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
        return "classic";
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public void initForeClock(FrameLayout templateView) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        this.foreMiuiClockView = (MiuiClockView) templateView.findViewById(R.id.fore_clock);
    }

    @Override // com.miui.aod.template.data.template.classicclock.AbstractClassicTemplateConfig, com.miui.aod.template.data.template.base.EffectsTemplateConfig, com.miui.aod.template.data.template.base.BaseTemplateConfig
    public void initTemplateBean(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        super.initTemplateBean(templateConfig);
        ClockInfo clockInfo = templateConfig.getClockInfo();
        this.foreClockBean.setClassicLine5(clockInfo.getClassicLine5());
        this.foreClockBean.setClassicSignature(clockInfo.getClassicSignature());
        this.foreClockBean.setPrimaryColor(clockInfo.getPrimaryColor());
        this.foreClockBean.setAutoPrimaryColor(clockInfo.isAutoPrimaryColor());
        this.foreClockBean.setSecondaryColor(clockInfo.getSecondaryColor());
        this.foreClockBean.setAutoSecondaryColor(clockInfo.isAutoSecondaryColor());
        this.foreClockBean.setBlendColor(clockInfo.getBlendColor());
        this.foreClockBean.setSecondaryBlendColor(clockInfo.getSecondaryBlendColor());
        this.foreClockBean.setClockEffect(clockInfo.getClockEffect());
        getCurrentClockBean().setClassicLine3(templateConfig.getClockInfo().getClassicLine3());
        getCurrentClockBean().setClassicLine4(templateConfig.getClockInfo().getClassicLine4());
        getCurrentClockBean().setClassicLine5(12);
        getCurrentClockBean().setClassicSignature(templateConfig.getClockInfo().getClassicSignature());
        Bundle bundle = this.extraParameters;
        if (bundle == null) {
            return;
        }
        ClockBean currentClockBean = getCurrentClockBean();
        String string = bundle.getString("health");
        String str = null;
        if (string != null) {
            Log.i("ClassicTemplateView", "health preseted");
        } else {
            string = null;
        }
        currentClockBean.setPresetHealthJson(string);
        ClockBean currentClockBean2 = getCurrentClockBean();
        String string2 = bundle.getString("weather");
        if (string2 != null) {
            Log.i("ClassicTemplateView", "weather preseted");
            str = string2;
        }
        currentClockBean2.setPresetWeatherJson(str);
        if (getCurrentClockBean().getClassicLine1() != 11 || DeviceUtil.INSTANCE.isDataCapable(getContext())) {
            return;
        }
        getCurrentClockBean().setClassicLine1(0);
    }

    @Override // com.miui.aod.template.data.template.classicclock.AbstractClassicTemplateConfig, com.miui.aod.template.data.template.base.BaseTemplateConfig
    public void onMiuiClockViewCreated() {
        MiuiClockView miuiClockView;
        super.onMiuiClockViewCreated();
        MiuiClockView miuiClockView2 = getMiuiClockView();
        View iClockView = miuiClockView2 != null ? miuiClockView2.getIClockView(ClockViewType.TEXT_AREA) : null;
        this.textAreaView = iClockView instanceof ClassicTextAreaView ? (ClassicTextAreaView) iClockView : null;
        MiuiClockView miuiClockView3 = getMiuiClockView();
        View iClockView2 = miuiClockView3 != null ? miuiClockView3.getIClockView(ClockViewType.TIME_AREA) : null;
        this.timeAreaView = iClockView2 instanceof MiuiTextGlassView ? (MiuiTextGlassView) iClockView2 : null;
        MiuiClockView miuiClockView4 = getMiuiClockView();
        View iClockView3 = miuiClockView4 != null ? miuiClockView4.getIClockView(ClockViewType.CONTENT_AREA1) : null;
        this.contentAreaView1 = iClockView3 instanceof ClassicContentAreaView ? (ClassicContentAreaView) iClockView3 : null;
        MiuiClockView miuiClockView5 = getMiuiClockView();
        View iClockView4 = miuiClockView5 != null ? miuiClockView5.getIClockView(ClockViewType.CONTENT_AREA2) : null;
        this.contentAreaView2 = iClockView4 instanceof ClassicContentAreaView ? (ClassicContentAreaView) iClockView4 : null;
        MiuiClockView miuiClockView6 = getMiuiClockView();
        View iClockView5 = miuiClockView6 != null ? miuiClockView6.getIClockView(ClockViewType.SIGNATURE_AREA) : null;
        this.signatureAreaView = iClockView5 instanceof EditText ? (EditText) iClockView5 : null;
        View clock = getClock();
        this.line3 = clock != null ? clock.findViewById(R.id.line3) : null;
        View clock2 = getClock();
        this.line4 = clock2 != null ? clock2.findViewById(R.id.line4) : null;
        setViewsMargin(this.timeAreaView, this.contentAreaView1, this.contentAreaView2, this.signatureAreaView);
        MiuiClockView miuiClockView7 = this.foreMiuiClockView;
        if (miuiClockView7 != null) {
            miuiClockView7.setVisibility(!miuiClockView7.isDualClock() ? 0 : 8);
        }
        if (isDualClock() || getTemplateConfig() == null || (miuiClockView = this.foreMiuiClockView) == null) {
            return;
        }
        miuiClockView.init(this.foreClockBean, 32, false);
    }

    public final void setViewsMargin(View... viewArr) {
        for (View view : viewArr) {
            if (view != null) {
                ViewUtil.setMarginTop(view, getContext().getResources().getDimensionPixelSize(R.dimen.kg_classic_clock_time_area_extra_margin_top));
            }
        }
    }

    @Override // com.miui.aod.template.data.template.classicclock.AbstractClassicTemplateConfig, com.miui.aod.template.data.template.base.BaseTemplateConfig
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
