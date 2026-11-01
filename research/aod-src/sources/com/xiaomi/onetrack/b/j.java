package com.xiaomi.onetrack.b;

import android.content.ContentValues;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.onetrack.util.s;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class j {
    private static final String a = "ConfigDbManager";
    private static final String b = "quality";
    private static final int f = 100;
    private i c;
    private ConcurrentHashMap<String, m> d;
    private ConcurrentHashMap<String, Boolean> e;

    public static class a {
        private static final j a = new j(null);

        private a() {
        }
    }

    private j() {
        this.d = new ConcurrentHashMap<>();
        this.e = new ConcurrentHashMap<>();
        this.c = new i(com.xiaomi.onetrack.f.a.a());
    }

    public /* synthetic */ j(k kVar) {
        this();
    }

    public static j a() {
        return a.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int b(JSONObject jSONObject) {
        try {
            int iOptInt = jSONObject.optInt(com.xiaomi.onetrack.b.a.e, 100);
            if (iOptInt < 0 || iOptInt > 100) {
                return 100;
            }
            return iOptInt;
        } catch (Exception e) {
            s.a(a, "getCommonSample Exception:" + e.getMessage());
            return 100;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(ArrayList<m> arrayList) throws Throwable {
        StringBuilder sb;
        SQLiteDatabase sQLiteDatabase = null;
        try {
            try {
                SQLiteDatabase writableDatabase = this.c.getWritableDatabase();
                try {
                    writableDatabase.beginTransaction();
                    for (m mVar : arrayList) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("app_id", mVar.a);
                        contentValues.put("timestamp", Long.valueOf(mVar.c));
                        JSONObject jSONObject = mVar.e;
                        if (jSONObject != null) {
                            contentValues.put(i.e, jSONObject.toString());
                        }
                        String str = mVar.d;
                        if (str != null) {
                            contentValues.put(i.f, str);
                        }
                        if (DatabaseUtils.queryNumEntries(writableDatabase, i.b, "app_id=?", new String[]{mVar.a}) > 0) {
                            s.a(a, "database updated, row: " + writableDatabase.update(i.b, contentValues, "app_id=?", new String[]{mVar.a}));
                        } else {
                            s.a(a, "database inserted, row: " + writableDatabase.insert(i.b, null, contentValues));
                        }
                        this.e.put(mVar.a, Boolean.TRUE);
                    }
                    writableDatabase.setTransactionSuccessful();
                    try {
                        writableDatabase.endTransaction();
                    } catch (Exception e) {
                        e = e;
                        sb = new StringBuilder();
                        sb.append("Exception while endTransaction:");
                        sb.append(e);
                        s.b(a, sb.toString());
                    }
                } catch (Exception e2) {
                    e = e2;
                    sQLiteDatabase = writableDatabase;
                    s.b(a, "updateToDb error: ", e);
                    if (sQLiteDatabase != null) {
                        try {
                            sQLiteDatabase.endTransaction();
                        } catch (Exception e3) {
                            e = e3;
                            sb = new StringBuilder();
                            sb.append("Exception while endTransaction:");
                            sb.append(e);
                            s.b(a, sb.toString());
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    sQLiteDatabase = writableDatabase;
                    if (sQLiteDatabase != null) {
                        try {
                            sQLiteDatabase.endTransaction();
                        } catch (Exception e4) {
                            s.b(a, "Exception while endTransaction:" + e4);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e5) {
            e = e5;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(ArrayList<m> arrayList) {
        JSONObject jSONObjectOptJSONObject;
        for (m mVar : arrayList) {
            JSONObject jSONObject = mVar.e;
            if (jSONObject != null && (jSONObjectOptJSONObject = jSONObject.optJSONObject(b)) != null) {
                com.xiaomi.onetrack.h.a.a.a().a(mVar.a, jSONObjectOptJSONObject);
            }
        }
    }

    public double a(String str, String str2, String str3, double d) {
        try {
            JSONObject jSONObjectA = a(str, str2);
            if (jSONObjectA != null) {
                return jSONObjectA.getDouble(str3);
            }
            s.a(a, "config not available, use default value");
            return d;
        } catch (Exception e) {
            s.b(a, "getDouble: " + e.toString());
            return d;
        }
    }

    public int a(String str, String str2, String str3, int i) {
        try {
            JSONObject jSONObjectA = a(str, str2);
            if (jSONObjectA != null) {
                return jSONObjectA.getInt(str3);
            }
            s.a(a, "config not available, use default value");
            return i;
        } catch (Exception e) {
            s.b(a, "getInt: " + e.toString());
            return i;
        }
    }

    public long a(String str, String str2, String str3, long j) {
        try {
            JSONObject jSONObjectA = a(str, str2);
            if (jSONObjectA != null) {
                return jSONObjectA.getLong(str3);
            }
            s.a(a, "config not available, use default value");
            return j;
        } catch (Exception e) {
            s.b(a, "getLong: " + e.toString());
            return j;
        }
    }

    public long a(JSONObject jSONObject, String str, String str2) {
        int iOptInt;
        int iOptInt2;
        m mVar;
        if (TextUtils.isEmpty(str)) {
            return 100L;
        }
        try {
            a(str);
            if (this.d.get(str) != null) {
                if (jSONObject != null) {
                    iOptInt2 = jSONObject.has(com.xiaomi.onetrack.b.a.l) ? jSONObject.optInt(com.xiaomi.onetrack.b.a.l, -1) : -1;
                    iOptInt = jSONObject.has(com.xiaomi.onetrack.b.a.e) ? jSONObject.optInt(com.xiaomi.onetrack.b.a.e, -1) : -1;
                } else {
                    iOptInt = -1;
                    iOptInt2 = -1;
                }
                if (iOptInt2 != -1) {
                    iOptInt = iOptInt2;
                } else if (iOptInt == -1) {
                    iOptInt = -1;
                }
                if (iOptInt != -1 || (mVar = this.d.get(str)) == null) {
                    s.a(a, "will return event sample " + iOptInt);
                    return iOptInt;
                }
                s.a(a, "will return common sample " + mVar.b);
                return mVar.b;
            }
        } catch (Exception e) {
            s.b(a, "getAppEventSample" + e.toString());
        }
        s.a(a, "will return def sample");
        return 100L;
    }

    public String a(String str, String str2, String str3, String str4) {
        try {
            JSONObject jSONObjectA = a(str, str2);
            if (jSONObjectA != null) {
                return jSONObjectA.getString(str3);
            }
            s.a(a, "config not available, use default value");
            return str4;
        } catch (Exception e) {
            s.b(a, "getString: " + e.toString());
            return str4;
        }
    }

    public JSONObject a(String str, String str2) {
        JSONObject jSONObject;
        JSONArray jSONArrayOptJSONArray;
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                a(str);
                m mVar = this.d.get(str);
                if (mVar != null && (jSONObject = mVar.e) != null && (jSONArrayOptJSONArray = jSONObject.optJSONArray("events")) != null) {
                    for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                        JSONObject jSONObject2 = jSONArrayOptJSONArray.getJSONObject(i);
                        if (TextUtils.equals(str2, jSONObject2.optString("event"))) {
                            if (!s.a) {
                                return jSONObject2;
                            }
                            s.a(a, "getEventConfig:" + jSONObject2.toString());
                            return jSONObject2;
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(a, "getEventConfig error: " + e.toString());
            }
        }
        return null;
    }

    public synchronized void a(String str) {
        if (!this.e.containsKey(str) || this.e.get(str).booleanValue() || this.d.get(str) == null) {
            FutureTask futureTask = new FutureTask(new l(this, str));
            com.xiaomi.onetrack.c.d.a(futureTask);
            try {
                m mVar = (m) futureTask.get(5L, TimeUnit.SECONDS);
                if (mVar != null) {
                    this.d.put(str, mVar);
                    this.e.put(str, Boolean.FALSE);
                }
            } catch (Exception e) {
                s.b(a, "getConfig error: " + e.toString());
            }
        }
    }

    public void a(ArrayList<m> arrayList) {
        com.xiaomi.onetrack.c.d.a(new k(this, arrayList));
    }

    public boolean a(String str, String str2, String str3, boolean z) {
        try {
            JSONObject jSONObjectA = a(str, str2);
            if (jSONObjectA != null) {
                return jSONObjectA.getBoolean(str3);
            }
            s.a(a, "config not available, use default value");
            return z;
        } catch (Exception e) {
            s.b(a, "getBoolean: " + e.toString());
            return z;
        }
    }

    public String b(String str) {
        JSONObject jSONObject;
        m mVarF = f(str);
        return (mVarF == null || (jSONObject = mVarF.e) == null) ? "" : jSONObject.optString(com.xiaomi.onetrack.b.a.g);
    }

    public boolean b(String str, String str2) {
        JSONObject jSONObject;
        try {
            m mVarF = f(str);
            if (mVarF == null || (jSONObject = mVarF.e) == null || !jSONObject.has(str2)) {
                return false;
            }
            return mVarF.e.optBoolean(str2);
        } catch (Exception e) {
            s.b(a, "getAppLevelBoolean" + e.toString());
            return false;
        }
    }

    public String c(String str) {
        m mVarF = f(str);
        return mVarF != null ? mVarF.d : "";
    }

    public int d(String str) {
        JSONObject jSONObject;
        m mVarF = f(str);
        if (mVarF == null || (jSONObject = mVarF.e) == null) {
            return 0;
        }
        return jSONObject.optInt("version");
    }

    public int e(String str) {
        JSONObject jSONObject;
        try {
            m mVarF = f(str);
            if (mVarF != null && (jSONObject = mVarF.e) != null && jSONObject.has("nvc")) {
                return mVarF.e.optInt("nvc", 0);
            }
        } catch (Exception e) {
            s.b(a, "getNewVersionConfig error: " + e.getMessage());
        }
        return 0;
    }

    public m f(String str) {
        s.a(a, "getAppConfigData start, appId: " + str);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            a(str);
        } catch (Exception e) {
            s.b(a, "getConfig error: " + e.getMessage());
        }
        return this.d.get(str);
    }

    public JSONObject g(String str) {
        JSONObject jSONObject;
        JSONObject jSONObject2 = new JSONObject();
        m mVarF = f(str);
        return (mVarF == null || (jSONObject = mVarF.e) == null) ? jSONObject2 : jSONObject.optJSONObject(b);
    }
}
