package com.miui.clock.oversize.b;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.Effect;
import com.miui.clock.utils.ClockEffectUtils;

/* JADX INFO: loaded from: classes.dex */
public class OversizeBAodInfo extends OversizeBBaseInfo {
    public OversizeBAodInfo(ClockBean clockBean, int i) {
        super(clockBean, i);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public float[] getAodGradientParams() {
        float[] fArr;
        int i = this.mGradientIndex;
        if (i == 1) {
            fArr = Effect.OversizeBHourEffect.Gradient_AOD_PARAMS_1;
        } else if (i == 2) {
            fArr = Effect.OversizeBHourEffect.Gradient_AOD_PARAMS_2;
        } else {
            fArr = i == 3 ? Effect.OversizeBHourEffect.Gradient_AOD_PARAMS_3 : Effect.OversizeBHourEffect.Gradient_AOD_PARAMS_0;
        }
        return ClockEffectUtils.scaleGradientParamsRadius(fArr, 0.5f);
    }

    public float[] getAodHourGradientParams() {
        float[] fArr;
        int i = this.mGradientIndex;
        if (i == 1) {
            fArr = Effect.OversizeBHourEffect.Gradient_AOD_PARAMS_1;
        } else if (i == 2) {
            fArr = Effect.OversizeBHourEffect.Gradient_AOD_PARAMS_2;
        } else {
            fArr = i == 3 ? Effect.OversizeBHourEffect.Gradient_AOD_PARAMS_3 : Effect.OversizeBHourEffect.Gradient_AOD_PARAMS_0;
        }
        return ClockEffectUtils.scaleGradientParams(ClockEffectUtils.scaleGradientParamsRadius(fArr, 0.5f), 0.432f);
    }

    public float[] getAodMinuteGradientParams() {
        float[] fArr;
        int i = this.mGradientIndex;
        if (i == 1) {
            fArr = Effect.OversizeBMinuteEffect.Gradient_AOD_PARAMS_1;
        } else if (i == 2) {
            fArr = Effect.OversizeBMinuteEffect.Gradient_AOD_PARAMS_2;
        } else {
            fArr = i == 3 ? Effect.OversizeBMinuteEffect.Gradient_AOD_PARAMS_3 : Effect.OversizeBMinuteEffect.Gradient_AOD_PARAMS_0;
        }
        return ClockEffectUtils.scaleGradientParams(ClockEffectUtils.scaleGradientParamsRadius(fArr, 0.5f), 0.432f);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseInfo, com.miui.clock.module.ClockStyleInfo
    public /* bridge */ /* synthetic */ int getDisplayType() {
        return super.getDisplayType();
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_oversize_b_aod;
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseInfo, com.miui.clock.module.ClockStyleInfo
    public /* bridge */ /* synthetic */ int getSecondaryColor() {
        return super.getSecondaryColor();
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "oversize_b_single";
    }
}
