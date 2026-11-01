package com.miui.clock.aesthetics;

import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockStyleInfo;

/* JADX INFO: loaded from: classes.dex */
public abstract class MiuiAestheticsClockBaseInfo extends ClockStyleInfo {
    public int displayType;
    public int mClockStyle = 1;
    public String weatherJson;

    public MiuiAestheticsClockBaseInfo(ClockBean clockBean) {
        parseClockBean(clockBean);
    }

    private void parseClockBean(ClockBean clockBean) {
        if (clockBean == null) {
            return;
        }
        setPrimaryColor(clockBean.getPrimaryColor());
        setSecondaryColor(clockBean.getSecondaryColor());
        setBlendColor(clockBean.getBlendColor());
        setSecondaryBlendColor(clockBean.getSecondaryBlendColor());
        setAutoPrimaryColor(clockBean.isAutoPrimaryColor());
        setAutoSecondaryColor(clockBean.isAutoSecondaryColor());
        setClockStyle(clockBean.getStyle());
        setWeatherJson(clockBean.getPresetWeatherJson());
        setInfoAreaColor(clockBean.getInfoAreaColor());
        setDisableContainerPassBlur(clockBean.isDisableContainerPassBlur());
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

    public String getWeatherJson() {
        return this.weatherJson;
    }

    public void setClockStyle(int i) {
        this.mClockStyle = i;
    }

    public void setWeatherJson(String str) {
        this.weatherJson = str;
    }
}
