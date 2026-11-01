package com.miui.maml.util;

import android.util.Log;

/* JADX INFO: loaded from: classes2.dex */
public class MamlLog {
    private MamlLog() {
    }

    public static void d(String str, String str2) {
        if (3 >= getLogLevel()) {
            Log.d(str, str2);
        }
    }

    public static void e(String str, String str2) {
        if (6 >= getLogLevel()) {
            Log.e(str, str2);
        }
    }

    public static void e(String str, String str2, Throwable th) {
        if (6 >= getLogLevel()) {
            Log.e(str, str2, th);
        }
    }

    public static int getLogLevel() {
        String str = SystemProperties.get("log.tag.MAML_LOG_LEVEL", "WARN");
        str.getClass();
        switch (str) {
            case "INFO":
                return 4;
            case "DEBUG":
                return 3;
            case "ERROR":
                return 6;
            case "VERBOSE":
                return 2;
            default:
                return 5;
        }
    }

    public static void i(String str, String str2) {
        if (4 >= getLogLevel()) {
            Log.i(str, str2);
        }
    }

    public static void w(String str, String str2) {
        if (5 >= getLogLevel()) {
            Log.w(str, str2);
        }
    }
}
