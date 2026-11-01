package com.xiaomi.onetrack.c;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes2.dex */
public class ad extends Handler {
    private static final String a = "UploadTimer";
    private static final int b = 5000;
    private static final int c = 15000;
    private static final int d = 1200000;
    private final int e;
    private final int f;
    private final int g;
    private int h;
    private AtomicBoolean i;

    public ad(Looper looper) {
        super(looper);
        this.e = 1000;
        this.f = 10000;
        this.g = d;
        this.h = 10000;
        this.i = new AtomicBoolean(false);
    }

    private void a(int i, long j) {
        removeMessages(i);
        com.xiaomi.onetrack.util.s.a(a, "will post msg, prio=" + i + ", delay=" + j);
        sendEmptyMessageDelayed(i, j);
    }

    private void b() {
        if (ag.a().a(2)) {
            this.h = 10000;
            com.xiaomi.onetrack.util.s.a(a, "retry success");
            return;
        }
        removeMessages(1000);
        int i = this.h * 2;
        this.h = i;
        if (i > d) {
            this.h = d;
        }
        com.xiaomi.onetrack.util.s.a(a, "will restart retry msg after " + this.h);
        sendEmptyMessageDelayed(1000, (long) this.h);
    }

    public void a() {
        com.xiaomi.onetrack.util.j.a(new af(this));
    }

    public void a(int i, boolean z) {
        if (hasMessages(1000)) {
            com.xiaomi.onetrack.util.s.a(a, "in retry mode, return, prio=" + i);
            return;
        }
        if (z) {
            removeMessages(i);
        }
        if (hasMessages(i)) {
            return;
        }
        long jA = z ? 0L : com.xiaomi.onetrack.b.o.a(i);
        com.xiaomi.onetrack.util.s.a(a, "will check prio=" + i + ", delay=" + jA);
        a(i, jA);
    }

    public void a(boolean z) {
        b.a(new ae(this, z));
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        super.handleMessage(message);
        int i = message.what;
        if (i == 1000) {
            b();
            return;
        }
        boolean zA = ag.a().a(i);
        com.xiaomi.onetrack.util.s.a(a, "handleCheckUpload ret=" + zA + ", prio=" + i);
        if (zA) {
            return;
        }
        com.xiaomi.onetrack.util.s.a(a, "handleCheckUpload failed, will check if need to send retry msg");
        if (hasMessages(1000)) {
            return;
        }
        sendEmptyMessageDelayed(1000, this.h);
        com.xiaomi.onetrack.util.s.a(a, "fire retry timer after " + this.h);
    }
}
