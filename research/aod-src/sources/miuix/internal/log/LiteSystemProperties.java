package miuix.internal.log;

import android.util.Log;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes2.dex */
class LiteSystemProperties {
    public static Class pClassSystemProperties;
    public static Method pGet;
    public static Method pGetInt;

    static {
        try {
            pClassSystemProperties = Class.forName("android.os.SystemProperties");
        } catch (Exception unused) {
            pClassSystemProperties = null;
        }
        Class cls = pClassSystemProperties;
        if (cls != null) {
            try {
                Method method = cls.getMethod("get", String.class, String.class);
                pGet = method;
                method.setAccessible(true);
            } catch (Exception unused2) {
            }
            try {
                Method method2 = pClassSystemProperties.getMethod("getInt", String.class, Integer.TYPE);
                pGetInt = method2;
                method2.setAccessible(true);
            } catch (Exception unused3) {
            }
        }
    }

    public static int getInt(String str, int i) {
        Method method = pGetInt;
        if (method != null) {
            try {
                return ((Integer) method.invoke(null, str, Integer.valueOf(i))).intValue();
            } catch (Exception e) {
                Log.e("Log:LiteSystemProperties", "key: " + str + " detail:" + e);
            }
        }
        return i;
    }
}
