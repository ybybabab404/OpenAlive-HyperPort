package com.miui.aod.widget;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import com.miui.maml.folme.AnimatedPropertyType;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class SunSelector {
    public static final int[] sChangePoint = {330, 340, 350, 360, 370, 380, 475, 570, 665, 760, 855, 950, 1045, 1060, 1070, 1080, 1090, AnimatedPropertyType.RESERVE, 1110, 1205, 1300, 45, 140, 235};
    public static long sSunRise;
    public static long sSunSet;

    public static int getChangePoint(int i) {
        return sChangePoint[i];
    }

    public static int getChangePointLength() {
        return sChangePoint.length;
    }

    public static int getDrawableIndex() {
        Calendar calendar = new Calendar(TimeZone.getDefault());
        return getDrawableIndex((calendar.get(18) * 60) + calendar.get(20));
    }

    public static int getDrawableIndex(int i) {
        int length = sChangePoint.length - 1;
        int i2 = 0;
        while (true) {
            int[] iArr = sChangePoint;
            if (i2 >= iArr.length - 1) {
                return length;
            }
            int i3 = iArr[i2];
            if (i >= i3 && i < iArr[i2 + 1]) {
                length = i2;
            }
            int i4 = i2 + 1;
            int i5 = iArr[i4];
            if (i5 < i3 && (i >= i3 || i < i5)) {
                length = i2;
            }
            i2 = i4;
        }
    }

    public static int getDrawableLength() {
        return AODBg.getSunImage().length;
    }

    public static int getSunImage(int i) {
        return AODBg.getSunImage()[i];
    }

    public static long getTimeWithOnlyHourAndMinute(java.util.Calendar calendar, long j, TimeZone timeZone) {
        calendar.setTimeZone(timeZone);
        calendar.setTimeInMillis(j);
        int i = calendar.get(11);
        int i2 = calendar.get(12);
        calendar.clear();
        return ((long) ((i * 60) + i2)) * 60000;
    }

    public static boolean shouldUpdateSunriseTime(Context context, int i) {
        return Settings.System.getInt(context.getContentResolver(), "sunrise_update", 0) != i;
    }

    public static void updateChangepoint(long j, long j2) {
        int[] iArr = sChangePoint;
        int i = (int) (j / 60000);
        iArr[3] = i;
        int i2 = (int) (j2 / 60000);
        iArr[15] = i2;
        int i3 = ((i2 - i) - 40) / 8;
        int i4 = ((i + 1440) - i2) - 60;
        int i5 = i4 / 6;
        iArr[0] = i - 30;
        iArr[1] = i - 20;
        iArr[2] = i - 10;
        for (int i6 = 0; i6 <= 5; i6++) {
            int[] iArr2 = sChangePoint;
            iArr2[i6] = iArr2[3] + ((i6 - 3) * 10);
        }
        for (int i7 = 6; i7 <= 12; i7++) {
            int[] iArr3 = sChangePoint;
            iArr3[i7] = iArr3[3] + 20 + ((i7 - 5) * i3);
        }
        for (int i8 = 13; i8 <= 18; i8++) {
            int[] iArr4 = sChangePoint;
            iArr4[i8] = iArr4[15] + ((i8 - 15) * 10);
        }
        for (int i9 = 19; i9 <= 23; i9++) {
            int[] iArr5 = sChangePoint;
            int i10 = iArr5[15] + 30 + ((i9 - 18) * i5);
            iArr5[i9] = i10;
            if (i10 > 1440) {
                iArr5[i9] = i10 - 1440;
            }
        }
    }

    public static void updateSunRiseTime(final Context context) {
        final java.util.Calendar calendar = java.util.Calendar.getInstance();
        final int i = calendar.get(6);
        if (shouldUpdateSunriseTime(context, i)) {
            new AsyncTask<Void, Void, Boolean>() { // from class: com.miui.aod.widget.SunSelector.1
                @Override // android.os.AsyncTask
                public Boolean doInBackground(Void... voidArr) {
                    boolean z = false;
                    try {
                        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://weather/weather"), null, null, null, null);
                        if (cursorQuery != null) {
                            try {
                                if (cursorQuery.getCount() > 0) {
                                    cursorQuery.moveToFirst();
                                    long j = cursorQuery.getLong(cursorQuery.getColumnIndex("sunrise"));
                                    long j2 = cursorQuery.getLong(cursorQuery.getColumnIndex("sunset"));
                                    TimeZone timeZone = TimeZone.getDefault();
                                    SunSelector.sSunRise = SunSelector.getTimeWithOnlyHourAndMinute(calendar, j, timeZone);
                                    SunSelector.sSunSet = SunSelector.getTimeWithOnlyHourAndMinute(calendar, j2, timeZone);
                                    z = true;
                                }
                            } catch (Exception e) {
                                Log.e("SunSelector", "error...", e);
                            } finally {
                                cursorQuery.close();
                            }
                        }
                    } catch (Exception e2) {
                        Log.e("SunSelector", "query sunRiseSet fail", e2);
                    }
                    return Boolean.valueOf(z);
                }

                @Override // android.os.AsyncTask
                public void onPostExecute(Boolean bool) {
                    if (bool.booleanValue()) {
                        Settings.System.putLong(context.getContentResolver(), "sunrise", SunSelector.sSunRise);
                        Settings.System.putLong(context.getContentResolver(), "sunset", SunSelector.sSunSet);
                        SunSelector.updateChangepoint(SunSelector.sSunRise, SunSelector.sSunSet);
                        Settings.System.putInt(context.getContentResolver(), "sunrise_update", i);
                    }
                }
            }.execute(new Void[0]);
        } else {
            updateChangepoint(Settings.System.getLong(context.getContentResolver(), "sunrise", 360L), Settings.System.getLong(context.getContentResolver(), "sunset", 1080L));
        }
    }
}
