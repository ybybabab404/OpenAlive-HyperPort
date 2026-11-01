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
public final class UpdateTaskCategoryParams {
    public final String taskCategoryId;
    public final SetField taskIds;
    public final SetField title;

    public UpdateTaskCategoryParams(String taskCategoryId, SetField setField, SetField setField2) {
        Intrinsics.checkNotNullParameter(taskCategoryId, "taskCategoryId");
        this.taskCategoryId = taskCategoryId;
        this.title = setField;
        this.taskIds = setField2;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof UpdateTaskCategoryParams)) {
            return false;
        }
        UpdateTaskCategoryParams updateTaskCategoryParams = (UpdateTaskCategoryParams) obj;
        return Intrinsics.areEqual(this.taskCategoryId, updateTaskCategoryParams.taskCategoryId) && Intrinsics.areEqual(this.title, updateTaskCategoryParams.title) && Intrinsics.areEqual(this.taskIds, updateTaskCategoryParams.taskIds);
    }

    public final String getTaskCategoryId() {
        return this.taskCategoryId;
    }

    public final SetField getTaskIds() {
        return this.taskIds;
    }

    public final SetField getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.taskCategoryId, this.title, this.taskIds);
    }
}
