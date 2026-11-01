package com.google.android.appfunctions.schema.tasks.v1;

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
public final class UpdateTaskParams {
    public final SetField allDay;
    public final SetField completed;
    public final SetField dateTime;
    public final SetField description;
    public final SetField recurrenceSchedule;
    public final String taskId;
    public final SetField title;

    public UpdateTaskParams(String taskId, SetField setField, SetField setField2, SetField setField3, SetField setField4, SetField setField5, SetField setField6) {
        Intrinsics.checkNotNullParameter(taskId, "taskId");
        this.taskId = taskId;
        this.title = setField;
        this.description = setField2;
        this.completed = setField3;
        this.dateTime = setField4;
        this.allDay = setField5;
        this.recurrenceSchedule = setField6;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof UpdateTaskParams)) {
            return false;
        }
        UpdateTaskParams updateTaskParams = (UpdateTaskParams) obj;
        return Intrinsics.areEqual(this.taskId, updateTaskParams.taskId) && Intrinsics.areEqual(this.title, updateTaskParams.title) && Intrinsics.areEqual(this.description, updateTaskParams.description) && Intrinsics.areEqual(this.completed, updateTaskParams.completed) && Intrinsics.areEqual(this.dateTime, updateTaskParams.dateTime) && Intrinsics.areEqual(this.allDay, updateTaskParams.allDay) && Intrinsics.areEqual(this.recurrenceSchedule, updateTaskParams.recurrenceSchedule);
    }

    public final SetField getAllDay() {
        return this.allDay;
    }

    public final SetField getCompleted() {
        return this.completed;
    }

    public final SetField getDateTime() {
        return this.dateTime;
    }

    public final SetField getDescription() {
        return this.description;
    }

    public final SetField getRecurrenceSchedule() {
        return this.recurrenceSchedule;
    }

    public final String getTaskId() {
        return this.taskId;
    }

    public final SetField getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.taskId, this.title, this.description, this.completed, this.dateTime, this.allDay, this.recurrenceSchedule);
    }
}
