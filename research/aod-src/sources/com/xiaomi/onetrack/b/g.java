package com.xiaomi.onetrack.b;

import android.text.TextUtils;
import com.xiaomi.onetrack.BuildConfig;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.util.DeviceUtil;
import com.xiaomi.onetrack.util.aa;
import com.xiaomi.onetrack.util.ac;
import com.xiaomi.onetrack.util.ad;
import com.xiaomi.onetrack.util.s;
import com.xiaomi.onetrack.util.t;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class g {
    public static final String a = "l";
    private static final String b = "CommonConfigUpdater";
    private static final String c = "t";
    private static final String d = "regionUrl";
    private static final String e = "levels";
    private static final String f = "Android";
    private static final long h = 172800000;
    private static volatile g i;
    private static ConcurrentHashMap<Integer, Integer> g = new ConcurrentHashMap<>();
    private static volatile boolean j = false;

    private g() {
        h();
    }

    private int a(int i2) {
        int i3 = i2 - 120000;
        if (i2 <= 120000) {
            i3 = 120000;
        }
        return 120000 + ((int) (Math.random() * ((double) i3)));
    }

    public static g a() {
        if (i == null) {
            synchronized (g.class) {
                try {
                    if (i == null) {
                        i = new g();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return i;
    }

    private void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optInt(com.xiaomi.onetrack.g.a.d) == 0) {
                String strOptString = jSONObject.optString(com.xiaomi.onetrack.g.a.e);
                JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                if (jSONObjectOptJSONObject != null) {
                    a(jSONObjectOptJSONObject);
                    ad.d(jSONObjectOptJSONObject.toString());
                    ad.c(strOptString);
                }
                ad.e(System.currentTimeMillis() + 86400000 + ((long) new Random().nextInt(86400000)));
            }
        } catch (JSONException e2) {
            s.a(b, "saveCommonCloudData: " + e2.toString());
        }
    }

    private void a(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(d);
        if (jSONObjectOptJSONObject != null) {
            aa.a().a(jSONObjectOptJSONObject);
        }
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(e);
        if (jSONArrayOptJSONArray != null) {
            for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                JSONObject jSONObject2 = jSONArrayOptJSONArray.getJSONObject(i2);
                int iOptInt = jSONObject2.optInt(a);
                int iOptInt2 = jSONObject2.optInt(c);
                if (iOptInt > 0 && iOptInt2 > 0) {
                    g.put(Integer.valueOf(iOptInt), Integer.valueOf(iOptInt2));
                }
            }
            i();
        }
    }

    private static boolean e() {
        if (!com.xiaomi.onetrack.g.c.a()) {
            s.b(b, "net is not connected!");
            return false;
        }
        if (TextUtils.isEmpty(ad.i())) {
            return true;
        }
        long jG = ad.g();
        return jG < System.currentTimeMillis() || jG - System.currentTimeMillis() > h;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        if (t.a(b) || j) {
            return;
        }
        j = true;
        HashMap map = new HashMap();
        try {
            if (OneTrack.getGlobalBasicModeEnable() != 2) {
                String strA = com.xiaomi.onetrack.util.oaid.a.a().a(com.xiaomi.onetrack.f.a.b());
                if (ac.b(strA)) {
                    strA = com.xiaomi.onetrack.d.d.c(strA);
                }
                map.put(n.a, strA);
            }
            map.put(n.c, t.e());
            map.put(n.d, t.d());
            map.put(n.e, t.k() ? "1" : "0");
            map.put(n.f, BuildConfig.SDK_VERSION);
            map.put(n.n, com.xiaomi.onetrack.f.a.c());
            map.put(n.g, t.g());
            map.put(n.h, DeviceUtil.b());
            map.put(n.i, t.l());
            map.put(n.m, f);
            String strD = aa.a().d();
            String strB = com.xiaomi.onetrack.g.b.b(strD, map, true);
            s.a(b, "url:" + strD + " response:" + strB);
            a(strB);
        } catch (Exception e2) {
            s.a(b, "requestCloudData: " + e2.toString());
        }
        j = false;
    }

    private HashMap<Integer, Integer> g() {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1, 1000);
        map.put(2, 15000);
        map.put(3, 900000);
        map.put(4, Integer.valueOf(a(900000)));
        return map;
    }

    private void h() {
        String strI = ad.i();
        if (TextUtils.isEmpty(strI)) {
            return;
        }
        try {
            a(new JSONObject(strI));
        } catch (Exception e2) {
            s.c(b, "updateCacheBySp error: " + e2.getMessage());
        }
    }

    private void i() {
        try {
            ConcurrentHashMap<Integer, Integer> concurrentHashMap = g;
            if (concurrentHashMap == null || concurrentHashMap.isEmpty() || !g.containsKey(4)) {
                return;
            }
            g.put(4, Integer.valueOf(a(g.get(4).intValue())));
        } catch (Exception e2) {
            s.a(b, "updateDiscreteValue error: " + e2.getMessage());
        }
    }

    public void b() {
        com.xiaomi.onetrack.util.j.a(new h(this));
    }

    public void c() {
        if (e()) {
            f();
        } else {
            s.a(b, "CommonConfigUpdater Does not meet prerequisites for request");
        }
    }

    public Map<Integer, Integer> d() {
        try {
            if (!g.isEmpty()) {
                return g;
            }
            h();
            return g.isEmpty() ? g() : g;
        } catch (Exception e2) {
            s.a(b, "getLevelIntervalConfig: " + e2.toString());
        }
    }
}
