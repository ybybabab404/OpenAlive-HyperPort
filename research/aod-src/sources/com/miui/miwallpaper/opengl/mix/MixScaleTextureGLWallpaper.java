package com.miui.miwallpaper.opengl.mix;

import com.miui.miwallpaper.opengl.ImageGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;

/* JADX INFO: loaded from: classes2.dex */
public class MixScaleTextureGLWallpaper extends AnimImageGLWallpaper {
    public int uDesktopUVScaleX;
    public int uDesktopUVScaleY;
    public int uDstTex;
    public int uIsNeedReverseUV;
    public int uKeyguardUVScaleX;
    public int uKeyguardUVScaleY;
    public int uMixFactor;
    public int uScale;

    public MixScaleTextureGLWallpaper(ImageGLProgram imageGLProgram) {
        super(imageGLProgram);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper, com.miui.miwallpaper.opengl.ImageGLWallpaper
    public void setupUniforms() {
        super.setupUniforms();
        this.uMixFactor = this.mProgram.getUniformHandle("uMixFactor");
        this.uScale = this.mProgram.getUniformHandle("uScale");
        this.uDstTex = this.mProgram.getUniformHandle("uDstTex");
        this.uIsNeedReverseUV = this.mProgram.getUniformHandle("isNeedReverseUV");
        this.uKeyguardUVScaleX = this.mProgram.getUniformHandle("uKeyguardUVScaleX");
        this.uKeyguardUVScaleY = this.mProgram.getUniformHandle("uKeyguardUVScaleY");
        this.uDesktopUVScaleX = this.mProgram.getUniformHandle("uDesktopUVScaleX");
        this.uDesktopUVScaleY = this.mProgram.getUniformHandle("uDesktopUVScaleY");
    }
}
