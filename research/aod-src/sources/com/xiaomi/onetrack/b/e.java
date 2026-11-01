package com.xiaomi.onetrack.b;

import android.content.Context;
import android.text.TextUtils;
import com.miui.aod.category.CategoryConstants;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.util.DeviceUtil;
import com.xiaomi.onetrack.util.aa;
import com.xiaomi.onetrack.util.ac;
import com.xiaomi.onetrack.util.ad;
import com.xiaomi.onetrack.util.s;
import com.xiaomi.onetrack.util.t;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class e {
    private static final String a = "CloudControlClawConfigUpdater";
    private static final long c = 345600000;
    private static final String d = "cv";
    private static final String e = "p";
    private static final String f = "i";
    private static final String g = "g";
    private static final String h = "r";
    private static final String i = "m";
    private static final String j = "d";
    private static final String k = "v";
    private static final String l = "cr";
    private static final String m = "result";
    private static final String n = "dl";
    private static final String o = "p";
    private static final String p = "re";
    private static final String q = "adm";
    private static ConcurrentHashMap<Integer, Integer> b = new ConcurrentHashMap<>();
    private static final AtomicBoolean r = new AtomicBoolean(false);
    private static final AtomicLong s = new AtomicLong(-1);

    public static void a() {
        AtomicBoolean atomicBoolean = r;
        try {
            if (!atomicBoolean.compareAndSet(false, true)) {
                s.a(a, "Another thread is already requesting data.");
                return;
            }
            try {
                if (c()) {
                    d();
                } else {
                    s.a(a, "CloudControlClawConfigUpdater Does not meet prerequisites for request");
                }
            } catch (Exception e2) {
                s.a(a, "CloudControlClawConfigUpdater pullDataSync err :" + e2.getMessage());
                atomicBoolean = r;
            }
            atomicBoolean.set(false);
        } catch (Throwable th) {
            r.set(false);
            throw th;
        }
    }

    private static void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optInt(com.xiaomi.onetrack.g.a.d) == 0) {
                String strOptString = jSONObject.optString("result");
                if (TextUtils.isEmpty(strOptString) || TextUtils.equals(CategoryConstants.DEFAULT_PARAM, strOptString)) {
                    com.xiaomi.onetrack.a.a.a().b();
                }
                try {
                    if (!ac.b(strOptString) || t.k()) {
                        return;
                    }
                    JSONObject jSONObject2 = new JSONObject(strOptString);
                    String strOptString2 = jSONObject2.optString(n);
                    JSONObject jSONObjectOptJSONObject = jSONObject2.optJSONObject("adm");
                    if (jSONObjectOptJSONObject != null) {
                        jSONObjectOptJSONObject.put(n, strOptString2);
                    }
                    com.xiaomi.onetrack.a.a.a().a(jSONObjectOptJSONObject);
                    return;
                } catch (Exception e2) {
                    s.c(a, "opt net cloud e: " + e2.getMessage());
                    return;
                }
                s.c(a, "saveCloudData error: " + e.toString());
            }
        } catch (JSONException e3) {
            s.c(a, "saveCloudData error: " + e3.toString());
        }
    }

    public static void b() {
        com.xiaomi.onetrack.util.j.a(new f());
    }

    private static boolean c() {
        if (t.k()) {
            s.a(a, "isInternationalBuild , return false");
            return false;
        }
        if (!com.xiaomi.onetrack.g.c.a()) {
            s.b(a, "net is not connected!");
            return false;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        AtomicLong atomicLong = s;
        if (atomicLong.get() == -1) {
            atomicLong.set(ad.D());
        }
        long j2 = atomicLong.get();
        s.a(a, "canRequest nextUpdateTime=" + j2);
        return j2 < jCurrentTimeMillis || j2 - jCurrentTimeMillis > c;
    }

    private static void d() {
        StringBuilder sb;
        if (t.a(a)) {
            return;
        }
        HashMap map = new HashMap();
        try {
            Context contextA = com.xiaomi.onetrack.f.a.a();
            map.put(d, com.xiaomi.onetrack.f.a.c());
            map.put("p", com.xiaomi.onetrack.f.a.e());
            if (OneTrack.getGlobalBasicModeEnable() != 2) {
                String strA = com.xiaomi.onetrack.util.oaid.a.a().a(contextA);
                if (ac.b(strA)) {
                    map.put(f, com.xiaomi.onetrack.d.d.c(strA));
                }
            }
            String strL = t.l();
            map.put(h, strL);
            map.put(i, DeviceUtil.b());
            map.put(j, t.g());
            map.put(k, t.e());
            map.put(l, t.c(strL));
            String strE = aa.a().e();
            String strA2 = com.xiaomi.onetrack.g.b.a(strE, (Map<String, String>) map, true);
            s.a(a, "url:" + strE + " response:" + strA2);
            a(strA2);
            try {
                long jCurrentTimeMillis = System.currentTimeMillis() + 86400000 + ((long) (Math.random() * 2.592E8d));
                s.set(jCurrentTimeMillis);
                ad.k(jCurrentTimeMillis);
            } catch (Exception e2) {
                e = e2;
                sb = new StringBuilder();
                sb.append("nextUpdateTime error: ");
                sb.append(e.toString());
                String string = sb.toString();
            }
        } catch (Exception e3) {
            s.c(a, "requestCloudData error: " + e3.toString());
            try {
                long jCurrentTimeMillis2 = System.currentTimeMillis() + 86400000 + ((long) (Math.random() * 2.592E8d));
                s.set(jCurrentTimeMillis2);
                ad.k(jCurrentTimeMillis2);
            } catch (Exception e4) {
                e = e4;
                sb = new StringBuilder();
                sb.append("nextUpdateTime error: ");
                sb.append(e.toString());
                String string2 = sb.toString();
            }
        } finally {
            try {
                long jCurrentTimeMillis3 = System.currentTimeMillis() + 86400000 + ((long) (Math.random() * 2.592E8d));
                s.set(jCurrentTimeMillis3);
                ad.k(jCurrentTimeMillis3);
            } catch (Exception e5) {
                s.c(a, "nextUpdateTime error: " + e5.toString());
            }
        }
    }
}
