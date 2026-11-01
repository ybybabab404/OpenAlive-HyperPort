package com.miui.clock.eastern.c.clokcinfo;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;

/* JADX INFO: loaded from: classes.dex */
public class EasternArtCSingleInfo extends EasternArtCBaseInfo {
    public EasternArtCSingleInfo(ClockBean clockBean, int i) {
        super(clockBean, i);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_eastern_art_c_single;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return null;
    }
}
