package miuix.internal.log.appender;

import android.os.SystemClock;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/* JADX INFO: loaded from: classes2.dex */
public class FileManager {
    public long mFileCheckStamp;
    public File mLogFile;
    public long mLogLength;
    public String mLogName;
    public String mLogRoot;
    public FileOutputStream mOutputStream;
    public long mPrepareRetryStamp;
    public int mRetryCount;
    public OutputStreamWriter mWriter;

    public FileManager(String str, String str2) {
        this.mLogRoot = str;
        this.mLogName = str2;
    }

    public static void closeQuietly(Writer writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException unused) {
            }
        }
    }

    public synchronized void close() {
        this.mLogFile = null;
        this.mRetryCount = 0;
        this.mOutputStream = null;
        closeQuietly(this.mWriter);
        this.mWriter = null;
        this.mLogLength = 0L;
    }

    public final void doWrite(String str) throws IOException {
        this.mWriter.write(str);
        this.mWriter.flush();
        this.mLogLength += (long) str.length();
    }

    public File getLogFile() {
        return this.mLogFile;
    }

    public long getLogSize() {
        return this.mLogLength;
    }

    public String onBuildLogPath() {
        return this.mLogRoot + "/" + this.mLogName + ".log";
    }

    public File onCreateLogFile() {
        String strOnBuildLogPath = onBuildLogPath();
        if (strOnBuildLogPath == null) {
            Log.e("FileManager", "Fail to build log path");
            return null;
        }
        File file = new File(strOnBuildLogPath);
        File parentFile = file.getParentFile();
        if (parentFile != null) {
            if (parentFile.exists()) {
                if (!parentFile.isDirectory()) {
                    Log.e("FileManager", "LogDir is not a directory: " + parentFile.getPath());
                    return null;
                }
            } else if (!parentFile.mkdirs() && !parentFile.exists()) {
                Log.e("FileManager", "Fail to create directory: " + parentFile.getPath());
                return null;
            }
        }
        if (!file.exists()) {
            try {
                if (!file.createNewFile() && !file.exists()) {
                    Log.e("FileManager", "Fail to create LogFile: " + strOnBuildLogPath);
                    return null;
                }
            } catch (IOException e) {
                Log.e("FileManager", "Fail to create LogFile: " + strOnBuildLogPath, e);
                return null;
            }
        } else if (!file.isFile()) {
            Log.e("FileManager", "LogFile is not a file: " + strOnBuildLogPath);
            return null;
        }
        return file;
    }

    public final void prepareWriter() {
        if (this.mRetryCount >= 10) {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            if (jElapsedRealtime - this.mPrepareRetryStamp <= 30000) {
                return;
            } else {
                this.mPrepareRetryStamp = jElapsedRealtime;
            }
        }
        this.mRetryCount++;
        File fileOnCreateLogFile = onCreateLogFile();
        this.mLogFile = fileOnCreateLogFile;
        if (fileOnCreateLogFile != null) {
            try {
                this.mOutputStream = new FileOutputStream(this.mLogFile, true);
                this.mWriter = new OutputStreamWriter(this.mOutputStream);
                this.mRetryCount = 0;
                this.mLogLength = this.mLogFile.length();
            } catch (FileNotFoundException e) {
                Log.e("FileManager", "Fail to create writer: " + this.mLogFile.getPath(), e);
            }
        }
    }

    public final void repairWriter() {
        close();
        prepareWriter();
    }

    public synchronized void write(String str) {
        try {
            OutputStreamWriter outputStreamWriter = this.mWriter;
            if (outputStreamWriter == null) {
                prepareWriter();
            } else if (outputStreamWriter != null) {
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                if (jElapsedRealtime - this.mFileCheckStamp > 1000) {
                    this.mFileCheckStamp = jElapsedRealtime;
                    if (!this.mLogFile.exists()) {
                        Log.d("FileManager", "Repair writer for log file is missing");
                        repairWriter();
                    }
                }
            }
            if (this.mWriter == null) {
                Log.e("FileManager", "Fail to append log for writer is null");
            } else {
                try {
                    doWrite(str);
                } catch (IOException e) {
                    Log.w("FileManager", "Retry to write log", e);
                    repairWriter();
                    if (this.mWriter == null) {
                        Log.e("FileManager", "Fail to append log for writer is null when retry");
                    } else {
                        try {
                            doWrite(str);
                        } catch (IOException e2) {
                            Log.e("FileManager", "Fail to append log for writer is null when retry", e2);
                        }
                    }
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
