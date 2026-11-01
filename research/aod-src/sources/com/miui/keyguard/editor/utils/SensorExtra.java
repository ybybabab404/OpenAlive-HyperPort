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
/* JADX INFO: compiled from: ThemeUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SensorExtra {
    public Integer allFrames;
    public String animType;
    public int duration;
    public float factor;
    public Integer smallFrames;
    public String smallThumbnailPath;
    public Integer stiffness;
    public String thumbnailPath;

    public SensorExtra(String str, String str2, Integer num, Integer num2, Integer num3, int i, float f, String animType) {
        Intrinsics.checkNotNullParameter(animType, "animType");
        this.thumbnailPath = str;
        this.smallThumbnailPath = str2;
        this.allFrames = num;
        this.smallFrames = num2;
        this.stiffness = num3;
        this.duration = i;
        this.factor = f;
        this.animType = animType;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SensorExtra)) {
            return false;
        }
        SensorExtra sensorExtra = (SensorExtra) obj;
        return Intrinsics.areEqual(this.thumbnailPath, sensorExtra.thumbnailPath) && Intrinsics.areEqual(this.smallThumbnailPath, sensorExtra.smallThumbnailPath) && Intrinsics.areEqual(this.allFrames, sensorExtra.allFrames) && Intrinsics.areEqual(this.smallFrames, sensorExtra.smallFrames) && Intrinsics.areEqual(this.stiffness, sensorExtra.stiffness) && this.duration == sensorExtra.duration && Float.compare(this.factor, sensorExtra.factor) == 0 && Intrinsics.areEqual(this.animType, sensorExtra.animType);
    }

    public int hashCode() {
        String str = this.thumbnailPath;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.smallThumbnailPath;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        Integer num = this.allFrames;
        int iHashCode3 = (iHashCode2 + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.smallFrames;
        int iHashCode4 = (iHashCode3 + (num2 == null ? 0 : num2.hashCode())) * 31;
        Integer num3 = this.stiffness;
        return ((((((iHashCode4 + (num3 != null ? num3.hashCode() : 0)) * 31) + Integer.hashCode(this.duration)) * 31) + Float.hashCode(this.factor)) * 31) + this.animType.hashCode();
    }

    public String toString() {
        return "SensorExtra(thumbnailPath=" + this.thumbnailPath + ", smallThumbnailPath=" + this.smallThumbnailPath + ", allFrames=" + this.allFrames + ", smallFrames=" + this.smallFrames + ", stiffness=" + this.stiffness + ", duration=" + this.duration + ", factor=" + this.factor + ", animType=" + this.animType + ")";
    }
}
