package com.xiaomi.onetrack.i;

import android.text.TextUtils;
import com.xiaomi.onetrack.util.s;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class a {
    public static final int j = 0;
    public static final int k = -10;
    private static final String l = "TokenBean";
    public int a;
    public String b;
    public String c;
    public String d;
    public int e;
    public long f;
    public String g;
    public String h;
    public boolean i = false;

    public static a a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            a aVar = new a();
            JSONObject jSONObject = new JSONObject(str);
            aVar.a = jSONObject.optInt(com.xiaomi.onetrack.g.a.d);
            String strOptString = jSONObject.optString(com.xiaomi.onetrack.g.a.c);
            aVar.b = strOptString;
            if (aVar.a != 0) {
                if (!TextUtils.isEmpty(strOptString)) {
                    s.b(l, "token exception response :" + aVar.b);
                }
                return null;
            }
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
            if (jSONObjectOptJSONObject != null) {
                String strOptString2 = jSONObjectOptJSONObject.optString("accessToken");
                aVar.h = jSONObjectOptJSONObject.optString("ot_pb_r");
                if (!TextUtils.isEmpty(strOptString2)) {
                    aVar.d = com.xiaomi.onetrack.d.a.c(strOptString2, com.xiaomi.onetrack.d.a.a);
                    aVar.e = jSONObjectOptJSONObject.optInt("expires");
                    long jOptLong = jSONObjectOptJSONObject.optLong("local_time", 0L);
                    if (jOptLong == 0) {
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        aVar.f = jCurrentTimeMillis;
                        jSONObjectOptJSONObject.put("local_time", jCurrentTimeMillis);
                    } else {
                        aVar.f = jOptLong;
                    }
                }
                if (TextUtils.isEmpty(strOptString2) || TextUtils.isEmpty(aVar.d)) {
                    s.b(l, "parseResponseData response token is null");
                    return null;
                }
            }
            aVar.g = jSONObject.toString();
            return aVar;
        } catch (Exception e) {
            s.b(l, "parseResponseData  e:" + e);
        }
    }

    public String toString() {
        return "TokenBean{code=" + this.a + ", msg='" + this.b + "', data='" + this.c + "', access_token='" + this.d + "', expires=" + this.e + ", local_time=" + this.f + ", response='" + this.g + "'}";
    }
}
