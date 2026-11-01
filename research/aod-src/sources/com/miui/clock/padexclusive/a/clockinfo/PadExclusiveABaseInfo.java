package com.miui.clock.padexclusive.a.clockinfo;

import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.Effect;

/* JADX INFO: loaded from: classes.dex */
public abstract class PadExclusiveABaseInfo extends ClockStyleInfo {
    public int displayType;
    public int plainColor = -1;
    public int plainBlendColor = -1;

    public PadExclusiveABaseInfo(ClockBean clockBean, int i) {
        this.displayType = i;
        parseClockBean(clockBean);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getDisplayType() {
        return this.displayType;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public float[] getLockGradientParams() {
        int i = this.mGradientIndex;
        if (i == 1) {
            return Effect.PadExclusiveAEffect.Gradient_NORMAL_PARAMS_1;
        }
        if (i == 2) {
            return Effect.PadExclusiveAEffect.Gradient_NORMAL_PARAMS_2;
        }
        return i == 3 ? Effect.PadExclusiveAEffect.Gradient_NORMAL_PARAMS_3 : Effect.PadExclusiveAEffect.Gradient_NORMAL_PARAMS_0;
    }

    public int getPlainBlendColor() {
        int i = this.plainBlendColor;
        if (i == 0) {
            return -1;
        }
        return i;
    }

    public int getPlainColor() {
        return this.plainColor;
    }

    public void parseClockBean(ClockBean clockBean) {
        if (clockBean == null) {
            return;
        }
        setPrimaryColor(clockBean.getPrimaryColor());
        setSecondaryColor(clockBean.getSecondaryColor());
        setInfoAreaColor(clockBean.getInfoAreaColor());
        setAutoPrimaryColor(clockBean.isAutoPrimaryColor());
        setAutoSecondaryColor(clockBean.isAutoSecondaryColor());
        setDisableContainerPassBlur(clockBean.isDisableContainerPassBlur());
        setClockEffect(clockBean.getClockEffect());
        setBlendColor(clockBean.getBlendColor());
        setSecondaryBlendColor(clockBean.getSecondaryBlendColor());
        setMultiWindowBlur(clockBean.supportMultiWindowBlur());
    }

    public void setPlainBlendColor(int i) {
        if (i != 0) {
            this.plainBlendColor = i;
        }
    }

    public void setPlainColor(int i) {
        if (i != 0) {
            this.plainColor = i;
        }
    }
}
