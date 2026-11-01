package com.xiaomi.onetrack.api;

import android.content.Context;
import com.xiaomi.onetrack.Configuration;

/* JADX INFO: loaded from: classes2.dex */
public class as extends c {
    public static volatile boolean i = false;
    private static final String j = "OneTrackImp";
    private ap k;

    public as(Context context, Configuration configuration) {
        super(context, configuration);
    }

    public static void a(String str, String str2) {
        boolean zB = com.xiaomi.onetrack.util.t.b(str2);
        if (i != zB) {
            i = zB;
            StringBuilder sb = new StringBuilder();
            sb.append("channel changed to ");
            sb.append(i ? "OneTrack" : "PubSub");
            com.xiaomi.onetrack.util.s.a(j, sb.toString());
        }
        if (com.xiaomi.onetrack.util.t.m()) {
            c.h = true;
            com.xiaomi.onetrack.util.s.a(j, "isOverrideMiuiRegionSetting：true ,oldRegion:" + str + ", newRegion:" + str2);
        }
        com.xiaomi.onetrack.c.y.a().a(str, zB);
    }

    private boolean m() {
        if (com.xiaomi.onetrack.util.s.a) {
            com.xiaomi.onetrack.util.s.a(j, "enable:" + l() + " isSupportPBSystem: " + n());
        }
        return l() && n();
    }

    private static boolean n() {
        try {
            int i2 = com.xiaomi.onetrack.f.a.b().getPackageManager().getPackageInfo(be.a, 0).versionCode;
            if (i2 >= 2024040800) {
                return true;
            }
            com.xiaomi.onetrack.util.s.a(j, "system analytics version: " + i2);
        } catch (Throwable th) {
            com.xiaomi.onetrack.util.s.b(j, "isSupportEmptyEvent error:" + th.getMessage());
        }
        return false;
    }

    @Override // com.xiaomi.onetrack.api.c
    public void a(Context context) {
        if (com.xiaomi.onetrack.util.t.b(null)) {
            i = true;
            if (com.xiaomi.onetrack.util.t.b() && k() && b()) {
                this.a = new aw(this.e, this.g);
            } else {
                this.a = new at(context, this.e, this.g);
            }
            ai.a().a(this.a);
            return;
        }
        i = false;
        if (com.xiaomi.onetrack.util.t.b() && m() && b()) {
            this.k = new ay(this.e, this.g);
        } else {
            this.k = new ba(this.e, this.g);
        }
        ai.a().a(this.k);
    }

    @Override // com.xiaomi.onetrack.api.c
    public boolean a() {
        if (i) {
            return com.xiaomi.onetrack.util.t.b() && k() && b();
        }
        return com.xiaomi.onetrack.util.t.b() && m() && b();
    }

    @Override // com.xiaomi.onetrack.api.c
    public void c(String str) {
        super.c(str);
    }

    @Override // com.xiaomi.onetrack.api.c
    public ap j() {
        if (i) {
            if (this.a == null) {
                a(this.c);
            } else if (c.h && (this.a instanceof aw)) {
                a(this.c);
            }
            com.xiaomi.onetrack.util.s.a(j, "getTrackImp mIOneTrack" + this.a);
            return this.a;
        }
        if (this.k == null) {
            a(this.c);
        } else if (c.h && (this.k instanceof ay)) {
            a(this.c);
        }
        com.xiaomi.onetrack.util.s.a(j, "getTrackImp mIPubSubTrack" + this.k);
        return this.k;
    }
}
