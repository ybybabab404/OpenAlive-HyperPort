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
public class l {
    private static final String c = "SamsungDeviceIDHelper";
    public final LinkedBlockingQueue<IBinder> a = new LinkedBlockingQueue<>(1);
    ServiceConnection b = new ServiceConnection() { // from class: com.xiaomi.onetrack.util.oaid.helpers.SamsungDeviceIDHelper$1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.a.a.offer(iBinder, 1L, TimeUnit.SECONDS);
            } catch (Exception e) {
                s.a("SamsungDeviceIDHelper", e.getMessage());
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    public String a(Context context) {
        Intent intent = new Intent();
        intent.setClassName("com.samsung.android.deviceidservice", "com.samsung.android.deviceidservice.DeviceIdService");
        String strA = "";
        if (context.bindService(intent, this.b, 1)) {
            try {
                try {
                    try {
                        IBinder iBinderPoll = this.a.poll(1L, TimeUnit.SECONDS);
                        if (iBinderPoll == null) {
                            try {
                                context.unbindService(this.b);
                                return "";
                            } catch (Exception e) {
                                s.a(c, e.getMessage());
                                return "";
                            }
                        }
                        strA = new com.xiaomi.onetrack.util.oaid.a.g.a(iBinderPoll).a();
                        context.unbindService(this.b);
                    } catch (Exception e2) {
                        s.a(c, e2.getMessage());
                        context.unbindService(this.b);
                    }
                } catch (Exception e3) {
                    s.a(c, e3.getMessage());
                }
            } catch (Throwable th) {
                try {
                    context.unbindService(this.b);
                } catch (Exception e4) {
                    s.a(c, e4.getMessage());
                }
                throw th;
            }
        }
        return strA;
    }
}
