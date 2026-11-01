package com.xiaomi.micloudsdk.request.utils;

import android.os.SystemClock;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes2.dex */
public class SyncRequestParam {
    public static volatile long sLastManualSyncTimeInMillis = -1;
    public static AtomicInteger sManualSyncCounter = new AtomicInteger(0);

    public static void decrementManualSyncCount() {
        sManualSyncCounter.decrementAndGet();
    }

    public static void incrementManualSyncCountAndRecordCurMillis() {
        sManualSyncCounter.incrementAndGet();
        sLastManualSyncTimeInMillis = SystemClock.uptimeMillis();
    }
}
