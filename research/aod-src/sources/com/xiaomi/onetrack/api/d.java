package com.xiaomi.onetrack.api;

import java.util.concurrent.ThreadFactory;

/* JADX INFO: loaded from: classes2.dex */
class d implements ThreadFactory {
    final /* synthetic */ c a;

    public d(c cVar) {
        this.a = cVar;
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, "onetrack_track_thread");
    }
}
