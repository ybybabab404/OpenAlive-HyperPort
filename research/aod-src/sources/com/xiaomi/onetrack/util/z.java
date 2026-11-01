package com.xiaomi.onetrack.util;

import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.FileReader;

/* JADX INFO: loaded from: classes2.dex */
public class z {
    private static final String a = "ProcessUtil";
    private static String b;
    private static Boolean c;

    public static boolean a() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static String b() throws Throwable {
        BufferedReader bufferedReader;
        Throwable th;
        if (!TextUtils.isEmpty(b)) {
            return b;
        }
        try {
            bufferedReader = new BufferedReader(new FileReader("/proc/" + Process.myPid() + "/cmdline"));
            try {
                try {
                    String line = bufferedReader.readLine();
                    if (!TextUtils.isEmpty(line)) {
                        String strTrim = line.trim();
                        if (!TextUtils.isEmpty(strTrim)) {
                            b = strTrim;
                            p.a(bufferedReader);
                            return strTrim;
                        }
                    }
                } catch (Exception e) {
                    e = e;
                    s.b(a, "getProcessName error, ", e);
                }
            } catch (Throwable th2) {
                th = th2;
                p.a(bufferedReader);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            bufferedReader = null;
        } catch (Throwable th3) {
            bufferedReader = null;
            th = th3;
            p.a(bufferedReader);
            throw th;
        }
        p.a(bufferedReader);
        return null;
    }

    public static boolean c() throws Throwable {
        Boolean bool = c;
        if (bool != null) {
            return bool.booleanValue();
        }
        String strB = b();
        if (TextUtils.isEmpty(strB) || !strB.equals(com.xiaomi.onetrack.f.a.e())) {
            c = Boolean.FALSE;
            return false;
        }
        c = Boolean.TRUE;
        return true;
    }
}
