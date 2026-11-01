package com.miui.miwallpaper.opengl.wave;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES30;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;
import com.miui.wallpaperglassshader.jar.R;
import effect_engine.utils.FBO;

/* JADX INFO: loaded from: classes2.dex */
public class WaveNoiseAnimGLProgram extends GlassAnimGLProgram {
    public static int mNoiseFboDownScale = 2;
    public WaveNoiseAnimGLWallpaper mWaveNoiseGLWallpaper;

    public WaveNoiseAnimGLProgram(Context context) {
        super(context);
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getFragmentShader() {
        return R.raw.wave_noise;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public AnimImageGLWallpaper getImageGLWallpaper() {
        WaveNoiseAnimGLWallpaper waveNoiseAnimGLWallpaper = new WaveNoiseAnimGLWallpaper(this);
        this.mWaveNoiseGLWallpaper = waveNoiseAnimGLWallpaper;
        return waveNoiseAnimGLWallpaper;
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getVertexShader() {
        return R.raw.vertex_shader;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void glProgramDrawEffectUniform(boolean z, int i, boolean z2) {
        super.glProgramDrawEffectUniform(z, i, z2);
        GLES30.glUniform2f(this.mWaveNoiseGLWallpaper.uResolution, this.mScreenSize.width() / mNoiseFboDownScale, this.mScreenSize.height() / mNoiseFboDownScale);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceChanged(int i, int i2) {
        super.onSurfaceChanged(i, i2);
        FBO fbo = this.mResultFBO;
        if (fbo != null) {
            fbo.destroy();
        }
        this.mResultFBO = new FBO(this.mScreenSize.width() / mNoiseFboDownScale, this.mScreenSize.height() / mNoiseFboDownScale, 33326);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceCreated(Bitmap bitmap) {
        super.onSurfaceCreated(bitmap);
        FBO fbo = this.mResultFBO;
        if (fbo != null) {
            fbo.destroy();
        }
        this.mResultFBO = new FBO(this.mScreenSize.width() / mNoiseFboDownScale, this.mScreenSize.height() / mNoiseFboDownScale, 33326);
        GLES30.glUniform1f(this.mWaveNoiseGLWallpaper.uLineArtifactFixer, 0.535f);
        double radians = (float) Math.toRadians(-22.920000076293945d);
        GLES30.glUniform2f(this.mWaveNoiseGLWallpaper.uWaveRotation, (float) Math.sin(radians), (float) Math.cos(radians));
        GLES30.glUniform2f(this.mWaveNoiseGLWallpaper.uWaveNoiseOffset, -20.2f, -15.2f);
        GLES30.glUniform1f(this.mWaveNoiseGLWallpaper.uWaveNoiseZ, -0.1f);
        GLES30.glUniform1f(this.mWaveNoiseGLWallpaper.unStartFrequency, 0.35f);
        GLES30.glUniform1f(this.mWaveNoiseGLWallpaper.unFlow, 84.0f);
        GLES30.glUniform1f(this.mWaveNoiseGLWallpaper.unLacunarity, 0.77f);
        GLES30.glUniform1f(this.mWaveNoiseGLWallpaper.unFlowRate, 10.5f);
        GLES30.glUniform1f(this.mWaveNoiseGLWallpaper.unGain, 2.66f);
        GLES30.glUniform1i(this.mWaveNoiseGLWallpaper.unOctaves, 3);
        GLES30.glUniform1f(this.mWaveNoiseGLWallpaper.uWaveDistort, 0.19f);
        GLES30.glUniform1f(this.mWaveNoiseGLWallpaper.uWaveDistortDens, 2.4f);
    }
}
