package com.xiaomi.micloudkeybag;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

/* JADX INFO: loaded from: classes2.dex */
public class MiCloudKeyBagServiceManager implements ServiceConnection {
    public IMiCloudKeyBagService mService;

    /* JADX INFO: renamed from: com.xiaomi.micloudkeybag.MiCloudKeyBagServiceManager$1, reason: invalid class name */
    class AnonymousClass1 extends OnKeyBagCallFinishedListener<Boolean> {
        @Override // com.xiaomi.micloudkeybag.OnKeyBagCallFinishedListener, com.xiaomi.micloudkeybag.IOnKeyBagCallFinishedListener
        public void onIsSupportedFinished(int i, boolean z) {
            setResult(i, Boolean.valueOf(z));
        }
    }

    public class Cipher {

        /* JADX INFO: renamed from: com.xiaomi.micloudkeybag.MiCloudKeyBagServiceManager$Cipher$1, reason: invalid class name */
        class AnonymousClass1 extends OnKeyBagCallFinishedListener<byte[]> {
            @Override // com.xiaomi.micloudkeybag.OnKeyBagCallFinishedListener, com.xiaomi.micloudkeybag.IOnKeyBagCallFinishedListener
            public void onEncryptFinished(int i, byte[] bArr) {
                setResult(i, bArr);
            }
        }

        /* JADX INFO: renamed from: com.xiaomi.micloudkeybag.MiCloudKeyBagServiceManager$Cipher$2, reason: invalid class name */
        class AnonymousClass2 extends OnKeyBagCallFinishedListener<byte[]> {
            @Override // com.xiaomi.micloudkeybag.OnKeyBagCallFinishedListener, com.xiaomi.micloudkeybag.IOnKeyBagCallFinishedListener
            public void onDecryptFinished(int i, byte[] bArr) {
                setResult(i, bArr);
            }
        }
    }

    @Override // android.content.ServiceConnection
    public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.mService = IMiCloudKeyBagService.Stub.asInterface(iBinder);
        notifyAll();
        Log.i("MiCloudKeyBagServiceManager", "onServiceConnected");
    }

    @Override // android.content.ServiceConnection
    public synchronized void onServiceDisconnected(ComponentName componentName) {
        this.mService = null;
        Log.i("MiCloudKeyBagServiceManager", "onServiceDisConnected");
    }
}
