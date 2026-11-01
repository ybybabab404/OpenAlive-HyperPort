package com.miui.clock.aesthetics;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;

/* JADX INFO: loaded from: classes.dex */
public class MiuiAestheticsClockInfo extends MiuiAestheticsClockBaseInfo {
    public MiuiAestheticsClockInfo(ClockBean clockBean, int i) {
        super(clockBean);
        ((MiuiAestheticsClockBaseInfo) this).displayType = i;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_aesthetics_a;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "eastern_a_minute";
    }
}
