package com.miui.keyguard.editor.utils;

import android.graphics.Bitmap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: HierarchyImageAvoidController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class DepthVideoParam {
    public final Bitmap bitmap;
    public final int showHeight;
    public final int showWidth;
    public static final Companion Companion = new Companion(null);
    public static final float[] CONVERT_ALPHA_TO_ARGB_MATRIX_VALUE = {0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};

    /* JADX INFO: compiled from: HierarchyImageAvoidController.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final float[] getCONVERT_ALPHA_TO_ARGB_MATRIX_VALUE() {
            return DepthVideoParam.CONVERT_ALPHA_TO_ARGB_MATRIX_VALUE;
        }
    }

    public DepthVideoParam(Bitmap bitmap, int i, int i2) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        this.bitmap = bitmap;
        this.showWidth = i;
        this.showHeight = i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DepthVideoParam)) {
            return false;
        }
        DepthVideoParam depthVideoParam = (DepthVideoParam) obj;
        return Intrinsics.areEqual(this.bitmap, depthVideoParam.bitmap) && this.showWidth == depthVideoParam.showWidth && this.showHeight == depthVideoParam.showHeight;
    }

    public final Bitmap getBitmap() {
        return this.bitmap;
    }

    public final int getShowHeight() {
        return this.showHeight;
    }

    public final int getShowWidth() {
        return this.showWidth;
    }

    public int hashCode() {
        return (((this.bitmap.hashCode() * 31) + Integer.hashCode(this.showWidth)) * 31) + Integer.hashCode(this.showHeight);
    }

    public String toString() {
        return "DepthVideoParam(bitmap=" + this.bitmap + ", showWidth=" + this.showWidth + ", showHeight=" + this.showHeight + ")";
    }
}
