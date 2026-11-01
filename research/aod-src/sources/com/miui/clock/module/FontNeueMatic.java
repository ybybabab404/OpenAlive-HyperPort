package com.miui.clock.module;

/* JADX INFO: loaded from: classes.dex */
public class FontNeueMatic extends BaseFontStyle {
    public final float[] mPercentages = {0.77f, 0.26f, 0.17f, 0.15f, 0.24f, 0.11f, 0.06f};

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
        return BaseFontStyle.Style.NeueMatic;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[][][] getHourOffset() {
        return null;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[][][] getMinuteOffset() {
        return null;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float[] getPercentages() {
        return this.mPercentages;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallColonResource() {
        return 0;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[] getSvgResource() {
        return null;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getTextBoxWidth() {
        return 44;
    }
}
