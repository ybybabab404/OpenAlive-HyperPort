package com.miui.keyguard.editor.edit.wallpaper;

import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

/* JADX INFO: loaded from: classes2.dex */
public class GLES20ContextFactory implements GLSurfaceView.EGLContextFactory, GLTextureView.EGLContextFactory {
    public static int EGL_CONTEXT_CLIENT_VERSION = 12440;

    @Override // android.opengl.GLSurfaceView.EGLContextFactory, com.miui.keyguard.editor.edit.wallpaper.GLTextureView.EGLContextFactory
    public EGLContext createContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig) {
        return egl10.eglCreateContext(eGLDisplay, eGLConfig, EGL10.EGL_NO_CONTEXT, new int[]{EGL_CONTEXT_CLIENT_VERSION, 2, 12344});
    }

    @Override // android.opengl.GLSurfaceView.EGLContextFactory, com.miui.keyguard.editor.edit.wallpaper.GLTextureView.EGLContextFactory
    public void destroyContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext) {
        egl10.eglDestroyContext(eGLDisplay, eGLContext);
    }
}
