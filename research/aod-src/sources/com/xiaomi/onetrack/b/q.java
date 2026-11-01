package com.xiaomi.onetrack.b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Process;
import android.text.TextUtils;
import com.xiaomi.onetrack.util.aa;
import com.xiaomi.onetrack.util.ac;
import com.xiaomi.onetrack.util.s;
import com.xiaomi.onetrack.util.t;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes2.dex */
public class q {
    private static final String a = "PubSubConfigManager";
    private static volatile q d;
    private ConcurrentHashMap<String, p> b = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> c = new ConcurrentHashMap<>();
    private a e = new a(com.xiaomi.onetrack.f.a.a());

    public static class a extends SQLiteOpenHelper {
        public static final String a = "ot_ps_project_info";
        public static final String b = "project_info";
        public static final String c = "_id";
        public static final String d = "appId";
        public static final String e = "projectId";
        public static final String f = "privateKeyId";
        public static final String g = "topics";
        public static final String h = "tokenBean";
        public static final String i = "timestamp";
        private static final int j = 1;
        private static final String k = "CREATE TABLE project_info (_id INTEGER PRIMARY KEY AUTOINCREMENT,appId TEXT,projectId TEXT,privateKeyId TEXT,topics BLOB,tokenBean TEXT,timestamp INTEGER)";

        public a(Context context) {
            super(context, a, (SQLiteDatabase.CursorFactory) null, 1);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(k);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
        }
    }

    private q() {
    }

    public static q a() {
        if (d == null) {
            synchronized (q.class) {
                try {
                    if (d == null) {
                        d = new q();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return d;
    }

    /* JADX WARN: Code duplicated, block: B:68:0x00e4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v23 */
    private void a(String str, p pVar) {
        Throwable th;
        ?? r8;
        Exception e;
        SQLiteDatabase writableDatabase;
        String str2;
        String str3;
        synchronized (this.e) {
            try {
                if (TextUtils.isEmpty(str) || pVar == null || TextUtils.isEmpty(pVar.b)) {
                    return;
                }
                try {
                    try {
                        writableDatabase = this.e.getWritableDatabase();
                        try {
                            writableDatabase.beginTransaction();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("appId", pVar.a);
                            contentValues.put(a.e, pVar.b);
                            contentValues.put(a.f, pVar.c);
                            contentValues.put("timestamp", Long.valueOf(pVar.f));
                            List<String> list = pVar.d;
                            if (list != null) {
                                contentValues.put(a.g, com.xiaomi.onetrack.util.d.a(list));
                            }
                            String str4 = pVar.e;
                            if (str4 != null) {
                                contentValues.put(a.h, str4);
                            }
                            if (DatabaseUtils.queryNumEntries(writableDatabase, a.b, "projectId=?", new String[]{pVar.b}) > 0) {
                                writableDatabase.update(a.b, contentValues, "projectId=?", new String[]{pVar.b});
                            } else {
                                writableDatabase.insert(a.b, null, contentValues);
                            }
                            writableDatabase.setTransactionSuccessful();
                            try {
                                writableDatabase.endTransaction();
                            } catch (Exception e2) {
                                str2 = a;
                                str3 = "addProjectBeanToDB Exception while endTransaction:" + e2.getMessage();
                                s.b(str2, str3);
                            }
                        } catch (Exception e3) {
                            e = e3;
                            s.b(a, "updateToDb error: ", e);
                            if (writableDatabase != null) {
                                try {
                                    writableDatabase.endTransaction();
                                } catch (Exception e4) {
                                    str2 = a;
                                    str3 = "addProjectBeanToDB Exception while endTransaction:" + e4.getMessage();
                                    s.b(str2, str3);
                                }
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        this = this;
                        if (r8 != 0) {
                            try {
                                r8.endTransaction();
                            } catch (Exception e5) {
                                s.b(a, "addProjectBeanToDB Exception while endTransaction:" + e5.getMessage());
                            }
                        }
                        throw th;
                    }
                } catch (Exception e6) {
                    e = e6;
                    writableDatabase = null;
                } catch (Throwable th3) {
                    th = th3;
                    r8 = 0;
                    if (r8 != 0) {
                        r8.endTransaction();
                    }
                    throw th;
                }
            } catch (Exception e7) {
                s.a(a, "addProjectBeanToDB Exception:" + e7.getMessage());
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:71:0x0118 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:74:? A[Catch: all -> 0x00c2, SYNTHETIC, TryCatch #0 {, blocks: (B:28:0x00c0, B:27:0x00a6, B:50:0x0114, B:36:0x00cc, B:37:0x00e3, B:58:0x0138, B:57:0x011e, B:49:0x00fc, B:23:0x00a0, B:45:0x00f6, B:32:0x00c6, B:53:0x0118), top: B:61:0x0004, inners: #1, #6, #7, #8 }] */
    private p b(String str, String str2) {
        Throwable th;
        Exception exc;
        Cursor cursorQuery;
        String str3;
        String str4;
        List<String> list;
        synchronized (this.e) {
            Cursor cursor = null;
            try {
                cursorQuery = this.e.getWritableDatabase().query(a.b, null, str + "=?", new String[]{str2}, null, null, null);
                try {
                    try {
                        int columnIndex = cursorQuery.getColumnIndex(a.e);
                        int columnIndex2 = cursorQuery.getColumnIndex("appId");
                        int columnIndex3 = cursorQuery.getColumnIndex(a.f);
                        int columnIndex4 = cursorQuery.getColumnIndex(a.g);
                        int columnIndex5 = cursorQuery.getColumnIndex(a.h);
                        int columnIndex6 = cursorQuery.getColumnIndex("timestamp");
                        if (!cursorQuery.moveToNext()) {
                            try {
                                cursorQuery.close();
                            } catch (Throwable th2) {
                                str3 = a;
                                str4 = "closeSafely e: " + th2.getMessage();
                                s.b(str3, str4);
                            }
                            return null;
                        }
                        p pVar = new p();
                        pVar.b = cursorQuery.getString(columnIndex);
                        pVar.a = cursorQuery.getString(columnIndex2);
                        pVar.c = cursorQuery.getString(columnIndex3);
                        Object objA = com.xiaomi.onetrack.util.d.a(cursorQuery.getBlob(columnIndex4));
                        if ((objA instanceof List) && (list = (List) objA) != null && !list.isEmpty()) {
                            pVar.d = list;
                        }
                        String string = cursorQuery.getString(columnIndex5);
                        if (ac.b(string)) {
                            pVar.e = string;
                        }
                        pVar.f = cursorQuery.getLong(columnIndex6);
                        try {
                            cursorQuery.close();
                        } catch (Throwable th3) {
                            s.b(a, "closeSafely e: " + th3.getMessage());
                        }
                        return pVar;
                    } catch (Exception e) {
                        exc = e;
                        s.a(a, "exception while getConfig", exc);
                        if (cursorQuery != null) {
                            try {
                                cursorQuery.close();
                            } catch (Throwable th4) {
                                str3 = a;
                                str4 = "closeSafely e: " + th4.getMessage();
                                s.b(str3, str4);
                            }
                        }
                    }
                } catch (Throwable th5) {
                    th = th5;
                    cursor = cursorQuery;
                    if (cursor != null) {
                        throw th;
                    }
                    try {
                        cursor.close();
                        throw th;
                    } catch (Throwable th6) {
                        s.b(a, "closeSafely e: " + th6.getMessage());
                        throw th;
                    }
                }
            } catch (Exception e2) {
                exc = e2;
                cursorQuery = null;
            } catch (Throwable th7) {
                th = th7;
                if (cursor != null) {
                    throw th;
                }
                cursor.close();
                throw th;
            }
            throw th;
        }
    }

    private void b(String str, p pVar) {
        String str2;
        String str3;
        synchronized (this.e) {
            try {
                if (TextUtils.isEmpty(str) || pVar == null || TextUtils.isEmpty(pVar.b)) {
                    return;
                }
                SQLiteDatabase writableDatabase = null;
                try {
                    try {
                        writableDatabase = this.e.getWritableDatabase();
                        writableDatabase.beginTransaction();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("timestamp", Long.valueOf(pVar.f));
                        contentValues.put(a.h, pVar.e);
                        if (DatabaseUtils.queryNumEntries(writableDatabase, a.b, "projectId=?", new String[]{pVar.b}) > 0) {
                            writableDatabase.update(a.b, contentValues, "projectId=?", new String[]{pVar.b});
                        }
                        if (s.a) {
                            s.a(a, "forceRefreshTokenToDB: " + contentValues.toString() + "tid:" + Process.myTid());
                        }
                        writableDatabase.setTransactionSuccessful();
                        try {
                            writableDatabase.endTransaction();
                        } catch (Throwable th) {
                            str2 = a;
                            str3 = "forceRefreshTokenToDB endTransactionSafely e: " + th.getMessage();
                            s.b(str2, str3);
                        }
                    } catch (Exception e) {
                        s.b(a, "updateToDb error: ", e);
                        if (writableDatabase != null) {
                            try {
                                writableDatabase.endTransaction();
                            } catch (Throwable th2) {
                                str2 = a;
                                str3 = "forceRefreshTokenToDB endTransactionSafely e: " + th2.getMessage();
                                s.b(str2, str3);
                            }
                        }
                    }
                } catch (Throwable th3) {
                    if (writableDatabase != null) {
                        try {
                            writableDatabase.endTransaction();
                        } catch (Throwable th4) {
                            s.b(a, "forceRefreshTokenToDB endTransactionSafely e: " + th4.getMessage());
                        }
                    }
                    throw th3;
                }
            } catch (Exception e2) {
                s.a(a, "addProjectBeanToDB Exception:" + e2.getMessage());
            }
        }
    }

    private p d() {
        a().a(com.xiaomi.onetrack.util.a.j, "onetrack_active", com.xiaomi.onetrack.util.a.l, com.xiaomi.onetrack.util.a.m);
        return b(a.e, com.xiaomi.onetrack.util.a.j);
    }

    public p a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        p pVar = this.b.get(str);
        return pVar == null ? b(a.e, str) : pVar;
    }

    public com.xiaomi.onetrack.i.a a(String str, String str2) {
        com.xiaomi.onetrack.i.a aVar = null;
        try {
            if (!t.a(a) && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && com.xiaomi.onetrack.i.b.a().b()) {
                HashMap map = new HashMap();
                map.put("timestamp", String.valueOf(System.currentTimeMillis()));
                String strA = com.xiaomi.onetrack.g.b.a(str2, aa.a().g(), map);
                com.xiaomi.onetrack.i.a aVarA = com.xiaomi.onetrack.i.a.a(strA);
                if (aVarA != null) {
                    try {
                        if (aVarA.a == 0) {
                            s.a(a, "getToken success");
                            com.xiaomi.onetrack.i.b.a().d();
                            return aVarA;
                        }
                    } catch (Exception e) {
                        e = e;
                        aVar = aVarA;
                        com.xiaomi.onetrack.i.b.a().c();
                        s.a(a, "getNewToken e:" + e.getMessage());
                        return aVar;
                    }
                }
                if (aVarA == null || aVarA.a != -10) {
                    com.xiaomi.onetrack.i.b.a().c();
                    s.a(a, "postGetToken responseData response:" + strA);
                    return null;
                }
                com.xiaomi.onetrack.i.b.a().c();
                s.a(a, "postGetToken invalid ,response:" + strA);
                this.b.remove(str);
                c(str);
                return null;
            }
            return null;
        } catch (Exception e2) {
            e = e2;
        }
    }

    public void a(p pVar) {
        if (pVar == null || TextUtils.isEmpty(pVar.b)) {
            return;
        }
        this.c.put(pVar.a, pVar.b);
        this.b.put(pVar.b, pVar);
        a(pVar.b, pVar);
    }

    public void a(String str, String str2, String str3, String str4) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            return;
        }
        p pVarA = a(str);
        if (pVarA == null) {
            p pVar = new p();
            pVar.b = str;
            pVar.c = str3;
            pVar.d.add(str2);
            pVar.a = str4;
            pVar.f = System.currentTimeMillis();
            a(pVar);
            return;
        }
        if (str.equals(pVarA.b) && str3.equals(pVarA.c) && !pVarA.d.contains(str2)) {
            pVarA.d.add(str2);
            pVarA.f = System.currentTimeMillis();
            a(pVarA);
        }
    }

    public boolean a(com.xiaomi.onetrack.i.a aVar) {
        if (aVar == null) {
            return false;
        }
        try {
            if (!TextUtils.isEmpty(aVar.d) && aVar.e > 0) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                int iNextInt = aVar.e - new Random().nextInt(aVar.e / 14);
                long j = ((jCurrentTimeMillis - aVar.f) / 1000) + 180;
                s.a(a, "interval:" + j + "_currentTimeMillis:" + jCurrentTimeMillis + "_token.local_time:" + aVar.f);
                if (j >= iNextInt) {
                    s.a(a, "interval:" + j + "_localExpiresTime:" + iNextInt + "_isValid:false");
                    return false;
                }
                s.a(a, "interval:" + j + "_localExpiresTime:" + iNextInt + "_isValid:true");
                return true;
            }
            return false;
        } catch (Exception e) {
            s.b(a, "isLocalTokenValid Exception:" + e.getMessage());
        }
    }

    public p b(String str) {
        String str2 = this.c.get(str);
        return ac.b(str2) ? a(str2) : b("appId", str);
    }

    public com.xiaomi.onetrack.i.a b() {
        com.xiaomi.onetrack.i.a aVarA;
        if (this.b.get(com.xiaomi.onetrack.util.a.j) != null) {
            aVarA = this.b.get(com.xiaomi.onetrack.util.a.j).a();
            if (a(aVarA)) {
                s.a(a, "getTokenBean memory cache is valid");
                return aVarA;
            }
        } else {
            aVarA = null;
        }
        p pVarB = b(a.e, com.xiaomi.onetrack.util.a.j);
        if (pVarB == null) {
            pVarB = d();
        }
        if (aVarA == null) {
            com.xiaomi.onetrack.i.a aVarA2 = pVarB.a();
            if (a(aVarA2)) {
                this.b.put(com.xiaomi.onetrack.util.a.j, pVarB);
                s.a(a, "getTokenBean db cache is valid");
                return aVarA2;
            }
        }
        com.xiaomi.onetrack.i.a aVarA3 = a(com.xiaomi.onetrack.util.a.j, com.xiaomi.onetrack.util.a.l);
        if (!a(aVarA3)) {
            return null;
        }
        pVarB.a(aVarA3);
        a(pVarB);
        return aVarA3;
    }

    public com.xiaomi.onetrack.i.a c() {
        try {
            p pVarB = b(a.e, com.xiaomi.onetrack.util.a.j);
            if (pVarB == null) {
                pVarB = d();
            }
            com.xiaomi.onetrack.i.a aVarA = a(com.xiaomi.onetrack.util.a.j, pVarB.c);
            if (a(aVarA)) {
                pVarB.a(aVarA);
                a(pVarB);
                return aVarA;
            }
            pVarB.b();
            this.b.put(com.xiaomi.onetrack.util.a.j, pVarB);
            b(com.xiaomi.onetrack.util.a.j, pVarB);
            return aVarA;
        } catch (Exception e) {
            s.b(a, "forceRefreshToken Exception：" + e.getMessage());
            return null;
        }
    }

    public void c(String str) {
        synchronized (this.e) {
            try {
                this.e.getWritableDatabase().delete(a.b, "projectId=?", new String[]{str});
            } catch (Exception e) {
                s.d(a, "delete project id info failed with " + e);
            }
        }
    }
}
