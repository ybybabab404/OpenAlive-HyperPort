package com.xiaomi.onetrack.c;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.Calendar;

/* JADX INFO: loaded from: classes2.dex */
class w implements Runnable {
    final /* synthetic */ r a;

    public w(r rVar) {
        this.a = rVar;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x00d6 -> B:38:0x00b9). Please report as a decompilation issue!!! */
    @Override // java.lang.Runnable
    public void run() {
        if (this.a.i == null) {
            return;
        }
        synchronized (this.a.i) {
            Cursor cursorQuery = null;
            try {
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
                        cursorQuery = writableDatabase.query("events", new String[]{"timestamp"}, "timestamp < ? ", strArr, null, null, "timestamp ASC");
                        if (cursorQuery.getCount() != 0) {
                            int iDelete = writableDatabase.delete("events", "timestamp < ? ", strArr);
                            if (iDelete > 0) {
                                com.xiaomi.onetrack.util.s.d("SystemImpCacheManager", "*** delete obsolete record success, remains=" + this.a.m.addAndGet(-iDelete));
                            } else {
                                com.xiaomi.onetrack.util.s.d("SystemImpCacheManager", "*** delete obsolete record failed, remains=" + this.a.m.get());
                            }
                        }
                    } catch (Throwable th) {
                        if (cursorQuery == null) {
                            throw th;
                        }
                        try {
                            cursorQuery.close();
                            throw th;
                        } catch (Exception unused) {
                            throw th;
                        }
                    }
                } catch (Exception e) {
                    com.xiaomi.onetrack.util.s.d("SystemImpCacheManager", "remove obsolete messages failed with " + e);
                    if (cursorQuery != null) {
                    }
                }
                cursorQuery.close();
            } catch (Exception unused2) {
            }
        }
    }
}
