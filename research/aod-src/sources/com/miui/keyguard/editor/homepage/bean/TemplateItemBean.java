package com.miui.keyguard.editor.homepage.bean;

import android.graphics.Bitmap;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.PresetTemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
/* JADX INFO: compiled from: TemplateItemBean.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class TemplateItemBean {
    public CommonConfig current;
    public Integer descriptionRes;
    public final int floor;
    public TemplateHistoryConfig history;
    public boolean isAutoDownload;
    public boolean isGuessSelect;
    public boolean isSelected;
    public boolean isThirdPartyDarkMode;
    public int itemType;
    public PresetTemplateConfig preset;
    public Bitmap thirdPartyThemeHomePreview;
    public Bitmap thirdPartyThemePreview;
    public Integer titleRes;

    public TemplateItemBean(int i, int i2, Integer num, Integer num2, TemplateHistoryConfig templateHistoryConfig, PresetTemplateConfig presetTemplateConfig, CommonConfig commonConfig, Bitmap bitmap, Bitmap bitmap2, boolean z, boolean z2, boolean z3, boolean z4) {
        this.itemType = i;
        this.floor = i2;
        this.titleRes = num;
        this.descriptionRes = num2;
        this.history = templateHistoryConfig;
        this.preset = presetTemplateConfig;
        this.current = commonConfig;
        this.thirdPartyThemePreview = bitmap;
        this.thirdPartyThemeHomePreview = bitmap2;
        this.isThirdPartyDarkMode = z;
        this.isSelected = z2;
        this.isGuessSelect = z3;
        this.isAutoDownload = z4;
    }

    public /* synthetic */ TemplateItemBean(int i, int i2, Integer num, Integer num2, TemplateHistoryConfig templateHistoryConfig, PresetTemplateConfig presetTemplateConfig, CommonConfig commonConfig, Bitmap bitmap, Bitmap bitmap2, boolean z, boolean z2, boolean z3, boolean z4, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, (i3 & 4) != 0 ? null : num, (i3 & 8) != 0 ? null : num2, (i3 & 16) != 0 ? null : templateHistoryConfig, (i3 & 32) != 0 ? null : presetTemplateConfig, (i3 & 64) != 0 ? null : commonConfig, (i3 & 128) != 0 ? null : bitmap, (i3 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? null : bitmap2, (i3 & 512) != 0 ? false : z, (i3 & 1024) != 0 ? false : z2, (i3 & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0 ? false : z3, (i3 & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0 ? false : z4);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TemplateItemBean)) {
            return false;
        }
        TemplateItemBean templateItemBean = (TemplateItemBean) obj;
        return this.itemType == templateItemBean.itemType && this.floor == templateItemBean.floor && Intrinsics.areEqual(this.titleRes, templateItemBean.titleRes) && Intrinsics.areEqual(this.descriptionRes, templateItemBean.descriptionRes) && Intrinsics.areEqual(this.history, templateItemBean.history) && Intrinsics.areEqual(this.preset, templateItemBean.preset) && Intrinsics.areEqual(this.current, templateItemBean.current) && Intrinsics.areEqual(this.thirdPartyThemePreview, templateItemBean.thirdPartyThemePreview) && Intrinsics.areEqual(this.thirdPartyThemeHomePreview, templateItemBean.thirdPartyThemeHomePreview) && this.isThirdPartyDarkMode == templateItemBean.isThirdPartyDarkMode && this.isSelected == templateItemBean.isSelected && this.isGuessSelect == templateItemBean.isGuessSelect && this.isAutoDownload == templateItemBean.isAutoDownload;
    }

    public final CommonConfig getCurrent() {
        return this.current;
    }

    public final Integer getDescriptionRes() {
        return this.descriptionRes;
    }

    public final TemplateHistoryConfig getHistory() {
        return this.history;
    }

    public final int getItemType() {
        return this.itemType;
    }

    public final PresetTemplateConfig getPreset() {
        return this.preset;
    }

    public final Bitmap getThirdPartyThemeHomePreview() {
        return this.thirdPartyThemeHomePreview;
    }

    public final Bitmap getThirdPartyThemePreview() {
        return this.thirdPartyThemePreview;
    }

    public final Integer getTitleRes() {
        return this.titleRes;
    }

    public int hashCode() {
        int iHashCode = ((Integer.hashCode(this.itemType) * 31) + Integer.hashCode(this.floor)) * 31;
        Integer num = this.titleRes;
        int iHashCode2 = (iHashCode + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.descriptionRes;
        int iHashCode3 = (iHashCode2 + (num2 == null ? 0 : num2.hashCode())) * 31;
        TemplateHistoryConfig templateHistoryConfig = this.history;
        int iHashCode4 = (iHashCode3 + (templateHistoryConfig == null ? 0 : templateHistoryConfig.hashCode())) * 31;
        PresetTemplateConfig presetTemplateConfig = this.preset;
        int iHashCode5 = (iHashCode4 + (presetTemplateConfig == null ? 0 : presetTemplateConfig.hashCode())) * 31;
        CommonConfig commonConfig = this.current;
        int iHashCode6 = (iHashCode5 + (commonConfig == null ? 0 : commonConfig.hashCode())) * 31;
        Bitmap bitmap = this.thirdPartyThemePreview;
        int iHashCode7 = (iHashCode6 + (bitmap == null ? 0 : bitmap.hashCode())) * 31;
        Bitmap bitmap2 = this.thirdPartyThemeHomePreview;
        return ((((((((iHashCode7 + (bitmap2 != null ? bitmap2.hashCode() : 0)) * 31) + Boolean.hashCode(this.isThirdPartyDarkMode)) * 31) + Boolean.hashCode(this.isSelected)) * 31) + Boolean.hashCode(this.isGuessSelect)) * 31) + Boolean.hashCode(this.isAutoDownload);
    }

    public final boolean isAutoDownload() {
        return this.isAutoDownload;
    }

    public final boolean isSelected() {
        return this.isSelected;
    }

    public final boolean isThirdPartyDarkMode() {
        return this.isThirdPartyDarkMode;
    }

    public final void setAutoDownload(boolean z) {
        this.isAutoDownload = z;
    }

    public final void setCurrent(CommonConfig commonConfig) {
        this.current = commonConfig;
    }

    public final void setSelected(boolean z) {
        this.isSelected = z;
    }

    public final void setThirdPartyThemePreview(Bitmap bitmap) {
        this.thirdPartyThemePreview = bitmap;
    }

    public String toString() {
        return "TemplateItemBean(itemType=" + this.itemType + ", floor=" + this.floor + ", titleRes=" + this.titleRes + ", descriptionRes=" + this.descriptionRes + ", history=" + this.history + ", preset=" + this.preset + ", current=" + this.current + ", thirdPartyThemePreview=" + this.thirdPartyThemePreview + ", thirdPartyThemeHomePreview=" + this.thirdPartyThemeHomePreview + ", isThirdPartyDarkMode=" + this.isThirdPartyDarkMode + ", isSelected=" + this.isSelected + ", isGuessSelect=" + this.isGuessSelect + ", isAutoDownload=" + this.isAutoDownload + ")";
    }
}
