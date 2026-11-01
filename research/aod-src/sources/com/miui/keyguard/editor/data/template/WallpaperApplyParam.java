package com.miui.keyguard.editor.data.template;

import android.graphics.Bitmap;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: TemplateApiImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class WallpaperApplyParam {
    public static final Companion Companion = new Companion(null);
    public final String croppedDepthPath;
    public final Bitmap croppedDepthWallpaper;
    public final boolean homeEnableBlur;
    public final int homeMagicType;
    public final int magicType;
    public final Bitmap sourceWallpaper;

    /* JADX INFO: compiled from: TemplateApiImpl.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final WallpaperApplyParam defaultInstance() {
            return new WallpaperApplyParam(null, null, null, -1, -1, false);
        }
    }

    public WallpaperApplyParam(String str, Bitmap bitmap, Bitmap bitmap2, int i, int i2, boolean z) {
        this.croppedDepthPath = str;
        this.croppedDepthWallpaper = bitmap;
        this.sourceWallpaper = bitmap2;
        this.magicType = i;
        this.homeMagicType = i2;
        this.homeEnableBlur = z;
    }

    public /* synthetic */ WallpaperApplyParam(String str, Bitmap bitmap, Bitmap bitmap2, int i, int i2, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? null : str, (i3 & 2) != 0 ? null : bitmap, (i3 & 4) != 0 ? null : bitmap2, i, i2, z);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WallpaperApplyParam)) {
            return false;
        }
        WallpaperApplyParam wallpaperApplyParam = (WallpaperApplyParam) obj;
        return Intrinsics.areEqual(this.croppedDepthPath, wallpaperApplyParam.croppedDepthPath) && Intrinsics.areEqual(this.croppedDepthWallpaper, wallpaperApplyParam.croppedDepthWallpaper) && Intrinsics.areEqual(this.sourceWallpaper, wallpaperApplyParam.sourceWallpaper) && this.magicType == wallpaperApplyParam.magicType && this.homeMagicType == wallpaperApplyParam.homeMagicType && this.homeEnableBlur == wallpaperApplyParam.homeEnableBlur;
    }

    public final boolean getHomeEnableBlur() {
        return this.homeEnableBlur;
    }

    public final Bitmap getSourceWallpaper() {
        return this.sourceWallpaper;
    }

    public int hashCode() {
        String str = this.croppedDepthPath;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        Bitmap bitmap = this.croppedDepthWallpaper;
        int iHashCode2 = (iHashCode + (bitmap == null ? 0 : bitmap.hashCode())) * 31;
        Bitmap bitmap2 = this.sourceWallpaper;
        return ((((((iHashCode2 + (bitmap2 != null ? bitmap2.hashCode() : 0)) * 31) + Integer.hashCode(this.magicType)) * 31) + Integer.hashCode(this.homeMagicType)) * 31) + Boolean.hashCode(this.homeEnableBlur);
    }

    public String toString() {
        return "WallpaperApplyParam(croppedDepthPath=" + this.croppedDepthPath + ", croppedDepthWallpaper=" + this.croppedDepthWallpaper + ", sourceWallpaper=" + this.sourceWallpaper + ", magicType=" + this.magicType + ", homeMagicType=" + this.homeMagicType + ", homeEnableBlur=" + this.homeEnableBlur + ")";
    }
}
