package com.xiaomi.aicr.common;

import android.app.ForegroundServiceStartNotAllowedException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import com.xiaomi.aicr.IAiCrCoreService;
import com.xiaomi.aicr.ICoreServiceCallback;
import java.util.HashSet;

/* JADX INFO: loaded from: classes2.dex */
public abstract class ManagerBase extends ICoreServiceCallback.Stub implements ServiceConnection {
    public volatile boolean connected;
    public Context mContext;
    public IAiCrCoreService mCoreService;
    public AiServiceConnection mServiceConn;
    public static final Object token = new Object();
    public static HashSet connectedService = new HashSet();

    public ManagerBase(Context context) {
        this.connected = false;
        SmartLog.d("ManagerBase", "ManagerBase, create manager: " + this);
        this.mContext = context;
        connectCoreService();
    }

    public ManagerBase(Context context, AiServiceConnection aiServiceConnection) {
        this(context);
        this.mServiceConn = aiServiceConnection;
    }

    public void connectCoreService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.xiaomi.aicr", "com.xiaomi.aicr.access.AiCrCoreService"));
        intent.putExtra("bind_type", toString());
        try {
            this.mContext.startForegroundService(intent);
        } catch (ForegroundServiceStartNotAllowedException | SecurityException e) {
            SmartLog.e("ManagerBase", "start service failed", e);
        }
        try {
            this.mContext.bindService(intent, this, 1);
        } catch (SecurityException e2) {
            SmartLog.e("ManagerBase", "bind service failed", e2);
        }
    }

    public abstract void downloadSpeed(String str, long j);

    public boolean isConnected() {
        return this.connected;
    }

    public boolean isSupport(String str) {
        try {
            this.mContext.getPackageManager().getPackageInfo("com.xiaomi.aicr", 0);
            IAiCrCoreService iAiCrCoreService = this.mCoreService;
            return iAiCrCoreService != null && iAiCrCoreService.isSupport(str);
        } catch (PackageManager.NameNotFoundException | RemoteException unused) {
            SmartLog.i("ManagerBase", "isSupport: false!!!");
            return false;
        }
    }

    @Override // com.xiaomi.aicr.ICoreServiceCallback
    public void onDownload(IBinder iBinder) {
        SmartLog.d("ManagerBase", "onDownload" + iBinder.toString());
        IAiCrCoreService iAiCrCoreService = this.mCoreService;
        if (iAiCrCoreService != null) {
            registerPlugin(iAiCrCoreService);
        }
    }

    public void onFirstTimeUse(String str) {
        SmartLog.d("ManagerBase", "use " + str + " first time, downloading...");
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.mCoreService = IAiCrCoreService.Stub.asInterface(iBinder);
        connectedService.add(toString());
        SmartLog.d("ManagerBase", "onServiceConnected, current: " + toString());
        registerPlugin(this.mCoreService);
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        SmartLog.d("ManagerBase", "onServiceDisconnected, current: " + toString());
        this.mCoreService = null;
        this.connected = false;
        unregisterPlugin();
    }

    public abstract void registerPlugin(IAiCrCoreService iAiCrCoreService);

    public void releaseConnect() {
        if (connectedService.contains(toString())) {
            SmartLog.d("ManagerBase", "release connected service:" + toString());
            connectedService.remove(toString());
            try {
                this.mCoreService.releaseConnect(toString());
            } catch (Exception unused) {
                SmartLog.d("ManagerBase", "fail to release connect :" + toString());
            }
        }
        if (connectedService.isEmpty()) {
            SmartLog.d("ManagerBase", "no service connection, unbind" + toString());
            try {
                this.mContext.unbindService(this);
            } catch (Exception e) {
                SmartLog.e("ManagerBase", "unbind error", e);
            }
        }
    }

    @Override // com.xiaomi.aicr.ICoreServiceCallback
    public void speed(String str, long j) {
        downloadSpeed(str, j);
    }

    public String toString() {
        return token.toString() + "/" + super.toString();
    }

    public abstract void unregisterPlugin();

    public boolean waitForConnection(long j) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        SmartLog.d("ManagerBase", "current " + jCurrentTimeMillis);
        if (Looper.getMainLooper().isCurrentThread()) {
            SmartLog.d("ManagerBase", "waitForConnection can't be called in main thread!");
            return false;
        }
        while (System.currentTimeMillis() <= jCurrentTimeMillis + j) {
            if (this.connected) {
                return true;
            }
            try {
                Thread.sleep(40L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.connected;
    }
}
