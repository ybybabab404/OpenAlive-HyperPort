package com.miui.miwallpaper.opengl.guass;

import android.content.Context;
import android.opengl.GLES30;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;
import com.miui.wallpaperglassshader.jar.R;
import effect_engine.utils.FBO;

/* JADX INFO: loaded from: classes2.dex */
public class GuassAnimGLProgram extends GlassAnimGLProgram {
    public FBO mFBO1;
    public GuassAnimGLWallpaper mGuassAnimGLWallpaper;
    public float[] mOffset;
    public float[] mWeight;
    public volatile int sampleRadius;
    public float[] tempOffset;

    public GuassAnimGLProgram(Context context) {
        super(context);
        this.mWeight = new float[8];
        this.mOffset = new float[8];
        this.sampleRadius = 0;
        this.tempOffset = new float[10];
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void commonDraw(boolean z, int i, boolean z2) {
        FBO fbo;
        FBO fbo2;
        useCreateGLProgram();
        GLES30.glClear(16384);
        GLES30.glUniformMatrix4fv(this.mGuassAnimGLWallpaper.uMVPMatrix, 1, false, this.mMVPMatrix, 0);
        FBO fbo3 = this.mFBO1;
        if (fbo3 != null) {
            GLES30.glUniform1f(this.mGuassAnimGLWallpaper.in_imageWidth, fbo3.getWidth());
            GLES30.glUniform1f(this.mGuassAnimGLWallpaper.in_imageHeight, this.mFBO1.getHeight());
        } else {
            GLES30.glUniform1f(this.mGuassAnimGLWallpaper.in_imageWidth, this.mScreenSize.width());
            GLES30.glUniform1f(this.mGuassAnimGLWallpaper.in_imageHeight, this.mScreenSize.height());
        }
        GLES30.glUniform1i(this.mGuassAnimGLWallpaper.sampleCount, this.sampleRadius);
        int i2 = this.mGuassAnimGLWallpaper.in_blurOffset;
        float[] fArr = this.tempOffset;
        GLES30.glUniform1fv(i2, fArr.length, fArr, 0);
        int i3 = this.mGuassAnimGLWallpaper.in_blurWeight;
        float[] fArr2 = this.mWeight;
        GLES30.glUniform1fv(i3, fArr2.length, fArr2, 0);
        FBO fbo4 = this.mFBO1;
        if (fbo4 != null) {
            GLES30.glUniform2f(this.mGuassAnimGLWallpaper.uResolution, fbo4.getWidth(), this.mFBO1.getHeight());
        } else {
            GLES30.glUniform2f(this.mGuassAnimGLWallpaper.uResolution, this.mScreenSize.width(), this.mScreenSize.height());
        }
        if (z) {
            GLES30.glUniform1i(this.mGuassAnimGLWallpaper.uIsNeedReverse, 1);
        } else {
            GLES30.glUniform1i(this.mGuassAnimGLWallpaper.uIsNeedReverse, 0);
        }
        useTexture(i, false);
        if (z && (fbo2 = this.mFBO1) != null) {
            fbo2.bind();
        }
        draw();
        if (!z || (fbo = this.mFBO1) == null) {
            return;
        }
        fbo.unbind();
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void disposeTexture() {
        super.disposeTexture();
        FBO fbo = this.mFBO1;
        if (fbo != null) {
            fbo.destroy();
        }
    }

    public synchronized int draw(int i, int i2, boolean z, boolean z2) {
        try {
            this.tempOffset = new float[16];
            for (int i3 = 0; i3 < this.sampleRadius; i3++) {
                if (i == 0) {
                    this.tempOffset[i3] = this.mOffset[i3];
                } else {
                    this.tempOffset[this.sampleRadius + i3] = this.mOffset[i3];
                }
            }
            commonDraw(z2, i2, z);
            FBO fbo = this.mFBO1;
            if (fbo == null) {
                return 0;
            }
            return fbo.getFboTexture();
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getFragmentShader() {
        return R.raw.blur;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public AnimImageGLWallpaper getImageGLWallpaper() {
        GuassAnimGLWallpaper guassAnimGLWallpaper = new GuassAnimGLWallpaper(this);
        this.mGuassAnimGLWallpaper = guassAnimGLWallpaper;
        return guassAnimGLWallpaper;
    }

    public synchronized void getRadiusFromSigma(float f) throws Throwable {
        double d;
        double d2;
        double d3;
        double d4;
        double d5;
        double d6;
        double d7;
        double d8;
        double d9;
        double d10;
        double d11;
        try {
            double[] dArr = new double[27];
            double d12 = f;
            double d13 = 2.0d;
            double dSqrt = 1.0d / Math.sqrt((3.141592655d * d12) * 2.0d);
            int i = 0;
            float f2 = 0.0f;
            while (true) {
                d = d13;
                double d14 = i - 13;
                double dExp = Math.exp(((((-1.0d) * d14) * d14) * 0.5d) / d12) * dSqrt;
                dArr[i] = dExp;
                f2 = (float) (((double) f2) + dExp);
                i++;
                if (i == 27) {
                    break;
                } else {
                    d13 = d;
                }
            }
            for (int i2 = 0; i2 != 27; i2++) {
                dArr[i2] = (1.0d / ((double) f2)) * dArr[i2];
            }
            try {
                double d15 = dArr[13] * 0.5d;
                dArr[13] = d15;
                double d16 = dArr[11];
                double d17 = dArr[10];
                double d18 = d16 + d17;
                double d19 = dArr[12];
                double d20 = d19 / (d15 + d19);
                double d21 = d17 + d16;
                double d22 = BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
                if (d21 < 0.002d) {
                    d2 = 0.0d;
                    d18 = 0.0d;
                } else {
                    d2 = (d17 / (d16 + d17)) + d;
                }
                double d23 = dArr[8];
                double d24 = dArr[9];
                if (d23 + d24 < 0.002d) {
                    d3 = 0.0d;
                    d4 = 0.0d;
                } else {
                    double d25 = d23 + d24;
                    d3 = (d23 / (d24 + d23)) + 4.0d;
                    d4 = d25;
                }
                double d26 = dArr[6];
                double d27 = dArr[7];
                if (d26 + d27 < 0.002d) {
                    d6 = 0.0d;
                    d5 = 0.0d;
                } else {
                    d5 = (d26 / (d26 + d27)) + 6.0d;
                    d6 = d26 + d27;
                }
                double d28 = dArr[4];
                double d29 = dArr[5];
                if (d28 + d29 < 0.002d) {
                    d7 = 0.0d;
                    d8 = 0.0d;
                } else {
                    d7 = (d28 / (d28 + d29)) + 8.0d;
                    d8 = d28 + d29;
                }
                double d30 = dArr[2];
                double d31 = dArr[3];
                if (d30 + d31 < 0.002d) {
                    d9 = 0.0d;
                    d10 = 0.0d;
                } else {
                    d9 = (d30 / (d30 + d31)) + 10.0d;
                    d10 = d30 + d31;
                }
                double d32 = dArr[0];
                double d33 = dArr[1];
                if (d32 + d33 < 0.002d) {
                    d11 = 0.0d;
                } else {
                    d22 = d32 + d33;
                    d11 = (d32 / (d32 + d33)) + 12.0d;
                }
                double d34 = d11;
                double d35 = 0.5d - (((((d18 + d4) + d6) + d8) + d10) + d22);
                float[] fArr = this.mOffset;
                fArr[0] = (float) d20;
                fArr[1] = (float) d2;
                fArr[2] = (float) d3;
                fArr[3] = (float) d5;
                fArr[4] = (float) d7;
                fArr[5] = (float) d9;
                fArr[6] = (float) d34;
                float[] fArr2 = this.mWeight;
                fArr2[0] = (float) d35;
                fArr2[1] = (float) d18;
                fArr2[2] = (float) d4;
                fArr2[3] = (float) d6;
                fArr2[4] = (float) d8;
                fArr2[5] = (float) d10;
                fArr2[6] = (float) d22;
                fArr2[7] = 0.0f;
                int i3 = 0;
                int i4 = 1;
                for (int i5 = 0; i5 < 8; i5++) {
                    float[] fArr3 = this.mWeight;
                    float f3 = fArr3[i5];
                    if (f3 <= 0.0f || f3 >= 1.0f) {
                        fArr3[i5] = 0.0f;
                    } else {
                        i3++;
                    }
                    float f4 = this.mOffset[i5];
                    if (i4 < ((int) (1.0f + f4))) {
                        i4 = ((int) f4) + 1;
                    }
                }
                this.sampleRadius = i3;
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getVertexShader() {
        return R.raw.vertex_shader;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void glProgramDrawEffectUniform(boolean z, int i, boolean z2) {
        super.glProgramDrawEffectUniform(z, i, z2);
        FBO fbo = this.mFBO1;
        if (fbo != null) {
            GLES30.glUniform1f(this.mGuassAnimGLWallpaper.in_imageWidth, fbo.getWidth());
            GLES30.glUniform1f(this.mGuassAnimGLWallpaper.in_imageHeight, this.mFBO1.getHeight());
        } else {
            GLES30.glUniform1f(this.mGuassAnimGLWallpaper.in_imageWidth, this.mScreenSize.width());
            GLES30.glUniform1f(this.mGuassAnimGLWallpaper.in_imageHeight, this.mScreenSize.height());
        }
        GLES30.glUniform1i(this.mGuassAnimGLWallpaper.sampleCount, this.sampleRadius);
        int i2 = this.mGuassAnimGLWallpaper.in_blurOffset;
        float[] fArr = this.tempOffset;
        GLES30.glUniform1fv(i2, fArr.length, fArr, 0);
        int i3 = this.mGuassAnimGLWallpaper.in_blurWeight;
        float[] fArr2 = this.mWeight;
        GLES30.glUniform1fv(i3, fArr2.length, fArr2, 0);
    }

    public void setFBO(FBO fbo) {
        this.mFBO1 = fbo;
    }
}
