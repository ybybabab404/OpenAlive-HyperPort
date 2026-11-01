package miuix.internal.log;

import java.util.concurrent.CopyOnWriteArrayList;
import miuix.internal.log.appender.Appender;
import miuix.internal.log.message.Message;

/* JADX INFO: loaded from: classes2.dex */
public class Logger {
    public String mName;
    public Level mLevel = Level.VERBOSE;
    public CopyOnWriteArrayList mAppenders = new CopyOnWriteArrayList();

    public Logger(String str) {
        this.mName = str;
    }

    public void addAppender(Appender appender) {
        if (appender == null) {
            throw new IllegalArgumentException("Appender not allowed to be null");
        }
        this.mAppenders.addIfAbsent(appender);
    }

    public final void doLog(Level level, long j, String str, String str2, Throwable th, Message message) {
        if (level.compareTo(this.mLevel) >= 0) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            for (Appender appender : this.mAppenders) {
                if (str2 == null) {
                    appender.append(this.mName, j, str, jCurrentTimeMillis, level, message);
                } else {
                    appender.append(this.mName, j, str, jCurrentTimeMillis, level, str2, th);
                }
            }
        }
    }

    public void log(Level level, long j, String str, String str2, Throwable th) {
        doLog(level, j, str, str2, th, null);
    }

    public void log(Level level, long j, String str, Message message) {
        doLog(level, j, str, null, null, message);
    }

    public void setLevel(Level level) {
        this.mLevel = level;
    }
}
