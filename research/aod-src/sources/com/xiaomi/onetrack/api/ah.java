package com.xiaomi.onetrack.api;

import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
class ah implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Map b;
    final /* synthetic */ c c;

    public ah(c cVar, String str, Map map) {
        this.c = cVar;
        this.a = str;
        this.b = map;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.c.i()) {
                return;
            }
            com.xiaomi.onetrack.h.d.b.a(this.c.e.getAppId(), this.a, com.xiaomi.onetrack.h.d.a.e, "", 1);
            if (this.c.e(this.a)) {
                com.xiaomi.onetrack.h.d.b.a(this.c.e.getAppId(), this.a, com.xiaomi.onetrack.h.d.a.f, com.xiaomi.onetrack.h.d.a.m, 1);
                ap apVarJ = this.c.j();
                c cVar = this.c;
                c.a(apVarJ, cVar.e, cVar.g, this.a, com.xiaomi.onetrack.h.d.a.f, com.xiaomi.onetrack.h.d.a.m);
                return;
            }
            JSONObject jSONObjectA = com.xiaomi.onetrack.util.u.a((Map<String, Object>) this.b, true);
            Map map = this.b;
            if (map != null && jSONObjectA != null && map.size() != jSONObjectA.length()) {
                com.xiaomi.onetrack.h.d.b.a(this.c.e.getAppId(), this.a, com.xiaomi.onetrack.h.d.a.f, com.xiaomi.onetrack.h.d.a.n, 1);
                ap apVarJ2 = this.c.j();
                c cVar2 = this.c;
                c.a(apVarJ2, cVar2.e, cVar2.g, this.a, com.xiaomi.onetrack.h.d.a.f, com.xiaomi.onetrack.h.d.a.n);
            }
            JSONObject jSONObjectG = this.c.g(this.a);
            String str = this.a;
            c cVar3 = this.c;
            this.c.j().a(this.a, an.a(str, jSONObjectA, cVar3.e, jSONObjectG, cVar3.g));
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b("BaseOneTrackImp", "track map error: " + e.toString());
            if (e instanceof JSONException) {
                com.xiaomi.onetrack.h.d.b.a(this.c.e.getAppId(), this.a, com.xiaomi.onetrack.h.d.a.g, "", 1);
                ap apVarJ3 = this.c.j();
                c cVar4 = this.c;
                c.a(apVarJ3, cVar4.e, cVar4.g, this.a, com.xiaomi.onetrack.h.d.a.g, "");
            }
        }
    }
}
