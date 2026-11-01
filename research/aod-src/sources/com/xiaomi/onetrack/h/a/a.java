package com.xiaomi.onetrack.h.a;

import android.net.Uri;
import android.text.TextUtils;
import com.xiaomi.onetrack.api.am;
import com.xiaomi.onetrack.b.j;
import com.xiaomi.onetrack.util.ad;
import com.xiaomi.onetrack.util.af;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.s;
import com.xiaomi.onetrack.util.u;
import com.xiaomi.onetrack.util.y;
import java.io.Closeable;
import java.util.HashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class a {
    private static final String a = "OTMonitorConfigManager";
    private static final String b = "content://com.miui.analytics.OneTrackProvider/monitorConfig";
    private static final String c = "monitorConfig";
    private static final String d = "appId";
    private static final String e = "pkg";
    private static final String f = "sign";
    private static final String g = "dl";
    private static final String h = "monitor";
    private static final String i = "failed";
    private static final String j = "last_update_time";
    private static final int k = 1000;
    private static final int l = 100;
    private static final int m = 10;
    private static a r;
    private HashMap<String, Boolean> n = new HashMap<>();
    private HashMap<String, y> o = new HashMap<>();
    private HashMap<String, Boolean> p = new HashMap<>();
    private HashMap<String, Boolean> q = new HashMap<>();

    public static a a() {
        if (r == null) {
            synchronized (a.class) {
                try {
                    if (r == null) {
                        r = new a();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return r;
    }

    private boolean a(JSONObject jSONObject) {
        if (jSONObject != null && jSONObject.length() != 0) {
            try {
                if (System.currentTimeMillis() - jSONObject.getLong(j) < 1800000) {
                    return false;
                }
            } catch (Exception e2) {
                s.b(a, "shouldGetRemoteMonitorConfig error: " + e2);
            }
        }
        return true;
    }

    private boolean b(String str) {
        long jN = ad.n(str);
        if (af.b() == jN / 100) {
            return ((int) (jN % 100)) <= 10;
        }
        ad.a(str, af.b() * 100);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r9v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v10, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r9v11 */
    /* JADX WARN: Type inference failed for: r9v12 */
    /* JADX WARN: Type inference failed for: r9v13 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v6, types: [java.io.Closeable] */
    private synchronized String c(String str) {
        String string;
        ?? r1 = 0;
        string = null;
        string = null;
        string = null;
        try {
            try {
                try {
                    Uri.Builder builderBuildUpon = Uri.parse(b).buildUpon();
                    builderBuildUpon.appendQueryParameter("appId", str);
                    builderBuildUpon.appendQueryParameter(e, com.xiaomi.onetrack.f.a.e());
                    builderBuildUpon.appendQueryParameter(f, com.xiaomi.onetrack.d.a.a(c + com.xiaomi.onetrack.f.a.e() + ((String) str)));
                    str = com.xiaomi.onetrack.f.a.a().getContentResolver().query(builderBuildUpon.build(), null, null, null, null);
                    if (str != 0) {
                        try {
                            boolean zMoveToFirst = str.moveToFirst();
                            str = str;
                            if (zMoveToFirst) {
                                string = str.getString(0);
                                str = str;
                            }
                        } catch (Exception e2) {
                            e = e2;
                            s.b(a, "getRemoteMonitorConfig error", e);
                            str = str;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                str = 0;
            } catch (Throwable th2) {
                th = th2;
                p.b((Closeable) r1);
                throw th;
            }
            p.b((Closeable) str);
        } catch (Throwable th3) {
            th = th3;
            r1 = str;
        }
        return string;
    }

    private void d(String str) {
        long jN = ad.n(str);
        if (af.b() == jN / 100) {
            ad.a(str, jN + 1);
        } else {
            ad.a(str, af.b() * 100);
        }
    }

    private boolean d(String str, String str2) {
        String strP = ad.p(str);
        if (TextUtils.isEmpty(strP)) {
            e(str);
            return true;
        }
        try {
            JSONObject jSONObject = new JSONObject(strP);
            if (af.b() == jSONObject.optLong("reportTime")) {
                return jSONObject.optInt("total") > 0 && jSONObject.optJSONObject("events").optInt(str2, 100) > 0;
            }
            e(str);
            return true;
        } catch (Exception e2) {
            s.b(a, "hasReportOTFailedTicket error: ", e2);
            return false;
        }
    }

    private void e(String str) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("reportTime", af.b());
            jSONObject.put("total", 1000);
            jSONObject.put("events", new JSONObject());
            ad.b(str, jSONObject.toString());
        } catch (Exception e2) {
            s.b(a, "setDefaultReportOTFailedTicket error: ", e2);
        }
    }

    public void a(String str) {
        JSONObject jSONObjectG;
        try {
            if (this.n.get(str).booleanValue()) {
                String strO = ad.o(str);
                jSONObjectG = !TextUtils.isEmpty(strO) ? new JSONObject(strO) : null;
                if (b(str) && a(jSONObjectG)) {
                    String strC = c(str);
                    s.a(a, "checkUpdateMonitorConfig, remoteConfig: " + strC + ", cachedConfig: " + strO);
                    if (!TextUtils.isEmpty(strC) && !strC.equals(strO)) {
                        jSONObjectG = new JSONObject(strC);
                        jSONObjectG.put(j, System.currentTimeMillis());
                        ad.a(str, jSONObjectG.toString());
                    }
                    d(str);
                }
            } else {
                jSONObjectG = j.a().g(str);
            }
            if (jSONObjectG == null || jSONObjectG.length() <= 0) {
                return;
            }
            a(str, jSONObjectG);
        } catch (Exception e2) {
            s.b(a, "checkUpdateMonitorConfig error: " + e2);
        }
    }

    public void a(String str, y yVar, boolean z) {
        JSONObject jSONObjectG;
        this.n.put(str, Boolean.valueOf(z));
        this.o.put(str, yVar);
        try {
            if (this.n.get(str).booleanValue()) {
                String strO = ad.o(str);
                jSONObjectG = !TextUtils.isEmpty(strO) ? new JSONObject(strO) : null;
            } else {
                jSONObjectG = j.a().g(str);
            }
            if (jSONObjectG == null || jSONObjectG.length() <= 0) {
                return;
            }
            a(str, jSONObjectG);
        } catch (Exception e2) {
            s.b(a, "loadLocalMonitorConfig error: " + e2);
        }
    }

    public void a(String str, JSONObject jSONObject) {
        boolean z;
        try {
            long jA = af.a(jSONObject.optString(g));
            boolean z2 = true;
            if (jA == -1) {
                z = jSONObject.optInt("monitor", 0) == 1;
                if (jSONObject.optInt(i, 0) != 1) {
                    z2 = false;
                }
            } else {
                boolean z3 = jA >= System.currentTimeMillis() && jSONObject.optInt("monitor", 0) == 1;
                if (jA < System.currentTimeMillis() || jSONObject.optInt(i, 0) != 1) {
                    z2 = false;
                }
                z = z3;
            }
            this.p.put(str, Boolean.valueOf(z));
            this.q.put(str, Boolean.valueOf(z2));
            s.a(a, "updateCachedMonitorConfig, shouldRecordAppStats: " + z + ", shouldRecordEventFailed: " + z2);
            if (z2) {
                return;
            }
            ad.q(str);
        } catch (Exception e2) {
            s.b(a, "checkUpdateMonitorConfig error" + e2.getMessage());
        }
    }

    public boolean a(String str, String str2) {
        if (u.b(str2)) {
            s.a(a, "shouldRecordStats, not record ot_quality event");
            return false;
        }
        if (this.o.get(str) != null && this.o.get(str).a(am.f)) {
            return this.p.get(str) != null && this.p.get(str).booleanValue();
        }
        s.a(a, "shouldRecordStats, privacy not permitted");
        return false;
    }

    public void b(String str, y yVar, boolean z) {
        this.n.put(str, Boolean.valueOf(z));
        this.o.put(str, yVar);
        a(str);
    }

    public boolean b(String str, String str2) {
        if (u.b(str2)) {
            s.a(a, "shouldRecordEventFailed, not record ot_quality event");
            return false;
        }
        if (this.o.get(str) == null || !this.o.get(str).a(am.g)) {
            s.a(a, "shouldRecordEventFailed, privacy not permitted");
            return false;
        }
        if (this.q.get(str) == null || !this.q.get(str).booleanValue()) {
            s.a(a, "shouldRecordEventFailed, quality config not permitted");
            return false;
        }
        if (d(str, str2)) {
            return true;
        }
        s.a(a, "shouldRecordEventFailed, no ticket");
        return false;
    }

    public void c(String str, String str2) {
        try {
            String strP = ad.p(str);
            if (TextUtils.isEmpty(strP)) {
                return;
            }
            JSONObject jSONObject = new JSONObject(strP);
            if (jSONObject.optLong("reportTime") != af.b()) {
                return;
            }
            int iOptInt = jSONObject.optInt("total");
            jSONObject.put("total", iOptInt > 0 ? iOptInt - 1 : 0);
            int iOptInt2 = jSONObject.optJSONObject("events").optInt(str2, -1);
            if (iOptInt2 == -1) {
                jSONObject.getJSONObject("events").put(str2, 99);
            } else {
                jSONObject.getJSONObject("events").put(str2, iOptInt2 > 0 ? iOptInt2 - 1 : 0);
            }
            ad.b(str, jSONObject.toString());
        } catch (Exception e2) {
            s.b(a, "updateReportOTFailedTicket error: ", e2);
        }
    }
}
