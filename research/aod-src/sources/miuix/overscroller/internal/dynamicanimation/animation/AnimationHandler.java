package miuix.overscroller.internal.dynamicanimation.animation;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Choreographer;
import androidx.annotation.RequiresApi;
import java.lang.reflect.Method;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes2.dex */
public class AnimationHandler {
    public static final boolean SUPPORT_MI_MOTION = Boolean.parseBoolean(LiteSystemProperties.get("ro.display.mimotion", "false"));
    public static final ThreadLocal sAnimatorHandler = new ThreadLocal();
    public AnimationFrameCallbackProvider mProvider;
    public final ArrayMap mDelayedCallbackStartTime = new ArrayMap();
    public final ArrayList mAnimationCallbacks = new ArrayList();
    public final AnimationCallbackDispatcher mCallbackDispatcher = new AnimationCallbackDispatcher();
    public long mCurrentFrameTime = 0;
    public boolean mListDirty = false;

    public class AnimationCallbackDispatcher {
        public AnimationCallbackDispatcher() {
        }

        public void dispatchAnimationFrame(long j) {
            AnimationHandler.this.doAnimationFrame(j);
            if (AnimationHandler.this.mAnimationCallbacks.size() > 0) {
                AnimationHandler.this.getProvider().postFrameCallback();
            }
        }
    }

    public interface AnimationFrameCallback {
        boolean doAnimationFrame(long j);
    }

    public static abstract class AnimationFrameCallbackProvider {
        public final AnimationCallbackDispatcher mDispatcher;

        public AnimationFrameCallbackProvider(AnimationCallbackDispatcher animationCallbackDispatcher) {
            this.mDispatcher = animationCallbackDispatcher;
        }

        public long getFrameDeltaNanos() {
            return 0L;
        }

        public boolean getFromFramePeriodNsecs() {
            return false;
        }

        public abstract boolean isCurrentThread();

        public abstract void postFrameCallback();

        public void postVsyncCallback() {
        }
    }

    @Deprecated
    public static class FrameCallbackProvider14 extends AnimationFrameCallbackProvider {
        public final Handler mHandler;
        public long mLastFrameTime;
        public final Runnable mRunnable;

        /* JADX INFO: renamed from: miuix.overscroller.internal.dynamicanimation.animation.AnimationHandler$FrameCallbackProvider14$1, reason: invalid class name */
        class AnonymousClass1 implements Runnable {
            public final /* synthetic */ FrameCallbackProvider14 this$0;

            @Override // java.lang.Runnable
            public void run() {
                this.this$0.mLastFrameTime = SystemClock.uptimeMillis();
                FrameCallbackProvider14 frameCallbackProvider14 = this.this$0;
                frameCallbackProvider14.mDispatcher.dispatchAnimationFrame(frameCallbackProvider14.mLastFrameTime);
            }
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.AnimationHandler.AnimationFrameCallbackProvider
        public boolean isCurrentThread() {
            return Thread.currentThread() == this.mHandler.getLooper().getThread();
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.AnimationHandler.AnimationFrameCallbackProvider
        public void postFrameCallback() {
            this.mHandler.postDelayed(this.mRunnable, Math.max(10 - (SystemClock.uptimeMillis() - this.mLastFrameTime), 0L));
        }
    }

    public static class FrameCallbackProvider16 extends AnimationFrameCallbackProvider {
        public final Choreographer mChoreographer;
        public final Choreographer.FrameCallback mChoreographerCallback;
        public final Looper mLooper;

        /* JADX INFO: renamed from: miuix.overscroller.internal.dynamicanimation.animation.AnimationHandler$FrameCallbackProvider16$1, reason: invalid class name */
        class AnonymousClass1 implements Choreographer.FrameCallback {
            public final /* synthetic */ FrameCallbackProvider16 this$0;

            @Override // android.view.Choreographer.FrameCallback
            public void doFrame(long j) {
                this.this$0.mDispatcher.dispatchAnimationFrame(j);
            }
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.AnimationHandler.AnimationFrameCallbackProvider
        public boolean isCurrentThread() {
            return Thread.currentThread() == this.mLooper.getThread();
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.AnimationHandler.AnimationFrameCallbackProvider
        public void postFrameCallback() {
            this.mChoreographer.postFrameCallback(this.mChoreographerCallback);
        }
    }

    @RequiresApi
    public static class FrameCallbackProvider33 extends AnimationFrameCallbackProvider {
        public final Choreographer mChoreographer;
        public final Choreographer.FrameCallback mChoreographerCallback;
        public long mFrameDeltaNanos;
        public boolean mFromFramePeriodNsecs;
        public Method mGetFramePeriodNsecs;
        public final Looper mLooper;
        public final Choreographer.VsyncCallback mVsyncCallback;

        public FrameCallbackProvider33(AnimationCallbackDispatcher animationCallbackDispatcher) {
            super(animationCallbackDispatcher);
            this.mChoreographer = Choreographer.getInstance();
            this.mGetFramePeriodNsecs = null;
            this.mLooper = Looper.myLooper();
            this.mFrameDeltaNanos = 0L;
            this.mFromFramePeriodNsecs = false;
            if (AnimationHandler.SUPPORT_MI_MOTION && this.mGetFramePeriodNsecs == null) {
                try {
                    Method declaredMethod = Choreographer.class.getDeclaredMethod("getFramePeriodNsecs", new Class[0]);
                    this.mGetFramePeriodNsecs = declaredMethod;
                    declaredMethod.setAccessible(true);
                } catch (Exception unused) {
                    Log.w("OverScroller Animation", "get getFramePeriodNSec failed ");
                }
            }
            this.mVsyncCallback = new Choreographer.VsyncCallback() { // from class: miuix.overscroller.internal.dynamicanimation.animation.AnimationHandler.FrameCallbackProvider33.1
                /* JADX WARN: Code duplicated, block: B:12:0x003f  */
                @Override // android.view.Choreographer.VsyncCallback
                public void onVsync(Choreographer.FrameData frameData) {
                    boolean z;
                    Choreographer.FrameTimeline[] frameTimelines;
                    int length;
                    if (FrameCallbackProvider33.this.mGetFramePeriodNsecs != null) {
                        try {
                            long jLongValue = ((Long) FrameCallbackProvider33.this.mGetFramePeriodNsecs.invoke(FrameCallbackProvider33.this.mChoreographer, new Object[0])).longValue();
                            if (jLongValue != -1) {
                                FrameCallbackProvider33.this.mFrameDeltaNanos = jLongValue;
                                try {
                                    FrameCallbackProvider33.this.mFromFramePeriodNsecs = true;
                                    z = true;
                                } catch (Exception unused2) {
                                    z = true;
                                    Log.w("OverScroller Animation", "onVsync getFramePeriodNSec failed");
                                }
                            } else {
                                z = false;
                            }
                        } catch (Exception unused3) {
                            z = false;
                        }
                    } else {
                        z = false;
                    }
                    if (z || (length = (frameTimelines = frameData.getFrameTimelines()).length) <= 1) {
                        return;
                    }
                    int i = length - 1;
                    FrameCallbackProvider33.this.mFrameDeltaNanos = Math.round(((frameTimelines[i].getExpectedPresentationTimeNanos() - frameTimelines[0].getExpectedPresentationTimeNanos()) * 1.0d) / ((double) i));
                }
            };
            this.mChoreographerCallback = new Choreographer.FrameCallback() { // from class: miuix.overscroller.internal.dynamicanimation.animation.AnimationHandler.FrameCallbackProvider33.2
                @Override // android.view.Choreographer.FrameCallback
                public void doFrame(long j) {
                    FrameCallbackProvider33.this.mDispatcher.dispatchAnimationFrame(j);
                }
            };
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.AnimationHandler.AnimationFrameCallbackProvider
        public long getFrameDeltaNanos() {
            return this.mFrameDeltaNanos;
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.AnimationHandler.AnimationFrameCallbackProvider
        public boolean getFromFramePeriodNsecs() {
            return this.mFromFramePeriodNsecs;
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.AnimationHandler.AnimationFrameCallbackProvider
        public boolean isCurrentThread() {
            return Thread.currentThread() == this.mLooper.getThread();
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.AnimationHandler.AnimationFrameCallbackProvider
        public void postFrameCallback() {
            this.mChoreographer.postVsyncCallback(this.mVsyncCallback);
            this.mChoreographer.postFrameCallback(this.mChoreographerCallback);
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.AnimationHandler.AnimationFrameCallbackProvider
        public void postVsyncCallback() {
            this.mChoreographer.postVsyncCallback(this.mVsyncCallback);
        }
    }

    public static AnimationHandler getInstance() {
        ThreadLocal threadLocal = sAnimatorHandler;
        if (threadLocal.get() == null) {
            threadLocal.set(new AnimationHandler());
        }
        return (AnimationHandler) threadLocal.get();
    }

    public void addAnimationFrameCallback(AnimationFrameCallback animationFrameCallback, long j) {
        if (this.mAnimationCallbacks.size() == 0) {
            getProvider().postFrameCallback();
        }
        if (!this.mAnimationCallbacks.contains(animationFrameCallback)) {
            this.mAnimationCallbacks.add(animationFrameCallback);
        }
        if (j > 0) {
            this.mDelayedCallbackStartTime.put(animationFrameCallback, Long.valueOf(SystemClock.uptimeMillis() + j));
        }
    }

    public final void cleanUpList() {
        if (this.mListDirty) {
            for (int size = this.mAnimationCallbacks.size() - 1; size >= 0; size--) {
                if (this.mAnimationCallbacks.get(size) == null) {
                    this.mAnimationCallbacks.remove(size);
                }
            }
            this.mListDirty = false;
        }
    }

    public final void doAnimationFrame(long j) {
        long jUptimeMillis = SystemClock.uptimeMillis();
        for (int i = 0; i < this.mAnimationCallbacks.size(); i++) {
            AnimationFrameCallback animationFrameCallback = (AnimationFrameCallback) this.mAnimationCallbacks.get(i);
            if (animationFrameCallback != null && isCallbackDue(animationFrameCallback, jUptimeMillis)) {
                animationFrameCallback.doAnimationFrame(j);
            }
        }
        cleanUpList();
    }

    public long getFrameDeltaNanos() {
        return getProvider().getFrameDeltaNanos();
    }

    public boolean getFromFramePeriodNsecs() {
        return getProvider().getFromFramePeriodNsecs();
    }

    public AnimationFrameCallbackProvider getProvider() {
        if (this.mProvider == null) {
            this.mProvider = new FrameCallbackProvider33(this.mCallbackDispatcher);
        }
        return this.mProvider;
    }

    public final boolean isCallbackDue(AnimationFrameCallback animationFrameCallback, long j) {
        Long l = (Long) this.mDelayedCallbackStartTime.get(animationFrameCallback);
        if (l == null) {
            return true;
        }
        if (l.longValue() >= j) {
            return false;
        }
        this.mDelayedCallbackStartTime.remove(animationFrameCallback);
        return true;
    }

    public boolean isCurrentThread() {
        return getProvider().isCurrentThread();
    }

    public void postVsyncCallback() {
        getProvider().postVsyncCallback();
    }

    public void removeCallback(AnimationFrameCallback animationFrameCallback) {
        this.mDelayedCallbackStartTime.remove(animationFrameCallback);
        int iIndexOf = this.mAnimationCallbacks.indexOf(animationFrameCallback);
        if (iIndexOf >= 0) {
            this.mAnimationCallbacks.set(iIndexOf, null);
            this.mListDirty = true;
        }
    }
}
