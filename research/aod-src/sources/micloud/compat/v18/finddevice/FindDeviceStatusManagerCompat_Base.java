package micloud.compat.v18.finddevice;

import android.content.Context;
import android.os.RemoteException;
import miui.cloud.finddevice.FindDeviceStatusManager;

/* JADX INFO: loaded from: classes2.dex */
class FindDeviceStatusManagerCompat_Base implements IFindDeviceStatusManagerCompat {
    protected final FindDeviceStatusManager mFindDeviceStatusManager;

    public FindDeviceStatusManagerCompat_Base(Context context) {
        this.mFindDeviceStatusManager = FindDeviceStatusManager.obtain(context);
    }

    public static FindDeviceStatusManagerCompat_Base obtain(Context context) {
        return new FindDeviceStatusManagerCompat_Base(context);
    }

    @Override // micloud.compat.v18.finddevice.IFindDeviceStatusManagerCompat
    public void asyncOpen(boolean z) {
        this.mFindDeviceStatusManager.asyncOpen();
    }

    @Override // micloud.compat.v18.finddevice.IFindDeviceStatusManagerCompat
    public FindDeviceInfoCompat getFindDeviceInfo() throws InterruptedException, RemoteException {
        return null;
    }

    @Override // micloud.compat.v18.finddevice.IFindDeviceStatusManagerCompat
    public FindDeviceInfoCompat getFindDeviceInfoFromServer() throws InterruptedException, RemoteException, FindDeviceStatusManager.FindDeviceStatusManagerException {
        return null;
    }

    @Override // micloud.compat.v18.finddevice.IFindDeviceStatusManagerCompat
    public void release() {
        this.mFindDeviceStatusManager.release();
    }

    @Override // micloud.compat.v18.finddevice.IFindDeviceStatusManagerCompat
    public void withdraw() throws InterruptedException, RemoteException, FindDeviceStatusManager.FindDeviceStatusManagerException {
    }
}
