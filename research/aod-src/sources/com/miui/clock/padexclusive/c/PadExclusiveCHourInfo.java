package com.miui.clock.padexclusive.c;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.Effect;

/* JADX INFO: loaded from: classes.dex */
public class PadExclusiveCHourInfo extends PadExclusiveCBaseInfo {
    public PadExclusiveCHourInfo(ClockBean clockBean) {
        super(clockBean);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_pad_exclusive_c_hour;
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBaseInfo, com.miui.clock.module.ClockStyleInfo
    public float[] getLockGradientParams() {
        if (getGradientType() == Effect.LAND_GRADIENT) {
            return getLockLandGradientParams();
        }
        int i = this.mGradientIndex;
        if (i == 1) {
            return Effect.PadExclusiveCHourEffect.Gradient_NORMAL_PARAMS_1;
        }
        if (i == 2) {
            return Effect.PadExclusiveCHourEffect.Gradient_NORMAL_PARAMS_2;
        }
        return i == 3 ? Effect.PadExclusiveCHourEffect.Gradient_NORMAL_PARAMS_3 : Effect.PadExclusiveCHourEffect.Gradient_NORMAL_PARAMS_0;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public float[] getLockLandGradientParams() {
        int i = this.mGradientIndex;
        if (i == 1) {
            return Effect.PadExclusiveCLandHourEffect.Gradient_NORMAL_PARAMS_1;
        }
        if (i == 2) {
            return Effect.PadExclusiveCLandHourEffect.Gradient_NORMAL_PARAMS_2;
        }
        return i == 3 ? Effect.PadExclusiveCLandHourEffect.Gradient_NORMAL_PARAMS_3 : Effect.PadExclusiveCLandHourEffect.Gradient_NORMAL_PARAMS_0;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "pad_exclusive_c";
    }
}
