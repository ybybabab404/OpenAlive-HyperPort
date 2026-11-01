package com.xiaomi.onetrack.api;

import com.xiaomi.taiyi.sdk.common.AIKeys;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
class m implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ Map b;
    final /* synthetic */ c c;

    public m(c cVar, boolean z, Map map) {
        this.c = cVar;
        this.a = z;
        this.b = map;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.c.i()) {
                return;
            }
            com.xiaomi.onetrack.h.d.b.a(this.c.e.getAppId(), "ot_logout", com.xiaomi.onetrack.h.d.a.e, "", 1);
            if (!this.a) {
                JSONObject jSONObjectA = com.xiaomi.onetrack.util.u.a((Map<String, Object>) this.b, false);
                Map map = this.b;
                if (map != null && jSONObjectA != null && map.size() != jSONObjectA.length()) {
                    com.xiaomi.onetrack.h.d.b.a(this.c.e.getAppId(), "ot_logout", com.xiaomi.onetrack.h.d.a.f, com.xiaomi.onetrack.h.d.a.n, 1);
                    ap apVarJ = this.c.j();
                    c cVar = this.c;
                    c.a(apVarJ, cVar.e, cVar.g, "ot_logout", com.xiaomi.onetrack.h.d.a.f, com.xiaomi.onetrack.h.d.a.n);
                }
                if (!com.xiaomi.onetrack.f.b.a(this.c.g.b())) {
                    String strP = com.xiaomi.onetrack.util.ad.p();
                    String strR = com.xiaomi.onetrack.util.ad.r();
                    jSONObjectA.put(AIKeys.KEY_UID, strP);
                    jSONObjectA.put("uid_type", strR);
                }
                JSONObject jSONObjectG = this.c.g("ot_logout");
                c cVar2 = this.c;
                this.c.j().a("ot_logout", an.d(jSONObjectA, cVar2.e, jSONObjectG, cVar2.g));
            }
            com.xiaomi.onetrack.util.ad.q();
            com.xiaomi.onetrack.util.ad.s();
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b("BaseOneTrackImp", "logout error:" + e.toString());
            if (e instanceof JSONException) {
                com.xiaomi.onetrack.h.d.b.a(this.c.e.getAppId(), "ot_logout", com.xiaomi.onetrack.h.d.a.g, "", 1);
                ap apVarJ2 = this.c.j();
                c cVar3 = this.c;
                c.a(apVarJ2, cVar3.e, cVar3.g, "ot_logout", com.xiaomi.onetrack.h.d.a.g, "");
            }
        }
    }
}
