package com.xiaomi.onetrack.api;

import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
class h implements Runnable {
    final /* synthetic */ Map a;
    final /* synthetic */ c b;

    public h(c cVar, Map map) {
        this.b = cVar;
        this.a = map;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.b.i()) {
                return;
            }
            com.xiaomi.onetrack.h.d.b.a(this.b.e.getAppId(), am.c, com.xiaomi.onetrack.h.d.a.e, "", 1);
            JSONObject jSONObjectG = this.b.g(am.c);
            JSONObject jSONObjectA = com.xiaomi.onetrack.util.u.a((Map<String, Object>) this.a, false);
            c cVar = this.b;
            this.b.j().a(am.c, an.a(jSONObjectA, cVar.e, jSONObjectG, cVar.g));
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b("BaseOneTrackImp", "setUserProfile map error:" + e.toString());
            if (e instanceof JSONException) {
                com.xiaomi.onetrack.h.d.b.a(this.b.e.getAppId(), am.c, com.xiaomi.onetrack.h.d.a.g, "", 1);
                ap apVarJ = this.b.j();
                c cVar2 = this.b;
                c.a(apVarJ, cVar2.e, cVar2.g, am.c, com.xiaomi.onetrack.h.d.a.g, "");
            }
        }
    }
}
