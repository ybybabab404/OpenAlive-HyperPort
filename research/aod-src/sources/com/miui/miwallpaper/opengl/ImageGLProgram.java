package com.miui.miwallpaper.opengl;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.EGL14;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.util.Log;
import com.miui.wallpaperglassshader.jar.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes2.dex */
public class ImageGLProgram {
    public static final String TAG = "ImageGLProgram";
    public final Context mContext;
    public int mProgramHandle;
    public int[] programBinaryFormat;
    public ByteBuffer programByteData;

    public ImageGLProgram(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public int getFragmentShader() {
        return R.raw.anim_fragment_shader;
    }

    public final int getProgramHandle(int i, int i2) {
        int iGlCreateProgram = GLES20.glCreateProgram();
        if (iGlCreateProgram == 0) {
            Log.d(TAG, "Can not create OpenGL ES program error message: " + GLUtils.getEGLErrorString(EGL14.eglGetError()));
            return 0;
        }
        GLES20.glAttachShader(iGlCreateProgram, i);
        GLES20.glAttachShader(iGlCreateProgram, i2);
        GLES30.glProgramParameteri(iGlCreateProgram, 33367, 1);
        GLES20.glLinkProgram(iGlCreateProgram);
        IntBuffer intBufferAllocate = IntBuffer.allocate(1);
        GLES20.glGetIntegerv(34814, intBufferAllocate);
        if (intBufferAllocate.array()[0] < 1) {
            Log.d(TAG, "Driver does not support any binary formats; cant cache program");
            return iGlCreateProgram;
        }
        int[] iArr = new int[1];
        GLES30.glGetProgramiv(iGlCreateProgram, 34625, iArr, 0);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(iArr[0]);
        this.programByteData = byteBufferAllocate;
        int[] iArr2 = new int[1];
        this.programBinaryFormat = iArr2;
        GLES30.glGetProgramBinary(iGlCreateProgram, iArr[0], null, 0, iArr2, 0, byteBufferAllocate);
        return iGlCreateProgram;
    }

    public final int getShaderHandle(int i, String str) {
        int iGlCreateShader = GLES20.glCreateShader(i);
        if (iGlCreateShader != 0) {
            GLES20.glShaderSource(iGlCreateShader, str);
            GLES20.glCompileShader(iGlCreateShader);
            return iGlCreateShader;
        }
        Log.d(TAG, "Create shader failed, type=" + i + " error message: " + GLUtils.getEGLErrorString(EGL14.eglGetError()));
        return 0;
    }

    public final String getShaderResource(int i) {
        Resources resources = this.mContext.getResources();
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resources.openRawResource(i)));
            while (true) {
                try {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append(line);
                    sb.append("\n");
                } catch (Throwable th) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
            bufferedReader.close();
        } catch (Resources.NotFoundException | IOException e) {
            Log.d(TAG, "Can not read the shader source", e);
            sb = null;
        }
        return sb == null ? "" : sb.toString();
    }

    public int getUniformHandle(String str) {
        return GLES20.glGetUniformLocation(this.mProgramHandle, str);
    }

    public int getVertexShader() {
        return R.raw.crop_image_wallpaper_vertex_shader;
    }

    public final int loadShaderProgram(int i, int i2) {
        return getProgramHandle(getShaderHandle(35633, getShaderResource(i)), getShaderHandle(35632, getShaderResource(i2)));
    }

    public void useCreateGLProgram() {
        GLES20.glUseProgram(this.mProgramHandle);
    }

    public void useGLProgram() {
        if (this.programByteData == null) {
            useGLProgram(getVertexShader(), getFragmentShader());
            return;
        }
        int iGlCreateProgram = GLES20.glCreateProgram();
        int i = this.programBinaryFormat[0];
        ByteBuffer byteBuffer = this.programByteData;
        GLES30.glProgramBinary(iGlCreateProgram, i, byteBuffer, byteBuffer.array().length);
        this.mProgramHandle = iGlCreateProgram;
        GLES20.glUseProgram(iGlCreateProgram);
    }

    public boolean useGLProgram(int i, int i2) {
        int iLoadShaderProgram = loadShaderProgram(i, i2);
        this.mProgramHandle = iLoadShaderProgram;
        GLES20.glUseProgram(iLoadShaderProgram);
        return true;
    }
}
