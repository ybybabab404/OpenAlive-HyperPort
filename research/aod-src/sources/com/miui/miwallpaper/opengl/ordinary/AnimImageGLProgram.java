package com.miui.miwallpaper.opengl.ordinary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;
import com.miui.miwallpaper.opengl.ImageGLProgram;
import com.miui.miwallpaper.opengl.TextureConfig;
import com.miui.miwallpaper.util.BitmapUtils;
import com.miui.miwallpaper.util.EffectSystemSettingUtils;
import com.miui.miwallpaper.util.ScreenUtils;
import effect_engine.utils.FBO;
import java.util.Arrays;

/* JADX INFO: loaded from: classes2.dex */
public class AnimImageGLProgram extends ImageGLProgram {
    public final float SCALE_FACTOR;
    public final String TAG;
    public final boolean isFold;
    public final AnimImageGLWallpaper mAnimImageGLWallpaper;
    public Bitmap mBitmap;
    public volatile int mDark;
    public volatile int mDarken;
    public int mGlassId;
    public boolean mIsTurnOnBlur;
    public float[] mMVPMatrix;
    public FBO mResultFBO;
    public volatile float mRevealValue;
    public float mScaleX;
    public float mScaleY;
    public Rect mScreenSize;
    public float mTranslateX;
    public float mTranslateY;

    public AnimImageGLProgram(Context context) {
        super(context);
        this.TAG = getClass().getSimpleName();
        this.isFold = EffectSystemSettingUtils.IS_FOLD_DEVICE;
        this.SCALE_FACTOR = 0.85f;
        this.mMVPMatrix = new float[16];
        this.mGlassId = 0;
        this.mAnimImageGLWallpaper = getImageGLWallpaper();
        this.mScreenSize = ScreenUtils.getScreenRect(context);
    }

    public final float[] changeMvpMatrixCrop(float f, float f2, float f3, float f4) {
        float[] fArr = new float[16];
        float f5 = f / f2;
        float f6 = f3 / f4;
        Matrix.setIdentityM(fArr, 0);
        if (f5 == f6) {
            Matrix.scaleM(fArr, 0, 1.0f, 1.0f, 1.0f);
        } else if (f5 < f6) {
            Matrix.scaleM(fArr, 0, 1.0f, (f2 / (f / f3)) / f4, 1.0f);
        } else {
            Matrix.scaleM(fArr, 0, (f / (f2 / f4)) / f3, 1.0f, 1.0f);
        }
        Log.d(this.TAG, "bitmapWidth = " + f + ", bitmapHeight = " + f2 + ", surfaceWidth = " + f3 + ", surfaceHeight = " + f4 + ", matrix = " + Arrays.toString(fArr));
        return fArr;
    }

    public void commonDraw(boolean z, int i, boolean z2) {
        FBO fbo;
        FBO fbo2;
        useCreateGLProgram();
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(16384);
        GLES20.glUniformMatrix4fv(this.mAnimImageGLWallpaper.uMVPMatrix, 1, false, this.mMVPMatrix, 0);
        GLES20.glUniform1f(this.mAnimImageGLWallpaper.uReveal, this.mRevealValue);
        GLES20.glUniform1i(this.mAnimImageGLWallpaper.uDarken, this.mDarken);
        GLES20.glUniform1i(this.mAnimImageGLWallpaper.uDarkMode, this.mDark);
        GLES20.glUniform2f(this.mAnimImageGLWallpaper.uScaleCenter, 0.0f, 0.0f);
        int i2 = this.mAnimImageGLWallpaper.uScaleOffset;
        float f = this.mScaleX;
        if (f <= 0.0f) {
            f = 1.0f;
        }
        float f2 = this.mScaleY;
        GLES20.glUniform4f(i2, f, f2 > 0.0f ? f2 : 1.0f, this.mTranslateX, this.mTranslateY);
        useTexture(i, z2);
        glProgramDrawEffectUniform(z, i, z2);
        if (z && (fbo2 = this.mResultFBO) != null) {
            fbo2.bind();
        }
        draw();
        if (!z || (fbo = this.mResultFBO) == null) {
            return;
        }
        fbo.unbind();
    }

    public void configTexture(int i, int i2, int i3) {
        this.mAnimImageGLWallpaper.configTexture(i, i2, i3);
    }

    public void disposeMainTexture() {
        if (this.mAnimImageGLWallpaper.mTextureId != 0) {
            GLES20.glDeleteTextures(1, new int[]{this.mAnimImageGLWallpaper.mTextureId}, 0);
        }
        FBO fbo = this.mResultFBO;
        if (fbo != null) {
            fbo.destroy();
        }
    }

    public void disposeTexture() {
        disposeMainTexture();
    }

    public void draw() {
        this.mAnimImageGLWallpaper.draw();
    }

    public int getFBOTexture() {
        FBO fbo = this.mResultFBO;
        if (fbo != null) {
            return fbo.getFboTexture();
        }
        return 0;
    }

    public AnimImageGLWallpaper getImageGLWallpaper() {
        return new AnimImageGLWallpaper(this);
    }

    public float[] getMVPMatrix() {
        return this.mMVPMatrix;
    }

    public void glProgramDrawEffectUniform(boolean z, int i, boolean z2) {
        GLES20.glUniform2f(this.mAnimImageGLWallpaper.uResolution, this.mScreenSize.width(), this.mScreenSize.height());
        if (z) {
            GLES20.glUniform1i(this.mAnimImageGLWallpaper.uIsNeedReverse, 1);
        } else {
            GLES20.glUniform1i(this.mAnimImageGLWallpaper.uIsNeedReverse, 0);
        }
    }

    public final boolean isEffectProgram() {
        return !AnimImageGLProgram.class.equals(getClass());
    }

    public void makeFrosting(Bitmap bitmap, int i) {
        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, Math.max(1, bitmap.getWidth() / 8), Math.max(1, bitmap.getHeight() / 8), true);
        Bitmap bitmapDoBlur = BitmapUtils.doBlur(bitmapCreateScaledBitmap, i);
        setup(bitmapDoBlur, new TextureConfig(0, true));
        BitmapUtils.recycleBitmap(bitmapCreateScaledBitmap);
        BitmapUtils.recycleBitmap(bitmapDoBlur);
    }

    public void onDrawFrame() {
        commonDraw(false, this.mAnimImageGLWallpaper.mTextureId, false);
    }

    public void onDrawFrameToFBO() {
        commonDraw(true, 0, false);
    }

    public void onSurfaceChanged(int i, int i2) {
        this.mScreenSize = new Rect(0, 0, i, i2);
    }

    public void onSurfaceCreated(Bitmap bitmap) {
        if (isEffectProgram()) {
            this.mBitmap = bitmap;
            useCreateGLProgram();
        }
    }

    public void setIdentityMVPMatrix() {
        Matrix.setIdentityM(this.mMVPMatrix, 0);
    }

    public void setScale(float f, float f2) {
        this.mScaleX = f;
        this.mScaleY = f2;
    }

    public void setTranslate(float f, float f2) {
        this.mTranslateX = f;
        this.mTranslateY = f2;
    }

    public void setUpUniformAndAttr() {
        AnimImageGLWallpaper animImageGLWallpaper = this.mAnimImageGLWallpaper;
        if (animImageGLWallpaper != null) {
            animImageGLWallpaper.setUpUniformAndAttr();
        }
    }

    public void setup(Bitmap bitmap, TextureConfig textureConfig) {
        useCreateGLProgram();
        this.mAnimImageGLWallpaper.setup(bitmap, textureConfig);
    }

    public void updateMVPMatrix(int i, int i2, Rect rect) {
        this.mMVPMatrix = changeMvpMatrixCrop(rect.width(), rect.height(), i, i2);
    }

    public void updateTexture(Bitmap bitmap, TextureConfig textureConfig) {
        updateMVPMatrix(this.mScreenSize.width(), this.mScreenSize.height(), new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
        this.mAnimImageGLWallpaper.updateTexture(bitmap, textureConfig);
    }

    public void useTexture(int i, boolean z) {
        useCreateGLProgram();
        this.mAnimImageGLWallpaper.useTexture(i, z);
    }
}
