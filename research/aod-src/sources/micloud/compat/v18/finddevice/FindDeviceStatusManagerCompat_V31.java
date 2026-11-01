package micloud.compat.v18.finddevice;

import android.content.Context;

/* JADX INFO: loaded from: classes2.dex */
class FindDeviceStatusManagerCompat_V31 extends FindDeviceStatusManagerCompat_Base {
    public FindDeviceStatusManagerCompat_V31(Context context) {
        super(context);
    }

    public static FindDeviceStatusManagerCompat_V31 obtain(Context context) {
        return new FindDeviceStatusManagerCompat_V31(context);
    }

    @Override // micloud.compat.v18.finddevice.FindDeviceStatusManagerCompat_Base, micloud.compat.v18.finddevice.IFindDeviceStatusManagerCompat
    public void asyncOpen(boolean z) {
        this.mFindDeviceStatusManager.asyncOpen(z);
    }
}
