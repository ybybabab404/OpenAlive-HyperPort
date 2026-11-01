package com.miui.miwallpaper.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import java.util.ArrayList;
import java.util.Iterator;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import miuix.animation.listener.TransitionListener;

/* JADX INFO: loaded from: classes2.dex */
public class GlassRenderer implements GLSurfaceView.Renderer {
    public float initScaleXI;
    public float initScaleYI;
    public float initTranslateX;
    public float initTranslateY;
    public PreviewGlassAnimRender mPreviewGlassAnimRender;
    public ArrayList mRenderListeners = new ArrayList();

    public interface IRenderListener {
        void onDrawFrame(GL10 gl10);

        default void onSurfaceChanged(GL10 gl10, int i, int i2) {
        }

        default void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        }
    }

    public GlassRenderer(Context context, Bitmap bitmap, int i) {
        this.mPreviewGlassAnimRender = new PreviewGlassAnimRender(context, bitmap, i);
    }

    public void cancelGlassAnim() {
        PreviewGlassAnimRender previewGlassAnimRender = this.mPreviewGlassAnimRender;
        if (previewGlassAnimRender != null) {
            previewGlassAnimRender.cancelGlassAnim();
        }
    }

    public Bitmap getBitmap() {
        return this.mPreviewGlassAnimRender.getBitmap();
    }

    public float getCurrentInitScaleX(int i) {
        return this.mPreviewGlassAnimRender.getCurrentInitScaleX(i);
    }

    public float[] getMatrix(Bitmap bitmap, int i, int i2) {
        return this.mPreviewGlassAnimRender.getMatrix(bitmap, i, i2);
    }

    public float getNowInitScaleX() {
        return this.mPreviewGlassAnimRender.getNowInitScaleX();
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onDrawFrame(GL10 gl10) {
        this.mPreviewGlassAnimRender.onDrawFrame();
        Iterator it = this.mRenderListeners.iterator();
        while (it.hasNext()) {
            ((IRenderListener) it.next()).onDrawFrame(gl10);
        }
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        this.mPreviewGlassAnimRender.onSurfaceChanged(i, i2);
        Iterator it = this.mRenderListeners.iterator();
        while (it.hasNext()) {
            ((IRenderListener) it.next()).onSurfaceChanged(gl10, i, i2);
        }
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        this.mPreviewGlassAnimRender.onSurfaceCreated();
        Iterator it = this.mRenderListeners.iterator();
        while (it.hasNext()) {
            ((IRenderListener) it.next()).onSurfaceCreated(gl10, eGLConfig);
        }
    }

    public void registerRenderListener(IRenderListener iRenderListener) {
        this.mRenderListeners.add(iRenderListener);
    }

    public void resetGlassState() {
        this.mPreviewGlassAnimRender.resetGlassState();
    }

    public void setBitmap(Bitmap bitmap, boolean... zArr) {
        this.mPreviewGlassAnimRender.setBitmap(bitmap, zArr);
    }

    public void setEffectType(int i) {
        this.mPreviewGlassAnimRender.setEffectType(i);
    }

    public void setGlassAnimCallback(AnimImageWallpaperRenderer.CallBack callBack, TransitionListener transitionListener) {
        this.mPreviewGlassAnimRender.setCallBack(callBack, transitionListener);
    }

    public void setGlassWithBlur(boolean z) {
        this.mPreviewGlassAnimRender.setGlassWithBlur(z);
    }

    public void setScale(float f, float f2) {
        this.mPreviewGlassAnimRender.setScale(f, f2);
    }

    public void setTranslate(float f, float f2) {
        this.mPreviewGlassAnimRender.setTranslate(f, f2);
    }

    public void startHideGlassAnim() {
        PreviewGlassAnimRender previewGlassAnimRender = this.mPreviewGlassAnimRender;
        if (previewGlassAnimRender != null) {
            previewGlassAnimRender.startHideGlassAnim();
        }
    }

    public void startShowGlassAnim() {
        PreviewGlassAnimRender previewGlassAnimRender = this.mPreviewGlassAnimRender;
        if (previewGlassAnimRender != null) {
            previewGlassAnimRender.hideGlassIOR();
            this.mPreviewGlassAnimRender.startShowGlassAnim();
        }
    }

    public void updateGlassEffectAndBlurState(int i, boolean z) {
        this.mPreviewGlassAnimRender.updateGlassEffectAndBlurState(i, z);
    }

    public void updateIsDarken(boolean z) {
        this.mPreviewGlassAnimRender.setDarken(z);
    }
}
