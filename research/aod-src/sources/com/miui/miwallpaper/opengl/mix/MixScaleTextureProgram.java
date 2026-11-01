package com.miui.miwallpaper.opengl.mix;

import android.graphics.Bitmap;
import android.opengl.GLES30;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;
import com.miui.miwallpaper.util.ScreenUtils;
import com.miui.wallpaperglassshader.jar.R;

/* JADX INFO: loaded from: classes2.dex */
public class MixScaleTextureProgram extends GlassAnimGLProgram {
    public float desktopUVScaleX;
    public float desktopUVScaleY;
    public float keyguardUVScaleX;
    public float keyguardUVScaleY;
    public Bitmap mDstBitmap;
    public int mDstTexture;
    public boolean mIsNeedReverse;
    public float mMixFactor;
    public MixScaleTextureGLWallpaper mMixScaleTextureGLWallpaper;
    public float mScale;

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getFragmentShader() {
        return R.raw.mix_fragment;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public AnimImageGLWallpaper getImageGLWallpaper() {
        MixScaleTextureGLWallpaper mixScaleTextureGLWallpaper = new MixScaleTextureGLWallpaper(this);
        this.mMixScaleTextureGLWallpaper = mixScaleTextureGLWallpaper;
        return mixScaleTextureGLWallpaper;
    }

    public float getValidParam(float f) {
        return Math.min(Math.max(0.0f, f), 1.0f);
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getVertexShader() {
        return R.raw.mix_vertex;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void glProgramDrawEffectUniform(boolean z, int i, boolean z2) {
        super.glProgramDrawEffectUniform(z, i, z2);
        GLES30.glUniform1f(this.mMixScaleTextureGLWallpaper.uMixFactor, this.mMixFactor);
        GLES30.glUniform1f(this.mMixScaleTextureGLWallpaper.uScale, 1.0f / this.mScale);
        GLES30.glUniform1f(this.mMixScaleTextureGLWallpaper.uKeyguardUVScaleX, 1.0f / this.keyguardUVScaleX);
        GLES30.glUniform1f(this.mMixScaleTextureGLWallpaper.uKeyguardUVScaleY, 1.0f / this.keyguardUVScaleY);
        GLES30.glUniform1f(this.mMixScaleTextureGLWallpaper.uDesktopUVScaleX, 1.0f / this.desktopUVScaleX);
        GLES30.glUniform1f(this.mMixScaleTextureGLWallpaper.uDesktopUVScaleY, 1.0f / this.desktopUVScaleY);
        GLES30.glViewport(0, 0, this.mScreenSize.width(), this.mScreenSize.height());
        if (this.mIsNeedReverse) {
            GLES30.glUniform1i(this.mMixScaleTextureGLWallpaper.uIsNeedReverseUV, 1);
        } else {
            GLES30.glUniform1i(this.mMixScaleTextureGLWallpaper.uIsNeedReverseUV, 0);
        }
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceChanged(int i, int i2) {
        super.onSurfaceChanged(i, i2);
        this.mScreenSize = ScreenUtils.getScreenRect(this.mContext);
        updateMixUVLocation();
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceCreated(Bitmap bitmap) {
        super.onSurfaceCreated(bitmap);
        this.mDstTexture = loadTexture(this.mDstBitmap);
    }

    public void updateMixUVLocation() {
        Bitmap bitmap = this.mBitmap;
        if (bitmap == null || this.mDstBitmap == null) {
            return;
        }
        float width = bitmap.getWidth() / this.mBitmap.getHeight();
        float width2 = this.mDstBitmap.getWidth() / this.mDstBitmap.getHeight();
        float fWidth = this.mScreenSize.width() / this.mScreenSize.height();
        if (width == fWidth) {
            this.keyguardUVScaleX = 1.0f;
            this.keyguardUVScaleY = 1.0f;
        } else if (width < fWidth) {
            float height = (this.mBitmap.getHeight() / ((this.mBitmap.getWidth() * 1.0f) / this.mScreenSize.width())) / this.mScreenSize.height();
            this.keyguardUVScaleX = 1.0f;
            if (height <= 0.0f) {
                height = 1.0f;
            }
            this.keyguardUVScaleY = height;
        } else {
            float width3 = (this.mBitmap.getWidth() / ((this.mBitmap.getHeight() * 1.0f) / this.mScreenSize.height())) / this.mScreenSize.width();
            if (width3 <= 0.0f) {
                width3 = 1.0f;
            }
            this.keyguardUVScaleX = width3;
            this.keyguardUVScaleY = 1.0f;
        }
        if (width2 == fWidth) {
            this.desktopUVScaleX = 1.0f;
            this.desktopUVScaleY = 1.0f;
        } else if (width2 < fWidth) {
            float height2 = (this.mDstBitmap.getHeight() / ((this.mDstBitmap.getWidth() * 1.0f) / this.mScreenSize.width())) / this.mScreenSize.height();
            this.desktopUVScaleX = 1.0f;
            this.desktopUVScaleY = height2 > 0.0f ? height2 : 1.0f;
        } else {
            float width4 = (this.mDstBitmap.getWidth() / ((this.mDstBitmap.getHeight() * 1.0f) / this.mScreenSize.height())) / this.mScreenSize.width();
            if (width4 <= 0.0f) {
                width4 = 1.0f;
            }
            this.desktopUVScaleX = width4;
            this.desktopUVScaleY = 1.0f;
        }
    }

    public void updateMixtextureFactor(float f) {
        this.mMixFactor = getValidParam(f);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void useTexture(int i, boolean z) {
        super.useTexture(i, z);
        GLES30.glActiveTexture(33985);
        GLES30.glBindTexture(3553, this.mDstTexture);
        GLES30.glUniform1i(this.mMixScaleTextureGLWallpaper.uDstTex, 1);
    }
}
