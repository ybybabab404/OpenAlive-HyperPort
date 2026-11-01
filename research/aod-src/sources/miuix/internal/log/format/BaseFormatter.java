package miuix.internal.log.format;

import androidx.exifinterface.media.ExifInterface;
import java.io.Writer;
import miuix.internal.log.Level;

/* JADX INFO: loaded from: classes2.dex */
public abstract class BaseFormatter implements Formatter {

    /* JADX INFO: renamed from: miuix.internal.log.format.BaseFormatter$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
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

    public static class StringBuilderWriter extends Writer {
        public StringBuilder iOut;

        public StringBuilderWriter(StringBuilder sb) {
            this.iOut = sb;
        }

        public static void checkOffsetAndCount(int i, int i2, int i3) {
            if ((i2 | i3) < 0 || i2 > i || i - i2 < i3) {
                throw new ArrayIndexOutOfBoundsException("length=" + i + "; regionStart=" + i2 + "; regionLength=" + i3);
            }
        }

        @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
        }

        @Override // java.io.Writer, java.io.Flushable
        public void flush() {
        }

        @Override // java.io.Writer
        public void write(char[] cArr, int i, int i2) {
            checkOffsetAndCount(cArr.length, i, i2);
            if (i2 == 0) {
                return;
            }
            this.iOut.append(cArr, i, i2);
        }
    }

    public static StringBuilder appendInt(StringBuilder sb, int i, int i2) {
        if (i2 == 2) {
            if (i < 10) {
                sb.append('0');
            }
            sb.append(i);
            return sb;
        }
        if (i2 != 3) {
            sb.append(i);
            return sb;
        }
        if (i < 10) {
            sb.append("00");
        } else if (i < 100) {
            sb.append('0');
        }
        sb.append(i);
        return sb;
    }

    public static String levelToName(Level level) {
        switch (AnonymousClass1.$SwitchMap$miuix$internal$log$Level[level.ordinal()]) {
            case 1:
                return ExifInterface.GPS_MEASUREMENT_INTERRUPTED;
            case 2:
                return "D";
            case 3:
                return "I";
            case 4:
                return ExifInterface.LONGITUDE_WEST;
            case 5:
                return ExifInterface.LONGITUDE_EAST;
            case 6:
                return "F";
            default:
                return "UN";
        }
    }

    public StringBuilder appendSpace(StringBuilder sb, long j) {
        if (j < 10) {
            sb.append("    ");
        } else if (j < 100) {
            sb.append("   ");
        } else if (j < 1000) {
            sb.append("  ");
        } else if (j < 10000) {
            sb.append(' ');
        }
        sb.append(j);
        return sb;
    }
}
