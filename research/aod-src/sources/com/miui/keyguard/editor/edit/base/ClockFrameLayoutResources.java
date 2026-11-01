package com.miui.keyguard.editor.edit.base;

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
/* JADX INFO: compiled from: BaseTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ClockFrameLayoutResources {
    public final int heightRes;
    public final int marginEndRes;
    public final int marginStartRes;
    public final int marginTopRes;
    public final int widthRes;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClockFrameLayoutResources)) {
            return false;
        }
        ClockFrameLayoutResources clockFrameLayoutResources = (ClockFrameLayoutResources) obj;
        return this.widthRes == clockFrameLayoutResources.widthRes && this.heightRes == clockFrameLayoutResources.heightRes && this.marginTopRes == clockFrameLayoutResources.marginTopRes && this.marginStartRes == clockFrameLayoutResources.marginStartRes && this.marginEndRes == clockFrameLayoutResources.marginEndRes;
    }

    public int hashCode() {
        return (((((((Integer.hashCode(this.widthRes) * 31) + Integer.hashCode(this.heightRes)) * 31) + Integer.hashCode(this.marginTopRes)) * 31) + Integer.hashCode(this.marginStartRes)) * 31) + Integer.hashCode(this.marginEndRes);
    }

    public String toString() {
        return "ClockFrameLayoutResources(widthRes=" + this.widthRes + ", heightRes=" + this.heightRes + ", marginTopRes=" + this.marginTopRes + ", marginStartRes=" + this.marginStartRes + ", marginEndRes=" + this.marginEndRes + ")";
    }
}
