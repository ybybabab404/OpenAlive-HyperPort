package kotlin.time;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import miuix.animation.controller.AnimState;
import miuix.animation.utils.CommonUtils;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: Duration.kt */
/* JADX INFO: loaded from: classes2.dex */
@SinceKotlin
@Metadata
@JvmInline
@WasExperimental
@SourceDebugExtension
public final class Duration implements Comparable<Duration> {
    public final long rawValue;
    public static final Companion Companion = new Companion(null);
    public static final long ZERO = m1183constructorimpl(0);
    public static final long INFINITE = DurationKt.durationOfMillis(4611686018427387903L);
    public static final long NEG_INFINITE = DurationKt.durationOfMillis(-4611686018427387903L);

    /* JADX INFO: compiled from: Duration.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getINFINITE-UwyO8pc, reason: not valid java name */
        public final long m1212getINFINITEUwyO8pc() {
            return Duration.INFINITE;
        }

        /* JADX INFO: renamed from: getNEG_INFINITE-UwyO8pc$kotlin_stdlib, reason: not valid java name */
        public final long m1213getNEG_INFINITEUwyO8pc$kotlin_stdlib() {
            return Duration.NEG_INFINITE;
        }

        /* JADX INFO: renamed from: getZERO-UwyO8pc, reason: not valid java name */
        public final long m1214getZEROUwyO8pc() {
            return Duration.ZERO;
        }
    }

    /* JADX INFO: renamed from: addValuesMixedRanges-UwyO8pc, reason: not valid java name */
    public static final long m1180addValuesMixedRangesUwyO8pc(long j, long j2, long j3) {
        long jNanosToMillis = DurationKt.nanosToMillis(j3);
        long j4 = j2 + jNanosToMillis;
        if (-4611686018426L > j4 || j4 >= 4611686018427L) {
            return DurationKt.durationOfMillis(RangesKt.coerceIn(j4, -4611686018427387903L, 4611686018427387903L));
        }
        return DurationKt.durationOfNanos(DurationKt.millisToNanos(j4) + (j3 - DurationKt.millisToNanos(jNanosToMillis)));
    }

    /* JADX INFO: renamed from: appendFractional-impl, reason: not valid java name */
    public static final void m1181appendFractionalimpl(long j, StringBuilder sb, int i, int i2, int i3, String str, boolean z) {
        sb.append(i);
        if (i2 != 0) {
            sb.append('.');
            String strPadStart = StringsKt.padStart(String.valueOf(i2), i3, '0');
            int i4 = -1;
            int length = strPadStart.length() - 1;
            if (length >= 0) {
                while (true) {
                    int i5 = length - 1;
                    if (strPadStart.charAt(length) != '0') {
                        i4 = length;
                        break;
                    } else if (i5 < 0) {
                        break;
                    } else {
                        length = i5;
                    }
                }
            }
            int i6 = i4 + 1;
            if (z || i6 >= 3) {
                sb.append((CharSequence) strPadStart, 0, ((i4 + 3) / 3) * 3);
                Intrinsics.checkNotNullExpressionValue(sb, "append(...)");
            } else {
                sb.append((CharSequence) strPadStart, 0, i6);
                Intrinsics.checkNotNullExpressionValue(sb, "append(...)");
            }
        }
        sb.append(str);
    }

    /* JADX INFO: renamed from: compareTo-LRDsOJo, reason: not valid java name */
    public static int m1182compareToLRDsOJo(long j, long j2) {
        long j3 = j ^ j2;
        if (j3 < 0 || (((int) j3) & 1) == 0) {
            return Intrinsics.compare(j, j2);
        }
        int i = (((int) j) & 1) - (((int) j2) & 1);
        return m1203isNegativeimpl(j) ? -i : i;
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1183constructorimpl(long j) {
        if (!DurationJvmKt.getDurationAssertionsEnabled()) {
            return j;
        }
        if (m1201isInNanosimpl(j)) {
            long jM1197getValueimpl = m1197getValueimpl(j);
            if (-4611686018426999999L <= jM1197getValueimpl && jM1197getValueimpl < 4611686018427000000L) {
                return j;
            }
            throw new AssertionError(m1197getValueimpl(j) + " ns is out of nanoseconds range");
        }
        long jM1197getValueimpl2 = m1197getValueimpl(j);
        if (-4611686018427387903L > jM1197getValueimpl2 || jM1197getValueimpl2 >= 4611686018427387904L) {
            throw new AssertionError(m1197getValueimpl(j) + " ms is out of milliseconds range");
        }
        long jM1197getValueimpl3 = m1197getValueimpl(j);
        if (-4611686018426L > jM1197getValueimpl3 || jM1197getValueimpl3 >= 4611686018427L) {
            return j;
        }
        throw new AssertionError(m1197getValueimpl(j) + " ms is denormalized");
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m1184equalsimpl(long j, Object obj) {
        return (obj instanceof Duration) && j == ((Duration) obj).m1211unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m1185equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* JADX INFO: renamed from: getAbsoluteValue-UwyO8pc, reason: not valid java name */
    public static final long m1186getAbsoluteValueUwyO8pc(long j) {
        return m1203isNegativeimpl(j) ? m1209unaryMinusUwyO8pc(j) : j;
    }

    /* JADX INFO: renamed from: getHoursComponent-impl, reason: not valid java name */
    public static final int m1187getHoursComponentimpl(long j) {
        if (m1202isInfiniteimpl(j)) {
            return 0;
        }
        return (int) (m1189getInWholeHoursimpl(j) % ((long) 24));
    }

    /* JADX INFO: renamed from: getInWholeDays-impl, reason: not valid java name */
    public static final long m1188getInWholeDaysimpl(long j) {
        return m1207toLongimpl(j, DurationUnit.DAYS);
    }

    /* JADX INFO: renamed from: getInWholeHours-impl, reason: not valid java name */
    public static final long m1189getInWholeHoursimpl(long j) {
        return m1207toLongimpl(j, DurationUnit.HOURS);
    }

    /* JADX INFO: renamed from: getInWholeMilliseconds-impl, reason: not valid java name */
    public static final long m1190getInWholeMillisecondsimpl(long j) {
        return (m1200isInMillisimpl(j) && m1199isFiniteimpl(j)) ? m1197getValueimpl(j) : m1207toLongimpl(j, DurationUnit.MILLISECONDS);
    }

    /* JADX INFO: renamed from: getInWholeMinutes-impl, reason: not valid java name */
    public static final long m1191getInWholeMinutesimpl(long j) {
        return m1207toLongimpl(j, DurationUnit.MINUTES);
    }

    /* JADX INFO: renamed from: getInWholeSeconds-impl, reason: not valid java name */
    public static final long m1192getInWholeSecondsimpl(long j) {
        return m1207toLongimpl(j, DurationUnit.SECONDS);
    }

    /* JADX INFO: renamed from: getMinutesComponent-impl, reason: not valid java name */
    public static final int m1193getMinutesComponentimpl(long j) {
        if (m1202isInfiniteimpl(j)) {
            return 0;
        }
        return (int) (m1191getInWholeMinutesimpl(j) % ((long) 60));
    }

    /* JADX INFO: renamed from: getNanosecondsComponent-impl, reason: not valid java name */
    public static final int m1194getNanosecondsComponentimpl(long j) {
        if (m1202isInfiniteimpl(j)) {
            return 0;
        }
        return (int) (m1200isInMillisimpl(j) ? DurationKt.millisToNanos(m1197getValueimpl(j) % ((long) 1000)) : m1197getValueimpl(j) % ((long) CommonUtils.UNIT_MILLIS_SECOND));
    }

    /* JADX INFO: renamed from: getSecondsComponent-impl, reason: not valid java name */
    public static final int m1195getSecondsComponentimpl(long j) {
        if (m1202isInfiniteimpl(j)) {
            return 0;
        }
        return (int) (m1192getInWholeSecondsimpl(j) % ((long) 60));
    }

    /* JADX INFO: renamed from: getStorageUnit-impl, reason: not valid java name */
    public static final DurationUnit m1196getStorageUnitimpl(long j) {
        return m1201isInNanosimpl(j) ? DurationUnit.NANOSECONDS : DurationUnit.MILLISECONDS;
    }

    /* JADX INFO: renamed from: getValue-impl, reason: not valid java name */
    public static final long m1197getValueimpl(long j) {
        return j >> 1;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m1198hashCodeimpl(long j) {
        return Long.hashCode(j);
    }

    /* JADX INFO: renamed from: isFinite-impl, reason: not valid java name */
    public static final boolean m1199isFiniteimpl(long j) {
        return !m1202isInfiniteimpl(j);
    }

    /* JADX INFO: renamed from: isInMillis-impl, reason: not valid java name */
    public static final boolean m1200isInMillisimpl(long j) {
        return (((int) j) & 1) == 1;
    }

    /* JADX INFO: renamed from: isInNanos-impl, reason: not valid java name */
    public static final boolean m1201isInNanosimpl(long j) {
        return (((int) j) & 1) == 0;
    }

    /* JADX INFO: renamed from: isInfinite-impl, reason: not valid java name */
    public static final boolean m1202isInfiniteimpl(long j) {
        return j == INFINITE || j == NEG_INFINITE;
    }

    /* JADX INFO: renamed from: isNegative-impl, reason: not valid java name */
    public static final boolean m1203isNegativeimpl(long j) {
        return j < 0;
    }

    /* JADX INFO: renamed from: isPositive-impl, reason: not valid java name */
    public static final boolean m1204isPositiveimpl(long j) {
        return j > 0;
    }

    /* JADX INFO: renamed from: minus-LRDsOJo, reason: not valid java name */
    public static final long m1205minusLRDsOJo(long j, long j2) {
        return m1206plusLRDsOJo(j, m1209unaryMinusUwyO8pc(j2));
    }

    /* JADX INFO: renamed from: plus-LRDsOJo, reason: not valid java name */
    public static final long m1206plusLRDsOJo(long j, long j2) {
        if (m1202isInfiniteimpl(j)) {
            if (m1199isFiniteimpl(j2) || (j2 ^ j) >= 0) {
                return j;
            }
            throw new IllegalArgumentException("Summing infinite durations of different signs yields an undefined result.");
        }
        if (m1202isInfiniteimpl(j2)) {
            return j2;
        }
        if ((((int) j) & 1) != (((int) j2) & 1)) {
            return m1200isInMillisimpl(j) ? m1180addValuesMixedRangesUwyO8pc(j, m1197getValueimpl(j), m1197getValueimpl(j2)) : m1180addValuesMixedRangesUwyO8pc(j, m1197getValueimpl(j2), m1197getValueimpl(j));
        }
        long jM1197getValueimpl = m1197getValueimpl(j) + m1197getValueimpl(j2);
        return m1201isInNanosimpl(j) ? DurationKt.durationOfNanosNormalized(jM1197getValueimpl) : DurationKt.durationOfMillisNormalized(jM1197getValueimpl);
    }

    /* JADX INFO: renamed from: toLong-impl, reason: not valid java name */
    public static final long m1207toLongimpl(long j, DurationUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        if (j == INFINITE) {
            return Long.MAX_VALUE;
        }
        if (j == NEG_INFINITE) {
            return Long.MIN_VALUE;
        }
        return DurationUnitKt__DurationUnitJvmKt.convertDurationUnit(m1197getValueimpl(j), m1196getStorageUnitimpl(j), unit);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m1208toStringimpl(long j) {
        if (j == 0) {
            return "0s";
        }
        if (j == INFINITE) {
            return "Infinity";
        }
        if (j == NEG_INFINITE) {
            return "-Infinity";
        }
        boolean zM1203isNegativeimpl = m1203isNegativeimpl(j);
        StringBuilder sb = new StringBuilder();
        if (zM1203isNegativeimpl) {
            sb.append('-');
        }
        long jM1186getAbsoluteValueUwyO8pc = m1186getAbsoluteValueUwyO8pc(j);
        long jM1188getInWholeDaysimpl = m1188getInWholeDaysimpl(jM1186getAbsoluteValueUwyO8pc);
        int iM1187getHoursComponentimpl = m1187getHoursComponentimpl(jM1186getAbsoluteValueUwyO8pc);
        int iM1193getMinutesComponentimpl = m1193getMinutesComponentimpl(jM1186getAbsoluteValueUwyO8pc);
        int iM1195getSecondsComponentimpl = m1195getSecondsComponentimpl(jM1186getAbsoluteValueUwyO8pc);
        int iM1194getNanosecondsComponentimpl = m1194getNanosecondsComponentimpl(jM1186getAbsoluteValueUwyO8pc);
        int i = 0;
        boolean z = jM1188getInWholeDaysimpl != 0;
        boolean z2 = iM1187getHoursComponentimpl != 0;
        boolean z3 = iM1193getMinutesComponentimpl != 0;
        boolean z4 = (iM1195getSecondsComponentimpl == 0 && iM1194getNanosecondsComponentimpl == 0) ? false : true;
        if (z) {
            sb.append(jM1188getInWholeDaysimpl);
            sb.append('d');
            i = 1;
        }
        if (z2 || (z && (z3 || z4))) {
            int i2 = i + 1;
            if (i > 0) {
                sb.append(' ');
            }
            sb.append(iM1187getHoursComponentimpl);
            sb.append('h');
            i = i2;
        }
        if (z3 || (z4 && (z2 || z))) {
            int i3 = i + 1;
            if (i > 0) {
                sb.append(' ');
            }
            sb.append(iM1193getMinutesComponentimpl);
            sb.append('m');
            i = i3;
        }
        if (z4) {
            int i4 = i + 1;
            if (i > 0) {
                sb.append(' ');
            }
            if (iM1195getSecondsComponentimpl != 0 || z || z2 || z3) {
                m1181appendFractionalimpl(j, sb, iM1195getSecondsComponentimpl, iM1194getNanosecondsComponentimpl, 9, "s", false);
            } else if (iM1194getNanosecondsComponentimpl >= 1000000) {
                m1181appendFractionalimpl(j, sb, iM1194getNanosecondsComponentimpl / AnimState.VIEW_SIZE, iM1194getNanosecondsComponentimpl % AnimState.VIEW_SIZE, 6, "ms", false);
            } else if (iM1194getNanosecondsComponentimpl >= 1000) {
                m1181appendFractionalimpl(j, sb, iM1194getNanosecondsComponentimpl / 1000, iM1194getNanosecondsComponentimpl % 1000, 3, "us", false);
            } else {
                sb.append(iM1194getNanosecondsComponentimpl);
                sb.append("ns");
            }
            i = i4;
        }
        if (zM1203isNegativeimpl && i > 1) {
            sb.insert(1, '(').append(')');
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: unaryMinus-UwyO8pc, reason: not valid java name */
    public static final long m1209unaryMinusUwyO8pc(long j) {
        return DurationKt.durationOf(-m1197getValueimpl(j), ((int) j) & 1);
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(Duration duration) {
        return m1210compareToLRDsOJo(duration.m1211unboximpl());
    }

    /* JADX INFO: renamed from: compareTo-LRDsOJo, reason: not valid java name */
    public int m1210compareToLRDsOJo(long j) {
        return m1182compareToLRDsOJo(this.rawValue, j);
    }

    public boolean equals(Object obj) {
        return m1184equalsimpl(this.rawValue, obj);
    }

    public int hashCode() {
        return m1198hashCodeimpl(this.rawValue);
    }

    public String toString() {
        return m1208toStringimpl(this.rawValue);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m1211unboximpl() {
        return this.rawValue;
    }
}
