package com.xiaomi.onetrack.api;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import com.miui.analytics.ITrack;
import com.xiaomi.onetrack.OneTrack;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes2.dex */
public class be {
    public static final String a = "com.miui.analytics";
    public static final String b = "com.miui.analytics.onetrack.OneTrackService";
    private static final String c = "ServiceConnectManager";
    private static final int h = 1;
    private static final int i = 2;
    private static final int j = 3;
    private static final int k = 4;
    private static final int l = 5;
    private static volatile be n = null;
    private static String o = "onetrack_service_connect";
    private static final int q = 3600000;
    private static final int r = 10;
    private static final int s = 10000;
    private static final int t = 5120000;
    private volatile ITrack d;
    private b m;
    private AtomicBoolean e = new AtomicBoolean(false);
    private AtomicBoolean f = new AtomicBoolean(false);
    private AtomicBoolean p = new AtomicBoolean(false);
    private int u = 10000;
    private ServiceConnection v = new ServiceConnection() { // from class: com.xiaomi.onetrack.api.ServiceConnectionManager$1
        @Override // android.content.ServiceConnection
        public void onBindingDied(ComponentName componentName) {
            try {
                this.a.g();
                this.a.m.sendEmptyMessage(3);
            } catch (Throwable th) {
                com.xiaomi.onetrack.util.s.a("ServiceConnectManager", "onBindingDied throwable:" + th.getMessage());
            }
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(ComponentName componentName) {
            try {
                com.xiaomi.onetrack.util.s.a("ServiceConnectManager", "onNullBinding");
                this.a.g();
            } catch (Throwable th) {
                com.xiaomi.onetrack.util.s.a("ServiceConnectManager", "onNullBinding throwable:" + th.getMessage());
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.a.d = ITrack.Stub.asInterface(iBinder);
                this.a.f.set(true);
                this.a.e.set(false);
                this.a.m.sendEmptyMessage(5);
                this.a.k();
                StringBuilder sb = new StringBuilder();
                sb.append("onServiceConnected  mConnecting ");
                sb.append(this.a.e);
                sb.append(" mBindResult:");
                sb.append(this.a.f);
                sb.append(" mIOneTrackService ");
                sb.append(this.a.d == null ? 0 : 1);
                sb.append(" pid:");
                sb.append(Process.myPid());
                sb.append(" tid:");
                sb.append(Process.myTid());
                com.xiaomi.onetrack.util.s.a("ServiceConnectManager", sb.toString());
            } catch (Throwable th) {
                this.a.d();
                com.xiaomi.onetrack.util.s.a("ServiceConnectManager", "onServiceConnected throwable:" + th.getMessage());
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            try {
                this.a.d();
                this.a.m.sendEmptyMessage(3);
                StringBuilder sb = new StringBuilder();
                sb.append("onServiceDisconnected:  mConnecting ");
                sb.append(this.a.e);
                sb.append(" mIOneTrackService ");
                sb.append(this.a.d == null ? 0 : 1);
                com.xiaomi.onetrack.util.s.a("ServiceConnectManager", sb.toString());
            } catch (Throwable th) {
                com.xiaomi.onetrack.util.s.a("ServiceConnectManager", "onServiceDisconnected throwable:" + th.getMessage());
            }
        }
    };
    private CopyOnWriteArrayList<a> w = new CopyOnWriteArrayList<>();
    private Context g = com.xiaomi.onetrack.f.a.b();

    public interface a {
        void a();
    }

    public static class b extends Handler {
        private final WeakReference<be> a;

        public b(Looper looper, be beVar) {
            super(looper);
            this.a = new WeakReference<>(beVar);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            be beVar = this.a.get();
            if (beVar == null) {
                return;
            }
            int i = message.what;
            if (i == 1) {
                beVar.g();
                return;
            }
            if (i == 2) {
                beVar.e();
                return;
            }
            if (i == 3) {
                beVar.i();
            } else if (i == 4) {
                beVar.j();
            } else if (i == 5) {
                beVar.h();
            }
        }
    }

    private be() {
        HandlerThread handlerThread = new HandlerThread(o);
        handlerThread.start();
        b bVar = new b(handlerThread.getLooper(), this);
        this.m = bVar;
        bVar.sendEmptyMessage(2);
    }

    public static be a() {
        if (n == null) {
            b();
        }
        return n;
    }

    public static void b() {
        if (n == null) {
            synchronized (be.class) {
                try {
                    if (n == null) {
                        n = new be();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        this.d = null;
        this.f.set(false);
        this.e.set(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        if (!this.e.get() && !this.f.get() && this.d == null) {
            f();
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("ensureService mConnecting: ");
        sb.append(this.e.get());
        sb.append(" mIsBindSuccess:");
        sb.append(this.f.get());
        sb.append(" mAnalytics: ");
        sb.append(this.d == null ? 0 : 1);
        com.xiaomi.onetrack.util.s.a(c, sb.toString());
    }

    private void f() {
        this.e.set(true);
        try {
            Intent intent = new Intent();
            intent.setClassName(a, b);
            boolean zBindService = this.g.bindService(intent, this.v, 1);
            if (zBindService) {
                this.f.set(true);
            } else {
                this.f.set(false);
                g();
                i();
            }
            com.xiaomi.onetrack.util.s.a(c, "bindService:  mConnecting: " + this.e + " bindResult:" + zBindService);
        } catch (Throwable th) {
            if (com.xiaomi.onetrack.util.s.a) {
                com.xiaomi.onetrack.util.s.b(c, "bindService e: " + th);
            }
            g();
            i();
        }
        this.e.set(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        try {
            if (this.f.get()) {
                this.g.unbindService(this.v);
            }
        } catch (Throwable th) {
            com.xiaomi.onetrack.util.s.a(c, "unBindService Throwable: " + th.getMessage());
        }
        d();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        this.u = 10000;
        this.m.removeMessages(4);
        com.xiaomi.onetrack.util.ad.m(0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        try {
            if (OneTrack.isUseSystemNetTrafficOnly()) {
                return;
            }
            com.xiaomi.onetrack.util.s.d(c, "reBind, mRetryDelay: " + this.u + ", hasMessages: " + this.m.hasMessages(4));
            if (this.u <= t && !this.m.hasMessages(4)) {
                this.m.sendEmptyMessageDelayed(4, this.u);
                this.u *= 2;
            }
            long jG = com.xiaomi.onetrack.util.ad.G();
            long jCurrentTimeMillis = System.currentTimeMillis();
            com.xiaomi.onetrack.util.s.d(c, "lastRebindTime: " + jG + ", currentTime: " + jCurrentTimeMillis);
            if (jG == 0) {
                com.xiaomi.onetrack.util.ad.m(jCurrentTimeMillis);
            } else if (jCurrentTimeMillis - jG > 3600000) {
                com.xiaomi.onetrack.c.ag.a().b();
                com.xiaomi.onetrack.util.ad.m(jCurrentTimeMillis);
            }
        } catch (Throwable th) {
            Log.e(com.xiaomi.onetrack.util.s.a(c), "reBind error: " + th.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j() {
        long jC = com.xiaomi.onetrack.c.r.a().c();
        com.xiaomi.onetrack.util.s.d(c, "handleMessage, db remain: " + jC);
        if (jC > 0) {
            e();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k() {
        CopyOnWriteArrayList<a> copyOnWriteArrayList = this.w;
        if (copyOnWriteArrayList == null || copyOnWriteArrayList.size() == 0) {
            return;
        }
        Iterator<a> it = this.w.iterator();
        while (it.hasNext()) {
            it.next().a();
        }
    }

    public void a(int i2) {
        if (i2 == 2) {
            this.m.sendEmptyMessageDelayed(1, 5000L);
        } else if (this.m.hasMessages(1)) {
            this.m.removeMessages(1);
        }
    }

    public void a(a aVar) {
        if (this.w.contains(aVar)) {
            return;
        }
        this.w.add(aVar);
    }

    public boolean a(String str, String str2, String str3) {
        if (!this.e.get()) {
            if (this.d != null) {
                try {
                    this.d.trackEvent(str3, com.xiaomi.onetrack.f.a.e(), str, str2);
                    return true;
                } catch (Throwable th) {
                    if (com.xiaomi.onetrack.util.s.a) {
                        com.xiaomi.onetrack.util.s.a(c, "track throwable: " + th);
                    }
                    g();
                    i();
                    return false;
                }
            }
            this.m.sendEmptyMessage(2);
        }
        return false;
    }

    public boolean a(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        if (!this.e.get()) {
            if (this.d != null) {
                try {
                    this.d.trackPSEvent(str, str2, str3, str4, com.xiaomi.onetrack.f.a.e(), str5, str6, str7);
                    return true;
                } catch (Throwable th) {
                    if (com.xiaomi.onetrack.util.s.a) {
                        com.xiaomi.onetrack.util.s.a(c, "track ps throwable: " + th);
                    }
                    g();
                    i();
                    return false;
                }
            }
            this.m.sendEmptyMessage(2);
        }
        return false;
    }

    public boolean c() {
        return this.f.get();
    }
}
