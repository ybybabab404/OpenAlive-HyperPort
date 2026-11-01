package com.miui.clock.module;

import android.graphics.Color;
import com.miui.clock.utils.DeviceConfig;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class Effect {
    public static float[] EMPTY_GRADIENT = new float[15];
    public static int LAND_GRADIENT = 1;
    public static int NORMAL_GRADIENT;

    public static class Glow {
        public int glowBlendMode;
        public float glowBlur;
        public String glowColor;
        public float glowColorAlpha;
        public float spread;
        public int srcColor;
        public int viewBlendMode;
        public float viewBlur;
        public String viewColor;
        public float viewColorAlpha;

        public Glow(int i, float f, String str, float f2, String str2, float f3, float f4, float f5, int i2, int i3) {
            this.srcColor = i;
            this.viewColorAlpha = f;
            this.viewColor = str;
            this.glowColorAlpha = f2;
            this.glowColor = str2;
            this.viewBlur = f3;
            this.glowBlur = f4;
            this.spread = f5;
            this.viewBlendMode = i2;
            this.glowBlendMode = i3;
        }
    }

    public static class GlowEffect {
        public static Map mPhoneClockGlowMap = new HashMap();
        public static Map mPadClockGlowMap = new HashMap();

        static {
            mPhoneClockGlowMap.put(Integer.valueOf(Color.parseColor("#FFFFFF")), new Glow(Color.parseColor("#FFFFFF"), 1.0f, "#FFFFFF", 0.8f, "#FFFFFF", 20.0f, 60.0f, 1.0f, 3, 3));
            mPhoneClockGlowMap.put(Integer.valueOf(Color.parseColor("#BABABA")), new Glow(Color.parseColor("#FFFFFF"), 1.0f, "#C7C7C7", 0.8f, "#5266FF", 20.0f, 60.0f, 1.0f, 3, 3));
            mPhoneClockGlowMap.put(Integer.valueOf(Color.parseColor("#F6C0C0")), new Glow(Color.parseColor("#FFF9F5"), 1.0f, "#FF1900", 0.8f, "#FF1A1A", 20.0f, 60.0f, 1.0f, 3, 3));
            mPhoneClockGlowMap.put(Integer.valueOf(Color.parseColor("#F9CCB8")), new Glow(Color.parseColor("#FFFDFA"), 1.0f, "#FF6D2E", 0.8f, "#FF1900", 20.0f, 60.0f, 1.0f, 3, 3));
            mPhoneClockGlowMap.put(Integer.valueOf(Color.parseColor("#FFF4BD")), new Glow(Color.parseColor("#FDFEF7"), 1.0f, "#FF7A00", 0.8f, "#FF2E00", 20.0f, 60.0f, 1.0f, 3, 3));
            mPhoneClockGlowMap.put(Integer.valueOf(Color.parseColor("#E0FDBB")), new Glow(Color.parseColor("#F5FFF7"), 1.0f, "#5CFFB8", 0.8f, "#33FF55", 20.0f, 60.0f, 1.0f, 3, 3));
            mPhoneClockGlowMap.put(Integer.valueOf(Color.parseColor("#B7F7BD")), new Glow(Color.parseColor("#FEFFFA"), 1.0f, "#4DFFA3", 0.8f, "#0586FF", 20.0f, 60.0f, 1.0f, 3, 3));
            mPhoneClockGlowMap.put(Integer.valueOf(Color.parseColor("#BDF4FF")), new Glow(Color.parseColor("#F5FFFE"), 1.0f, "#5CECFF", 0.8f, "#3341FF", 20.0f, 60.0f, 1.0f, 3, 3));
            mPhoneClockGlowMap.put(Integer.valueOf(Color.parseColor("#B6B9F9")), new Glow(Color.parseColor("#FEFFFA"), 1.0f, "#D375FF", 0.8f, "#391FFF", 20.0f, 60.0f, 1.0f, 3, 3));
            mPhoneClockGlowMap.put(Integer.valueOf(Color.parseColor("#FFBADB")), new Glow(Color.parseColor("#FFFAFB"), 1.0f, "#FF6161", 0.8f, "#FF0066", 20.0f, 60.0f, 1.0f, 3, 3));
            mPhoneClockGlowMap.put(Integer.valueOf(Color.parseColor("#CEBDFF")), new Glow(Color.parseColor("#FFFEFA"), 1.0f, "#F95858", 0.8f, "#FF4D70", 20.0f, 60.0f, 1.0f, 3, 3));
            mPhoneClockGlowMap.put(Integer.valueOf(Color.parseColor("#DBADFF")), new Glow(Color.parseColor("#FFFFFF"), 1.0f, "#6F52FF", 0.8f, "#FF0A2F", 20.0f, 60.0f, 1.0f, 3, 3));
            mPadClockGlowMap.put(Integer.valueOf(Color.parseColor("#FFFFFF")), new Glow(Color.parseColor("#FFFFFF"), 0.8f, "#FFFFFF", 0.7f, "#FFFFFF", 30.0f, 80.0f, 1.0f, 3, 3));
            mPadClockGlowMap.put(Integer.valueOf(Color.parseColor("#BABABA")), new Glow(Color.parseColor("#FFFFFF"), 0.8f, "#C7C7C7", 0.7f, "#5266FF", 30.0f, 80.0f, 1.0f, 3, 3));
            mPadClockGlowMap.put(Integer.valueOf(Color.parseColor("#F6C0C0")), new Glow(Color.parseColor("#FFF9F5"), 0.8f, "#FF1900", 0.7f, "#FF1A1A", 30.0f, 80.0f, 1.0f, 3, 3));
            mPadClockGlowMap.put(Integer.valueOf(Color.parseColor("#F9CCB8")), new Glow(Color.parseColor("#FFFDFA"), 0.8f, "#FF6D2E", 0.7f, "#FF1900", 30.0f, 80.0f, 1.0f, 3, 3));
            mPadClockGlowMap.put(Integer.valueOf(Color.parseColor("#FFF4BD")), new Glow(Color.parseColor("#FDFEF7"), 0.8f, "#FF7A00", 0.7f, "#FF2E00", 30.0f, 80.0f, 1.0f, 3, 3));
            mPadClockGlowMap.put(Integer.valueOf(Color.parseColor("#E0FDBB")), new Glow(Color.parseColor("#F5FFF7"), 0.8f, "#5CFFB8", 0.7f, "#33FF55", 30.0f, 80.0f, 1.0f, 3, 3));
            mPadClockGlowMap.put(Integer.valueOf(Color.parseColor("#B7F7BD")), new Glow(Color.parseColor("#FEFFFA"), 0.8f, "#4DFFA3", 0.7f, "#0586FF", 30.0f, 80.0f, 1.0f, 3, 3));
            mPadClockGlowMap.put(Integer.valueOf(Color.parseColor("#BDF4FF")), new Glow(Color.parseColor("#F5FFFE"), 0.8f, "#5CECFF", 0.7f, "#3341FF", 30.0f, 80.0f, 1.0f, 3, 3));
            mPadClockGlowMap.put(Integer.valueOf(Color.parseColor("#B6B9F9")), new Glow(Color.parseColor("#FEFFFA"), 0.8f, "#D375FF", 0.7f, "#391FFF", 30.0f, 80.0f, 1.0f, 3, 3));
            mPadClockGlowMap.put(Integer.valueOf(Color.parseColor("#FFBADB")), new Glow(Color.parseColor("#FFFAFB"), 0.8f, "#FF6161", 0.7f, "#FF0066", 30.0f, 80.0f, 1.0f, 3, 3));
            mPadClockGlowMap.put(Integer.valueOf(Color.parseColor("#CEBDFF")), new Glow(Color.parseColor("#FFFEFA"), 0.8f, "#F95858", 0.7f, "#FF4D70", 30.0f, 80.0f, 1.0f, 3, 3));
            mPadClockGlowMap.put(Integer.valueOf(Color.parseColor("#DBADFF")), new Glow(Color.parseColor("#FFFFFF"), 0.8f, "#6F52FF", 0.7f, "#FF0A2F", 30.0f, 80.0f, 1.0f, 3, 3));
        }

        public static Glow getGlowParam(int i) {
            Glow glow = DeviceConfig.PAD_DEVICE ? (Glow) mPadClockGlowMap.get(Integer.valueOf(i)) : (Glow) mPhoneClockGlowMap.get(Integer.valueOf(i));
            return glow == null ? new Glow(Color.parseColor("#FFFFFF"), 1.0f, "#FFFFFF", 0.8f, "#FFFFFF", 20.0f, 20.0f, 1.0f, 3, 3) : glow;
        }
    }

    public static class MagazineCStyle1Effect {
        public static final float[] Gradient_NORMAL_PARAMS_0 = {-100.0f, 0.0f, 50.0f, 600.0f, 1.6f, 0.0f, 1200.0f, 50.0f, 850.0f, 1.6f, 600.0f, 80.0f, 30.0f, 500.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_1 = {-200.0f, 400.0f, 30.0f, 600.0f, 1.6f, 500.0f, 1200.0f, 60.0f, 900.0f, 1.6f, 500.0f, -210.0f, 40.0f, 600.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_2 = {-100.0f, 200.0f, 50.0f, 600.0f, 1.6f, 200.0f, 1300.0f, 30.0f, 900.0f, 1.6f, 600.0f, 300.0f, 40.0f, 600.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_3 = {-200.0f, 400.0f, 40.0f, 600.0f, 1.6f, 500.0f, 1200.0f, 30.0f, 900.0f, 1.6f, 500.0f, -200.0f, 60.0f, 600.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_0 = {-100.0f, 0.0f, 0.0f, 600.0f, 1.6f, 0.0f, 1200.0f, 0.0f, 850.0f, 1.6f, 600.0f, 80.0f, 0.0f, 500.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_1 = {-200.0f, 400.0f, 0.0f, 600.0f, 1.6f, 500.0f, 1200.0f, 0.0f, 900.0f, 1.6f, 500.0f, -210.0f, 0.0f, 600.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_2 = {-100.0f, 200.0f, 0.0f, 600.0f, 1.6f, 200.0f, 1300.0f, 0.0f, 900.0f, 1.6f, 600.0f, 300.0f, 0.0f, 600.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_3 = {-200.0f, 400.0f, 0.0f, 600.0f, 1.6f, 500.0f, 1200.0f, 0.0f, 900.0f, 1.6f, 500.0f, -200.0f, 0.0f, 600.0f, 1.6f};
    }

    public static class MagazineCStyle2Effect {
        public static final float[] Gradient_NORMAL_PARAMS_0 = {0.0f, 0.0f, 60.0f, 650.0f, 1.6f, 300.0f, 800.0f, 40.0f, 700.0f, 1.6f, 1000.0f, 0.0f, 30.0f, 600.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_1 = {0.0f, 200.0f, 20.0f, 600.0f, 1.6f, 500.0f, 900.0f, 60.0f, 600.0f, 1.6f, 800.0f, 100.0f, 50.0f, 700.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_2 = {-100.0f, 400.0f, 40.0f, 700.0f, 1.6f, 900.0f, 900.0f, 60.0f, 800.0f, 1.6f, 900.0f, -100.0f, 20.0f, 600.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_3 = {0.0f, 200.0f, 60.0f, 600.0f, 1.6f, 500.0f, 900.0f, 30.0f, 500.0f, 1.6f, 800.0f, 100.0f, 40.0f, 400.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_0 = {0.0f, 0.0f, 0.0f, 650.0f, 1.6f, 300.0f, 800.0f, 0.0f, 700.0f, 1.6f, 1000.0f, 0.0f, 0.0f, 600.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_1 = {0.0f, 200.0f, 0.0f, 600.0f, 1.6f, 500.0f, 900.0f, 0.0f, 600.0f, 1.6f, 800.0f, 100.0f, 0.0f, 700.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_2 = {-100.0f, 400.0f, 0.0f, 700.0f, 1.6f, 900.0f, 900.0f, 0.0f, 800.0f, 1.6f, 900.0f, -100.0f, 0.0f, 600.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_3 = {0.0f, 200.0f, 0.0f, 600.0f, 1.6f, 500.0f, 900.0f, 0.0f, 500.0f, 1.6f, 800.0f, 100.0f, 0.0f, 400.0f, 1.6f};
    }

    public static class OversizeAHourEffect {
        public static final float[] Gradient_NORMAL_PARAMS_0 = {-154.0f, -178.0f, 120.0f, 1200.0f, 1.6f, -154.0f, 1822.0f, 60.0f, 800.0f, 1.6f, 1046.0f, 822.0f, 60.0f, 800.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_1 = {-154.0f, 1022.0f, 60.0f, 600.0f, 1.6f, 846.0f, 1822.0f, 60.0f, 800.0f, 1.6f, 1046.0f, 22.0f, 120.0f, 1000.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_2 = {-154.0f, 1822.0f, 80.0f, 1000.0f, 1.6f, 1046.0f, 1022.0f, 50.0f, 800.0f, 1.6f, 446.0f, -78.0f, 60.0f, 800.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_3 = {-54.0f, 822.0f, 60.0f, 600.0f, 1.6f, 446.0f, 2022.0f, 80.0f, 800.0f, 1.6f, 1246.0f, 22.0f, 60.0f, 1000.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_0 = {-154.0f, -178.0f, 0.0f, 1200.0f, 1.6f, -154.0f, 1822.0f, 0.0f, 800.0f, 1.6f, 1046.0f, 822.0f, 0.0f, 800.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_1 = {-154.0f, 1022.0f, 0.0f, 600.0f, 1.6f, 846.0f, 1822.0f, 0.0f, 800.0f, 1.6f, 1046.0f, 22.0f, 0.0f, 1000.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_2 = {-154.0f, 1822.0f, 0.0f, 1000.0f, 1.6f, 1046.0f, 1022.0f, 0.0f, 800.0f, 1.6f, 446.0f, -78.0f, 0.0f, 800.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_3 = {-54.0f, 822.0f, 0.0f, 600.0f, 1.6f, 446.0f, 2022.0f, 0.0f, 800.0f, 1.6f, 1246.0f, 22.0f, 0.0f, 1000.0f, 1.6f};
    }

    public static class OversizeAMinuteEffect {
        public static final float[] Gradient_NORMAL_PARAMS_0 = {-154.0f, -1214.0f, 120.0f, 1200.0f, 1.6f, -154.0f, 786.0f, 60.0f, 800.0f, 1.6f, 1046.0f, -214.0f, 60.0f, 800.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_1 = {-154.0f, -14.0f, 60.0f, 600.0f, 1.6f, 846.0f, 786.0f, 60.0f, 800.0f, 1.6f, 1046.0f, -1014.0f, 120.0f, 1000.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_2 = {-154.0f, 786.0f, 80.0f, 1000.0f, 1.6f, 1046.0f, -14.0f, 50.0f, 800.0f, 1.6f, 446.0f, -1114.0f, 60.0f, 800.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_3 = {-54.0f, -214.0f, 60.0f, 600.0f, 1.6f, 446.0f, 986.0f, 80.0f, 800.0f, 1.6f, 1246.0f, -1014.0f, 60.0f, 1000.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_0 = {-154.0f, -1214.0f, 0.0f, 1200.0f, 1.6f, -154.0f, 786.0f, 0.0f, 800.0f, 1.6f, 1046.0f, -214.0f, 0.0f, 800.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_1 = {-154.0f, -14.0f, 0.0f, 600.0f, 1.6f, 846.0f, 786.0f, 0.0f, 800.0f, 1.6f, 1046.0f, -1014.0f, 0.0f, 1000.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_2 = {-154.0f, 786.0f, 0.0f, 1000.0f, 1.6f, 1046.0f, -14.0f, 0.0f, 800.0f, 1.6f, 446.0f, -1114.0f, 0.0f, 800.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_3 = {-54.0f, -214.0f, 0.0f, 600.0f, 1.6f, 446.0f, 986.0f, 0.0f, 800.0f, 1.6f, 1246.0f, -1014.0f, 0.0f, 1000.0f, 1.6f};
    }

    public static class OversizeBHourEffect {
        public static final float[] Gradient_NORMAL_PARAMS_0 = {-189.0f, -137.0f, 100.0f, 1400.0f, 1.6f, 211.0f, 1363.0f, 40.0f, 800.0f, 1.6f, 1011.0f, 463.0f, 30.0f, 800.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_1 = {111.0f, -137.0f, 40.0f, 1100.0f, 1.6f, 411.0f, 1463.0f, 80.0f, 1000.0f, 1.6f, 1211.0f, 663.0f, 40.0f, 600.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_2 = {11.0f, 363.0f, 40.0f, 900.0f, 1.6f, 1211.0f, 1263.0f, 40.0f, 1200.0f, 1.6f, 1211.0f, -137.0f, 100.0f, 1000.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_3 = {111.0f, -137.0f, 60.0f, 1100.0f, 1.6f, 411.0f, 1463.0f, 40.0f, 1000.0f, 1.6f, 1311.0f, 363.0f, 100.0f, 900.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_0 = {-189.0f, -137.0f, 0.0f, 1400.0f, 1.6f, 211.0f, 1363.0f, 0.0f, 800.0f, 1.6f, 1011.0f, 463.0f, 0.0f, 800.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_1 = {111.0f, -137.0f, 0.0f, 1100.0f, 1.6f, 411.0f, 1463.0f, 0.0f, 1000.0f, 1.6f, 1211.0f, 663.0f, 0.0f, 600.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_2 = {11.0f, 363.0f, 0.0f, 900.0f, 1.6f, 1211.0f, 1263.0f, 0.0f, 1200.0f, 1.6f, 1211.0f, -137.0f, 0.0f, 1000.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_3 = {111.0f, -137.0f, 0.0f, 1100.0f, 1.6f, 411.0f, 1463.0f, 0.0f, 1000.0f, 1.6f, 1311.0f, 363.0f, 0.0f, 900.0f, 1.6f};
    }

    public static class OversizeBMinuteEffect {
        public static final float[] Gradient_NORMAL_PARAMS_0 = {-679.0f, -137.0f, 100.0f, 1400.0f, 1.6f, -279.0f, 1363.0f, 40.0f, 800.0f, 1.6f, 521.0f, 463.0f, 30.0f, 800.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_1 = {-379.0f, -137.0f, 40.0f, 1100.0f, 1.6f, -79.0f, 1463.0f, 80.0f, 1000.0f, 1.6f, 721.0f, 663.0f, 40.0f, 600.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_2 = {-479.0f, 363.0f, 40.0f, 900.0f, 1.6f, 721.0f, 1263.0f, 40.0f, 1200.0f, 1.6f, 721.0f, -137.0f, 100.0f, 1000.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_3 = {-379.0f, -137.0f, 60.0f, 1100.0f, 1.6f, -79.0f, 1463.0f, 40.0f, 1000.0f, 1.6f, 821.0f, 363.0f, 100.0f, 900.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_0 = {-679.0f, -137.0f, 0.0f, 1400.0f, 1.6f, -279.0f, 1363.0f, 0.0f, 800.0f, 1.6f, 521.0f, 463.0f, 0.0f, 800.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_1 = {-379.0f, -137.0f, 0.0f, 1100.0f, 1.6f, -79.0f, 1463.0f, 0.0f, 1000.0f, 1.6f, 721.0f, 663.0f, 0.0f, 600.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_2 = {-479.0f, 363.0f, 0.0f, 900.0f, 1.6f, 721.0f, 1263.0f, 0.0f, 1200.0f, 1.6f, 721.0f, -137.0f, 0.0f, 1000.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_3 = {-379.0f, -137.0f, 0.0f, 1100.0f, 1.6f, -79.0f, 1463.0f, 0.0f, 1000.0f, 1.6f, 821.0f, 363.0f, 0.0f, 900.0f, 1.6f};
    }

    public static class PadExclusiveAEffect {
        public static final float[] Gradient_NORMAL_PARAMS_0 = {200.0f, 0.0f, 30.0f, 600.0f, 1.6f, 1000.0f, -400.0f, 60.0f, 800.0f, 1.6f, 2000.0f, 800.0f, 40.0f, 1000.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_1 = {0.0f, 600.0f, 25.0f, 1000.0f, 1.6f, 800.0f, -400.0f, 30.0f, 800.0f, 1.6f, 2200.0f, -200.0f, 40.0f, 1000.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_2 = {600.0f, 1000.0f, 30.0f, 1000.0f, 1.6f, 800.0f, -500.0f, 60.0f, 1000.0f, 1.6f, 2200.0f, 800.0f, 50.0f, 800.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_3 = {-300.0f, 800.0f, 60.0f, 1000.0f, 1.6f, 1000.0f, 800.0f, 20.0f, 500.0f, 1.6f, 2400.0f, -200.0f, 60.0f, 1400.0f, 1.6f};
    }

    public static class PadExclusiveALandEffect {
        public static final float[] Gradient_NORMAL_PARAMS_0 = {300.0f, 100.0f, 30.0f, 1000.0f, 1.6f, 1600.0f, -300.0f, 40.0f, 1000.0f, 1.6f, 3000.0f, 700.0f, 40.0f, 800.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_1 = {0.0f, 800.0f, 40.0f, 1000.0f, 1.6f, 1600.0f, 0.0f, 50.0f, 1000.0f, 1.6f, 3000.0f, 1300.0f, 60.0f, 1000.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_2 = {-200.0f, 400.0f, 40.0f, 1000.0f, 1.6f, 1400.0f, 1200.0f, 50.0f, 1000.0f, 1.6f, 2600.0f, 0.0f, 60.0f, 1000.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_3 = {-500.0f, 500.0f, 60.0f, 1200.0f, 1.6f, 1800.0f, 1200.0f, 20.0f, 1000.0f, 1.6f, 2400.0f, -300.0f, 60.0f, 1400.0f, 1.6f};
    }

    public static class PadExclusiveBEffect {
        public static final float[] Gradient_NORMAL_PARAMS_0 = {-400.0f, 1100.0f, 70.0f, 600.0f, 1.6f, 1100.0f, 1400.0f, 120.0f, 700.0f, 1.6f, 300.0f, 2350.0f, 300.0f, 800.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_1 = {-400.0f, 1500.0f, 100.0f, 600.0f, 1.6f, 1100.0f, 800.0f, 120.0f, 700.0f, 1.6f, 600.0f, 2300.0f, 300.0f, 800.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_2 = {-400.0f, 1600.0f, 100.0f, 600.0f, 1.6f, 300.0f, 500.0f, 100.0f, 700.0f, 1.6f, 900.0f, 1500.0f, 100.0f, 500.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_3 = {30.0f, 2100.0f, 200.0f, 600.0f, 1.6f, 20.0f, 400.0f, 300.0f, 700.0f, 1.6f, 900.0f, 1400.0f, 100.0f, 500.0f, 1.6f};
    }

    public static class PadExclusiveBLandEffect {
        public static final float[] Gradient_NORMAL_PARAMS_0 = {-600.0f, 800.0f, 200.0f, 800.0f, 1.6f, 1200.0f, 1000.0f, 350.0f, 600.0f, 1.6f, 400.0f, 1700.0f, 120.0f, 550.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_1 = {800.0f, -150.0f, 400.0f, 800.0f, 1.6f, 600.0f, 1800.0f, 300.0f, 600.0f, 1.6f, -600.0f, 1100.0f, 300.0f, 800.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_2 = {400.0f, -200.0f, 800.0f, 800.0f, 1.6f, 1100.0f, 1100.0f, 200.0f, 600.0f, 1.6f, -500.0f, 1000.0f, 200.0f, 700.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_3 = {400.0f, -350.0f, 400.0f, 1000.0f, 1.6f, 1100.0f, 1100.0f, 100.0f, 600.0f, 1.6f, -600.0f, 1100.0f, 300.0f, 800.0f, 1.6f};
    }

    public static class PadExclusiveCHourEffect {
        public static final float[] Gradient_NORMAL_PARAMS_0 = {0.0f, 0.0f, 40.0f, 400.0f, 1.6f, 1160.0f, 33.0f, 46.0f, 462.0f, 1.6f, 666.0f, 666.0f, 26.0f, 396.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_1 = {600.0f, 0.0f, 40.0f, 400.0f, 1.6f, 1162.0f, 666.0f, 46.0f, 462.0f, 1.6f, 132.0f, 666.0f, 26.0f, 396.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_2 = {990.0f, 0.0f, 33.0f, 400.0f, 1.6f, 832.0f, 726.0f, 40.0f, 400.0f, 1.6f, 66.0f, 400.0f, 26.0f, 400.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_3 = {600.0f, 0.0f, 40.0f, 528.0f, 1.6f, 1162.0f, 660.0f, 26.0f, 462.0f, 1.6f, 132.0f, 660.0f, 33.0f, 400.0f, 1.6f};
    }

    public static class PadExclusiveCLandHourEffect {
        public static final float[] Gradient_NORMAL_PARAMS_0 = {0.0f, 0.0f, 60.0f, 600.0f, 1.6f, 1760.0f, 50.0f, 70.0f, 700.0f, 1.6f, 1000.0f, 1000.0f, 40.0f, 600.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_1 = {900.0f, 0.0f, 60.0f, 600.0f, 1.6f, 1760.0f, 1000.0f, 70.0f, 700.0f, 1.6f, 200.0f, 1000.0f, 40.0f, 600.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_2 = {1500.0f, 0.0f, 50.0f, 600.0f, 1.6f, 1260.0f, 1100.0f, 60.0f, 600.0f, 1.6f, 100.0f, 600.0f, 40.0f, 700.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_3 = {900.0f, 0.0f, 60.0f, 800.0f, 1.6f, 1760.0f, 1000.0f, 40.0f, 700.0f, 1.6f, 200.0f, 1000.0f, 50.0f, 600.0f, 1.6f};
    }

    public static class PadExclusiveCLandMinuteEffect {
        public static final float[] Gradient_NORMAL_PARAMS_0 = {-1060.0f, 0.0f, 60.0f, 600.0f, 1.6f, 700.0f, 50.0f, 70.0f, 700.0f, 1.6f, -60.0f, 1000.0f, 40.0f, 600.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_1 = {-160.0f, 0.0f, 60.0f, 600.0f, 1.6f, 700.0f, 1000.0f, 70.0f, 700.0f, 1.6f, -860.0f, 1000.0f, 40.0f, 600.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_2 = {440.0f, 0.0f, 50.0f, 600.0f, 1.6f, 200.0f, 1100.0f, 60.0f, 600.0f, 1.6f, -960.0f, 600.0f, 40.0f, 700.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_3 = {-160.0f, 0.0f, 60.0f, 800.0f, 1.6f, 700.0f, 1000.0f, 40.0f, 700.0f, 1.6f, -860.0f, 1000.0f, 50.0f, 600.0f, 1.6f};
    }

    public static class PadExclusiveCMinuteEffect {
        public static final float[] Gradient_NORMAL_PARAMS_0 = {-700.0f, 0.0f, 40.0f, 400.0f, 1.6f, 462.0f, 33.0f, 46.0f, 462.0f, 1.6f, -40.0f, 666.0f, 26.0f, 396.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_1 = {-106.0f, 0.0f, 40.0f, 400.0f, 1.6f, 462.0f, 666.0f, 46.0f, 462.0f, 1.6f, -568.0f, 666.0f, 26.0f, 396.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_2 = {290.0f, 0.0f, 33.0f, 400.0f, 1.6f, 132.0f, 726.0f, 40.0f, 400.0f, 1.6f, -634.0f, 400.0f, 26.0f, 400.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_3 = {-106.0f, 0.0f, 40.0f, 528.0f, 1.6f, 462.0f, 660.0f, 26.0f, 462.0f, 1.6f, -568.0f, 660.0f, 33.0f, 400.0f, 1.6f};
    }

    public static class RhombusEffect {
        public static final float[] Gradient_NORMAL_PARAMS_0 = {1400.0f, 400.0f, 500.0f, 900.0f, 1.6f, -350.0f, 500.0f, 400.0f, 950.0f, 1.6f, 1200.0f, 2000.0f, 600.0f, 1000.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_1 = {900.0f, 100.0f, 100.0f, 900.0f, 1.6f, -400.0f, 1600.0f, 200.0f, 1200.0f, 1.6f, 1400.0f, 1600.0f, 600.0f, 1000.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_2 = {1200.0f, 300.0f, 400.0f, 900.0f, 1.6f, -600.0f, 1600.0f, 500.0f, 1200.0f, 1.6f, 1000.0f, 1800.0f, 200.0f, 800.0f, 1.6f};
        public static final float[] Gradient_NORMAL_PARAMS_3 = {900.0f, 100.0f, 300.0f, 900.0f, 1.6f, -400.0f, 1600.0f, 300.0f, 1200.0f, 1.6f, 1400.0f, 1600.0f, 200.0f, 1000.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_0 = {1400.0f, 400.0f, 0.0f, 900.0f, 1.6f, -350.0f, 500.0f, 0.0f, 950.0f, 1.6f, 1200.0f, 2000.0f, 0.0f, 1000.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_1 = {900.0f, 100.0f, 0.0f, 900.0f, 1.6f, -400.0f, 1600.0f, 0.0f, 1200.0f, 1.6f, 1400.0f, 1600.0f, 0.0f, 1000.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_2 = {1200.0f, 300.0f, 0.0f, 900.0f, 1.6f, -600.0f, 1600.0f, 0.0f, 1200.0f, 1.6f, 1000.0f, 1800.0f, 0.0f, 800.0f, 1.6f};
        public static final float[] Gradient_AOD_PARAMS_3 = {900.0f, 100.0f, 0.0f, 900.0f, 1.6f, -400.0f, 1600.0f, 0.0f, 1200.0f, 1.6f, 1400.0f, 1600.0f, 0.0f, 1000.0f, 1.6f};
    }

    public static float[] hiddenGradientParams(float[] fArr) {
        float[] fArr2 = new float[15];
        for (int i = 0; i < 15; i++) {
            if (i == 2 || i == 7 || i == 12) {
                fArr2[i] = 0.0f;
            } else {
                fArr2[i] = fArr[i];
            }
        }
        return fArr2;
    }

    public static boolean needHiddenGradient(float[] fArr) {
        if (fArr == null) {
            return false;
        }
        if (fArr == EMPTY_GRADIENT) {
            return true;
        }
        float f = fArr[2];
        if (f == 0.0f) {
            float f2 = fArr[7];
            if (f == f2 && f2 == fArr[12]) {
                return true;
            }
        }
        return false;
    }
}
