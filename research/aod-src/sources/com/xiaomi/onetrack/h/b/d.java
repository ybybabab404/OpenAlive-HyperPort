package com.xiaomi.onetrack.h.b;

import android.database.sqlite.SQLiteDatabase;
import com.xiaomi.onetrack.util.af;
import com.xiaomi.onetrack.util.s;

/* JADX INFO: loaded from: classes2.dex */
class d implements Runnable {
    final /* synthetic */ b a;

    public d(b bVar) {
        this.a = bVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            synchronized (this.a.d) {
                SQLiteDatabase writableDatabase = this.a.d.getWritableDatabase();
                writableDatabase.delete(a.a, "date < ?", new String[]{String.valueOf(af.a(7))});
                writableDatabase.delete(a.b, "date < ?", new String[]{String.valueOf(af.a(7))});
            }
        } catch (Exception e) {
            s.b("OTMonitorDBManager", "removeObsoleteInfo error: " + e.getMessage());
        }
    }
}
