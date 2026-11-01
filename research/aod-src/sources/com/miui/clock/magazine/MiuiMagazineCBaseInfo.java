package com.miui.clock.magazine;

import android.graphics.Color;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.Effect;
import com.miui.clock.utils.ClockEffectUtils;

/* JADX INFO: loaded from: classes.dex */
public abstract class MiuiMagazineCBaseInfo extends ClockStyleInfo {
    public int displayType;
    public boolean enableDiffusion;
    public int mClockStyle = 5;

    public MiuiMagazineCBaseInfo(ClockBean clockBean, int i) {
        this.displayType = i;
        parseClockBean(clockBean);
    }

    private void parseClockBean(ClockBean clockBean) {
        if (clockBean == null) {
            return;
        }
        setPrimaryColor(clockBean.getPrimaryColor());
        setSecondaryColor(clockBean.getSecondaryColor());
        setClockStyle(clockBean.getStyle());
        setEnableDiffusion(clockBean.isEnableDiffusion());
        setAutoSecondaryColor(clockBean.isAutoSecondaryColor());
        setAutoPrimaryColor(clockBean.isAutoPrimaryColor());
        setDisableContainerPassBlur(clockBean.isDisableContainerPassBlur());
        setAnimationPrimaryColor(clockBean.getPrimaryColor());
        setAnimationSecondaryColor(clockBean.getSecondaryColor());
        setBlendColor(clockBean.getBlendColor());
        setSecondaryBlendColor(clockBean.getSecondaryBlendColor());
        setClockEffect(clockBean.getClockEffect());
        setMultiWindowBlur(clockBean.supportMultiWindowBlur());
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public float[] getAodGradientParams() {
        float[] fArr;
        if (this.mClockStyle == 5) {
            int i = this.mGradientIndex;
            if (i == 1) {
                fArr = Effect.MagazineCStyle1Effect.Gradient_AOD_PARAMS_1;
            } else if (i == 2) {
                fArr = Effect.MagazineCStyle1Effect.Gradient_AOD_PARAMS_2;
            } else {
                fArr = i == 3 ? Effect.MagazineCStyle1Effect.Gradient_AOD_PARAMS_3 : Effect.MagazineCStyle1Effect.Gradient_AOD_PARAMS_0;
            }
        } else {
            int i2 = this.mGradientIndex;
            if (i2 == 1) {
                fArr = Effect.MagazineCStyle2Effect.Gradient_AOD_PARAMS_1;
            } else if (i2 == 2) {
                fArr = Effect.MagazineCStyle2Effect.Gradient_AOD_PARAMS_2;
            } else {
                fArr = i2 == 3 ? Effect.MagazineCStyle2Effect.Gradient_AOD_PARAMS_3 : Effect.MagazineCStyle2Effect.Gradient_AOD_PARAMS_0;
            }
        }
        return ClockEffectUtils.scaleGradientParamsRadius(fArr, 0.5f);
    }

    public int getClockStyle() {
        return this.mClockStyle;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getDisplayType() {
        return this.displayType;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public float[] getLockGradientParams() {
        float[] fArr;
        if (this.mClockStyle == 5) {
            int i = this.mGradientIndex;
            if (i == 1) {
                fArr = Effect.MagazineCStyle1Effect.Gradient_NORMAL_PARAMS_1;
            } else if (i == 2) {
                fArr = Effect.MagazineCStyle1Effect.Gradient_NORMAL_PARAMS_2;
            } else {
                fArr = i == 3 ? Effect.MagazineCStyle1Effect.Gradient_NORMAL_PARAMS_3 : Effect.MagazineCStyle1Effect.Gradient_NORMAL_PARAMS_0;
            }
        } else {
            int i2 = this.mGradientIndex;
            if (i2 == 1) {
                fArr = Effect.MagazineCStyle2Effect.Gradient_NORMAL_PARAMS_1;
            } else if (i2 == 2) {
                fArr = Effect.MagazineCStyle2Effect.Gradient_NORMAL_PARAMS_2;
            } else {
                fArr = i2 == 3 ? Effect.MagazineCStyle2Effect.Gradient_NORMAL_PARAMS_3 : Effect.MagazineCStyle2Effect.Gradient_NORMAL_PARAMS_0;
            }
        }
        return ClockEffectUtils.scaleGradientParamsRadius(fArr, 0.5f);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getSecondaryColor() {
        if (isSuperSaveOpen()) {
            return Color.parseColor("#757575");
        }
        int secondaryColor = super.getSecondaryColor();
        if (secondaryColor == 0) {
            return -1;
        }
        return secondaryColor;
    }

    public boolean isEnableDiffusion() {
        return this.enableDiffusion;
    }

    public void setClockStyle(int i) {
        this.mClockStyle = i;
    }

    public void setEnableDiffusion(boolean z) {
        this.enableDiffusion = z;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public void updateAodHighTextColors() {
        if (this.secondaryColor == 0) {
            this.secondaryColor = -1;
        }
    }
}
