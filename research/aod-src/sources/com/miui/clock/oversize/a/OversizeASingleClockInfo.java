package com.miui.clock.oversize.a;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.Effect;
import com.miui.clock.utils.ClockEffectUtils;

/* JADX INFO: loaded from: classes.dex */
public class OversizeASingleClockInfo extends OversizeABaseInfo {
    public OversizeASingleClockInfo(ClockBean clockBean, int i) {
        super(clockBean, i);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public float[] getAodGradientParams() {
        int i = this.mGradientIndex;
        if (i == 1) {
            return Effect.OversizeAMinuteEffect.Gradient_AOD_PARAMS_1;
        }
        if (i == 2) {
            return Effect.OversizeAMinuteEffect.Gradient_AOD_PARAMS_2;
        }
        return i == 3 ? Effect.OversizeAMinuteEffect.Gradient_AOD_PARAMS_3 : Effect.OversizeAMinuteEffect.Gradient_AOD_PARAMS_0;
    }

    public float[] getAodHourGradientParams() {
        float[] fArr;
        int i = this.mGradientIndex;
        if (i == 1) {
            fArr = Effect.OversizeAHourEffect.Gradient_AOD_PARAMS_1;
        } else if (i == 2) {
            fArr = Effect.OversizeAHourEffect.Gradient_AOD_PARAMS_2;
        } else {
            fArr = i == 3 ? Effect.OversizeAHourEffect.Gradient_AOD_PARAMS_3 : Effect.OversizeAHourEffect.Gradient_AOD_PARAMS_0;
        }
        return ClockEffectUtils.scaleGradientParams(ClockEffectUtils.scaleGradientParamsRadius(fArr, 0.5f), 0.4f);
    }

    public float[] getAodMinuteGradientParams() {
        float[] fArr;
        int i = this.mGradientIndex;
        if (i == 1) {
            fArr = Effect.OversizeAMinuteEffect.Gradient_AOD_PARAMS_1;
        } else if (i == 2) {
            fArr = Effect.OversizeAMinuteEffect.Gradient_AOD_PARAMS_2;
        } else {
            fArr = i == 3 ? Effect.OversizeAMinuteEffect.Gradient_AOD_PARAMS_3 : Effect.OversizeAMinuteEffect.Gradient_AOD_PARAMS_0;
        }
        return ClockEffectUtils.scaleGradientParams(ClockEffectUtils.scaleGradientParamsRadius(fArr, 0.5f), 0.4f);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_oversize_a_single;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "oversize_a_single";
    }
}
