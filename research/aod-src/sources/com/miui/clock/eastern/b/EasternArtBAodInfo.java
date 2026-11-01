package com.miui.clock.eastern.b;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;

/* JADX INFO: loaded from: classes.dex */
public class EasternArtBAodInfo extends EasternArtBBaseInfo {
    public EasternArtBAodInfo(ClockBean clockBean) {
        super(clockBean);
    }

    public EasternArtBAodInfo(ClockBean clockBean, int i) {
        super(clockBean, i);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_eastern_art_b_aod;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "eastern_b_aod";
    }
}
