package com.miui.keyguard.editor.edit.color;

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
/* JADX INFO: compiled from: ColorBean.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ColorSelectBean {
    public final int colorId;
    public final int fontFilterId;
    public final boolean isAutoPickColor;
    public final boolean isPrimaryColor;
    public final int selectedColor;

    public ColorSelectBean(int i, boolean z, boolean z2, int i2, int i3) {
        this.selectedColor = i;
        this.isPrimaryColor = z;
        this.isAutoPickColor = z2;
        this.fontFilterId = i2;
        this.colorId = i3;
    }

    public /* synthetic */ ColorSelectBean(int i, boolean z, boolean z2, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i4 & 2) != 0 ? false : z, (i4 & 4) != 0 ? false : z2, (i4 & 8) != 0 ? 0 : i2, (i4 & 16) != 0 ? 13 : i3);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ColorSelectBean)) {
            return false;
        }
        ColorSelectBean colorSelectBean = (ColorSelectBean) obj;
        return this.selectedColor == colorSelectBean.selectedColor && this.isPrimaryColor == colorSelectBean.isPrimaryColor && this.isAutoPickColor == colorSelectBean.isAutoPickColor && this.fontFilterId == colorSelectBean.fontFilterId && this.colorId == colorSelectBean.colorId;
    }

    public final int getColorId() {
        return this.colorId;
    }

    public final int getFontFilterId() {
        return this.fontFilterId;
    }

    public final int getSelectedColor() {
        return this.selectedColor;
    }

    public int hashCode() {
        return (((((((Integer.hashCode(this.selectedColor) * 31) + Boolean.hashCode(this.isPrimaryColor)) * 31) + Boolean.hashCode(this.isAutoPickColor)) * 31) + Integer.hashCode(this.fontFilterId)) * 31) + Integer.hashCode(this.colorId);
    }

    public final boolean isAutoPickColor() {
        return this.isAutoPickColor;
    }

    public final boolean isPrimaryColor() {
        return this.isPrimaryColor;
    }

    public String toString() {
        return "ColorSelectBean(selectedColor=" + this.selectedColor + ", isPrimaryColor=" + this.isPrimaryColor + ", isAutoPickColor=" + this.isAutoPickColor + ", fontFilterId=" + this.fontFilterId + ", colorId=" + this.colorId + ")";
    }
}
