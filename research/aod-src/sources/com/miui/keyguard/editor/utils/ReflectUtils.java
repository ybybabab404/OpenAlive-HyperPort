package com.miui.keyguard.editor.utils;

import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.onetrack.api.an;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class ReflectUtils {
    public static boolean mCheckThrowException;
    public static Map sMethodCache = new HashMap();
    public static Map sFieldCache = new HashMap();
    public static Class[] PRIMITIVE_CLASSES = {Boolean.TYPE, Byte.TYPE, Character.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Void.TYPE};
    public static String[] SIGNATURE_OF_PRIMTIVE_CLASSES = {"Z", an.a, "C", ExifInterface.LATITUDE_SOUTH, "I", "J", "F", "D", ExifInterface.GPS_MEASUREMENT_INTERRUPTED};
    public static StringBuffer stringBuffer = new StringBuffer();

    public static String generateMethodCacheKey(Class cls, String str, String str2) {
        return cls.toString() + "/" + str + "/" + str2;
    }

    public static Method getMethod(Class cls, String str, String str2, Class... clsArr) {
        Method declaredMethod;
        try {
            String strGenerateMethodCacheKey = generateMethodCacheKey(cls, str, str2);
            Method method = (Method) sMethodCache.get(strGenerateMethodCacheKey);
            if (method != null) {
                return method;
            }
            try {
                declaredMethod = cls.getMethod(str, clsArr);
            } catch (Exception unused) {
                declaredMethod = cls.getDeclaredMethod(str, clsArr);
            }
            sMethodCache.put(strGenerateMethodCacheKey, declaredMethod);
            return declaredMethod;
        } catch (Exception e) {
            Log.d("ReflectUtils", "getMethod", e);
            throwException(cls.getName(), str, e);
            return null;
        }
    }

    public static String getMethodSignature(Class cls, Class... clsArr) {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        if (clsArr != null) {
            for (Class cls2 : clsArr) {
                sb.append(getSignature(cls2));
            }
        }
        sb.append(')');
        sb.append(getSignature(cls));
        return sb.toString();
    }

    public static String getSignature(Class cls) {
        int i = 0;
        while (true) {
            Class[] clsArr = PRIMITIVE_CLASSES;
            if (i >= clsArr.length) {
                return getSignature(cls.getName());
            }
            if (cls == clsArr[i]) {
                return SIGNATURE_OF_PRIMTIVE_CLASSES[i];
            }
            i++;
        }
    }

    public static String getSignature(String str) {
        int i = 0;
        while (true) {
            Class[] clsArr = PRIMITIVE_CLASSES;
            if (i >= clsArr.length) {
                break;
            }
            if (clsArr[i].getName().equals(str)) {
                str = SIGNATURE_OF_PRIMTIVE_CLASSES[i];
            }
            i++;
        }
        String strReplace = str.replace(".", "/");
        if (strReplace.startsWith("[")) {
            return strReplace;
        }
        return "L" + strReplace + ";";
    }

    public static Object invokeObject(Class cls, Object obj, String str, Class cls2, Class[] clsArr, Object... objArr) {
        if (clsArr == null) {
            try {
                clsArr = new Class[0];
            } catch (Exception e) {
                Log.e("ReflectUtils", "invokeObject", e);
                return null;
            }
        }
        Method method = getMethod(cls, str, getMethodSignature(cls2, clsArr), clsArr);
        if (method != null) {
            return method.invoke(obj, objArr);
        }
        return null;
    }

    public static void throwException(String str, String str2, Exception exc) {
        if (mCheckThrowException) {
            String name = exc.getClass().getName();
            stringBuffer.append(str + "---" + name + "---" + str2 + "\n");
        }
    }
}
