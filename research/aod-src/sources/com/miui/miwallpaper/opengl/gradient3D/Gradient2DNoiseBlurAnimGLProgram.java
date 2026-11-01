package com.miui.miwallpaper.opengl.gradient3D;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES30;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;
import com.miui.wallpaperglassshader.jar.R;
import effect_engine.utils.FBO;
import miuix.mipalette.MiPalette;

/* JADX INFO: loaded from: classes2.dex */
public class Gradient2DNoiseBlurAnimGLProgram extends GlassAnimGLProgram {
    public float mColorSwatchFactor;
    public Gradient2DNoiseBlurAnimGLWallpaper mGradient2DNoiseBlurAnimGLWallpaper;
    public float mPattern;

    public Gradient2DNoiseBlurAnimGLProgram(Context context) {
        super(context);
        this.mPattern = (float) (Math.random() * 10.0d);
        this.mProgress = 1.0f;
    }

    public void animateNoiseColorSwatch(float f) {
        this.mColorSwatchFactor = f;
    }

    public void animateNoisePattern(float f) {
        this.mPattern = f;
    }

    public void animateNoiseProgress(float f) {
        this.mProgress = f;
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getFragmentShader() {
        return R.raw.gradient2d;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public AnimImageGLWallpaper getImageGLWallpaper() {
        Gradient2DNoiseBlurAnimGLWallpaper gradient2DNoiseBlurAnimGLWallpaper = new Gradient2DNoiseBlurAnimGLWallpaper(this);
        this.mGradient2DNoiseBlurAnimGLWallpaper = gradient2DNoiseBlurAnimGLWallpaper;
        return gradient2DNoiseBlurAnimGLWallpaper;
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getVertexShader() {
        return R.raw.vertex_shader;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void glProgramDrawEffectUniform(boolean z, int i, boolean z2) {
        super.glProgramDrawEffectUniform(z, i, z2);
        GLES30.glUniform1f(this.mGradient2DNoiseBlurAnimGLWallpaper.uProgress, this.mProgress);
        GLES30.glUniform1f(this.mGradient2DNoiseBlurAnimGLWallpaper.uPattern, this.mPattern);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceChanged(int i, int i2) {
        super.onSurfaceChanged(i, i2);
        FBO fbo = this.mResultFBO;
        if (fbo != null) {
            fbo.destroy();
        }
        this.mResultFBO = new FBO(this.mScreenSize.width(), this.mScreenSize.height(), true);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceCreated(Bitmap bitmap) {
        super.onSurfaceCreated(bitmap);
        FBO fbo = this.mResultFBO;
        if (fbo != null) {
            fbo.destroy();
        }
        this.mResultFBO = new FBO(this.mScreenSize.width(), this.mScreenSize.height(), true);
        GLES30.glUniform1f(this.mGradient2DNoiseBlurAnimGLWallpaper.uOctave, 2.7f);
        GLES30.glUniform1f(this.mGradient2DNoiseBlurAnimGLWallpaper.uFrequency, 0.7f);
        GLES30.glUniform3f(this.mGradient2DNoiseBlurAnimGLWallpaper.uColorSwatch1, 0.012f, 0.471f, 0.651f);
        GLES30.glUniform3f(this.mGradient2DNoiseBlurAnimGLWallpaper.uColorSwatch2, 0.349f, 0.71f, 0.851f);
        GLES30.glUniform3f(this.mGradient2DNoiseBlurAnimGLWallpaper.uColorSwatch3, 0.749f, 0.424f, 0.231f);
        GLES30.glUniform3f(this.mGradient2DNoiseBlurAnimGLWallpaper.uColorSwatch4, 0.051f, 0.051f, 0.051f);
        GLES30.glUniform3f(this.mGradient2DNoiseBlurAnimGLWallpaper.uColorSwatch4, 0.25f, 0.14f, 0.08f);
        MiPalette.getMainColorHSV(bitmap);
    }
}
