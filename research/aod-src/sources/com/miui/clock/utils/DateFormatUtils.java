package com.miui.clock.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import com.miui.clock.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class DateFormatUtils {
    public static Integer mUserId;
    public static HashMap simpleDateFormatHashMap = new HashMap();

    public static void clearCache() {
        simpleDateFormatHashMap.clear();
    }

    public static void clearUserIdCache() {
        Log.d("DateFormatUtils", "clearUserIdCache user id " + mUserId);
        mUserId = null;
    }

    public static String formatTime(String str) {
        return formatTime(str, Locale.ENGLISH);
    }

    public static String formatTime(String str, Locale locale) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) simpleDateFormatHashMap.get(str);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(str, locale);
            Log.d("DateFormatUtils", "DataFormatUtils formatTime: " + simpleDateFormat.getTimeZone());
            simpleDateFormatHashMap.put(str, simpleDateFormat);
        }
        return simpleDateFormat.format(new Date());
    }

    public static void initDataFormat(Context context) {
        String str = new String[]{context.getResources().getString(R.string.format_month_day_style2)}[0];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, Locale.ENGLISH);
        if (simpleDateFormatHashMap.containsKey(str)) {
            return;
        }
        simpleDateFormatHashMap.put(str, simpleDateFormat);
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
        Integer num = mUserId;
        if (num != null) {
            return num.intValue();
        }
        try {
            Integer num2 = (Integer) ActivityManager.class.getMethod("getCurrentUser", new Class[0]).invoke(null, new Object[0]);
            Log.d("DateFormatUtils", "get current user id successful， before" + mUserId + ", new: " + num2.intValue());
            mUserId = num2;
        } catch (Exception e) {
            mUserId = 0;
            Log.e("DateFormatUtils", "get current user id fail" + e.getMessage());
        }
        Integer num3 = mUserId;
        if (num3 != null) {
            return num3.intValue();
        }
        return 0;
    }
}
