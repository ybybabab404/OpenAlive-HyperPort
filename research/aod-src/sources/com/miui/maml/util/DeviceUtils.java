package com.miui.maml.util;

import android.graphics.Point;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/* JADX INFO: loaded from: classes2.dex */
public class DeviceUtils {
    private static final float DEFAULT_RESIZE_SCALE_FOLD = 0.5f;
    private static final float DEFAULT_RESIZE_SCALE_PAD = 0.383f;

    private DeviceUtils() {
    }

    public static int getDeviceLevel() {
        if (isLiteV1Covered()) {
            return 0;
        }
        return miuix.device.DeviceUtils.getDeviceLevel();
    }

    private static int getPhysicalScreenSmallWidthDip(WindowManager windowManager) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        return (int) (Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels) / displayMetrics.density);
    }

    private static float getSpecialPadRatio(String str) {
        if (isSpecital(str)) {
            return 0.766f;
        }
        return DEFAULT_RESIZE_SCALE_PAD;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0047  */
    public static int[] getTargetScreenWH(WindowManager windowManager, float f, String str) {
        if (windowManager == null) {
            throw new NullPointerException("wm is null");
        }
        Display defaultDisplay = windowManager.getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        int rotation = defaultDisplay.getRotation();
        boolean z = true;
        if (rotation != 1 && rotation != 3) {
            z = false;
        }
        if (!needScaleDevice(windowManager) && f <= 0.0f) {
            f = 1.0f;
        } else if (Math.abs(f) <= 1.0E-6d) {
            if (isPad()) {
                f = getSpecialPadRatio(str);
            } else if (isFold()) {
                f = 0.5f;
            } else {
                f = 1.0f;
            }
        }
        return new int[]{(int) ((z ? point.y : point.x) * f), z ? point.x : point.y};
    }

    public static boolean isFold() {
        return SystemProperties.getInt("persist.sys.muiltdisplay_type", 0) == 2;
    }

    private static boolean isLiteV1Covered() {
        return isMiuiLite() || isMiuiMiddle();
    }

    private static boolean isMiuiLite() {
        return miuix.device.DeviceUtils.isMiuiLiteRom();
    }

    private static boolean isMiuiMiddle() {
        return miuix.device.DeviceUtils.isMiuiMiddle();
    }

    public static boolean isPad() {
        return SystemProperties.get("ro.build.characteristics").contains("tablet");
    }

    private static boolean isSpecital(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return "1x2".equals(str) || "2x1".equals(str);
    }

    private static boolean needScaleDevice(WindowManager windowManager) {
        if (isPad()) {
            return true;
        }
        return isFold() && getPhysicalScreenSmallWidthDip(windowManager) >= 600;
    }
}
