package com.google.android.appfunctions.schema.clock.timers.v1;

import androidx.appfunctions.AppFunctionSerializable;
import com.google.android.appfunctions.schema.types.v1.SetField;
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
public final class UpdateTimerParams {
    public final SetField durationMs;
    public final SetField label;
    public final String timerId;
    public final SetField timerState;

    public UpdateTimerParams(String timerId, SetField setField, SetField setField2, SetField setField3) {
        Intrinsics.checkNotNullParameter(timerId, "timerId");
        this.timerId = timerId;
        this.label = setField;
        this.durationMs = setField2;
        this.timerState = setField3;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof UpdateTimerParams)) {
            return false;
        }
        UpdateTimerParams updateTimerParams = (UpdateTimerParams) obj;
        return Intrinsics.areEqual(this.timerId, updateTimerParams.timerId) && Intrinsics.areEqual(this.label, updateTimerParams.label) && Intrinsics.areEqual(this.durationMs, updateTimerParams.durationMs) && Intrinsics.areEqual(this.timerState, updateTimerParams.timerState);
    }

    public final SetField getDurationMs() {
        return this.durationMs;
    }

    public final SetField getLabel() {
        return this.label;
    }

    public final String getTimerId() {
        return this.timerId;
    }

    public final SetField getTimerState() {
        return this.timerState;
    }

    public int hashCode() {
        return Objects.hash(this.timerId, this.label, this.durationMs, this.timerState);
    }
}
