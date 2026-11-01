package com.miui.clock.tiny.pets.interactive;

import com.miui.clock.tiny.R;
import com.miui.clock.tiny.model.TinyClockBean;
import com.miui.clock.tiny.model.TinyClockInfo;

/* JADX INFO: loaded from: classes.dex */
public class InteractivePetClockInfoBase extends TinyClockInfo {
    public float designWidth;

    public InteractivePetClockInfoBase(TinyClockBean tinyClockBean, int i) {
        super(tinyClockBean, i);
        this.designWidth = 372.0f;
        setScale(tinyClockBean.getScale());
    }

    public InteractivePetsStyle.InteractivePetsClockViewColorStyle getClockColorStyleInfo(int i) {
        return InteractivePetsStyle.getClockColorStyleInfo(getClockStyle());
    }

    public float getDesignWidthDp() {
        return this.designWidth;
    }

    @Override // com.miui.clock.tiny.model.TinyClockInfo
    public int getLayoutId(int i) {
        return R.layout.layout_interactive_pets_clock_base_view;
    }

    @Override // com.miui.clock.tiny.model.TinyClockInfo
    public float getScale() {
        return this.designWidth / 372.0f;
    }

    public void setDesignWidthDp(float f) {
        this.designWidth = f;
    }

    @Override // com.miui.clock.tiny.model.TinyClockInfo
    public void setScale(float f) {
        super.setScale(f);
        this.designWidth = f * 372.0f;
    }
}
