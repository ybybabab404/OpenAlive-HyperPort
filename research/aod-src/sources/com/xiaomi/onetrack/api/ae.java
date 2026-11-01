package com.xiaomi.onetrack.api;

/* JADX INFO: loaded from: classes2.dex */
class ae implements Runnable {
    final /* synthetic */ c a;

    public ae(c cVar) {
        this.a = cVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.xiaomi.onetrack.h.a.a aVarA = com.xiaomi.onetrack.h.a.a.a();
        String appId = this.a.e.getAppId();
        c cVar = this.a;
        aVarA.b(appId, cVar.g, cVar.a());
        this.a.r();
    }
}
