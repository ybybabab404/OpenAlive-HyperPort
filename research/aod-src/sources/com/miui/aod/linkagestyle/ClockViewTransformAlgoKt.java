package com.miui.aod.linkagestyle;

import android.graphics.RectF;
import com.miui.aod.Utils;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ClockViewTransformAlgo.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class ClockViewTransformAlgoKt {
    public static final Object calcClockTransform(RectF targetRect, RectF clockContainerRect, int i, RectF clockRect, float f, float f2) {
        Intrinsics.checkNotNullParameter(targetRect, "targetRect");
        Intrinsics.checkNotNullParameter(clockContainerRect, "clockContainerRect");
        Intrinsics.checkNotNullParameter(clockRect, "clockRect");
        if (f < 0.0f || targetRect.width() <= 0.0f || targetRect.height() <= 0.0f || clockContainerRect.width() <= 0.0f || clockContainerRect.height() <= 0.0f || clockRect.width() <= 0.0f || clockRect.height() <= 0.0f) {
            Result.Companion companion = Result.Companion;
            return Result.m1061constructorimpl(ResultKt.createFailure(new IllegalArgumentException("invalid params, " + targetRect + ", " + clockContainerRect + ", " + clockRect + ", " + f + ", " + f2)));
        }
        float fWidth = targetRect.width();
        float fHeight = targetRect.height();
        float fWidth2 = clockContainerRect.width();
        float fHeight2 = clockContainerRect.height();
        float fWidth3 = clockRect.width();
        float fHeight3 = clockRect.height();
        float f3 = fWidth / 2.0f;
        float f4 = (fHeight / 2.0f) - f;
        float f5 = fWidth2 / 2.0f;
        float f6 = fHeight2 / 2.0f;
        float f7 = (i + (fHeight3 / 2.0f)) - f6;
        float f8 = 2 * f2;
        float fMin = Math.min(1.0f, Math.min((fWidth - f8) / fWidth3, ((fHeight - f) - f8) / fHeight3));
        float f9 = Utils.isLayoutRtl() ? f5 - f3 : f3 - f5;
        float f10 = (f4 - f6) - (f7 * fMin);
        Result.Companion companion2 = Result.Companion;
        return Result.m1061constructorimpl(new TransformResult(fMin, f9, f10));
    }
}
