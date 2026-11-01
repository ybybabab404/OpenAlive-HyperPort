package com.miui.aod.template.data.template.magazineclock;

import android.content.Context;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.miui.aod.R;
import com.miui.aod.template.FullAodTemplateView;
import com.miui.aod.template.data.template.base.EffectsTemplateConfig;
import com.miui.clock.MiuiClockView;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.lockscreeninfo.LockScreenInfoLayout;
import com.miui.lockscreeninfo.model.ModelFactory;
import com.miui.lockscreeninfo.model.SignatureInfo;
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
/* JADX INFO: compiled from: MagazineSignatureTemplateConfig.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public abstract class MagazineSignatureTemplateConfig extends EffectsTemplateConfig {
    public SignatureInfo currentSignatureInfo;
    public LockScreenInfoLayout signatureView;
    public boolean updateSignatureSuccess;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MagazineSignatureTemplateConfig(TemplateConfig config, Context context, FullAodTemplateView templateView) {
        super(config, context, templateView);
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        SignatureInfo signatureInfoCreateModel = ModelFactory.createModel(getClockStyleType());
        Intrinsics.checkNotNullExpressionValue(signatureInfoCreateModel, "createModel(...)");
        this.currentSignatureInfo = signatureInfoCreateModel;
    }

    public static final void onMiuiClockViewCreated$lambda$4(MagazineSignatureTemplateConfig magazineSignatureTemplateConfig, MiuiClockView miuiClockView) {
        ViewGroup viewGroup = (ViewGroup) magazineSignatureTemplateConfig.getBackContentLayer();
        if (viewGroup != null) {
            viewGroup.addView(miuiClockView);
        }
    }

    public final SignatureInfo getCurrentSignatureInfo() {
        return this.currentSignatureInfo;
    }

    @Override // com.miui.aod.template.data.template.base.EffectsTemplateConfig, com.miui.aod.template.data.template.base.BaseTemplateConfig
    public void initTemplateBean(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        super.initTemplateBean(templateConfig);
        com.miui.keyguard.editor.data.bean.SignatureInfo signatureInfo = templateConfig.getSignatureInfo();
        if (signatureInfo != null) {
            this.currentSignatureInfo.setContent(signatureInfo.getContent());
            this.currentSignatureInfo.setAlignment(signatureInfo.getAlignment());
            this.currentSignatureInfo.setPrimaryColor(signatureInfo.getPrimaryColor());
            this.currentSignatureInfo.setAutoPrimaryColor(signatureInfo.isAutoPrimaryColor());
            this.currentSignatureInfo.setTextsize(signatureInfo.getTextSize());
            this.currentSignatureInfo.setLetterSpaceValue(signatureInfo.getLetterSpaceValue());
            this.currentSignatureInfo.setLineSpaceValue(signatureInfo.getLineSpaceValue());
            this.currentSignatureInfo.setTemplateFontType(signatureInfo.getTemplateFontType());
            this.currentSignatureInfo.setBlendColor(signatureInfo.getBlendColor());
            this.currentSignatureInfo.setClockEffect(signatureInfo.getClockEffect());
            this.currentSignatureInfo.setDisplayType(32);
        }
        ClockInfo clockInfo = templateConfig.getClockInfo();
        getCurrentClockBean().setStyle(clockInfo.getStyle());
        getCurrentClockBean().setPrimaryColor(clockInfo.getPrimaryColor());
        getCurrentClockBean().setAutoPrimaryColor(clockInfo.isAutoPrimaryColor());
        getCurrentClockBean().setSecondaryColor(clockInfo.getSecondaryColor());
        getCurrentClockBean().setAutoSecondaryColor(clockInfo.isAutoSecondaryColor());
        getCurrentClockBean().setBlendColor(clockInfo.getBlendColor());
        getCurrentClockBean().setSecondaryBlendColor(clockInfo.getSecondaryBlendColor());
        getCurrentClockBean().setClockEffect(clockInfo.getClockEffect());
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public void onMiuiClockViewCreated() {
        LockScreenInfoLayout lockScreenInfoLayout = (LockScreenInfoLayout) getTemplateView().findViewById(R.id.signature_view);
        if (lockScreenInfoLayout == null) {
            lockScreenInfoLayout = null;
        } else if (isDualClock()) {
            lockScreenInfoLayout.setVisibility(8);
        } else if (getTemplateConfig() != null) {
            lockScreenInfoLayout.setModel(this.currentSignatureInfo);
        }
        this.signatureView = lockScreenInfoLayout;
        super.onMiuiClockViewCreated();
        final MiuiClockView miuiClockView = getMiuiClockView();
        if (miuiClockView == null) {
            return;
        }
        if (isDualClock()) {
            ViewParent parent = miuiClockView.getParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type android.view.ViewGroup");
            ((ViewGroup) parent).removeView(miuiClockView);
            getTemplateView().post(new Runnable() { // from class: com.miui.aod.template.data.template.magazineclock.MagazineSignatureTemplateConfig$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MagazineSignatureTemplateConfig.onMiuiClockViewCreated$lambda$4(this.f$0, miuiClockView);
                }
            });
        }
        if (getSuccessUpdateClockView() && this.updateSignatureSuccess) {
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
        LockScreenInfoLayout lockScreenInfoLayout = this.signatureView;
        if (lockScreenInfoLayout != null) {
            lockScreenInfoLayout.setClockPalette(colorData.getDarkSignatureArea(), colorData.getSignaturePalette());
        }
        setSuccessUpdateClockView(getMiuiClockView() != null);
        this.updateSignatureSuccess = this.signatureView != null;
    }
}
