package micloud.compat.v18.finddevice;

import android.content.Context;
import android.os.RemoteException;
import miui.cloud.finddevice.FindDeviceInfo;
import miui.cloud.finddevice.FindDeviceStatusManager;

/* JADX INFO: loaded from: classes2.dex */
public class FindDeviceStatusManagerCompat_V34 extends FindDeviceStatusManagerCompat_V31 {
    public FindDeviceStatusManagerCompat_V34(Context context) {
        super(context);
    }

    public static FindDeviceStatusManagerCompat_V34 obtain(Context context) {
        return new FindDeviceStatusManagerCompat_V34(context);
    }

    @Override // micloud.compat.v18.finddevice.FindDeviceStatusManagerCompat_V31, micloud.compat.v18.finddevice.FindDeviceStatusManagerCompat_Base, micloud.compat.v18.finddevice.IFindDeviceStatusManagerCompat
    public /* bridge */ /* synthetic */ void asyncOpen(boolean z) {
        super.asyncOpen(z);
    }

    @Override // micloud.compat.v18.finddevice.FindDeviceStatusManagerCompat_Base, micloud.compat.v18.finddevice.IFindDeviceStatusManagerCompat
    public FindDeviceInfoCompat getFindDeviceInfo() throws InterruptedException, RemoteException {
        FindDeviceInfo findDeviceInfo = this.mFindDeviceStatusManager.getFindDeviceInfo();
        return new FindDeviceInfoCompat(findDeviceInfo.isOpen, findDeviceInfo.isLocked, findDeviceInfo.sessionUserId, findDeviceInfo.displayId, findDeviceInfo.fid, findDeviceInfo.email, findDeviceInfo.phone, findDeviceInfo.findToken);
    }

    @Override // micloud.compat.v18.finddevice.FindDeviceStatusManagerCompat_Base, micloud.compat.v18.finddevice.IFindDeviceStatusManagerCompat
    public FindDeviceInfoCompat getFindDeviceInfoFromServer() throws InterruptedException, RemoteException, FindDeviceStatusManager.FindDeviceStatusManagerException {
        FindDeviceInfo findDeviceInfoFromServer = this.mFindDeviceStatusManager.getFindDeviceInfoFromServer();
        return new FindDeviceInfoCompat(findDeviceInfoFromServer.isOpen, findDeviceInfoFromServer.isLocked, findDeviceInfoFromServer.sessionUserId, findDeviceInfoFromServer.displayId, findDeviceInfoFromServer.fid, findDeviceInfoFromServer.email, findDeviceInfoFromServer.phone, findDeviceInfoFromServer.findToken);
    }

    @Override // micloud.compat.v18.finddevice.FindDeviceStatusManagerCompat_Base, micloud.compat.v18.finddevice.IFindDeviceStatusManagerCompat
    public /* bridge */ /* synthetic */ void release() {
        super.release();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: miui.cloud.finddevice.FindDeviceStatusManager$FindDeviceStatusManagerException */
    @Override // micloud.compat.v18.finddevice.FindDeviceStatusManagerCompat_Base, micloud.compat.v18.finddevice.IFindDeviceStatusManagerCompat
    public /* bridge */ /* synthetic */ void withdraw() throws InterruptedException, RemoteException, FindDeviceStatusManager.FindDeviceStatusManagerException {
        super.withdraw();
    }
}
