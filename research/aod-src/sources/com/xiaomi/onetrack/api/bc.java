package com.xiaomi.onetrack.api;

/* JADX INFO: loaded from: classes2.dex */
class bc implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ ba b;

    public bc(ba baVar, boolean z) {
        this.b = baVar;
        this.a = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.a) {
            com.xiaomi.onetrack.b.a.a().c(this.b.e.getAdEventAppId());
        }
        com.xiaomi.onetrack.b.a.a().c(this.b.e.getAppId());
    }
}
