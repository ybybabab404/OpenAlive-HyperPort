package miui.cloud.util;

import android.util.Log;
import com.xiaomi.micloudsdk.utils.ReflectUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes2.dex */
public class DeviceUtils {
    public static final String MARKET_NAME = getAndroidSystemProperties("ro.product.marketname", null);

    public static String getAndroidSystemProperties(String str, String str2) {
        Class clsLoadClass = ReflectUtils.loadClass("android.os.SystemProperties");
        if (clsLoadClass == null) {
            Log.d("getAndroidSystemProperties", "class SystemProperties not found");
            return str2;
        }
        Method method = ReflectUtils.getMethod(clsLoadClass, "get", String.class, String.class);
        if (method == null) {
            Log.d("getAndroidSystemProperties", "no method get");
            return str2;
        }
        method.setAccessible(true);
        try {
            return (String) method.invoke(clsLoadClass, str, str2);
        } catch (IllegalAccessException e) {
            Log.d("getAndroidSystemProperties", "error: " + e);
            return str2;
        } catch (InvocationTargetException e2) {
            Log.d("getAndroidSystemProperties", "error: " + e2);
            return str2;
        }
    }
}
