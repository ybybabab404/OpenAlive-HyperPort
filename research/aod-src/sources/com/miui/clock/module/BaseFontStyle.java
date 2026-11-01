package com.miui.clock.module;

import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseFontStyle {
    public final float[][][] mHourRotateDegree;
    public final int[] mLargeClockHeight;
    public final int[] mLargeClockWidth;
    public final float[][][] mMinuteRotateDegree;
    public int mUserDefinedColonResource;
    public int[] mUserDefinedResources;
    public int mUserDefinedSmallColonResource;
    public final float[] mCenterXOffset = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    public final float[] mNotificationCenterXOffset = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};

    public enum Style {
        BodoniModa,
        DryBrush9,
        FoundryGridnik,
        MiCamera,
        MiSansNormal,
        MiSansDemiBold,
        MiSansExtraLight,
        MiSansRoundLock,
        NeumaticCompressed,
        Salome,
        SilkSerif,
        SKPupok,
        Copperplate,
        NeueMatic,
        NeumaticCompressedBlack,
        HeadingNowMono
    }

    public BaseFontStyle() {
        Class cls = Float.TYPE;
        this.mHourRotateDegree = (float[][][]) Array.newInstance((Class<?>) cls, 10, 10, 10);
        this.mMinuteRotateDegree = (float[][][]) Array.newInstance((Class<?>) cls, 10, 10, 10);
        this.mLargeClockWidth = new int[10];
        this.mLargeClockHeight = new int[10];
    }

    public boolean enableRotate() {
        return false;
    }

    public int getAodDateMarginTop() {
        return 0;
    }

    public float[] getCHSPercentages() {
        return getPercentages();
    }

    public float getColonInterval() {
        return 0.0f;
    }

    public abstract int getColonResource();

    public abstract float getColonSize();

    public int[] getFontRotateValue(int i, float f) {
        return null;
    }

    public abstract Style getFontStyle();

    public abstract int[][][] getHourOffset();

    public float[][][] getHourRotateDegree() {
        return this.mHourRotateDegree;
    }

    public int getLargeClockHeight() {
        return 0;
    }

    public int[] getLargeClockHeights() {
        return this.mLargeClockHeight;
    }

    public int[] getLargeClockWidth() {
        return this.mLargeClockWidth;
    }

    public float[] getLeftEmpty() {
        return new float[100];
    }

    public float getMaxRotateDegree() {
        return 0.0f;
    }

    public abstract int[][][] getMinuteOffset();

    public float[][][] getMinuteRotateDegree() {
        return this.mMinuteRotateDegree;
    }

    public float[] getNotificationCenterXOffset() {
        return this.mNotificationCenterXOffset;
    }

    public abstract float[] getPercentages();

    public float[] getRightEmpty() {
        return new float[100];
    }

    public int getSmallClockInnerHeight() {
        return 0;
    }

    public int getSmallClockInnerMarginOffset() {
        return 0;
    }

    public abstract int getSmallColonResource();

    public int getSmallColonResourceHeight() {
        return 0;
    }

    public abstract int[] getSvgResource();

    public abstract int getTextBoxWidth();

    public int[] getUserDefinedResources() {
        return this.mUserDefinedResources;
    }

    public void setUserDefinedColonResource(int i) {
        this.mUserDefinedColonResource = i;
    }

    public void setUserDefinedResource(int[] iArr) {
        this.mUserDefinedResources = iArr;
    }
}
