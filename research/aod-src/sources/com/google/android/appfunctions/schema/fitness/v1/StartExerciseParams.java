package com.google.android.appfunctions.schema.fitness.v1;

import androidx.appfunctions.AppFunctionSerializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
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
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
@AppFunctionSerializable
@Metadata
public final class StartExerciseParams {
    public static final Companion Companion = new Companion(null);
    public final ExerciseGoal exerciseGoal;
    public final String exerciseType;

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    @Metadata
    public static final class Companion {

        /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
        @Metadata
        @Retention(RetentionPolicy.SOURCE)
        @kotlin.annotation.Retention
        public @interface ExerciseType {
        }

        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public StartExerciseParams(String exerciseType, ExerciseGoal exerciseGoal) {
        Intrinsics.checkNotNullParameter(exerciseType, "exerciseType");
        this.exerciseType = exerciseType;
        this.exerciseGoal = exerciseGoal;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof StartExerciseParams)) {
            return false;
        }
        StartExerciseParams startExerciseParams = (StartExerciseParams) obj;
        return Intrinsics.areEqual(this.exerciseType, startExerciseParams.exerciseType) && Intrinsics.areEqual(this.exerciseGoal, startExerciseParams.exerciseGoal);
    }

    public final ExerciseGoal getExerciseGoal() {
        return this.exerciseGoal;
    }

    public final String getExerciseType() {
        return this.exerciseType;
    }

    public int hashCode() {
        return Objects.hash(this.exerciseType, this.exerciseGoal);
    }
}
