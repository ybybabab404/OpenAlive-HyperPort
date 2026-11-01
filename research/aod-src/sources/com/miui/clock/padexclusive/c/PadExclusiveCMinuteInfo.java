package com.miui.clock.padexclusive.c;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.Effect;

/* JADX INFO: loaded from: classes.dex */
public class PadExclusiveCMinuteInfo extends PadExclusiveCBaseInfo {
    public PadExclusiveCMinuteInfo(ClockBean clockBean) {
        super(clockBean);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_pad_exclusive_c_minute;
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBaseInfo, com.miui.clock.module.ClockStyleInfo
    public float[] getLockGradientParams() {
        if (getGradientType() == Effect.LAND_GRADIENT) {
            return getLockLandGradientParams();
        }
        int i = this.mGradientIndex;
        if (i == 1) {
            return Effect.PadExclusiveCMinuteEffect.Gradient_NORMAL_PARAMS_1;
        }
        if (i == 2) {
            return Effect.PadExclusiveCMinuteEffect.Gradient_NORMAL_PARAMS_2;
        }
        return i == 3 ? Effect.PadExclusiveCMinuteEffect.Gradient_NORMAL_PARAMS_3 : Effect.PadExclusiveCMinuteEffect.Gradient_NORMAL_PARAMS_0;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public float[] getLockLandGradientParams() {
        int i = this.mGradientIndex;
        if (i == 1) {
            return Effect.PadExclusiveCLandMinuteEffect.Gradient_NORMAL_PARAMS_1;
        }
        if (i == 2) {
            return Effect.PadExclusiveCLandMinuteEffect.Gradient_NORMAL_PARAMS_2;
        }
        return i == 3 ? Effect.PadExclusiveCLandMinuteEffect.Gradient_NORMAL_PARAMS_3 : Effect.PadExclusiveCLandMinuteEffect.Gradient_NORMAL_PARAMS_0;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "pad_exclusive_c_minute";
    }
}
