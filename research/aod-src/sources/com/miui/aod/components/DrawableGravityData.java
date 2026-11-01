package com.miui.aod.components;

/* JADX INFO: loaded from: classes.dex */
public class DrawableGravityData extends DrawableData {
    public int gravity;
    public final String mAlignNameForAnalytic;
    public final int mDescriptionId;

    public DrawableGravityData(int i, int i2, String str, int i3) {
        super(i, String.valueOf(i2));
        this.gravity = i2;
        this.mAlignNameForAnalytic = str;
        this.mDescriptionId = i3;
    }
}
