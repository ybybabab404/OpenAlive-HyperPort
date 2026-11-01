package com.xiaomi.onetrack.api;

/* JADX INFO: loaded from: classes2.dex */
class ar implements Runnable {
    final /* synthetic */ Thread a;
    final /* synthetic */ Throwable b;
    final /* synthetic */ aq c;

    public ar(aq aqVar, Thread thread, Throwable th) {
        this.c = aqVar;
        this.a = thread;
        this.b = th;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        this.c.a(this.a, this.b);
    }
}
