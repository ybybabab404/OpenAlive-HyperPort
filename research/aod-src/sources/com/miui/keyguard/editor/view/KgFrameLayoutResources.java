package com.miui.keyguard.editor.view;

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
/* JADX INFO: compiled from: KgFrame.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class KgFrameLayoutResources {
    public final Integer heightRes;
    public final Integer marginBottomRes;
    public final Integer marginEndRes;
    public final Integer marginStartRes;
    public final Integer marginTopRes;
    public final Integer widthRes;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KgFrameLayoutResources)) {
            return false;
        }
        KgFrameLayoutResources kgFrameLayoutResources = (KgFrameLayoutResources) obj;
        return Intrinsics.areEqual(this.widthRes, kgFrameLayoutResources.widthRes) && Intrinsics.areEqual(this.heightRes, kgFrameLayoutResources.heightRes) && Intrinsics.areEqual(this.marginStartRes, kgFrameLayoutResources.marginStartRes) && Intrinsics.areEqual(this.marginEndRes, kgFrameLayoutResources.marginEndRes) && Intrinsics.areEqual(this.marginTopRes, kgFrameLayoutResources.marginTopRes) && Intrinsics.areEqual(this.marginBottomRes, kgFrameLayoutResources.marginBottomRes);
    }

    public int hashCode() {
        Integer num = this.widthRes;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        Integer num2 = this.heightRes;
        int iHashCode2 = (iHashCode + (num2 == null ? 0 : num2.hashCode())) * 31;
        Integer num3 = this.marginStartRes;
        int iHashCode3 = (iHashCode2 + (num3 == null ? 0 : num3.hashCode())) * 31;
        Integer num4 = this.marginEndRes;
        int iHashCode4 = (iHashCode3 + (num4 == null ? 0 : num4.hashCode())) * 31;
        Integer num5 = this.marginTopRes;
        int iHashCode5 = (iHashCode4 + (num5 == null ? 0 : num5.hashCode())) * 31;
        Integer num6 = this.marginBottomRes;
        return iHashCode5 + (num6 != null ? num6.hashCode() : 0);
    }

    public String toString() {
        return "KgFrameLayoutResources(widthRes=" + this.widthRes + ", heightRes=" + this.heightRes + ", marginStartRes=" + this.marginStartRes + ", marginEndRes=" + this.marginEndRes + ", marginTopRes=" + this.marginTopRes + ", marginBottomRes=" + this.marginBottomRes + ")";
    }
}
