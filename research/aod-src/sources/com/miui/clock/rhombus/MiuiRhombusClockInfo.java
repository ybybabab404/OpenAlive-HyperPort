package com.miui.clock.rhombus;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;

/* JADX INFO: loaded from: classes.dex */
public class MiuiRhombusClockInfo extends MiuiRhombusClockBaseInfo {
    public MiuiRhombusClockInfo(ClockBean clockBean, int i) {
        super(clockBean, i);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_rhombus;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "rhombus";
    }
}
