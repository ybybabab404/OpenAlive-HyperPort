package com.xiaomi.onetrack.c;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteBlobTooBigException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.xiaomi.onetrack.api.an;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class n {
    private static final String a = "PubSubEventManager";
    private static n b = null;
    private static final int d = 1000;
    private static final int e = 204800;
    private static final int f = 512000;
    private static final int g = 307200;
    private static final int h = 7;
    private static final int i = 300;
    private static final String j = "priority ASC, _id ASC";
    private static volatile boolean k = false;
    private static final int l = 5242880;
    private a c = new a(com.xiaomi.onetrack.f.a.a());

    public static class a extends SQLiteOpenHelper {
        public static final String a = "ot_ps_sdk";
        public static final String b = "otps_events";
        public static final String c = "_id";
        public static final String d = "appid";
        public static final String e = "package";
        public static final String f = "projectid";
        public static final String g = "topic";
        public static final String h = "data";
        public static final String i = "attribute";
        public static final String j = "timestamp";
        public static final String k = "event_name";
        public static final String l = "priority";
        private static final int m = 1;
        private static final String n = "CREATE TABLE otps_events (_id INTEGER PRIMARY KEY AUTOINCREMENT,appid TEXT,package TEXT,event_name TEXT,projectid TEXT,topic TEXT,data BLOB,attribute BLOB,priority INTEGER,timestamp INTEGER)";

        public a(Context context) {
            super(context, a, (SQLiteDatabase.CursorFactory) null, 1);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(n);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
        }
    }

    private n() {
        d();
    }

    public static n a() {
        if (b == null) {
            b();
        }
        return b;
    }

    private void a(String str, String str2, com.xiaomi.onetrack.f.b bVar, Map<String, String> map, String str3, String str4) {
        if (!com.xiaomi.onetrack.h.a.a.a().b(bVar.c(), bVar.e())) {
            com.xiaomi.onetrack.util.s.a(a, "trackFailedEvent, should not record");
            return;
        }
        try {
            a(str, str2, an.a(bVar, str3, str4), map, false);
            com.xiaomi.onetrack.h.a.a.a().c(bVar.c(), bVar.e());
        } catch (Exception e2) {
            com.xiaomi.onetrack.util.s.b(a, "trackFailedEvent error", e2);
        }
    }

    public static void b() {
        if (b == null) {
            synchronized (n.class) {
                try {
                    if (b == null) {
                        b = new n();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void b(String str, String str2, com.xiaomi.onetrack.f.b bVar, Map<String, String> map, boolean z) throws Throwable {
        boolean z2;
        boolean z3 = false;
        try {
            synchronized (this.c) {
                try {
                    if (bVar != null && bVar.i() && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                        byte[] bArrB = com.xiaomi.onetrack.d.a.b(bVar.g().toString());
                        if (z || "onetrack_bug_report".equals(bVar.e())) {
                            if (bArrB.length > f) {
                                com.xiaomi.onetrack.util.s.b(a, "ad or bug Too large data, discard ***");
                                com.xiaomi.onetrack.h.d.b.a(bVar.c(), bVar.e(), com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.o, 1);
                                a(str, str2, bVar, map, com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.o);
                                return;
                            }
                        } else if (bArrB.length > e) {
                            com.xiaomi.onetrack.util.s.b(a, "Too large data, discard ***");
                            com.xiaomi.onetrack.h.d.b.a(bVar.c(), bVar.e(), com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.o, 1);
                            a(str, str2, bVar, map, com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.o);
                            return;
                        }
                        SQLiteDatabase writableDatabase = this.c.getWritableDatabase();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("appid", bVar.c());
                        contentValues.put("package", bVar.d());
                        contentValues.put("event_name", bVar.e());
                        contentValues.put("projectid", str);
                        contentValues.put("topic", str2);
                        contentValues.put("attribute", com.xiaomi.onetrack.util.d.a((Object) map));
                        contentValues.put("timestamp", Long.valueOf(System.currentTimeMillis()));
                        contentValues.put("data", bArrB);
                        contentValues.put("priority", Integer.valueOf(bVar.f()));
                        long jInsert = writableDatabase.insert(a.b, null, contentValues);
                        com.xiaomi.onetrack.util.s.a(a, "DB-Thread: addOTMessageToDatabase , row=" + jInsert);
                        z2 = jInsert != -1;
                        if (z2) {
                            try {
                                if (com.xiaomi.onetrack.util.s.a) {
                                    com.xiaomi.onetrack.util.s.a(a, "添加后，DB 中事件个数为 " + f());
                                }
                                a(false);
                            } catch (Throwable th) {
                                th = th;
                                z3 = z2;
                                throw th;
                            }
                        }
                    }
                    com.xiaomi.onetrack.util.s.c(a, "addEventToDatabase message is inValid. topic:" + str2 + ", data EventName:" + bVar.e() + ", projectId:" + str);
                    return;
                } catch (Throwable th2) {
                    th = th2;
                }
            }
            throw th;
        } catch (Exception e2) {
            com.xiaomi.onetrack.util.s.b(a, "addEvent exception: ", e2);
            z2 = z3;
        }
        if (z2) {
            com.xiaomi.onetrack.h.d.b.a(bVar.c(), bVar.e(), com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.l, 1);
        } else {
            com.xiaomi.onetrack.h.d.b.a(bVar.c(), bVar.e(), com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.p, 1);
            a(str, str2, bVar, map, com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.p);
        }
    }

    private void g() {
        try {
            this.c.getWritableDatabase().delete(a.b, null, null);
            a(true);
            com.xiaomi.onetrack.util.s.a(a, "delete table otps_events");
        } catch (Exception e2) {
            com.xiaomi.onetrack.util.s.b(a, "delete table error: " + e2.getMessage());
        }
    }

    public int a(ArrayList<Long> arrayList) {
        int iDelete;
        synchronized (this.c) {
            try {
                try {
                    SQLiteDatabase writableDatabase = this.c.getWritableDatabase();
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
                    iDelete = writableDatabase.delete(a.b, sb.toString(), null);
                    com.xiaomi.onetrack.util.s.a(a, "*** *** deleted events count " + iDelete);
                    long jF = a().f();
                    if (jF != 0) {
                        z = false;
                    }
                    a(z);
                    com.xiaomi.onetrack.util.s.a(a, "after delete DB record remains=" + jF);
                } catch (Exception e2) {
                    com.xiaomi.onetrack.util.s.b(a, "e=" + e2);
                    return 0;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return iDelete;
    }

    public int a(List<m> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        ArrayList<Long> arrayList = new ArrayList<>();
        Iterator<m> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(Long.valueOf(it.next().b()));
        }
        return a(arrayList);
    }

    /* JADX WARN: Code duplicated, block: B:110:0x0114 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:111:0x011a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:48:0x0115  */
    /* JADX WARN: Code duplicated, block: B:52:0x0131 A[LOOP:0: B:5:0x0065->B:52:0x0131, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:96:0x019b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 5, insn: 0x00ac: MOVE (r3 I:??[OBJECT, ARRAY]) = (r5 I:??[OBJECT, ARRAY]), block:B:15:0x00ac */
    /* JADX WARN: Type inference failed for: r26v0 */
    /* JADX WARN: Type inference failed for: r26v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r26v2 */
    /* JADX WARN: Type inference failed for: r27v0 */
    /* JADX WARN: Type inference failed for: r27v1, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r27v2 */
    public p a(int i2) {
        p pVar;
        Cursor cursorQuery;
        Cursor cursor;
        boolean z;
        boolean z2;
        ?? A;
        Object objA;
        ?? r27;
        int length;
        Map map;
        Cursor cursor2 = null;
        pVar = null;
        pVar = null;
        p pVar2 = null;
        try {
            try {
                try {
                    cursorQuery = this.c.getReadableDatabase().query(a.b, null, null, null, null, null, j, String.valueOf(i));
                    try {
                        int columnIndex = cursorQuery.getColumnIndex("_id");
                        cursorQuery.getColumnIndex("appid");
                        int columnIndex2 = cursorQuery.getColumnIndex("projectid");
                        int columnIndex3 = cursorQuery.getColumnIndex("topic");
                        int columnIndex4 = cursorQuery.getColumnIndex("data");
                        int columnIndex5 = cursorQuery.getColumnIndex("attribute");
                        int columnIndex6 = cursorQuery.getColumnIndex("timestamp");
                        int columnIndex7 = cursorQuery.getColumnIndex("priority");
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        boolean z3 = true;
                        boolean z4 = true;
                        int i3 = 0;
                        int i4 = 0;
                        while (true) {
                            if (!cursorQuery.moveToNext()) {
                                pVar = pVar2;
                                break;
                            }
                            long j2 = cursorQuery.getLong(columnIndex);
                            String string = cursorQuery.getString(columnIndex2);
                            String string2 = cursorQuery.getString(columnIndex3);
                            byte[] blob = cursorQuery.getBlob(columnIndex5);
                            byte[] blob2 = cursorQuery.getBlob(columnIndex4);
                            long j3 = cursorQuery.getLong(columnIndex6);
                            try {
                                if (z4) {
                                    int i5 = cursorQuery.getInt(columnIndex7);
                                    if (i5 > i2) {
                                        com.xiaomi.onetrack.util.s.a(a, "ps no records of priority[" + i2 + "], first record priority=" + i5);
                                        try {
                                            cursorQuery.close();
                                        } catch (Exception unused) {
                                        }
                                        return pVar2;
                                    }
                                    z2 = false;
                                    com.xiaomi.onetrack.util.s.b(a, "blob too big ***", e);
                                    g();
                                    if (cursorQuery != null) {
                                        cursorQuery.close();
                                    }
                                    return pVar;
                                }
                                z2 = z4;
                                if (objA != null) {
                                    pVar = pVar2;
                                    try {
                                        if (objA instanceof Map) {
                                            map = (Map) objA;
                                        }
                                        r27 = map;
                                        arrayList.add(new m(j2, string, string2, A, r27, j3));
                                        arrayList2.add(Long.valueOf(j2));
                                        i3++;
                                        length = i4 + A.length();
                                        if (arrayList.size() < 1000) {
                                            break;
                                        }
                                        if (length >= g) {
                                            com.xiaomi.onetrack.util.s.a(a, "reached max len: " + length);
                                            break;
                                        }
                                        i4 = length;
                                        pVar2 = pVar;
                                        z4 = z2;
                                    } catch (SQLiteBlobTooBigException e2) {
                                        e = e2;
                                        com.xiaomi.onetrack.util.s.b(a, "blob too big ***", e);
                                        g();
                                        if (cursorQuery != null) {
                                        }
                                        return pVar;
                                    } catch (Exception e3) {
                                        e = e3;
                                        com.xiaomi.onetrack.util.s.a(a, "", e);
                                        if (cursorQuery != null) {
                                        }
                                        return pVar;
                                    }
                                } else {
                                    pVar = pVar2;
                                }
                                arrayList.add(new m(j2, string, string2, A, r27, j3));
                                arrayList2.add(Long.valueOf(j2));
                                i3++;
                            } catch (Exception e4) {
                                com.xiaomi.onetrack.util.s.b(a, "*** error ***", e4);
                            }
                            A = blob2 != null ? com.xiaomi.onetrack.d.a.a(blob2) : pVar2;
                            objA = com.xiaomi.onetrack.util.d.a(blob);
                            if (objA == null) {
                                map = new HashMap();
                                r27 = map;
                            } else {
                                r27 = pVar;
                            }
                            length = i4 + A.length();
                            if (arrayList.size() < 1000) {
                                break;
                                break;
                            }
                            if (length >= g) {
                                com.xiaomi.onetrack.util.s.a(a, "reached max len: " + length);
                                break;
                            }
                            i4 = length;
                            pVar2 = pVar;
                            z4 = z2;
                        }
                        int i6 = i3;
                        if (arrayList2.size() > 0) {
                            try {
                                if (cursorQuery.isAfterLast()) {
                                    if (i6 == i) {
                                        com.xiaomi.onetrack.util.s.a(a, "cursor max one query number");
                                        z = false;
                                    } else {
                                        com.xiaomi.onetrack.util.s.a(a, "cursor isAfterLast");
                                    }
                                    p pVar3 = new p(arrayList, i6, arrayList2, z);
                                    cursorQuery.close();
                                    return pVar3;
                                }
                                if (cursorQuery.getInt(columnIndex7) <= i2) {
                                    z3 = false;
                                }
                                cursorQuery.close();
                            } catch (Exception unused2) {
                            }
                            z = z3;
                            p pVar4 = new p(arrayList, i6, arrayList2, z);
                            return pVar4;
                        }
                    } catch (SQLiteBlobTooBigException e5) {
                        e = e5;
                        pVar = pVar2;
                    } catch (Exception e6) {
                        e = e6;
                        pVar = pVar2;
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor2 = cursor;
                    if (cursor2 != null) {
                        try {
                            cursor2.close();
                        } catch (Exception unused3) {
                        }
                    }
                    throw th;
                }
            } catch (SQLiteBlobTooBigException e7) {
                e = e7;
                pVar = null;
                cursorQuery = null;
            } catch (Exception e8) {
                e = e8;
                pVar = null;
                cursorQuery = null;
            } catch (Throwable th2) {
                th = th2;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
            cursorQuery.close();
        } catch (Exception unused4) {
        }
        return pVar;
    }

    public synchronized void a(String str, String str2, com.xiaomi.onetrack.f.b bVar, Map<String, String> map, boolean z) {
        try {
            try {
                b(str, str2, bVar, map, z);
                if (z) {
                    com.xiaomi.onetrack.a.c.a().a(bVar);
                }
                com.xiaomi.onetrack.a.c.b.a().b(false);
                ag.a().a(bVar.f(), false);
            } catch (Exception e2) {
                com.xiaomi.onetrack.util.s.b(a, "EventManager.addTrackMessage exception: ", e2);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void a(boolean z) {
        k = z;
    }

    public boolean a(ab abVar) {
        synchronized (this.c) {
            SQLiteDatabase sQLiteDatabase = null;
            try {
                if (abVar != null) {
                    try {
                        ArrayList<x> arrayList = abVar.a;
                        if (arrayList != null && arrayList.size() != 0) {
                            SQLiteDatabase writableDatabase = this.c.getWritableDatabase();
                            try {
                                writableDatabase.beginTransaction();
                                for (x xVar : abVar.a) {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("appid", xVar.b());
                                    contentValues.put("package", xVar.c());
                                    contentValues.put("event_name", xVar.j());
                                    contentValues.put("projectid", xVar.d());
                                    contentValues.put("topic", xVar.e());
                                    contentValues.put("attribute", com.xiaomi.onetrack.util.d.a((Object) xVar.h()));
                                    contentValues.put("timestamp", Long.valueOf(xVar.i()));
                                    contentValues.put("data", xVar.f());
                                    contentValues.put("priority", Integer.valueOf(xVar.g()));
                                    com.xiaomi.onetrack.util.s.a(a, "DB-Thread: EventManager.addTransformDataToDB , row=" + writableDatabase.insert(a.b, null, contentValues));
                                }
                                a(false);
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

    public int b(List<m> list) {
        synchronized (this.c) {
            if (list != null) {
                try {
                    if (list.size() != 0) {
                        try {
                            SQLiteDatabase writableDatabase = this.c.getWritableDatabase();
                            boolean z = true;
                            StringBuilder sb = new StringBuilder(((Long.toString(list.get(0).b()).length() + 1) * list.size()) + 16);
                            sb.append("_id");
                            sb.append(" in (");
                            int size = list.size();
                            int i2 = 0;
                            boolean z2 = false;
                            while (i2 < size) {
                                if (i2 == size - 1) {
                                    sb.append(list.get(i2).b());
                                } else {
                                    sb.append(list.get(i2).b());
                                    sb.append(com.xiaomi.onetrack.util.ac.b);
                                }
                                i2++;
                                z2 = true;
                            }
                            sb.append(")");
                            if (!z2) {
                                return 0;
                            }
                            com.xiaomi.onetrack.util.s.a(a, "*** *** deleted events sb id " + sb.toString());
                            int iDelete = writableDatabase.delete(a.b, sb.toString(), null);
                            com.xiaomi.onetrack.util.s.a(a, "*** *** deleted events count " + iDelete);
                            long jF = a().f();
                            if (jF != 0) {
                                z = false;
                            }
                            a(z);
                            com.xiaomi.onetrack.util.s.a(a, "after delete DB record remains=" + jF);
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

    /* JADX WARN: Code duplicated, block: B:70:0x0126 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Not initialized variable reg: 4, insn: 0x0097: MOVE (r2 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:13:0x0097 */
    public ab c() throws Throwable {
        Cursor cursor;
        ab abVar;
        Cursor cursorQuery;
        Cursor cursor2;
        boolean z;
        byte[] bArr;
        try {
            try {
                cursorQuery = this.c.getReadableDatabase().query(a.b, null, null, null, null, null, j, String.valueOf(i));
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
                            g();
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
                        } else if (i4 == i) {
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

    public void d() {
        d.a(new o(this));
    }

    public synchronized boolean e() {
        return k;
    }

    public long f() {
        return DatabaseUtils.queryNumEntries(this.c.getReadableDatabase(), a.b);
    }
}
