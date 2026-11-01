package com.miui.aod.util;

import android.os.Handler;
import com.miui.aod.util.wakelock.WakeLock;

/* JADX INFO: loaded from: classes.dex */
public class CancelableWakeLockTask {
    public final Handler mHandler;
    public Runnable mLastWrappedTask;
    public int mLockCount;
    public final String mTag;
    public final Runnable mTask;
    public final WakeLock mWakeLock;

    /* JADX INFO: renamed from: $r8$lambda$3vrCq4yk-HlRMA-3s-xaHWFoc3Q, reason: not valid java name */
    public static /* synthetic */ void m534$r8$lambda$3vrCq4ykHlRMA3sxaHWFoc3Q(CancelableWakeLockTask cancelableWakeLockTask) {
        cancelableWakeLockTask.mLockCount--;
        cancelableWakeLockTask.mTask.run();
    }

    public CancelableWakeLockTask(Runnable runnable, WakeLock wakeLock, Handler handler, String str) {
        this.mTask = runnable;
        this.mWakeLock = wakeLock;
        this.mHandler = handler;
        this.mTag = str;
    }

    public void execute(long j) {
        remove();
        Runnable runnableWrapWithLog = this.mWakeLock.wrapWithLog(new Runnable() { // from class: com.miui.aod.util.CancelableWakeLockTask$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CancelableWakeLockTask.m534$r8$lambda$3vrCq4ykHlRMA3sxaHWFoc3Q(this.f$0);
            }
        }, this.mTag);
        this.mLastWrappedTask = runnableWrapWithLog;
        this.mLockCount++;
        this.mHandler.postDelayed(runnableWrapWithLog, j);
    }

    public boolean hasScheduled() {
        return this.mLockCount > 0;
    }

    public void remove() {
        if (this.mLockCount == 1) {
            this.mWakeLock.release(this.mTag);
            this.mLockCount = 0;
        }
        if (this.mLockCount <= 1) {
            this.mHandler.removeCallbacks(this.mLastWrappedTask);
            return;
        }
        throw new RuntimeException(this.mTag + " wake count " + this.mLockCount);
    }
}
