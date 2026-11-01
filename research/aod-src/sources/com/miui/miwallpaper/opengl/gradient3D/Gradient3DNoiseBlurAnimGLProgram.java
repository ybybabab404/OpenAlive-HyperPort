package com.miui.miwallpaper.opengl.gradient3D;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES30;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;
import com.miui.wallpaperglassshader.jar.R;

/* JADX INFO: loaded from: classes2.dex */
public class Gradient3DNoiseBlurAnimGLProgram extends GlassAnimGLProgram {
    public Gradient3DNoiseBlurAnimGLWallpaper mGradient3DNoiseBlurAnimGLWallpaper;
    public float mPattern;

    public Gradient3DNoiseBlurAnimGLProgram(Context context) {
        super(context);
        this.mPattern = (float) (Math.random() * 10.0d);
        this.mProgress = 1.0f;
    }

    public void animateNoisePattern(float f) {
        this.mPattern = f;
    }

    public void animateNoiseProgress(float f) {
        this.mProgress = f;
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getFragmentShader() {
        return R.raw.gradient3d;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public AnimImageGLWallpaper getImageGLWallpaper() {
        Gradient3DNoiseBlurAnimGLWallpaper gradient3DNoiseBlurAnimGLWallpaper = new Gradient3DNoiseBlurAnimGLWallpaper(this);
        this.mGradient3DNoiseBlurAnimGLWallpaper = gradient3DNoiseBlurAnimGLWallpaper;
        return gradient3DNoiseBlurAnimGLWallpaper;
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getVertexShader() {
        return R.raw.vertex_shader;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void glProgramDrawEffectUniform(boolean z, int i, boolean z2) {
        super.glProgramDrawEffectUniform(z, i, z2);
        GLES30.glUniform1f(this.mGradient3DNoiseBlurAnimGLWallpaper.uProgress, this.mProgress);
        GLES30.glUniform1f(this.mGradient3DNoiseBlurAnimGLWallpaper.uPattern, this.mPattern);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceChanged(int i, int i2) {
        super.onSurfaceChanged(i, i2);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceCreated(Bitmap bitmap) {
        super.onSurfaceCreated(bitmap);
        GLES30.glUniform1f(this.mGradient3DNoiseBlurAnimGLWallpaper.uOctave, 7.4f);
        GLES30.glUniform1f(this.mGradient3DNoiseBlurAnimGLWallpaper.uFrequency, 0.9f);
    }
}
