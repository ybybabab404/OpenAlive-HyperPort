package com.xiaomi.onetrack.d;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.onetrack.util.aa;
import com.xiaomi.onetrack.util.ad;
import com.xiaomi.onetrack.util.s;
import com.xiaomi.onetrack.util.t;
import java.util.HashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class f {
    public static final JSONObject a = new JSONObject();
    private static final String b = "SecretKeyManager";
    private static final String c = "secretKey";
    private static final String d = "sid";
    private static final String e = "key";
    private Context f;
    private JSONObject g;
    private String[] h;

    public static final class a {
        private static final f a = new f();

        private a() {
        }
    }

    private f() {
        this.g = null;
        this.h = new String[2];
        this.f = com.xiaomi.onetrack.f.a.a();
    }

    public static f a() {
        return a.a;
    }

    private void d() {
        if (s.a) {
            if (TextUtils.isEmpty(this.h[0]) || TextUtils.isEmpty(this.h[1])) {
                s.a(b, "key or sid is invalid!");
            } else {
                s.a(b, "key  and sid is valid! ");
            }
        }
    }

    private JSONObject e() {
        JSONObject jSONObjectF = this.g;
        if (jSONObjectF == null && (jSONObjectF = f()) != null) {
            this.g = jSONObjectF;
        }
        return jSONObjectF == null ? c() : jSONObjectF;
    }

    private JSONObject f() {
        try {
            String strD = ad.d();
            if (TextUtils.isEmpty(strD)) {
                return null;
            }
            return new JSONObject(b.b(this.f, strD));
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public synchronized String[] b() {
        try {
            JSONObject jSONObjectE = e();
            this.h[0] = jSONObjectE != null ? jSONObjectE.optString(e) : "";
            this.h[1] = jSONObjectE != null ? jSONObjectE.optString(d) : "";
            d();
        } catch (Throwable th) {
            throw th;
        }
        return this.h;
    }

    public JSONObject c() {
        try {
            if (t.a(b)) {
                return a;
            }
            byte[] bArrA = com.xiaomi.onetrack.d.a.a();
            String strA = c.a(e.a(bArrA));
            HashMap map = new HashMap();
            map.put(c, strA);
            String strB = com.xiaomi.onetrack.g.b.b(aa.a().f(), map, true);
            if (!TextUtils.isEmpty(strB)) {
                JSONObject jSONObject = new JSONObject(strB);
                int iOptInt = jSONObject.optInt(com.xiaomi.onetrack.g.a.d);
                JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                if (iOptInt == 0 && jSONObjectOptJSONObject != null) {
                    String strOptString = jSONObjectOptJSONObject.optString(e);
                    String strOptString2 = jSONObjectOptJSONObject.optString(d);
                    if (!TextUtils.isEmpty(strOptString) && !TextUtils.isEmpty(strOptString2)) {
                        String strA2 = c.a(com.xiaomi.onetrack.d.a.b(c.a(strOptString), bArrA));
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put(e, strA2);
                        jSONObject2.put(d, strOptString2);
                        this.g = jSONObject2;
                        ad.a(b.a(this.f, jSONObject2.toString()));
                        ad.d(System.currentTimeMillis());
                    }
                }
            }
            return this.g;
        } catch (Exception e2) {
            s.b(b, "requestSecretData: " + e2.toString());
        }
    }
}
