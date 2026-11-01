package com.xiaomi.onetrack.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes2.dex */
public class af {
    public static final long a = 604800000;
    public static final int b = 86400000;
    public static final int c = 43200000;
    public static final int d = 3600000;
    public static final int e = 1800000;
    public static final int f = 60000;
    public static final int g = 1000;
    private static final String h = "TimeUtil";
    private static final long i = 300000;

    public static int a(int i2) {
        Calendar calendar;
        try {
            calendar = Calendar.getInstance(TimeZone.getTimeZone(t.c()));
        } catch (Exception unused) {
            calendar = Calendar.getInstance();
        }
        calendar.set(6, calendar.get(6) - i2);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        try {
            return Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));
        } catch (Exception e2) {
            s.b(h, "getDate error: " + e2.getMessage());
            return -1;
        }
    }

    public static long a() {
        return System.currentTimeMillis();
    }

    public static long a(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        try {
            return simpleDateFormat.parse(str).getTime() + 86400000;
        } catch (ParseException e2) {
            s.c(h, "getMidnightTime error: " + e2.getMessage());
            return -1L;
        }
    }

    public static boolean a(long j) {
        s.a(h, "inTodayClientTime,current ts :" + System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        long timeInMillis = calendar.getTimeInMillis();
        long j2 = timeInMillis + 86400000;
        s.a(h, "[start]:" + timeInMillis + "\n[end]:" + j2 + "duration" + ((j2 - timeInMillis) - 86400000));
        StringBuilder sb = new StringBuilder();
        sb.append("is in today:");
        sb.append(timeInMillis <= j && j < j2);
        s.a(h, sb.toString());
        return timeInMillis <= j && j < j2;
    }

    public static boolean a(long j, long j2) {
        return Math.abs(System.currentTimeMillis() - j) >= j2;
    }

    public static long b() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTimeInMillis();
    }

    public static boolean b(long j) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        Calendar calendar2 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar2.setTimeInMillis(j);
        return calendar2.get(1) == calendar.get(1) && calendar2.get(2) == calendar.get(2) && calendar2.get(5) == calendar.get(5);
    }

    public static int c() {
        try {
            return Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai")).get(5);
        } catch (Exception e2) {
            s.c(h, "getDayOfMonth error: " + e2.getMessage());
            return -1;
        }
    }

    public static int d() {
        try {
            int i2 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai")).get(7);
            if (i2 == 1) {
                return 7;
            }
            return i2 - 1;
        } catch (Exception e2) {
            s.c(h, "getDayOfWeek error: " + e2.getMessage());
            return -1;
        }
    }
}
