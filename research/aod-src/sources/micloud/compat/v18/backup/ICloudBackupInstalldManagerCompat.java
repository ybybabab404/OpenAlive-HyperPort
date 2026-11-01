package micloud.compat.v18.backup;

import android.content.Context;

/* JADX INFO: loaded from: classes2.dex */
public interface ICloudBackupInstalldManagerCompat {
    ScanInfo getDataFileInfo(Context context, String str, long j) throws InterruptedException, InstalldInvokeFailedException, InstalldInvokeTimeoutException, InstalldOperateFailedException;

    boolean isSupport();

    ListDataDirResult listDataDir(Context context, String str, long j, int i, long j2) throws InterruptedException, InstalldInvokeFailedException, InstalldInvokeTimeoutException, InstalldOperateFailedException;

    void moveData(Context context, String str, String str2, String str3, String str4, int i, boolean z, int i2, boolean z2, long j) throws InterruptedException, InstalldInvokeFailedException, InstalldInvokeTimeoutException, InstalldOperateFailedException;
}
