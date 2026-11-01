package miuix.internal.log.format;

import miuix.internal.log.Level;
import miuix.internal.log.message.Message;

/* JADX INFO: loaded from: classes2.dex */
public interface Formatter {
    String format(String str, long j, String str2, long j2, Level level, String str3, Throwable th);

    String format(String str, long j, String str2, long j2, Level level, Message message);
}
