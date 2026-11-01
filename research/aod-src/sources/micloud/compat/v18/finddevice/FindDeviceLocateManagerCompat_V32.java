package micloud.compat.v18.finddevice;

import android.content.Context;
import miui.cloud.finddevice.FindDeviceLocateManager;

/* JADX INFO: loaded from: classes2.dex */
public class FindDeviceLocateManagerCompat_V32 extends FindDeviceLocateManagerCompat_Base {
    @Override // micloud.compat.v18.finddevice.FindDeviceLocateManagerCompat_Base, micloud.compat.v18.finddevice.IFindDeviceLocateManagerCompat
    public boolean isLocalLocateSupported(Context context) {
        return FindDeviceLocateManager.isServerSupported(context);
    }

    @Override // micloud.compat.v18.finddevice.FindDeviceLocateManagerCompat_Base, micloud.compat.v18.finddevice.IFindDeviceLocateManagerCompat
    public boolean isLowBatteryLocateEnabled(Context context) {
        return FindDeviceLocateManager.isLowBatteryLocateEnabled(context);
    }

    @Override // micloud.compat.v18.finddevice.FindDeviceLocateManagerCompat_Base, micloud.compat.v18.finddevice.IFindDeviceLocateManagerCompat
    public boolean isPowerPressLocateEnabled(Context context) {
        return FindDeviceLocateManager.isPowerPressLocateEnabled(context);
    }

    @Override // micloud.compat.v18.finddevice.FindDeviceLocateManagerCompat_Base, micloud.compat.v18.finddevice.IFindDeviceLocateManagerCompat
    public void resetLowBatteryLocate(Context context) {
        FindDeviceLocateManager.resetLowBatteryLocate(context);
    }

    @Override // micloud.compat.v18.finddevice.FindDeviceLocateManagerCompat_Base, micloud.compat.v18.finddevice.IFindDeviceLocateManagerCompat
    public void resetPowerPressLocate(Context context) {
        FindDeviceLocateManager.resetPowerPressLocate(context);
    }

    @Override // micloud.compat.v18.finddevice.FindDeviceLocateManagerCompat_Base, micloud.compat.v18.finddevice.IFindDeviceLocateManagerCompat
    public void setLowBatteryLocateEnabled(Context context, boolean z) {
        FindDeviceLocateManager.setLowBatteryLocateEnabled(context, z);
    }

    @Override // micloud.compat.v18.finddevice.FindDeviceLocateManagerCompat_Base, micloud.compat.v18.finddevice.IFindDeviceLocateManagerCompat
    public void setPowerPressLocateEnabled(Context context, boolean z) {
        FindDeviceLocateManager.setPowerPressLocateEnabled(context, z);
    }
}
