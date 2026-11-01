package com.miui.miwallpaper.opengl.wave;

import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;

/* JADX INFO: loaded from: classes2.dex */
public class WaveNoiseAnimGLWallpaper extends AnimImageGLWallpaper {
    public int uLineArtifactFixer;
    public int uWaveDistort;
    public int uWaveDistortDens;
    public int uWaveNoiseOffset;
    public int uWaveNoiseZ;
    public int uWaveRotation;
    public int unFlow;
    public int unFlowRate;
    public int unGain;
    public int unLacunarity;
    public int unOctaves;
    public int unStartFrequency;

    public WaveNoiseAnimGLWallpaper(WaveNoiseAnimGLProgram waveNoiseAnimGLProgram) {
        super(waveNoiseAnimGLProgram);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper, com.miui.miwallpaper.opengl.ImageGLWallpaper
    public void setupUniforms() {
        super.setupUniforms();
        this.uLineArtifactFixer = this.mProgram.getUniformHandle("uLineArtifactFixer");
        this.uWaveRotation = this.mProgram.getUniformHandle("uWaveRotation");
        this.uWaveNoiseOffset = this.mProgram.getUniformHandle("uWaveNoiseOffset");
        this.uWaveNoiseZ = this.mProgram.getUniformHandle("uWaveNoiseZ");
        this.unStartFrequency = this.mProgram.getUniformHandle("unStartFrequency");
        this.unFlow = this.mProgram.getUniformHandle("unFlow");
        this.unLacunarity = this.mProgram.getUniformHandle("unLacunarity");
        this.unFlowRate = this.mProgram.getUniformHandle("unFlowRate");
        this.unGain = this.mProgram.getUniformHandle("unGain");
        this.unOctaves = this.mProgram.getUniformHandle("unOctaves");
        this.uWaveDistort = this.mProgram.getUniformHandle("uWaveDistort");
        this.uWaveDistortDens = this.mProgram.getUniformHandle("uWaveDistortDens");
    }
}
