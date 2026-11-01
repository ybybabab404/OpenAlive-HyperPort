package com.miui.miwallpaper.opengl.guass;

import android.content.Context;
import android.opengl.GLES30;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;
import com.miui.wallpaperglassshader.jar.R;
import effect_engine.utils.FBO;

/* JADX INFO: loaded from: classes2.dex */
public class ClipAnimGLProgram extends GlassAnimGLProgram {
    public boolean isNeedReverse;
    public ClipAnimGLWallpaper mClipAnimGLWallpaper;
    public FBO mFBO1;

    public ClipAnimGLProgram(Context context) {
        super(context);
        this.isNeedReverse = true;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void commonDraw(boolean z, int i, boolean z2) {
        FBO fbo;
        FBO fbo2;
        useCreateGLProgram();
        GLES30.glClear(16384);
        GLES30.glUniformMatrix4fv(this.mClipAnimGLWallpaper.uMVPMatrix, 1, false, this.mMVPMatrix, 0);
        int i2 = this.mClipAnimGLWallpaper.uScaleOffset;
        float f = this.mScaleX;
        if (f <= 0.0f) {
            f = 1.0f;
        }
        float f2 = this.mScaleY;
        GLES30.glUniform4f(i2, f, f2 > 0.0f ? f2 : 1.0f, this.mTranslateX, this.mTranslateY);
        GLES30.glViewport(0, 0, this.mScreenSize.width(), this.mScreenSize.height());
        useTexture(i, false);
        if (z && this.isNeedReverse) {
            GLES30.glUniform1i(this.mClipAnimGLWallpaper.uIsNeedReverse, 1);
        } else {
            GLES30.glUniform1i(this.mClipAnimGLWallpaper.uIsNeedReverse, 0);
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
        return getNowShaderResourceID(this.mContext, R.raw.clip, R.raw.clip_clamp_border);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public AnimImageGLWallpaper getImageGLWallpaper() {
        ClipAnimGLWallpaper clipAnimGLWallpaper = new ClipAnimGLWallpaper(this);
        this.mClipAnimGLWallpaper = clipAnimGLWallpaper;
        return clipAnimGLWallpaper;
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getVertexShader() {
        return getNowShaderResourceID(this.mContext, R.raw.vertex_shader, R.raw.vertex_clamp_border);
    }

    public void setFBO(FBO fbo) {
        this.mFBO1 = fbo;
    }

    public void setIsNeedReverse(boolean z) {
        this.isNeedReverse = z;
    }
}
