package com.miui.clock.module;

import com.miui.clock.R;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class MiuiClassicPlusClockSmallInfo extends MiuiClassicPlusClockInfo {
    public MiuiClassicPlusClockSmallInfo(ClockBean clockBean) {
        super(clockBean);
    }

    @Override // com.miui.clock.module.MiuiClassicPlusClockInfo, com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_classic_plus_small;
    }

    @Override // com.miui.clock.module.MiuiClassicPlusClockInfo, com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "classic_plus_small";
    }
}
