package com.xiaomi.onetrack.c;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.Calendar;

/* JADX INFO: loaded from: classes2.dex */
class o implements Runnable {
    final /* synthetic */ n a;

    public o(n nVar) {
        this.a = nVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.a.c == null) {
            return;
        }
        synchronized (this.a.c) {
            Cursor cursorQuery = null;
            try {
                try {
                    SQLiteDatabase writableDatabase = this.a.c.getWritableDatabase();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(6, calendar.get(6) - 7);
                    calendar.set(11, 0);
                    calendar.set(12, 0);
                    calendar.set(13, 0);
                    String[] strArr = {Long.toString(calendar.getTimeInMillis())};
                    cursorQuery = writableDatabase.query(n.a.b, new String[]{"appid", "count(*) as count"}, "timestamp < ? ", strArr, "appid", null, null);
                    if (cursorQuery.getCount() != 0) {
                        while (cursorQuery.moveToNext()) {
                            String string = cursorQuery.getString(cursorQuery.getColumnIndex("appid"));
                            int i = cursorQuery.getInt(cursorQuery.getColumnIndex(com.xiaomi.onetrack.h.b.a.h));
                            com.xiaomi.onetrack.util.s.a("PubSubEventManager", "removeObsoleteEvents: appId=" + string + ", count=" + i);
                            com.xiaomi.onetrack.h.d.b.a(string, "", com.xiaomi.onetrack.h.d.a.k, "", i);
                        }
                        com.xiaomi.onetrack.util.s.a("PubSubEventManager", "*** deleted obsolete item count=" + writableDatabase.delete(n.a.b, "timestamp < ? ", strArr));
                    }
                    long jF = n.a().f();
                    this.a.a(jF == 0);
                    com.xiaomi.onetrack.util.s.a("PubSubEventManager", "after delete obsolete record remains=" + jF);
                } catch (Exception e) {
                    com.xiaomi.onetrack.util.s.d("PubSubEventManager", "remove obsolete messages failed with " + e);
                    if (cursorQuery != null) {
                    }
                }
                try {
                    cursorQuery.close();
                } catch (Exception unused) {
                }
            } catch (Throwable th) {
                if (cursorQuery == null) {
                    throw th;
                }
                try {
                    cursorQuery.close();
                    throw th;
                } catch (Exception unused2) {
                    throw th;
                }
            }
        }
    }
}
