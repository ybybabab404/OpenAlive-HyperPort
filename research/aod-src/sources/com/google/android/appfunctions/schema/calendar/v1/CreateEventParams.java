package com.google.android.appfunctions.schema.calendar.v1;

import androidx.appfunctions.AppFunctionSerializable;
import com.google.android.appfunctions.schema.types.v1.DateTime;
import java.util.List;
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
public final class CreateEventParams {
    public final Boolean allDay;
    public final List attendeeIds;
    public final String description;
    public final DateTime endDate;
    public final String eventStatus;
    public final String externalId;
    public final String location;
    public final String recurrenceSchedule;
    public final DateTime startDate;
    public final String title;

    public CreateEventParams(String title, String str, DateTime startDate, DateTime endDate, List attendeeIds, Boolean bool, String str2, String str3, String str4, String str5) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(startDate, "startDate");
        Intrinsics.checkNotNullParameter(endDate, "endDate");
        Intrinsics.checkNotNullParameter(attendeeIds, "attendeeIds");
        this.title = title;
        this.description = str;
        this.startDate = startDate;
        this.endDate = endDate;
        this.attendeeIds = attendeeIds;
        this.allDay = bool;
        this.location = str2;
        this.recurrenceSchedule = str3;
        this.eventStatus = str4;
        this.externalId = str5;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CreateEventParams)) {
            return false;
        }
        CreateEventParams createEventParams = (CreateEventParams) obj;
        return Intrinsics.areEqual(this.title, createEventParams.title) && Intrinsics.areEqual(this.description, createEventParams.description) && Intrinsics.areEqual(this.startDate, createEventParams.startDate) && Intrinsics.areEqual(this.endDate, createEventParams.endDate) && Intrinsics.areEqual(this.attendeeIds, createEventParams.attendeeIds) && Intrinsics.areEqual(this.allDay, createEventParams.allDay) && Intrinsics.areEqual(this.location, createEventParams.location) && Intrinsics.areEqual(this.recurrenceSchedule, createEventParams.recurrenceSchedule) && Intrinsics.areEqual(this.eventStatus, createEventParams.eventStatus) && Intrinsics.areEqual(this.externalId, createEventParams.externalId);
    }

    public final Boolean getAllDay() {
        return this.allDay;
    }

    public final List getAttendeeIds() {
        return this.attendeeIds;
    }

    public final String getDescription() {
        return this.description;
    }

    public final DateTime getEndDate() {
        return this.endDate;
    }

    public final String getEventStatus() {
        return this.eventStatus;
    }

    public final String getExternalId() {
        return this.externalId;
    }

    public final String getLocation() {
        return this.location;
    }

    public final String getRecurrenceSchedule() {
        return this.recurrenceSchedule;
    }

    public final DateTime getStartDate() {
        return this.startDate;
    }

    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.title, this.description, this.startDate, this.endDate, this.attendeeIds, this.allDay, this.location, this.recurrenceSchedule, this.eventStatus, this.externalId);
    }
}
