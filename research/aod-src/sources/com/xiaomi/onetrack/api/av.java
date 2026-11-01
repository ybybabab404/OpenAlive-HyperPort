package com.xiaomi.onetrack.api;

/* JADX INFO: loaded from: classes2.dex */
class av implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ at b;

    public av(at atVar, int i) {
        this.b = atVar;
        this.a = i;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (com.xiaomi.onetrack.c.j.b() && this.a == 2) {
            com.xiaomi.onetrack.c.ag.a().a(0, true);
            com.xiaomi.onetrack.c.ag.a().a(1, true);
            com.xiaomi.onetrack.a.c.b.a().b(false);
        }
    }
}
