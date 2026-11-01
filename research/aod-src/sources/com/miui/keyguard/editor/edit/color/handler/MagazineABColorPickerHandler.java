package com.miui.keyguard.editor.edit.color.handler;

import com.miui.clock.module.ClockBean;
import com.miui.clock.module.Effect;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.edit.color.ColorSelectBean;
import com.miui.keyguard.editor.edit.magazineclock.MagazineSignatureTemplateView;
import com.miui.lockscreeninfo.model.SignatureInfo;
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
/* JADX INFO: compiled from: MagazineABColorPickerHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class MagazineABColorPickerHandler extends ColorPickHandler {
    public final ClockBean currentClockBean;
    public final SignatureInfo currentSignatureInfo;
    public final MagazineSignatureTemplateView magazineTemplateView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MagazineABColorPickerHandler(MagazineSignatureTemplateView magazineTemplateView, ClockBean currentClockBean, SignatureInfo currentSignatureInfo) {
        super(magazineTemplateView, currentClockBean, null, 4, null);
        Intrinsics.checkNotNullParameter(magazineTemplateView, "magazineTemplateView");
        Intrinsics.checkNotNullParameter(currentClockBean, "currentClockBean");
        Intrinsics.checkNotNullParameter(currentSignatureInfo, "currentSignatureInfo");
        this.magazineTemplateView = magazineTemplateView;
        this.currentClockBean = currentClockBean;
        this.currentSignatureInfo = currentSignatureInfo;
    }

    @Override // com.miui.keyguard.editor.edit.color.handler.ColorPickHandler
    public void onColorSelected(ColorSelectBean data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.currentClockBean.setClockEffect(data.getFontFilterId());
        this.currentSignatureInfo.setClockEffect(data.getFontFilterId());
        updateColorId(data);
        if (data.isPrimaryColor()) {
            int fontFilterId = data.getFontFilterId();
            if (fontFilterId == 0) {
                this.currentClockBean.setPrimaryColor(data.getSelectedColor());
                this.currentClockBean.setAutoPrimaryColor(data.isAutoPickColor());
            } else if (fontFilterId != 3) {
                this.currentClockBean.setPrimaryColor(data.getSelectedColor());
                this.currentClockBean.setBlendColor(data.getSelectedColor());
                this.currentClockBean.setAutoPrimaryColor(data.isAutoPickColor());
            } else {
                Effect.Glow glowParam = Effect.GlowEffect.getGlowParam(data.getSelectedColor());
                Intrinsics.checkNotNullExpressionValue(glowParam, "getGlowParam(...)");
                this.currentClockBean.setPrimaryColor(glowParam.srcColor);
                this.currentClockBean.setBlendColor(data.getSelectedColor());
                this.currentClockBean.setAutoPrimaryColor(false);
            }
        } else {
            int fontFilterId2 = data.getFontFilterId();
            if (fontFilterId2 == 0) {
                this.currentSignatureInfo.setPrimaryColor(data.getSelectedColor());
                this.currentSignatureInfo.setAutoPrimaryColor(data.isAutoPickColor());
            } else if (fontFilterId2 != 3) {
                this.currentSignatureInfo.setPrimaryColor(data.getSelectedColor());
                this.currentSignatureInfo.setBlendColor(data.getSelectedColor());
                this.currentSignatureInfo.setAutoPrimaryColor(data.isAutoPickColor());
            } else {
                Effect.Glow glowParam2 = Effect.GlowEffect.getGlowParam(data.getSelectedColor());
                Intrinsics.checkNotNullExpressionValue(glowParam2, "getGlowParam(...)");
                this.currentSignatureInfo.setPrimaryColor(glowParam2.srcColor);
                this.currentSignatureInfo.setBlendColor(data.getSelectedColor());
                this.currentSignatureInfo.setAutoPrimaryColor(false);
            }
        }
        this.magazineTemplateView.onSignatureInfoUpdate();
        this.magazineTemplateView.onClockInfoUpdate();
    }

    @Override // com.miui.keyguard.editor.edit.color.handler.ColorPickHandler
    public void updateColorId(ColorSelectBean data) {
        com.miui.keyguard.editor.data.bean.SignatureInfo signatureInfo;
        ClockInfo clockInfo;
        Intrinsics.checkNotNullParameter(data, "data");
        if (data.isPrimaryColor()) {
            TemplateConfig templateConfig = getTemplateView().templateConfig();
            if (templateConfig == null || (clockInfo = templateConfig.getClockInfo()) == null) {
                return;
            }
            clockInfo.setPrimaryFontColorPanelId(data.getColorId());
            return;
        }
        TemplateConfig templateConfig2 = getTemplateView().templateConfig();
        if (templateConfig2 == null || (signatureInfo = templateConfig2.getSignatureInfo()) == null) {
            return;
        }
        signatureInfo.setPrimaryFontColorPanelId(data.getColorId());
    }
}
