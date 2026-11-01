package com.miui.aod.widget;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.view.TextureView;

/* JADX INFO: loaded from: classes.dex */
public class FrameAnimationView extends TextureView implements TextureView.SurfaceTextureListener {
    public FrameAnimationController mFrameAnimation;
    public final int mFrameInterval;
    public final boolean mFromFile;
    public boolean mPendingStartAnimation;
    public final int[] mResArray;
    public final String[] mResFilePathArray;
    public boolean mSurfaceReady;

    public FrameAnimationView(Context context, int i, int[] iArr) {
        super(context);
        i = i <= 0 ? 30 : i;
        this.mFromFile = false;
        this.mFrameInterval = 1000 / i;
        this.mResArray = iArr;
        this.mResFilePathArray = null;
        init();
    }

    public FrameAnimationView(Context context, int i, String[] strArr) {
        super(context);
        i = i <= 0 ? 30 : i;
        this.mFromFile = true;
        this.mFrameInterval = 1000 / i;
        this.mResArray = null;
        this.mResFilePathArray = strArr;
        init();
    }

    public long getDuration() {
        int length;
        int i;
        int[] iArr = this.mResArray;
        if (iArr != null) {
            length = iArr.length;
            i = this.mFrameInterval;
        } else {
            String[] strArr = this.mResFilePathArray;
            if (strArr == null) {
                return 0L;
            }
            length = strArr.length;
            i = this.mFrameInterval;
        }
        return length * i;
    }

    public final void init() {
        setOpaque(false);
        this.mPendingStartAnimation = false;
        this.mSurfaceReady = false;
        FrameAnimationController frameAnimationController = new FrameAnimationController(this, this);
        this.mFrameAnimation = frameAnimationController;
        frameAnimationController.setFrameInterval(this.mFrameInterval);
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mFrameAnimation.release();
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        this.mSurfaceReady = true;
        if (this.mPendingStartAnimation) {
            startAnimation();
            this.mPendingStartAnimation = false;
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        this.mSurfaceReady = false;
        return true;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public void setAnimationCallBack(FrameAnimationController.AnimationCallBack animationCallBack) {
        FrameAnimationController frameAnimationController = this.mFrameAnimation;
        if (frameAnimationController != null) {
            frameAnimationController.setAnimationCallBack(animationCallBack);
        }
    }

    public void startAnimation() {
        if (!this.mSurfaceReady) {
            this.mPendingStartAnimation = true;
            return;
        }
        if (this.mFromFile) {
            this.mFrameAnimation.startAnimation(this.mResFilePathArray, 0);
        } else {
            this.mFrameAnimation.startAnimation(this.mResArray, 0);
        }
        this.mPendingStartAnimation = false;
    }
}
