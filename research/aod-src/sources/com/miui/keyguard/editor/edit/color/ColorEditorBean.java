package com.miui.keyguard.editor.edit.color;

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
/* JADX INFO: compiled from: ColorBean.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ColorEditorBean {
    public final boolean anchorViewEnabled;
    public final ColorData colorData;
    public final boolean isAutoPrimaryColor;
    public final boolean isAutoSecondaryColor;
    public final int primaryColorLabel;
    public final boolean reverse;
    public final boolean secondaryColorEnable;
    public final int secondaryColorLabel;
    public final Integer secondaryDisableTipRes;
    public final int selectedPrimaryColor;
    public final int selectedSecondaryColor;
    public final Integer switchLabel;
    public final boolean switchOpen;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ColorEditorBean)) {
            return false;
        }
        ColorEditorBean colorEditorBean = (ColorEditorBean) obj;
        return Intrinsics.areEqual(this.colorData, colorEditorBean.colorData) && this.selectedPrimaryColor == colorEditorBean.selectedPrimaryColor && this.selectedSecondaryColor == colorEditorBean.selectedSecondaryColor && this.isAutoPrimaryColor == colorEditorBean.isAutoPrimaryColor && this.isAutoSecondaryColor == colorEditorBean.isAutoSecondaryColor && this.primaryColorLabel == colorEditorBean.primaryColorLabel && this.secondaryColorLabel == colorEditorBean.secondaryColorLabel && Intrinsics.areEqual(this.switchLabel, colorEditorBean.switchLabel) && this.switchOpen == colorEditorBean.switchOpen && this.reverse == colorEditorBean.reverse && this.secondaryColorEnable == colorEditorBean.secondaryColorEnable && Intrinsics.areEqual(this.secondaryDisableTipRes, colorEditorBean.secondaryDisableTipRes) && this.anchorViewEnabled == colorEditorBean.anchorViewEnabled;
    }

    public int hashCode() {
        int iHashCode = ((((((((((((this.colorData.hashCode() * 31) + Integer.hashCode(this.selectedPrimaryColor)) * 31) + Integer.hashCode(this.selectedSecondaryColor)) * 31) + Boolean.hashCode(this.isAutoPrimaryColor)) * 31) + Boolean.hashCode(this.isAutoSecondaryColor)) * 31) + Integer.hashCode(this.primaryColorLabel)) * 31) + Integer.hashCode(this.secondaryColorLabel)) * 31;
        Integer num = this.switchLabel;
        int iHashCode2 = (((((((iHashCode + (num == null ? 0 : num.hashCode())) * 31) + Boolean.hashCode(this.switchOpen)) * 31) + Boolean.hashCode(this.reverse)) * 31) + Boolean.hashCode(this.secondaryColorEnable)) * 31;
        Integer num2 = this.secondaryDisableTipRes;
        return ((iHashCode2 + (num2 != null ? num2.hashCode() : 0)) * 31) + Boolean.hashCode(this.anchorViewEnabled);
    }

    public String toString() {
        return "ColorEditorBean(colorData=" + this.colorData + ", selectedPrimaryColor=" + this.selectedPrimaryColor + ", selectedSecondaryColor=" + this.selectedSecondaryColor + ", isAutoPrimaryColor=" + this.isAutoPrimaryColor + ", isAutoSecondaryColor=" + this.isAutoSecondaryColor + ", primaryColorLabel=" + this.primaryColorLabel + ", secondaryColorLabel=" + this.secondaryColorLabel + ", switchLabel=" + this.switchLabel + ", switchOpen=" + this.switchOpen + ", reverse=" + this.reverse + ", secondaryColorEnable=" + this.secondaryColorEnable + ", secondaryDisableTipRes=" + this.secondaryDisableTipRes + ", anchorViewEnabled=" + this.anchorViewEnabled + ")";
    }
}
