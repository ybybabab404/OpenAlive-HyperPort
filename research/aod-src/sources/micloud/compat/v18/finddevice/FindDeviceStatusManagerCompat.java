package micloud.compat.v18.finddevice;

import android.content.Context;
import android.os.RemoteException;
import com.xiaomi.micloudsdk.utils.MiCloudSdkBuild;
import miui.cloud.finddevice.FindDeviceStatusManager;

/* JADX INFO: loaded from: classes2.dex */
public class FindDeviceStatusManagerCompat {
    private final IFindDeviceStatusManagerCompat mFindDeviceStatusManagerImpl;

    private FindDeviceStatusManagerCompat(Context context) {
        int i = MiCloudSdkBuild.CURRENT_VERSION;
        if (i >= 36) {
            this.mFindDeviceStatusManagerImpl = FindDeviceStatusManagerCompat_V36.obtain(context);
            return;
        }
        if (i >= 34) {
            this.mFindDeviceStatusManagerImpl = FindDeviceStatusManagerCompat_V34.obtain(context);
        } else if (i >= 31) {
            this.mFindDeviceStatusManagerImpl = FindDeviceStatusManagerCompat_V31.obtain(context);
        } else {
            this.mFindDeviceStatusManagerImpl = FindDeviceStatusManagerCompat_Base.obtain(context);
        }
    }

    public static FindDeviceStatusManagerCompat obtain(Context context) {
        return new FindDeviceStatusManagerCompat(context);
    }

    public void asyncOpen(boolean z) {
        this.mFindDeviceStatusManagerImpl.asyncOpen(z);
    }

    public FindDeviceInfoCompat getFindDeviceInfo() throws InterruptedException, RemoteException {
        return this.mFindDeviceStatusManagerImpl.getFindDeviceInfo();
    }

    public FindDeviceInfoCompat getFindDeviceInfoFromServer() throws InterruptedException, RemoteException, FindDeviceStatusManager.FindDeviceStatusManagerException {
        return this.mFindDeviceStatusManagerImpl.getFindDeviceInfoFromServer();
    }

    public void release() {
        this.mFindDeviceStatusManagerImpl.release();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: miui.cloud.finddevice.FindDeviceStatusManager$FindDeviceStatusManagerException */
    public void withdraw() throws InterruptedException, RemoteException, FindDeviceStatusManager.FindDeviceStatusManagerException {
        this.mFindDeviceStatusManagerImpl.withdraw();
    }
}
