package com.miui.keyguard.editor.utils;

import kotlin.Metadata;
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
/* JADX INFO: compiled from: ViewUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class BlendColorInfo {
    public final int blendColor;
    public final BlendMode blendMode;

    public BlendColorInfo(int i, BlendMode blendMode) {
        Intrinsics.checkNotNullParameter(blendMode, "blendMode");
        this.blendColor = i;
        this.blendMode = blendMode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BlendColorInfo)) {
            return false;
        }
        BlendColorInfo blendColorInfo = (BlendColorInfo) obj;
        return this.blendColor == blendColorInfo.blendColor && this.blendMode == blendColorInfo.blendMode;
    }

    public final int getBlendColor() {
        return this.blendColor;
    }

    public final BlendMode getBlendMode() {
        return this.blendMode;
    }

    public int hashCode() {
        return (Integer.hashCode(this.blendColor) * 31) + this.blendMode.hashCode();
    }

    public String toString() {
        return "BlendColorInfo(blendColor=" + this.blendColor + ", blendMode=" + this.blendMode + ")";
    }
}
