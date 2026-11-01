package com.miui.miwallpaper.opengl;

import com.miui.miwallpaper.opengl.moru.MoruGlassParam;

/* JADX INFO: loaded from: classes2.dex */
public class TextureConfig {
    public int TEXTURE_MAG_FILTER;
    public int TEXTURE_MIN_FILTER;
    public int TEXTURE_WRAP_S;
    public int TEXTURE_WRAP_T;

    public TextureConfig(int i, int i2, int i3, int i4) {
        this.TEXTURE_MIN_FILTER = i;
        this.TEXTURE_MAG_FILTER = i2;
        this.TEXTURE_WRAP_T = i3;
        this.TEXTURE_WRAP_S = i4;
    }

    public TextureConfig(int i, boolean z) {
        this.TEXTURE_MIN_FILTER = 9729;
        this.TEXTURE_MAG_FILTER = 9729;
        this.TEXTURE_WRAP_T = 10497;
        this.TEXTURE_WRAP_S = 10497;
        if (i == MoruGlassParam.PRISM.index) {
            this.TEXTURE_MAG_FILTER = 9985;
            this.TEXTURE_MIN_FILTER = 9985;
        }
        if (z) {
            this.TEXTURE_WRAP_S = 33071;
            this.TEXTURE_WRAP_T = 33071;
        }
    }
}
