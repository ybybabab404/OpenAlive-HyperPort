package miuix.util;

import android.content.Context;
import android.os.Process;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import miuix.internal.log.Level;
import miuix.internal.log.Logger;
import miuix.internal.log.LoggerFactory;
import miuix.internal.log.message.AbstractMessage;
import miuix.internal.log.message.Message;
import miuix.internal.log.message.MessageFactory;

/* JADX INFO: loaded from: classes3.dex */
public class Log {

    public static class AsyncCustomFileLoggerInstance {
    }

    public static class AsyncFileLoggerInstance {
        public static volatile Facade facade;

        public static Facade getInstance(Context context) {
            if (facade == null) {
                synchronized (AsyncFileLoggerInstance.class) {
                    try {
                        if (facade == null) {
                            facade = new Facade(LoggerFactory.getFileLogger(context), true);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            return facade;
        }
    }

    public static class CustomFileLoggerInstance {
    }

    public static class ExternalFileLoggerInstance {
        public static Facade facade;

        public static Facade instance() {
            return facade;
        }
    }

    public static class ExternalFullFacade extends Facade {
        /* JADX WARN: Multi-variable type inference failed */
        public ExternalFullFacade() {
            super((Logger) null);
        }

        @Override // miuix.util.Log.Facade
        public void doLog(Level level, String str, String str2, Throwable th, Message message) {
            LogcatLoggerInstance.INSTANCE.doLog(level, str, str2, th, message);
            ExternalFileLoggerInstance.instance().doLog(level, str, str2, th, message);
        }
    }

    public static class ExternalFullLoggerInstance {
        public static final Facade INSTANCE = new ExternalFullFacade();
    }

    public static class Facade {
        public Logger mLogger;
        public boolean mPrintAsync;
        public ExecutorService mSingleThreadExecutor;

        public static class PrintLogRunnable extends AbstractMessage implements Runnable {
            public Level mLevel;
            public Logger mLogger;
            public Message mMessage;
            public String mTag;
            public String mText;
            public Throwable mThrowable;
            public long mTid;

            public static PrintLogRunnable obtain() {
                return (PrintLogRunnable) MessageFactory.obtain(PrintLogRunnable.class);
            }

            @Override // miuix.internal.log.message.Message
            public void format(Appendable appendable) {
            }

            @Override // miuix.internal.log.message.Message
            public Throwable getThrowable() {
                return null;
            }

            @Override // miuix.internal.log.message.AbstractMessage
            public void onRecycle() {
                this.mLogger = null;
                this.mLevel = null;
                this.mTag = null;
                this.mText = null;
                this.mThrowable = null;
                Message message = this.mMessage;
                if (message != null) {
                    message.recycle();
                    this.mMessage = null;
                }
            }

            @Override // java.lang.Runnable
            public void run() {
                Logger logger = this.mLogger;
                if (logger == null) {
                    android.util.Log.e("LogcatFacade", "mLogger is null");
                } else {
                    Message message = this.mMessage;
                    if (message == null) {
                        logger.log(this.mLevel, this.mTid, this.mTag, this.mText, this.mThrowable);
                    } else {
                        logger.log(this.mLevel, this.mTid, this.mTag, message);
                    }
                }
                recycle();
            }

            public void setLogInfo(Logger logger, Level level, String str, String str2, Throwable th, Message message) {
                this.mLogger = logger;
                this.mLevel = level;
                this.mTag = str;
                this.mText = str2;
                this.mThrowable = th;
                this.mMessage = message;
                this.mTid = Process.myTid();
            }
        }

        public Facade(Logger logger) {
            this(logger, false);
        }

        public Facade(Logger logger, boolean z) {
            this.mLogger = logger;
            this.mPrintAsync = z;
            if (z) {
                this.mSingleThreadExecutor = Executors.newSingleThreadExecutor();
            }
        }

        public void debug(String str, String str2) {
            log(Level.DEBUG, str, str2, null);
        }

        public void doLog(Level level, String str, String str2, Throwable th, Message message) {
            if (!this.mPrintAsync) {
                printLog(level, str, str2, th, message);
                return;
            }
            PrintLogRunnable printLogRunnableObtain = PrintLogRunnable.obtain();
            printLogRunnableObtain.setLogInfo(this.mLogger, level, str, str2, th, message);
            this.mSingleThreadExecutor.execute(printLogRunnableObtain);
        }

        public void error(String str, String str2) {
            log(Level.ERROR, str, str2, null);
        }

        public void info(String str, String str2) {
            log(Level.INFO, str, str2, null);
        }

        public final void log(Level level, String str, String str2, Throwable th) {
            doLog(level, str, str2, th, null);
        }

        public final void printLog(Level level, String str, String str2, Throwable th, Message message) {
            if (this.mLogger == null) {
                android.util.Log.e("LogcatFacade", "mLogger is null");
                return;
            }
            long jMyTid = Process.myTid();
            if (message == null) {
                this.mLogger.log(level, jMyTid, str, str2, th);
            } else {
                this.mLogger.log(level, jMyTid, str, message);
                message.recycle();
            }
        }

        public void verbose(String str, String str2) {
            log(Level.VERBOSE, str, str2, null);
        }

        public void warn(String str, String str2) {
            log(Level.WARNING, str, str2, null);
        }
    }

    public static class FileLoggerInstance {
        public static Facade facade;

        public static Facade instance() {
            return facade;
        }
    }

    public static class FullFacade extends Facade {
        /* JADX WARN: Multi-variable type inference failed */
        public FullFacade() {
            super((Logger) null);
        }

        @Override // miuix.util.Log.Facade
        public void doLog(Level level, String str, String str2, Throwable th, Message message) {
            LogcatLoggerInstance.INSTANCE.doLog(level, str, str2, th, message);
            FileLoggerInstance.instance().doLog(level, str, str2, th, message);
        }
    }

    public static class FullLoggerInstance {
        public static final Facade INSTANCE = new FullFacade();
    }

    public static class LogcatLoggerInstance {
        public static final Facade INSTANCE = new Facade(LoggerFactory.getLogcatLogger());
    }

    public static Facade getAsyncFileLogger(Context context) {
        return AsyncFileLoggerInstance.getInstance(context);
    }

    public static Facade getLogcatLogger() {
        return LogcatLoggerInstance.INSTANCE;
    }
}
