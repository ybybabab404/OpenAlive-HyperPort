package com.miui.keyguard.editor.edit.wallpaper;

import android.graphics.Bitmap;
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
/* JADX INFO: compiled from: IWallpaperLayer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class WallpaperEditParam {
    public final Bitmap depthWallpaper;

    public WallpaperEditParam(Bitmap bitmap) {
        this.depthWallpaper = bitmap;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof WallpaperEditParam) && Intrinsics.areEqual(this.depthWallpaper, ((WallpaperEditParam) obj).depthWallpaper);
    }

    public int hashCode() {
        Bitmap bitmap = this.depthWallpaper;
        if (bitmap == null) {
            return 0;
        }
        return bitmap.hashCode();
    }

    public String toString() {
        return "WallpaperEditParam(depthWallpaper=" + this.depthWallpaper + ")";
    }
}
