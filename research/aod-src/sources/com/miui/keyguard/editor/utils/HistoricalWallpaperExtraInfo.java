package com.miui.keyguard.editor.utils;

import com.miui.keyguard.editor.view.AutoBottomSheetKt;
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
/* JADX INFO: compiled from: ThemeUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class HistoricalWallpaperExtraInfo {
    public String imagePath;
    public boolean isAiGenerated;
    public Boolean isSupportFullScreenAod;
    public boolean isSupportLoop;
    public SensorExtra sensorExtra;
    public int sensorVideoAllFrameCount;
    public int sensorVideoRate;
    public int sensorVideoSmallScreenFrameCount;
    public String smallThumbnailPath;
    public int stiffness;
    public String thumbnailPath;
    public String videoPath;
    public int videoType;
    public String wallpaperType;

    public HistoricalWallpaperExtraInfo(int i, int i2, int i3, String str, String str2, int i4, String str3, String str4, String str5, boolean z, int i5, boolean z2, SensorExtra sensorExtra, Boolean bool) {
        this.sensorVideoRate = i;
        this.sensorVideoAllFrameCount = i2;
        this.sensorVideoSmallScreenFrameCount = i3;
        this.thumbnailPath = str;
        this.smallThumbnailPath = str2;
        this.stiffness = i4;
        this.wallpaperType = str3;
        this.videoPath = str4;
        this.imagePath = str5;
        this.isAiGenerated = z;
        this.videoType = i5;
        this.isSupportLoop = z2;
        this.sensorExtra = sensorExtra;
        this.isSupportFullScreenAod = bool;
    }

    public /* synthetic */ HistoricalWallpaperExtraInfo(int i, int i2, int i3, String str, String str2, int i4, String str3, String str4, String str5, boolean z, int i5, boolean z2, SensorExtra sensorExtra, Boolean bool, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this((i6 & 1) != 0 ? 0 : i, (i6 & 2) != 0 ? 0 : i2, (i6 & 4) != 0 ? 0 : i3, (i6 & 8) != 0 ? null : str, (i6 & 16) != 0 ? null : str2, (i6 & 32) != 0 ? 10 : i4, (i6 & 64) != 0 ? null : str3, (i6 & 128) != 0 ? null : str4, (i6 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? null : str5, (i6 & 512) != 0 ? false : z, (i6 & 1024) != 0 ? 0 : i5, (i6 & AutoBottomSheetKt.ALIGN_SCREEN_END) == 0 ? z2 : false, (i6 & AutoBottomSheetKt.ALIGN_SCREEN_TOP) == 0 ? sensorExtra : null, (i6 & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0 ? Boolean.FALSE : bool);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HistoricalWallpaperExtraInfo)) {
            return false;
        }
        HistoricalWallpaperExtraInfo historicalWallpaperExtraInfo = (HistoricalWallpaperExtraInfo) obj;
        return this.sensorVideoRate == historicalWallpaperExtraInfo.sensorVideoRate && this.sensorVideoAllFrameCount == historicalWallpaperExtraInfo.sensorVideoAllFrameCount && this.sensorVideoSmallScreenFrameCount == historicalWallpaperExtraInfo.sensorVideoSmallScreenFrameCount && Intrinsics.areEqual(this.thumbnailPath, historicalWallpaperExtraInfo.thumbnailPath) && Intrinsics.areEqual(this.smallThumbnailPath, historicalWallpaperExtraInfo.smallThumbnailPath) && this.stiffness == historicalWallpaperExtraInfo.stiffness && Intrinsics.areEqual(this.wallpaperType, historicalWallpaperExtraInfo.wallpaperType) && Intrinsics.areEqual(this.videoPath, historicalWallpaperExtraInfo.videoPath) && Intrinsics.areEqual(this.imagePath, historicalWallpaperExtraInfo.imagePath) && this.isAiGenerated == historicalWallpaperExtraInfo.isAiGenerated && this.videoType == historicalWallpaperExtraInfo.videoType && this.isSupportLoop == historicalWallpaperExtraInfo.isSupportLoop && Intrinsics.areEqual(this.sensorExtra, historicalWallpaperExtraInfo.sensorExtra) && Intrinsics.areEqual(this.isSupportFullScreenAod, historicalWallpaperExtraInfo.isSupportFullScreenAod);
    }

    public final String getImagePath() {
        return this.imagePath;
    }

    public int hashCode() {
        int iHashCode = ((((Integer.hashCode(this.sensorVideoRate) * 31) + Integer.hashCode(this.sensorVideoAllFrameCount)) * 31) + Integer.hashCode(this.sensorVideoSmallScreenFrameCount)) * 31;
        String str = this.thumbnailPath;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.smallThumbnailPath;
        int iHashCode3 = (((iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31) + Integer.hashCode(this.stiffness)) * 31;
        String str3 = this.wallpaperType;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.videoPath;
        int iHashCode5 = (iHashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.imagePath;
        int iHashCode6 = (((((((iHashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31) + Boolean.hashCode(this.isAiGenerated)) * 31) + Integer.hashCode(this.videoType)) * 31) + Boolean.hashCode(this.isSupportLoop)) * 31;
        SensorExtra sensorExtra = this.sensorExtra;
        int iHashCode7 = (iHashCode6 + (sensorExtra == null ? 0 : sensorExtra.hashCode())) * 31;
        Boolean bool = this.isSupportFullScreenAod;
        return iHashCode7 + (bool != null ? bool.hashCode() : 0);
    }

    public final void setAiGenerated(boolean z) {
        this.isAiGenerated = z;
    }

    public final void setImagePath(String str) {
        this.imagePath = str;
    }

    public final void setSensorExtra(SensorExtra sensorExtra) {
        this.sensorExtra = sensorExtra;
    }

    public final void setSensorVideoAllFrameCount(int i) {
        this.sensorVideoAllFrameCount = i;
    }

    public final void setSensorVideoSmallScreenFrameCount(int i) {
        this.sensorVideoSmallScreenFrameCount = i;
    }

    public final void setSmallThumbnailPath(String str) {
        this.smallThumbnailPath = str;
    }

    public final void setStiffness(int i) {
        this.stiffness = i;
    }

    public final void setSupportFullScreenAod(Boolean bool) {
        this.isSupportFullScreenAod = bool;
    }

    public final void setSupportLoop(boolean z) {
        this.isSupportLoop = z;
    }

    public final void setThumbnailPath(String str) {
        this.thumbnailPath = str;
    }

    public final void setVideoPath(String str) {
        this.videoPath = str;
    }

    public final void setVideoType(int i) {
        this.videoType = i;
    }

    public final void setWallpaperType(String str) {
        this.wallpaperType = str;
    }

    public String toString() {
        return "HistoricalWallpaperExtraInfo(sensorVideoRate=" + this.sensorVideoRate + ", sensorVideoAllFrameCount=" + this.sensorVideoAllFrameCount + ", sensorVideoSmallScreenFrameCount=" + this.sensorVideoSmallScreenFrameCount + ", thumbnailPath=" + this.thumbnailPath + ", smallThumbnailPath=" + this.smallThumbnailPath + ", stiffness=" + this.stiffness + ", wallpaperType=" + this.wallpaperType + ", videoPath=" + this.videoPath + ", imagePath=" + this.imagePath + ", isAiGenerated=" + this.isAiGenerated + ", videoType=" + this.videoType + ", isSupportLoop=" + this.isSupportLoop + ", sensorExtra=" + this.sensorExtra + ", isSupportFullScreenAod=" + this.isSupportFullScreenAod + ")";
    }
}
