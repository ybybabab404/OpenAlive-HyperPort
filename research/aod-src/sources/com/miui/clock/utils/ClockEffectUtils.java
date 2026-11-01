package com.miui.clock.utils;

import android.util.Log;
import android.view.View;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.Effect;

/* JADX INFO: loaded from: classes.dex */
public class ClockEffectUtils {
    public static void clearClockEffectsContainer(View view, ClockStyleInfo clockStyleInfo) {
        MiuiBlurUtils.clearContainerPassBlur(view);
        MiuiBlurUtils.clearDiffEffectContainer(view);
    }

    public static void clearClockEffectsContainer(View view, ClockStyleInfo clockStyleInfo, boolean z) {
        int clockEffect = clockStyleInfo.getClockEffect();
        if (z || !isBlurMixType(clockEffect)) {
            MiuiBlurUtils.clearContainerPassBlur(view);
        }
        if (z || !isDifferenceType(clockEffect)) {
            MiuiBlurUtils.clearDiffEffectContainer(view);
        }
        if (z || !isGlassType(clockEffect)) {
            MiuiBlurUtils.clearGlassBlurContainer(view);
        }
    }

    public static void clearClockEffectsView(View view, ClockStyleInfo clockStyleInfo, boolean z) {
        int clockEffect = clockStyleInfo.getClockEffect();
        if (z || !isBlurMixType(clockEffect)) {
            MiuiBlurUtils.clearMemberBlendColor(view);
        }
        if (z || !isDifferenceType(clockEffect)) {
            MiuiBlurUtils.clearDiffEffectMethod(view);
        }
        if (z || !isGlassType(clockEffect)) {
            MiuiBlurUtils.clearGlassEffectMethod(view);
        }
    }

    public static void clearClockGradualEffect(View view, ClockStyleInfo clockStyleInfo, boolean z) {
        int clockEffect = clockStyleInfo.getClockEffect();
        if (z || !isGradualType(clockEffect)) {
            MiuiBlurUtils.clearGradientBlurMethod(view);
        }
    }

    public static boolean hasBlurBlendRendering(int i) {
        return isBlurMixType(i) || isGlassType(i);
    }

    public static boolean isBlurMixType(int i) {
        return i == 1;
    }

    public static boolean isDifferenceType(int i) {
        return i == 2;
    }

    public static boolean isGlassType(int i) {
        return i == 5;
    }

    public static boolean isGlowType(int i) {
        return false;
    }

    public static boolean isGradualType(int i) {
        return i == 4;
    }

    public static boolean isLessThanOS2() {
        return false;
    }

    public static boolean isSolidType(int i) {
        return i == 0;
    }

    public static boolean needFallbackToSolid(int i) {
        return isBlurMixType(i) || isGlassType(i);
    }

    public static boolean needPickSolidColor(int i) {
        return isGradualType(i) || isSolidType(i);
    }

    public static boolean needPickSolidColor(int i, boolean z) {
        if (needPickSolidColor(i)) {
            return true;
        }
        return !z && needFallbackToSolid(i);
    }

    public static float[] scaleGradientParams(float[] fArr, float f) {
        return scaleGradientParams(fArr, f, 0.0f, 0.0f);
    }

    public static float[] scaleGradientParams(float[] fArr, float f, float f2, float f3) {
        float[] fArr2 = new float[fArr.length];
        if (f <= 0.0f) {
            f = 1.0f;
        }
        for (int i = 0; i < fArr.length; i++) {
            if (i == 4 || i == 9 || i == 14) {
                fArr2[i] = fArr[i];
            } else if (i == 0 || i == 5 || i == 10) {
                fArr2[i] = (fArr[i] * f) + f2;
            } else if (i == 1 || i == 6 || i == 11) {
                fArr2[i] = (fArr[i] * f) + f3;
            } else {
                fArr2[i] = fArr[i] * f;
            }
        }
        return fArr2;
    }

    public static float[] scaleGradientParamsRadius(float[] fArr, float f) {
        float[] fArr2 = new float[fArr.length];
        if (f <= 0.0f) {
            f = 1.0f;
        }
        for (int i = 0; i < fArr.length; i++) {
            if (i == 2 || i == 7 || i == 12) {
                fArr2[i] = fArr[i] * f;
            } else {
                fArr2[i] = fArr[i];
            }
        }
        return fArr2;
    }

    public static void setClockEffectsContainer(View view, int i, ClockStyleInfo clockStyleInfo, boolean z, boolean z2) {
        if (view == null || clockStyleInfo == null) {
            return;
        }
        int clockEffect = clockStyleInfo.getClockEffect();
        if (isBlurMixType(clockEffect) && DeviceConfig.supportBackgroundBlur(view.getContext())) {
            if (clockStyleInfo.isDisableContainerPassBlur()) {
                return;
            }
            MiuiBlurUtils.setContainerPassBlur(view, i, clockStyleInfo.isMultiWindowBlur());
            return;
        }
        if (isDifferenceType(clockEffect) && DeviceConfig.supportAdvanceVisualEffect(view.getContext())) {
            if (z) {
                return;
            }
            MiuiBlurUtils.setDiffEffectContainer(view, clockStyleInfo.isMultiWindowBlur());
        } else {
            if (isGradualType(clockEffect) && DeviceConfig.supportAdvanceVisualEffect(view.getContext())) {
                return;
            }
            if (isGlowType(clockEffect) && DeviceConfig.supportAdvanceVisualEffect(view.getContext())) {
                MiuiBlurUtils.setGlowEffectContainer(view);
            } else if (isGlassType(clockEffect)) {
                MiuiBlurUtils.setGlassBlurContainer(view, i, clockStyleInfo.isMultiWindowBlur());
            }
        }
    }

    public static void setClockEffectsView(View view, ClockStyleInfo clockStyleInfo, boolean z, int i, int i2, int i3, boolean z2, boolean z3) {
        if (view == null) {
            return;
        }
        if ((view.getContext() == null || !DeviceConfig.isHighTextContrastEnabled(view.getContext())) && clockStyleInfo != null) {
            int clockEffect = clockStyleInfo.getClockEffect();
            if (hasBlurBlendRendering(clockEffect) && DeviceConfig.supportBackgroundBlur(view.getContext())) {
                MiuiBlurUtils.setMemberBlendColors(view, z, i, i2, i3);
                return;
            }
            if (isDifferenceType(clockEffect) && DeviceConfig.supportAdvanceVisualEffect(view.getContext())) {
                if (z2) {
                    return;
                }
                MiuiBlurUtils.setMemberDiffEffectColor(view, i);
            } else if (isGlowType(clockEffect) && DeviceConfig.supportAdvanceVisualEffect(view.getContext())) {
                MiuiBlurUtils.setGlowEffectMethod(view, Effect.GlowEffect.getGlowParam(i), z2, z3);
            } else if (isGradualType(clockEffect)) {
                DeviceConfig.supportAdvanceVisualEffect(view.getContext());
            }
        }
    }

    public static void setClockEffectsView(View view, ClockStyleInfo clockStyleInfo, boolean z, int i, int i2, int i3, boolean z2, boolean z3, int i4) {
        if (view == null) {
            return;
        }
        if ((view.getContext() == null || !DeviceConfig.isHighTextContrastEnabled(view.getContext())) && clockStyleInfo != null) {
            int clockEffect = clockStyleInfo.getClockEffect();
            if (hasBlurBlendRendering(clockEffect) && DeviceConfig.supportBackgroundBlur(view.getContext())) {
                MiuiBlurUtils.setMemberBlendColors(view, z, i, 255, i4, i2, true, i3);
                return;
            }
            if (isDifferenceType(clockEffect) && DeviceConfig.supportAdvanceVisualEffect(view.getContext())) {
                if (z2) {
                    return;
                }
                MiuiBlurUtils.setMemberDiffEffectColor(view, i);
            } else if (isGlowType(clockEffect) && DeviceConfig.supportAdvanceVisualEffect(view.getContext())) {
                MiuiBlurUtils.setGlowEffectMethod(view, Effect.GlowEffect.getGlowParam(i), z2, z3);
            } else if (isGradualType(clockEffect)) {
                DeviceConfig.supportAdvanceVisualEffect(view.getContext());
            }
        }
    }

    public static void setClockEffectsView(View view, ClockStyleInfo clockStyleInfo, boolean z, int i, int i2, boolean z2, boolean z3) {
        setClockEffectsView(view, clockStyleInfo, z, i, i2, 0, z2, z3);
    }

    public static void setClockGradualEffect(float[] fArr, View view, ClockStyleInfo clockStyleInfo, boolean z, boolean z2) {
        setClockGradualEffect(fArr, view, clockStyleInfo, z, z2, 1.0f);
    }

    public static void setClockGradualEffect(float[] fArr, View view, ClockStyleInfo clockStyleInfo, boolean z, boolean z2, float f) {
        if (view == null || clockStyleInfo == null) {
            return;
        }
        if ((view.getContext() == null || !DeviceConfig.isHighTextContrastEnabled(view.getContext())) && isGradualType(clockStyleInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(view.getContext())) {
            if (fArr != null) {
                MiuiBlurUtils.setGradientBlurMethod(view, fArr);
            } else {
                Log.d("ClockEffectUtils", "userAnimInfo is null");
                MiuiBlurUtils.setGradientBlurMethod(view, scaleGradientParams(z ? clockStyleInfo.getAodGradientParams() : clockStyleInfo.getLockGradientParams(), f));
            }
        }
    }
}
