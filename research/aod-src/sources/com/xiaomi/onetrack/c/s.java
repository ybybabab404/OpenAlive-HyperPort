package com.xiaomi.onetrack.c;

/* JADX INFO: loaded from: classes2.dex */
class s implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;
    final /* synthetic */ r d;

    public s(r rVar, String str, String str2, String str3) {
        this.d = rVar;
        this.a = str;
        this.b = str2;
        this.c = str3;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.d.b(null, null, this.a, this.b, this.c, null);
    }
}
