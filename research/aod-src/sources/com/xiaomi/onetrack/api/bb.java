package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.Configuration;

/* JADX INFO: loaded from: classes2.dex */
class bb implements Runnable {
    final /* synthetic */ Configuration a;
    final /* synthetic */ ba b;

    public bb(ba baVar, Configuration configuration) {
        this.b = baVar;
        this.a = configuration;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.xiaomi.onetrack.c.b.a();
        ai.a().d();
        ai.a().f();
        com.xiaomi.onetrack.b.q.a().a(this.a.getProjectId(), com.xiaomi.onetrack.util.a.a(this.a.getAppId()), this.a.getPrivateKeyId(), this.a.getAppId());
        com.xiaomi.onetrack.util.r.a().a(Boolean.FALSE);
        com.xiaomi.onetrack.c.n.b();
    }
}
