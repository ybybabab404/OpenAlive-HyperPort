package micloud.compat.v18.finddevice;

import android.content.Context;
import android.os.RemoteException;
import miui.cloud.finddevice.FindDeviceStatusManager;

/* JADX INFO: loaded from: classes2.dex */
public class FindDeviceStatusManagerCompat_V36 extends FindDeviceStatusManagerCompat_V34 {
    public FindDeviceStatusManagerCompat_V36(Context context) {
        super(context);
    }

    public static FindDeviceStatusManagerCompat_V36 obtain(Context context) {
        return new FindDeviceStatusManagerCompat_V36(context);
    }

    @Override // micloud.compat.v18.finddevice.FindDeviceStatusManagerCompat_V34, micloud.compat.v18.finddevice.FindDeviceStatusManagerCompat_Base, micloud.compat.v18.finddevice.IFindDeviceStatusManagerCompat
    public void withdraw() throws InterruptedException, RemoteException, FindDeviceStatusManager.FindDeviceStatusManagerException {
        this.mFindDeviceStatusManager.withdraw();
    }
}
