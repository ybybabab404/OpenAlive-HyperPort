package micloud.compat.v18.backup;

import android.content.Context;
import miui.cloud.app.backup.InstalldAdapter;

/* JADX INFO: loaded from: classes2.dex */
public class CloudBackupInstalldManagerCompat_V33 implements ICloudBackupInstalldManagerCompat {
    @Override // micloud.compat.v18.backup.ICloudBackupInstalldManagerCompat
    public ScanInfo getDataFileInfo(Context context, String str, long j) throws InterruptedException, InstalldInvokeFailedException, InstalldInvokeTimeoutException, InstalldOperateFailedException {
        try {
            miui.cloud.app.backup.ScanDirInfo dataFileInfo = InstalldAdapter.getDataFileInfo(context, str, j);
            if (dataFileInfo instanceof miui.cloud.app.backup.ScanDirInfo) {
                return new ScanDirInfo(dataFileInfo.path);
            }
            if (dataFileInfo instanceof miui.cloud.app.backup.ScanFileInfo) {
                miui.cloud.app.backup.ScanFileInfo scanFileInfo = (miui.cloud.app.backup.ScanFileInfo) dataFileInfo;
                return new ScanFileInfo(scanFileInfo.path, scanFileInfo.md5, scanFileInfo.sha1, scanFileInfo.size);
            }
            throw new IllegalStateException("unknown scan info " + dataFileInfo);
        } catch (miui.cloud.app.backup.InstalldInvokeTimeoutException e) {
            throw new InstalldInvokeTimeoutException(e);
        } catch (miui.cloud.app.backup.InstalldInvokeFailedException e2) {
            throw new InstalldInvokeFailedException(e2);
        } catch (miui.cloud.app.backup.InstalldOperateFailedException e3) {
            throw new InstalldOperateFailedException(e3.errCode, e3);
        }
    }

    @Override // micloud.compat.v18.backup.ICloudBackupInstalldManagerCompat
    public boolean isSupport() {
        return InstalldAdapter.isSupport();
    }

    @Override // micloud.compat.v18.backup.ICloudBackupInstalldManagerCompat
    public ListDataDirResult listDataDir(Context context, String str, long j, int i, long j2) throws InterruptedException, InstalldInvokeFailedException, InstalldInvokeTimeoutException, InstalldOperateFailedException {
        try {
            miui.cloud.app.backup.ListDataDirResult listDataDirResultListDataDir = InstalldAdapter.listDataDir(context, str, j, i, j2);
            return new ListDataDirResult(listDataDirResultListDataDir.list, listDataDirResultListDataDir.offset);
        } catch (miui.cloud.app.backup.InstalldInvokeTimeoutException e) {
            throw new InstalldInvokeTimeoutException(e);
        } catch (miui.cloud.app.backup.InstalldInvokeFailedException e2) {
            throw new InstalldInvokeFailedException(e2);
        } catch (miui.cloud.app.backup.InstalldOperateFailedException e3) {
            throw new InstalldOperateFailedException(e3.errCode, e3);
        }
    }

    @Override // micloud.compat.v18.backup.ICloudBackupInstalldManagerCompat
    public void moveData(Context context, String str, String str2, String str3, String str4, int i, boolean z, int i2, boolean z2, long j) throws InterruptedException, InstalldInvokeFailedException, InstalldInvokeTimeoutException, InstalldOperateFailedException {
        try {
            InstalldAdapter.moveData(context, str, str2, str3, str4, i, z, i2, j);
        } catch (miui.cloud.app.backup.InstalldInvokeTimeoutException e) {
            throw new InstalldInvokeTimeoutException(e);
        } catch (miui.cloud.app.backup.InstalldInvokeFailedException e2) {
            throw new InstalldInvokeFailedException(e2);
        } catch (miui.cloud.app.backup.InstalldOperateFailedException e3) {
            throw new InstalldOperateFailedException(e3.errCode, e3);
        }
    }
}
