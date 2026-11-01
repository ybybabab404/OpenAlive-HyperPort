package com.xiaomi.onetrack.c;

/* JADX INFO: loaded from: classes2.dex */
class t implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;
    final /* synthetic */ String d;
    final /* synthetic */ String e;
    final /* synthetic */ String f;
    final /* synthetic */ r g;

    public t(r rVar, String str, String str2, String str3, String str4, String str5, String str6) {
        this.g = rVar;
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = str5;
        this.f = str6;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.g.b(this.a, this.b, this.c, this.d, this.e, this.f);
    }
}
