package miuix.internal.log.format;

import android.os.Process;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.io.PrintWriter;
import java.util.Calendar;
import miuix.internal.log.Level;
import miuix.internal.log.message.Message;

/* JADX INFO: loaded from: classes2.dex */
public class SimpleAospLikeFormatter extends BaseFormatter {
    public static final long MY_PID = Process.myPid();
    public ThreadLocal mThreadCache = new ThreadLocal<ThreadCache>() { // from class: miuix.internal.log.format.SimpleAospLikeFormatter.1
        @Override // java.lang.ThreadLocal
        public ThreadCache initialValue() {
            return new ThreadCache();
        }
    };

    public static class CachedDateFormat {
        public StringBuilder iCache;
        public long iCacheTime;
        public Calendar iCalendar;
        public long iMillisecondsSlot;
        public long iSecondsSlot;

        public CachedDateFormat() {
            this.iCache = new StringBuilder();
            this.iCalendar = Calendar.getInstance();
        }

        public final void buildCache(long j) {
            this.iCache.setLength(0);
            this.iCalendar.setTimeInMillis(j);
            StringBuilder sb = this.iCache;
            sb.append(this.iCalendar.get(1));
            sb.append('-');
            BaseFormatter.appendInt(this.iCache, this.iCalendar.get(2) + 1, 2).append('-');
            BaseFormatter.appendInt(this.iCache, this.iCalendar.get(5), 2).append(' ');
            BaseFormatter.appendInt(this.iCache, this.iCalendar.get(11), 2).append(':');
            BaseFormatter.appendInt(this.iCache, this.iCalendar.get(12), 2).append(':');
            BaseFormatter.appendInt(this.iCache, this.iCalendar.get(13), 2).append('.');
            BaseFormatter.appendInt(this.iCache, this.iCalendar.get(14), 3);
        }

        public void format(StringBuilder sb, long j) {
            if (j != this.iCacheTime) {
                long j2 = this.iSecondsSlot;
                if (j2 == 0 || j < j2 || j >= j2 + 60000) {
                    buildCache(j);
                    this.iSecondsSlot = (j / 60000) * 60000;
                    this.iMillisecondsSlot = (j / 1000) * 1000;
                } else {
                    long j3 = this.iMillisecondsSlot;
                    if (j < j3 || j >= 1000 + j3) {
                        int i = (int) (j - j2);
                        int i2 = i / 1000;
                        int i3 = i2 * 1000;
                        StringBuilder sb2 = this.iCache;
                        sb2.setLength(sb2.length() - 6);
                        BaseFormatter.appendInt(this.iCache, i2, 2).append('.');
                        BaseFormatter.appendInt(this.iCache, i - i3, 3);
                        this.iMillisecondsSlot = this.iSecondsSlot + ((long) i3);
                    } else {
                        StringBuilder sb3 = this.iCache;
                        sb3.setLength(sb3.length() - 3);
                        BaseFormatter.appendInt(this.iCache, (int) (j - j3), 3);
                    }
                }
                this.iCacheTime = j;
            }
            sb.append((CharSequence) this.iCache);
        }
    }

    public static class ThreadCache {
        public CachedDateFormat dateFormat = new CachedDateFormat();
        public StringBuilder out = new StringBuilder();
        public PrintWriter printer = new PrintWriter(new BaseFormatter.StringBuilderWriter(this.out));
    }

    public final String doFormat(String str, long j, String str2, long j2, Level level, String str3, Message message, Throwable th) {
        ThreadCache threadCache = (ThreadCache) this.mThreadCache.get();
        StringBuilder sb = threadCache.out;
        sb.setLength(0);
        threadCache.dateFormat.format(sb, j2);
        sb.append(' ');
        appendSpace(sb, MY_PID);
        sb.append(' ');
        appendSpace(sb, j);
        sb.append(' ');
        sb.append(BaseFormatter.levelToName(level));
        sb.append(' ');
        sb.append(str2);
        sb.append(": ");
        if (str3 != null || message == null) {
            sb.append(str3);
        } else {
            message.format(sb);
        }
        sb.append('\n');
        if (th != null) {
            th.printStackTrace(threadCache.printer);
        }
        if (sb.length() > 8192) {
            sb.setLength(AutoBottomSheetKt.ALIGN_SCREEN_START);
            sb.trimToSize();
        }
        return sb.toString();
    }

    @Override // miuix.internal.log.format.Formatter
    public String format(String str, long j, String str2, long j2, Level level, String str3, Throwable th) {
        return doFormat(str, j, str2, j2, level, str3, null, th);
    }

    @Override // miuix.internal.log.format.Formatter
    public String format(String str, long j, String str2, long j2, Level level, Message message) {
        return doFormat(str, j, str2, j2, level, null, message, message != null ? message.getThrowable() : null);
    }
}
