package micloud.compat.v18.backup;

import android.content.Context;

/* JADX INFO: loaded from: classes2.dex */
public interface ICloudBackupPackageManagerCompat {
    void deletePackageAsUser(Context context, String str, int i, int i2, long j) throws InterruptedException, PackageManagerOperateFailedException, PackageManagerInvokeTimeoutException;
}
