package com.miui.lockscreeninfo;

import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.onetrack.api.an;
import java.lang.reflect.Field;
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

    public static String generateFieldCacheKey(Class cls, String str, String str2) {
        return cls.toString() + "/" + str + "/" + str2;
    }

    public static String generateMethodCacheKey(Class cls, String str, String str2) {
        return cls.toString() + "/" + str + "/" + str2;
    }

    public static Class getClass(String str) {
        try {
            return Class.forName(str);
        } catch (Exception e) {
            Log.e("ReflectUtils", "getClass", e);
            return null;
        }
    }

    public static Field getField(Class cls, String str, Class cls2) {
        return getField(cls, str, getSignature(cls2));
    }

    public static Field getField(Class cls, String str, String str2) {
        try {
            String strGenerateFieldCacheKey = generateFieldCacheKey(cls, str, str2);
            Field field = (Field) sFieldCache.get(strGenerateFieldCacheKey);
            if (field != null) {
                return field;
            }
            Field field2 = cls.getField(str);
            field2.setAccessible(true);
            sFieldCache.put(strGenerateFieldCacheKey, field2);
            return field2;
        } catch (Exception e) {
            Log.e("ReflectUtils", "getField", e);
            throwException(cls.getName(), str, e);
            return null;
        }
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

    public static Object invokeObject(Class cls, Object obj, String str, Class cls2, Object obj2, Class[] clsArr, Object... objArr) {
        if (clsArr == null) {
            try {
                clsArr = new Class[0];
            } catch (Exception e) {
                Log.e("ReflectUtils", "invokeObject", e);
            }
        }
        Method method = getMethod(cls, str, getMethodSignature(cls2, clsArr), clsArr);
        if (method != null) {
            return method.invoke(obj, objArr);
        }
        return obj2;
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

    public static Object newInstance(String str, Class[] clsArr, Object... objArr) {
        try {
            return getClass(str).getConstructor(clsArr).newInstance(objArr);
        } catch (Exception e) {
            Log.e("ReflectUtils", "newInstance" + e.getMessage());
            return null;
        }
    }

    public static void setValue(Object obj, String str, Class cls, Object obj2) {
        try {
            Field field = getField(obj.getClass(), str, cls);
            if (cls == Integer.TYPE) {
                Integer num = (Integer) obj2;
                num.intValue();
                field.set(obj, num);
                return;
            }
            if (cls == Float.TYPE) {
                Float f = (Float) obj2;
                f.floatValue();
                field.set(obj, f);
                return;
            }
            if (cls == Long.TYPE) {
                Long l = (Long) obj2;
                l.longValue();
                field.set(obj, l);
                return;
            }
            if (cls == Boolean.TYPE) {
                Boolean bool = (Boolean) obj2;
                bool.booleanValue();
                field.set(obj, bool);
                return;
            }
            if (cls == Double.TYPE) {
                Double d = (Double) obj2;
                d.doubleValue();
                field.set(obj, d);
                return;
            }
            if (cls == Character.TYPE) {
                Character ch = (Character) obj2;
                ch.charValue();
                field.set(obj, ch);
            } else if (cls == Byte.TYPE) {
                Byte b = (Byte) obj2;
                b.byteValue();
                field.set(obj, b);
            } else {
                if (cls != Short.TYPE) {
                    field.set(obj, cls.cast(obj2));
                    return;
                }
                Short sh = (Short) obj2;
                sh.shortValue();
                field.set(obj, sh);
            }
        } catch (Exception e) {
            Log.d("ReflectUtils", "error setvalue", e);
        }
    }

    public static void throwException(String str, String str2, Exception exc) {
        if (mCheckThrowException) {
            String name = exc.getClass().getName();
            stringBuffer.append(str + "---" + name + "---" + str2 + "\n");
        }
    }
}
