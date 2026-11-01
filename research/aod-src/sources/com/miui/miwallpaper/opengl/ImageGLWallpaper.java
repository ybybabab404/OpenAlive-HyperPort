package com.miui.miwallpaper.opengl;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* JADX INFO: loaded from: classes2.dex */
public class ImageGLWallpaper {
    public static final String TAG = "ImageGLWallpaper";
    public final ImageGLProgram mProgram;
    public final FloatBuffer mTextureBuffer;
    public volatile int mTextureId;
    public int mUniTexture;
    public final FloatBuffer mVertexBuffer;
    public static final float[] VERTICES = {-1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f};
    public static final float[] TEXTURES = {0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};

    public ImageGLWallpaper(ImageGLProgram imageGLProgram) {
        this.mProgram = imageGLProgram;
        float[] fArr = VERTICES;
        FloatBuffer floatBufferAsFloatBuffer = ByteBuffer.allocateDirect(fArr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.mVertexBuffer = floatBufferAsFloatBuffer;
        floatBufferAsFloatBuffer.put(fArr);
        floatBufferAsFloatBuffer.position(0);
        float[] fArr2 = TEXTURES;
        FloatBuffer floatBufferAsFloatBuffer2 = ByteBuffer.allocateDirect(fArr2.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.mTextureBuffer = floatBufferAsFloatBuffer2;
        floatBufferAsFloatBuffer2.put(fArr2);
        floatBufferAsFloatBuffer2.position(0);
    }

    public void configTexture(int i, int i2, int i3) {
        this.mTextureId = i;
        GLES20.glBindTexture(3553, i);
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glTexParameteri(3553, 10240, 9729);
        GLES20.glTexParameteri(3553, 10242, i3);
        GLES20.glTexParameteri(3553, 10243, i2);
        GLES20.glBindTexture(3553, 0);
    }

    public void draw() {
        GLES20.glDrawArrays(4, 0, VERTICES.length / 2);
    }

    public final boolean isRGB565Supported() {
        int[] iArr = new int[1];
        GLES20.glGetIntegerv(34466, iArr, 0);
        int i = iArr[0];
        int[] iArr2 = new int[i];
        GLES20.glGetIntegerv(34467, iArr2, 0);
        for (int i2 = 0; i2 < i; i2++) {
            if (iArr2[i2] == 36194) {
                return true;
            }
        }
        Log.d(TAG, "opengl not supported RGB565");
        return false;
    }

    public void setUpUniformAndAttr() {
        setupAttributes();
        setupUniforms();
    }

    public void setup(Bitmap bitmap, TextureConfig textureConfig) {
        setupAttributes();
        setupUniforms();
        setupTexture(bitmap, textureConfig);
    }

    public void setupAttributes() {
        this.mVertexBuffer.position(0);
        GLES20.glVertexAttribPointer(1, 2, 5126, false, 0, (Buffer) this.mVertexBuffer);
        GLES20.glEnableVertexAttribArray(1);
        this.mTextureBuffer.position(0);
        GLES20.glVertexAttribPointer(0, 2, 5126, false, 0, (Buffer) this.mTextureBuffer);
        GLES20.glEnableVertexAttribArray(0);
    }

    public final void setupTexture(Bitmap bitmap, TextureConfig textureConfig) {
        int[] iArr = new int[1];
        if (bitmap == null || bitmap.isRecycled()) {
            Log.w(TAG, "setupTexture: invalid bitmap");
            return;
        }
        GLES20.glGenTextures(1, iArr, 0);
        int i = iArr[0];
        if (i == 0) {
            Log.w(TAG, "setupTexture: glGenTextures() failed");
            return;
        }
        try {
            GLES20.glBindTexture(3553, i);
            Bitmap.Config config = bitmap.getConfig();
            Bitmap.Config config2 = Bitmap.Config.ARGB_8888;
            if (config != config2 && !isRGB565Supported()) {
                bitmap = bitmap.copy(config2, true);
            }
            GLUtils.texImage2D(3553, 0, bitmap, 0);
            GLES20.glTexParameteri(3553, 10241, textureConfig.TEXTURE_MIN_FILTER);
            GLES20.glTexParameteri(3553, 10240, textureConfig.TEXTURE_MAG_FILTER);
            GLES20.glTexParameteri(3553, 10242, textureConfig.TEXTURE_WRAP_S);
            GLES20.glTexParameteri(3553, 10243, textureConfig.TEXTURE_WRAP_T);
            GLES20.glGenerateMipmap(3553);
            this.mTextureId = iArr[0];
        } catch (IllegalArgumentException e) {
            Log.w(TAG, "Failed uploading texture: " + e.getLocalizedMessage());
        }
    }

    public void setupUniforms() {
        this.mUniTexture = this.mProgram.getUniformHandle("uUITex");
    }

    public void updateTexture(Bitmap bitmap, TextureConfig textureConfig) {
        setupTexture(bitmap, textureConfig);
    }

    public void useTexture(int i, boolean z) {
        GLES20.glActiveTexture(33984);
        if (!z) {
            i = this.mTextureId;
        }
        GLES20.glBindTexture(3553, i);
        GLES20.glUniform1i(this.mUniTexture, 0);
    }
}
