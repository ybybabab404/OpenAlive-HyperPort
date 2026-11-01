package com.miui.clock.module;

import com.miui.aod.category.TextStyleCategoryInfo;
import com.miui.clock.R;

/* JADX INFO: loaded from: classes.dex */
public class FontFoundryGridnik extends BaseFontStyle {
    public final int[] mSVGResource = {R.drawable.font_f_0, R.drawable.font_f_1, R.drawable.font_f_2, R.drawable.font_f_3, R.drawable.font_f_4, R.drawable.font_f_5, R.drawable.font_f_6, R.drawable.font_f_7, R.drawable.font_f_8, R.drawable.font_f_9};
    public final float[] mPercentages = {0.79f, 0.2f, 0.14f, 0.14f, 0.25f, 0.14f, 0.06f};
    public final int[][][] mHourOffset = {new int[][]{new int[]{158, 102, 207, 209}, new int[]{158, 102, 197, 212}, new int[]{158, 102, 206, 209}, new int[]{171, 99, 192, 199}, new int[]{146, 102, 195, 210}, new int[]{158, 102, 204, 212}, new int[]{158, 102, 209, 206}, new int[]{168, 102, 206, 210}, new int[]{158, 102, 207, 212}, new int[]{162, 102, 204, 212}}, new int[][]{new int[]{156, 100, 207, 209}, new int[]{TextStyleCategoryInfo.MIN_FONT_WEIGHT, 100, 197, 210}, new int[]{156, 100, 208, 209}, new int[]{156, 100, 200, 209}, new int[]{145, 100, 195, 208}, new int[]{153, 100, 204, 212}, new int[]{156, 100, 209, 206}, new int[]{154, 100, 202, 198}, new int[]{156, 100, 207, 212}, new int[]{158, 100, 204, 212}}, new int[][]{new int[]{164, 102, 207, 209}, new int[]{155, 102, 195, 187}, new int[]{158, 102, 204, 209}, new int[]{164, 102, 200, 209}}};
    public final int[][][] mMinuteOffset = {new int[][]{new int[]{207, 85, 159, 197}, new int[]{207, 85, 148, 197}, new int[]{207, 85, 157, 197}, new int[]{207, 85, 159, 193}, new int[]{205, 85, 136, 196}, new int[]{207, 85, 154, 197}, new int[]{207, 85, 160, 195}, new int[]{207, 85, 162, 197}, new int[]{207, 85, 159, 193}, new int[]{207, 85, 159, 194}}, new int[][]{new int[]{202, 85, 159, 197}, new int[]{200, 96, 144, 195}, new int[]{202, 85, 159, 197}, new int[]{202, 85, 159, 197}, new int[]{186, 85, 149, 198}, new int[]{202, 85, 155, 197}, new int[]{202, 85, 158, 197}, new int[]{192, 100, 156, 198}, new int[]{202, 85, 161, 197}, new int[]{202, 85, 160, 194}}, new int[][]{new int[]{207, 85, 159, 197}, new int[]{207, 85, 154, 194}, new int[]{207, 85, 161, 192}, new int[]{207, 85, 160, 192}, new int[]{207, 85, 144, 194}, new int[]{207, 85, 156, 197}, new int[]{207, 85, 160, 195}, new int[]{207, 93, 156, 198}, new int[]{207, 85, 161, 193}, new int[]{207, 85, 161, 194}}, new int[][]{new int[]{207, 85, 159, 197}, new int[]{207, 85, 148, 197}, new int[]{207, 85, 157, 197}, new int[]{207, 85, 159, 193}, new int[]{206, 85, 135, 194}, new int[]{207, 85, 156, 197}, new int[]{207, 85, 159, 195}, new int[]{207, 85, 162, 198}, new int[]{207, 85, 159, 193}, new int[]{207, 85, 159, 194}}, new int[][]{new int[]{216, 85, 144, 197}, new int[]{215, 85, 135, 197}, new int[]{216, 89, 145, 197}, new int[]{216, 89, 142, 198}, new int[]{208, 91, 131, 198}, new int[]{216, 85, 147, 197}, new int[]{216, 85, 146, 196}, new int[]{216, 85, 153, 198}, new int[]{216, 85, 146, 196}, new int[]{216, 85, 146, 196}}, new int[][]{new int[]{207, 85, 159, 197}, new int[]{207, 85, 148, 197}, new int[]{207, 85, 157, 197}, new int[]{207, 85, 159, 193}, new int[]{207, 89, 136, 196}, new int[]{207, 85, 156, 197}, new int[]{207, 85, 159, 195}, new int[]{207, 85, 162, 198}, new int[]{207, 85, 158, 193}, new int[]{207, 85, 158, 194}}};
    public final float[] mSVGEmptyLeft = {20.190475f, 28.190475f, 19.428572f, 19.428572f, 12.190476f, 19.428572f, 20.190475f, 19.428572f, 20.190475f, 19.428572f};
    public final float[] mSVGEmptyRight = {113.90476f, 105.90476f, 113.90476f, 113.90476f, 114.666664f, 113.90476f, 114.666664f, 113.90476f, 113.90476f, 113.90476f};
    public final int[] mLargeClockWidth = {134, 134, 134, 134, 134, 134, 134, 134, 134, 134};

    @Override // com.miui.clock.module.BaseFontStyle
    public int getAodDateMarginTop() {
        return 40;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float getColonInterval() {
        return 36.0f;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getColonResource() {
        int i = this.mUserDefinedColonResource;
        return i != 0 ? i : R.drawable.miui_rhombus_colon_rect_6;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float getColonSize() {
        return 32.0f;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public BaseFontStyle.Style getFontStyle() {
        return BaseFontStyle.Style.FoundryGridnik;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[][][] getHourOffset() {
        return this.mHourOffset;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getLargeClockHeight() {
        return 159;
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
        return -8;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallColonResource() {
        int i = this.mUserDefinedSmallColonResource;
        return i != 0 ? i : R.drawable.font_f_colon;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallColonResourceHeight() {
        return 159;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[] getSvgResource() {
        return this.mSVGResource;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getTextBoxWidth() {
        return 134;
    }
}
