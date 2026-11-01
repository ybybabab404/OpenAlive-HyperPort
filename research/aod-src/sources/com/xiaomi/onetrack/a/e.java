package com.xiaomi.onetrack.a;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.s;
import java.util.Calendar;

/* JADX INFO: loaded from: classes2.dex */
class e implements Runnable {
    final /* synthetic */ c a;

    public e(c cVar) {
        this.a = cVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.a.i == null) {
            return;
        }
        synchronized (this.a.i) {
            Cursor cursorQuery = null;
            try {
                try {
                    SQLiteDatabase writableDatabase = this.a.i.getWritableDatabase();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(6, calendar.get(6) - 7);
                    calendar.set(11, 0);
                    calendar.set(12, 0);
                    calendar.set(13, 0);
                    String[] strArr = {Long.toString(calendar.getTimeInMillis())};
                    cursorQuery = writableDatabase.query(c.a.b, new String[]{"timestamp"}, "timestamp < ? ", strArr, null, null, "timestamp ASC");
                    if (cursorQuery.getCount() != 0) {
                        int iDelete = writableDatabase.delete(c.a.b, "timestamp < ? ", strArr);
                        s.a("AdMonitorManager", "*** deleted obsolete ad monitor count=" + iDelete);
                        if (iDelete > 0) {
                            s.a("AdMonitorManager", "delete obsolete ad monitor success, remains=" + this.a.j.addAndGet(-iDelete));
                        } else {
                            s.a("AdMonitorManager", "delete obsolete ad monitor failed, remains=" + this.a.j.get());
                        }
                    } else {
                        s.a("AdMonitorManager", "no obsolete ad monitor record, remains=" + this.a.j.get());
                    }
                } catch (Exception e) {
                    s.b("AdMonitorManager", "remove obsolete ad monitor failed with " + e.getMessage());
                }
                p.a(cursorQuery);
            } catch (Throwable th) {
                p.a(cursorQuery);
                throw th;
            }
        }
    }
}
