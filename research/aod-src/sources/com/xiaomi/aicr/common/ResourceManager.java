package com.xiaomi.aicr.common;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.xiaomi.aicr.IDownloadListener;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;

/* JADX INFO: loaded from: classes2.dex */
public class ResourceManager implements ServiceConnection {
    public volatile boolean connected;
    public IDownloadListener mCoreService;
    public static final Object token = new Object();
    public static HashSet connectedService = new HashSet();

    /* JADX INFO: renamed from: com.xiaomi.aicr.common.ResourceManager$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {
        public final /* synthetic */ long[] val$fileSize;
        public final /* synthetic */ String val$finalUrl;

        @Override // java.lang.Runnable
        public void run() {
            try {
                URLConnection uRLConnectionOpenConnection = new URL(this.val$finalUrl).openConnection();
                uRLConnectionOpenConnection.connect();
                this.val$fileSize[0] = uRLConnectionOpenConnection.getContentLengthLong();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        IDownloadListener iDownloadListenerAsInterface = IDownloadListener.Stub.asInterface(iBinder);
        this.mCoreService = iDownloadListenerAsInterface;
        this.connected = iDownloadListenerAsInterface != null;
        SmartLog.d("ResourceManager", "mCoreService: " + this.connected);
        connectedService.add(toString());
        SmartLog.d("ResourceManager", "onServiceConnected, current: " + toString());
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        SmartLog.d("ResourceManager", "onServiceDisconnected, current: " + toString());
        this.mCoreService = null;
        this.connected = false;
    }

    public String toString() {
        return token.toString() + "/" + super.toString();
    }
}
