package com.google.android.appfunctions.schema.clock.timers.v1;

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
public final class CreateTimerParams {
    public final String externalId;
    public final String id;
    public final String label;
    public final long originalDurationMillis;
    public final String timerState;

    public CreateTimerParams(String str, long j, String str2, String str3, String str4) {
        this.label = str;
        this.originalDurationMillis = j;
        this.timerState = str2;
        this.externalId = str3;
        this.id = str4;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CreateTimerParams)) {
            return false;
        }
        CreateTimerParams createTimerParams = (CreateTimerParams) obj;
        return Intrinsics.areEqual(this.label, createTimerParams.label) && this.originalDurationMillis == createTimerParams.originalDurationMillis && Intrinsics.areEqual(this.timerState, createTimerParams.timerState) && Intrinsics.areEqual(this.externalId, createTimerParams.externalId) && Intrinsics.areEqual(this.id, createTimerParams.id);
    }

    public final String getExternalId() {
        return this.externalId;
    }

    public final String getId() {
        return this.id;
    }

    public final String getLabel() {
        return this.label;
    }

    public final long getOriginalDurationMillis() {
        return this.originalDurationMillis;
    }

    public final String getTimerState() {
        return this.timerState;
    }

    public int hashCode() {
        return Objects.hash(this.label, Long.valueOf(this.originalDurationMillis), this.timerState, this.externalId, this.id);
    }
}
