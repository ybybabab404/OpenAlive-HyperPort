package com.miui.clock.eastern.b;

import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockStyleInfo;

/* JADX INFO: loaded from: classes.dex */
public abstract class EasternArtBBaseInfo extends ClockStyleInfo {
    public int displayType;
    public int style;

    public EasternArtBBaseInfo(ClockBean clockBean) {
        parseClockBean(clockBean);
    }

    public EasternArtBBaseInfo(ClockBean clockBean, int i) {
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
        setInfoAreaColor(clockBean.getInfoAreaColor());
        setClockStyle(clockBean.getStyle());
        setAutoPrimaryColor(clockBean.isAutoPrimaryColor());
        setAutoSecondaryColor(clockBean.isAutoSecondaryColor());
        setDisableContainerPassBlur(clockBean.isDisableContainerPassBlur());
        setClockEffect(clockBean.getClockEffect());
        setMultiWindowBlur(clockBean.supportMultiWindowBlur());
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getDisplayType() {
        return this.displayType;
    }

    public void setClockStyle(int i) {
        this.style = i;
    }
}
