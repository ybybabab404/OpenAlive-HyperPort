package com.google.android.appfunctions.schema.calendar.v1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.google.android.appfunctions.schema.types.v1.C$DateTimeFactory;
import com.google.android.appfunctions.schema.types.v1.C$SetFieldFactory;
import com.google.android.appfunctions.schema.types.v1.DateTime;
import com.google.android.appfunctions.schema.types.v1.SetField;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.calendar.v1.$UpdateEventParamsFactory, reason: invalid class name */
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
public final class C$UpdateEventParamsFactory implements AppFunctionSerializableFactory<UpdateEventParams> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public UpdateEventParams fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.calendar.v1.UpdateEventParams");
        C$SetFieldFactory c$SetFieldFactory = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory2 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory3 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.SerializableTypeParameter(DateTime.class, new C$DateTimeFactory()));
        C$SetFieldFactory c$SetFieldFactory4 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveListTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory5 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(Boolean.TYPE));
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("eventId");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("title");
        SetField setFieldFromAppFunctionData = appFunctionData2 != null ? c$SetFieldFactory.fromAppFunctionData(appFunctionData2) : null;
        AppFunctionData appFunctionData3 = appFunctionDataWithSpec.getAppFunctionData("description");
        SetField setFieldFromAppFunctionData2 = appFunctionData3 != null ? c$SetFieldFactory2.fromAppFunctionData(appFunctionData3) : null;
        AppFunctionData appFunctionData4 = appFunctionDataWithSpec.getAppFunctionData("startDate");
        SetField setFieldFromAppFunctionData3 = appFunctionData4 != null ? c$SetFieldFactory3.fromAppFunctionData(appFunctionData4) : null;
        AppFunctionData appFunctionData5 = appFunctionDataWithSpec.getAppFunctionData("endDate");
        SetField setFieldFromAppFunctionData4 = appFunctionData5 != null ? c$SetFieldFactory3.fromAppFunctionData(appFunctionData5) : null;
        AppFunctionData appFunctionData6 = appFunctionDataWithSpec.getAppFunctionData("attendeeIds");
        SetField setFieldFromAppFunctionData5 = appFunctionData6 != null ? c$SetFieldFactory4.fromAppFunctionData(appFunctionData6) : null;
        AppFunctionData appFunctionData7 = appFunctionDataWithSpec.getAppFunctionData("allDay");
        SetField setFieldFromAppFunctionData6 = appFunctionData7 != null ? c$SetFieldFactory5.fromAppFunctionData(appFunctionData7) : null;
        AppFunctionData appFunctionData8 = appFunctionDataWithSpec.getAppFunctionData("location");
        SetField setFieldFromAppFunctionData7 = appFunctionData8 != null ? c$SetFieldFactory2.fromAppFunctionData(appFunctionData8) : null;
        AppFunctionData appFunctionData9 = appFunctionDataWithSpec.getAppFunctionData("recurrenceSchedule");
        SetField setFieldFromAppFunctionData8 = appFunctionData9 != null ? c$SetFieldFactory2.fromAppFunctionData(appFunctionData9) : null;
        AppFunctionData appFunctionData10 = appFunctionDataWithSpec.getAppFunctionData("eventStatus");
        return new UpdateEventParams(stringOrNull, setFieldFromAppFunctionData, setFieldFromAppFunctionData2, setFieldFromAppFunctionData3, setFieldFromAppFunctionData4, setFieldFromAppFunctionData5, setFieldFromAppFunctionData6, setFieldFromAppFunctionData7, setFieldFromAppFunctionData8, appFunctionData10 != null ? c$SetFieldFactory.fromAppFunctionData(appFunctionData10) : null);
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
    public AppFunctionData toAppFunctionData(@NonNull UpdateEventParams appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$SetFieldFactory c$SetFieldFactory = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory2 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory3 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.SerializableTypeParameter(DateTime.class, new C$DateTimeFactory()));
        C$SetFieldFactory c$SetFieldFactory4 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveListTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory5 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(Boolean.TYPE));
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.calendar.v1.UpdateEventParams");
        appFunctionDataBuilder.setString("eventId", appFunctionSerializable.getEventId());
        SetField title = appFunctionSerializable.getTitle();
        if (title != null) {
            appFunctionDataBuilder.setAppFunctionData("title", c$SetFieldFactory.toAppFunctionData(title));
        }
        SetField description = appFunctionSerializable.getDescription();
        if (description != null) {
            appFunctionDataBuilder.setAppFunctionData("description", c$SetFieldFactory2.toAppFunctionData(description));
        }
        SetField startDate = appFunctionSerializable.getStartDate();
        if (startDate != null) {
            appFunctionDataBuilder.setAppFunctionData("startDate", c$SetFieldFactory3.toAppFunctionData(startDate));
        }
        SetField endDate = appFunctionSerializable.getEndDate();
        if (endDate != null) {
            appFunctionDataBuilder.setAppFunctionData("endDate", c$SetFieldFactory3.toAppFunctionData(endDate));
        }
        SetField attendeeIds = appFunctionSerializable.getAttendeeIds();
        if (attendeeIds != null) {
            appFunctionDataBuilder.setAppFunctionData("attendeeIds", c$SetFieldFactory4.toAppFunctionData(attendeeIds));
        }
        SetField allDay = appFunctionSerializable.getAllDay();
        if (allDay != null) {
            appFunctionDataBuilder.setAppFunctionData("allDay", c$SetFieldFactory5.toAppFunctionData(allDay));
        }
        SetField location = appFunctionSerializable.getLocation();
        if (location != null) {
            appFunctionDataBuilder.setAppFunctionData("location", c$SetFieldFactory2.toAppFunctionData(location));
        }
        SetField recurrenceSchedule = appFunctionSerializable.getRecurrenceSchedule();
        if (recurrenceSchedule != null) {
            appFunctionDataBuilder.setAppFunctionData("recurrenceSchedule", c$SetFieldFactory2.toAppFunctionData(recurrenceSchedule));
        }
        SetField eventStatus = appFunctionSerializable.getEventStatus();
        if (eventStatus != null) {
            appFunctionDataBuilder.setAppFunctionData("eventStatus", c$SetFieldFactory.toAppFunctionData(eventStatus));
        }
        return appFunctionDataBuilder.build();
    }
}
