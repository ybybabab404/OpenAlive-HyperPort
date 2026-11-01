package com.miui.clock.tiny.doodle;

import com.miui.clock.tiny.R;
import com.miui.clock.tiny.model.TinyClockBean;

/* JADX INFO: loaded from: classes.dex */
public class DoodleClockInfo extends DoodleClockInfoBase {
    public boolean doAnimation;
    public int leftDoodleColor;
    public int rightDoodleColor;

    public DoodleClockInfo(TinyClockBean tinyClockBean, int i) {
        super(tinyClockBean, i);
        this.doAnimation = false;
    }

    @Override // com.miui.clock.tiny.doodle.DoodleClockInfoBase, com.miui.clock.tiny.model.TinyClockInfo
    public int getLayoutId(int i) {
        return R.layout.layout_doodle_clock_view;
    }

    public int getLeftDoodleColor() {
        return this.leftDoodleColor;
    }

    public int getRightDoodleColor() {
        return this.rightDoodleColor;
    }

    public boolean isDoAnimation() {
        return this.doAnimation;
    }

    public void setDoAnimation(boolean z) {
        this.doAnimation = z;
    }

    public void setLeftDoodleColor(int i) {
        this.leftDoodleColor = i;
    }

    public void setRightDoodleColor(int i) {
        this.rightDoodleColor = i;
    }

    @Override // com.miui.clock.tiny.doodle.DoodleClockInfoBase, com.miui.clock.tiny.model.TinyClockInfo
    public String toString() {
        return "DoodleClockInfo{leftDoodleColor=" + this.leftDoodleColor + ", rightDoodleColor=" + this.rightDoodleColor + ", doAnimation=" + this.doAnimation + "} -> " + super.toString();
    }
}
