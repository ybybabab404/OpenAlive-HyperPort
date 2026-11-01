package com.miui.keyguard.editor.view;

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
/* JADX INFO: compiled from: FontFilterView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class FontFilterViewStatus {
    public int colorLightAnimStatus;
    public boolean colorViewPagerAnimStatus;
    public boolean filterTypeListAnimStatus;
    public boolean hslContainerAnimStatus;

    public FontFilterViewStatus(int i, boolean z, boolean z2, boolean z3) {
        this.colorLightAnimStatus = i;
        this.hslContainerAnimStatus = z;
        this.colorViewPagerAnimStatus = z2;
        this.filterTypeListAnimStatus = z3;
    }

    public /* synthetic */ FontFilterViewStatus(int i, boolean z, boolean z2, boolean z3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i, (i2 & 2) != 0 ? false : z, (i2 & 4) != 0 ? false : z2, (i2 & 8) != 0 ? false : z3);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FontFilterViewStatus)) {
            return false;
        }
        FontFilterViewStatus fontFilterViewStatus = (FontFilterViewStatus) obj;
        return this.colorLightAnimStatus == fontFilterViewStatus.colorLightAnimStatus && this.hslContainerAnimStatus == fontFilterViewStatus.hslContainerAnimStatus && this.colorViewPagerAnimStatus == fontFilterViewStatus.colorViewPagerAnimStatus && this.filterTypeListAnimStatus == fontFilterViewStatus.filterTypeListAnimStatus;
    }

    public final int getColorLightAnimStatus() {
        return this.colorLightAnimStatus;
    }

    public final boolean getColorViewPagerAnimStatus() {
        return this.colorViewPagerAnimStatus;
    }

    public int hashCode() {
        return (((((Integer.hashCode(this.colorLightAnimStatus) * 31) + Boolean.hashCode(this.hslContainerAnimStatus)) * 31) + Boolean.hashCode(this.colorViewPagerAnimStatus)) * 31) + Boolean.hashCode(this.filterTypeListAnimStatus);
    }

    public final void setColorLightAnimStatus(int i) {
        this.colorLightAnimStatus = i;
    }

    public String toString() {
        return "FontFilterViewStatus(colorLightAnimStatus=" + this.colorLightAnimStatus + ", hslContainerAnimStatus=" + this.hslContainerAnimStatus + ", colorViewPagerAnimStatus=" + this.colorViewPagerAnimStatus + ", filterTypeListAnimStatus=" + this.filterTypeListAnimStatus + ")";
    }
}
