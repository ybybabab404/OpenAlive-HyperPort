package com.miui.clock.module;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import com.miui.clock.aesthetics.MiuiAestheticsClockInfo;
import com.miui.clock.aesthetics.MiuiAestheticsHourClockInfo;
import com.miui.clock.aesthetics.MiuiAestheticsNotificationClockInfo;
import com.miui.clock.aesthetics.MiuiAestheticsSingleClockInfo;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.allInOne.AllInOneHourInfo;
import com.miui.clock.allInOne.AllInOneMinuteInfo;
import com.miui.clock.allInOne.AllInOneSingleClockInfo;
import com.miui.clock.eastern.b.EasternArtBAodInfo;
import com.miui.clock.eastern.b.EasternArtBDataUpperClockInfo;
import com.miui.clock.eastern.b.EasternArtBNotificationInfo;
import com.miui.clock.eastern.b.EasternArtBTimeBelowInfo;
import com.miui.clock.eastern.b.EasternArtBVerticalAodInfo;
import com.miui.clock.eastern.b.EasternArtBVerticalDataUpInfo;
import com.miui.clock.eastern.b.EasternArtBVerticalTimeBelowInfo;
import com.miui.clock.eastern.c.clokcinfo.EasternArtCAodInfo;
import com.miui.clock.eastern.c.clokcinfo.EasternArtCHourInfo;
import com.miui.clock.eastern.c.clokcinfo.EasternArtCMinuteInfo;
import com.miui.clock.eastern.c.clokcinfo.EasternArtCSingleInfo;
import com.miui.clock.eastern.d.EasternArtDAodInfo;
import com.miui.clock.eastern.d.EasternArtDHourInfo;
import com.miui.clock.eastern.d.EasternArtDMinuteInfo;
import com.miui.clock.graffiti.MiuiGraffitiAodClockInfo;
import com.miui.clock.graffiti.MiuiGraffitiClockInfo;
import com.miui.clock.magazine.MiuiMagazineAClockInfo;
import com.miui.clock.magazine.MiuiMagazineANotificationClockInfo;
import com.miui.clock.magazine.MiuiMagazineASingleClockInfo;
import com.miui.clock.magazine.MiuiMagazineBClockInfo;
import com.miui.clock.magazine.MiuiMagazineBNotificationClockInfo;
import com.miui.clock.magazine.MiuiMagazineCClockInfo;
import com.miui.clock.magazine.MiuiMagazineCDateClockInfo;
import com.miui.clock.magazine.MiuiMagazineCNotificationClockInfo;
import com.miui.clock.magazine.MiuiMagazineCSingleClockInfo;
import com.miui.clock.oversize.a.OversizeAHourInfo;
import com.miui.clock.oversize.a.OversizeAMinuteInfo;
import com.miui.clock.oversize.a.OversizeASingleClockInfo;
import com.miui.clock.oversize.b.OversizeBAodInfo;
import com.miui.clock.oversize.b.OversizeBHourInfo;
import com.miui.clock.oversize.b.OversizeBMinuteInfo;
import com.miui.clock.padexclusive.a.clockinfo.PadExclusiveAClockInfo;
import com.miui.clock.padexclusive.a.clockinfo.PadExclusiveASignatureClockInfo;
import com.miui.clock.padexclusive.b.PadExclusiveBClockInfo;
import com.miui.clock.padexclusive.c.PadExclusiveCHourInfo;
import com.miui.clock.padexclusive.c.PadExclusiveCMinuteInfo;
import com.miui.clock.pets.DepthPetsAodClockInfo;
import com.miui.clock.pets.DepthPetsClockInfo;
import com.miui.clock.pets.DepthPetsSecondaryClockInfo;
import com.miui.clock.rhombus.MiuiRhombusClockInfo;
import com.miui.clock.rhombus.MiuiRhombusNotificationClockInfo;
import com.miui.clock.rhombus.MiuiRhombusSingleClockInfo;
import com.miui.clock.smartframe.MiuiSmartFrameClockInfo;
import com.miui.clock.smartframe.MiuiSmartFrameSingleClockInfo;
import com.miui.clock.utils.GlobalColorUtils;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class ClockStyleInfo {
    public static final float[] Gradient_AOD_PARAMS = {1400.0f, 400.0f, 400.0f, 900.0f, 1.6f, -350.0f, 500.0f, 320.0f, 950.0f, 1.6f, 1200.0f, 2000.0f, 480.0f, 1000.0f, 1.6f};
    public static final float[] Gradient_LOCK_PARAMS = {1400.0f, 400.0f, 500.0f, 900.0f, 1.6f, -350.0f, 500.0f, 400.0f, 950.0f, 1.6f, 1200.0f, 2000.0f, 600.0f, 1000.0f, 1.6f};
    public int animationPrimaryColor;
    public int animationSecondaryColor;
    public int aodInfoColor;
    public int aodPrimaryColor;
    public int aodSecondaryColor;
    public float clockBottomRatio;
    public int clockEffect;
    public float clockTopRatio;
    public int currentBlendColor;
    public int currentPrimaryColor;
    public int currentSecondBlendColor;
    public int currentSecondaryColor;
    public boolean currentTextDark;
    public int displayType;
    public int fontStyle;
    public int fullAodBlendColor;
    public int fullAodInfoColor;
    public int fullAodPrimaryColor;
    public int fullAodSecondaryBlendColor;
    public int fullAodSecondaryColor;
    public Float glassTransparency;
    public boolean isAutoPrimaryColor;
    public boolean isAutoSecondaryColor;
    public Boolean isDoubleRow;
    public boolean isSuperSaveOpen;
    public boolean mMultiWindowBlur;
    public Map mPalette;
    public boolean mShouldObserveSettings;
    public int originMagazineColor;
    public int secondaryColor;
    public boolean skipScreenTransform;
    public float timeHeight;
    public float timeWidth;
    public String type;
    public int primaryColor = -1;
    public int infoAreaColor = Color.parseColor("#99ffffff");
    public int blendColor = -1;
    public int aodBlendColor = 0;
    public int secondaryBlendColor = -1;
    public int aodSecondaryBlendColor = 0;
    public int glassColor = -1;
    public int aodGlassColor = 0;
    public int secondaryGlassColor = -1;
    public int aodSecondaryGlassColor = 0;
    public boolean mInfoTextDark = false;
    public boolean mTextDark = false;
    public boolean disableContainerPassBlur = false;
    public AllInOneClockStyle.Region clockDepthType = AllInOneClockStyle.Region.REGION_1;
    public int hollowStyle = 0;
    public boolean isColonShow = true;
    public int currentLabAlpha = 255;
    public int GradientType = Effect.NORMAL_GRADIENT;
    public int mGradientIndex = 0;

    public ClockStyleInfo() {
    }

    public ClockStyleInfo(ClockBean clockBean, int i) {
        this.displayType = i;
        parseClockBean(clockBean);
    }

    public static ClockStyleInfo convertInfoFromClockBean(Context context, ClockBean clockBean, int i) {
        if (clockBean == null) {
            clockBean = ClockBean.getClassicDefaultBean(context);
        }
        Log.d("ClockStyleInfo", " convertInfoFromClockBean，templateId = " + clockBean.getTemplateId());
        String templateId = clockBean.getTemplateId();
        templateId.getClass();
        switch (templateId) {
            case "eastern_c_single":
                return new EasternArtCAodInfo(clockBean, i);
            case "magazine_a_single":
                return new MiuiMagazineASingleClockInfo(clockBean);
            case "depth_pets_aod":
                return new DepthPetsAodClockInfo(clockBean, i);
            case "rhombus_single":
                return new MiuiRhombusSingleClockInfo(clockBean);
            case "oversize_a":
                return isAODType(i) ? new OversizeASingleClockInfo(clockBean, i) : new OversizeAHourInfo(clockBean, i);
            case "oversize_b":
                return isAODType(i) ? new OversizeBAodInfo(clockBean, i) : new OversizeBMinuteInfo(clockBean, i);
            case "oversize_b_hour":
                return new OversizeBHourInfo(clockBean, i);
            case "eastern_a_minute":
                if (isAODType(i)) {
                    return new MiuiAestheticsSingleClockInfo(clockBean, i);
                }
                return isNotificationType(i) ? new MiuiAestheticsNotificationClockInfo(clockBean, i) : new MiuiAestheticsClockInfo(clockBean, i);
            case "classic_signature":
                return new MiuiClassicSignatureClockInfo(clockBean, i);
            case "classic_max_minute":
                return new MiuiClassicMaxMinuteClockInfo(clockBean, i);
            case "pad_exclusive_a_signature":
                return new PadExclusiveASignatureClockInfo(clockBean, i);
            case "eastern_a_single":
                return new MiuiAestheticsSingleClockInfo(clockBean, i);
            case "doodle":
                return isAODType(i) ? new MiuiGraffitiAodClockInfo(clockBean) : new MiuiGraffitiClockInfo(clockBean);
            case "classic_plus_small":
                return new MiuiClassicPlusClockSmallInfo(clockBean);
            case "eastern_b_aod":
                return new EasternArtBAodInfo(clockBean);
            case "magazine_c_date_only":
                return new MiuiMagazineCDateClockInfo(clockBean, i);
            case "eastern_b_data":
                return new EasternArtBDataUpperClockInfo(clockBean, i);
            case "eastern_b_data_vertical":
                return new EasternArtBVerticalDataUpInfo(clockBean);
            case "eastern_b_vertical_aod":
                return new EasternArtBVerticalAodInfo(clockBean);
            case "rhombus_notification":
                return new MiuiRhombusNotificationClockInfo(clockBean);
            case "oversize_b_single":
                return new OversizeBAodInfo(clockBean, i);
            case "eastern_c_notification":
                return new EasternArtCSingleInfo(clockBean, i);
            case "all_in_one_minute":
                return new AllInOneMinuteInfo(clockBean, i);
            case "eastern_d_minute":
                return new EasternArtDMinuteInfo(clockBean, i);
            case "magazine_a":
                if (isAODType(i)) {
                    return new MiuiMagazineASingleClockInfo(clockBean);
                }
                return isNotificationType(i) ? new MiuiMagazineANotificationClockInfo(clockBean) : new MiuiMagazineAClockInfo(clockBean, i);
            case "magazine_b":
                return isNotificationType(i) ? new MiuiMagazineBNotificationClockInfo(clockBean, i) : new MiuiMagazineBClockInfo(clockBean, i);
            case "magazine_c":
                if (isAODType(i)) {
                    return new MiuiMagazineCSingleClockInfo(clockBean, i);
                }
                return isNotificationType(i) ? new MiuiMagazineCNotificationClockInfo(clockBean, i) : new MiuiMagazineCClockInfo(clockBean, i);
            case "all_in_one_single":
                return new AllInOneSingleClockInfo(clockBean, i);
            case "eastern_d_single":
                return new EasternArtDAodInfo(clockBean, i);
            case "depth_pets_secondary":
                return new DepthPetsSecondaryClockInfo(clockBean, i);
            case "smart_frame":
            case "smart_frame_notification":
                return isAODType(i) ? new MiuiSmartFrameSingleClockInfo(clockBean) : new MiuiSmartFrameClockInfo(clockBean);
            case "classic_small":
                return new MiuiClassicClockSmallInfo(clockBean);
            case "classic_max":
                return isAODType(i) ? new MiuiClassicMaxAodClockInfo(clockBean, i) : new MiuiClassicMaxHourClockInfo(clockBean, i);
            case "classic_plus":
                return new MiuiClassicPlusClockInfo(clockBean, i);
            case "dual":
                return new MiuiDualClockInfo(clockBean);
            case "magazine_c_notification":
                return new MiuiMagazineCNotificationClockInfo(clockBean, i);
            case "pad_exclusive_c_minute":
                return new PadExclusiveCMinuteInfo(clockBean);
            case "depth_pets":
                return isAODType(i) ? new DepthPetsAodClockInfo(clockBean, i) : new DepthPetsClockInfo(clockBean, i);
            case "eastern_a_notification":
                return new MiuiAestheticsNotificationClockInfo(clockBean, i);
            case "eastern_b_vertical":
                return new EasternArtBVerticalTimeBelowInfo(clockBean);
            case "magazine_b_notification":
                return new MiuiMagazineBNotificationClockInfo(clockBean, i);
            case "classic":
                return isNotificationType(i) ? new MiuiClassicClockSmallInfo(clockBean) : new MiuiClassicClockInfo(clockBean, i);
            case "classic_plus_signature":
                return new MiuiClassicPlusSignatureClockInfo(clockBean, i);
            case "magazine_a_notification":
                return new MiuiMagazineANotificationClockInfo(clockBean);
            case "rhombus":
                if (isAODType(i)) {
                    return new MiuiRhombusSingleClockInfo(clockBean);
                }
                return isNotificationType(i) ? new MiuiRhombusNotificationClockInfo(clockBean) : new MiuiRhombusClockInfo(clockBean, i);
            case "all_in_one":
                return isAODType(i) ? new AllInOneSingleClockInfo(clockBean, i) : new AllInOneHourInfo(clockBean, i);
            case "pad_exclusive_a":
                return new PadExclusiveAClockInfo(clockBean, i);
            case "pad_exclusive_b":
                return new PadExclusiveBClockInfo(clockBean);
            case "pad_exclusive_c":
                return new PadExclusiveCHourInfo(clockBean);
            case "magazine_c_single":
                return new MiuiMagazineCSingleClockInfo(clockBean, i);
            case "oversize_a_minute":
                return new OversizeAMinuteInfo(clockBean, i);
            case "eastern_b_vertical_notification":
                return new EasternArtBNotificationInfo(clockBean);
            case "eastern_a":
                if (isAODType(i)) {
                    return new MiuiAestheticsSingleClockInfo(clockBean, i);
                }
                return isNotificationType(i) ? new MiuiAestheticsNotificationClockInfo(clockBean, i) : new MiuiAestheticsHourClockInfo(clockBean, i);
            case "eastern_b":
                return isAODType(i) ? new EasternArtBAodInfo(clockBean, i) : new EasternArtBTimeBelowInfo(clockBean, i);
            case "eastern_c":
                return isAODType(i) ? new EasternArtCAodInfo(clockBean, i) : new EasternArtCHourInfo(clockBean, i);
            case "eastern_d":
                return new EasternArtDHourInfo(clockBean, i);
            case "oversize_a_single":
                return new OversizeASingleClockInfo(clockBean, i);
            case "doodle_single":
                return new MiuiGraffitiAodClockInfo(clockBean);
            case "smart_frame_single":
                return new MiuiSmartFrameSingleClockInfo(clockBean);
            case "eastern_c_minute":
                return new EasternArtCMinuteInfo(clockBean, i);
            default:
                return new MiuiClassicClockInfo(clockBean, i);
        }
    }

    public static boolean hasExtraFlag(int i, int i2) {
        return (i & i2) == i2;
    }

    public static boolean isAODType(int i) {
        return (i & 1) == 1;
    }

    public static boolean isFullAODType(int i) {
        return (i & 32) == 32;
    }

    public static boolean isNotificationType(int i) {
        return (i & 2) == 2;
    }

    public static boolean isPreviewType(int i) {
        return (i & 8) == 8;
    }

    public static boolean isSecondaryType(int i) {
        return (i & 64) == 64;
    }

    private void parseClockBean(ClockBean clockBean) {
        if (clockBean == null) {
            return;
        }
        setPrimaryColor(clockBean.getPrimaryColor());
        setSecondaryColor(clockBean.getSecondaryColor());
        setAutoPrimaryColor(clockBean.isAutoPrimaryColor());
        setAutoSecondaryColor(clockBean.isAutoSecondaryColor());
        setDisableContainerPassBlur(clockBean.isDisableContainerPassBlur());
        setClockEffect(clockBean.getClockEffect());
        setBlendColor(clockBean.getBlendColor());
        setSecondaryBlendColor(clockBean.getSecondaryBlendColor());
        setMultiWindowBlur(clockBean.supportMultiWindowBlur());
        setClockDepthType(clockBean.getClockDepthType());
        setClockTopRatio(clockBean.getClockTopRatio());
        setClockBottomRatio(clockBean.getClockBottomRatio());
        setTimeWidth(clockBean.getTimeWidth());
        setTimeHeight(clockBean.getTimeHeight());
        setSkipScreenTransform(clockBean.isSkipScreenTransform());
        setGlassTransparency(clockBean.getGlassTransparency());
        setHollowStyle(clockBean.getHollowStyle());
        setIsDoubleRow(Boolean.valueOf(clockBean.isDoubleRow()));
        setIsColonShow(clockBean.isColonShow());
        setFontStyle(clockBean.getFontStyle());
    }

    public void convertAnimationColor() {
        setAnimationPrimaryColor(getPrimaryColor());
        setAnimationSecondaryColor(getSecondaryColor());
    }

    public void convertPrimaryColorByBlendColor(boolean z) {
        if (z) {
            setPrimaryColor(this.blendColor);
            setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(this.blendColor));
        } else {
            setPrimaryColorNotChangeAodColor(this.blendColor);
        }
        setAnimationPrimaryColor(this.blendColor);
    }

    public void convertSecondaryColorByBlendColor(boolean z) {
        if (z) {
            setSecondaryColor(this.secondaryBlendColor);
            setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(this.secondaryBlendColor));
        } else {
            setSecondaryColorNotChangeAodColor(this.secondaryBlendColor);
        }
        setAnimationSecondaryColor(this.secondaryBlendColor);
    }

    public int getAnimationPrimaryColor() {
        int i = this.animationPrimaryColor;
        if (i == 0) {
            return -1;
        }
        return i;
    }

    public int getAnimationSecondaryColor() {
        int i = this.animationSecondaryColor;
        if (i == 0) {
            return -1;
        }
        return i;
    }

    public int getAodBlendColor() {
        int i;
        if (this.isSuperSaveOpen) {
            return -1;
        }
        int i2 = this.aodBlendColor;
        if (i2 == 0 && (i = this.blendColor) != 0) {
            return GlobalColorUtils.transformAodColor(i);
        }
        if (i2 == 0) {
            return -1;
        }
        return i2;
    }

    public int getAodGlassColor() {
        int i;
        if (this.isSuperSaveOpen) {
            return -1;
        }
        int i2 = this.aodGlassColor;
        if (i2 == 0 && (i = this.glassColor) != 0) {
            return GlobalColorUtils.transformAodColor(i);
        }
        if (i2 == 0) {
            return -1;
        }
        return i2;
    }

    public float[] getAodGradientParams() {
        return Gradient_AOD_PARAMS;
    }

    public int getAodInfoColor() {
        if (this.isSuperSaveOpen) {
            return -1;
        }
        return this.aodInfoColor;
    }

    public int getAodPrimaryColor() {
        int i;
        if (this.isSuperSaveOpen) {
            return -1;
        }
        int i2 = this.aodPrimaryColor;
        if (i2 == 0 && (i = this.primaryColor) != 0) {
            return GlobalColorUtils.transformAodColor(i);
        }
        if (i2 == 0) {
            return -1;
        }
        return i2;
    }

    public int getAodSecondaryBlendColor() {
        int i;
        if (this.isSuperSaveOpen) {
            return -1;
        }
        int i2 = this.aodSecondaryBlendColor;
        if (i2 == 0 && (i = this.secondaryBlendColor) != 0) {
            return GlobalColorUtils.transformAodColor(i);
        }
        if (i2 == 0) {
            return -1;
        }
        return i2;
    }

    public int getAodSecondaryColor() {
        int i;
        if (this.isSuperSaveOpen) {
            return -1;
        }
        int i2 = this.aodSecondaryColor;
        if (i2 == 0 && (i = this.secondaryColor) != 0) {
            return GlobalColorUtils.transformAodColor(i);
        }
        if (i2 == 0) {
            return -1;
        }
        return i2;
    }

    public int getAodSecondaryGlassColor() {
        int i;
        if (this.isSuperSaveOpen) {
            return -1;
        }
        int i2 = this.aodSecondaryGlassColor;
        if (i2 == 0 && (i = this.secondaryGlassColor) != 0) {
            return GlobalColorUtils.transformAodColor(i);
        }
        if (i2 == 0) {
            return -1;
        }
        return i2;
    }

    public int getBlendColor() {
        int i;
        if (this.isSuperSaveOpen || (i = this.blendColor) == 0) {
            return -1;
        }
        return i;
    }

    public float getClockBottomRatio() {
        return this.clockBottomRatio;
    }

    public AllInOneClockStyle.Region getClockDepthType() {
        return this.clockDepthType;
    }

    public int getClockEffect() {
        return this.clockEffect;
    }

    public float getClockTopRatio() {
        return this.clockTopRatio;
    }

    public int getCurrentLabAlpha() {
        return this.currentLabAlpha;
    }

    public int getCurrentPrimaryColor() {
        int i = this.currentPrimaryColor;
        return i != 0 ? i : getPrimaryColor();
    }

    public int getCurrentSecondaryColor() {
        int i = this.currentSecondaryColor;
        return i != 0 ? i : getSecondaryColor();
    }

    public boolean getCurrentTextDark() {
        return this.currentTextDark;
    }

    public int getDisplayType() {
        return this.displayType;
    }

    public int getFontStyle() {
        return this.fontStyle;
    }

    public int getFullAodBlendColor() {
        int i = this.fullAodBlendColor;
        return i == 0 ? GlobalColorUtils.transformFullAodColor(getBlendColor()) : i;
    }

    public int getFullAodInfoColor() {
        int i = this.fullAodInfoColor;
        return i == 0 ? GlobalColorUtils.transformFullAodColor(getInfoAreaColor()) : i;
    }

    public int getFullAodPrimaryColor() {
        int i = this.fullAodPrimaryColor;
        if (i != 0) {
            return i;
        }
        int primaryColor = this.animationPrimaryColor;
        if (primaryColor == 0) {
            primaryColor = getPrimaryColor();
        }
        return GlobalColorUtils.transformFullAodColor(primaryColor);
    }

    public int getFullAodSecondaryBlendColor() {
        int i = this.fullAodSecondaryBlendColor;
        return i == 0 ? GlobalColorUtils.transformFullAodColor(getSecondaryBlendColor()) : i;
    }

    public int getFullAodSecondaryColor() {
        int i = this.fullAodSecondaryColor;
        if (i != 0) {
            return i;
        }
        int secondaryColor = this.animationSecondaryColor;
        if (secondaryColor == 0) {
            secondaryColor = getSecondaryColor();
        }
        return GlobalColorUtils.transformFullAodColor(secondaryColor);
    }

    public int getGlassColor() {
        int i = this.glassColor;
        if (i == 0) {
            return -1;
        }
        return i;
    }

    public Float getGlassTransparency() {
        return this.glassTransparency;
    }

    public int getGradientType() {
        return this.GradientType;
    }

    public int getHollowStyle() {
        return this.hollowStyle;
    }

    public int getInfoAreaColor() {
        return this.isSuperSaveOpen ? Color.parseColor("#99ffffff") : this.infoAreaColor;
    }

    public abstract int getLayoutId();

    public float[] getLockGradientParams() {
        return this.GradientType == Effect.LAND_GRADIENT ? getLockLandGradientParams() : Gradient_LOCK_PARAMS;
    }

    public float[] getLockLandGradientParams() {
        return Gradient_LOCK_PARAMS;
    }

    public int getOriginMagazineColor() {
        int i = this.originMagazineColor;
        return i == 0 ? getPrimaryColor() : i;
    }

    public Map getPalette() {
        return this.mPalette;
    }

    public int getPrimaryColor() {
        if (this.isSuperSaveOpen) {
            return -1;
        }
        return this.primaryColor;
    }

    public int getSecondaryBlendColor() {
        int i;
        if (this.isSuperSaveOpen || (i = this.secondaryBlendColor) == 0) {
            return -1;
        }
        return i;
    }

    public int getSecondaryColor() {
        if (this.isSuperSaveOpen) {
            return -1;
        }
        return this.secondaryColor;
    }

    public int getSecondaryGlassColor() {
        int i = this.secondaryGlassColor;
        if (i == 0) {
            return -1;
        }
        return i;
    }

    public float getTimeHeight() {
        return this.timeHeight;
    }

    public float getTimeWidth() {
        return this.timeWidth;
    }

    public abstract String getType();

    public boolean isAutoPrimaryColor() {
        if (this.isSuperSaveOpen) {
            return false;
        }
        return this.isAutoPrimaryColor;
    }

    public boolean isAutoSecondaryColor() {
        if (this.isSuperSaveOpen) {
            return false;
        }
        return this.isAutoSecondaryColor;
    }

    public boolean isColonShow() {
        return this.isColonShow;
    }

    public boolean isDisableContainerPassBlur() {
        return this.disableContainerPassBlur;
    }

    public boolean isInfoTextDark() {
        return this.mInfoTextDark;
    }

    public boolean isMultiWindowBlur() {
        return this.mMultiWindowBlur;
    }

    public boolean isSkipScreenTransform() {
        return this.skipScreenTransform;
    }

    public boolean isSuperSaveOpen() {
        return this.isSuperSaveOpen;
    }

    public void setAnimationPrimaryColor(int i) {
        this.animationPrimaryColor = i;
    }

    public void setAnimationSecondaryColor(int i) {
        this.animationSecondaryColor = i;
    }

    public void setAodBlendColor(int i) {
        if (i != 0) {
            this.aodBlendColor = i;
        }
    }

    public void setAodGlassColor(int i) {
        this.aodGlassColor = i;
    }

    public void setAodInfoColor(int i) {
        this.aodInfoColor = i;
    }

    public void setAodPrimaryColor(int i) {
        this.aodPrimaryColor = i;
    }

    public void setAodSecondaryBlendColor(int i) {
        if (i != 0) {
            this.aodSecondaryBlendColor = i;
        }
    }

    public void setAodSecondaryColor(int i) {
        this.aodSecondaryColor = i;
    }

    public void setAodSecondaryGlassColor(int i) {
        this.aodSecondaryGlassColor = i;
    }

    public void setAutoPrimaryColor(boolean z) {
        this.isAutoPrimaryColor = z;
    }

    public void setAutoSecondaryColor(boolean z) {
        this.isAutoSecondaryColor = z;
    }

    public void setBlendColor(int i) {
        if (i != 0) {
            this.blendColor = i;
        }
    }

    public void setClockBottomRatio(float f) {
        this.clockBottomRatio = f;
    }

    public void setClockDepthType(AllInOneClockStyle.Region region) {
        this.clockDepthType = region;
    }

    public void setClockEffect(int i) {
        this.clockEffect = i;
    }

    public void setClockTopRatio(float f) {
        this.clockTopRatio = f;
    }

    public void setCurrentBlendColor(int i) {
        this.currentBlendColor = i;
    }

    public void setCurrentLabAlpha(int i) {
        this.currentLabAlpha = i;
    }

    public void setCurrentPrimaryColor(int i) {
        this.currentPrimaryColor = i;
    }

    public void setCurrentSecondBlendColor(int i) {
        this.currentSecondBlendColor = i;
    }

    public void setCurrentSecondaryColor(int i) {
        this.currentSecondaryColor = i;
    }

    public void setCurrentTextDark(boolean z) {
        this.currentTextDark = z;
    }

    public void setDisableContainerPassBlur(boolean z) {
        this.disableContainerPassBlur = z;
    }

    public void setFontStyle(int i) {
        this.fontStyle = i;
    }

    public void setFullAodBlendColor(int i) {
        this.fullAodBlendColor = i;
    }

    public void setFullAodInfoColor(int i) {
        this.fullAodInfoColor = i;
    }

    public void setFullAodPrimaryColor(int i) {
        this.fullAodPrimaryColor = i;
    }

    public void setFullAodSecondaryBlendColor(int i) {
        this.fullAodSecondaryBlendColor = i;
    }

    public void setFullAodSecondaryColor(int i) {
        this.fullAodSecondaryColor = i;
    }

    public void setGlassColor(int i) {
        this.glassColor = i;
    }

    public void setGlassTransparency(Float f) {
        this.glassTransparency = f;
    }

    public void setGradientIndex(int i) {
        this.mGradientIndex = i;
        if (i >= 4) {
            this.mGradientIndex = 0;
        }
    }

    public void setGradientType(int i) {
        this.GradientType = i;
    }

    public void setHollowStyle(int i) {
        this.hollowStyle = i;
    }

    public void setInfoAreaColor(int i) {
        if (i != 0) {
            this.infoAreaColor = i;
            this.aodInfoColor = GlobalColorUtils.transformAodColor(i);
        }
    }

    public void setInfoTextDark(boolean z) {
        this.mInfoTextDark = z;
    }

    public void setIsColonShow(boolean z) {
        this.isColonShow = z;
    }

    public void setIsDoubleRow(Boolean bool) {
        this.isDoubleRow = bool;
    }

    public void setMultiWindowBlur(boolean z) {
        this.mMultiWindowBlur = z;
    }

    public void setOriginMagazineColor(int i) {
        this.originMagazineColor = i;
    }

    public void setPalette(Map map) {
        this.mPalette = map;
    }

    public void setPrimaryColor(int i) {
        if (i != 0) {
            this.primaryColor = i;
            this.aodPrimaryColor = GlobalColorUtils.transformAodColor(i);
        }
    }

    public void setPrimaryColorNotChangeAodColor(int i) {
        if (i != 0) {
            this.primaryColor = i;
        }
    }

    public void setSecondaryBlendColor(int i) {
        if (i != 0) {
            this.secondaryBlendColor = i;
        }
    }

    public void setSecondaryColor(int i) {
        if (i != 0) {
            this.secondaryColor = i;
            this.aodSecondaryColor = GlobalColorUtils.transformAodColor(i);
        }
    }

    public void setSecondaryColorNotChangeAodColor(int i) {
        if (i != 0) {
            this.secondaryColor = i;
        }
    }

    public void setSecondaryGlassColor(int i) {
        this.secondaryGlassColor = i;
    }

    public void setShouldObserveSettings() {
        this.mShouldObserveSettings = true;
    }

    public void setSkipScreenTransform(boolean z) {
        this.skipScreenTransform = z;
    }

    public void setSuperSaveOpen(boolean z) {
        this.isSuperSaveOpen = z;
    }

    public void setTextDark(boolean z) {
        this.mTextDark = z;
    }

    public void setTimeHeight(float f) {
        this.timeHeight = f;
    }

    public void setTimeWidth(float f) {
        this.timeWidth = f;
    }

    public boolean shouldObserveSettings() {
        return this.mShouldObserveSettings;
    }

    public void snapshotCurrentColors() {
        this.currentPrimaryColor = this.primaryColor;
        this.currentSecondaryColor = this.secondaryColor;
        this.currentBlendColor = this.blendColor;
        this.currentSecondBlendColor = this.secondaryBlendColor;
    }

    public String toString() {
        return "ClockStyleInfo{aodBlendColor=" + this.aodBlendColor + ", type='" + this.type + "', fontStyle=" + this.fontStyle + ", glassTransparency=" + this.glassTransparency + ", hollowStyle=" + this.hollowStyle + ", isDoubleRow=" + this.isDoubleRow + ", isColonShow=" + this.isColonShow + ", isSuperSaveOpen=" + this.isSuperSaveOpen + ", isAutoPrimaryColor=" + this.isAutoPrimaryColor + ", primaryColor=" + this.primaryColor + ", animationPrimaryColor=" + this.animationPrimaryColor + ", animationSecondaryColor=" + this.animationSecondaryColor + ", aodPrimaryColor=" + this.aodPrimaryColor + ", fullAodPrimaryColor=" + this.fullAodPrimaryColor + ", isAutoSecondaryColor=" + this.isAutoSecondaryColor + ", secondaryColor=" + this.secondaryColor + ", aodSecondaryColor=" + this.aodSecondaryColor + ", fullAodSecondaryColor=" + this.fullAodSecondaryColor + ", infoAreaColor=" + this.infoAreaColor + ", aodInfoColor=" + this.aodInfoColor + ", fullAodInfoColor=" + this.fullAodInfoColor + ", blendColor=" + this.blendColor + ", fullAodBlendColor=" + this.fullAodBlendColor + ", secondaryBlendColor=" + this.secondaryBlendColor + ", aodSecondaryBlendColor=" + this.aodSecondaryBlendColor + ", fullAodSecondaryBlendColor=" + this.fullAodSecondaryBlendColor + ", originMagazineColor=" + this.originMagazineColor + ", clockEffect=" + this.clockEffect + ", currentPrimaryColor=" + this.currentPrimaryColor + ", currentSecondaryColor=" + this.currentSecondaryColor + ", currentBlendColor=" + this.currentBlendColor + ", currentSecondBlendColor=" + this.currentSecondBlendColor + ", GradientType=" + this.GradientType + ", mGradientIndex=" + this.mGradientIndex + '}';
    }

    public void updateAodHighTextColors() {
        if (this.secondaryColor == 0) {
            this.secondaryColor = -1;
        }
        if (this.aodPrimaryColor == 0 || this.aodSecondaryColor == 0) {
            this.aodPrimaryColor = -1;
            this.aodSecondaryColor = -1;
        }
    }

    public void updateClockBlendColor(int i, int i2) {
        if (i != 0) {
            setCurrentBlendColor(i);
        }
        if (i2 != 0) {
            setCurrentSecondBlendColor(i2);
        }
    }

    public void updateGradientTypeByOrientation(int i) {
        if (i == 2) {
            setGradientType(Effect.LAND_GRADIENT);
        } else if (i == 1) {
            setGradientType(Effect.NORMAL_GRADIENT);
        }
    }
}
