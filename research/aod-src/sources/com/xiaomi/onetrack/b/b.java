package com.xiaomi.onetrack.b;

/* JADX INFO: loaded from: classes2.dex */
class b implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ a b;

    public b(a aVar, String str) {
        this.b = aVar;
        this.a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.c(this.a);
    }
}
