package com.google.android.appfunctions.schema.tasks.v1;

import androidx.appfunctions.AppFunctionSerializable;
import com.google.android.appfunctions.schema.types.v1.DateTime;
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
public final class Task {
    public static final Companion Companion = new Companion(null);
    public final Boolean allDay;
    public final Boolean completed;
    public final DateTime dateTime;
    public final String description;
    public final String id;
    public final String recurrenceSchedule;
    public final String taskCategoryId;
    public final String title;

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public Task(String id, String title, String str, Boolean bool, String str2, DateTime dateTime, Boolean bool2, String str3) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        this.id = id;
        this.title = title;
        this.description = str;
        this.completed = bool;
        this.taskCategoryId = str2;
        this.dateTime = dateTime;
        this.allDay = bool2;
        this.recurrenceSchedule = str3;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Task)) {
            return false;
        }
        Task task = (Task) obj;
        return Intrinsics.areEqual(this.id, task.id) && Intrinsics.areEqual(this.title, task.title) && Intrinsics.areEqual(this.description, task.description) && Intrinsics.areEqual(this.completed, task.completed) && Intrinsics.areEqual(this.taskCategoryId, task.taskCategoryId) && Intrinsics.areEqual(this.dateTime, task.dateTime) && Intrinsics.areEqual(this.allDay, task.allDay) && Intrinsics.areEqual(this.recurrenceSchedule, task.recurrenceSchedule);
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

    public final String getId() {
        return this.id;
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
        return Objects.hash(this.id, this.title, this.description, this.completed, this.taskCategoryId, this.dateTime, this.allDay, this.recurrenceSchedule);
    }
}
