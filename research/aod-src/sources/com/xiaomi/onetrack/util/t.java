package com.xiaomi.onetrack.util;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.an;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes2.dex */
public class t {
    public static final int a = 33;
    public static final int b = 29;
    public static final int c = 25;
    public static final int d = 24;
    public static final int e = 23;
    public static final int f = 22;
    public static final int g = 21;
    public static final int h = 19;
    public static final int i = 17;
    public static final int j = 28;
    public static final int k = 2;
    public static final int l = 4;
    private static final String m = "OsUtil";
    private static Class n = null;
    private static Method o = null;
    private static Boolean p = null;
    private static final String q = "";
    private static Method r = null;
    private static boolean s = false;
    private static String t = null;
    private static boolean u = false;
    private static int v = 0;
    private static boolean w = false;
    private static String x;

    static {
        try {
            r = Class.forName("android.os.SystemProperties").getMethod("get", String.class);
        } catch (Throwable th) {
            s.b(m, "sGetProp init failed ex: " + th.getMessage());
        }
    }

    public static String a(int i2) {
        char c2;
        try {
            int i3 = i2 / 60000;
            if (i3 < 0) {
                i3 = -i3;
                c2 = '-';
            } else {
                c2 = '+';
            }
            StringBuilder sb = new StringBuilder(9);
            sb.append("GMT");
            sb.append(c2);
            a(sb, i3 / 60);
            sb.append(':');
            a(sb, i3 % 60);
            return sb.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    public static void a() {
        if (w) {
            return;
        }
        w = true;
        try {
            n = Class.forName("miui.os.Build");
        } catch (Throwable th) {
            s.b(m, "sMiuiBuild init failed ex: " + th.getMessage());
        }
        try {
            Method declaredMethod = Class.forName("android.provider.MiuiSettings$Secure").getDeclaredMethod("isUserExperienceProgramEnable", ContentResolver.class);
            o = declaredMethod;
            declaredMethod.setAccessible(true);
        } catch (Throwable th2) {
            s.b(m, "sMiuiUEPMethod init failed ex: " + th2.getMessage());
        }
    }

    private static void a(StringBuilder sb, int i2) {
        String string = Integer.toString(i2);
        for (int i3 = 0; i3 < 2 - string.length(); i3++) {
            sb.append('0');
        }
        sb.append(string);
    }

    public static void a(boolean z) {
        u = z;
    }

    public static void a(boolean z, String str, OneTrack.Mode mode) {
        int i2;
        if (mode == OneTrack.Mode.APP) {
            i2 = 3;
        } else if (mode == OneTrack.Mode.PLUGIN) {
            i2 = 2;
        } else {
            i2 = mode == OneTrack.Mode.SDK ? 1 : 0;
        }
        if (v <= i2) {
            s = z;
            t = str;
            v = i2;
        }
    }

    public static boolean a(Context context) {
        if (o == null) {
            try {
                if (b()) {
                    int i2 = Settings.Secure.getInt(context.getContentResolver(), "upload_log_pref", -1);
                    s.a(m, "isUserExperiencePlanEnabled upload_log_value: " + i2);
                    if (i2 != 1 && i2 == 0) {
                        return false;
                    }
                }
                return true;
            } catch (Throwable th) {
                s.a(m, "Settings failed: " + th.toString());
            }
        }
        try {
            return ((Boolean) o.invoke(null, context.getContentResolver())).booleanValue();
        } catch (Throwable th2) {
            Log.d(m, "isUserExperiencePlanEnabled failed: " + th2.getMessage());
            return true;
        }
    }

    public static boolean a(String str) {
        if (OneTrack.isDisable() || OneTrack.isUseSystemNetTrafficOnly()) {
            s.c(str, "should not access network or location, cta");
            return true;
        }
        if (!i()) {
            s.c(str, "should not access network or location, not provisioned");
            return true;
        }
        if (com.xiaomi.onetrack.c.j.b()) {
            return false;
        }
        s.c(str, "should not access network or location, cta");
        return true;
    }

    public static boolean b() {
        Boolean bool = p;
        if (bool != null) {
            return bool.booleanValue();
        }
        if (TextUtils.isEmpty(d("ro.miui.ui.version.code"))) {
            p = Boolean.FALSE;
        } else {
            p = Boolean.TRUE;
        }
        return p.booleanValue();
    }

    public static boolean b(String str) {
        if (!ac.b(str)) {
            str = l();
        }
        return a.o.contains(str.toUpperCase());
    }

    public static String c() {
        return a(TimeZone.getDefault().getOffset(System.currentTimeMillis()));
    }

    public static String c(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if ("CN".equalsIgnoreCase(str)) {
            return "CN";
        }
        if ("RU".equalsIgnoreCase(str)) {
            return "RU";
        }
        if ("IN".equalsIgnoreCase(str)) {
            return "IN";
        }
        return a.n.contains(str.toUpperCase()) ? a.f : "SG";
    }

    public static String d() {
        if (!TextUtils.isEmpty(x)) {
            return x;
        }
        Class cls = n;
        if (cls == null) {
            return "";
        }
        try {
            if (((Boolean) cls.getField("IS_ALPHA_BUILD").get(null)).booleanValue()) {
                x = ExifInterface.GPS_MEASUREMENT_IN_PROGRESS;
                return ExifInterface.GPS_MEASUREMENT_IN_PROGRESS;
            }
            String str = Build.VERSION.INCREMENTAL;
            boolean zContains = str.contains(".DEV");
            boolean zContains2 = str.contains(".BETA");
            boolean zBooleanValue = ((Boolean) n.getField("IS_DEVELOPMENT_VERSION").get(null)).booleanValue();
            if (((Boolean) n.getField("IS_STABLE_VERSION").get(null)).booleanValue()) {
                x = ExifInterface.LATITUDE_SOUTH;
                return ExifInterface.LATITUDE_SOUTH;
            }
            if (zBooleanValue && zContains2) {
                x = an.a;
                return an.a;
            }
            if (zBooleanValue && zContains) {
                x = "X";
                return "X";
            }
            if (!zBooleanValue) {
                return "";
            }
            x = "D";
            return "D";
        } catch (Exception e2) {
            Log.e(s.a(m), "getRomBuildCode failed: " + e2.getMessage());
            return "";
        }
    }

    private static String d(String str) {
        try {
            Method method = r;
            if (method != null) {
                return String.valueOf(method.invoke(null, str));
            }
        } catch (Throwable th) {
            s.b(m, "getProp failed ex: " + th.getMessage());
        }
        return null;
    }

    public static String e() {
        return Build.VERSION.INCREMENTAL;
    }

    public static String f() {
        return ae.a("ro.mi.os.version.incremental", "");
    }

    public static String g() {
        return Build.VERSION.RELEASE;
    }

    public static int h() {
        Integer num;
        Throwable th;
        try {
            Method declaredMethod = Class.forName("android.os.UserHandle").getDeclaredMethod("getUserId", Integer.TYPE);
            declaredMethod.setAccessible(true);
            int iMyUid = Process.myUid();
            num = (Integer) declaredMethod.invoke(null, Integer.valueOf(iMyUid));
            try {
                s.a(m, String.format("getUserId, uid:%d, userId:%d", Integer.valueOf(iMyUid), num));
            } catch (Throwable th2) {
                th = th2;
                Log.e(s.a(m), "getUserId exception: " + th.getMessage());
            }
        } catch (Throwable th3) {
            num = null;
            th = th3;
        }
        if (num == null) {
            num = 0;
        }
        return num.intValue();
    }

    @TargetApi(17)
    public static boolean i() {
        try {
            if (OneTrack.getForceDeviceProvisioned()) {
                s.c(m, "force setting device provisioned, don't read the system real attribute value");
                return true;
            }
            boolean z = Settings.Global.getInt(com.xiaomi.onetrack.f.a.b().getContentResolver(), "device_provisioned", 0) != 0;
            if (!z) {
                s.c(m, "Provisioned: " + z);
            }
            return z;
        } catch (Throwable th) {
            s.b(m, "isDeviceProvisioned exception：" + th.getMessage());
            return true;
        }
    }

    public static boolean j() {
        Class cls = n;
        if (cls != null) {
            try {
                return ((Boolean) cls.getField("IS_INTERNATIONAL_BUILD").get(null)).booleanValue();
            } catch (Throwable unused) {
            }
        }
        String strP = p();
        if (TextUtils.isEmpty(strP)) {
            return false;
        }
        return !TextUtils.equals("CN", strP.toUpperCase());
    }

    public static boolean k() {
        if (b() && !u) {
            return j();
        }
        String strP = t;
        if (TextUtils.isEmpty(strP)) {
            strP = p();
        }
        return (TextUtils.isEmpty(strP) || TextUtils.equals(strP, "CN")) ? false : true;
    }

    public static String l() {
        String strP = ((!b() || u) && !TextUtils.isEmpty(t)) ? t : p();
        return ac.b(strP) ? strP.toUpperCase() : strP;
    }

    public static boolean m() {
        return u;
    }

    public static String n() {
        String strP = p();
        return ac.b(strP) ? strP.toUpperCase() : strP;
    }

    public static String o() {
        String strL = l();
        if (ac.b(strL)) {
            if ("IN".equalsIgnoreCase(strL)) {
                return "in";
            }
            if (a.n.contains(strL.toUpperCase())) {
                return "eu";
            }
            s.a(m, "current region：" + strL + "，no match ,select default sg");
        }
        return "sg";
    }

    private static String p() {
        try {
            String strA = ae.a("ro.miui.region", "");
            if (TextUtils.isEmpty(strA)) {
                strA = ae.a("ro.product.locale.region", "");
            }
            if (TextUtils.isEmpty(strA)) {
                Object objInvoke = Class.forName("android.os.LocaleList").getMethod("getDefault", new Class[0]).invoke(null, new Object[0]);
                Object objInvoke2 = objInvoke.getClass().getMethod("size", new Class[0]).invoke(objInvoke, new Object[0]);
                if ((objInvoke2 instanceof Integer) && ((Integer) objInvoke2).intValue() > 0) {
                    Object objInvoke3 = objInvoke.getClass().getMethod("get", Integer.TYPE).invoke(objInvoke, 0);
                    Object objInvoke4 = objInvoke3.getClass().getMethod("getCountry", new Class[0]).invoke(objInvoke3, new Object[0]);
                    if (objInvoke4 instanceof String) {
                        strA = (String) objInvoke4;
                    }
                }
            }
            if (TextUtils.isEmpty(strA)) {
                strA = Locale.getDefault().getCountry();
            }
            if (!TextUtils.isEmpty(strA)) {
                return strA.trim();
            }
        } catch (Throwable th) {
            s.b(m, "getRegion Exception: " + th.getMessage());
        }
        return "";
    }
}
