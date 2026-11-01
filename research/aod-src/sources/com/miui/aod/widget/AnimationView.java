package com.miui.aod.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.TextureView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AnimationView extends TextureView implements TextureView.SurfaceTextureListener {
    public final Choreographer.FrameCallback frameCallback;
    public volatile boolean mAnimationRunning;
    public final List mDrawerList;
    public boolean mPendingStartAnimation;
    public boolean mSurfaceAvailable;

    public interface AnimationDrawer {

        public interface AnimationStateListener {
            default void onAnimationEnd() {
            }

            default void onAnimationStart() {
            }
        }

        boolean onAnimationDraw(Canvas canvas, long j);
    }

    /* JADX INFO: renamed from: $r8$lambda$fvTr0zU-9a2fWgtHtlL9ny62pT8, reason: not valid java name */
    public static /* synthetic */ boolean m555$r8$lambda$fvTr0zU9a2fWgtHtlL9ny62pT8(Canvas canvas, long j, AnimationDrawer animationDrawer) {
        return !animationDrawer.onAnimationDraw(canvas, j);
    }

    public AnimationView(Context context) {
        this(context, null);
    }

    public AnimationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AnimationView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDrawerList = Collections.synchronizedList(new ArrayList());
        this.frameCallback = new Choreographer.FrameCallback() { // from class: com.miui.aod.widget.AnimationView.1
            @Override // android.view.Choreographer.FrameCallback
            public void doFrame(long j) {
                AnimationView.this.dispatchDraw(j);
                if (AnimationView.this.mAnimationRunning) {
                    Choreographer.getInstance().postFrameCallback(this);
                }
            }
        };
        init();
    }

    public void addAnimationDrawer(AnimationDrawer animationDrawer) {
        if (animationDrawer != null) {
            synchronized (this.mDrawerList) {
                try {
                    Iterator it = this.mDrawerList.iterator();
                    while (it.hasNext()) {
                        if (animationDrawer.equals((AnimationDrawer) it.next())) {
                            Log.e("AnimationView", "addAnimationDrawer: duplicate");
                            return;
                        }
                    }
                    this.mDrawerList.add(animationDrawer);
                    if (!this.mAnimationRunning) {
                        startAnimation();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX WARN: Bottom block not found for handler: all -> 0x003a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void dispatchDraw(final long r5) {
        /*
            r4 = this;
            boolean r0 = r4.mSurfaceAvailable
            if (r0 == 0) goto L3f
            java.util.List r0 = r4.mDrawerList
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L3f
            android.graphics.Canvas r0 = r4.lockCanvas()
            if (r0 == 0) goto L3f
            android.graphics.PorterDuff$Mode r1 = android.graphics.PorterDuff.Mode.CLEAR     // Catch: java.lang.Throwable -> L3a
            r2 = 0
            r0.drawColor(r2, r1)     // Catch: java.lang.Throwable -> L3a
            java.util.List r1 = r4.mDrawerList     // Catch: java.lang.Throwable -> L3a
            monitor-enter(r1)     // Catch: java.lang.Throwable -> L3a
            java.util.List r2 = r4.mDrawerList     // Catch: java.lang.Throwable -> L31
            com.miui.aod.widget.AnimationView$$ExternalSyntheticLambda0 r3 = new com.miui.aod.widget.AnimationView$$ExternalSyntheticLambda0     // Catch: java.lang.Throwable -> L31
            r3.<init>()     // Catch: java.lang.Throwable -> L31
            r2.removeIf(r3)     // Catch: java.lang.Throwable -> L31
            java.util.List r5 = r4.mDrawerList     // Catch: java.lang.Throwable -> L31
            boolean r5 = r5.isEmpty()     // Catch: java.lang.Throwable -> L31
            if (r5 == 0) goto L33
            r4.stopAnimation()     // Catch: java.lang.Throwable -> L31
            goto L33
        L31:
            r5 = move-exception
            goto L38
        L33:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L31
            r4.unlockCanvasAndPostSafely(r0)
            return
        L38:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L31
            throw r5     // Catch: java.lang.Throwable -> L3a
        L3a:
            r5 = move-exception
            r4.unlockCanvasAndPostSafely(r0)
            throw r5
        L3f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.aod.widget.AnimationView.dispatchDraw(long):void");
    }

    public final void init() {
        setOpaque(false);
        this.mSurfaceAvailable = false;
        this.mAnimationRunning = false;
        setSurfaceTextureListener(this);
    }

    @Override // android.view.TextureView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mDrawerList.isEmpty()) {
            return;
        }
        startAnimation();
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mDrawerList.clear();
        stopAnimation();
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        this.mSurfaceAvailable = true;
        if (this.mPendingStartAnimation) {
            startAnimation();
            this.mPendingStartAnimation = false;
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return true;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public final void startAnimation() {
        if (this.mAnimationRunning) {
            return;
        }
        if (!this.mSurfaceAvailable) {
            this.mPendingStartAnimation = true;
        } else {
            this.mAnimationRunning = true;
            Choreographer.getInstance().postFrameCallback(this.frameCallback);
        }
    }

    public final void stopAnimation() {
        this.mAnimationRunning = false;
        this.mPendingStartAnimation = false;
        Choreographer.getInstance().postFrameCallback(this.frameCallback);
    }

    public final void unlockCanvasAndPostSafely(Canvas canvas) {
        if (this.mSurfaceAvailable) {
            unlockCanvasAndPost(canvas);
        }
    }
}
