package com.miui.aod.utils;

import android.content.Context;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.Trace;
import android.text.format.DateFormat;
import android.text.format.Formatter;
import android.util.Log;
import android.util.TimeUtils;
import android.view.WindowManager;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public final class CommonUtils {
    public static int BRIGHTNESS_ON = 0;
    public static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES = 1;
    private static final String TAG = "CommonUtils";

    static {
        try {
            BRIGHTNESS_ON = (1 << ((Integer) ReflectUtil.getStaticObjectField(Class.forName("miui.os.DeviceFeature"), "BACKLIGHT_BIT")).intValue()) - 1;
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
            Log.e(TAG, "failed call miui.os.DeviceFeature#BACKLIGHT_BIT", e);
        }
    }

    private CommonUtils() {
    }

    public static void formatDuration(long j, PrintWriter printWriter) {
        TimeUtils.formatDuration(j, printWriter);
    }

    public static String formatShortElapsedTime(Context context, long j) {
        return Formatter.formatShortElapsedTime(context, j);
    }

    public static int getEnrolledFingerprintsSize(FingerprintManager fingerprintManager) {
        return fingerprintManager.getEnrolledFingerprints().size();
    }

    public static boolean hasCallbacks(Handler handler, Runnable runnable) {
        return handler.hasCallbacks(runnable);
    }

    public static boolean is24HourFormat(Context context, int i) {
        return DateFormat.is24HourFormat(context, i);
    }

    public static void requestWakeUp(Context context) {
        ((PowerManager) context.getSystemService(PowerManager.class)).wakeUp(SystemClock.uptimeMillis(), "com.android.systemui:NODOZE");
    }

    public static int resolveHour(int i, boolean z) {
        if (z) {
            return i;
        }
        if (i > 12) {
            i -= 12;
        }
        if (i == 0) {
            return 12;
        }
        return i;
    }

    public static void setLayoutInDisplayCutoutMode(WindowManager.LayoutParams layoutParams, int i) {
        layoutParams.layoutInDisplayCutoutMode = i;
    }

    public static void setXfermodeForDrawable(Drawable drawable, Xfermode xfermode) {
        drawable.setXfermode(xfermode);
    }

    public static void traceAppCounter(String str, int i) {
        Trace.traceCounter(4096L, str, i);
    }
}
