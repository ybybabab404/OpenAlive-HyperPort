package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.ServiceQualityEvent;
import org.json.JSONException;

/* JADX INFO: loaded from: classes2.dex */
class x implements Runnable {
    final /* synthetic */ ServiceQualityEvent a;
    final /* synthetic */ c b;

    public x(c cVar, ServiceQualityEvent serviceQualityEvent) {
        this.b = cVar;
        this.a = serviceQualityEvent;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (this.b.i()) {
                return;
            }
            com.xiaomi.onetrack.h.d.b.a(this.b.e.getAppId(), am.e, com.xiaomi.onetrack.h.d.a.e, "", 1);
            ServiceQualityEvent serviceQualityEvent = this.a;
            c cVar = this.b;
            this.b.j().a(am.e, an.a(serviceQualityEvent, cVar.e, cVar.g));
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b("BaseOneTrackImp", "trackNetAvailableEvent error: " + e.toString());
            if (e instanceof JSONException) {
                com.xiaomi.onetrack.h.d.b.a(this.b.e.getAppId(), am.e, com.xiaomi.onetrack.h.d.a.g, "", 1);
                ap apVarJ = this.b.j();
                c cVar2 = this.b;
                c.a(apVarJ, cVar2.e, cVar2.g, am.e, com.xiaomi.onetrack.h.d.a.g, "");
            }
        }
    }
}
