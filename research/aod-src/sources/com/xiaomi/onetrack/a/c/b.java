package com.xiaomi.onetrack.a.c;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.xiaomi.onetrack.b.o;
import com.xiaomi.onetrack.util.s;

/* JADX INFO: loaded from: classes2.dex */
public class b {
    private static final String a = "AdMonitorUploadTimer";
    private static volatile b b;
    private a c;
    private boolean d;

    public static final class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        private void a(int i, long j) {
            removeMessages(i);
            s.a(b.a, "will post msg, prio=" + i + ", delay=" + j);
            sendEmptyMessageDelayed(i, j);
        }

        public void a(int i, boolean z) {
            if (hasMessages(i)) {
                s.a(b.a, "has message\u3000prio=" + i);
                return;
            }
            long jA = z ? 1000L : o.a(i);
            s.a(b.a, "will check prio=" + i + ", delay=" + jA);
            a(i, jA);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            s.a(b.a, "AdMonitorUploadTimer.handleMessage, msg.what=" + message.what);
            c.a();
        }
    }

    private b() {
        this.d = true;
        try {
            this.d = com.xiaomi.onetrack.f.a.a().getDatabasePath(com.xiaomi.onetrack.a.c.a.a).exists();
        } catch (Exception e) {
            s.b(a, "checkAdDatabaseExist error", e);
        }
    }

    public static b a() {
        if (b == null) {
            synchronized (b.class) {
                try {
                    if (b == null) {
                        b = new b();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return b;
    }

    private void b() {
        if (this.c == null) {
            HandlerThread handlerThread = new HandlerThread("onetrack_ad_monitor_uploader");
            handlerThread.start();
            this.c = new a(handlerThread.getLooper());
        }
    }

    public void a(boolean z) {
        this.d = z;
    }

    public synchronized void b(boolean z) {
        if (!this.d) {
            s.a(a, "no ad database file, skip it!");
        } else if (com.xiaomi.onetrack.a.c.a().b() <= 0) {
            s.a(a, "ad database has no data, skip it!");
        } else {
            b();
            this.c.a(0, z);
        }
    }

    public void c(boolean z) {
        if (z) {
            b(true);
        }
    }
}
