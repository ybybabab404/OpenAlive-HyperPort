package com.miui.clock.tiny.doodle;

import com.miui.clock.tiny.R;
import com.miui.clock.tiny.model.TinyClockBean;
import com.miui.clock.tiny.model.TinyClockInfo;

/* JADX INFO: loaded from: classes.dex */
public class DoodleClockInfoBase extends TinyClockInfo {
    public int aodPrimaryColor;
    public int dayBottomColor;
    public int dayColor;
    public float designWidth;
    public boolean needInverseColor;
    public int todayColor;
    public int todayDayBottom;

    public DoodleClockInfoBase(TinyClockBean tinyClockBean, int i) {
        super(tinyClockBean, i);
        this.needInverseColor = false;
        this.designWidth = 372.0f;
        parseClockBean(tinyClockBean);
    }

    public int getAodPrimaryColor() {
        return this.aodPrimaryColor;
    }

    public DoodleStyle.DoodleClockViewColorStyle getClockColorStyleInfo(int i) {
        int clockStyle = getClockStyle();
        DoodleStyle.DoodleClockViewColorStyle[] doodleClockViewColorStyleArr = DoodleStyle.colorStyles;
        DoodleStyle.DoodleClockViewColorStyle doodleClockViewColorStyle = doodleClockViewColorStyleArr[clockStyle % doodleClockViewColorStyleArr.length];
        boolean zIsWallpaperDark180 = i == 2 ? isWallpaperDark180() : isWallpaperDark0();
        if (!this.needInverseColor || isAODType()) {
            zIsWallpaperDark180 = doodleClockViewColorStyle.isDefaultDark();
        }
        doodleClockViewColorStyle.setDark(zIsWallpaperDark180);
        return doodleClockViewColorStyle;
    }

    public int getDayBottomColor() {
        return this.dayBottomColor;
    }

    public int getDayColor() {
        return this.dayColor;
    }

    public float getDesignWidthDp() {
        return this.designWidth;
    }

    @Override // com.miui.clock.tiny.model.TinyClockInfo
    public int getLayoutId(int i) {
        return R.layout.layout_doodle_clock_base_view;
    }

    @Override // com.miui.clock.tiny.model.TinyClockInfo
    public float getScale() {
        return this.designWidth / 372.0f;
    }

    public int getTodayColor() {
        return this.todayColor;
    }

    public int getTodayDayBottom() {
        return this.todayDayBottom;
    }

    public final void parseClockBean(TinyClockBean tinyClockBean) {
        setScale(tinyClockBean.getScale());
        setNeedInverseColor(tinyClockBean.isNeedInverseColor());
        setAodPrimaryColor(DoodleStyle.DoodleClockViewColorStyle.AOD_CLOCK_BLACK);
    }

    public void setAodPrimaryColor(int i) {
        this.aodPrimaryColor = i;
    }

    public void setDayBottomColor(int i) {
        this.dayBottomColor = i;
    }

    public void setDayColor(int i) {
        this.dayColor = i;
    }

    public void setDesignWidthDp(float f) {
        this.designWidth = f;
    }

    public void setNeedInverseColor(boolean z) {
        this.needInverseColor = z;
    }

    @Override // com.miui.clock.tiny.model.TinyClockInfo
    public void setScale(float f) {
        super.setScale(f);
        this.designWidth = f * 372.0f;
    }

    public void setTodayColor(int i) {
        this.todayColor = i;
    }

    public void setTodayDayBottom(int i) {
        this.todayDayBottom = i;
    }

    @Override // com.miui.clock.tiny.model.TinyClockInfo
    public String toString() {
        return "DoodleClockInfoBase{, todayColor=" + this.todayColor + ", dayColor=" + this.dayColor + ", todayDayBottom=" + this.todayDayBottom + ", dayBottomColor=" + this.dayBottomColor + "} -> " + super.toString();
    }
}
