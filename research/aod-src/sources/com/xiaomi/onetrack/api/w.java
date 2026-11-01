package com.xiaomi.onetrack.api;

/* JADX INFO: loaded from: classes2.dex */
class w implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ c b;

    public w(c cVar, String str) {
        this.b = cVar;
        this.a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            com.xiaomi.onetrack.util.r.a().a(this.a);
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b("BaseOneTrackImp", "setInstanceId error: " + e.toString());
        }
    }
}
