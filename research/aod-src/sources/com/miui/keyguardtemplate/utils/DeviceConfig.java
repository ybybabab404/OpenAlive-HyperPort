package com.miui.keyguardtemplate.utils;

import android.content.Context;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.miui.aod.common.StyleInfo;
import com.miui.keyguardtemplate.Util;
import java.lang.reflect.InvocationTargetException;
import miui.util.MiuiMultiDisplayTypeInfo;
import miuix.core.util.SystemProperties;

/* JADX INFO: loaded from: classes2.dex */
public class DeviceConfig {
    public static final boolean BACKGROUND_BLUR_SUPPORTED;
    public static final boolean FOLD_DEVICE;
    public static final boolean PAD_DEVICE;
    public static int sDisplayMode;
    public static final Point[] sScreenRealSizePoint;

    static {
        FOLD_DEVICE = SystemProperties.getInt("persist.sys.muiltdisplay_type", 0) == 2;
        PAD_DEVICE = "tablet".equals(SystemProperties.get("ro.build.characteristics", StyleInfo.DEFAULT_PARAMETER));
        sDisplayMode = -2;
        sScreenRealSizePoint = new Point[2];
        BACKGROUND_BLUR_SUPPORTED = SystemProperties.getBoolean("persist.sys.background_blur_supported", false);
    }

    public static float calculateScale(Context context) {
        return context.getResources().getConfiguration().smallestScreenWidthDp / getDesignDp(context);
    }

    public static Point currentDisplaySize(Context context) {
        Point point = new Point();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealSize(point);
        return point;
    }

    public static float getDesignDp(Context context) {
        if (isJ18() && !inLargeScreen(context)) {
            return 320.0f;
        }
        if (isJ18() && inLargeScreen(context)) {
            return 676.0f;
        }
        if (FOLD_DEVICE && Util.inFoldLargeScreen(context)) {
            return 696.0f;
        }
        return PAD_DEVICE ? 711.0f : 392.0f;
    }

    public static int getDeviceFormType() {
        if (sDisplayMode == -2) {
            try {
                Object objInvoke = MiuiMultiDisplayTypeInfo.class.getMethod("getDeviceType", new Class[0]).invoke(MiuiMultiDisplayTypeInfo.class, new Object[0]);
                if (objInvoke instanceof Integer) {
                    sDisplayMode = ((Integer) objInvoke).intValue();
                }
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return sDisplayMode;
    }

    public static Point getFlipScreenPhysicalSize(Context context, boolean z) {
        Display display;
        Display display2;
        Point point = z ? sScreenRealSizePoint[1] : sScreenRealSizePoint[0];
        if (point != null) {
            return point;
        }
        Point point2 = new Point();
        Display[] displays = ((DisplayManager) context.getSystemService("display")).getDisplays("android.hardware.display.category.ALL_INCLUDING_DISABLED");
        if (displays == null || displays.length != 2) {
            return currentDisplaySize(context);
        }
        Display display3 = displays[0];
        if (display3 != null && displays[1] != null) {
            if (display3.getMode().getPhysicalHeight() > displays[1].getMode().getPhysicalHeight()) {
                display = displays[0];
                display2 = displays[1];
            } else {
                display = displays[1];
                display2 = displays[0];
            }
            if (z) {
                point2.set(display.getMode().getPhysicalWidth(), display.getMode().getPhysicalHeight());
            } else {
                point2.set(display2.getMode().getPhysicalWidth(), display2.getMode().getPhysicalHeight());
            }
            if (isValidScreenPoint(point2)) {
                if (z) {
                    sScreenRealSizePoint[1] = point2;
                } else {
                    sScreenRealSizePoint[0] = point2;
                }
                Log.d("ClockDeviceConfig", "getFlipScreenPhysicalSize: width=" + point2.x + " height=" + point2.y);
                return point2;
            }
        }
        return currentDisplaySize(context);
    }

    public static boolean inLargeScreen(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    public static boolean isFlip() {
        if (sDisplayMode == -2) {
            sDisplayMode = getDeviceFormType();
        }
        return sDisplayMode == 4;
    }

    public static boolean isJ18() {
        return TextUtils.equals(Build.DEVICE, "cetus");
    }

    public static boolean isPhone() {
        return (FOLD_DEVICE || PAD_DEVICE) ? false : true;
    }

    public static boolean isValidScreenPoint(Point point) {
        return point != null && point.x > 0 && point.y > 0;
    }
}
