package com.miui.clock.eastern.d;

import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockStyleInfo;

/* JADX INFO: loaded from: classes.dex */
public abstract class EasternArtDBaseInfo extends ClockStyleInfo {
    public int displayType;

    public EasternArtDBaseInfo(ClockBean clockBean, int i) {
        this.displayType = i;
        parseClockBean(clockBean);
    }

    private void parseClockBean(ClockBean clockBean) {
        if (clockBean == null) {
            return;
        }
        setPrimaryColor(clockBean.getPrimaryColor());
        setSecondaryColor(clockBean.getSecondaryColor());
        setBlendColor(clockBean.getBlendColor());
        setSecondaryBlendColor(clockBean.getSecondaryBlendColor());
        setAutoPrimaryColor(clockBean.isAutoPrimaryColor());
        setAutoSecondaryColor(clockBean.isAutoSecondaryColor());
        setInfoAreaColor(clockBean.getInfoAreaColor());
        setDisableContainerPassBlur(clockBean.isDisableContainerPassBlur());
        setClockEffect(clockBean.getClockEffect());
        setMultiWindowBlur(clockBean.supportMultiWindowBlur());
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getDisplayType() {
        return this.displayType;
    }
}
