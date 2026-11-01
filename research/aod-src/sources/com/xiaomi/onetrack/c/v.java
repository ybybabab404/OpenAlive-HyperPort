package com.xiaomi.onetrack.c;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes2.dex */
class v implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;
    final /* synthetic */ r d;

    public v(r rVar, String str, String str2, String str3) {
        this.d = rVar;
        this.a = str;
        this.b = str2;
        this.c = str3;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (j.b()) {
            String strA = com.xiaomi.onetrack.util.a.a(this.a);
            if (TextUtils.isEmpty(strA)) {
                com.xiaomi.onetrack.util.s.a("SystemImpCacheManager", "trackPubSubSystemImpCache defaultTopic is null");
            } else {
                this.d.a(this.b, strA, this.c, this.a, true);
            }
        }
    }
}
