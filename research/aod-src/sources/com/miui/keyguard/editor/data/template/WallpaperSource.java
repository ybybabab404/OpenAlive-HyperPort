package com.miui.keyguard.editor.data.template;

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
/* JADX INFO: compiled from: WallpaperSource.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class WallpaperSource {
    public final Bitmap homeWallpaper;
    public final Bitmap lockWallpaper;
    public Bitmap sensorLargeWallpaper;
    public Bitmap sensorSmallWallpaper;
    public final LockWallpaperSource source;
    public Bitmap videoDeepSubject;
    public final Bitmap wallpaperForPickColor;

    public WallpaperSource(Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3, LockWallpaperSource source) {
        Intrinsics.checkNotNullParameter(source, "source");
        this.lockWallpaper = bitmap;
        this.homeWallpaper = bitmap2;
        this.wallpaperForPickColor = bitmap3;
        this.source = source;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WallpaperSource)) {
            return false;
        }
        WallpaperSource wallpaperSource = (WallpaperSource) obj;
        return Intrinsics.areEqual(this.lockWallpaper, wallpaperSource.lockWallpaper) && Intrinsics.areEqual(this.homeWallpaper, wallpaperSource.homeWallpaper) && Intrinsics.areEqual(this.wallpaperForPickColor, wallpaperSource.wallpaperForPickColor) && this.source == wallpaperSource.source;
    }

    public final Bitmap getHomeWallpaper() {
        return this.homeWallpaper;
    }

    public final Bitmap getLockWallpaper() {
        return this.lockWallpaper;
    }

    public final Bitmap getSensorLargeWallpaper() {
        return this.sensorLargeWallpaper;
    }

    public final Bitmap getSensorSmallWallpaper() {
        return this.sensorSmallWallpaper;
    }

    public final LockWallpaperSource getSource() {
        return this.source;
    }

    public final Bitmap getVideoDeepSubject() {
        return this.videoDeepSubject;
    }

    public final Bitmap getWallpaperForPickColor() {
        return this.wallpaperForPickColor;
    }

    public int hashCode() {
        Bitmap bitmap = this.lockWallpaper;
        int iHashCode = (bitmap == null ? 0 : bitmap.hashCode()) * 31;
        Bitmap bitmap2 = this.homeWallpaper;
        int iHashCode2 = (iHashCode + (bitmap2 == null ? 0 : bitmap2.hashCode())) * 31;
        Bitmap bitmap3 = this.wallpaperForPickColor;
        return ((iHashCode2 + (bitmap3 != null ? bitmap3.hashCode() : 0)) * 31) + this.source.hashCode();
    }

    public final void setSensorLargeWallpaper(Bitmap bitmap) {
        this.sensorLargeWallpaper = bitmap;
    }

    public final void setSensorSmallWallpaper(Bitmap bitmap) {
        this.sensorSmallWallpaper = bitmap;
    }

    public final void setVideoDeepSubject(Bitmap bitmap) {
        this.videoDeepSubject = bitmap;
    }

    public String toString() {
        return "WallpaperSource(lockWallpaper=" + this.lockWallpaper + ", homeWallpaper=" + this.homeWallpaper + ", wallpaperForPickColor=" + this.wallpaperForPickColor + ", source=" + this.source + ")";
    }
}
