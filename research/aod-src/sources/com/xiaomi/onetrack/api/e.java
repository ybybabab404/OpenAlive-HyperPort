package com.xiaomi.onetrack.api;

import android.text.TextUtils;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
class e implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Map b;
    final /* synthetic */ List c;
    final /* synthetic */ c d;

    public e(c cVar, String str, Map map, List list) {
        this.d = cVar;
        this.a = str;
        this.b = map;
        this.c = list;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.d.i()) {
                return;
            }
            com.xiaomi.onetrack.h.d.b.a(this.d.e.getAppId(), this.a, com.xiaomi.onetrack.h.d.a.e, "", 1);
            if (TextUtils.isEmpty(this.d.e.getAdEventAppId())) {
                com.xiaomi.onetrack.util.s.a("BaseOneTrackImp", "adEventAppId is null,Please configure,event name:" + this.a);
                com.xiaomi.onetrack.h.d.b.a(this.d.e.getAppId(), this.a, com.xiaomi.onetrack.h.d.a.f, com.xiaomi.onetrack.h.d.a.o, 1);
                ap apVarJ = this.d.j();
                c cVar = this.d;
                c.a(apVarJ, cVar.e, cVar.g, this.a, com.xiaomi.onetrack.h.d.a.f, com.xiaomi.onetrack.h.d.a.o);
                return;
            }
            if (this.d.e(this.a)) {
                com.xiaomi.onetrack.h.d.b.a(this.d.e.getAppId(), this.a, com.xiaomi.onetrack.h.d.a.f, com.xiaomi.onetrack.h.d.a.m, 1);
                ap apVarJ2 = this.d.j();
                c cVar2 = this.d;
                c.a(apVarJ2, cVar2.e, cVar2.g, this.a, com.xiaomi.onetrack.h.d.a.f, com.xiaomi.onetrack.h.d.a.m);
                return;
            }
            JSONObject jSONObjectA = com.xiaomi.onetrack.util.u.a((Map<String, Object>) this.b, true);
            Map map = this.b;
            if (map != null && jSONObjectA != null && map.size() != jSONObjectA.length()) {
                com.xiaomi.onetrack.h.d.b.a(this.d.e.getAppId(), this.a, com.xiaomi.onetrack.h.d.a.f, com.xiaomi.onetrack.h.d.a.n, 1);
                ap apVarJ3 = this.d.j();
                c cVar3 = this.d;
                c.a(apVarJ3, cVar3.e, cVar3.g, this.a, com.xiaomi.onetrack.h.d.a.f, com.xiaomi.onetrack.h.d.a.n);
            }
            JSONArray jSONArrayA = com.xiaomi.onetrack.util.u.a(this.c);
            JSONObject jSONObjectG = this.d.g(this.a);
            String str = this.a;
            c cVar4 = this.d;
            this.d.j().a(this.a, an.a(str, jSONObjectA, cVar4.e, jSONObjectG, cVar4.g, jSONArrayA));
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b("BaseOneTrackImp", "track map error: " + e.toString());
            if (e instanceof JSONException) {
                com.xiaomi.onetrack.h.d.b.a(this.d.e.getAppId(), this.a, com.xiaomi.onetrack.h.d.a.g, "", 1);
                ap apVarJ4 = this.d.j();
                c cVar5 = this.d;
                c.a(apVarJ4, cVar5.e, cVar5.g, this.a, com.xiaomi.onetrack.h.d.a.g, "");
            }
        }
    }
}
