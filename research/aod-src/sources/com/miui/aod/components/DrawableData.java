package com.miui.aod.components;

/* JADX INFO: loaded from: classes.dex */
public class DrawableData {
    public final int mAccessibilityDescriptionResId;
    public final String mName;
    public final int mResId;

    public DrawableData(int i) {
        this(i, String.valueOf(i), 0);
    }

    public DrawableData(int i, String str) {
        this(i, str, 0);
    }

    public DrawableData(int i, String str, int i2) {
        this.mResId = i;
        this.mName = str;
        this.mAccessibilityDescriptionResId = i2;
    }
}
