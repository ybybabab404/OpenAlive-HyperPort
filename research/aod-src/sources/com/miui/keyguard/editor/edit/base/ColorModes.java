package com.miui.keyguard.editor.edit.base;

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
/* JADX INFO: compiled from: BaseTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ColorModes {
    public Integer funcText;
    public Integer scaleHint;
    public Integer wallpaper;

    public ColorModes(Integer num, Integer num2, Integer num3) {
        this.wallpaper = num;
        this.scaleHint = num2;
        this.funcText = num3;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ColorModes)) {
            return false;
        }
        ColorModes colorModes = (ColorModes) obj;
        return Intrinsics.areEqual(this.wallpaper, colorModes.wallpaper) && Intrinsics.areEqual(this.scaleHint, colorModes.scaleHint) && Intrinsics.areEqual(this.funcText, colorModes.funcText);
    }

    public final Integer getScaleHint() {
        return this.scaleHint;
    }

    public final Integer getWallpaper() {
        return this.wallpaper;
    }

    public int hashCode() {
        Integer num = this.wallpaper;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        Integer num2 = this.scaleHint;
        int iHashCode2 = (iHashCode + (num2 == null ? 0 : num2.hashCode())) * 31;
        Integer num3 = this.funcText;
        return iHashCode2 + (num3 != null ? num3.hashCode() : 0);
    }

    public String toString() {
        return "ColorModes(wallpaper=" + this.wallpaper + ", scaleHint=" + this.scaleHint + ", funcText=" + this.funcText + ")";
    }
}
