package com.miui.clock.module;

import com.miui.clock.R;

/* JADX INFO: loaded from: classes.dex */
public class FontHumaneMono extends BaseFontStyle {
    public final int[] mSVGResource = {R.drawable.font_hnm_0, R.drawable.font_hnm_1, R.drawable.font_hnm_2, R.drawable.font_hnm_3, R.drawable.font_hnm_4, R.drawable.font_hnm_5, R.drawable.font_hnm_6, R.drawable.font_hnm_7, R.drawable.font_hnm_8, R.drawable.font_hnm_9};

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
