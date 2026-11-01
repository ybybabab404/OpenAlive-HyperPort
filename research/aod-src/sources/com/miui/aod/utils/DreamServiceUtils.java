package com.miui.aod.utils;

import android.content.res.Resources;
import android.service.dreams.DreamService;
import android.util.Log;
import android.util.TypedValue;
import java.lang.reflect.Method;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: DreamServiceUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class DreamServiceUtils {
    private static final int DEFAULT_LOW_BRIGHTNESS = 1;

    @NotNull
    private static final String TAG = "DreamServiceUtils";

    @NotNull
    public static final DreamServiceUtils INSTANCE = new DreamServiceUtils();

    @NotNull
    private static final Lazy DEFAULT_HIGH_BRIGHTNESS$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.aod.utils.DreamServiceUtils$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Integer.valueOf(DreamServiceUtils.DEFAULT_HIGH_BRIGHTNESS_delegate$lambda$0());
        }
    });

    @NotNull
    private static final Lazy floatBrightnessMethod$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.aod.utils.DreamServiceUtils$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return DreamServiceUtils.floatBrightnessMethod_delegate$lambda$2();
        }
    });

    @NotNull
    private static final Lazy lowBrightnessFloat$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.aod.utils.DreamServiceUtils$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Float.valueOf(DreamServiceUtils.lowBrightnessFloat_delegate$lambda$3());
        }
    });

    @NotNull
    private static final Lazy highBrightnessFloat$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.aod.utils.DreamServiceUtils$$ExternalSyntheticLambda3
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Float.valueOf(DreamServiceUtils.highBrightnessFloat_delegate$lambda$4());
        }
    });

    private DreamServiceUtils() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int DEFAULT_HIGH_BRIGHTNESS_delegate$lambda$0() {
        int i = CommonUtils.BRIGHTNESS_ON;
        if (i <= 1) {
            return 16383;
        }
        return i;
    }

    private final float convertToFloatBrightness(int i) {
        if (i == -1 || i == 0) {
            return i;
        }
        if (i == getDEFAULT_HIGH_BRIGHTNESS()) {
            return getHighBrightnessFloat();
        }
        if (i != 1) {
            Log.w(TAG, "convertToFloatBrightness brightness:" + i + " not low(1), please confirm.");
        }
        return getLowBrightnessFloat();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Method floatBrightnessMethod_delegate$lambda$2() {
        try {
            Method method = DreamService.class.getMethod("setDozeScreenBrightness", Float.TYPE);
            Log.d(TAG, "Float brightness API available");
            return method;
        } catch (NoSuchMethodException unused) {
            Log.d(TAG, "Float brightness API not available, using int API");
            return null;
        }
    }

    private final int getDEFAULT_HIGH_BRIGHTNESS() {
        return ((Number) DEFAULT_HIGH_BRIGHTNESS$delegate.getValue()).intValue();
    }

    @JvmStatic
    public static final int getDefaultHighBrightness() {
        return INSTANCE.getDEFAULT_HIGH_BRIGHTNESS();
    }

    @JvmStatic
    public static final int getDefaultLowBrightness() {
        return 1;
    }

    private final Float getDeviceFeatureFloat(String str) {
        try {
            return Float.valueOf(Class.forName("miui.os.DeviceFeature").getField(str).getFloat(null));
        } catch (Exception e) {
            Log.d(TAG, "DeviceFeature." + str + " not available: " + e.getMessage());
            return null;
        }
    }

    private final Method getFloatBrightnessMethod() {
        return (Method) floatBrightnessMethod$delegate.getValue();
    }

    private final float getFloatSystemConfig(String str, float f) {
        try {
            Resources system = Resources.getSystem();
            int identifier = system.getIdentifier(str, "dimen", "android");
            if (identifier != 0) {
                TypedValue typedValue = new TypedValue();
                system.getValue(identifier, typedValue, true);
                if (typedValue.type == 4) {
                    return typedValue.getFloat();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to get " + str, e);
        }
        return f;
    }

    private final float getHighBrightnessFloat() {
        return ((Number) highBrightnessFloat$delegate.getValue()).floatValue();
    }

    private final float getLowBrightnessFloat() {
        return ((Number) lowBrightnessFloat$delegate.getValue()).floatValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float highBrightnessFloat_delegate$lambda$4() {
        DreamServiceUtils dreamServiceUtils = INSTANCE;
        if (dreamServiceUtils.getFloatBrightnessMethod() == null) {
            Log.d(TAG, "highBrightnessFloat skipped (no float API), default: 1.0");
            return 1.0f;
        }
        Float deviceFeatureFloat = dreamServiceUtils.getDeviceFeatureFloat("BRIGHTNESS_MAX_FLOAT");
        float fFloatValue = deviceFeatureFloat != null ? deviceFeatureFloat.floatValue() : dreamServiceUtils.getFloatSystemConfig("config_screenBrightnessSettingMaximumFloat", 1.0f);
        Log.d(TAG, "highBrightnessFloat initialized: " + fFloatValue);
        return fFloatValue;
    }

    @JvmStatic
    public static final boolean isSupportFloatBrightness() {
        return INSTANCE.getFloatBrightnessMethod() != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float lowBrightnessFloat_delegate$lambda$3() {
        DreamServiceUtils dreamServiceUtils = INSTANCE;
        if (dreamServiceUtils.getFloatBrightnessMethod() == null) {
            Log.d(TAG, "lowBrightnessFloat skipped (no float API), default: 0.0");
            return 0.0f;
        }
        Float deviceFeatureFloat = dreamServiceUtils.getDeviceFeatureFloat("BRIGHTNESS_MIN_FLOAT");
        float fFloatValue = deviceFeatureFloat != null ? deviceFeatureFloat.floatValue() : dreamServiceUtils.getFloatSystemConfig("config_screenBrightnessSettingMinimumFloat", 0.0f);
        Log.d(TAG, "lowBrightnessFloat initialized: " + fFloatValue);
        return fFloatValue;
    }

    @JvmStatic
    public static final void setDebug(@NotNull DreamService dreamService, boolean z) {
        Intrinsics.checkNotNullParameter(dreamService, "dreamService");
        try {
            dreamService.setDebug(z);
        } catch (Throwable th) {
            Log.e(TAG, "setDebug error, please adapt it, " + th.getMessage());
        }
    }

    @JvmStatic
    public static final void setDozeScreenBrightness(@NotNull DreamService dreamService, int i) {
        Intrinsics.checkNotNullParameter(dreamService, "dreamService");
        DreamServiceUtils dreamServiceUtils = INSTANCE;
        Method floatBrightnessMethod = dreamServiceUtils.getFloatBrightnessMethod();
        if (floatBrightnessMethod != null) {
            float fConvertToFloatBrightness = dreamServiceUtils.convertToFloatBrightness(i);
            try {
                floatBrightnessMethod.invoke(dreamService, Float.valueOf(fConvertToFloatBrightness));
                Log.d(TAG, "brightness set to float: " + fConvertToFloatBrightness + " (from int: " + i + ")");
                return;
            } catch (Exception e) {
                Log.e(TAG, "Float brightness invoke failed, fallback to int", e);
            }
        }
        try {
            dreamService.setDozeScreenBrightness(i);
        } catch (Throwable th) {
            Log.e(TAG, "setDozeScreenBrightness error, please adapt it, " + th.getMessage());
        }
        Log.d(TAG, "brightness set to: " + i);
    }

    @JvmStatic
    public static final void setDozeScreenState(@NotNull DreamService dreamService, int i) {
        Intrinsics.checkNotNullParameter(dreamService, "dreamService");
        try {
            dreamService.setDozeScreenState(i);
        } catch (Throwable th) {
            Log.e(TAG, "setDozeScreenState error, please adapt it, " + th.getMessage());
        }
        Log.d(TAG, "screen state set to: " + i);
    }

    @JvmStatic
    public static final void startDozing(@NotNull DreamService dreamService) {
        Intrinsics.checkNotNullParameter(dreamService, "dreamService");
        try {
            dreamService.startDozing();
        } catch (Throwable th) {
            Log.e(TAG, "startDozing error, please adapt it, " + th.getMessage());
        }
    }
}
