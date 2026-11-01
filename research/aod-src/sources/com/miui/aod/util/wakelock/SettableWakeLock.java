package com.miui.aod.util.wakelock;

import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class SettableWakeLock {
    public boolean mAcquired;
    public final WakeLock mInner;
    public final String mWhy;

    public SettableWakeLock(WakeLock wakeLock, String str) {
        Objects.requireNonNull(wakeLock, "inner wakelock required");
        this.mInner = wakeLock;
        this.mWhy = str;
    }

    public synchronized void setAcquired(boolean z) {
        try {
            if (this.mAcquired != z) {
                if (z) {
                    this.mInner.acquire(this.mWhy);
                } else {
                    this.mInner.release(this.mWhy);
                }
                this.mAcquired = z;
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
