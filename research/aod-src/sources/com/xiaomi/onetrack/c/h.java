package com.xiaomi.onetrack.c;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.Calendar;

/* JADX INFO: loaded from: classes2.dex */
class h implements Runnable {
    final /* synthetic */ e a;

    public h(e eVar) {
        this.a = eVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.a.k == null) {
            return;
        }
        synchronized (this.a.k) {
            Cursor cursorQuery = null;
            try {
                try {
                    SQLiteDatabase writableDatabase = this.a.k.getWritableDatabase();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(6, calendar.get(6) - 7);
                    calendar.set(11, 0);
                    calendar.set(12, 0);
                    calendar.set(13, 0);
                    String[] strArr = {Long.toString(calendar.getTimeInMillis())};
                    cursorQuery = writableDatabase.query("events", new String[]{"appid", "count(*) as count"}, "timestamp < ? ", strArr, "appid", null, null);
                    if (cursorQuery.getCount() != 0) {
                        while (cursorQuery.moveToNext()) {
                            String string = cursorQuery.getString(cursorQuery.getColumnIndex("appid"));
                            int i = cursorQuery.getInt(cursorQuery.getColumnIndex(com.xiaomi.onetrack.h.b.a.h));
                            com.xiaomi.onetrack.util.s.a("EventManager", "removeObsoleteEvents: appId=" + string + ", count=" + i);
                            com.xiaomi.onetrack.h.d.b.a(string, "", com.xiaomi.onetrack.h.d.a.k, "", i);
                        }
                        com.xiaomi.onetrack.util.s.a("EventManager", "*** deleted obsolete item count=" + writableDatabase.delete("events", "timestamp < ? ", strArr));
                    }
                    long jD = e.a().d();
                    com.xiaomi.onetrack.b.o.a(jD == 0);
                    com.xiaomi.onetrack.util.s.a("EventManager", "after delete obsolete record remains=" + jD);
                } catch (Exception e) {
                    com.xiaomi.onetrack.util.s.d("EventManager", "remove obsolete events failed with " + e);
                    if (cursorQuery != null) {
                    }
                }
                cursorQuery.close();
            } catch (Throwable th) {
                if (cursorQuery == null) {
                    throw th;
                }
                cursorQuery.close();
                throw th;
            }
        }
    }
}
