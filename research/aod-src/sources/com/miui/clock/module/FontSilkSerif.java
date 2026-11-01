package com.miui.clock.module;

import com.miui.aod.category.TextStyleCategoryInfo;
import com.miui.clock.R;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes.dex */
public class FontSilkSerif extends BaseFontStyle {
    public final int[] mSVGResource = {R.drawable.font_a_0, R.drawable.font_a_1, R.drawable.font_a_2, R.drawable.font_a_3, R.drawable.font_a_4, R.drawable.font_a_5, R.drawable.font_a_6, R.drawable.font_a_7, R.drawable.font_a_8, R.drawable.font_a_9};
    public final float[] mPercentages = {0.8f, 0.21f, 0.13f, 0.13f, 0.33f, 0.13f, 0.06f};
    public final int[][][] mHourOffset = {new int[][]{new int[]{159, 95, 186, 207}, new int[]{152, 95, 208, 204}, new int[]{159, 95, 204, 207}, new int[]{159, 95, 194, 207}, new int[]{130, 95, 191, 202}, new int[]{152, 95, 204, 207}, new int[]{151, 95, 189, 206}, new int[]{159, 95, 200, 207}, new int[]{155, 95, 198, 207}, new int[]{159, 95, 198, 207}}, new int[][]{new int[]{188, 95, 186, 207}, new int[]{175, 96, 202, 204}, new int[]{180, com.miui.maml.R.styleable.AppCompatTheme_textAppearanceListItemSmall, 194, 207}, new int[]{171, 98, 188, 203}, new int[]{165, 95, 180, 202}, new int[]{180, 95, 197, 207}, new int[]{182, 95, 185, 207}, new int[]{174, com.miui.maml.R.styleable.AppCompatTheme_textAppearanceListItemSmall, 197, 209}, new int[]{175, 95, 198, 207}, new int[]{181, 95, 200, 203}}, new int[][]{new int[]{183, 95, 186, 207}, new int[]{170, 95, 208, 204}, new int[]{181, 95, 194, 207}, new int[]{177, 95, 190, 207}}};
    public final int[][][] mMinuteOffset = {new int[][]{new int[]{186, 83, 159, 195}, new int[]{186, 83, 174, 195}, new int[]{186, 83, 177, 195}, new int[]{186, 83, 167, 195}, new int[]{186, 83, 128, 192}, new int[]{186, 83, 170, 195}, new int[]{186, 83, 154, 195}, new int[]{186, 83, 173, 195}, new int[]{186, 83, 167, 195}, new int[]{186, 83, 171, 198}}, new int[][]{new int[]{218, 83, 156, 195}, new int[]{202, 83, 175, 191}, new int[]{218, 83, 156, 185}, new int[]{215, 83, 144, 185}, new int[]{218, 83, 129, 190}, new int[]{216, 83, 161, 195}, new int[]{216, 83, 151, 195}, new int[]{218, 87, 153, 191}, new int[]{218, 83, 155, 195}, new int[]{225, 83, 156, 191}}, new int[][]{new int[]{210, 83, 159, 195}, new int[]{204, 86, 174, 195}, new int[]{207, 83, 168, 195}, new int[]{200, 83, 167, 195}, new int[]{202, 83, com.miui.maml.R.styleable.AppCompatTheme_windowMinWidthMinor, 196}, new int[]{198, 83, 170, 195}, new int[]{205, 83, 154, EaseManager.EaseStyleDef.PERLIN}, new int[]{198, 83, 181, 195}, new int[]{203, 83, 167, 195}, new int[]{203, 83, 174, 198}}, new int[][]{new int[]{203, 83, 159, 195}, new int[]{203, 83, 174, 195}, new int[]{200, 83, 172, 195}, new int[]{202, 83, 164, 195}, new int[]{202, 83, com.miui.maml.R.styleable.AppCompatTheme_windowMinWidthMinor, 192}, new int[]{200, 83, 170, 195}, new int[]{200, 83, 154, 195}, new int[]{204, 83, 173, 195}, new int[]{200, 83, 167, 195}, new int[]{200, 83, 171, 198}}, new int[][]{new int[]{EaseManager.EaseStyleDef.PERLIN, 83, 159, 195}, new int[]{204, 83, 174, 195}, new int[]{206, 83, 166, 195}, new int[]{204, 83, 164, 195}, new int[]{204, 83, com.miui.maml.R.styleable.AppCompatTheme_viewInflaterClass, 194}, new int[]{204, 83, 170, 195}, new int[]{196, 83, 154, 195}, new int[]{204, 83, 174, 202}, new int[]{204, 83, 167, 195}, new int[]{196, 83, 171, 198}}, new int[][]{new int[]{EaseManager.EaseStyleDef.PERLIN, 83, TextStyleCategoryInfo.MIN_FONT_WEIGHT, 195}, new int[]{EaseManager.EaseStyleDef.PERLIN, 83, 170, 195}, new int[]{EaseManager.EaseStyleDef.PERLIN, 83, 161, 195}, new int[]{EaseManager.EaseStyleDef.PERLIN, 83, 160, 195}, new int[]{200, 83, com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMinor, 184}, new int[]{EaseManager.EaseStyleDef.PERLIN, 83, 161, 195}, new int[]{EaseManager.EaseStyleDef.PERLIN, 83, 139, 195}, new int[]{EaseManager.EaseStyleDef.PERLIN, 83, 169, 195}, new int[]{EaseManager.EaseStyleDef.PERLIN, 83, 158, 195}, new int[]{EaseManager.EaseStyleDef.PERLIN, 83, 158, 198}}};
    public final float[] mSVGEmptyLeft = {16.380953f, 12.952381f, 4.952381f, 11.809524f, 8.0f, 11.809524f, 13.333333f, 10.666667f, 12.190476f, 11.428572f};
    public final float[] mSVGEmptyRight = {120.7619f, 67.42857f, 107.809525f, 113.14286f, 125.333336f, 105.14286f, 116.57143f, 117.333336f, 113.14286f, 113.90476f};
    public final int[] mLargeClockWidth = {141, 77, com.miui.maml.R.styleable.AppCompatTheme_windowActionBar, com.miui.maml.R.styleable.AppCompatTheme_windowMinWidthMajor, 134, com.miui.maml.R.styleable.AppCompatTheme_windowActionModeOverlay, 129, com.miui.maml.R.styleable.AppCompatTheme_windowActionBarOverlay, com.miui.maml.R.styleable.AppCompatTheme_windowMinWidthMinor, com.miui.maml.R.styleable.AppCompatTheme_windowNoTitle};

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
        return 28.0f;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public BaseFontStyle.Style getFontStyle() {
        return BaseFontStyle.Style.SilkSerif;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[][][] getHourOffset() {
        return this.mHourOffset;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getLargeClockHeight() {
        return 174;
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
        return 70;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallClockInnerMarginOffset() {
        return -2;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallColonResource() {
        int i = this.mUserDefinedSmallColonResource;
        return i != 0 ? i : R.drawable.font_a_colon;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallColonResourceHeight() {
        return 174;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[] getSvgResource() {
        return this.mSVGResource;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getTextBoxWidth() {
        return 141;
    }
}
