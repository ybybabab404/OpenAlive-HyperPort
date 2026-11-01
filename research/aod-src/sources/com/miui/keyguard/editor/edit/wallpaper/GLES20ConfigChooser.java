package com.miui.keyguard.editor.edit.wallpaper;

import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;

/* JADX INFO: loaded from: classes2.dex */
public class GLES20ConfigChooser implements GLSurfaceView.EGLConfigChooser, GLTextureView.EGLConfigChooser {
    public static int[] s_configAttribs2 = {12324, 4, 12323, 4, 12322, 4, 12352, 4, 12344};
    public int mAlphaSize;
    public int mBlueSize;
    public int mDepthSize;
    public int mGreenSize;
    public int mRedSize;
    public int mStencilSize;

    public GLES20ConfigChooser(int i, int i2, int i3, int i4, int i5, int i6) {
        this.mRedSize = i;
        this.mGreenSize = i2;
        this.mBlueSize = i3;
        this.mAlphaSize = i4;
        this.mDepthSize = i5;
        this.mStencilSize = i6;
    }

    @Override // android.opengl.GLSurfaceView.EGLConfigChooser, com.miui.keyguard.editor.edit.wallpaper.GLTextureView.EGLConfigChooser
    public EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay) {
        int[] iArr = new int[1];
        egl10.eglChooseConfig(eGLDisplay, s_configAttribs2, null, 0, iArr);
        int i = iArr[0];
        if (i <= 0) {
            throw new IllegalArgumentException("No configs match configSpec");
        }
        EGLConfig[] eGLConfigArr = new EGLConfig[i];
        egl10.eglChooseConfig(eGLDisplay, s_configAttribs2, eGLConfigArr, i, iArr);
        return chooseConfig(egl10, eGLDisplay, eGLConfigArr);
    }

    public EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr) {
        int length = eGLConfigArr.length;
        int i = 0;
        while (i < length) {
            EGLConfig eGLConfig = eGLConfigArr[i];
            GLES20ConfigChooser gLES20ConfigChooser = this;
            EGL10 egl11 = egl10;
            EGLDisplay eGLDisplay2 = eGLDisplay;
            int iFindConfigAttrib = gLES20ConfigChooser.findConfigAttrib(egl11, eGLDisplay2, eGLConfig, 12325, 0);
            int iFindConfigAttrib2 = gLES20ConfigChooser.findConfigAttrib(egl11, eGLDisplay2, eGLConfig, 12326, 0);
            if (iFindConfigAttrib >= gLES20ConfigChooser.mDepthSize && iFindConfigAttrib2 >= gLES20ConfigChooser.mStencilSize) {
                int iFindConfigAttrib3 = gLES20ConfigChooser.findConfigAttrib(egl11, eGLDisplay2, eGLConfig, 12324, 0);
                int iFindConfigAttrib4 = gLES20ConfigChooser.findConfigAttrib(egl11, eGLDisplay2, eGLConfig, 12323, 0);
                int iFindConfigAttrib5 = gLES20ConfigChooser.findConfigAttrib(egl11, eGLDisplay2, eGLConfig, 12322, 0);
                int iFindConfigAttrib6 = gLES20ConfigChooser.findConfigAttrib(egl11, eGLDisplay2, eGLConfig, 12321, 0);
                if (iFindConfigAttrib3 == gLES20ConfigChooser.mRedSize && iFindConfigAttrib4 == gLES20ConfigChooser.mGreenSize && iFindConfigAttrib5 == gLES20ConfigChooser.mBlueSize && iFindConfigAttrib6 == gLES20ConfigChooser.mAlphaSize) {
                    return eGLConfig;
                }
            }
            i++;
            this = gLES20ConfigChooser;
            egl10 = egl11;
            eGLDisplay = eGLDisplay2;
        }
        return null;
    }

    public final int findConfigAttrib(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, int i, int i2) {
        int[] iArr = new int[1];
        return egl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, i, iArr) ? iArr[0] : i2;
    }
}
