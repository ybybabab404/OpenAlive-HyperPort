package com.xiaomi.onetrack.util;

import android.content.Context;
import android.text.TextUtils;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class aa {
    private static final String B = "/track/v4";
    private static final String C = "/track/key_get";
    private static final String D = "/api/v4/detail/config";
    private static final String E = "/api/v4/detail/config_common";
    private static final String F = "/api/v1/token";
    private static final String G = "/api/ex/lastUseful";
    private static final String a = "RegionDomainManager";
    private static final String b = "CN";
    private static final String c = "INTL";
    private static final String d = "IN";
    private static final String e = "RU";
    private static final String f = "http://";
    private static final String g = "https://";
    private static String h = "";
    private static String i = "";
    private static String j = "";
    private static String k = "";
    private static String l = "";
    private static String m = "";
    private static String n = "";
    private static String o = "";
    private static String p = "";
    private static String q = "";
    private Context H;
    private static final byte[] r = {116, 114, 97, 99, 107, 105, 110, 103, 46, 109, 105, 117, 105, 46, 99, 111, 109};
    private static final byte[] s = {116, 114, 97, 99, 107, 105, 110, 103, 46, 105, 110, 116, 108, 46, 109, 105, 117, 105, 46, 99, 111, 109};
    private static final byte[] t = {116, 114, 97, 99, 107, 105, 110, 103, 46, 114, 117, 115, 46, 109, 105, 117, 105, 46, 99, 111, 109};
    private static final byte[] u = {116, 114, 97, 99, 107, 105, 110, 103, 46, 105, 110, 100, 105, 97, 46, 109, 105, 117, 105, 46, 99, 111, 109};
    private static final byte[] v = {116, 114, 97, 99, 107, 105, 110, 103, 46, 101, 117, 46, 109, 105, 117, 105, 46, 99, 111, 109};
    private static final byte[] w = {115, 100, 107, 99, 111, 110, 102, 105, 103, 46, 120, 105, 97, 111, 109, 105, 46, 99, 111, 109};
    private static final byte[] x = {115, 100, 107, 99, 111, 110, 102, 105, 103, 46, 105, 110, 116, 108, 46, 120, 105, 97, 111, 109, 105, 46, 99, 111, 109};
    private static final byte[] y = {115, 100, 107, 99, 111, 110, 102, 105, 103, 46, 105, 110, 100, 105, 97, 46, 120, 105, 97, 111, 109, 105, 46, 99, 111, 109};
    private static final byte[] z = {115, 100, 107, 99, 111, 110, 102, 105, 103, 46, 114, 117, 115, 46, 120, 105, 97, 111, 109, 105, 46, 99, 111, 109};
    private static final byte[] A = {115, 100, 107, 99, 111, 110, 102, 105, 103, 46, 101, 117, 46, 120, 105, 97, 111, 109, 105, 46, 99, 111, 109};
    private static ConcurrentHashMap<String, String> I = new ConcurrentHashMap<>();

    public static class a {
        private static final aa a = new aa();

        private a() {
        }
    }

    private aa() {
        h();
        I.put(d, k);
        I.put(e, j);
        i();
    }

    public static aa a() {
        return a.a;
    }

    private void h() {
        h = a(r);
        i = a(s);
        j = a(t);
        k = a(u);
        l = a(v);
        m = a(w);
        n = a(x);
        o = a(y);
        p = a(z);
        q = a(A);
    }

    private void i() {
        try {
            String strE = ad.e();
            if (TextUtils.isEmpty(strE)) {
                return;
            }
            a(new JSONObject(strE));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    private String j() {
        return g;
    }

    private String k() {
        String strL = t.l();
        if (TextUtils.equals(strL, b)) {
            return h;
        }
        String str = I.get(strL);
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            return com.xiaomi.onetrack.util.a.n.contains(strL.toUpperCase()) ? l : i;
        } catch (Exception e2) {
            s.b(a, "getTrackingHostname error: ", e2);
            return i;
        }
    }

    private String l() {
        String strL = t.l();
        if (TextUtils.equals(strL, b)) {
            return m;
        }
        if (TextUtils.equals(strL, d)) {
            return o;
        }
        if (TextUtils.equals(strL, e)) {
            return p;
        }
        try {
            return com.xiaomi.onetrack.util.a.n.contains(strL.toUpperCase()) ? q : n;
        } catch (Exception e2) {
            s.b(a, "getSdkConfigHost error: ", e2);
            return n;
        }
    }

    public String a(String str, String str2, String str3) {
        return str + str2 + str3;
    }

    public String a(byte[] bArr) {
        String str = "";
        try {
            String str2 = new String(bArr, "UTF-8");
            try {
                s.a(a, "transmitToString host:" + str2);
                return str2;
            } catch (Exception e2) {
                e = e2;
                str = str2;
                s.b(a, e.getMessage());
                return str;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    public synchronized void a(JSONObject jSONObject) {
        try {
            s.a(a, "updateHostMap:" + jSONObject.toString());
            try {
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    String strOptString = jSONObject.optString(next);
                    if (!TextUtils.isEmpty(next) && !TextUtils.isEmpty(strOptString)) {
                        I.put(next, strOptString);
                    }
                }
                ad.b(new JSONObject(I).toString());
            } catch (Exception e2) {
                s.a(a, "updateHostMap: " + e2.toString());
            }
            s.a(a, "merge config:" + new JSONObject(I).toString());
        } catch (Throwable th) {
            throw th;
        }
    }

    public String b() {
        try {
            if (TextUtils.isEmpty(ad.i())) {
                com.xiaomi.onetrack.b.g.a().c();
            }
        } catch (Exception e2) {
            s.a(a, "getTrackingUrl: " + e2.toString());
        }
        return a(j(), k(), B);
    }

    public String c() {
        return a(j(), l(), D);
    }

    public String d() {
        return a(j(), l(), E);
    }

    public String e() {
        return a(j(), l(), G);
    }

    public String f() {
        return a(j(), k(), C);
    }

    public String g() {
        try {
            if (TextUtils.isEmpty(ad.i())) {
                com.xiaomi.onetrack.b.g.a().c();
            }
        } catch (Exception e2) {
            s.a(a, "getTrackingUrl: " + e2.toString());
        }
        return a(j(), l(), F);
    }
}
