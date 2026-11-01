package com.xiaomi.onetrack.c;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteBlobTooBigException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.xiaomi.onetrack.api.am;
import com.xiaomi.onetrack.api.an;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class e {
    private static final String a = "EventManager";
    private static final boolean b = false;
    private static final int c = 204800;
    private static final int d = 512000;
    private static final int e = 307200;
    private static final int f = 300;
    private static final int g = 300;
    private static final String h = "priority ASC, _id ASC";
    private static final int i = 7;
    private static e j = null;
    private static final int l = 5242880;
    private static BroadcastReceiver m = new f();
    private a k;

    public static class a extends SQLiteOpenHelper {
        public static final String a = "onetrack";
        public static final String b = "events";
        public static final String c = "_id";
        public static final String d = "appid";
        public static final String e = "package";
        public static final String f = "event_name";
        public static final String g = "priority";
        public static final String h = "data";
        public static final String i = "timestamp";
        private static final int j = 1;
        private static final String k = "CREATE TABLE events (_id INTEGER PRIMARY KEY AUTOINCREMENT,appid TEXT,package TEXT,event_name TEXT,priority INTEGER,data BLOB,timestamp INTEGER)";

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

    private e() {
        Context contextA = com.xiaomi.onetrack.f.a.a();
        this.k = new a(contextA);
        c();
        b(contextA);
    }

    public static e a() {
        if (j == null) {
            a(com.xiaomi.onetrack.f.a.b());
        }
        return j;
    }

    public static void a(Context context) {
        if (j == null) {
            synchronized (e.class) {
                try {
                    if (j == null) {
                        j = new e();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void a(com.xiaomi.onetrack.f.b bVar, String str, String str2) {
        if (!com.xiaomi.onetrack.h.a.a.a().b(bVar.c(), bVar.e())) {
            com.xiaomi.onetrack.util.s.a(a, "trackFailedEvent, should not record");
            return;
        }
        try {
            a(an.a(bVar, str, str2), false);
            com.xiaomi.onetrack.h.a.a.a().c(bVar.c(), bVar.e());
        } catch (Exception e2) {
            com.xiaomi.onetrack.util.s.b(a, "trackFailedEvent error", e2);
        }
    }

    private static void b(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        context.registerReceiver(m, intentFilter);
    }

    private boolean b(com.xiaomi.onetrack.f.b bVar, boolean z) throws Throwable {
        boolean z2;
        boolean z3 = false;
        try {
            synchronized (this.k) {
                try {
                    if (!bVar.i()) {
                        com.xiaomi.onetrack.util.s.c(a, "addEventToDatabase event is inValid, event:" + bVar.e());
                        return false;
                    }
                    SQLiteDatabase writableDatabase = this.k.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("appid", bVar.c());
                    contentValues.put("package", bVar.d());
                    contentValues.put("event_name", bVar.e());
                    contentValues.put("priority", Integer.valueOf(bVar.f()));
                    contentValues.put("timestamp", Long.valueOf(System.currentTimeMillis()));
                    byte[] bArrB = com.xiaomi.onetrack.d.a.b(bVar.g().toString());
                    if (z || "onetrack_bug_report".equals(bVar.e())) {
                        if (bArrB.length > d) {
                            com.xiaomi.onetrack.util.s.b(a, "ad or bug Too large data, discard ***");
                            com.xiaomi.onetrack.h.d.b.a(bVar.c(), bVar.e(), com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.o, 1);
                            a(bVar, com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.o);
                            return false;
                        }
                    } else if (bArrB.length > c) {
                        com.xiaomi.onetrack.util.s.b(a, "Too large data, discard ***");
                        com.xiaomi.onetrack.h.d.b.a(bVar.c(), bVar.e(), com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.o, 1);
                        a(bVar, com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.o);
                        return false;
                    }
                    contentValues.put("data", bArrB);
                    long jInsert = writableDatabase.insert("events", null, contentValues);
                    com.xiaomi.onetrack.util.s.a(a, "DB-Thread: EventManager.addEventToDatabase , row=" + jInsert);
                    z2 = jInsert != -1;
                    if (z2) {
                        try {
                            if (com.xiaomi.onetrack.util.s.a) {
                                com.xiaomi.onetrack.util.s.a(a, "添加后，DB 中事件个数为 " + d());
                            }
                            com.xiaomi.onetrack.b.o.a(false);
                        } catch (Throwable th) {
                            z3 = z2;
                            th = th;
                            throw th;
                        }
                    }
                    if (z2) {
                        com.xiaomi.onetrack.h.d.b.a(bVar.c(), bVar.e(), com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.l, 1);
                    } else {
                        com.xiaomi.onetrack.h.d.b.a(bVar.c(), bVar.e(), com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.p, 1);
                        a(bVar, com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.p);
                    }
                    return z2;
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        } catch (Exception e2) {
            com.xiaomi.onetrack.util.s.b(a, "EventManager.addEventToDatabase exception: ", e2);
            z2 = z3;
        }
    }

    private void e() {
        try {
            this.k.getWritableDatabase().delete("events", null, null);
            com.xiaomi.onetrack.util.s.a(a, "delete table events");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public int a(ArrayList<Long> arrayList) {
        synchronized (this.k) {
            if (arrayList != null) {
                try {
                    if (arrayList.size() != 0) {
                        try {
                            SQLiteDatabase writableDatabase = this.k.getWritableDatabase();
                            boolean z = true;
                            StringBuilder sb = new StringBuilder(((Long.toString(arrayList.get(0).longValue()).length() + 1) * arrayList.size()) + 16);
                            sb.append("_id");
                            sb.append(" in (");
                            sb.append(arrayList.get(0));
                            int size = arrayList.size();
                            for (int i2 = 1; i2 < size; i2++) {
                                sb.append(com.xiaomi.onetrack.util.ac.b);
                                sb.append(arrayList.get(i2));
                            }
                            sb.append(")");
                            int iDelete = writableDatabase.delete("events", sb.toString(), null);
                            com.xiaomi.onetrack.util.s.a(a, "deleted events count " + iDelete);
                            long jD = a().d();
                            if (jD != 0) {
                                z = false;
                            }
                            com.xiaomi.onetrack.b.o.a(z);
                            com.xiaomi.onetrack.util.s.a(a, "after delete DB record remains=" + jD);
                            return iDelete;
                        } catch (Exception e2) {
                            com.xiaomi.onetrack.util.s.b(a, "e=" + e2);
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

    /* JADX WARN: Code duplicated, block: B:87:0x01c3  */
    /* JADX WARN: Not initialized variable reg: 5, insn: 0x0083: MOVE (r3 I:??[OBJECT, ARRAY]) = (r5 I:??[OBJECT, ARRAY]), block:B:13:0x0083 */
    public i a(int i2) {
        Cursor cursor;
        i iVar;
        Cursor cursorQuery;
        Cursor cursor2;
        boolean z;
        boolean z2;
        int i3;
        int i4;
        try {
            try {
                cursorQuery = this.k.getReadableDatabase().query("events", null, null, null, null, null, h, String.valueOf(300));
                try {
                    int columnIndex = cursorQuery.getColumnIndex("_id");
                    int columnIndex2 = cursorQuery.getColumnIndex("appid");
                    cursorQuery.getColumnIndex("package");
                    int columnIndex3 = cursorQuery.getColumnIndex("event_name");
                    int columnIndex4 = cursorQuery.getColumnIndex("priority");
                    int columnIndex5 = cursorQuery.getColumnIndex("data");
                    int columnIndex6 = cursorQuery.getColumnIndex("timestamp");
                    JSONArray jSONArray = new JSONArray();
                    ArrayList arrayList = new ArrayList();
                    HashMap map = new HashMap();
                    boolean z3 = true;
                    boolean z4 = true;
                    int i5 = 0;
                    int i6 = 0;
                    while (true) {
                        if (!cursorQuery.moveToNext()) {
                            iVar = null;
                            break;
                        }
                        long j2 = cursorQuery.getLong(columnIndex);
                        cursorQuery.getLong(columnIndex6);
                        iVar = null;
                        try {
                            String string = cursorQuery.getString(columnIndex2);
                            byte[] blob = cursorQuery.getBlob(columnIndex5);
                            String strA = blob != null ? com.xiaomi.onetrack.d.a.a(blob) : null;
                            if (z4) {
                                int i7 = cursorQuery.getInt(columnIndex4);
                                if (i7 > i2) {
                                    com.xiaomi.onetrack.util.s.a(a, "No records of priority[" + i2 + "], first record priority=" + i7);
                                    cursorQuery.close();
                                    return null;
                                }
                                z2 = false;
                            } else {
                                z2 = z4;
                            }
                            JSONObject jSONObject = new JSONObject(strA);
                            try {
                                jSONArray.put(jSONObject);
                                arrayList.add(Long.valueOf(j2));
                                i5++;
                                if (com.xiaomi.onetrack.util.u.b(cursorQuery.getString(columnIndex3))) {
                                    i3 = columnIndex;
                                    i4 = columnIndex2;
                                } else {
                                    i3 = columnIndex;
                                    i4 = columnIndex2;
                                    try {
                                        if (com.xiaomi.onetrack.util.af.b(jSONObject.optJSONObject(an.b).optLong(com.xiaomi.onetrack.f.b.C0047b.p, 0L))) {
                                            map.put(string, Integer.valueOf((map.get(string) == null ? 0 : ((Integer) map.get(string)).intValue()) + 1));
                                        }
                                    } catch (Exception e2) {
                                        e = e2;
                                        com.xiaomi.onetrack.util.s.b(a, "*** error ***", e);
                                    }
                                }
                            } catch (Exception e3) {
                                e = e3;
                                i3 = columnIndex;
                                i4 = columnIndex2;
                            }
                            int length = i6 + strA.length();
                            if (length >= e) {
                                com.xiaomi.onetrack.util.s.a(a, "reached max len: " + length);
                                break;
                            }
                            i6 = length;
                            columnIndex = i3;
                            columnIndex2 = i4;
                            z4 = z2;
                        } catch (SQLiteBlobTooBigException e4) {
                            e = e4;
                            com.xiaomi.onetrack.util.s.b(a, "blob too big ***", e);
                            e();
                            if (cursorQuery != null) {
                            }
                            return iVar;
                        } catch (Exception e5) {
                            e = e5;
                            com.xiaomi.onetrack.util.s.a(a, "", e);
                            if (cursorQuery != null) {
                            }
                            return iVar;
                        }
                    }
                    int i8 = i5;
                    if (arrayList.size() > 0) {
                        if (cursorQuery.isAfterLast()) {
                            if (i8 == 300) {
                                com.xiaomi.onetrack.util.s.a(a, "cursor max one query number");
                                z = false;
                            } else {
                                com.xiaomi.onetrack.util.s.a(a, "cursor isAfterLast");
                            }
                            i iVar2 = new i(jSONArray, i8, arrayList, z, map);
                            cursorQuery.close();
                            return iVar2;
                        }
                        if (cursorQuery.getInt(columnIndex4) <= i2) {
                            z3 = false;
                        }
                        z = z3;
                        i iVar3 = new i(jSONArray, i8, arrayList, z, map);
                        cursorQuery.close();
                        return iVar3;
                    }
                } catch (SQLiteBlobTooBigException e6) {
                    e = e6;
                    iVar = null;
                } catch (Exception e7) {
                    e = e7;
                    iVar = null;
                }
            } catch (Throwable th) {
                th = th;
                cursor = cursor2;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (SQLiteBlobTooBigException e8) {
            e = e8;
            iVar = null;
            cursorQuery = null;
        } catch (Exception e9) {
            e = e9;
            iVar = null;
            cursorQuery = null;
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        cursorQuery.close();
        return iVar;
    }

    public Boolean a(com.xiaomi.onetrack.a.b.a aVar, int i2, String str, Throwable th) {
        Cursor cursor;
        synchronized (this.k) {
            try {
                try {
                    try {
                        SQLiteDatabase writableDatabase = this.k.getWritableDatabase();
                        Cursor cursorQuery = writableDatabase.query("events", new String[]{"_id", "data"}, "appid = ? AND event_name = ? AND priority = ?", new String[]{com.xiaomi.onetrack.util.a.m, am.h, String.valueOf(2)}, null, null, null, "50");
                        if (cursorQuery != null) {
                            try {
                                try {
                                    if (cursorQuery.getCount() != 0) {
                                        int columnIndex = cursorQuery.getColumnIndex("_id");
                                        int columnIndex2 = cursorQuery.getColumnIndex("data");
                                        while (cursorQuery.moveToNext()) {
                                            long j2 = cursorQuery.getLong(columnIndex);
                                            byte[] blob = cursorQuery.getBlob(columnIndex2);
                                            String strA = blob != null ? com.xiaomi.onetrack.d.a.a(blob) : null;
                                            if (com.xiaomi.onetrack.util.ac.b(strA)) {
                                                try {
                                                    JSONObject jSONObject = new JSONObject(strA);
                                                    if (aVar.j().equals(jSONObject.optJSONObject(an.b).optString(com.xiaomi.onetrack.f.b.C0047b.J))) {
                                                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(an.a);
                                                        String strOptString = jSONObjectOptJSONObject.optString("type");
                                                        int iOptInt = jSONObjectOptJSONObject.optInt("url_index");
                                                        int iOptInt2 = jSONObjectOptJSONObject.optInt(com.xiaomi.onetrack.e.a.h);
                                                        String strOptString2 = jSONObjectOptJSONObject.optString(com.xiaomi.onetrack.e.a.i);
                                                        String strOptString3 = jSONObjectOptJSONObject.optString("message");
                                                        if (com.xiaomi.onetrack.e.a.a.equals(strOptString)) {
                                                            if (aVar.i() == iOptInt && i2 == iOptInt2) {
                                                                try {
                                                                    if (!TextUtils.isEmpty(str)) {
                                                                        try {
                                                                            if (str.equals(strOptString2)) {
                                                                            }
                                                                        } catch (Exception e2) {
                                                                            e = e2;
                                                                            com.xiaomi.onetrack.util.s.a(a, "hasAndUpdateSameAdMExceptionEvent exception:" + e);
                                                                        }
                                                                    }
                                                                    if (th == null || TextUtils.isEmpty(th.getMessage()) || th.getMessage().equals(strOptString3)) {
                                                                        jSONObjectOptJSONObject.put("retry", jSONObjectOptJSONObject.optInt("retry", 0) + 1);
                                                                        byte[] bArrB = com.xiaomi.onetrack.d.a.b(jSONObject.toString());
                                                                        ContentValues contentValues = new ContentValues();
                                                                        contentValues.put("data", bArrB);
                                                                        int iUpdate = writableDatabase.update("events", contentValues, "_id = ?", new String[]{String.valueOf(j2)});
                                                                        if (iUpdate > 0) {
                                                                            com.xiaomi.onetrack.util.s.a(a, "updata same adMException rowsupdated:" + iUpdate);
                                                                            Boolean bool = Boolean.TRUE;
                                                                            com.xiaomi.onetrack.util.p.b(cursorQuery);
                                                                            return bool;
                                                                        }
                                                                        continue;
                                                                    }
                                                                } catch (Exception e3) {
                                                                    e = e3;
                                                                    com.xiaomi.onetrack.util.s.a(a, "hasAndUpdateSameAdMExceptionEvent exception:" + e);
                                                                }
                                                            }
                                                            com.xiaomi.onetrack.util.s.a(a, "item exception no same");
                                                        } else {
                                                            com.xiaomi.onetrack.util.s.a(a, "no admException");
                                                        }
                                                    }
                                                } catch (Exception e4) {
                                                    e = e4;
                                                }
                                            }
                                        }
                                        com.xiaomi.onetrack.util.p.b(cursorQuery);
                                        return Boolean.FALSE;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    cursor = cursorQuery;
                                    com.xiaomi.onetrack.util.p.b(cursor);
                                    throw th;
                                }
                            } catch (SQLiteBlobTooBigException e5) {
                                e = e5;
                                cursor = cursorQuery;
                                com.xiaomi.onetrack.util.s.b(a, "blob too big ***", e);
                                e();
                                com.xiaomi.onetrack.util.p.b(cursor);
                            } catch (Exception e6) {
                                e = e6;
                                cursor = cursorQuery;
                                com.xiaomi.onetrack.util.s.b(a, "getAppIdSingleEventData error, e: ", e);
                                com.xiaomi.onetrack.util.p.b(cursor);
                            }
                        }
                        Boolean bool2 = Boolean.FALSE;
                        com.xiaomi.onetrack.util.p.b(cursorQuery);
                        return bool2;
                    } catch (Throwable th3) {
                        th = th3;
                    }
                } catch (SQLiteBlobTooBigException e7) {
                    e = e7;
                    cursor = null;
                } catch (Exception e8) {
                    e = e8;
                    cursor = null;
                } catch (Throwable th4) {
                    th = th4;
                    cursor = null;
                }
            } catch (Throwable th5) {
                throw th5;
            }
        }
    }

    public synchronized void a(com.xiaomi.onetrack.f.b bVar, boolean z) {
        try {
            try {
                b(bVar, z);
                if (z) {
                    com.xiaomi.onetrack.a.c.a().a(bVar);
                }
                com.xiaomi.onetrack.a.c.b.a().b(false);
                com.xiaomi.onetrack.util.s.a(a, "addEvent: " + bVar.e() + ", data: " + bVar.g().toString());
                ag.a().a(bVar.f(), false);
            } catch (Exception e2) {
                com.xiaomi.onetrack.util.s.b(a, "EventManager.addEvent exception: ", e2);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public boolean a(ab abVar) {
        synchronized (this.k) {
            SQLiteDatabase sQLiteDatabase = null;
            try {
                if (abVar != null) {
                    try {
                        ArrayList<x> arrayList = abVar.a;
                        if (arrayList != null && arrayList.size() != 0) {
                            SQLiteDatabase writableDatabase = this.k.getWritableDatabase();
                            try {
                                writableDatabase.beginTransaction();
                                for (x xVar : abVar.a) {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("appid", xVar.b());
                                    contentValues.put("package", xVar.c());
                                    contentValues.put("event_name", xVar.j());
                                    contentValues.put("priority", Integer.valueOf(xVar.g()));
                                    contentValues.put("timestamp", Long.valueOf(xVar.i()));
                                    contentValues.put("data", xVar.f());
                                    com.xiaomi.onetrack.util.s.a(a, "DB-Thread: EventManager.addTransformDataToDB , row=" + writableDatabase.insert("events", null, contentValues));
                                }
                                com.xiaomi.onetrack.b.o.a(false);
                                writableDatabase.setTransactionSuccessful();
                                try {
                                    writableDatabase.endTransaction();
                                } catch (Exception e2) {
                                    com.xiaomi.onetrack.util.s.b(a, "addTransformDataToDB Exception while endTransaction:" + e2);
                                }
                                return true;
                            } catch (Exception e3) {
                                e = e3;
                                sQLiteDatabase = writableDatabase;
                                com.xiaomi.onetrack.util.s.a(a, "DB-Thread: addTransformDataToDB , exception:" + e.getMessage());
                                if (sQLiteDatabase != null) {
                                    try {
                                        sQLiteDatabase.endTransaction();
                                    } catch (Exception e4) {
                                        com.xiaomi.onetrack.util.s.b(a, "addTransformDataToDB Exception while endTransaction:" + e4);
                                    }
                                }
                                return false;
                            } catch (Throwable th) {
                                th = th;
                                sQLiteDatabase = writableDatabase;
                                if (sQLiteDatabase != null) {
                                    try {
                                        sQLiteDatabase.endTransaction();
                                    } catch (Exception e5) {
                                        com.xiaomi.onetrack.util.s.b(a, "addTransformDataToDB Exception while endTransaction:" + e5);
                                    }
                                }
                                throw th;
                            }
                        }
                    } catch (Exception e6) {
                        e = e6;
                    }
                }
                com.xiaomi.onetrack.util.s.a(a, "TransformEvent记录为空，即将返回");
                return false;
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:70:0x0126 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Not initialized variable reg: 4, insn: 0x0097: MOVE (r2 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:13:0x0097 */
    public ab b() throws Throwable {
        Cursor cursor;
        ab abVar;
        Cursor cursorQuery;
        Cursor cursor2;
        boolean z;
        byte[] bArr;
        try {
            try {
                cursorQuery = this.k.getReadableDatabase().query("events", null, null, null, null, null, h, String.valueOf(300));
                try {
                    int columnIndex = cursorQuery.getColumnIndex("_id");
                    int columnIndex2 = cursorQuery.getColumnIndex("appid");
                    int columnIndex3 = cursorQuery.getColumnIndex("package");
                    int columnIndex4 = cursorQuery.getColumnIndex("event_name");
                    int columnIndex5 = cursorQuery.getColumnIndex("data");
                    int columnIndex6 = cursorQuery.getColumnIndex("timestamp");
                    int columnIndex7 = cursorQuery.getColumnIndex("priority");
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    int i2 = 0;
                    int i3 = 0;
                    while (true) {
                        if (!cursorQuery.moveToNext()) {
                            abVar = null;
                            break;
                        }
                        long j2 = cursorQuery.getLong(columnIndex);
                        String string = cursorQuery.getString(columnIndex2);
                        String string2 = cursorQuery.getString(columnIndex4);
                        String string3 = cursorQuery.getString(columnIndex3);
                        byte[] blob = cursorQuery.getBlob(columnIndex5);
                        try {
                            bArr = blob;
                            abVar = null;
                            try {
                                arrayList.add(new x(j2, null, null, string, string3, string2, blob, null, cursorQuery.getLong(columnIndex6), cursorQuery.getInt(columnIndex7)));
                                arrayList2.add(Long.valueOf(j2));
                                i2++;
                            } catch (Exception e2) {
                                e = e2;
                                com.xiaomi.onetrack.util.s.b(a, "*** error ***", e);
                            }
                        } catch (Exception e3) {
                            e = e3;
                            bArr = blob;
                            abVar = null;
                        }
                        try {
                            int length = i3 + bArr.length;
                            if (length >= l) {
                                com.xiaomi.onetrack.util.s.a(a, "reached max len: " + length);
                                break;
                            }
                            i3 = length;
                        } catch (SQLiteBlobTooBigException e4) {
                            e = e4;
                            com.xiaomi.onetrack.util.s.b(a, "blob too big ***", e);
                            e();
                            if (cursorQuery != null) {
                            }
                            return abVar;
                        } catch (Exception e5) {
                            e = e5;
                            com.xiaomi.onetrack.util.s.a(a, "", e);
                            if (cursorQuery != null) {
                            }
                            return abVar;
                        }
                    }
                    int i4 = i2;
                    if (arrayList2.size() > 0) {
                        if (!cursorQuery.isAfterLast()) {
                            z = false;
                        } else if (i4 == 300) {
                            com.xiaomi.onetrack.util.s.a(a, "cursor max one query number");
                            z = false;
                        } else {
                            com.xiaomi.onetrack.util.s.a(a, "cursor isAfterLast");
                            z = true;
                        }
                        ab abVar2 = new ab(arrayList, i4, arrayList2, z);
                        try {
                            cursorQuery.close();
                        } catch (Exception unused) {
                        }
                        return abVar2;
                    }
                } catch (SQLiteBlobTooBigException e6) {
                    e = e6;
                    abVar = null;
                } catch (Exception e7) {
                    e = e7;
                    abVar = null;
                }
            } catch (Throwable th) {
                th = th;
                cursor = cursor2;
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Exception unused2) {
                    }
                }
                throw th;
            }
        } catch (SQLiteBlobTooBigException e8) {
            e = e8;
            abVar = null;
            cursorQuery = null;
        } catch (Exception e9) {
            e = e9;
            abVar = null;
            cursorQuery = null;
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        try {
            cursorQuery.close();
        } catch (Exception unused3) {
        }
        return abVar;
    }

    public void c() {
        d.a(new h(this), 1000L);
    }

    public long d() {
        try {
            return DatabaseUtils.queryNumEntries(this.k.getReadableDatabase(), "events");
        } catch (Exception e2) {
            com.xiaomi.onetrack.util.s.b(a, "getTotalEventsNumberSync failed with " + e2.getMessage());
            return 0L;
        }
    }
}
