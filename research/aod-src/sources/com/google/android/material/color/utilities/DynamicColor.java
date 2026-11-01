package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;
import com.miui.aod.components.view.BaseStyleSelectView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public final class DynamicColor {
    public final Function background;
    public final ContrastCurve contrastCurve;
    public final HashMap hctCache;
    public final boolean isBackground;
    public final String name;
    public final Function opacity;
    public final Function palette;
    public final Function secondBackground;
    public final Function tone;
    public final Function toneDeltaPair;

    public DynamicColor(String str, Function function, Function function2, boolean z, Function function3, Function function4, ContrastCurve contrastCurve, Function function5) {
        this.hctCache = new HashMap();
        this.name = str;
        this.palette = function;
        this.tone = function2;
        this.isBackground = z;
        this.background = function3;
        this.secondBackground = function4;
        this.contrastCurve = contrastCurve;
        this.toneDeltaPair = function5;
        this.opacity = null;
    }

    public DynamicColor(String str, Function function, Function function2, boolean z, Function function3, Function function4, ContrastCurve contrastCurve, Function function5, Function function6) {
        this.hctCache = new HashMap();
        this.name = str;
        this.palette = function;
        this.tone = function2;
        this.isBackground = z;
        this.background = function3;
        this.secondBackground = function4;
        this.contrastCurve = contrastCurve;
        this.toneDeltaPair = function5;
        this.opacity = function6;
    }

    public static double enableLightForeground(double d) {
        if (!tonePrefersLightForeground(d) || toneAllowsLightForeground(d)) {
            return d;
        }
        return 49.0d;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0044 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:24:0x0045 A[RETURN] */
    public static double foregroundTone(double d, double d2) {
        double dLighterUnsafe = Contrast.lighterUnsafe(d, d2);
        double dDarkerUnsafe = Contrast.darkerUnsafe(d, d2);
        double dRatioOfTones = Contrast.ratioOfTones(dLighterUnsafe, d);
        double dRatioOfTones2 = Contrast.ratioOfTones(dDarkerUnsafe, d);
        if (!tonePrefersLightForeground(d)) {
            if (dRatioOfTones2 >= d2 || dRatioOfTones2 >= dRatioOfTones) {
                return dDarkerUnsafe;
            }
            return dLighterUnsafe;
        }
        boolean z = Math.abs(dRatioOfTones - dRatioOfTones2) < 0.1d && dRatioOfTones < d2 && dRatioOfTones2 < d2;
        if (dRatioOfTones >= d2 || dRatioOfTones >= dRatioOfTones2 || z) {
            return dLighterUnsafe;
        }
        return dDarkerUnsafe;
    }

    public static DynamicColor fromPalette(String str, Function function, Function function2) {
        return new DynamicColor(str, function, function2, false, null, null, null, null);
    }

    public static boolean toneAllowsLightForeground(double d) {
        return Math.round(d) <= 49;
    }

    public static boolean tonePrefersLightForeground(double d) {
        return Math.round(d) < 60;
    }

    public int getArgb(DynamicScheme dynamicScheme) {
        int i = getHct(dynamicScheme).toInt();
        Function function = this.opacity;
        if (function == null) {
            return i;
        }
        return (MathUtils.clampInt(0, 255, (int) Math.round(((Double) function.apply(dynamicScheme)).doubleValue() * 255.0d)) << 24) | (16777215 & i);
    }

    public Hct getHct(DynamicScheme dynamicScheme) {
        Hct hct = (Hct) this.hctCache.get(dynamicScheme);
        if (hct != null) {
            return hct;
        }
        Hct hct2 = ((TonalPalette) this.palette.apply(dynamicScheme)).getHct(getTone(dynamicScheme));
        if (this.hctCache.size() > 4) {
            this.hctCache.clear();
        }
        this.hctCache.put(dynamicScheme, hct2);
        return hct2;
    }

    public double getTone(DynamicScheme dynamicScheme) {
        double d;
        double dMax;
        double dMin;
        boolean z = false;
        boolean z2 = dynamicScheme.contrastLevel < BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
        Function function = this.toneDeltaPair;
        if (function == null) {
            double dDoubleValue = ((Double) this.tone.apply(dynamicScheme)).doubleValue();
            Function function2 = this.background;
            if (function2 == null) {
                return dDoubleValue;
            }
            double tone = ((DynamicColor) function2.apply(dynamicScheme)).getTone(dynamicScheme);
            double contrast = this.contrastCurve.getContrast(dynamicScheme.contrastLevel);
            if (Contrast.ratioOfTones(tone, dDoubleValue) < contrast) {
                dDoubleValue = foregroundTone(tone, contrast);
            }
            if (z2) {
                dDoubleValue = foregroundTone(tone, contrast);
            }
            if (!this.isBackground || 50.0d > dDoubleValue || dDoubleValue >= 60.0d) {
                d = dDoubleValue;
            } else {
                d = 49.0d;
                if (Contrast.ratioOfTones(49.0d, tone) < contrast) {
                    d = 60.0d;
                }
            }
            if (this.secondBackground != null) {
                double tone2 = ((DynamicColor) this.background.apply(dynamicScheme)).getTone(dynamicScheme);
                double tone3 = ((DynamicColor) this.secondBackground.apply(dynamicScheme)).getTone(dynamicScheme);
                double dMax2 = Math.max(tone2, tone3);
                double dMin2 = Math.min(tone2, tone3);
                if (Contrast.ratioOfTones(dMax2, d) < contrast || Contrast.ratioOfTones(dMin2, d) < contrast) {
                    double dLighter = Contrast.lighter(dMax2, contrast);
                    double dDarker = Contrast.darker(dMin2, contrast);
                    ArrayList arrayList = new ArrayList();
                    if (dLighter != -1.0d) {
                        arrayList.add(Double.valueOf(dLighter));
                    }
                    if (dDarker != -1.0d) {
                        arrayList.add(Double.valueOf(dDarker));
                    }
                    if (tonePrefersLightForeground(tone2) || tonePrefersLightForeground(tone3)) {
                        if (dLighter == -1.0d) {
                            return 100.0d;
                        }
                        return dLighter;
                    }
                    if (arrayList.size() == 1) {
                        return ((Double) arrayList.get(0)).doubleValue();
                    }
                    return dDarker == -1.0d ? BaseStyleSelectView.COLLAPSE_SLIDE_VALUE : dDarker;
                }
            }
            return d;
        }
        ToneDeltaPair toneDeltaPair = (ToneDeltaPair) function.apply(dynamicScheme);
        DynamicColor roleA = toneDeltaPair.getRoleA();
        DynamicColor roleB = toneDeltaPair.getRoleB();
        double delta = toneDeltaPair.getDelta();
        TonePolarity polarity = toneDeltaPair.getPolarity();
        boolean stayTogether = toneDeltaPair.getStayTogether();
        double tone4 = ((DynamicColor) this.background.apply(dynamicScheme)).getTone(dynamicScheme);
        if (polarity == TonePolarity.NEARER || ((polarity == TonePolarity.LIGHTER && !dynamicScheme.isDark) || (polarity == TonePolarity.DARKER && dynamicScheme.isDark))) {
            z = true;
        }
        DynamicColor dynamicColor = z ? roleA : roleB;
        DynamicColor dynamicColor2 = z ? roleB : roleA;
        boolean zEquals = this.name.equals(dynamicColor.name);
        double d2 = dynamicScheme.isDark ? 1.0d : -1.0d;
        double contrast2 = dynamicColor.contrastCurve.getContrast(dynamicScheme.contrastLevel);
        double contrast3 = dynamicColor2.contrastCurve.getContrast(dynamicScheme.contrastLevel);
        double dDoubleValue2 = ((Double) dynamicColor.tone.apply(dynamicScheme)).doubleValue();
        if (Contrast.ratioOfTones(tone4, dDoubleValue2) < contrast2) {
            dDoubleValue2 = foregroundTone(tone4, contrast2);
        }
        double dDoubleValue3 = ((Double) dynamicColor2.tone.apply(dynamicScheme)).doubleValue();
        if (Contrast.ratioOfTones(tone4, dDoubleValue3) < contrast3) {
            dDoubleValue3 = foregroundTone(tone4, contrast3);
        }
        if (z2) {
            dDoubleValue2 = foregroundTone(tone4, contrast2);
            dDoubleValue3 = foregroundTone(tone4, contrast3);
        }
        if ((dDoubleValue3 - dDoubleValue2) * d2 < delta) {
            double d3 = delta * d2;
            dDoubleValue3 = MathUtils.clampDouble(BaseStyleSelectView.COLLAPSE_SLIDE_VALUE, 100.0d, dDoubleValue2 + d3);
            if ((dDoubleValue3 - dDoubleValue2) * d2 < delta) {
                dDoubleValue2 = MathUtils.clampDouble(BaseStyleSelectView.COLLAPSE_SLIDE_VALUE, 100.0d, dDoubleValue3 - d3);
            }
        }
        if (50.0d > dDoubleValue2 || dDoubleValue2 >= 60.0d) {
            if (50.0d > dDoubleValue3 || dDoubleValue3 >= 60.0d) {
                dMax = dDoubleValue3;
            } else if (!stayTogether) {
                dMax = d2 > BaseStyleSelectView.COLLAPSE_SLIDE_VALUE ? 60.0d : 49.0d;
            } else if (d2 > BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
                dMax = Math.max(dDoubleValue3, (delta * d2) + 60.0d);
                dDoubleValue2 = 60.0d;
            } else {
                dMin = Math.min(dDoubleValue3, (delta * d2) + 49.0d);
                dMax = dMin;
                dDoubleValue2 = 49.0d;
            }
        } else if (d2 > BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
            dMax = Math.max(dDoubleValue3, (delta * d2) + 60.0d);
            dDoubleValue2 = 60.0d;
        } else {
            dMin = Math.min(dDoubleValue3, (delta * d2) + 49.0d);
            dMax = dMin;
            dDoubleValue2 = 49.0d;
        }
        return zEquals ? dDoubleValue2 : dMax;
    }
}
