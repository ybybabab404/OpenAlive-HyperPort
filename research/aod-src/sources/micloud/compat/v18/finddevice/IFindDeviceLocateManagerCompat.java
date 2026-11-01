package micloud.compat.v18.finddevice;

import android.content.Context;

/* JADX INFO: loaded from: classes2.dex */
public interface IFindDeviceLocateManagerCompat {
    boolean isLocalLocateSupported(Context context);

    boolean isLowBatteryLocateEnabled(Context context);

    boolean isPowerPressLocateEnabled(Context context);

    void resetLowBatteryLocate(Context context);

    void resetPowerPressLocate(Context context);

    void setLowBatteryLocateEnabled(Context context, boolean z);

    void setPowerPressLocateEnabled(Context context, boolean z);
}
