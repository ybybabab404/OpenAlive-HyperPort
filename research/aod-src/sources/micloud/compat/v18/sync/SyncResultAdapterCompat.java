package micloud.compat.v18.sync;

import android.content.SyncResult;
import com.xiaomi.micloudsdk.utils.MiCloudSdkBuild;

/* JADX INFO: loaded from: classes2.dex */
public class SyncResultAdapterCompat {
    private static final ISyncResultAdapterCompat sSyncResultAdapterCompatImpl;

    static {
        if (MiCloudSdkBuild.CURRENT_VERSION >= 29) {
            sSyncResultAdapterCompatImpl = new SyncResultAdapterCompatMiCloud_V29();
        } else {
            sSyncResultAdapterCompatImpl = new SyncResultAdapterCompatMiCloud_Base();
        }
    }

    private SyncResultAdapterCompat() {
    }

    public static void setMiSyncResultMessage(SyncResult syncResult, String str) {
        sSyncResultAdapterCompatImpl.setMiSyncResultMessage(syncResult, str);
    }
}
