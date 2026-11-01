package micloud.compat.v18.finddevice;

import android.os.RemoteException;
import miui.cloud.finddevice.FindDeviceStatusManager;

/* JADX INFO: loaded from: classes2.dex */
interface IFindDeviceStatusManagerCompat {
    void asyncOpen(boolean z);

    FindDeviceInfoCompat getFindDeviceInfo() throws InterruptedException, RemoteException;

    FindDeviceInfoCompat getFindDeviceInfoFromServer() throws InterruptedException, RemoteException, FindDeviceStatusManager.FindDeviceStatusManagerException;

    void release();

    void withdraw() throws InterruptedException, RemoteException, FindDeviceStatusManager.FindDeviceStatusManagerException;
}
