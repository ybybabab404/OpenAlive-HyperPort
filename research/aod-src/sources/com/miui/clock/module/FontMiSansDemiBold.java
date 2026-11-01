package com.miui.clock.module;

/* JADX INFO: loaded from: classes.dex */
public class FontMiSansDemiBold extends BaseFontStyle {
    public final float[] mPercentages = {0.8f, 0.32f, 0.22f, 0.22f, 0.43f, 0.17f, 0.06f};

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
        return BaseFontStyle.Style.MiSansDemiBold;
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
        return 57;
    }
}
