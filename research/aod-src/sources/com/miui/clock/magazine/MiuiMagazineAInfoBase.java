package com.miui.clock.magazine;

import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockStyleInfo;

/* JADX INFO: loaded from: classes.dex */
public abstract class MiuiMagazineAInfoBase extends ClockStyleInfo {
    public int displayType;
    public int mClockStyle = 1;

    public MiuiMagazineAInfoBase(ClockBean clockBean) {
        parseClockBean(clockBean);
    }

    private void parseClockBean(ClockBean clockBean) {
        if (clockBean == null) {
            return;
        }
        setPrimaryColor(clockBean.getPrimaryColor());
        setInfoAreaColor(clockBean.getInfoAreaColor());
        setBlendColor(clockBean.getBlendColor());
        setSecondaryBlendColor(clockBean.getSecondaryBlendColor());
        setClockStyle(clockBean.getStyle());
        setAutoPrimaryColor(clockBean.isAutoPrimaryColor());
        setDisableContainerPassBlur(clockBean.isDisableContainerPassBlur());
        setAnimationPrimaryColor(clockBean.getPrimaryColor());
        setAnimationSecondaryColor(clockBean.getSecondaryColor());
        setClockEffect(clockBean.getClockEffect());
        setMultiWindowBlur(clockBean.supportMultiWindowBlur());
    }

    public int getClockStyle() {
        return this.mClockStyle;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getDisplayType() {
        return this.displayType;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "magazine_a";
    }

    public void setClockStyle(int i) {
        this.mClockStyle = i;
    }
}
