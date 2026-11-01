package com.miui.miwallpaper.opengl.ordinary;

import com.miui.miwallpaper.opengl.ImageGLProgram;
import com.miui.miwallpaper.opengl.ImageGLWallpaper;

/* JADX INFO: loaded from: classes2.dex */
public class AnimImageGLWallpaper extends ImageGLWallpaper {
    public int uDarkMode;
    public int uDarken;
    public int uIsNeedReverse;
    public int uMVPMatrix;
    public int uResolution;
    public int uReveal;
    public int uScaleCenter;
    public int uScaleOffset;

    public AnimImageGLWallpaper(ImageGLProgram imageGLProgram) {
        super(imageGLProgram);
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLWallpaper
    public void setupUniforms() {
        super.setupUniforms();
        this.uReveal = this.mProgram.getUniformHandle("uReveal");
        this.uDarken = this.mProgram.getUniformHandle("uDarken");
        this.uDarkMode = this.mProgram.getUniformHandle("uDarkMode");
        this.uMVPMatrix = this.mProgram.getUniformHandle("uMVPMatrix");
        this.uScaleOffset = this.mProgram.getUniformHandle("uScaleOffset");
        this.uScaleCenter = this.mProgram.getUniformHandle("uScaleCenter");
        this.uResolution = this.mProgram.getUniformHandle("uResolution");
        this.uIsNeedReverse = this.mProgram.getUniformHandle("uIsNeedReverse");
    }
}
