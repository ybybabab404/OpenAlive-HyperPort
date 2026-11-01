package cn.kuaipan.android.utils;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class ContextUtils {
    public static volatile Context sMyContext;

    public static Context getContext() {
        Context context;
        Context context2 = sMyContext;
        if (context2 != null) {
            return context2;
        }
        synchronized (ContextUtils.class) {
            context = sMyContext;
            if (context == null) {
                try {
                    context = (Context) JavaCalls.callMethod(JavaCalls.callStaticMethod("android.app.ActivityThread", "currentActivityThread", new Object[0]), "getApplication", new Object[0]);
                } catch (Exception unused) {
                }
                if (context == null) {
                    throw new RuntimeException("My Application havn't be call onCreate by Framework.");
                }
                sMyContext = context;
            }
        }
        return context;
    }
}
