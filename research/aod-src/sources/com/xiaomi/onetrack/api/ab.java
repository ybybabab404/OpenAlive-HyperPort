package com.xiaomi.onetrack.api;

/* JADX INFO: loaded from: classes2.dex */
class ab implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ c b;

    public ab(c cVar, boolean z) {
        this.b = cVar;
        this.a = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.g.a(this.a);
        com.xiaomi.onetrack.util.ad.a(com.xiaomi.onetrack.util.u.a(this.b.e), this.a);
    }
}
