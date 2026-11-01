package cn.kuaipan.android.utils;

import android.util.Log;
import java.lang.reflect.Method;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class JavaCalls {
    public static final HashMap PRIMITIVE_MAP;

    public static class JavaParam<T> {
        public final Class clazz;
        public final Object obj;
    }

    static {
        HashMap map = new HashMap();
        PRIMITIVE_MAP = map;
        Class cls = Boolean.TYPE;
        map.put(Boolean.class, cls);
        Class cls2 = Byte.TYPE;
        map.put(Byte.class, cls2);
        Class cls3 = Character.TYPE;
        map.put(Character.class, cls3);
        Class cls4 = Short.TYPE;
        map.put(Short.class, cls4);
        Class cls5 = Integer.TYPE;
        map.put(Integer.class, cls5);
        Class cls6 = Float.TYPE;
        map.put(Float.class, cls6);
        Class cls7 = Long.TYPE;
        map.put(Long.class, cls7);
        Class cls8 = Double.TYPE;
        map.put(Double.class, cls8);
        map.put(cls, cls);
        map.put(cls2, cls2);
        map.put(cls3, cls3);
        map.put(cls4, cls4);
        map.put(cls5, cls5);
        map.put(cls6, cls6);
        map.put(cls7, cls7);
        map.put(cls8, cls8);
    }

    public static Object callMethod(Object obj, String str, Object... objArr) {
        try {
            return callMethodOrThrow(obj, str, objArr);
        } catch (Exception e) {
            Log.w("JavaCalls", "Meet exception when call Method '" + str + "' in " + obj, e);
            return null;
        }
    }

    public static Object callMethodOrThrow(Object obj, String str, Object... objArr) {
        return getDeclaredMethod(obj.getClass(), str, getParameterTypes(objArr)).invoke(obj, getParameters(objArr));
    }

    public static Object callStaticMethod(String str, String str2, Object... objArr) {
        try {
            return callStaticMethodOrThrow(Class.forName(str), str2, objArr);
        } catch (Exception e) {
            Log.w("JavaCalls", "Meet exception when call Method '" + str2 + "' in " + str, e);
            return null;
        }
    }

    public static Object callStaticMethodOrThrow(Class cls, String str, Object... objArr) {
        return getDeclaredMethod(cls, str, getParameterTypes(objArr)).invoke(null, getParameters(objArr));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean compareClassLists(Class[] clsArr, Class[] clsArr2) {
        if (clsArr == 0) {
            return clsArr2 == null || clsArr2.length == 0;
        }
        int length = clsArr.length;
        if (clsArr2 == null) {
            return length == 0;
        }
        if (length != clsArr2.length) {
            return false;
        }
        for (int i = length - 1; i >= 0; i--) {
            if (!clsArr[i].isAssignableFrom(clsArr2[i])) {
                HashMap map = PRIMITIVE_MAP;
                if (!map.containsKey(clsArr[i]) || !((Class) map.get(clsArr[i])).equals(map.get(clsArr2[i]))) {
                }
            }
            return true;
        }
        return false;
    }

    public static Method findMethodByName(Method[] methodArr, String str, Class[] clsArr) throws NoSuchMethodException {
        if (str == null) {
            throw new NullPointerException("Method name must not be null.");
        }
        for (Method method : methodArr) {
            if (method.getName().equals(str) && compareClassLists(method.getParameterTypes(), clsArr)) {
                return method;
            }
        }
        throw new NoSuchMethodException(str);
    }

    public static Method getDeclaredMethod(Class cls, String str, Class... clsArr) {
        return findMethodByName(cls.getDeclaredMethods(), str, clsArr);
    }

    public static Class[] getParameterTypes(Object... objArr) {
        if (objArr == null || objArr.length <= 0) {
            return null;
        }
        Class[] clsArr = new Class[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            Object obj = objArr[i];
            if (obj == null || !(obj instanceof JavaParam)) {
                clsArr[i] = obj == null ? null : obj.getClass();
            } else {
                clsArr[i] = ((JavaParam) obj).clazz;
            }
        }
        return clsArr;
    }

    public static Object[] getParameters(Object... objArr) {
        if (objArr == null || objArr.length <= 0) {
            return null;
        }
        Object[] objArr2 = new Object[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            Object obj = objArr[i];
            if (obj == null || !(obj instanceof JavaParam)) {
                objArr2[i] = obj;
            } else {
                objArr2[i] = ((JavaParam) obj).obj;
            }
        }
        return objArr2;
    }
}
