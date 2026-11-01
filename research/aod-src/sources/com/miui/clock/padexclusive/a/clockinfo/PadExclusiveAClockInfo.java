package com.miui.clock.padexclusive.a.clockinfo;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.Effect;

/* JADX INFO: loaded from: classes.dex */
public class PadExclusiveAClockInfo extends PadExclusiveABaseInfo {
    public PadExclusiveAClockInfo(ClockBean clockBean, int i) {
        super(clockBean, i);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_pad_exclusive_a;
    }

    @Override // com.miui.clock.padexclusive.a.clockinfo.PadExclusiveABaseInfo, com.miui.clock.module.ClockStyleInfo
    public float[] getLockGradientParams() {
        if (getGradientType() == Effect.LAND_GRADIENT) {
            return getLockLandGradientParams();
        }
        int i = this.mGradientIndex;
        if (i == 1) {
            return Effect.PadExclusiveAEffect.Gradient_NORMAL_PARAMS_1;
        }
        if (i == 2) {
            return Effect.PadExclusiveAEffect.Gradient_NORMAL_PARAMS_2;
        }
        return i == 3 ? Effect.PadExclusiveAEffect.Gradient_NORMAL_PARAMS_3 : Effect.PadExclusiveAEffect.Gradient_NORMAL_PARAMS_0;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public float[] getLockLandGradientParams() {
        int i = this.mGradientIndex;
        if (i == 1) {
            return Effect.PadExclusiveALandEffect.Gradient_NORMAL_PARAMS_1;
        }
        if (i == 2) {
            return Effect.PadExclusiveALandEffect.Gradient_NORMAL_PARAMS_2;
        }
        return i == 3 ? Effect.PadExclusiveALandEffect.Gradient_NORMAL_PARAMS_3 : Effect.PadExclusiveALandEffect.Gradient_NORMAL_PARAMS_0;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return this.type;
    }
}
