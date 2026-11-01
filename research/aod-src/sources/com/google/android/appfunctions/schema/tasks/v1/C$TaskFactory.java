package com.google.android.appfunctions.schema.tasks.v1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.google.android.appfunctions.schema.types.v1.C$DateTimeFactory;
import com.google.android.appfunctions.schema.types.v1.DateTime;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.tasks.v1.$TaskFactory, reason: invalid class name */
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
@RequiresApi
@Metadata
public final class C$TaskFactory implements AppFunctionSerializableFactory<Task> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public Task fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.tasks.v1.Task");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("id");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("title");
        if (stringOrNull2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull3 = appFunctionDataWithSpec.getStringOrNull("description");
        Boolean booleanOrNull = appFunctionDataWithSpec.getBooleanOrNull("completed");
        String stringOrNull4 = appFunctionDataWithSpec.getStringOrNull("taskCategoryId");
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("dateTime");
        return new Task(stringOrNull, stringOrNull2, stringOrNull3, booleanOrNull, stringOrNull4, appFunctionData2 != null ? c$DateTimeFactory.fromAppFunctionData(appFunctionData2) : null, appFunctionDataWithSpec.getBooleanOrNull("allDay"), appFunctionDataWithSpec.getStringOrNull("recurrenceSchedule"));
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public /* bridge */ /* synthetic */ AppFunctionData.Builder getAppFunctionDataBuilder(@NotNull String str) {
        return super.getAppFunctionDataBuilder(str);
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public /* bridge */ /* synthetic */ AppFunctionData getAppFunctionDataWithSpec(@NotNull AppFunctionData appFunctionData, @NotNull String str) {
        return super.getAppFunctionDataWithSpec(appFunctionData, str);
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public AppFunctionData toAppFunctionData(@NonNull Task appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.tasks.v1.Task");
        appFunctionDataBuilder.setString("id", appFunctionSerializable.getId());
        appFunctionDataBuilder.setString("title", appFunctionSerializable.getTitle());
        String description = appFunctionSerializable.getDescription();
        if (description != null) {
            appFunctionDataBuilder.setString("description", description);
        }
        Boolean completed = appFunctionSerializable.getCompleted();
        if (completed != null) {
            appFunctionDataBuilder.setBoolean("completed", completed.booleanValue());
        }
        String taskCategoryId = appFunctionSerializable.getTaskCategoryId();
        if (taskCategoryId != null) {
            appFunctionDataBuilder.setString("taskCategoryId", taskCategoryId);
        }
        DateTime dateTime = appFunctionSerializable.getDateTime();
        if (dateTime != null) {
            appFunctionDataBuilder.setAppFunctionData("dateTime", c$DateTimeFactory.toAppFunctionData(dateTime));
        }
        Boolean allDay = appFunctionSerializable.getAllDay();
        if (allDay != null) {
            appFunctionDataBuilder.setBoolean("allDay", allDay.booleanValue());
        }
        String recurrenceSchedule = appFunctionSerializable.getRecurrenceSchedule();
        if (recurrenceSchedule != null) {
            appFunctionDataBuilder.setString("recurrenceSchedule", recurrenceSchedule);
        }
        return appFunctionDataBuilder.build();
    }
}
