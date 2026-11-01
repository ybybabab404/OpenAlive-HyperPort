package com.miui.keyguard.editor.edit.color.handler;

import com.miui.clock.module.ClockBean;
import com.miui.clock.module.Effect;
import com.miui.keyguard.editor.edit.color.ColorSelectBean;
import com.miui.keyguard.editor.edit.magazineclock.MagazineCTemplateView;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: MagazineCColorPickerHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class MagazineCColorPickerHandler extends ColorPickHandler {
    public final ClockBean currentClockBean;
    public final ClockBean currentDateClockBean;
    public final MagazineCTemplateView magazineCTemplateView;

    /* JADX INFO: compiled from: MagazineCColorPickerHandler.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ColorEditorStyle.values().length];
            try {
                iArr[ColorEditorStyle.SINGLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ColorEditorStyle.DUAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MagazineCColorPickerHandler(MagazineCTemplateView magazineCTemplateView, ClockBean currentClockBean, ClockBean currentDateClockBean) {
        super(magazineCTemplateView, currentClockBean, null, 4, null);
        Intrinsics.checkNotNullParameter(magazineCTemplateView, "magazineCTemplateView");
        Intrinsics.checkNotNullParameter(currentClockBean, "currentClockBean");
        Intrinsics.checkNotNullParameter(currentDateClockBean, "currentDateClockBean");
        this.magazineCTemplateView = magazineCTemplateView;
        this.currentClockBean = currentClockBean;
        this.currentDateClockBean = currentDateClockBean;
    }

    public ColorEditorStyle getColorEditorStyle() {
        return (this.currentClockBean.getStyle() == 6 || this.currentClockBean.getStyle() == 9) ? ColorEditorStyle.SINGLE : ColorEditorStyle.DUAL;
    }

    @Override // com.miui.keyguard.editor.edit.color.handler.ColorPickHandler
    public void onColorSelected(ColorSelectBean data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.currentClockBean.setClockEffect(data.getFontFilterId());
        this.currentDateClockBean.setClockEffect(data.getFontFilterId());
        updateColorId(data);
        int i = WhenMappings.$EnumSwitchMapping$0[getColorEditorStyle().ordinal()];
        if (i == 1) {
            int fontFilterId = data.getFontFilterId();
            if (fontFilterId == 0) {
                this.currentClockBean.setPrimaryColor(data.getSelectedColor());
                this.currentClockBean.setAutoPrimaryColor(data.isAutoPickColor());
                this.currentDateClockBean.setPrimaryColor(data.getSelectedColor());
                this.currentDateClockBean.setAutoPrimaryColor(data.isAutoPickColor());
            } else if (fontFilterId != 3) {
                this.currentClockBean.setPrimaryColor(data.getSelectedColor());
                this.currentClockBean.setBlendColor(data.getSelectedColor());
                this.currentClockBean.setAutoPrimaryColor(data.isAutoPickColor());
                this.currentDateClockBean.setBlendColor(data.getSelectedColor());
                this.currentDateClockBean.setPrimaryColor(data.getSelectedColor());
                this.currentDateClockBean.setAutoPrimaryColor(data.isAutoPickColor());
            } else {
                Effect.Glow glowParam = Effect.GlowEffect.getGlowParam(data.getSelectedColor());
                Intrinsics.checkNotNullExpressionValue(glowParam, "getGlowParam(...)");
                this.currentClockBean.setPrimaryColor(glowParam.srcColor);
                this.currentClockBean.setBlendColor(data.getSelectedColor());
                this.currentClockBean.setAutoPrimaryColor(false);
                this.currentDateClockBean.setPrimaryColor(glowParam.srcColor);
                this.currentDateClockBean.setBlendColor(data.getSelectedColor());
                this.currentDateClockBean.setAutoPrimaryColor(false);
            }
        } else {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            if (data.isPrimaryColor()) {
                int fontFilterId2 = data.getFontFilterId();
                if (fontFilterId2 == 0) {
                    this.currentClockBean.setPrimaryColor(data.getSelectedColor());
                    this.currentClockBean.setAutoPrimaryColor(data.isAutoPickColor());
                    this.currentDateClockBean.setPrimaryColor(data.getSelectedColor());
                    this.currentDateClockBean.setAutoPrimaryColor(data.isAutoPickColor());
                } else if (fontFilterId2 != 3) {
                    this.currentClockBean.setPrimaryColor(data.getSelectedColor());
                    this.currentClockBean.setBlendColor(data.getSelectedColor());
                    this.currentClockBean.setAutoPrimaryColor(data.isAutoPickColor());
                    this.currentDateClockBean.setPrimaryColor(data.getSelectedColor());
                    this.currentDateClockBean.setBlendColor(data.getSelectedColor());
                    this.currentDateClockBean.setAutoPrimaryColor(data.isAutoPickColor());
                } else {
                    Effect.Glow glowParam2 = Effect.GlowEffect.getGlowParam(data.getSelectedColor());
                    Intrinsics.checkNotNullExpressionValue(glowParam2, "getGlowParam(...)");
                    this.currentClockBean.setPrimaryColor(glowParam2.srcColor);
                    this.currentClockBean.setBlendColor(data.getSelectedColor());
                    this.currentClockBean.setAutoPrimaryColor(false);
                    this.currentDateClockBean.setPrimaryColor(glowParam2.srcColor);
                    this.currentDateClockBean.setBlendColor(data.getSelectedColor());
                    this.currentDateClockBean.setAutoPrimaryColor(false);
                }
            } else {
                int fontFilterId3 = data.getFontFilterId();
                if (fontFilterId3 == 0) {
                    this.currentClockBean.setSecondaryColor(data.getSelectedColor());
                    this.currentClockBean.setAutoSecondaryColor(data.isAutoPickColor());
                    this.currentDateClockBean.setSecondaryColor(data.getSelectedColor());
                    this.currentDateClockBean.setAutoSecondaryColor(data.isAutoPickColor());
                } else if (fontFilterId3 != 3) {
                    this.currentClockBean.setSecondaryColor(data.getSelectedColor());
                    this.currentClockBean.setSecondaryBlendColor(data.getSelectedColor());
                    this.currentClockBean.setAutoSecondaryColor(data.isAutoPickColor());
                    this.currentDateClockBean.setSecondaryColor(data.getSelectedColor());
                    this.currentDateClockBean.setSecondaryBlendColor(data.getSelectedColor());
                    this.currentDateClockBean.setAutoSecondaryColor(data.isAutoPickColor());
                } else {
                    Effect.Glow glowParam3 = Effect.GlowEffect.getGlowParam(data.getSelectedColor());
                    Intrinsics.checkNotNullExpressionValue(glowParam3, "getGlowParam(...)");
                    this.currentClockBean.setSecondaryColor(glowParam3.srcColor);
                    this.currentClockBean.setSecondaryBlendColor(data.getSelectedColor());
                    this.currentClockBean.setAutoSecondaryColor(false);
                    this.currentDateClockBean.setSecondaryColor(glowParam3.srcColor);
                    this.currentDateClockBean.setSecondaryBlendColor(data.getSelectedColor());
                    this.currentDateClockBean.setAutoSecondaryColor(false);
                }
            }
        }
        this.magazineCTemplateView.onClockInfoUpdate();
    }
}
