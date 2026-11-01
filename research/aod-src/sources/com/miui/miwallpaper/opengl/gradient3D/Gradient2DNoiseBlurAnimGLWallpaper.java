package com.miui.miwallpaper.opengl.gradient3D;

import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;

/* JADX INFO: loaded from: classes2.dex */
public class Gradient2DNoiseBlurAnimGLWallpaper extends AnimImageGLWallpaper {
    public int uColorSwatch1;
    public int uColorSwatch2;
    public int uColorSwatch3;
    public int uColorSwatch4;
    public int uFrequency;
    public int uOctave;
    public int uPattern;
    public int uProgress;

    public Gradient2DNoiseBlurAnimGLWallpaper(Gradient2DNoiseBlurAnimGLProgram gradient2DNoiseBlurAnimGLProgram) {
        super(gradient2DNoiseBlurAnimGLProgram);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper, com.miui.miwallpaper.opengl.ImageGLWallpaper
    public void setupUniforms() {
        super.setupUniforms();
        this.uProgress = this.mProgram.getUniformHandle("uProgress");
        this.uPattern = this.mProgram.getUniformHandle("uPattern");
        this.uOctave = this.mProgram.getUniformHandle("uOctave");
        this.uFrequency = this.mProgram.getUniformHandle("uFrequency");
        this.uColorSwatch1 = this.mProgram.getUniformHandle("uColorSwatch1");
        this.uColorSwatch2 = this.mProgram.getUniformHandle("uColorSwatch2");
        this.uColorSwatch3 = this.mProgram.getUniformHandle("uColorSwatch3");
        this.uColorSwatch4 = this.mProgram.getUniformHandle("uColorSwatch4");
    }
}
