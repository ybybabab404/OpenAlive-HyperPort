package com.miui.clock.magazine;

import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockStyleInfo;

/* JADX INFO: loaded from: classes.dex */
public abstract class MiuiMagazineBBaseInfo extends ClockStyleInfo {
    public int displayType;

    public MiuiMagazineBBaseInfo(ClockBean clockBean, int i) {
        this.displayType = i;
        parseClockBean(clockBean);
    }

    private void parseClockBean(ClockBean clockBean) {
        if (clockBean == null) {
            return;
        }
        setPrimaryColor(clockBean.getPrimaryColor());
        setInfoAreaColor(clockBean.getInfoAreaColor());
        setAutoPrimaryColor(clockBean.isAutoPrimaryColor());
        setDisableContainerPassBlur(clockBean.isDisableContainerPassBlur());
        setAnimationPrimaryColor(clockBean.getPrimaryColor());
        setAnimationSecondaryColor(clockBean.getSecondaryColor());
        setBlendColor(clockBean.getBlendColor());
        setSecondaryBlendColor(clockBean.getSecondaryBlendColor());
        setClockEffect(clockBean.getClockEffect());
        setMultiWindowBlur(clockBean.supportMultiWindowBlur());
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getDisplayType() {
        return this.displayType;
    }
}
