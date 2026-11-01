package com.miui.clock.oversize.a;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.Effect;
import com.miui.clock.utils.ClockEffectUtils;

/* JADX INFO: loaded from: classes.dex */
public class OversizeAMinuteInfo extends OversizeABaseInfo {
    public OversizeAMinuteInfo(ClockBean clockBean, int i) {
        super(clockBean, i);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public float[] getAodGradientParams() {
        float[] fArr;
        int i = this.mGradientIndex;
        if (i == 1) {
            fArr = Effect.OversizeAMinuteEffect.Gradient_AOD_PARAMS_1;
        } else if (i == 2) {
            fArr = Effect.OversizeAMinuteEffect.Gradient_AOD_PARAMS_2;
        } else {
            fArr = i == 3 ? Effect.OversizeAMinuteEffect.Gradient_AOD_PARAMS_3 : Effect.OversizeAMinuteEffect.Gradient_AOD_PARAMS_0;
        }
        return ClockEffectUtils.scaleGradientParamsRadius(fArr, 0.5f);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_oversize_a_minute;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public float[] getLockGradientParams() {
        float[] fArr;
        int i = this.mGradientIndex;
        if (i == 1) {
            fArr = Effect.OversizeAMinuteEffect.Gradient_NORMAL_PARAMS_1;
        } else if (i == 2) {
            fArr = Effect.OversizeAMinuteEffect.Gradient_NORMAL_PARAMS_2;
        } else {
            fArr = i == 3 ? Effect.OversizeAMinuteEffect.Gradient_NORMAL_PARAMS_3 : Effect.OversizeAMinuteEffect.Gradient_NORMAL_PARAMS_0;
        }
        return ClockEffectUtils.scaleGradientParamsRadius(fArr, 0.5f);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "oversize_a_minute";
    }
}
