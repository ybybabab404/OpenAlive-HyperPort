package com.xiaomi.onetrack.b;

import android.database.Cursor;
import android.text.TextUtils;
import com.xiaomi.onetrack.util.s;
import java.util.concurrent.Callable;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
class l implements Callable<m> {
    final /* synthetic */ String a;
    final /* synthetic */ j b;

    public l(j jVar, String str) {
        this.b = jVar;
        this.a = str;
    }

    /* JADX WARN: Code duplicated, block: B:36:0x0098 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.util.concurrent.Callable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public m call() throws Exception {
        Cursor cursorQuery;
        Cursor cursor = null;
        try {
            cursorQuery = this.b.c.getWritableDatabase().query(i.b, null, "app_id=?", new String[]{this.a}, null, null, null);
            try {
                try {
                    int columnIndex = cursorQuery.getColumnIndex("app_id");
                    int columnIndex2 = cursorQuery.getColumnIndex(i.e);
                    int columnIndex3 = cursorQuery.getColumnIndex(i.f);
                    int columnIndex4 = cursorQuery.getColumnIndex("timestamp");
                    if (cursorQuery.moveToNext()) {
                        m mVar = new m();
                        mVar.a = cursorQuery.getString(columnIndex);
                        String string = cursorQuery.getString(columnIndex2);
                        if (!TextUtils.isEmpty(string)) {
                            mVar.e = new JSONObject(string);
                        }
                        mVar.b = j.b(mVar.e);
                        mVar.d = cursorQuery.getString(columnIndex3);
                        mVar.c = cursorQuery.getLong(columnIndex4);
                        try {
                            cursorQuery.close();
                            return mVar;
                        } catch (Exception unused) {
                            s.a("ConfigDbManager", "getConfig  cursor.close");
                            return mVar;
                        }
                    }
                } catch (Exception e) {
                    e = e;
                    s.a("ConfigDbManager", e.getMessage());
                    if (cursorQuery != null) {
                    }
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                cursor = cursorQuery;
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Exception unused2) {
                        s.a("ConfigDbManager", "getConfig  cursor.close");
                    }
                }
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            cursorQuery = null;
        } catch (Throwable th2) {
            th = th2;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        try {
            cursorQuery.close();
        } catch (Exception unused3) {
            s.a("ConfigDbManager", "getConfig  cursor.close");
        }
        return null;
    }
}
