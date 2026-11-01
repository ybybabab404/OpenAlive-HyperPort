package micloud.compat.v18.stat;

import android.content.Context;
import android.os.Bundle;

/* JADX INFO: loaded from: classes2.dex */
interface IMiCloudStatUtilCompat {
    void performSyncErrorStat(Context context, long j, Bundle bundle);

    void performSyncPhoneStateStat(Context context, Bundle bundle);

    void performSyncSuccessStat(Context context, String str, long j, Bundle bundle);

    void setMasterSyncAutomatically(Context context, boolean z);

    void startMiCloudMainActivity(Context context);

    void wrapErrorBundle(Bundle bundle, String str, String str2, boolean z);
}
