package com.xiaomi.onetrack.util.oaid.helpers;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.xiaomi.onetrack.api.am;
import com.xiaomi.onetrack.util.s;

/* JADX INFO: loaded from: classes2.dex */
public class m {
    private static final String a = "VivoDeviceIDHelper";

    public String a(Context context) {
        String string = "";
        try {
            Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/OAID"), null, null, null, null);
            if (cursorQuery != null) {
                string = cursorQuery.moveToNext() ? cursorQuery.getString(cursorQuery.getColumnIndex(am.t)) : "";
                cursorQuery.close();
            }
            return string;
        } catch (Exception e) {
            s.a(a, e.getMessage());
            return string;
        }
    }
}
