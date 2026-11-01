package com.miui.clock.tiny.pets.interactive;

import com.miui.clock.tiny.R;
import com.miui.clock.tiny.model.TinyClockBean;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public class InteractivePetsClockInfo extends InteractivePetClockInfoBase {
    public int actionIndex;

    public InteractivePetsClockInfo(TinyClockBean tinyClockBean, int i) {
        super(tinyClockBean, i);
        this.actionIndex = -1;
        this.actionIndex = new Random().nextInt(InteractivePetsStyle.mPetsAodStaticAnimationFrameResource[getPetsIndex()].length);
    }

    public int getActionIndex() {
        return this.actionIndex;
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetClockInfoBase, com.miui.clock.tiny.model.TinyClockInfo
    public int getLayoutId(int i) {
        return isAODType() ? R.layout.layout_interactive_pets_aod_clock : R.layout.layout_pets_interactive_clock_view;
    }

    public int getPetsIndex() {
        if (getClockStyle() < 1) {
            return 0;
        }
        return (getClockStyle() - 1) % InteractivePetsStyle.mPetsAodStaticAnimationFrameResource.length;
    }
}
