package com.miui.clock.aesthetics;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;

/* JADX INFO: loaded from: classes.dex */
public class MiuiAestheticsSingleClockInfo extends MiuiAestheticsClockBaseInfo {
    public MiuiAestheticsSingleClockInfo(ClockBean clockBean, int i) {
        super(clockBean);
        ((MiuiAestheticsClockBaseInfo) this).displayType = i;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_aesthetics_a_single;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "eastern_a_single";
    }
}
