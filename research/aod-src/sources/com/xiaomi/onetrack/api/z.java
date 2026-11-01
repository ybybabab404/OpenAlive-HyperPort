package com.xiaomi.onetrack.api;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes2.dex */
class z implements Runnable {
    final /* synthetic */ c a;

    public z(c cVar) {
        this.a = cVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            com.xiaomi.onetrack.util.s.a();
            com.xiaomi.onetrack.util.t.a();
            com.xiaomi.onetrack.b.a.a().a(this.a.e.getAppId());
            if (com.xiaomi.onetrack.util.ac.b(this.a.e.getAdEventAppId())) {
                ai.a().c();
                com.xiaomi.onetrack.b.a.a().a(this.a.e.getAdEventAppId());
            }
            if (com.xiaomi.onetrack.util.ad.w() == 0) {
                com.xiaomi.onetrack.util.ad.h(System.currentTimeMillis());
            }
            if (!TextUtils.isEmpty(this.a.e.getInstanceId())) {
                com.xiaomi.onetrack.util.r.a().a(this.a.e.getInstanceId());
            }
            this.a.h();
            com.xiaomi.onetrack.util.e.a();
            com.xiaomi.onetrack.c.j.c(false);
            com.xiaomi.onetrack.c.r.a(this.a.e.getAppId());
        } catch (Throwable th) {
            com.xiaomi.onetrack.util.s.b("BaseOneTrackImp", "init WorkerExecutor execute throwable:" + th.getMessage());
        }
    }
}
