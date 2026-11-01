package micloud.compat.v18.finddevice;

import android.content.Context;

/* JADX INFO: loaded from: classes2.dex */
public class FindDeviceLocateManagerCompat_Base implements IFindDeviceLocateManagerCompat {
    @Override // micloud.compat.v18.finddevice.IFindDeviceLocateManagerCompat
    public boolean isLocalLocateSupported(Context context) {
        return false;
    }

    @Override // micloud.compat.v18.finddevice.IFindDeviceLocateManagerCompat
    public boolean isLowBatteryLocateEnabled(Context context) {
        return false;
    }

    @Override // micloud.compat.v18.finddevice.IFindDeviceLocateManagerCompat
    public boolean isPowerPressLocateEnabled(Context context) {
        return false;
    }

    @Override // micloud.compat.v18.finddevice.IFindDeviceLocateManagerCompat
    public void resetLowBatteryLocate(Context context) {
    }

    @Override // micloud.compat.v18.finddevice.IFindDeviceLocateManagerCompat
    public void resetPowerPressLocate(Context context) {
    }

    @Override // micloud.compat.v18.finddevice.IFindDeviceLocateManagerCompat
    public void setLowBatteryLocateEnabled(Context context, boolean z) {
    }

    @Override // micloud.compat.v18.finddevice.IFindDeviceLocateManagerCompat
    public void setPowerPressLocateEnabled(Context context, boolean z) {
    }
}
