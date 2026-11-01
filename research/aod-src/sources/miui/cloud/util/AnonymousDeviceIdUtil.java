package miui.cloud.util;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import com.xiaomi.onetrack.api.am;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import miui.cloud.os.SystemProperties;
import miui.os.Build;

/* JADX INFO: loaded from: classes2.dex */
public class AnonymousDeviceIdUtil {
    public static Method sGetAAID;
    public static Method sGetOAID;
    public static Method sGetUDID;
    public static Method sGetVAID;
    public static Object sIdProivderImpl;
    public static Set sOAIDDeviceSet;

    static {
        HashSet hashSet = new HashSet();
        sOAIDDeviceSet = hashSet;
        hashSet.add("cmi");
        sOAIDDeviceSet.add("umi");
        sOAIDDeviceSet.add("lmi");
        sOAIDDeviceSet.add("picasso");
        sOAIDDeviceSet.add("phoenix");
        sOAIDDeviceSet.add("phoenixin");
        sOAIDDeviceSet.add("vangogh");
        sOAIDDeviceSet.add("monet");
        sOAIDDeviceSet.add("toco");
        sOAIDDeviceSet.add("merlin");
        sOAIDDeviceSet.add("curtana");
        sOAIDDeviceSet.add("durandal");
        sOAIDDeviceSet.add("excalibur");
        sOAIDDeviceSet.add("joyeuse");
        sOAIDDeviceSet.add("gram");
        try {
            Class<?> cls = Class.forName("com.android.id.impl.IdProviderImpl");
            sIdProivderImpl = cls.newInstance();
            try {
                sGetUDID = cls.getMethod("getUDID", Context.class);
            } catch (NoSuchMethodException e) {
                Log.e("AnonymousDeviceIdUtil", "getUDID not avaliable", e);
            }
            try {
                sGetOAID = cls.getMethod("getOAID", Context.class);
            } catch (NoSuchMethodException e2) {
                Log.e("AnonymousDeviceIdUtil", "getOAID not avaliable", e2);
            }
            try {
                sGetVAID = cls.getMethod("getVAID", Context.class);
            } catch (NoSuchMethodException e3) {
                Log.e("AnonymousDeviceIdUtil", "getVAID not avaliable", e3);
            }
            try {
                sGetAAID = cls.getMethod("getAAID", Context.class);
            } catch (NoSuchMethodException e4) {
                Log.e("AnonymousDeviceIdUtil", "getAAID not avaliable", e4);
            }
        } catch (ClassNotFoundException e5) {
            Log.e("AnonymousDeviceIdUtil", "provider not avaliable", e5);
        } catch (IllegalAccessException e6) {
            Log.e("AnonymousDeviceIdUtil", "provider not avaliable", e6);
        } catch (InstantiationException e7) {
            Log.e("AnonymousDeviceIdUtil", "provider not avaliable", e7);
        }
    }

    public static String getAndroidId(Context context) {
        Log.i("AnonymousDeviceIdUtil", "android id");
        return Settings.Secure.getString(context.getContentResolver(), am.E);
    }

    public static String getId(Context context, Method method) {
        Object obj = sIdProivderImpl;
        if (obj == null || method == null) {
            return null;
        }
        try {
            return (String) method.invoke(obj, context);
        } catch (IllegalAccessException e) {
            Log.e("AnonymousDeviceIdUtil", "exception invoking " + method, e);
            return null;
        } catch (InvocationTargetException e2) {
            Log.e("AnonymousDeviceIdUtil", "exception invoking " + method, e2);
            return null;
        }
    }

    public static String getOAID(Context context) {
        return getId(context, sGetOAID);
    }

    public static boolean isEnforced(Context context) {
        if ("1".equals(SystemProperties.get("ro.miui.restrict_imei"))) {
            Log.i("AnonymousDeviceIdUtil", "enforced");
            return true;
        }
        if (Build.IS_TABLET) {
            Log.i("AnonymousDeviceIdUtil", "new pad, enforced");
            return true;
        }
        Log.i("AnonymousDeviceIdUtil", "greater than S, enforced");
        return true;
    }

    public static boolean useOAID() {
        return sOAIDDeviceSet.contains(android.os.Build.DEVICE.toLowerCase());
    }
}
