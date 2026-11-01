package com.miui.clock.rhombus;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;

/* JADX INFO: loaded from: classes.dex */
public class MiuiRhombusSingleClockInfo extends MiuiRhombusClockBaseInfo {
    public MiuiRhombusSingleClockInfo(ClockBean clockBean) {
        super(clockBean);
        ((MiuiRhombusClockBaseInfo) this).displayType = 1;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_rhombus_single;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "rhombus_single";
    }
}
