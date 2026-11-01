package com.miui.keyguard.editor.data.bean;

import android.graphics.Bitmap;
import android.util.Log;
import com.miui.keyguard.editor.data.template.LockWallpaperSource;
import com.miui.keyguard.editor.data.template.TemplateFilePathGenerator;
import com.miui.keyguard.editor.data.template.WallpaperInfoType;
import com.miui.keyguard.editor.utils.WideColorGamutManager;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: TemplateConfig.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class WallpaperInfo {

    @Nullable
    private String cropSubject;

    @Nullable
    private String croppedFrontBack;

    @Nullable
    private transient Bitmap croppedFrontBackBitmap;

    @Nullable
    private transient LockWallpaperSource currentLockWallpaperSource;

    @Nullable
    private transient Bitmap currentSmallSensorWallpaper;

    @Nullable
    private transient Bitmap currentWallpaper;

    @Nullable
    private String depthVideo;

    @Nullable
    private String depthVideoScreenShot;

    @Nullable
    private String downloadUrl;
    private boolean enableBlur;
    private boolean enableBlurHome;
    private int enableDoodle;

    @Nullable
    private ExtraInfo extraInfo;

    @Nullable
    private Long fileSize;

    @Nullable
    private String frontBack;

    @Nullable
    private transient Bitmap frontBackBitmap;

    @Nullable
    private String galleryContent;

    @Nullable
    private String imagePath;

    @NotNull
    private WallpaperInfoType infoType;
    private boolean isAiGenerated;
    private boolean isDepthVideo;
    private transient boolean isDirectGenerate;
    private boolean isNeedDark;

    @Nullable
    private Boolean isSupportFullScreenAod;
    private boolean isSupportLoop;
    private boolean isWideColorGamut;

    @Nullable
    private LargeScreenHierarchyEnable largeScreenHierarchyEnable;
    private int magicType;
    private int magicTypeHome;

    @Nullable
    private String mask;
    private transient int mattingMode;

    @Nullable
    private String originResourcePath;

    @Nullable
    private WallpaperPositionInfo positionInfo;

    @Nullable
    private String resourceType;

    @Nullable
    private SensorWallpaperPrams sensorWallpaperPrams;

    @Nullable
    private String sha1;

    @Nullable
    private String source;

    @Nullable
    private String subject;

    @Nullable
    private transient Bitmap subjectBitmap;
    private boolean supportSubject;

    @Nullable
    private String videoName;

    @Nullable
    private transient Bitmap videoSubjectBitmap;
    private int videoType;

    @Nullable
    private transient Bitmap videoWallpaperBitmap;

    /* JADX INFO: compiled from: TemplateConfig.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[WallpaperInfoType.values().length];
            try {
                iArr[WallpaperInfoType.LOCK_WALLPAPER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[WallpaperInfoType.HOME_WALLPAPER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public WallpaperInfo(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, int i, @Nullable String str6, @Nullable WallpaperPositionInfo wallpaperPositionInfo, @Nullable String str7, @Nullable String str8, boolean z, @Nullable SensorWallpaperPrams sensorWallpaperPrams, boolean z2, @Nullable String str9, boolean z3, @Nullable LargeScreenHierarchyEnable largeScreenHierarchyEnable, boolean z4, boolean z5, @Nullable String str10, @Nullable String str11, @Nullable String str12, @Nullable String str13, @Nullable Long l, int i2, boolean z6, int i3, int i4, @Nullable ExtraInfo extraInfo, boolean z7, @Nullable Boolean bool, @Nullable String str14) {
        this.source = str;
        this.mask = str2;
        this.subject = str3;
        this.frontBack = str4;
        this.croppedFrontBack = str5;
        this.magicType = i;
        this.cropSubject = str6;
        this.positionInfo = wallpaperPositionInfo;
        this.resourceType = str7;
        this.originResourcePath = str8;
        this.supportSubject = z;
        this.sensorWallpaperPrams = sensorWallpaperPrams;
        this.isNeedDark = z2;
        this.galleryContent = str9;
        this.enableBlur = z3;
        this.largeScreenHierarchyEnable = largeScreenHierarchyEnable;
        this.isSupportLoop = z4;
        this.isDepthVideo = z5;
        this.depthVideo = str10;
        this.downloadUrl = str11;
        this.videoName = str12;
        this.sha1 = str13;
        this.fileSize = l;
        this.magicTypeHome = i2;
        this.enableBlurHome = z6;
        this.enableDoodle = i3;
        this.videoType = i4;
        this.extraInfo = extraInfo;
        this.isAiGenerated = z7;
        this.isSupportFullScreenAod = bool;
        this.imagePath = str14;
        this.infoType = WallpaperInfoType.LOCK_WALLPAPER;
    }

    public /* synthetic */ WallpaperInfo(String str, String str2, String str3, String str4, String str5, int i, String str6, WallpaperPositionInfo wallpaperPositionInfo, String str7, String str8, boolean z, SensorWallpaperPrams sensorWallpaperPrams, boolean z2, String str9, boolean z3, LargeScreenHierarchyEnable largeScreenHierarchyEnable, boolean z4, boolean z5, String str10, String str11, String str12, String str13, Long l, int i2, boolean z6, int i3, int i4, ExtraInfo extraInfo, boolean z7, Boolean bool, String str14, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, str4, str5, i, str6, wallpaperPositionInfo, str7, str8, (i5 & 1024) != 0 ? true : z, (i5 & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0 ? null : sensorWallpaperPrams, (i5 & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0 ? false : z2, (i5 & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0 ? null : str9, (i5 & 16384) != 0 ? false : z3, (32768 & i5) != 0 ? null : largeScreenHierarchyEnable, (65536 & i5) != 0 ? true : z4, (131072 & i5) != 0 ? false : z5, (262144 & i5) != 0 ? null : str10, (524288 & i5) != 0 ? null : str11, (1048576 & i5) != 0 ? null : str12, (2097152 & i5) != 0 ? null : str13, (4194304 & i5) != 0 ? null : l, i2, z6, i3, (67108864 & i5) != 0 ? 0 : i4, (134217728 & i5) != 0 ? null : extraInfo, (268435456 & i5) != 0 ? false : z7, (536870912 & i5) != 0 ? Boolean.FALSE : bool, (i5 & 1073741824) != 0 ? null : str14);
    }

    public static /* synthetic */ WallpaperInfo copy$default(WallpaperInfo wallpaperInfo, String str, String str2, String str3, String str4, String str5, int i, String str6, WallpaperPositionInfo wallpaperPositionInfo, String str7, String str8, boolean z, SensorWallpaperPrams sensorWallpaperPrams, boolean z2, String str9, boolean z3, LargeScreenHierarchyEnable largeScreenHierarchyEnable, boolean z4, boolean z5, String str10, String str11, String str12, String str13, Long l, int i2, boolean z6, int i3, int i4, ExtraInfo extraInfo, boolean z7, Boolean bool, String str14, int i5, Object obj) {
        String str15;
        Boolean bool2;
        String str16 = (i5 & 1) != 0 ? wallpaperInfo.source : str;
        String str17 = (i5 & 2) != 0 ? wallpaperInfo.mask : str2;
        String str18 = (i5 & 4) != 0 ? wallpaperInfo.subject : str3;
        String str19 = (i5 & 8) != 0 ? wallpaperInfo.frontBack : str4;
        String str20 = (i5 & 16) != 0 ? wallpaperInfo.croppedFrontBack : str5;
        int i6 = (i5 & 32) != 0 ? wallpaperInfo.magicType : i;
        String str21 = (i5 & 64) != 0 ? wallpaperInfo.cropSubject : str6;
        WallpaperPositionInfo wallpaperPositionInfo2 = (i5 & 128) != 0 ? wallpaperInfo.positionInfo : wallpaperPositionInfo;
        String str22 = (i5 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? wallpaperInfo.resourceType : str7;
        String str23 = (i5 & 512) != 0 ? wallpaperInfo.originResourcePath : str8;
        boolean z8 = (i5 & 1024) != 0 ? wallpaperInfo.supportSubject : z;
        SensorWallpaperPrams sensorWallpaperPrams2 = (i5 & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0 ? wallpaperInfo.sensorWallpaperPrams : sensorWallpaperPrams;
        boolean z9 = (i5 & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0 ? wallpaperInfo.isNeedDark : z2;
        String str24 = (i5 & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0 ? wallpaperInfo.galleryContent : str9;
        String str25 = str16;
        boolean z10 = (i5 & 16384) != 0 ? wallpaperInfo.enableBlur : z3;
        LargeScreenHierarchyEnable largeScreenHierarchyEnable2 = (i5 & 32768) != 0 ? wallpaperInfo.largeScreenHierarchyEnable : largeScreenHierarchyEnable;
        boolean z11 = (i5 & 65536) != 0 ? wallpaperInfo.isSupportLoop : z4;
        boolean z12 = (i5 & 131072) != 0 ? wallpaperInfo.isDepthVideo : z5;
        String str26 = (i5 & 262144) != 0 ? wallpaperInfo.depthVideo : str10;
        String str27 = (i5 & 524288) != 0 ? wallpaperInfo.downloadUrl : str11;
        String str28 = (i5 & 1048576) != 0 ? wallpaperInfo.videoName : str12;
        String str29 = (i5 & 2097152) != 0 ? wallpaperInfo.sha1 : str13;
        Long l2 = (i5 & 4194304) != 0 ? wallpaperInfo.fileSize : l;
        int i7 = (i5 & 8388608) != 0 ? wallpaperInfo.magicTypeHome : i2;
        boolean z13 = (i5 & 16777216) != 0 ? wallpaperInfo.enableBlurHome : z6;
        int i8 = (i5 & 33554432) != 0 ? wallpaperInfo.enableDoodle : i3;
        int i9 = (i5 & 67108864) != 0 ? wallpaperInfo.videoType : i4;
        ExtraInfo extraInfo2 = (i5 & 134217728) != 0 ? wallpaperInfo.extraInfo : extraInfo;
        boolean z14 = (i5 & 268435456) != 0 ? wallpaperInfo.isAiGenerated : z7;
        Boolean bool3 = (i5 & 536870912) != 0 ? wallpaperInfo.isSupportFullScreenAod : bool;
        if ((i5 & 1073741824) != 0) {
            bool2 = bool3;
            str15 = wallpaperInfo.imagePath;
        } else {
            str15 = str14;
            bool2 = bool3;
        }
        return wallpaperInfo.copy(str25, str17, str18, str19, str20, i6, str21, wallpaperPositionInfo2, str22, str23, z8, sensorWallpaperPrams2, z9, str24, z10, largeScreenHierarchyEnable2, z11, z12, str26, str27, str28, str29, l2, i7, z13, i8, i9, extraInfo2, z14, bool2, str15);
    }

    @Nullable
    public final String component1() {
        return this.source;
    }

    @Nullable
    public final String component10() {
        return this.originResourcePath;
    }

    public final boolean component11() {
        return this.supportSubject;
    }

    @Nullable
    public final SensorWallpaperPrams component12() {
        return this.sensorWallpaperPrams;
    }

    public final boolean component13() {
        return this.isNeedDark;
    }

    @Nullable
    public final String component14() {
        return this.galleryContent;
    }

    public final boolean component15() {
        return this.enableBlur;
    }

    @Nullable
    public final LargeScreenHierarchyEnable component16() {
        return this.largeScreenHierarchyEnable;
    }

    public final boolean component17() {
        return this.isSupportLoop;
    }

    public final boolean component18() {
        return this.isDepthVideo;
    }

    @Nullable
    public final String component19() {
        return this.depthVideo;
    }

    @Nullable
    public final String component2() {
        return this.mask;
    }

    @Nullable
    public final String component20() {
        return this.downloadUrl;
    }

    @Nullable
    public final String component21() {
        return this.videoName;
    }

    @Nullable
    public final String component22() {
        return this.sha1;
    }

    @Nullable
    public final Long component23() {
        return this.fileSize;
    }

    public final int component24() {
        return this.magicTypeHome;
    }

    public final boolean component25() {
        return this.enableBlurHome;
    }

    public final int component26() {
        return this.enableDoodle;
    }

    public final int component27() {
        return this.videoType;
    }

    @Nullable
    public final ExtraInfo component28() {
        return this.extraInfo;
    }

    public final boolean component29() {
        return this.isAiGenerated;
    }

    @Nullable
    public final String component3() {
        return this.subject;
    }

    @Nullable
    public final Boolean component30() {
        return this.isSupportFullScreenAod;
    }

    @Nullable
    public final String component31() {
        return this.imagePath;
    }

    @Nullable
    public final String component4() {
        return this.frontBack;
    }

    @Nullable
    public final String component5() {
        return this.croppedFrontBack;
    }

    public final int component6() {
        return this.magicType;
    }

    @Nullable
    public final String component7() {
        return this.cropSubject;
    }

    @Nullable
    public final WallpaperPositionInfo component8() {
        return this.positionInfo;
    }

    @Nullable
    public final String component9() {
        return this.resourceType;
    }

    @NotNull
    public final WallpaperInfo copy(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, int i, @Nullable String str6, @Nullable WallpaperPositionInfo wallpaperPositionInfo, @Nullable String str7, @Nullable String str8, boolean z, @Nullable SensorWallpaperPrams sensorWallpaperPrams, boolean z2, @Nullable String str9, boolean z3, @Nullable LargeScreenHierarchyEnable largeScreenHierarchyEnable, boolean z4, boolean z5, @Nullable String str10, @Nullable String str11, @Nullable String str12, @Nullable String str13, @Nullable Long l, int i2, boolean z6, int i3, int i4, @Nullable ExtraInfo extraInfo, boolean z7, @Nullable Boolean bool, @Nullable String str14) {
        return new WallpaperInfo(str, str2, str3, str4, str5, i, str6, wallpaperPositionInfo, str7, str8, z, sensorWallpaperPrams, z2, str9, z3, largeScreenHierarchyEnable, z4, z5, str10, str11, str12, str13, l, i2, z6, i3, i4, extraInfo, z7, bool, str14);
    }

    @NotNull
    public final WallpaperInfo deepCopy() {
        WallpaperInfo wallpaperInfoCopy$default = copy$default(this, null, null, null, null, null, 0, null, null, null, null, false, null, false, null, false, null, false, false, null, null, null, null, null, 0, false, 0, 0, null, false, null, null, Integer.MAX_VALUE, null);
        wallpaperInfoCopy$default.currentWallpaper = this.currentWallpaper;
        wallpaperInfoCopy$default.currentSmallSensorWallpaper = this.currentSmallSensorWallpaper;
        WallpaperPositionInfo wallpaperPositionInfo = this.positionInfo;
        wallpaperInfoCopy$default.positionInfo = wallpaperPositionInfo != null ? wallpaperPositionInfo.deepCopy() : null;
        SensorWallpaperPrams sensorWallpaperPrams = this.sensorWallpaperPrams;
        wallpaperInfoCopy$default.sensorWallpaperPrams = sensorWallpaperPrams != null ? SensorWallpaperPrams.copy$default(sensorWallpaperPrams, null, null, null, null, null, null, null, null, null, null, 1023, null) : null;
        LargeScreenHierarchyEnable largeScreenHierarchyEnable = this.largeScreenHierarchyEnable;
        wallpaperInfoCopy$default.largeScreenHierarchyEnable = largeScreenHierarchyEnable != null ? largeScreenHierarchyEnable.copy() : null;
        wallpaperInfoCopy$default.infoType = this.infoType;
        return wallpaperInfoCopy$default;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WallpaperInfo)) {
            return false;
        }
        WallpaperInfo wallpaperInfo = (WallpaperInfo) obj;
        return Intrinsics.areEqual(this.source, wallpaperInfo.source) && Intrinsics.areEqual(this.mask, wallpaperInfo.mask) && Intrinsics.areEqual(this.subject, wallpaperInfo.subject) && Intrinsics.areEqual(this.frontBack, wallpaperInfo.frontBack) && Intrinsics.areEqual(this.croppedFrontBack, wallpaperInfo.croppedFrontBack) && this.magicType == wallpaperInfo.magicType && Intrinsics.areEqual(this.cropSubject, wallpaperInfo.cropSubject) && Intrinsics.areEqual(this.positionInfo, wallpaperInfo.positionInfo) && Intrinsics.areEqual(this.resourceType, wallpaperInfo.resourceType) && Intrinsics.areEqual(this.originResourcePath, wallpaperInfo.originResourcePath) && this.supportSubject == wallpaperInfo.supportSubject && Intrinsics.areEqual(this.sensorWallpaperPrams, wallpaperInfo.sensorWallpaperPrams) && this.isNeedDark == wallpaperInfo.isNeedDark && Intrinsics.areEqual(this.galleryContent, wallpaperInfo.galleryContent) && this.enableBlur == wallpaperInfo.enableBlur && Intrinsics.areEqual(this.largeScreenHierarchyEnable, wallpaperInfo.largeScreenHierarchyEnable) && this.isSupportLoop == wallpaperInfo.isSupportLoop && this.isDepthVideo == wallpaperInfo.isDepthVideo && Intrinsics.areEqual(this.depthVideo, wallpaperInfo.depthVideo) && Intrinsics.areEqual(this.downloadUrl, wallpaperInfo.downloadUrl) && Intrinsics.areEqual(this.videoName, wallpaperInfo.videoName) && Intrinsics.areEqual(this.sha1, wallpaperInfo.sha1) && Intrinsics.areEqual(this.fileSize, wallpaperInfo.fileSize) && this.magicTypeHome == wallpaperInfo.magicTypeHome && this.enableBlurHome == wallpaperInfo.enableBlurHome && this.enableDoodle == wallpaperInfo.enableDoodle && this.videoType == wallpaperInfo.videoType && Intrinsics.areEqual(this.extraInfo, wallpaperInfo.extraInfo) && this.isAiGenerated == wallpaperInfo.isAiGenerated && Intrinsics.areEqual(this.isSupportFullScreenAod, wallpaperInfo.isSupportFullScreenAod) && Intrinsics.areEqual(this.imagePath, wallpaperInfo.imagePath);
    }

    @Nullable
    public final String getCropSubject() {
        return this.cropSubject;
    }

    @Nullable
    public final String getCroppedFrontBack() {
        return this.croppedFrontBack;
    }

    @Nullable
    public final Bitmap getCroppedFrontBackBitmap() {
        return this.croppedFrontBackBitmap;
    }

    @Nullable
    public final LockWallpaperSource getCurrentLockWallpaperSource() {
        return this.currentLockWallpaperSource;
    }

    @Nullable
    public final Bitmap getCurrentSmallSensorWallpaper() {
        return this.currentSmallSensorWallpaper;
    }

    @Nullable
    public final Bitmap getCurrentWallpaper() {
        return this.currentWallpaper;
    }

    @Nullable
    public final String getDepthVideo() {
        return this.depthVideo;
    }

    @Nullable
    public final String getDepthVideoScreenShot() {
        return this.depthVideoScreenShot;
    }

    @Nullable
    public final String getDownloadUrl() {
        return this.downloadUrl;
    }

    public final boolean getEnableBlur() {
        return this.enableBlur;
    }

    public final boolean getEnableBlurHome() {
        return this.enableBlurHome;
    }

    public final int getEnableDoodle() {
        return this.enableDoodle;
    }

    @Nullable
    public final ExtraInfo getExtraInfo() {
        return this.extraInfo;
    }

    @Nullable
    public final Long getFileSize() {
        return this.fileSize;
    }

    @Nullable
    public final String getFrontBack() {
        return this.frontBack;
    }

    @Nullable
    public final Bitmap getFrontBackBitmap() {
        return this.frontBackBitmap;
    }

    @Nullable
    public final String getGalleryContent() {
        return this.galleryContent;
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
    public final LargeScreenHierarchyEnable getLargeScreenHierarchyEnable() {
        return this.largeScreenHierarchyEnable;
    }

    public final int getMagicType() {
        return this.magicType;
    }

    public final int getMagicTypeHome() {
        return this.magicTypeHome;
    }

    @Nullable
    public final String getMask() {
        return this.mask;
    }

    public final int getMattingMode() {
        return this.mattingMode;
    }

    @Nullable
    public final String getOriginResourcePath() {
        return this.originResourcePath;
    }

    @Nullable
    public final WallpaperPositionInfo getPositionInfo() {
        return this.positionInfo;
    }

    @Nullable
    public final String getResourceType() {
        return this.resourceType;
    }

    @Nullable
    public final SensorWallpaperPrams getSensorWallpaperPrams() {
        return this.sensorWallpaperPrams;
    }

    @Nullable
    public final String getSha1() {
        return this.sha1;
    }

    @Nullable
    public final String getSource() {
        return this.source;
    }

    @Nullable
    public final String getSubject() {
        return this.subject;
    }

    @Nullable
    public final Bitmap getSubjectBitmap() {
        return this.subjectBitmap;
    }

    public final boolean getSupportSubject() {
        return this.supportSubject;
    }

    @Nullable
    public final String getVideoName() {
        return this.videoName;
    }

    @Nullable
    public final Bitmap getVideoSubjectBitmap() {
        return this.videoSubjectBitmap;
    }

    public final int getVideoType() {
        return this.videoType;
    }

    @Nullable
    public final Bitmap getVideoWallpaperBitmap() {
        return this.videoWallpaperBitmap;
    }

    @NotNull
    public final TemplateFilePathGenerator.TemplateFileType getVideoWallpaperFileType() {
        int i = WhenMappings.$EnumSwitchMapping$0[this.infoType.ordinal()];
        if (i == 1) {
            return TemplateFilePathGenerator.TemplateFileType.VIDEO_WALLPAPER;
        }
        if (i == 2) {
            return TemplateFilePathGenerator.TemplateFileType.VIDEO_WALLPAPER_HOME;
        }
        throw new NoWhenBranchMatchedException();
    }

    @NotNull
    public final TemplateFilePathGenerator.TemplateFileType getWallpaperFileType() {
        WallpaperInfoType wallpaperInfoType = this.infoType;
        if (wallpaperInfoType == null) {
            Log.e("TemplateConfig", "info type is null:" + this);
            return TemplateFilePathGenerator.TemplateFileType.WALLPAPER;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[wallpaperInfoType.ordinal()];
        if (i == 1) {
            return TemplateFilePathGenerator.TemplateFileType.WALLPAPER;
        }
        if (i == 2) {
            return TemplateFilePathGenerator.TemplateFileType.WALLPAPER_HOME;
        }
        Log.e("TemplateConfig", "info error:" + this + " infoType=" + this.infoType);
        return TemplateFilePathGenerator.TemplateFileType.WALLPAPER;
    }

    public int hashCode() {
        String str = this.source;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.mask;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.subject;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.frontBack;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.croppedFrontBack;
        int iHashCode5 = (((iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31) + Integer.hashCode(this.magicType)) * 31;
        String str6 = this.cropSubject;
        int iHashCode6 = (iHashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31;
        WallpaperPositionInfo wallpaperPositionInfo = this.positionInfo;
        int iHashCode7 = (iHashCode6 + (wallpaperPositionInfo == null ? 0 : wallpaperPositionInfo.hashCode())) * 31;
        String str7 = this.resourceType;
        int iHashCode8 = (iHashCode7 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.originResourcePath;
        int iHashCode9 = (((iHashCode8 + (str8 == null ? 0 : str8.hashCode())) * 31) + Boolean.hashCode(this.supportSubject)) * 31;
        SensorWallpaperPrams sensorWallpaperPrams = this.sensorWallpaperPrams;
        int iHashCode10 = (((iHashCode9 + (sensorWallpaperPrams == null ? 0 : sensorWallpaperPrams.hashCode())) * 31) + Boolean.hashCode(this.isNeedDark)) * 31;
        String str9 = this.galleryContent;
        int iHashCode11 = (((iHashCode10 + (str9 == null ? 0 : str9.hashCode())) * 31) + Boolean.hashCode(this.enableBlur)) * 31;
        LargeScreenHierarchyEnable largeScreenHierarchyEnable = this.largeScreenHierarchyEnable;
        int iHashCode12 = (((((iHashCode11 + (largeScreenHierarchyEnable == null ? 0 : largeScreenHierarchyEnable.hashCode())) * 31) + Boolean.hashCode(this.isSupportLoop)) * 31) + Boolean.hashCode(this.isDepthVideo)) * 31;
        String str10 = this.depthVideo;
        int iHashCode13 = (iHashCode12 + (str10 == null ? 0 : str10.hashCode())) * 31;
        String str11 = this.downloadUrl;
        int iHashCode14 = (iHashCode13 + (str11 == null ? 0 : str11.hashCode())) * 31;
        String str12 = this.videoName;
        int iHashCode15 = (iHashCode14 + (str12 == null ? 0 : str12.hashCode())) * 31;
        String str13 = this.sha1;
        int iHashCode16 = (iHashCode15 + (str13 == null ? 0 : str13.hashCode())) * 31;
        Long l = this.fileSize;
        int iHashCode17 = (((((((((iHashCode16 + (l == null ? 0 : l.hashCode())) * 31) + Integer.hashCode(this.magicTypeHome)) * 31) + Boolean.hashCode(this.enableBlurHome)) * 31) + Integer.hashCode(this.enableDoodle)) * 31) + Integer.hashCode(this.videoType)) * 31;
        ExtraInfo extraInfo = this.extraInfo;
        int iHashCode18 = (((iHashCode17 + (extraInfo == null ? 0 : extraInfo.hashCode())) * 31) + Boolean.hashCode(this.isAiGenerated)) * 31;
        Boolean bool = this.isSupportFullScreenAod;
        int iHashCode19 = (iHashCode18 + (bool == null ? 0 : bool.hashCode())) * 31;
        String str14 = this.imagePath;
        return iHashCode19 + (str14 != null ? str14.hashCode() : 0);
    }

    public final boolean isAiGenerated() {
        return this.isAiGenerated;
    }

    public final boolean isDepthVideo() {
        return this.isDepthVideo;
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

    public final boolean isSupportLoop() {
        return this.isSupportLoop;
    }

    public final boolean isWideColorGamut() {
        return WideColorGamutManager.INSTANCE.isWideColorBitmap(this.currentWallpaper);
    }

    public final void resetMagicType(@NotNull String reason) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        Log.i("TemplateConfig", "resetMagicType: for " + reason);
        this.magicType = 0;
        this.enableBlur = false;
    }

    public final void setAiGenerated(boolean z) {
        this.isAiGenerated = z;
    }

    public final void setCropSubject(@Nullable String str) {
        this.cropSubject = str;
    }

    public final void setCroppedFrontBack(@Nullable String str) {
        this.croppedFrontBack = str;
    }

    public final void setCroppedFrontBackBitmap(@Nullable Bitmap bitmap) {
        this.croppedFrontBackBitmap = bitmap;
    }

    public final void setCurrentLockWallpaperSource(@Nullable LockWallpaperSource lockWallpaperSource) {
        this.currentLockWallpaperSource = lockWallpaperSource;
    }

    public final void setCurrentSmallSensorWallpaper(@Nullable Bitmap bitmap) {
        this.currentSmallSensorWallpaper = bitmap;
    }

    public final void setCurrentWallpaper(@Nullable Bitmap bitmap) {
        this.currentWallpaper = bitmap;
    }

    public final void setDepthVideo(@Nullable String str) {
        this.depthVideo = str;
    }

    public final void setDepthVideo(boolean z) {
        this.isDepthVideo = z;
    }

    public final void setDepthVideoScreenShot(@Nullable String str) {
        this.depthVideoScreenShot = str;
    }

    public final void setDirectGenerate(boolean z) {
        this.isDirectGenerate = z;
    }

    public final void setDownloadUrl(@Nullable String str) {
        this.downloadUrl = str;
    }

    public final void setEnableBlur(boolean z) {
        this.enableBlur = z;
    }

    public final void setEnableBlurHome(boolean z) {
        this.enableBlurHome = z;
    }

    public final void setEnableDoodle(int i) {
        this.enableDoodle = i;
    }

    public final void setExtraInfo(@Nullable ExtraInfo extraInfo) {
        this.extraInfo = extraInfo;
    }

    public final void setFileSize(@Nullable Long l) {
        this.fileSize = l;
    }

    public final void setFrontBack(@Nullable String str) {
        this.frontBack = str;
    }

    public final void setFrontBackBitmap(@Nullable Bitmap bitmap) {
        this.frontBackBitmap = bitmap;
    }

    public final void setGalleryContent(@Nullable String str) {
        this.galleryContent = str;
    }

    public final void setImagePath(@Nullable String str) {
        this.imagePath = str;
    }

    public final void setInfoType(@NotNull WallpaperInfoType wallpaperInfoType) {
        Intrinsics.checkNotNullParameter(wallpaperInfoType, "<set-?>");
        this.infoType = wallpaperInfoType;
    }

    public final void setLargeScreenHierarchyEnable(@Nullable LargeScreenHierarchyEnable largeScreenHierarchyEnable) {
        this.largeScreenHierarchyEnable = largeScreenHierarchyEnable;
    }

    public final void setMagicType(int i) {
        this.magicType = i;
    }

    public final void setMagicTypeHome(int i) {
        this.magicTypeHome = i;
    }

    public final void setMask(@Nullable String str) {
        this.mask = str;
    }

    public final void setMattingMode(int i) {
        this.mattingMode = i;
    }

    public final void setNeedDark(boolean z) {
        this.isNeedDark = z;
    }

    public final void setOriginResourcePath(@Nullable String str) {
        this.originResourcePath = str;
    }

    public final void setPositionInfo(@Nullable WallpaperPositionInfo wallpaperPositionInfo) {
        this.positionInfo = wallpaperPositionInfo;
    }

    public final void setResourceType(@Nullable String str) {
        this.resourceType = str;
    }

    public final void setSensorWallpaperPrams(@Nullable SensorWallpaperPrams sensorWallpaperPrams) {
        this.sensorWallpaperPrams = sensorWallpaperPrams;
    }

    public final void setSha1(@Nullable String str) {
        this.sha1 = str;
    }

    public final void setSource(@Nullable String str) {
        this.source = str;
    }

    public final void setSubject(@Nullable String str) {
        this.subject = str;
    }

    public final void setSubjectBitmap(@Nullable Bitmap bitmap) {
        this.subjectBitmap = bitmap;
    }

    public final void setSupportFullScreenAod(@Nullable Boolean bool) {
        this.isSupportFullScreenAod = bool;
    }

    public final void setSupportLoop(boolean z) {
        this.isSupportLoop = z;
    }

    public final void setSupportSubject(boolean z) {
        this.supportSubject = z;
    }

    public final void setVideoName(@Nullable String str) {
        this.videoName = str;
    }

    public final void setVideoSubjectBitmap(@Nullable Bitmap bitmap) {
        this.videoSubjectBitmap = bitmap;
    }

    public final void setVideoType(int i) {
        this.videoType = i;
    }

    public final void setVideoWallpaperBitmap(@Nullable Bitmap bitmap) {
        this.videoWallpaperBitmap = bitmap;
    }

    @NotNull
    public String toString() {
        return "WallpaperInfo(source=" + this.source + ", mask=" + this.mask + ", subject=" + this.subject + ", frontBack=" + this.frontBack + ", croppedFrontBack=" + this.croppedFrontBack + ", magicType=" + this.magicType + ", cropSubject=" + this.cropSubject + ", positionInfo=" + this.positionInfo + ", resourceType=" + this.resourceType + ", originResourcePath=" + this.originResourcePath + ", supportSubject=" + this.supportSubject + ", sensorWallpaperPrams=" + this.sensorWallpaperPrams + ", isNeedDark=" + this.isNeedDark + ", galleryContent=" + this.galleryContent + ", enableBlur=" + this.enableBlur + ", largeScreenHierarchyEnable=" + this.largeScreenHierarchyEnable + ", isSupportLoop=" + this.isSupportLoop + ", isDepthVideo=" + this.isDepthVideo + ", depthVideo=" + this.depthVideo + ", downloadUrl=" + this.downloadUrl + ", videoName=" + this.videoName + ", sha1=" + this.sha1 + ", fileSize=" + this.fileSize + ", magicTypeHome=" + this.magicTypeHome + ", enableBlurHome=" + this.enableBlurHome + ", enableDoodle=" + this.enableDoodle + ", videoType=" + this.videoType + ", extraInfo=" + this.extraInfo + ", isAiGenerated=" + this.isAiGenerated + ", isSupportFullScreenAod=" + this.isSupportFullScreenAod + ", imagePath=" + this.imagePath + ")";
    }

    public final void updateMagicType(int i) {
        this.magicType = i;
    }
}
