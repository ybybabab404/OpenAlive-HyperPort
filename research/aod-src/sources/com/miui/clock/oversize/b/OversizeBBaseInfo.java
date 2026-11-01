package com.miui.clock.oversize.b;

import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockStyleInfo;

/* JADX INFO: loaded from: classes.dex */
abstract class OversizeBBaseInfo extends ClockStyleInfo {
    public int displayType;

    public OversizeBBaseInfo(ClockBean clockBean, int i) {
        this.displayType = i;
        setAutoPrimaryColor(clockBean.isAutoPrimaryColor());
        setPrimaryColor(clockBean.getPrimaryColor());
        setAutoSecondaryColor(clockBean.isAutoSecondaryColor());
        setSecondaryColor(clockBean.getSecondaryColor());
        setBlendColor(clockBean.getBlendColor());
        setSecondaryBlendColor(clockBean.getSecondaryBlendColor());
        setInfoAreaColor(clockBean.getInfoAreaColor());
        setClockEffect(clockBean.getClockEffect());
        setDisableContainerPassBlur(clockBean.isDisableContainerPassBlur());
        setMultiWindowBlur(clockBean.supportMultiWindowBlur());
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getDisplayType() {
        return this.displayType;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getSecondaryColor() {
        if (this.secondaryColor == 0) {
            return -1;
        }
        return super.getSecondaryColor();
    }
}
