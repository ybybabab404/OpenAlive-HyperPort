package com.miui.miwallpaper.material.utils;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.miwallpaper.material.utils.clockdesign.ClockDesignInfo;
import com.miui.miwallpaper.material.utils.clockdesign.RectCalculable;
import com.miui.miwallpaper.util.EffectSystemSettingUtils;
import com.miui.miwallpaper.util.SystemUtil;
import com.xiaomi.onetrack.util.ac;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import miui.os.Build;
import miui.util.MiuiMultiDisplayTypeInfo;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class PartRectColorUtils {
    public static final boolean IS_FOLDABLE_DEVICE;
    public static final AtomicReference mStatusBarRect = new AtomicReference();
    public static final AtomicReference mClockStyleRect = new AtomicReference();
    public static final AtomicReference mFingerprintRect = new AtomicReference();
    public static final AtomicReference mBottomIconRect = new AtomicReference();
    public static final AtomicReference mMagazineScriptRect = new AtomicReference();
    public static final AtomicReference mOversizeCapsuleRect = new AtomicReference();
    public static final AtomicReference mFullImageRect = new AtomicReference();
    public static final AtomicReference mStatusBarRectLandscape = new AtomicReference();
    public static final AtomicReference mClockStyleRectLandscape = new AtomicReference();
    public static final AtomicReference mFingerprintRectLandscape = new AtomicReference();
    public static final AtomicReference mBottomIconRectLandscape = new AtomicReference();
    public static final AtomicReference mMagazineScriptRectLandscape = new AtomicReference();
    public static final AtomicReference mOversizeCapsuleLandscape = new AtomicReference();

    static {
        IS_FOLDABLE_DEVICE = isFoldDevices() || isFlipDevice();
    }

    public static int clamp(int i, int i2, int i3) {
        return Math.max(i2, Math.min(i3, i));
    }

    public static void computeCommonClockStyleRect(boolean z, float f, float f2, float f3, float f4, float f5, float f6) {
        computeGlobalColor(z ? "clock_style_rect_landscape" : "clock_style_rect", f, f2, f3, f4);
    }

    public static void computeCommonStatusBarRect(boolean z, float f, float f2, float f3, float f4, float f5, float f6, ClockDesignInfo.StatusBarDesign statusBarDesign) {
        int i = (int) (f3 / 2.0f);
        int i2 = (int) (f4 / 2.0f);
        int i3 = (int) (((f2 - f4) * statusBarDesign.height) / f6);
        int i4 = (int) f;
        Rect rect = new Rect(i, 0, i4 - i, i3);
        if (f3 >= 1.0f) {
            rect.set(i, 0, (int) (f - i), i3);
        } else if (f4 > 1.0f) {
            rect.set(0, i2, i4, i3 + i2);
        }
        if (z) {
            mStatusBarRectLandscape.set(rect);
        } else {
            mStatusBarRect.set(rect);
        }
    }

    public static void computeFlipSmallScreenRect(Context context, int i, String str, int[] iArr, float f, float f2, float f3, float f4, float f5, float f6, Map map) {
        int[] foldDisplaySize = getFoldDisplaySize(context, false);
        if (foldDisplaySize != null) {
            f3 = foldDisplaySize[0];
            f4 = foldDisplaySize[1];
        }
        float[] fArrComputeOffset = computeOffset(f, f2, f3, f4);
        computeCommonStatusBarRect(false, f, f2, fArrComputeOffset[0], fArrComputeOffset[1], f5, f6, ClockDesignInfo.STATUS_BAR_DESIGN_FLIP_SMALL_SCREEN);
    }

    public static void computeFoldSmallScreenRect(Context context, int i, String str, int[] iArr, float f, float f2, float f3, float f4, float f5, float f6, Map map) {
        float f7;
        float f8;
        int[] foldDisplaySize = getFoldDisplaySize(context, false);
        if (foldDisplaySize != null) {
            f7 = foldDisplaySize[0];
            f8 = foldDisplaySize[1];
        } else {
            f7 = f3;
            f8 = f4;
        }
        computeRect(false, i, context, str, iArr, f, f2, computeOffset(f, f2, f7, f8), f5, f6);
        map.put("status_bar_rect_small_screen", getCopyOfRect(mStatusBarRect));
        map.put("clock_style_rect_small_screen", getCopyOfRect(mClockStyleRect));
        map.put("finger_print_rect_small_screen", getCopyOfRect(mFingerprintRect));
        map.put("bottom_icon_rect_small_screen", getCopyOfRect(mBottomIconRect));
        map.put("magazine_script_rect_small_screen", getCopyOfRect(mMagazineScriptRect));
        map.put("oversize_capsule_rect_small_screen", getCopyOfRect(mOversizeCapsuleRect));
    }

    public static void computeFullImageRect(float f, float f2, float f3, float f4) {
        computeGlobalColor("full_image_rect", f, f2, f3, f4);
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0045  */
    public static void computeGlobalColor(String str, float f, float f2, float f3, float f4) {
        byte b;
        int i = (int) (f3 / 2.0f);
        int i2 = (int) (f4 / 2.0f);
        int i3 = (int) f;
        int i4 = (int) f2;
        Rect rect = new Rect(i, i2, i3 - i, i4 - i2);
        int iHashCode = str.hashCode();
        if (iHashCode != -800661373) {
            if (iHashCode != -790602248) {
                if (iHashCode == -88259073 && str.equals("clock_style_rect_landscape")) {
                    b = 1;
                } else {
                    b = -1;
                }
            } else if (str.equals("full_image_rect")) {
                b = 2;
            } else {
                b = -1;
            }
        } else if (str.equals("clock_style_rect")) {
            b = 0;
        } else {
            b = -1;
        }
        if (b == 0) {
            mClockStyleRect.set(rect);
        } else if (b != 1) {
            mFullImageRect.set(new Rect(0, 0, i3, i4));
        } else {
            mClockStyleRectLandscape.set(rect);
        }
    }

    public static void computeIconAndFingerPrintRect(boolean z, float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = (int) (((f2 - f4) * ClockDesignInfo.CLASSIC_CLOCK_DESIGN.height) / f6);
        int i = (int) (f2 - f7);
        int i2 = (int) f;
        int i3 = (int) f2;
        Rect rect = new Rect(0, i, i2, i3);
        if (f3 >= 1.0f) {
            int i4 = ((int) f3) / 2;
            rect.set(i4, i, (int) (f - i4), i3);
        } else if (f4 > 1.0f) {
            float f8 = f2 - ((int) (f4 / 2.0f));
            rect.set(0, (int) (f8 - f7), i2, (int) f8);
        }
        if (z) {
            mBottomIconRectLandscape.set(rect);
            mFingerprintRectLandscape.set(rect);
        } else {
            mBottomIconRect.set(rect);
            mFingerprintRect.set(rect);
        }
    }

    public static void computeLandscapeRect(Context context, int i, String str, int[] iArr, float f, float f2, float f3, float f4, float f5, float f6, Map map) {
        float[] fArrComputeOffset = computeOffset(f, f2, f3, f4);
        initLandscapeRect();
        computeRect(true, i, context, str, iArr, f, f2, fArrComputeOffset, f5, f6);
        map.put("status_bar_rect_landscape", getCopyOfRect(mStatusBarRectLandscape));
        map.put("clock_style_rect_landscape", getCopyOfRect(mClockStyleRectLandscape));
        map.put("finger_print_rect_landscape", getCopyOfRect(mFingerprintRectLandscape));
        map.put("bottom_icon_rect_landscape", getCopyOfRect(mBottomIconRectLandscape));
        map.put("magazine_script_rect_landscape", getCopyOfRect(mMagazineScriptRectLandscape));
        map.put("oversize_capsule_rect_landscape", getCopyOfRect(mOversizeCapsuleLandscape));
    }

    public static float[] computeOffset(float f, float f2, float f3, float f4) {
        float fMin = Math.min(f / f3, f2 / f4);
        float fAbs = Math.abs(f3 - (f / fMin)) * fMin;
        float fAbs2 = Math.abs(f4 - (f2 / fMin)) * fMin;
        Log.d("PartRectColorUtils", "computeOffset, offsetX=" + fAbs + ", offsetY=" + fAbs2);
        return new float[]{fAbs, fAbs2};
    }

    public static void computeRect(boolean z, int i, Context context, String str, int[] iArr, float f, float f2, float[] fArr, float f3, float f4) {
        RectCalculable.fromType(str).computeImageRect(z, i, context, str, iArr, f, f2, fArr[0], fArr[1], f3, f4);
    }

    public static void computeStatusBarRect(boolean z, boolean z2, float f, float f2, float f3, float f4, float f5, float f6, Map map, ClockDesignInfo.StatusBarDesign statusBarDesign) {
        if (!z2) {
            float[] fArrComputeOffset = computeOffset(f, f2, f3, f4);
            computeCommonStatusBarRect(false, f, f2, fArrComputeOffset[0], fArrComputeOffset[1], f5, f6, statusBarDesign);
        } else if (!z) {
            float[] fArrComputeOffset2 = computeOffset(f, f2, f3, f4);
            computeCommonStatusBarRect(z, f, f2, fArrComputeOffset2[0], fArrComputeOffset2[1], f5, f6, statusBarDesign);
        } else {
            float[] fArrComputeOffset3 = computeOffset(f, f2, f3, f4);
            mStatusBarRectLandscape.set(new Rect());
            computeCommonStatusBarRect(z, f, f2, fArrComputeOffset3[0], fArrComputeOffset3[1], f5, f6, statusBarDesign);
        }
    }

    public static Map convertScreenRectsToBitmapRects(Context context, Map map, float f, float f2, int i) {
        float[] screenSizeForWhich = getScreenSizeForWhich(context, i);
        float f3 = screenSizeForWhich[0];
        float f4 = screenSizeForWhich[1];
        HashMap map2 = new HashMap();
        for (Map.Entry entry : map.entrySet()) {
            Rect rect = (Rect) entry.getValue();
            if (rect == null || rect.isEmpty()) {
                map2.put((String) entry.getKey(), new Rect());
            } else {
                boolean zIsScreenOrientationLandscape = EffectSystemSettingUtils.isScreenOrientationLandscape(context);
                map2.put((String) entry.getKey(), screenRectToBitmapRect(rect, zIsScreenOrientationLandscape ? f4 : f3, zIsScreenOrientationLandscape ? f3 : f4, f, f2));
            }
        }
        Log.d("PartRectColorUtils", "convertScreenRectsToBitmapRects: which=" + i + " screen=[" + f3 + AnimatedProperty.PROPERTY_NAME_X + f4 + "] bitmap=[" + f + AnimatedProperty.PROPERTY_NAME_X + f2 + "] result=" + map2);
        return map2;
    }

    public static Rect getCopyOfRect(AtomicReference atomicReference) {
        Rect rect = (Rect) atomicReference.get();
        return rect != null ? new Rect(rect) : new Rect();
    }

    public static int[] getCurrentLockScreenInfo(Context context) {
        int i;
        int i2;
        JSONObject jSONObjectOptJSONObject;
        JSONObject jSONObjectOptJSONObject2;
        boolean zIsOS3KeyguardEditorAtLeast = SystemUtil.isOS3KeyguardEditorAtLeast(context);
        String string = Settings.Secure.getString(context.getContentResolver(), zIsOS3KeyguardEditorAtLeast ? "constant_template_editor_info" : "constant_lockscreen_info");
        if (string != null) {
            try {
                if (zIsOS3KeyguardEditorAtLeast) {
                    JSONObject jSONObjectOptJSONObject3 = new JSONObject(string).optJSONObject("lockscreenInfo");
                    if (jSONObjectOptJSONObject3 != null) {
                        jSONObjectOptJSONObject = jSONObjectOptJSONObject3.optJSONObject("clockInfo");
                        jSONObjectOptJSONObject2 = jSONObjectOptJSONObject3.optJSONObject("signatureInfo");
                    } else {
                        jSONObjectOptJSONObject = null;
                        jSONObjectOptJSONObject2 = null;
                    }
                    i = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.getInt("style") : -1;
                    i2 = jSONObjectOptJSONObject2 != null ? jSONObjectOptJSONObject2.getInt("alignment") : -1;
                } else {
                    JSONObject jSONObject = new JSONObject(string);
                    JSONObject jSONObjectOptJSONObject4 = jSONObject.optJSONObject("clockInfo");
                    int i3 = jSONObjectOptJSONObject4 != null ? jSONObjectOptJSONObject4.getInt("style") : -1;
                    JSONObject jSONObjectOptJSONObject5 = jSONObject.optJSONObject("signatureInfo");
                    if (jSONObjectOptJSONObject5 != null) {
                        int i4 = jSONObjectOptJSONObject5.getInt("alignment");
                        i = i3;
                        i2 = i4;
                    } else {
                        i = i3;
                    }
                }
                Log.d("PartRectColorUtils", "getCurrentLockScreenInfo, clockStyle = " + i + ", alignment = " + i2);
                return new int[]{i, i2};
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new int[]{-1, -1};
    }

    public static int[] getFoldDisplaySize(Context context, boolean z) {
        Display display;
        Display[] displays = ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplays("android.hardware.display.category.ALL_INCLUDING_DISABLED");
        if (displays.length != 2 || (display = displays[0]) == null || displays[1] == null) {
            return null;
        }
        Display display2 = display.getMode().getPhysicalWidth() < displays[1].getMode().getPhysicalWidth() ? displays[0] : displays[1];
        Display display3 = displays[0].getMode().getPhysicalWidth() < displays[1].getMode().getPhysicalWidth() ? displays[1] : displays[0];
        if (z) {
            display2 = display3;
        }
        return new int[]{display2.getMode().getPhysicalWidth(), display2.getMode().getPhysicalHeight()};
    }

    public static Map getPartScreenRectUtils(Context context, String str, int[] iArr, float f, float f2, int i) {
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        Context context2 = context;
        Log.d("PartRectColorUtils", "getPartScreenRectUtils, clockStyleType=" + str + " clockTypeInfo=" + Arrays.toString(iArr));
        Rect bounds = ((WindowManager) context2.getSystemService(WindowManager.class)).getMaximumWindowMetrics().getBounds();
        float fMin = (float) Math.min(bounds.width(), bounds.height());
        float fMax = (float) Math.max(bounds.width(), bounds.height());
        HashMap map = new HashMap();
        float[] fArrComputeOffset = computeOffset(f, f2, fMin, fMax);
        initRect();
        boolean z = IS_FOLDABLE_DEVICE;
        float f8 = 392.0f;
        float f9 = 871.0f;
        if (z) {
            int[] foldDisplaySize = getFoldDisplaySize(context2, true);
            if (foldDisplaySize != null) {
                fArrComputeOffset = computeOffset(f, f2, foldDisplaySize[0], foldDisplaySize[1]);
                fMin = foldDisplaySize[0];
                fMax = foldDisplaySize[1];
            }
            if (isFoldDevices()) {
                f8 = 696.0f;
                f9 = 785.0f;
            }
        }
        float[] fArr = fArrComputeOffset;
        HashMap map2 = map;
        float f10 = f9;
        float f11 = fMin;
        float f12 = f8;
        float f13 = fMax;
        boolean z2 = i == 1 || i == 2;
        String str2 = "status_bar_rect";
        if (i == 2 || i == 8) {
            if (!z) {
                f10 = f10;
                f3 = f12;
                str2 = "status_bar_rect";
                if (isPad()) {
                    computeLandscapeRect(context, 4, str, iArr, f, f2, f13, f11, 1137.0f, 711.0f, map2);
                    f5 = f13;
                    f4 = f11;
                    f10 = 1137.0f;
                    f3 = 711.0f;
                    computeRect(false, 4, context, str, iArr, f, f2, fArr, 711.0f, 1137.0f);
                } else {
                    f4 = f11;
                    f5 = f13;
                    computeRect(false, 1, context, str, iArr, f, f2, fArr, f3, f10);
                    context2 = context;
                }
                float f14 = f10;
                f12 = f3;
                f10 = f14;
                map2.put(str2, getCopyOfRect(mStatusBarRect));
                map2.put("clock_style_rect", getCopyOfRect(mClockStyleRect));
                map2.put("finger_print_rect", getCopyOfRect(mFingerprintRect));
                map2.put("bottom_icon_rect", getCopyOfRect(mBottomIconRect));
                map2.put("magazine_script_rect", getCopyOfRect(mMagazineScriptRect));
                map2.put("oversize_capsule_rect", getCopyOfRect(mOversizeCapsuleRect));
                map2.put("full_image_rect", getCopyOfRect(mFullImageRect));
                Log.d("PartRectColorUtils", "getPartScreenRectUtils: " + map2);
            } else {
                if (!z2) {
                    if (isFoldDevices()) {
                        computeFoldSmallScreenRect(context, 3, str, iArr, f, f2, f11, f13, 392.0f, 871.0f, map2);
                    }
                    Log.d("PartRectColorUtils", "getPartScreenRectUtils: small rect " + map2);
                    return map2;
                }
                if (isFlipDevice()) {
                    f3 = f12;
                    f5 = f13;
                    f4 = f11;
                } else {
                    computeLandscapeRect(context2, 2, str, iArr, f, f2, f13, f11, f10, f12, map2);
                    f3 = f12;
                    f5 = f13;
                    f4 = f11;
                }
                computeRect(false, 2, context, str, iArr, f, f2, fArr, f3, f10);
            }
            context2 = context;
            float f15 = f10;
            f12 = f3;
            f10 = f15;
            map2.put(str2, getCopyOfRect(mStatusBarRect));
            map2.put("clock_style_rect", getCopyOfRect(mClockStyleRect));
            map2.put("finger_print_rect", getCopyOfRect(mFingerprintRect));
            map2.put("bottom_icon_rect", getCopyOfRect(mBottomIconRect));
            map2.put("magazine_script_rect", getCopyOfRect(mMagazineScriptRect));
            map2.put("oversize_capsule_rect", getCopyOfRect(mOversizeCapsuleRect));
            map2.put("full_image_rect", getCopyOfRect(mFullImageRect));
            Log.d("PartRectColorUtils", "getPartScreenRectUtils: " + map2);
        } else {
            f5 = f13;
            f4 = f11;
            str2 = "status_bar_rect";
        }
        float f16 = f10;
        float f17 = f12;
        if (i != 1 && i != 4) {
            return map2;
        }
        if (!z) {
            float f18 = f5;
            float f19 = f4;
            if (isPad()) {
                ClockDesignInfo.StatusBarDesign statusBarDesign = ClockDesignInfo.STATUS_BAR_DESIGN;
                computeStatusBarRect(true, true, f, f2, f18, f19, 1137.0f, 711.0f, map2, statusBarDesign);
                map2 = map2;
                computeStatusBarRect(false, true, f, f2, f19, f18, 711.0f, 1137.0f, map2, statusBarDesign);
            } else {
                computeStatusBarRect(false, true, f, f2, f19, f18, f17, f16, map2, ClockDesignInfo.STATUS_BAR_DESIGN);
            }
        } else {
            if (!z2) {
                float f20 = f5;
                float f21 = f4;
                if (isFoldDevices()) {
                    int[] foldDisplaySize2 = getFoldDisplaySize(context2, false);
                    if (foldDisplaySize2 != null) {
                        float f22 = foldDisplaySize2[0];
                        f20 = foldDisplaySize2[1];
                        f21 = f22;
                    }
                    computeStatusBarRect(true, false, f, f2, f21, f20, 392.0f, 871.0f, map2, ClockDesignInfo.STATUS_BAR_DESIGN);
                } else {
                    computeFlipSmallScreenRect(context2, 3, str, iArr, f, f2, f21, f20, 372.0f, 428.0f, map2);
                }
                map2.put("status_bar_rect_small_screen", getCopyOfRect(mStatusBarRect));
                return map2;
            }
            if (isFlipDevice()) {
                f6 = f5;
                f7 = f4;
            } else {
                float f23 = f5;
                float f24 = f4;
                computeStatusBarRect(true, true, f, f2, f23, f24, f16, f17, map2, ClockDesignInfo.STATUS_BAR_DESIGN);
                f6 = f23;
                f7 = f24;
                f16 = f16;
                f17 = f17;
                map2 = map2;
            }
            computeStatusBarRect(false, true, f, f2, f7, f6, f17, f16, map2, ClockDesignInfo.STATUS_BAR_DESIGN);
        }
        if (isFoldDevices()) {
            map2.put("status_bar_rect_landscape", getCopyOfRect(mStatusBarRectLandscape));
        }
        if (isPad()) {
            map2.put("status_bar_rect_landscape", getCopyOfRect(mStatusBarRectLandscape));
        }
        map2.put(str2, getCopyOfRect(mStatusBarRect));
        return map2;
    }

    public static float[] getScreenSizeForWhich(Context context, int i) {
        int[] foldDisplaySize;
        boolean z = i == 4 || i == 8;
        if (IS_FOLDABLE_DEVICE && (foldDisplaySize = getFoldDisplaySize(context, !z)) != null) {
            return new float[]{Math.min(foldDisplaySize[0], foldDisplaySize[1]), Math.max(foldDisplaySize[0], foldDisplaySize[1])};
        }
        Rect bounds = ((WindowManager) context.getSystemService(WindowManager.class)).getMaximumWindowMetrics().getBounds();
        return new float[]{Math.min(bounds.width(), bounds.height()), Math.max(bounds.width(), bounds.height())};
    }

    public static int getWallpaperEffectType(Context context, int i) {
        return Settings.Secure.getInt(context.getContentResolver(), "wallpaper_effect_type_" + i, 0);
    }

    public static void initLandscapeRect() {
        mStatusBarRectLandscape.set(new Rect());
        mClockStyleRectLandscape.set(new Rect());
        mFingerprintRectLandscape.set(new Rect());
        mBottomIconRectLandscape.set(new Rect());
        mMagazineScriptRectLandscape.set(new Rect());
        mOversizeCapsuleLandscape.set(new Rect());
    }

    public static void initRect() {
        mStatusBarRect.set(new Rect());
        mClockStyleRect.set(new Rect());
        mFingerprintRect.set(new Rect());
        mBottomIconRect.set(new Rect());
        mMagazineScriptRect.set(new Rect());
        mOversizeCapsuleRect.set(new Rect());
    }

    public static boolean isFlipDevice() {
        try {
            Object objInvoke = MiuiMultiDisplayTypeInfo.class.getMethod("isFlipDevice", new Class[0]).invoke(MiuiMultiDisplayTypeInfo.class, new Object[0]);
            if (objInvoke instanceof Boolean) {
                return ((Boolean) objInvoke).booleanValue();
            }
        } catch (Exception e) {
            Log.e("PartRectColorUtils", "isFlipDevice fail", e);
        }
        return false;
    }

    public static boolean isFoldDevices() {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return ((Integer) cls.getMethod("getInt", String.class, Integer.TYPE).invoke(cls, "persist.sys.muiltdisplay_type", 0)).intValue() == 2;
        } catch (Throwable th) {
            Log.e("PartRectColorUtils", "isFoldDevices fail : ", th);
            return false;
        }
    }

    public static boolean isPad() {
        return Build.IS_TABLET;
    }

    public static Rect screenRectToBitmapRect(Rect rect, float f, float f2, float f3, float f4) {
        if (f <= 0.0f || f2 <= 0.0f || f3 <= 0.0f || f4 <= 0.0f) {
            Log.w("PartRectColorUtils", "screenRectToBitmapRect: invalid dimensions, screen=[" + f + AnimatedProperty.PROPERTY_NAME_X + f2 + "] bitmap=[" + f3 + AnimatedProperty.PROPERTY_NAME_X + f4 + "]");
            return new Rect(0, 0, (int) f3, (int) f4);
        }
        Log.d("PartRectColorUtils", "screenRectToBitmapRect: screen=[" + f + AnimatedProperty.PROPERTY_NAME_X + f2 + "] bitmap=[" + f3 + AnimatedProperty.PROPERTY_NAME_X + f4 + "]");
        float fMax = Math.max(f / f3, f2 / f4);
        float f5 = ((f3 * fMax) - f) / 2.0f;
        float f6 = ((f4 * fMax) - f2) / 2.0f;
        int iRound = Math.round((((float) rect.left) + f5) / fMax);
        int iRound2 = Math.round((((float) rect.top) + f6) / fMax);
        int iRound3 = Math.round((((float) rect.right) + f5) / fMax);
        int iRound4 = Math.round((((float) rect.bottom) + f6) / fMax);
        int i = (int) f3;
        int i2 = (int) f4;
        int iClamp = clamp(iRound, 0, i);
        int iClamp2 = clamp(iRound2, 0, i2);
        int iClamp3 = clamp(iRound3, 0, i);
        int iClamp4 = clamp(iRound4, 0, i2);
        if (iClamp < iClamp3 && iClamp2 < iClamp4) {
            return new Rect(iClamp, iClamp2, iClamp3, iClamp4);
        }
        Log.w("PartRectColorUtils", "screenRectToBitmapRect: converted rect is empty, fallback to full bitmap. screenRect=" + rect + " bitmapRect=[" + iClamp + ac.b + iClamp2 + ac.b + iClamp3 + ac.b + iClamp4 + "]");
        return new Rect(0, 0, i, i2);
    }

    public static void setClockStyleRect(boolean z, Rect rect) {
        if (z) {
            mClockStyleRectLandscape.set(rect);
        } else {
            mClockStyleRect.set(rect);
        }
    }

    public static void setMagazineScriptRect(boolean z, Rect rect) {
        if (z) {
            mMagazineScriptRectLandscape.set(rect);
        } else {
            mMagazineScriptRect.set(rect);
        }
    }

    public static void setOversizeCapsuleRect(boolean z, Rect rect) {
        if (z) {
            mOversizeCapsuleLandscape.set(rect);
        } else {
            mOversizeCapsuleRect.set(rect);
        }
    }
}
