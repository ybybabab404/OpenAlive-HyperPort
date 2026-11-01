package micloud.compat.v18.backup;

import android.content.Context;
import com.xiaomi.micloudsdk.utils.MiCloudSdkBuild;
import miui.cloud.util.DeviceFeatureUtils;

/* JADX INFO: loaded from: classes2.dex */
public class CloudBackupInstalldManagerCompat {
    private static final ICloudBackupInstalldManagerCompat sCloudBackupInstalldManagerImpl;

    static {
        int i = MiCloudSdkBuild.CURRENT_VERSION;
        if (i >= 35) {
            sCloudBackupInstalldManagerImpl = new CloudBackupInstalldManagerCompat_V35();
        } else if (i >= 33) {
            sCloudBackupInstalldManagerImpl = new CloudBackupInstalldManagerCompat_V33();
        } else {
            sCloudBackupInstalldManagerImpl = null;
        }
    }

    public static ScanInfo getDataFileInfo(Context context, String str, long j) throws InterruptedException, InstalldInvokeFailedException, InstalldInvokeTimeoutException, InstalldOperateFailedException {
        if (isSupport()) {
            return sCloudBackupInstalldManagerImpl.getDataFileInfo(context, str, j);
        }
        throw new IllegalStateException("function not support. ");
    }

    public static boolean isFileChangeCheckSupport() {
        return isSupport() && DeviceFeatureUtils.hasDeviceFeature("support_file_change_check");
    }

    public static boolean isSupport() {
        ICloudBackupInstalldManagerCompat iCloudBackupInstalldManagerCompat = sCloudBackupInstalldManagerImpl;
        return iCloudBackupInstalldManagerCompat != null && iCloudBackupInstalldManagerCompat.isSupport();
    }

    public static ListDataDirResult listDataDir(Context context, String str, long j, int i, long j2) throws InterruptedException, InstalldInvokeFailedException, InstalldInvokeTimeoutException, InstalldOperateFailedException {
        if (isSupport()) {
            return sCloudBackupInstalldManagerImpl.listDataDir(context, str, j, i, j2);
        }
        throw new IllegalStateException("function not support. ");
    }

    public static void moveData(Context context, String str, String str2, String str3, String str4, int i, boolean z, int i2, boolean z2, long j) throws InterruptedException, InstalldInvokeFailedException, InstalldInvokeTimeoutException, InstalldOperateFailedException {
        if (!isSupport()) {
            throw new IllegalStateException("function not support. ");
        }
        sCloudBackupInstalldManagerImpl.moveData(context, str, str2, str3, str4, i, z, i2, z2, j);
    }
}
