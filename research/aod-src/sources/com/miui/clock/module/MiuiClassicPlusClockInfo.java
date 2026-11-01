package com.miui.clock.module;

import com.miui.clock.R;

/* JADX INFO: loaded from: classes.dex */
public class MiuiClassicPlusClockInfo extends MiuiClassicClockInfoBase {
    public ClockBean mClockBean;
    public int mTimeStyle;

    public MiuiClassicPlusClockInfo(ClockBean clockBean) {
        super(clockBean);
        this.mClockBean = clockBean;
    }

    public MiuiClassicPlusClockInfo(ClockBean clockBean, int i) {
        super(clockBean);
        this.mClockBean = clockBean;
        ((MiuiClassicClockInfoBase) this).displayType = i;
    }

    @Override // com.miui.clock.module.MiuiClassicClockInfoBase
    public void convertFromClockBean(ClockBean clockBean) {
        super.convertFromClockBean(clockBean);
        this.mTimeStyle = clockBean.getClassicLine2();
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_classic_plus;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "classic_plus";
    }
}
