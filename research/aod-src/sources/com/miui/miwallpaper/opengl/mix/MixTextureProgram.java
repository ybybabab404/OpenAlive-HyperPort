package com.miui.miwallpaper.opengl.mix;

import android.graphics.Bitmap;
import android.graphics.Rect;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;
import com.miui.miwallpaper.opengl.TextureConfig;
import com.miui.miwallpaper.opengl.guass.BlurGlassAnimGLProgram;

/* JADX INFO: loaded from: classes2.dex */
public class MixTextureProgram extends GlassAnimGLProgram {
    public boolean IS_HIGH_END;
    public BlurGlassAnimGLProgram mBlurGlassAnimGLProgram;
    public MixScaleTextureProgram mMixScaleGLPorgram;

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void commonDraw(boolean z, int i, boolean z2) {
        MixScaleTextureProgram mixScaleTextureProgram = this.mMixScaleGLPorgram;
        if (mixScaleTextureProgram == null || this.mBlurGlassAnimGLProgram == null) {
            return;
        }
        if (!this.IS_HIGH_END) {
            mixScaleTextureProgram.commonDraw(z, i, z2);
        } else {
            mixScaleTextureProgram.onDrawFrameToFBO();
            this.mBlurGlassAnimGLProgram.commonDraw(z, this.mMixScaleGLPorgram.getFBOTexture(), true);
        }
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void disposeTexture() {
        MixScaleTextureProgram mixScaleTextureProgram = this.mMixScaleGLPorgram;
        if (mixScaleTextureProgram != null) {
            mixScaleTextureProgram.disposeTexture();
            this.mMixScaleGLPorgram = null;
        }
        BlurGlassAnimGLProgram blurGlassAnimGLProgram = this.mBlurGlassAnimGLProgram;
        if (blurGlassAnimGLProgram != null) {
            blurGlassAnimGLProgram.disposeTexture();
            this.mBlurGlassAnimGLProgram = null;
        }
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceChanged(int i, int i2) {
        BlurGlassAnimGLProgram blurGlassAnimGLProgram = this.mBlurGlassAnimGLProgram;
        if (blurGlassAnimGLProgram != null && this.IS_HIGH_END) {
            blurGlassAnimGLProgram.onSurfaceChanged(i, i2);
        }
        MixScaleTextureProgram mixScaleTextureProgram = this.mMixScaleGLPorgram;
        if (mixScaleTextureProgram != null) {
            mixScaleTextureProgram.onSurfaceChanged(i, i2);
        }
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceCreated(Bitmap bitmap) {
        this.mBitmap = bitmap;
        BlurGlassAnimGLProgram blurGlassAnimGLProgram = this.mBlurGlassAnimGLProgram;
        if (blurGlassAnimGLProgram != null && this.IS_HIGH_END) {
            blurGlassAnimGLProgram.onSurfaceCreated(bitmap);
            this.mBlurGlassAnimGLProgram.setRadius(0.0f);
        }
        MixScaleTextureProgram mixScaleTextureProgram = this.mMixScaleGLPorgram;
        if (mixScaleTextureProgram != null) {
            mixScaleTextureProgram.onSurfaceCreated(bitmap);
        }
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void setup(Bitmap bitmap, TextureConfig textureConfig) {
        BlurGlassAnimGLProgram blurGlassAnimGLProgram = this.mBlurGlassAnimGLProgram;
        if (blurGlassAnimGLProgram != null && this.IS_HIGH_END) {
            blurGlassAnimGLProgram.setup(bitmap, textureConfig);
        }
        MixScaleTextureProgram mixScaleTextureProgram = this.mMixScaleGLPorgram;
        if (mixScaleTextureProgram != null) {
            mixScaleTextureProgram.setup(bitmap, textureConfig);
        }
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void updateMVPMatrix(int i, int i2, Rect rect) {
        MixScaleTextureProgram mixScaleTextureProgram = this.mMixScaleGLPorgram;
        if (mixScaleTextureProgram == null || this.mBlurGlassAnimGLProgram == null) {
            return;
        }
        mixScaleTextureProgram.updateMVPMatrix(i, i2, rect);
        this.mBlurGlassAnimGLProgram.updateMVPMatrix(i, i2, rect);
    }

    public synchronized void updateMixtextureStatus(float f, float f2, boolean z) {
        MixScaleTextureProgram mixScaleTextureProgram = this.mMixScaleGLPorgram;
        if (mixScaleTextureProgram != null && this.mBlurGlassAnimGLProgram != null) {
            mixScaleTextureProgram.updateMixtextureFactor(f);
            if (this.IS_HIGH_END) {
                this.mBlurGlassAnimGLProgram.setRadius(f2);
            }
        }
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public void useGLProgram() {
        MixScaleTextureProgram mixScaleTextureProgram = this.mMixScaleGLPorgram;
        if (mixScaleTextureProgram != null) {
            mixScaleTextureProgram.useGLProgram();
        }
        BlurGlassAnimGLProgram blurGlassAnimGLProgram = this.mBlurGlassAnimGLProgram;
        if (blurGlassAnimGLProgram == null || !this.IS_HIGH_END) {
            return;
        }
        blurGlassAnimGLProgram.useGLProgram();
    }
}
