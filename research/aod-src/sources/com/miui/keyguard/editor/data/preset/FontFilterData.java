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
public final class FontFilterData {
    public final int color;
    public final int colorId;
    public int filterId;
    public boolean onlySet;

    public FontFilterData(int i, int i2, int i3, boolean z) {
        this.filterId = i;
        this.colorId = i2;
        this.color = i3;
        this.onlySet = z;
    }

    public /* synthetic */ FontFilterData(int i, int i2, int i3, boolean z, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, i3, (i4 & 8) != 0 ? false : z);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FontFilterData)) {
            return false;
        }
        FontFilterData fontFilterData = (FontFilterData) obj;
        return this.filterId == fontFilterData.filterId && this.colorId == fontFilterData.colorId && this.color == fontFilterData.color && this.onlySet == fontFilterData.onlySet;
    }

    public final int getColor() {
        return this.color;
    }

    public final int getColorId() {
        return this.colorId;
    }

    public final int getFilterId() {
        return this.filterId;
    }

    public final boolean getOnlySet() {
        return this.onlySet;
    }

    public int hashCode() {
        return (((((Integer.hashCode(this.filterId) * 31) + Integer.hashCode(this.colorId)) * 31) + Integer.hashCode(this.color)) * 31) + Boolean.hashCode(this.onlySet);
    }

    public final void setFilterId(int i) {
        this.filterId = i;
    }

    public String toString() {
        return "FontFilterData(filterId=" + this.filterId + ", colorId=" + this.colorId + ", color=" + this.color + ", onlySet=" + this.onlySet + ")";
    }
}
