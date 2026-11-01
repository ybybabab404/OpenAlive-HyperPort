package com.miui.keyguard.editor.edit.color;

import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
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
/* JADX INFO: compiled from: ColorBean.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ColorData {
    public final Map clockPalette;
    public final boolean darkClockArea;
    public final boolean darkInfoText;
    public final boolean darkSignatureArea;
    public final int extraColor1;
    public final int extraColor2;
    public final int primaryColor;
    public final int randomColorType;
    public final int secondaryColor;
    public final Map signaturePalette;

    public ColorData(int i, int i2, int i3, int i4, boolean z, boolean z2, Map clockPalette, boolean z3, Map signaturePalette, int i5) {
        Intrinsics.checkNotNullParameter(clockPalette, "clockPalette");
        Intrinsics.checkNotNullParameter(signaturePalette, "signaturePalette");
        this.primaryColor = i;
        this.secondaryColor = i2;
        this.extraColor1 = i3;
        this.extraColor2 = i4;
        this.darkClockArea = z;
        this.darkInfoText = z2;
        this.clockPalette = clockPalette;
        this.darkSignatureArea = z3;
        this.signaturePalette = signaturePalette;
        this.randomColorType = i5;
    }

    public /* synthetic */ ColorData(int i, int i2, int i3, int i4, boolean z, boolean z2, Map map, boolean z3, Map map2, int i5, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this((i6 & 1) != 0 ? 0 : i, (i6 & 2) != 0 ? 0 : i2, (i6 & 4) != 0 ? 0 : i3, (i6 & 8) != 0 ? 0 : i4, (i6 & 16) != 0 ? false : z, (i6 & 32) != 0 ? false : z2, (i6 & 64) != 0 ? MapsKt.emptyMap() : map, (i6 & 128) != 0 ? false : z3, (i6 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? MapsKt.emptyMap() : map2, (i6 & 512) != 0 ? 0 : i5);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ColorData)) {
            return false;
        }
        ColorData colorData = (ColorData) obj;
        return this.primaryColor == colorData.primaryColor && this.secondaryColor == colorData.secondaryColor && this.extraColor1 == colorData.extraColor1 && this.extraColor2 == colorData.extraColor2 && this.darkClockArea == colorData.darkClockArea && this.darkInfoText == colorData.darkInfoText && Intrinsics.areEqual(this.clockPalette, colorData.clockPalette) && this.darkSignatureArea == colorData.darkSignatureArea && Intrinsics.areEqual(this.signaturePalette, colorData.signaturePalette) && this.randomColorType == colorData.randomColorType;
    }

    public final Map getClockPalette() {
        return this.clockPalette;
    }

    public final boolean getDarkClockArea() {
        return this.darkClockArea;
    }

    public final boolean getDarkInfoText() {
        return this.darkInfoText;
    }

    public final boolean getDarkSignatureArea() {
        return this.darkSignatureArea;
    }

    public final int getExtraColor1() {
        return this.extraColor1;
    }

    public final int getExtraColor2() {
        return this.extraColor2;
    }

    public final int getPrimaryColor() {
        return this.primaryColor;
    }

    public final int getRandomColorType() {
        return this.randomColorType;
    }

    public final int getSecondaryColor() {
        return this.secondaryColor;
    }

    public final Map getSignaturePalette() {
        return this.signaturePalette;
    }

    public int hashCode() {
        return (((((((((((((((((Integer.hashCode(this.primaryColor) * 31) + Integer.hashCode(this.secondaryColor)) * 31) + Integer.hashCode(this.extraColor1)) * 31) + Integer.hashCode(this.extraColor2)) * 31) + Boolean.hashCode(this.darkClockArea)) * 31) + Boolean.hashCode(this.darkInfoText)) * 31) + this.clockPalette.hashCode()) * 31) + Boolean.hashCode(this.darkSignatureArea)) * 31) + this.signaturePalette.hashCode()) * 31) + Integer.hashCode(this.randomColorType);
    }

    public String toString() {
        return "ColorData(primaryColor=" + this.primaryColor + ", secondaryColor=" + this.secondaryColor + ", extraColor1=" + this.extraColor1 + ", extraColor2=" + this.extraColor2 + ", darkClockArea=" + this.darkClockArea + ", darkInfoText=" + this.darkInfoText + ", clockPalette=" + this.clockPalette + ", darkSignatureArea=" + this.darkSignatureArea + ", signaturePalette=" + this.signaturePalette + ", randomColorType=" + this.randomColorType + ")";
    }
}
