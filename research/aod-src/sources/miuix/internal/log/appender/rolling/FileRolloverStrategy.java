package miuix.internal.log.appender.rolling;

import android.util.Log;
import java.io.File;

/* JADX INFO: loaded from: classes2.dex */
public class FileRolloverStrategy implements RolloverStrategy {
    public int mMaxBackupIndex = 1;
    public long mMaxFileSize = 1048576;

    @Override // miuix.internal.log.appender.rolling.RolloverStrategy
    public String rollover(RollingFileManager rollingFileManager) {
        if (rollingFileManager.getLogSize() < this.mMaxFileSize) {
            return null;
        }
        File logFile = rollingFileManager.getLogFile();
        Log.d("FileRolloverStrategy", "Start to rollover");
        String str = logFile.getPath() + '.';
        for (int i = this.mMaxBackupIndex - 1; i > 0; i--) {
            File file = new File(str + i);
            if (file.exists()) {
                file.renameTo(new File(str + (i + 1)));
            }
        }
        logFile.renameTo(new File(str + 1));
        Log.d("FileRolloverStrategy", "Rollover done");
        return logFile.getPath();
    }

    public void setMaxBackupIndex(int i) {
        if (i >= 1) {
            this.mMaxBackupIndex = i;
            return;
        }
        throw new IllegalArgumentException("index can't be less than 1: " + i);
    }

    public void setMaxFileSize(int i) {
        if (i >= 1) {
            this.mMaxFileSize = i;
            return;
        }
        throw new IllegalArgumentException("size can't be less than 1: " + i);
    }
}
