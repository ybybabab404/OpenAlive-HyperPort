package com.miui.keyguard.editor.edit.wallpaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.opengl.GLDebugHelper;
import android.opengl.GLSurfaceView;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.miui.keyguard.editor.EditorApplicationProxy;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.data.bean.WallpaperPositionInfo;
import com.miui.keyguard.editor.data.bean.WallpaperTypeInfo;
import com.miui.keyguard.editor.edit.base.WallpaperEditor;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil;
import com.miui.keyguard.editor.utils.Process;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.utils.WideColorGamutManager;
import com.miui.miwallpaper.GlassShaderUtils;
import com.miui.miwallpaper.opengl.AnimImageWallpaperRenderer;
import com.miui.miwallpaper.opengl.GlassRenderer;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;
import miuix.animation.listener.TransitionListener;
import miuix.appcompat.app.AppCompatActivity;

/* JADX INFO: loaded from: classes2.dex */
public class GLTextureView extends TextureView implements TextureView.SurfaceTextureListener, IWallpaperLayer, GestureListener, ViewTreeObserver.OnGlobalLayoutListener {
    public static final boolean DEBUG;
    public static final boolean LOG_ATTACH_DETACH;
    public static final boolean LOG_EGL;
    public static final boolean LOG_PAUSE_RESUME;
    public static final boolean LOG_RENDERER;
    public static final boolean LOG_RENDERER_DRAW_FRAME;
    public static final boolean LOG_SURFACE;
    public static final boolean LOG_THREADS;
    public int currentMagicType;
    public GestureManager gestureManager;
    public GlassRenderer glassRender;
    public boolean mAutoLifecycleEnabled;
    public Matrix mCachedMatrix;
    public int mDebugFlags;
    public boolean mDetached;
    public EGLConfigChooser mEGLConfigChooser;
    public int mEGLContextClientVersion;
    public EGLContextFactory mEGLContextFactory;
    public EGLWindowSurfaceFactory mEGLWindowSurfaceFactory;
    public GLThread mGLThread;
    public GLWrapper mGLWrapper;
    public final DefaultLifecycleObserver mLifecycleObserver;
    public boolean mLifecycleObserverRegistered;
    public boolean mPreserveEGLContextOnPause;
    public GLSurfaceView.Renderer mRenderer;
    public AtomicBoolean mTextureUpdated;
    public final HashSet mTextureUpdatedListeners;
    public final WeakReference mThisWeakRef;
    public boolean needHideAnim;
    public Boolean scaleable;
    public WallpaperCallback wallpaperCallback;
    public HashSet wallpaperChangedListenerSet;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.wallpaper.GLTextureView$3, reason: invalid class name */
    public class AnonymousClass3 extends TransitionListener {
        public final /* synthetic */ int val$magicType;

        /* JADX INFO: renamed from: $r8$lambda$CLH1rTtoa-foIuNdFauBreRDsG0, reason: not valid java name */
        public static /* synthetic */ void m790$r8$lambda$CLH1rTtoafoIuNdFauBreRDsG0(AnonymousClass3 anonymousClass3, int i) {
            GLTextureView.this.destroyEglSurfaceAndContext();
            GLTextureView.this.createEglSurfaceAndContext();
            GLTextureView.this.glassRender.setEffectType(i);
            GLTextureView.this.requestRender();
        }

        public AnonymousClass3(int i) {
            this.val$magicType = i;
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            if (GLTextureView.this.shouldHideAnim(this.val$magicType)) {
                GLTextureView gLTextureView = GLTextureView.this;
                final int i = this.val$magicType;
                gLTextureView.queueEvent(new Runnable() { // from class: com.miui.keyguard.editor.edit.wallpaper.GLTextureView$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        GLTextureView.AnonymousClass3.m790$r8$lambda$CLH1rTtoafoIuNdFauBreRDsG0(this.f$0, i);
                    }
                });
            }
        }
    }

    public abstract class BaseConfigChooser implements EGLConfigChooser {
        public int[] mConfigSpec;

        public BaseConfigChooser(int[] iArr) {
            this.mConfigSpec = filterConfigSpec(iArr);
        }

        @Override // com.miui.keyguard.editor.edit.wallpaper.GLTextureView.EGLConfigChooser
        public EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay) {
            int[] iArr = new int[1];
            if (!egl10.eglChooseConfig(eGLDisplay, this.mConfigSpec, null, 0, iArr)) {
                throw new IllegalArgumentException("eglChooseConfig failed");
            }
            int i = iArr[0];
            if (i <= 0) {
                throw new IllegalArgumentException("No configs match configSpec");
            }
            EGLConfig[] eGLConfigArr = new EGLConfig[i];
            if (!egl10.eglChooseConfig(eGLDisplay, this.mConfigSpec, eGLConfigArr, i, iArr)) {
                throw new IllegalArgumentException("eglChooseConfig#2 failed");
            }
            EGLConfig eGLConfigChooseConfig = chooseConfig(egl10, eGLDisplay, eGLConfigArr);
            if (eGLConfigChooseConfig != null) {
                return eGLConfigChooseConfig;
            }
            throw new IllegalArgumentException("No config chosen");
        }

        public abstract EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr);

        public final int[] filterConfigSpec(int[] iArr) {
            if (GLTextureView.this.mEGLContextClientVersion != 2 && GLTextureView.this.mEGLContextClientVersion != 3) {
                return iArr;
            }
            int length = iArr.length;
            int[] iArr2 = new int[length + 2];
            int i = length - 1;
            System.arraycopy(iArr, 0, iArr2, 0, i);
            iArr2[i] = 12352;
            if (GLTextureView.this.mEGLContextClientVersion == 2) {
                iArr2[length] = 4;
            } else {
                iArr2[length] = 64;
            }
            iArr2[length + 1] = 12344;
            return iArr2;
        }
    }

    public class ComponentSizeChooser extends BaseConfigChooser {
        public int mAlphaSize;
        public int mBlueSize;
        public int mDepthSize;
        public int mGreenSize;
        public int mRedSize;
        public int mStencilSize;
        public int[] mValue;

        public ComponentSizeChooser(int i, int i2, int i3, int i4, int i5, int i6) {
            super(new int[]{12324, i, 12323, i2, 12322, i3, 12321, i4, 12325, i5, 12326, i6, 12344});
            this.mValue = new int[1];
            this.mRedSize = i;
            this.mGreenSize = i2;
            this.mBlueSize = i3;
            this.mAlphaSize = i4;
            this.mDepthSize = i5;
            this.mStencilSize = i6;
        }

        private int findConfigAttrib(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, int i, int i2) {
            return egl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, i, this.mValue) ? this.mValue[0] : i2;
        }

        @Override // com.miui.keyguard.editor.edit.wallpaper.GLTextureView.BaseConfigChooser
        public EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr) {
            int length = eGLConfigArr.length;
            int i = 0;
            while (i < length) {
                EGLConfig eGLConfig = eGLConfigArr[i];
                ComponentSizeChooser componentSizeChooser = this;
                EGL10 egl11 = egl10;
                EGLDisplay eGLDisplay2 = eGLDisplay;
                int iFindConfigAttrib = componentSizeChooser.findConfigAttrib(egl11, eGLDisplay2, eGLConfig, 12325, 0);
                int iFindConfigAttrib2 = componentSizeChooser.findConfigAttrib(egl11, eGLDisplay2, eGLConfig, 12326, 0);
                if (iFindConfigAttrib >= componentSizeChooser.mDepthSize && iFindConfigAttrib2 >= componentSizeChooser.mStencilSize) {
                    int iFindConfigAttrib3 = componentSizeChooser.findConfigAttrib(egl11, eGLDisplay2, eGLConfig, 12324, 0);
                    int iFindConfigAttrib4 = componentSizeChooser.findConfigAttrib(egl11, eGLDisplay2, eGLConfig, 12323, 0);
                    int iFindConfigAttrib5 = componentSizeChooser.findConfigAttrib(egl11, eGLDisplay2, eGLConfig, 12322, 0);
                    int iFindConfigAttrib6 = componentSizeChooser.findConfigAttrib(egl11, eGLDisplay2, eGLConfig, 12321, 0);
                    if (iFindConfigAttrib3 == componentSizeChooser.mRedSize && iFindConfigAttrib4 == componentSizeChooser.mGreenSize && iFindConfigAttrib5 == componentSizeChooser.mBlueSize && iFindConfigAttrib6 == componentSizeChooser.mAlphaSize) {
                        return eGLConfig;
                    }
                }
                i++;
                this = componentSizeChooser;
                egl10 = egl11;
                eGLDisplay = eGLDisplay2;
            }
            return null;
        }
    }

    public class DefaultContextFactory implements EGLContextFactory {
        public int EGL_CONTEXT_CLIENT_VERSION;

        public DefaultContextFactory() {
            this.EGL_CONTEXT_CLIENT_VERSION = 12440;
        }

        @Override // com.miui.keyguard.editor.edit.wallpaper.GLTextureView.EGLContextFactory
        public EGLContext createContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig) {
            int[] iArr = {this.EGL_CONTEXT_CLIENT_VERSION, GLTextureView.this.mEGLContextClientVersion, 12344};
            EGLContext eGLContext = EGL10.EGL_NO_CONTEXT;
            if (GLTextureView.this.mEGLContextClientVersion == 0) {
                iArr = null;
            }
            return egl10.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, iArr);
        }

        @Override // com.miui.keyguard.editor.edit.wallpaper.GLTextureView.EGLContextFactory
        public void destroyContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext) {
            if (egl10.eglDestroyContext(eGLDisplay, eGLContext)) {
                return;
            }
            Log.e("Keyguard-Editor-GLTextureView", "DefaultContextFactory display:" + eGLDisplay + " context: " + eGLContext);
            if (GLTextureView.LOG_THREADS) {
                Log.i("Keyguard-Editor-GLTextureView", "DefaultContextFactory tid=" + Thread.currentThread().getId());
            }
            EglHelper.throwEglException("eglDestroyContex", egl10.eglGetError());
        }
    }

    public static class DefaultWindowSurfaceFactory implements EGLWindowSurfaceFactory {
        public DefaultWindowSurfaceFactory() {
        }

        @Override // com.miui.keyguard.editor.edit.wallpaper.GLTextureView.EGLWindowSurfaceFactory
        public EGLSurface createWindowSurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj, int[] iArr) {
            try {
                try {
                    return egl10.eglCreateWindowSurface(eGLDisplay, eGLConfig, obj, iArr);
                } catch (Exception unused) {
                    return null;
                }
            } catch (IllegalArgumentException e) {
                Log.e("Keyguard-Editor-GLTextureView", "eglCreateWindowSurface", e);
                KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperBlackScreen("GLTextureView.createWindowSurface_IllegalArgumentException", null);
                return null;
            } catch (UnsupportedOperationException e2) {
                Log.e("Keyguard-Editor-GLTextureView", "eglCreateWindowSurface", e2);
                KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperBlackScreen("GLTextureView.createWindowSurface_UnsupportedOperationException", null);
                return null;
            }
        }

        @Override // com.miui.keyguard.editor.edit.wallpaper.GLTextureView.EGLWindowSurfaceFactory
        public void destroySurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLSurface eGLSurface) {
            egl10.eglDestroySurface(eGLDisplay, eGLSurface);
        }
    }

    public interface EGLConfigChooser {
        EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay);
    }

    public interface EGLContextFactory {
        EGLContext createContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig);

        void destroyContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext);
    }

    public interface EGLWindowSurfaceFactory {
        EGLSurface createWindowSurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj, int[] iArr);

        void destroySurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLSurface eGLSurface);
    }

    public static class EglHelper {
        public static final Object sEglDestroyLock = new Object();
        public EGL10 mEgl;
        public EGLConfig mEglConfig;
        public EGLContext mEglContext;
        public EGLDisplay mEglDisplay;
        public EGLSurface mEglSurface;
        public final Set mExts = new HashSet();
        public WeakReference mGLTextureViewWeakRef;

        public EglHelper(WeakReference weakReference) {
            this.mGLTextureViewWeakRef = weakReference;
        }

        public static String eglGetErrorString(int i) {
            switch (i) {
                case 12288:
                    return "EGL_SUCCESS";
                case 12289:
                    return "EGL_NOT_INITIALIZED";
                case 12290:
                    return "EGL_BAD_ACCESS";
                case 12291:
                    return "EGL_BAD_ALLOC";
                case 12292:
                    return "EGL_BAD_ATTRIBUTE";
                case 12293:
                    return "EGL_BAD_CONFIG";
                case 12294:
                    return "EGL_BAD_CONTEXT";
                case 12295:
                    return "EGL_BAD_CURRENT_SURFACE";
                case 12296:
                    return "EGL_BAD_DISPLAY";
                case 12297:
                    return "EGL_BAD_MATCH";
                case 12298:
                    return "EGL_BAD_NATIVE_PIXMAP";
                case 12299:
                    return "EGL_BAD_NATIVE_WINDOW";
                case 12300:
                    return "EGL_BAD_PARAMETER";
                case 12301:
                    return "EGL_BAD_SURFACE";
                case 12302:
                    return "EGL_CONTEXT_LOST";
                default:
                    return "0x" + Integer.toHexString(i);
            }
        }

        public static String formatEglError(String str, int i) {
            return str + " failed: " + eglGetErrorString(i);
        }

        public static void logEglErrorAsWarning(String str, String str2, int i) {
            Log.w(str, formatEglError(str2, i));
        }

        public static void throwEglException(String str, int i) {
            String eglError = formatEglError(str, i);
            if (GLTextureView.LOG_THREADS) {
                Log.e("Keyguard-Editor-GLTextureView", "EglHelper throwEglException tid=" + Thread.currentThread().getId() + " " + eglError);
            }
            throw new RuntimeException(eglError);
        }

        public final boolean checkExtensionCapability(String str) {
            return this.mExts.contains(str);
        }

        public GL createGL() {
            GL gl = this.mEglContext.getGL();
            GLTextureView gLTextureView = (GLTextureView) this.mGLTextureViewWeakRef.get();
            if (gLTextureView != null) {
                if (gLTextureView.mGLWrapper != null) {
                    gl = gLTextureView.mGLWrapper.wrap(gl);
                }
                if ((gLTextureView.mDebugFlags & 3) != 0) {
                    return GLDebugHelper.wrap(gl, (gLTextureView.mDebugFlags & 1) == 0 ? 0 : 1, (gLTextureView.mDebugFlags & 2) != 0 ? new LogWriter() : null);
                }
            }
            return gl;
        }

        public boolean createSurface() {
            if (GLTextureView.LOG_EGL) {
                Log.w("Keyguard-Editor-GLTextureView", "EglHelper createSurface() tid=" + Thread.currentThread().getId());
            }
            if (this.mEgl == null) {
                throw new RuntimeException("egl not initialized");
            }
            if (this.mEglDisplay == null) {
                throw new RuntimeException("eglDisplay not initialized");
            }
            if (this.mEglConfig == null) {
                throw new RuntimeException("mEglConfig not initialized");
            }
            destroySurfaceImp();
            GLTextureView gLTextureView = (GLTextureView) this.mGLTextureViewWeakRef.get();
            if (gLTextureView != null) {
                this.mEglSurface = gLTextureView.mEGLWindowSurfaceFactory.createWindowSurface(this.mEgl, this.mEglDisplay, this.mEglConfig, gLTextureView.getSurfaceTexture(), initExts());
            } else {
                this.mEglSurface = null;
                Log.e("Keyguard-Editor-GLTextureView", "createSurface: texture view is null");
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperBlackScreen("GLTextureView.createSurface_textureView_null", null);
                } catch (Exception unused) {
                }
            }
            EGLSurface eGLSurface = this.mEglSurface;
            if (eGLSurface == null || eGLSurface == EGL10.EGL_NO_SURFACE) {
                if (this.mEgl.eglGetError() == 12299) {
                    Log.e("Keyguard-Editor-GLTextureView", "EglHelper createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
                }
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperBlackScreen("GLTextureView.createSurface_egl_no_surface", null);
                } catch (Exception unused2) {
                }
                return false;
            }
            if (this.mEgl.eglMakeCurrent(this.mEglDisplay, eGLSurface, eGLSurface, this.mEglContext)) {
                return true;
            }
            logEglErrorAsWarning("Keyguard-Editor-GLTextureView", "EGLHelper eglMakeCurrent", this.mEgl.eglGetError());
            try {
                KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperBlackScreen("GLTextureView.createSurface_eglMakeCurrent_failed", null);
            } catch (Exception unused3) {
            }
            return false;
        }

        public void destroyEglSource() {
            synchronized (sEglDestroyLock) {
                try {
                    if (this.mEgl == null || this.mEglDisplay == null || this.mEglContext == null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("destroyEglSource mEglContext:");
                        sb.append(this.mEglContext != null);
                        Log.d("Keyguard-Editor-GLTextureView", sb.toString());
                    } else {
                        GLTextureView gLTextureView = (GLTextureView) this.mGLTextureViewWeakRef.get();
                        if (gLTextureView != null) {
                            gLTextureView.mEGLContextFactory.destroyContext(this.mEgl, this.mEglDisplay, this.mEglContext);
                        }
                        this.mEglContext = null;
                        destroySurface();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void destroySurface() {
            if (GLTextureView.LOG_EGL) {
                Log.w("Keyguard-Editor-GLTextureView", "EglHelper destroySurface() tid=" + Thread.currentThread().getId());
            }
            destroySurfaceImp();
        }

        public final void destroySurfaceImp() {
            EGLSurface eGLSurface;
            EGLSurface eGLSurface2 = this.mEglSurface;
            if (eGLSurface2 == null || eGLSurface2 == (eGLSurface = EGL10.EGL_NO_SURFACE)) {
                return;
            }
            this.mEgl.eglMakeCurrent(this.mEglDisplay, eGLSurface, eGLSurface, EGL10.EGL_NO_CONTEXT);
            GLTextureView gLTextureView = (GLTextureView) this.mGLTextureViewWeakRef.get();
            if (gLTextureView != null) {
                gLTextureView.mEGLWindowSurfaceFactory.destroySurface(this.mEgl, this.mEglDisplay, this.mEglSurface);
            }
            this.mEglSurface = null;
        }

        public void finish() {
            if (GLTextureView.LOG_EGL) {
                Log.w("Keyguard-Editor-GLTextureView", "EglHelper finish() tid=" + Thread.currentThread().getId());
            }
            if (this.mEglContext != null) {
                GLTextureView gLTextureView = (GLTextureView) this.mGLTextureViewWeakRef.get();
                if (gLTextureView != null) {
                    gLTextureView.mEGLContextFactory.destroyContext(this.mEgl, this.mEglDisplay, this.mEglContext);
                }
                this.mEglContext = null;
            }
            EGLDisplay eGLDisplay = this.mEglDisplay;
            if (eGLDisplay != null) {
                this.mEgl.eglTerminate(eGLDisplay);
                this.mEglDisplay = null;
            }
        }

        public int getWcgCapability() {
            if (checkExtensionCapability("EGL_EXT_gl_colorspace_display_bt2020_passthrough")) {
                return 13457;
            }
            return checkExtensionCapability("EGL_EXT_gl_colorspace_display_p3_passthrough") ? 13456 : 0;
        }

        public void initEglContext() {
            GLTextureView gLTextureView = (GLTextureView) this.mGLTextureViewWeakRef.get();
            if (gLTextureView == null) {
                this.mEglConfig = null;
                this.mEglContext = null;
                Log.d("Keyguard-Editor-GLTextureView", "initEglContextFailed");
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperBlackScreen("GLTextureView.initEglContext_view_gc", null);
                    return;
                } catch (Exception unused) {
                    return;
                }
            }
            if (this.mEglDisplay == null) {
                this.mEglDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            }
            try {
                this.mEglConfig = gLTextureView.mEGLConfigChooser.chooseConfig(this.mEgl, this.mEglDisplay);
            } catch (Exception e) {
                Log.e("Keyguard-Editor-GLTextureView", "initEglContext chooseConfig failed", e);
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperBlackScreen("GLTextureView.initEglContext_chooseConfig_exception", null);
                } catch (Exception unused2) {
                }
            }
            this.mEglContext = gLTextureView.mEGLContextFactory.createContext(this.mEgl, this.mEglDisplay, this.mEglConfig);
        }

        public final int[] initExts() {
            this.mExts.clear();
            String strEglQueryString = this.mEgl.eglQueryString(this.mEglDisplay, 12373);
            if (!TextUtils.isEmpty(strEglQueryString)) {
                Collections.addAll(this.mExts, strEglQueryString.split(" "));
            }
            if (!supportWcg()) {
                return null;
            }
            int wcgCapability = getWcgCapability();
            int[] iArr = {12445, wcgCapability, 12344};
            Log.d("Keyguard-Editor-GLTextureView", "create WCG EglWindowSurface, colorSpace=0x" + Integer.toHexString(wcgCapability));
            return iArr;
        }

        public boolean isEglReady() {
            return this.mEgl != null;
        }

        public void start() {
            if (GLTextureView.LOG_EGL) {
                Log.w("Keyguard-Editor-GLTextureView", "EglHelper start() tid=" + Thread.currentThread().getId());
            }
            EGL10 egl10 = (EGL10) EGLContext.getEGL();
            this.mEgl = egl10;
            EGLDisplay eGLDisplayEglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            this.mEglDisplay = eGLDisplayEglGetDisplay;
            if (eGLDisplayEglGetDisplay == EGL10.EGL_NO_DISPLAY) {
                throw new RuntimeException("eglGetDisplay failed");
            }
            if (!this.mEgl.eglInitialize(eGLDisplayEglGetDisplay, new int[2])) {
                throw new RuntimeException("eglInitialize failed");
            }
            GLTextureView gLTextureView = (GLTextureView) this.mGLTextureViewWeakRef.get();
            if (gLTextureView == null) {
                this.mEglConfig = null;
                this.mEglContext = null;
            } else {
                this.mEglConfig = gLTextureView.mEGLConfigChooser.chooseConfig(this.mEgl, this.mEglDisplay);
                this.mEglContext = gLTextureView.mEGLContextFactory.createContext(this.mEgl, this.mEglDisplay, this.mEglConfig);
            }
            EGLContext eGLContext = this.mEglContext;
            if (eGLContext == null || eGLContext == EGL10.EGL_NO_CONTEXT) {
                this.mEglContext = null;
                throwEglException("createContext");
            }
            if (GLTextureView.LOG_EGL) {
                Log.w("Keyguard-Editor-GLTextureView", "EglHelper createContext " + this.mEglContext + " tid=" + Thread.currentThread().getId());
            }
            this.mEglSurface = null;
        }

        public final boolean supportWcg() {
            return (this.mGLTextureViewWeakRef.get() != null ? WideColorGamutManager.INSTANCE.isWideColorBitmap(((GLTextureView) this.mGLTextureViewWeakRef.get()).getOriginBitmap()) : false) && WideColorGamutManager.INSTANCE.supportWideColor() && (checkExtensionCapability("EGL_KHR_gl_colorspace") && getWcgCapability() > 0);
        }

        public int swap() {
            EGLSurface eGLSurface;
            EGLDisplay eGLDisplay = this.mEglDisplay;
            if (eGLDisplay == null || (eGLSurface = this.mEglSurface) == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("swap: egl env error: mEglDisplay:");
                sb.append(this.mEglDisplay != null);
                sb.append(" mEglSurface:");
                sb.append(this.mEglSurface);
                Log.e("Keyguard-Editor-GLTextureView", sb.toString());
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperBlackScreen("GLTextureView.swap_egl_env_null", null);
                } catch (Exception unused) {
                }
                return this.mEglDisplay == null ? 12296 : 12301;
            }
            if (this.mEgl.eglSwapBuffers(eGLDisplay, eGLSurface)) {
                return 12288;
            }
            Log.d("Keyguard-Editor-GLTextureView", "swap: mEglDisplay=" + this.mEglDisplay + " mEglSurface=" + this.mEglSurface + " error code:" + this.mEgl.eglGetError());
            return this.mEgl.eglGetError();
        }

        public final void throwEglException(String str) {
            throwEglException(str, this.mEgl.eglGetError());
        }
    }

    public static class GLThread extends HandlerThread {
        public EglHelper mEglHelper;
        public ArrayList mEventQueue;
        public boolean mExited;
        public boolean mFinishedCreatingEglSurface;
        public WeakReference mGLTextureViewWeakRef;
        public boolean mHadSuccessfulSwap;
        public boolean mHasSurface;
        public boolean mHaveEglContext;
        public boolean mHaveEglSurface;
        public int mHeight;
        public boolean mPaused;
        public boolean mRenderComplete;
        public int mRenderMode;
        public boolean mRequestPaused;
        public boolean mRequestRender;
        public boolean mShouldExit;
        public boolean mShouldReleaseEglContext;
        public boolean mSizeChanged;
        public boolean mSurfaceIsBad;
        public boolean mWaitingForSurface;
        public int mWidth;
        public final GLThreadManager sGLThreadManager;

        public GLThread(WeakReference weakReference) {
            super("GLThread");
            this.sGLThreadManager = new GLThreadManager();
            this.mEventQueue = new ArrayList();
            this.mSizeChanged = true;
            this.mHadSuccessfulSwap = false;
            this.mWidth = 0;
            this.mHeight = 0;
            this.mRequestRender = true;
            this.mRenderMode = 1;
            this.mGLTextureViewWeakRef = weakReference;
        }

        public boolean ableToDraw() {
            return this.mHaveEglContext && this.mHaveEglSurface && readyToDraw();
        }

        public void createEglContextAndSurface() {
            EglHelper eglHelper = this.mEglHelper;
            if (eglHelper == null) {
                Log.e("Keyguard-Editor-GLTextureView", "mEglHelper is null");
            } else {
                eglHelper.initEglContext();
                this.mEglHelper.createSurface();
            }
        }

        public void destroyEglContextAndSurface() {
            this.mEglHelper.destroyEglSource();
        }

        public int getRenderMode() {
            int i;
            synchronized (this.sGLThreadManager) {
                i = this.mRenderMode;
            }
            return i;
        }

        /* JADX WARN: Code duplicated, block: B:113:0x0260  */
        /* JADX WARN: Code duplicated, block: B:118:0x026a A[Catch: all -> 0x0039, TryCatch #7 {, blocks: (B:6:0x0026, B:8:0x002a, B:19:0x003c, B:21:0x0044, B:127:0x02a5, B:24:0x0059, B:25:0x0060, B:27:0x0066, B:29:0x0073, B:32:0x00ac, B:34:0x00b0, B:36:0x00b6, B:37:0x00d0, B:39:0x00dc, B:42:0x00e8, B:44:0x00ec, B:46:0x00f2, B:48:0x0111, B:51:0x0119, B:53:0x011d, B:58:0x012f, B:60:0x0137, B:62:0x0140, B:56:0x0129, B:64:0x015c, B:66:0x0164, B:68:0x016f, B:69:0x0189, B:71:0x018d, B:73:0x0191, B:75:0x0197, B:76:0x01b1, B:78:0x01b5, B:79:0x01b8, B:80:0x01c3, B:82:0x01c7, B:84:0x01cb, B:86:0x01d1, B:87:0x01eb, B:89:0x01f5, B:91:0x01fb, B:92:0x0215, B:93:0x021f, B:95:0x0225, B:108:0x0251, B:110:0x0255, B:112:0x0259, B:114:0x0262, B:116:0x0266, B:118:0x026a, B:120:0x0274, B:123:0x0293, B:126:0x029c, B:217:0x0414, B:219:0x041a, B:221:0x04a5, B:223:0x04ac, B:99:0x022f, B:101:0x0237, B:103:0x023d, B:105:0x0248, B:106:0x024d), top: B:252:0x0026, outer: #0, inners: #1 }] */
        /* JADX WARN: Code duplicated, block: B:120:0x0274 A[Catch: all -> 0x0039, TryCatch #7 {, blocks: (B:6:0x0026, B:8:0x002a, B:19:0x003c, B:21:0x0044, B:127:0x02a5, B:24:0x0059, B:25:0x0060, B:27:0x0066, B:29:0x0073, B:32:0x00ac, B:34:0x00b0, B:36:0x00b6, B:37:0x00d0, B:39:0x00dc, B:42:0x00e8, B:44:0x00ec, B:46:0x00f2, B:48:0x0111, B:51:0x0119, B:53:0x011d, B:58:0x012f, B:60:0x0137, B:62:0x0140, B:56:0x0129, B:64:0x015c, B:66:0x0164, B:68:0x016f, B:69:0x0189, B:71:0x018d, B:73:0x0191, B:75:0x0197, B:76:0x01b1, B:78:0x01b5, B:79:0x01b8, B:80:0x01c3, B:82:0x01c7, B:84:0x01cb, B:86:0x01d1, B:87:0x01eb, B:89:0x01f5, B:91:0x01fb, B:92:0x0215, B:93:0x021f, B:95:0x0225, B:108:0x0251, B:110:0x0255, B:112:0x0259, B:114:0x0262, B:116:0x0266, B:118:0x026a, B:120:0x0274, B:123:0x0293, B:126:0x029c, B:217:0x0414, B:219:0x041a, B:221:0x04a5, B:223:0x04ac, B:99:0x022f, B:101:0x0237, B:103:0x023d, B:105:0x0248, B:106:0x024d), top: B:252:0x0026, outer: #0, inners: #1 }] */
        /* JADX WARN: Code duplicated, block: B:122:0x0291  */
        /* JADX WARN: Code duplicated, block: B:125:0x029a  */
        /* JADX WARN: Code duplicated, block: B:158:0x02f3 A[Catch: all -> 0x02b0, TryCatch #0 {all -> 0x02b0, blocks: (B:4:0x001c, B:5:0x0025, B:129:0x02a8, B:133:0x02b5, B:135:0x02bb, B:136:0x02c2, B:138:0x02ca, B:139:0x02cc, B:146:0x02da, B:147:0x02db, B:148:0x02dd, B:156:0x02f0, B:158:0x02f3, B:161:0x0307, B:163:0x030d, B:164:0x0314, B:166:0x031e, B:169:0x032c, B:171:0x0332, B:172:0x0355, B:174:0x035f, B:176:0x0367, B:178:0x036d, B:180:0x038e, B:182:0x0398, B:183:0x039f, B:187:0x03ad, B:188:0x03b4, B:189:0x03bc, B:190:0x03be, B:197:0x03cd, B:198:0x03ce, B:200:0x03d4, B:201:0x03ee, B:203:0x03f2, B:205:0x03f7, B:210:0x0401, B:227:0x04be, B:141:0x02ce, B:142:0x02d5, B:6:0x0026, B:8:0x002a, B:19:0x003c, B:21:0x0044, B:127:0x02a5, B:24:0x0059, B:25:0x0060, B:27:0x0066, B:29:0x0073, B:32:0x00ac, B:34:0x00b0, B:36:0x00b6, B:37:0x00d0, B:39:0x00dc, B:42:0x00e8, B:44:0x00ec, B:46:0x00f2, B:48:0x0111, B:51:0x0119, B:53:0x011d, B:58:0x012f, B:60:0x0137, B:62:0x0140, B:56:0x0129, B:64:0x015c, B:66:0x0164, B:68:0x016f, B:69:0x0189, B:71:0x018d, B:73:0x0191, B:75:0x0197, B:76:0x01b1, B:78:0x01b5, B:79:0x01b8, B:80:0x01c3, B:82:0x01c7, B:84:0x01cb, B:86:0x01d1, B:87:0x01eb, B:89:0x01f5, B:91:0x01fb, B:92:0x0215, B:93:0x021f, B:95:0x0225, B:108:0x0251, B:110:0x0255, B:112:0x0259, B:114:0x0262, B:116:0x0266, B:118:0x026a, B:120:0x0274, B:123:0x0293, B:126:0x029c, B:217:0x0414, B:219:0x041a, B:221:0x04a5, B:223:0x04ac, B:99:0x022f, B:101:0x0237, B:103:0x023d, B:105:0x0248, B:106:0x024d, B:192:0x03c0, B:193:0x03c7, B:150:0x02df, B:151:0x02e8), top: B:239:0x001c, inners: #6, #7, #9, #10 }] */
        /* JADX WARN: Code duplicated, block: B:159:0x0303  */
        /* JADX WARN: Code duplicated, block: B:161:0x0307 A[Catch: all -> 0x02b0, TryCatch #0 {all -> 0x02b0, blocks: (B:4:0x001c, B:5:0x0025, B:129:0x02a8, B:133:0x02b5, B:135:0x02bb, B:136:0x02c2, B:138:0x02ca, B:139:0x02cc, B:146:0x02da, B:147:0x02db, B:148:0x02dd, B:156:0x02f0, B:158:0x02f3, B:161:0x0307, B:163:0x030d, B:164:0x0314, B:166:0x031e, B:169:0x032c, B:171:0x0332, B:172:0x0355, B:174:0x035f, B:176:0x0367, B:178:0x036d, B:180:0x038e, B:182:0x0398, B:183:0x039f, B:187:0x03ad, B:188:0x03b4, B:189:0x03bc, B:190:0x03be, B:197:0x03cd, B:198:0x03ce, B:200:0x03d4, B:201:0x03ee, B:203:0x03f2, B:205:0x03f7, B:210:0x0401, B:227:0x04be, B:141:0x02ce, B:142:0x02d5, B:6:0x0026, B:8:0x002a, B:19:0x003c, B:21:0x0044, B:127:0x02a5, B:24:0x0059, B:25:0x0060, B:27:0x0066, B:29:0x0073, B:32:0x00ac, B:34:0x00b0, B:36:0x00b6, B:37:0x00d0, B:39:0x00dc, B:42:0x00e8, B:44:0x00ec, B:46:0x00f2, B:48:0x0111, B:51:0x0119, B:53:0x011d, B:58:0x012f, B:60:0x0137, B:62:0x0140, B:56:0x0129, B:64:0x015c, B:66:0x0164, B:68:0x016f, B:69:0x0189, B:71:0x018d, B:73:0x0191, B:75:0x0197, B:76:0x01b1, B:78:0x01b5, B:79:0x01b8, B:80:0x01c3, B:82:0x01c7, B:84:0x01cb, B:86:0x01d1, B:87:0x01eb, B:89:0x01f5, B:91:0x01fb, B:92:0x0215, B:93:0x021f, B:95:0x0225, B:108:0x0251, B:110:0x0255, B:112:0x0259, B:114:0x0262, B:116:0x0266, B:118:0x026a, B:120:0x0274, B:123:0x0293, B:126:0x029c, B:217:0x0414, B:219:0x041a, B:221:0x04a5, B:223:0x04ac, B:99:0x022f, B:101:0x0237, B:103:0x023d, B:105:0x0248, B:106:0x024d, B:192:0x03c0, B:193:0x03c7, B:150:0x02df, B:151:0x02e8), top: B:239:0x001c, inners: #6, #7, #9, #10 }] */
        /* JADX WARN: Code duplicated, block: B:163:0x030d A[Catch: all -> 0x02b0, TryCatch #0 {all -> 0x02b0, blocks: (B:4:0x001c, B:5:0x0025, B:129:0x02a8, B:133:0x02b5, B:135:0x02bb, B:136:0x02c2, B:138:0x02ca, B:139:0x02cc, B:146:0x02da, B:147:0x02db, B:148:0x02dd, B:156:0x02f0, B:158:0x02f3, B:161:0x0307, B:163:0x030d, B:164:0x0314, B:166:0x031e, B:169:0x032c, B:171:0x0332, B:172:0x0355, B:174:0x035f, B:176:0x0367, B:178:0x036d, B:180:0x038e, B:182:0x0398, B:183:0x039f, B:187:0x03ad, B:188:0x03b4, B:189:0x03bc, B:190:0x03be, B:197:0x03cd, B:198:0x03ce, B:200:0x03d4, B:201:0x03ee, B:203:0x03f2, B:205:0x03f7, B:210:0x0401, B:227:0x04be, B:141:0x02ce, B:142:0x02d5, B:6:0x0026, B:8:0x002a, B:19:0x003c, B:21:0x0044, B:127:0x02a5, B:24:0x0059, B:25:0x0060, B:27:0x0066, B:29:0x0073, B:32:0x00ac, B:34:0x00b0, B:36:0x00b6, B:37:0x00d0, B:39:0x00dc, B:42:0x00e8, B:44:0x00ec, B:46:0x00f2, B:48:0x0111, B:51:0x0119, B:53:0x011d, B:58:0x012f, B:60:0x0137, B:62:0x0140, B:56:0x0129, B:64:0x015c, B:66:0x0164, B:68:0x016f, B:69:0x0189, B:71:0x018d, B:73:0x0191, B:75:0x0197, B:76:0x01b1, B:78:0x01b5, B:79:0x01b8, B:80:0x01c3, B:82:0x01c7, B:84:0x01cb, B:86:0x01d1, B:87:0x01eb, B:89:0x01f5, B:91:0x01fb, B:92:0x0215, B:93:0x021f, B:95:0x0225, B:108:0x0251, B:110:0x0255, B:112:0x0259, B:114:0x0262, B:116:0x0266, B:118:0x026a, B:120:0x0274, B:123:0x0293, B:126:0x029c, B:217:0x0414, B:219:0x041a, B:221:0x04a5, B:223:0x04ac, B:99:0x022f, B:101:0x0237, B:103:0x023d, B:105:0x0248, B:106:0x024d, B:192:0x03c0, B:193:0x03c7, B:150:0x02df, B:151:0x02e8), top: B:239:0x001c, inners: #6, #7, #9, #10 }] */
        /* JADX WARN: Code duplicated, block: B:166:0x031e A[Catch: all -> 0x02b0, TryCatch #0 {all -> 0x02b0, blocks: (B:4:0x001c, B:5:0x0025, B:129:0x02a8, B:133:0x02b5, B:135:0x02bb, B:136:0x02c2, B:138:0x02ca, B:139:0x02cc, B:146:0x02da, B:147:0x02db, B:148:0x02dd, B:156:0x02f0, B:158:0x02f3, B:161:0x0307, B:163:0x030d, B:164:0x0314, B:166:0x031e, B:169:0x032c, B:171:0x0332, B:172:0x0355, B:174:0x035f, B:176:0x0367, B:178:0x036d, B:180:0x038e, B:182:0x0398, B:183:0x039f, B:187:0x03ad, B:188:0x03b4, B:189:0x03bc, B:190:0x03be, B:197:0x03cd, B:198:0x03ce, B:200:0x03d4, B:201:0x03ee, B:203:0x03f2, B:205:0x03f7, B:210:0x0401, B:227:0x04be, B:141:0x02ce, B:142:0x02d5, B:6:0x0026, B:8:0x002a, B:19:0x003c, B:21:0x0044, B:127:0x02a5, B:24:0x0059, B:25:0x0060, B:27:0x0066, B:29:0x0073, B:32:0x00ac, B:34:0x00b0, B:36:0x00b6, B:37:0x00d0, B:39:0x00dc, B:42:0x00e8, B:44:0x00ec, B:46:0x00f2, B:48:0x0111, B:51:0x0119, B:53:0x011d, B:58:0x012f, B:60:0x0137, B:62:0x0140, B:56:0x0129, B:64:0x015c, B:66:0x0164, B:68:0x016f, B:69:0x0189, B:71:0x018d, B:73:0x0191, B:75:0x0197, B:76:0x01b1, B:78:0x01b5, B:79:0x01b8, B:80:0x01c3, B:82:0x01c7, B:84:0x01cb, B:86:0x01d1, B:87:0x01eb, B:89:0x01f5, B:91:0x01fb, B:92:0x0215, B:93:0x021f, B:95:0x0225, B:108:0x0251, B:110:0x0255, B:112:0x0259, B:114:0x0262, B:116:0x0266, B:118:0x026a, B:120:0x0274, B:123:0x0293, B:126:0x029c, B:217:0x0414, B:219:0x041a, B:221:0x04a5, B:223:0x04ac, B:99:0x022f, B:101:0x0237, B:103:0x023d, B:105:0x0248, B:106:0x024d, B:192:0x03c0, B:193:0x03c7, B:150:0x02df, B:151:0x02e8), top: B:239:0x001c, inners: #6, #7, #9, #10 }] */
        /* JADX WARN: Code duplicated, block: B:169:0x032c A[Catch: all -> 0x02b0, TryCatch #0 {all -> 0x02b0, blocks: (B:4:0x001c, B:5:0x0025, B:129:0x02a8, B:133:0x02b5, B:135:0x02bb, B:136:0x02c2, B:138:0x02ca, B:139:0x02cc, B:146:0x02da, B:147:0x02db, B:148:0x02dd, B:156:0x02f0, B:158:0x02f3, B:161:0x0307, B:163:0x030d, B:164:0x0314, B:166:0x031e, B:169:0x032c, B:171:0x0332, B:172:0x0355, B:174:0x035f, B:176:0x0367, B:178:0x036d, B:180:0x038e, B:182:0x0398, B:183:0x039f, B:187:0x03ad, B:188:0x03b4, B:189:0x03bc, B:190:0x03be, B:197:0x03cd, B:198:0x03ce, B:200:0x03d4, B:201:0x03ee, B:203:0x03f2, B:205:0x03f7, B:210:0x0401, B:227:0x04be, B:141:0x02ce, B:142:0x02d5, B:6:0x0026, B:8:0x002a, B:19:0x003c, B:21:0x0044, B:127:0x02a5, B:24:0x0059, B:25:0x0060, B:27:0x0066, B:29:0x0073, B:32:0x00ac, B:34:0x00b0, B:36:0x00b6, B:37:0x00d0, B:39:0x00dc, B:42:0x00e8, B:44:0x00ec, B:46:0x00f2, B:48:0x0111, B:51:0x0119, B:53:0x011d, B:58:0x012f, B:60:0x0137, B:62:0x0140, B:56:0x0129, B:64:0x015c, B:66:0x0164, B:68:0x016f, B:69:0x0189, B:71:0x018d, B:73:0x0191, B:75:0x0197, B:76:0x01b1, B:78:0x01b5, B:79:0x01b8, B:80:0x01c3, B:82:0x01c7, B:84:0x01cb, B:86:0x01d1, B:87:0x01eb, B:89:0x01f5, B:91:0x01fb, B:92:0x0215, B:93:0x021f, B:95:0x0225, B:108:0x0251, B:110:0x0255, B:112:0x0259, B:114:0x0262, B:116:0x0266, B:118:0x026a, B:120:0x0274, B:123:0x0293, B:126:0x029c, B:217:0x0414, B:219:0x041a, B:221:0x04a5, B:223:0x04ac, B:99:0x022f, B:101:0x0237, B:103:0x023d, B:105:0x0248, B:106:0x024d, B:192:0x03c0, B:193:0x03c7, B:150:0x02df, B:151:0x02e8), top: B:239:0x001c, inners: #6, #7, #9, #10 }] */
        /* JADX WARN: Code duplicated, block: B:171:0x0332 A[Catch: all -> 0x02b0, TryCatch #0 {all -> 0x02b0, blocks: (B:4:0x001c, B:5:0x0025, B:129:0x02a8, B:133:0x02b5, B:135:0x02bb, B:136:0x02c2, B:138:0x02ca, B:139:0x02cc, B:146:0x02da, B:147:0x02db, B:148:0x02dd, B:156:0x02f0, B:158:0x02f3, B:161:0x0307, B:163:0x030d, B:164:0x0314, B:166:0x031e, B:169:0x032c, B:171:0x0332, B:172:0x0355, B:174:0x035f, B:176:0x0367, B:178:0x036d, B:180:0x038e, B:182:0x0398, B:183:0x039f, B:187:0x03ad, B:188:0x03b4, B:189:0x03bc, B:190:0x03be, B:197:0x03cd, B:198:0x03ce, B:200:0x03d4, B:201:0x03ee, B:203:0x03f2, B:205:0x03f7, B:210:0x0401, B:227:0x04be, B:141:0x02ce, B:142:0x02d5, B:6:0x0026, B:8:0x002a, B:19:0x003c, B:21:0x0044, B:127:0x02a5, B:24:0x0059, B:25:0x0060, B:27:0x0066, B:29:0x0073, B:32:0x00ac, B:34:0x00b0, B:36:0x00b6, B:37:0x00d0, B:39:0x00dc, B:42:0x00e8, B:44:0x00ec, B:46:0x00f2, B:48:0x0111, B:51:0x0119, B:53:0x011d, B:58:0x012f, B:60:0x0137, B:62:0x0140, B:56:0x0129, B:64:0x015c, B:66:0x0164, B:68:0x016f, B:69:0x0189, B:71:0x018d, B:73:0x0191, B:75:0x0197, B:76:0x01b1, B:78:0x01b5, B:79:0x01b8, B:80:0x01c3, B:82:0x01c7, B:84:0x01cb, B:86:0x01d1, B:87:0x01eb, B:89:0x01f5, B:91:0x01fb, B:92:0x0215, B:93:0x021f, B:95:0x0225, B:108:0x0251, B:110:0x0255, B:112:0x0259, B:114:0x0262, B:116:0x0266, B:118:0x026a, B:120:0x0274, B:123:0x0293, B:126:0x029c, B:217:0x0414, B:219:0x041a, B:221:0x04a5, B:223:0x04ac, B:99:0x022f, B:101:0x0237, B:103:0x023d, B:105:0x0248, B:106:0x024d, B:192:0x03c0, B:193:0x03c7, B:150:0x02df, B:151:0x02e8), top: B:239:0x001c, inners: #6, #7, #9, #10 }] */
        /* JADX WARN: Code duplicated, block: B:174:0x035f A[Catch: all -> 0x02b0, TryCatch #0 {all -> 0x02b0, blocks: (B:4:0x001c, B:5:0x0025, B:129:0x02a8, B:133:0x02b5, B:135:0x02bb, B:136:0x02c2, B:138:0x02ca, B:139:0x02cc, B:146:0x02da, B:147:0x02db, B:148:0x02dd, B:156:0x02f0, B:158:0x02f3, B:161:0x0307, B:163:0x030d, B:164:0x0314, B:166:0x031e, B:169:0x032c, B:171:0x0332, B:172:0x0355, B:174:0x035f, B:176:0x0367, B:178:0x036d, B:180:0x038e, B:182:0x0398, B:183:0x039f, B:187:0x03ad, B:188:0x03b4, B:189:0x03bc, B:190:0x03be, B:197:0x03cd, B:198:0x03ce, B:200:0x03d4, B:201:0x03ee, B:203:0x03f2, B:205:0x03f7, B:210:0x0401, B:227:0x04be, B:141:0x02ce, B:142:0x02d5, B:6:0x0026, B:8:0x002a, B:19:0x003c, B:21:0x0044, B:127:0x02a5, B:24:0x0059, B:25:0x0060, B:27:0x0066, B:29:0x0073, B:32:0x00ac, B:34:0x00b0, B:36:0x00b6, B:37:0x00d0, B:39:0x00dc, B:42:0x00e8, B:44:0x00ec, B:46:0x00f2, B:48:0x0111, B:51:0x0119, B:53:0x011d, B:58:0x012f, B:60:0x0137, B:62:0x0140, B:56:0x0129, B:64:0x015c, B:66:0x0164, B:68:0x016f, B:69:0x0189, B:71:0x018d, B:73:0x0191, B:75:0x0197, B:76:0x01b1, B:78:0x01b5, B:79:0x01b8, B:80:0x01c3, B:82:0x01c7, B:84:0x01cb, B:86:0x01d1, B:87:0x01eb, B:89:0x01f5, B:91:0x01fb, B:92:0x0215, B:93:0x021f, B:95:0x0225, B:108:0x0251, B:110:0x0255, B:112:0x0259, B:114:0x0262, B:116:0x0266, B:118:0x026a, B:120:0x0274, B:123:0x0293, B:126:0x029c, B:217:0x0414, B:219:0x041a, B:221:0x04a5, B:223:0x04ac, B:99:0x022f, B:101:0x0237, B:103:0x023d, B:105:0x0248, B:106:0x024d, B:192:0x03c0, B:193:0x03c7, B:150:0x02df, B:151:0x02e8), top: B:239:0x001c, inners: #6, #7, #9, #10 }] */
        /* JADX WARN: Code duplicated, block: B:178:0x036d A[Catch: all -> 0x02b0, TryCatch #0 {all -> 0x02b0, blocks: (B:4:0x001c, B:5:0x0025, B:129:0x02a8, B:133:0x02b5, B:135:0x02bb, B:136:0x02c2, B:138:0x02ca, B:139:0x02cc, B:146:0x02da, B:147:0x02db, B:148:0x02dd, B:156:0x02f0, B:158:0x02f3, B:161:0x0307, B:163:0x030d, B:164:0x0314, B:166:0x031e, B:169:0x032c, B:171:0x0332, B:172:0x0355, B:174:0x035f, B:176:0x0367, B:178:0x036d, B:180:0x038e, B:182:0x0398, B:183:0x039f, B:187:0x03ad, B:188:0x03b4, B:189:0x03bc, B:190:0x03be, B:197:0x03cd, B:198:0x03ce, B:200:0x03d4, B:201:0x03ee, B:203:0x03f2, B:205:0x03f7, B:210:0x0401, B:227:0x04be, B:141:0x02ce, B:142:0x02d5, B:6:0x0026, B:8:0x002a, B:19:0x003c, B:21:0x0044, B:127:0x02a5, B:24:0x0059, B:25:0x0060, B:27:0x0066, B:29:0x0073, B:32:0x00ac, B:34:0x00b0, B:36:0x00b6, B:37:0x00d0, B:39:0x00dc, B:42:0x00e8, B:44:0x00ec, B:46:0x00f2, B:48:0x0111, B:51:0x0119, B:53:0x011d, B:58:0x012f, B:60:0x0137, B:62:0x0140, B:56:0x0129, B:64:0x015c, B:66:0x0164, B:68:0x016f, B:69:0x0189, B:71:0x018d, B:73:0x0191, B:75:0x0197, B:76:0x01b1, B:78:0x01b5, B:79:0x01b8, B:80:0x01c3, B:82:0x01c7, B:84:0x01cb, B:86:0x01d1, B:87:0x01eb, B:89:0x01f5, B:91:0x01fb, B:92:0x0215, B:93:0x021f, B:95:0x0225, B:108:0x0251, B:110:0x0255, B:112:0x0259, B:114:0x0262, B:116:0x0266, B:118:0x026a, B:120:0x0274, B:123:0x0293, B:126:0x029c, B:217:0x0414, B:219:0x041a, B:221:0x04a5, B:223:0x04ac, B:99:0x022f, B:101:0x0237, B:103:0x023d, B:105:0x0248, B:106:0x024d, B:192:0x03c0, B:193:0x03c7, B:150:0x02df, B:151:0x02e8), top: B:239:0x001c, inners: #6, #7, #9, #10 }] */
        /* JADX WARN: Code duplicated, block: B:179:0x038b  */
        /* JADX WARN: Code duplicated, block: B:182:0x0398 A[Catch: all -> 0x02b0, TryCatch #0 {all -> 0x02b0, blocks: (B:4:0x001c, B:5:0x0025, B:129:0x02a8, B:133:0x02b5, B:135:0x02bb, B:136:0x02c2, B:138:0x02ca, B:139:0x02cc, B:146:0x02da, B:147:0x02db, B:148:0x02dd, B:156:0x02f0, B:158:0x02f3, B:161:0x0307, B:163:0x030d, B:164:0x0314, B:166:0x031e, B:169:0x032c, B:171:0x0332, B:172:0x0355, B:174:0x035f, B:176:0x0367, B:178:0x036d, B:180:0x038e, B:182:0x0398, B:183:0x039f, B:187:0x03ad, B:188:0x03b4, B:189:0x03bc, B:190:0x03be, B:197:0x03cd, B:198:0x03ce, B:200:0x03d4, B:201:0x03ee, B:203:0x03f2, B:205:0x03f7, B:210:0x0401, B:227:0x04be, B:141:0x02ce, B:142:0x02d5, B:6:0x0026, B:8:0x002a, B:19:0x003c, B:21:0x0044, B:127:0x02a5, B:24:0x0059, B:25:0x0060, B:27:0x0066, B:29:0x0073, B:32:0x00ac, B:34:0x00b0, B:36:0x00b6, B:37:0x00d0, B:39:0x00dc, B:42:0x00e8, B:44:0x00ec, B:46:0x00f2, B:48:0x0111, B:51:0x0119, B:53:0x011d, B:58:0x012f, B:60:0x0137, B:62:0x0140, B:56:0x0129, B:64:0x015c, B:66:0x0164, B:68:0x016f, B:69:0x0189, B:71:0x018d, B:73:0x0191, B:75:0x0197, B:76:0x01b1, B:78:0x01b5, B:79:0x01b8, B:80:0x01c3, B:82:0x01c7, B:84:0x01cb, B:86:0x01d1, B:87:0x01eb, B:89:0x01f5, B:91:0x01fb, B:92:0x0215, B:93:0x021f, B:95:0x0225, B:108:0x0251, B:110:0x0255, B:112:0x0259, B:114:0x0262, B:116:0x0266, B:118:0x026a, B:120:0x0274, B:123:0x0293, B:126:0x029c, B:217:0x0414, B:219:0x041a, B:221:0x04a5, B:223:0x04ac, B:99:0x022f, B:101:0x0237, B:103:0x023d, B:105:0x0248, B:106:0x024d, B:192:0x03c0, B:193:0x03c7, B:150:0x02df, B:151:0x02e8), top: B:239:0x001c, inners: #6, #7, #9, #10 }] */
        /* JADX WARN: Code duplicated, block: B:185:0x03a9  */
        /* JADX WARN: Code duplicated, block: B:187:0x03ad A[Catch: all -> 0x02b0, TRY_LEAVE, TryCatch #0 {all -> 0x02b0, blocks: (B:4:0x001c, B:5:0x0025, B:129:0x02a8, B:133:0x02b5, B:135:0x02bb, B:136:0x02c2, B:138:0x02ca, B:139:0x02cc, B:146:0x02da, B:147:0x02db, B:148:0x02dd, B:156:0x02f0, B:158:0x02f3, B:161:0x0307, B:163:0x030d, B:164:0x0314, B:166:0x031e, B:169:0x032c, B:171:0x0332, B:172:0x0355, B:174:0x035f, B:176:0x0367, B:178:0x036d, B:180:0x038e, B:182:0x0398, B:183:0x039f, B:187:0x03ad, B:188:0x03b4, B:189:0x03bc, B:190:0x03be, B:197:0x03cd, B:198:0x03ce, B:200:0x03d4, B:201:0x03ee, B:203:0x03f2, B:205:0x03f7, B:210:0x0401, B:227:0x04be, B:141:0x02ce, B:142:0x02d5, B:6:0x0026, B:8:0x002a, B:19:0x003c, B:21:0x0044, B:127:0x02a5, B:24:0x0059, B:25:0x0060, B:27:0x0066, B:29:0x0073, B:32:0x00ac, B:34:0x00b0, B:36:0x00b6, B:37:0x00d0, B:39:0x00dc, B:42:0x00e8, B:44:0x00ec, B:46:0x00f2, B:48:0x0111, B:51:0x0119, B:53:0x011d, B:58:0x012f, B:60:0x0137, B:62:0x0140, B:56:0x0129, B:64:0x015c, B:66:0x0164, B:68:0x016f, B:69:0x0189, B:71:0x018d, B:73:0x0191, B:75:0x0197, B:76:0x01b1, B:78:0x01b5, B:79:0x01b8, B:80:0x01c3, B:82:0x01c7, B:84:0x01cb, B:86:0x01d1, B:87:0x01eb, B:89:0x01f5, B:91:0x01fb, B:92:0x0215, B:93:0x021f, B:95:0x0225, B:108:0x0251, B:110:0x0255, B:112:0x0259, B:114:0x0262, B:116:0x0266, B:118:0x026a, B:120:0x0274, B:123:0x0293, B:126:0x029c, B:217:0x0414, B:219:0x041a, B:221:0x04a5, B:223:0x04ac, B:99:0x022f, B:101:0x0237, B:103:0x023d, B:105:0x0248, B:106:0x024d, B:192:0x03c0, B:193:0x03c7, B:150:0x02df, B:151:0x02e8), top: B:239:0x001c, inners: #6, #7, #9, #10 }] */
        /* JADX WARN: Code duplicated, block: B:191:0x03bf  */
        /* JADX WARN: Code duplicated, block: B:198:0x03ce A[Catch: all -> 0x02b0, TryCatch #0 {all -> 0x02b0, blocks: (B:4:0x001c, B:5:0x0025, B:129:0x02a8, B:133:0x02b5, B:135:0x02bb, B:136:0x02c2, B:138:0x02ca, B:139:0x02cc, B:146:0x02da, B:147:0x02db, B:148:0x02dd, B:156:0x02f0, B:158:0x02f3, B:161:0x0307, B:163:0x030d, B:164:0x0314, B:166:0x031e, B:169:0x032c, B:171:0x0332, B:172:0x0355, B:174:0x035f, B:176:0x0367, B:178:0x036d, B:180:0x038e, B:182:0x0398, B:183:0x039f, B:187:0x03ad, B:188:0x03b4, B:189:0x03bc, B:190:0x03be, B:197:0x03cd, B:198:0x03ce, B:200:0x03d4, B:201:0x03ee, B:203:0x03f2, B:205:0x03f7, B:210:0x0401, B:227:0x04be, B:141:0x02ce, B:142:0x02d5, B:6:0x0026, B:8:0x002a, B:19:0x003c, B:21:0x0044, B:127:0x02a5, B:24:0x0059, B:25:0x0060, B:27:0x0066, B:29:0x0073, B:32:0x00ac, B:34:0x00b0, B:36:0x00b6, B:37:0x00d0, B:39:0x00dc, B:42:0x00e8, B:44:0x00ec, B:46:0x00f2, B:48:0x0111, B:51:0x0119, B:53:0x011d, B:58:0x012f, B:60:0x0137, B:62:0x0140, B:56:0x0129, B:64:0x015c, B:66:0x0164, B:68:0x016f, B:69:0x0189, B:71:0x018d, B:73:0x0191, B:75:0x0197, B:76:0x01b1, B:78:0x01b5, B:79:0x01b8, B:80:0x01c3, B:82:0x01c7, B:84:0x01cb, B:86:0x01d1, B:87:0x01eb, B:89:0x01f5, B:91:0x01fb, B:92:0x0215, B:93:0x021f, B:95:0x0225, B:108:0x0251, B:110:0x0255, B:112:0x0259, B:114:0x0262, B:116:0x0266, B:118:0x026a, B:120:0x0274, B:123:0x0293, B:126:0x029c, B:217:0x0414, B:219:0x041a, B:221:0x04a5, B:223:0x04ac, B:99:0x022f, B:101:0x0237, B:103:0x023d, B:105:0x0248, B:106:0x024d, B:192:0x03c0, B:193:0x03c7, B:150:0x02df, B:151:0x02e8), top: B:239:0x001c, inners: #6, #7, #9, #10 }] */
        /* JADX WARN: Code duplicated, block: B:200:0x03d4 A[Catch: all -> 0x02b0, TryCatch #0 {all -> 0x02b0, blocks: (B:4:0x001c, B:5:0x0025, B:129:0x02a8, B:133:0x02b5, B:135:0x02bb, B:136:0x02c2, B:138:0x02ca, B:139:0x02cc, B:146:0x02da, B:147:0x02db, B:148:0x02dd, B:156:0x02f0, B:158:0x02f3, B:161:0x0307, B:163:0x030d, B:164:0x0314, B:166:0x031e, B:169:0x032c, B:171:0x0332, B:172:0x0355, B:174:0x035f, B:176:0x0367, B:178:0x036d, B:180:0x038e, B:182:0x0398, B:183:0x039f, B:187:0x03ad, B:188:0x03b4, B:189:0x03bc, B:190:0x03be, B:197:0x03cd, B:198:0x03ce, B:200:0x03d4, B:201:0x03ee, B:203:0x03f2, B:205:0x03f7, B:210:0x0401, B:227:0x04be, B:141:0x02ce, B:142:0x02d5, B:6:0x0026, B:8:0x002a, B:19:0x003c, B:21:0x0044, B:127:0x02a5, B:24:0x0059, B:25:0x0060, B:27:0x0066, B:29:0x0073, B:32:0x00ac, B:34:0x00b0, B:36:0x00b6, B:37:0x00d0, B:39:0x00dc, B:42:0x00e8, B:44:0x00ec, B:46:0x00f2, B:48:0x0111, B:51:0x0119, B:53:0x011d, B:58:0x012f, B:60:0x0137, B:62:0x0140, B:56:0x0129, B:64:0x015c, B:66:0x0164, B:68:0x016f, B:69:0x0189, B:71:0x018d, B:73:0x0191, B:75:0x0197, B:76:0x01b1, B:78:0x01b5, B:79:0x01b8, B:80:0x01c3, B:82:0x01c7, B:84:0x01cb, B:86:0x01d1, B:87:0x01eb, B:89:0x01f5, B:91:0x01fb, B:92:0x0215, B:93:0x021f, B:95:0x0225, B:108:0x0251, B:110:0x0255, B:112:0x0259, B:114:0x0262, B:116:0x0266, B:118:0x026a, B:120:0x0274, B:123:0x0293, B:126:0x029c, B:217:0x0414, B:219:0x041a, B:221:0x04a5, B:223:0x04ac, B:99:0x022f, B:101:0x0237, B:103:0x023d, B:105:0x0248, B:106:0x024d, B:192:0x03c0, B:193:0x03c7, B:150:0x02df, B:151:0x02e8), top: B:239:0x001c, inners: #6, #7, #9, #10 }] */
        /* JADX WARN: Code duplicated, block: B:207:0x03fb  */
        /* JADX WARN: Code duplicated, block: B:209:0x03ff  */
        /* JADX WARN: Code duplicated, block: B:212:0x0405  */
        /* JADX WARN: Code duplicated, block: B:214:0x040b  */
        /* JADX WARN: Code duplicated, block: B:246:0x03f2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:253:0x04c2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:264:0x0266 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:49:0x0115  */
        /* JADX WARN: Instruction removed from duplicated block: B:120:0x0274, please report this as an issue */
        /* JADX WARN: Instruction removed from duplicated block: B:171:0x0332, please report this as an issue */
        /* JADX WARN: Instruction removed from duplicated block: B:178:0x036d, please report this as an issue */
        /* JADX WARN: Instruction removed from duplicated block: B:200:0x03d4, please report this as an issue */
        public final void guardedRun() {
            boolean z;
            boolean z2;
            boolean z3;
            GLTextureView gLTextureView;
            int iSwap;
            boolean z4;
            GLTextureView gLTextureView2;
            GLTextureView gLTextureView3;
            this.mEglHelper = new EglHelper(this.mGLTextureViewWeakRef);
            this.mHaveEglContext = false;
            this.mHaveEglSurface = false;
            boolean z5 = false;
            boolean z6 = false;
            boolean z7 = false;
            boolean z8 = false;
            boolean z9 = false;
            int i = 0;
            int i2 = 0;
            boolean z10 = false;
            boolean z11 = false;
            boolean z12 = false;
            GL10 gl10 = null;
            while (true) {
                Runnable runnable = null;
                while (true) {
                    try {
                        Log.w("Keyguard-Editor-GLTextureView", "guardedRun");
                        synchronized (this.sGLThreadManager) {
                            while (true) {
                                if (this.mShouldExit) {
                                    synchronized (this.sGLThreadManager) {
                                        stopEglSurfaceLocked();
                                        stopEglContextLocked();
                                    }
                                    return;
                                }
                                if (!this.mEventQueue.isEmpty()) {
                                    runnable = (Runnable) this.mEventQueue.remove(0);
                                    if (this.mEglHelper.isEglReady()) {
                                        gl10 = gl10;
                                        break;
                                    }
                                    Log.w("Keyguard-Editor-GLTextureView", "egl not ready");
                                }
                                boolean z13 = this.mPaused;
                                boolean z14 = this.mRequestPaused;
                                if (z13 != z14) {
                                    this.mPaused = z14;
                                    this.sGLThreadManager.notifyAll();
                                    if (GLTextureView.LOG_PAUSE_RESUME) {
                                        Log.i("Keyguard-Editor-GLTextureView", "GLThread mPaused is now " + this.mPaused + " tid=" + getId());
                                    }
                                } else {
                                    z14 = false;
                                }
                                if (this.mShouldReleaseEglContext) {
                                    if (GLTextureView.LOG_SURFACE) {
                                        Log.i("Keyguard-Editor-GLTextureView", "GLThread releasing EGL context because asked to tid=" + getId());
                                    }
                                    stopEglSurfaceLocked();
                                    stopEglContextLocked();
                                    this.mShouldReleaseEglContext = false;
                                    z11 = true;
                                }
                                if (z5 != 0) {
                                    stopEglSurfaceLocked();
                                    stopEglContextLocked();
                                    z5 = false;
                                }
                                if (z14) {
                                    z5 = z5;
                                    if (this.mHaveEglSurface) {
                                        if (GLTextureView.LOG_SURFACE) {
                                            Log.i("Keyguard-Editor-GLTextureView", "GLThread releasing EGL surface because paused tid=" + getId());
                                        }
                                        stopEglSurfaceLocked();
                                    } else {
                                        z5 = z5;
                                        z7 = z7;
                                    }
                                } else {
                                    z5 = z5;
                                    z7 = z7;
                                }
                                if (z14 && this.mHaveEglContext) {
                                    GLTextureView gLTextureView4 = (GLTextureView) this.mGLTextureViewWeakRef.get();
                                    if (!(gLTextureView4 == null ? false : gLTextureView4.mPreserveEGLContextOnPause) || this.sGLThreadManager.shouldReleaseEGLContextWhenPausing()) {
                                        stopEglContextLocked();
                                        if (GLTextureView.LOG_SURFACE) {
                                            Log.i("Keyguard-Editor-GLTextureView", "GLThread releasing EGL context because paused tid=" + getId());
                                        }
                                    }
                                }
                                if (z14 && this.sGLThreadManager.shouldTerminateEGLWhenPausing()) {
                                    this.mEglHelper.finish();
                                    if (GLTextureView.LOG_SURFACE) {
                                        Log.i("Keyguard-Editor-GLTextureView", "GLThread terminating EGL because paused tid=" + getId());
                                    }
                                }
                                if (!this.mHasSurface && !this.mWaitingForSurface) {
                                    if (GLTextureView.LOG_SURFACE) {
                                        Log.i("Keyguard-Editor-GLTextureView", "GLThread noticed TextureView surface lost tid=" + getId());
                                    }
                                    if (this.mHaveEglSurface) {
                                        stopEglSurfaceLocked();
                                    }
                                    this.mWaitingForSurface = true;
                                    this.mSurfaceIsBad = false;
                                    this.sGLThreadManager.notifyAll();
                                }
                                if (this.mHasSurface && this.mWaitingForSurface) {
                                    if (GLTextureView.LOG_SURFACE) {
                                        Log.i("Keyguard-Editor-GLTextureView", "GLThread noticed TextureView surface acquired tid=" + getId());
                                    }
                                    this.mWaitingForSurface = false;
                                    this.sGLThreadManager.notifyAll();
                                }
                                if (z10) {
                                    if (GLTextureView.LOG_SURFACE) {
                                        Log.i("Keyguard-Editor-GLTextureView", "GLThread sending render notification tid=" + getId());
                                    }
                                    this.mRenderComplete = true;
                                    this.sGLThreadManager.notifyAll();
                                    z10 = false;
                                    z12 = false;
                                }
                                if (readyToDraw()) {
                                    if (!this.mHaveEglContext) {
                                        if (z11) {
                                            z3 = z6;
                                            z2 = false;
                                        } else if (this.sGLThreadManager.tryAcquireEglContextLocked(this)) {
                                            try {
                                                this.mEglHelper.start();
                                                this.mHaveEglContext = true;
                                                this.sGLThreadManager.notifyAll();
                                                z2 = z11;
                                                z3 = true;
                                            } catch (RuntimeException e) {
                                                this.sGLThreadManager.releaseEglContextLocked(this);
                                                throw e;
                                            }
                                        }
                                        if (this.mHaveEglContext || this.mHaveEglSurface) {
                                            z7 = z7;
                                        } else {
                                            this.mHaveEglSurface = true;
                                            z7 = true;
                                            z8 = true;
                                            z9 = true;
                                        }
                                        if (this.mHaveEglSurface) {
                                            if (this.mSizeChanged) {
                                                i = this.mWidth;
                                                i2 = this.mHeight;
                                                if (GLTextureView.LOG_SURFACE) {
                                                    Log.i("Keyguard-Editor-GLTextureView", "GLThread noticing that we want render notification tid=" + getId());
                                                }
                                                this.mSizeChanged = false;
                                                z7 = true;
                                                z9 = true;
                                                z12 = true;
                                            } else {
                                                z3 = z3;
                                            }
                                            this.mRequestRender = false;
                                            this.sGLThreadManager.notifyAll();
                                            z6 = z3;
                                            z11 = z2;
                                            break;
                                        }
                                        z6 = z3;
                                        z11 = z2;
                                    }
                                    z2 = z11;
                                    z3 = z6;
                                    if (this.mHaveEglContext) {
                                        z7 = z7;
                                    } else {
                                        z7 = z7;
                                    }
                                    if (this.mHaveEglSurface) {
                                        if (this.mSizeChanged) {
                                            i = this.mWidth;
                                            i2 = this.mHeight;
                                            if (GLTextureView.LOG_SURFACE) {
                                                Log.i("Keyguard-Editor-GLTextureView", "GLThread noticing that we want render notification tid=" + getId());
                                            }
                                            this.mSizeChanged = false;
                                            z7 = true;
                                            z9 = true;
                                            z12 = true;
                                        } else {
                                            z3 = z3;
                                        }
                                        this.mRequestRender = false;
                                        this.sGLThreadManager.notifyAll();
                                        z6 = z3;
                                        z11 = z2;
                                        break;
                                    }
                                    z6 = z3;
                                    z11 = z2;
                                } else {
                                    z7 = z7;
                                }
                                if (GLTextureView.LOG_THREADS) {
                                    Log.i("Keyguard-Editor-GLTextureView", "GLThread waiting tid=" + getId() + " mHaveEglContext: " + this.mHaveEglContext + " mHaveEglSurface: " + this.mHaveEglSurface + " mFinishedCreatingEglSurface: " + this.mFinishedCreatingEglSurface + " mPaused: " + this.mPaused + " mHasSurface: " + this.mHasSurface + " mSurfaceIsBad: " + this.mSurfaceIsBad + " mWaitingForSurface: " + this.mWaitingForSurface + " mWidth: " + this.mWidth + " mHeight: " + this.mHeight + " mRequestRender: " + this.mRequestRender + " mRenderMode: " + this.mRenderMode);
                                }
                                this.sGLThreadManager.wait();
                                if (runnable != null) {
                                    z = false;
                                    this.mEventQueue.add(0, runnable);
                                    runnable = null;
                                } else {
                                    z = false;
                                }
                                z7 = z7;
                                gl10 = gl10;
                                z6 = z6;
                            }
                        }
                        if (runnable != null) {
                            break;
                        }
                        if (z7) {
                            if (GLTextureView.LOG_SURFACE) {
                                Log.w("Keyguard-Editor-GLTextureView", "GLThread egl createSurface");
                            }
                            if (this.mEglHelper.createSurface()) {
                                synchronized (this.sGLThreadManager) {
                                    this.mFinishedCreatingEglSurface = true;
                                    this.sGLThreadManager.notifyAll();
                                }
                                z7 = false;
                                if (z8) {
                                    GL10 gl11 = (GL10) this.mEglHelper.createGL();
                                    this.sGLThreadManager.checkGLDriver(gl11);
                                    gl10 = gl11;
                                    z8 = false;
                                } else {
                                    gl10 = gl10;
                                }
                                if (z6) {
                                    if (GLTextureView.LOG_RENDERER) {
                                        Log.w("Keyguard-Editor-GLTextureView", "GLThread onSurfaceCreated");
                                    }
                                    gLTextureView3 = (GLTextureView) this.mGLTextureViewWeakRef.get();
                                    if (gLTextureView3 != null) {
                                        gLTextureView3.mRenderer.onSurfaceCreated(gl10, this.mEglHelper.mEglConfig);
                                    }
                                    z6 = false;
                                }
                                if (z9) {
                                    if (GLTextureView.LOG_RENDERER) {
                                        Log.w("Keyguard-Editor-GLTextureView", "GLThread onSurfaceChanged(" + i + ", " + i2 + ")");
                                    }
                                    gLTextureView2 = (GLTextureView) this.mGLTextureViewWeakRef.get();
                                    if (gLTextureView2 != null) {
                                        gLTextureView2.mRenderer.onSurfaceChanged(gl10, i, i2);
                                    }
                                    z9 = false;
                                }
                                if (GLTextureView.LOG_RENDERER_DRAW_FRAME) {
                                    Log.w("Keyguard-Editor-GLTextureView", "GLThread onDrawFrame tid=" + getId());
                                }
                                gLTextureView = (GLTextureView) this.mGLTextureViewWeakRef.get();
                                if (gLTextureView != null) {
                                    gLTextureView.mRenderer.onDrawFrame(gl10);
                                }
                                iSwap = this.mEglHelper.swap();
                                if (iSwap != 12288) {
                                    z4 = true;
                                    this.mHadSuccessfulSwap = true;
                                } else if (iSwap != 12302) {
                                    EglHelper.logEglErrorAsWarning("Keyguard-Editor-GLTextureView", "GLThread eglSwapBuffers", iSwap);
                                    KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperBlackScreen("GLTextureView.guardedRun_swap_error_surface_bad", null);
                                    synchronized (this.sGLThreadManager) {
                                        this.mSurfaceIsBad = true;
                                        this.sGLThreadManager.notifyAll();
                                        z4 = true;
                                    }
                                } else {
                                    if (GLTextureView.LOG_SURFACE) {
                                        Log.i("Keyguard-Editor-GLTextureView", "GLThread egl context lost tid=" + getId());
                                    }
                                    if (this.mHadSuccessfulSwap) {
                                        KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperFlashScreen("GLTextureView.guardedRun_egl_context_lost", null);
                                    }
                                    z4 = true;
                                    z5 = true;
                                }
                                if (z12) {
                                    z10 = z4;
                                }
                                z7 = z7;
                                z6 = z6;
                            } else {
                                synchronized (this.sGLThreadManager) {
                                    this.mFinishedCreatingEglSurface = true;
                                    this.mSurfaceIsBad = true;
                                    this.sGLThreadManager.notifyAll();
                                }
                                gl10 = gl10;
                            }
                            synchronized (this.sGLThreadManager) {
                                stopEglSurfaceLocked();
                                stopEglContextLocked();
                                throw th;
                            }
                        }
                        if (z8) {
                            GL10 gl12 = (GL10) this.mEglHelper.createGL();
                            this.sGLThreadManager.checkGLDriver(gl12);
                            gl10 = gl12;
                            z8 = false;
                        } else {
                            gl10 = gl10;
                        }
                        if (z6) {
                            if (GLTextureView.LOG_RENDERER) {
                                Log.w("Keyguard-Editor-GLTextureView", "GLThread onSurfaceCreated");
                            }
                            gLTextureView3 = (GLTextureView) this.mGLTextureViewWeakRef.get();
                            if (gLTextureView3 != null) {
                                gLTextureView3.mRenderer.onSurfaceCreated(gl10, this.mEglHelper.mEglConfig);
                            }
                            z6 = false;
                        }
                        if (z9) {
                            if (GLTextureView.LOG_RENDERER) {
                                Log.w("Keyguard-Editor-GLTextureView", "GLThread onSurfaceChanged(" + i + ", " + i2 + ")");
                            }
                            gLTextureView2 = (GLTextureView) this.mGLTextureViewWeakRef.get();
                            if (gLTextureView2 != null) {
                                gLTextureView2.mRenderer.onSurfaceChanged(gl10, i, i2);
                            }
                            z9 = false;
                        }
                        if (GLTextureView.LOG_RENDERER_DRAW_FRAME) {
                            Log.w("Keyguard-Editor-GLTextureView", "GLThread onDrawFrame tid=" + getId());
                        }
                        gLTextureView = (GLTextureView) this.mGLTextureViewWeakRef.get();
                        if (gLTextureView != null) {
                            gLTextureView.mRenderer.onDrawFrame(gl10);
                        }
                        iSwap = this.mEglHelper.swap();
                        if (iSwap != 12288) {
                            z4 = true;
                            this.mHadSuccessfulSwap = true;
                        } else if (iSwap != 12302) {
                            EglHelper.logEglErrorAsWarning("Keyguard-Editor-GLTextureView", "GLThread eglSwapBuffers", iSwap);
                            try {
                                KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperBlackScreen("GLTextureView.guardedRun_swap_error_surface_bad", null);
                            } catch (Exception unused) {
                            }
                            synchronized (this.sGLThreadManager) {
                                this.mSurfaceIsBad = true;
                                this.sGLThreadManager.notifyAll();
                            }
                            z4 = true;
                        } else {
                            if (GLTextureView.LOG_SURFACE) {
                                Log.i("Keyguard-Editor-GLTextureView", "GLThread egl context lost tid=" + getId());
                            }
                            if (this.mHadSuccessfulSwap) {
                                try {
                                    try {
                                        KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperFlashScreen("GLTextureView.guardedRun_egl_context_lost", null);
                                    } catch (Exception unused2) {
                                    }
                                } catch (Exception unused3) {
                                }
                            }
                            z4 = true;
                            z5 = true;
                        }
                        if (z12) {
                            z10 = z4;
                        }
                        z7 = z7;
                        z6 = z6;
                    } catch (Throwable th) {
                        synchronized (this.sGLThreadManager) {
                            stopEglSurfaceLocked();
                            stopEglContextLocked();
                            throw th;
                        }
                    }
                }
                runnable.run();
                gl10 = gl10;
            }
        }

        public void onPause() {
            synchronized (this.sGLThreadManager) {
                try {
                    if (GLTextureView.LOG_PAUSE_RESUME) {
                        Log.i("Keyguard-Editor-GLTextureView", "GLThread onPause tid=" + getId());
                    }
                    this.mRequestPaused = true;
                    this.sGLThreadManager.notifyAll();
                    while (!this.mExited && !this.mPaused) {
                        if (GLTextureView.LOG_PAUSE_RESUME) {
                            Log.i("Keyguard-Editor-GLTextureView", "Main thread onPause waiting for mPaused.");
                        }
                        try {
                            this.sGLThreadManager.wait();
                        } catch (InterruptedException unused) {
                            Thread.currentThread().interrupt();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void onResume() {
            synchronized (this.sGLThreadManager) {
                try {
                    if (GLTextureView.LOG_PAUSE_RESUME) {
                        Log.i("Keyguard-Editor-GLTextureView", "GLThread onResume tid=" + getId());
                    }
                    this.mRequestPaused = false;
                    this.mRequestRender = true;
                    this.mRenderComplete = false;
                    this.sGLThreadManager.notifyAll();
                    while (!this.mExited && this.mPaused && !this.mRenderComplete) {
                        if (GLTextureView.LOG_PAUSE_RESUME) {
                            Log.i("Keyguard-Editor-GLTextureView", "Main thread onResume waiting for !mPaused.");
                        }
                        try {
                            this.sGLThreadManager.wait();
                        } catch (InterruptedException unused) {
                            Thread.currentThread().interrupt();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void onWindowResize(int i, int i2) {
            synchronized (this.sGLThreadManager) {
                this.mWidth = i;
                this.mHeight = i2;
                this.mSizeChanged = true;
                this.mRequestRender = true;
                this.mRenderComplete = false;
                this.sGLThreadManager.notifyAll();
                while (!this.mExited && !this.mPaused && !this.mRenderComplete && ableToDraw()) {
                    if (GLTextureView.LOG_SURFACE) {
                        Log.i("Keyguard-Editor-GLTextureView", "Main thread onWindowResize waiting for render complete from tid=" + getId());
                    }
                    try {
                        this.sGLThreadManager.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void queueEvent(Runnable runnable) {
            if (runnable == null) {
                throw new IllegalArgumentException("r must not be null");
            }
            synchronized (this.sGLThreadManager) {
                this.mEventQueue.add(runnable);
                this.sGLThreadManager.notifyAll();
            }
        }

        public final boolean readyToDraw() {
            if (this.mPaused || !this.mHasSurface || this.mSurfaceIsBad || this.mWidth <= 0 || this.mHeight <= 0) {
                return false;
            }
            return this.mRequestRender || this.mRenderMode == 1;
        }

        public void requestExitAndWait() {
            synchronized (this.sGLThreadManager) {
                this.mShouldExit = true;
                this.sGLThreadManager.notifyAll();
                while (!this.mExited) {
                    try {
                        this.sGLThreadManager.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void requestReleaseEglContextLocked() {
            this.mShouldReleaseEglContext = true;
            this.sGLThreadManager.notifyAll();
        }

        public void requestRender() {
            synchronized (this.sGLThreadManager) {
                this.mRequestRender = true;
                this.sGLThreadManager.notifyAll();
            }
        }

        @Override // android.os.HandlerThread, java.lang.Thread, java.lang.Runnable
        public void run() {
            setName("GLThread " + getId());
            if (GLTextureView.LOG_THREADS) {
                Log.i("Keyguard-Editor-GLTextureView", "GLThread starting tid=" + getId());
            }
            try {
                guardedRun();
            } catch (InterruptedException unused) {
            } finally {
                this.sGLThreadManager.threadExiting(this);
            }
        }

        public void setRenderMode(int i) {
            if (i < 0 || i > 1) {
                throw new IllegalArgumentException("renderMode");
            }
            synchronized (this.sGLThreadManager) {
                this.mRenderMode = i;
                this.sGLThreadManager.notifyAll();
            }
        }

        public final void stopEglContextLocked() {
            if (this.mHaveEglContext) {
                this.mEglHelper.finish();
                this.mHaveEglContext = false;
                this.sGLThreadManager.releaseEglContextLocked(this);
            }
        }

        public final void stopEglSurfaceLocked() {
            if (this.mHaveEglSurface) {
                this.mHaveEglSurface = false;
                this.mEglHelper.destroySurface();
            }
        }

        public void surfaceCreated() {
            synchronized (this.sGLThreadManager) {
                try {
                    if (GLTextureView.LOG_THREADS) {
                        Log.i("Keyguard-Editor-GLTextureView", "GLThread surfaceCreated tid=" + getId());
                    }
                    this.mHasSurface = true;
                    this.mFinishedCreatingEglSurface = false;
                    this.sGLThreadManager.notifyAll();
                    while (this.mWaitingForSurface && !this.mFinishedCreatingEglSurface && !this.mExited) {
                        try {
                            this.sGLThreadManager.wait();
                        } catch (InterruptedException unused) {
                            Thread.currentThread().interrupt();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void surfaceDestroyed() {
            synchronized (this.sGLThreadManager) {
                try {
                    if (GLTextureView.LOG_THREADS) {
                        Log.i("Keyguard-Editor-GLTextureView", "GLThread surfaceDestroyed tid=" + getId());
                    }
                    this.mHasSurface = false;
                    this.sGLThreadManager.notifyAll();
                    while (!this.mWaitingForSurface && !this.mExited) {
                        try {
                            this.sGLThreadManager.wait();
                        } catch (InterruptedException unused) {
                            Thread.currentThread().interrupt();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public static class GLThreadManager {
        public static String TAG = "GLThreadManager";
        public GLThread mEglOwner;
        public boolean mGLESDriverCheckComplete;
        public int mGLESVersion;
        public boolean mLimitedGLESContexts;
        public boolean mMultipleGLESContextsAllowed;

        public GLThreadManager() {
        }

        public synchronized void checkGLDriver(GL10 gl10) {
            try {
                if (!this.mGLESDriverCheckComplete) {
                    checkGLESVersion();
                    String strGlGetString = gl10.glGetString(7937);
                    if (this.mGLESVersion < 131072) {
                        this.mMultipleGLESContextsAllowed = !strGlGetString.startsWith("Q3Dimension MSM7500 ");
                        notifyAll();
                    }
                    this.mLimitedGLESContexts = !this.mMultipleGLESContextsAllowed;
                    if (GLTextureView.LOG_SURFACE) {
                        Log.w(TAG, "checkGLDriver renderer = \"" + strGlGetString + "\" multipleContextsAllowed = " + this.mMultipleGLESContextsAllowed + " mLimitedGLESContexts = " + this.mLimitedGLESContexts);
                    }
                    this.mGLESDriverCheckComplete = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }

        public final void checkGLESVersion() {
        }

        public synchronized void releaseEglContextLocked(GLThread gLThread) {
            try {
                if (this.mEglOwner == gLThread) {
                    this.mEglOwner = null;
                }
                notifyAll();
            } catch (Throwable th) {
                throw th;
            }
        }

        public synchronized boolean shouldReleaseEGLContextWhenPausing() {
            return this.mLimitedGLESContexts;
        }

        public synchronized boolean shouldTerminateEGLWhenPausing() {
            checkGLESVersion();
            return !this.mMultipleGLESContextsAllowed;
        }

        public synchronized void threadExiting(GLThread gLThread) {
            try {
                if (GLTextureView.LOG_THREADS) {
                    Log.i(TAG, "GLThread exiting tid=" + gLThread.getId());
                }
                gLThread.mExited = true;
                if (this.mEglOwner == gLThread) {
                    this.mEglOwner = null;
                }
                notifyAll();
            } catch (Throwable th) {
                throw th;
            }
        }

        public synchronized boolean tryAcquireEglContextLocked(GLThread gLThread) {
            GLThread gLThread2 = this.mEglOwner;
            if (gLThread2 != gLThread && gLThread2 != null) {
                checkGLESVersion();
                if (this.mMultipleGLESContextsAllowed) {
                    return true;
                }
                GLThread gLThread3 = this.mEglOwner;
                if (gLThread3 != null) {
                    gLThread3.requestReleaseEglContextLocked();
                }
                return false;
            }
            this.mEglOwner = gLThread;
            notifyAll();
            return true;
        }
    }

    public interface GLWrapper {
        GL wrap(GL gl);
    }

    public static class LogWriter extends Writer {
        public StringBuilder mBuilder = new StringBuilder();

        @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            flushBuilder();
        }

        @Override // java.io.Writer, java.io.Flushable
        public void flush() {
            flushBuilder();
        }

        public final void flushBuilder() {
            if (this.mBuilder.length() > 0) {
                Log.v("Keyguard-Editor-GLTextureView", this.mBuilder.toString());
                StringBuilder sb = this.mBuilder;
                sb.delete(0, sb.length());
            }
        }

        @Override // java.io.Writer
        public void write(char[] cArr, int i, int i2) {
            for (int i3 = 0; i3 < i2; i3++) {
                char c = cArr[i + i3];
                if (c == '\n') {
                    flushBuilder();
                } else {
                    this.mBuilder.append(c);
                }
            }
        }
    }

    public class SimpleEGLConfigChooser extends ComponentSizeChooser {
        public SimpleEGLConfigChooser(boolean z) {
            super(8, 8, 8, 0, z ? 16 : 0, 0);
        }
    }

    public interface TextureUpdatedListener {
        void onTextureUpdated();
    }

    /* JADX INFO: renamed from: $r8$lambda$2FqNM_lWzWqtZSh5BNvCiL-XKxM, reason: not valid java name */
    public static /* synthetic */ void m771$r8$lambda$2FqNM_lWzWqtZSh5BNvCiLXKxM(GLTextureView gLTextureView) {
        gLTextureView.getClass();
        try {
            GlassRenderer glassRenderer = gLTextureView.glassRender;
            if (glassRenderer != null) {
                glassRenderer.cancelGlassAnim();
                gLTextureView.glassRender = null;
            }
        } catch (Exception e) {
            Log.e("Keyguard-Editor-GLTextureView", "Error releasing glassRender resources", e);
        }
    }

    public static /* synthetic */ void $r8$lambda$4P8Xx8OeWehrpLTFTqnKQpRbKH8(GLTextureView gLTextureView, boolean z) {
        GlassRenderer glassRenderer = gLTextureView.glassRender;
        if (glassRenderer == null) {
            return;
        }
        glassRenderer.setGlassWithBlur(z);
        gLTextureView.requestRender();
    }

    public static /* synthetic */ void $r8$lambda$4eYXui_2QXt_yXQqbRTgp5M4cvA(final GLTextureView gLTextureView, int i) {
        GlassRenderer glassRenderer;
        if (!gLTextureView.shouldHideAnim(i)) {
            gLTextureView.destroyEglSurfaceAndContext();
            gLTextureView.createEglSurfaceAndContext();
        }
        final float nowInitScaleX = (gLTextureView.gestureManager.getFirstLoad() || (glassRenderer = gLTextureView.glassRender) == null) ? 1.0f : glassRenderer.getNowInitScaleX();
        if (gLTextureView.glassRender != null) {
            gLTextureView.setCurrentMagicType(i);
            gLTextureView.glassRender.cancelGlassAnim();
            if (gLTextureView.shouldHideAnim(i)) {
                gLTextureView.glassRender.startHideGlassAnim();
            } else {
                gLTextureView.glassRender.setEffectType(i);
            }
            gLTextureView.glassRender.setGlassAnimCallback(new AnimImageWallpaperRenderer.CallBack() { // from class: com.miui.keyguard.editor.edit.wallpaper.GLTextureView.2
                @Override // com.miui.miwallpaper.opengl.AnimImageWallpaperRenderer.CallBack
                public void onRefresh(boolean z) {
                    GLTextureView.this.requestRender();
                }
            }, gLTextureView.new AnonymousClass3(i));
            if (i != 0 && DeviceUtil.INSTANCE.supportGlassAnim()) {
                gLTextureView.glassRender.startShowGlassAnim();
            }
        }
        if (gLTextureView.gestureManager != null) {
            ViewUtil.INSTANCE.runOnUIThread(gLTextureView.getContext() instanceof AppCompatActivity ? (AppCompatActivity) gLTextureView.getContext() : null, null, new Runnable() { // from class: com.miui.keyguard.editor.edit.wallpaper.GLTextureView$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    GLTextureView.m772$r8$lambda$RVIcuxiqY5lFVNQ2MKI0MeeEm4(this.f$0, nowInitScaleX);
                }
            });
        }
    }

    public static /* synthetic */ void $r8$lambda$PkHfcjZFUWxE14yrcjBiayRZE0Q(GLTextureView gLTextureView, WallpaperTypeInfo wallpaperTypeInfo, boolean z) {
        GlassRenderer glassRenderer = gLTextureView.glassRender;
        if (glassRenderer != null) {
            glassRenderer.setBitmap(wallpaperTypeInfo.getBitmap(), z);
            gLTextureView.requestRender();
        }
    }

    public static /* synthetic */ void $r8$lambda$QqMkMiRTG_ubZGINdEwGJ6HILao(GLTextureView gLTextureView) {
        GlassRenderer glassRenderer = gLTextureView.glassRender;
        if (glassRenderer != null) {
            glassRenderer.resetGlassState();
            gLTextureView.requestRender();
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$RVIcuxiqY-5lFVNQ2MKI0MeeEm4, reason: not valid java name */
    public static /* synthetic */ void m772$r8$lambda$RVIcuxiqY5lFVNQ2MKI0MeeEm4(GLTextureView gLTextureView, float f) {
        GestureManager gestureManager = gLTextureView.gestureManager;
        GlassRenderer glassRenderer = gLTextureView.glassRender;
        gestureManager.switchGlassFitMatrix(glassRenderer != null ? glassRenderer.getNowInitScaleX() : 1.0f, f);
        Matrix matrix = gLTextureView.gestureManager.getMatrix();
        if (matrix != null) {
            gLTextureView.onApply(matrix, null, null);
        }
    }

    public static /* synthetic */ void $r8$lambda$exFRl5xAwLgWKjAV59VS2CfYOyU(GLTextureView gLTextureView, int i, boolean z) {
        GlassRenderer glassRenderer = gLTextureView.glassRender;
        if (glassRenderer != null) {
            glassRenderer.updateGlassEffectAndBlurState(i, z);
            gLTextureView.requestRender();
        }
    }

    static {
        boolean editor_debug = EditorApplicationProxy.Companion.getEDITOR_DEBUG();
        DEBUG = editor_debug;
        LOG_ATTACH_DETACH = editor_debug;
        LOG_THREADS = editor_debug;
        LOG_PAUSE_RESUME = editor_debug;
        LOG_SURFACE = editor_debug;
        LOG_RENDERER = editor_debug;
        LOG_RENDERER_DRAW_FRAME = editor_debug;
        LOG_EGL = editor_debug;
    }

    public GLTextureView(Context context) {
        super(context);
        this.needHideAnim = false;
        this.mTextureUpdated = new AtomicBoolean(false);
        this.mLifecycleObserverRegistered = false;
        this.mAutoLifecycleEnabled = false;
        this.mLifecycleObserver = new DefaultLifecycleObserver() { // from class: com.miui.keyguard.editor.edit.wallpaper.GLTextureView.1
            @Override // androidx.lifecycle.DefaultLifecycleObserver
            public void onPause(LifecycleOwner lifecycleOwner) {
                if (!GLTextureView.this.mAutoLifecycleEnabled || GLTextureView.this.mRenderer == null || GLTextureView.this.mGLThread == null) {
                    return;
                }
                GLTextureView.this.onPause();
            }

            @Override // androidx.lifecycle.DefaultLifecycleObserver
            public void onResume(LifecycleOwner lifecycleOwner) {
                if (!GLTextureView.this.mAutoLifecycleEnabled || GLTextureView.this.mRenderer == null || GLTextureView.this.mGLThread == null) {
                    return;
                }
                GLTextureView.this.onResume();
            }
        };
        this.mTextureUpdatedListeners = new HashSet();
        this.mThisWeakRef = new WeakReference(this);
        this.gestureManager = null;
        this.currentMagicType = -1;
        this.glassRender = null;
        this.wallpaperCallback = null;
        this.scaleable = Boolean.FALSE;
        this.wallpaperChangedListenerSet = new HashSet();
        init();
    }

    public GLTextureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.needHideAnim = false;
        this.mTextureUpdated = new AtomicBoolean(false);
        this.mLifecycleObserverRegistered = false;
        this.mAutoLifecycleEnabled = false;
        this.mLifecycleObserver = new DefaultLifecycleObserver() { // from class: com.miui.keyguard.editor.edit.wallpaper.GLTextureView.1
            @Override // androidx.lifecycle.DefaultLifecycleObserver
            public void onPause(LifecycleOwner lifecycleOwner) {
                if (!GLTextureView.this.mAutoLifecycleEnabled || GLTextureView.this.mRenderer == null || GLTextureView.this.mGLThread == null) {
                    return;
                }
                GLTextureView.this.onPause();
            }

            @Override // androidx.lifecycle.DefaultLifecycleObserver
            public void onResume(LifecycleOwner lifecycleOwner) {
                if (!GLTextureView.this.mAutoLifecycleEnabled || GLTextureView.this.mRenderer == null || GLTextureView.this.mGLThread == null) {
                    return;
                }
                GLTextureView.this.onResume();
            }
        };
        this.mTextureUpdatedListeners = new HashSet();
        this.mThisWeakRef = new WeakReference(this);
        this.gestureManager = null;
        this.currentMagicType = -1;
        this.glassRender = null;
        this.wallpaperCallback = null;
        this.scaleable = Boolean.FALSE;
        this.wallpaperChangedListenerSet = new HashSet();
        init();
    }

    private void init() {
        super.setSurfaceTextureListener(this);
        if (Process.INSTANCE.isThemeManagerProcess()) {
            enableAutoLifecycle(true);
        }
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void addGestureListener(WallpaperChangedListener wallpaperChangedListener) {
        if (wallpaperChangedListener == null) {
            return;
        }
        this.wallpaperChangedListenerSet.add(wallpaperChangedListener);
    }

    public void addNextTextureUpdatedListener(TextureUpdatedListener textureUpdatedListener) {
        this.mTextureUpdatedListeners.add(textureUpdatedListener);
    }

    public void addTextureUpdatedListener(TextureUpdatedListener textureUpdatedListener) {
        Log.i("Keyguard-Editor-GLTextureView", "addSurfaceTextureUpdatedListener");
        if (this.mTextureUpdated.get()) {
            textureUpdatedListener.onTextureUpdated();
        } else {
            this.mTextureUpdatedListeners.add(textureUpdatedListener);
        }
    }

    public final void checkRenderThreadState() {
        if (this.mGLThread != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
    }

    public void createEglSurfaceAndContext() {
        try {
            if (this.mDetached || getWindowToken() == null || getSurfaceTexture() == null) {
                Log.w("Keyguard-Editor-GLTextureView", "Skip createEglSurfaceAndContext: view detached or surface invalid");
                KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperBlackScreen("GLTextureView.createEglSurfaceAndContext_detached", null);
                return;
            }
            GLThread gLThread = this.mGLThread;
            if (gLThread != null) {
                gLThread.createEglContextAndSurface();
            } else {
                Log.e("Keyguard-Editor-GLTextureView", "createEglSurfaceAndContext: mGLThread is null");
                KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperBlackScreen("GLTextureView.createEglSurfaceAndContext_glThread_null", null);
            }
        } catch (Exception unused) {
        }
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public int currentMagicType() {
        return this.currentMagicType;
    }

    public void destroyEglSurfaceAndContext() {
        GLThread gLThread = this.mGLThread;
        if (gLThread == null) {
            Log.e("Keyguard-Editor-GLTextureView", "mGLThread is null");
        } else {
            gLThread.destroyEglContextAndSurface();
        }
    }

    public void enableAutoLifecycle(boolean z) {
        this.mAutoLifecycleEnabled = z;
        if (!z) {
            unregisterFromLifecycleOwner();
        } else if (isAttachedToWindow()) {
            registerToLifecycleOwner();
        }
    }

    public void finalize() throws Throwable {
        try {
            GLThread gLThread = this.mGLThread;
            if (gLThread != null) {
                gLThread.requestExitAndWait();
            }
        } finally {
            super.finalize();
        }
    }

    public int getDebugFlags() {
        return this.mDebugFlags;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public GestureManager getGestureManager() {
        return this.gestureManager;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public float getGlassInitScaleX() {
        if (this.glassRender == null) {
            return 1.0f;
        }
        return this.gestureManager.getRenderScaleWhenZoom() == null ? this.glassRender.getNowInitScaleX() : this.gestureManager.getRenderScaleWhenZoom().floatValue();
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public boolean getInTemplate() {
        return true;
    }

    public boolean getNeedHideAnim() {
        return this.needHideAnim;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public Bitmap getOriginBitmap() {
        GlassRenderer glassRenderer = this.glassRender;
        if (glassRenderer != null) {
            return glassRenderer.getBitmap();
        }
        return null;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public WallpaperPositionInfo getPosition(Bitmap bitmap) {
        GestureManager gestureManager = this.gestureManager;
        if (gestureManager != null) {
            return gestureManager.getPosition(bitmap);
        }
        return null;
    }

    public boolean getPreserveEGLContextOnPause() {
        return this.mPreserveEGLContextOnPause;
    }

    public int getRenderMode() {
        return this.mGLThread.getRenderMode();
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public Boolean getScaleable() {
        return this.scaleable;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public Surface getVideoSurface() {
        return null;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public Bitmap getWallpaperBitmap(Bitmap bitmap) {
        getBitmap(bitmap);
        return bitmap;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public WallpaperCallback getWallpaperCallback() {
        return this.wallpaperCallback;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @NonNull
    public HashSet<WallpaperChangedListener> getWallpaperChangedListenerSet() {
        return this.wallpaperChangedListenerSet;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public Matrix getWallpaperMatrix() {
        GestureManager gestureManager = this.gestureManager;
        if (gestureManager != null) {
            return gestureManager.getMatrix();
        }
        return null;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public String getWallpaperType() {
        return null;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public boolean isCut() {
        return false;
    }

    public boolean isTextureUpdated() {
        return this.mTextureUpdated.get();
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.GestureListener
    public void onApply(Matrix matrix, Boolean bool, Matrix matrix2) {
        GlassRenderer glassRenderer;
        if (bool != null && bool.booleanValue() && (glassRenderer = this.glassRender) != null) {
            GlassShaderUtils.setInitScaleAndTranslate(matrix2, glassRenderer);
        }
        this.mCachedMatrix = matrix;
        if (this.glassRender != null) {
            if (getWidth() <= 0 || getHeight() <= 0) {
                GestureManager gestureManager = this.gestureManager;
                if (gestureManager != null) {
                    Point screenSize = gestureManager.getScreenSize();
                    Log.e("Keyguard-Editor-GLTextureView", "gl error, new size:" + screenSize);
                    setLeftTopRightBottom(0, 0, screenSize.x, screenSize.y);
                }
                try {
                    KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperBlackScreen("GLTextureView.onApply_size_zero", null);
                } catch (Exception unused) {
                }
            }
            GlassShaderUtils.setTransformMatrix(matrix, this, this.glassRender);
            requestRender();
            Iterator<WallpaperChangedListener> it = getWallpaperChangedListenerSet().iterator();
            while (it.hasNext()) {
                it.next().onChanged(matrix);
            }
        }
    }

    @Override // android.view.TextureView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (LOG_ATTACH_DETACH) {
            Log.d("Keyguard-Editor-GLTextureView", "onAttachedToWindow reattach =" + this.mDetached);
        }
        if (this.mDetached && this.mRenderer != null) {
            GLThread gLThread = this.mGLThread;
            int renderMode = gLThread != null ? gLThread.getRenderMode() : 1;
            GLThread gLThread2 = new GLThread(this.mThisWeakRef);
            this.mGLThread = gLThread2;
            if (renderMode != 1) {
                gLThread2.setRenderMode(renderMode);
            }
            this.mGLThread.start();
        }
        this.mDetached = false;
        getViewTreeObserver().addOnGlobalLayoutListener(this);
        registerToLifecycleOwner();
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        if (LOG_ATTACH_DETACH) {
            Log.d("Keyguard-Editor-GLTextureView", "onDetachedFromWindow");
        }
        unregisterFromLifecycleOwner();
        if (this.mGLThread != null) {
            queueEvent(new Runnable() { // from class: com.miui.keyguard.editor.edit.wallpaper.GLTextureView$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    GLTextureView.m771$r8$lambda$2FqNM_lWzWqtZSh5BNvCiLXKxM(this.f$0);
                }
            });
            this.mGLThread.requestExitAndWait();
        }
        this.mDetached = true;
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        GestureManager gestureManager = this.gestureManager;
        if (gestureManager == null || this.glassRender == null) {
            return;
        }
        gestureManager.targetViewFitSrcDrawable(null);
    }

    public void onPause() {
        this.mGLThread.onPause();
    }

    public void onResume() {
        this.mGLThread.onResume();
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        Log.i("Keyguard-Editor-GLTextureView", "onSurfaceCreated: " + currentMagicType());
        if (this.mCachedMatrix == null || this.glassRender == null || this.mGLThread == null || currentMagicType() != 60000) {
            return;
        }
        GlassShaderUtils.setTransformMatrix(this.mCachedMatrix, this, this.glassRender);
        requestRender();
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        this.mGLThread.surfaceCreated();
        onSurfaceTextureSizeChanged(surfaceTexture, i, i2);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        GLThread gLThread = this.mGLThread;
        if (gLThread == null) {
            return true;
        }
        gLThread.surfaceDestroyed();
        this.mGLThread.destroyEglContextAndSurface();
        return true;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        this.mGLThread.onWindowResize(i, i2);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        this.mTextureUpdated.set(true);
        Iterator it = this.mTextureUpdatedListeners.iterator();
        while (it.hasNext()) {
            ((TextureUpdatedListener) it.next()).onTextureUpdated();
        }
        Log.i("Keyguard-Editor-GLTextureView", "onSurfaceTextureUpdated");
        this.mTextureUpdatedListeners.clear();
        for (WallpaperChangedListener wallpaperChangedListener : this.wallpaperChangedListenerSet) {
            if (wallpaperChangedListener instanceof TextureUpdatedListener) {
                ((TextureUpdatedListener) wallpaperChangedListener).onTextureUpdated();
            }
        }
    }

    public void onSwitchWallpaperBitmap(WallpaperTypeInfo wallpaperTypeInfo) {
        switchWallpaperBitmap(wallpaperTypeInfo, true);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.scaleable.booleanValue() || motionEvent == null) {
            return false;
        }
        GestureManager gestureManager = this.gestureManager;
        if (gestureManager == null) {
            return this.scaleable.booleanValue();
        }
        gestureManager.notifyCheckNeedRemoveBackground();
        boolean zOnTouchEvent = this.gestureManager.getMScaleGestureDetector().onTouchEvent(motionEvent);
        boolean zOnTouchEvent2 = this.gestureManager.getGestureDetector().onTouchEvent(motionEvent);
        boolean z = motionEvent.getAction() == 1 || motionEvent.getAction() == 262;
        if (!zOnTouchEvent2 && z) {
            this.gestureManager.notifyGestureEnd();
        }
        return zOnTouchEvent || zOnTouchEvent2;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void onWallpaperSelected(WallpaperTypeInfo wallpaperTypeInfo, String str) {
        switchWallpaperBitmap(wallpaperTypeInfo, false);
    }

    public void queueEvent(Runnable runnable) {
        this.mGLThread.queueEvent(runnable);
    }

    public final void registerToLifecycleOwner() {
        if (this.mAutoLifecycleEnabled) {
            Object context = getContext();
            if (context instanceof LifecycleOwner) {
                Lifecycle lifecycle = ((LifecycleOwner) context).getLifecycle();
                if (!this.mLifecycleObserverRegistered) {
                    lifecycle.addObserver(this.mLifecycleObserver);
                    this.mLifecycleObserverRegistered = true;
                }
                if (!isAttachedToWindow() || this.mRenderer == null || this.mGLThread == null) {
                    return;
                }
                if (lifecycle.getCurrentState() == Lifecycle.State.RESUMED) {
                    onResume();
                } else {
                    onPause();
                }
            }
        }
    }

    public void requestRender() {
        this.mGLThread.requestRender();
        Log.i("Keyguard-Editor-GLTextureView", "requestRender");
    }

    public void resetGlassState() {
        GlassRenderer glassRenderer;
        GestureManager gestureManager = this.gestureManager;
        if (gestureManager != null && (glassRenderer = this.glassRender) != null) {
            gestureManager.recordRenderScaleWhenZoom(Float.valueOf(glassRenderer.getNowInitScaleX()));
        }
        queueEvent(new Runnable() { // from class: com.miui.keyguard.editor.edit.wallpaper.GLTextureView$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                GLTextureView.$r8$lambda$QqMkMiRTG_ubZGINdEwGJ6HILao(this.f$0);
            }
        });
    }

    public void setCurrentMagicType(int i) {
        this.currentMagicType = i;
    }

    public void setDebugFlags(int i) {
        this.mDebugFlags = i;
    }

    public void setEGLConfigChooser(int i, int i2, int i3, int i4, int i5, int i6) {
        setEGLConfigChooser(new ComponentSizeChooser(i, i2, i3, i4, i5, i6));
    }

    public void setEGLConfigChooser(EGLConfigChooser eGLConfigChooser) {
        checkRenderThreadState();
        this.mEGLConfigChooser = eGLConfigChooser;
    }

    public void setEGLConfigChooser(boolean z) {
        setEGLConfigChooser(new SimpleEGLConfigChooser(z));
    }

    public void setEGLContextClientVersion(int i) {
        checkRenderThreadState();
        this.mEGLContextClientVersion = i;
    }

    public void setEGLContextFactory(EGLContextFactory eGLContextFactory) {
        checkRenderThreadState();
        this.mEGLContextFactory = eGLContextFactory;
    }

    public void setEGLWindowSurfaceFactory(EGLWindowSurfaceFactory eGLWindowSurfaceFactory) {
        checkRenderThreadState();
        this.mEGLWindowSurfaceFactory = eGLWindowSurfaceFactory;
    }

    public void setGLWrapper(GLWrapper gLWrapper) {
        this.mGLWrapper = gLWrapper;
    }

    public void setGlassWithBlur(final boolean z) {
        if (this.glassRender != null) {
            queueEvent(new Runnable() { // from class: com.miui.keyguard.editor.edit.wallpaper.GLTextureView$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    GLTextureView.$r8$lambda$4P8Xx8OeWehrpLTFTqnKQpRbKH8(this.f$0, z);
                }
            });
        }
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setImageAndPosition(@Nullable Bitmap bitmap, @Nullable WallpaperPositionInfo wallpaperPositionInfo, @Nullable String str, @Nullable String str2) {
        if (bitmap == null) {
            Log.e("Keyguard-Editor-GLTextureView", "GLTextureView setImageAndPosition bitmap is null");
            try {
                KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperBlackScreen("GLTextureView.setImageAndPosition_bitmap_null", null);
                return;
            } catch (Exception unused) {
                return;
            }
        }
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
        this.gestureManager = null;
        MagicWallpaperGestureManager magicWallpaperGestureManager = new MagicWallpaperGestureManager(getContext(), this, this, bitmapDrawable, wallpaperPositionInfo);
        this.gestureManager = magicWallpaperGestureManager;
        if (str2 != null) {
            magicWallpaperGestureManager.setFromWhere(str2);
        }
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setInTemplate(boolean z) {
    }

    public void setLastMatrix(Matrix matrix) {
        if (getGestureManager() == null || matrix == null) {
            return;
        }
        getGestureManager().setMatrix(matrix);
    }

    public void setNeedHideAnim(boolean z) {
        this.needHideAnim = z;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setOriginBitmap(@Nullable Bitmap bitmap) {
    }

    public void setPreserveEGLContextOnPause(boolean z) {
        this.mPreserveEGLContextOnPause = z;
    }

    public void setRenderMode(int i) {
        this.mGLThread.setRenderMode(i);
    }

    public void setRenderer(GLSurfaceView.Renderer renderer) {
        checkRenderThreadState();
        if (this.mEGLConfigChooser == null) {
            this.mEGLConfigChooser = new SimpleEGLConfigChooser(true);
        }
        if (this.mEGLContextFactory == null) {
            this.mEGLContextFactory = new DefaultContextFactory();
        }
        if (this.mEGLWindowSurfaceFactory == null) {
            this.mEGLWindowSurfaceFactory = new DefaultWindowSurfaceFactory();
        }
        this.mRenderer = renderer;
        GLThread gLThread = new GLThread(this.mThisWeakRef);
        this.mGLThread = gLThread;
        gLThread.start();
        if (isAttachedToWindow()) {
            registerToLifecycleOwner();
        }
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setScaleable(Boolean bool) {
        this.scaleable = bool;
        GestureManager gestureManager = this.gestureManager;
        if (gestureManager != null) {
            gestureManager.setScaleable(bool);
        }
    }

    @Override // android.view.TextureView
    @Deprecated
    public void setSurfaceTextureListener(TextureView.SurfaceTextureListener surfaceTextureListener) {
        Log.e("Keyguard-Editor-GLTextureView", "setSurfaceTextureListener preserved, setRenderer() instead?");
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setWallpaperCallback(@Nullable WallpaperCallback wallpaperCallback) {
        this.wallpaperCallback = wallpaperCallback;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setWallpaperType(@Nullable String str) {
    }

    public final boolean shouldHideAnim(int i) {
        return this.needHideAnim && DeviceUtil.INSTANCE.supportGlassAnim() && MagicType.INSTANCE.isOrigin(i);
    }

    public void stopGLThread() {
        GLThread gLThread = this.mGLThread;
        if (gLThread != null) {
            gLThread.requestExitAndWait();
        }
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void switchGlassShader(final int i, WallpaperEditParam wallpaperEditParam) {
        Log.i("Keyguard-Editor-GLTextureView", "switchGlassShader: " + i);
        queueEvent(new Runnable() { // from class: com.miui.keyguard.editor.edit.wallpaper.GLTextureView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                GLTextureView.$r8$lambda$4eYXui_2QXt_yXQqbRTgp5M4cvA(this.f$0, i);
            }
        });
    }

    public final void switchWallpaperBitmap(final WallpaperTypeInfo wallpaperTypeInfo, final boolean z) {
        try {
            if (wallpaperTypeInfo == null || this.glassRender == null) {
                if (wallpaperTypeInfo == null) {
                    KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperNotDisplayed("GLTextureView.switchWallpaperBitmap_wallpaperTypeInfo_null", null);
                    return;
                } else {
                    KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperBlackScreen("GLTextureView.switchWallpaperBitmap_glassRender_null", null);
                    return;
                }
            }
            Bitmap bitmap = wallpaperTypeInfo.getBitmap();
            if (bitmap == null) {
                KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperBlackScreen("GLTextureView.switchWallpaperBitmap_bitmap_null", null);
                return;
            }
            setImageAndPosition(bitmap, null, null, null);
            this.glassRender.updateIsDarken(wallpaperTypeInfo.isNeedDark());
            this.scaleable = Boolean.valueOf(WallpaperEditor.canScale(wallpaperTypeInfo.getType()));
            if (getWidth() > 0) {
                this.gestureManager.targetViewFitSrcDrawable(null);
            }
            queueEvent(new Runnable() { // from class: com.miui.keyguard.editor.edit.wallpaper.GLTextureView$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    GLTextureView.$r8$lambda$PkHfcjZFUWxE14yrcjBiayRZE0Q(this.f$0, wallpaperTypeInfo, z);
                }
            });
        } catch (Exception unused) {
        }
    }

    public final void unregisterFromLifecycleOwner() {
        if (this.mLifecycleObserverRegistered) {
            Object context = getContext();
            if (!(context instanceof LifecycleOwner)) {
                this.mLifecycleObserverRegistered = false;
            } else {
                ((LifecycleOwner) context).getLifecycle().removeObserver(this.mLifecycleObserver);
                this.mLifecycleObserverRegistered = false;
            }
        }
    }

    public void updateGlassEffectAndBlurState(final int i, final boolean z) {
        queueEvent(new Runnable() { // from class: com.miui.keyguard.editor.edit.wallpaper.GLTextureView$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                GLTextureView.$r8$lambda$exFRl5xAwLgWKjAV59VS2CfYOyU(this.f$0, i, z);
            }
        });
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public View wallpaperLayerView() {
        return this;
    }
}
