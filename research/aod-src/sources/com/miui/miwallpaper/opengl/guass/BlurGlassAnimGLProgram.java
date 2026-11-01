package com.miui.miwallpaper.opengl.guass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;
import com.miui.miwallpaper.opengl.TextureConfig;
import com.miui.wallpaperglassshader.jar.R;
import effect_engine.utils.FBO;
import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes2.dex */
public class BlurGlassAnimGLProgram extends GlassAnimGLProgram {
    public final int MAX_BLUR_RADIUS;
    public volatile FBO[][] downFBOViews;
    public boolean isKeyguardPictorialChangeClose;
    public volatile float mBlurRadius;
    public ClipAnimGLProgram mClipAnimGLProgram;
    public DownSamplingAnimGLProgram mDownSamplingAnimGLProgram;
    public volatile FBO mFBO0;
    public volatile FBO mFBO1;
    public GuassAnimGLProgram mGuassAnimGLProgram;
    public int mMainTextureId;
    public volatile int mSampleCount;
    public UpSamplingAnimGLProgram mUpSamplingAnimGLProgram;
    public int originBitmapHeight;
    public int originBitmapWidth;

    public BlurGlassAnimGLProgram(Context context, int i, int i2, boolean z) {
        super(context);
        this.mSampleCount = 0;
        this.downFBOViews = (FBO[][]) Array.newInstance((Class<?>) FBO.class, 4, 2);
        this.MAX_BLUR_RADIUS = 76;
        this.mClipAnimGLProgram = new ClipAnimGLProgram(context);
        this.mDownSamplingAnimGLProgram = new DownSamplingAnimGLProgram(context);
        this.mUpSamplingAnimGLProgram = new UpSamplingAnimGLProgram(context);
        this.mGuassAnimGLProgram = new GuassAnimGLProgram(context);
        this.originBitmapWidth = i;
        this.originBitmapHeight = i2;
        this.isKeyguardPictorialChangeClose = z;
        setRadius(76.0f);
    }

    public synchronized int blur(int i) {
        int iDraw;
        try {
            this.mGuassAnimGLProgram.configTexture(i, 33071, 33071);
            if (this.mSampleCount == 0) {
                this.mGuassAnimGLProgram.setFBO(this.mFBO0);
            } else if (this.mSampleCount >= 1) {
                this.mGuassAnimGLProgram.setFBO(this.downFBOViews[this.mSampleCount - 1][1]);
            }
            iDraw = this.mGuassAnimGLProgram.draw(0, i, false, true);
            this.mGuassAnimGLProgram.configTexture(iDraw, 33071, 33071);
            if (this.mSampleCount == 0) {
                this.mGuassAnimGLProgram.setFBO(this.mFBO1);
                if (this.mFBO0 != null) {
                    this.mFBO0.recoverLastViewPort();
                }
            } else {
                this.mGuassAnimGLProgram.setFBO(this.downFBOViews[this.mSampleCount - 1][0]);
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.mGuassAnimGLProgram.draw(1, iDraw, false, true);
    }

    public final void calculateDownSampleFBO(FBO fbo, FBO fbo2) {
        char c;
        int width = fbo.getWidth();
        int height = fbo2.getHeight();
        int i = 0;
        while (true) {
            c = 1;
            if (i >= this.downFBOViews.length) {
                break;
            }
            if (this.downFBOViews[i][0] != null) {
                this.downFBOViews[i][0].destroy();
            }
            if (this.downFBOViews[i][1] != null) {
                this.downFBOViews[i][1].destroy();
            }
            i++;
        }
        int i2 = 0;
        while (i2 < 4) {
            int i3 = i2 + 1;
            float fPow = (float) Math.pow(2.0d, i3);
            double d = width / fPow;
            double d2 = height / fPow;
            char c2 = c;
            this.downFBOViews[i2][0] = new FBO((int) Math.ceil(d), (int) Math.ceil(d2), false);
            this.downFBOViews[i2][c2] = new FBO((int) Math.ceil(d), (int) Math.ceil(d2), false);
            c = c2;
            i2 = i3;
        }
    }

    public final synchronized int clipBeforeDown(boolean z, int i, boolean z2) {
        try {
            if (z2) {
                this.mClipAnimGLProgram.configTexture(i, 33071, 33071);
            } else {
                this.mClipAnimGLProgram.configTexture(this.mMainTextureId, 33071, 33071);
            }
            this.mClipAnimGLProgram.setFBO(this.mFBO0);
            this.mClipAnimGLProgram.commonDraw(z, i, z2);
        } catch (Throwable th) {
            throw th;
        }
        return this.mClipAnimGLProgram.getFBOTexture();
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void commonDraw(boolean z, int i, boolean z2) {
        if (this.mBlurRadius >= 1.0f) {
            this.mClipAnimGLProgram.setIsNeedReverse(true);
            upSampling(z, blur(downSampling(true, clipBeforeDown(true, i, z2), true)), false);
        } else {
            this.mClipAnimGLProgram.setIsNeedReverse(this.mUpSamplingAnimGLProgram.isNeedReverse());
            clipBeforeDown(z, i, z2);
        }
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void disposeTexture() {
        super.disposeTexture();
        if (this.mFBO0 != null) {
            this.mFBO0.destroy();
        }
        if (this.mFBO1 != null) {
            this.mFBO1.destroy();
        }
        for (int i = 0; i < this.downFBOViews.length; i++) {
            if (this.downFBOViews[i][0] != null) {
                this.downFBOViews[i][0].destroy();
            }
            if (this.downFBOViews[i][1] != null) {
                this.downFBOViews[i][1].destroy();
            }
        }
        ClipAnimGLProgram clipAnimGLProgram = this.mClipAnimGLProgram;
        if (clipAnimGLProgram != null) {
            clipAnimGLProgram.disposeTexture();
        }
        DownSamplingAnimGLProgram downSamplingAnimGLProgram = this.mDownSamplingAnimGLProgram;
        if (downSamplingAnimGLProgram != null) {
            downSamplingAnimGLProgram.disposeTexture();
        }
        UpSamplingAnimGLProgram upSamplingAnimGLProgram = this.mUpSamplingAnimGLProgram;
        if (upSamplingAnimGLProgram != null) {
            upSamplingAnimGLProgram.disposeTexture();
        }
        GuassAnimGLProgram guassAnimGLProgram = this.mGuassAnimGLProgram;
        if (guassAnimGLProgram != null) {
            guassAnimGLProgram.disposeTexture();
        }
    }

    public final synchronized int downSampling(boolean z, int i, boolean z2) {
        int fBOTexture;
        try {
            if (z2) {
                this.mDownSamplingAnimGLProgram.configTexture(i, 33071, 33071);
            } else {
                this.mDownSamplingAnimGLProgram.configTexture(this.mMainTextureId, 33071, 33071);
            }
            if (this.mSampleCount == 0) {
                this.mDownSamplingAnimGLProgram.setScale(1.0f);
                this.mDownSamplingAnimGLProgram.setFBO(this.mFBO1);
                this.mDownSamplingAnimGLProgram.commonDraw(z, i, z2);
                fBOTexture = this.mDownSamplingAnimGLProgram.getFBOTexture();
            } else if (this.mSampleCount < 4) {
                this.mDownSamplingAnimGLProgram.setScale((float) Math.pow(2.0d, this.mSampleCount));
                this.mDownSamplingAnimGLProgram.setFBO(this.downFBOViews[this.mSampleCount - 1][0]);
                this.mDownSamplingAnimGLProgram.commonDraw(z, i, z2);
                fBOTexture = this.mDownSamplingAnimGLProgram.getFBOTexture();
            } else if (this.mSampleCount == 4) {
                this.mDownSamplingAnimGLProgram.setScale(8.0f);
                this.mDownSamplingAnimGLProgram.setFBO(this.downFBOViews[2][0]);
                this.mDownSamplingAnimGLProgram.commonDraw(true, i, z2);
                int fBOTexture2 = this.mDownSamplingAnimGLProgram.getFBOTexture();
                this.mDownSamplingAnimGLProgram.configTexture(fBOTexture2, 33071, 33071);
                this.mDownSamplingAnimGLProgram.setScale(2.0f);
                this.mDownSamplingAnimGLProgram.setFBO(this.downFBOViews[3][0]);
                this.mDownSamplingAnimGLProgram.commonDraw(z, fBOTexture2, z2);
                fBOTexture = this.mDownSamplingAnimGLProgram.getFBOTexture();
            } else {
                fBOTexture = 0;
            }
        } catch (Throwable th) {
            throw th;
        }
        return fBOTexture;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public int getFBOTexture() {
        if (this.mBlurRadius >= 1.0f) {
            UpSamplingAnimGLProgram upSamplingAnimGLProgram = this.mUpSamplingAnimGLProgram;
            if (upSamplingAnimGLProgram != null) {
                return upSamplingAnimGLProgram.getFBOTexture();
            }
            return 0;
        }
        ClipAnimGLProgram clipAnimGLProgram = this.mClipAnimGLProgram;
        if (clipAnimGLProgram != null) {
            return clipAnimGLProgram.getFBOTexture();
        }
        return 0;
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getFragmentShader() {
        return R.raw.blur;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public float[] getMVPMatrix() {
        ClipAnimGLProgram clipAnimGLProgram = this.mClipAnimGLProgram;
        return clipAnimGLProgram != null ? clipAnimGLProgram.getMVPMatrix() : this.mMVPMatrix;
    }

    public final synchronized float getSampleCount(float f) {
        float f2;
        try {
            this.mSampleCount = 0;
            f2 = f * f;
            float f3 = f2 > 100.0f ? 30.25f : 12.599999f;
            if (f2 > 400.0f) {
                f2 = (f2 / 64.0f) - 0.140625f;
                this.mSampleCount = 3;
            }
            if (f2 >= 90.25f) {
                f2 = (f2 / 16.0f) - 0.47265625f;
                this.mSampleCount = 2;
            }
            if (f2 >= f3) {
                f2 = (f2 / 4.0f) - 0.756625f;
                if (this.mSampleCount >= 1) {
                    this.mSampleCount++;
                } else {
                    this.mSampleCount = 1;
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return f2;
    }

    public float[] getScaleXY() {
        ClipAnimGLProgram clipAnimGLProgram = this.mClipAnimGLProgram;
        return clipAnimGLProgram != null ? new float[]{clipAnimGLProgram.mScaleX, clipAnimGLProgram.mScaleY} : new float[]{1.0f, 1.0f};
    }

    public float[] getTranslateXY() {
        ClipAnimGLProgram clipAnimGLProgram = this.mClipAnimGLProgram;
        return clipAnimGLProgram != null ? new float[]{clipAnimGLProgram.mTranslateX, clipAnimGLProgram.mTranslateY} : new float[]{0.0f, 0.0f};
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getVertexShader() {
        return R.raw.vertex_shader;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceChanged(int i, int i2) {
        super.onSurfaceChanged(i, i2);
        if (this.mFBO0 != null) {
            this.mFBO0.destroy();
        }
        if (this.mFBO1 != null) {
            this.mFBO1.destroy();
        }
        this.mFBO0 = new FBO(i, i2, false);
        this.mFBO1 = new FBO(i, i2, false);
        this.mFBO0.recordLastViewPort();
        ClipAnimGLProgram clipAnimGLProgram = this.mClipAnimGLProgram;
        if (clipAnimGLProgram != null) {
            clipAnimGLProgram.onSurfaceChanged(i, i2);
        }
        DownSamplingAnimGLProgram downSamplingAnimGLProgram = this.mDownSamplingAnimGLProgram;
        if (downSamplingAnimGLProgram != null) {
            downSamplingAnimGLProgram.onSurfaceChanged(i, i2);
        }
        UpSamplingAnimGLProgram upSamplingAnimGLProgram = this.mUpSamplingAnimGLProgram;
        if (upSamplingAnimGLProgram != null) {
            upSamplingAnimGLProgram.onSurfaceChanged(i, i2);
        }
        GuassAnimGLProgram guassAnimGLProgram = this.mGuassAnimGLProgram;
        if (guassAnimGLProgram != null) {
            guassAnimGLProgram.onSurfaceChanged(i, i2);
        }
        calculateDownSampleFBO(this.mFBO0, this.mFBO1);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceCreated(Bitmap bitmap) {
        this.mBitmap = bitmap;
        if (this.mFBO0 != null) {
            this.mFBO0.destroy();
        }
        if (this.mFBO1 != null) {
            this.mFBO1.destroy();
        }
        this.mFBO0 = new FBO(this.mScreenSize.width(), this.mScreenSize.height(), false);
        this.mFBO1 = new FBO(this.mScreenSize.width(), this.mScreenSize.height(), false);
        this.mFBO0.recordLastViewPort();
        calculateDownSampleFBO(this.mFBO0, this.mFBO1);
        this.mMainTextureId = loadTexture(bitmap);
        ClipAnimGLProgram clipAnimGLProgram = this.mClipAnimGLProgram;
        if (clipAnimGLProgram != null) {
            clipAnimGLProgram.onSurfaceCreated(bitmap);
        }
        DownSamplingAnimGLProgram downSamplingAnimGLProgram = this.mDownSamplingAnimGLProgram;
        if (downSamplingAnimGLProgram != null) {
            downSamplingAnimGLProgram.onSurfaceCreated(bitmap);
        }
        UpSamplingAnimGLProgram upSamplingAnimGLProgram = this.mUpSamplingAnimGLProgram;
        if (upSamplingAnimGLProgram != null) {
            upSamplingAnimGLProgram.onSurfaceCreated(bitmap);
        }
        GuassAnimGLProgram guassAnimGLProgram = this.mGuassAnimGLProgram;
        if (guassAnimGLProgram != null) {
            guassAnimGLProgram.onSurfaceCreated(bitmap);
        }
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void setIdentityMVPMatrix() {
        ClipAnimGLProgram clipAnimGLProgram = this.mClipAnimGLProgram;
        if (clipAnimGLProgram != null) {
            clipAnimGLProgram.setIdentityMVPMatrix();
        }
        DownSamplingAnimGLProgram downSamplingAnimGLProgram = this.mDownSamplingAnimGLProgram;
        if (downSamplingAnimGLProgram != null) {
            downSamplingAnimGLProgram.setIdentityMVPMatrix();
        }
        GuassAnimGLProgram guassAnimGLProgram = this.mGuassAnimGLProgram;
        if (guassAnimGLProgram != null) {
            guassAnimGLProgram.setIdentityMVPMatrix();
        }
        UpSamplingAnimGLProgram upSamplingAnimGLProgram = this.mUpSamplingAnimGLProgram;
        if (upSamplingAnimGLProgram != null) {
            upSamplingAnimGLProgram.setIdentityMVPMatrix();
        }
    }

    public synchronized void setRadius(float f) {
        this.mBlurRadius = f;
        this.mGuassAnimGLProgram.getRadiusFromSigma(getSampleCount(Math.max(Math.min(76.0f, f), 1.0f)));
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void setScale(float f, float f2) {
        ClipAnimGLProgram clipAnimGLProgram = this.mClipAnimGLProgram;
        if (clipAnimGLProgram != null) {
            clipAnimGLProgram.setScale(f, f2);
        }
    }

    public void setShadowRatio(float f) {
        UpSamplingAnimGLProgram upSamplingAnimGLProgram = this.mUpSamplingAnimGLProgram;
        if (upSamplingAnimGLProgram != null) {
            upSamplingAnimGLProgram.setShadowRatio(f);
        }
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void setTranslate(float f, float f2) {
        ClipAnimGLProgram clipAnimGLProgram = this.mClipAnimGLProgram;
        if (clipAnimGLProgram != null) {
            clipAnimGLProgram.setTranslate(f, f2);
        }
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void setup(Bitmap bitmap, TextureConfig textureConfig) {
        ClipAnimGLProgram clipAnimGLProgram = this.mClipAnimGLProgram;
        if (clipAnimGLProgram != null) {
            clipAnimGLProgram.setUpUniformAndAttr();
        }
        DownSamplingAnimGLProgram downSamplingAnimGLProgram = this.mDownSamplingAnimGLProgram;
        if (downSamplingAnimGLProgram != null) {
            downSamplingAnimGLProgram.setUpUniformAndAttr();
        }
        UpSamplingAnimGLProgram upSamplingAnimGLProgram = this.mUpSamplingAnimGLProgram;
        if (upSamplingAnimGLProgram != null) {
            upSamplingAnimGLProgram.setUpUniformAndAttr();
        }
        GuassAnimGLProgram guassAnimGLProgram = this.mGuassAnimGLProgram;
        if (guassAnimGLProgram != null) {
            guassAnimGLProgram.setUpUniformAndAttr();
        }
    }

    public synchronized void upSampling(boolean z, int i, boolean z2) {
        try {
            if (this.mFBO0 != null) {
                this.mFBO0.recoverLastViewPort();
            }
            this.mUpSamplingAnimGLProgram.configTexture(i, 33071, 33071);
            this.mUpSamplingAnimGLProgram.commonDraw(z, i, z2);
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void updateMVPMatrix(int i, int i2, Rect rect) {
        ClipAnimGLProgram clipAnimGLProgram = this.mClipAnimGLProgram;
        if (clipAnimGLProgram != null) {
            clipAnimGLProgram.updateMVPMatrix(i, i2, rect);
        }
        DownSamplingAnimGLProgram downSamplingAnimGLProgram = this.mDownSamplingAnimGLProgram;
        if (downSamplingAnimGLProgram != null) {
            downSamplingAnimGLProgram.setIdentityMVPMatrix();
        }
        UpSamplingAnimGLProgram upSamplingAnimGLProgram = this.mUpSamplingAnimGLProgram;
        if (upSamplingAnimGLProgram != null) {
            upSamplingAnimGLProgram.setIdentityMVPMatrix();
        }
        GuassAnimGLProgram guassAnimGLProgram = this.mGuassAnimGLProgram;
        if (guassAnimGLProgram != null) {
            guassAnimGLProgram.setIdentityMVPMatrix();
        }
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public void useGLProgram() {
        ClipAnimGLProgram clipAnimGLProgram = this.mClipAnimGLProgram;
        if (clipAnimGLProgram != null) {
            clipAnimGLProgram.useGLProgram();
        }
        DownSamplingAnimGLProgram downSamplingAnimGLProgram = this.mDownSamplingAnimGLProgram;
        if (downSamplingAnimGLProgram != null) {
            downSamplingAnimGLProgram.useGLProgram();
        }
        UpSamplingAnimGLProgram upSamplingAnimGLProgram = this.mUpSamplingAnimGLProgram;
        if (upSamplingAnimGLProgram != null) {
            upSamplingAnimGLProgram.useGLProgram();
        }
        GuassAnimGLProgram guassAnimGLProgram = this.mGuassAnimGLProgram;
        if (guassAnimGLProgram != null) {
            guassAnimGLProgram.useGLProgram();
        }
    }
}
