package com.xiaomi.onetrack.api;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.xiaomi.onetrack.OneTrack;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes2.dex */
public class ai {
    private static final String a = "BroadcastManager";
    private static String b = "onetrack_broadcast_manager";
    private static volatile ai c = null;
    private static final int e = 10;
    private static final int f = 100;
    private static final int g = 101;
    private static volatile boolean h = false;
    private static volatile boolean j = false;
    private Handler d;
    private CopyOnWriteArrayList<ap> i = new CopyOnWriteArrayList<>();
    private AtomicBoolean k = new AtomicBoolean(false);
    private boolean l = false;
    private boolean m = false;
    private BroadcastReceiver n = new aj(this);
    private BroadcastReceiver o = new ak(this);

    public class a extends Handler {
        private a(Looper looper) {
            super(looper);
        }

        public /* synthetic */ a(ai aiVar, Looper looper, aj ajVar) {
            this(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            boolean zA;
            if (message == null) {
                return;
            }
            int i = message.what;
            if (i == 100 || i == 101) {
                try {
                    ai.this.a(i);
                } catch (Exception e) {
                    com.xiaomi.onetrack.util.s.a(ai.a, "screenReceiver exception: ", e);
                }
            }
            if (message.what == 10) {
                if (ai.this.k.get()) {
                    try {
                        if (OneTrack.isRestrictGetNetworkInfo()) {
                            zA = com.xiaomi.onetrack.b.o.c();
                            com.xiaomi.onetrack.b.o.b(!zA);
                        } else {
                            zA = com.xiaomi.onetrack.g.c.a();
                            com.xiaomi.onetrack.b.o.b(zA);
                        }
                        com.xiaomi.onetrack.util.s.a(ai.a, "Only one of allowed NetworkInfo :" + OneTrack.isRestrictGetNetworkInfo() + " ,network status changed, isNetworkConnected: " + com.xiaomi.onetrack.b.o.c());
                        if (zA) {
                            if (ai.this.l) {
                                com.xiaomi.onetrack.a.c.b.a().c(com.xiaomi.onetrack.b.o.c());
                            }
                            if (ai.this.m) {
                                com.xiaomi.onetrack.c.ag.a().a(com.xiaomi.onetrack.b.o.c());
                            }
                        }
                    } catch (Throwable th) {
                        com.xiaomi.onetrack.util.s.b(ai.a, "MESSAGE_BROADCAST_NET_RECEIVER throwable:" + th.getMessage());
                    }
                }
                ai.this.k.set(true);
            }
        }
    }

    private ai() {
        try {
            HandlerThread handlerThread = new HandlerThread(b);
            handlerThread.start();
            this.d = new a(this, handlerThread.getLooper(), null);
        } catch (Throwable unused) {
        }
    }

    public static ai a() {
        if (c == null) {
            b();
        }
        return c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        for (ap apVar : this.i) {
            if (i == 100) {
                apVar.a(true);
            } else if (i == 101) {
                apVar.a(false);
            }
        }
    }

    public static void b() {
        if (c == null) {
            synchronized (ai.class) {
                try {
                    if (c == null) {
                        c = new ai();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void g() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        com.xiaomi.onetrack.f.a.b().registerReceiver(this.n, intentFilter);
        Log.d(com.xiaomi.onetrack.util.s.a(a), "register screen receiver");
    }

    private void h() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        com.xiaomi.onetrack.f.a.b().registerReceiver(this.o, intentFilter);
        Log.d(com.xiaomi.onetrack.util.s.a(a), "register net receiver");
    }

    public void a(ap apVar) {
        if (this.i.contains(apVar)) {
            return;
        }
        this.i.add(apVar);
    }

    public void c() {
        this.l = true;
    }

    public void d() {
        this.m = true;
    }

    public void e() {
        if (h) {
            return;
        }
        h = true;
        try {
            g();
        } catch (Throwable unused) {
            h = false;
        }
    }

    public void f() {
        if (j) {
            return;
        }
        j = true;
        boolean zB = com.xiaomi.onetrack.g.c.b();
        com.xiaomi.onetrack.util.s.a(a, "Get network status for the first time, isNetworkConnected: " + zB);
        com.xiaomi.onetrack.b.o.b(zB);
        try {
            h();
        } catch (Throwable unused) {
            j = false;
        }
    }
}
