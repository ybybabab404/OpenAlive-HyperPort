package miuix.core.util;

import android.content.Context;
import android.provider.Settings;
import android.view.View;

/* JADX INFO: loaded from: classes2.dex */
public class HyperMaterialUtils {
    public static boolean isForceEnable = true;
    public static final Boolean mDefaultEnable;
    public static Boolean mEnable = null;
    public static final boolean mIsSupport;
    public static final boolean mIsSupportGlass;
    public static int mVersion = -1;

    static {
        if (!isForceEnable) {
            mIsSupport = false;
            mVersion = -1;
            mDefaultEnable = Boolean.FALSE;
            mIsSupportGlass = false;
            return;
        }
        mIsSupport = Boolean.valueOf(SystemProperties.get("persist.sys.background_blur_supported", "false")).booleanValue();
        if (RomUtils.getHyperOsVersion() > 1) {
            mVersion = SystemProperties.getInt("persist.sys.advanced_visual_release", -1);
        } else if (RomUtils.getHyperOsVersion() == 1) {
            mVersion = SystemProperties.getInt("persist.sys.background_blur_version", -1);
        }
        mDefaultEnable = Boolean.valueOf(SystemProperties.get("persist.sys.background_blur_status_default", "false"));
        mIsSupportGlass = SystemProperties.getBoolean("persist.sys.bionic_material_supported", false);
    }

    public static void applyContainer(View view, MaterialConfig.BlurConfig blurConfig) {
        if (view == null || blurConfig == null) {
            return;
        }
        MiuiBlurUtils.setBlurConfig(view, view.getResources().getDisplayMetrics().density, blurConfig);
    }

    public static void applyContainerWithGlass(View view, MaterialConfig.GlassConfig glassConfig) {
        if (view == null || glassConfig == null) {
            return;
        }
        MiuiBlurUtils.setBackgroundBlurMode(view, 1);
        MiuiBlurUtils.setViewBlurMode(view, 1);
        MiuiBlurUtils.setMiViewMaterialType(view, 1);
        if (glassConfig.hasBlurRadius()) {
            MiuiBlurUtils.setMiGlassBlurRadius(view, glassConfig.smallBlurRadius, glassConfig.bigBlurRadius);
        }
        MiuiBlurUtils.setGlass(view, glassConfig.shaderParams);
    }

    public static void applyElement(View view, MaterialConfig.ColorBlendConfig colorBlendConfig, MaterialConfig.BloomStrokeConfig bloomStrokeConfig, MaterialConfig.ShadowConfig shadowConfig) {
        applyMaterialInternal(view, 1, colorBlendConfig, bloomStrokeConfig, null, shadowConfig);
    }

    public static void applyElement(View view, MaterialConfig materialConfig) {
        if (materialConfig == null) {
            clearElement(view);
        } else {
            applyElement(view, materialConfig.getColorBlendConfig(), materialConfig.getBloomStrokeConfig(), materialConfig.getShadowConfig());
        }
    }

    public static void applyMaterialInternal(View view, int i, MaterialConfig.ColorBlendConfig colorBlendConfig, MaterialConfig.BloomStrokeConfig bloomStrokeConfig, float[] fArr, MaterialConfig.ShadowConfig shadowConfig) {
        if (view == null) {
            return;
        }
        if (colorBlendConfig != null) {
            MiuiBlurUtils.setViewBlurMode(view, i);
            MiuiBlurUtils.setBackgroundBlendConfig(view, colorBlendConfig.blendColors, colorBlendConfig.blendModes);
        }
        if (bloomStrokeConfig != null) {
            HyperBloomStrokeUtils.setBloomStrokeConfig(view, bloomStrokeConfig);
        }
        if (fArr != null) {
            HyperBloomStrokeUtils.setBloomStrokeWithDp(view, fArr);
        }
        if (shadowConfig != null) {
            MiShadowUtils.setShadowConfig(view, shadowConfig);
        }
    }

    public static void applyViewMaterial(View view, MaterialConfig materialConfig) {
        if (materialConfig == null) {
            MiuiBlurUtils.setBlurConfig(view, 0.0f, null);
            MiShadowUtils.clearMiShadow(view);
            HyperBloomStrokeUtils.clearBloomStroke(view);
            return;
        }
        float f = view.getContext().getResources().getDisplayMetrics().density;
        MaterialConfig.ColorBlendConfig colorBlendConfig = materialConfig.getColorBlendConfig();
        MaterialConfig.BlurConfig blurConfig = materialConfig.getBlurConfig();
        if (colorBlendConfig != null && blurConfig == null) {
            MiuiBlurUtils.setMixEffectEnabled(view, true);
            MiuiBlurUtils.setViewBlurMode(view, 1);
            MiuiBlurUtils.setBackgroundBlendConfig(view, colorBlendConfig.blendColors, colorBlendConfig.blendModes);
        } else if (blurConfig != null) {
            MiuiBlurUtils.setMixEffectEnabled(view, false);
            MiuiBlurUtils.setBlurConfig(view, f, blurConfig);
        }
        MaterialConfig.ShadowConfig shadowConfig = materialConfig.getShadowConfig();
        if (shadowConfig != null) {
            MiShadowUtils.setShadowConfig(view, shadowConfig);
        }
        MaterialConfig.BloomStrokeConfig bloomStrokeConfig = materialConfig.getBloomStrokeConfig();
        if (bloomStrokeConfig != null) {
            HyperBloomStrokeUtils.setBloomStrokeConfig(view, bloomStrokeConfig);
        }
    }

    public static void clearContainer(View view) {
        if (view == null) {
            return;
        }
        MiuiBlurUtils.clearBackgroundBlur(view);
    }

    public static void clearElement(View view) {
        if (view == null) {
            return;
        }
        MiuiBlurUtils.setViewBlurMode(view, 0);
        MiuiBlurUtils.clearBackgroundBlendConfig(view);
        HyperBloomStrokeUtils.clearBloomStroke(view);
        MiShadowUtils.clearMiShadow(view);
    }

    public static synchronized void clearFeatureEnable() {
        mEnable = null;
    }

    public static int getMaterialType(Context context) {
        if (!mIsSupport || context == null) {
            return -1;
        }
        return Settings.Secure.getInt(context.getContentResolver(), "material_style", -1);
    }

    public static int getVersion() {
        return mVersion;
    }

    public static boolean isDefaultFeatureEnable() {
        return mDefaultEnable.booleanValue();
    }

    public static boolean isEnable() {
        return isSupport();
    }

    public static boolean isEnableBionics(Context context) {
        return getMaterialType(context) == 1;
    }

    public static synchronized boolean isFeatureEnable(Context context) {
        if (!mIsSupport) {
            return false;
        }
        if (context == null) {
            return false;
        }
        Boolean boolValueOf = Boolean.valueOf(Settings.Secure.getInt(context.getContentResolver(), "background_blur_enable", 0) == 1);
        mEnable = boolValueOf;
        return boolValueOf.booleanValue();
    }

    public static boolean isFeatureEnableNoCache(Context context) {
        return mIsSupport && context != null && Settings.Secure.getInt(context.getContentResolver(), "background_blur_enable", 0) == 1;
    }

    public static boolean isSupport() {
        return isForceEnable && mIsSupport;
    }

    public static boolean isSupportGlass() {
        return isSupport() && mIsSupportGlass;
    }
}
