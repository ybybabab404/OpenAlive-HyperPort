package com.miui.clock.padexclusive.c;

import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.Effect;

/* JADX INFO: loaded from: classes.dex */
public abstract class PadExclusiveCBaseInfo extends ClockStyleInfo {
    public PadExclusiveCBaseInfo(ClockBean clockBean) {
        parseClockBean(clockBean);
    }

    private void parseClockBean(ClockBean clockBean) {
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

    @Override // com.miui.clock.module.ClockStyleInfo
    public float[] getLockGradientParams() {
        int i = this.mGradientIndex;
        if (i == 1) {
            return Effect.PadExclusiveCHourEffect.Gradient_NORMAL_PARAMS_1;
        }
        if (i == 2) {
            return Effect.PadExclusiveCHourEffect.Gradient_NORMAL_PARAMS_2;
        }
        return i == 3 ? Effect.PadExclusiveCHourEffect.Gradient_NORMAL_PARAMS_3 : Effect.PadExclusiveCHourEffect.Gradient_NORMAL_PARAMS_0;
    }
}
