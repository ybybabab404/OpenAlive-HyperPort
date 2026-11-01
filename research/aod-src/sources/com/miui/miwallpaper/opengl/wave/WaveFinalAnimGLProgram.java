package com.miui.miwallpaper.opengl.wave;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES30;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;
import com.miui.wallpaperglassshader.jar.R;
import effect_engine.utils.FBO;

/* JADX INFO: loaded from: classes2.dex */
public class WaveFinalAnimGLProgram extends GlassAnimGLProgram {
    public WaveFinalAnimGLWallpaper mWaveFinalAnimGLWallpaper;

    public WaveFinalAnimGLProgram(Context context) {
        super(context);
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getFragmentShader() {
        return R.raw.wave_final_fs;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public AnimImageGLWallpaper getImageGLWallpaper() {
        WaveFinalAnimGLWallpaper waveFinalAnimGLWallpaper = new WaveFinalAnimGLWallpaper(this);
        this.mWaveFinalAnimGLWallpaper = waveFinalAnimGLWallpaper;
        return waveFinalAnimGLWallpaper;
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getVertexShader() {
        return R.raw.wave_final_vs;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void glProgramDrawEffectUniform(boolean z, int i, boolean z2) {
        GLES30.glViewport(0, 0, this.mScreenSize.width(), this.mScreenSize.height());
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceChanged(int i, int i2) {
        super.onSurfaceChanged(i, i2);
        FBO fbo = this.mResultFBO;
        if (fbo != null) {
            fbo.destroy();
        }
        this.mResultFBO = new FBO(i, i2, false);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceCreated(Bitmap bitmap) {
        super.onSurfaceCreated(bitmap);
        FBO fbo = this.mResultFBO;
        if (fbo != null) {
            fbo.destroy();
        }
        this.mResultFBO = new FBO(this.mScreenSize.width(), this.mScreenSize.height(), false);
    }
}
