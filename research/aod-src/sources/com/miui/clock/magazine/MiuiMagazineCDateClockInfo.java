package com.miui.clock.magazine;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;

/* JADX INFO: loaded from: classes.dex */
public class MiuiMagazineCDateClockInfo extends MiuiMagazineCBaseInfo {
    public MiuiMagazineCDateClockInfo(ClockBean clockBean, int i) {
        super(clockBean, i);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_magazine_c_date_only;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "magazine_c_date_only";
    }
}
