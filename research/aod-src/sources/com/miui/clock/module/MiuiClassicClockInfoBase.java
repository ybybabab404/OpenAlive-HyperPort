package com.miui.clock.module;

import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.ColorUtils;

/* JADX INFO: loaded from: classes.dex */
public abstract class MiuiClassicClockInfoBase extends ClockStyleInfo {
    public int classicLine1;
    public int classicLine2;
    public int classicLine3;
    public int classicLine4;
    public int classicLine5;
    public int clockWeight;
    public int displayType;
    public boolean enableDiffusion;
    public String healthJson;
    public String signature;
    public int style;
    public String templateId;
    public boolean unablePresetData;
    public String weatherJson;

    public MiuiClassicClockInfoBase() {
    }

    public MiuiClassicClockInfoBase(ClockBean clockBean) {
        convertFromClockBean(clockBean);
    }

    public MiuiClassicClockInfoBase(ClockBean clockBean, int i) {
        super(clockBean, i);
        this.displayType = i;
        convertFromClockBean(clockBean);
    }

    public void convertFromClockBean(ClockBean clockBean) {
        if (clockBean == null) {
            return;
        }
        setTemplateId(clockBean.getTemplateId());
        setClassicLine1(clockBean.getClassicLine1());
        setClassicLine2(clockBean.getClassicLine2());
        setClassicLine3(clockBean.getClassicLine3());
        setClassicLine4(clockBean.getClassicLine4());
        setClassicLine5(clockBean.getClassicLine5());
        setClockWeight(clockBean.getClockWeight());
        setSignature(clockBean.getClassicSignature());
        setPrimaryColor(clockBean.getPrimaryColor());
        setSecondaryColor(clockBean.getSecondaryColor());
        setBlendColor(clockBean.getBlendColor());
        setSecondaryBlendColor(clockBean.getSecondaryBlendColor());
        setInfoAreaColor(clockBean.getInfoAreaColor());
        setStyle(clockBean.getStyle());
        setEnableDiffusion(clockBean.isEnableDiffusion());
        setAutoPrimaryColor(clockBean.isAutoPrimaryColor());
        setAutoSecondaryColor(clockBean.isAutoSecondaryColor());
        setDisableContainerPassBlur(clockBean.isDisableContainerPassBlur());
        setAnimationPrimaryColor(clockBean.getPrimaryColor());
        setAnimationSecondaryColor(getClassicPlusSecondaryColor());
        setClockEffect(clockBean.getClockEffect());
        setHealthJson(clockBean.getPresetHealthJson());
        setWeatherJson(clockBean.getPresetWeatherJson());
        setUnablePresetData(clockBean.isUnablePresetData());
        setMultiWindowBlur(clockBean.supportMultiWindowBlur());
    }

    public int getClassicLine1() {
        return this.classicLine1;
    }

    public int getClassicLine2() {
        return this.classicLine2;
    }

    public int getClassicLine3() {
        return this.classicLine3;
    }

    public int getClassicLine4() {
        return this.classicLine4;
    }

    public int getClassicLine5() {
        return this.classicLine5;
    }

    public int getClassicPlusSecondaryBlendColor() {
        if (this.isSuperSaveOpen) {
            return -1;
        }
        if (isAutoSecondaryColor()) {
            return getBlendColor();
        }
        if (getSecondaryBlendColor() != 0) {
            return getSecondaryBlendColor();
        }
        return ClockStyleInfo.isFullAODType(getDisplayType()) ? getFullAodSecondaryBlendColor() : getBlendColor();
    }

    public int getClassicPlusSecondaryColor() {
        if (isSuperSaveOpen()) {
            return -1;
        }
        if (isAutoSecondaryColor() || getSecondaryColor() == 0) {
            return getPrimaryColor();
        }
        if (ClockStyleInfo.isAODType(getDisplayType())) {
            return getAodSecondaryColor();
        }
        return ClockStyleInfo.isFullAODType(getDisplayType()) ? getFullAodSecondaryColor() : getSecondaryColor();
    }

    public int getClockWeight() {
        return this.clockWeight;
    }

    public int getCorrectSecondaryColor(int i, int i2) {
        if (this.isSuperSaveOpen) {
            return -1;
        }
        return (isAutoSecondaryColor() || i2 == 0) ? i : i2;
    }

    public int getDateColor() {
        return getPrimaryColor();
    }

    public int getDatePrimaryColor(int i) {
        return ClockEffectUtils.hasBlurBlendRendering(getClockEffect()) ? ColorUtils.toOpaqueRed255(i) : i;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getDisplayType() {
        return this.displayType;
    }

    public String getHealthJson() {
        return this.healthJson;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getPrimaryColor() {
        if (isSuperSaveOpen()) {
            return -1;
        }
        return ClockStyleInfo.isAODType(getDisplayType()) ? getAodPrimaryColor() : super.getPrimaryColor();
    }

    public String getSignature() {
        return this.signature;
    }

    public int getStyle() {
        if (this.style == 0) {
            this.style = 21;
        }
        return this.style;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public String getWeatherJson() {
        return this.weatherJson;
    }

    public boolean hasSignature() {
        String str = this.signature;
        return (str == null || str.isEmpty()) ? false : true;
    }

    public boolean isEnableDiffusion() {
        return this.enableDiffusion;
    }

    public boolean isUnablePresetData() {
        return this.unablePresetData;
    }

    public void setClassicLine1(int i) {
        this.classicLine1 = i;
    }

    public void setClassicLine2(int i) {
        this.classicLine2 = i;
    }

    public void setClassicLine3(int i) {
        this.classicLine3 = i;
    }

    public void setClassicLine4(int i) {
        this.classicLine4 = i;
    }

    public void setClassicLine5(int i) {
        this.classicLine5 = i;
    }

    public void setClockWeight(int i) {
        this.clockWeight = i;
    }

    public void setEnableDiffusion(boolean z) {
        this.enableDiffusion = z;
    }

    public void setHealthJson(String str) {
        this.healthJson = str;
    }

    public void setSignature(String str) {
        this.signature = str;
    }

    public void setStyle(int i) {
        this.style = i;
    }

    public void setTemplateId(String str) {
        this.templateId = str;
    }

    public void setUnablePresetData(boolean z) {
        this.unablePresetData = z;
    }

    public void setWeatherJson(String str) {
        this.weatherJson = str;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String toString() {
        return "MiuiClassicClockInfoBase{type='" + this.type + "', isSuperSaveOpen=" + this.isSuperSaveOpen + ", isAutoPrimaryColor=" + this.isAutoPrimaryColor + ", primaryColor=" + this.primaryColor + ", aodPrimaryColor=" + this.aodPrimaryColor + ", fullAodPrimaryColor=" + this.fullAodPrimaryColor + ", isAutoSecondaryColor=" + this.isAutoSecondaryColor + ", secondaryColor=" + this.secondaryColor + ", aodSecondaryColor=" + this.aodSecondaryColor + ", fullAodSecondaryColor=" + this.fullAodSecondaryColor + ", infoAreaColor=" + this.infoAreaColor + ", aodInfoColor=" + this.aodInfoColor + ", fullAodInfoColor=" + this.fullAodInfoColor + ", blendColor=" + this.blendColor + ", aodBlendColor=" + this.aodBlendColor + ", fullAodBlendColor=" + this.fullAodBlendColor + ", secondaryBlendColor=" + this.secondaryBlendColor + ", aodSecondaryBlendColor=" + this.aodSecondaryBlendColor + ", fullAodSecondaryBlendColor=" + this.fullAodSecondaryBlendColor + ", mGradientIndex=" + this.mGradientIndex + ", templateId='" + this.templateId + "', classicLine1=" + this.classicLine1 + ", classicLine2=" + this.classicLine2 + ", classicLine3=" + this.classicLine3 + ", classicLine4=" + this.classicLine4 + ", classicLine5=" + this.classicLine5 + ", signature='" + this.signature + "', healthJson='" + this.healthJson + "', weatherJson='" + this.weatherJson + "', style=" + this.style + ", enableDiffusion=" + this.enableDiffusion + ", displayType=" + this.displayType + '}';
    }
}
