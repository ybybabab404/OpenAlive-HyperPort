package com.miui.clock.module;

import com.miui.clock.R;
import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes.dex */
public class FontWholeHourCopperplate extends BaseFontStyle {
    public final int[][][] mHourOffset;
    public final int[][][] mMinuteOffset;
    public final float[] mPercentages;
    public final float[] mSVGEmptyLeft;
    public final float[] mSVGEmptyRight;
    public final int[] mSVGResource = {R.drawable.font_j_r_top_00, R.drawable.font_j_r_top_01, R.drawable.font_j_r_top_02, R.drawable.font_j_r_top_03, R.drawable.font_j_r_top_04, R.drawable.font_j_r_top_05, R.drawable.font_j_r_top_06, R.drawable.font_j_r_top_07, R.drawable.font_j_r_top_08, R.drawable.font_j_r_top_09, R.drawable.font_j_r_top_10, R.drawable.font_j_r_top_11, R.drawable.font_j_r_top_12, R.drawable.font_j_r_top_13, R.drawable.font_j_r_top_14, R.drawable.font_j_r_top_15, R.drawable.font_j_r_top_16, R.drawable.font_j_r_top_17, R.drawable.font_j_r_top_18, R.drawable.font_j_r_top_19, R.drawable.font_j_r_top_20, R.drawable.font_j_r_top_21, R.drawable.font_j_r_top_22, R.drawable.font_j_r_top_23};

    public FontWholeHourCopperplate() {
        Class cls = Integer.TYPE;
        this.mHourOffset = (int[][][]) Array.newInstance((Class<?>) cls, 10, 10, 10);
        this.mMinuteOffset = (int[][][]) Array.newInstance((Class<?>) cls, 10, 10, 10);
        this.mPercentages = new float[]{0.805f, 0.1f, 0.06f, 0.06f, 0.13f, 0.05f, 0.02f};
        this.mSVGEmptyLeft = new float[]{80.7619f, 80.0f, 77.71429f, 80.7619f, 86.85714f, 85.71429f, 84.95238f, 73.90476f, 84.95238f, 93.71429f, 90.666664f, 96.7619f, 78.85714f, 88.7619f, 96.7619f, 97.90476f, 94.85714f, 90.666664f, 101.71429f, 97.90476f, 86.85714f, 75.809525f, 77.71429f, 77.71429f};
        this.mSVGEmptyRight = new float[]{313.5238f, 307.0476f, 321.90475f, 337.14285f, 312.0f, 312.0f, 325.33334f, 313.14285f, 326.09525f, 323.42856f, 312.38095f, 297.5238f, 297.90475f, 301.7143f, 309.7143f, 311.2381f, 317.33334f, 308.9524f, 318.09525f, 316.57144f, 326.4762f, 313.5238f, 324.1905f, 323.0476f};
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float getColonInterval() {
        return 40.0f;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getColonResource() {
        return R.drawable.miui_rhombus_colon_circle;
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
    public int getSmallColonResource() {
        return R.drawable.font_j_h_colon;
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
