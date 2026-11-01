package com.miui.clock.module;

import com.miui.clock.R;

/* JADX INFO: loaded from: classes.dex */
public class FontNeumaticCompressedBF extends BaseFontStyle {
    public final int[] mSVGResource = {R.drawable.font_ncbf_0, R.drawable.font_ncbf_1, R.drawable.font_ncbf_2, R.drawable.font_ncbf_3, R.drawable.font_ncbf_4, R.drawable.font_ncbf_5, R.drawable.font_ncbf_6, R.drawable.font_ncbf_7, R.drawable.font_ncbf_8, R.drawable.font_ncbf_9};

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
        return BaseFontStyle.Style.NeumaticCompressedBlack;
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
