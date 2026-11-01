package com.xiaomi.onetrack.util;

import android.content.Context;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes2.dex */
public class q {
    private static final String a = "IdentifierManager";
    private static Object b;
    private static Class<?> c;
    private static Method d;

    static {
        try {
            Class<?> cls = Class.forName("com.android.id.impl.IdProviderImpl");
            c = cls;
            b = cls.newInstance();
            d = c.getMethod("getOAID", Context.class);
        } catch (Exception e) {
            s.a(a, "reflect exception!", e);
        }
    }

    public static String a(Context context) {
        return a(context, d);
    }

    private static String a(Context context, Method method) {
        Object obj;
        if (context == null || (obj = b) == null || method == null) {
            return "";
        }
        try {
            Object objInvoke = method.invoke(obj, context);
            return objInvoke != null ? (String) objInvoke : "";
        } catch (Throwable th) {
            s.a(a, "oaid invoke exception!", th);
            return "";
        }
    }

    public static boolean a() {
        return (c == null || b == null) ? false : true;
    }
}
