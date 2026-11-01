package com.miui.clock.module;

import com.miui.clock.R;
import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes.dex */
public class FontWholeMinuteCopperplate extends BaseFontStyle {
    public final int[][][] mHourOffset;
    public final int[][][] mMinuteOffset;
    public final float[] mPercentages;
    public final float[] mSVGEmptyLeft;
    public final float[] mSVGEmptyRight;
    public final int[] mSVGResource = {R.drawable.font_j_r_bottom_00, R.drawable.font_j_r_bottom_01, R.drawable.font_j_r_bottom_02, R.drawable.font_j_r_bottom_03, R.drawable.font_j_r_bottom_04, R.drawable.font_j_r_bottom_05, R.drawable.font_j_r_bottom_06, R.drawable.font_j_r_bottom_07, R.drawable.font_j_r_bottom_08, R.drawable.font_j_r_bottom_09, R.drawable.font_j_r_bottom_10, R.drawable.font_j_r_bottom_11, R.drawable.font_j_r_bottom_12, R.drawable.font_j_r_bottom_13, R.drawable.font_j_r_bottom_14, R.drawable.font_j_r_bottom_15, R.drawable.font_j_r_bottom_16, R.drawable.font_j_r_bottom_17, R.drawable.font_j_r_bottom_18, R.drawable.font_j_r_bottom_19, R.drawable.font_j_r_bottom_20, R.drawable.font_j_r_bottom_21, R.drawable.font_j_r_bottom_22, R.drawable.font_j_r_bottom_23, R.drawable.font_j_r_bottom_24, R.drawable.font_j_r_bottom_25, R.drawable.font_j_r_bottom_26, R.drawable.font_j_r_bottom_27, R.drawable.font_j_r_bottom_28, R.drawable.font_j_r_bottom_29, R.drawable.font_j_r_bottom_30, R.drawable.font_j_r_bottom_31, R.drawable.font_j_r_bottom_32, R.drawable.font_j_r_bottom_33, R.drawable.font_j_r_bottom_34, R.drawable.font_j_r_bottom_35, R.drawable.font_j_r_bottom_36, R.drawable.font_j_r_bottom_37, R.drawable.font_j_r_bottom_38, R.drawable.font_j_r_bottom_39, R.drawable.font_j_r_bottom_40, R.drawable.font_j_r_bottom_41, R.drawable.font_j_r_bottom_42, R.drawable.font_j_r_bottom_43, R.drawable.font_j_r_bottom_44, R.drawable.font_j_r_bottom_45, R.drawable.font_j_r_bottom_46, R.drawable.font_j_r_bottom_47, R.drawable.font_j_r_bottom_48, R.drawable.font_j_r_bottom_49, R.drawable.font_j_r_bottom_50, R.drawable.font_j_r_bottom_51, R.drawable.font_j_r_bottom_52, R.drawable.font_j_r_bottom_53, R.drawable.font_j_r_bottom_54, R.drawable.font_j_r_bottom_55, R.drawable.font_j_r_bottom_56, R.drawable.font_j_r_bottom_57, R.drawable.font_j_r_bottom_58, R.drawable.font_j_r_bottom_59};

    public FontWholeMinuteCopperplate() {
        Class cls = Integer.TYPE;
        this.mHourOffset = (int[][][]) Array.newInstance((Class<?>) cls, 10, 10, 10);
        this.mMinuteOffset = (int[][][]) Array.newInstance((Class<?>) cls, 10, 10, 10);
        this.mPercentages = new float[]{0.805f, 0.1f, 0.06f, 0.06f, 0.13f, 0.05f, 0.02f};
        this.mSVGEmptyLeft = new float[]{29.714285f, 30.857143f, 30.857143f, 30.857143f, 17.904762f, 30.857143f, 29.714285f, 19.809525f, 27.809525f, 30.857143f, 30.857143f, 34.666668f, 27.809525f, 34.666668f, 27.809525f, 27.809525f, 29.714285f, 29.714285f, 36.95238f, 36.95238f, 40.0f, 40.0f, 40.0f, 34.666668f, 40.0f, 40.0f, 40.0f, 40.0f, 40.0f, 40.0f, 40.0f, 40.0f, 42.666668f, 40.0f, 42.666668f, 40.0f, 40.0f, 40.0f, 40.0f, 40.0f, 5.714286f, 5.714286f, 5.714286f, 5.714286f, 5.714286f, 5.714286f, 4.952381f, 6.857143f, 5.714286f, 5.714286f, 6.857143f, 6.857143f, 6.857143f, 6.857143f, 6.857143f, 5.714286f, 6.857143f, 6.857143f, 40.761906f, 6.857143f};
        this.mSVGEmptyRight = new float[]{262.85715f, 264.7619f, 281.14285f, 272.0f, 244.19048f, 256.7619f, 270.09525f, 259.0476f, 254.09525f, 260.57144f, 252.19048f, 239.2381f, 242.66667f, 245.33333f, 243.42857f, 247.61905f, 253.33333f, 254.4762f, 259.42856f, 256.0f, 267.42856f, 266.2857f, 261.7143f, 279.61905f, 253.71428f, 279.2381f, 267.42856f, 278.4762f, 263.2381f, 261.33334f, 266.66666f, 267.42856f, 273.5238f, 267.0476f, 272.7619f, 255.61905f, 268.9524f, 266.66666f, 255.2381f, 262.4762f, 272.7619f, 272.7619f, 290.66666f, 277.33334f, 265.5238f, 274.66666f, 278.4762f, 283.42856f, 276.57144f, 283.42856f, 270.4762f, 259.0476f, 281.14285f, 288.0f, 253.71428f, 257.90475f, 284.57144f, 276.57144f, 262.09525f, 264.7619f};
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
