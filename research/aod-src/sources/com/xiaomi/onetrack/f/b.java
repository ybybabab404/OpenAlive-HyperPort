package com.xiaomi.onetrack.f;

import android.content.Context;
import android.text.TextUtils;
import com.miui.aod.common.StyleInfo;
import com.xiaomi.onetrack.BuildConfig;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.an;
import com.xiaomi.onetrack.api.ao;
import com.xiaomi.onetrack.util.DeviceUtil;
import com.xiaomi.onetrack.util.ac;
import com.xiaomi.onetrack.util.ad;
import com.xiaomi.onetrack.util.af;
import com.xiaomi.onetrack.util.r;
import com.xiaomi.onetrack.util.s;
import com.xiaomi.onetrack.util.t;
import com.xiaomi.onetrack.util.y;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class b {
    private static final String a = "Event";
    public static final int l = 0;
    public static final int m = 1;
    public static final int n = 2;
    public static final int o = 3;
    private long b;
    private String c;
    private String d;
    private String e;
    private int f;
    private JSONObject g;
    private long h;

    public static class a {
        private long a;
        private String b;
        private String c;
        private String d;
        private int e;
        private JSONObject f;
        private long g;

        public a a(int i) {
            this.e = i;
            return this;
        }

        public a a(long j) {
            this.a = this.a;
            return this;
        }

        public a a(String str) {
            this.b = str;
            return this;
        }

        public a a(JSONObject jSONObject) {
            this.f = jSONObject;
            return this;
        }

        public b a() {
            return new b(this);
        }

        public a b(long j) {
            this.g = j;
            return this;
        }

        public a b(String str) {
            this.c = str;
            return this;
        }

        public a c(String str) {
            this.d = str;
            return this;
        }
    }

    /* JADX INFO: renamed from: com.xiaomi.onetrack.f.b$b, reason: collision with other inner class name */
    public static class C0047b {
        public static String A = "ot_test_env";
        public static String B = "ot_privacy_policy";
        public static String C = "market_name";
        public static String D = "ot_ad";
        public static String E = "ot_basic_mode";
        public static String F = "ot_ad_monitor";
        public static String G = "ot_hybrid_type";
        public static String H = "ot_mi_os";
        public static String I = "ot_device_type";
        public static String J = "ot_event_id";
        public static String K = "ot_url_count";
        public static String L = "ot_pb_r";
        public static String a = "event";
        public static String b = "oaid";
        public static String c = "gaid";
        public static String d = "android_id";
        public static String e = "instance_id";
        public static String f = "mfrs";
        public static String g = "model";
        public static String h = "platform";
        public static String i = "miui";
        public static String j = "build";
        public static String k = "os_ver";
        public static String l = "app_id";
        public static String m = "app_ver";
        public static String n = "pkg";
        public static String o = "channel";
        public static String p = "e_ts";
        public static String q = "tz";
        public static String r = "net";
        public static String s = "region";
        public static String t = "plugin_id";
        public static String u = "sdk_ver";
        public static String v = "uid";
        public static String w = "uid_type";
        public static String x = "sid";
        public static String y = "sdk_mode";
        public static String z = "ot_first_day";
    }

    public b() {
    }

    private b(a aVar) {
        this.b = aVar.a;
        this.c = aVar.b;
        this.d = aVar.c;
        this.e = aVar.d;
        this.f = aVar.e;
        this.g = aVar.f;
        this.h = aVar.g;
    }

    public b(b bVar) throws JSONException {
        this.b = bVar.b();
        this.c = bVar.c();
        this.d = bVar.d();
        this.e = bVar.e();
        this.f = bVar.f();
        this.g = new JSONObject(bVar.g().toString());
        this.h = bVar.h();
    }

    public static JSONObject a(ao aoVar, Configuration configuration, y yVar, boolean z) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        Context contextB = com.xiaomi.onetrack.f.a.b();
        String strL = t.l();
        com.xiaomi.onetrack.b.a.a().d(strL);
        jSONObject.put(C0047b.a, aoVar.a());
        boolean zA = a(yVar.b());
        if (!zA) {
            if (t.b() ? t.k() : configuration.isInternational()) {
                if (yVar.b(aoVar.a())) {
                    String strB = DeviceUtil.b(contextB);
                    if (!TextUtils.isEmpty(strB)) {
                        jSONObject.put(C0047b.c, strB);
                    }
                }
                if (!t.b(strL) && yVar.c(aoVar.a())) {
                    jSONObject.put(C0047b.L, "");
                }
            } else {
                String strA = com.xiaomi.onetrack.util.oaid.a.a().a(contextB);
                jSONObject.put(C0047b.b, strA);
                if (!ac.b(strA)) {
                    jSONObject.put(C0047b.d, DeviceUtil.f(contextB));
                }
            }
            jSONObject.put(C0047b.e, r.a().b());
            jSONObject.put(C0047b.t, configuration.getPluginId());
            if (!TextUtils.isEmpty(aoVar.e()) && !TextUtils.isEmpty(aoVar.f())) {
                jSONObject.put(C0047b.v, aoVar.e());
                jSONObject.put(C0047b.w, aoVar.f());
            }
            jSONObject.put(C0047b.x, t.h());
        }
        jSONObject.put(C0047b.D, z);
        jSONObject.put(C0047b.f, DeviceUtil.d());
        jSONObject.put(C0047b.g, DeviceUtil.b());
        jSONObject.put(C0047b.h, "Android");
        jSONObject.put(C0047b.I, DeviceUtil.h());
        jSONObject.put(C0047b.i, t.e());
        jSONObject.put(C0047b.H, t.f());
        jSONObject.put(C0047b.j, t.d());
        jSONObject.put(C0047b.k, t.g());
        jSONObject.put(C0047b.m, com.xiaomi.onetrack.f.a.c());
        jSONObject.put(C0047b.p, aoVar.b());
        jSONObject.put(C0047b.q, t.c());
        jSONObject.put(C0047b.r, com.xiaomi.onetrack.g.c.a(contextB).toString());
        jSONObject.put(C0047b.s, strL);
        jSONObject.put(C0047b.u, BuildConfig.SDK_VERSION);
        jSONObject.put(C0047b.l, aoVar.c());
        jSONObject.put(C0047b.n, com.xiaomi.onetrack.f.a.e());
        jSONObject.put(C0047b.o, !TextUtils.isEmpty(aoVar.d()) ? aoVar.d() : StyleInfo.DEFAULT_PARAMETER);
        jSONObject.put(C0047b.y, (configuration.getMode() != null ? configuration.getMode() : OneTrack.Mode.APP).getType());
        jSONObject.put(C0047b.z, af.b(ad.w()));
        if (s.c) {
            jSONObject.put(C0047b.A, true);
        }
        jSONObject.put(C0047b.B, yVar.a());
        jSONObject.put(C0047b.C, DeviceUtil.c());
        jSONObject.put(C0047b.E, zA);
        jSONObject.put(C0047b.G, "JS");
        return jSONObject;
    }

    public static JSONObject a(String str, Configuration configuration, y yVar, boolean z) throws JSONException {
        return a(str, configuration, "", yVar, z);
    }

    public static JSONObject a(String str, Configuration configuration, String str2, y yVar, boolean z) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        String strL = t.l();
        com.xiaomi.onetrack.b.a.a().d(strL);
        Context contextB = com.xiaomi.onetrack.f.a.b();
        jSONObject.put(C0047b.a, str);
        boolean zA = a(yVar.b());
        if (!zA) {
            if (t.b() ? t.k() : configuration.isInternational()) {
                if (yVar.b(str)) {
                    String strB = DeviceUtil.b(contextB);
                    if (!TextUtils.isEmpty(strB)) {
                        jSONObject.put(C0047b.c, strB);
                    }
                }
                if (!t.b(strL) && yVar.c(str)) {
                    jSONObject.put(C0047b.L, "");
                }
            } else {
                String strA = com.xiaomi.onetrack.util.oaid.a.a().a(contextB);
                jSONObject.put(C0047b.b, strA);
                if (!ac.b(strA)) {
                    jSONObject.put(C0047b.d, DeviceUtil.f(contextB));
                }
            }
            jSONObject.put(C0047b.e, r.a().b());
            a(jSONObject, configuration, str2);
            a(jSONObject, contextB);
            jSONObject.put(C0047b.x, t.h());
        }
        jSONObject.put(C0047b.f, DeviceUtil.d());
        jSONObject.put(C0047b.g, DeviceUtil.b());
        jSONObject.put(C0047b.h, "Android");
        jSONObject.put(C0047b.I, DeviceUtil.h());
        jSONObject.put(C0047b.i, t.e());
        jSONObject.put(C0047b.H, t.f());
        jSONObject.put(C0047b.j, t.d());
        jSONObject.put(C0047b.k, t.g());
        jSONObject.put(C0047b.m, com.xiaomi.onetrack.f.a.c());
        jSONObject.put(C0047b.p, System.currentTimeMillis());
        jSONObject.put(C0047b.q, t.c());
        jSONObject.put(C0047b.r, com.xiaomi.onetrack.g.c.a(contextB).toString());
        jSONObject.put(C0047b.s, strL);
        jSONObject.put(C0047b.u, BuildConfig.SDK_VERSION);
        if (z) {
            jSONObject.put(C0047b.l, configuration.getAdEventAppId());
        } else {
            jSONObject.put(C0047b.l, configuration.getAppId());
        }
        jSONObject.put(C0047b.D, z);
        jSONObject.put(C0047b.n, com.xiaomi.onetrack.f.a.e());
        jSONObject.put(C0047b.o, !TextUtils.isEmpty(configuration.getChannel()) ? configuration.getChannel() : StyleInfo.DEFAULT_PARAMETER);
        jSONObject.put(C0047b.y, (configuration.getMode() != null ? configuration.getMode() : OneTrack.Mode.APP).getType());
        jSONObject.put(C0047b.z, af.b(ad.w()));
        if (s.c) {
            jSONObject.put(C0047b.A, true);
        }
        jSONObject.put(C0047b.B, yVar.a());
        jSONObject.put(C0047b.C, DeviceUtil.c());
        jSONObject.put(C0047b.E, zA);
        return jSONObject;
    }

    private static void a(JSONObject jSONObject, Context context) throws JSONException {
        String strP = ad.p();
        String strR = ad.r();
        if (TextUtils.isEmpty(strP) || TextUtils.isEmpty(strR)) {
            return;
        }
        jSONObject.put(C0047b.v, strP);
        jSONObject.put(C0047b.w, strR);
    }

    private static void a(JSONObject jSONObject, Configuration configuration, String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            jSONObject.put(C0047b.t, configuration.getPluginId());
        } else {
            jSONObject.put(C0047b.t, str);
        }
    }

    public static boolean a(boolean z) {
        if (OneTrack.getGlobalBasicModeEnable() == 0) {
            return z;
        }
        return OneTrack.getGlobalBasicModeEnable() != 1 && OneTrack.getGlobalBasicModeEnable() == 2;
    }

    public void a(int i) {
        this.f = i;
    }

    public void a(long j) {
        this.b = j;
    }

    public long b() {
        return this.b;
    }

    public void b(long j) {
        this.h = j;
    }

    public void b(String str) {
        this.c = str;
    }

    public void b(JSONObject jSONObject) {
        this.g = jSONObject;
    }

    public String c() {
        return this.c;
    }

    public void c(String str) {
        this.d = str;
    }

    public String d() {
        return this.d;
    }

    public void d(String str) {
        this.e = str;
    }

    public String e() {
        return this.e;
    }

    public int f() {
        return this.f;
    }

    public JSONObject g() {
        return this.g;
    }

    public long h() {
        return this.h;
    }

    public boolean i() {
        try {
            JSONObject jSONObject = this.g;
            return (jSONObject == null || !jSONObject.has(an.b) || !this.g.has(an.a) || TextUtils.isEmpty(this.c) || TextUtils.isEmpty(this.d)) ? false : true;
        } catch (Exception e) {
            s.b(a, "check event isValid error, ", e);
            return false;
        }
    }
}
