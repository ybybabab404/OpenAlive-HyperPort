package com.miui.miwallpaper.util;

import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes2.dex */
public class ReflectUtil {
    public static Object callObjectMethod2(Object obj, Class cls, String str, Class[] clsArr, Object... objArr) throws NoSuchMethodException {
        Method method = obj.getClass().getMethod(str, clsArr);
        method.setAccessible(true);
        return method.invoke(obj, objArr);
    }
}
