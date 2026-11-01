package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.taiyi.sdk.common.AIKeys;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
class j implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ OneTrack.UserIdType b;
    final /* synthetic */ boolean c;
    final /* synthetic */ Map d;
    final /* synthetic */ c e;

    public j(c cVar, String str, OneTrack.UserIdType userIdType, boolean z, Map map) {
        this.e = cVar;
        this.a = str;
        this.b = userIdType;
        this.c = z;
        this.d = map;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.e.i()) {
                return;
            }
            com.xiaomi.onetrack.h.d.b.a(this.e.e.getAppId(), "ot_login", com.xiaomi.onetrack.h.d.a.e, "", 1);
            com.xiaomi.onetrack.util.ad.f(this.a);
            com.xiaomi.onetrack.util.ad.g(this.b.getUserIdType());
            if (this.c) {
                return;
            }
            JSONObject jSONObjectA = com.xiaomi.onetrack.util.u.a((Map<String, Object>) this.d, false);
            Map map = this.d;
            if (map != null && jSONObjectA != null && map.size() != jSONObjectA.length()) {
                com.xiaomi.onetrack.h.d.b.a(this.e.e.getAppId(), "ot_login", com.xiaomi.onetrack.h.d.a.f, com.xiaomi.onetrack.h.d.a.n, 1);
                ap apVarJ = this.e.j();
                c cVar = this.e;
                c.a(apVarJ, cVar.e, cVar.g, "ot_login", com.xiaomi.onetrack.h.d.a.f, com.xiaomi.onetrack.h.d.a.n);
            }
            if (!com.xiaomi.onetrack.f.b.a(this.e.g.b())) {
                jSONObjectA.put(AIKeys.KEY_UID, this.a);
                jSONObjectA.put("uid_type", this.b.getUserIdType());
            }
            JSONObject jSONObjectG = this.e.g("ot_login");
            c cVar2 = this.e;
            this.e.j().a("ot_login", an.c(jSONObjectA, cVar2.e, jSONObjectG, cVar2.g));
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b("BaseOneTrackImp", "login error:" + e.toString());
            if (e instanceof JSONException) {
                com.xiaomi.onetrack.h.d.b.a(this.e.e.getAppId(), "ot_login", com.xiaomi.onetrack.h.d.a.g, "", 1);
                ap apVarJ2 = this.e.j();
                c cVar3 = this.e;
                c.a(apVarJ2, cVar3.e, cVar3.g, "ot_login", com.xiaomi.onetrack.h.d.a.g, "");
            }
        }
    }
}
