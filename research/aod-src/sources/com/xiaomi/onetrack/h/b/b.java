package com.xiaomi.onetrack.h.b;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;
import com.xiaomi.onetrack.util.af;
import com.xiaomi.onetrack.util.j;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.s;
import java.util.HashMap;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes2.dex */
public class b {
    public static final int a = 7;
    private static final String b = "OTMonitorDBManager";
    private static b c;
    private a d = new a(com.xiaomi.onetrack.f.a.a());

    private b() {
        c();
    }

    private int a(SQLiteDatabase sQLiteDatabase, String str, int i, String str2) {
        Cursor cursorQuery = null;
        int i2 = 0;
        try {
            cursorQuery = sQLiteDatabase.query(a.b, new String[]{a.h}, "app_id = ? AND date = ? AND event = ?", new String[]{str, String.valueOf(i), str2}, null, null, null);
            if (cursorQuery != null && cursorQuery.moveToFirst()) {
                i2 = cursorQuery.getInt(cursorQuery.getColumnIndexOrThrow(a.h));
            }
            return i2;
        } catch (Exception e) {
            s.b(b, "getEventStatsRecordCount error: " + e.getMessage());
            return 0;
        } finally {
            p.b(cursorQuery);
        }
    }

    private int a(SQLiteDatabase sQLiteDatabase, String str, int i, String str2, String str3) {
        Cursor cursorQuery = null;
        int i2 = 0;
        try {
            cursorQuery = sQLiteDatabase.query(a.a, new String[]{a.h}, "app_id = ? AND date = ? AND stage = ? AND reason = ?", new String[]{str, String.valueOf(i), str2, str3}, null, null, null);
            if (cursorQuery != null && cursorQuery.moveToFirst()) {
                i2 = cursorQuery.getInt(cursorQuery.getColumnIndexOrThrow(a.h));
            }
            return i2;
        } catch (Exception e) {
            s.b(b, "getAppStatsRecordCount error: " + e.getMessage());
            return 0;
        } finally {
            p.b(cursorQuery);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Pair<Integer, String> a(SQLiteDatabase sQLiteDatabase, Cursor cursor) {
        Pair<Integer, String> pairB = b(sQLiteDatabase, cursor);
        return pairB == null ? c(sQLiteDatabase, cursor) : pairB;
    }

    public static b a() {
        if (c == null) {
            synchronized (b.class) {
                try {
                    if (c == null) {
                        c = new b();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public HashMap<String, HashMap<String, Integer>> a(SQLiteDatabase sQLiteDatabase, Cursor cursor, int i, String str) {
        HashMap<String, HashMap<String, Integer>> map = new HashMap<>();
        try {
            Cursor cursorQuery = sQLiteDatabase.query(a.a, new String[]{a.f, a.g, a.h}, "app_id = ? AND date = ?", new String[]{str, String.valueOf(i)}, "stage, reason", null, null);
            while (cursorQuery.moveToNext()) {
                String string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow(a.f));
                String string2 = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow(a.g));
                int i2 = cursorQuery.getInt(cursorQuery.getColumnIndexOrThrow(a.h));
                if (map.containsKey(string)) {
                    map.get(string).put(string2, Integer.valueOf(i2));
                } else {
                    HashMap<String, Integer> map2 = new HashMap<>();
                    map2.put(string2, Integer.valueOf(i2));
                    map.put(string, map2);
                }
            }
        } catch (Exception e) {
            s.b(b, "getAppStats error: " + e.getMessage());
        }
        return map;
    }

    private Pair<Integer, String> b(SQLiteDatabase sQLiteDatabase, Cursor cursor) {
        try {
            Cursor cursorQuery = sQLiteDatabase.query(a.a, new String[]{"app_id", "date"}, "date < ? ", new String[]{String.valueOf(af.a(0))}, null, null, "date DESC", "1");
            if (cursorQuery == null || !cursorQuery.moveToNext()) {
                return null;
            }
            return new Pair<>(Integer.valueOf(cursorQuery.getInt(cursorQuery.getColumnIndexOrThrow("date"))), cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("app_id")));
        } catch (Exception e) {
            s.b(b, "getKeyFromAppStats error: " + e.getMessage());
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public HashMap<String, Integer> b(SQLiteDatabase sQLiteDatabase, Cursor cursor, int i, String str) {
        HashMap<String, Integer> map = new HashMap<>();
        try {
            Cursor cursorQuery = sQLiteDatabase.query(a.b, new String[]{"event", a.h}, "app_id = ? AND date = ?", new String[]{str, String.valueOf(i)}, null, null, null);
            while (cursorQuery.moveToNext()) {
                map.put(cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("event")), Integer.valueOf(cursorQuery.getInt(cursorQuery.getColumnIndexOrThrow(a.h))));
            }
        } catch (Exception e) {
            s.b(b, "getEventStats error: " + e.getMessage());
        }
        return map;
    }

    private Pair<Integer, String> c(SQLiteDatabase sQLiteDatabase, Cursor cursor) {
        try {
            Cursor cursorQuery = sQLiteDatabase.query(a.b, new String[]{"app_id", "date"}, "date < ? ", new String[]{String.valueOf(af.a(0))}, null, null, "date DESC", "1");
            if (cursorQuery == null || !cursorQuery.moveToNext()) {
                return null;
            }
            return new Pair<>(Integer.valueOf(cursorQuery.getInt(cursorQuery.getColumnIndexOrThrow("date"))), cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("app_id")));
        } catch (Exception e) {
            s.b(b, "getKeyFromEventStats error: " + e.getMessage());
            return null;
        }
    }

    public void a(int i, String str) {
        try {
            synchronized (this.d) {
                SQLiteDatabase writableDatabase = this.d.getWritableDatabase();
                writableDatabase.delete(a.a, "date = ? AND app_id = ?", new String[]{String.valueOf(i), str});
                writableDatabase.delete(a.b, "date = ? AND app_id = ?", new String[]{String.valueOf(i), str});
            }
        } catch (Exception e) {
            s.b(b, "deleteOTMonitorInfo error: " + e.getMessage());
        }
    }

    public void a(String str, int i, String str2, int i2) {
        try {
            synchronized (this.d) {
                try {
                    SQLiteDatabase writableDatabase = this.d.getWritableDatabase();
                    int iA = a(writableDatabase, str, i, str2);
                    s.a(b, "addEventStatsRecord countInDB: " + iA);
                    if (iA > 0) {
                        writableDatabase.execSQL("UPDATE event_stats SET count = count + ? WHERE app_id = ? AND date = ? AND event = ?", new Object[]{Integer.valueOf(i2), str, Integer.valueOf(i), str2});
                    } else {
                        writableDatabase.execSQL("INSERT INTO event_stats (app_id, date, event, count) VALUES (?, ?, ?, ?)", new Object[]{str, Integer.valueOf(i), str2, Integer.valueOf(i2)});
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (Exception e) {
            s.b(b, "addEventStatsRecord error: " + e.getMessage());
        }
    }

    public void a(String str, int i, String str2, String str3, int i2) {
        try {
            synchronized (this.d) {
                try {
                    SQLiteDatabase writableDatabase = this.d.getWritableDatabase();
                    int iA = a(writableDatabase, str, i, str2, str3);
                    s.a(b, "addAppStatsRecord countInDB: " + iA);
                    if (iA > 0) {
                        writableDatabase.execSQL("UPDATE app_stats SET count = count + ? WHERE app_id = ? AND date = ? AND stage = ? AND reason = ?", new Object[]{Integer.valueOf(i2), str, Integer.valueOf(i), str2, str3});
                    } else {
                        writableDatabase.execSQL("INSERT INTO app_stats (app_id, date, stage, reason, count) VALUES (?, ?, ?, ?, ?)", new Object[]{str, Integer.valueOf(i), str2, str3, Integer.valueOf(i2)});
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (Exception e) {
            s.b(b, "addAppStatsRecord error: " + e.getMessage());
        }
    }

    public com.xiaomi.onetrack.h.c.a b() {
        try {
            FutureTask futureTask = new FutureTask(new c(this));
            j.a(futureTask);
            com.xiaomi.onetrack.h.c.a aVar = (com.xiaomi.onetrack.h.c.a) futureTask.get(10L, TimeUnit.SECONDS);
            if (aVar != null) {
                return aVar;
            }
            return null;
        } catch (Exception e) {
            s.b(b, "getRequestFailedInfo futureTask error: " + e.getMessage());
            return null;
        }
    }

    public void c() {
        j.a(new d(this));
    }
}
