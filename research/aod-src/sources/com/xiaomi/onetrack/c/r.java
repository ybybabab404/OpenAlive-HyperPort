package com.xiaomi.onetrack.c;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteBlobTooBigException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.xiaomi.onetrack.api.an;
import com.xiaomi.onetrack.api.be;
import java.io.Closeable;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class r {
    public static final int a = 104857600;
    private static final String b = "SystemImpCacheManager";
    private static final String c = "systemimp_cache";
    private static String d = "systemimp_cache_%s";
    private static volatile r e = null;
    private static String f = "system_imp_cache_manager";
    private static final int h = 307200;
    private static final String j = "_id ASC";
    private static final int k = 100;
    private static final int l = 7;
    private Handler g;
    private a i;
    private final AtomicLong m;

    public static class a extends SQLiteOpenHelper {
        public static final String a = "onetrack_cache";
        public static final String b = "events";
        public static final String c = "_id";
        public static final String d = "appid";
        public static final String e = "projectid";
        public static final String f = "topic";
        public static final String g = "event_name";
        public static final String h = "data";
        public static final String i = "attribute";
        public static final String j = "timestamp";
        private static final String k = "drop table if exists %s";
        private static final int l = 2;
        private static final String m = "CREATE TABLE events (_id INTEGER PRIMARY KEY AUTOINCREMENT,appid TEXT,projectid TEXT,topic TEXT,event_name TEXT,data TEXT,attribute TEXT,timestamp INTEGER)";

        public a(Context context) {
            super(context, a, (SQLiteDatabase.CursorFactory) null, 2);
        }

        private void a(SQLiteDatabase sQLiteDatabase) {
            try {
                sQLiteDatabase.execSQL("ALTER TABLE events ADD COLUMN projectid TEXT;");
                sQLiteDatabase.execSQL("ALTER TABLE events ADD COLUMN topic TEXT;");
            } catch (Throwable th) {
                com.xiaomi.onetrack.util.s.b(r.b, "addPBColumn error:" + th.getMessage());
                th.printStackTrace();
            }
        }

        private void b(SQLiteDatabase sQLiteDatabase) {
            try {
                sQLiteDatabase.execSQL(m);
            } catch (Throwable th) {
                com.xiaomi.onetrack.util.s.b(r.b, "createTable error:" + th.getMessage());
                th.printStackTrace();
            }
        }

        private void c(SQLiteDatabase sQLiteDatabase) {
            try {
                sQLiteDatabase.execSQL(String.format(k, "events"));
            } catch (Throwable th) {
                com.xiaomi.onetrack.util.s.b(r.b, "dropTable error:" + th.getMessage());
                th.printStackTrace();
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            b(sQLiteDatabase);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
            c(sQLiteDatabase);
            b(sQLiteDatabase);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
            if (i3 == 2 && i2 == 1) {
                a(sQLiteDatabase);
            }
        }
    }

    private r() {
        AtomicLong atomicLong = new AtomicLong(0L);
        this.m = atomicLong;
        try {
            this.i = new a(com.xiaomi.onetrack.f.a.a());
            HandlerThread handlerThread = new HandlerThread(f);
            handlerThread.start();
            this.g = new Handler(handlerThread.getLooper());
            long jH = h();
            com.xiaomi.onetrack.util.s.d(b, "SystemImpCacheManager init, There are " + jH + " events in DB");
            atomicLong.set(jH);
            g();
        } catch (Throwable th) {
            com.xiaomi.onetrack.util.s.b(b, "SystemImpCacheManager init Throwable: " + th.getMessage());
        }
    }

    public static r a() {
        if (e == null) {
            b();
        }
        return e;
    }

    public static synchronized void a(String str) {
        try {
            com.xiaomi.onetrack.util.s.a(b, "removeObsoleteFile->appId: " + str);
            File file = new File(f());
            if (file.exists() && file.isDirectory()) {
                String str2 = String.format(d, str);
                com.xiaomi.onetrack.util.s.a(b, "removeObsoleteFile->dirFile: " + file.getAbsolutePath() + ", fileName: " + str2);
                File[] fileArrListFiles = file.listFiles();
                for (int i = 0; i < fileArrListFiles.length; i++) {
                    if (fileArrListFiles[i].isFile() && fileArrListFiles[i].getName().equalsIgnoreCase(str2)) {
                        fileArrListFiles[i].delete();
                        break;
                    }
                }
            }
        } catch (Exception e2) {
            com.xiaomi.onetrack.util.s.b(b, "systemimp removeObsoleteEvent error: " + e2.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(String str, String str2, String str3, String str4, boolean z) {
        ArrayList<c.a> arrayList;
        boolean zA;
        try {
            try {
                ArrayList<Long> arrayList2 = new ArrayList<>();
                int i = 0;
                while (i <= 10000) {
                    com.xiaomi.onetrack.util.s.a(b, "cycle start");
                    arrayList2.clear();
                    c cVarC = c(str4);
                    if (cVarC == null || (arrayList = cVarC.a) == null || arrayList.size() == 0) {
                        com.xiaomi.onetrack.util.s.a(b, "满足条件的记录为空，即将返回, appId=" + str4);
                    } else {
                        for (int i2 = 0; i2 < cVarC.a.size(); i2++) {
                            c.a aVar = cVarC.a.get(i2);
                            String str5 = aVar.b;
                            String str6 = aVar.c;
                            if (z) {
                                String strA = aVar.d;
                                if (TextUtils.isEmpty(strA)) {
                                    strA = com.xiaomi.onetrack.util.a.a();
                                }
                                zA = be.a().a(str, str2, str3, str4, str5, str6, strA);
                            } else {
                                zA = be.a().a(str5, str6, str4);
                            }
                            if (!zA) {
                                com.xiaomi.onetrack.util.s.a(b, "Failed to trackEvent by binder, break!");
                                break;
                            }
                            arrayList2.add(Long.valueOf(aVar.a));
                        }
                        if (a(arrayList2) == 0) {
                            com.xiaomi.onetrack.util.s.b(b, "Delete DB failed!,eventIds.size:" + arrayList2.size());
                        } else if (arrayList2.size() != cVarC.a.size()) {
                            com.xiaomi.onetrack.util.s.c(b, "Failed to trackEvent by binder, exit cycle!");
                        } else if (cVarC.b) {
                            com.xiaomi.onetrack.util.s.a(b, "No more records for appId=" + str4);
                        } else {
                            i++;
                        }
                    }
                }
                com.xiaomi.onetrack.util.s.a(b, "exceed max cycle count, maxCount: " + i);
            } catch (Exception e2) {
                com.xiaomi.onetrack.util.s.b(b, "trackSystemImpCache error: " + e2.getMessage());
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public static void b() {
        if (e == null) {
            synchronized (r.class) {
                try {
                    if (e == null) {
                        e = new r();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str, String str2, String str3, String str4, String str5, String str6) {
        try {
            synchronized (this.i) {
                try {
                    com.xiaomi.onetrack.util.s.a(b, "addEvent->appId: " + str3 + ", eventName: " + str4);
                    File databasePath = com.xiaomi.onetrack.f.a.a().getDatabasePath(this.i.getDatabaseName());
                    if (databasePath != null && databasePath.length() > 104857600) {
                        com.xiaomi.onetrack.util.s.c(b, "DB size is " + databasePath.length() + " bytes, exceed max size!");
                        return;
                    }
                    SQLiteDatabase writableDatabase = this.i.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("projectid", str);
                    contentValues.put("topic", str2);
                    contentValues.put("appid", str3);
                    contentValues.put("event_name", str4);
                    contentValues.put("attribute", str6);
                    contentValues.put("timestamp", Long.valueOf(System.currentTimeMillis()));
                    byte[] bArrB = com.xiaomi.onetrack.d.a.b(str5);
                    if (bArrB == null) {
                        com.xiaomi.onetrack.util.s.c(b, "encryptedBytes is null");
                        return;
                    }
                    contentValues.put("data", com.xiaomi.onetrack.d.c.a(bArrB));
                    long jInsert = writableDatabase.insert("events", null, contentValues);
                    com.xiaomi.onetrack.util.s.a(b, "doSaveCacheData , row=" + jInsert);
                    if (jInsert == -1) {
                        com.xiaomi.onetrack.util.s.c(b, "doSaveCacheData failed");
                    } else if (com.xiaomi.onetrack.util.s.a) {
                        com.xiaomi.onetrack.util.s.a(b, "添加后，DB 中事件个数为 " + this.m.incrementAndGet());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (Exception e2) {
            com.xiaomi.onetrack.util.s.b(b, "doSaveData exception: ", e2);
        }
    }

    /* JADX WARN: Not initialized variable reg: 4, insn: 0x00bc: MOVE (r3 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:26:0x00bc */
    private c c(String str) {
        Closeable closeable;
        Closeable closeable2;
        c cVar;
        Cursor cursorQuery;
        boolean z;
        synchronized (this.i) {
            try {
                try {
                    try {
                        com.xiaomi.onetrack.util.s.a(b, "getCacheEventsDataSync start");
                        cursorQuery = this.i.getReadableDatabase().query("events", new String[]{"_id", "event_name", "data", "attribute"}, "appid = ? ", new String[]{str}, null, null, j, String.valueOf(100));
                        try {
                            int columnIndex = cursorQuery.getColumnIndex("_id");
                            int columnIndex2 = cursorQuery.getColumnIndex("event_name");
                            int columnIndex3 = cursorQuery.getColumnIndex("data");
                            int columnIndex4 = cursorQuery.getColumnIndex("attribute");
                            ArrayList arrayList = new ArrayList();
                            int length = 0;
                            while (true) {
                                if (!cursorQuery.moveToNext()) {
                                    cVar = null;
                                    break;
                                }
                                try {
                                    long j2 = cursorQuery.getLong(columnIndex);
                                    String string = cursorQuery.getString(columnIndex2);
                                    String string2 = cursorQuery.getString(columnIndex3);
                                    cVar = null;
                                    try {
                                        String string3 = cursorQuery.getString(columnIndex4);
                                        String strA = string2 != null ? com.xiaomi.onetrack.d.a.a(com.xiaomi.onetrack.d.c.a(string2)) : null;
                                        if (!TextUtils.isEmpty(strA)) {
                                            c.a aVar = new c.a();
                                            aVar.a = j2;
                                            aVar.b = string;
                                            aVar.c = strA;
                                            aVar.d = string3;
                                            arrayList.add(aVar);
                                            length += strA.length();
                                            if (length >= h) {
                                                com.xiaomi.onetrack.util.s.a(b, "reached max len: " + length);
                                                break;
                                            }
                                            continue;
                                            e = e;
                                            com.xiaomi.onetrack.util.s.b(b, "blob too big ***", e);
                                            i();
                                            com.xiaomi.onetrack.util.p.a(cursorQuery);
                                            return cVar;
                                        }
                                        continue;
                                    } catch (Throwable th) {
                                        th = th;
                                        try {
                                            com.xiaomi.onetrack.util.s.b(b, "Finally *** error ***", th);
                                        } catch (SQLiteBlobTooBigException e2) {
                                            e = e2;
                                        } catch (Exception e3) {
                                            e = e3;
                                            com.xiaomi.onetrack.util.s.b(b, "getEventsDataSync error", e);
                                            com.xiaomi.onetrack.util.p.a(cursorQuery);
                                            return cVar;
                                        }
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    cVar = null;
                                }
                            }
                            if (arrayList.size() > 0) {
                                if (!cursorQuery.isAfterLast()) {
                                    z = false;
                                } else if (arrayList.size() == 100) {
                                    com.xiaomi.onetrack.util.s.a(b, "reach max number per one query ");
                                    z = false;
                                } else {
                                    com.xiaomi.onetrack.util.s.a(b, "cursor isAfterLast");
                                    z = true;
                                }
                                c cVar2 = new c(arrayList, z);
                                com.xiaomi.onetrack.util.p.a(cursorQuery);
                                return cVar2;
                            }
                        } catch (SQLiteBlobTooBigException e4) {
                            e = e4;
                            cVar = null;
                        } catch (Exception e5) {
                            e = e5;
                            cVar = null;
                        }
                    } catch (Throwable th3) {
                        throw th3;
                    }
                } catch (SQLiteBlobTooBigException e6) {
                    e = e6;
                    cVar = null;
                    cursorQuery = null;
                } catch (Exception e7) {
                    e = e7;
                    cVar = null;
                    cursorQuery = null;
                } catch (Throwable th4) {
                    th = th4;
                    closeable2 = null;
                    com.xiaomi.onetrack.util.p.a(closeable2);
                    throw th;
                }
                com.xiaomi.onetrack.util.p.a(cursorQuery);
                return cVar;
            } catch (Throwable th5) {
                th = th5;
                closeable2 = closeable;
            }
        }
    }

    private static String f() {
        return com.xiaomi.onetrack.f.a.a().getFilesDir().getAbsolutePath() + File.separator + c;
    }

    private void g() {
        this.g.post(new w(this));
    }

    private long h() {
        try {
            return DatabaseUtils.queryNumEntries(this.i.getReadableDatabase(), "events");
        } catch (Exception e2) {
            com.xiaomi.onetrack.util.s.b(b, "getTotalCacheEventsNumberSync failed with " + e2.getMessage());
            return 0L;
        }
    }

    private void i() {
        try {
            int iDelete = this.i.getWritableDatabase().delete("events", null, null);
            com.xiaomi.onetrack.util.s.a(b, "delete table events");
            if (iDelete > 0) {
                com.xiaomi.onetrack.util.s.d(b, "delete table success, remains=" + this.m.addAndGet(-iDelete));
                return;
            }
            com.xiaomi.onetrack.util.s.d(b, "delete table failed, remains=" + this.m.get());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public int a(ArrayList<Long> arrayList) {
        synchronized (this.i) {
            if (arrayList != null) {
                try {
                    if (arrayList.size() != 0) {
                        try {
                            SQLiteDatabase writableDatabase = this.i.getWritableDatabase();
                            StringBuilder sb = new StringBuilder(((Long.toString(arrayList.get(0).longValue()).length() + 1) * arrayList.size()) + 16);
                            sb.append("_id");
                            sb.append(" in (");
                            sb.append(arrayList.get(0));
                            int size = arrayList.size();
                            for (int i = 1; i < size; i++) {
                                sb.append(com.xiaomi.onetrack.util.ac.b);
                                sb.append(arrayList.get(i));
                            }
                            sb.append(")");
                            int iDelete = writableDatabase.delete("events", sb.toString(), null);
                            if (iDelete > 0) {
                                com.xiaomi.onetrack.util.s.d(b, "delete events success, remains=" + this.m.addAndGet(-iDelete));
                            } else {
                                com.xiaomi.onetrack.util.s.d(b, "delete events failed, remains=" + this.m.get());
                            }
                            return iDelete;
                        } catch (Exception e2) {
                            com.xiaomi.onetrack.util.s.b(b, "deleteEventsSync error, e: ", e2);
                            return 0;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return 0;
        }
    }

    public void a(String str, String str2, String str3) {
        if (this.g == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        this.g.post(new s(this, str, str2, str3));
    }

    public void a(String str, String str2, String str3, String str4, String str5, String str6) {
        if (this.g == null || TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4)) {
            return;
        }
        this.g.post(new t(this, str, str2, str3, str4, str5, str6));
    }

    public void b(String str) {
        if (this.g == null || TextUtils.isEmpty(str)) {
            com.xiaomi.onetrack.util.s.a(b, "trackPubSubSystemImpCache has id is null");
        } else {
            this.g.post(new u(this, str));
        }
    }

    public void b(String str, String str2, String str3) {
        if (this.g == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            com.xiaomi.onetrack.util.s.a(b, "trackPubSubSystemImpCache has id is null");
        } else {
            this.g.post(new v(this, str3, str, str2));
        }
    }

    public long c() {
        return this.m.get();
    }

    public i d() {
        Cursor cursor;
        a aVar;
        i iVar;
        Cursor cursorQuery;
        boolean z;
        a aVar2 = this.i;
        synchronized (aVar2) {
            try {
                try {
                    try {
                        com.xiaomi.onetrack.util.s.a(b, "getOTCacheDataSync start");
                        cursorQuery = this.i.getReadableDatabase().query("events", new String[]{"_id", "appid", "event_name", "data"}, null, null, null, null, j, String.valueOf(100));
                        try {
                            int columnIndex = cursorQuery.getColumnIndex("_id");
                            int columnIndex2 = cursorQuery.getColumnIndex("appid");
                            int columnIndex3 = cursorQuery.getColumnIndex("event_name");
                            int columnIndex4 = cursorQuery.getColumnIndex("data");
                            JSONArray jSONArray = new JSONArray();
                            ArrayList arrayList = new ArrayList();
                            HashMap map = new HashMap();
                            int i = 0;
                            int length = 0;
                            while (true) {
                                if (!cursorQuery.moveToNext()) {
                                    aVar = aVar2;
                                    iVar = null;
                                    break;
                                }
                                try {
                                    long j2 = cursorQuery.getLong(columnIndex);
                                    String string = cursorQuery.getString(columnIndex3);
                                    iVar = null;
                                    try {
                                        String string2 = cursorQuery.getString(columnIndex2);
                                        String string3 = cursorQuery.getString(columnIndex4);
                                        String strA = string3 != null ? com.xiaomi.onetrack.d.a.a(com.xiaomi.onetrack.d.c.a(string3)) : null;
                                        JSONObject jSONObject = new JSONObject(strA);
                                        try {
                                            jSONArray.put(jSONObject);
                                            arrayList.add(Long.valueOf(j2));
                                            i++;
                                            if (com.xiaomi.onetrack.util.u.b(string)) {
                                                aVar = aVar2;
                                            } else {
                                                aVar = aVar2;
                                                try {
                                                    try {
                                                        if (com.xiaomi.onetrack.util.af.b(jSONObject.optJSONObject(an.b).optLong(com.xiaomi.onetrack.f.b.C0047b.p, 0L))) {
                                                            map.put(string2, Integer.valueOf((map.get(string2) == null ? 0 : ((Integer) map.get(string2)).intValue()) + 1));
                                                        }
                                                    } catch (Exception e2) {
                                                        e = e2;
                                                        com.xiaomi.onetrack.util.s.b(b, "*** error ***", e);
                                                    }
                                                } catch (Throwable th) {
                                                    th = th;
                                                    try {
                                                        com.xiaomi.onetrack.util.s.b(b, "Finally *** error ***", th);
                                                        aVar2 = aVar;
                                                    } catch (SQLiteBlobTooBigException e3) {
                                                        e = e3;
                                                        com.xiaomi.onetrack.util.s.b(b, "blob too big ***", e);
                                                        i();
                                                        com.xiaomi.onetrack.util.p.a(cursorQuery);
                                                        return iVar;
                                                    } catch (Exception e4) {
                                                        e = e4;
                                                        com.xiaomi.onetrack.util.s.b(b, "getEventsDataSync error", e);
                                                        com.xiaomi.onetrack.util.p.a(cursorQuery);
                                                        return iVar;
                                                    }
                                                }
                                            }
                                        } catch (Exception e5) {
                                            e = e5;
                                            aVar = aVar2;
                                        }
                                        length += strA.length();
                                        if (length >= h) {
                                            com.xiaomi.onetrack.util.s.a(b, "reached max len: " + length);
                                            break;
                                        }
                                        continue;
                                        aVar2 = aVar;
                                    } catch (Throwable th2) {
                                        th = th2;
                                        aVar = aVar2;
                                    }
                                } catch (Throwable th3) {
                                    th = th3;
                                    aVar = aVar2;
                                    iVar = null;
                                }
                            }
                            if (arrayList.size() > 0) {
                                if (!cursorQuery.isAfterLast()) {
                                    z = false;
                                } else if (i == 100) {
                                    com.xiaomi.onetrack.util.s.a(b, "cursor max one query number");
                                    z = false;
                                } else {
                                    com.xiaomi.onetrack.util.s.a(b, "cursor isAfterLast");
                                    z = true;
                                }
                                i iVar2 = new i(jSONArray, i, arrayList, z, map);
                                com.xiaomi.onetrack.util.p.a(cursorQuery);
                                return iVar2;
                            }
                        } catch (SQLiteBlobTooBigException e6) {
                            e = e6;
                            aVar = aVar2;
                            iVar = null;
                        } catch (Exception e7) {
                            e = e7;
                            aVar = aVar2;
                            iVar = null;
                        } catch (Throwable th4) {
                            th = th4;
                            cursor = cursorQuery;
                            com.xiaomi.onetrack.util.p.a(cursor);
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                    }
                } catch (SQLiteBlobTooBigException e8) {
                    e = e8;
                    aVar = aVar2;
                    iVar = null;
                    cursorQuery = null;
                } catch (Exception e9) {
                    e = e9;
                    aVar = aVar2;
                    iVar = null;
                    cursorQuery = null;
                } catch (Throwable th6) {
                    th = th6;
                    cursor = null;
                    com.xiaomi.onetrack.util.p.a(cursor);
                    throw th;
                }
                com.xiaomi.onetrack.util.p.a(cursorQuery);
                return iVar;
            } catch (Throwable th7) {
                throw th7;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 4, insn: 0x0102: MOVE (r3 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:38:0x0102 */
    /* JADX WARN: Type inference failed for: r18v1 */
    /* JADX WARN: Type inference failed for: r18v10 */
    /* JADX WARN: Type inference failed for: r18v11 */
    /* JADX WARN: Type inference failed for: r18v12 */
    /* JADX WARN: Type inference failed for: r18v13 */
    /* JADX WARN: Type inference failed for: r18v15, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r18v16 */
    /* JADX WARN: Type inference failed for: r18v17 */
    /* JADX WARN: Type inference failed for: r18v18 */
    /* JADX WARN: Type inference failed for: r18v19, types: [com.xiaomi.onetrack.c.m, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r18v2 */
    /* JADX WARN: Type inference failed for: r18v20 */
    /* JADX WARN: Type inference failed for: r18v21 */
    /* JADX WARN: Type inference failed for: r18v22 */
    /* JADX WARN: Type inference failed for: r18v23 */
    /* JADX WARN: Type inference failed for: r18v24 */
    /* JADX WARN: Type inference failed for: r18v25 */
    /* JADX WARN: Type inference failed for: r18v3, types: [com.xiaomi.onetrack.c.p] */
    /* JADX WARN: Type inference failed for: r18v4 */
    /* JADX WARN: Type inference failed for: r18v5 */
    /* JADX WARN: Type inference failed for: r18v6 */
    /* JADX WARN: Type inference failed for: r18v7 */
    /* JADX WARN: Type inference failed for: r18v8 */
    /* JADX WARN: Type inference failed for: r18v9 */
    public p e() {
        Closeable closeable;
        ?? r18;
        Cursor cursorQuery;
        ?? r19;
        Closeable closeable2;
        ?? r110;
        ?? string;
        boolean z;
        synchronized (this.i) {
            try {
                try {
                    try {
                        com.xiaomi.onetrack.util.s.a(b, "getPBCacheDataSync start");
                        cursorQuery = this.i.getReadableDatabase().query("events", null, null, null, null, null, j, String.valueOf(100));
                        try {
                            int columnIndex = cursorQuery.getColumnIndex("_id");
                            int columnIndex2 = cursorQuery.getColumnIndex("projectid");
                            int columnIndex3 = cursorQuery.getColumnIndex("topic");
                            int columnIndex4 = cursorQuery.getColumnIndex("appid");
                            int columnIndex5 = cursorQuery.getColumnIndex("data");
                            int columnIndex6 = cursorQuery.getColumnIndex("attribute");
                            int columnIndex7 = cursorQuery.getColumnIndex("timestamp");
                            ArrayList arrayList = new ArrayList();
                            ArrayList arrayList2 = new ArrayList();
                            int i = 0;
                            int i2 = 0;
                            while (true) {
                                if (!cursorQuery.moveToNext()) {
                                    string = 0;
                                    break;
                                }
                                try {
                                    long j2 = cursorQuery.getLong(columnIndex);
                                    String string2 = cursorQuery.getString(columnIndex2);
                                    String string3 = cursorQuery.getString(columnIndex3);
                                    if (!TextUtils.isEmpty(string2) && !TextUtils.isEmpty(string3)) {
                                        cursorQuery.getString(columnIndex4);
                                        String string4 = cursorQuery.getString(columnIndex5);
                                        string = cursorQuery.getString(columnIndex6);
                                        long j3 = cursorQuery.getLong(columnIndex7);
                                        String strA = string4 != null ? com.xiaomi.onetrack.d.a.a(com.xiaomi.onetrack.d.c.a(string4)) : null;
                                        try {
                                            string = new m(j2, string2, string3, strA, com.xiaomi.onetrack.util.d.a((String) string), j3);
                                            arrayList.add(string);
                                            arrayList2.add(Long.valueOf(j2));
                                            i++;
                                            string = 0;
                                        } catch (Exception e2) {
                                            string = 0;
                                            com.xiaomi.onetrack.util.s.b(b, "*** error ***", e2);
                                        }
                                        try {
                                            int length = i2 + strA.length();
                                            if (length >= h) {
                                                try {
                                                    com.xiaomi.onetrack.util.s.a(b, "reached max len: " + length);
                                                    break;
                                                } catch (Throwable th) {
                                                    th = th;
                                                    i2 = length;
                                                    try {
                                                        com.xiaomi.onetrack.util.s.b(b, "Finally *** error ***", th);
                                                    } catch (SQLiteBlobTooBigException e3) {
                                                        e = e3;
                                                        r19 = string;
                                                        com.xiaomi.onetrack.util.s.b(b, "blob too big ***", e);
                                                        i();
                                                        r110 = r19;
                                                        com.xiaomi.onetrack.util.p.a(cursorQuery);
                                                        return r110;
                                                    } catch (Exception e4) {
                                                        e = e4;
                                                        r18 = string;
                                                        com.xiaomi.onetrack.util.s.b(b, "getEventsDataSync error", e);
                                                        r110 = r18;
                                                        com.xiaomi.onetrack.util.p.a(cursorQuery);
                                                        return r110;
                                                    }
                                                }
                                            } else {
                                                i2 = length;
                                            }
                                        } catch (Throwable th2) {
                                            th = th2;
                                        }
                                    }
                                } catch (Throwable th3) {
                                    th = th3;
                                    string = 0;
                                }
                            }
                            int i3 = i;
                            r110 = string;
                            if (arrayList2.size() > 0) {
                                if (!cursorQuery.isAfterLast()) {
                                    z = false;
                                } else if (i3 == 100) {
                                    com.xiaomi.onetrack.util.s.a(b, "cursor max one query number");
                                    z = false;
                                } else {
                                    com.xiaomi.onetrack.util.s.a(b, "cursor isAfterLast");
                                    z = true;
                                }
                                p pVar = new p(arrayList, i3, arrayList2, z);
                                com.xiaomi.onetrack.util.p.a(cursorQuery);
                                return pVar;
                            }
                        } catch (SQLiteBlobTooBigException e5) {
                            e = e5;
                            r19 = 0;
                        } catch (Exception e6) {
                            e = e6;
                            r18 = 0;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        closeable = closeable2;
                        com.xiaomi.onetrack.util.p.a(closeable);
                        throw th;
                    }
                } catch (SQLiteBlobTooBigException e7) {
                    e = e7;
                    r19 = 0;
                    cursorQuery = null;
                } catch (Exception e8) {
                    e = e8;
                    r18 = 0;
                    cursorQuery = null;
                } catch (Throwable th5) {
                    th = th5;
                    closeable = null;
                    com.xiaomi.onetrack.util.p.a(closeable);
                    throw th;
                }
                com.xiaomi.onetrack.util.p.a(cursorQuery);
                return r110;
            } catch (Throwable th6) {
                throw th6;
            }
        }
    }
}
