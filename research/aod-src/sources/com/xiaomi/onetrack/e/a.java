package com.xiaomi.onetrack.e;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.onetrack.api.an;
import com.xiaomi.onetrack.util.DeviceUtil;
import com.xiaomi.onetrack.util.q;
import com.xiaomi.onetrack.util.r;
import com.xiaomi.onetrack.util.s;
import com.xiaomi.onetrack.util.t;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class a extends com.xiaomi.onetrack.f.b {
    public static final String a = "adm";
    public static final String b = "type";
    public static final String c = "ad_event";
    public static final String d = "ad_pkg";
    public static final String e = "e_host";
    public static final String f = "url_index";
    public static final String g = "retry";
    public static final String h = "err_code";
    public static final String i = "err_body";
    public static final String j = "exception";
    public static final String k = "message";
    private static final String p = "CaughtAdMExceptionEvent";
    private static final String q = "onetrack_exception";

    public a(com.xiaomi.onetrack.a.b.a aVar, int i2, String str, Throwable th) {
        b(com.xiaomi.onetrack.util.a.m);
        c(com.xiaomi.onetrack.f.a.e());
        a(2);
        d("onetrack_exception");
        b(b(aVar, i2, str, th));
        b(System.currentTimeMillis());
    }

    private JSONObject b(com.xiaomi.onetrack.a.b.a aVar, int i2, String str, Throwable th) {
        try {
            JSONObject jSONObjectA = a(aVar);
            JSONObject jSONObjectA2 = a(aVar, i2, str, th);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(an.b, jSONObjectA);
            jSONObject.put(an.a, jSONObjectA2);
            return jSONObject;
        } catch (JSONException e2) {
            s.b(p, "create caught exception event error", e2);
            return null;
        }
    }

    public String a(String str) {
        try {
            return TextUtils.isEmpty(str) ? "" : new URL(str).getHost();
        } catch (Exception e2) {
            s.b(p, "getHost error", e2);
            return "";
        }
    }

    public JSONObject a(com.xiaomi.onetrack.a.b.a aVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        Context contextA = com.xiaomi.onetrack.f.a.a();
        jSONObject.put(com.xiaomi.onetrack.f.b.C0047b.a, e());
        if (s.a) {
            if (t.k()) {
                jSONObject.put(com.xiaomi.onetrack.f.b.C0047b.c, DeviceUtil.b(contextA));
            } else {
                jSONObject.put(com.xiaomi.onetrack.f.b.C0047b.b, q.a(contextA));
            }
        }
        jSONObject.put(com.xiaomi.onetrack.f.b.C0047b.e, r.a().b());
        jSONObject.put(com.xiaomi.onetrack.f.b.C0047b.f, DeviceUtil.d());
        jSONObject.put(com.xiaomi.onetrack.f.b.C0047b.g, DeviceUtil.b());
        jSONObject.put(com.xiaomi.onetrack.f.b.C0047b.h, "Android");
        jSONObject.put(com.xiaomi.onetrack.f.b.C0047b.i, t.e());
        jSONObject.put(com.xiaomi.onetrack.f.b.C0047b.H, t.f());
        jSONObject.put(com.xiaomi.onetrack.f.b.C0047b.j, t.d());
        jSONObject.put(com.xiaomi.onetrack.f.b.C0047b.k, t.g());
        jSONObject.put(com.xiaomi.onetrack.f.b.C0047b.m, com.xiaomi.onetrack.f.a.c());
        jSONObject.put(com.xiaomi.onetrack.f.b.C0047b.p, System.currentTimeMillis());
        jSONObject.put(com.xiaomi.onetrack.f.b.C0047b.q, t.c());
        jSONObject.put(com.xiaomi.onetrack.f.b.C0047b.r, com.xiaomi.onetrack.g.c.a(contextA).toString());
        jSONObject.put(com.xiaomi.onetrack.f.b.C0047b.s, t.l());
        jSONObject.put(com.xiaomi.onetrack.f.b.C0047b.l, com.xiaomi.onetrack.util.a.m);
        jSONObject.put(com.xiaomi.onetrack.f.b.C0047b.n, com.xiaomi.onetrack.f.a.e());
        jSONObject.put(com.xiaomi.onetrack.f.b.C0047b.C, DeviceUtil.c());
        if (aVar != null) {
            jSONObject.put(com.xiaomi.onetrack.f.b.C0047b.J, aVar.j());
            jSONObject.put(com.xiaomi.onetrack.f.b.C0047b.K, aVar.h());
        }
        return jSONObject;
    }

    public JSONObject a(com.xiaomi.onetrack.a.b.a aVar, int i2, String str, Throwable th) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", a);
            if (aVar != null) {
                jSONObject.put(c, aVar.a());
                jSONObject.put(d, aVar.f());
                jSONObject.put(e, a(aVar.c()));
                jSONObject.put("url_index", aVar.i());
            }
            jSONObject.put("retry", 0);
            jSONObject.put(h, i2);
            jSONObject.put(i, str);
            if (th != null) {
                try {
                    StringWriter stringWriter = new StringWriter();
                    th.printStackTrace(new PrintWriter(stringWriter));
                    jSONObject.put("exception", stringWriter.toString());
                } catch (Throwable unused) {
                    s.b(p, "printStackTrace error");
                }
                jSONObject.put("message", th.getMessage());
            }
            return jSONObject;
        } catch (JSONException e2) {
            s.b(p, "createBusinessData error", e2);
            return null;
        }
    }
}
