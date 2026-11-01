package miuix.internal.log.appender.rolling;

import miuix.internal.log.appender.FileManager;

/* JADX INFO: loaded from: classes2.dex */
public class RollingFileManager extends FileManager {
    public String mLogPath;
    public RolloverStrategy mRolloverStrategy;

    public RollingFileManager(String str, String str2) {
        super(str, str2);
    }

    public final void checkRollover() {
        RolloverStrategy rolloverStrategy;
        if (this.mLogFile == null || (rolloverStrategy = this.mRolloverStrategy) == null) {
            return;
        }
        String strRollover = rolloverStrategy.rollover(this);
        this.mLogPath = strRollover;
        if (strRollover != null) {
            close();
        }
    }

    @Override // miuix.internal.log.appender.FileManager
    public String onBuildLogPath() {
        String str = this.mLogPath;
        return str == null ? super.onBuildLogPath() : str;
    }

    public synchronized void setRolloverStrategy(RolloverStrategy rolloverStrategy) {
        this.mRolloverStrategy = rolloverStrategy;
    }

    @Override // miuix.internal.log.appender.FileManager
    public synchronized void write(String str) {
        checkRollover();
        super.write(str);
    }
}
