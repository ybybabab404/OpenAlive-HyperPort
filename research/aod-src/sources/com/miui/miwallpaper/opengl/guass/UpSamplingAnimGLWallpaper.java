package com.miui.miwallpaper.opengl.guass;

import com.miui.miwallpaper.opengl.ImageGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;

/* JADX INFO: loaded from: classes2.dex */
public class UpSamplingAnimGLWallpaper extends AnimImageGLWallpaper {
    public int isNeedAddSaturn;
    public int uNoiseCoeff;
    public int uShadowRatio;

    public UpSamplingAnimGLWallpaper(ImageGLProgram imageGLProgram) {
        super(imageGLProgram);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper, com.miui.miwallpaper.opengl.ImageGLWallpaper
    public void setupUniforms() {
        super.setupUniforms();
        this.uNoiseCoeff = this.mProgram.getUniformHandle("noise_coeff");
        this.isNeedAddSaturn = this.mProgram.getUniformHandle("isNeedAddSaturn");
        this.uShadowRatio = this.mProgram.getUniformHandle("uShadowRatio");
    }
}
