package com.miui.aod.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;
import com.miui.aod.util.wakelock.WakeLock;

/* JADX INFO: loaded from: classes.dex */
public class NotificationAnimationView extends View {
    public final Choreographer.FrameCallback frameCallback;
    public volatile boolean mAnimationRunning;
    public AnimationDrawer mDrawer;
    public volatile long mFrameTime;
    public WakeLock mWakeLock;

    public interface AnimationDrawer {

        public interface AnimationStateListener {
            default void onAnimationEnd() {
            }
        }

        long getDuration();

        boolean onAnimationDraw(Canvas canvas, long j);

        default void release() {
        }

        default void setAnimationListener(AnimationStateListener animationStateListener) {
        }

        default void setRepeatMode(boolean z) {
        }
    }

    public static /* synthetic */ void $r8$lambda$jApNs4hW4I9xFotz3uUzr1aDisE(NotificationAnimationView notificationAnimationView) {
        notificationAnimationView.releaseDrawer();
        notificationAnimationView.invalidate();
        notificationAnimationView.mAnimationRunning = false;
        Log.i("NotificationAnimationView", "stopAnimation: release ");
    }

    public NotificationAnimationView(Context context) {
        this(context, null);
    }

    public NotificationAnimationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NotificationAnimationView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.frameCallback = new Choreographer.FrameCallback() { // from class: com.miui.aod.widget.NotificationAnimationView.1
            @Override // android.view.Choreographer.FrameCallback
            public void doFrame(long j) {
                NotificationAnimationView.this.dispatchDraw(j);
                if (NotificationAnimationView.this.mAnimationRunning) {
                    Choreographer.getInstance().postFrameCallback(this);
                }
            }
        };
        init();
    }

    public final void dispatchDraw(long j) {
        this.mFrameTime = j;
        invalidate();
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public final void init() {
        this.mAnimationRunning = false;
        this.mWakeLock = WakeLock.wrapForAnimation(getContext(), "NotificationAnimationView");
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        AnimationDrawer animationDrawer = this.mDrawer;
        if (animationDrawer == null || animationDrawer.onAnimationDraw(canvas, this.mFrameTime)) {
            return;
        }
        Log.i("NotificationAnimationView", "onDraw: finished");
        stopAnimation();
    }

    public void releaseDrawer() {
        AnimationDrawer animationDrawer = this.mDrawer;
        if (animationDrawer != null) {
            animationDrawer.release();
            this.mDrawer = null;
        }
    }

    public void setAnimationDrawer(AnimationDrawer animationDrawer) {
        this.mDrawer = animationDrawer;
    }

    public void startAnimation() {
        if (this.mAnimationRunning) {
            return;
        }
        this.mWakeLock.setMaxAcquireTime(10000L);
        this.mWakeLock.acquire("NotificationAnimationView");
        StringBuilder sb = new StringBuilder();
        sb.append("startAnimation: drawer:");
        AnimationDrawer animationDrawer = this.mDrawer;
        sb.append(animationDrawer == null ? "null" : animationDrawer.toString());
        Log.d("NotificationAnimationView", sb.toString());
        this.mAnimationRunning = true;
        Choreographer.getInstance().postFrameCallback(this.frameCallback);
    }

    public void stopAnimation() {
        if (this.mDrawer != null || this.mAnimationRunning) {
            this.mWakeLock.wrapWithLog(new Runnable() { // from class: com.miui.aod.widget.NotificationAnimationView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationAnimationView.$r8$lambda$jApNs4hW4I9xFotz3uUzr1aDisE(this.f$0);
                }
            }, "NotificationAnimationView").run();
            Choreographer.getInstance().removeFrameCallback(this.frameCallback);
        }
    }
}
