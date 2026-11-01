package com.xiaomi.micloudsdk.sync;

import android.util.Log;

/* JADX INFO: loaded from: classes2.dex */
class LogcatSyncLogSender extends SyncLogSender {
    @Override // com.xiaomi.micloudsdk.sync.SyncLogSender
    public void openSyncLog() {
    }

    @Override // com.xiaomi.micloudsdk.sync.SyncLogSender
    public void release() {
    }

    @Override // com.xiaomi.micloudsdk.sync.SyncLogSender
    public void sendLog(String str, String str2) {
        Log.i(str, str2);
    }
}
