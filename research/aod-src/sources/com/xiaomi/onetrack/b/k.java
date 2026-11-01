package com.xiaomi.onetrack.b;

import com.xiaomi.onetrack.util.s;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes2.dex */
class k implements Runnable {
    final /* synthetic */ ArrayList a;
    final /* synthetic */ j b;

    public k(j jVar, ArrayList arrayList) {
        this.b = jVar;
        this.a = arrayList;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        if (s.a) {
            s.a("ConfigDbManager", "update: " + this.a);
        }
        this.b.b((ArrayList<m>) this.a);
        this.b.c((ArrayList<m>) this.a);
    }
}
