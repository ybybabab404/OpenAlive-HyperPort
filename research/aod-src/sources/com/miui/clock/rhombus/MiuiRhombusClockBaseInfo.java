package com.miui.clock.rhombus;

import android.content.Context;
import android.graphics.Color;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.clock.hct.Hct;
import com.miui.clock.hct.HctSolver;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.Effect;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.GlobalColorUtils;
import com.miui.maml.data.VariableNames;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToDoubleFunction;

/* JADX INFO: loaded from: classes.dex */
public abstract class MiuiRhombusClockBaseInfo extends ClockStyleInfo {
    public int displayType;
    public boolean enableDiffusion;
    public boolean isDiffHourMinuteColor;
    public int mAodColonColor;
    public int mAodHourColor;
    public int mAodMinuteColor;
    public int mDarkMinuteColor;
    public int mLightMinuteColor;
    public int originInfoAreaColor;
    public int mClockStyle = 1;
    public List mList = new ArrayList();
    public Map maps = new HashMap();

    public class HctWrapper {
        public Hct hct;
        public String name;

        public HctWrapper(String str, Hct hct) {
            this.hct = hct;
            this.name = str;
        }

        public double getTone() {
            return this.hct.getTone();
        }
    }

    public MiuiRhombusClockBaseInfo(ClockBean clockBean) {
        parseClockBean(clockBean);
    }

    public MiuiRhombusClockBaseInfo(ClockBean clockBean, int i) {
        this.displayType = i;
        parseClockBean(clockBean);
    }

    private void parseClockBean(ClockBean clockBean) {
        if (clockBean == null) {
            return;
        }
        setPrimaryColor(clockBean.getPrimaryColor());
        setSecondaryColor(clockBean.getSecondaryColor());
        setBlendColor(clockBean.getBlendColor());
        setSecondaryBlendColor(clockBean.getSecondaryBlendColor());
        setInfoAreaColor(clockBean.getInfoAreaColor());
        setFullAodInfoColor(Color.parseColor("#80FFFFFF"));
        setClockStyle(clockBean.getStyle());
        setDiffHourMinuteColor(clockBean.isDiffHourMinuteColor());
        setAutoSecondaryColor(clockBean.isAutoSecondaryColor());
        setAutoPrimaryColor(clockBean.isAutoPrimaryColor());
        setEnableDiffusion(clockBean.isEnableDiffusion());
        setDisableContainerPassBlur(clockBean.isDisableContainerPassBlur());
        setAnimationPrimaryColor(clockBean.getPrimaryColor());
        setAnimationSecondaryColor(clockBean.getSecondaryColor());
        setClockEffect(clockBean.getClockEffect());
        setMultiWindowBlur(clockBean.supportMultiWindowBlur());
    }

    public void calculateFinalAODColor(Context context) {
        if (DeviceConfig.isHighTextContrastEnabled(context) && isAutoPrimaryColor()) {
            setAodHourColor(getAodPrimaryColor());
            setAodMinuteColor(getAodPrimaryColor());
            setAodColonColor(getAodSecondaryColor());
        } else if (isDiffHourMinuteColor()) {
            getDiffHourMinuteAodColor(context);
        } else {
            getNormalAodColor(context);
        }
    }

    public int calculateMinuteColor(int i, boolean z) {
        Hct hctFromInt = Hct.fromInt(i);
        double tone = hctFromInt.getTone();
        return (tone >= 40.0d ? !z : z) ? HctSolver.solveToInt(hctFromInt.getHue(), hctFromInt.getChroma(), Math.max(BaseStyleSelectView.COLLAPSE_SLIDE_VALUE, tone - 8.0d)) : HctSolver.solveToInt(hctFromInt.getHue(), hctFromInt.getChroma(), Math.min(100.0d, (0.8500000238418579d * tone) + 15.5d));
    }

    public int getAodColonColor() {
        return this.mAodColonColor;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public float[] getAodGradientParams() {
        float[] fArr;
        int i = this.mGradientIndex;
        if (i == 1) {
            fArr = Effect.RhombusEffect.Gradient_AOD_PARAMS_1;
        } else if (i == 2) {
            fArr = Effect.RhombusEffect.Gradient_AOD_PARAMS_2;
        } else {
            fArr = i == 3 ? Effect.RhombusEffect.Gradient_AOD_PARAMS_3 : Effect.RhombusEffect.Gradient_AOD_PARAMS_0;
        }
        return ClockEffectUtils.scaleGradientParamsRadius(fArr, 0.5f);
    }

    public int getAodHourColor() {
        return this.mAodHourColor;
    }

    public int getAodMinuteColor() {
        return this.mAodMinuteColor;
    }

    public int getClockStyle() {
        return this.mClockStyle;
    }

    public final void getDiffHourMinuteAodColor(Context context) {
        int iCalculateMinuteColor;
        int i = 0;
        boolean z = DeviceConfig.supportBackgroundBlur(context) && ClockEffectUtils.isBlurMixType(getClockEffect());
        int primaryColor = getPrimaryColor();
        if (z && !isAutoPrimaryColor()) {
            primaryColor = getBlendColor();
        }
        if ((primaryColor != -1 || (iCalculateMinuteColor = this.mLightMinuteColor) == 0) && (primaryColor != -16777216 || (iCalculateMinuteColor = this.mDarkMinuteColor) == 0)) {
            iCalculateMinuteColor = calculateMinuteColor(primaryColor, false);
            primaryColor = calculateMinuteColor(primaryColor, true);
        }
        int iTransformAodColor = GlobalColorUtils.transformAodColor(primaryColor);
        int iTransformAodColor2 = GlobalColorUtils.transformAodColor(iCalculateMinuteColor);
        int aodSecondaryColor = getAodSecondaryColor();
        Hct hctFromInt = Hct.fromInt(iTransformAodColor);
        Hct hctFromInt2 = Hct.fromInt(iTransformAodColor2);
        Hct hctFromInt3 = Hct.fromInt(aodSecondaryColor);
        this.mList.clear();
        this.mList.add(new HctWrapper("hour", hctFromInt));
        this.mList.add(new HctWrapper(VariableNames.VAR_MINUTE, hctFromInt2));
        this.mList.add(new HctWrapper("colon", hctFromInt3));
        Collections.sort(this.mList, Comparator.comparingDouble(new ToDoubleFunction() { // from class: com.miui.clock.rhombus.MiuiRhombusClockBaseInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.ToDoubleFunction
            public final double applyAsDouble(Object obj) {
                return ((MiuiRhombusClockBaseInfo.HctWrapper) obj).getTone();
            }
        }));
        ((HctWrapper) this.mList.get(0)).hct.setTone(50.0d);
        Map palette = getPalette();
        if (!z || !isAutoPrimaryColor() || palette == null) {
            while (i < this.mList.size()) {
                HctWrapper hctWrapper = (HctWrapper) this.mList.get(i);
                if ("hour".equals(hctWrapper.name)) {
                    setAodHourColor(hctWrapper.hct.toInt());
                } else if (VariableNames.VAR_MINUTE.equals(hctWrapper.name)) {
                    setAodMinuteColor(hctWrapper.hct.toInt());
                } else if ("colon".equals(hctWrapper.name)) {
                    setAodColonColor(hctWrapper.hct.toInt());
                }
                i++;
            }
            return;
        }
        while (i < this.mList.size()) {
            HctWrapper hctWrapper2 = (HctWrapper) this.mList.get(i);
            if ("colon".equals(hctWrapper2.name)) {
                setAodColonColor(hctWrapper2.hct.toInt());
            }
            i++;
        }
        if (isInfoTextDark()) {
            if (palette.get("secondary80") != null) {
                primaryColor = ((Integer) palette.get("secondary80")).intValue();
            }
            setAodHourColor(primaryColor);
            if (palette.get("secondary60") != null) {
                iCalculateMinuteColor = ((Integer) palette.get("secondary60")).intValue();
            }
            setAodMinuteColor(iCalculateMinuteColor);
        } else {
            setAodHourColor(Color.parseColor("#D9D9D9"));
            setAodMinuteColor(Color.parseColor("#A6A6A6"));
        }
        setAodColonColor(aodSecondaryColor);
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getDisplayType() {
        return this.displayType;
    }

    public int[] getHourAndMinuteBlendColor() {
        int iCalculateMinuteColor;
        int blendColor = getBlendColor();
        if (!isDiffHourMinuteColor()) {
            return new int[]{blendColor, blendColor};
        }
        if ((blendColor != -1 || (iCalculateMinuteColor = this.mLightMinuteColor) == 0) && (blendColor != -16777216 || (iCalculateMinuteColor = this.mDarkMinuteColor) == 0)) {
            iCalculateMinuteColor = calculateMinuteColor(blendColor, false);
            blendColor = calculateMinuteColor(blendColor, true);
        }
        return new int[]{blendColor, iCalculateMinuteColor};
    }

    public int[] getHourAndMinuteColor(Context context) {
        int iCalculateMinuteColor;
        int primaryColor = getPrimaryColor();
        if ((!DeviceConfig.isHighTextContrastEnabled(context) || !isAutoPrimaryColor()) && isDiffHourMinuteColor()) {
            if (DeviceConfig.supportBackgroundBlur(context) && ClockEffectUtils.isBlurMixType(getClockEffect())) {
                return new int[]{primaryColor, -6710887};
            }
            if ((primaryColor != -1 || (iCalculateMinuteColor = this.mLightMinuteColor) == 0) && (primaryColor != -16777216 || (iCalculateMinuteColor = this.mDarkMinuteColor) == 0)) {
                iCalculateMinuteColor = calculateMinuteColor(primaryColor, false);
                primaryColor = calculateMinuteColor(primaryColor, true);
            }
            return new int[]{primaryColor, iCalculateMinuteColor};
        }
        return new int[]{primaryColor, primaryColor};
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public float[] getLockGradientParams() {
        float[] fArr;
        int i = this.mGradientIndex;
        if (i == 1) {
            fArr = Effect.RhombusEffect.Gradient_NORMAL_PARAMS_1;
        } else if (i == 2) {
            fArr = Effect.RhombusEffect.Gradient_NORMAL_PARAMS_2;
        } else {
            fArr = i == 3 ? Effect.RhombusEffect.Gradient_NORMAL_PARAMS_3 : Effect.RhombusEffect.Gradient_NORMAL_PARAMS_0;
        }
        return ClockEffectUtils.scaleGradientParamsRadius(fArr, 0.5f);
    }

    public final void getNormalAodColor(Context context) {
        int aodPrimaryColor = getAodPrimaryColor();
        int aodSecondaryColor = getAodSecondaryColor();
        Map palette = getPalette();
        boolean z = ClockEffectUtils.isBlurMixType(getClockEffect()) && DeviceConfig.supportBackgroundBlur(context);
        if (!z || !isAutoPrimaryColor() || palette == null) {
            if (z) {
                setAodHourColor(getAodBlendColor());
                setAodMinuteColor(getAodBlendColor());
                setAodColonColor(aodSecondaryColor);
                return;
            } else {
                setAodHourColor(aodPrimaryColor);
                setAodMinuteColor(aodPrimaryColor);
                setAodColonColor(aodSecondaryColor);
                return;
            }
        }
        if (isInfoTextDark()) {
            if (palette.get("secondary70") != null) {
                aodPrimaryColor = ((Integer) palette.get("secondary70")).intValue();
            }
            setAodHourColor(aodPrimaryColor);
            setAodMinuteColor(aodPrimaryColor);
        } else {
            setAodHourColor(Color.parseColor("#BEBEBE"));
            setAodMinuteColor(Color.parseColor("#BEBEBE"));
        }
        if (isAutoSecondaryColor()) {
            setAodColonColor(Color.parseColor("#FF3535"));
        } else {
            setAodColonColor(aodSecondaryColor);
        }
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getSecondaryColor() {
        if (isSuperSaveOpen()) {
            return -65536;
        }
        return super.getSecondaryColor();
    }

    public boolean isDiffHourMinuteColor() {
        if (isSuperSaveOpen()) {
            return false;
        }
        return this.isDiffHourMinuteColor;
    }

    public boolean isEnableDiffusion() {
        return this.enableDiffusion;
    }

    public void setAodColonColor(int i) {
        this.mAodColonColor = i;
    }

    public void setAodHourColor(int i) {
        this.mAodHourColor = i;
    }

    public void setAodMinuteColor(int i) {
        this.mAodMinuteColor = i;
    }

    public void setClockStyle(int i) {
        this.mClockStyle = i;
    }

    public void setDarkMinuteColor(int i) {
        this.mDarkMinuteColor = i;
    }

    public void setDiffHourMinuteColor(boolean z) {
        this.isDiffHourMinuteColor = z;
    }

    public void setEnableDiffusion(boolean z) {
        this.enableDiffusion = z;
    }

    public void setLightMinuteColor(int i) {
        this.mLightMinuteColor = i;
    }

    public void setOriginInfoAreaColor(int i) {
        this.originInfoAreaColor = i;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public void updateAodHighTextColors() {
        if (isAutoPrimaryColor()) {
            setPrimaryColor(-1);
        }
    }
}
