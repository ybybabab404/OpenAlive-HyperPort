package com.miui.aod.util.wakelock;

import android.os.Handler;

/* JADX INFO: loaded from: classes.dex */
public class DelayedWakeLock implements WakeLock {
    public final Handler mHandler;
    public final WakeLock mInner;

    public DelayedWakeLock(Handler handler, WakeLock wakeLock) {
        this.mHandler = handler;
        this.mInner = wakeLock;
    }

    @Override // com.miui.aod.util.wakelock.WakeLock
    public void acquire(String str) {
        this.mInner.acquire(str);
    }

    @Override // com.miui.aod.util.wakelock.WakeLock
    public void release(final String str) {
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.aod.util.wakelock.DelayedWakeLock$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mInner.release(str);
            }
        }, 100L);
    }
}
