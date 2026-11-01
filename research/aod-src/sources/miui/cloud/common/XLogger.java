package miui.cloud.common;

import android.os.Environment;
import android.util.Log;
import java.io.File;

/* JADX INFO: loaded from: classes2.dex */
public class XLogger {
    public static final String DEFAULT_LOGGER_CLASS = "miui.cloud.common.XLogger";
    public static volatile LogSender sLogSender = new LogSender() { // from class: miui.cloud.common.XLogger.1
        public final boolean sLogOnAdb = new File(Environment.getExternalStorageDirectory(), "log_on_adb").exists();

        @Override // miui.cloud.common.XLogger.LogSender
        public void sendLog(int i, String str, String str2) {
            if (this.sLogOnAdb) {
                Log.println(i, str, str2);
            }
        }
    };
    public static volatile int sDefLogLevel = 4;
    public static volatile int sOutputLogLevel = 2;
    public static volatile boolean sEnableCallStacktrace = true;

    public interface LogSender {
        void sendLog(int i, String str, String str2);
    }

    public static String getCallerInfo(String str) {
        boolean z = false;
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            if (stackTraceElement.getClassName().equals(str)) {
                z = true;
            } else if (z) {
                return String.format("%s::%s@%s:%s, thread:%s", stackTraceElement.getClassName(), stackTraceElement.getMethodName(), stackTraceElement.getFileName(), Integer.valueOf(stackTraceElement.getLineNumber()), Long.valueOf(Thread.currentThread().getId()));
            }
        }
        return "";
    }

    public static String getObjectString(Object obj) {
        if (obj == null) {
            return "NULL";
        }
        return obj instanceof Throwable ? getThrowableString((Throwable) obj) : obj.toString();
    }

    public static String getThrowableString(Throwable th) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10 && th != null; i++) {
            if (sb.length() != 0) {
                sb.append(" *Caused by* ");
            }
            sb.append(th.toString());
            th = th.getCause();
        }
        if (th != null) {
            sb.append(" *and more...*");
        }
        return sb.toString();
    }

    public static void log(Object... objArr) {
        logAtLevel(sDefLogLevel, objArr);
    }

    public static void logAtLevel(int i, Object... objArr) {
        logAtLevelImp(i, null, DEFAULT_LOGGER_CLASS, objArr);
    }

    public static void logAtLevelImp(int i, String str, String str2, Object... objArr) {
        LogSender logSender;
        Object objectString;
        if (i >= sOutputLogLevel && (logSender = sLogSender) != null) {
            String str3 = sEnableCallStacktrace ? getCallerInfo(str2) + "--" : "";
            if (objArr.length == 1) {
                objectString = getObjectString(objArr[0]);
            } else {
                StringBuilder sb = new StringBuilder();
                for (Object obj : objArr) {
                    if (sb.length() != 0) {
                        sb.append(", ");
                    }
                    sb.append(getObjectString(obj));
                }
                objectString = sb;
            }
            if (str == null) {
                str = "##XLogger##";
            }
            logSender.sendLog(i, str, str3 + objectString);
        }
    }

    public static void loge(Object... objArr) {
        logAtLevel(6, objArr);
    }

    public static void logi(Object... objArr) {
        logAtLevel(4, objArr);
    }
}
