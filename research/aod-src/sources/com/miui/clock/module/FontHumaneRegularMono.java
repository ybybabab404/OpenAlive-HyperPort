package com.miui.clock.module;

import com.miui.clock.R;

/* JADX INFO: loaded from: classes.dex */
public class FontHumaneRegularMono extends BaseFontStyle {
    public final int[] mSVGResource = {R.drawable.font_hnmr_0, R.drawable.font_hnmr_1, R.drawable.font_hnmr_2, R.drawable.font_hnmr_3, R.drawable.font_hnmr_4, R.drawable.font_hnmr_5, R.drawable.font_hnmr_6, R.drawable.font_hnmr_7, R.drawable.font_hnmr_8, R.drawable.font_hnmr_9};

    @Override // com.miui.clock.module.BaseFontStyle
    public int getColonResource() {
        return 0;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float getColonSize() {
        return 0.0f;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public BaseFontStyle.Style getFontStyle() {
        return BaseFontStyle.Style.HeadingNowMono;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[][][] getHourOffset() {
        return new int[0][][];
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[][][] getMinuteOffset() {
        return new int[0][][];
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float[] getPercentages() {
        return new float[0];
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallColonResource() {
        return 0;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[] getSvgResource() {
        return this.mSVGResource;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getTextBoxWidth() {
        return 0;
    }
}
