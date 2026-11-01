package com.xiaomi.onetrack.h.d;

import android.text.TextUtils;
import com.xiaomi.onetrack.util.af;

/* JADX INFO: loaded from: classes2.dex */
class c implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;
    final /* synthetic */ int d;
    final /* synthetic */ String e;

    public c(String str, String str2, String str3, int i, String str4) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = i;
        this.e = str4;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.xiaomi.onetrack.h.b.b.a().a(this.a, af.a(0), this.b, this.c, this.d);
        if (a.g.equals(this.b) || !(TextUtils.isEmpty(this.c) || a.l.equals(this.c))) {
            com.xiaomi.onetrack.h.b.b.a().a(this.a, af.a(0), this.e, this.d);
        }
    }
}
