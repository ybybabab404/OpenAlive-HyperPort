package com.miui.clock.magazine;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.Effect;
import com.miui.clock.utils.ClockEffectUtils;

/* JADX INFO: loaded from: classes.dex */
public class MiuiMagazineCSingleClockInfo extends MiuiMagazineCBaseInfo {
    public MiuiMagazineCSingleClockInfo(ClockBean clockBean, int i) {
        super(clockBean, i);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBaseInfo, com.miui.clock.module.ClockStyleInfo
    public float[] getAodGradientParams() {
        float[] fArr;
        float[] fArrScaleGradientParams;
        float[] fArr2;
        if (this.mClockStyle == 5) {
            int i = this.mGradientIndex;
            if (i == 1) {
                fArr2 = Effect.MagazineCStyle1Effect.Gradient_AOD_PARAMS_1;
            } else if (i == 2) {
                fArr2 = Effect.MagazineCStyle1Effect.Gradient_AOD_PARAMS_2;
            } else {
                fArr2 = i == 3 ? Effect.MagazineCStyle1Effect.Gradient_AOD_PARAMS_3 : Effect.MagazineCStyle1Effect.Gradient_AOD_PARAMS_0;
            }
            fArrScaleGradientParams = ClockEffectUtils.scaleGradientParams(fArr2, 1.0f, 0.0f, -54.0f);
        } else {
            int i2 = this.mGradientIndex;
            if (i2 == 1) {
                fArr = Effect.MagazineCStyle2Effect.Gradient_AOD_PARAMS_1;
            } else if (i2 == 2) {
                fArr = Effect.MagazineCStyle2Effect.Gradient_AOD_PARAMS_2;
            } else {
                fArr = i2 == 3 ? Effect.MagazineCStyle2Effect.Gradient_AOD_PARAMS_3 : Effect.MagazineCStyle2Effect.Gradient_AOD_PARAMS_0;
            }
            fArrScaleGradientParams = ClockEffectUtils.scaleGradientParams(fArr, 0.4f);
        }
        return ClockEffectUtils.scaleGradientParamsRadius(fArrScaleGradientParams, 0.5f);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.miui_clock_layout_magazine_c_single;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "magazine_c_single";
    }
}
