package com.miui.clock.graffiti;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;

/* JADX INFO: loaded from: classes.dex */
public class MiuiGraffitiClockInfo extends MiuiGraffitiClockInfoBase {
    public MiuiGraffitiClockInfo(ClockBean clockBean) {
        super(clockBean);
    }

    @Override // com.miui.clock.graffiti.MiuiGraffitiClockInfoBase, com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_graffiti;
    }
}
