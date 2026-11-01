package com.miui.clock.palette;

import com.miui.clock.utils.GlobalColorUtils;
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
/* JADX INFO: compiled from: ClockPaletteStrategy.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class SecondaryColors {
    public final int animationSecondaryColor;
    public final int aodSecondaryBlendColor;
    public final int aodSecondaryColor;
    public int fullAodSecondaryBlendColor;
    public final int fullAodSecondaryColor;
    public final int secondaryBlendColor;
    public final int secondaryColor;

    public SecondaryColors(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        this.secondaryColor = i;
        this.secondaryBlendColor = i2;
        this.animationSecondaryColor = i3;
        this.aodSecondaryColor = i4;
        this.fullAodSecondaryColor = i5;
        this.aodSecondaryBlendColor = i6;
        this.fullAodSecondaryBlendColor = i7;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ SecondaryColors(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, DefaultConstructorMarker defaultConstructorMarker) {
        i2 = (i8 & 2) != 0 ? i : i2;
        this(i, i2, (i8 & 4) != 0 ? i : i3, (i8 & 8) != 0 ? GlobalColorUtils.transformAodColor(i) : i4, (i8 & 16) != 0 ? GlobalColorUtils.transformFullAodColor(i) : i5, (i8 & 32) != 0 ? GlobalColorUtils.transformAodColor(i2) : i6, (i8 & 64) != 0 ? GlobalColorUtils.transformFullAodColor(i2) : i7);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SecondaryColors)) {
            return false;
        }
        SecondaryColors secondaryColors = (SecondaryColors) obj;
        return this.secondaryColor == secondaryColors.secondaryColor && this.secondaryBlendColor == secondaryColors.secondaryBlendColor && this.animationSecondaryColor == secondaryColors.animationSecondaryColor && this.aodSecondaryColor == secondaryColors.aodSecondaryColor && this.fullAodSecondaryColor == secondaryColors.fullAodSecondaryColor && this.aodSecondaryBlendColor == secondaryColors.aodSecondaryBlendColor && this.fullAodSecondaryBlendColor == secondaryColors.fullAodSecondaryBlendColor;
    }

    public final int getAodSecondaryBlendColor() {
        return this.aodSecondaryBlendColor;
    }

    public final int getAodSecondaryColor() {
        return this.aodSecondaryColor;
    }

    public final int getFullAodSecondaryBlendColor() {
        return this.fullAodSecondaryBlendColor;
    }

    public final int getFullAodSecondaryColor() {
        return this.fullAodSecondaryColor;
    }

    public final int getSecondaryBlendColor() {
        return this.secondaryBlendColor;
    }

    public final int getSecondaryColor() {
        return this.secondaryColor;
    }

    public int hashCode() {
        return (((((((((((Integer.hashCode(this.secondaryColor) * 31) + Integer.hashCode(this.secondaryBlendColor)) * 31) + Integer.hashCode(this.animationSecondaryColor)) * 31) + Integer.hashCode(this.aodSecondaryColor)) * 31) + Integer.hashCode(this.fullAodSecondaryColor)) * 31) + Integer.hashCode(this.aodSecondaryBlendColor)) * 31) + Integer.hashCode(this.fullAodSecondaryBlendColor);
    }

    public String toString() {
        return "SecondaryColors(secondaryColor=" + this.secondaryColor + ", secondaryBlendColor=" + this.secondaryBlendColor + ", animationSecondaryColor=" + this.animationSecondaryColor + ", aodSecondaryColor=" + this.aodSecondaryColor + ", fullAodSecondaryColor=" + this.fullAodSecondaryColor + ", aodSecondaryBlendColor=" + this.aodSecondaryBlendColor + ", fullAodSecondaryBlendColor=" + this.fullAodSecondaryBlendColor + ')';
    }
}
