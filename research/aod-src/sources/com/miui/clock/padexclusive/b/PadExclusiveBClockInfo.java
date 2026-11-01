package com.miui.clock.padexclusive.b;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.Effect;
import com.miui.clock.utils.ClockEffectUtils;

/* JADX INFO: loaded from: classes.dex */
public class PadExclusiveBClockInfo extends ClockStyleInfo {
    public String templateId;

    public PadExclusiveBClockInfo(ClockBean clockBean) {
        if (clockBean != null) {
            setTemplateId(clockBean.getTemplateId());
            setPrimaryColor(clockBean.getPrimaryColor());
            setSecondaryColor(clockBean.getSecondaryColor());
            setInfoAreaColor(clockBean.getInfoAreaColor());
            setAutoPrimaryColor(clockBean.isAutoPrimaryColor());
            setAutoSecondaryColor(clockBean.isAutoSecondaryColor());
            setDisableContainerPassBlur(clockBean.isDisableContainerPassBlur());
            setClockEffect(clockBean.getClockEffect());
            setBlendColor(clockBean.getBlendColor());
            setSecondaryBlendColor(clockBean.getSecondaryBlendColor());
            setMultiWindowBlur(clockBean.supportMultiWindowBlur());
        }
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.layout_miui_clock_pad_exclusive_b;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public float[] getLockGradientParams() {
        float[] fArr;
        if (getGradientType() == Effect.LAND_GRADIENT) {
            return getLockLandGradientParams();
        }
        int i = this.mGradientIndex;
        if (i == 1) {
            fArr = Effect.PadExclusiveBEffect.Gradient_NORMAL_PARAMS_1;
        } else if (i == 2) {
            fArr = Effect.PadExclusiveBEffect.Gradient_NORMAL_PARAMS_2;
        } else {
            fArr = i == 3 ? Effect.PadExclusiveBEffect.Gradient_NORMAL_PARAMS_3 : Effect.PadExclusiveBEffect.Gradient_NORMAL_PARAMS_0;
        }
        return ClockEffectUtils.scaleGradientParams(fArr, 1.0f, 133.5f, 0.0f);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public float[] getLockLandGradientParams() {
        float[] fArr;
        int i = this.mGradientIndex;
        if (i == 1) {
            fArr = Effect.PadExclusiveBLandEffect.Gradient_NORMAL_PARAMS_1;
        } else if (i == 2) {
            fArr = Effect.PadExclusiveBLandEffect.Gradient_NORMAL_PARAMS_2;
        } else {
            fArr = i == 3 ? Effect.PadExclusiveBLandEffect.Gradient_NORMAL_PARAMS_3 : Effect.PadExclusiveBLandEffect.Gradient_NORMAL_PARAMS_0;
        }
        return ClockEffectUtils.scaleGradientParams(fArr, 1.0f, 193.0f, 0.0f);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "pad_exclusive_b";
    }

    public void setTemplateId(String str) {
        this.templateId = str;
    }
}
