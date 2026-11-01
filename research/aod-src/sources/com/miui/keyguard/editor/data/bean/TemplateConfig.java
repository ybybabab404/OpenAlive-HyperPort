package com.miui.keyguard.editor.data.bean;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import com.miui.keyguard.editor.base.TemplateEnvironment;
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
/* JADX INFO: compiled from: TemplateConfig.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class TemplateConfig {
    private transient boolean applyToDeskTop;

    @Nullable
    private transient Bundle clockExtras;

    @NotNull
    private ClockInfo clockInfo;

    @Nullable
    private transient Bitmap composedWallpaper;

    @Nullable
    private transient Bitmap darkenWallpaper;

    @Nullable
    private DoodleInfo doodle;

    @Nullable
    private transient TemplateEnvironment environment;
    private int hideFlag;
    private int hideParam;
    private transient boolean isThirdPartyTheme;
    private transient boolean isThirdPartyWallpaper;

    @Nullable
    private String lastUpdate;
    private int randomColorType;

    @Nullable
    private SignatureInfo signatureInfo;

    @Nullable
    private final SmartFrameInfo smartFrame;

    @Nullable
    private String templateName;
    private int version;

    @Nullable
    private WallpaperInfo wallpaperInfo;

    @Nullable
    private WallpaperInfo wallpaperInfoForVideo;

    public TemplateConfig(int i, @NotNull ClockInfo clockInfo, @Nullable SignatureInfo signatureInfo, @Nullable WallpaperInfo wallpaperInfo, @Nullable WallpaperInfo wallpaperInfo2, @Nullable DoodleInfo doodleInfo, @Nullable SmartFrameInfo smartFrameInfo, @Nullable String str, @Nullable String str2, int i2, int i3) {
        Intrinsics.checkNotNullParameter(clockInfo, "clockInfo");
        this.version = i;
        this.clockInfo = clockInfo;
        this.signatureInfo = signatureInfo;
        this.wallpaperInfo = wallpaperInfo;
        this.wallpaperInfoForVideo = wallpaperInfo2;
        this.doodle = doodleInfo;
        this.smartFrame = smartFrameInfo;
        this.templateName = str;
        this.lastUpdate = str2;
        this.hideParam = i2;
        this.hideFlag = i3;
    }

    public /* synthetic */ TemplateConfig(int i, ClockInfo clockInfo, SignatureInfo signatureInfo, WallpaperInfo wallpaperInfo, WallpaperInfo wallpaperInfo2, DoodleInfo doodleInfo, SmartFrameInfo smartFrameInfo, String str, String str2, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, clockInfo, signatureInfo, wallpaperInfo, wallpaperInfo2, doodleInfo, smartFrameInfo, str, str2, (i4 & 512) != 0 ? 0 : i2, (i4 & 1024) != 0 ? 0 : i3);
    }

    public static /* synthetic */ TemplateConfig copy$default(TemplateConfig templateConfig, int i, ClockInfo clockInfo, SignatureInfo signatureInfo, WallpaperInfo wallpaperInfo, WallpaperInfo wallpaperInfo2, DoodleInfo doodleInfo, SmartFrameInfo smartFrameInfo, String str, String str2, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i = templateConfig.version;
        }
        if ((i4 & 2) != 0) {
            clockInfo = templateConfig.clockInfo;
        }
        if ((i4 & 4) != 0) {
            signatureInfo = templateConfig.signatureInfo;
        }
        if ((i4 & 8) != 0) {
            wallpaperInfo = templateConfig.wallpaperInfo;
        }
        if ((i4 & 16) != 0) {
            wallpaperInfo2 = templateConfig.wallpaperInfoForVideo;
        }
        if ((i4 & 32) != 0) {
            doodleInfo = templateConfig.doodle;
        }
        if ((i4 & 64) != 0) {
            smartFrameInfo = templateConfig.smartFrame;
        }
        if ((i4 & 128) != 0) {
            str = templateConfig.templateName;
        }
        if ((i4 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0) {
            str2 = templateConfig.lastUpdate;
        }
        if ((i4 & 512) != 0) {
            i2 = templateConfig.hideParam;
        }
        if ((i4 & 1024) != 0) {
            i3 = templateConfig.hideFlag;
        }
        int i5 = i2;
        int i6 = i3;
        String str3 = str;
        String str4 = str2;
        DoodleInfo doodleInfo2 = doodleInfo;
        SmartFrameInfo smartFrameInfo2 = smartFrameInfo;
        WallpaperInfo wallpaperInfo3 = wallpaperInfo2;
        SignatureInfo signatureInfo2 = signatureInfo;
        return templateConfig.copy(i, clockInfo, signatureInfo2, wallpaperInfo, wallpaperInfo3, doodleInfo2, smartFrameInfo2, str3, str4, i5, i6);
    }

    public final int component1() {
        return this.version;
    }

    public final int component10() {
        return this.hideParam;
    }

    public final int component11() {
        return this.hideFlag;
    }

    @NotNull
    public final ClockInfo component2() {
        return this.clockInfo;
    }

    @Nullable
    public final SignatureInfo component3() {
        return this.signatureInfo;
    }

    @Nullable
    public final WallpaperInfo component4() {
        return this.wallpaperInfo;
    }

    @Nullable
    public final WallpaperInfo component5() {
        return this.wallpaperInfoForVideo;
    }

    @Nullable
    public final DoodleInfo component6() {
        return this.doodle;
    }

    @Nullable
    public final SmartFrameInfo component7() {
        return this.smartFrame;
    }

    @Nullable
    public final String component8() {
        return this.templateName;
    }

    @Nullable
    public final String component9() {
        return this.lastUpdate;
    }

    @NotNull
    public final TemplateConfig copy(int i, @NotNull ClockInfo clockInfo, @Nullable SignatureInfo signatureInfo, @Nullable WallpaperInfo wallpaperInfo, @Nullable WallpaperInfo wallpaperInfo2, @Nullable DoodleInfo doodleInfo, @Nullable SmartFrameInfo smartFrameInfo, @Nullable String str, @Nullable String str2, int i2, int i3) {
        Intrinsics.checkNotNullParameter(clockInfo, "clockInfo");
        return new TemplateConfig(i, clockInfo, signatureInfo, wallpaperInfo, wallpaperInfo2, doodleInfo, smartFrameInfo, str, str2, i2, i3);
    }

    @NotNull
    public final TemplateConfig deepCopy() {
        TemplateConfig templateConfigCopy$default = copy$default(this, 0, null, null, null, null, null, null, null, null, 0, 0, 2047, null);
        WallpaperInfo wallpaperInfo = this.wallpaperInfo;
        if (!TextUtils.isEmpty(wallpaperInfo != null ? wallpaperInfo.getSource() : null)) {
            WallpaperInfo wallpaperInfo2 = this.wallpaperInfo;
            WallpaperInfo wallpaperInfoCopy$default = wallpaperInfo2 != null ? WallpaperInfo.copy$default(wallpaperInfo2, null, null, null, null, null, 0, null, null, null, null, false, null, false, null, false, null, false, false, null, null, null, null, null, 0, false, 0, 0, null, false, null, null, Integer.MAX_VALUE, null) : null;
            templateConfigCopy$default.wallpaperInfo = wallpaperInfoCopy$default;
            if (wallpaperInfoCopy$default != null) {
                WallpaperInfo wallpaperInfo3 = this.wallpaperInfo;
                wallpaperInfoCopy$default.setCurrentWallpaper(wallpaperInfo3 != null ? wallpaperInfo3.getCurrentWallpaper() : null);
            }
            WallpaperInfo wallpaperInfo4 = templateConfigCopy$default.wallpaperInfo;
            if (wallpaperInfo4 != null) {
                WallpaperInfo wallpaperInfo5 = this.wallpaperInfo;
                wallpaperInfo4.setCurrentSmallSensorWallpaper(wallpaperInfo5 != null ? wallpaperInfo5.getCurrentSmallSensorWallpaper() : null);
            }
            WallpaperInfo wallpaperInfo6 = templateConfigCopy$default.wallpaperInfo;
            if (wallpaperInfo6 != null) {
                WallpaperInfo wallpaperInfo7 = this.wallpaperInfo;
                wallpaperInfo6.setCurrentLockWallpaperSource(wallpaperInfo7 != null ? wallpaperInfo7.getCurrentLockWallpaperSource() : null);
            }
            WallpaperInfo wallpaperInfo8 = templateConfigCopy$default.wallpaperInfo;
            if (wallpaperInfo8 != null) {
                WallpaperInfo wallpaperInfo9 = this.wallpaperInfo;
                wallpaperInfo8.setSubjectBitmap(wallpaperInfo9 != null ? wallpaperInfo9.getSubjectBitmap() : null);
            }
            WallpaperInfo wallpaperInfo10 = templateConfigCopy$default.wallpaperInfo;
            if (wallpaperInfo10 != null) {
                WallpaperInfo wallpaperInfo11 = this.wallpaperInfo;
                wallpaperInfo10.setFrontBackBitmap(wallpaperInfo11 != null ? wallpaperInfo11.getFrontBackBitmap() : null);
            }
            WallpaperInfo wallpaperInfo12 = templateConfigCopy$default.wallpaperInfo;
            if (wallpaperInfo12 != null) {
                WallpaperInfo wallpaperInfo13 = this.wallpaperInfo;
                wallpaperInfo12.setCroppedFrontBackBitmap(wallpaperInfo13 != null ? wallpaperInfo13.getCroppedFrontBackBitmap() : null);
            }
        }
        ClockInfo clockInfo = this.clockInfo;
        templateConfigCopy$default.clockInfo = clockInfo.copy(((-1) & 1) != 0 ? clockInfo.templateId : null, ((-1) & 2) != 0 ? clockInfo.primaryColor : 0, ((-1) & 4) != 0 ? clockInfo.secondaryColor : 0, ((-1) & 8) != 0 ? clockInfo.blendColor : 0, ((-1) & 16) != 0 ? clockInfo.secondaryBlendColor : 0, ((-1) & 32) != 0 ? clockInfo.clockEffect : 0, ((-1) & 64) != 0 ? clockInfo.style : 0, ((-1) & 128) != 0 ? clockInfo.enableDiffusion : false, ((-1) & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? clockInfo.classicLine1 : 0, ((-1) & 512) != 0 ? clockInfo.classicLine2 : 0, ((-1) & 1024) != 0 ? clockInfo.classicLine3 : 0, ((-1) & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0 ? clockInfo.classicLine4 : 0, ((-1) & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0 ? clockInfo.classicLine5 : 0, ((-1) & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0 ? clockInfo.primaryFontColorPanelId : 0, ((-1) & 16384) != 0 ? clockInfo.secondaryFontColorPanelId : 0, ((-1) & 32768) != 0 ? clockInfo.classicSignature : null, ((-1) & 65536) != 0 ? clockInfo.signatureLine1 : null, ((-1) & 131072) != 0 ? clockInfo.signatureLine2 : null, ((-1) & 262144) != 0 ? clockInfo.signatureLine3 : null, ((-1) & 524288) != 0 ? clockInfo.isAutoPrimaryColor : false, ((-1) & 1048576) != 0 ? clockInfo.isAutoSecondaryColor : false, ((-1) & 2097152) != 0 ? clockInfo.isDiffHourMinuteColor : false, ((-1) & 4194304) != 0 ? clockInfo.primaryColorValueInfo : null, ((-1) & 8388608) != 0 ? clockInfo.secondaryColorValueInfo : null, ((-1) & 16777216) != 0 ? clockInfo.isDarkClockArea : false, ((-1) & 33554432) != 0 ? clockInfo.clockWeight : 0, ((-1) & 67108864) != 0 ? clockInfo.extraFlag : 0, ((-1) & 134217728) != 0 ? clockInfo.clockTopRatio : 0.0f, ((-1) & 268435456) != 0 ? clockInfo.clockBottomRatio : 0.0f, ((-1) & 536870912) != 0 ? clockInfo.timeWidth : 0.0f, ((-1) & 1073741824) != 0 ? clockInfo.timeHeight : 0.0f, ((-1) & Integer.MIN_VALUE) != 0 ? clockInfo.clockDepthType : 0, (255 & 1) != 0 ? clockInfo.glassTransparency : 0.0f, (255 & 2) != 0 ? clockInfo.fontStyle : 0, (255 & 4) != 0 ? clockInfo.hollowStyle : 0, (255 & 8) != 0 ? clockInfo.isDoubleRow : false, (255 & 16) != 0 ? clockInfo.isColonShow : false, (255 & 32) != 0 ? clockInfo.isSkipScreenTransform : false, (255 & 64) != 0 ? clockInfo.userModifiedPrimaryColor : false, (255 & 128) != 0 ? clockInfo.userModifiedSecondaryColor : false);
        SignatureInfo signatureInfo = this.signatureInfo;
        templateConfigCopy$default.signatureInfo = signatureInfo != null ? SignatureInfo.copy$default(signatureInfo, null, null, 0, 0, 0, 0, 0, false, 0, 0.0f, 0.0f, 0.0f, 4095, null) : null;
        templateConfigCopy$default.randomColorType = this.randomColorType;
        templateConfigCopy$default.clockExtras = this.clockExtras;
        DoodleInfo doodleInfo = this.doodle;
        templateConfigCopy$default.doodle = doodleInfo != null ? DoodleInfo.copy$default(doodleInfo, 0, false, 0, 0, 0, 31, null) : null;
        templateConfigCopy$default.isThirdPartyWallpaper = this.isThirdPartyWallpaper;
        templateConfigCopy$default.isThirdPartyTheme = this.isThirdPartyTheme;
        templateConfigCopy$default.lastUpdate = this.lastUpdate;
        return templateConfigCopy$default;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TemplateConfig)) {
            return false;
        }
        TemplateConfig templateConfig = (TemplateConfig) obj;
        return this.version == templateConfig.version && Intrinsics.areEqual(this.clockInfo, templateConfig.clockInfo) && Intrinsics.areEqual(this.signatureInfo, templateConfig.signatureInfo) && Intrinsics.areEqual(this.wallpaperInfo, templateConfig.wallpaperInfo) && Intrinsics.areEqual(this.wallpaperInfoForVideo, templateConfig.wallpaperInfoForVideo) && Intrinsics.areEqual(this.doodle, templateConfig.doodle) && Intrinsics.areEqual(this.smartFrame, templateConfig.smartFrame) && Intrinsics.areEqual(this.templateName, templateConfig.templateName) && Intrinsics.areEqual(this.lastUpdate, templateConfig.lastUpdate) && this.hideParam == templateConfig.hideParam && this.hideFlag == templateConfig.hideFlag;
    }

    public final boolean getApplyToDeskTop() {
        return this.applyToDeskTop;
    }

    @Nullable
    public final Bundle getClockExtras() {
        return this.clockExtras;
    }

    @NotNull
    public final ClockInfo getClockInfo() {
        return this.clockInfo;
    }

    @Nullable
    public final Bitmap getComposedWallpaper() {
        return this.composedWallpaper;
    }

    @Nullable
    public final Bitmap getDarkenWallpaper() {
        return this.darkenWallpaper;
    }

    @Nullable
    public final DoodleInfo getDoodle() {
        return this.doodle;
    }

    @Nullable
    public final TemplateEnvironment getEnvironment() {
        return this.environment;
    }

    public final int getHideFlag() {
        return this.hideFlag;
    }

    public final int getHideParam() {
        return this.hideParam;
    }

    @Nullable
    public final String getLastUpdate() {
        return this.lastUpdate;
    }

    public final int getRandomColorType() {
        return this.randomColorType;
    }

    @Nullable
    public final SignatureInfo getSignatureInfo() {
        return this.signatureInfo;
    }

    @Nullable
    public final SmartFrameInfo getSmartFrame() {
        return this.smartFrame;
    }

    @Nullable
    public final String getTemplateName() {
        return this.templateName;
    }

    public final int getVersion() {
        return this.version;
    }

    @Nullable
    public final WallpaperInfo getWallpaperInfo() {
        return this.wallpaperInfo;
    }

    @Nullable
    public final WallpaperInfo getWallpaperInfoForVideo() {
        return this.wallpaperInfoForVideo;
    }

    public int hashCode() {
        int iHashCode = ((Integer.hashCode(this.version) * 31) + this.clockInfo.hashCode()) * 31;
        SignatureInfo signatureInfo = this.signatureInfo;
        int iHashCode2 = (iHashCode + (signatureInfo == null ? 0 : signatureInfo.hashCode())) * 31;
        WallpaperInfo wallpaperInfo = this.wallpaperInfo;
        int iHashCode3 = (iHashCode2 + (wallpaperInfo == null ? 0 : wallpaperInfo.hashCode())) * 31;
        WallpaperInfo wallpaperInfo2 = this.wallpaperInfoForVideo;
        int iHashCode4 = (iHashCode3 + (wallpaperInfo2 == null ? 0 : wallpaperInfo2.hashCode())) * 31;
        DoodleInfo doodleInfo = this.doodle;
        int iHashCode5 = (iHashCode4 + (doodleInfo == null ? 0 : doodleInfo.hashCode())) * 31;
        SmartFrameInfo smartFrameInfo = this.smartFrame;
        int iHashCode6 = (iHashCode5 + (smartFrameInfo == null ? 0 : smartFrameInfo.hashCode())) * 31;
        String str = this.templateName;
        int iHashCode7 = (iHashCode6 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.lastUpdate;
        return ((((iHashCode7 + (str2 != null ? str2.hashCode() : 0)) * 31) + Integer.hashCode(this.hideParam)) * 31) + Integer.hashCode(this.hideFlag);
    }

    public final boolean isPresetTemplate() {
        return (this.wallpaperInfo == null || this.wallpaperInfoForVideo == null) ? false : true;
    }

    public final boolean isThirdPartyTheme() {
        return this.isThirdPartyTheme;
    }

    public final boolean isThirdPartyWallpaper() {
        return this.isThirdPartyWallpaper;
    }

    public final void setApplyToDeskTop(boolean z) {
        this.applyToDeskTop = z;
    }

    public final void setClockExtras(@Nullable Bundle bundle) {
        this.clockExtras = bundle;
    }

    public final void setClockInfo(@NotNull ClockInfo clockInfo) {
        Intrinsics.checkNotNullParameter(clockInfo, "<set-?>");
        this.clockInfo = clockInfo;
    }

    public final void setComposedWallpaper(@Nullable Bitmap bitmap) {
        this.composedWallpaper = bitmap;
    }

    public final void setDarkenWallpaper(@Nullable Bitmap bitmap) {
        this.darkenWallpaper = bitmap;
    }

    public final void setDoodle(@Nullable DoodleInfo doodleInfo) {
        this.doodle = doodleInfo;
    }

    public final void setEnvironment(@Nullable TemplateEnvironment templateEnvironment) {
        this.environment = templateEnvironment;
    }

    public final void setHideFlag(int i) {
        this.hideFlag = i;
    }

    public final void setHideParam(int i) {
        this.hideParam = i;
    }

    public final void setLastUpdate(@Nullable String str) {
        this.lastUpdate = str;
    }

    public final void setRandomColorType(int i) {
        this.randomColorType = i;
    }

    public final void setSignatureInfo(@Nullable SignatureInfo signatureInfo) {
        this.signatureInfo = signatureInfo;
    }

    public final void setTemplateName(@Nullable String str) {
        this.templateName = str;
    }

    public final void setThirdPartyTheme(boolean z) {
        this.isThirdPartyTheme = z;
    }

    public final void setThirdPartyWallpaper(boolean z) {
        this.isThirdPartyWallpaper = z;
    }

    public final void setVersion(int i) {
        this.version = i;
    }

    public final void setWallpaperInfo(@Nullable WallpaperInfo wallpaperInfo) {
        this.wallpaperInfo = wallpaperInfo;
    }

    public final void setWallpaperInfoForVideo(@Nullable WallpaperInfo wallpaperInfo) {
        this.wallpaperInfoForVideo = wallpaperInfo;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder("TemplateConfig{");
        sb.append("wallpaperInfo?.currentWallpaper='");
        WallpaperInfo wallpaperInfo = this.wallpaperInfo;
        sb.append(wallpaperInfo != null ? wallpaperInfo.getCurrentWallpaper() : null);
        sb.append('\'');
        sb.append("randomColorType='");
        sb.append(this.randomColorType);
        sb.append('\'');
        sb.append("isThirdPartyWallpaper='");
        sb.append(this.isThirdPartyWallpaper);
        sb.append('\'');
        sb.append("isThirdPartyTheme='");
        sb.append(this.isThirdPartyTheme);
        sb.append('\'');
        sb.append("darkenWallpaper='");
        sb.append(this.darkenWallpaper);
        sb.append('\'');
        sb.append("wallpaperInfo='");
        sb.append(this.wallpaperInfo);
        sb.append('\'');
        sb.append("clockInfo='");
        sb.append(this.clockInfo);
        sb.append('\'');
        sb.append("clockExtras='");
        sb.append(this.clockExtras);
        sb.append('\'');
        sb.append("signatureInfo='");
        sb.append(this.signatureInfo);
        sb.append('\'');
        sb.append('}');
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
