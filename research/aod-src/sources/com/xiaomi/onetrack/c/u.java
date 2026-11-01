package com.xiaomi.onetrack.c;

/* JADX INFO: loaded from: classes2.dex */
class u implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ r b;

    public u(r rVar, String str) {
        this.b = rVar;
        this.a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (j.b()) {
            this.b.a(null, null, null, this.a, false);
        }
    }
}
