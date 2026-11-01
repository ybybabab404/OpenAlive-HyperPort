package com.xiaomi.onetrack.c;

import com.xiaomi.onetrack.api.ai;

/* JADX INFO: loaded from: classes2.dex */
class k implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ String b;

    public k(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        ai.a().e();
        j.c(this.a, this.b);
    }
}
