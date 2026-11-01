package micloud.compat.v18.backup;

import android.content.Context;
import miui.cloud.app.backup.InstalldAdapter;

/* JADX INFO: loaded from: classes2.dex */
public class CloudBackupInstalldManagerCompat_V35 extends CloudBackupInstalldManagerCompat_V33 {
    @Override // micloud.compat.v18.backup.CloudBackupInstalldManagerCompat_V33, micloud.compat.v18.backup.ICloudBackupInstalldManagerCompat
    public void moveData(Context context, String str, String str2, String str3, String str4, int i, boolean z, int i2, boolean z2, long j) throws InterruptedException, InstalldInvokeFailedException, InstalldInvokeTimeoutException, InstalldOperateFailedException {
        try {
            InstalldAdapter.moveData(context, str, str2, str3, str4, i, z, i2, z2, j);
        } catch (miui.cloud.app.backup.InstalldOperateFailedException e) {
            throw new InstalldOperateFailedException(e.errCode, e);
        } catch (miui.cloud.app.backup.InstalldInvokeFailedException e2) {
            throw new InstalldInvokeFailedException(e2);
        } catch (miui.cloud.app.backup.InstalldInvokeTimeoutException e3) {
            throw new InstalldInvokeTimeoutException(e3);
        }
    }
}
