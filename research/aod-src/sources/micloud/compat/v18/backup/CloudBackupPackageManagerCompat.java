package micloud.compat.v18.backup;

import android.content.Context;
import com.xiaomi.micloudsdk.utils.MiCloudSdkBuild;

/* JADX INFO: loaded from: classes2.dex */
public class CloudBackupPackageManagerCompat {
    private static final ICloudBackupPackageManagerCompat sCloudBackupPackageManagerImpl;

    static {
        if (MiCloudSdkBuild.CURRENT_VERSION >= 33) {
            sCloudBackupPackageManagerImpl = new CloudBackupPackageManagerCompat_V33();
        } else {
            sCloudBackupPackageManagerImpl = null;
        }
    }

    public static void deletePackageAsUser(Context context, String str, int i, int i2, long j) throws InterruptedException, PackageManagerOperateFailedException, PackageManagerInvokeTimeoutException {
        if (!isSupport()) {
            throw new IllegalStateException("function not support. ");
        }
        sCloudBackupPackageManagerImpl.deletePackageAsUser(context, str, i, i2, j);
    }

    public static boolean isSupport() {
        return sCloudBackupPackageManagerImpl != null;
    }
}
