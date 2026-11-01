package com.miui.keyguard.editor.data.bean;

import android.graphics.Bitmap;
import com.miui.keyguard.editor.data.template.WallpaperInfoType;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: WallpaperTypeInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class WallpaperTypeInfo {

    @Nullable
    private Bitmap bitmap;

    @Nullable
    private Bitmap depthBitmap;
    private boolean enableBlur;

    @Nullable
    private String extraPath;

    @Nullable
    private String from;

    @Nullable
    private String imagePath;

    @NotNull
    private WallpaperInfoType infoType;
    private boolean isAiGenerated;
    private boolean isDirectGenerate;
    private boolean isNeedDark;

    @Nullable
    private Boolean isSupportFullScreenAod;

    @Nullable
    private Boolean isSupportLoop;

    @Nullable
    private String maskPath;
    private int mattingMode;

    @Nullable
    private WallpaperPositionInfo positionInfo;

    @Nullable
    private String resourcePath;

    @Nullable
    private SensorWallpaperPrams sensorWallpaperPrams;
    private boolean supportSubject;

    @Nullable
    private String type;

    @Nullable
    private Integer videoType;

    public WallpaperTypeInfo(@Nullable Bitmap bitmap, @Nullable String str, @Nullable String str2, @Nullable String str3, int i, boolean z, @Nullable SensorWallpaperPrams sensorWallpaperPrams, boolean z2, boolean z3, @Nullable Boolean bool, @Nullable Bitmap bitmap2, @Nullable Integer num, @Nullable String str4, boolean z4, boolean z5, @Nullable WallpaperPositionInfo wallpaperPositionInfo, @Nullable String str5, @NotNull WallpaperInfoType infoType, @Nullable Boolean bool2, @Nullable String str6) {
        Intrinsics.checkNotNullParameter(infoType, "infoType");
        this.bitmap = bitmap;
        this.type = str;
        this.resourcePath = str2;
        this.maskPath = str3;
        this.mattingMode = i;
        this.supportSubject = z;
        this.sensorWallpaperPrams = sensorWallpaperPrams;
        this.isNeedDark = z2;
        this.enableBlur = z3;
        this.isSupportLoop = bool;
        this.depthBitmap = bitmap2;
        this.videoType = num;
        this.extraPath = str4;
        this.isAiGenerated = z4;
        this.isDirectGenerate = z5;
        this.positionInfo = wallpaperPositionInfo;
        this.from = str5;
        this.infoType = infoType;
        this.isSupportFullScreenAod = bool2;
        this.imagePath = str6;
    }

    public /* synthetic */ WallpaperTypeInfo(Bitmap bitmap, String str, String str2, String str3, int i, boolean z, SensorWallpaperPrams sensorWallpaperPrams, boolean z2, boolean z3, Boolean bool, Bitmap bitmap2, Integer num, String str4, boolean z4, boolean z5, WallpaperPositionInfo wallpaperPositionInfo, String str5, WallpaperInfoType wallpaperInfoType, Boolean bool2, String str6, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(bitmap, str, (i2 & 4) != 0 ? null : str2, (i2 & 8) != 0 ? null : str3, (i2 & 16) != 0 ? 0 : i, (i2 & 32) != 0 ? true : z, (i2 & 64) != 0 ? null : sensorWallpaperPrams, (i2 & 128) != 0 ? false : z2, (i2 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? false : z3, (i2 & 512) != 0 ? Boolean.TRUE : bool, (i2 & 1024) != 0 ? null : bitmap2, (i2 & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0 ? 0 : num, (i2 & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0 ? null : str4, (i2 & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0 ? false : z4, (i2 & 16384) != 0 ? false : z5, (32768 & i2) != 0 ? null : wallpaperPositionInfo, (65536 & i2) != 0 ? null : str5, (131072 & i2) != 0 ? WallpaperInfoType.LOCK_WALLPAPER : wallpaperInfoType, (262144 & i2) != 0 ? Boolean.FALSE : bool2, (i2 & 524288) != 0 ? null : str6);
    }

    public static /* synthetic */ WallpaperTypeInfo copy$default(WallpaperTypeInfo wallpaperTypeInfo, Bitmap bitmap, String str, String str2, String str3, int i, boolean z, SensorWallpaperPrams sensorWallpaperPrams, boolean z2, boolean z3, Boolean bool, Bitmap bitmap2, Integer num, String str4, boolean z4, boolean z5, WallpaperPositionInfo wallpaperPositionInfo, String str5, WallpaperInfoType wallpaperInfoType, Boolean bool2, String str6, int i2, Object obj) {
        String str7;
        Boolean bool3;
        Bitmap bitmap3 = (i2 & 1) != 0 ? wallpaperTypeInfo.bitmap : bitmap;
        String str8 = (i2 & 2) != 0 ? wallpaperTypeInfo.type : str;
        String str9 = (i2 & 4) != 0 ? wallpaperTypeInfo.resourcePath : str2;
        String str10 = (i2 & 8) != 0 ? wallpaperTypeInfo.maskPath : str3;
        int i3 = (i2 & 16) != 0 ? wallpaperTypeInfo.mattingMode : i;
        boolean z6 = (i2 & 32) != 0 ? wallpaperTypeInfo.supportSubject : z;
        SensorWallpaperPrams sensorWallpaperPrams2 = (i2 & 64) != 0 ? wallpaperTypeInfo.sensorWallpaperPrams : sensorWallpaperPrams;
        boolean z7 = (i2 & 128) != 0 ? wallpaperTypeInfo.isNeedDark : z2;
        boolean z8 = (i2 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? wallpaperTypeInfo.enableBlur : z3;
        Boolean bool4 = (i2 & 512) != 0 ? wallpaperTypeInfo.isSupportLoop : bool;
        Bitmap bitmap4 = (i2 & 1024) != 0 ? wallpaperTypeInfo.depthBitmap : bitmap2;
        Integer num2 = (i2 & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0 ? wallpaperTypeInfo.videoType : num;
        String str11 = (i2 & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0 ? wallpaperTypeInfo.extraPath : str4;
        boolean z9 = (i2 & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0 ? wallpaperTypeInfo.isAiGenerated : z4;
        Bitmap bitmap5 = bitmap3;
        boolean z10 = (i2 & 16384) != 0 ? wallpaperTypeInfo.isDirectGenerate : z5;
        WallpaperPositionInfo wallpaperPositionInfo2 = (i2 & 32768) != 0 ? wallpaperTypeInfo.positionInfo : wallpaperPositionInfo;
        String str12 = (i2 & 65536) != 0 ? wallpaperTypeInfo.from : str5;
        WallpaperInfoType wallpaperInfoType2 = (i2 & 131072) != 0 ? wallpaperTypeInfo.infoType : wallpaperInfoType;
        Boolean bool5 = (i2 & 262144) != 0 ? wallpaperTypeInfo.isSupportFullScreenAod : bool2;
        if ((i2 & 524288) != 0) {
            bool3 = bool5;
            str7 = wallpaperTypeInfo.imagePath;
        } else {
            str7 = str6;
            bool3 = bool5;
        }
        return wallpaperTypeInfo.copy(bitmap5, str8, str9, str10, i3, z6, sensorWallpaperPrams2, z7, z8, bool4, bitmap4, num2, str11, z9, z10, wallpaperPositionInfo2, str12, wallpaperInfoType2, bool3, str7);
    }

    @Nullable
    public final Bitmap component1() {
        return this.bitmap;
    }

    @Nullable
    public final Boolean component10() {
        return this.isSupportLoop;
    }

    @Nullable
    public final Bitmap component11() {
        return this.depthBitmap;
    }

    @Nullable
    public final Integer component12() {
        return this.videoType;
    }

    @Nullable
    public final String component13() {
        return this.extraPath;
    }

    public final boolean component14() {
        return this.isAiGenerated;
    }

    public final boolean component15() {
        return this.isDirectGenerate;
    }

    @Nullable
    public final WallpaperPositionInfo component16() {
        return this.positionInfo;
    }

    @Nullable
    public final String component17() {
        return this.from;
    }

    @NotNull
    public final WallpaperInfoType component18() {
        return this.infoType;
    }

    @Nullable
    public final Boolean component19() {
        return this.isSupportFullScreenAod;
    }

    @Nullable
    public final String component2() {
        return this.type;
    }

    @Nullable
    public final String component20() {
        return this.imagePath;
    }

    @Nullable
    public final String component3() {
        return this.resourcePath;
    }

    @Nullable
    public final String component4() {
        return this.maskPath;
    }

    public final int component5() {
        return this.mattingMode;
    }

    public final boolean component6() {
        return this.supportSubject;
    }

    @Nullable
    public final SensorWallpaperPrams component7() {
        return this.sensorWallpaperPrams;
    }

    public final boolean component8() {
        return this.isNeedDark;
    }

    public final boolean component9() {
        return this.enableBlur;
    }

    @NotNull
    public final WallpaperTypeInfo copy(@Nullable Bitmap bitmap, @Nullable String str, @Nullable String str2, @Nullable String str3, int i, boolean z, @Nullable SensorWallpaperPrams sensorWallpaperPrams, boolean z2, boolean z3, @Nullable Boolean bool, @Nullable Bitmap bitmap2, @Nullable Integer num, @Nullable String str4, boolean z4, boolean z5, @Nullable WallpaperPositionInfo wallpaperPositionInfo, @Nullable String str5, @NotNull WallpaperInfoType infoType, @Nullable Boolean bool2, @Nullable String str6) {
        Intrinsics.checkNotNullParameter(infoType, "infoType");
        return new WallpaperTypeInfo(bitmap, str, str2, str3, i, z, sensorWallpaperPrams, z2, z3, bool, bitmap2, num, str4, z4, z5, wallpaperPositionInfo, str5, infoType, bool2, str6);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WallpaperTypeInfo)) {
            return false;
        }
        WallpaperTypeInfo wallpaperTypeInfo = (WallpaperTypeInfo) obj;
        return Intrinsics.areEqual(this.bitmap, wallpaperTypeInfo.bitmap) && Intrinsics.areEqual(this.type, wallpaperTypeInfo.type) && Intrinsics.areEqual(this.resourcePath, wallpaperTypeInfo.resourcePath) && Intrinsics.areEqual(this.maskPath, wallpaperTypeInfo.maskPath) && this.mattingMode == wallpaperTypeInfo.mattingMode && this.supportSubject == wallpaperTypeInfo.supportSubject && Intrinsics.areEqual(this.sensorWallpaperPrams, wallpaperTypeInfo.sensorWallpaperPrams) && this.isNeedDark == wallpaperTypeInfo.isNeedDark && this.enableBlur == wallpaperTypeInfo.enableBlur && Intrinsics.areEqual(this.isSupportLoop, wallpaperTypeInfo.isSupportLoop) && Intrinsics.areEqual(this.depthBitmap, wallpaperTypeInfo.depthBitmap) && Intrinsics.areEqual(this.videoType, wallpaperTypeInfo.videoType) && Intrinsics.areEqual(this.extraPath, wallpaperTypeInfo.extraPath) && this.isAiGenerated == wallpaperTypeInfo.isAiGenerated && this.isDirectGenerate == wallpaperTypeInfo.isDirectGenerate && Intrinsics.areEqual(this.positionInfo, wallpaperTypeInfo.positionInfo) && Intrinsics.areEqual(this.from, wallpaperTypeInfo.from) && this.infoType == wallpaperTypeInfo.infoType && Intrinsics.areEqual(this.isSupportFullScreenAod, wallpaperTypeInfo.isSupportFullScreenAod) && Intrinsics.areEqual(this.imagePath, wallpaperTypeInfo.imagePath);
    }

    @Nullable
    public final Bitmap getBitmap() {
        return this.bitmap;
    }

    @Nullable
    public final Bitmap getDepthBitmap() {
        return this.depthBitmap;
    }

    public final boolean getEnableBlur() {
        return this.enableBlur;
    }

    @Nullable
    public final String getExtraPath() {
        return this.extraPath;
    }

    @Nullable
    public final String getFrom() {
        return this.from;
    }

    @Nullable
    public final String getImagePath() {
        return this.imagePath;
    }

    @NotNull
    public final WallpaperInfoType getInfoType() {
        return this.infoType;
    }

    @Nullable
    public final String getMaskPath() {
        return this.maskPath;
    }

    public final int getMattingMode() {
        return this.mattingMode;
    }

    @Nullable
    public final WallpaperPositionInfo getPositionInfo() {
        return this.positionInfo;
    }

    @Nullable
    public final String getResourcePath() {
        return this.resourcePath;
    }

    @Nullable
    public final SensorWallpaperPrams getSensorWallpaperPrams() {
        return this.sensorWallpaperPrams;
    }

    public final boolean getSupportSubject() {
        return this.supportSubject;
    }

    @Nullable
    public final String getType() {
        return this.type;
    }

    @Nullable
    public final Integer getVideoType() {
        return this.videoType;
    }

    public int hashCode() {
        Bitmap bitmap = this.bitmap;
        int iHashCode = (bitmap == null ? 0 : bitmap.hashCode()) * 31;
        String str = this.type;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.resourcePath;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.maskPath;
        int iHashCode4 = (((((iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31) + Integer.hashCode(this.mattingMode)) * 31) + Boolean.hashCode(this.supportSubject)) * 31;
        SensorWallpaperPrams sensorWallpaperPrams = this.sensorWallpaperPrams;
        int iHashCode5 = (((((iHashCode4 + (sensorWallpaperPrams == null ? 0 : sensorWallpaperPrams.hashCode())) * 31) + Boolean.hashCode(this.isNeedDark)) * 31) + Boolean.hashCode(this.enableBlur)) * 31;
        Boolean bool = this.isSupportLoop;
        int iHashCode6 = (iHashCode5 + (bool == null ? 0 : bool.hashCode())) * 31;
        Bitmap bitmap2 = this.depthBitmap;
        int iHashCode7 = (iHashCode6 + (bitmap2 == null ? 0 : bitmap2.hashCode())) * 31;
        Integer num = this.videoType;
        int iHashCode8 = (iHashCode7 + (num == null ? 0 : num.hashCode())) * 31;
        String str4 = this.extraPath;
        int iHashCode9 = (((((iHashCode8 + (str4 == null ? 0 : str4.hashCode())) * 31) + Boolean.hashCode(this.isAiGenerated)) * 31) + Boolean.hashCode(this.isDirectGenerate)) * 31;
        WallpaperPositionInfo wallpaperPositionInfo = this.positionInfo;
        int iHashCode10 = (iHashCode9 + (wallpaperPositionInfo == null ? 0 : wallpaperPositionInfo.hashCode())) * 31;
        String str5 = this.from;
        int iHashCode11 = (((iHashCode10 + (str5 == null ? 0 : str5.hashCode())) * 31) + this.infoType.hashCode()) * 31;
        Boolean bool2 = this.isSupportFullScreenAod;
        int iHashCode12 = (iHashCode11 + (bool2 == null ? 0 : bool2.hashCode())) * 31;
        String str6 = this.imagePath;
        return iHashCode12 + (str6 != null ? str6.hashCode() : 0);
    }

    public final boolean isAiGenerated() {
        return this.isAiGenerated;
    }

    public final boolean isDirectGenerate() {
        return this.isDirectGenerate;
    }

    public final boolean isNeedDark() {
        return this.isNeedDark;
    }

    @Nullable
    public final Boolean isSupportFullScreenAod() {
        return this.isSupportFullScreenAod;
    }

    @Nullable
    public final Boolean isSupportLoop() {
        return this.isSupportLoop;
    }

    public final void setAiGenerated(boolean z) {
        this.isAiGenerated = z;
    }

    public final void setBitmap(@Nullable Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public final void setDepthBitmap(@Nullable Bitmap bitmap) {
        this.depthBitmap = bitmap;
    }

    public final void setDirectGenerate(boolean z) {
        this.isDirectGenerate = z;
    }

    public final void setEnableBlur(boolean z) {
        this.enableBlur = z;
    }

    public final void setExtraPath(@Nullable String str) {
        this.extraPath = str;
    }

    public final void setFrom(@Nullable String str) {
        this.from = str;
    }

    public final void setImagePath(@Nullable String str) {
        this.imagePath = str;
    }

    public final void setInfoType(@NotNull WallpaperInfoType wallpaperInfoType) {
        Intrinsics.checkNotNullParameter(wallpaperInfoType, "<set-?>");
        this.infoType = wallpaperInfoType;
    }

    public final void setMaskPath(@Nullable String str) {
        this.maskPath = str;
    }

    public final void setMattingMode(int i) {
        this.mattingMode = i;
    }

    public final void setNeedDark(boolean z) {
        this.isNeedDark = z;
    }

    public final void setPositionInfo(@Nullable WallpaperPositionInfo wallpaperPositionInfo) {
        this.positionInfo = wallpaperPositionInfo;
    }

    public final void setResourcePath(@Nullable String str) {
        this.resourcePath = str;
    }

    public final void setSensorWallpaperPrams(@Nullable SensorWallpaperPrams sensorWallpaperPrams) {
        this.sensorWallpaperPrams = sensorWallpaperPrams;
    }

    public final void setSupportFullScreenAod(@Nullable Boolean bool) {
        this.isSupportFullScreenAod = bool;
    }

    public final void setSupportLoop(@Nullable Boolean bool) {
        this.isSupportLoop = bool;
    }

    public final void setSupportSubject(boolean z) {
        this.supportSubject = z;
    }

    public final void setType(@Nullable String str) {
        this.type = str;
    }

    public final void setVideoType(@Nullable Integer num) {
        this.videoType = num;
    }

    @NotNull
    public String toString() {
        return "WallpaperTypeInfo(bitmap=" + this.bitmap + ", type=" + this.type + ", resourcePath=" + this.resourcePath + ", maskPath=" + this.maskPath + ", mattingMode=" + this.mattingMode + ", supportSubject=" + this.supportSubject + ", sensorWallpaperPrams=" + this.sensorWallpaperPrams + ", isNeedDark=" + this.isNeedDark + ", enableBlur=" + this.enableBlur + ", isSupportLoop=" + this.isSupportLoop + ", depthBitmap=" + this.depthBitmap + ", videoType=" + this.videoType + ", extraPath=" + this.extraPath + ", isAiGenerated=" + this.isAiGenerated + ", isDirectGenerate=" + this.isDirectGenerate + ", positionInfo=" + this.positionInfo + ", from=" + this.from + ", infoType=" + this.infoType + ", isSupportFullScreenAod=" + this.isSupportFullScreenAod + ", imagePath=" + this.imagePath + ")";
    }
}
