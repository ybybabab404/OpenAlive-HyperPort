package com.miui.miwallpaper.opengl.wave;

import com.miui.miwallpaper.opengl.ImageGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;

/* JADX INFO: loaded from: classes2.dex */
public class WaveRefractAnimGLWallpaper extends AnimImageGLWallpaper {
    public int uFlatten;
    public int uInnerProgress;
    public int uLightRotation;
    public int uLightStrength;
    public int uNoiseTex;
    public int uReflectStrength;
    public int uRefractDist;
    public int uRefractHOffset;

    public WaveRefractAnimGLWallpaper(ImageGLProgram imageGLProgram) {
        super(imageGLProgram);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper, com.miui.miwallpaper.opengl.ImageGLWallpaper
    public void setupUniforms() {
        super.setupUniforms();
        this.uInnerProgress = this.mProgram.getUniformHandle("uInnerProgress");
        this.uReflectStrength = this.mProgram.getUniformHandle("uReflectStrength");
        this.uRefractDist = this.mProgram.getUniformHandle("uRefractDist");
        this.uRefractHOffset = this.mProgram.getUniformHandle("uRefractHOffset");
        this.uLightStrength = this.mProgram.getUniformHandle("uLightStrength");
        this.uLightRotation = this.mProgram.getUniformHandle("uLightRotation");
        this.uNoiseTex = this.mProgram.getUniformHandle("uNoiseTex");
        this.uFlatten = this.mProgram.getUniformHandle("uFlatten");
    }
}
