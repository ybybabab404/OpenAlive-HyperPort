package com.miui.clock.eastern.c.clokcinfo;

import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockStyleInfo;

/* JADX INFO: loaded from: classes.dex */
public abstract class EasternArtCBaseInfo extends ClockStyleInfo {
    public int style;

    public EasternArtCBaseInfo(ClockBean clockBean, int i) {
        super(clockBean, i);
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

    public int getClockStyle() {
        return this.style;
    }

    public void setClockStyle(int i) {
        this.style = i;
    }
}
