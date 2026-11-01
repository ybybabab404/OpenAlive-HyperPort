package com.miui.clock.smartframe;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;

/* JADX INFO: loaded from: classes.dex */
public class MiuiSmartFrameSingleClockInfo extends MiuiSmartFrameClockBaseInfo {
    public MiuiSmartFrameSingleClockInfo(ClockBean clockBean) {
        super(clockBean);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_smart_frame_single;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "smart_frame_single";
    }
}
