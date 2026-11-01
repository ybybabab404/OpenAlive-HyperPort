package com.miui.clock.rhombus;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;

/* JADX INFO: loaded from: classes.dex */
public class MiuiRhombusNotificationClockInfo extends MiuiRhombusClockBaseInfo {
    public MiuiRhombusNotificationClockInfo(ClockBean clockBean) {
        super(clockBean);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_rhombus_notification;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "rhombus_notification";
    }
}
