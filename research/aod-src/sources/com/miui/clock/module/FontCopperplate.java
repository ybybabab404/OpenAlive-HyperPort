package com.miui.clock.module;

import com.miui.clock.R;
import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes.dex */
public class FontCopperplate extends BaseFontStyle {
    public final int[][][] mHourOffset;
    public final int[] mLargeClockWidth;
    public final int[][][] mMinuteOffset;
    public final float[] mPercentages;
    public final float[] mSVGEmptyLeft;
    public final float[] mSVGEmptyRight;
    public final int[] mSVGResource = {R.drawable.font_j_h_0, R.drawable.font_j_h_1, R.drawable.font_j_h_2, R.drawable.font_j_h_3, R.drawable.font_j_h_4, R.drawable.font_j_h_5, R.drawable.font_j_h_6, R.drawable.font_j_h_7, R.drawable.font_j_h_8, R.drawable.font_j_h_9};

    public FontCopperplate() {
        Class cls = Integer.TYPE;
        this.mHourOffset = (int[][][]) Array.newInstance((Class<?>) cls, 10, 10, 10);
        this.mMinuteOffset = (int[][][]) Array.newInstance((Class<?>) cls, 10, 10, 10);
        this.mPercentages = new float[]{0.805f, 0.1f, 0.06f, 0.06f, 0.13f, 0.05f, 0.02f};
        this.mSVGEmptyLeft = new float[]{9.904762f, 14.857142f, 9.142858f, 11.809524f, 4.1904764f, 12.190476f, 9.904762f, 6.095238f, 9.904762f, 7.6190476f};
        this.mSVGEmptyRight = new float[]{118.85714f, 64.7619f, 104.7619f, 111.2381f, 116.190475f, 112.0f, 116.57143f, 103.2381f, 121.14286f, 113.52381f};
        this.mLargeClockWidth = new int[]{130, 82, com.miui.maml.R.styleable.AppCompatTheme_tooltipFrameBackground, com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMinor, com.miui.maml.R.styleable.AppCompatTheme_windowFixedWidthMinor, com.miui.maml.R.styleable.AppCompatTheme_windowFixedWidthMinor, 127, com.miui.maml.R.styleable.AppCompatTheme_tooltipForegroundColor, 132, com.miui.maml.R.styleable.AppCompatTheme_windowMinWidthMinor};
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getAodDateMarginTop() {
        return 40;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float getColonInterval() {
        return 40.0f;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getColonResource() {
        int i = this.mUserDefinedColonResource;
        return i != 0 ? i : R.drawable.miui_rhombus_colon_circle;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float getColonSize() {
        return 32.0f;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public BaseFontStyle.Style getFontStyle() {
        return BaseFontStyle.Style.Copperplate;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[][][] getHourOffset() {
        return this.mHourOffset;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getLargeClockHeight() {
        return 161;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[] getLargeClockWidth() {
        return this.mLargeClockWidth;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float[] getLeftEmpty() {
        return this.mSVGEmptyLeft;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[][][] getMinuteOffset() {
        return this.mMinuteOffset;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float[] getPercentages() {
        return this.mPercentages;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float[] getRightEmpty() {
        return this.mSVGEmptyRight;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallClockInnerHeight() {
        return 74;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallClockInnerMarginOffset() {
        return -4;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallColonResource() {
        int i = this.mUserDefinedSmallColonResource;
        return i != 0 ? i : R.drawable.font_j_h_colon;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallColonResourceHeight() {
        return 161;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[] getSvgResource() {
        return this.mSVGResource;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getTextBoxWidth() {
        return 352;
    }
}
