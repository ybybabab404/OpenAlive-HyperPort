package com.miui.keyguard.editor.data.preset;

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
/* JADX INFO: compiled from: FontFilter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class FontFilterInfo {
    public int currentFilterTypeIndex;
    public int filterId;

    public FontFilterInfo(int i, int i2) {
        this.filterId = i;
        this.currentFilterTypeIndex = i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(FontFilterInfo.class, obj != null ? obj.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type com.miui.keyguard.editor.data.preset.FontFilterInfo");
        FontFilterInfo fontFilterInfo = (FontFilterInfo) obj;
        return this.filterId == fontFilterInfo.filterId && this.currentFilterTypeIndex == fontFilterInfo.currentFilterTypeIndex;
    }

    public final int getCurrentFilterTypeIndex() {
        return this.currentFilterTypeIndex;
    }

    public final int getFilterId() {
        return this.filterId;
    }

    public int hashCode() {
        return (this.filterId * 31) + this.currentFilterTypeIndex;
    }

    public final void setCurrentFilterTypeIndex(int i) {
        this.currentFilterTypeIndex = i;
    }

    public final void setFilterId(int i) {
        this.filterId = i;
    }

    public String toString() {
        return "FontFilterInfo(filterId=" + this.filterId + ", currentFilterTypeIndex=" + this.currentFilterTypeIndex + ")";
    }
}
