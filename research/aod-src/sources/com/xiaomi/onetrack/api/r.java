package com.xiaomi.onetrack.api;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
class r implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ long b;
    final /* synthetic */ c c;

    public r(c cVar, String str, long j) {
        this.c = cVar;
        this.a = str;
        this.b = j;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.c.i()) {
                return;
            }
            if (!this.c.e.isAutoTrackActivityAction()) {
                com.xiaomi.onetrack.util.s.a("BaseOneTrackImp", "config.autoTrackActivityAction is false, ignore onetrack_pa pause event");
                return;
            }
            com.xiaomi.onetrack.h.d.b.a(this.c.e.getAppId(), "onetrack_pa", com.xiaomi.onetrack.h.d.a.e, "", 1);
            JSONObject jSONObjectG = this.c.g("onetrack_pa");
            String str = this.a;
            long j = this.b;
            c cVar = this.c;
            com.xiaomi.onetrack.util.n.a("onetrack_pa", an.a(str, "onetrack_pa", j, cVar.e, jSONObjectG, cVar.g));
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b("BaseOneTrackImp", "savePageEndData error:" + e.toString());
            if (e instanceof JSONException) {
                com.xiaomi.onetrack.h.d.b.a(this.c.e.getAppId(), "onetrack_pa", com.xiaomi.onetrack.h.d.a.g, "", 1);
                ap apVarJ = this.c.j();
                c cVar2 = this.c;
                c.a(apVarJ, cVar2.e, cVar2.g, "onetrack_pa", com.xiaomi.onetrack.h.d.a.g, "");
            }
        }
    }
}
