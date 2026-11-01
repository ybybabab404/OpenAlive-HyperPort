package com.xiaomi.onetrack.api;

import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
class f implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ c b;

    public f(c cVar, String str) {
        this.b = cVar;
        this.a = str;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.b.i()) {
                return;
            }
            JSONArray jSONArray = new JSONArray(this.a);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i);
                if (jSONObjectOptJSONObject != null && jSONObjectOptJSONObject.has(an.b) && jSONObjectOptJSONObject.has(an.a)) {
                    ao aoVar = new ao(jSONObjectOptJSONObject.optJSONObject(an.b));
                    if (!this.b.f(aoVar.a())) {
                        JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject(an.a);
                        JSONObject jSONObjectG = this.b.g(aoVar.a());
                        c cVar = this.b;
                        this.b.j().a(aoVar.a(), an.a(aoVar, jSONObjectOptJSONObject2, cVar.e, jSONObjectG, cVar.g));
                    }
                } else {
                    com.xiaomi.onetrack.util.s.a("BaseOneTrackImp", "h5 json is empty or has no \"H\" or \"B\" ");
                }
            }
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b("BaseOneTrackImp", "trackEventFromH5 error: " + e.toString());
        }
    }
}
