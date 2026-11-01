package com.miui.clock.module;

import com.miui.clock.R;

/* JADX INFO: loaded from: classes.dex */
public class MiuiClassicMaxAodClockInfo extends MiuiClassicClockInfoBase {
    public ClockBean mClockBean;

    public MiuiClassicMaxAodClockInfo(ClockBean clockBean, int i) {
        super(clockBean);
        this.mClockBean = clockBean;
        ((MiuiClassicClockInfoBase) this).displayType = i;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_classic_max_aod;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "classic_max_single";
    }
}
