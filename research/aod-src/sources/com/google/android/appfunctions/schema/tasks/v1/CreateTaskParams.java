package com.google.android.appfunctions.schema.tasks.v1;

import androidx.appfunctions.AppFunctionSerializable;
import com.google.android.appfunctions.schema.types.v1.DateTime;
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
public final class CreateTaskParams {
    public final Boolean allDay;
    public final Boolean completed;
    public final DateTime dateTime;
    public final String description;
    public final String externalId;
    public final String recurrenceSchedule;
    public final String taskCategoryId;
    public final String title;

    public CreateTaskParams(String title, String str, Boolean bool, DateTime dateTime, Boolean bool2, String str2, String str3, String str4) {
        Intrinsics.checkNotNullParameter(title, "title");
        this.title = title;
        this.description = str;
        this.completed = bool;
        this.dateTime = dateTime;
        this.allDay = bool2;
        this.recurrenceSchedule = str2;
        this.externalId = str3;
        this.taskCategoryId = str4;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CreateTaskParams)) {
            return false;
        }
        CreateTaskParams createTaskParams = (CreateTaskParams) obj;
        return Intrinsics.areEqual(this.title, createTaskParams.title) && Intrinsics.areEqual(this.description, createTaskParams.description) && Intrinsics.areEqual(this.completed, createTaskParams.completed) && Intrinsics.areEqual(this.dateTime, createTaskParams.dateTime) && Intrinsics.areEqual(this.allDay, createTaskParams.allDay) && Intrinsics.areEqual(this.recurrenceSchedule, createTaskParams.recurrenceSchedule) && Intrinsics.areEqual(this.externalId, createTaskParams.externalId) && Intrinsics.areEqual(this.taskCategoryId, createTaskParams.taskCategoryId);
    }

    public final Boolean getAllDay() {
        return this.allDay;
    }

    public final Boolean getCompleted() {
        return this.completed;
    }

    public final DateTime getDateTime() {
        return this.dateTime;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getExternalId() {
        return this.externalId;
    }

    public final String getRecurrenceSchedule() {
        return this.recurrenceSchedule;
    }

    public final String getTaskCategoryId() {
        return this.taskCategoryId;
    }

    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.title, this.description, this.completed, this.dateTime, this.allDay, this.recurrenceSchedule, this.externalId, this.taskCategoryId);
    }
}
