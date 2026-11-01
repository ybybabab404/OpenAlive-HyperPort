package com.miui.clock.oversize.a;

import com.miui.clock.hct.Hct;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.GlobalColorUtils;

/* JADX INFO: loaded from: classes.dex */
public abstract class OversizeABaseInfo extends ClockStyleInfo {
    public int displayType;
    public int mClockStyle = 1;

    public OversizeABaseInfo(ClockBean clockBean, int i) {
        this.displayType = i;
        parseClockBean(clockBean);
    }

    public static int covertWeekTextColor(int i) {
        if (i == 0) {
            i = -1;
        }
        return GlobalColorUtils.transformWithTone(i, Hct.fromInt(i).getTone() >= 75.0d ? 20 : 98);
    }

    private void parseClockBean(ClockBean clockBean) {
        if (clockBean == null) {
            return;
        }
        setClockStyle(clockBean.getStyle());
        setPrimaryColor(clockBean.getPrimaryColor());
        setSecondaryColor(clockBean.getSecondaryColor());
        setInfoAreaColor(clockBean.getInfoAreaColor());
        setBlendColor(clockBean.getBlendColor());
        setSecondaryBlendColor(clockBean.getSecondaryBlendColor());
        setAutoPrimaryColor(clockBean.isAutoPrimaryColor());
        setAutoSecondaryColor(clockBean.isAutoSecondaryColor());
        setDisableContainerPassBlur(clockBean.isDisableContainerPassBlur());
        setClockEffect(clockBean.getClockEffect());
        setMultiWindowBlur(clockBean.supportMultiWindowBlur());
    }

    public int getClockStyle() {
        return this.mClockStyle;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getDisplayType() {
        return this.displayType;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getInfoAreaColor() {
        return this.isSuperSaveOpen ? covertWeekTextColor(-1) : super.getInfoAreaColor();
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getSecondaryColor() {
        if (this.secondaryColor == 0) {
            return -1;
        }
        return super.getSecondaryColor();
    }

    public void setClockStyle(int i) {
        this.mClockStyle = i;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public void updateAodHighTextColors() {
        updateInfoColors();
        if (this.aodPrimaryColor == 0 || this.aodSecondaryColor == 0) {
            this.aodPrimaryColor = -1;
            this.aodSecondaryColor = -1;
            int iCovertWeekTextColor = covertWeekTextColor(-1);
            this.infoAreaColor = iCovertWeekTextColor;
            this.aodInfoColor = iCovertWeekTextColor;
            this.fullAodInfoColor = iCovertWeekTextColor;
        }
    }

    public void updateInfoColors() {
        int i = this.primaryColor;
        if (ClockEffectUtils.isBlurMixType(getClockEffect()) || ClockEffectUtils.isDifferenceType(getClockEffect())) {
            i = this.blendColor;
        }
        int iCovertWeekTextColor = covertWeekTextColor(i);
        this.infoAreaColor = iCovertWeekTextColor;
        this.aodInfoColor = iCovertWeekTextColor;
        this.fullAodInfoColor = iCovertWeekTextColor;
    }
}
