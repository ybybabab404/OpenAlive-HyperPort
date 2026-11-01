package com.miui.clock.module;

import com.miui.clock.R;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes.dex */
public class FontMiSerif extends BaseFontStyle {
    public final int[] mSVGResource = {R.drawable.font_g_0, R.drawable.font_g_1, R.drawable.font_g_2, R.drawable.font_g_3, R.drawable.font_g_4, R.drawable.font_g_5, R.drawable.font_g_6, R.drawable.font_g_7, R.drawable.font_g_8, R.drawable.font_g_9};
    public final float[] mPercentages = {0.85f, 0.21f, 0.15f, 0.15f, 0.25f, 0.12f, 0.06f};
    public final float[] mSVGEmptyLeft = {8.0f, 10.285714f, 8.0f, 5.3333335f, 7.2380953f, 3.8095238f, 9.904762f, 8.380953f, 8.0f, 5.714286f};
    public final float[] mSVGEmptyRight = {121.14286f, 84.57143f, 109.333336f, 102.47619f, 123.04762f, 99.04762f, 112.38095f, 103.61905f, 108.57143f, 108.190475f};
    public final int[] mLargeClockWidth = {129, 95, com.miui.maml.R.styleable.AppCompatTheme_viewInflaterClass, com.miui.maml.R.styleable.AppCompatTheme_textColorSearchUrl, com.miui.maml.R.styleable.AppCompatTheme_windowMinWidthMinor, com.miui.maml.R.styleable.AppCompatTheme_textAppearanceListItemSmall, com.miui.maml.R.styleable.AppCompatTheme_windowActionBarOverlay, com.miui.maml.R.styleable.AppCompatTheme_textAppearancePopupMenuHeader, com.miui.maml.R.styleable.AppCompatTheme_viewInflaterClass, com.miui.maml.R.styleable.AppCompatTheme_windowActionBarOverlay};
    public final int[][][] mHourOffset = {new int[][]{new int[]{174, 99, 200, 197}, new int[]{168, 99, 219, 197}, new int[]{177, 99, EaseManager.EaseStyleDef.PERLIN, EaseManager.EaseStyleDef.PERLIN}, new int[]{174, 99, 209, 197}, new int[]{141, 92, 198, 202}, new int[]{174, 99, 217, 197}, new int[]{163, 99, 200, 197}, new int[]{174, 99, 216, 203}, new int[]{164, 99, 216, 197}, new int[]{165, 99, 216, EaseManager.EaseStyleDef.PERLIN}}, new int[][]{new int[]{190, 93, 200, 197}, new int[]{166, 88, 219, 197}, new int[]{190, 93, EaseManager.EaseStyleDef.PERLIN, EaseManager.EaseStyleDef.PERLIN}, new int[]{174, 93, 209, 197}, new int[]{178, 93, 178, 199}, new int[]{174, 93, 217, 197}, new int[]{183, 93, 200, 197}, new int[]{178, 93, 216, 203}, new int[]{172, 93, 216, 197}, new int[]{179, 93, 216, EaseManager.EaseStyleDef.PERLIN}}, new int[][]{new int[]{175, 95, 217, 199}, new int[]{163, 95, 210, 187}, new int[]{178, 95, 218, 199}, new int[]{182, 95, 211, 197}}};
    public final int[][][] mMinuteOffset = {new int[][]{new int[]{200, 91, 174, 190}, new int[]{216, 91, 171, 190}, new int[]{200, 91, 182, 186}, new int[]{200, 91, 186, 186}, new int[]{200, 91, 147, 187}, new int[]{216, 91, 171, 196}, new int[]{200, 91, 165, 186}, new int[]{216, 91, 174, 196}, new int[]{200, 91, 181, 192}, new int[]{200, 91, 184, 186}}, new int[][]{new int[]{227, 91, 164, 190}, new int[]{219, 96, 160, 197}, new int[]{227, 91, 160, 186}, new int[]{227, 91, 156, 191}, new int[]{227, 91, 136, 186}, new int[]{227, 91, 164, 196}, new int[]{227, 91, 163, 186}, new int[]{227, 91, 165, 195}, new int[]{227, 91, 164, 192}, new int[]{227, 91, 170, 186}}, new int[][]{new int[]{224, 91, 174, 190}, new int[]{224, 91, 166, 195}, new int[]{224, 91, 171, 189}, new int[]{224, 91, 170, 186}, new int[]{224, 91, 142, 189}, new int[]{224, 91, 173, 196}, new int[]{224, 91, 167, 186}, new int[]{224, 91, 174, 196}, new int[]{224, 91, 169, 192}, new int[]{224, 91, 176, 186}}, new int[][]{new int[]{209, 91, 174, 190}, new int[]{209, 91, 179, 192}, new int[]{209, 91, 182, 186}, new int[]{209, 91, 182, 186}, new int[]{209, 91, 143, 180}, new int[]{221, 91, 171, 196}, new int[]{209, 91, 166, 186}, new int[]{222, 86, 174, 196}, new int[]{209, 91, 181, 192}, new int[]{209, 91, 184, 189}}, new int[][]{new int[]{223, 91, 170, 190}, new int[]{223, 91, 171, 197}, new int[]{223, 91, 171, 186}, new int[]{223, 91, 171, 186}, new int[]{223, 91, 140, 186}, new int[]{223, 91, 171, 196}, new int[]{223, 91, 163, 186}, new int[]{223, 91, 177, 196}, new int[]{223, 91, 171, 192}, new int[]{223, 91, 177, 189}}, new int[][]{new int[]{219, 91, 166, 190}, new int[]{219, 91, 173, 192}, new int[]{219, 91, 173, 186}, new int[]{219, 91, 174, 186}, new int[]{219, 91, 138, 182}, new int[]{219, 91, 177, 196}, new int[]{219, 91, 156, 186}, new int[]{228, 91, 169, 196}, new int[]{219, 91, 170, 192}, new int[]{219, 91, 172, 189}}};

    @Override // com.miui.clock.module.BaseFontStyle
    public int getAodDateMarginTop() {
        return 32;
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
        return 36.0f;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public BaseFontStyle.Style getFontStyle() {
        return BaseFontStyle.Style.BodoniModa;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[][][] getHourOffset() {
        return this.mHourOffset;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getLargeClockHeight() {
        return 170;
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
        return 0;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallColonResource() {
        int i = this.mUserDefinedSmallColonResource;
        return i != 0 ? i : R.drawable.font_g_colon;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallColonResourceHeight() {
        return 170;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[] getSvgResource() {
        return this.mSVGResource;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getTextBoxWidth() {
        return 129;
    }
}
