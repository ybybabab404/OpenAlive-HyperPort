package micloud.compat.v18.backup;

import android.content.Context;
import miui.cloud.app.backup.PackageManagerAdapter;

/* JADX INFO: loaded from: classes2.dex */
public class CloudBackupPackageManagerCompat_V33 implements ICloudBackupPackageManagerCompat {
    @Override // micloud.compat.v18.backup.ICloudBackupPackageManagerCompat
    public void deletePackageAsUser(Context context, String str, int i, int i2, long j) throws InterruptedException, PackageManagerOperateFailedException, PackageManagerInvokeTimeoutException {
        try {
            PackageManagerAdapter.deletePackageAsUser(context, str, i, i2, j);
        } catch (miui.cloud.app.backup.PackageManagerOperateFailedException e) {
            throw new PackageManagerOperateFailedException(e.errCode, e);
        } catch (miui.cloud.app.backup.PackageManagerInvokeTimeoutException e2) {
            throw new PackageManagerInvokeTimeoutException(e2);
        }
    }
}
