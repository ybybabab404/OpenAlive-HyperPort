package com.xiaomi.onetrack.b;

import android.os.Message;
import android.text.TextUtils;
import com.xiaomi.onetrack.c.ag;
import com.xiaomi.onetrack.util.s;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
class c implements Runnable {
    final /* synthetic */ JSONObject a;
    final /* synthetic */ a b;

    public c(a aVar, JSONObject jSONObject) {
        this.b = aVar;
        this.a = jSONObject;
    }

    @Override // java.lang.Runnable
    public void run() {
        s.a("AppConfigUpdater", "checkAppConfigVersion start");
        JSONArray jSONArrayOptJSONArray = this.a.optJSONArray(ag.a);
        if (jSONArrayOptJSONArray != null) {
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
                String strOptString = jSONObjectOptJSONObject == null ? "" : jSONObjectOptJSONObject.optString("appId");
                s.a("AppConfigUpdater", "appId: " + strOptString);
                if (!TextUtils.isEmpty(strOptString)) {
                    int iOptInt = jSONObjectOptJSONObject == null ? 0 : jSONObjectOptJSONObject.optInt("version");
                    int iD = j.a().d(strOptString);
                    boolean zH = a.h(strOptString);
                    boolean z = a.G.containsKey(strOptString) && ((Boolean) a.G.get(strOptString)).booleanValue();
                    s.a("AppConfigUpdater", "local version: " + iD + ", server version: " + iOptInt + ", canUpdate: " + zH + ", isScheduling: " + z);
                    if (iOptInt > 0 && iOptInt > iD && zH && !z) {
                        Message messageObtain = Message.obtain();
                        messageObtain.what = 0;
                        messageObtain.obj = strOptString;
                        long jRandom = (long) (Math.random() * 1800000.0d);
                        s.a("AppConfigUpdater", "the message will be handled after " + jRandom + " ms");
                        a.E.sendMessageDelayed(messageObtain, jRandom);
                        a.G.put(strOptString, Boolean.TRUE);
                    }
                }
            }
        }
    }
}
