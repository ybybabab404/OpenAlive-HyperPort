package com.miui.clock.hct;

/* JADX INFO: loaded from: classes.dex */
public final class Hct {
    public int argb;
    public double chroma;
    public double hue;
    public double tone;

    public Hct(int i) {
        setInternalState(i);
    }

    public static Hct fromInt(int i) {
        return new Hct(i);
    }

    public double getChroma() {
        return this.chroma;
    }

    public double getHue() {
        return this.hue;
    }

    public double getTone() {
        return this.tone;
    }

    public final void setInternalState(int i) {
        this.argb = i;
        Cam16 cam16FromInt = Cam16.fromInt(i);
        this.hue = cam16FromInt.getHue();
        this.chroma = cam16FromInt.getChroma();
        this.tone = ColorUtils.lstarFromArgb(i);
    }

    public void setTone(double d) {
        setInternalState(HctSolver.solveToInt(this.hue, this.chroma, d));
    }

    public int toInt() {
        return this.argb;
    }
}
