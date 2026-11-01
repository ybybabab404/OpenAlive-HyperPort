package com.xiaomi.onetrack.b;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.onetrack.BuildConfig;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.as;
import com.xiaomi.onetrack.util.DeviceUtil;
import com.xiaomi.onetrack.util.aa;
import com.xiaomi.onetrack.util.ac;
import com.xiaomi.onetrack.util.ad;
import com.xiaomi.onetrack.util.s;
import com.xiaomi.onetrack.util.t;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class a {
    private static final int A = 2;
    private static final int B = 100;
    private static final long D = 1800000;
    private static final int F = 0;
    public static final String a = "disable_log";
    public static final String b = "event";
    public static final String c = "events";
    public static final String d = "level";
    public static final String e = "sample";
    public static final String f = "needIds";
    public static final String g = "bannedParams";
    public static final String h = "wl";
    public static final String i = "nvc";
    public static final String j = "version";
    public static final String k = "cycle";
    public static final String l = "h_sample";
    public static final String m = "nc";
    public static final String n = "needId";
    private static final String o = "AppConfigUpdater";
    private static final long p = 172800000;
    private static final String q = "hash";
    private static final String r = "appId";
    private static final String s = "apps";
    private static final String t = "type";
    private static final String u = "status";
    private static final String v = "deleted";
    private static final String w = "Android";
    private static final int y = 0;
    private static final int z = 1;
    private static AtomicBoolean x = new AtomicBoolean(false);
    private static ConcurrentHashMap<String, Long> C = new ConcurrentHashMap<>();
    private static b E = new b(Looper.getMainLooper(), null);
    private static ConcurrentHashMap<String, Boolean> G = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Boolean> H = new ConcurrentHashMap<>();
    private static String I = "";

    /* JADX INFO: renamed from: com.xiaomi.onetrack.b.a$a, reason: collision with other inner class name */
    public static class C0046a {
        private static final a a = new a(null);

        private C0046a() {
        }
    }

    public static class b extends Handler {
        private b(Looper looper) {
            super(looper);
        }

        public /* synthetic */ b(Looper looper, com.xiaomi.onetrack.b.b bVar) {
            this(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            s.a(a.o, "ScheduleCloudHandler.handleMessage, msg.what=" + message.what);
            if (message.what == 0) {
                Object obj = message.obj;
                if (obj == null) {
                    s.a(a.o, "ScheduleCloudHandler.handleMessage, msg.obj is null");
                    return;
                }
                try {
                    String str = (String) obj;
                    s.a(a.o, "ScheduleCloudHandler.handleMessage, appId: " + str);
                    com.xiaomi.onetrack.util.j.a(new d(this, str));
                } catch (Exception e) {
                    s.b(a.o, "handleMessage error: " + e.getMessage());
                }
            }
        }
    }

    private a() {
        String strX = ad.x();
        if (!TextUtils.isEmpty(strX)) {
            I = strX;
            return;
        }
        String strL = t.l();
        if (TextUtils.isEmpty(strL)) {
            return;
        }
        I = strL;
        ad.k(strL);
    }

    public /* synthetic */ a(com.xiaomi.onetrack.b.b bVar) {
        this();
    }

    public static a a() {
        return C0046a.a;
    }

    private static JSONArray a(JSONArray jSONArray, JSONArray jSONArray2) {
        int i2 = 0;
        while (jSONArray2 != null) {
            try {
                if (i2 >= jSONArray2.length()) {
                    break;
                }
                JSONObject jSONObjectOptJSONObject = jSONArray2.optJSONObject(i2);
                String strOptString = jSONObjectOptJSONObject.optString("event");
                for (int i3 = 0; jSONArray != null && i3 < jSONArray.length(); i3++) {
                    if (TextUtils.equals(strOptString, jSONArray.optJSONObject(i3).optString("event"))) {
                        jSONArray.remove(i3);
                        break;
                    }
                }
                if (!jSONObjectOptJSONObject.has("status") || (jSONObjectOptJSONObject.has("status") && !TextUtils.equals(jSONObjectOptJSONObject.optString("status"), v))) {
                    if (jSONArray == null) {
                        jSONArray = new JSONArray();
                    }
                    jSONArray.put(jSONObjectOptJSONObject);
                }
                i2++;
            } catch (Exception e2) {
                s.b(o, "mergeEventsElement error:" + e2.toString());
            }
        }
        return jSONArray;
    }

    private static JSONObject a(String str, JSONObject jSONObject) {
        try {
            m mVarF = j.a().f(str);
            jSONObject.put("events", a(mVarF != null ? mVarF.e.optJSONArray("events") : null, jSONObject.optJSONArray("events")));
            return jSONObject;
        } catch (Exception e2) {
            s.b(o, "mergeConfig: " + e2.toString());
            return null;
        }
    }

    public static void a(String str, List<String> list) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optInt(com.xiaomi.onetrack.g.a.d) == 0) {
                d(list);
                a(jSONObject.optJSONObject("data").optJSONArray("apps"), list);
            }
        } catch (Exception e2) {
            s.a(o, "saveAppCloudData: " + e2.toString());
        }
    }

    private static void a(String str, JSONObject jSONObject, long j2) throws JSONException {
        int iOptInt = jSONObject == null ? 0 : jSONObject.optInt("version");
        int iD = j.a().d(str);
        s.a(o, "local version: " + iD + ", server version: " + iOptInt);
        if (iD > 0 && iOptInt < iD && !f(str)) {
            a(jSONObject, j2);
            return;
        }
        int iOptInt2 = jSONObject != null ? jSONObject.optInt("type") : -1;
        s.a(o, "type: " + iOptInt2);
        if (iOptInt2 == 0 || iOptInt2 == 1 || f(str)) {
            a(jSONObject, j2, iOptInt);
        } else if (iOptInt2 == 2) {
            b(jSONObject, j2);
        } else {
            s.a(o, "handleData do nothing!");
        }
    }

    private static void a(List<String> list, long j2) {
        try {
            ArrayList<m> arrayList = new ArrayList<>();
            for (int i2 = 0; i2 < list.size(); i2++) {
                m mVar = new m();
                mVar.a = list.get(i2);
                mVar.b = 100L;
                mVar.c = j2;
                arrayList.add(mVar);
            }
            j.a().a(arrayList);
        } catch (Exception e2) {
            s.b(o, "handleError" + e2.toString());
        }
    }

    private static void a(List<String> list, long j2, List<String> list2) {
        try {
            if (list.size() != list2.size()) {
                list.removeAll(list2);
                a(list, j2);
            }
        } catch (Exception e2) {
            s.b(o, "handleInvalidAppIds error:" + e2.toString());
        }
    }

    private static void a(JSONArray jSONArray, List<String> list) throws JSONException {
        s.a(o, "updateDataToDb start");
        long jCurrentTimeMillis = System.currentTimeMillis() + 86400000 + ((long) new Random().nextInt(86400000));
        if (jSONArray == null || jSONArray.length() <= 0) {
            a(list, jCurrentTimeMillis);
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i2);
            String strOptString = jSONObjectOptJSONObject == null ? "" : jSONObjectOptJSONObject.optString("appId");
            s.a(o, "appId: " + strOptString);
            if (!TextUtils.isEmpty(strOptString)) {
                arrayList.add(strOptString);
                a(strOptString, jSONObjectOptJSONObject, jCurrentTimeMillis);
            }
        }
        a(list, jCurrentTimeMillis, arrayList);
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            H.put(it.next(), Boolean.FALSE);
        }
    }

    private static void a(JSONObject jSONObject, long j2) {
        ArrayList<m> arrayList = new ArrayList<>();
        if (jSONObject != null) {
            m mVar = new m();
            mVar.a = jSONObject.optString("appId");
            mVar.c = j2;
            arrayList.add(mVar);
        }
        if (arrayList.isEmpty()) {
            s.a(o, "updateMinVersionData no timestamp can be updated!");
        } else {
            j.a().a(arrayList);
        }
    }

    private static void a(JSONObject jSONObject, long j2, int i2) throws JSONException {
        m mVarF;
        JSONObject jSONObject2;
        ArrayList<m> arrayList = new ArrayList<>();
        if (jSONObject != null) {
            m mVar = new m();
            mVar.d = jSONObject.optString("hash");
            mVar.a = jSONObject.optString("appId");
            mVar.b = b(jSONObject);
            mVar.c = j2;
            if (!jSONObject.has("events") && (mVarF = j.a().f(mVar.a)) != null && (jSONObject2 = mVarF.e) != null && jSONObject2.optJSONArray("events") != null) {
                jSONObject.put("events", mVarF.e.optJSONArray("events"));
            }
            mVar.e = jSONObject;
            arrayList.add(mVar);
        }
        if (arrayList.isEmpty()) {
            s.a(o, "handleFullOrNoNewData no configuration can be updated!");
        } else {
            j.a().a(arrayList);
        }
    }

    private static int b(JSONObject jSONObject) {
        try {
            int iOptInt = jSONObject.optInt(e, 100);
            if (iOptInt < 0 || iOptInt > 100) {
                return 100;
            }
            return iOptInt;
        } catch (Exception e2) {
            s.a(o, "getCommonSample Exception:" + e2.getMessage());
            return 100;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(List<String> list) {
        s.a(o, "pullCloudData start, appIds: " + list.toString());
        if (t.a(o)) {
            return;
        }
        if (x.compareAndSet(false, true)) {
            HashMap map = new HashMap();
            try {
                String strL = t.l();
                if (OneTrack.getGlobalBasicModeEnable() != 2) {
                    Context contextB = com.xiaomi.onetrack.f.a.b();
                    if (t.k()) {
                        String strB = DeviceUtil.b(contextB);
                        if (!TextUtils.isEmpty(strB)) {
                            map.put(n.b, com.xiaomi.onetrack.d.d.c(strB));
                        }
                    } else {
                        String strA = com.xiaomi.onetrack.util.oaid.a.a().a(contextB);
                        if (ac.b(strA)) {
                            map.put(n.a, com.xiaomi.onetrack.d.d.c(strA));
                        }
                    }
                }
                map.put(n.c, t.e());
                map.put(n.d, t.d());
                map.put(n.e, t.k() ? "1" : "0");
                map.put(n.f, BuildConfig.SDK_VERSION);
                map.put(n.n, com.xiaomi.onetrack.f.a.c());
                map.put(n.g, t.g());
                map.put(n.h, DeviceUtil.b());
                map.put(n.i, strL);
                map.put(n.j, c(list));
                map.put(n.k, com.xiaomi.onetrack.f.a.e());
                map.put(n.m, w);
                map.put(n.o, "1");
                map.put("nvc", ExifInterface.GPS_MEASUREMENT_2D);
                String strC = aa.a().c();
                s.a(o, "pullData:" + strC);
                String strB2 = com.xiaomi.onetrack.g.b.b(strC, map, true);
                s.a(o, "response:" + strB2);
                a(strB2, list);
            } catch (Exception e2) {
                s.b(o, "pullCloudData error: " + e2.getMessage());
            } finally {
                x.set(false);
            }
        }
    }

    private static void b(JSONObject jSONObject, long j2) {
        ArrayList<m> arrayList = new ArrayList<>();
        if (jSONObject == null || !jSONObject.has("events")) {
            s.a(o, "handleIncrementalUpdate config is not change!");
        } else {
            m mVar = new m();
            mVar.d = jSONObject.optString("hash");
            String strOptString = jSONObject.optString("appId");
            mVar.a = strOptString;
            mVar.b = b(jSONObject);
            mVar.c = j2;
            mVar.e = a(strOptString, jSONObject);
            arrayList.add(mVar);
        }
        if (arrayList.isEmpty()) {
            s.a(o, "handleIncrementalUpdate no configuration can be updated!");
        } else {
            j.a().a(arrayList);
        }
    }

    private static String c(List<String> list) {
        JSONArray jSONArray = new JSONArray();
        try {
            JSONObject jSONObject = new JSONObject();
            for (int i2 = 0; i2 < list.size(); i2++) {
                String str = list.get(i2);
                jSONObject.put("appId", str);
                if (f(str)) {
                    jSONObject.put("hash", "");
                } else {
                    jSONObject.put("hash", j.a().c(str));
                }
                jSONArray.put(jSONObject);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return jSONArray.toString();
    }

    private static void d(List<String> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            C.put(list.get(i2), Long.valueOf(System.currentTimeMillis() + D));
        }
    }

    private static boolean f(String str) {
        ConcurrentHashMap<String, Boolean> concurrentHashMap = H;
        return concurrentHashMap != null && concurrentHashMap.containsKey(str) && H.get(str).booleanValue();
    }

    private boolean g(String str) {
        if (!com.xiaomi.onetrack.g.c.a()) {
            s.a(o, "net is not connected!");
            return false;
        }
        m mVarF = j.a().f(str);
        if (mVarF == null) {
            return true;
        }
        long j2 = mVarF.c;
        return j2 < System.currentTimeMillis() || j2 - System.currentTimeMillis() > p || f(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean h(String str) {
        Long l2 = C.get(str);
        return l2 == null || l2.longValue() - System.currentTimeMillis() < 0 || l2.longValue() - System.currentTimeMillis() > D;
    }

    public void a(String str) {
        H.put(str, Boolean.FALSE);
    }

    public void a(JSONObject jSONObject) {
        com.xiaomi.onetrack.util.j.a(new c(this, jSONObject));
    }

    public void b(String str) {
        com.xiaomi.onetrack.util.j.a(new com.xiaomi.onetrack.b.b(this, str));
    }

    public void c(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (!g(str)) {
            s.a(o, "AppConfigUpdater Does not meet prerequisites for request");
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        b(arrayList);
    }

    public void d(String str) {
        if (TextUtils.isEmpty(I) || TextUtils.isEmpty(str) || TextUtils.equals(I, str)) {
            return;
        }
        as.a(I, str);
        Iterator<Map.Entry<String, Boolean>> it = H.entrySet().iterator();
        while (it.hasNext()) {
            it.next().setValue(Boolean.TRUE);
        }
        I = str;
        ad.k(str);
    }
}
