package com.xiaomi.onetrack.f;

import android.content.Context;
import com.xiaomi.onetrack.api.ai;
import com.xiaomi.onetrack.util.r;

/* JADX INFO: loaded from: classes2.dex */
class j implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ i b;

    public j(i iVar, Context context) {
        this.b = iVar;
        this.a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        r.a().a(Boolean.FALSE);
        com.xiaomi.onetrack.c.b.a();
        ai.a().d();
        ai.a().f();
        com.xiaomi.onetrack.c.e.a(this.a);
    }
}
