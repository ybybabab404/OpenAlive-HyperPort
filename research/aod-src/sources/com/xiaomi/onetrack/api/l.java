package com.xiaomi.onetrack.api;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
class l implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Number b;
    final /* synthetic */ c c;

    public l(c cVar, String str, Number number) {
        this.c = cVar;
        this.a = str;
        this.b = number;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.c.i()) {
                return;
            }
            com.xiaomi.onetrack.h.d.b.a(this.c.e.getAppId(), am.d, com.xiaomi.onetrack.h.d.a.e, "", 1);
            JSONObject jSONObjectG = this.c.g(am.d);
            JSONObject jSONObjectPut = new JSONObject().put(this.a, this.b);
            c cVar = this.c;
            this.c.j().a(am.d, an.b(jSONObjectPut, cVar.e, jSONObjectG, cVar.g));
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b("BaseOneTrackImp", "userProfileIncrement single error:" + e.toString());
            if (e instanceof JSONException) {
                com.xiaomi.onetrack.h.d.b.a(this.c.e.getAppId(), am.d, com.xiaomi.onetrack.h.d.a.g, "", 1);
                ap apVarJ = this.c.j();
                c cVar2 = this.c;
                c.a(apVarJ, cVar2.e, cVar2.g, am.d, com.xiaomi.onetrack.h.d.a.g, "");
            }
        }
    }
}
