package com.miui.keyguard.editor.data.bean;

import android.content.Context;
import android.util.Log;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.allInOne.AllInOneUtil;
import com.miui.clock.module.ClockBean;
import com.miui.keyguard.editor.data.template.WallpaperInfoType;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.GsonSingletonKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
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
public final class CommonConfig {

    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private HomeConfig homeInfo;
    private transient boolean isOtaMigrated;
    private boolean isUpdated;

    @Nullable
    private String lastUpdate;

    @NotNull
    private TemplateConfig lockscreenInfo;
    private long updateTemplate;
    private int version;

    /* JADX INFO: compiled from: TemplateConfig.kt */
    @Metadata
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final void applyClockBeanToClockInfo(ClockBean clockBean, ClockInfo clockInfo) {
            clockInfo.setTemplateId(clockBean.getTemplateId());
            clockInfo.setStyle(clockBean.getStyle());
            clockInfo.setPrimaryColor(clockBean.getPrimaryColor());
            clockInfo.setAutoPrimaryColor(clockBean.isAutoPrimaryColor());
            clockInfo.setSecondaryColor(clockBean.getSecondaryColor());
            clockInfo.setAutoSecondaryColor(clockBean.isAutoSecondaryColor());
            clockInfo.setBlendColor(clockBean.getBlendColor());
            clockInfo.setSecondaryBlendColor(clockBean.getSecondaryBlendColor());
            clockInfo.setClockEffect(clockBean.getClockEffect());
            clockInfo.setClockWeight(clockBean.getClockWeight());
            clockInfo.setClassicLine1(clockBean.getClassicLine1());
            clockInfo.setClassicLine2(clockBean.getClassicLine2());
            clockInfo.setClassicLine3(clockBean.getClassicLine3());
            clockInfo.setClassicLine4(clockBean.getClassicLine4());
            clockInfo.setClassicLine5(clockBean.getClassicLine5());
            clockInfo.setClassicSignature(clockBean.getClassicSignature());
            clockInfo.setTimeWidth(clockBean.getTimeWidth());
            clockInfo.setTimeHeight(clockBean.getTimeHeight());
            clockInfo.setClockTopRatio(clockBean.getClockTopRatio());
            clockInfo.setClockBottomRatio(clockBean.getClockBottomRatio());
            clockInfo.setDoubleRow(clockBean.isDoubleRow());
            clockInfo.setClockDepthType(AllInOneClockStyle.toRegionInt(clockBean.getClockDepthType()));
            clockInfo.setGlassTransparency(clockBean.getGlassTransparency().floatValue());
            clockInfo.setFontStyle(clockBean.getFontStyle());
            clockInfo.setHollowStyle(clockBean.getHollowStyle());
            clockInfo.setColonShow(clockBean.isColonShow());
            clockInfo.setSkipScreenTransform(clockBean.isSkipScreenTransform());
        }

        private final ClockBean buildClockBeanFromClockInfo(ClockInfo clockInfo) {
            ClockBean clockBean = new ClockBean(clockInfo.getTemplateId());
            clockBean.setStyle(clockInfo.getStyle());
            clockBean.setPrimaryColor(clockInfo.getPrimaryColor());
            clockBean.setAutoPrimaryColor(clockInfo.isAutoPrimaryColor());
            clockBean.setSecondaryColor(clockInfo.getSecondaryColor());
            clockBean.setAutoSecondaryColor(clockInfo.isAutoSecondaryColor());
            clockBean.setBlendColor(clockInfo.getBlendColor());
            clockBean.setSecondaryBlendColor(clockInfo.getSecondaryBlendColor());
            clockBean.setClockEffect(clockInfo.getClockEffect());
            clockBean.setClockWeight(clockInfo.getClockWeight());
            clockBean.setClassicLine1(clockInfo.getClassicLine1());
            clockBean.setClassicLine2(clockInfo.getClassicLine2());
            clockBean.setClassicLine3(clockInfo.getClassicLine3());
            clockBean.setClassicLine4(clockInfo.getClassicLine4());
            clockBean.setClassicLine5(clockInfo.getClassicLine5());
            clockBean.setClassicSignature(clockInfo.getClassicSignature());
            clockBean.setTimeWidth(clockInfo.getTimeWidth());
            clockBean.setTimeHeight(clockInfo.getTimeHeight());
            clockBean.setClockTopRatio(clockInfo.getClockTopRatio());
            clockBean.setClockBottomRatio(clockInfo.getClockBottomRatio());
            clockBean.setDoubleRow(clockInfo.isDoubleRow());
            clockBean.setClockDepthType(AllInOneClockStyle.toRegion(clockInfo.getClockDepthType()));
            clockBean.setGlassTransparency(Float.valueOf(clockInfo.getGlassTransparency()));
            clockBean.setFontStyle(clockInfo.getFontStyle());
            clockBean.setHollowStyle(clockInfo.getHollowStyle());
            clockBean.setColonShow(clockInfo.isColonShow());
            return clockBean;
        }

        private final void fillQ18SensorOuterVideoForWallpaperInfo(WallpaperInfo wallpaperInfo) {
            SensorWallpaperPrams sensorWallpaperPrams;
            if (wallpaperInfo == null || (sensorWallpaperPrams = wallpaperInfo.getSensorWallpaperPrams()) == null || !WallpaperController.Companion.fillQ18SensorOuterVideoIfNeeded$default(WallpaperController.Companion, wallpaperInfo.getResourceType(), sensorWallpaperPrams, null, CollectionsKt.listOf((Object[]) new String[]{wallpaperInfo.getOriginResourcePath(), wallpaperInfo.getSource(), sensorWallpaperPrams.getOuterVideoPreViewPath(), sensorWallpaperPrams.getOuterVideoPath(), sensorWallpaperPrams.getSmallThumbnailPath(), sensorWallpaperPrams.getThumbnailPath()}), 4, null)) {
                return;
            }
            Log.i("TemplateConfig", "fillQ18SensorOuterVideoPath: resolved outerVideoPath=" + sensorWallpaperPrams.getOuterVideoPath() + ", outerVideoPreViewPath=" + sensorWallpaperPrams.getOuterVideoPreViewPath());
        }

        private final void fillQ18SensorOuterVideoPath(CommonConfig commonConfig) {
            if (DeviceUtil.INSTANCE.isQ18()) {
                fillQ18SensorOuterVideoForWallpaperInfo(commonConfig.getLockscreenInfo().getWallpaperInfo());
                fillQ18SensorOuterVideoForWallpaperInfo(commonConfig.getHomeInfo().getWallpaperInfo());
            }
        }

        public static /* synthetic */ CommonConfig fromJson$default(Companion companion, String str, String str2, Context context, int i, Object obj) {
            if ((i & 2) != 0) {
                str2 = null;
            }
            if ((i & 4) != 0) {
                context = null;
            }
            return companion.fromJson(str, str2, context);
        }

        private final void updateClassicFirstLine(ClockInfo clockInfo) {
            if (Intrinsics.areEqual(clockInfo.getTemplateId(), "classic")) {
                if (clockInfo.getClassicLine1() != 11) {
                    return;
                } else {
                    clockInfo.setClassicLine1(DeviceUtil.INSTANCE.isInternationalBuild() ? 0 : 101);
                }
            } else if (Intrinsics.areEqual(clockInfo.getTemplateId(), "classic_plus")) {
                clockInfo.setClassicLine1(clockInfo.getClassicLine3() == 11 ? 100 : clockInfo.getClassicLine3());
            }
            Log.d("TemplateConfig", "updateClassicFirstLine: clockInfo.classicLine1=" + clockInfo.getClassicLine1());
        }

        private final void updateCurrentTemplateWithOTA(TemplateConfig templateConfig) {
            Log.d("TemplateConfig", "version=" + templateConfig.getVersion() + ", updateCurrentTemplateWithOTA: templateId=" + templateConfig.getClockInfo().getTemplateId() + ", clockInfo.style=" + templateConfig.getClockInfo().getStyle());
            templateConfig.setVersion(3);
            if (Intrinsics.areEqual(templateConfig.getClockInfo().getTemplateId(), "classic_plus")) {
                switch (templateConfig.getClockInfo().getStyle()) {
                    case 21:
                        templateConfig.getClockInfo().setStyle(21);
                        templateConfig.getClockInfo().setClockWeight(305);
                        break;
                    case 22:
                        templateConfig.getClockInfo().setStyle(21);
                        templateConfig.getClockInfo().setClockWeight(250);
                        break;
                    case 23:
                        templateConfig.getClockInfo().setStyle(21);
                        templateConfig.getClockInfo().setClockWeight(450);
                        break;
                    case 24:
                        templateConfig.getClockInfo().setStyle(22);
                        templateConfig.getClockInfo().setClockWeight(420);
                        break;
                    case 25:
                        templateConfig.getClockInfo().setStyle(24);
                        templateConfig.getClockInfo().setClockWeight(330);
                        break;
                    case 26:
                        templateConfig.getClockInfo().setStyle(23);
                        templateConfig.getClockInfo().setClockWeight(450);
                        break;
                    case 27:
                        templateConfig.getClockInfo().setStyle(21);
                        templateConfig.getClockInfo().setClockWeight(630);
                        break;
                    case 28:
                        templateConfig.getClockInfo().setStyle(25);
                        templateConfig.getClockInfo().setClockWeight(520);
                        break;
                    default:
                        templateConfig.getClockInfo().setStyle(templateConfig.getClockInfo().getStyle());
                        templateConfig.getClockInfo().setClockWeight(templateConfig.getClockInfo().getClockWeight());
                        break;
                }
            }
            updateClassicFirstLine(templateConfig.getClockInfo());
        }

        @NotNull
        public final CommonConfig fromJson(@NotNull String json, @Nullable String str, @Nullable Context context) {
            CommonConfig commonConfigFromOTA;
            Intrinsics.checkNotNullParameter(json, "json");
            try {
                commonConfigFromOTA = (CommonConfig) GsonSingletonKt.getGSON_SINGLETON().fromJson(json, CommonConfig.class);
            } catch (Exception e) {
                Log.d("TemplateConfig", "fromJson: error:" + e + " json=" + json);
                json = str;
                commonConfigFromOTA = null;
            }
            if ((commonConfigFromOTA != null ? commonConfigFromOTA.getLockscreenInfo() : null) == null) {
                TemplateConfig templateConfig = (TemplateConfig) GsonSingletonKt.getGSON_SINGLETON().fromJson(json, TemplateConfig.class);
                Log.d("TemplateConfig", "OTA json: oldConfig=" + templateConfig);
                Intrinsics.checkNotNull(templateConfig);
                commonConfigFromOTA = fromOTA(templateConfig);
            } else if (commonConfigFromOTA.getHomeInfo() == null) {
                commonConfigFromOTA.setVersion(2);
                WallpaperInfo wallpaperInfo = commonConfigFromOTA.getLockscreenInfo().getWallpaperInfo();
                WallpaperInfo wallpaperInfoDeepCopy = wallpaperInfo != null ? wallpaperInfo.deepCopy() : null;
                WallpaperInfo wallpaperInfoForVideo = commonConfigFromOTA.getLockscreenInfo().getWallpaperInfoForVideo();
                commonConfigFromOTA.setHomeInfo(new HomeConfig(0, wallpaperInfoDeepCopy, wallpaperInfoForVideo != null ? wallpaperInfoForVideo.deepCopy() : null, commonConfigFromOTA.getLockscreenInfo().getHideParam()));
            }
            if (commonConfigFromOTA.getLockscreenInfo().getVersion() < 3) {
                updateCurrentTemplateWithOTA(commonConfigFromOTA.getLockscreenInfo());
            }
            if (commonConfigFromOTA.getLockscreenInfo().getVersion() < 4 && context != null && DeviceUtil.INSTANCE.osVersion() >= 4) {
                try {
                    migrateClockForOTA(context, commonConfigFromOTA);
                } catch (Exception e2) {
                    Log.w("TemplateConfig", "fromJson: OTA clock migration failed, will retry next time", e2);
                }
            }
            fillQ18SensorOuterVideoPath(commonConfigFromOTA);
            ClockInfo clockInfo = commonConfigFromOTA.getLockscreenInfo().getClockInfo();
            if (!clockInfo.isAutoPrimaryColor() && clockInfo.getPrimaryFontColorPanelId() == 0) {
                clockInfo.setPrimaryFontColorPanelId(13);
            }
            if (!clockInfo.isAutoSecondaryColor() && clockInfo.getSecondaryFontColorPanelId() == 0) {
                clockInfo.setSecondaryFontColorPanelId(13);
            }
            WallpaperInfo wallpaperInfo2 = commonConfigFromOTA.getLockscreenInfo().getWallpaperInfo();
            if (wallpaperInfo2 != null) {
                wallpaperInfo2.setInfoType(WallpaperInfoType.LOCK_WALLPAPER);
            }
            WallpaperInfo wallpaperInfo3 = commonConfigFromOTA.getHomeInfo().getWallpaperInfo();
            if (wallpaperInfo3 != null) {
                wallpaperInfo3.setInfoType(WallpaperInfoType.HOME_WALLPAPER);
            }
            return commonConfigFromOTA;
        }

        @NotNull
        public final CommonConfig fromOTA(@NotNull TemplateConfig config) {
            Intrinsics.checkNotNullParameter(config, "config");
            WallpaperInfo wallpaperInfo = config.getWallpaperInfo();
            WallpaperInfo wallpaperInfoDeepCopy = wallpaperInfo != null ? wallpaperInfo.deepCopy() : null;
            WallpaperInfo wallpaperInfoForVideo = config.getWallpaperInfoForVideo();
            HomeConfig homeConfig = new HomeConfig(2, wallpaperInfoDeepCopy, wallpaperInfoForVideo != null ? wallpaperInfoForVideo.deepCopy() : null, config.getHideParam());
            WallpaperInfo wallpaperInfo2 = homeConfig.getWallpaperInfo();
            if (wallpaperInfo2 != null) {
                wallpaperInfo2.setMagicType(0);
            }
            WallpaperInfo wallpaperInfo3 = homeConfig.getWallpaperInfo();
            if (wallpaperInfo3 != null) {
                wallpaperInfo3.setEnableBlur(false);
            }
            CommonConfig commonConfig = new CommonConfig(config.getLastUpdate(), System.currentTimeMillis(), config.getVersion(), config, homeConfig);
            WallpaperInfo wallpaperInfo4 = commonConfig.getLockscreenInfo().getWallpaperInfo();
            if (wallpaperInfo4 != null) {
                wallpaperInfo4.setInfoType(WallpaperInfoType.LOCK_WALLPAPER);
            }
            WallpaperInfo wallpaperInfo5 = commonConfig.getHomeInfo().getWallpaperInfo();
            if (wallpaperInfo5 != null) {
                wallpaperInfo5.setInfoType(WallpaperInfoType.HOME_WALLPAPER);
            }
            return commonConfig;
        }

        public final void migrateClockForOTA(@NotNull Context context, @NotNull CommonConfig config) throws Exception {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(config, "config");
            ClockInfo clockInfo = config.getLockscreenInfo().getClockInfo();
            int version = config.getLockscreenInfo().getVersion();
            Log.d("TemplateConfig", "migrateClockForOTA: start templateId=" + clockInfo.getTemplateId() + ", style=" + clockInfo.getStyle() + ", version=" + version);
            try {
                applyClockBeanToClockInfo(AllInOneUtil.dealWithOTAClockBean(buildClockBeanFromClockInfo(clockInfo), context), clockInfo);
                config.getLockscreenInfo().setVersion(4);
                config.setVersion(4);
                config.setOtaMigrated(true);
                Log.d("TemplateConfig", "migrateClockForOTA: success, new templateId = " + clockInfo.getTemplateId() + " new style=" + clockInfo.getStyle());
            } catch (Exception e) {
                config.getLockscreenInfo().setVersion(version);
                config.setVersion(version);
                config.setOtaMigrated(false);
                Log.e("TemplateConfig", "migrateClockForOTA: failed, version rollback to " + version, e);
                throw e;
            }
        }
    }

    public CommonConfig(@Nullable String str, long j, int i, @NotNull TemplateConfig lockscreenInfo, @NotNull HomeConfig homeInfo) {
        Intrinsics.checkNotNullParameter(lockscreenInfo, "lockscreenInfo");
        Intrinsics.checkNotNullParameter(homeInfo, "homeInfo");
        this.lastUpdate = str;
        this.updateTemplate = j;
        this.version = i;
        this.lockscreenInfo = lockscreenInfo;
        this.homeInfo = homeInfo;
    }

    public static /* synthetic */ CommonConfig copy$default(CommonConfig commonConfig, String str, long j, int i, TemplateConfig templateConfig, HomeConfig homeConfig, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = commonConfig.lastUpdate;
        }
        if ((i2 & 2) != 0) {
            j = commonConfig.updateTemplate;
        }
        if ((i2 & 4) != 0) {
            i = commonConfig.version;
        }
        if ((i2 & 8) != 0) {
            templateConfig = commonConfig.lockscreenInfo;
        }
        if ((i2 & 16) != 0) {
            homeConfig = commonConfig.homeInfo;
        }
        HomeConfig homeConfig2 = homeConfig;
        int i3 = i;
        return commonConfig.copy(str, j, i3, templateConfig, homeConfig2);
    }

    @Nullable
    public final String component1() {
        return this.lastUpdate;
    }

    public final long component2() {
        return this.updateTemplate;
    }

    public final int component3() {
        return this.version;
    }

    @NotNull
    public final TemplateConfig component4() {
        return this.lockscreenInfo;
    }

    @NotNull
    public final HomeConfig component5() {
        return this.homeInfo;
    }

    @NotNull
    public final CommonConfig copy(@Nullable String str, long j, int i, @NotNull TemplateConfig lockscreenInfo, @NotNull HomeConfig homeInfo) {
        Intrinsics.checkNotNullParameter(lockscreenInfo, "lockscreenInfo");
        Intrinsics.checkNotNullParameter(homeInfo, "homeInfo");
        return new CommonConfig(str, j, i, lockscreenInfo, homeInfo);
    }

    @NotNull
    public final CommonConfig deepCopy() {
        TemplateConfig templateConfigDeepCopy = this.lockscreenInfo.deepCopy();
        HomeConfig homeConfigCopy$default = HomeConfig.copy$default(this.homeInfo, 0, null, null, 0, 15, null);
        CommonConfig commonConfigCopy$default = copy$default(this, null, 0L, 0, null, null, 31, null);
        commonConfigCopy$default.lockscreenInfo = templateConfigDeepCopy;
        commonConfigCopy$default.homeInfo = homeConfigCopy$default;
        return commonConfigCopy$default;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CommonConfig)) {
            return false;
        }
        CommonConfig commonConfig = (CommonConfig) obj;
        return Intrinsics.areEqual(this.lastUpdate, commonConfig.lastUpdate) && this.updateTemplate == commonConfig.updateTemplate && this.version == commonConfig.version && Intrinsics.areEqual(this.lockscreenInfo, commonConfig.lockscreenInfo) && Intrinsics.areEqual(this.homeInfo, commonConfig.homeInfo);
    }

    @NotNull
    public final HomeConfig getHomeInfo() {
        return this.homeInfo;
    }

    @Nullable
    public final String getLastUpdate() {
        return this.lastUpdate;
    }

    @NotNull
    public final TemplateConfig getLockscreenInfo() {
        return this.lockscreenInfo;
    }

    public final long getUpdateTemplate() {
        return this.updateTemplate;
    }

    public final int getVersion() {
        return this.version;
    }

    public int hashCode() {
        String str = this.lastUpdate;
        return ((((((((str == null ? 0 : str.hashCode()) * 31) + Long.hashCode(this.updateTemplate)) * 31) + Integer.hashCode(this.version)) * 31) + this.lockscreenInfo.hashCode()) * 31) + this.homeInfo.hashCode();
    }

    public final boolean isOtaMigrated() {
        return this.isOtaMigrated;
    }

    public final boolean isUpdated() {
        return this.isUpdated;
    }

    public final void setHomeInfo(@NotNull HomeConfig homeConfig) {
        Intrinsics.checkNotNullParameter(homeConfig, "<set-?>");
        this.homeInfo = homeConfig;
    }

    public final void setLastUpdate(@Nullable String str) {
        this.lastUpdate = str;
    }

    public final void setLockscreenInfo(@NotNull TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "<set-?>");
        this.lockscreenInfo = templateConfig;
    }

    public final void setOtaMigrated(boolean z) {
        this.isOtaMigrated = z;
    }

    public final void setUpdateTemplate(long j) {
        this.updateTemplate = j;
    }

    public final void setUpdated(boolean z) {
        this.isUpdated = z;
    }

    public final void setVersion(int i) {
        this.version = i;
    }

    @NotNull
    public String toString() {
        return "CommonConfig(lastUpdate=" + this.lastUpdate + ", updateTemplate=" + this.updateTemplate + ", version=" + this.version + ", lockscreenInfo=" + this.lockscreenInfo + ", homeInfo=" + this.homeInfo + ")";
    }
}
