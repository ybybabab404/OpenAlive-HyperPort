package com.miui.miwallpaper.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram;

/* JADX INFO: loaded from: classes2.dex */
public abstract class GlassAnimGLProgram extends AnimImageGLProgram {
    public final String TAG;
    public float mProgress;
    public float mReveal;
    public final int normalizationHeight;
    public final int normalizationWidth;

    public GlassAnimGLProgram(Context context) {
        super(context);
        this.TAG = getClass().getSimpleName();
        this.mReveal = -1.0f;
        this.mProgress = 0.0f;
        this.normalizationWidth = 1080;
        this.normalizationHeight = 2400;
    }

    public int getNowShaderResourceID(Context context, int i, int i2) {
        return context.getPackageName().equals("com.miui.miwallpaper") ? i : i2;
    }

    public int loadTexture(Context context, int i, TextureConfig textureConfig) {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        if (iArr[0] == 0) {
            return 0;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(context.getResources(), i, options);
        if (bitmapDecodeResource == null) {
            GLES20.glDeleteTextures(1, iArr, 0);
            return 0;
        }
        GLES20.glBindTexture(3553, iArr[0]);
        GLES20.glTexParameteri(3553, 10241, textureConfig.TEXTURE_MIN_FILTER);
        GLES20.glTexParameteri(3553, 10240, textureConfig.TEXTURE_MAG_FILTER);
        GLES20.glTexParameteri(3553, 10242, textureConfig.TEXTURE_WRAP_S);
        GLES20.glTexParameteri(3553, 10243, textureConfig.TEXTURE_WRAP_T);
        GLUtils.texImage2D(3553, 0, bitmapDecodeResource, 0);
        bitmapDecodeResource.recycle();
        GLES20.glGenerateMipmap(3553);
        return iArr[0];
    }

    public int loadTexture(Bitmap bitmap) {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        if (iArr[0] == 0 || bitmap.isRecycled()) {
            return 0;
        }
        GLES20.glBindTexture(3553, iArr[0]);
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glTexParameteri(3553, 10240, 9729);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLUtils.texImage2D(3553, 0, bitmap, 0);
        return iArr[0];
    }
}
