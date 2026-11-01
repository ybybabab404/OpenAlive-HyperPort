package com.miui.miwallpaper.opengl.guass;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES30;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;
import com.miui.wallpaperglassshader.jar.R;
import effect_engine.utils.FBO;

/* JADX INFO: loaded from: classes2.dex */
public class UpSamplingAnimGLProgram extends GlassAnimGLProgram {
    public final float kNoiseCoeff;
    public FBO mFBO1;
    public boolean mIsNeedAddSaturn;
    public boolean mIsNeedReverse;
    public float mShadowRatio;
    public UpSamplingAnimGLWallpaper mUpSamplingAnimGLWallpaper;

    public UpSamplingAnimGLProgram(Context context) {
        super(context);
        this.kNoiseCoeff = 0.0045f;
        this.mIsNeedAddSaturn = true;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void commonDraw(boolean z, int i, boolean z2) {
        FBO fbo;
        FBO fbo2;
        useCreateGLProgram();
        GLES30.glClear(16384);
        GLES30.glUniformMatrix4fv(this.mUpSamplingAnimGLWallpaper.uMVPMatrix, 1, false, this.mMVPMatrix, 0);
        int i2 = this.mUpSamplingAnimGLWallpaper.uScaleOffset;
        float f = this.mScaleX;
        if (f <= 0.0f) {
            f = 1.0f;
        }
        float f2 = this.mScaleY;
        GLES30.glUniform4f(i2, f, f2 > 0.0f ? f2 : 1.0f, this.mTranslateX, this.mTranslateY);
        FBO fbo3 = this.mFBO1;
        if (fbo3 != null) {
            GLES30.glUniform2f(this.mUpSamplingAnimGLWallpaper.uResolution, fbo3.getWidth(), this.mFBO1.getHeight());
        } else {
            GLES30.glUniform2f(this.mUpSamplingAnimGLWallpaper.uResolution, this.mScreenSize.width(), this.mScreenSize.height());
        }
        GLES30.glViewport(0, 0, this.mScreenSize.width(), this.mScreenSize.height());
        GLES30.glUniform1i(this.mUpSamplingAnimGLWallpaper.uIsNeedReverse, (this.mIsNeedReverse && z) ? 1 : 0);
        GLES30.glUniform1i(this.mUpSamplingAnimGLWallpaper.isNeedAddSaturn, this.mIsNeedAddSaturn ? 1 : 0);
        GLES30.glUniform1f(this.mUpSamplingAnimGLWallpaper.uShadowRatio, this.mShadowRatio);
        useTexture(i, z2);
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

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public int getFBOTexture() {
        FBO fbo = this.mFBO1;
        if (fbo != null) {
            return fbo.getFboTexture();
        }
        return 0;
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getFragmentShader() {
        return R.raw.up;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public AnimImageGLWallpaper getImageGLWallpaper() {
        UpSamplingAnimGLWallpaper upSamplingAnimGLWallpaper = new UpSamplingAnimGLWallpaper(this);
        this.mUpSamplingAnimGLWallpaper = upSamplingAnimGLWallpaper;
        return upSamplingAnimGLWallpaper;
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getVertexShader() {
        return R.raw.vertex_shader;
    }

    public boolean isNeedReverse() {
        return this.mIsNeedReverse;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceChanged(int i, int i2) {
        super.onSurfaceChanged(i, i2);
        FBO fbo = this.mFBO1;
        if (fbo != null) {
            fbo.destroy();
        }
        this.mFBO1 = new FBO(i, i2, false);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceCreated(Bitmap bitmap) {
        super.onSurfaceCreated(bitmap);
        FBO fbo = this.mFBO1;
        if (fbo != null) {
            fbo.destroy();
        }
        this.mFBO1 = new FBO(this.mScreenSize.width(), this.mScreenSize.height(), false);
        GLES30.glUniform1f(this.mUpSamplingAnimGLWallpaper.uNoiseCoeff, 0.0045f);
    }

    public void setShadowRatio(float f) {
        this.mShadowRatio = f;
    }
}
