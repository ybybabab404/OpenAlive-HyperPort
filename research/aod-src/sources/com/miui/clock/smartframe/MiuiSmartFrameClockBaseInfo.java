package com.miui.clock.smartframe;

import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockStyleInfo;

/* JADX INFO: loaded from: classes.dex */
public abstract class MiuiSmartFrameClockBaseInfo extends ClockStyleInfo {
    public int mClockStyle = 1;

    public MiuiSmartFrameClockBaseInfo(ClockBean clockBean) {
        parseClockBean(clockBean);
    }

    private void parseClockBean(ClockBean clockBean) {
        if (clockBean == null) {
            return;
        }
        setPrimaryColor(clockBean.getPrimaryColor());
        setClockStyle(clockBean.getStyle());
        setAutoPrimaryColor(clockBean.isAutoPrimaryColor());
        setSecondaryColor(clockBean.getSecondaryColor());
        setAutoSecondaryColor(clockBean.isAutoSecondaryColor());
        setMultiWindowBlur(clockBean.supportMultiWindowBlur());
    }

    public int getClockStyle() {
        return this.mClockStyle;
    }

    public void setClockStyle(int i) {
        this.mClockStyle = i;
    }
}
