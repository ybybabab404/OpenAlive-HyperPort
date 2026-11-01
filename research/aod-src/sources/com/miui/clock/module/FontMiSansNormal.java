package com.miui.clock.module;

/* JADX INFO: loaded from: classes.dex */
public class FontMiSansNormal extends BaseFontStyle {
    public final float[] mPercentages = {0.8f, 0.2f, 0.15f, 0.15f, 0.28f, 0.12f, 0.06f};
    public final float[] mCHSPercentages = {0.81f, 0.17f, 0.14f, 0.14f, 0.18f, 0.13f, 0.05f};

    @Override // com.miui.clock.module.BaseFontStyle
    public float[] getCHSPercentages() {
        return this.mCHSPercentages;
    }

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
        return BaseFontStyle.Style.MiSansNormal;
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
        return 41;
    }
}
