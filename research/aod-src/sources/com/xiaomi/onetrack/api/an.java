package com.xiaomi.onetrack.api;

import android.content.Context;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.ServiceQualityEvent;
import com.xiaomi.onetrack.util.DeviceUtil;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class an {
    public static final String a = "B";
    public static final String b = "H";
    private static final String c = "EventDataBuilder";

    public static com.xiaomi.onetrack.f.b a(com.xiaomi.onetrack.f.b bVar, String str, String str2) throws JSONException {
        com.xiaomi.onetrack.f.b bVar2 = new com.xiaomi.onetrack.f.b(bVar);
        bVar2.d(am.g);
        JSONObject jSONObjectG = bVar2.g();
        jSONObjectG.getJSONObject(b).put(com.xiaomi.onetrack.f.b.C0047b.a, am.g);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(com.xiaomi.onetrack.h.d.a.t, bVar.e());
        jSONObject.put(com.xiaomi.onetrack.h.d.a.u, "sdk");
        jSONObject.put(com.xiaomi.onetrack.h.d.a.v, str);
        jSONObject.put(com.xiaomi.onetrack.h.d.a.w, str2);
        jSONObjectG.put(a, jSONObject);
        return bVar2;
    }

    public static String a(long j, String str, long j2, long j3, Configuration configuration, com.xiaomi.onetrack.util.y yVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(b, com.xiaomi.onetrack.f.b.a(am.m, configuration, yVar, false));
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(am.aa, j);
        jSONObject2.put(am.ab, str);
        jSONObject2.put(am.ac, j2);
        jSONObject2.put(am.ad, j3);
        jSONObject.put(a, jSONObject2);
        return jSONObject.toString();
    }

    public static String a(Configuration configuration, com.xiaomi.onetrack.util.y yVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(b, com.xiaomi.onetrack.f.b.a(am.l, configuration, yVar, false));
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(am.t, com.xiaomi.onetrack.c.j.b());
        jSONObject.put(a, jSONObject2);
        return jSONObject.toString();
    }

    public static String a(Configuration configuration, JSONObject jSONObject, com.xiaomi.onetrack.util.y yVar) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(b, com.xiaomi.onetrack.f.b.a("onetrack_dau", configuration, yVar, false));
        JSONObject jSONObject3 = new JSONObject();
        Context contextB = com.xiaomi.onetrack.f.a.b();
        boolean zN = com.xiaomi.onetrack.util.ad.n();
        if (zN) {
            com.xiaomi.onetrack.util.ad.c(false);
        }
        jSONObject3.put(am.B, zN);
        if (!(com.xiaomi.onetrack.util.t.b() ? com.xiaomi.onetrack.util.t.k() : configuration.isInternational())) {
            if (configuration.isIMEIEnable()) {
                jSONObject3.put(am.C, DeviceUtil.c(contextB));
            }
            if (configuration.isIMSIEnable()) {
                jSONObject3.put(am.D, DeviceUtil.d(contextB));
            }
        }
        jSONObject3.put(am.F, al.a(configuration));
        jSONObject2.put(a, com.xiaomi.onetrack.util.u.a(jSONObject3, jSONObject));
        return jSONObject2.toString();
    }

    public static String a(ServiceQualityEvent serviceQualityEvent, Configuration configuration, com.xiaomi.onetrack.util.y yVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(b, com.xiaomi.onetrack.f.b.a(am.e, configuration, yVar, false));
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(am.G, serviceQualityEvent.getScheme());
        jSONObject2.put(am.H, serviceQualityEvent.getHost());
        jSONObject2.put(am.I, serviceQualityEvent.getPort());
        jSONObject2.put("path", serviceQualityEvent.getPath());
        jSONObject2.put(am.K, serviceQualityEvent.getIps());
        jSONObject2.put("response", serviceQualityEvent.getResponseCode());
        jSONObject2.put(am.M, serviceQualityEvent.getStatusCode());
        jSONObject2.put("exception", serviceQualityEvent.getExceptionTag());
        jSONObject2.put(am.O, serviceQualityEvent.getResultType());
        jSONObject2.put("retry", serviceQualityEvent.getRetryCount());
        jSONObject2.put(am.Q, serviceQualityEvent.getRequestTimestamp());
        jSONObject2.put(am.R, serviceQualityEvent.getRequestNetType());
        jSONObject2.put(am.S, serviceQualityEvent.getDnsLookupTime());
        jSONObject2.put(am.T, serviceQualityEvent.getTcpConnectTime());
        jSONObject2.put(am.V, serviceQualityEvent.getHandshakeTime());
        jSONObject2.put(am.W, serviceQualityEvent.getReceiveFirstByteTime());
        jSONObject2.put(am.X, serviceQualityEvent.getReceiveAllByteTime());
        jSONObject2.put(am.U, serviceQualityEvent.getRequestDataSendTime());
        jSONObject2.put("duration", serviceQualityEvent.getDuration());
        jSONObject2.put(am.Z, serviceQualityEvent.getNetSdkVersion());
        Map<String, Object> extraParams = serviceQualityEvent.getExtraParams();
        if (extraParams != null && extraParams.size() > 0) {
            for (Map.Entry<String, Object> entry : extraParams.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (com.xiaomi.onetrack.util.u.b(value)) {
                    jSONObject2.put(key, value);
                }
            }
        }
        jSONObject.put(a, jSONObject2);
        return jSONObject.toString();
    }

    public static String a(ao aoVar, JSONObject jSONObject, Configuration configuration, JSONObject jSONObject2, com.xiaomi.onetrack.util.y yVar) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(b, com.xiaomi.onetrack.f.b.a(aoVar, configuration, yVar, false));
        jSONObject3.put(a, com.xiaomi.onetrack.util.u.a(jSONObject, jSONObject2));
        return jSONObject3.toString();
    }

    public static String a(com.xiaomi.onetrack.h.c.a aVar, Configuration configuration, com.xiaomi.onetrack.util.y yVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(b, com.xiaomi.onetrack.f.b.a(am.f, configuration, yVar, false));
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(com.xiaomi.onetrack.h.d.a.a, aVar.b());
        jSONObject2.put(com.xiaomi.onetrack.h.d.a.b, "sdk");
        JSONObject jSONObject3 = new JSONObject();
        HashMap<String, HashMap<String, Integer>> mapC = aVar.c();
        if (mapC != null) {
            jSONObject3 = a(mapC);
        }
        jSONObject2.put(com.xiaomi.onetrack.h.d.a.c, jSONObject3.toString());
        JSONArray jSONArray = new JSONArray();
        HashMap<String, Integer> mapD = aVar.d();
        if (mapD != null && mapD.size() > 0) {
            for (Map.Entry<String, Integer> entry : mapD.entrySet()) {
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put(com.xiaomi.onetrack.h.d.a.r, entry.getKey());
                jSONObject4.put(com.xiaomi.onetrack.h.d.a.s, entry.getValue());
                jSONArray.put(jSONObject4);
            }
        }
        jSONObject2.put(com.xiaomi.onetrack.h.d.a.d, jSONArray);
        jSONObject.put(a, jSONObject2);
        return jSONObject.toString();
    }

    public static String a(String str, String str2, long j, Configuration configuration, JSONObject jSONObject, com.xiaomi.onetrack.util.y yVar) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(b, com.xiaomi.onetrack.f.b.a(str2, configuration, yVar, false));
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(am.v, str);
        jSONObject3.put("type", 2);
        jSONObject3.put("duration", j);
        jSONObject2.put(a, com.xiaomi.onetrack.util.u.a(jSONObject3, jSONObject));
        return jSONObject2.toString();
    }

    public static String a(String str, String str2, Configuration configuration, JSONObject jSONObject, boolean z, com.xiaomi.onetrack.util.y yVar) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(b, com.xiaomi.onetrack.f.b.a(str2, configuration, yVar, false));
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(am.v, str);
        jSONObject3.put("type", 1);
        jSONObject3.put(am.x, z);
        jSONObject2.put(a, com.xiaomi.onetrack.util.u.a(jSONObject3, jSONObject));
        return jSONObject2.toString();
    }

    public static String a(String str, String str2, String str3, Configuration configuration, com.xiaomi.onetrack.util.y yVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(b, com.xiaomi.onetrack.f.b.a(am.g, configuration, yVar, false));
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(com.xiaomi.onetrack.h.d.a.t, str);
        jSONObject2.put(com.xiaomi.onetrack.h.d.a.u, "sdk");
        jSONObject2.put(com.xiaomi.onetrack.h.d.a.v, str2);
        jSONObject2.put(com.xiaomi.onetrack.h.d.a.w, str3);
        jSONObject.put(a, jSONObject2);
        return jSONObject.toString();
    }

    public static String a(String str, String str2, String str3, String str4, String str5, long j, Configuration configuration, JSONObject jSONObject, com.xiaomi.onetrack.util.y yVar, boolean z) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObjectA = com.xiaomi.onetrack.f.b.a("onetrack_bug_report", configuration, yVar, false);
        if (str5 != null) {
            jSONObjectA.put(com.xiaomi.onetrack.f.b.C0047b.m, str5);
        }
        jSONObject2.put(b, jSONObjectA);
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("exception", str);
        jSONObject3.put("type", str3);
        jSONObject3.put("message", str2);
        jSONObject3.put(am.q, str4);
        jSONObject3.put(am.r, j);
        if (z) {
            jSONObject3.put(am.s, 1);
        }
        jSONObject2.put(a, com.xiaomi.onetrack.util.u.a(jSONObject3, jSONObject));
        return jSONObject2.toString();
    }

    public static String a(String str, String str2, JSONObject jSONObject, Configuration configuration, JSONObject jSONObject2, com.xiaomi.onetrack.util.y yVar) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(b, com.xiaomi.onetrack.f.b.a(str2, configuration, str, yVar, false));
        jSONObject3.put(a, com.xiaomi.onetrack.util.u.a(jSONObject, jSONObject2));
        return jSONObject3.toString();
    }

    public static String a(String str, JSONObject jSONObject, Configuration configuration, JSONObject jSONObject2, com.xiaomi.onetrack.util.y yVar) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(b, com.xiaomi.onetrack.f.b.a(str, configuration, yVar, false));
        jSONObject3.put(a, com.xiaomi.onetrack.util.u.a(jSONObject, jSONObject2));
        return jSONObject3.toString();
    }

    public static String a(String str, JSONObject jSONObject, Configuration configuration, JSONObject jSONObject2, com.xiaomi.onetrack.util.y yVar, JSONArray jSONArray) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        JSONObject jSONObjectA = com.xiaomi.onetrack.f.b.a(str, configuration, yVar, true);
        if (jSONArray != null && jSONArray.length() > 0) {
            jSONObjectA.put(com.xiaomi.onetrack.f.b.C0047b.F, jSONArray);
        }
        jSONObject3.put(b, jSONObjectA);
        jSONObject3.put(a, com.xiaomi.onetrack.util.u.a(jSONObject, jSONObject2));
        return jSONObject3.toString();
    }

    public static String a(JSONObject jSONObject, Configuration configuration, JSONObject jSONObject2, com.xiaomi.onetrack.util.y yVar) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(b, com.xiaomi.onetrack.f.b.a(am.c, configuration, yVar, false));
        jSONObject3.put(a, com.xiaomi.onetrack.util.u.a(jSONObject, jSONObject2));
        return jSONObject3.toString();
    }

    private static JSONObject a(HashMap<String, HashMap<String, Integer>> map) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        HashMap<String, Integer> map2 = map.get(com.xiaomi.onetrack.h.d.a.e);
        if (map2 != null) {
            jSONObject.put(com.xiaomi.onetrack.h.d.a.e, map2.get(""));
        } else {
            jSONObject.put(com.xiaomi.onetrack.h.d.a.e, 0);
        }
        HashMap<String, Integer> map3 = map.get(com.xiaomi.onetrack.h.d.a.f);
        JSONObject jSONObject2 = new JSONObject();
        if (map3 == null) {
            jSONObject2.put(com.xiaomi.onetrack.h.d.a.m, 0);
            jSONObject2.put(com.xiaomi.onetrack.h.d.a.n, 0);
            jSONObject2.put(com.xiaomi.onetrack.h.d.a.o, 0);
            jSONObject2.put(com.xiaomi.onetrack.h.d.a.p, 0);
        } else {
            jSONObject2.put(com.xiaomi.onetrack.h.d.a.m, map3.get(com.xiaomi.onetrack.h.d.a.m) == null ? 0 : map3.get(com.xiaomi.onetrack.h.d.a.m).intValue());
            jSONObject2.put(com.xiaomi.onetrack.h.d.a.n, map3.get(com.xiaomi.onetrack.h.d.a.n) == null ? 0 : map3.get(com.xiaomi.onetrack.h.d.a.n).intValue());
            jSONObject2.put(com.xiaomi.onetrack.h.d.a.o, map3.get(com.xiaomi.onetrack.h.d.a.o) == null ? 0 : map3.get(com.xiaomi.onetrack.h.d.a.o).intValue());
            jSONObject2.put(com.xiaomi.onetrack.h.d.a.p, map3.get(com.xiaomi.onetrack.h.d.a.p) == null ? 0 : map3.get(com.xiaomi.onetrack.h.d.a.p).intValue());
        }
        jSONObject.put(com.xiaomi.onetrack.h.d.a.f, jSONObject2);
        HashMap<String, Integer> map4 = map.get(com.xiaomi.onetrack.h.d.a.g);
        if (map4 != null) {
            jSONObject.put(com.xiaomi.onetrack.h.d.a.g, map4.get(""));
        } else {
            jSONObject.put(com.xiaomi.onetrack.h.d.a.g, 0);
        }
        HashMap<String, Integer> map5 = map.get(com.xiaomi.onetrack.h.d.a.h);
        JSONObject jSONObject3 = new JSONObject();
        if (map5 == null) {
            jSONObject3.put(com.xiaomi.onetrack.h.d.a.m, 0);
            jSONObject3.put(com.xiaomi.onetrack.h.d.a.n, 0);
        } else {
            jSONObject3.put(com.xiaomi.onetrack.h.d.a.m, map5.get(com.xiaomi.onetrack.h.d.a.m) == null ? 0 : map5.get(com.xiaomi.onetrack.h.d.a.m).intValue());
            jSONObject3.put(com.xiaomi.onetrack.h.d.a.n, map5.get(com.xiaomi.onetrack.h.d.a.n) == null ? 0 : map5.get(com.xiaomi.onetrack.h.d.a.n).intValue());
        }
        jSONObject.put(com.xiaomi.onetrack.h.d.a.h, jSONObject3);
        if (map.get(com.xiaomi.onetrack.h.d.a.i) != null) {
            HashMap<String, Integer> map6 = map.get(com.xiaomi.onetrack.h.d.a.i);
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put(com.xiaomi.onetrack.h.d.a.l, map6.get(com.xiaomi.onetrack.h.d.a.l) == null ? 0 : map6.get(com.xiaomi.onetrack.h.d.a.l).intValue());
            jSONObject4.put(com.xiaomi.onetrack.h.d.a.m, map6.get(com.xiaomi.onetrack.h.d.a.m) == null ? 0 : map6.get(com.xiaomi.onetrack.h.d.a.m).intValue());
            jSONObject4.put(com.xiaomi.onetrack.h.d.a.n, map6.get(com.xiaomi.onetrack.h.d.a.n) == null ? 0 : map6.get(com.xiaomi.onetrack.h.d.a.n).intValue());
            jSONObject4.put(com.xiaomi.onetrack.h.d.a.o, map6.get(com.xiaomi.onetrack.h.d.a.o) == null ? 0 : map6.get(com.xiaomi.onetrack.h.d.a.o).intValue());
            jSONObject4.put(com.xiaomi.onetrack.h.d.a.p, map6.get(com.xiaomi.onetrack.h.d.a.p) == null ? 0 : map6.get(com.xiaomi.onetrack.h.d.a.p).intValue());
            jSONObject4.put(com.xiaomi.onetrack.h.d.a.q, map6.get(com.xiaomi.onetrack.h.d.a.q) != null ? map6.get(com.xiaomi.onetrack.h.d.a.q).intValue() : 0);
            jSONObject.put(com.xiaomi.onetrack.h.d.a.i, jSONObject4);
        }
        if (map.get(com.xiaomi.onetrack.h.d.a.j) != null) {
            jSONObject.put(com.xiaomi.onetrack.h.d.a.j, map.get(com.xiaomi.onetrack.h.d.a.j).get(""));
        }
        if (map.get(com.xiaomi.onetrack.h.d.a.k) != null) {
            jSONObject.put(com.xiaomi.onetrack.h.d.a.k, map.get(com.xiaomi.onetrack.h.d.a.k).get(""));
        }
        return jSONObject;
    }

    public static String b(JSONObject jSONObject, Configuration configuration, JSONObject jSONObject2, com.xiaomi.onetrack.util.y yVar) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(b, com.xiaomi.onetrack.f.b.a(am.d, configuration, yVar, false));
        jSONObject3.put(a, com.xiaomi.onetrack.util.u.a(jSONObject, jSONObject2));
        return jSONObject3.toString();
    }

    public static String c(JSONObject jSONObject, Configuration configuration, JSONObject jSONObject2, com.xiaomi.onetrack.util.y yVar) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(b, com.xiaomi.onetrack.f.b.a("ot_login", configuration, yVar, false));
        jSONObject3.put(a, com.xiaomi.onetrack.util.u.a(jSONObject, jSONObject2));
        return jSONObject3.toString();
    }

    public static String d(JSONObject jSONObject, Configuration configuration, JSONObject jSONObject2, com.xiaomi.onetrack.util.y yVar) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(b, com.xiaomi.onetrack.f.b.a("ot_logout", configuration, yVar, false));
        jSONObject3.put(a, com.xiaomi.onetrack.util.u.a(jSONObject, jSONObject2));
        return jSONObject3.toString();
    }
}
