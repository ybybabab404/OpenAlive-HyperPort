package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.CrashAnalysis;
import java.util.Arrays;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
class g implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ String b;
    final /* synthetic */ Map c;
    final /* synthetic */ String d;
    final /* synthetic */ String e;
    final /* synthetic */ String f;
    final /* synthetic */ String g;
    final /* synthetic */ long h;
    final /* synthetic */ c i;

    public g(c cVar, boolean z, String str, Map map, String str2, String str3, String str4, String str5, long j) {
        this.i = cVar;
        this.a = z;
        this.b = str;
        this.c = map;
        this.d = str2;
        this.e = str3;
        this.f = str4;
        this.g = str5;
        this.h = j;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.i.i()) {
                return;
            }
            com.xiaomi.onetrack.h.d.b.a(this.i.e.getAppId(), "onetrack_bug_report", com.xiaomi.onetrack.h.d.a.e, "", 1);
            if (this.a && !Arrays.asList("java", CrashAnalysis.ANR_CRASH, CrashAnalysis.NATIVE_CRASH).contains(this.b)) {
                com.xiaomi.onetrack.util.s.c("BaseOneTrackImp", "track custom exception failed, crashType invalid!!!");
                com.xiaomi.onetrack.h.d.b.a(this.i.e.getAppId(), "onetrack_bug_report", com.xiaomi.onetrack.h.d.a.f, com.xiaomi.onetrack.h.d.a.n, 1);
                return;
            }
            JSONObject jSONObjectD = this.i.d((Map<String, Object>) this.c);
            ap apVarJ = this.i.j();
            String str = this.d;
            String str2 = this.e;
            String str3 = this.b;
            String str4 = this.f;
            String str5 = this.g;
            long j = this.h;
            c cVar = this.i;
            apVarJ.a("onetrack_bug_report", an.a(str, str2, str3, str4, str5, j, cVar.e, jSONObjectD, cVar.g, this.a));
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b("BaseOneTrackImp", "trackException error: " + e.toString());
            if (e instanceof JSONException) {
                com.xiaomi.onetrack.h.d.b.a(this.i.e.getAppId(), "onetrack_bug_report", com.xiaomi.onetrack.h.d.a.g, "", 1);
                ap apVarJ2 = this.i.j();
                c cVar2 = this.i;
                c.a(apVarJ2, cVar2.e, cVar2.g, "onetrack_bug_report", com.xiaomi.onetrack.h.d.a.g, "");
            }
        }
    }
}
