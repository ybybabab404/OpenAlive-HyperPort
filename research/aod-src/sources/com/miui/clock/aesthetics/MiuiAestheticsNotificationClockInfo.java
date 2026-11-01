package com.miui.clock.aesthetics;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;

/* JADX INFO: loaded from: classes.dex */
public class MiuiAestheticsNotificationClockInfo extends MiuiAestheticsClockBaseInfo {
    public MiuiAestheticsNotificationClockInfo(ClockBean clockBean, int i) {
        super(clockBean);
        ((MiuiAestheticsClockBaseInfo) this).displayType = i;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_aesthetics_a_notification;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "eastern_a_notification";
    }
}
