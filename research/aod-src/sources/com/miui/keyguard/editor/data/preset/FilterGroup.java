package com.miui.keyguard.editor.data.preset;

import java.util.Arrays;
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
/* JADX INFO: compiled from: FontFilter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class FilterGroup {
    public final FilterColor[] colorData;
    public final int filterId;
    public boolean isSupport;
    public final boolean supportMutableColor;
    public final int titleResId;

    public FilterGroup(int i, int i2, FilterColor[] colorData, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(colorData, "colorData");
        this.filterId = i;
        this.titleResId = i2;
        this.colorData = colorData;
        this.supportMutableColor = z;
        this.isSupport = z2;
    }

    public /* synthetic */ FilterGroup(int i, int i2, FilterColor[] filterColorArr, boolean z, boolean z2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, filterColorArr, (i3 & 8) != 0 ? true : z, (i3 & 16) != 0 ? true : z2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(FilterGroup.class, obj != null ? obj.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type com.miui.keyguard.editor.data.preset.FilterGroup");
        FilterGroup filterGroup = (FilterGroup) obj;
        return this.filterId == filterGroup.filterId && this.titleResId == filterGroup.titleResId && Arrays.equals(this.colorData, filterGroup.colorData);
    }

    public final FilterColor[] getColorData() {
        return this.colorData;
    }

    public final int getFilterId() {
        return this.filterId;
    }

    public final int getTitleResId() {
        return this.titleResId;
    }

    public int hashCode() {
        return (((this.filterId * 31) + this.titleResId) * 31) + Arrays.hashCode(this.colorData);
    }

    public final boolean isSupport() {
        return this.isSupport;
    }

    public final void setSupport(boolean z) {
        this.isSupport = z;
    }

    public String toString() {
        return "FilterGroup(filterId=" + this.filterId + ", titleResId=" + this.titleResId + ", colorData=" + Arrays.toString(this.colorData) + ", supportMutableColor=" + this.supportMutableColor + ", isSupport=" + this.isSupport + ")";
    }
}
