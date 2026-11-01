package miui.cloud.util;

import android.content.ContentResolver;

/* JADX INFO: loaded from: classes2.dex */
public class SyncAutoSettingUtil {
    public static boolean getXiaomiGlobalSyncAutomatically() {
        if (DeviceFeatureUtils.hasDeviceFeature("exempt_master_sync_auto")) {
            return true;
        }
        return ContentResolver.getMasterSyncAutomatically();
    }
}
