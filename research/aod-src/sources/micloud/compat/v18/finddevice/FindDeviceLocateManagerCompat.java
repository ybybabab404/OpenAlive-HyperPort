package micloud.compat.v18.finddevice;

import android.content.Context;
import com.xiaomi.micloudsdk.utils.MiCloudSdkBuild;

/* JADX INFO: loaded from: classes2.dex */
public class FindDeviceLocateManagerCompat {
    private static final IFindDeviceLocateManagerCompat sFindDeviceLocateManagerImpl;

    static {
        if (MiCloudSdkBuild.CURRENT_VERSION >= 32) {
            sFindDeviceLocateManagerImpl = new FindDeviceLocateManagerCompat_V32();
        } else {
            sFindDeviceLocateManagerImpl = new FindDeviceLocateManagerCompat_Base();
        }
    }

    public static boolean isLocalLocateSupported(Context context) {
        return sFindDeviceLocateManagerImpl.isLocalLocateSupported(context);
    }

    public static boolean isLowBatteryLocateEnabled(Context context) {
        return sFindDeviceLocateManagerImpl.isLowBatteryLocateEnabled(context);
    }

    public static boolean isPowerPressLocateEnabled(Context context) {
        return sFindDeviceLocateManagerImpl.isPowerPressLocateEnabled(context);
    }

    public static void resetLowBatteryLocate(Context context) {
        sFindDeviceLocateManagerImpl.resetLowBatteryLocate(context);
    }

    public static void resetPowerPressLocate(Context context) {
        sFindDeviceLocateManagerImpl.resetPowerPressLocate(context);
    }

    public static void setLowBatteryLocateEnabled(Context context, boolean z) {
        sFindDeviceLocateManagerImpl.setLowBatteryLocateEnabled(context, z);
    }

    public static void setPowerPressLocateEnabled(Context context, boolean z) {
        sFindDeviceLocateManagerImpl.setPowerPressLocateEnabled(context, z);
    }
}
