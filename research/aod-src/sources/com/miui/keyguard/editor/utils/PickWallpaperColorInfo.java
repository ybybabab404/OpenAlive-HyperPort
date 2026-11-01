package com.miui.keyguard.editor.utils;

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
/* JADX INFO: compiled from: BaseWallpaperController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class PickWallpaperColorInfo {
    public Integer clockInfoStyle;
    public Integer signatureAlignment;
    public String templateId;

    public PickWallpaperColorInfo(String str, Integer num, Integer num2) {
        this.templateId = str;
        this.clockInfoStyle = num;
        this.signatureAlignment = num2;
    }

    public /* synthetic */ PickWallpaperColorInfo(String str, Integer num, Integer num2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? -1 : num, (i & 4) != 0 ? -1 : num2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PickWallpaperColorInfo)) {
            return false;
        }
        PickWallpaperColorInfo pickWallpaperColorInfo = (PickWallpaperColorInfo) obj;
        return Intrinsics.areEqual(this.templateId, pickWallpaperColorInfo.templateId) && Intrinsics.areEqual(this.clockInfoStyle, pickWallpaperColorInfo.clockInfoStyle) && Intrinsics.areEqual(this.signatureAlignment, pickWallpaperColorInfo.signatureAlignment);
    }

    public final Integer getClockInfoStyle() {
        return this.clockInfoStyle;
    }

    public final Integer getSignatureAlignment() {
        return this.signatureAlignment;
    }

    public final String getTemplateId() {
        return this.templateId;
    }

    public int hashCode() {
        String str = this.templateId;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        Integer num = this.clockInfoStyle;
        int iHashCode2 = (iHashCode + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.signatureAlignment;
        return iHashCode2 + (num2 != null ? num2.hashCode() : 0);
    }

    public final void setClockInfoStyle(Integer num) {
        this.clockInfoStyle = num;
    }

    public final void setSignatureAlignment(Integer num) {
        this.signatureAlignment = num;
    }

    public String toString() {
        return "PickWallpaperColorInfo(templateId=" + this.templateId + ", clockInfoStyle=" + this.clockInfoStyle + ", signatureAlignment=" + this.signatureAlignment + ")";
    }
}
