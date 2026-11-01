package com.google.android.appfunctions.schema.tasks.v1;

import androidx.appfunctions.AppFunctionSerializable;
import java.util.List;
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
public final class TaskCategory {
    public static final Companion Companion = new Companion(null);
    public final String id;
    public final Boolean isReadOnly;
    public final List taskIds;
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

    public TaskCategory(String id, String title, List taskIds, Boolean bool) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(taskIds, "taskIds");
        this.id = id;
        this.title = title;
        this.taskIds = taskIds;
        this.isReadOnly = bool;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TaskCategory)) {
            return false;
        }
        TaskCategory taskCategory = (TaskCategory) obj;
        return Intrinsics.areEqual(this.id, taskCategory.id) && Intrinsics.areEqual(this.title, taskCategory.title) && Intrinsics.areEqual(this.taskIds, taskCategory.taskIds) && Intrinsics.areEqual(this.isReadOnly, taskCategory.isReadOnly);
    }

    public final String getId() {
        return this.id;
    }

    public final List getTaskIds() {
        return this.taskIds;
    }

    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.title, this.taskIds, this.isReadOnly);
    }

    public final Boolean isReadOnly() {
        return this.isReadOnly;
    }
}
