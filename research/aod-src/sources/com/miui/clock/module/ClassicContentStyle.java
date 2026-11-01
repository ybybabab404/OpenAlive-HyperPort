package com.miui.clock.module;

import com.xiaomi.onetrack.g.d;
import java.util.Locale;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes.dex */
public class ClassicContentStyle {
    public static final int[][] LOCALE_FALLBACK_MAPPING = {new int[]{101, 100}, new int[]{103, 100}, new int[]{209, 200}, new int[]{210, 202}, new int[]{211, 200}, new int[]{212, 207}};

    public static class FontStyle {
    }

    public static class Health {
        public static boolean isHealthType(int i) {
            return i >= 500 && i <= 508;
        }
    }

    public static class LargeDate {
        public static boolean isLargeDateType(int i) {
            return i >= 200 && i <= 212;
        }

        public static boolean needSwitchStyle(int i) {
            switch (i) {
                case EaseManager.EaseStyleDef.PERLIN /* 201 */:
                case 203:
                case 204:
                case 206:
                case 207:
                case 208:
                    return true;
                case 202:
                case 205:
                default:
                    return false;
            }
        }
    }

    public static class SmallDate {
    }

    public static class Time {
        public static boolean isHalfNumber(int i) {
            return i == 302 || i == 303;
        }

        public static boolean isTimeType(int i) {
            return i >= 300 && i <= 305;
        }
    }

    public static class TimeTypesetting {
    }

    public static class Weather {
        public static boolean isWeatherType(int i) {
            return i >= 400 && i <= 409;
        }
    }

    public static int getActualRenderStyle(int i) {
        if (!Locale.CHINESE.getLanguage().equals(Locale.getDefault().getLanguage())) {
            for (int[] iArr : LOCALE_FALLBACK_MAPPING) {
                if (iArr[0] == i) {
                    return iArr[1];
                }
            }
        }
        return i;
    }

    public static boolean isNeedPointIcon(int i) {
        return Weather.isWeatherType(i) || Health.isHealthType(i);
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0035 A[FALL_THROUGH, RETURN] */
    public static boolean needOpenTextSameWidth(int i, boolean z) {
        if (Time.isHalfNumber(i)) {
            return true;
        }
        if (!z) {
            return false;
        }
        if (i != 300 && i != 409 && i != 500 && i != 502 && i != 504 && i != 508 && i != 405 && i != 406) {
            switch (i) {
                default:
                    switch (i) {
                        default:
                            switch (i) {
                                case 400:
                                case d.b /* 401 */:
                                case 402:
                                    break;
                                default:
                                    return false;
                            }
                        case 208:
                        case 209:
                        case 210:
                            return true;
                    }
                case 200:
                case EaseManager.EaseStyleDef.PERLIN /* 201 */:
                case 202:
                case 203:
                case 204:
                case 205:
                    return true;
            }
        }
        return true;
    }
}
