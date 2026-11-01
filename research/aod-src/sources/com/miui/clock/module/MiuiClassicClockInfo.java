package com.miui.clock.module;

import com.miui.clock.R;

/* JADX INFO: loaded from: classes.dex */
public class MiuiClassicClockInfo extends MiuiClassicClockInfoBase {
    public ClockBean mClockBean;

    public MiuiClassicClockInfo() {
    }

    public MiuiClassicClockInfo(ClockBean clockBean, int i) {
        super(clockBean);
        this.mClockBean = clockBean;
        ((MiuiClassicClockInfoBase) this).displayType = i;
    }

    @Override // com.miui.clock.module.MiuiClassicClockInfoBase
    public void convertFromClockBean(ClockBean clockBean) {
        super.convertFromClockBean(clockBean);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_classic;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "classic";
    }
}
