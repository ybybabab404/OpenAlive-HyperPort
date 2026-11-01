package com.xiaomi.onetrack.f;

/* JADX INFO: loaded from: classes2.dex */
class f implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;
    final /* synthetic */ String d;

    public f(String str, String str2, String str3, String str4) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.xiaomi.onetrack.c.e.a().a(com.xiaomi.onetrack.e.c.b(this.a, this.b, this.c, this.d), true);
    }
}
