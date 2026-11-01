package com.miui.clock.tiny.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class DateFormatUtils {
    public static String formatTime(String str) {
        return formatTime(str, Locale.ENGLISH);
    }

    public static String formatTime(String str, Locale locale) {
        return new SimpleDateFormat(str, locale).format(new Date());
    }

    public static int getHourInt(boolean z, Calendar calendar) {
        if (z) {
            return calendar.get(18);
        }
        int i = calendar.get(18);
        if (i == 0) {
            return 12;
        }
        return i < 13 ? i : i - 12;
    }

    public static int getMinInt(Calendar calendar) {
        return calendar.get(20);
    }

    public static String getTimeString(int i, boolean z) {
        if (i >= 10 || !z) {
            return String.valueOf(i);
        }
        return "0" + i;
    }

    public static boolean is24HourFormat(Context context) {
        if (isCrossUser(context)) {
            try {
                return ((Boolean) DateFormat.class.getMethod("is24HourFormat", Context.class, Integer.TYPE).invoke(null, context, Integer.valueOf(updateCurrentUserId()))).booleanValue();
            } catch (Exception e) {
                Log.e("DateFormatUtils", "register receiver as user fail", e);
            }
        }
        return DateFormat.is24HourFormat(context);
    }

    public static boolean isAOD(Context context) {
        return "com.miui.aod".equals(context.getPackageName());
    }

    public static boolean isCrossUser(Context context) {
        return isSystemUI(context) || isAOD(context);
    }

    public static boolean isSystemUI(Context context) {
        return "com.android.systemui".equals(context.getPackageName());
    }

    public static int updateCurrentUserId() {
        try {
            return ((Integer) ActivityManager.class.getMethod("getCurrentUser", new Class[0]).invoke(null, new Object[0])).intValue();
        } catch (Exception e) {
            Log.e("DateFormatUtils", "get current user id fail", e);
            return 0;
        }
    }
}
