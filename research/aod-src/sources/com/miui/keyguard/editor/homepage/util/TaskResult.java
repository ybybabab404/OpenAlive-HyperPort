package com.miui.keyguard.editor.homepage.util;

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
/* JADX INFO: compiled from: TemplateApplyController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class TaskResult {
    public final String currentWallpaperType;
    public final int galleryStatus;
    public final Bitmap historyScreenshot;
    public final Bitmap historyWallpaper;
    public final Bitmap historyWallpaperScreenshot;
    public final boolean isVideoWallpaper;
    public final Bitmap presetWallpaper;

    public TaskResult(String str, boolean z, int i, Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4) {
        this.currentWallpaperType = str;
        this.isVideoWallpaper = z;
        this.galleryStatus = i;
        this.historyScreenshot = bitmap;
        this.historyWallpaperScreenshot = bitmap2;
        this.presetWallpaper = bitmap3;
        this.historyWallpaper = bitmap4;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TaskResult)) {
            return false;
        }
        TaskResult taskResult = (TaskResult) obj;
        return Intrinsics.areEqual(this.currentWallpaperType, taskResult.currentWallpaperType) && this.isVideoWallpaper == taskResult.isVideoWallpaper && this.galleryStatus == taskResult.galleryStatus && Intrinsics.areEqual(this.historyScreenshot, taskResult.historyScreenshot) && Intrinsics.areEqual(this.historyWallpaperScreenshot, taskResult.historyWallpaperScreenshot) && Intrinsics.areEqual(this.presetWallpaper, taskResult.presetWallpaper) && Intrinsics.areEqual(this.historyWallpaper, taskResult.historyWallpaper);
    }

    public final String getCurrentWallpaperType() {
        return this.currentWallpaperType;
    }

    public final int getGalleryStatus() {
        return this.galleryStatus;
    }

    public final Bitmap getHistoryScreenshot() {
        return this.historyScreenshot;
    }

    public final Bitmap getHistoryWallpaper() {
        return this.historyWallpaper;
    }

    public final Bitmap getHistoryWallpaperScreenshot() {
        return this.historyWallpaperScreenshot;
    }

    public final Bitmap getPresetWallpaper() {
        return this.presetWallpaper;
    }

    public int hashCode() {
        String str = this.currentWallpaperType;
        int iHashCode = (((((str == null ? 0 : str.hashCode()) * 31) + Boolean.hashCode(this.isVideoWallpaper)) * 31) + Integer.hashCode(this.galleryStatus)) * 31;
        Bitmap bitmap = this.historyScreenshot;
        int iHashCode2 = (iHashCode + (bitmap == null ? 0 : bitmap.hashCode())) * 31;
        Bitmap bitmap2 = this.historyWallpaperScreenshot;
        int iHashCode3 = (iHashCode2 + (bitmap2 == null ? 0 : bitmap2.hashCode())) * 31;
        Bitmap bitmap3 = this.presetWallpaper;
        int iHashCode4 = (iHashCode3 + (bitmap3 == null ? 0 : bitmap3.hashCode())) * 31;
        Bitmap bitmap4 = this.historyWallpaper;
        return iHashCode4 + (bitmap4 != null ? bitmap4.hashCode() : 0);
    }

    public final boolean isVideoWallpaper() {
        return this.isVideoWallpaper;
    }

    public String toString() {
        return "TaskResult(currentWallpaperType=" + this.currentWallpaperType + ", isVideoWallpaper=" + this.isVideoWallpaper + ", galleryStatus=" + this.galleryStatus + ", historyScreenshot=" + this.historyScreenshot + ", historyWallpaperScreenshot=" + this.historyWallpaperScreenshot + ", presetWallpaper=" + this.presetWallpaper + ", historyWallpaper=" + this.historyWallpaper + ")";
    }
}
