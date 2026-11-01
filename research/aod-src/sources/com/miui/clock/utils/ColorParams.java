package com.miui.clock.utils;

import android.animation.ArgbEvaluator;
import kotlin.Metadata;
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
/* JADX INFO: compiled from: ColorParams.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class ColorParams {
    public int currentColor;
    public int endColor;
    public final ArgbEvaluator evaluator = new ArgbEvaluator();
    public float fraction;
    public int startColor;

    public final void setEndColor(int i) {
        this.endColor = i;
    }

    public final void setFraction(float f) {
        float fCoerceIn = RangesKt.coerceIn(f, 0.0f, 1.0f);
        Object objEvaluate = this.evaluator.evaluate(fCoerceIn, Integer.valueOf(this.startColor), Integer.valueOf(this.endColor));
        Intrinsics.checkNotNull(objEvaluate, "null cannot be cast to non-null type kotlin.Int");
        this.currentColor = ((Integer) objEvaluate).intValue();
        this.fraction = fCoerceIn;
    }

    public final void setStartColor(int i) {
        this.startColor = i;
    }

    public String toString() {
        return "ColorParams(startColor=" + Integer.toHexString(this.startColor) + ", endColor=" + Integer.toHexString(this.endColor) + ", currentColor=" + Integer.toHexString(this.currentColor) + ", fraction=" + this.fraction + ')';
    }
}
