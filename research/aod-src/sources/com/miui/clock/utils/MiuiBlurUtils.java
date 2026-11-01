package com.miui.clock.utils;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import com.miui.clock.module.Effect;
import com.miui.maml.R;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import miuix.core.util.SystemProperties;

/* JADX INFO: loaded from: classes.dex */
public class MiuiBlurUtils {
    public static final Method M_ADD_MI_BACKGROUND_BLEND_COLOR;
    public static final Method M_CHOOSE_BACKGROUND_BLUR_CONTAINER;
    public static final Method M_CLEAR_MI_BACKGROUND_BLEND_COLOR;
    public static final Method M_DISABLE_MI_BACKGROUND_CONTAIN_BELOW;
    public static final Method M_SET_ATLAS_MAX_PATH_SIZE;
    public static final Method M_SET_GLASS_EFFECT;
    public static final Method M_SET_GLOW_EFFECT_6;
    public static final Method M_SET_GLOW_EFFECT_8;
    public static final Method M_SET_MIX_EFFECT_ENABLED;
    public static final Method M_SET_MI_BACKGROUND_BLEND_COLORS;
    public static final Method M_SET_MI_BACKGROUND_BLUR_MODE;
    public static final Method M_SET_MI_BACKGROUND_BLUR_RADIUS;
    public static final Method M_SET_MI_CUSTOM_SURFACE_COLOR_TYPE;
    public static final Method M_SET_MI_GLASS;
    public static final Method M_SET_MI_GLASS_BLUR_RADIUS;
    public static final Method M_SET_MI_GLASS_CLIP;
    public static final Method M_SET_MI_SELF_BLUR_TYPE;
    public static final Method M_SET_MI_VIEW_BLUR_MODE;
    public static final Method M_SET_MI_VIEW_MATERIAL_TYPE;
    public static final Method M_SET_PASS_TEXTURE_SCALE;
    public static final Method M_SET_PASS_WINDOW_BLUR_ENABLED;
    public static final Method M_SET_SELF_GRADIENT_BLUR_PARAMS;
    public static final Method M_SIMPLIFY_PATH;
    public static volatile boolean sDynamicLogEnabled;

    static {
        sDynamicLogEnabled = SystemProperties.getInt("persist.sys.sf.dynamic.log", 0) == 4;
        Class cls = Boolean.TYPE;
        M_SET_PASS_WINDOW_BLUR_ENABLED = resolveViewMethod("setPassWindowBlurEnabled", cls);
        Class cls2 = Integer.TYPE;
        M_SET_MI_BACKGROUND_BLUR_MODE = resolveViewMethod("setMiBackgroundBlurMode", cls2);
        M_SET_MI_BACKGROUND_BLUR_RADIUS = resolveViewMethod("setMiBackgroundBlurRadius", cls2);
        M_ADD_MI_BACKGROUND_BLEND_COLOR = resolveViewMethod("addMiBackgroundBlendColor", cls2, cls2);
        M_SET_MI_BACKGROUND_BLEND_COLORS = resolveViewMethod("setMiBackgroundBlendColors", ArrayList.class);
        M_SET_MIX_EFFECT_ENABLED = resolveViewMethod("setMixEffectEnabled", cls);
        M_CLEAR_MI_BACKGROUND_BLEND_COLOR = resolveViewMethod("clearMiBackgroundBlendColor", new Class[0]);
        M_SET_MI_VIEW_BLUR_MODE = resolveViewMethod("setMiViewBlurMode", cls2);
        M_DISABLE_MI_BACKGROUND_CONTAIN_BELOW = resolveViewMethod("disableMiBackgroundContainBelow", cls);
        M_CHOOSE_BACKGROUND_BLUR_CONTAINER = resolveViewMethod("chooseBackgroundBlurContainer", View.class);
        M_SET_MI_SELF_BLUR_TYPE = resolveViewMethod("setMiSelfBlurType", cls2);
        M_SET_SELF_GRADIENT_BLUR_PARAMS = resolveViewMethod("setSelfGradientBlurParams", float[].class, cls2);
        Class cls3 = Float.TYPE;
        M_SET_GLOW_EFFECT_8 = resolveViewMethod("setGlowEffect", cls2, cls2, cls2, cls3, cls3, cls3, cls2, cls2);
        M_SET_GLOW_EFFECT_6 = resolveViewMethod("setGlowEffect", cls2, cls2, cls2, cls3, cls3, cls3);
        M_SET_PASS_TEXTURE_SCALE = resolveViewMethod("setPassTextureScale", cls3);
        M_SET_MI_CUSTOM_SURFACE_COLOR_TYPE = resolveViewMethod("setMiCustomSurfaceColorType", cls2);
        M_SET_MI_GLASS_CLIP = resolveViewMethod("setMiGlassClip", cls3, cls3, cls3, cls3);
        M_SET_MI_GLASS_BLUR_RADIUS = resolveViewMethod("setMiGlassBlurRadius", cls2, cls2);
        M_SET_MI_VIEW_MATERIAL_TYPE = resolveViewMethod("setMiViewMaterialType", cls2);
        M_SET_MI_GLASS = resolveViewMethod("setMiGlass", float[].class);
        M_SET_ATLAS_MAX_PATH_SIZE = resolvePaintMethod("setAtlasMaxPathSize", cls2, cls2);
        M_SET_GLASS_EFFECT = resolvePaintMethod("setGlassEffect", cls);
        M_SIMPLIFY_PATH = resolvePaintMethod("simplifyPath", Path.class);
    }

    public static void DYNAMIC_LOG_D(String str, String str2) {
        if (sDynamicLogEnabled) {
            Log.d(str, str2);
        }
    }

    public static void addMiBackgroundBlendColor(View view, int i, int i2) {
        Method method = M_ADD_MI_BACKGROUND_BLEND_COLOR;
        if (method == null) {
            return;
        }
        try {
            method.invoke(view, Integer.valueOf(i), Integer.valueOf(i2));
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "addMiBackgroundBlendColor", e);
        }
    }

    public static void chooseBackgroundBlurContainer(View view, View view2) {
        Method method = M_CHOOSE_BACKGROUND_BLUR_CONTAINER;
        if (method == null) {
            return;
        }
        try {
            method.invoke(view, view2);
            Log.d("MiuiBlurUtils", "chooseBackgroundBlurContainer: successfully");
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "chooseBackgroundBlurContainer: ");
            e.printStackTrace();
        }
    }

    public static void clearContainerPassBlur(View view) {
        DYNAMIC_LOG_D("MiuiBlurUtils", "clearContainerPassBlur " + view);
        if (DeviceConfig.BACKGROUND_BLUR_SUPPORTED_2) {
            if (view == null) {
                Log.d("MiuiBlurUtils", "clearContainerMiBackgroundBlur view is null");
                return;
            }
            try {
                setMiBackgroundBlurMode(view, 0);
                setMiBackgroundBlurRadius(view, 0);
                DYNAMIC_LOG_D("MiuiBlurUtils", "clearContainerPassBlur result :" + setPassWindowBlurEnabled(view, false) + ", view: " + view);
            } catch (Exception e) {
                Log.e("MiuiBlurUtils", "clearContainerMiBackgroundBlur error , view :" + view);
                e.printStackTrace();
            }
        }
    }

    public static void clearDiffEffectContainer(View view) {
        DYNAMIC_LOG_D("MiuiBlurUtils", "clearDiffEffectContainer " + view);
        if (view == null || !DeviceConfig.SUPPORT_BACKGROUND_BLUR) {
            Log.d("MiuiBlurUtils", "setDiffEffectMethod view is null");
            return;
        }
        try {
            setMiBackgroundBlurMode(view, 0);
            setPassWindowBlurEnabled(view, false);
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "setDiffEffectMethod error , view :" + view);
            e.printStackTrace();
        }
    }

    public static void clearDiffEffectMethod(View view) {
        DYNAMIC_LOG_D("MiuiBlurUtils", "clearDiffEffectMethod " + view);
        if (view == null || !DeviceConfig.SUPPORT_BACKGROUND_BLUR) {
            Log.d("MiuiBlurUtils", "setDiffEffectMethod view is null");
            return;
        }
        try {
            setMiViewBlurMode(view, 0);
            clearMiBackgroundBlendColors(view);
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "clearDiffEffectMethod error , view :" + view);
            e.printStackTrace();
        }
    }

    public static void clearGlassBlurContainer(View view) {
        DYNAMIC_LOG_D("MiuiBlurUtils", "clearGlassBlurContainer " + view);
        if (!DeviceConfig.BACKGROUND_BLUR_SUPPORTED_2) {
            Log.d("MiuiBlurUtils", "clearGlassBlurContainer not support blur");
            return;
        }
        if (view == null) {
            Log.d("MiuiBlurUtils", "clearGlassBlurContainer view is null");
            return;
        }
        try {
            setMiBackgroundBlurMode(view, 0);
            setPassWindowBlurEnabled(view, false);
            Log.e("MiuiBlurUtils", "clearGlassBlurContainer  , view :" + view);
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "clearGlassBlurContainer error , view :" + view);
            e.printStackTrace();
        }
    }

    public static void clearGlassEffectMethod(View view) {
        DYNAMIC_LOG_D("MiuiBlurUtils", "clearGlassEffectMethod " + view);
        if (view == null || !DeviceConfig.SUPPORT_BACKGROUND_BLUR) {
            Log.d("MiuiBlurUtils", "clearGlassEffectMethod view is null");
            return;
        }
        try {
            setMiViewMaterialType(view, 0);
            setMiGlass(view, new float[42]);
            setMiViewBlurMode(view, 0);
            setMiGlassBlurRadius(view, 0, 0);
            setMiCustomSurfaceColorType(view, 4);
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "clearGlassEffectMethod error , view :" + view);
            e.printStackTrace();
        }
    }

    public static void clearGradientBlurMethod(View view) {
        Method method;
        if (view == null || !DeviceConfig.SUPPORT_BACKGROUND_BLUR) {
            return;
        }
        DYNAMIC_LOG_D("MiuiBlurUtils", "clearGradientBlurMethod " + view);
        float[] fArr = Effect.EMPTY_GRADIENT;
        Method method2 = M_SET_MI_SELF_BLUR_TYPE;
        if (method2 == null || (method = M_SET_SELF_GRADIENT_BLUR_PARAMS) == null) {
            return;
        }
        try {
            method2.invoke(view, 0);
            method.invoke(view, fArr, 2);
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "clearGradientBlurMethod", e);
        }
    }

    public static void clearMemberBlendColor(View view) {
        if (DeviceConfig.BACKGROUND_BLUR_SUPPORTED_2) {
            if (view == null) {
                Log.d("MiuiBlurUtils", "clearMemberBlendColor view is null");
                return;
            }
            try {
                setMiViewBlurMode(view, 0);
                clearMiBackgroundBlendColors(view);
                DYNAMIC_LOG_D("MiuiBlurUtils", "clearMemberBlendColor view :" + view);
            } catch (Exception e) {
                Log.e("MiuiBlurUtils", "clearMemberBlendColor error , view :" + view);
                e.printStackTrace();
            }
        }
    }

    public static void clearMiBackgroundBlendColor(View view) {
        Method method = M_CLEAR_MI_BACKGROUND_BLEND_COLOR;
        if (method == null) {
            return;
        }
        try {
            method.invoke(view, new Object[0]);
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "clearMiBackgroundBlendColor", e);
        }
    }

    public static void clearMiBackgroundBlendColors(View view) {
        Method method = M_SET_MI_BACKGROUND_BLEND_COLORS;
        if (method == null) {
            return;
        }
        try {
            method.invoke(view, null);
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "clearMiBackgroundBlendColors", e);
        }
    }

    public static void disableMiBackgroundContainBelow(View view, boolean z) {
        Method method = M_DISABLE_MI_BACKGROUND_CONTAIN_BELOW;
        if (method == null) {
            return;
        }
        try {
            method.invoke(view, Boolean.valueOf(z));
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "disableMiBackgroundContainBelow", e);
        }
    }

    public static int mixColor(float f, String str) {
        int color = Color.parseColor(str);
        return Color.argb((int) (f * 255.0f), Color.red(color), Color.green(color), Color.blue(color));
    }

    public static Method resolvePaintMethod(String str, Class... clsArr) {
        try {
            Method declaredMethod = Paint.class.getDeclaredMethod(str, clsArr);
            declaredMethod.setAccessible(true);
            return declaredMethod;
        } catch (Exception unused) {
            Log.w("MiuiBlurUtils", "Paint method not found: " + str);
            return null;
        }
    }

    public static Method resolveViewMethod(String str, Class... clsArr) {
        try {
            return View.class.getMethod(str, clsArr);
        } catch (Exception unused) {
            Log.w("MiuiBlurUtils", "View method not found: " + str);
            return null;
        }
    }

    public static void setAtlasMaxPathSize(Paint paint, int i, int i2) {
        Method method = M_SET_ATLAS_MAX_PATH_SIZE;
        if (method == null) {
            return;
        }
        try {
            method.invoke(paint, Integer.valueOf(i), Integer.valueOf(i2));
            Log.d("MiuiBlurUtils", "setAtlasMaxPathSize " + i);
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "setGpuOptimize reflect failed！", e);
        }
    }

    public static boolean setContainerPassBlur(View view, int i, boolean z) {
        boolean z2;
        DYNAMIC_LOG_D("MiuiBlurUtils", "setContainerPassBlur " + view);
        if (!DeviceConfig.BACKGROUND_BLUR_SUPPORTED_2) {
            Log.d("MiuiBlurUtils", "not support blur");
            return false;
        }
        if (view == null) {
            Log.d("MiuiBlurUtils", "setPassBlur view is null");
            return false;
        }
        if (!z) {
            try {
                z2 = DateFormatUtils.isSystemUI(view.getContext());
            } catch (Exception e) {
                Log.e("MiuiBlurUtils", "setContainerPassBlur error , view :" + view);
                e.printStackTrace();
                return false;
            }
        }
        boolean passWindowBlurEnabled = setPassWindowBlurEnabled(view, z2);
        setMiBackgroundBlurMode(view, 1);
        setMiBackgroundBlurRadius(view, i);
        boolean passTextureScale = setPassTextureScale(view, 0.0f);
        if (DateFormatUtils.isSystemUI(view.getContext())) {
            disableMiBackgroundContainBelow(view, true);
        }
        DYNAMIC_LOG_D("MiuiBlurUtils", "setContainerPassBlur result :" + passWindowBlurEnabled + ",view : " + view + ", resetScale 0 = " + passTextureScale + ", multiWindowBlur=" + z);
        return passWindowBlurEnabled;
    }

    public static void setDiffEffectContainer(View view, boolean z) {
        boolean z2;
        DYNAMIC_LOG_D("MiuiBlurUtils", "setDiffEffectContainer " + view);
        if (view == null || !DeviceConfig.supportAdvanceVisualEffect(view.getContext())) {
            Log.d("MiuiBlurUtils", "not support blur");
            return;
        }
        if (!z) {
            try {
                z2 = DateFormatUtils.isSystemUI(view.getContext());
            } catch (Exception e) {
                Log.e("MiuiBlurUtils", "setDiffEffectMethod error , view :" + view + ", multiWindowBlur=" + z);
                e.printStackTrace();
                return;
            }
        }
        setPassWindowBlurEnabled(view, z2);
        setMiBackgroundBlurMode(view, 2);
        DYNAMIC_LOG_D("MiuiBlurUtils", " reset scale diffEffectContainer " + setPassTextureScale(view, 1.0f));
        if (DateFormatUtils.isSystemUI(view.getContext())) {
            disableMiBackgroundContainBelow(view, true);
        }
    }

    public static boolean setGlassBlurContainer(View view, int i, boolean z) {
        boolean z2;
        DYNAMIC_LOG_D("MiuiBlurUtils", "setGlassBlurContainer " + view);
        if (!DeviceConfig.BACKGROUND_BLUR_SUPPORTED_2) {
            Log.d("MiuiBlurUtils", "not support blur");
            return false;
        }
        if (view == null) {
            Log.d("MiuiBlurUtils", "setPassBlur view is null");
            return false;
        }
        if (!z) {
            try {
                z2 = DateFormatUtils.isSystemUI(view.getContext());
            } catch (Exception e) {
                Log.e("MiuiBlurUtils", "setContainerPassBlur error , view :" + view);
                e.printStackTrace();
                return false;
            }
        }
        boolean passWindowBlurEnabled = setPassWindowBlurEnabled(view, z2);
        setMiBackgroundBlurMode(view, 1);
        setMiGlassBlurRadius(view, i, i);
        if (DateFormatUtils.isSystemUI(view.getContext())) {
            disableMiBackgroundContainBelow(view, true);
        }
        DYNAMIC_LOG_D("MiuiBlurUtils", "setContainerPassBlur result :" + passWindowBlurEnabled + ",view : " + view + ", multiWindowBlur=" + z);
        return passWindowBlurEnabled;
    }

    public static void setGlassEffectMethod(View view, float[] fArr) {
        DYNAMIC_LOG_D("MiuiBlurUtils", "setGlassEffectMethod " + view);
        if (view == null) {
            Log.d("MiuiBlurUtils", "setMemberBlendColor view is null");
            return;
        }
        if (view.getContext() == null || !DeviceConfig.isHighTextContrastEnabled(view.getContext())) {
            try {
                setMiViewMaterialType(view, 1);
                setMiGlass(view, fArr);
                setMiViewBlurMode(view, 3);
                setMiCustomSurfaceColorType(view, 16);
                DYNAMIC_LOG_D("MiuiBlurUtils", "setGlassEffectMethod view:" + view + "， glassData:" + Arrays.toString(fArr));
            } catch (Exception e) {
                Log.e("MiuiBlurUtils", "setGlassEffectMethod error , view :" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void setGlowEffectContainer(View view) {
        DYNAMIC_LOG_D("MiuiBlurUtils", "setGlowEffectContainer " + view);
        if (view == null || !DeviceConfig.supportAdvanceVisualEffect(view.getContext())) {
            return;
        }
        setPassWindowBlurEnabled(view, DateFormatUtils.isSystemUI(view.getContext()));
        if (DateFormatUtils.isSystemUI(view.getContext())) {
            disableMiBackgroundContainBelow(view, true);
        }
    }

    public static void setGlowEffectMethod(View view, Effect.Glow glow, boolean z, boolean z2) {
        DYNAMIC_LOG_D("MiuiBlurUtils", "setGlowEffectMethod " + view);
        if (view == null || glow == null || !DeviceConfig.supportAdvanceVisualEffect(view.getContext())) {
            return;
        }
        int iTransformFullAodColor = glow.srcColor;
        if (z2) {
            iTransformFullAodColor = GlobalColorUtils.transformFullAodColor(iTransformFullAodColor);
        }
        float f = glow.viewColorAlpha;
        float f2 = glow.glowColorAlpha;
        float f3 = 0.0f;
        if (z || z2) {
            f = 0.0f;
            f2 = 0.0f;
        }
        int iMixColor = mixColor(f, glow.viewColor);
        int iMixColor2 = mixColor(f2, glow.glowColor);
        float f4 = glow.viewBlur;
        float f5 = glow.glowBlur;
        if (z || z2) {
            f5 = 0.0f;
        } else {
            f3 = f4;
        }
        float f6 = glow.spread;
        int i = glow.viewBlendMode;
        int i2 = glow.glowBlendMode;
        Method method = M_SET_GLOW_EFFECT_8;
        if (method != null) {
            try {
                method.invoke(view, Integer.valueOf(iTransformFullAodColor), Integer.valueOf(iMixColor), Integer.valueOf(iMixColor2), Float.valueOf(f3), Float.valueOf(f5), Float.valueOf(f6), Integer.valueOf(i), Integer.valueOf(i2));
            } catch (Exception e) {
                Log.e("MiuiBlurUtils", "setGlowEffectMethod(8)", e);
            }
        }
    }

    public static void setGradientBlurMethod(View view, float[] fArr) {
        Method method;
        if (view == null || fArr == null || !DeviceConfig.supportAdvanceVisualEffect(view.getContext()) || DeviceConfig.isHighTextContrastEnabled(view.getContext())) {
            return;
        }
        DYNAMIC_LOG_D("MiuiBlurUtils", "setGradientBlurMethod " + view + ", info = " + Arrays.toString(fArr));
        if (DateFormatUtils.isSystemUI(view.getContext())) {
            disableMiBackgroundContainBelow(view, true);
        }
        Method method2 = M_SET_MI_SELF_BLUR_TYPE;
        if (method2 == null || (method = M_SET_SELF_GRADIENT_BLUR_PARAMS) == null) {
            return;
        }
        try {
            method2.invoke(view, 2);
            method.invoke(view, fArr, 2);
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "setGradientBlurMethod", e);
        }
    }

    public static void setGradientBlurMethod(View view, float[] fArr, float f) {
        setGradientBlurMethod(view, ClockEffectUtils.scaleGradientParams(fArr, f));
    }

    public static void setMemberBlendColor(View view, boolean z, int i) {
        setMemberBlendColor(view, z, i, 255);
    }

    public static void setMemberBlendColor(View view, boolean z, int i, int i2) {
        DYNAMIC_LOG_D("MiuiBlurUtils", "setMemberBlendColor " + view);
        if (view == null || !DeviceConfig.supportBackgroundBlur(view.getContext())) {
            Log.d("MiuiBlurUtils", "setMemberBlendColor view is null");
            return;
        }
        try {
            clearMiBackgroundBlendColor(view);
            setMiViewBlurMode(view, 3);
            int iArgb = Color.argb(i2, Color.red(i), Color.green(i), Color.blue(i));
            int iArgb2 = Color.argb(i2, 0, 0, 0);
            addMiBackgroundBlendColor(view, iArgb, 101);
            if (z) {
                addMiBackgroundBlendColor(view, iArgb2, R.styleable.AppCompatTheme_textAppearanceListItemSmall);
            } else {
                addMiBackgroundBlendColor(view, iArgb2, 103);
            }
            DYNAMIC_LOG_D("MiuiBlurUtils", "setMemberBlendColor: view:" + view + ",colorDark:" + z + ",color:" + Integer.toHexString(iArgb) + ",labColor:" + Integer.toHexString(iArgb2));
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "setMemberBlendColor error , view :" + view);
            e.printStackTrace();
        }
    }

    public static void setMemberBlendColors(View view, boolean z, int i, int i2) {
        setMemberBlendColors(view, z, i, 255, i2, true, 0);
    }

    public static void setMemberBlendColors(View view, boolean z, int i, int i2, int i3) {
        setMemberBlendColors(view, z, i, 255, i2, true, i3);
    }

    public static void setMemberBlendColors(View view, boolean z, int i, int i2, int i3, int i4, boolean z2, int i5) {
        DYNAMIC_LOG_D("MiuiBlurUtils", "setMemberBlendColors " + view);
        if (view == null || !DeviceConfig.supportBackgroundBlur(view.getContext())) {
            Log.d("MiuiBlurUtils", "setMemberBlendColor view is null");
            return;
        }
        if (view.getContext() == null || !DeviceConfig.isHighTextContrastEnabled(view.getContext())) {
            try {
                setMiViewBlurMode(view, 3);
                int iArgb = Color.argb(i2, Color.red(i), Color.green(i), Color.blue(i));
                int iArgb2 = Color.argb(i3, 0, 0, 0);
                ArrayList arrayList = new ArrayList();
                arrayList.add(new Point(iArgb, 101));
                if (z) {
                    arrayList.add(new Point(iArgb2, R.styleable.AppCompatTheme_textAppearanceListItemSmall));
                } else {
                    arrayList.add(new Point(iArgb2, 103));
                }
                if (z2 && DeviceConfig.supportBlurMode1000() && i4 != 0) {
                    arrayList.add(new Point(i4, 1000));
                }
                if (i5 != 0) {
                    arrayList.add(new Point(i5, 3));
                }
                setMiBackgroundBlendColors(view, arrayList);
                DYNAMIC_LOG_D("MiuiBlurUtils", "setMemberBlendColor: view:" + view + ",colorDark:" + z + ",color:" + Integer.toHexString(iArgb) + ",originColor:" + Integer.toHexString(i4) + ",overColor:" + Integer.toHexString(i5) + ",labColor:" + Integer.toHexString(iArgb2) + ",colorAlpha:" + i2 + ",labColorAlpha:" + i3 + ",useOriginColor:" + z2);
            } catch (Exception e) {
                Log.e("MiuiBlurUtils", "setMemberBlendColor error , view :" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void setMemberBlendColors(View view, boolean z, int i, int i2, int i3, boolean z2, int i4) {
        setMemberBlendColors(view, z, i, i2, i2, i3, z2, i4);
    }

    public static void setMemberDiffEffectColor(View view, int i) {
        DYNAMIC_LOG_D("MiuiBlurUtils", "setMemberDiffEffectColor " + view);
        if (view == null || !DeviceConfig.supportAdvanceVisualEffect(view.getContext())) {
            Log.d("MiuiBlurUtils", "setDiffEffectMethod view is null");
            return;
        }
        try {
            setMiViewBlurMode(view, 3);
            ArrayList arrayList = new ArrayList();
            arrayList.add(new Point(i, 20));
            setMiBackgroundBlendColors(view, arrayList);
            DYNAMIC_LOG_D("MiuiBlurUtils", "setMemberDiffEffectColor diffColor = " + i);
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "setDiffEffectMethod error , view :" + view);
            e.printStackTrace();
        }
    }

    public static void setMiBackgroundBlendColors(View view, List list) {
        Method method = M_SET_MI_BACKGROUND_BLEND_COLORS;
        if (method == null) {
            return;
        }
        try {
            method.invoke(view, list);
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "setMiBackgroundBlendColors", e);
        }
    }

    public static void setMiBackgroundBlurMode(View view, int i) {
        Method method = M_SET_MI_BACKGROUND_BLUR_MODE;
        if (method == null) {
            return;
        }
        try {
            method.invoke(view, Integer.valueOf(i));
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "setMiBackgroundBlurMode", e);
        }
    }

    public static void setMiBackgroundBlurRadius(View view, int i) {
        Method method = M_SET_MI_BACKGROUND_BLUR_RADIUS;
        if (method == null) {
            return;
        }
        try {
            method.invoke(view, Integer.valueOf(i));
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "setMiBackgroundBlurRadius", e);
        }
    }

    public static void setMiCustomSurfaceColorType(View view, int i) {
        Method method = M_SET_MI_CUSTOM_SURFACE_COLOR_TYPE;
        if (method == null) {
            return;
        }
        try {
            method.invoke(view, Integer.valueOf(i));
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "setMiCustomSurfaceColorType", e);
        }
    }

    public static void setMiGlass(View view, float[] fArr) {
        Method method = M_SET_MI_GLASS;
        if (method == null) {
            return;
        }
        try {
            method.invoke(view, fArr);
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "setMiGlass", e);
        }
    }

    public static void setMiGlassBlurRadius(View view, int i, int i2) {
        Method method = M_SET_MI_GLASS_BLUR_RADIUS;
        if (method == null) {
            return;
        }
        try {
            method.invoke(view, Integer.valueOf(i), Integer.valueOf(i2));
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "setMiGlassBlurRadius", e);
        }
    }

    public static void setMiGlassClip(View view, float f, float f2, float f3, float f4) {
        Method method = M_SET_MI_GLASS_CLIP;
        if (method == null) {
            return;
        }
        try {
            method.invoke(view, Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4));
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "setMiGlassClip", e);
        }
    }

    public static void setMiViewBlurMode(View view, int i) {
        Method method = M_SET_MI_VIEW_BLUR_MODE;
        if (method == null) {
            return;
        }
        try {
            method.invoke(view, Integer.valueOf(i));
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "setMiViewBlurMode", e);
        }
    }

    public static void setMiViewMaterialType(View view, int i) {
        Method method = M_SET_MI_VIEW_MATERIAL_TYPE;
        if (method == null) {
            return;
        }
        try {
            method.invoke(view, Integer.valueOf(i));
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "setMiViewMaterialType", e);
        }
    }

    public static void setPaintGlassEffect(Paint paint, boolean z) {
        Method method = M_SET_GLASS_EFFECT;
        if (method == null) {
            return;
        }
        try {
            method.invoke(paint, Boolean.valueOf(z));
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "setGlassEffect reflect failed！", e);
        }
    }

    public static boolean setPassTextureScale(View view, float f) {
        Method method = M_SET_PASS_TEXTURE_SCALE;
        if (method == null) {
            return false;
        }
        try {
            Object objInvoke = method.invoke(view, Float.valueOf(f));
            if (objInvoke instanceof Boolean) {
                return ((Boolean) objInvoke).booleanValue();
            }
            return false;
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "setPassTextureScale", e);
            return false;
        }
    }

    public static boolean setPassWindowBlurEnabled(View view, boolean z) {
        Method method = M_SET_PASS_WINDOW_BLUR_ENABLED;
        if (method == null) {
            return false;
        }
        try {
            Object objInvoke = method.invoke(view, Boolean.valueOf(z));
            if (objInvoke instanceof Boolean) {
                return ((Boolean) objInvoke).booleanValue();
            }
            return false;
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "setPassWindowBlurEnabled", e);
            return false;
        }
    }

    public static void simplifyPath(Paint paint, Path path) {
        Method method = M_SIMPLIFY_PATH;
        if (method == null) {
            return;
        }
        try {
            method.invoke(paint, path);
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "simplifyPath failed", e);
        }
    }
}
