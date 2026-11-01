package com.xiaomi.onetrack.i;

import android.text.TextUtils;
import com.xiaomi.onetrack.util.ad;
import com.xiaomi.onetrack.util.s;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class b {
    private static final String a = "TokenChecker";
    private static final int b = 10;
    private static final int c = 4;
    private static final int d = 1800;
    private static final String e = "nextRequestTime";
    private static final String f = "failedCount";
    private static final String g = "invalidCount";
    private static long h;
    private static int i;
    private static int j;
    private static long k;
    private static int l;
    private static int m;
    private static volatile b n;
    private JSONObject o;

    private b() {
        this.o = null;
        try {
            String strC = ad.C();
            if (TextUtils.isEmpty(strC)) {
                return;
            }
            JSONObject jSONObject = new JSONObject(strC);
            this.o = jSONObject;
            long jOptLong = jSONObject.optLong(e, 0L);
            k = jOptLong;
            h = jOptLong;
            int iOptInt = this.o.optInt(f, 0);
            l = iOptInt;
            i = iOptInt;
            int iOptInt2 = this.o.optInt(g, 0);
            m = iOptInt2;
            j = iOptInt2;
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public static int a(int i2) {
        int iMin;
        int i3;
        int iNextInt = 0;
        if (i2 <= 0) {
            return 0;
        }
        int iMin2 = d;
        if (i2 >= 5) {
            iMin = 1200;
        } else {
            try {
                iMin = Math.min(d, ((int) Math.pow(4.0d, i2 - 1)) * 10);
                try {
                    iMin2 = Math.min(d, ((int) Math.pow(4.0d, i2)) * 10);
                } catch (Exception e2) {
                    e = e2;
                    i3 = 0;
                    s.a(a, "getRetryInterval exception maxIntervalSec：" + iNextInt + "_minIntervalSec:" + iMin + "_randomIntervalSec:" + i3);
                    StringBuilder sb = new StringBuilder();
                    sb.append("getRetryInterval exception：");
                    sb.append(e);
                    s.a(a, sb.toString());
                    return i3;
                }
            } catch (Exception e3) {
                e = e3;
                iMin = 0;
                i3 = 0;
            }
        }
        try {
            iNextInt = new Random().nextInt((iMin2 - iMin) + 1) + iMin;
            s.a(a, "getRetryInterval failedCount：" + i2 + " ,randomIntervalSec:" + iNextInt);
            return iNextInt;
        } catch (Exception e4) {
            e = e4;
            int i4 = iMin2;
            i3 = iNextInt;
            iNextInt = i4;
            s.a(a, "getRetryInterval exception maxIntervalSec：" + iNextInt + "_minIntervalSec:" + iMin + "_randomIntervalSec:" + i3);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("getRetryInterval exception：");
            sb2.append(e);
            s.a(a, sb2.toString());
            return i3;
        }
    }

    public static b a() {
        if (n == null) {
            synchronized (b.class) {
                try {
                    if (n == null) {
                        n = new b();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return n;
    }

    private void g() {
        try {
            if (this.o == null) {
                this.o = new JSONObject();
            }
            long j2 = h;
            if (j2 == k && i == l && j == m) {
                return;
            }
            this.o.put(e, j2);
            this.o.put(f, i);
            this.o.put(g, j);
            ad.r(this.o.toString());
            k = h;
            l = i;
            m = j;
        } catch (Exception unused) {
        }
    }

    public boolean b() {
        boolean z = true;
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            long j2 = h;
            if (jCurrentTimeMillis < j2 && j2 - jCurrentTimeMillis <= 1800000) {
                z = false;
                s.b(a, "not allow update,  nextRequestTime：" + h);
                return false;
            }
            return true;
        } catch (Exception e2) {
            s.b(a, "checkAllowUpdate check error：", e2);
            return z;
        }
    }

    public void c() {
        i++;
        h = System.currentTimeMillis() + (((long) a(i)) * 1000);
        s.a(a, "addFiledCount nextRequestTime：" + h);
        g();
    }

    public void d() {
        if (j >= 2) {
            return;
        }
        i = 0;
        h = 0L;
        g();
    }

    public void e() {
        int i2 = j + 1;
        j = i2;
        if (i2 >= 2) {
            c();
        }
        g();
    }

    public void f() {
        i = 0;
        j = 0;
        h = 0L;
        g();
    }
}
