package com.xiaomi.onetrack.api;

import android.text.TextUtils;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
class s implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ c b;

    public s(c cVar, boolean z) {
        this.b = cVar;
        this.a = z;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.b.i()) {
                return;
            }
            if (!this.b.e.isAutoTrackActivityAction()) {
                com.xiaomi.onetrack.util.s.a("BaseOneTrackImp", "config.autoTrackActivityAction is false, ignore onetrack_pa pause event");
                return;
            }
            String strA = com.xiaomi.onetrack.util.n.a("onetrack_pa");
            if (TextUtils.isEmpty(strA)) {
                return;
            }
            JSONObject jSONObject = new JSONObject(strA);
            this.b.j().a("onetrack_pa", jSONObject.put(an.a, jSONObject.optJSONObject(an.a).put(am.y, this.a)).toString());
            if (com.xiaomi.onetrack.util.s.a) {
                com.xiaomi.onetrack.util.s.a("BaseOneTrackImp", "trackPageEndAuto");
            }
            com.xiaomi.onetrack.util.n.a("onetrack_pa", "");
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b("BaseOneTrackImp", "trackPageEndAuto error:" + e.toString());
        }
    }
}
