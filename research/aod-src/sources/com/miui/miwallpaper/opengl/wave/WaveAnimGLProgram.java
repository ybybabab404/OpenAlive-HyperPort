package com.miui.miwallpaper.opengl.wave;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;
import com.miui.miwallpaper.opengl.TextureConfig;

/* JADX INFO: loaded from: classes2.dex */
public class WaveAnimGLProgram extends GlassAnimGLProgram {
    public final WaveFinalAnimGLProgram mWaveFinalProgram;
    public final WaveNoiseAnimGLProgram mWaveNoiseProgram;
    public final WaveRefractAnimGLProgram mWaveRefractProgram;

    public WaveAnimGLProgram(Context context) {
        super(context);
        this.mWaveNoiseProgram = new WaveNoiseAnimGLProgram(context);
        this.mWaveFinalProgram = new WaveFinalAnimGLProgram(context);
        this.mWaveRefractProgram = new WaveRefractAnimGLProgram(context);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void commonDraw(boolean z, int i, boolean z2) {
        WaveNoiseAnimGLProgram waveNoiseAnimGLProgram = this.mWaveNoiseProgram;
        if (waveNoiseAnimGLProgram == null || this.mWaveFinalProgram == null || this.mWaveRefractProgram == null) {
            return;
        }
        waveNoiseAnimGLProgram.onDrawFrameToFBO();
        this.mWaveRefractProgram.setWaveNoiseTexture(this.mWaveNoiseProgram.getFBOTexture());
        this.mWaveRefractProgram.commonDraw(true, i, z2);
        this.mWaveFinalProgram.commonDraw(z, this.mWaveRefractProgram.getFBOTexture(), true);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceChanged(int i, int i2) {
        WaveFinalAnimGLProgram waveFinalAnimGLProgram;
        if (this.mWaveNoiseProgram == null || (waveFinalAnimGLProgram = this.mWaveFinalProgram) == null || this.mWaveRefractProgram == null) {
            return;
        }
        waveFinalAnimGLProgram.onSurfaceChanged(i, i2);
        this.mWaveNoiseProgram.onSurfaceChanged(i, i2);
        this.mWaveRefractProgram.onSurfaceChanged(i, i2);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceCreated(Bitmap bitmap) {
        WaveFinalAnimGLProgram waveFinalAnimGLProgram;
        this.mBitmap = bitmap;
        if (this.mWaveNoiseProgram == null || (waveFinalAnimGLProgram = this.mWaveFinalProgram) == null || this.mWaveRefractProgram == null) {
            return;
        }
        waveFinalAnimGLProgram.onSurfaceCreated(bitmap);
        this.mWaveNoiseProgram.onSurfaceCreated(bitmap);
        this.mWaveRefractProgram.onSurfaceCreated(bitmap);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void setScale(float f, float f2) {
        WaveFinalAnimGLProgram waveFinalAnimGLProgram = this.mWaveFinalProgram;
        if (waveFinalAnimGLProgram != null) {
            waveFinalAnimGLProgram.setScale(f, f2);
        }
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void setTranslate(float f, float f2) {
        WaveFinalAnimGLProgram waveFinalAnimGLProgram = this.mWaveFinalProgram;
        if (waveFinalAnimGLProgram != null) {
            waveFinalAnimGLProgram.setTranslate(f, f2);
        }
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void setup(Bitmap bitmap, TextureConfig textureConfig) {
        WaveRefractAnimGLProgram waveRefractAnimGLProgram;
        if (this.mWaveNoiseProgram == null || this.mWaveFinalProgram == null || (waveRefractAnimGLProgram = this.mWaveRefractProgram) == null) {
            return;
        }
        waveRefractAnimGLProgram.setup(bitmap, textureConfig);
        this.mWaveNoiseProgram.setup(bitmap, textureConfig);
        this.mWaveFinalProgram.setup(bitmap, textureConfig);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void updateMVPMatrix(int i, int i2, Rect rect) {
        WaveRefractAnimGLProgram waveRefractAnimGLProgram;
        if (this.mWaveNoiseProgram == null || this.mWaveFinalProgram == null || (waveRefractAnimGLProgram = this.mWaveRefractProgram) == null) {
            return;
        }
        waveRefractAnimGLProgram.setIdentityMVPMatrix();
        this.mWaveNoiseProgram.setIdentityMVPMatrix();
        this.mWaveFinalProgram.updateMVPMatrix(i, i2, rect);
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public void useGLProgram() {
        WaveFinalAnimGLProgram waveFinalAnimGLProgram;
        if (this.mWaveNoiseProgram == null || (waveFinalAnimGLProgram = this.mWaveFinalProgram) == null || this.mWaveRefractProgram == null) {
            return;
        }
        waveFinalAnimGLProgram.useGLProgram();
        this.mWaveRefractProgram.useGLProgram();
        this.mWaveNoiseProgram.useGLProgram();
    }
}
