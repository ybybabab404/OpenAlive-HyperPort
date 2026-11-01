package com.xiaomi.onetrack.api;

/* JADX INFO: loaded from: classes2.dex */
class bd implements Runnable {
    final /* synthetic */ ba a;

    public bd(ba baVar) {
        this.a = baVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (com.xiaomi.onetrack.c.j.b()) {
            com.xiaomi.onetrack.c.ag.a().a(0, true);
            com.xiaomi.onetrack.c.ag.a().a(1, true);
        }
    }
}
