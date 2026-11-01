package com.miui.clock.eastern.d;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;

/* JADX INFO: loaded from: classes.dex */
public class EasternArtDMinuteInfo extends EasternArtDBaseInfo {
    public EasternArtDMinuteInfo(ClockBean clockBean, int i) {
        super(clockBean, i);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_eastern_art_d_minute;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "eastern_d_minute";
    }
}
