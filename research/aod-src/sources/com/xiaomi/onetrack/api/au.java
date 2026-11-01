package com.xiaomi.onetrack.api;

/* JADX INFO: loaded from: classes2.dex */
class au implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ at b;

    public au(at atVar, boolean z) {
        this.b = atVar;
        this.a = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.a) {
            com.xiaomi.onetrack.b.e.a();
            com.xiaomi.onetrack.b.a.a().c(this.b.e.getAdEventAppId());
        }
        com.xiaomi.onetrack.b.a.a().c(this.b.e.getAppId());
    }
}
