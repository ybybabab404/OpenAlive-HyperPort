package com.xiaomi.onetrack.api;

import android.os.Process;
import android.text.TextUtils;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class aw implements ap, be.a {
    private static final String a = "OneTrackSystemImp";
    private static final int b = 102400;
    private static final int c = 512000;
    private static final int d = 2;
    private final ConcurrentHashMap<String, String> e = new ConcurrentHashMap<>();
    private Configuration f;
    private be g;
    private com.xiaomi.onetrack.util.y h;

    public aw(Configuration configuration, com.xiaomi.onetrack.util.y yVar) {
        this.f = configuration;
        this.h = yVar;
        be beVarA = be.a();
        this.g = beVarA;
        beVarA.a(this);
        com.xiaomi.onetrack.util.j.a(new ax(this));
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
            return false;
        }
        boolean z2 = str != null && str.equals("onetrack_bug_report");
        if (z || z2) {
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
            com.xiaomi.onetrack.c.r.a().b(this.f.getAppId());
            if (TextUtils.isEmpty(this.f.getAdEventAppId())) {
                return;
            }
            com.xiaomi.onetrack.c.r.a().b(this.f.getAdEventAppId());
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
        this.g.a(i);
    }

    @Override // com.xiaomi.onetrack.api.ap
    public void a(String str, String str2) {
        boolean zA = a(str2);
        com.xiaomi.onetrack.util.y yVar = this.h;
        if (yVar != null && !yVar.a(str) && !zA) {
            com.xiaomi.onetrack.util.s.a(a, "The privacy policy is not permitted, and the event is not basic or recommend event or custom dau event, skip it.");
            return;
        }
        if (!a(str, str2, zA)) {
            com.xiaomi.onetrack.h.d.b.a(this.f.getAppId(), str, com.xiaomi.onetrack.h.d.a.h, com.xiaomi.onetrack.h.d.a.m, 1);
            c.a(this, this.f, this.h, str, com.xiaomi.onetrack.h.d.a.h, com.xiaomi.onetrack.h.d.a.m);
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
        String appId = this.f.getAppId();
        if (zA) {
            appId = this.f.getAdEventAppId();
        }
        if (this.g.a(str, str2, appId)) {
            return;
        }
        if (com.xiaomi.onetrack.util.s.a) {
            com.xiaomi.onetrack.util.s.a(a, "track mIOneTrackService is null! SystemImpCacheManager cache data:" + str2);
        }
        if (!am.g.equals(str)) {
            com.xiaomi.onetrack.c.r.a().a(appId, str, str2);
        }
        com.xiaomi.onetrack.h.d.b.a(this.f.getAppId(), str, com.xiaomi.onetrack.h.d.a.h, com.xiaomi.onetrack.h.d.a.n, 1);
    }

    @Override // com.xiaomi.onetrack.api.ap
    public void a(boolean z) {
        if (as.i) {
            com.xiaomi.onetrack.c.j.a(this);
        }
    }
}
