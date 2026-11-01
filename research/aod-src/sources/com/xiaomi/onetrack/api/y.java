package com.xiaomi.onetrack.api;

import org.json.JSONException;

/* JADX INFO: loaded from: classes2.dex */
class y implements Runnable {
    final /* synthetic */ c a;

    public y(c cVar) {
        this.a = cVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (this.a.i()) {
                return;
            }
            com.xiaomi.onetrack.h.d.b.a(this.a.e.getAppId(), am.l, com.xiaomi.onetrack.h.d.a.e, "", 1);
            c cVar = this.a;
            this.a.j().a(am.l, an.a(cVar.e, cVar.g));
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b("BaseOneTrackImp", "cta event error: " + e.toString());
            if (e instanceof JSONException) {
                com.xiaomi.onetrack.h.d.b.a(this.a.e.getAppId(), am.l, com.xiaomi.onetrack.h.d.a.g, "", 1);
                ap apVarJ = this.a.j();
                c cVar2 = this.a;
                c.a(apVarJ, cVar2.e, cVar2.g, am.l, com.xiaomi.onetrack.h.d.a.g, "");
            }
        }
    }
}
