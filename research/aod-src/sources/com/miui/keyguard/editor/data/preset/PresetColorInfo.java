package com.miui.keyguard.editor.data.preset;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: FontFilter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class PresetColorInfo {
    public int clockEffect;
    public int primaryColor;
    public int primaryFontColorPanelId;
    public int secondaryColor;
    public int secondaryFontColorPanelId;

    public PresetColorInfo(int i, int i2, int i3, int i4, int i5) {
        this.clockEffect = i;
        this.primaryColor = i2;
        this.secondaryColor = i3;
        this.primaryFontColorPanelId = i4;
        this.secondaryFontColorPanelId = i5;
    }

    public /* synthetic */ PresetColorInfo(int i, int i2, int i3, int i4, int i5, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this((i6 & 1) != 0 ? 0 : i, (i6 & 2) != 0 ? -1 : i2, (i6 & 4) != 0 ? -1 : i3, (i6 & 8) != 0 ? 0 : i4, (i6 & 16) != 0 ? 0 : i5);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PresetColorInfo)) {
            return false;
        }
        PresetColorInfo presetColorInfo = (PresetColorInfo) obj;
        return this.clockEffect == presetColorInfo.clockEffect && this.primaryColor == presetColorInfo.primaryColor && this.secondaryColor == presetColorInfo.secondaryColor && this.primaryFontColorPanelId == presetColorInfo.primaryFontColorPanelId && this.secondaryFontColorPanelId == presetColorInfo.secondaryFontColorPanelId;
    }

    public int hashCode() {
        return (((((((Integer.hashCode(this.clockEffect) * 31) + Integer.hashCode(this.primaryColor)) * 31) + Integer.hashCode(this.secondaryColor)) * 31) + Integer.hashCode(this.primaryFontColorPanelId)) * 31) + Integer.hashCode(this.secondaryFontColorPanelId);
    }

    public final void setClockEffect(int i) {
        this.clockEffect = i;
    }

    public final void setPrimaryColor(int i) {
        this.primaryColor = i;
    }

    public final void setPrimaryFontColorPanelId(int i) {
        this.primaryFontColorPanelId = i;
    }

    public final void setSecondaryColor(int i) {
        this.secondaryColor = i;
    }

    public final void setSecondaryFontColorPanelId(int i) {
        this.secondaryFontColorPanelId = i;
    }

    public String toString() {
        return "PresetColorInfo(clockEffect=" + this.clockEffect + ", primaryColor=" + this.primaryColor + ", secondaryColor=" + this.secondaryColor + ", primaryFontColorPanelId=" + this.primaryFontColorPanelId + ", secondaryFontColorPanelId=" + this.secondaryFontColorPanelId + ")";
    }
}
