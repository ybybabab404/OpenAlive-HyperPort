package miui.cloud.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.FileObserver;
import android.os.SystemClock;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import androidx.exifinterface.media.ExifInterface;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import miui.cloud.os.MultiuserUtils;

/* JADX INFO: loaded from: classes2.dex */
public class SwitchFileLogSender implements XLogger.LogSender {
    public final Context mAppContext;
    public long mFailOpenTime;
    public boolean mInit;
    public int mMaxFileCount;
    public int mMaxFileSizeInByte;
    public FileObserver mMonitor;
    public FileOutputStream mOutputStream;
    public XLogger.LogSender mParentLogSender;
    public PathProvider mPathProvider;
    public boolean mShutdown;
    public long mSizeUsed;

    /* JADX INFO: renamed from: miui.cloud.common.SwitchFileLogSender$1, reason: invalid class name */
    class AnonymousClass1 implements PathProvider {
        public final /* synthetic */ String val$packageName;

        @Override // miui.cloud.common.SwitchFileLogSender.PathProvider
        public File getPath(Context context) {
            return new File(Environment.getExternalStorageDirectory(), String.format("MIUI/debug_log/%s", this.val$packageName));
        }
    }

    public interface PathProvider {
        File getPath(Context context);
    }

    public final void closeLogFileLocked() {
        this.mMonitor.stopWatching();
        this.mMonitor = null;
        IOUtil.closeQuietly(this.mOutputStream);
        this.mOutputStream = null;
        this.mSizeUsed = 0L;
        this.mFailOpenTime = 0L;
    }

    public final void init() {
        if (this.mInit) {
            return;
        }
        this.mInit = true;
        registerShutdownListener(this.mAppContext);
    }

    public final synchronized void onBaseDirChanged() {
        FileDescriptor fd;
        int i;
        FileOutputStream fileOutputStream = this.mOutputStream;
        if (fileOutputStream == null) {
            return;
        }
        StructStat structStatFstat = null;
        try {
            fd = fileOutputStream.getFD();
        } catch (IOException unused) {
            fd = null;
        }
        if (fd == null) {
            return;
        }
        boolean z = false;
        try {
            structStatFstat = Os.fstat(fd);
            i = 0;
        } catch (ErrnoException e) {
            i = e.errno;
        }
        boolean z2 = true;
        if (structStatFstat != null && structStatFstat.st_nlink <= 0) {
            z = true;
        }
        if (i != OsConstants.ENOENT) {
            z2 = z;
        }
        if (z2) {
            XLogger.LogSender logSender = this.mParentLogSender;
            if (logSender != null) {
                logSender.sendLog(5, getClass().getName(), "File unlinked. ");
            }
            closeLogFileLocked();
        }
    }

    public boolean onFilterLogByLevelLocked(int i) {
        return true;
    }

    public String onGetLevelTagLocked(int i) {
        switch (i) {
            case 2:
                return ExifInterface.GPS_MEASUREMENT_INTERRUPTED;
            case 3:
                return "D";
            case 4:
                return "I";
            case 5:
                return ExifInterface.LONGITUDE_WEST;
            case 6:
                return ExifInterface.LONGITUDE_EAST;
            case 7:
                return ExifInterface.GPS_MEASUREMENT_IN_PROGRESS;
            default:
                return String.valueOf(i);
        }
    }

    public final synchronized void onShutDown() {
        sendLogLocked(6, getClass().getName(), "========================== shut down ========================== ");
        this.mShutdown = true;
        if (this.mOutputStream != null) {
            closeLogFileLocked();
        }
    }

    public final void prepareLogFileLocked() {
        File file;
        if (this.mOutputStream != null) {
            return;
        }
        if (this.mFailOpenTime > 0 && Math.abs(SystemClock.elapsedRealtime() - this.mFailOpenTime) < 180000) {
            XLogger.LogSender logSender = this.mParentLogSender;
            if (logSender != null) {
                logSender.sendLog(6, getClass().getName(), "Failed to open log file recently. Abort. ");
                return;
            }
            return;
        }
        try {
            File path = this.mPathProvider.getPath(this.mAppContext);
            if (path == null) {
                XLogger.LogSender logSender2 = this.mParentLogSender;
                if (logSender2 != null) {
                    logSender2.sendLog(6, getClass().getName(), "Failed to get base log path. Abort. ");
                }
                if (this.mOutputStream == null) {
                    this.mFailOpenTime = SystemClock.elapsedRealtime();
                    return;
                } else {
                    this.mFailOpenTime = 0L;
                    return;
                }
            }
            File file2 = new File(path, "" + MultiuserUtils.myUserId());
            if (!file2.isDirectory()) {
                file2.mkdirs();
            }
            if (!file2.isDirectory()) {
                XLogger.LogSender logSender3 = this.mParentLogSender;
                if (logSender3 != null) {
                    logSender3.sendLog(6, getClass().getName(), String.format("Failed to create folder %s. ", file2.getAbsolutePath()));
                }
                if (this.mOutputStream == null) {
                    this.mFailOpenTime = SystemClock.elapsedRealtime();
                    return;
                } else {
                    this.mFailOpenTime = 0L;
                    return;
                }
            }
            String externalStorageState = Environment.getExternalStorageState(file2);
            if (!"unknown".equals(externalStorageState) && !"mounted".equals(externalStorageState)) {
                XLogger.LogSender logSender4 = this.mParentLogSender;
                if (logSender4 != null) {
                    logSender4.sendLog(6, getClass().getName(), "Storage not mounted. ");
                }
                if (this.mOutputStream == null) {
                    this.mFailOpenTime = SystemClock.elapsedRealtime();
                    return;
                } else {
                    this.mFailOpenTime = 0L;
                    return;
                }
            }
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= this.mMaxFileCount) {
                    file = null;
                    break;
                }
                file = new File(file2, i + ".log");
                if (!file.exists() || file.length() < this.mMaxFileSizeInByte) {
                    break;
                    break;
                }
                i++;
            }
            if (file == null) {
                long jLastModified = new File(file2, "0.log").lastModified();
                long j = 0;
                for (int i2 = 1; i2 < this.mMaxFileCount; i2++) {
                    long jLastModified2 = new File(file2, i2 + ".log").lastModified();
                    if (jLastModified2 < jLastModified) {
                        j = i2;
                        jLastModified = jLastModified2;
                    }
                }
                file = new File(file2, j + ".log");
            } else {
                z = true;
            }
            try {
                this.mOutputStream = new FileOutputStream(file, z);
                this.mSizeUsed = z ? file.length() : 0L;
                FileObserver fileObserver = new FileObserver(file2.getPath(), 1536) { // from class: miui.cloud.common.SwitchFileLogSender.2
                    @Override // android.os.FileObserver
                    public void onEvent(int i3, String str) {
                        SwitchFileLogSender.this.onBaseDirChanged();
                    }
                };
                this.mMonitor = fileObserver;
                fileObserver.startWatching();
                if (this.mOutputStream == null) {
                    this.mFailOpenTime = SystemClock.elapsedRealtime();
                } else {
                    this.mFailOpenTime = 0L;
                }
            } catch (FileNotFoundException e) {
                XLogger.LogSender logSender5 = this.mParentLogSender;
                if (logSender5 != null) {
                    logSender5.sendLog(6, getClass().getName(), String.format("Failed to switch to file %s, error: %s. ", file.getAbsolutePath(), e));
                }
                if (this.mOutputStream == null) {
                    this.mFailOpenTime = SystemClock.elapsedRealtime();
                } else {
                    this.mFailOpenTime = 0L;
                }
            }
        } catch (Throwable th) {
            if (this.mOutputStream == null) {
                this.mFailOpenTime = SystemClock.elapsedRealtime();
            } else {
                this.mFailOpenTime = 0L;
            }
            throw th;
        }
    }

    public final void registerShutdownListener(Context context) {
        context.registerReceiver(new BroadcastReceiver() { // from class: miui.cloud.common.SwitchFileLogSender.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                SwitchFileLogSender.this.onShutDown();
            }
        }, new IntentFilter("android.intent.action.ACTION_SHUTDOWN"), null, null);
    }

    @Override // miui.cloud.common.XLogger.LogSender
    public synchronized void sendLog(int i, String str, String str2) {
        sendLogLocked(i, str, str2);
    }

    public final void sendLogLocked(int i, String str, String str2) {
        init();
        XLogger.LogSender logSender = this.mParentLogSender;
        if (logSender != null) {
            logSender.sendLog(i, str, str2);
        }
        if (onFilterLogByLevelLocked(i)) {
            if (this.mShutdown) {
                XLogger.LogSender logSender2 = this.mParentLogSender;
                if (logSender2 != null) {
                    logSender2.sendLog(6, getClass().getName(), "Shutdown state. Skip outputing. ");
                    return;
                }
                return;
            }
            prepareLogFileLocked();
            if (this.mOutputStream == null) {
                XLogger.LogSender logSender3 = this.mParentLogSender;
                if (logSender3 != null) {
                    logSender3.sendLog(6, getClass().getName(), "Null output stream. Skip outputing. ");
                    return;
                }
                return;
            }
            byte[] bytes = String.format("LV:%s, TM: %s, TAG: %s, MSG: %s\n", onGetLevelTagLocked(i), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), str, str2).getBytes();
            this.mSizeUsed += (long) bytes.length;
            try {
                this.mOutputStream.write(bytes);
                this.mOutputStream.flush();
            } catch (IOException e) {
                XLogger.LogSender logSender4 = this.mParentLogSender;
                if (logSender4 != null) {
                    logSender4.sendLog(6, getClass().getName(), String.format("Failed to output log, IOException: %s", e));
                }
            }
            if (this.mSizeUsed >= this.mMaxFileSizeInByte) {
                closeLogFileLocked();
            }
        }
    }
}
