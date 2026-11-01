package miuix.internal.log.appender;

import android.util.Log;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import miuix.internal.log.Level;
import miuix.internal.log.message.Message;

/* JADX INFO: loaded from: classes2.dex */
public class LogcatAppender implements Appender {
    public ThreadLocal mThreadCache = new ThreadLocal<StringBuilder>() { // from class: miuix.internal.log.appender.LogcatAppender.1
        @Override // java.lang.ThreadLocal
        public StringBuilder initialValue() {
            return new StringBuilder();
        }
    };

    /* JADX INFO: renamed from: miuix.internal.log.appender.LogcatAppender$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$miuix$internal$log$Level;

        static {
            int[] iArr = new int[Level.values().length];
            $SwitchMap$miuix$internal$log$Level = iArr;
            try {
                iArr[Level.VERBOSE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$miuix$internal$log$Level[Level.DEBUG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$miuix$internal$log$Level[Level.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$miuix$internal$log$Level[Level.WARNING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$miuix$internal$log$Level[Level.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$miuix$internal$log$Level[Level.FATAL.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    @Override // miuix.internal.log.appender.Appender
    public void append(String str, long j, String str2, long j2, Level level, String str3, Throwable th) {
        switch (AnonymousClass2.$SwitchMap$miuix$internal$log$Level[level.ordinal()]) {
            case 1:
                if (th != null) {
                    Log.v(str2, str3, th);
                } else {
                    Log.v(str2, str3);
                }
                break;
            case 2:
                if (th != null) {
                    Log.d(str2, str3, th);
                } else {
                    Log.d(str2, str3);
                }
                break;
            case 3:
                if (th != null) {
                    Log.i(str2, str3, th);
                } else {
                    Log.i(str2, str3);
                }
                break;
            case 4:
                if (th != null) {
                    Log.w(str2, str3, th);
                } else {
                    Log.w(str2, str3);
                }
                break;
            case 5:
                if (th != null) {
                    Log.e(str2, str3, th);
                } else {
                    Log.e(str2, str3);
                }
                break;
            case 6:
                if (th != null) {
                    Log.wtf(str2, str3, th);
                } else {
                    Log.wtf(str2, str3);
                }
                break;
        }
    }

    @Override // miuix.internal.log.appender.Appender
    public void append(String str, long j, String str2, long j2, Level level, Message message) {
        StringBuilder sb = (StringBuilder) this.mThreadCache.get();
        if (sb != null) {
            sb.setLength(0);
        }
        if (message != null) {
            message.format(sb);
        }
        append(str, j, str2, j2, level, sb != null ? sb.toString() : null, message != null ? message.getThrowable() : null);
        if (sb == null || sb.length() <= 8192) {
            return;
        }
        sb.setLength(AutoBottomSheetKt.ALIGN_SCREEN_START);
        sb.trimToSize();
    }
}
