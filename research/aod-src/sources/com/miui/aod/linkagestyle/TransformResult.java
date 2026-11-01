package com.miui.aod.linkagestyle;

import kotlin.Metadata;

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
public final class TransformResult {
    public final float scale;
    public final float translationX;
    public final float translationY;

    public TransformResult(float f, float f2, float f3) {
        this.scale = f;
        this.translationX = f2;
        this.translationY = f3;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TransformResult)) {
            return false;
        }
        TransformResult transformResult = (TransformResult) obj;
        return Float.compare(this.scale, transformResult.scale) == 0 && Float.compare(this.translationX, transformResult.translationX) == 0 && Float.compare(this.translationY, transformResult.translationY) == 0;
    }

    public final float getScale() {
        return this.scale;
    }

    public final float getTranslationX() {
        return this.translationX;
    }

    public final float getTranslationY() {
        return this.translationY;
    }

    public int hashCode() {
        return (((Float.hashCode(this.scale) * 31) + Float.hashCode(this.translationX)) * 31) + Float.hashCode(this.translationY);
    }

    public String toString() {
        return "TransformResult(scale=" + this.scale + ", translationX=" + this.translationX + ", translationY=" + this.translationY + ")";
    }
}
