package com.miui.aod.util;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.miui.aod.AODApplication;
import com.miui.aod.Utils;
import com.miui.aod.utils.MiuiSettingsUtils;
import com.miui.maml.folme.AnimatedProperty;
import miui.util.FeatureParser;

/* JADX INFO: loaded from: classes.dex */
public final class ScreenResolutionUtils {
    public static volatile ScreenResolutionUtils mInstance;
    public boolean mIsSupported;
    public Point mPhysicalSize;
    public Point mScreenRealSize;
    public WindowManager mWindowManager;

    public ScreenResolutionUtils() {
        initResolution();
    }

    public static void cleanRealSize() {
        if (Utils.isFoldDevice()) {
            getInstance().mScreenRealSize = null;
        }
    }

    public static synchronized ScreenResolutionUtils getInstance() {
        try {
            if (mInstance == null) {
                synchronized (ScreenResolutionUtils.class) {
                    try {
                        if (mInstance == null) {
                            mInstance = new ScreenResolutionUtils();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        } catch (Throwable th2) {
            throw th2;
        }
        return mInstance;
    }

    public Point getRealSize() {
        if (this.mIsSupported || this.mScreenRealSize == null) {
            Point point = new Point();
            this.mWindowManager.getDefaultDisplay().getRealSize(point);
            Point point2 = new Point();
            this.mScreenRealSize = point2;
            point2.x = Math.min(point.x, point.y);
            this.mScreenRealSize.y = Math.max(point.x, point.y);
            Log.i("ScreenResolutionUtils", "getRealSize: " + this.mScreenRealSize.toString());
        }
        return this.mScreenRealSize;
    }

    public float getScreenRatio() {
        return (getRealSize().y * 1.0f) / this.mPhysicalSize.y;
    }

    public Rect getScreenRect(Context context) {
        WindowManager windowManager;
        if (context == null || (windowManager = this.mWindowManager) == null) {
            return new Rect();
        }
        Rect bounds = windowManager.getCurrentWindowMetrics().getBounds();
        Log.i("ScreenResolutionUtils", "get bounds = " + bounds);
        return bounds;
    }

    public final void initPhysicalSize() {
        if (this.mPhysicalSize == null) {
            Display.Mode mode = this.mWindowManager.getDefaultDisplay().getMode();
            this.mPhysicalSize = new Point(mode.getPhysicalWidth(), mode.getPhysicalHeight());
            String stringFromSystemProperties = MiuiSettingsUtils.getStringFromSystemProperties("persist.sys.miui_default_resolution", "");
            if (!TextUtils.isEmpty(stringFromSystemProperties)) {
                String[] strArrSplit = stringFromSystemProperties.split(AnimatedProperty.PROPERTY_NAME_X);
                if (strArrSplit.length == 2) {
                    this.mPhysicalSize.x = Integer.parseInt(strArrSplit[0]);
                    this.mPhysicalSize.y = Integer.parseInt(strArrSplit[1]);
                }
            }
            Log.i("ScreenResolutionUtils", "getPhysicalSize: " + this.mPhysicalSize.toString());
        }
    }

    public final void initResolution() {
        initWmIfNeed();
        initPhysicalSize();
        initSupportState();
    }

    /* JADX WARN: Code duplicated, block: B:7:0x000d  */
    public final void initSupportState() {
        boolean z;
        int[] intArray = FeatureParser.getIntArray("screen_resolution_supported");
        if (intArray != null) {
            z = intArray.length > 1;
        }
        this.mIsSupported = z;
        Log.i("ScreenResolutionUtils", "support screen resolutions: " + this.mIsSupported);
    }

    public final void initWmIfNeed() {
        if (this.mWindowManager == null) {
            this.mWindowManager = (WindowManager) AODApplication.sInstance.getSystemService("window");
        }
    }
}
