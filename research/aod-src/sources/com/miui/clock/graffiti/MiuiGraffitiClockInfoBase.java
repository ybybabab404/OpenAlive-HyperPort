package com.miui.clock.graffiti;

import android.util.Log;
import com.miui.clock.R;
import com.miui.clock.SettingsObserver;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockStyleInfo;

/* JADX INFO: loaded from: classes.dex */
public class MiuiGraffitiClockInfoBase extends ClockStyleInfo {
    public int mClockStyle = 1;
    public boolean hiddenDoodle = false;

    public MiuiGraffitiClockInfoBase(ClockBean clockBean) {
        parseClockBean(clockBean);
    }

    private void parseClockBean(ClockBean clockBean) {
        if (clockBean == null) {
            return;
        }
        setPrimaryColor(clockBean.getPrimaryColor());
        setAutoPrimaryColor(clockBean.isAutoPrimaryColor());
        setClockStyle(clockBean.getStyle());
        setHiddenDoodle(GraffitiStyle.isHiddenDoodleByFlag(clockBean.getExtraFlag()));
        setAnimationPrimaryColor(clockBean.getPrimaryColor());
        setAnimationSecondaryColor(clockBean.getSecondaryColor());
        setMultiWindowBlur(clockBean.supportMultiWindowBlur());
        Log.d("MiuiGraffitiClockInfoBase", "parseClockBean:   isHiddenDoodle: " + isHiddenDoodle());
    }

    public int getClockStyle() {
        return this.mClockStyle;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_graffiti;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "doodle";
    }

    public boolean isHiddenDoodle() {
        if (this.hiddenDoodle) {
            return true;
        }
        return shouldObserveSettings() && SettingsObserver.getBoolean("lockscreen_template_doodle_text_support");
    }

    public void setClockStyle(int i) {
        this.mClockStyle = i;
    }

    public void setHiddenDoodle(boolean z) {
        this.hiddenDoodle = z;
    }
}
