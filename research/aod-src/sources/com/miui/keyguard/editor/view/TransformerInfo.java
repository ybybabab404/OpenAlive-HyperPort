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
/* JADX INFO: compiled from: LockScreenTransformerLayer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class TransformerInfo {
    public final TransformerPositionInfo bottomPositionInfo;
    public final TransformerPositionInfo currentPositionInfo;
    public final Integer descriptionRes;
    public final TransformerPositionInfo leftPositionInfo;
    public final TransformerPositionInfo rightPositionInfo;
    public final Integer titleRes;

    public TransformerInfo(Integer num, Integer num2, TransformerPositionInfo transformerPositionInfo, TransformerPositionInfo transformerPositionInfo2, TransformerPositionInfo transformerPositionInfo3, TransformerPositionInfo transformerPositionInfo4) {
        this.titleRes = num;
        this.descriptionRes = num2;
        this.currentPositionInfo = transformerPositionInfo;
        this.leftPositionInfo = transformerPositionInfo2;
        this.rightPositionInfo = transformerPositionInfo3;
        this.bottomPositionInfo = transformerPositionInfo4;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TransformerInfo)) {
            return false;
        }
        TransformerInfo transformerInfo = (TransformerInfo) obj;
        return Intrinsics.areEqual(this.titleRes, transformerInfo.titleRes) && Intrinsics.areEqual(this.descriptionRes, transformerInfo.descriptionRes) && Intrinsics.areEqual(this.currentPositionInfo, transformerInfo.currentPositionInfo) && Intrinsics.areEqual(this.leftPositionInfo, transformerInfo.leftPositionInfo) && Intrinsics.areEqual(this.rightPositionInfo, transformerInfo.rightPositionInfo) && Intrinsics.areEqual(this.bottomPositionInfo, transformerInfo.bottomPositionInfo);
    }

    public final TransformerPositionInfo getBottomPositionInfo() {
        return this.bottomPositionInfo;
    }

    public final TransformerPositionInfo getCurrentPositionInfo() {
        return this.currentPositionInfo;
    }

    public final Integer getDescriptionRes() {
        return this.descriptionRes;
    }

    public final TransformerPositionInfo getLeftPositionInfo() {
        return this.leftPositionInfo;
    }

    public final TransformerPositionInfo getRightPositionInfo() {
        return this.rightPositionInfo;
    }

    public final Integer getTitleRes() {
        return this.titleRes;
    }

    public int hashCode() {
        Integer num = this.titleRes;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        Integer num2 = this.descriptionRes;
        int iHashCode2 = (iHashCode + (num2 == null ? 0 : num2.hashCode())) * 31;
        TransformerPositionInfo transformerPositionInfo = this.currentPositionInfo;
        int iHashCode3 = (iHashCode2 + (transformerPositionInfo == null ? 0 : transformerPositionInfo.hashCode())) * 31;
        TransformerPositionInfo transformerPositionInfo2 = this.leftPositionInfo;
        int iHashCode4 = (iHashCode3 + (transformerPositionInfo2 == null ? 0 : transformerPositionInfo2.hashCode())) * 31;
        TransformerPositionInfo transformerPositionInfo3 = this.rightPositionInfo;
        int iHashCode5 = (iHashCode4 + (transformerPositionInfo3 == null ? 0 : transformerPositionInfo3.hashCode())) * 31;
        TransformerPositionInfo transformerPositionInfo4 = this.bottomPositionInfo;
        return iHashCode5 + (transformerPositionInfo4 != null ? transformerPositionInfo4.hashCode() : 0);
    }

    public String toString() {
        return "TransformerInfo(titleRes=" + this.titleRes + ", descriptionRes=" + this.descriptionRes + ", currentPositionInfo=" + this.currentPositionInfo + ", leftPositionInfo=" + this.leftPositionInfo + ", rightPositionInfo=" + this.rightPositionInfo + ", bottomPositionInfo=" + this.bottomPositionInfo + ")";
    }
}
