package com.xiaomi.onetrack.api;

import android.os.Process;
import android.text.TextUtils;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class ay implements ap, be.a {
    private static final String a = "PubSubSystemImp";
    private static final int b = 102400;
    private static final int c = 512000;
    private static final int d = 2;
    private Configuration e;
    private be f;
    private com.xiaomi.onetrack.util.y g;

    public ay(Configuration configuration, com.xiaomi.onetrack.util.y yVar) {
        this.e = configuration;
        this.g = yVar;
        be beVarA = be.a();
        this.f = beVarA;
        beVarA.a(this);
        com.xiaomi.onetrack.util.j.a(new az(this, configuration));
    }

    private boolean a(String str) {
        try {
            return new JSONObject(str).optJSONObject(an.b).optBoolean(com.xiaomi.onetrack.f.b.C0047b.D, false);
        } catch (Throwable unused) {
            com.xiaomi.onetrack.util.s.a(a, "");
            return false;
        }
    }

    private boolean a(String str, String str2, boolean z) {
        if (OneTrack.isDisable()) {
            com.xiaomi.onetrack.util.s.a(a, "isDisable");
            return false;
        }
        Configuration configuration = this.e;
        if (configuration == null || TextUtils.isEmpty(configuration.getProjectId()) || TextUtils.isEmpty(this.e.getPrivateKeyId())) {
            com.xiaomi.onetrack.util.s.a(a, "projectId or pKeyId is empty, so return");
            return false;
        }
        if (str != null && str.equals("onetrack_bug_report")) {
            return true;
        }
        if (z) {
            if (str2 != null && str2.length() > c) {
                com.xiaomi.onetrack.util.s.a(a, "ad Event size exceed limitation!");
                return false;
            }
        } else if (str2 != null && str2.length() * 2 > b) {
            com.xiaomi.onetrack.util.s.a(a, "Event size exceed limitation!");
            return false;
        }
        return true;
    }

    private void b() {
        try {
            com.xiaomi.onetrack.c.r.a().b(this.e.getProjectId(), this.e.getPrivateKeyId(), this.e.getAppId());
            if (TextUtils.isEmpty(this.e.getAdEventAppId())) {
                return;
            }
            com.xiaomi.onetrack.c.r.a().b(this.e.getProjectId(), this.e.getPrivateKeyId(), this.e.getAdEventAppId());
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.a(a, "trackCachedEvents: " + e.toString());
        }
    }

    @Override // com.xiaomi.onetrack.api.be.a
    public void a() {
        b();
    }

    @Override // com.xiaomi.onetrack.api.ap
    public void a(int i) {
        this.f.a(i);
    }

    @Override // com.xiaomi.onetrack.api.ap
    public void a(String str, String str2) {
        boolean zA = a(str2);
        com.xiaomi.onetrack.util.y yVar = this.g;
        if (yVar != null && !yVar.a(str) && !zA) {
            com.xiaomi.onetrack.util.s.a(a, "The privacy policy is not permitted, and the event is not basic or recommend event or custom dau event, skip it.");
            return;
        }
        if (!a(str, str2, zA)) {
            com.xiaomi.onetrack.h.d.b.a(this.e.getAppId(), str, com.xiaomi.onetrack.h.d.a.h, com.xiaomi.onetrack.h.d.a.m, 1);
            c.a(this, this.e, this.g, str, com.xiaomi.onetrack.h.d.a.h, com.xiaomi.onetrack.h.d.a.m);
            return;
        }
        if (com.xiaomi.onetrack.c.j.b()) {
            com.xiaomi.onetrack.c.j.a(this);
        } else if (!am.l.equalsIgnoreCase(str)) {
            com.xiaomi.onetrack.c.j.a(str, str2);
            return;
        }
        if (com.xiaomi.onetrack.util.s.a) {
            com.xiaomi.onetrack.util.s.a(a, "track name:" + str + " data :" + str2 + " tid" + Process.myTid());
        }
        String appId = this.e.getAppId();
        if (zA) {
            appId = this.e.getAdEventAppId();
        }
        String str3 = appId;
        String strA = com.xiaomi.onetrack.util.a.a(str3);
        String strA2 = com.xiaomi.onetrack.util.a.a();
        if (this.f.a(this.e.getProjectId(), strA, this.e.getPrivateKeyId(), str3, str, str2, strA2)) {
            return;
        }
        if (com.xiaomi.onetrack.util.s.a) {
            com.xiaomi.onetrack.util.s.a(a, "track mIOneTrackService is null! SystemImpCacheManager cache data:" + str2);
        }
        if (!am.g.equals(str)) {
            com.xiaomi.onetrack.c.r.a().a(this.e.getProjectId(), strA, str3, str, str2, strA2);
        }
        com.xiaomi.onetrack.h.d.b.a(this.e.getAppId(), str, com.xiaomi.onetrack.h.d.a.h, com.xiaomi.onetrack.h.d.a.n, 1);
    }

    @Override // com.xiaomi.onetrack.api.ap
    public void a(boolean z) {
        if (as.i) {
            return;
        }
        com.xiaomi.onetrack.c.j.a(this);
    }
}
