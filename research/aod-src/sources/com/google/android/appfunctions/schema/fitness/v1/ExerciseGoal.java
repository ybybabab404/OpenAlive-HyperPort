package com.google.android.appfunctions.schema.fitness.v1;

import androidx.appfunctions.AppFunctionSerializable;
import java.util.Objects;
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
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
@AppFunctionSerializable
@Metadata
public final class ExerciseGoal {
    public final Long calories;
    public final Long distanceMeters;
    public final Long durationMillis;

    public ExerciseGoal(Long l, Long l2, Long l3) {
        this.durationMillis = l;
        this.distanceMeters = l2;
        this.calories = l3;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ExerciseGoal)) {
            return false;
        }
        ExerciseGoal exerciseGoal = (ExerciseGoal) obj;
        return Intrinsics.areEqual(this.durationMillis, exerciseGoal.durationMillis) && Intrinsics.areEqual(this.distanceMeters, exerciseGoal.distanceMeters) && Intrinsics.areEqual(this.calories, exerciseGoal.calories);
    }

    public final Long getCalories() {
        return this.calories;
    }

    public final Long getDistanceMeters() {
        return this.distanceMeters;
    }

    public final Long getDurationMillis() {
        return this.durationMillis;
    }

    public int hashCode() {
        return Objects.hash(this.durationMillis, this.distanceMeters, this.calories);
    }
}
