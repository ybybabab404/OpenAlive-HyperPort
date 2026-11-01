package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;

/* JADX INFO: loaded from: classes2.dex */
class az implements Runnable {
    final /* synthetic */ Configuration a;
    final /* synthetic */ ay b;

    public az(ay ayVar, Configuration configuration) {
        this.b = ayVar;
        this.a = configuration;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.xiaomi.onetrack.b.q.a().a(this.a.getProjectId(), com.xiaomi.onetrack.util.a.a(this.a.getAppId()), this.a.getPrivateKeyId(), this.a.getAppId());
        com.xiaomi.onetrack.util.r.a().a(Boolean.TRUE);
        if (OneTrack.isRestrictGetNetworkInfo()) {
            ai.a().f();
        }
    }
}
