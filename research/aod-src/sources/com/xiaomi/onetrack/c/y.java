package com.xiaomi.onetrack.c;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes2.dex */
public class y {
    private static final String a = "TransformChannelManager";
    private static y b;

    private y() {
    }

    public static y a() {
        if (b == null) {
            b();
        }
        return b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ab abVar) {
        Iterator<x> it = abVar.a.iterator();
        ArrayList arrayList = new ArrayList();
        while (it.hasNext()) {
            x next = it.next();
            String strB = next.b();
            com.xiaomi.onetrack.b.p pVarB = com.xiaomi.onetrack.b.q.a().b(strB);
            if (pVarB == null || TextUtils.isEmpty(pVarB.b)) {
                arrayList.add(Long.valueOf(next.a()));
                it.remove();
            } else {
                next.c(pVarB.b);
                next.d(com.xiaomi.onetrack.util.a.a(strB));
                next.a(com.xiaomi.onetrack.util.a.b());
            }
        }
        abVar.c.removeAll(arrayList);
    }

    public static void b() {
        if (b == null) {
            synchronized (y.class) {
                try {
                    if (b == null) {
                        b = new y();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public void a(String str, boolean z) {
        if (z != com.xiaomi.onetrack.util.t.b(str)) {
            if (z) {
                c();
            } else {
                d();
            }
        }
    }

    public void c() {
        d.a(new z(this));
    }

    public void d() {
        d.a(new aa(this));
    }
}
