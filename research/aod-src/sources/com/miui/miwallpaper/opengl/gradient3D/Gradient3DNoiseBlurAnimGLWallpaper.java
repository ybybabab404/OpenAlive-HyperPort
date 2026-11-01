package com.miui.miwallpaper.opengl.gradient3D;

import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;

/* JADX INFO: loaded from: classes2.dex */
public class Gradient3DNoiseBlurAnimGLWallpaper extends AnimImageGLWallpaper {
    public int uFrequency;
    public int uOctave;
    public int uPattern;
    public int uProgress;

    public Gradient3DNoiseBlurAnimGLWallpaper(Gradient3DNoiseBlurAnimGLProgram gradient3DNoiseBlurAnimGLProgram) {
        super(gradient3DNoiseBlurAnimGLProgram);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper, com.miui.miwallpaper.opengl.ImageGLWallpaper
    public void setupUniforms() {
        super.setupUniforms();
        this.uProgress = this.mProgram.getUniformHandle("uProgress");
        this.uPattern = this.mProgram.getUniformHandle("uPattern");
        this.uOctave = this.mProgram.getUniformHandle("uOctave");
        this.uFrequency = this.mProgram.getUniformHandle("uFrequency");
    }
}
