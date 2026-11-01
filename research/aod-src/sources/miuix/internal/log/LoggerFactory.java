package miuix.internal.log;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import miuix.internal.log.appender.FileAppender;
import miuix.internal.log.appender.LogcatAppender;
import miuix.internal.log.appender.rolling.FileRolloverStrategy;
import miuix.internal.log.appender.rolling.RollingFileManager;
import miuix.internal.log.format.SimpleAospLikeFormatter;
import miuix.internal.log.format.SimpleFormatter;
import miuix.internal.log.util.Config;

/* JADX INFO: loaded from: classes2.dex */
public class LoggerFactory {
    public static final boolean IS_DEBUGGABLE;

    static {
        IS_DEBUGGABLE = LiteSystemProperties.getInt("ro.debuggable", 0) == 1;
    }

    public static Logger getFileLogger(Context context) {
        return getFileLogger(context, Config.getDefaultCacheLogDir(context), Config.LOG_NAME);
    }

    public static Logger getFileLogger(Context context, String str, String str2) {
        Bundle bundle;
        Logger logger = new Logger(str2);
        FileAppender fileAppender = new FileAppender();
        if (Config.isUseAospLikeLogFormat()) {
            fileAppender.setFormatter(new SimpleAospLikeFormatter());
        } else {
            fileAppender.setFormatter(new SimpleFormatter());
        }
        FileRolloverStrategy fileRolloverStrategy = new FileRolloverStrategy();
        try {
            bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("LoggerFactory", e.getMessage());
            bundle = null;
        }
        fileRolloverStrategy.setMaxBackupIndex(obtainMaxBackup(bundle) - 1);
        fileRolloverStrategy.setMaxFileSize(obtainMaxFileSize(bundle));
        RollingFileManager rollingFileManager = new RollingFileManager(str, str2);
        rollingFileManager.setRolloverStrategy(fileRolloverStrategy);
        fileAppender.setFileManager(rollingFileManager);
        logger.addAppender(fileAppender);
        if (IS_DEBUGGABLE) {
            logger.setLevel(Level.VERBOSE);
        } else {
            logger.setLevel(Level.INFO);
        }
        return logger;
    }

    public static Logger getLogcatLogger() {
        Logger logger = new Logger(Config.LOG_NAME);
        logger.addAppender(new LogcatAppender());
        if (IS_DEBUGGABLE) {
            logger.setLevel(Level.VERBOSE);
            return logger;
        }
        logger.setLevel(Level.INFO);
        return logger;
    }

    public static int obtainMaxBackup(Bundle bundle) {
        if (bundle != null && bundle.containsKey("maxBackup")) {
            Object obj = bundle.get("maxBackup");
            if (obj instanceof Integer) {
                Integer num = (Integer) obj;
                if (num.intValue() < 20) {
                    return num.intValue();
                }
            }
            Log.e("LoggerFactory", "Log config error:maxBackup must be int type and smaller than 20");
        }
        return 4;
    }

    public static int obtainMaxFileSize(Bundle bundle) {
        if (bundle != null && bundle.containsKey("maxFileMbSize")) {
            Object obj = bundle.get("maxFileMbSize");
            if (obj instanceof Integer) {
                Integer num = (Integer) obj;
                if (num.intValue() <= 10) {
                    return num.intValue() * 1048576;
                }
            }
            Log.e("LoggerFactory", "Log config error:maxFileMbSize must be int type and smaller than 10");
        }
        return 1048576;
    }
}
