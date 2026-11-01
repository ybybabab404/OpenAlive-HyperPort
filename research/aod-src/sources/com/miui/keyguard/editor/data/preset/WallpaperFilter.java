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
/* JADX INFO: compiled from: WallpaperFilter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class WallpaperFilter {
    public final int filterId;
    public final int iconNameResId;
    public final int iconsResId;
    public final boolean supportDoodle;
    public final boolean supportHierarchy;
    public final boolean supportMatte;

    public WallpaperFilter(int i, int i2, int i3, boolean z, boolean z2, boolean z3) {
        this.filterId = i;
        this.iconsResId = i2;
        this.iconNameResId = i3;
        this.supportHierarchy = z;
        this.supportMatte = z2;
        this.supportDoodle = z3;
    }

    public /* synthetic */ WallpaperFilter(int i, int i2, int i3, boolean z, boolean z2, boolean z3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, i3, (i4 & 8) != 0 ? false : z, (i4 & 16) != 0 ? false : z2, (i4 & 32) != 0 ? false : z3);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WallpaperFilter)) {
            return false;
        }
        WallpaperFilter wallpaperFilter = (WallpaperFilter) obj;
        return this.filterId == wallpaperFilter.filterId && this.iconsResId == wallpaperFilter.iconsResId && this.iconNameResId == wallpaperFilter.iconNameResId && this.supportHierarchy == wallpaperFilter.supportHierarchy && this.supportMatte == wallpaperFilter.supportMatte && this.supportDoodle == wallpaperFilter.supportDoodle;
    }

    public final int getFilterId() {
        return this.filterId;
    }

    public final int getIconNameResId() {
        return this.iconNameResId;
    }

    public final int getIconsResId() {
        return this.iconsResId;
    }

    public final boolean getSupportHierarchy() {
        return this.supportHierarchy;
    }

    public final boolean getSupportMatte() {
        return this.supportMatte;
    }

    public int hashCode() {
        return (((((((((Integer.hashCode(this.filterId) * 31) + Integer.hashCode(this.iconsResId)) * 31) + Integer.hashCode(this.iconNameResId)) * 31) + Boolean.hashCode(this.supportHierarchy)) * 31) + Boolean.hashCode(this.supportMatte)) * 31) + Boolean.hashCode(this.supportDoodle);
    }

    public String toString() {
        return "WallpaperFilter(filterId=" + this.filterId + ", iconsResId=" + this.iconsResId + ", iconNameResId=" + this.iconNameResId + ", supportHierarchy=" + this.supportHierarchy + ", supportMatte=" + this.supportMatte + ", supportDoodle=" + this.supportDoodle + ")";
    }
}
