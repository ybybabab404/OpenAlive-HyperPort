package com.miui.miwallpaper.opengl.guass;

import android.content.Context;
import android.opengl.GLES30;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;
import com.miui.wallpaperglassshader.jar.R;
import effect_engine.utils.FBO;

/* JADX INFO: loaded from: classes2.dex */
public class DownSamplingAnimGLProgram extends GlassAnimGLProgram {
    public boolean isNeedReverse;
    public DownSamplingAnimGLWallpaper mDownSamplingAnimGLWallpaper;
    public FBO mFBO1;
    public float mScale;

    public DownSamplingAnimGLProgram(Context context) {
        super(context);
        this.isNeedReverse = true;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void commonDraw(boolean z, int i, boolean z2) {
        FBO fbo;
        FBO fbo2;
        useCreateGLProgram();
        GLES30.glClear(16384);
        GLES30.glUniformMatrix4fv(this.mDownSamplingAnimGLWallpaper.uMVPMatrix, 1, false, this.mMVPMatrix, 0);
        int i2 = this.mDownSamplingAnimGLWallpaper.uScaleOffset;
        float f = this.mScaleX;
        if (f <= 0.0f) {
            f = 1.0f;
        }
        float f2 = this.mScaleY;
        GLES30.glUniform4f(i2, f, f2 > 0.0f ? f2 : 1.0f, this.mTranslateX, this.mTranslateY);
        GLES30.glUniform1f(this.mDownSamplingAnimGLWallpaper.uScale, this.mScale);
        FBO fbo3 = this.mFBO1;
        if (fbo3 != null) {
            GLES30.glUniform2f(this.mDownSamplingAnimGLWallpaper.uResolution, fbo3.getWidth(), this.mFBO1.getHeight());
        } else {
            GLES30.glUniform2f(this.mDownSamplingAnimGLWallpaper.uResolution, this.mScreenSize.width(), this.mScreenSize.height());
        }
        useTexture(i, false);
        if (z) {
            GLES30.glUniform1i(this.mDownSamplingAnimGLWallpaper.uIsNeedReverse, 1);
        } else {
            GLES30.glUniform1i(this.mDownSamplingAnimGLWallpaper.uIsNeedReverse, 0);
        }
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
        return R.raw.down;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public AnimImageGLWallpaper getImageGLWallpaper() {
        DownSamplingAnimGLWallpaper downSamplingAnimGLWallpaper = new DownSamplingAnimGLWallpaper(this);
        this.mDownSamplingAnimGLWallpaper = downSamplingAnimGLWallpaper;
        return downSamplingAnimGLWallpaper;
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getVertexShader() {
        return R.raw.vertex_shader;
    }

    public void setFBO(FBO fbo) {
        this.mFBO1 = fbo;
    }

    public void setScale(float f) {
        this.mScale = f;
    }
}
