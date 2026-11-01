package com.google.android.appfunctions.schema.calendar.v1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.google.android.appfunctions.schema.types.v1.C$DateTimeFactory;
import com.google.android.appfunctions.schema.types.v1.DateTime;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.calendar.v1.$CreateEventParamsFactory, reason: invalid class name */
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
public final class C$CreateEventParamsFactory implements AppFunctionSerializableFactory<CreateEventParams> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public CreateEventParams fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.calendar.v1.CreateEventParams");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("title");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("description");
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("startDate");
        if (appFunctionData2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        DateTime dateTimeFromAppFunctionData = c$DateTimeFactory.fromAppFunctionData(appFunctionData2);
        AppFunctionData appFunctionData3 = appFunctionDataWithSpec.getAppFunctionData("endDate");
        if (appFunctionData3 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        DateTime dateTimeFromAppFunctionData2 = c$DateTimeFactory.fromAppFunctionData(appFunctionData3);
        List stringList = appFunctionDataWithSpec.getStringList("attendeeIds");
        if (stringList == null) {
            stringList = CollectionsKt.emptyList();
        }
        return new CreateEventParams(stringOrNull, stringOrNull2, dateTimeFromAppFunctionData, dateTimeFromAppFunctionData2, stringList, appFunctionDataWithSpec.getBooleanOrNull("allDay"), appFunctionDataWithSpec.getStringOrNull("location"), appFunctionDataWithSpec.getStringOrNull("recurrenceSchedule"), appFunctionDataWithSpec.getStringOrNull("eventStatus"), appFunctionDataWithSpec.getStringOrNull("externalId"));
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
    public AppFunctionData toAppFunctionData(@NonNull CreateEventParams appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.calendar.v1.CreateEventParams");
        appFunctionDataBuilder.setString("title", appFunctionSerializable.getTitle());
        String description = appFunctionSerializable.getDescription();
        if (description != null) {
            appFunctionDataBuilder.setString("description", description);
        }
        appFunctionDataBuilder.setAppFunctionData("startDate", c$DateTimeFactory.toAppFunctionData(appFunctionSerializable.getStartDate()));
        appFunctionDataBuilder.setAppFunctionData("endDate", c$DateTimeFactory.toAppFunctionData(appFunctionSerializable.getEndDate()));
        appFunctionDataBuilder.setStringList("attendeeIds", appFunctionSerializable.getAttendeeIds());
        Boolean allDay = appFunctionSerializable.getAllDay();
        if (allDay != null) {
            appFunctionDataBuilder.setBoolean("allDay", allDay.booleanValue());
        }
        String location = appFunctionSerializable.getLocation();
        if (location != null) {
            appFunctionDataBuilder.setString("location", location);
        }
        String recurrenceSchedule = appFunctionSerializable.getRecurrenceSchedule();
        if (recurrenceSchedule != null) {
            appFunctionDataBuilder.setString("recurrenceSchedule", recurrenceSchedule);
        }
        String eventStatus = appFunctionSerializable.getEventStatus();
        if (eventStatus != null) {
            appFunctionDataBuilder.setString("eventStatus", eventStatus);
        }
        String externalId = appFunctionSerializable.getExternalId();
        if (externalId != null) {
            appFunctionDataBuilder.setString("externalId", externalId);
        }
        return appFunctionDataBuilder.build();
    }
}
