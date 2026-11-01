package com.miui.aod.util;

import android.text.TextUtils;
import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class ReflectUtil {
    public static Object callObjectMethod(Object obj, String str, Class[] clsArr, Object... objArr) {
        return obj.getClass().getDeclaredMethod(str, clsArr).invoke(obj, objArr);
    }

    public static Object callObjectMethod2(Object obj, Class cls, String str, Class[] clsArr, Object... objArr) throws NoSuchMethodException {
        Method method = obj.getClass().getMethod(str, clsArr);
        method.setAccessible(true);
        return method.invoke(obj, objArr);
    }

    public static Object callObjectMethod2(Object obj, String str, Class[] clsArr, Object... objArr) {
        return obj.getClass().getMethod(str, clsArr).invoke(obj, objArr);
    }

    public static Object callStaticObjectMethod(Class cls, Class cls2, String str, Class[] clsArr, Object... objArr) throws NoSuchMethodException {
        Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
        declaredMethod.setAccessible(true);
        return declaredMethod.invoke(null, objArr);
    }

    public static Object getFieldFromSuperClass(Object obj, String str) {
        if (obj != null && !TextUtils.isEmpty(str)) {
            Class<?> superclass = obj.getClass();
            do {
                try {
                    Field declaredField = superclass.getDeclaredField(str);
                    declaredField.setAccessible(true);
                    return declaredField.get(obj);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    Log.e("ReflectUtil", "error...", e);
                    superclass = superclass.getSuperclass();
                }
            } while (superclass != obj.getClass());
        }
        return null;
    }

    public static Object getObjectField(Object obj, String str) throws NoSuchFieldException {
        Field declaredField = obj.getClass().getDeclaredField(str);
        declaredField.setAccessible(true);
        return declaredField.get(obj);
    }

    public static Object getObjectField(Object obj, String str, Class cls) throws NoSuchFieldException {
        Field declaredField = obj.getClass().getDeclaredField(str);
        declaredField.setAccessible(true);
        return declaredField.get(obj);
    }

    public static void setObjectField(Object obj, String str, Object obj2) throws IllegalAccessException, NoSuchFieldException {
        Field declaredField = obj.getClass().getDeclaredField(str);
        declaredField.setAccessible(true);
        declaredField.set(obj, obj2);
    }
}
