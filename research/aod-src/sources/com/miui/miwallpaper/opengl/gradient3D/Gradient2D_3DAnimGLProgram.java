package com.miui.miwallpaper.opengl.gradient3D;

import android.content.Context;
import android.graphics.Bitmap;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;
import com.miui.miwallpaper.opengl.TextureConfig;
import com.miui.miwallpaper.opengl.guass.BlurGlassAnimGLProgram;

/* JADX INFO: loaded from: classes2.dex */
public class Gradient2D_3DAnimGLProgram extends GlassAnimGLProgram {
    public BlurGlassAnimGLProgram mBlurAnimGLProgram;
    public Gradient2DNoiseBlurAnimGLProgram mGradient2DNoiseAnimGLProgram;

    public Gradient2D_3DAnimGLProgram(Context context) {
        super(context);
        this.mBlurAnimGLProgram = new BlurGlassAnimGLProgram(context, 0, 0, true);
        this.mGradient2DNoiseAnimGLProgram = new Gradient2DNoiseBlurAnimGLProgram(context);
    }

    public void animateNoiseColorSwatch(float f) {
        Gradient2DNoiseBlurAnimGLProgram gradient2DNoiseBlurAnimGLProgram = this.mGradient2DNoiseAnimGLProgram;
        if (gradient2DNoiseBlurAnimGLProgram != null) {
            gradient2DNoiseBlurAnimGLProgram.animateNoiseColorSwatch(f);
        }
    }

    public void animateNoisePattern(float f) {
        Gradient2DNoiseBlurAnimGLProgram gradient2DNoiseBlurAnimGLProgram = this.mGradient2DNoiseAnimGLProgram;
        if (gradient2DNoiseBlurAnimGLProgram != null) {
            gradient2DNoiseBlurAnimGLProgram.animateNoisePattern(f);
        }
    }

    public void animateNoiseProgress(float f) {
        Gradient2DNoiseBlurAnimGLProgram gradient2DNoiseBlurAnimGLProgram = this.mGradient2DNoiseAnimGLProgram;
        if (gradient2DNoiseBlurAnimGLProgram != null) {
            gradient2DNoiseBlurAnimGLProgram.animateNoiseProgress(f);
        }
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void commonDraw(boolean z, int i, boolean z2) {
        BlurGlassAnimGLProgram blurGlassAnimGLProgram = this.mBlurAnimGLProgram;
        if (blurGlassAnimGLProgram == null || this.mGradient2DNoiseAnimGLProgram == null) {
            return;
        }
        blurGlassAnimGLProgram.commonDraw(true, i, z2);
        this.mGradient2DNoiseAnimGLProgram.commonDraw(z, this.mBlurAnimGLProgram.getFBOTexture(), true);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public int getFBOTexture() {
        Gradient2DNoiseBlurAnimGLProgram gradient2DNoiseBlurAnimGLProgram = this.mGradient2DNoiseAnimGLProgram;
        if (gradient2DNoiseBlurAnimGLProgram != null) {
            return gradient2DNoiseBlurAnimGLProgram.getFBOTexture();
        }
        return 0;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceChanged(int i, int i2) {
        BlurGlassAnimGLProgram blurGlassAnimGLProgram;
        if (this.mGradient2DNoiseAnimGLProgram == null || (blurGlassAnimGLProgram = this.mBlurAnimGLProgram) == null) {
            return;
        }
        blurGlassAnimGLProgram.onSurfaceChanged(i, i2);
        this.mGradient2DNoiseAnimGLProgram.onSurfaceChanged(i, i2);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceCreated(Bitmap bitmap) {
        this.mBitmap = bitmap;
        BlurGlassAnimGLProgram blurGlassAnimGLProgram = this.mBlurAnimGLProgram;
        if (blurGlassAnimGLProgram == null || this.mGradient2DNoiseAnimGLProgram == null) {
            return;
        }
        blurGlassAnimGLProgram.onSurfaceCreated(bitmap);
        this.mBlurAnimGLProgram.setRadius(76.0f);
        this.mGradient2DNoiseAnimGLProgram.onSurfaceCreated(bitmap);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void setIdentityMVPMatrix() {
        Gradient2DNoiseBlurAnimGLProgram gradient2DNoiseBlurAnimGLProgram = this.mGradient2DNoiseAnimGLProgram;
        if (gradient2DNoiseBlurAnimGLProgram == null || this.mBlurAnimGLProgram == null) {
            return;
        }
        gradient2DNoiseBlurAnimGLProgram.setIdentityMVPMatrix();
        this.mBlurAnimGLProgram.setIdentityMVPMatrix();
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void setup(Bitmap bitmap, TextureConfig textureConfig) {
        BlurGlassAnimGLProgram blurGlassAnimGLProgram = this.mBlurAnimGLProgram;
        if (blurGlassAnimGLProgram == null || this.mGradient2DNoiseAnimGLProgram == null) {
            return;
        }
        blurGlassAnimGLProgram.setup(bitmap, textureConfig);
        this.mGradient2DNoiseAnimGLProgram.setup(bitmap, textureConfig);
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public void useGLProgram() {
        BlurGlassAnimGLProgram blurGlassAnimGLProgram = this.mBlurAnimGLProgram;
        if (blurGlassAnimGLProgram == null || this.mGradient2DNoiseAnimGLProgram == null) {
            return;
        }
        blurGlassAnimGLProgram.useGLProgram();
        this.mGradient2DNoiseAnimGLProgram.useGLProgram();
    }
}
