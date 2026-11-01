package com.miui.clock.module;

import com.miui.clock.R;

/* JADX INFO: loaded from: classes.dex */
public class MiuiDualClockInfo extends ClockStyleInfo {
    public String mLocalCity;

    public MiuiDualClockInfo(ClockBean clockBean) {
        parseClockBean(clockBean);
    }

    private void parseClockBean(ClockBean clockBean) {
        if (clockBean == null) {
            return;
        }
        setPrimaryColor(clockBean.getPrimaryColor());
        setInfoAreaColor(clockBean.getInfoAreaColor());
        this.mLocalCity = clockBean.getDualClockLocalCity();
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_dual_clock;
    }

    public String getLocalCity() {
        return this.mLocalCity;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "dual";
    }
}
