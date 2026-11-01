package com.miui.clock.tiny.pets.ai;

import com.miui.clock.tiny.R;
import com.miui.clock.tiny.model.TinyClockBean;
import com.miui.clock.tiny.pets.interactive.InteractivePetsClockInfo;
import com.miui.clock.tiny.pets.interactive.InteractivePetsStyle;

/* JADX INFO: loaded from: classes.dex */
public class AiPetsClockInfo extends InteractivePetsClockInfo {
    public AiPetsClockInfo(TinyClockBean tinyClockBean, int i) {
        super(tinyClockBean, i);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetClockInfoBase
    public InteractivePetsStyle.InteractivePetsClockViewColorStyle getClockColorStyleInfo(int i) {
        return new InteractivePetsStyle.InteractivePetsClockViewColorStyle(getPrimaryColor());
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockInfo, com.miui.clock.tiny.pets.interactive.InteractivePetClockInfoBase, com.miui.clock.tiny.model.TinyClockInfo
    public int getLayoutId(int i) {
        return isAODType() ? R.layout.layout_ai_pets_aod_clock : R.layout.layout_pets_interactive_clock_view;
    }
}
