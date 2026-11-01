package com.google.android.appfunctions.schema.clock.alarms.v1;

import androidx.appfunctions.AppFunctionSerializable;
import com.google.android.appfunctions.schema.types.v1.TimeOfDay;
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
public final class CreateAlarmParams {
    public final String alarmStatus;
    public final DayPattern dayPattern;
    public final String externalId;
    public final String label;
    public final TimeOfDay time;

    public CreateAlarmParams(String str, TimeOfDay time, String str2, DayPattern dayPattern, String str3) {
        Intrinsics.checkNotNullParameter(time, "time");
        this.label = str;
        this.time = time;
        this.alarmStatus = str2;
        this.dayPattern = dayPattern;
        this.externalId = str3;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CreateAlarmParams)) {
            return false;
        }
        CreateAlarmParams createAlarmParams = (CreateAlarmParams) obj;
        return Intrinsics.areEqual(this.label, createAlarmParams.label) && Intrinsics.areEqual(this.time, createAlarmParams.time) && Intrinsics.areEqual(this.alarmStatus, createAlarmParams.alarmStatus) && Intrinsics.areEqual(this.dayPattern, createAlarmParams.dayPattern) && Intrinsics.areEqual(this.externalId, createAlarmParams.externalId);
    }

    public final String getAlarmStatus() {
        return this.alarmStatus;
    }

    public final DayPattern getDayPattern() {
        return this.dayPattern;
    }

    public final String getExternalId() {
        return this.externalId;
    }

    public final String getLabel() {
        return this.label;
    }

    public final TimeOfDay getTime() {
        return this.time;
    }

    public int hashCode() {
        return Objects.hash(this.label, this.time, this.alarmStatus, this.dayPattern, this.externalId);
    }
}
