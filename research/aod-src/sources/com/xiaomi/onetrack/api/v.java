package com.xiaomi.onetrack.api;

import android.text.TextUtils;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
class v implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ c b;

    public v(c cVar, String str) {
        this.b = cVar;
        this.a = str;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            String strA = com.xiaomi.onetrack.util.n.a(com.xiaomi.onetrack.util.u.a(this.b.e));
            if (TextUtils.isEmpty(strA)) {
                return;
            }
            JSONObject jSONObject = new JSONObject(strA);
            jSONObject.remove(this.a);
            com.xiaomi.onetrack.util.n.a(com.xiaomi.onetrack.util.u.a(this.b.e), jSONObject.toString());
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b("BaseOneTrackImp", "removeCommonProperty error:" + e.toString());
        }
    }
}
