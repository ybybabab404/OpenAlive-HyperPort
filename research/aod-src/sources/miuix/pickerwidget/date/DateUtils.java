package miuix.pickerwidget.date;

import android.content.Context;
import android.text.format.DateFormat;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.util.TimeZone;
import miuix.core.util.Pools;
import miuix.pickerwidget.R;

/* JADX INFO: loaded from: classes2.dex */
public class DateUtils {
    public static final Pools.Pool CALENDAR_POOL = Pools.createSoftReferencePool(new Pools.Manager<Calendar>() { // from class: miuix.pickerwidget.date.DateUtils.1
        @Override // miuix.core.util.Pools.Manager
        public Calendar createInstance() {
            return new Calendar();
        }
    }, 1);

    public static String formatDateTime(Context context, long j, int i) {
        StringBuilder sb = (StringBuilder) Pools.getStringBuilderPool().acquire();
        String string = formatDateTime(context, sb, j, i, null).toString();
        Pools.getStringBuilderPool().release(sb);
        return string;
    }

    public static String formatDateTime(Context context, long j, int i, TimeZone timeZone) {
        StringBuilder sb = (StringBuilder) Pools.getStringBuilderPool().acquire();
        String string = formatDateTime(context, sb, j, i, timeZone).toString();
        Pools.getStringBuilderPool().release(sb);
        return string;
    }

    public static StringBuilder formatDateTime(Context context, StringBuilder sb, long j, int i, TimeZone timeZone) {
        if ((i & 16) == 0 && (i & 32) == 0) {
            i |= DateFormat.is24HourFormat(context) ? 32 : 16;
        }
        String string = context.getString(getFormatResId(i));
        StringBuilder sb2 = (StringBuilder) Pools.getStringBuilderPool().acquire();
        Calendar calendar = (Calendar) CALENDAR_POOL.acquire();
        calendar.setTimeZone(timeZone);
        calendar.setTimeInMillis(j);
        int length = string.length();
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = string.charAt(i2);
            if (cCharAt == 'D') {
                sb2.append(context.getString(getDatePatternResId(i)));
            } else if (cCharAt == 'T') {
                sb2.append(context.getString(getTimePatternResId(calendar, i)));
            } else if (cCharAt != 'W') {
                sb2.append(cCharAt);
            } else {
                sb2.append(context.getString(getWeekdayPatternResId(i)));
            }
        }
        calendar.format(context, sb, sb2);
        Pools.getStringBuilderPool().release(sb2);
        CALENDAR_POOL.release(calendar);
        return sb;
    }

    public static int getDatePatternResId(int i) {
        if ((i & 32768) == 32768) {
            if ((i & 512) == 512) {
                if ((i & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) == 256) {
                    return (i & 128) == 128 ? R.string.fmt_date_numeric_year_month_day : R.string.fmt_date_numeric_year_month;
                }
                return R.string.fmt_date_numeric_year;
            }
            if ((i & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) == 256) {
                return (i & 128) == 128 ? R.string.fmt_date_numeric_month_day : R.string.fmt_date_numeric_month;
            }
            if ((i & 128) == 128) {
                return R.string.fmt_date_numeric_day;
            }
            throw new IllegalArgumentException("no any time date");
        }
        if ((i & AutoBottomSheetKt.ALIGN_SCREEN_TOP) == 4096) {
            if ((i & 512) == 512) {
                if ((i & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) == 256) {
                    return (i & 128) == 128 ? R.string.fmt_date_short_year_month_day : R.string.fmt_date_short_year_month;
                }
                return R.string.fmt_date_year;
            }
            if ((i & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) == 256) {
                return (i & 128) == 128 ? R.string.fmt_date_short_month_day : R.string.fmt_date_short_month;
            }
            if ((i & 128) == 128) {
                return R.string.fmt_date_day;
            }
            throw new IllegalArgumentException("no any time date");
        }
        if ((i & 512) == 512) {
            if ((i & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) == 256) {
                return (i & 128) == 128 ? R.string.fmt_date_long_year_month_day : R.string.fmt_date_long_year_month;
            }
            return R.string.fmt_date_year;
        }
        if ((i & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) == 256) {
            return (i & 128) == 128 ? R.string.fmt_date_long_month_day : R.string.fmt_date_long_month;
        }
        if ((i & 128) == 128) {
            return R.string.fmt_date_day;
        }
        throw new IllegalArgumentException("no any time date");
    }

    public static int getFormatResId(int i) {
        if ((i & 1024) == 1024) {
            if ((i & 896) != 0) {
                if ((i & 15) != 0) {
                    return (i & AutoBottomSheetKt.ALIGN_SCREEN_END) == 2048 ? R.string.fmt_weekday_date_time_timezone : R.string.fmt_weekday_date_time;
                }
                return (i & AutoBottomSheetKt.ALIGN_SCREEN_END) == 2048 ? R.string.fmt_weekday_date_timezone : R.string.fmt_weekday_date;
            }
            if ((i & 15) != 0) {
                return (i & AutoBottomSheetKt.ALIGN_SCREEN_END) == 2048 ? R.string.fmt_weekday_time_timezone : R.string.fmt_weekday_time;
            }
            return (i & AutoBottomSheetKt.ALIGN_SCREEN_END) == 2048 ? R.string.fmt_weekday_timezone : R.string.fmt_weekday;
        }
        if ((i & 896) != 0) {
            if ((i & 15) != 0) {
                return (i & AutoBottomSheetKt.ALIGN_SCREEN_END) == 2048 ? R.string.fmt_date_time_timezone : R.string.fmt_date_time;
            }
            return (i & AutoBottomSheetKt.ALIGN_SCREEN_END) == 2048 ? R.string.fmt_date_timezone : R.string.fmt_date;
        }
        if ((i & 15) != 0) {
            return (i & AutoBottomSheetKt.ALIGN_SCREEN_END) == 2048 ? R.string.fmt_time_timezone : R.string.fmt_time;
        }
        return (i & AutoBottomSheetKt.ALIGN_SCREEN_END) == 2048 ? R.string.fmt_timezone : R.string.empty;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x003b A[PHI: r0
  0x003b: PHI (r0v8 int) = (r0v6 int), (r0v7 int), (r0v7 int), (r0v6 int) binds: [B:15:0x0028, B:17:0x0032, B:19:0x0036, B:13:0x0024] A[DONT_GENERATE, DONT_INLINE]] */
    public static int getTimePatternResId(Calendar calendar, int i) {
        if ((i & 16384) == 16384 && (((i & 1) != 1 || calendar.get(22) == 0) && (i & 14) != 0)) {
            int i2 = i & (-2);
            if (((i & 2) != 2 || calendar.get(21) == 0) && (i & 12) != 0) {
                i2 = i & (-4);
                if (calendar.get(20) != 0 || (i & 8) == 0) {
                    i = i2;
                } else {
                    i &= -8;
                }
            } else {
                i = i2;
            }
        }
        if ((i & 8) != 8) {
            if ((i & 4) == 4) {
                if ((i & 2) == 2) {
                    return (i & 1) == 1 ? R.string.fmt_time_minute_second_millis : R.string.fmt_time_minute_second;
                }
                return R.string.fmt_time_minute;
            }
            if ((i & 2) == 2) {
                return (i & 1) == 1 ? R.string.fmt_time_second_millis : R.string.fmt_time_second;
            }
            if ((i & 1) == 1) {
                return R.string.fmt_time_millis;
            }
            throw new IllegalArgumentException("no any time date");
        }
        if ((i & 16) != 16) {
            if ((i & 4) != 4) {
                return R.string.fmt_time_24hour;
            }
            if ((i & 2) == 2) {
                return (i & 1) == 1 ? R.string.fmt_time_24hour_minute_second_millis : R.string.fmt_time_24hour_minute_second;
            }
            return R.string.fmt_time_24hour_minute;
        }
        if ((i & 64) == 64) {
            if ((i & 4) != 4) {
                return R.string.fmt_time_12hour;
            }
            if ((i & 2) == 2) {
                return (i & 1) == 1 ? R.string.fmt_time_12hour_minute_second_millis : R.string.fmt_time_12hour_minute_second;
            }
            return R.string.fmt_time_12hour_minute;
        }
        if ((i & 4) != 4) {
            return R.string.fmt_time_12hour_pm;
        }
        if ((i & 2) == 2) {
            return (i & 1) == 1 ? R.string.fmt_time_12hour_minute_second_millis_pm : R.string.fmt_time_12hour_minute_second_pm;
        }
        return R.string.fmt_time_12hour_minute_pm;
    }

    public static int getWeekdayPatternResId(int i) {
        return (i & AutoBottomSheetKt.ALIGN_SCREEN_START) == 8192 ? R.string.fmt_weekday_short : R.string.fmt_weekday_long;
    }
}
