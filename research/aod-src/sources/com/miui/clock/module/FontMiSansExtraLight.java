package com.miui.clock.module;

/* JADX INFO: loaded from: classes.dex */
public class FontMiSansExtraLight extends BaseFontStyle {
    public final float[] mPercentages = {0.85f, 0.21f, 0.12f, 0.12f, 0.22f, 0.1f, 0.06f};

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
        return BaseFontStyle.Style.MiSansExtraLight;
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
        return 52;
    }
}
