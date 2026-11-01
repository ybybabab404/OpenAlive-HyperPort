package com.miui.clock.padexclusive.a.clockinfo;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;

/* JADX INFO: loaded from: classes.dex */
public class PadExclusiveASignatureClockInfo extends PadExclusiveABaseInfo {
    public String signatureLine1;
    public String signatureLine2;
    public String signatureLine3;

    public PadExclusiveASignatureClockInfo(ClockBean clockBean, int i) {
        super(clockBean, i);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_pad_exclusive_a_signal;
    }

    public String getSignatureLine1() {
        return this.signatureLine1;
    }

    public String getSignatureLine2() {
        return this.signatureLine2;
    }

    public String getSignatureLine3() {
        return this.signatureLine3;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return this.type;
    }

    @Override // com.miui.clock.padexclusive.a.clockinfo.PadExclusiveABaseInfo
    public void parseClockBean(ClockBean clockBean) {
        if (clockBean == null) {
            return;
        }
        setSignatureLine1(clockBean.getSignatureLine1());
        setSignatureLine2(clockBean.getSignatureLine2());
        setSignatureLine3(clockBean.getSignatureLine3());
        super.parseClockBean(clockBean);
    }

    public void setSignatureLine1(String str) {
        this.signatureLine1 = str;
    }

    public void setSignatureLine2(String str) {
        this.signatureLine2 = str;
    }

    public void setSignatureLine3(String str) {
        this.signatureLine3 = str;
    }
}
