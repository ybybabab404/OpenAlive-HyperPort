package com.xiaomi.onetrack.util.oaid.helpers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.xiaomi.onetrack.util.s;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes2.dex */
public class n {
    private static final String d = "ZTEDeviceIDHelper";
    String a = "com.mdid.msa";
    public final LinkedBlockingQueue<IBinder> b = new LinkedBlockingQueue<>(1);
    ServiceConnection c = new ServiceConnection() { // from class: com.xiaomi.onetrack.util.oaid.helpers.ZTEDeviceIDHelper$1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.a.b.offer(iBinder, 1L, TimeUnit.SECONDS);
            } catch (Exception e) {
                s.a("ZTEDeviceIDHelper", e.getMessage());
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    private void a(String str, Context context) {
        Intent intent = new Intent();
        intent.setClassName(this.a, "com.mdid.msa.service.MsaKlService");
        intent.setAction("com.bun.msa.action.start.service");
        intent.putExtra("com.bun.msa.param.pkgname", str);
        try {
            intent.putExtra("com.bun.msa.param.runinset", true);
            context.startService(intent);
        } catch (Exception e) {
            s.a(d, e.getMessage());
        }
    }

    /* JADX INFO: Removed unreachable split cross block B:38:0x0091 */
    /* JADX WARN: Multi-variable type inference failed */
    public String a(Context context) {
        try {
            context.getPackageManager().getPackageInfo(this.a, 0);
        } catch (Exception e) {
            s.a(d, e.getMessage());
        }
        String packageName = context.getPackageName();
        a(packageName, context);
        Intent intent = new Intent();
        intent.setClassName("com.mdid.msa", "com.mdid.msa.service.MsaIdService");
        intent.setAction("com.bun.msa.action.bindto.service");
        intent.putExtra("com.bun.msa.param.pkgname", packageName);
        boolean zBindService = context.bindService(intent, this.c, 1);
        String strB = "";
        try {
            try {
                if (zBindService) {
                    try {
                        IBinder iBinderPoll = this.b.poll(1L, TimeUnit.SECONDS);
                        if (iBinderPoll == null) {
                            try {
                                context.unbindService(this.c);
                            } catch (Exception e2) {
                                s.a(d, e2.getMessage());
                            }
                            return "";
                        }
                        strB = new com.xiaomi.onetrack.util.oaid.a.h.a.C0054a(iBinderPoll).b();
                        ServiceConnection serviceConnection = this.c;
                        context.unbindService(serviceConnection);
                        this = serviceConnection;
                    } catch (Exception e3) {
                        s.a(d, e3.getMessage());
                        ServiceConnection serviceConnection2 = this.c;
                        context.unbindService(serviceConnection2);
                        this = serviceConnection2;
                    }
                }
            } catch (Throwable th) {
                try {
                    context.unbindService(this.c);
                } catch (Exception e4) {
                    s.a(d, e4.getMessage());
                }
                throw th;
            }
        } catch (Exception e5) {
            String message = e5.getMessage();
            s.a(d, message);
            this = message;
        }
        return strB;
    }
}
