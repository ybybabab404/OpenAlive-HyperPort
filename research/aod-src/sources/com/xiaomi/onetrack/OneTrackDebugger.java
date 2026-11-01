package com.xiaomi.onetrack;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.xiaomi.onetrack.util.oaid.a;
import com.xiaomi.onetrack.util.r;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes2.dex */
public class OneTrackDebugger {
    private static volatile OneTrackDebugger a = null;
    private static String b = "com.xiaomi.onetrack.otdebugger.FloatWindowService";
    private ConcurrentHashMap<Long, Configuration> c = new ConcurrentHashMap<>();

    private OneTrackDebugger() {
    }

    public static OneTrackDebugger getInstance() {
        if (a == null) {
            synchronized (OneTrackDebugger.class) {
                try {
                    if (a == null) {
                        a = new OneTrackDebugger();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return a;
    }

    public String getInstanceId() {
        return r.a().b();
    }

    public String getOaid(Context context) {
        return a.a().a(context.getApplicationContext());
    }

    public ConcurrentHashMap<Long, Configuration> getSdkConfig() {
        return this.c;
    }

    public void setSdkConfig(Configuration configuration) {
        this.c.put(Long.valueOf(System.currentTimeMillis()), configuration);
    }

    public void startDebugger() {
        try {
            com.xiaomi.onetrack.f.a.b().startService(new Intent(com.xiaomi.onetrack.f.a.b(), Class.forName(b)));
        } catch (Throwable th) {
            Log.d("startDebugger", th.getMessage());
        }
    }
}
