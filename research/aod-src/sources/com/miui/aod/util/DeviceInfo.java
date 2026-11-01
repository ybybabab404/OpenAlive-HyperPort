package com.miui.aod.util;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import com.miui.aod.AODApplication;
import com.miui.aod.MemInfoReader;
import com.miui.aod.common.BackgroundThread;
import com.tencent.mmkv.MMKV;
import miuix.core.util.MiuixUIUtils;

/* JADX INFO: loaded from: classes.dex */
public class DeviceInfo {
    public static DisplayMetrics mDisplayMetrics = null;
    public static boolean sLowMemDevice = false;

    public static /* synthetic */ void $r8$lambda$ktJcEXMJcyWUv7ZLg5yhLVqKpUE() {
        try {
            MMKV.defaultMMKV().encode("MemLevel", Long.parseLong(MemInfoReader.getMemTotal()));
        } catch (NumberFormatException e) {
            Log.e("DeviceInfo", "error...", e);
        }
        sLowMemDevice = getTotalMem() >= 0 && getTotalMem() <= 4194304;
    }

    public static int dp2Px(int i) {
        return (int) ((getDefaultDisplayMetrics().density * i) + 0.5f);
    }

    public static DisplayMetrics getDefaultDisplayMetrics() {
        if (mDisplayMetrics == null) {
            mDisplayMetrics = AODApplication.sInstance.getResources().getDisplayMetrics();
        }
        return mDisplayMetrics;
    }

    public static Point getDisplaySize() {
        Point point = new Point();
        point.x = getDefaultDisplayMetrics().widthPixels;
        point.y = getDefaultDisplayMetrics().heightPixels;
        return point;
    }

    public static Point getRealSize() {
        return ScreenResolutionUtils.getInstance().getRealSize();
    }

    public static int getStatusBarHeight(Context context) {
        return MiuixUIUtils.getStatusBarHeight(context);
    }

    public static long getTotalMem() {
        return MMKV.defaultMMKV().decodeLong("MemLevel", -1L);
    }

    public static boolean isLowMemDevice() {
        return sLowMemDevice;
    }

    public static int px2Dp(int i) {
        return (int) ((i / getDefaultDisplayMetrics().density) + 0.5f);
    }

    public static void updateDeviceMemInfo() {
        BackgroundThread.post(new Runnable() { // from class: com.miui.aod.util.DeviceInfo$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DeviceInfo.$r8$lambda$ktJcEXMJcyWUv7ZLg5yhLVqKpUE();
            }
        });
    }
}
