package com.miui.clock.magazine;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;

/* JADX INFO: loaded from: classes.dex */
public class MiuiMagazineAClockInfo extends MiuiMagazineAInfoBase {
    public MiuiMagazineAClockInfo(ClockBean clockBean, int i) {
        super(clockBean);
        ((MiuiMagazineAInfoBase) this).displayType = i;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_magazine_a;
    }

    @Override // com.miui.clock.magazine.MiuiMagazineAInfoBase, com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "magazine_a";
    }
}
