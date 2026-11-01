package com.miui.clock.module;

import com.miui.clock.R;

/* JADX INFO: loaded from: classes.dex */
public class FontNeumaticCompressed extends BaseFontStyle {
    public final int[] mSVGResource = {R.drawable.font_h_0, R.drawable.font_h_1, R.drawable.font_h_2, R.drawable.font_h_3, R.drawable.font_h_4, R.drawable.font_h_5, R.drawable.font_h_6, R.drawable.font_h_7, R.drawable.font_h_8, R.drawable.font_h_9};
    public final int[][][] mHourOffset = {new int[][]{new int[]{184, 86, 218, 170}, new int[]{184, 86, 218, 170}, new int[]{184, 86, 218, 170}, new int[]{184, 86, 218, 170}, new int[]{181, 86, 217, 170}, new int[]{184, 86, 219, 170}, new int[]{184, 86, 218, 170}, new int[]{181, 86, 218, 166}, new int[]{184, 86, 218, 170}, new int[]{184, 86, 218, 170}}, new int[][]{new int[]{187, 81, 218, 171}, new int[]{189, 81, 214, 166}, new int[]{187, 81, 218, 171}, new int[]{187, 81, 218, 171}, new int[]{194, 81, 205, 171}, new int[]{187, 81, 219, 171}, new int[]{187, 81, 218, 171}, new int[]{187, 81, 215, 171}, new int[]{187, 81, 218, 171}, new int[]{187, 81, 218, 171}}, new int[][]{new int[]{184, 78, 218, 171}, new int[]{178, 78, 217, 179}, new int[]{184, 78, 218, 171}, new int[]{184, 78, 218, 171}}};
    public final int[][][] mMinuteOffset = {new int[][]{new int[]{220, 75, 187, 168}, new int[]{220, 75, 190, 170}, new int[]{220, 75, 187, 168}, new int[]{220, 75, 187, 168}, new int[]{220, 75, 179, 168}, new int[]{220, 75, 188, 168}, new int[]{220, 75, 187, 168}, new int[]{220, 75, 188, 170}, new int[]{220, 75, 187, 168}, new int[]{220, 75, 187, 168}}, new int[][]{new int[]{222, 85, 187, 168}, new int[]{222, 85, 190, 170}, new int[]{222, 85, 187, 168}, new int[]{222, 85, 187, 168}, new int[]{222, 85, 179, 168}, new int[]{222, 85, 188, 168}, new int[]{222, 85, 187, 168}, new int[]{222, 85, 188, 170}, new int[]{222, 85, 187, 168}, new int[]{222, 85, 187, 168}}, new int[][]{new int[]{220, 73, 187, 166}, new int[]{220, 82, 190, 195}, new int[]{220, 73, 187, 166}, new int[]{220, 73, 187, 166}, new int[]{220, 73, 179, 166}, new int[]{220, 73, 188, 166}, new int[]{220, 73, 187, 166}, new int[]{220, 73, 181, 193}, new int[]{220, 73, 187, 166}, new int[]{220, 73, 187, 166}}, new int[][]{new int[]{220, 73, 187, 166}, new int[]{220, 73, 190, 168}, new int[]{220, 73, 187, 166}, new int[]{220, 73, 187, 166}, new int[]{220, 73, 179, 166}, new int[]{220, 73, 188, 166}, new int[]{220, 73, 187, 166}, new int[]{220, 73, 188, 168}, new int[]{220, 73, 187, 166}, new int[]{220, 73, 187, 166}}, new int[][]{new int[]{220, 82, 187, 167}, new int[]{220, 82, 190, 169}, new int[]{220, 82, 187, 167}, new int[]{220, 82, 187, 167}, new int[]{220, 82, 179, 167}, new int[]{220, 82, 188, 167}, new int[]{220, 82, 187, 167}, new int[]{220, 82, 188, 169}, new int[]{220, 82, 187, 167}, new int[]{220, 82, 187, 166}}, new int[][]{new int[]{220, 75, 187, 166}, new int[]{220, 75, 190, 168}, new int[]{220, 75, 187, 166}, new int[]{220, 75, 187, 166}, new int[]{220, 75, 179, 166}, new int[]{220, 75, 188, 166}, new int[]{220, 75, 187, 166}, new int[]{220, 75, 188, 168}, new int[]{220, 75, 187, 166}, new int[]{220, 75, 187, 166}}};
    public final float[] mPercentages = {0.78f, 0.27f, 0.27f, 0.27f, 0.27f, 0.23f, 0.12f};
    public final float[] mSVGEmptyLeft = {1.5238096f, 1.1428572f, 1.5238096f, 1.5238096f, 1.1428572f, 1.1428572f, 1.5238096f, 0.7619048f, 1.5238096f, 1.5238096f};
    public final float[] mSVGEmptyRight = {42.666668f, 26.666666f, 42.666668f, 42.666668f, 48.0f, 41.904762f, 42.666668f, 38.857143f, 42.666668f, 42.666668f};
    public final int[] mLargeClockWidth = {44, 28, 44, 44, 49, 43, 44, 40, 44, 44};

    @Override // com.miui.clock.module.BaseFontStyle
    public int getAodDateMarginTop() {
        return 40;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float getColonInterval() {
        return 48.0f;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getColonResource() {
        int i = this.mUserDefinedColonResource;
        return i != 0 ? i : R.drawable.miui_rhombus_colon_rect_8;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float getColonSize() {
        return 28.0f;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public BaseFontStyle.Style getFontStyle() {
        return BaseFontStyle.Style.NeumaticCompressed;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[][][] getHourOffset() {
        return this.mHourOffset;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getLargeClockHeight() {
        return 181;
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
        return 72;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallClockInnerMarginOffset() {
        return 28;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallColonResource() {
        int i = this.mUserDefinedSmallColonResource;
        return i != 0 ? i : R.drawable.font_h_colon;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallColonResourceHeight() {
        return 181;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[] getSvgResource() {
        return this.mSVGResource;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getTextBoxWidth() {
        return 49;
    }
}
