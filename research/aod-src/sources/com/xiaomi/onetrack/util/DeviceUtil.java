package com.xiaomi.onetrack.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.TextUtils;
import com.xiaomi.onetrack.api.am;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class DeviceUtil {
    static final int a = 15;
    static final int b = 6;
    private static final String c = "DeviceUtil";
    private static final String d = "MI_BOX";
    private static final String e = "MI_TVBOX";
    private static final String f = "MI_PROJECTOR";
    private static final String g = "MI_TV";
    private static final String h = "MI_PAD";
    private static final String i = "MI_WIFI_SPEAKER";
    private static final String j = "MI_PHONE";
    private static final String k = "OTHER";
    private static final String l = "";
    private static final int m = 15;
    private static Method n = null;
    private static String o = null;
    private static String p = null;
    private static volatile boolean q = false;
    private static final String r = "0000000000000000";
    private static String s;
    private static Boolean t;

    public static class GAIDClient {
        private static final String a = "GAIDClient";

        public static final class AdvertisingConnection implements ServiceConnection {
            private static final int a = 30000;
            private boolean b;
            private IBinder c;

            private AdvertisingConnection() {
                this.b = false;
            }

            public IBinder a() throws InterruptedException {
                IBinder iBinder = this.c;
                if (iBinder != null) {
                    return iBinder;
                }
                if (iBinder == null && !this.b) {
                    synchronized (this) {
                        try {
                            wait(30000L);
                            if (this.c == null) {
                                throw new InterruptedException("Not connect or connect timeout to google play service");
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
                return this.c;
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                synchronized (this) {
                    this.c = iBinder;
                    notifyAll();
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                this.b = true;
                this.c = null;
            }
        }

        public static final class a implements IInterface {
            private IBinder a;

            public a(IBinder iBinder) {
                this.a = iBinder;
            }

            public String a() throws RemoteException {
                if (this.a == null) {
                    return "";
                }
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    this.a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public boolean a(boolean z) throws RemoteException {
                if (this.a == null) {
                    return false;
                }
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.a.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }
        }

        private GAIDClient() {
        }

        public static String a(Context context) {
            if (!c(context)) {
                s.a(a, "Google play service is not available");
                return "";
            }
            AdvertisingConnection advertisingConnection = new AdvertisingConnection();
            try {
                Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
                intent.setPackage("com.google.android.gms");
                if (context.bindService(intent, advertisingConnection, 1)) {
                    String strA = new a(advertisingConnection.a()).a();
                    context.unbindService(advertisingConnection);
                    return strA;
                }
            } catch (Throwable th) {
                try {
                    s.a(a, "Query Google ADID failed: " + th.getMessage());
                } finally {
                    context.unbindService(advertisingConnection);
                }
            }
            return "";
        }

        public static boolean b(Context context) {
            if (!c(context)) {
                s.a(a, "Google play service is not available");
                return false;
            }
            AdvertisingConnection advertisingConnection = new AdvertisingConnection();
            try {
                Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
                intent.setPackage("com.google.android.gms");
                if (context.bindService(intent, advertisingConnection, 1)) {
                    boolean zA = new a(advertisingConnection.a()).a(true);
                    context.unbindService(advertisingConnection);
                    return zA;
                }
            } catch (Throwable th) {
                try {
                    s.a(a, "Query Google isLimitAdTrackingEnabled failed :" + th.getMessage());
                } finally {
                    context.unbindService(advertisingConnection);
                }
            }
            return false;
        }

        private static boolean c(Context context) {
            try {
                context.getPackageManager().getPackageInfo("com.android.vending", 16384);
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
                return false;
            }
        }
    }

    public static class a {
        private a() {
        }

        private static <T> T a(Class<?> cls, String str) {
            try {
                Field declaredField = cls.getDeclaredField(str);
                declaredField.setAccessible(true);
                return (T) declaredField.get(null);
            } catch (Throwable th) {
                s.c(DeviceUtil.c, "getStaticVariableValue exception: " + th.getMessage());
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static String b() {
            try {
                Class<?> cls = Class.forName("mitv.common.ConfigurationManager");
                int i = Integer.parseInt(String.valueOf(cls.getMethod("getProductCategory", new Class[0]).invoke(cls.getMethod("getInstance", new Class[0]).invoke(cls, new Object[0]), new Object[0])));
                Class<?> cls2 = Class.forName("mitv.tv.TvContext");
                if (i == Integer.parseInt(String.valueOf(a(cls2, "PRODUCT_CATEGORY_MITV")))) {
                    return DeviceUtil.g;
                }
                if (i == Integer.parseInt(String.valueOf(a(cls2, "PRODUCT_CATEGORY_MIBOX")))) {
                    return DeviceUtil.d;
                }
                if (i == Integer.parseInt(String.valueOf(a(cls2, "PRODUCT_CATEGORY_MITVBOX")))) {
                    return DeviceUtil.e;
                }
                return i == Integer.parseInt(String.valueOf(a(cls2, "PRODUCT_CATEGORY_MIPROJECTOR"))) ? DeviceUtil.f : "";
            } catch (Throwable th) {
                s.c(DeviceUtil.c, "getMiTvProductCategory exception: " + th.getMessage());
                return "";
            }
        }
    }

    static {
        try {
            n = Class.forName("android.os.SystemProperties").getMethod("get", String.class);
        } catch (Throwable th) {
            s.b(c, "sGetProp init failed ex: " + th.getMessage());
        }
        t = null;
    }

    public static String a(Context context) {
        return "";
    }

    private static String a(String str) {
        try {
            Method method = n;
            if (method != null) {
                return String.valueOf(method.invoke(null, str));
            }
        } catch (Throwable th) {
            s.a(c, "getProp failed ex: " + th.getMessage());
        }
        return null;
    }

    public static void a() {
        o = null;
    }

    public static String b() {
        return Build.MODEL;
    }

    public static String b(Context context) {
        if (!TextUtils.isEmpty(o)) {
            return o;
        }
        if (GAIDClient.b(context)) {
            return "";
        }
        String strA = GAIDClient.a(context);
        if (TextUtils.isEmpty(strA)) {
            return "";
        }
        o = strA;
        return strA;
    }

    public static String c() {
        return a("ro.product.marketname");
    }

    public static List<String> c(Context context) {
        return null;
    }

    public static String d() {
        return Build.MANUFACTURER;
    }

    public static String d(Context context) {
        return "";
    }

    public static String e(Context context) {
        return com.xiaomi.onetrack.util.oaid.a.a().a(context);
    }

    public static void e() {
        com.xiaomi.onetrack.util.oaid.a.a().b();
    }

    public static String f(Context context) {
        try {
            if (ac.b(p)) {
                return p;
            }
            String string = Settings.System.getString(context.getContentResolver(), am.E);
            if (!ac.b(string) || r.equals(string)) {
                return "";
            }
            p = string;
            return string;
        } catch (Throwable th) {
            s.b(c, "getandroid d throwable:" + th.getMessage());
            return "";
        }
    }

    public static boolean f() {
        return com.xiaomi.onetrack.util.oaid.a.a().c();
    }

    public static boolean g() {
        if (t == null) {
            String strB = b();
            if (ac.b(strB)) {
                String upperCase = strB.toUpperCase();
                if (upperCase.contains("MITV") || upperCase.contains("MIBOX") || upperCase.contains("PROJECTOR")) {
                    Boolean bool = Boolean.TRUE;
                    t = bool;
                    return bool.booleanValue();
                }
            }
            t = Boolean.FALSE;
        }
        return t.booleanValue();
    }

    public static String h() {
        if (!TextUtils.isEmpty(s)) {
            return s;
        }
        String strB = a.b();
        s = strB;
        if (!TextUtils.isEmpty(strB)) {
            return s;
        }
        if (i()) {
            s = h;
            return h;
        }
        if (j()) {
            s = i;
            return i;
        }
        if (t.b()) {
            s = j;
            return j;
        }
        s = k;
        return k;
    }

    private static boolean i() {
        try {
            return ((Boolean) Class.forName("miui.os.Build").getField("IS_TABLET").get(null)).booleanValue();
        } catch (Throwable th) {
            s.c(c, "get IS_TABLET from miui.os.Build exception: " + th.getMessage());
            try {
                return m.a("is_pad", false);
            } catch (Throwable th2) {
                s.c(c, "get is_pad from FeatureParser exception: " + th2.getMessage());
                return false;
            }
        }
    }

    private static boolean j() {
        String strA = ae.a("ro.product.brand");
        return !TextUtils.isEmpty(strA) && strA.contains("XiaoAiTongXue");
    }
}
