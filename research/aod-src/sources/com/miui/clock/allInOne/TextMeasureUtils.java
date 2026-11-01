package com.miui.clock.allInOne;

import android.graphics.Paint;
import android.util.Log;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: TextMeasureUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class TextMeasureUtils {
    public static String cachedSettings;
    public static volatile Method setFontVariationOverride;
    public static final TextMeasureUtils INSTANCE = new TextMeasureUtils();
    public static float cachedWeight = Float.NaN;
    public static float cachedWidth = Float.NaN;
    public static float cachedHeight = Float.NaN;
    public static float[] charWidthsBuf = new float[8];

    public static final void applyFontVariation(Paint paint, float f, float f2, float f3) {
        Intrinsics.checkNotNullParameter(paint, "paint");
        TextMeasureUtils textMeasureUtils = INSTANCE;
        String variationSettings = textMeasureUtils.getVariationSettings(f, f2, f3);
        if (StringsKt.equals(variationSettings, paint.getFontVariationSettings(), true)) {
            return;
        }
        Method methodEnsureVariationMethod = textMeasureUtils.ensureVariationMethod(paint);
        if (methodEnsureVariationMethod == null) {
            Log.d("TextMeasureUtils", "setFontVariationOverride method not found");
            return;
        }
        try {
            methodEnsureVariationMethod.invoke(paint, variationSettings);
        } catch (Exception e) {
            Log.w("TextMeasureUtils", "applyFontVariation reflect failed", e);
        }
    }

    public static final void getSegmentWidths(Paint paint, String fullText, int[] segmentLengths, float[] outWidths) {
        Intrinsics.checkNotNullParameter(paint, "paint");
        Intrinsics.checkNotNullParameter(fullText, "fullText");
        Intrinsics.checkNotNullParameter(segmentLengths, "segmentLengths");
        Intrinsics.checkNotNullParameter(outWidths, "outWidths");
        int length = fullText.length();
        if (length > charWidthsBuf.length) {
            charWidthsBuf = new float[length];
        }
        paint.getTextWidths(fullText, charWidthsBuf);
        int length2 = segmentLengths.length;
        int i = 0;
        int i2 = 0;
        while (i < length2) {
            int i3 = segmentLengths[i] + i2;
            float f = 0.0f;
            while (i2 < i3) {
                f += charWidthsBuf[i2];
                i2++;
            }
            outWidths[i] = f;
            i++;
            i2 = i3;
        }
    }

    public static final void preparePaint(Paint paint, float f, float f2, float f3, float f4) {
        Intrinsics.checkNotNullParameter(paint, "paint");
        paint.setTextSize(f);
        applyFontVariation(paint, f2, f3, f4);
    }

    public final Method ensureVariationMethod(Paint paint) {
        Method method = setFontVariationOverride;
        if (method != null) {
            return method;
        }
        Method[] declaredMethods = paint.getClass().getDeclaredMethods();
        Intrinsics.checkNotNullExpressionValue(declaredMethods, "getDeclaredMethods(...)");
        for (Method method2 : declaredMethods) {
            if (Intrinsics.areEqual(method2.getName(), "setFontVariationOverride")) {
                method2.setAccessible(true);
                setFontVariationOverride = method2;
                return method2;
            }
            if (Intrinsics.areEqual(method2.getName(), "setCustomVariationOverride")) {
                method2.setAccessible(true);
                setFontVariationOverride = method2;
                return method2;
            }
        }
        return null;
    }

    public final String getVariationSettings(float f, float f2, float f3) {
        String str = cachedSettings;
        if (str != null && f == cachedWeight && f2 == cachedWidth && f3 == cachedHeight) {
            return str;
        }
        String str2 = "'wght' " + f + ", 'wdth' " + f2 + ", 'HGHT' " + f3;
        cachedWeight = f;
        cachedWidth = f2;
        cachedHeight = f3;
        cachedSettings = str2;
        return str2;
    }
}
