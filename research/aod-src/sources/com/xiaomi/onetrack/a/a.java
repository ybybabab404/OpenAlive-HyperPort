package com.xiaomi.onetrack.a;

import android.text.TextUtils;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.onetrack.util.ac;
import com.xiaomi.onetrack.util.ad;
import com.xiaomi.onetrack.util.af;
import com.xiaomi.onetrack.util.s;
import com.xiaomi.onetrack.util.t;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class a {
    private static String g = "AdMExceptionControl";
    private static volatile a h;
    public ConcurrentSkipListSet<String> a;
    public ConcurrentSkipListSet<String> b;
    public ConcurrentSkipListSet<String> c;
    public ConcurrentSkipListSet<String> d;
    public ConcurrentSkipListSet<String> e;
    private long k;
    private long i = -1;
    private int j = 20;
    public volatile boolean f = false;

    private a() {
        this.k = 0L;
        String strE = ad.E();
        if (ac.b(strE)) {
            a(strE);
            this.k = ad.F();
        }
    }

    public static a a() {
        if (h == null) {
            e();
        }
        return h;
    }

    private ConcurrentSkipListSet<String> a(ConcurrentSkipListSet<String> concurrentSkipListSet, JSONArray jSONArray) {
        if (jSONArray != null && jSONArray.length() != 0) {
            if (concurrentSkipListSet == null) {
                try {
                    concurrentSkipListSet = new ConcurrentSkipListSet<>();
                } catch (Exception e) {
                    s.b(g, "refreshMemorySet: ", e);
                }
            }
            concurrentSkipListSet.clear();
            for (int i = 0; i < jSONArray.length(); i++) {
                concurrentSkipListSet.add(jSONArray.getString(i));
            }
            return concurrentSkipListSet;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(com.xiaomi.onetrack.a.b.a aVar, int i, Throwable th) {
        ConcurrentSkipListSet<String> concurrentSkipListSet = this.c;
        if (concurrentSkipListSet != null && !concurrentSkipListSet.isEmpty()) {
            boolean z = false;
            for (String str : this.c) {
                if (aVar.c().contains(str)) {
                    z = true;
                }
                s.a(g, "AdMUrl:" + aVar.c() + "，cloudUrl:" + str + "，isMatchInUrl:" + z);
            }
            if (!z) {
                s.a(g, "is filter ,caught exception url no match");
                return true;
            }
        }
        ConcurrentSkipListSet<String> concurrentSkipListSet2 = this.d;
        if (concurrentSkipListSet2 != null && !concurrentSkipListSet2.isEmpty()) {
            boolean z2 = false;
            for (String str2 : this.d) {
                if (str2.contains("xx")) {
                    if (Pattern.matches(str2.replace("xx", "\\d{2}"), String.valueOf(i))) {
                        z2 = true;
                    }
                } else if (str2.equals(String.valueOf(i))) {
                    z2 = true;
                }
            }
            if (!z2) {
                s.a(g, "is filter ,caught exception code no match,code is " + i);
                return true;
            }
        }
        ConcurrentSkipListSet<String> concurrentSkipListSet3 = this.e;
        if (concurrentSkipListSet3 != null && !concurrentSkipListSet3.isEmpty()) {
            if (th == null || TextUtils.isEmpty(th.getClass().getSimpleName())) {
                s.a(g, "is filter ,caught exception type is null");
                return true;
            }
            boolean z3 = false;
            for (String str3 : this.e) {
                if (str3.equals(th.getClass().getSimpleName())) {
                    z3 = true;
                }
                s.a(g, "AdMException:" + th.getClass().getName() + "，cloudException:" + str3 + "，isMatchExceptionType:" + z3);
            }
            if (!z3) {
                s.a(g, "is filter ,caught exception type no match");
                return true;
            }
        }
        return false;
    }

    private static void e() {
        if (h == null) {
            synchronized (a.class) {
                try {
                    if (h == null) {
                        h = new a();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean f() {
        if (this.i != -1 && System.currentTimeMillis() < this.i) {
            return false;
        }
        s.a(g, "admPolicy is expiredTime :" + this.i);
        b();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean g() {
        if (this.k == 0) {
            s.a(g, "no caught data found, allowTodayCaught true");
            return true;
        }
        long jB = af.b();
        long j = this.k;
        if (j / 100 != jB) {
            s.a(g, "today no caught , allowTodayCaught true");
            this.k = 0L;
            ad.l(0L);
            return true;
        }
        long j2 = j - (jB * 100);
        if (j2 < this.j) {
            s.a(g, "today allow caught ,mDaysMaxCount :" + this.j + ", todayCount:" + j2);
            return true;
        }
        s.a(g, "today not allow caught ,mDaysMaxCount :" + this.j + ", todayCount:" + j2);
        return false;
    }

    public void a(com.xiaomi.onetrack.a.b.a aVar, int i, String str, Throwable th) {
        if (!this.f) {
            s.a(g, "OpenCaughtAdMException is false");
            return;
        }
        if (t.k()) {
            s.a(g, "trackAdMCaughtExceptionEvent isInternational return");
        } else if (aVar != null && ac.b(aVar.c()) && ac.b(aVar.j())) {
            com.xiaomi.onetrack.c.d.a(new b(this, aVar, i, th, str));
        } else {
            s.a(g, "caught exception return , monitor or url is null");
        }
    }

    public void a(String str) {
        try {
            s.a(g, "parseAdmPolicy: " + str);
            if (ac.b(str)) {
                JSONObject jSONObject = new JSONObject(str);
                this.i = af.a(jSONObject.optString("dl"));
                if (f()) {
                    return;
                }
                this.f = true;
                JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("p");
                JSONArray jSONArrayOptJSONArray2 = jSONObject.optJSONArray("e");
                JSONArray jSONArrayOptJSONArray3 = jSONObject.optJSONArray("u");
                JSONArray jSONArrayOptJSONArray4 = jSONObject.optJSONArray("r");
                JSONArray jSONArrayOptJSONArray5 = jSONObject.optJSONArray(AnimatedProperty.PROPERTY_NAME_X);
                int iOptInt = jSONObject.optInt("c", 20);
                this.j = iOptInt;
                if (iOptInt > 100) {
                    this.j = 100;
                }
                this.a = a(this.a, jSONArrayOptJSONArray);
                this.b = a(this.b, jSONArrayOptJSONArray2);
                this.c = a(this.c, jSONArrayOptJSONArray3);
                this.d = a(this.d, jSONArrayOptJSONArray4);
                this.e = a(this.e, jSONArrayOptJSONArray5);
                ad.s(str);
            }
        } catch (Exception e) {
            s.b(g, "parseAdmPolicy exception: " + e);
        }
    }

    public void a(JSONObject jSONObject) {
        if (jSONObject == null) {
            b();
        } else {
            a(jSONObject.toString());
        }
    }

    public void b() {
        ad.s("");
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = false;
    }

    public void c() {
        long j = this.k;
        if (j == 0) {
            this.k = (af.b() * 100) + 1;
        } else {
            this.k = j + 1;
        }
        ad.l(this.k);
    }
}
