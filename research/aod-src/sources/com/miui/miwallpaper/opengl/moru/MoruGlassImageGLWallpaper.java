package com.miui.miwallpaper.opengl.moru;

import com.miui.miwallpaper.opengl.ImageGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;

/* JADX INFO: loaded from: classes2.dex */
public class MoruGlassImageGLWallpaper extends AnimImageGLWallpaper {
    public int normalTex;
    public int uAspIORLightShadow;
    public int uDarkness;
    public int uDisplacement;
    public int uLight;
    public int uNormalMask;
    public int uNormalOffsetXY;
    public int uNormalScaleXYThickDis;
    public int uProgress;
    public int uSaturnContrast;
    public int uSaturnProgress;
    public int uSurfaceRatio;

    public MoruGlassImageGLWallpaper(ImageGLProgram imageGLProgram) {
        super(imageGLProgram);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper, com.miui.miwallpaper.opengl.ImageGLWallpaper
    public void setupUniforms() {
        super.setupUniforms();
        this.uDisplacement = this.mProgram.getUniformHandle("uDisplacement");
        this.uLight = this.mProgram.getUniformHandle("uLight");
        this.uNormalScaleXYThickDis = this.mProgram.getUniformHandle("uNormalScaleXYThickDis");
        this.uNormalOffsetXY = this.mProgram.getUniformHandle("uNormalOffsetXY");
        this.uAspIORLightShadow = this.mProgram.getUniformHandle("uAspIORLightShadow");
        this.normalTex = this.mProgram.getUniformHandle("normalTex");
        this.uDarkness = this.mProgram.getUniformHandle("uDarkness");
        this.uSurfaceRatio = this.mProgram.getUniformHandle("uSurfaceRatio");
        this.uSaturnContrast = this.mProgram.getUniformHandle("uSaturnContrast");
        this.uProgress = this.mProgram.getUniformHandle("uProgress");
        this.uNormalMask = this.mProgram.getUniformHandle("normalMaskTex");
        this.uSaturnProgress = this.mProgram.getUniformHandle("uSaturnProgress");
    }
}
