package com.xiaomi.onetrack.b;

import android.text.TextUtils;
import com.xiaomi.onetrack.util.s;
import com.xiaomi.onetrack.util.t;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class o {
    private static String a = "ConfigProvider";
    private static volatile boolean b = false;
    private static volatile boolean c = true;

    public static int a(int i) {
        int iIntValue;
        if (s.b) {
            s.a(a, "debug upload mode, send events immediately");
            return 0;
        }
        try {
            iIntValue = g.a().d().get(Integer.valueOf(i + 1)).intValue();
        } catch (Exception unused) {
            iIntValue = 60000;
        }
        s.a(a, "getUploadInterval " + iIntValue);
        return iIntValue;
    }

    public static String a(JSONObject jSONObject, String str, String str2, String str3) {
        if (jSONObject != null) {
            try {
                if (jSONObject.has(str2)) {
                    return jSONObject.optString(str2);
                }
            } catch (Exception e) {
                s.c(a, "getEventString error: " + e.getMessage());
                return str3;
            }
        }
        a("getEventString", str, str2);
        return str3;
    }

    public static JSONArray a(JSONObject jSONObject, String str, String str2, JSONArray jSONArray) {
        if (jSONObject != null) {
            try {
                if (jSONObject.has(str2)) {
                    return jSONObject.optJSONArray(str2);
                }
            } catch (Exception e) {
                s.c(a, "getEventJSONArray error: " + e.getMessage());
                return jSONArray;
            }
        }
        a("getEventJSONArray", str, str2);
        return jSONArray;
    }

    private static void a(String str, String str2, String str3) {
        s.a(a, str + " config not available, use default value eventName:" + str2 + " key:" + str3);
    }

    public static synchronized void a(boolean z) {
        b = z;
    }

    public static boolean a() {
        try {
            String[] strArrB = com.xiaomi.onetrack.d.f.a().b();
            return (!TextUtils.isEmpty(strArrB[0]) && !TextUtils.isEmpty(strArrB[1])) && !t.a(a);
        } catch (Exception e) {
            s.a(a, "ConfigProvider.available", e);
            return false;
        }
    }

    public static boolean a(String str) {
        return j.a().e(str) == 2;
    }

    public static boolean a(JSONObject jSONObject, String str, String str2, boolean z) {
        if (jSONObject != null) {
            try {
                if (jSONObject.has(str2)) {
                    return jSONObject.optBoolean(str2);
                }
            } catch (Exception e) {
                s.c(a, "getEventBoolean error: " + e.getMessage());
                return z;
            }
        }
        a("getEventBoolean", str, str2);
        return z;
    }

    public static void b(boolean z) {
        c = z;
    }

    public static synchronized boolean b() {
        return b;
    }

    public static boolean b(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return j.a().b(str, a.h);
    }

    public static boolean c() {
        return c;
    }

    public static boolean c(String str) {
        try {
            return j.a().b(str, a.a);
        } catch (Exception e) {
            s.b(a, "isDisableTrackForApp error: " + e.toString());
            return false;
        }
    }
}
