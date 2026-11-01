package com.miui.aod.util.wakelock;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;
import com.miui.aod.Utils;

/* JADX INFO: loaded from: classes.dex */
public interface WakeLock {
    /* JADX INFO: renamed from: $r8$lambda$pwloA2KmxTN-lnYfEpU8sMbsta4, reason: not valid java name */
    static /* synthetic */ void m551$r8$lambda$pwloA2KmxTNlnYfEpU8sMbsta4(Runnable runnable, WakeLock wakeLock, String str) {
        try {
            runnable.run();
        } finally {
            wakeLock.release(str);
        }
    }

    static PowerManager.WakeLock createDrawInner(Context context, String str) {
        return ((PowerManager) context.getSystemService(PowerManager.class)).newWakeLock(128, str);
    }

    static PowerManager.WakeLock createDrawInner(PowerManager powerManager, String str) {
        return powerManager.newWakeLock(128, str);
    }

    static PowerManager.WakeLock createPartialInner(Context context, String str) {
        return ((PowerManager) context.getSystemService(PowerManager.class)).newWakeLock(1, str);
    }

    static PowerManager.WakeLock createPartialInner(PowerManager powerManager, String str) {
        return powerManager.newWakeLock(1, str);
    }

    static WakeLock wrap(Context context, String str) {
        return wrap(context, str, 20000L);
    }

    static WakeLock wrap(Context context, String str, long j) {
        return wrap(createPartialInner(context, str), j);
    }

    static WakeLock wrap(PowerManager.WakeLock wakeLock) {
        return wrap(wakeLock, 20000L);
    }

    static WakeLock wrap(PowerManager.WakeLock wakeLock, long j) {
        return new WakeLock(j, wakeLock) { // from class: com.miui.aod.util.wakelock.WakeLock.2
            public long innerMaxTimeOut;
            public final /* synthetic */ PowerManager.WakeLock val$inner;
            public final /* synthetic */ long val$maxTimeout;

            {
                this.val$maxTimeout = j;
                this.val$inner = wakeLock;
                this.innerMaxTimeOut = j;
            }

            @Override // com.miui.aod.util.wakelock.WakeLock
            public void acquire(String str) {
                if (Utils.isAodProcess()) {
                    return;
                }
                Log.d("AODWakeLock", "longWakeLock : acquire from:" + str + " inner :" + this.val$inner.isHeld());
                this.val$inner.acquire(this.innerMaxTimeOut);
            }

            @Override // com.miui.aod.util.wakelock.WakeLock
            public void release(String str) {
                if (Utils.isAodProcess()) {
                    return;
                }
                Log.d("AODWakeLock", "longWakeLock : release from:" + str + " hold:" + this.val$inner.isHeld());
                if (this.val$inner.isHeld()) {
                    this.val$inner.release();
                }
                Log.d("AODWakeLock", "longWakeLock : release after:" + str + " hold:" + this.val$inner.isHeld());
            }

            @Override // com.miui.aod.util.wakelock.WakeLock
            public void setMaxAcquireTime(long j2) {
                this.innerMaxTimeOut = j2;
            }
        };
    }

    static WakeLock wrapForAnimation(Context context, String str) {
        return wrapForAnimation(context, str, 20000L);
    }

    static WakeLock wrapForAnimation(Context context, String str, long j) {
        return wrapForAnimation(context, str, j, false);
    }

    static WakeLock wrapForAnimation(Context context, String str, long j, boolean z) {
        PowerManager.WakeLock wakeLockCreateDrawInner = createDrawInner(context, str + ":draw");
        PowerManager.WakeLock wakeLockCreatePartialInner = createPartialInner(context, str + ":cpu");
        wakeLockCreateDrawInner.setReferenceCounted(z);
        wakeLockCreatePartialInner.setReferenceCounted(z);
        final WakeLock wakeLockWrap = wrap(wakeLockCreateDrawInner, j);
        return new WakeLock() { // from class: com.miui.aod.util.wakelock.WakeLock.1
            @Override // com.miui.aod.util.wakelock.WakeLock
            public void acquire(String str2) {
                WakeLock.this.acquire(str2 + ":cpu");
                wakeLockWrap.acquire(str2 + ":draw");
            }

            @Override // com.miui.aod.util.wakelock.WakeLock
            public void release(String str2) {
                wakeLockWrap.release(str2 + ":draw");
                WakeLock.this.release(str2 + ":cpu");
            }

            @Override // com.miui.aod.util.wakelock.WakeLock
            public void setMaxAcquireTime(long j2) {
                wakeLockWrap.setMaxAcquireTime(j2);
                WakeLock.this.setMaxAcquireTime(j2);
            }
        };
    }

    static Runnable wrapImpl(WakeLock wakeLock, Runnable runnable) {
        return wrapImplWithLog(wakeLock, runnable, "wrap");
    }

    static Runnable wrapImplWithLog(final WakeLock wakeLock, final Runnable runnable, final String str) {
        wakeLock.acquire(str);
        return new Runnable() { // from class: com.miui.aod.util.wakelock.WakeLock$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                WakeLock.m551$r8$lambda$pwloA2KmxTNlnYfEpU8sMbsta4(runnable, wakeLock, str);
            }
        };
    }

    void acquire(String str);

    void release(String str);

    default void setMaxAcquireTime(long j) {
    }

    default Runnable wrap(Runnable runnable) {
        return wrapImpl(this, runnable);
    }

    default Runnable wrapWithLog(Runnable runnable, String str) {
        return wrapImplWithLog(this, runnable, str);
    }
}
