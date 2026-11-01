package com.xiaomi.onetrack.api;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
class i implements Runnable {
    final /* synthetic */ Object a;
    final /* synthetic */ String b;
    final /* synthetic */ c c;

    public i(c cVar, Object obj, String str) {
        this.c = cVar;
        this.a = obj;
        this.b = str;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.c.i()) {
                return;
            }
            com.xiaomi.onetrack.h.d.b.a(this.c.e.getAppId(), am.c, com.xiaomi.onetrack.h.d.a.e, "", 1);
            if (com.xiaomi.onetrack.util.u.b(this.a)) {
                JSONObject jSONObjectG = this.c.g(am.c);
                JSONObject jSONObjectPut = new JSONObject().put(this.b, this.a);
                c cVar = this.c;
                this.c.j().a(am.c, an.a(jSONObjectPut, cVar.e, jSONObjectG, cVar.g));
                return;
            }
            com.xiaomi.onetrack.h.d.b.a(this.c.e.getAppId(), am.c, com.xiaomi.onetrack.h.d.a.f, com.xiaomi.onetrack.h.d.a.n, 1);
            ap apVarJ = this.c.j();
            c cVar2 = this.c;
            c.a(apVarJ, cVar2.e, cVar2.g, am.c, com.xiaomi.onetrack.h.d.a.f, com.xiaomi.onetrack.h.d.a.n);
            com.xiaomi.onetrack.util.u.a("BaseOneTrackImp", this.b);
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b("BaseOneTrackImp", "setUserProfile single error:" + e.toString());
            if (e instanceof JSONException) {
                com.xiaomi.onetrack.h.d.b.a(this.c.e.getAppId(), am.c, com.xiaomi.onetrack.h.d.a.g, "", 1);
                ap apVarJ2 = this.c.j();
                c cVar3 = this.c;
                c.a(apVarJ2, cVar3.e, cVar3.g, am.c, com.xiaomi.onetrack.h.d.a.g, "");
            }
        }
    }
}
