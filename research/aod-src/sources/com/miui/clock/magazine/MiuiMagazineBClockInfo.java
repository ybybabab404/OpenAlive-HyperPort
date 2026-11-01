package com.miui.clock.magazine;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;

/* JADX INFO: loaded from: classes.dex */
public class MiuiMagazineBClockInfo extends MiuiMagazineBBaseInfo {
    public MiuiMagazineBClockInfo(ClockBean clockBean, int i) {
        super(clockBean, i);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_magazine_b;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "magazine_b";
    }
}
