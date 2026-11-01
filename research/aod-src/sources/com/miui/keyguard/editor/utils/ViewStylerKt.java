package com.miui.keyguard.editor.utils;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import com.miui.clock.utils.DeviceConfig;
import com.miui.keyguard.editor.EditorActivity;
import com.miui.keyguard.editor.EditorApplicationProxy;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ViewStyler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ViewStylerKt {
    public static final void addBackgroundBlendColor(final View view, int i, BlendMode blendMode) {
        try {
            InvokeCache.getOrCacheMethod$default(InvokeCache.INSTANCE, "addMiBackgroundBlendColor", 0, new Function1() { // from class: com.miui.keyguard.editor.utils.ViewStylerKt$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ViewStylerKt.addBackgroundBlendColor$lambda$3(view, (String) obj);
                }
            }, 2, null).invoke(view, Integer.valueOf(i), Integer.valueOf(blendMode.getMode()));
        } catch (Exception e) {
            Log.e("ViewUtil", "addBackgroundBlendColor error", e);
        }
    }

    public static final Method addBackgroundBlendColor$lambda$3(View view, String it) throws NoSuchMethodException {
        Intrinsics.checkNotNullParameter(it, "it");
        Class<?> cls = view.getClass();
        Class cls2 = Integer.TYPE;
        Method method = cls.getMethod(it, cls2, cls2);
        Intrinsics.checkNotNullExpressionValue(method, "getMethod(...)");
        return method;
    }

    public static final void blurAndBlendBackground(View view, BlendColorInfo[] blendColors) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(blendColors, "blendColors");
        setViewBlur$default(view, 0, 2, null);
        for (BlendColorInfo blendColorInfo : blendColors) {
            addBackgroundBlendColor(view, blendColorInfo.getBlendColor(), blendColorInfo.getBlendMode());
        }
    }

    public static final boolean canUseBlur() {
        boolean zSupportBackgroundBlur = DeviceConfig.supportBackgroundBlur(EditorApplicationProxy.Companion.getApplication());
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Log.i("ViewUtil", "BACKGROUND_BLUR_SUPPORTED " + deviceUtil.getBACKGROUND_BLUR_SUPPORTED() + "， KEY_BLUR_ENABLE= " + EditorActivity.backgroundBlurEnable + ",isSupportModel = " + zSupportBackgroundBlur);
        return deviceUtil.getBACKGROUND_BLUR_SUPPORTED() && EditorActivity.backgroundBlurEnable && zSupportBackgroundBlur;
    }

    public static final boolean canUseBlur(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            DeviceConfig.updateBackgroundBlurEnabled();
        } catch (NoSuchMethodError unused) {
            Log.e("ViewUtil", "canUseBlur: NoSuchMethodError");
        }
        boolean zSupportBackgroundBlur = DeviceConfig.supportBackgroundBlur(context);
        boolean z = Settings.Secure.getInt(context.getContentResolver(), "background_blur_enable", 0) == 1;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Log.i("ViewUtil", "BACKGROUND_BLUR_SUPPORTED " + deviceUtil.getBACKGROUND_BLUR_SUPPORTED() + "， KEY_BLUR_ENABLE= " + z + ",isSupportModel = " + zSupportBackgroundBlur);
        return deviceUtil.getBACKGROUND_BLUR_SUPPORTED() && z && zSupportBackgroundBlur;
    }

    public static final void clearBackgroundBlendColor(final View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        try {
            InvokeCache.getOrCacheMethod$default(InvokeCache.INSTANCE, "clearMiBackgroundBlendColor", 0, new Function1() { // from class: com.miui.keyguard.editor.utils.ViewStylerKt$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ViewStylerKt.clearBackgroundBlendColor$lambda$4(view, (String) obj);
                }
            }, 2, null).invoke(view, new Object[0]);
        } catch (Exception e) {
            Log.e("ViewUtil", "clearBackgroundBlendColor error", e);
        }
    }

    public static final Method clearBackgroundBlendColor$lambda$4(View view, String it) throws NoSuchMethodException {
        Intrinsics.checkNotNullParameter(it, "it");
        Method method = view.getClass().getMethod(it, new Class[0]);
        Intrinsics.checkNotNullExpressionValue(method, "getMethod(...)");
        return method;
    }

    public static final void clearBlurMode(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        try {
            Method orCacheMethod$default = InvokeCache.getOrCacheMethod$default(InvokeCache.INSTANCE, "setMiBackgroundBlurMode", 0, new Function1() { // from class: com.miui.keyguard.editor.utils.ViewStylerKt$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ViewStylerKt.clearBlurMode$lambda$5((String) obj);
                }
            }, 2, null);
            orCacheMethod$default.setAccessible(true);
            orCacheMethod$default.invoke(view, Integer.valueOf(BlurMode.CLEAR.getMode()));
        } catch (Exception e) {
            Log.e("ViewUtil", "clearBlurMode failed", e);
        }
    }

    public static final Method clearBlurMode$lambda$5(String it) throws NoSuchMethodException {
        Intrinsics.checkNotNullParameter(it, "it");
        Method declaredMethod = View.class.getDeclaredMethod(it, Integer.TYPE);
        Intrinsics.checkNotNullExpressionValue(declaredMethod, "getDeclaredMethod(...)");
        return declaredMethod;
    }

    public static final void setBackgroundBlurMode(final View view, float f, int i) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        try {
            InvokeCache invokeCache = InvokeCache.INSTANCE;
            InvokeCache.getOrCacheMethod$default(invokeCache, "setMiBackgroundBlurMode", 0, new Function1() { // from class: com.miui.keyguard.editor.utils.ViewStylerKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ViewStylerKt.setBackgroundBlurMode$lambda$0(view, (String) obj);
                }
            }, 2, null).invoke(view, Integer.valueOf(i));
            Method orCacheMethod$default = InvokeCache.getOrCacheMethod$default(invokeCache, "setMiBackgroundBlurRadius", 0, new Function1() { // from class: com.miui.keyguard.editor.utils.ViewStylerKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ViewStylerKt.setBackgroundBlurMode$lambda$1(view, (String) obj);
                }
            }, 2, null);
            int iCoerceAtMost = RangesKt.coerceAtMost((int) f, DensityUtils.dp2Px(100));
            Log.d("ViewUtil", "setBackgroundRenderEffect radius=" + iCoerceAtMost);
            orCacheMethod$default.invoke(view, Integer.valueOf(iCoerceAtMost));
        } catch (Exception e) {
            Log.e("ViewUtil", "blurBackground error", e);
        }
    }

    public static /* synthetic */ void setBackgroundBlurMode$default(View view, float f, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = BlurMode.MODE1.getMode();
        }
        setBackgroundBlurMode(view, f, i);
    }

    public static final Method setBackgroundBlurMode$lambda$0(View view, String it) throws NoSuchMethodException {
        Intrinsics.checkNotNullParameter(it, "it");
        Method method = view.getClass().getMethod(it, Integer.TYPE);
        Intrinsics.checkNotNullExpressionValue(method, "getMethod(...)");
        return method;
    }

    public static final Method setBackgroundBlurMode$lambda$1(View view, String it) throws NoSuchMethodException {
        Intrinsics.checkNotNullParameter(it, "it");
        Method method = view.getClass().getMethod(it, Integer.TYPE);
        Intrinsics.checkNotNullExpressionValue(method, "getMethod(...)");
        return method;
    }

    public static final void setViewBlur(final View view, int i) {
        try {
            InvokeCache.getOrCacheMethod$default(InvokeCache.INSTANCE, "setMiViewBlurMode", 0, new Function1() { // from class: com.miui.keyguard.editor.utils.ViewStylerKt$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ViewStylerKt.setViewBlur$lambda$2(view, (String) obj);
                }
            }, 2, null).invoke(view, Integer.valueOf(i));
        } catch (Exception e) {
            Log.e("ViewUtil", "addBackgroundBlendColor error", e);
        }
    }

    public static /* synthetic */ void setViewBlur$default(View view, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 1;
        }
        setViewBlur(view, i);
    }

    public static final Method setViewBlur$lambda$2(View view, String it) throws NoSuchMethodException {
        Intrinsics.checkNotNullParameter(it, "it");
        Method method = view.getClass().getMethod(it, Integer.TYPE);
        Intrinsics.checkNotNullExpressionValue(method, "getMethod(...)");
        return method;
    }
}
