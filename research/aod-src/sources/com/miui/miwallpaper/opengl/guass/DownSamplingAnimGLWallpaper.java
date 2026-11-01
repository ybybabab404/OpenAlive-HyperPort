package com.miui.miwallpaper.opengl.guass;

import com.miui.miwallpaper.opengl.ImageGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;

/* JADX INFO: loaded from: classes2.dex */
public class DownSamplingAnimGLWallpaper extends AnimImageGLWallpaper {
    public int uScale;

    public DownSamplingAnimGLWallpaper(ImageGLProgram imageGLProgram) {
        super(imageGLProgram);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper, com.miui.miwallpaper.opengl.ImageGLWallpaper
    public void setupUniforms() {
        super.setupUniforms();
        this.uScale = this.mProgram.getUniformHandle("uScale");
    }
}
