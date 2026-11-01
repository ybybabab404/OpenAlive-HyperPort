package com.miui.clock.eastern.b;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;

/* JADX INFO: loaded from: classes.dex */
public class EasternArtBTimeBelowInfo extends EasternArtBBaseInfo {
    public EasternArtBTimeBelowInfo(ClockBean clockBean, int i) {
        super(clockBean, i);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_eastern_art_b_time_below;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "eastern_b";
    }
}
