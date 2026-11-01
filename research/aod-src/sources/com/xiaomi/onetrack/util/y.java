package com.xiaomi.onetrack.util;

import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;

/* JADX INFO: loaded from: classes2.dex */
public class y {
    private static final String a = "custom_open";
    private static final String b = "custom_close";
    private static final String c = "exprience_open";
    private static final String d = "exprience_close";
    private static final String e = "PrivacyManager";
    private static final long l = 900000;
    private OneTrack.IEventHook f;
    private Configuration g;
    private Boolean h;
    private boolean i;
    private boolean j = false;
    private long k = 0;

    public y(Configuration configuration) {
        this.g = configuration;
    }

    private boolean c() {
        if (this.h == null) {
            this.h = Boolean.valueOf(ad.j(u.a(this.g)));
        }
        return this.h.booleanValue();
    }

    private boolean d() {
        if (Math.abs(System.currentTimeMillis() - this.k) > l) {
            this.k = System.currentTimeMillis();
            this.i = t.a(com.xiaomi.onetrack.f.a.b());
        }
        return this.i;
    }

    private boolean d(String str) {
        return "onetrack_dau".equals(str) || "onetrack_pa".equals(str);
    }

    private boolean e(String str) {
        OneTrack.IEventHook iEventHook = this.f;
        return iEventHook != null && iEventHook.isCustomDauEvent(str);
    }

    public String a() {
        if (this.g.isUseCustomPrivacyPolicy()) {
            return c() ? a : b;
        }
        return d() ? c : d;
    }

    public void a(OneTrack.IEventHook iEventHook) {
        this.f = iEventHook;
    }

    public void a(boolean z) {
        this.h = Boolean.valueOf(z);
    }

    public boolean a(String str) {
        boolean zD;
        if (this.g.isUseCustomPrivacyPolicy()) {
            StringBuilder sb = new StringBuilder();
            sb.append("use custom privacy policy, the policy is ");
            sb.append(c() ? "open" : "close");
            s.a(e, sb.toString());
            zD = c();
        } else {
            zD = d();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("use system experience plan, the policy is ");
            sb2.append(zD ? "open" : "close");
            s.a(e, sb2.toString());
        }
        if (zD) {
            return zD;
        }
        boolean zD2 = d(str);
        boolean zB = b(str);
        boolean zE = e(str);
        StringBuilder sb3 = new StringBuilder();
        sb3.append("This event ");
        sb3.append(str);
        sb3.append(zD2 ? " is " : " is not ");
        sb3.append("basic event and ");
        sb3.append(zB ? "is" : "is not");
        sb3.append(" recommend event and ");
        sb3.append(zE ? "is" : "is not");
        sb3.append(" custom dau event");
        s.a(e, sb3.toString());
        return zD2 || zB || zE;
    }

    public void b(boolean z) {
        this.j = z;
    }

    public boolean b() {
        return this.j;
    }

    public boolean b(String str) {
        OneTrack.IEventHook iEventHook = this.f;
        return iEventHook != null && iEventHook.isRecommendEvent(str);
    }

    public boolean c(String str) {
        OneTrack.IEventHook iEventHook = this.f;
        return iEventHook != null && iEventHook.isTrackIPRegionEvent(str);
    }
}
