package com.miui.miwallpaper.opengl.moru;

import com.miui.wallpaperglassshader.jar.R;

/* JADX INFO: loaded from: classes2.dex */
public enum MoruGlassParam {
    PINSTRIPE(0, 10, 0.15f, 0.0f, 1.08f, 1.0f, 0.3f, 0.36f, 0.0f, 0.0f, 0.68f, 0.4f, 1.0f, R.drawable.moru_2, R.drawable.depth_light_shadow_2),
    THICK_STRIPE(1, 25, 0.31f, 0.0f, 1.28f, 1.0f, 0.36f, 0.58f, 0.1f, 0.0f, 0.58f, 0.65f, 0.36f, R.drawable.moru_3, R.drawable.depth_light_shadow_3),
    PRISM(2, 8, 0.14f, 0.0f, 1.08f, 1.0f, 0.5f, 0.5f, 0.0f, 0.04f, 0.5f, 0.85f, 1.0f, R.drawable.moru_4, R.drawable.depth_light_shadow_4),
    THIN_LINE(3, 25, 0.44f, 0.158f, 1.14f, 1.0f, 0.53f, 0.56f, 0.0f, 0.0f, 0.8f, 0.3f, 1.0f, R.drawable.moru_5, R.drawable.depth_light_shadow_5),
    CORRUGATION(4, 20, 0.24f, 0.0f, 1.14f, 1.0f, 0.39f, 0.37f, 0.06f, 0.02f, 0.6f, 1.0f, 1.0f, R.drawable.moru_6, R.drawable.depth_light_shadow_6);

    public float ASP;
    public final float IOR;
    public final int blurRadius;
    public final float darkness;
    public final int depthLightShadowTex;
    public final float displacement;
    public final float distance;
    public final int index;
    public final float lightness;
    public final int normalTex;
    public final float offsetX;
    public final float scaleX;
    public final float scaleY;
    public final float shadowness;
    public final float thickness;
    public final float uNormalScaleX;
    public final float uNormalScaleY = 1.0f;
    public final float offsetY = 0.0f;

    MoruGlassParam(int i, int i2, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, int i3, int i4) {
        this.index = i;
        this.blurRadius = i2;
        this.uNormalScaleX = f;
        this.offsetX = f2;
        this.scaleX = f3;
        this.scaleY = f4;
        this.thickness = f5;
        this.distance = f6;
        this.normalTex = i3;
        this.depthLightShadowTex = i4;
        this.displacement = f7;
        this.darkness = f8;
        this.IOR = f9;
        this.lightness = f10;
        this.shadowness = f11;
    }

    public static MoruGlassParam getValueByIndex(int i) {
        if (i == 0) {
            return PINSTRIPE;
        }
        if (i == 1) {
            return THICK_STRIPE;
        }
        if (i != 3) {
            return i != 4 ? PRISM : CORRUGATION;
        }
        return THIN_LINE;
    }
}
