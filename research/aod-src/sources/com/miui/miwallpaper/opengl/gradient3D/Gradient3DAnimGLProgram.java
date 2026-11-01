package com.miui.miwallpaper.opengl.gradient3D;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;
import com.miui.miwallpaper.opengl.TextureConfig;
import miuix.mipalette.MiPalette;

/* JADX INFO: loaded from: classes2.dex */
public class Gradient3DAnimGLProgram extends GlassAnimGLProgram {
    public Bitmap mCurrentBitmap;
    public int[] mCurrentColor;
    public final Gradient2D_3DAnimGLProgram mGradient2DAnimGLProgram;
    public final Gradient3DNoiseBlurAnimGLProgram mGradient3DNoiseBlurAnimGLProgram;
    public Bitmap mTargetBitmap;
    public int[] mTargetColor;

    public Gradient3DAnimGLProgram(Context context) {
        super(context);
        this.mGradient2DAnimGLProgram = new Gradient2D_3DAnimGLProgram(context);
        this.mGradient3DNoiseBlurAnimGLProgram = new Gradient3DNoiseBlurAnimGLProgram(context);
    }

    public void animateNoiseColorSwatch(float f) {
        Gradient2D_3DAnimGLProgram gradient2D_3DAnimGLProgram = this.mGradient2DAnimGLProgram;
        if (gradient2D_3DAnimGLProgram == null || this.mGradient3DNoiseBlurAnimGLProgram == null) {
            return;
        }
        gradient2D_3DAnimGLProgram.animateNoiseColorSwatch(f);
    }

    public void animateNoisePattern(float f) {
        Gradient2D_3DAnimGLProgram gradient2D_3DAnimGLProgram = this.mGradient2DAnimGLProgram;
        if (gradient2D_3DAnimGLProgram == null || this.mGradient3DNoiseBlurAnimGLProgram == null) {
            return;
        }
        gradient2D_3DAnimGLProgram.animateNoisePattern(f);
        this.mGradient3DNoiseBlurAnimGLProgram.animateNoisePattern(f);
    }

    public void animateNoiseProgress(float f) {
        Gradient2D_3DAnimGLProgram gradient2D_3DAnimGLProgram = this.mGradient2DAnimGLProgram;
        if (gradient2D_3DAnimGLProgram == null || this.mGradient3DNoiseBlurAnimGLProgram == null) {
            return;
        }
        gradient2D_3DAnimGLProgram.animateNoiseProgress(f);
        this.mGradient3DNoiseBlurAnimGLProgram.animateNoiseProgress(f);
    }

    public void animateNoiseSameColorSwatch(float f) {
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void commonDraw(boolean z, int i, boolean z2) {
        Gradient2D_3DAnimGLProgram gradient2D_3DAnimGLProgram = this.mGradient2DAnimGLProgram;
        if (gradient2D_3DAnimGLProgram == null || this.mGradient3DNoiseBlurAnimGLProgram == null) {
            return;
        }
        gradient2D_3DAnimGLProgram.commonDraw(true, i, z2);
        this.mGradient3DNoiseBlurAnimGLProgram.commonDraw(z, this.mGradient2DAnimGLProgram.getFBOTexture(), true);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceCreated(Bitmap bitmap) {
        this.mBitmap = bitmap;
        Gradient2D_3DAnimGLProgram gradient2D_3DAnimGLProgram = this.mGradient2DAnimGLProgram;
        if (gradient2D_3DAnimGLProgram == null || this.mGradient3DNoiseBlurAnimGLProgram == null) {
            return;
        }
        gradient2D_3DAnimGLProgram.onSurfaceCreated(bitmap);
        this.mGradient3DNoiseBlurAnimGLProgram.onSurfaceCreated(bitmap);
    }

    public void setResourceBitmap(Bitmap bitmap, Bitmap bitmap2) {
        this.mCurrentBitmap = bitmap;
        this.mTargetBitmap = bitmap2;
        int mainColorHSV = MiPalette.getMainColorHSV(bitmap);
        int mainColorHSV2 = MiPalette.getMainColorHSV(bitmap2);
        this.mCurrentColor = new int[]{(mainColorHSV & 16711680) >> 16, (mainColorHSV & 65280) >> 8, mainColorHSV & 255};
        this.mTargetColor = new int[]{(mainColorHSV2 & 16711680) >> 16, (mainColorHSV2 & 65280) >> 8, mainColorHSV2 & 255};
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void setup(Bitmap bitmap, TextureConfig textureConfig) {
        Gradient2D_3DAnimGLProgram gradient2D_3DAnimGLProgram = this.mGradient2DAnimGLProgram;
        if (gradient2D_3DAnimGLProgram == null || this.mGradient3DNoiseBlurAnimGLProgram == null) {
            return;
        }
        gradient2D_3DAnimGLProgram.setup(bitmap, textureConfig);
        this.mGradient3DNoiseBlurAnimGLProgram.setup(bitmap, textureConfig);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void updateMVPMatrix(int i, int i2, Rect rect) {
        Gradient3DNoiseBlurAnimGLProgram gradient3DNoiseBlurAnimGLProgram;
        if (this.mGradient2DAnimGLProgram == null || (gradient3DNoiseBlurAnimGLProgram = this.mGradient3DNoiseBlurAnimGLProgram) == null) {
            return;
        }
        gradient3DNoiseBlurAnimGLProgram.updateMVPMatrix(i, i2, rect);
        this.mGradient2DAnimGLProgram.setIdentityMVPMatrix();
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public void useGLProgram() {
        Gradient2D_3DAnimGLProgram gradient2D_3DAnimGLProgram = this.mGradient2DAnimGLProgram;
        if (gradient2D_3DAnimGLProgram == null || this.mGradient3DNoiseBlurAnimGLProgram == null) {
            return;
        }
        gradient2D_3DAnimGLProgram.useGLProgram();
        this.mGradient3DNoiseBlurAnimGLProgram.useGLProgram();
    }
}
