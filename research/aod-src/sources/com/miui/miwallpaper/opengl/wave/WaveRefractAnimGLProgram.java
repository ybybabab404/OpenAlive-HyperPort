package com.miui.miwallpaper.opengl.wave;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES30;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;
import com.miui.wallpaperglassshader.jar.R;
import effect_engine.utils.FBO;

/* JADX INFO: loaded from: classes2.dex */
public class WaveRefractAnimGLProgram extends GlassAnimGLProgram {
    public int mFboDownScale;
    public int mWaveNoiseTexture;
    public WaveRefractAnimGLWallpaper mWaveRefrectGLWallpaper;

    public WaveRefractAnimGLProgram(Context context) {
        super(context);
        this.mFboDownScale = 2;
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getFragmentShader() {
        return R.raw.wave_frag;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public AnimImageGLWallpaper getImageGLWallpaper() {
        WaveRefractAnimGLWallpaper waveRefractAnimGLWallpaper = new WaveRefractAnimGLWallpaper(this);
        this.mWaveRefrectGLWallpaper = waveRefractAnimGLWallpaper;
        return waveRefractAnimGLWallpaper;
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getVertexShader() {
        return R.raw.vertex_shader;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void glProgramDrawEffectUniform(boolean z, int i, boolean z2) {
        super.glProgramDrawEffectUniform(z, i, z2);
        GLES30.glViewport(0, 0, this.mScreenSize.width() / this.mFboDownScale, this.mScreenSize.height() / this.mFboDownScale);
        GLES30.glUniform2f(this.mWaveRefrectGLWallpaper.uResolution, this.mScreenSize.width() / this.mFboDownScale, this.mScreenSize.height() / this.mFboDownScale);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceCreated(Bitmap bitmap) {
        super.onSurfaceCreated(bitmap);
        FBO fbo = this.mResultFBO;
        if (fbo != null) {
            fbo.destroy();
        }
        this.mResultFBO = new FBO(this.mScreenSize.width() / this.mFboDownScale, this.mScreenSize.height() / this.mFboDownScale, true);
        float radians = (float) Math.toRadians(-180.0d);
        float radians2 = (float) Math.toRadians(78.0999984741211d);
        GLES30.glUniform1f(this.mWaveRefrectGLWallpaper.uFlatten, 0.93f);
        GLES30.glUniform1f(this.mWaveRefrectGLWallpaper.uInnerProgress, this.mProgress);
        GLES30.glUniform1f(this.mWaveRefrectGLWallpaper.uReflectStrength, 0.15f);
        GLES30.glUniform1f(this.mWaveRefrectGLWallpaper.uRefractDist, 17.2f);
        GLES30.glUniform1f(this.mWaveRefrectGLWallpaper.uRefractHOffset, 0.79f);
        GLES30.glUniform1f(this.mWaveRefrectGLWallpaper.uLightStrength, 0.05f);
        double d = radians2;
        GLES30.glUniform3f(this.mWaveRefrectGLWallpaper.uLightRotation, (float) Math.sin(d), (float) Math.tan(radians), (float) Math.cos(d));
    }

    public void setWaveNoiseTexture(int i) {
        this.mWaveNoiseTexture = i;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void useTexture(int i, boolean z) {
        super.useTexture(i, z);
        GLES30.glActiveTexture(33985);
        GLES30.glBindTexture(3553, this.mWaveNoiseTexture);
        GLES30.glUniform1i(this.mWaveRefrectGLWallpaper.uNoiseTex, 1);
    }
}
