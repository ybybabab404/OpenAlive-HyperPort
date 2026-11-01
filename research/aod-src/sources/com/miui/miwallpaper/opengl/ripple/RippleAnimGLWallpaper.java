package com.miui.miwallpaper.opengl.ripple;

import com.miui.miwallpaper.opengl.ImageGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;

/* JADX INFO: loaded from: classes2.dex */
public class RippleAnimGLWallpaper extends AnimImageGLWallpaper {
    public int uDepthTex;
    public int uFbmTex;
    public int uGlassScale_uInnerProgress_uTime;
    public int uLodThres_Fac_Shadow_Highlight;
    public int uNormalTex;
    public int uRipplePosRadius1_uIOR;
    public int uRipplePosRadius2_uNormalScale;
    public int uRipplePosRadius3;
    public int uSmoothstepTex;

    public RippleAnimGLWallpaper(ImageGLProgram imageGLProgram) {
        super(imageGLProgram);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper, com.miui.miwallpaper.opengl.ImageGLWallpaper
    public void setupUniforms() {
        super.setupUniforms();
        this.uDepthTex = this.mProgram.getUniformHandle("uDepthTex");
        this.uNormalTex = this.mProgram.getUniformHandle("uNormalTex");
        this.uFbmTex = this.mProgram.getUniformHandle("uFbmTex");
        this.uSmoothstepTex = this.mProgram.getUniformHandle("uSmoothstepTex");
        this.uRipplePosRadius1_uIOR = this.mProgram.getUniformHandle("uRipplePosRadius1_uIOR");
        this.uRipplePosRadius2_uNormalScale = this.mProgram.getUniformHandle("uRipplePosRadius2_uNormalScale");
        this.uRipplePosRadius3 = this.mProgram.getUniformHandle("uRipplePosRadius3");
        this.uLodThres_Fac_Shadow_Highlight = this.mProgram.getUniformHandle("uLodThres_Fac_Shadow_Highlight");
        this.uGlassScale_uInnerProgress_uTime = this.mProgram.getUniformHandle("uGlassScale_uInnerProgress_uTime");
    }
}
