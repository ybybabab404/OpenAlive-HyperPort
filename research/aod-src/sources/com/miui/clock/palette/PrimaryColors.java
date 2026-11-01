package com.miui.clock.palette;

import com.miui.clock.utils.ColorUtils;
import com.miui.clock.utils.GlobalColorUtils;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
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
public final class PrimaryColors {
    public final int animationPrimaryColor;
    public final int aodBlendColor;
    public final int aodInfoColor;
    public final int aodPrimaryColor;
    public final int blendColor;
    public int fullAodBlendColor;
    public final int fullAodInfoColor;
    public final int fullAodPrimaryColor;
    public final int infoAreaColor;
    public final int originMagazineColor;
    public final int originPrimaryColor;
    public final int primaryColor;

    public PrimaryColors(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12) {
        this.primaryColor = i;
        this.blendColor = i2;
        this.aodPrimaryColor = i3;
        this.fullAodPrimaryColor = i4;
        this.aodBlendColor = i5;
        this.fullAodBlendColor = i6;
        this.originMagazineColor = i7;
        this.animationPrimaryColor = i8;
        this.originPrimaryColor = i9;
        this.infoAreaColor = i10;
        this.aodInfoColor = i11;
        this.fullAodInfoColor = i12;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ PrimaryColors(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, DefaultConstructorMarker defaultConstructorMarker) {
        i2 = (i13 & 2) != 0 ? i : i2;
        i3 = (i13 & 4) != 0 ? GlobalColorUtils.transformAodColor(i) : i3;
        i4 = (i13 & 8) != 0 ? GlobalColorUtils.transformFullAodColor(i) : i4;
        this(i, i2, i3, i4, (i13 & 16) != 0 ? GlobalColorUtils.transformAodColor(i2) : i5, (i13 & 32) != 0 ? GlobalColorUtils.transformFullAodColor(i2) : i6, (i13 & 64) != 0 ? ColorUtils.blendColor(i, 0.6f) : i7, (i13 & 128) != 0 ? i : i8, (i13 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? i : i9, (i13 & 512) != 0 ? i : i10, (i13 & 1024) != 0 ? i3 : i11, (i13 & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0 ? i4 : i12);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PrimaryColors)) {
            return false;
        }
        PrimaryColors primaryColors = (PrimaryColors) obj;
        return this.primaryColor == primaryColors.primaryColor && this.blendColor == primaryColors.blendColor && this.aodPrimaryColor == primaryColors.aodPrimaryColor && this.fullAodPrimaryColor == primaryColors.fullAodPrimaryColor && this.aodBlendColor == primaryColors.aodBlendColor && this.fullAodBlendColor == primaryColors.fullAodBlendColor && this.originMagazineColor == primaryColors.originMagazineColor && this.animationPrimaryColor == primaryColors.animationPrimaryColor && this.originPrimaryColor == primaryColors.originPrimaryColor && this.infoAreaColor == primaryColors.infoAreaColor && this.aodInfoColor == primaryColors.aodInfoColor && this.fullAodInfoColor == primaryColors.fullAodInfoColor;
    }

    public final int getAnimationPrimaryColor() {
        return this.animationPrimaryColor;
    }

    public final int getAodBlendColor() {
        return this.aodBlendColor;
    }

    public final int getAodInfoColor() {
        return this.aodInfoColor;
    }

    public final int getAodPrimaryColor() {
        return this.aodPrimaryColor;
    }

    public final int getBlendColor() {
        return this.blendColor;
    }

    public final int getFullAodBlendColor() {
        return this.fullAodBlendColor;
    }

    public final int getFullAodInfoColor() {
        return this.fullAodInfoColor;
    }

    public final int getFullAodPrimaryColor() {
        return this.fullAodPrimaryColor;
    }

    public final int getInfoAreaColor() {
        return this.infoAreaColor;
    }

    public final int getOriginMagazineColor() {
        return this.originMagazineColor;
    }

    public final int getPrimaryColor() {
        return this.primaryColor;
    }

    public int hashCode() {
        return (((((((((((((((((((((Integer.hashCode(this.primaryColor) * 31) + Integer.hashCode(this.blendColor)) * 31) + Integer.hashCode(this.aodPrimaryColor)) * 31) + Integer.hashCode(this.fullAodPrimaryColor)) * 31) + Integer.hashCode(this.aodBlendColor)) * 31) + Integer.hashCode(this.fullAodBlendColor)) * 31) + Integer.hashCode(this.originMagazineColor)) * 31) + Integer.hashCode(this.animationPrimaryColor)) * 31) + Integer.hashCode(this.originPrimaryColor)) * 31) + Integer.hashCode(this.infoAreaColor)) * 31) + Integer.hashCode(this.aodInfoColor)) * 31) + Integer.hashCode(this.fullAodInfoColor);
    }

    public String toString() {
        return "PrimaryColors(primaryColor=" + this.primaryColor + ", blendColor=" + this.blendColor + ", aodPrimaryColor=" + this.aodPrimaryColor + ", fullAodPrimaryColor=" + this.fullAodPrimaryColor + ", aodBlendColor=" + this.aodBlendColor + ", fullAodBlendColor=" + this.fullAodBlendColor + ", originMagazineColor=" + this.originMagazineColor + ", animationPrimaryColor=" + this.animationPrimaryColor + ", originPrimaryColor=" + this.originPrimaryColor + ", infoAreaColor=" + this.infoAreaColor + ", aodInfoColor=" + this.aodInfoColor + ", fullAodInfoColor=" + this.fullAodInfoColor + ')';
    }
}
