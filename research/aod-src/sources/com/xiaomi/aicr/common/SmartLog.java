package com.xiaomi.aicr.common;

import android.util.Log;

/* JADX INFO: loaded from: classes2.dex */
public class SmartLog {
    public static boolean sDebug = false;

    static {
        try {
            Class<?> cls = Class.forName("com.xiaomi.aicr.BuildConfig");
            sDebug = ((String) cls.getField("BUILD_TYPE").get(null)).equals("debug") || ((String) cls.getField("FLAVOR").get(null)).equals("demo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int d(String str, String str2) {
        if (!sDebug) {
            return 0;
        }
        return Log.d("AiCrEngine_" + str, str2);
    }

    public static int e(String str, String str2) {
        return Log.e("AiCrEngine_" + str, str2);
    }

    public static int e(String str, String str2, Exception exc) {
        return Log.e("AiCrEngine_" + str, str2, exc);
    }

    public static int i(String str, String str2) {
        return Log.i("AiCrEngine_" + str, str2);
    }
}
