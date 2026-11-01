package com.xiaomi.onetrack.api;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
class p implements Runnable {
    final /* synthetic */ c a;

    public p(c cVar) {
        this.a = cVar;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.a.i() || com.xiaomi.onetrack.util.af.b(com.xiaomi.onetrack.util.ad.o())) {
                return;
            }
            com.xiaomi.onetrack.h.d.b.a(this.a.e.getAppId(), "onetrack_dau", com.xiaomi.onetrack.h.d.a.e, "", 1);
            com.xiaomi.onetrack.util.ad.g(System.currentTimeMillis());
            JSONObject jSONObjectG = this.a.g("onetrack_dau");
            c cVar = this.a;
            this.a.j().a("onetrack_dau", an.a(cVar.e, jSONObjectG, cVar.g));
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b("BaseOneTrackImp", "trackDau error  e:" + e.toString());
            if (e instanceof JSONException) {
                com.xiaomi.onetrack.h.d.b.a(this.a.e.getAppId(), "onetrack_dau", com.xiaomi.onetrack.h.d.a.g, "", 1);
                ap apVarJ = this.a.j();
                c cVar2 = this.a;
                c.a(apVarJ, cVar2.e, cVar2.g, "onetrack_dau", com.xiaomi.onetrack.h.d.a.g, "");
            }
        }
    }
}
