package com.miui.clock.eastern.b;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;

/* JADX INFO: loaded from: classes.dex */
public class EasternArtBNotificationInfo extends EasternArtBBaseInfo {
    public EasternArtBNotificationInfo(ClockBean clockBean) {
        super(clockBean);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_eastern_art_b_notification;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "eastern_b_vertical_notification";
    }
}
