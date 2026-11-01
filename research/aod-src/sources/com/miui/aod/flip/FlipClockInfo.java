package com.miui.aod.flip;

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
/* JADX INFO: compiled from: FlipClockInfo.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class FlipClockInfo {
    public final int aodPrimaryColor;
    public final int blendColor;
    public final int blendColor180;
    public final boolean needInverseColor;
    public final int primaryColor;
    public final int primaryColor180;
    public final int style;
    public final String templateId;
    public final boolean wallpaperDark;
    public final boolean wallpaperDark180;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FlipClockInfo)) {
            return false;
        }
        FlipClockInfo flipClockInfo = (FlipClockInfo) obj;
        return Intrinsics.areEqual(this.templateId, flipClockInfo.templateId) && this.primaryColor == flipClockInfo.primaryColor && this.primaryColor180 == flipClockInfo.primaryColor180 && this.blendColor == flipClockInfo.blendColor && this.blendColor180 == flipClockInfo.blendColor180 && this.wallpaperDark == flipClockInfo.wallpaperDark && this.wallpaperDark180 == flipClockInfo.wallpaperDark180 && this.style == flipClockInfo.style && this.aodPrimaryColor == flipClockInfo.aodPrimaryColor && this.needInverseColor == flipClockInfo.needInverseColor;
    }

    public final int getBlendColor() {
        return this.blendColor;
    }

    public final int getBlendColor180() {
        return this.blendColor180;
    }

    public final boolean getNeedInverseColor() {
        return this.needInverseColor;
    }

    public final int getPrimaryColor() {
        return this.primaryColor;
    }

    public final int getPrimaryColor180() {
        return this.primaryColor180;
    }

    public final int getStyle() {
        return this.style;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final FlipLinkageTemplateId getTemplate() {
        String str = this.templateId;
        switch (str.hashCode()) {
            case -1927105479:
                if (str.equals("splicing")) {
                    return FlipLinkageTemplateId.SPLICING;
                }
                break;
            case -1378241396:
                if (str.equals("bubble")) {
                    return FlipLinkageTemplateId.Bubble;
                }
                break;
            case -1326135015:
                if (str.equals("doodle")) {
                    return FlipLinkageTemplateId.Doodle;
                }
                break;
            case -1044620687:
                if (str.equals("interactive_pets")) {
                    return FlipLinkageTemplateId.Interactive_Pets;
                }
                break;
            case -1009223445:
                if (str.equals("ai_pets")) {
                    return FlipLinkageTemplateId.AI_Pets;
                }
                break;
            case 3437364:
                if (str.equals("pets")) {
                    return FlipLinkageTemplateId.Pets;
                }
                break;
            case 853620882:
                if (str.equals("classic")) {
                    return FlipLinkageTemplateId.Classic;
                }
                break;
        }
        return FlipLinkageTemplateId.SPLICING;
    }

    public final boolean getWallpaperDark() {
        return this.wallpaperDark;
    }

    public final boolean getWallpaperDark180() {
        return this.wallpaperDark180;
    }

    public int hashCode() {
        return (((((((((((((((((this.templateId.hashCode() * 31) + Integer.hashCode(this.primaryColor)) * 31) + Integer.hashCode(this.primaryColor180)) * 31) + Integer.hashCode(this.blendColor)) * 31) + Integer.hashCode(this.blendColor180)) * 31) + Boolean.hashCode(this.wallpaperDark)) * 31) + Boolean.hashCode(this.wallpaperDark180)) * 31) + Integer.hashCode(this.style)) * 31) + Integer.hashCode(this.aodPrimaryColor)) * 31) + Boolean.hashCode(this.needInverseColor);
    }

    public String toString() {
        return "FlipClockInfo(templateId=" + this.templateId + ", primaryColor=" + this.primaryColor + ", primaryColor180=" + this.primaryColor180 + ", blendColor=" + this.blendColor + ", blendColor180=" + this.blendColor180 + ", wallpaperDark=" + this.wallpaperDark + ", wallpaperDark180=" + this.wallpaperDark180 + ", style=" + this.style + ", aodPrimaryColor=" + this.aodPrimaryColor + ", needInverseColor=" + this.needInverseColor + ")";
    }
}
