package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.OneTrack;

/* JADX INFO: loaded from: classes2.dex */
class ax implements Runnable {
    final /* synthetic */ aw a;

    public ax(aw awVar) {
        this.a = awVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.xiaomi.onetrack.util.r.a().a(Boolean.TRUE);
        if (OneTrack.isRestrictGetNetworkInfo()) {
            ai.a().f();
        }
    }
}
