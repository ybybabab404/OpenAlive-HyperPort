package micloud.compat.v18.sync;

import android.content.SyncResult;

/* JADX INFO: loaded from: classes2.dex */
class SyncResultAdapterCompatMiCloud_V29 extends SyncResultAdapterCompatMiCloud_Base {
    @Override // micloud.compat.v18.sync.SyncResultAdapterCompatMiCloud_Base, micloud.compat.v18.sync.ISyncResultAdapterCompat
    public void setMiSyncResultMessage(SyncResult syncResult, String str) {
        miui.cloud.content.SyncResult.setMiSyncResultMessage(syncResult, str);
    }
}
