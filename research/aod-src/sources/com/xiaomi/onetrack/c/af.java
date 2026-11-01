package com.xiaomi.onetrack.c;

/* JADX INFO: loaded from: classes2.dex */
class af implements Runnable {
    final /* synthetic */ ad a;

    public af(ad adVar) {
        this.a = adVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.a.i.get()) {
            com.xiaomi.onetrack.b.g.a().c();
        }
        this.a.i.set(true);
    }
}
