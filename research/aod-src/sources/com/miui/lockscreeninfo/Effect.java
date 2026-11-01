package com.miui.lockscreeninfo;

import android.graphics.Color;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class Effect {

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
    }
}
