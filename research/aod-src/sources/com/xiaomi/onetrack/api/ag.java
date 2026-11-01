package com.xiaomi.onetrack.api;

import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
class ag implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Map b;
    final /* synthetic */ String c;
    final /* synthetic */ c d;

    public ag(c cVar, String str, Map map, String str2) {
        this.d = cVar;
        this.a = str;
        this.b = map;
        this.c = str2;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.d.i()) {
                return;
            }
            com.xiaomi.onetrack.h.d.b.a(this.d.e.getAppId(), this.a, com.xiaomi.onetrack.h.d.a.e, "", 1);
            if (this.d.e(this.a)) {
                com.xiaomi.onetrack.h.d.b.a(this.d.e.getAppId(), this.a, com.xiaomi.onetrack.h.d.a.f, com.xiaomi.onetrack.h.d.a.m, 1);
                ap apVarJ = this.d.j();
                c cVar = this.d;
                c.a(apVarJ, cVar.e, cVar.g, this.a, com.xiaomi.onetrack.h.d.a.f, com.xiaomi.onetrack.h.d.a.m);
                return;
            }
            JSONObject jSONObjectA = com.xiaomi.onetrack.util.u.a((Map<String, Object>) this.b, true);
            Map map = this.b;
            if (map != null && jSONObjectA != null && map.size() != jSONObjectA.length()) {
                com.xiaomi.onetrack.h.d.b.a(this.d.e.getAppId(), this.a, com.xiaomi.onetrack.h.d.a.f, com.xiaomi.onetrack.h.d.a.n, 1);
                ap apVarJ2 = this.d.j();
                c cVar2 = this.d;
                c.a(apVarJ2, cVar2.e, cVar2.g, this.a, com.xiaomi.onetrack.h.d.a.f, com.xiaomi.onetrack.h.d.a.n);
            }
            JSONObject jSONObjectG = this.d.g(this.a);
            String str = this.c;
            String str2 = this.a;
            c cVar3 = this.d;
            this.d.j().a(this.a, an.a(str, str2, jSONObjectA, cVar3.e, jSONObjectG, cVar3.g));
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b("BaseOneTrackImp", "track json error:" + e.toString());
            if (e instanceof JSONException) {
                com.xiaomi.onetrack.h.d.b.a(this.d.e.getAppId(), this.a, com.xiaomi.onetrack.h.d.a.g, "", 1);
                ap apVarJ3 = this.d.j();
                c cVar4 = this.d;
                c.a(apVarJ3, cVar4.e, cVar4.g, this.a, com.xiaomi.onetrack.h.d.a.g, "");
            }
        }
    }
}
