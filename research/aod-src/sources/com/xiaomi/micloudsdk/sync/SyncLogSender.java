package com.xiaomi.micloudsdk.sync;

/* JADX INFO: loaded from: classes2.dex */
abstract class SyncLogSender {
    public abstract void openSyncLog();

    public abstract void release();

    public abstract void sendLog(String str, String str2);
}
