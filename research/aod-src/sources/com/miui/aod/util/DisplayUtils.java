package com.miui.aod.util;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;
import android.view.DisplayCutout;
import com.miui.aod.widget.Direction;

/* JADX INFO: loaded from: classes.dex */
public final class DisplayUtils {
    public static Direction getCutoutPosition(Context context) {
        if (context != null && context.getDisplay() != null) {
            DisplayCutout cutout = context.getDisplay().getCutout();
            if (!cutout.getBoundingRectLeft().isEmpty()) {
                return Direction.CAMERA_CUTOUT_ON_THE_LEFT;
            }
            if (!cutout.getBoundingRectRight().isEmpty()) {
                return Direction.CAMERA_CUTOUT_ON_THE_RIGHT;
            }
        }
        return Direction.CAMERA_CUTOUT_ON_NONE;
    }

    public static int getDisplayState(Context context) {
        if (context == null || context.getDisplay() == null) {
            return -1;
        }
        return context.getDisplay().getState();
    }

    public static boolean hasVirtualDisplay(Context context) {
        for (Display display : ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplays()) {
            try {
                if (((Integer) display.getClass().getMethod("getType", new Class[0]).invoke(display, new Object[0])).intValue() == 5) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
