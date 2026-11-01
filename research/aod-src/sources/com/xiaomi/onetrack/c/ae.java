package com.xiaomi.onetrack.c;

/* JADX INFO: loaded from: classes2.dex */
class ae implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ ad b;

    public ae(ad adVar, boolean z) {
        this.b = adVar;
        this.a = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.a();
        try {
            if (this.a) {
                int[] iArr = {0, 1, 2};
                for (int i = 0; i < 3; i++) {
                    int i2 = iArr[i];
                    int iA = com.xiaomi.onetrack.b.o.a(i2);
                    if (!this.b.hasMessages(i2)) {
                        this.b.sendEmptyMessageDelayed(i2, iA);
                    }
                }
            }
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.a("UploadTimer", "netReceiver error: " + e);
        }
    }
}
