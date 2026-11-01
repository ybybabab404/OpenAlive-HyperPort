package com.miui.clock.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import com.miui.aod.common.StyleInfo;
import com.xiaomi.onetrack.util.ac;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Locale;
import miui.util.MiuiMultiDisplayTypeInfo;
import miuix.core.util.SystemProperties;
import miuix.device.DeviceUtils;

/* JADX INFO: loaded from: classes.dex */
public class DeviceConfig {
    public static final boolean BACKGROUND_BLUR_KEYGUARD_DISABLED;
    public static final boolean BACKGROUND_BLUR_STATUS_DEFAULT;
    public static final boolean BACKGROUND_BLUR_SUPPORTED_2;
    public static final int BACKGROUND_BLUR_VERSION;
    public static final int COMPUTILITY_V2_LEVEL;
    public static final boolean FOLD_DEVICE;
    public static final String GXZW_POSITION;
    public static final ArrayList NO_SUPPORT_BLUR_DEVICE;
    public static final int OS2_ADVANCED_VISUAL_VERSION;
    public static final boolean PAD_DEVICE;
    public static final boolean SUPPORT_BACKGROUND_BLUR;
    public static int mBackgroundBlurEnabled;
    public static Method mGetIntForUsr;
    public static int sCachedCurrentH;
    public static int sCachedCurrentW;
    public static int sCachedDensityDpi;
    public static int sCachedDisplayId;
    public static int sCachedOrientation;
    public static int sCachedScreenHeightDp;
    public static int sCachedScreenWidthDp;
    public static int sDisplayMode;
    public static final ThreadLocal sScreenFake;
    public static final int sStableDeviceDensityDpi;

    public enum ScreenType {
        FOLD_OUT_LAND,
        FOLD_OUT_PORTRAIT,
        PAD_LAND,
        PAD_PORTRAIT,
        PHONE,
        PHONE_LAND
    }

    static {
        FOLD_DEVICE = SystemProperties.getInt("persist.sys.muiltdisplay_type", 0) == 2;
        PAD_DEVICE = "tablet".equals(SystemProperties.get("ro.build.characteristics", StyleInfo.DEFAULT_PARAMETER));
        sCachedDisplayId = -1;
        sCachedScreenWidthDp = 0;
        sCachedScreenHeightDp = 0;
        sCachedDensityDpi = 0;
        sCachedOrientation = -1;
        sCachedCurrentW = 0;
        sCachedCurrentH = 0;
        sDisplayMode = -2;
        sScreenFake = new ThreadLocal();
        mBackgroundBlurEnabled = -1;
        ArrayList arrayList = new ArrayList();
        NO_SUPPORT_BLUR_DEVICE = arrayList;
        BACKGROUND_BLUR_KEYGUARD_DISABLED = SystemProperties.getBoolean("persist.sys.background_blur_keyguard_disabled", false);
        BACKGROUND_BLUR_STATUS_DEFAULT = SystemProperties.getBoolean("persist.sys.background_blur_status_default", false);
        BACKGROUND_BLUR_SUPPORTED_2 = SystemProperties.getBoolean("persist.sys.background_blur_supported", false);
        BACKGROUND_BLUR_VERSION = SystemProperties.getInt("persist.sys.background_blur_version", 0);
        OS2_ADVANCED_VISUAL_VERSION = SystemProperties.getInt("persist.sys.advanced_visual_release", 0);
        sStableDeviceDensityDpi = SystemProperties.getInt("ro.sf.lcd_density", 440);
        arrayList.add("yudi");
        arrayList.add("corot");
        SUPPORT_BACKGROUND_BLUR = checkDeviceSupportBackgroundBlur();
        GXZW_POSITION = SystemProperties.get("ro.hardware.fp.fod.location", "");
        COMPUTILITY_V2_LEVEL = SystemProperties.getInt("persist.sys.computilityV2.devicelevel", -1);
    }

    public static float calculateScale(Context context) {
        int i = context.getResources().getConfiguration().smallestScreenWidthDp;
        if (isO80() && isAOD(context)) {
            if (i == 930) {
                i = 949;
            } else if (i == 888) {
                i = 907;
            } else if (i == 1163) {
                i = 1187;
            }
        }
        return i / getDesignDp(context);
    }

    public static float calculateScaleByScreenWidth(Context context, int i) {
        return (context.getResources().getConfiguration().screenWidthDp * 1.0f) / i;
    }

    public static boolean checkDeviceSupportBackgroundBlur() {
        return (!BACKGROUND_BLUR_SUPPORTED_2 || (NO_SUPPORT_BLUR_DEVICE.contains(Build.DEVICE) && !supportBlurMode1000()) || (BACKGROUND_BLUR_KEYGUARD_DISABLED && isHighResolution())) ? false : true;
    }

    public static float dpToPx(float f, Context context) {
        return f * context.getResources().getDisplayMetrics().density * calculateScale(context);
    }

    public static int dpToPxRounded(float f, Context context) {
        return Math.round(dpToPx(f, context));
    }

    public static int[] getCachedCurrentScreenSize(WindowManager windowManager, Display display, Configuration configuration) {
        int iWidth;
        int iHeight;
        int i;
        int i2;
        int displayId = display.getDisplayId();
        if (displayId == sCachedDisplayId && configuration.screenWidthDp == sCachedScreenWidthDp && configuration.screenHeightDp == sCachedScreenHeightDp && configuration.densityDpi == sCachedDensityDpi && configuration.orientation == sCachedOrientation && (i = sCachedCurrentW) > 0 && (i2 = sCachedCurrentH) > 0) {
            return new int[]{i, i2};
        }
        try {
            Rect bounds = windowManager.getMaximumWindowMetrics().getBounds();
            iWidth = bounds.width();
            iHeight = bounds.height();
        } catch (Throwable unused) {
            Point point = new Point();
            display.getRealSize(point);
            iWidth = point.x;
            iHeight = point.y;
        }
        sCachedDisplayId = displayId;
        sCachedScreenWidthDp = configuration.screenWidthDp;
        sCachedScreenHeightDp = configuration.screenHeightDp;
        sCachedDensityDpi = configuration.densityDpi;
        sCachedOrientation = configuration.orientation;
        sCachedCurrentW = iWidth;
        sCachedCurrentH = iHeight;
        return new int[]{iWidth, iHeight};
    }

    public static float getDesignDp(Context context) {
        if (isJ18() && !inLargeScreen(context)) {
            return 320.0f;
        }
        if (isJ18() && inLargeScreen(context)) {
            return 676.0f;
        }
        if (isQ18() && inFoldLargeScreen(context)) {
            return 618.0f;
        }
        if (FOLD_DEVICE && inFoldLargeScreen(context)) {
            return 696.0f;
        }
        return PAD_DEVICE ? 711.0f : 392.0f;
    }

    public static float getDesignDp(Context context, ScreenType screenType) {
        if (PAD_DEVICE && screenType == ScreenType.PAD_LAND) {
            return 1137.0f;
        }
        return getDesignDp(context);
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

    public static int getDimen(int i, Context context) {
        return Math.round(context.getResources().getDimensionPixelSize(i) * calculateScale(context));
    }

    public static float getDimenDpValue(int i, Context context) {
        TypedValue typedValue = new TypedValue();
        context.getResources().getValue(i, typedValue, true);
        return TypedValue.complexToFloat(typedValue.data);
    }

    public static int[] getFoldOuterScreenSize(Context context) {
        if (!FOLD_DEVICE) {
            return null;
        }
        try {
            Display[] displays = ((DisplayManager) context.getSystemService("display")).getDisplays("android.hardware.display.category.ALL_INCLUDING_DISABLED");
            if (displays != null && displays.length >= 2) {
                Display display = displays[0];
                for (Display display2 : displays) {
                    if (display2.getDisplayId() < 2 && display2.getMode().getPhysicalWidth() < display.getMode().getPhysicalWidth()) {
                        display = display2;
                    }
                }
                int physicalWidth = display.getMode().getPhysicalWidth();
                int physicalHeight = display.getMode().getPhysicalHeight();
                return new int[]{Math.min(physicalWidth, physicalHeight), Math.max(physicalWidth, physicalHeight), sStableDeviceDensityDpi};
            }
            return null;
        } catch (Exception e) {
            Log.w("ClockDeviceConfig", "getFoldOuterScreenSize failed", e);
            return null;
        }
    }

    public static Method getIntForUserMethod() {
        if (mGetIntForUsr == null) {
            try {
                Class cls = Integer.TYPE;
                mGetIntForUsr = Settings.Secure.class.getMethod("getIntForUser", ContentResolver.class, String.class, cls, cls);
            } catch (NoSuchMethodException e) {
                Log.d("ClockDeviceConfig", "getIntForUserMethod: " + e.getMessage());
            }
        }
        return mGetIntForUsr;
    }

    public static String getQHDMode() {
        String str = SystemProperties.get("persist.sys.miui_resolution", null);
        return (TextUtils.isEmpty(str) || Integer.parseInt(str.split(ac.b)[0]) != 1440) ? "FHD+" : "WQHD+";
    }

    public static Rect getScreenRect(Context context) {
        if (context == null) {
            return new Rect();
        }
        Rect bounds = ((WindowManager) context.getSystemService(WindowManager.class)).getCurrentWindowMetrics().getBounds();
        Log.i("ClockDeviceConfig", "get bounds = " + bounds);
        return bounds;
    }

    public static ScreenType getScreenType(Context context) {
        boolean z = context.getResources().getConfiguration().orientation == 2;
        if (PAD_DEVICE) {
            return z ? ScreenType.PAD_LAND : ScreenType.PAD_PORTRAIT;
        }
        if (FOLD_DEVICE && inFoldLargeScreen(context)) {
            return z ? ScreenType.FOLD_OUT_LAND : ScreenType.FOLD_OUT_PORTRAIT;
        }
        return z ? ScreenType.PHONE_LAND : ScreenType.PHONE;
    }

    public static int[] getScreenWH(Context context) {
        Display defaultDisplay;
        int[] iArr = new int[2];
        if (isScreenFake()) {
            Configuration configuration = context.getResources().getConfiguration();
            float f = configuration.densityDpi / 160.0f;
            iArr[0] = (int) (configuration.screenWidthDp * f);
            iArr[1] = (int) (configuration.screenHeightDp * f);
            return iArr;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        try {
            defaultDisplay = context.getDisplay();
        } catch (UnsupportedOperationException unused) {
            defaultDisplay = windowManager.getDefaultDisplay();
        }
        int[] cachedCurrentScreenSize = getCachedCurrentScreenSize(windowManager, defaultDisplay, context.getResources().getConfiguration());
        int i = cachedCurrentScreenSize[0];
        int i2 = cachedCurrentScreenSize[1];
        if (!FOLD_DEVICE && !PAD_DEVICE) {
            int i3 = context.getResources().getConfiguration().orientation;
            if (i3 == 2) {
                Log.w("ClockDeviceConfig", "getScreenWH: phone got abnormal orientation: " + i3 + ", forced portrait");
            }
            iArr[0] = Math.min(i, i2);
            iArr[1] = Math.max(i, i2);
            return iArr;
        }
        int rotation = defaultDisplay.getRotation();
        if (rotation == 1 || rotation == 3) {
            iArr[0] = Math.max(i, i2);
            iArr[1] = Math.min(i, i2);
        } else {
            iArr[0] = Math.min(i, i2);
            iArr[1] = Math.max(i, i2);
        }
        Log.d("ClockDeviceConfig", "getScreenWH: result width = " + iArr[0] + " , height = " + iArr[1]);
        return iArr;
    }

    public static boolean inFoldLargeScreen(Context context) {
        return context.getResources().getConfiguration().smallestScreenWidthDp >= 600;
    }

    public static boolean inLargeScreen(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    public static boolean isAOD(Context context) {
        return "com.miui.aod".equals(context.getPackageName());
    }

    public static int isBackgroundBlurEnabled(Context context) {
        if (!DateFormatUtils.isSystemUI(context)) {
            return Settings.Secure.getInt(context.getContentResolver(), "background_blur_enable", 0);
        }
        try {
            return ((Integer) getIntForUserMethod().invoke(null, context.getContentResolver(), "background_blur_enable", 0, Integer.valueOf(DateFormatUtils.updateCurrentUserId()))).intValue();
        } catch (Exception e) {
            Log.e("ClockDeviceConfig", "getIntForUser fail", e);
            return 0;
        }
    }

    public static boolean isComputilityV2High() {
        return COMPUTILITY_V2_LEVEL >= 4;
    }

    public static boolean isCupAndGupHighLevel() {
        return DeviceUtils.getDeviceLevel(DeviceUtils.DEV_STANDARD_VERSION, DeviceUtils.TYPE_CPU) == 2 && DeviceUtils.getDeviceLevel(DeviceUtils.DEV_STANDARD_VERSION, DeviceUtils.TYPE_GPU) == 2;
    }

    public static boolean isFlip() {
        if (sDisplayMode == -2) {
            sDisplayMode = getDeviceFormType();
        }
        return sDisplayMode == 4;
    }

    public static boolean isFoldDevice() {
        return FOLD_DEVICE;
    }

    public static boolean isGlassEffectEnable(Context context) {
        if (!supportGlassEffect()) {
            return false;
        }
        if (mBackgroundBlurEnabled == -1) {
            mBackgroundBlurEnabled = isBackgroundBlurEnabled(context);
        }
        return mBackgroundBlurEnabled == 1;
    }

    public static boolean isGlobalRom() {
        return miuix.os.Build.IS_INTERNATIONAL_BUILD;
    }

    public static boolean isGxzwLowPosition() {
        return "low".equals(GXZW_POSITION);
    }

    public static boolean isHighResolution() {
        return TextUtils.equals(getQHDMode(), "WQHD+");
    }

    public static boolean isHighTextContrastEnabled(Context context) {
        int iIntValue;
        if (!DateFormatUtils.isSystemUI(context)) {
            return Settings.Secure.getInt(context.getContentResolver(), "high_text_contrast_enabled", 0) == 1;
        }
        try {
            iIntValue = ((Integer) getIntForUserMethod().invoke(null, context.getContentResolver(), "high_text_contrast_enabled", 0, Integer.valueOf(DateFormatUtils.updateCurrentUserId()))).intValue();
        } catch (Exception e) {
            Log.e("ClockDeviceConfig", "getIntForUser fail", e);
            iIntValue = 0;
        }
        return iIntValue == 1;
    }

    public static boolean isJ18() {
        return TextUtils.equals(Build.DEVICE, "cetus");
    }

    public static boolean isO80() {
        return TextUtils.equals(Build.DEVICE, "jinghu");
    }

    public static boolean isPadDevice() {
        return PAD_DEVICE;
    }

    public static boolean isPhoneDevice() {
        return (PAD_DEVICE || FOLD_DEVICE) ? false : true;
    }

    public static boolean isPortraitDevice(Context context) {
        return context.getResources().getConfiguration().orientation == 1;
    }

    public static boolean isQ18() {
        return TextUtils.equals(Build.DEVICE, "lhasa");
    }

    public static boolean isRTL() {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
    }

    public static boolean isScreenFake() {
        Boolean bool = (Boolean) sScreenFake.get();
        return bool != null && bool.booleanValue();
    }

    public static float pxToDp(float f, Context context) {
        return (f / calculateScale(context)) / context.getResources().getDisplayMetrics().density;
    }

    public static void setBackgroundBlurEnabled(int i) {
        mBackgroundBlurEnabled = i;
    }

    public static void setScreenFake(boolean z) {
        sScreenFake.set(Boolean.valueOf(z));
    }

    public static float simpleDpToPx(float f, Context context) {
        return f * context.getResources().getDisplayMetrics().density;
    }

    public static boolean supportAdvanceVisualEffect(Context context) {
        if (mBackgroundBlurEnabled == -1) {
            mBackgroundBlurEnabled = isBackgroundBlurEnabled(context);
        }
        return supportDiffEffectAndGradientEffect() && mBackgroundBlurEnabled == 1;
    }

    public static boolean supportBackgroundBlur(Context context) {
        if (mBackgroundBlurEnabled == -1) {
            mBackgroundBlurEnabled = isBackgroundBlurEnabled(context);
        }
        return SUPPORT_BACKGROUND_BLUR && mBackgroundBlurEnabled == 1;
    }

    public static boolean supportBlurMode1000() {
        return BACKGROUND_BLUR_VERSION >= 2 || OS2_ADVANCED_VISUAL_VERSION >= 2;
    }

    public static boolean supportDiffEffect() {
        return supportDiffEffectAndGradientEffect();
    }

    public static boolean supportDiffEffectAndGradientEffect() {
        return SUPPORT_BACKGROUND_BLUR && OS2_ADVANCED_VISUAL_VERSION >= 3 && BACKGROUND_BLUR_STATUS_DEFAULT;
    }

    public static boolean supportGlassEffect() {
        return isComputilityV2High() && SUPPORT_BACKGROUND_BLUR && OS2_ADVANCED_VISUAL_VERSION >= 4 && BACKGROUND_BLUR_STATUS_DEFAULT;
    }

    public static boolean supportGlowEffect() {
        return false;
    }

    public static boolean supportGradientEffect() {
        return supportDiffEffectAndGradientEffect();
    }

    public static boolean supportVariableFont() {
        return isComputilityV2High();
    }

    public static void updateBackgroundBlurEnabled() {
        mBackgroundBlurEnabled = -1;
    }
}
