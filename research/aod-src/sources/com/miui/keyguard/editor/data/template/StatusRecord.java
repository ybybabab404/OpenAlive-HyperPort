package com.miui.keyguard.editor.data.template;

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
    */
/* JADX INFO: compiled from: TemplateChangeRecorder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class StatusRecord {
    public Integer hierarchyStatus;
    public Integer magicType;
    public Integer matteStatus;

    public StatusRecord(Integer num, Integer num2, Integer num3) {
        this.hierarchyStatus = num;
        this.magicType = num2;
        this.matteStatus = num3;
    }

    public /* synthetic */ StatusRecord(Integer num, Integer num2, Integer num3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : num, (i & 2) != 0 ? null : num2, (i & 4) != 0 ? null : num3);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StatusRecord)) {
            return false;
        }
        StatusRecord statusRecord = (StatusRecord) obj;
        return Intrinsics.areEqual(this.hierarchyStatus, statusRecord.hierarchyStatus) && Intrinsics.areEqual(this.magicType, statusRecord.magicType) && Intrinsics.areEqual(this.matteStatus, statusRecord.matteStatus);
    }

    public final Integer getHierarchyStatus() {
        return this.hierarchyStatus;
    }

    public final Integer getMagicType() {
        return this.magicType;
    }

    public final Integer getMatteStatus() {
        return this.matteStatus;
    }

    public int hashCode() {
        Integer num = this.hierarchyStatus;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        Integer num2 = this.magicType;
        int iHashCode2 = (iHashCode + (num2 == null ? 0 : num2.hashCode())) * 31;
        Integer num3 = this.matteStatus;
        return iHashCode2 + (num3 != null ? num3.hashCode() : 0);
    }

    public final boolean isEmpty() {
        return this.hierarchyStatus == null && this.magicType == null && this.matteStatus == null;
    }

    public final boolean isNotEmpty() {
        return !isEmpty();
    }

    public final void setHierarchyStatus(Integer num) {
        this.hierarchyStatus = num;
    }

    public String toString() {
        return "StatusRecord(hierarchyStatus=" + this.hierarchyStatus + ", magicType=" + this.magicType + ", matteStatus=" + this.matteStatus + ")";
    }
}
