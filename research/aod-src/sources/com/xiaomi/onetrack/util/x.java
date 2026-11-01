package com.xiaomi.onetrack.util;

import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.onetrack.api.be;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes2.dex */
public class x {
    private static final String a = "PermissionUtil";
    private static final String b = "android.permission.READ_PRIVILEGED_PHONE_STATE";
    private static Set<String> c;

    static {
        try {
            HashSet hashSet = new HashSet();
            c = hashSet;
            hashSet.add("android");
            c.add(be.a);
            c.add("com.miui.cit");
            c.add("com.xiaomi.finddevice");
            c.add("com.miui.securitycenter");
            c.add("com.android.settings");
            c.add("com.android.vending");
            c.add("com.google.android.gms");
            c.add("com.xiaomi.factory.mmi");
            c.add("com.miui.qr");
            c.add("com.android.contacts");
            c.add("com.qualcomm.qti.autoregistration");
            c.add("com.miui.tsmclient");
            c.add("com.miui.sekeytool");
            c.add("com.android.updater");
            if ("cn_chinamobile".equals(ae.a("ro.miui.cust_variant")) || "cn_chinatelecom".equals(ae.a("ro.miui.cust_variant"))) {
                c.add("com.mobiletools.systemhelper");
                c.add("com.miui.dmregservice");
            }
        } catch (Exception e) {
            Log.e(a, "static initializer: " + e.toString());
        }
    }

    private static boolean a() {
        try {
            return t.b() && !t.k() && "1".equals(ae.a("ro.miui.restrict_imei"));
        } catch (Exception e) {
            s.b(a, "isRestrictIMEI " + e.toString());
            return false;
        }
    }

    public static boolean a(Context context) {
        if (a()) {
            return a(com.xiaomi.onetrack.f.a.e()) && a(context, b);
        }
        return a(context, b);
    }

    private static boolean a(Context context, String str) {
        return context.checkPermission(str, Process.myPid(), Process.myUid()) == 0;
    }

    private static boolean a(String str) {
        Set<String> set;
        return (TextUtils.isEmpty(str) || (set = c) == null || !set.contains(str)) ? false : true;
    }

    public static boolean b(Context context) {
        return a(context, b);
    }
}
