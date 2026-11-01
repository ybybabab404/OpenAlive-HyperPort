package com.xiaomi.onetrack.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteBlobTooBigException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.xiaomi.onetrack.b.o;
import com.xiaomi.onetrack.util.ac;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.s;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes2.dex */
public class c {
    private static final String a = "AdMonitorManager";
    private static final int b = 204800;
    private static final int c = 100;
    private static final int d = 4;
    private static final int e = 300;
    private static final String f = "_id ASC";
    private static final int g = 7;
    private static c h;
    private final a i;
    private final AtomicLong j;

    public static class a extends SQLiteOpenHelper {
        public static final String a = "onetrack_ad";
        public static final String b = "monitor";
        public static final String c = "_id";
        public static final String d = "appid";
        public static final String e = "package";
        public static final String f = "event_name";
        public static final String g = "url";
        public static final String h = "timestamp";
        public static final String i = "send_count";
        public static final String j = "ot_event_id";
        public static final String k = "url_count";
        public static final String l = "url_index";
        private static final String m = "drop table if exists %s";
        private static final int n = 2;
        private static final String o = "CREATE TABLE monitor (_id INTEGER PRIMARY KEY AUTOINCREMENT,appid TEXT,package TEXT,event_name TEXT,url TEXT,ot_event_id TEXT,send_count INTEGER DEFAULT 0,url_count INTEGER DEFAULT 0,url_index INTEGER DEFAULT 0,timestamp INTEGER)";

        public a(Context context) {
            super(context, a, (SQLiteDatabase.CursorFactory) null, 2);
        }

        private void a(SQLiteDatabase sQLiteDatabase) {
            try {
                sQLiteDatabase.execSQL(o);
            } catch (Throwable th) {
                s.b(c.a, "createTable error:" + th.getMessage());
                th.printStackTrace();
            }
        }

        private void b(SQLiteDatabase sQLiteDatabase) {
            try {
                sQLiteDatabase.execSQL(String.format(m, b));
            } catch (Throwable th) {
                s.b(c.a, "dropTable error:" + th.getMessage());
                th.printStackTrace();
            }
        }

        private void c(SQLiteDatabase sQLiteDatabase) {
            try {
                sQLiteDatabase.execSQL("ALTER TABLE monitor ADD COLUMN ot_event_id TEXT;");
                sQLiteDatabase.execSQL("ALTER TABLE monitor ADD COLUMN url_count INTEGER DEFAULT 0;");
                sQLiteDatabase.execSQL("ALTER TABLE monitor ADD COLUMN url_index INTEGER DEFAULT 0;");
            } catch (Throwable th) {
                s.b(c.a, "adMTableAddColumn error:" + th.getMessage());
                th.printStackTrace();
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            a(sQLiteDatabase);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
            b(sQLiteDatabase);
            a(sQLiteDatabase);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
            if (i3 == 2 && i2 == 1) {
                c(sQLiteDatabase);
            }
        }
    }

    private c() {
        AtomicLong atomicLong = new AtomicLong(0L);
        this.j = atomicLong;
        this.i = new a(com.xiaomi.onetrack.f.a.a());
        com.xiaomi.onetrack.a.c.b.a().a(true);
        long jF = f();
        atomicLong.set(jF);
        s.a(a, "AdMonitorManager init, There are " + jF + " events in DB");
        if (jF > 0) {
            d();
        }
    }

    public static c a() {
        if (h == null) {
            a(com.xiaomi.onetrack.f.a.a());
        }
        return h;
    }

    public static void a(Context context) {
        if (h == null) {
            synchronized (c.class) {
                try {
                    if (h == null) {
                        h = new c();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.xiaomi.onetrack.a.b.a aVar) {
        synchronized (this.i) {
            try {
                if (!aVar.k()) {
                    s.c(a, "addAdMonitorToDatabase event is inValid, event:" + aVar.a());
                    return;
                }
                SQLiteDatabase writableDatabase = this.i.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("appid", aVar.e());
                contentValues.put("package", aVar.f());
                contentValues.put("event_name", aVar.a());
                contentValues.put("timestamp", Long.valueOf(aVar.d()));
                contentValues.put(a.g, aVar.c());
                contentValues.put(a.j, aVar.j());
                contentValues.put(a.k, Integer.valueOf(aVar.h()));
                contentValues.put("url_index", Integer.valueOf(aVar.i()));
                long jInsert = writableDatabase.insert(a.b, null, contentValues);
                s.a(a, "DB-Thread: AdMonitorManager.addAdMonitorToDatabase , row=" + jInsert);
                if (jInsert != -1) {
                    s.a(a, "添加后，ad monitor url 中事件个数为 " + this.j.incrementAndGet());
                }
            } catch (Throwable th) {
                s.b(a, "addAdMonitorToDatabase Throwable：" + th.getMessage());
            }
        }
    }

    private long f() {
        try {
            return DatabaseUtils.queryNumEntries(this.i.getReadableDatabase(), a.b);
        } catch (Exception e2) {
            s.b(a, "getTotalEventsNumberSync failed with " + e2.getMessage());
            return 0L;
        }
    }

    private void g() {
        try {
            int iDelete = this.i.getWritableDatabase().delete(a.b, null, null);
            if (iDelete > 0) {
                s.a(a, "delete table success, remains=" + this.j.addAndGet(-iDelete));
                return;
            }
            s.a(a, "delete table failed, remains=" + this.j.get());
        } catch (Exception e2) {
            s.b(a, "deleteTable failed with " + e2.getMessage());
        }
    }

    public int a(ArrayList<Integer> arrayList) {
        synchronized (this.i) {
            if (arrayList != null) {
                try {
                    if (arrayList.size() != 0) {
                        try {
                            SQLiteDatabase writableDatabase = this.i.getWritableDatabase();
                            StringBuilder sb = new StringBuilder(((Long.toString(arrayList.get(0).intValue()).length() + 1) * arrayList.size()) + 16);
                            sb.append("_id");
                            sb.append(" in (");
                            sb.append(arrayList.get(0));
                            int size = arrayList.size();
                            for (int i = 1; i < size; i++) {
                                sb.append(ac.b);
                                sb.append(arrayList.get(i));
                            }
                            sb.append(")");
                            int iDelete = writableDatabase.delete(a.b, sb.toString(), null);
                            s.a(a, "*** *** deleted ad monitor count " + iDelete);
                            if (iDelete > 0) {
                                s.a(a, "delete ad monitors success, remains=" + this.j.addAndGet(-iDelete));
                            } else {
                                s.a(a, "delete ad monitors failed, remains=" + this.j.get());
                            }
                            return iDelete;
                        } catch (Exception e2) {
                            s.b(a, "e=" + e2);
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

    public void a(com.xiaomi.onetrack.f.b bVar) {
        try {
            com.xiaomi.onetrack.a.a.a.a(new d(this, bVar));
        } catch (Throwable th) {
            s.a(a, "filterAdMonitor Throwable：" + th.getMessage());
        }
    }

    public boolean a(long j, long j2, int i) {
        if (i <= 0) {
            return true;
        }
        if (i < 4) {
            if (Math.abs(j - j2) >= o.a(i - 1)) {
                return true;
            }
        }
        return false;
    }

    public long b() {
        return this.j.get();
    }

    public void b(ArrayList<Integer> arrayList) {
        String str;
        String str2;
        synchronized (this.i) {
            if (arrayList != null) {
                try {
                    try {
                        if (arrayList.size() > 0) {
                            SQLiteDatabase writableDatabase = null;
                            try {
                                try {
                                    writableDatabase = this.i.getWritableDatabase();
                                    writableDatabase.beginTransaction();
                                    for (Integer num : arrayList) {
                                        num.intValue();
                                        writableDatabase.execSQL(String.format("update %s set %s = %s + 1 where %s = %s", a.b, a.i, a.i, "_id", num));
                                    }
                                    writableDatabase.setTransactionSuccessful();
                                    try {
                                        writableDatabase.endTransaction();
                                        writableDatabase.close();
                                    } catch (Exception e2) {
                                        str = a;
                                        str2 = "addAdMonitorsRetryCount endTransaction error: " + e2.getMessage();
                                        s.b(str, str2);
                                    }
                                } catch (Throwable th) {
                                    if (0 != 0) {
                                        try {
                                            writableDatabase.endTransaction();
                                            writableDatabase.close();
                                        } catch (Exception e3) {
                                            s.b(a, "addAdMonitorsRetryCount endTransaction error: " + e3.getMessage());
                                        }
                                    }
                                    throw th;
                                }
                            } catch (Exception e4) {
                                s.b(a, "addAdMonitorsRetryCount Exception: " + e4.getMessage());
                                if (writableDatabase != null) {
                                    try {
                                        writableDatabase.endTransaction();
                                        writableDatabase.close();
                                    } catch (Exception e5) {
                                        str = a;
                                        str2 = "addAdMonitorsRetryCount endTransaction error: " + e5.getMessage();
                                        s.b(str, str2);
                                    }
                                }
                            }
                        }
                    } catch (Throwable th2) {
                        s.a(a, "addAdMonitorsRetryCount Throwable:" + th2.getMessage());
                    }
                } catch (Throwable th3) {
                    throw th3;
                }
            }
        }
    }

    public com.xiaomi.onetrack.a.c.a c() {
        Cursor cursor;
        com.xiaomi.onetrack.a.c.a aVar;
        Cursor cursorQuery;
        Cursor cursor2;
        int i;
        this = this;
        synchronized (this.i) {
            try {
                try {
                    try {
                        cursorQuery = this.i.getReadableDatabase().query(a.b, null, null, null, null, null, f);
                        try {
                            try {
                                int columnIndex = cursorQuery.getColumnIndex("_id");
                                cursorQuery.getColumnIndex("appid");
                                int columnIndex2 = cursorQuery.getColumnIndex("package");
                                int columnIndex3 = cursorQuery.getColumnIndex("event_name");
                                int columnIndex4 = cursorQuery.getColumnIndex("timestamp");
                                int columnIndex5 = cursorQuery.getColumnIndex(a.g);
                                int columnIndex6 = cursorQuery.getColumnIndex(a.i);
                                int columnIndex7 = cursorQuery.getColumnIndex(a.j);
                                int columnIndex8 = cursorQuery.getColumnIndex(a.k);
                                int columnIndex9 = cursorQuery.getColumnIndex("url_index");
                                ArrayList arrayList = new ArrayList();
                                int i2 = columnIndex8;
                                int i3 = columnIndex9;
                                long jCurrentTimeMillis = System.currentTimeMillis();
                                int i4 = 0;
                                while (true) {
                                    if (!cursorQuery.moveToNext()) {
                                        arrayList = arrayList;
                                        cursor2 = cursorQuery;
                                        aVar = null;
                                        i = i4;
                                        break;
                                    }
                                    int i5 = i3;
                                    int i6 = cursorQuery.getInt(columnIndex6);
                                    ArrayList arrayList2 = arrayList;
                                    int i7 = i2;
                                    long j = cursorQuery.getLong(columnIndex4);
                                    columnIndex4 = columnIndex4;
                                    i3 = i5;
                                    aVar = null;
                                    columnIndex6 = columnIndex6;
                                    arrayList = arrayList2;
                                    try {
                                        if (this.a(jCurrentTimeMillis, j, i6)) {
                                            String string = cursorQuery.getString(columnIndex2);
                                            long j2 = jCurrentTimeMillis;
                                            String string2 = cursorQuery.getString(columnIndex3);
                                            String string3 = cursorQuery.getString(columnIndex7);
                                            int i8 = columnIndex2;
                                            int i9 = cursorQuery.getInt(i7);
                                            int i10 = cursorQuery.getInt(i3);
                                            int i11 = columnIndex3;
                                            int i12 = cursorQuery.getInt(columnIndex);
                                            int i13 = columnIndex;
                                            String string4 = cursorQuery.getString(columnIndex5);
                                            cursor2 = cursorQuery;
                                            try {
                                                com.xiaomi.onetrack.a.b.a aVar2 = new com.xiaomi.onetrack.a.b.a();
                                                aVar2.b(string4);
                                                aVar2.a(i12);
                                                aVar2.a(j);
                                                aVar2.b(i6);
                                                aVar2.a(string2);
                                                aVar2.d(string);
                                                aVar2.e(string3);
                                                aVar2.c(i9);
                                                aVar2.d(i10);
                                                arrayList.add(aVar2);
                                                i = i4 + 1;
                                                if (i >= 100) {
                                                    break;
                                                }
                                                i4 = i;
                                                columnIndex2 = i8;
                                                jCurrentTimeMillis = j2;
                                                i2 = i7;
                                                columnIndex3 = i11;
                                                columnIndex = i13;
                                                cursorQuery = cursor2;
                                            } catch (SQLiteBlobTooBigException e2) {
                                                e = e2;
                                                cursorQuery = cursor2;
                                                s.b(a, "blob too big ***", e);
                                                g();
                                                p.a(cursorQuery);
                                                return aVar;
                                            } catch (Exception e3) {
                                                e = e3;
                                                cursorQuery = cursor2;
                                                s.b(a, "getAdMonitorDataSync failed, ", e);
                                                p.a(cursorQuery);
                                                return aVar;
                                            } catch (Throwable th) {
                                                th = th;
                                                cursor = cursor2;
                                                p.a(cursor);
                                                throw th;
                                            }
                                        } else {
                                            i2 = i7;
                                        }
                                    } catch (SQLiteBlobTooBigException e4) {
                                        e = e4;
                                        s.b(a, "blob too big ***", e);
                                        g();
                                        p.a(cursorQuery);
                                        return aVar;
                                    } catch (Exception e5) {
                                        e = e5;
                                        s.b(a, "getAdMonitorDataSync failed, ", e);
                                        p.a(cursorQuery);
                                        return aVar;
                                    }
                                }
                                boolean z = true;
                                if (arrayList.size() > 0) {
                                    s.a(a, "get ad monitor size :" + arrayList.size());
                                    if (cursor2.isAfterLast()) {
                                        s.a(a, "cursor isAfterLast");
                                    } else {
                                        z = false;
                                    }
                                }
                                com.xiaomi.onetrack.a.c.a aVar3 = new com.xiaomi.onetrack.a.c.a(i, arrayList, z);
                                p.a(cursor2);
                                return aVar3;
                            } catch (Throwable th2) {
                                th = th2;
                                cursor2 = cursorQuery;
                            }
                        } catch (SQLiteBlobTooBigException e6) {
                            e = e6;
                            aVar = null;
                        } catch (Exception e7) {
                            e = e7;
                            aVar = null;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        cursor = cursorQuery;
                    }
                } catch (SQLiteBlobTooBigException e8) {
                    e = e8;
                    aVar = null;
                    cursorQuery = null;
                } catch (Exception e9) {
                    e = e9;
                    aVar = null;
                    cursorQuery = null;
                } catch (Throwable th4) {
                    th = th4;
                    cursor = null;
                }
            } catch (Throwable th5) {
                throw th5;
            }
        }
    }

    public void d() {
        com.xiaomi.onetrack.a.a.a.a(new e(this), 1000L);
    }

    public void e() {
        synchronized (this.i) {
            Cursor cursorQuery = null;
            try {
                try {
                    SQLiteDatabase writableDatabase = this.i.getWritableDatabase();
                    String[] strArr = {Long.toString(4L)};
                    cursorQuery = writableDatabase.query(a.b, new String[]{"timestamp"}, "send_count >= ? ", strArr, null, null, f);
                    if (cursorQuery.getCount() != 0) {
                        int iDelete = writableDatabase.delete(a.b, "send_count >= ? ", strArr);
                        s.a(a, "*** deleted retry expired ad monitor count=" + iDelete);
                        if (iDelete > 0) {
                            s.a(a, "delete retry expired ad monitor success, remains=" + this.j.addAndGet(-iDelete));
                        } else {
                            s.a(a, "delete retry expired ad monitor failed, remains=" + this.j.get());
                        }
                    } else {
                        s.a(a, "no retry expired ad monitor record, remains=" + this.j.get());
                    }
                } catch (Exception e2) {
                    s.b(a, "remove retry expired ad monitor failed with " + e2.getMessage());
                }
                p.a(cursorQuery);
            } catch (Throwable th) {
                p.a(cursorQuery);
                throw th;
            }
        }
    }
}
