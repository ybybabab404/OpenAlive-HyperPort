package com.miui.maml.util;

import androidx.annotation.Keep;

/* JADX INFO: loaded from: classes2.dex */
@Keep
public class SystemProperties {
    private static boolean isSupportGet = false;
    private static boolean isSupportGetBoolean = false;
    private static boolean isSupportGetInt = false;
    private static boolean isSupportGetLong = false;

    static {
        Class<?> cls;
        try {
            cls = Class.forName("android.os.SystemProperties");
        } catch (Exception unused) {
            cls = null;
        }
        if (cls != null) {
            boolean z = true;
            try {
                isSupportGet = cls.getMethod("get", String.class, String.class) != null;
            } catch (Exception unused2) {
                isSupportGet = false;
            }
            try {
                isSupportGetInt = cls.getMethod("getInt", String.class, Integer.TYPE) != null;
            } catch (Exception unused3) {
                isSupportGetInt = false;
            }
            try {
                isSupportGetLong = cls.getMethod("getLong", String.class, Long.TYPE) != null;
            } catch (Exception unused4) {
                isSupportGetLong = false;
            }
            try {
                if (cls.getMethod("getBoolean", String.class, Boolean.TYPE) == null) {
                    z = false;
                }
                isSupportGetBoolean = z;
            } catch (Exception unused5) {
                isSupportGetBoolean = false;
            }
        }
    }

    public static String get(String str) {
        return get(str, "");
    }

    public static String get(String str, String str2) {
        return isSupportGet ? android.os.SystemProperties.get(str, str2) : str2;
    }

    public static boolean getBoolean(String str, boolean z) {
        return isSupportGetBoolean ? android.os.SystemProperties.getBoolean(str, z) : z;
    }

    public static int getInt(String str, int i) {
        return isSupportGetInt ? android.os.SystemProperties.getInt(str, i) : i;
    }

    public static long getLong(String str, long j) {
        return isSupportGetLong ? android.os.SystemProperties.getLong(str, j) : j;
    }
}
