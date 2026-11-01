package com.miui.miwallpaper.opengl.guass;

import com.miui.miwallpaper.opengl.ImageGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;

/* JADX INFO: loaded from: classes2.dex */
public class GuassAnimGLWallpaper extends AnimImageGLWallpaper {
    public int in_blurOffset;
    public int in_blurWeight;
    public int in_imageHeight;
    public int in_imageWidth;
    public int sampleCount;

    public GuassAnimGLWallpaper(ImageGLProgram imageGLProgram) {
        super(imageGLProgram);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper, com.miui.miwallpaper.opengl.ImageGLWallpaper
    public void setupUniforms() {
        super.setupUniforms();
        this.in_imageWidth = this.mProgram.getUniformHandle("in_imageWidth");
        this.in_imageHeight = this.mProgram.getUniformHandle("in_imageHeight");
        this.sampleCount = this.mProgram.getUniformHandle("sampleCount");
        this.in_blurOffset = this.mProgram.getUniformHandle("in_blurOffset");
        this.in_blurWeight = this.mProgram.getUniformHandle("in_blurWeight");
    }
}
