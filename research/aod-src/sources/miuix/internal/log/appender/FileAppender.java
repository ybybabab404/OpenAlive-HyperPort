package miuix.internal.log.appender;

import android.util.Log;
import miuix.internal.log.Level;
import miuix.internal.log.format.Formatter;
import miuix.internal.log.message.Message;

/* JADX INFO: loaded from: classes2.dex */
public class FileAppender implements Appender {
    public FileManager mFileManager;
    public Formatter mFormatter;

    @Override // miuix.internal.log.appender.Appender
    public void append(String str, long j, String str2, long j2, Level level, String str3, Throwable th) {
        doAppend(str, j, str2, j2, level, str3, th, null);
    }

    @Override // miuix.internal.log.appender.Appender
    public void append(String str, long j, String str2, long j2, Level level, Message message) {
        doAppend(str, j, str2, j2, level, null, null, message);
    }

    public void close() {
        FileManager fileManager = this.mFileManager;
        if (fileManager != null) {
            fileManager.close();
            this.mFileManager = null;
        }
    }

    public final void doAppend(String str, long j, String str2, long j2, Level level, String str3, Throwable th, Message message) {
        Formatter formatter = this.mFormatter;
        if (formatter == null) {
            Log.e("FileAppender", "Fail to append log for formatter is null");
            return;
        }
        FileManager fileManager = this.mFileManager;
        if (fileManager == null) {
            Log.e("FileAppender", "Fail to append log for FileManager is null");
        } else if (str3 == null) {
            fileManager.write(formatter.format(str, j, str2, j2, level, message));
        } else {
            fileManager.write(formatter.format(str, j, str2, j2, level, str3, th));
        }
    }

    public void setFileManager(FileManager fileManager) {
        if (this.mFileManager == fileManager) {
            return;
        }
        close();
        this.mFileManager = fileManager;
    }

    public void setFormatter(Formatter formatter) {
        this.mFormatter = formatter;
    }
}
