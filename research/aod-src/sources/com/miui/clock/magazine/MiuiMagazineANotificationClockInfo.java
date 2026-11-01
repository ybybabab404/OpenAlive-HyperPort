package com.miui.clock.magazine;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;

/* JADX INFO: loaded from: classes.dex */
public class MiuiMagazineANotificationClockInfo extends MiuiMagazineAInfoBase {
    public MiuiMagazineANotificationClockInfo(ClockBean clockBean) {
        super(clockBean);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_magazine_a_notification;
    }

    @Override // com.miui.clock.magazine.MiuiMagazineAInfoBase, com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "magazine_a_notification";
    }
}
