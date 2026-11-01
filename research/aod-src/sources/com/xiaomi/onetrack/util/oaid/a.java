package com.xiaomi.onetrack.util.oaid;

import android.content.Context;
import android.os.SystemClock;
import com.xiaomi.onetrack.util.oaid.helpers.b;
import com.xiaomi.onetrack.util.oaid.helpers.h;
import com.xiaomi.onetrack.util.q;
import com.xiaomi.onetrack.util.s;
import com.xiaomi.onetrack.util.t;
import com.xiaomi.onetrack.util.z;

/* JADX INFO: loaded from: classes2.dex */
public class a {
    private static final String a = "a";
    private static volatile a b;
    private static long d;
    private volatile String c = "";
    private final int e = 3;
    private volatile int f = 0;
    private boolean g = false;

    public static a a() {
        if (b == null) {
            synchronized (a.class) {
                try {
                    if (b == null) {
                        b = new a();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return b;
    }

    private boolean d() {
        if (this.f > 3) {
            return true;
        }
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long jAbs = Math.abs(jElapsedRealtime - d);
        if (this.f == 1) {
            if (jAbs < 3000) {
                return true;
            }
        } else if (this.f == 2) {
            if (jAbs < 10000) {
                return true;
            }
        } else if (this.f == 3 && jAbs < 60000) {
            return true;
        }
        s.a(a, "get time：" + this.f);
        d = jElapsedRealtime;
        return false;
    }

    public String a(Context context) {
        String strA;
        synchronized (this.c) {
            try {
                if (z.a()) {
                    if (s.a) {
                        throw new IllegalStateException("Don't use it on the main thread");
                    }
                    s.b(a, "getOaid() throw exception : Don't use it on the main thread");
                    return "";
                }
                if (this.c != null && !this.c.equals("")) {
                    return this.c;
                }
                if (d()) {
                    s.a(a, "isNotAllowedGetOaid");
                    return this.c;
                }
                if (t.b()) {
                    this.c = q.a(context);
                    this.f++;
                    return this.c;
                }
                if (!this.g && (strA = new h().a(context)) != null && !strA.equals("")) {
                    this.c = strA;
                    this.f++;
                    return strA;
                }
                String strA2 = new b().a(context);
                if (strA2 == null || strA2.equals("")) {
                    this.f++;
                    return this.c;
                }
                this.c = strA2;
                this.f++;
                return strA2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void a(boolean z) {
        this.g = z;
        s.a(a, "setCloseOaidDependMsaSDK：" + this.g);
    }

    public void b() {
        this.f = 0;
    }

    public boolean c() {
        return (this.c == null || this.c.equals("")) ? false : true;
    }
}
