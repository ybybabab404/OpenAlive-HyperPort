package com.google.android.appfunctions.schema.clock.alarms.v1;

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
public final class UpdateAlarmParams {
    public final String alarmId;
    public final SetField alarmStatus;
    public final SetField dayPattern;
    public final SetField label;
    public final SetField time;

    public UpdateAlarmParams(String alarmId, SetField setField, SetField setField2, SetField setField3, SetField setField4) {
        Intrinsics.checkNotNullParameter(alarmId, "alarmId");
        this.alarmId = alarmId;
        this.label = setField;
        this.time = setField2;
        this.alarmStatus = setField3;
        this.dayPattern = setField4;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof UpdateAlarmParams)) {
            return false;
        }
        UpdateAlarmParams updateAlarmParams = (UpdateAlarmParams) obj;
        return Intrinsics.areEqual(this.alarmId, updateAlarmParams.alarmId) && Intrinsics.areEqual(this.label, updateAlarmParams.label) && Intrinsics.areEqual(this.time, updateAlarmParams.time) && Intrinsics.areEqual(this.alarmStatus, updateAlarmParams.alarmStatus) && Intrinsics.areEqual(this.dayPattern, updateAlarmParams.dayPattern);
    }

    public final String getAlarmId() {
        return this.alarmId;
    }

    public final SetField getAlarmStatus() {
        return this.alarmStatus;
    }

    public final SetField getDayPattern() {
        return this.dayPattern;
    }

    public final SetField getLabel() {
        return this.label;
    }

    public final SetField getTime() {
        return this.time;
    }

    public int hashCode() {
        return Objects.hash(this.alarmId, this.label, this.time, this.alarmStatus, this.dayPattern);
    }
}
